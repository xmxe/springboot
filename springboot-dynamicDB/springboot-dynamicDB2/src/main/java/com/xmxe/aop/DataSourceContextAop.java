package com.xmxe.aop;

import com.xmxe.anno.DataSourceSwitcher;
import com.xmxe.util.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(value = 1)
@Component
public class DataSourceContextAop {
	Logger logger = LoggerFactory.getLogger(DataSourceSwitcher.class);

	@Around("@annotation(com.xmxe.anno.DataSourceSwitcher)")
	public Object setDynamicDataSource(ProceedingJoinPoint pjp) throws Throwable {
		boolean clear = false;
		try {
			Method method = this.getMethod(pjp);
			DataSourceSwitcher dataSourceSwitcher = method.getAnnotation(DataSourceSwitcher.class);
			clear = dataSourceSwitcher.clear();
			DataSourceContextHolder.set(dataSourceSwitcher.value().getDataSourceName());
			logger.info("数据源切换至：{}", dataSourceSwitcher.value().getDataSourceName());
			return pjp.proceed();
		} finally {
			if (clear) {
				DataSourceContextHolder.clear();
			}

		}
	}

	private Method getMethod(JoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		return signature.getMethod();
	}

}