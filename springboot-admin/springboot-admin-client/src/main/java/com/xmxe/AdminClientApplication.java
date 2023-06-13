package com.xmxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class AdminClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdminClientApplication.class,args);
	}

	@Scheduled(cron = "0/10 * * * * ?")
	public void task() {
		System.out.println("springboot admin计划任务");
	}
}