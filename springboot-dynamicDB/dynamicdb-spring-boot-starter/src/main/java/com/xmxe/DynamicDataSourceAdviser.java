package com.xmxe;

import com.xmxe.common.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Order(100)
public class DynamicDataSourceAdviser {

    @Pointcut("@annotation(com.xmxe.common.DataSource)")
    public void pointcut(){};

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        try {
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            // 获取被代理的方法对象
            Method targetMethod = methodSignature.getMethod();
            // 获取数据源注解
            DataSource ds = targetMethod.getAnnotation(DataSource.class);
            if (Objects.nonNull(ds)) {
                DynamicDataSourceHolder.setDataSource(ds.value());
            }
            return point.proceed();
        } finally {
            DynamicDataSourceHolder.remove();
        }
    }

}