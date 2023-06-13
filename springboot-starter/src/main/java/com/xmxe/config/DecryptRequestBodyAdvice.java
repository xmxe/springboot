package com.xmxe.config;

import com.xmxe.anno.Decrypt;
import com.xmxe.util.AESUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 使用@RequestBody获取数据前解密
 * RequestBodyAdviceAdapter实现了RequestBodyAdvice接口 所以只需继承即可
 */
@EnableConfigurationProperties(EncryptProperties.class)
@ControllerAdvice
public class DecryptRequestBodyAdvice extends RequestBodyAdviceAdapter {
	@Autowired
	EncryptProperties encryptProperties;

	/**
	 * 当@Decrypt注解在方法或参数上时执行
	 * 在参数转换成具体的对象之前执行，我们先从流中加载到数据，然后对数据进行解密，解密完成后再重新构造HttpInputMessage对象返回。
	 */
	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return methodParameter.hasMethodAnnotation(Decrypt.class) || methodParameter.hasParameterAnnotation(Decrypt.class);
	}

	@Override
	public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		byte[] body = new byte[inputMessage.getBody().available()];
		inputMessage.getBody().read(body);
		try {
			byte[] decrypt = AESUtils.decrypt(body, encryptProperties.getKey().getBytes());
			final ByteArrayInputStream bais = new ByteArrayInputStream(decrypt);
			return new HttpInputMessage() {
				@Override
				public InputStream getBody() throws IOException {
					return bais;
				}

				@Override
				public HttpHeaders getHeaders() {
					return inputMessage.getHeaders();
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
	}
}