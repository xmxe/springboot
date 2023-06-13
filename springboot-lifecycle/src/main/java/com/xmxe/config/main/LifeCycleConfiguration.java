package com.xmxe.config.main;

import com.xmxe.load.SpringBeanLoadOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifeCycleConfiguration {

	@Bean(destroyMethod = "methodDestory", initMethod = "methodInit")//bean name默认方法名 不可重复，如果方法名是beanLoadOrder与类名重复会报错，"有相同的bean"。也可以通过修改bean name解决报错
	public SpringBeanLoadOrder springBeanLoadOrderName(){
		SpringBeanLoadOrder springBeanLoadOrder = new SpringBeanLoadOrder();
		return springBeanLoadOrder;
	}
}