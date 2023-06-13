package com.xmxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry// 启用重试机制
@EnableCaching // 启动缓存
//@MapperScan("com.xmxe.dao")
//@ImportResource(value = {"classpath:applicationContext.xml"})如果使用xml配置spring 使用@ImportResource导入配置文件
public class FeatureApplication {
	public static void main(String[] args) {
		SpringApplication.run(FeatureApplication.class, args);
	}
}