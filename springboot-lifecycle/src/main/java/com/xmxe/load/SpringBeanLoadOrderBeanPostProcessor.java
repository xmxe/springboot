package com.xmxe.load;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component("springBeanLoadOrderNameBeanPostProcessor")
public class SpringBeanLoadOrderBeanPostProcessor implements BeanPostProcessor {

	private Logger logger = LoggerFactory.getLogger(SpringBeanLoadOrderBeanPostProcessor.class);

	/**
	 * implements BeanPostProcessor
	 * bean初始化的前置方法,ioc的每个bean都会走一遍
	 * order 4
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// 经测试一个类同时实现BeanPostProcessor，InitializingBean时同一个bean只会走afterPropertiesSet()即InitializingBean实现的方法
		// 不会走BeanPostProcessor的前置与后置方法，不单单是当前bean，BeanPostProcessor的前置后置方法每个bean都会执行一遍，而其他的实现的方法则只是当前bean执行，其他bean不执行
		logger.info("order 4 - beanName==[{}],BeanPostProcessor.postProcessBeforeInitialization()前置方法--bean==[{}]",beanName,bean);
		return bean;
	}

	/**
	 * implements BeanPostProcessor
	 * bean初始化后的后置方法,ioc的每个bean都会走一遍
	 * order 8
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// 经测试同时实现BeanPostProcessor，InitializingBean时同一个bean只会走afterPropertiesSet()即InitializingBean实现的方法
		// 不会走BeanPostProcessor的前置与后置方法，不单单是当前bean，BeanPostProcessor的前置后置方法每个bean都会执行一遍，而其他的实现的方法则只是当前bean执行,其他bean不执行
		logger.info("order 8 - beanName==[{}],BeanPostProcessor.postProcessAfterInitialization()后置方法--bean==[{}]",beanName,bean);
		return bean;
	}

}