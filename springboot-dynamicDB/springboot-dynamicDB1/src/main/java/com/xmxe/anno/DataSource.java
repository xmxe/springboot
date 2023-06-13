package com.xmxe.anno;

import com.xmxe.config.DynamicDataSourceProvider;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个注解将来加在Service层的方法上，使用该注解的时候，需要指定一个数据源名称，不指定的话，默认就使用master作为数据源。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DataSource {
	String dataSourceName() default DynamicDataSourceProvider.DEFAULT_DATASOURCE;

	@AliasFor("dataSourceName")
	String value() default DynamicDataSourceProvider.DEFAULT_DATASOURCE;
}