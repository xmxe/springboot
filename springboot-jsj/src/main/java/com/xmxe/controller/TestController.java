package com.xmxe.controller;

import com.xmxe.entity.SerResult;
import com.xmxe.service.TestService;
import com.xmxe.service.UserService;
import com.xmxe.call.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TestController {
	private TestService testService;

	@Autowired
	public void setTestService(TestService prettyTestService) {
		this.testService = prettyTestService;
	}

	@GetMapping("test")
	public void test(){
		testService.db();
	}


	// 不用注入UserService,不用再写@Autowired 一行搞定
	@GetMapping("/{userId}")
	public SerResult<String> getUser(@PathVariable Long userId) {
		return ServiceManager.call(UserService::queryUser, userId);
	}
	// 多参数
	@PutMapping("/{userId}")
	public SerResult<Object> updateUser(@PathVariable Long userId,@RequestBody Map<String,Object> param) {
		return ServiceManager.call(UserService.updateUser(userId), param);
	}
}