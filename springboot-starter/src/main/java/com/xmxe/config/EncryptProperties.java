package com.xmxe.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptProperties {

	// AES密钥需要16位
	private final static String DEFAULT_KEY = "xxxxxxxxxxxxxxxx";
	private String key = DEFAULT_KEY;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
