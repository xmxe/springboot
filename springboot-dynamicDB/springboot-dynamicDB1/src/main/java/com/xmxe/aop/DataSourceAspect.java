package com.xmxe.aop;

import com.xmxe.anno.DataSource;
import com.xmxe.util.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Order(1)
@Component
public class DataSourceAspect {
	@Pointcut("@annotation(com.xmxe.anno.DataSource)" + "|| @within(com.xmxe.anno.DataSource)")
	public void dsPc() {}

	/**
	 * 扫描注解，如果有注解的话设置数据源
	 */
	@Around("dsPc()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		// 根据当前的切点，调用getDataSource()获取到@DataSource注解，这个注解可能来自方法上也可能来自类上，方法上的优先级高于类上的优先级。
		DataSource dataSource = getDataSource(point);
		// 如果拿到的注解不为空，则我们在DynamicDataSourceContextHolder中设置当前的数据源名称，设置完成后进行方法的调用
		// 如果拿到的注解为空，那么就直接进行方法的调用，不再设置数据源了（将来会自动使用默认的数据源）。
		if (Objects.nonNull(dataSource)) {
			// 设置数据源为注解上的value
			DynamicDataSourceContextHolder.setDataSourceType(dataSource.dataSourceName());
		}
		try {
			return point.proceed();
		} finally {
			// 最后记得方法调用完成后，从ThreadLocal中移除数据源。
			// 销毁数据源 在执行方法之后
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
	}

	/**
	 * 获取需要切换的数据源
	 */
	public DataSource getDataSource(ProceedingJoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		// 扫描方法上的注解
		DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
		if (Objects.nonNull(dataSource)) {
			return dataSource;
		}
		// 扫描类上的注解
		return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
	}
}