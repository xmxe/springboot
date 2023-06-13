package com.xmxe.config.main;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * HandlerMethodReturnValueHandler的作用是对处理器的处理结果再进行一次二次加工
 * 在Controller中调用接口自动封装一层,如调用/returnValue
 * 代码写的返回{"status": 200,"message": "请求成功","response": "book"}
 * 处理后实际返回{"status":"ok","data":{"status": 200,"message": "请求成功","response": "book"}}
 */
public class MyHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

	// 这里的实例是RequestResponseBodyMethodProcessor(在afterPropertiesSet()中定义):这个用来处理添加了@ResponseBody注解的返回值类型
	private HandlerMethodReturnValueHandler handler;

	public MyHandlerMethodReturnValueHandler(HandlerMethodReturnValueHandler handler) {
		this.handler = handler;
	}

	/**
	 * 这个处理器是否支持相应的返回值类型
	 */
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return handler.supportsReturnType(returnType);
	}

	/**
	 * 对方法返回值进行处理
	 */
	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
		/*
		 * 由于我们要做的功能其实是在RequestResponseBodyMethodProcessor基础之上实现的，因为支持@ResponseBody,输出JSON那些东西都不变，我们只是在输出之前修改一下数据而已。
		 * 所以我这里直接定义了一个属性HandlerMethodReturnValueHandler，这个属性的实例就是RequestResponseBodyMethodProcessor，supportsReturnType方法就按照RequestResponseBodyMethodProcessor的要求来，
		 * 在handleReturnValue方法中，我们先对返回值进行一个预处理，然后调用RequestResponseBodyMethodProcessor#handleReturnValue方法继续输出JSON即可。
		 */
		Map<String, Object> map = new HashMap<>();
		map.put("status", "ok");
		map.put("data", returnValue);
		handler.handleReturnValue(map, returnType, mavContainer, webRequest);
	}
}