package com.xmxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 排除druid快速配置类（jdbc的自动装配机制）,否则报Failed to configure a DataSource: 'url' attribute is not specified and no embedded
 * 即配置文件里没有写spring.datasource.driver-class-name，url之类的配置便会报错
 */
@SpringBootApplication //(exclude = DruidDataSourceAutoConfigure.class)
public class MybatisApplication {
	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}
}