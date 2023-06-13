package com.xmxe.config;

import com.xmxe.service.XmxeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * 如果⼀个配置类只配置@ConfigurationProperties注解，⽽没有使⽤@Component，那么在IOC容器中是获取不到properties配置⽂件转化的bean。
 * 说⽩了@EnableConfigurationProperties相当于把使⽤@ConfigurationProperties的类进⾏了⼀次注⼊。
 * 测试发现@ConfigurationProperties与@EnableConfigurationProperties关系特别
 */
@EnableConfigurationProperties({XmxeProperties.class})
/**
 * 在Spring中有时需要根据配置项来控制某个类或者某个bean是否需要加载.这个时候就可以通过@ConditionnalOnProperty来实现
 */
@ConditionalOnProperty(
		value = {"xmxe.enabled"},
		havingValue = "true", // 与name或value配合 配置项值是true的时候加载bean 否则不加载bean
		matchIfMissing = true // true:xmxe.enabled配置项不在的时候也加载bean false:xmxe.enabled配置项不存在的时候不加载bean
)
@ComponentScan("com.xmxe")// 扫描@ControllerAdvice
public class XmxeConfiguration {

	@Autowired
	XmxeProperties xmxeProperties;

	@Bean
	public XmxeService xmxeService(){
		return new XmxeService();
	}
}

/**
 * String[] value() default {}; //数组，获取对应property名称的值，与name不可同时使用
 * String prefix() default "";//property名称的前缀，可有可无
 * String[] name() default {};//数组，property完整名称或部分名称（可与prefix组合使用，组成完整的property名称），与value不可同时使用
 * String havingValue() default "";//可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
 * boolean matchIfMissing() default false;//缺少该property时是否可以加载。如果为true，没有该property也会正常加载；反之报错
 */