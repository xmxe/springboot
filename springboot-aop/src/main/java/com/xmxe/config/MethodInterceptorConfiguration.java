package com.xmxe.config;

import com.xmxe.anno.InterceptorAnnotation;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用@Configuration+@Bean代替切面
 */
@Configuration
public class MethodInterceptorConfiguration {
	// service..*.*(..)第一个..表示service包和子包下的路径
	public static final String traceExecution = "execution(* com.xmxe.service.Method*.*(..))";
	public static final String traceAnnotationExecution = "@annotation(com.xmxe.anno.InterceptorAnnotation)";

	@Bean
	public DefaultPointcutAdvisor defaultPointcutAdvisor() {
		CustomMethodInterceptor methodInterceptor = new CustomMethodInterceptor();
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();

		//case 1
		// AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		// pointcut.setExpression(traceExecution);
		// advisor.setPointcut(pointcut);

		//case 2
		// JdkRegexpMethodPointcut pointcut2 = new JdkRegexpMethodPointcut();
		// pointcut2.setPattern("com.xmxe.service.*");
		// advisor.setPointcut(pointcut2);

		//case 3
		// AspectJExpressionPointcut pointcut3 = new AspectJExpressionPointcut();
		// pointcut3.setExpression(traceAnnotationExecution);
		// advisor.setPointcut(pointcut3);

		//case 4
		AnnotationMatchingPointcut pointcut4 = new AnnotationMatchingPointcut(null, InterceptorAnnotation.class);
		advisor.setPointcut(pointcut4);

		advisor.setAdvice(methodInterceptor);
		return advisor;
	}

}