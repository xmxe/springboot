package com.xmxe.config;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 使用第三方代理
 */
@Component
public class CustomMethodInterceptor implements org.aopalliance.intercept.MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		System.out.println("执行方法"+method+"之前");
		System.out.println("invocation.getThis()"+invocation.getThis()+"--方法参数"+
				Arrays.toString(invocation.getArguments()));
		Object obj = invocation.proceed();
		System.out.println("执行方法"+method+"之后");
		return obj;
	}
}