package com.xmxe.anno;

import java.lang.annotation.*;

/**
 * HandlerMethodArgumentResolver参数解析器
 * 通过这个注解来标识是否需要用到自定义解析器解析参数
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserId {
	boolean required() default true;
}