package com.xmxe.config.main;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
//@PropertySource("classpath:application.properties") // 这个注解导入刚才增加的jdbc配置文件 只支持properties
//@PropertySource(value = {"classpath:user.yml"}, factory = PropertySourceFactory.class)
//@EnableAspectJAutoProxy//开启自动代理 测试AOP时打开
public class MainConfiguration {

	/**
	 * 统一页码处理配置
	 */
	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
		/* return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
		    @Override
		    public void customize(ConfigurableWebServerFactory factory) {
		        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
		        factory.addErrorPages(errorPage404);
		    }
		};*/

		return (factory -> {
			ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/static/404.html");
			ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/static/500.html");
			factory.addErrorPages(errorPage404, errorPage500);
		});
	}

	//springboot 1.5.x写法
	// @Bean
	// public EmbeddedServletContainerCustomizer containerCustomizer() {
	// 	return new EmbeddedServletContainerCustomizer() {
	// 		@Override
	// 		public void customize(ConfigurableEmbeddedServletContainer container) {
	// 			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
	// 			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
	// 			container.addErrorPages(error404Page, error500Page);
	// 		}
	// 	};
	// }


	// @Bean
	// public CorsFilter corsFilter() {
	// 	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	// 	source.registerCorsConfiguration("/**", addCorsConfig());
	// 	return new CorsFilter(source);
	// }



	/*
	 * 在V3上使用ResponseEntity<byte[]>完成浏览器下载功能 但是下载的文件损坏或者乱码，在V3的spring.xml方式配置了下面的消息转换器完成controller中的download接口
	 * 因此想在springboot中测试下配置了下面这个bean,但是不配置这个bean在项目中下载文件并不会出现文件内容乱码或者文件损坏所以注释掉，也没有测试这个bean
	 * 而且这个bean应该可以在com.xmxe.config.interceptor.InterceptorConfiguration中的configureMessageConverters()方法中配置，因为下载文件没有出现乱码所以都没有进行测试
	 */
	// @Bean
	// public RequestMappingHandlerAdapter requestMappingHandlerAdapter(){
	// 	RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
	// 	List<HttpMessageConverter<?>> converters = new ArrayList<>();
	// 	converters.add(new ByteArrayHttpMessageConverter());
	// 	MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
	// 	List<MediaType> mediaTypes = MediaType.parseMediaTypes("application/json;charset=UTF-8,text/json;charset=UTF-8,text/html;charset=UTF-8");
	// 	mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);
	// 	converters.add(mappingJackson2HttpMessageConverter);
	// 	adapter.setMessageConverters(converters);
	// 	return adapter;
	// }


}