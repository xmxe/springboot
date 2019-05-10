package com.mySpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan//springboot启动类扫描servlet组件(过滤器filter)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
