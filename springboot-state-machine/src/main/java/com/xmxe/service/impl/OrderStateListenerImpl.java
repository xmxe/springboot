package com.xmxe.service.impl;

import com.xmxe.anno.LogResult;
import com.xmxe.entity.CommonConstants;
import com.xmxe.entity.Order;
import com.xmxe.entity.OrderStatus;
import com.xmxe.entity.OrderStatusChangeEvent;
import com.xmxe.mapper.OrderMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 监听状态的变化
 */
@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
@Slf4j
public class OrderStateListenerImpl {
	@Resource
	private OrderMapper orderMapper;

	@Resource
	private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

	@OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
	@Transactional(rollbackFor = Exception.class)
	@LogResult(key = CommonConstants.payTransition)
	public void payTransition(Message<OrderStatusChangeEvent> message) {
		Order order = (Order) message.getHeaders().get("order");
		log.info("支付，状态机反馈信息：{}",  message.getHeaders().toString());
			try{
			// 更新订单
			order.setStatus(OrderStatus.WAIT_DELIVER.getKey());
			orderMapper.updateById(order);
			// 其他业务

			//模拟异常
			if(Objects.equals(order.getName(),"A")){
				throw new RuntimeException("执行业务异常");
			}
			//成功 则为1
			orderStateMachine.getExtendedState().getVariables().put(CommonConstants.payTransition+order.getId(),1);
		} catch (Exception e) {
			//如果出现异常，则进行回滚
			log.error("payTransition 出现异常：{}",e);
			//将异常信息变量信息中，失败则为0
			orderStateMachine.getExtendedState().getVariables().put(CommonConstants.payTransition+order.getId(), 0);
			throw e;
		}
	}
	@OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
	@LogResult(key = CommonConstants.deliverTransition)
	public void deliverTransition(Message<OrderStatusChangeEvent> message) {
		Order order = (Order) message.getHeaders().get("order");
		log.info("发货，状态机反馈信息：{}",  message.getHeaders().toString());
		// 更新订单
		order.setStatus(OrderStatus.WAIT_RECEIVE.getKey());
		orderMapper.updateById(order);
		// 其他业务
	}
	@OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
	@LogResult(key = CommonConstants.receiveTransition)
	public void receiveTransition(Message<OrderStatusChangeEvent> message) {
		Order order = (Order) message.getHeaders().get("order");
		log.info("确认收货，状态机反馈信息：{}",  message.getHeaders().toString());
		// 更新订单
		order.setStatus(OrderStatus.FINISH.getKey());
		orderMapper.updateById(order);
		// 其他业务
	}
}