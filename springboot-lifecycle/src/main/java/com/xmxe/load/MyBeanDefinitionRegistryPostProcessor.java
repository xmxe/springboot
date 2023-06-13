package com.xmxe.load;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * 这个接口在读取项目中的beanDefinition之后执行，提供一个补充的扩展点
 * 使用场景：你可以在这里动态注册自己的beanDefinition，可以加载classpath之外的bean
 */
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
		System.out.println("BeanDefinitionRegistryPostProcessor->postProcessBeanDefinitionRegistry");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
		System.out.println("BeanDefinitionRegistryPostProcessor->postProcessBeanFactory");
	}
}