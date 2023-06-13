package com.xmxe;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xmxe.entity.Order;
import com.xmxe.entity.User;
import com.xmxe.mapper.OrderMapper;
import com.xmxe.mapper.UserMapper;
import com.xmxe.service.OrderService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ShardingJdbcApplicationTests {

	@Resource
	private OrderMapper orderMapper;

	@Resource
	private UserMapper userMapper;

	@Autowired
	OrderService orderService;

	@Test
	public void addUser() {
		for (int i = 1;i <= 10; i++) {
			User user = new User();
			user.setUserId(Long.valueOf(i));
			user.setUserName("张三" + i);
			userMapper.insert(user);
		}
	}

	@Test
	public void addOrder() {
		for (int i = 1;i <= 10; i++) {
			Order order = new Order();
			order.setUserId(Long.valueOf(i));
			order.setOrderCode("acb"+ i);
			order.setAddressId(String.valueOf(i));
			orderMapper.insert(order);
		}
	}

	@Test
	public void testListOrder(){
		List<Order> list = orderMapper.selectList(new LambdaQueryWrapper<Order>().in(Order::getUserId, Arrays.asList(1,2,3,4,5)).orderByAsc(Order::getUserId));
		System.out.println(list);
	}

	@Test
	public void updateOrder(){
		Order order = orderService.getOne(new LambdaQueryWrapper<Order>().eq(Order::getOrderId, 510462094471573504L));
		orderService.update(new LambdaUpdateWrapper<Order>().set(Order::getAddressId, "99999").eq(Order::getOrderId, 510462094471573504L));

	}

	@Test
	public void testListOrder2(){
		List<Order> list = orderService.list(new LambdaQueryWrapper<Order>().in(Order::getUserId, Arrays.asList(1L,2L,3L,4L,5L,6L,7L)).orderByAsc(Order::getUserId));
		System.out.println(list);
	}

	// UnsupportedOperationException
	@Test
	public void testListOrder3(){
		List<Order> list = orderService.list(new LambdaQueryWrapper<Order>().between(Order::getUserId, 70L, 90L).orderByAsc(Order::getUserId));
		System.out.println(list);
	}

	@Test
	public void testListOrder4(){
		List<Order> list = orderService.list(new LambdaQueryWrapper<Order>().orderByAsc(Order::getUserId));
		System.out.println(list);
	}

}