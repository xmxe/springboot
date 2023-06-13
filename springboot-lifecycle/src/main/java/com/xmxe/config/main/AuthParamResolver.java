package com.xmxe.config.main;

import com.xmxe.anno.UserId;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

/**
 * 参数解析器是Spring提供的用于解析自定义参数的工具，我们常用的@RequestParam注解就有它的影子，使用它，我们可以将参数在进入Controller Action之前就组合成我们想要的样子。
 * Spring会维护一个ResolverList，在请求到达时，Spring发现有自定义类型参数（非基本类型），会依次尝试这些Resolver，直到有一个Resolver能解析需要的参数。
 * 要实现一个参数解析器，需要实现HandlerMethodArgumentResolver接口。
 */
@Component
public class AuthParamResolver implements HandlerMethodArgumentResolver {

	/**
	 * MethodParameter是Spring对被注解修饰过参数的包装，从其中能拿到参数的反射相关信息。
	 * supportsParameter传入一个参数，用以判断此参数是否能够使用该解析器。
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(UserId.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Annotation[] annotations = parameter.getParameterAnnotations();
		// 逐一处理
		for (Annotation annotation : annotations) {
			// userId
			if (annotation instanceof UserId) {
				return resolveUserId((UserId) annotation, parameter, mavContainer, webRequest, binderFactory);
			}
		}
		return null;
	}

	/**
	 * 自动在带有@UserId标注的参数前面加"0"
	 */
	private Object resolveUserId(UserId annotation, MethodParameter parameter, ModelAndViewContainer mavContainer,
	                             NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		String paramName = parameter.getParameterName();
		String userId = webRequest.getParameter(paramName);
		return userId + "0";

	}
}