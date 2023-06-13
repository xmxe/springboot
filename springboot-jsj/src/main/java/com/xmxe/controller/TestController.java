package com.xmxe.controller;

import com.xmxe.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}