package com.xmxe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xmxe.mapper")
public class StateMachineApplication {
	public static void main(String[] args) {
		SpringApplication.run(StateMachineApplication.class, args);
	}
}