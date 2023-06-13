package com.xmxe;

import com.xmxe.config.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebSocketNettyApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebSocketNettyApplication.class, args);
		try {
			new NettyServer(12345).start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}