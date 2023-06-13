package com.xmxe;

import com.xmxe.load.MyApplicationContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
// springboot启动类扫描servlet组件,⽤@ServletComponentScan注解后，Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解⾃动注册，⽆需其他代码。
@ServletComponentScan(basePackages = {"com.xmxe.config.filter"})
@EnableAsync
public class LifeCycleApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(LifeCycleApplication.class);
		springApplication.addInitializers(new MyApplicationContextInitializer());
		springApplication.run(args);
	}
}