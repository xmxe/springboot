package com.xmxe.load;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.beans.PropertyDescriptor;

/**
 * 该接口继承了BeanPostProcess接口，区别如下：
 * BeanPostProcess接口只在bean的初始化阶段进行扩展（注入spring上下文前后），而InstantiationAwareBeanPostProcessor接口
 * 在此基础上增加了3个方法，把可扩展的范围增加了实例化阶段和属性注入阶段。
 */
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

	/**
	 * 初始化bean之前，相当于把bean注入spring上下文之前
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("[TestInstantiationAwareBeanPostProcessor] before initialization " + beanName);
		return bean;
	}

	/**
	 * 初始化bean之后，相当于把bean注入spring上下文之后
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("[TestInstantiationAwareBeanPostProcessor] after initialization " + beanName);
		return bean;
	}

	/**
	 * 实例化bean之前，相当于new这个bean之前
	 */
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		System.out.println("[TestInstantiationAwareBeanPostProcessor] before instantiation " + beanName);
		return null;
	}

	/**
	 * 实例化bean之后，相当于new这个bean之后
	 */
	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		System.out.println("[TestInstantiationAwareBeanPostProcessor] after instantiation " + beanName);
		return true;
	}

	/**
	 * bean已经实例化完成，在属性注入时阶段触发，@Autowired,@Resource等注解原理基于此方法实现
	 */
	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
		System.out.println("[TestInstantiationAwareBeanPostProcessor] postProcessPropertyValues " + beanName);
		return pvs;
	}

}