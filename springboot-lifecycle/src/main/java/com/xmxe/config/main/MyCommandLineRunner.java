package com.xmxe.config.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动时加载
 */
@Component
@Order(3)//@Order注解中，数字越小，优先级越大，默认情况下，优先级的值为Integer.MAX_VALUE，表示优先级最低。
public class MyCommandLineRunner implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(MyCommandLineRunner.class);
	@Override
	public void run(String... args) throws Exception {
		// 可以根据项目启动时: java -jar demo.jar arg1 arg2 arg3传入的参数进行一些处理
		logger.info("this is CommandLineRunner");
	}

}