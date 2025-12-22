package com.xmxe.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Spring上下文工具类
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	private static volatile boolean initialized = false;

	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}

	@PostConstruct
	public void init() {
		initialized = true;
	}

	/**
	 * 获取ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 按类型获取Bean
	 */
	public static <T> T getBean(Class<T> requiredType) {
		checkApplicationContext();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 按名称获取Bean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		checkApplicationContext();
		return (T) applicationContext.getBean(beanName);
	}

	/**
	 * 按名称和类型获取Bean
	 */
	public static <T> T getBean(String beanName, Class<T> requiredType) {
		checkApplicationContext();
		return applicationContext.getBean(beanName, requiredType);
	}

	/**
	 * 获取指定类型的所有Bean
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
		checkApplicationContext();
		return applicationContext.getBeansOfType(requiredType);
	}

	/**
	 * 获取当前环境
	 */
	public static Environment getEnvironment() {
		checkApplicationContext();
		return applicationContext.getEnvironment();
	}

	/**
	 * 获取配置属性
	 */
	public static String getProperty(String key) {
		checkApplicationContext();
		return applicationContext.getEnvironment().getProperty(key);
	}

	/**
	 * 获取配置属性，带默认值
	 */
	public static String getProperty(String key, String defaultValue) {
		checkApplicationContext();
		return applicationContext.getEnvironment().getProperty(key, defaultValue);
	}

	/**
	 * 获取配置属性（指定类型）
	 */
	public static <T> T getProperty(String key, Class<T> targetType) {
		checkApplicationContext();
		return applicationContext.getEnvironment().getProperty(key, targetType);
	}

	/**
	 * 发布事件
	 */
	public static void publishEvent(Object event) {
		checkApplicationContext();
		applicationContext.publishEvent(event);
	}

	/**
	 * 检查是否存在指定名称的Bean
	 */
	public static boolean containsBean(String beanName) {
		if (applicationContext == null || !initialized) {
			return false;
		}
		return applicationContext.containsBean(beanName);
	}

	/**
	 * 检查应用上下文是否已初始化
	 */
	private static void checkApplicationContext() {
		Assert.state(applicationContext != null && initialized,
				"ApplicationContext未初始化，请确保：\n" +
						"1. 此类已被Spring管理（添加了@Component等注解）\n" +
						"2. Spring容器已启动完成\n" +
						"3. 不是在静态初始化块或@PostConstruct方法中过早调用此工具类");
	}

	/**
	 * 安全的获取Bean，如果不存在则返回null
	 */
	public static <T> T getBeanSafely(Class<T> requiredType) {
		if (applicationContext == null || !initialized) {
			return null;
		}
		try {
			return applicationContext.getBean(requiredType);
		} catch (BeansException e) {
			return null;
		}
	}
}