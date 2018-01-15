package com.jn.zfl.mySpringBoot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.jn.zfl.mySpringBoot.dao")//配置扫描器，将mybatis接口的实现加入到ioc容器中 
@ComponentScan("com.jn.zfl.mySpringBoot")//自动扫描
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
