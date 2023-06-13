package com.xmxe.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmxe.anno.Encrypt;
import com.xmxe.util.AESUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 使用@ResponseBody返回数据前对数据进行加密
 */
@EnableConfigurationProperties(EncryptProperties.class)
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<String> {
	private ObjectMapper om = new ObjectMapper();

	@Autowired
	EncryptProperties encryptProperties;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.hasMethodAnnotation(Encrypt.class);
	}

	@Override
	public String beforeBodyWrite(String str, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
		byte[] keyBytes = encryptProperties.getKey().getBytes();
		try {
			String encrypt = AESUtils.encrypt(str.getBytes(),keyBytes);
			return encrypt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}