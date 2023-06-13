package com.xmxe.config.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 记录SpringApplication.run()里面方法的执行时间
 */
public class MySpringApplicationRunListener implements SpringApplicationRunListener {

	Logger log = LoggerFactory.getLogger(MySpringApplicationRunListener.class);
	// 这个构造函数不能少，因为源码中限定了使用该参数类型的构造函数反射生成实例。否则反射生成实例会报错
	public MySpringApplicationRunListener(SpringApplication sa, String[] args) {}

	/**
	 * run方法第一次被执行时调用，早期初始化工作
	 */
	@Override
	public void starting(ConfigurableBootstrapContext bootstrapContext) {
		log.info("starting {}", LocalDateTime.now());
	}

	/**
	 * environment创建后，ApplicationContext创建前
	 */
	@Override
	public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
		log.info("environmentPrepared {}", LocalDateTime.now());
	}

	/**
	 * ApplicationContext实例创建，部分属性设置了
	 */
	@Override
	public void contextPrepared(ConfigurableApplicationContext context)  {
		log.info("contextPrepared {}", LocalDateTime.now());
	}

	/**
	 * ApplicationContext加载后，refresh前
	 */
	@Override
	public void contextLoaded(ConfigurableApplicationContext context) {
		log.info("contextLoaded {}", LocalDateTime.now());
	}

	/**
	 * refresh后,org.springframework.boot.CommandLineRunner和org.springframework.boot.ApplicationRunner还未调用
	 */
	@Override
	public void started(ConfigurableApplicationContext context, Duration timeTaken) {
		log.info("started {}", LocalDateTime.now());
	}

	/**
	 * 所有初始化完成后，run结束前
	 */
	@Override
	public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
		log.info("ready {}", LocalDateTime.now());
	}

	/**
	 * 初始化失败后
	 */
	@Override
	public void failed(ConfigurableApplicationContext context, Throwable exception) {
		log.info("failed {}", LocalDateTime.now());
	}

	/*
	 * run方法中是通过getSpringFactoriesInstances方法来获取META-INF/spring.factories下配置的SpringApplicationRunListener的实现类，
	 * 其底层是依赖SpringFactoriesLoader来获取配置的类的全限定类名，然后反射生成实例;这种方式在SpringBoot用的非常多，如EnableAutoConfiguration、ApplicationListener、ApplicationContextInitializer等。
	 */

}