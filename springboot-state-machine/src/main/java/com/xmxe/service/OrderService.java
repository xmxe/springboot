package com.xmxe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmxe.entity.Order;

public interface OrderService extends IService<Order> {
	/**
	 * 创建订单
	 */
	Order create(Order order);

	/**
	 * 对订单进行支付
	 */
	Order pay(Long id);

	/**
	 * 对订单进行发货
	 */
	Order deliver(Long id);

	/**
	 * 对订单进行确认收货
	 */
	Order receive(Long id);

}