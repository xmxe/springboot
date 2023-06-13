package com.xmxe.controller;

import com.xmxe.entity.Order;
import com.xmxe.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Resource
	private OrderService orderService;

	/**
	 * 根据id查询订单
	 */
	@RequestMapping("/getById")
	public Order getById(@RequestParam("id") Long id) {
		//根据id查询订单
		Order order = orderService.getById(id);
		return order;
	}

	/**
	 * 创建订单
	 */
	@RequestMapping("/create")
	public String create(@RequestBody Order order) {
		//创建订单
		orderService.create(order);
		return "sucess";
	}

	/**
	 * 对订单进行支付
	 */
	@RequestMapping("/pay")
	public String pay(@RequestParam("id") Long id) {
		//对订单进行支付
		orderService.pay(id);
		return "success";
	}

	/**
	 * 对订单进行发货
	 */
	@RequestMapping("/deliver")
	public String deliver(@RequestParam("id") Long id) {
		//对订单进行确认收货
		orderService.deliver(id);
		return "success";
	}

	/**
	 * 对订单进行确认收货
	 */
	@RequestMapping("/receive")
	public String receive(@RequestParam("id") Long id) {
		//对订单进行确认收货
		orderService.receive(id);
		return "success";
	}
}