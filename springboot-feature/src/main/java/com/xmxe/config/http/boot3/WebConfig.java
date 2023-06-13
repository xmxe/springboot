package com.xmxe.config.http.boot3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebConfig {
	@Bean
	WebClient webClient(ObjectMapper objectMapper) {
		return WebClient.builder()
				.baseUrl("http://localhost:8080")
				.build();
	}

	@Bean
	UserClient postClient(WebClient webClient) {
		HttpServiceProxyFactory httpServiceProxyFactory =
				HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
						.build();
		return httpServiceProxyFactory.createClient(UserClient.class);
	}
}