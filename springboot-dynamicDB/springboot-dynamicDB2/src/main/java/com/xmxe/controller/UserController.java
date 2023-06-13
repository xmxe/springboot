package com.xmxe.controller;

import com.xmxe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("t")
	public void contextLoads() {
		System.out.println("userService.master() = " + userService.master());
		System.out.println("userService.slave() = " + userService.slave());
	}
}
