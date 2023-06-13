package com.xmxe.anno;

import com.xmxe.enums.DataSourceEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSourceSwitcher {
	/**
	 * 默认数据源
	 */
	DataSourceEnum value() default DataSourceEnum.MASTER;
	/**
	 * 清除
	 */
	boolean clear() default true;

}