package com.xmxe.config.interceptor;

import com.xmxe.config.main.AuthParamResolver;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class InterceptorConfiguration implements WebMvcConfigurer {

	/**
	 * 自定义拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册拦截器
		InterceptorRegistration ir = registry.addInterceptor(new MyInterceptor());
		// 配置不拦截的路径
		ir.excludePathPatterns("/");
		ir.excludePathPatterns("/code");
		ir.excludePathPatterns("/loginCheck");
		ir.excludePathPatterns("/static/**");
		// 配置拦截的路径
		ir.addPathPatterns("/**");
		// 还可以在这里注册其它的拦截器
		// registry.addInterceptor(new OtherInterceptor()).addPathPatterns("/**");

	}

	/**
	 * 自定义静态资源路径
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")//指的是对外暴露的访问路径
				.addResourceLocations("classpath:/static/");//指的是内部文件放置的目录
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
	}

	/**
	 * 指定首页
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/").setViewName("login");
		//registry.setOrder(Ordered.HIGHEST_PRECEDENCE);//springmvc的拦截器的优先级默认是高于shiro的，通过此方法可配置优先级的顺序
		//实现一个请求到视图的映射，而无需书写controller
		//registry.addViewController("/").setViewName("redirect:/index");

	}

	/**
	 * configureMessageConverters配置消息转换器
	 * 在com.xmxe.config.main.MainConfiguration注册bean应该也可以实现功能配置
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// StringHttpMessageConverter converter  = new StringHttpMessageConverter(StandardCharsets.UTF_8);
		// converters.add(converter);
	}

	/**
	 * cors跨域请求 使用此方法配置之后在使用拦截器会失效
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//设置允许跨域的路径
		registry.addMapping("/**")
				//设置允许跨域请求的域名
				// .allowedOrigins("*")
				.allowedOriginPatterns("*")
				//是否允许证书 不再默认开启
				.allowCredentials(true)
				//设置允许的方法
				.allowedMethods("GET", "POST", "DELETE", "PUT")
				//跨域允许时间
				.maxAge(3600);

	}

	/**
	 * 注册自定义参数解析器
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new AuthParamResolver());
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		// Converter接口定义了两个泛型类型参数，分别代表源类型(S)，目标类型(T)
		// T convert(S source);
		registry.addConverter(new Converter<String, Date> () {
			@Override
			public Date convert(String source) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					return dateFormat.parse(source);
				} catch (ParseException e) {
					throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
				}
			}
		});
		/*
		 * 需要注意的是当Converter与PropertyEditor同时存在时,框架往往优先使用Converter完成数据类型转换工作。
		 * 但是为了避免出现潜在的冲突或错误，建议避免同时将相同的类型(或超集)同时注册为Converter和PropertyEditor
		 */
	}

}