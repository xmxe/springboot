package com.xmxe.controller;

import com.xmxe.anno.AopAction;
import com.xmxe.anno.InterceptorAnnotation;
import com.xmxe.service.AopService;
import com.xmxe.service.MethodInterceptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopController {

	@Autowired
	AopService aopService;

	@Autowired
	MethodInterceptorService methodInterceptorService;

	@AopAction//日志注解
	@GetMapping("aop")
	public String aop(){
		try {
			int c = 1/0;
		}catch (Exception e){
			e.printStackTrace();
		}
		return aopService.aopAnno();
	}

	@InterceptorAnnotation
	@GetMapping("method")
	public String method(){
		return methodInterceptorService.doSomething();
	}
}