package com.mySpringBoot.config.interceptor;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class InterceptorConfiguration extends WebMvcConfigurationSupport {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册拦截器
		InterceptorRegistration ir = registry.addInterceptor(new MyInterceptor());
		// 配置拦截的路径
		ir.addPathPatterns("/**");
		// 配置不拦截的路径
		ir.excludePathPatterns("/code.do");
		ir.excludePathPatterns("/loginCheck.do");
		ir.excludePathPatterns("/resources/**");
		ir.excludePathPatterns("/static/**");
		// 还可以在这里注册其它的拦截器
		// registry.addInterceptor(new OtherInterceptor()).addPathPatterns("/**");

	}
	//自定义静态资源路径
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
	}
	//首页
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		registry.addViewController("/").setViewName("index");
	    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	    //实现一个请求到视图的映射，而无需书写controller
	    //registry.addViewController("/index").setViewName("forward:/index.jsp");  
	    
	}
}
/**在SpringBoot2.0及Spring 5.0 WebMvcConfigurerAdapter已被废弃, 1.直接实现WebMvcConfigurer （官方推荐）2.直接继承WebMvcConfigurationSupport
 * WebMvcConfigurerAdapter常用的方法
// 解决跨域问题 
public void addCorsMappings(CorsRegistry registry) ;

// 添加拦截器
void addInterceptors(InterceptorRegistry registry);

// 这里配置视图解析器 
void configureViewResolvers(ViewResolverRegistry registry);

// 配置内容裁决的一些选项
void configureContentNegotiation(ContentNegotiationConfigurer configurer);

// 视图跳转控制器 
void addViewControllers(ViewControllerRegistry registry);

// 静态资源处理 
void addResourceHandlers(ResourceHandlerRegistry registry);

// 默认静态资源处理器
void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);
 * 
 * */
