package com.jn.zfl.mySpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jn.zfl.mySpringBoot.bean.User;
import com.jn.zfl.mySpringBoot.service.UserService;

@RestController//@Controller+@ResponseBody

public class UserController {	
	
	@Autowired
	UserService userservice;
	
	@RequestMapping("/helwo")
	public String HelWo() {
		return "Hello World";
	}

	@RequestMapping("/getUserById")
	public JSONObject getUserById(@RequestParam("userId") String userId) {
		User user = userservice.getUserById(Integer.valueOf(userId));
		JSONObject json = new JSONObject();
		json.put("user",user);
		return json;
	}
}
