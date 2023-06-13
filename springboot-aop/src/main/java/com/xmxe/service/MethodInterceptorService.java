package com.xmxe.service;

import org.springframework.stereotype.Service;

@Service
public class MethodInterceptorService {
	public String doSomething(){
		return "MethodInterceptor";
	}
}