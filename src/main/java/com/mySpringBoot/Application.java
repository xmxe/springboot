package com.mySpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@MapperScan("com.mySpringBoot.dao")//配置扫描器，将mybatis接口的实现加入到ioc容器中 
@ComponentScan("com.mySpringBoot")//自动扫描
@ServletComponentScan//springboot启动类扫描servlet组件(过滤器filter)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
