package com.xmxe.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过自定义filter通过过滤链进入HttpServletRequestWrapper 实现在Controller层里面通过request.getParameter()时完成某些功能
 */
@Order(1)//定义优先级
@WebFilter(filterName="customFilter",urlPatterns="/*")
public class CustomFilterToRequestWrapper implements Filter{

	Logger logger = LoggerFactory.getLogger(CustomFilterToRequestWrapper.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("CustomFilterToRequestWrapper开始启动并完成初始化,可以在init方法中初始化某一段代码");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 根据过滤器优先级处理request response
		// do something...
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		//该字段必填。它的值要么是请求时Origin字段的具体值，要么是一个*，表示接受任意域名的请求
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		//该字段必填。它的值是逗号分隔的一个具体的字符串(GET,POST...)或者*，表明服务器支持的所有跨域请求的方法。注意，返回的是所有支持的方法，而不单是浏览器请求的那个方法。这是为了避免多次"预检"请求
		httpResponse.setHeader("Access-Control-Allow-Methods", "*");
		//该字段可选，用来指定本次预检请求的有效期，单位为秒。在有效期间，不用发出另一条预检请求。
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, Connection, User-Agent, Cookie");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.setHeader("Content-type", "application/json");
		httpResponse.setHeader("Cache-Control", "no-cache, must-revalidate");
		// 进入下一个过滤链
		// chain.doFilter(request, httpResponse);

		// HttpServletRequestWrapper使用了装饰模式可以增强request,HttpServletResponseWrapper同理，可以重写response方法
		chain.doFilter(new CustomHttpServletRequestWrapper((HttpServletRequest) request),httpResponse);
	}

	@Override
	public void destroy() {}


	/**
	 * 通过request.getParameter()时完成某些功能
	 */
	static class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper{

		public CustomHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		/**
		 * 重写需要修改的方法,这样使用request.getParameter()时就会先校验下参数，可根据自己的业务需求进行定制
		 */
		@Override
		public String getParameter(String name) {
			return filter(super.getRequest().getParameter(name));
		}

		//过滤...
		private String filter(String value){
			//...
			return value;
		}

	}
}