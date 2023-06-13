package com.xmxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan//springboot启动类扫描servlet组件(过滤器filter)⽤@ServletComponentScan注解后，Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解⾃动注册，⽆需其他代码。
public class WebSocketFilterApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebSocketFilterApplication.class, args);
	}
}