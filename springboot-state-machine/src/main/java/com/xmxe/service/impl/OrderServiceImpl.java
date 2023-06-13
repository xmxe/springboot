package com.xmxe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmxe.entity.CommonConstants;
import com.xmxe.entity.Order;
import com.xmxe.entity.OrderStatus;
import com.xmxe.entity.OrderStatusChangeEvent;
import com.xmxe.mapper.OrderMapper;
import com.xmxe.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("orderService")
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
	@Resource
	private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;
	// 使用内存持久化类持久化
	@Resource
	private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, String> stateMachineMemPersister;
	// 基于redis持久化
	// @Resource
	// private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, String> stateMachineRedisPersister;

	@Resource
	private OrderMapper orderMapper;

	/**
	 * 创建订单
	 */
	public Order create(Order order) {
		order.setStatus(OrderStatus.WAIT_PAYMENT.getKey());
		orderMapper.insert(order);
		return order;
	}

	/**
	 * 对订单进行支付
	 */
	public Order pay(Long id) {
		Order order = orderMapper.selectById(id);
		log.info("线程名称：{},尝试支付，订单号：{}", Thread.currentThread().getName(), id);
		if (!sendEvent(OrderStatusChangeEvent.PAYED, order)) {
			log.error("线程名称：{},支付失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
			throw new RuntimeException("支付失败, 订单状态异常");
		}
		return order;
	}

	/**
	 * 对订单进行发货
	 */
	public Order deliver(Long id) {
		Order order = orderMapper.selectById(id);
		log.info("线程名称：{},尝试发货，订单号：{}", Thread.currentThread().getName(), id);
		if (!sendEvent(OrderStatusChangeEvent.DELIVERY, order)) {
			log.error("线程名称：{},发货失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
			throw new RuntimeException("发货失败, 订单状态异常");
		}
		return order;
	}

	/**
	 * 对订单进行确认收货
	 */
	public Order receive(Long id) {
		Order order = orderMapper.selectById(id);
		log.info("线程名称：{},尝试收货，订单号：{}", Thread.currentThread().getName(), id);
		if (!sendEvent(OrderStatusChangeEvent.RECEIVED, order)) {
			log.error("线程名称：{},收货失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
			throw new RuntimeException("收货失败, 订单状态异常");
		}
		return order;
	}

	/**
	 * 发送订单状态转换事件
	 * synchronized修饰保证这个方法是线程安全的
	 */
	private synchronized boolean sendEvent_old(OrderStatusChangeEvent changeEvent, Order order) {
		boolean result = false;
		try {
			//启动状态机
			orderStateMachine.start();
			//尝试恢复状态机状态
			stateMachineMemPersister.restore(orderStateMachine, String.valueOf(order.getId()));
			Message message = MessageBuilder.withPayload(changeEvent).setHeader("order", order).build();
			result = orderStateMachine.sendEvent(message);
			//持久化状态机状态
			stateMachineMemPersister.persist(orderStateMachine, String.valueOf(order.getId()));
		} catch (Exception e) {
			log.error("订单操作失败:{}", e);
		} finally {
			orderStateMachine.stop();
		}
		return result;
	}

	/**
	 * 发送事件改造：如果获取到业务执行异常，则返回失败，不进行状态机持久化
	 */
	/**
	 * 发送订单状态转换事件
	 * synchronized修饰保证这个方法是线程安全的
	 */
	private synchronized boolean sendEvent(OrderStatusChangeEvent changeEvent, Order order){
		boolean result = false;
		try {
			//启动状态机
			orderStateMachine.start();
			//尝试恢复状态机状态
			stateMachineMemPersister.restore(orderStateMachine, String.valueOf(order.getId()));
			Message message = MessageBuilder.withPayload(changeEvent).setHeader("order", order).build();
			result = orderStateMachine.sendEvent(message);
			if(!result){
				return false;
			}
			//获取到监听的结果信息
			Integer o = (Integer) orderStateMachine.getExtendedState().getVariables().get(CommonConstants.payTransition + order.getId());
			//操作完成之后,删除本次对应的key信息
			orderStateMachine.getExtendedState().getVariables().remove(CommonConstants.payTransition+order.getId());
			//如果事务执行成功，则持久化状态机
			if(Objects.equals(1,Integer.valueOf(o))){
				//持久化状态机状态
				stateMachineMemPersister.persist(orderStateMachine, String.valueOf(order.getId()));
			}else {
				//订单执行业务异常
				return false;
			}
		} catch (Exception e) {
			log.error("订单操作失败:{}", e);
		} finally {
			orderStateMachine.stop();
		}
		return result;
	}
}