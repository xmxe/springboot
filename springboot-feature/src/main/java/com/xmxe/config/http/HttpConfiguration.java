package com.xmxe.config.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConfiguration {

	/**
	 * 没有实例化RestTemplate时，初始化RestTemplate
	 */
	@ConditionalOnMissingBean(RestTemplate.class)
	@Bean
	public RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		return restTemplate;
	}

	/**
	 * 使用HttpClient作为底层客户端
	 */
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 5000;
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout)
				.setSocketTimeout(timeout)
				.build();
		CloseableHttpClient client = HttpClientBuilder
				.create()
				.setDefaultRequestConfig(config)
				.build();
		return new HttpComponentsClientHttpRequestFactory(client);
	}

	/**
	 * 使用OkHttpClient作为底层客户端
	 */
	// private ClientHttpRequestFactory getClientHttpRequestFactory() {
	// 	OkHttpClient okHttpClient = new OkHttpClient.Builder()
	// 			.connectTimeout(5, TimeUnit.SECONDS)
	// 			.writeTimeout(5, TimeUnit.SECONDS)
	// 			.readTimeout(5, TimeUnit.SECONDS)
	// 			.build();
	// 	return new OkHttp3ClientHttpRequestFactory(okHttpClient);
	// }
}