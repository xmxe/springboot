package com.xmxe.config.main;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
	/**
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param o 出现异常的对象
	 * @param e 异常对象
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
		ModelAndView mv=new ModelAndView();
		if (e instanceof NullPointerException){
			mv.setViewName("error5");
		}
		if(e instanceof  ArithmeticException){
			mv.setViewName("error6");
		}
		mv.addObject("error",e.toString());
		return mv;
	}
}