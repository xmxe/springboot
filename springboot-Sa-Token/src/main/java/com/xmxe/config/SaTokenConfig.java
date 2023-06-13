package com.xmxe.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaStrategy;
import com.xmxe.entity.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

/**
 * Sa-Token 使用全局拦截器完成注解鉴权功能，为了不为项目带来不必要的性能负担，拦截器默认处于关闭状态
 * 因此，为了使用注解鉴权，你必须手动将 Sa-Token 的全局拦截器注册到你项目中
 *
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {


	/**
	 * 注册sa-token拦截器
	 * 路由拦截鉴权 https://sa-token.dev33.cn/doc/index.html#/use/route-check
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SaRouteInterceptor((req, resp, handler) -> {
			// 获取配置文件中的白名单路径
//			List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
			List<String> ignoreUrls = List.of("/",
					"/swagger-ui/**",
					"/v2/api-docs/**",
					"/admin/login",
					"/admin/isLogin",
					"/swagger-resources/**",
					"/*.html",
					"/*.css",
					"/*.js");
			// 登录认证：除白名单路径外均需要登录认证
			SaRouter.match(Collections.singletonList("/**"), ignoreUrls, StpUtil::checkLogin);
			// 角色认证：ROLE_ADMIN可以访问/brand下所有接口，ROLE_USER只能访问/brand/listAll
			SaRouter.match("/brand/listAll", () -> {
				StpUtil.checkRoleOr("ROLE_ADMIN","ROLE_USER");
				//强制退出匹配链
				SaRouter.stop();
			});
			SaRouter.match("/brand/**", () -> StpUtil.checkRole("ROLE_ADMIN"));
			// StpUtil.checkRoleOr("super-admin", "shop-admin"); 当前账号是否含有指定角色标识 [指定多个，只要其一验证通过即可]

			// 权限认证
			SaRouter.match("/brand/list", () -> StpUtil.checkPermission("brand:read"));
			SaRouter.match("/brand/{id}", () -> StpUtil.checkPermission("brand:read"));
		})).addPathPatterns("/**");
	}


	/**
	 * 注册 [Sa-Token 全局过滤器]
	 */
	@Bean
	public SaServletFilter getSaServletFilter() {
		return new SaServletFilter()
				// 指定 [拦截路由] 与 [放行路由]
				.addInclude("/**")// .addExclude("/favicon.ico")
				// 认证函数: 每次请求执行
				.setAuth(obj -> {
					// System.out.println("---------- sa全局认证 " + SaHolder.getRequest().getRequestPath());
				})
				// 异常处理函数：每次认证函数发生异常时执行此函数
				.setError(e -> {
					System.out.println("---------- sa全局异常 ");
					return AjaxJson.getError(e.getMessage());
				})
				// 前置函数：在每次认证函数之前执行
				.setBeforeAuth(r -> {
					// ---------- 设置一些安全响应头 ----------
					SaHolder.getResponse()
							// 服务器名称
							.setServer("sa-server")
							// 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
							.setHeader("X-Frame-Options", "SAMEORIGIN")
							// 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
							.setHeader("X-XSS-Protection", "1; mode=block")
							// 禁用浏览器内容嗅探
							.setHeader("X-Content-Type-Options", "nosniff")
					;
				})
				;
	}

	/**
	 * 重写 Sa-Token 框架内部算法策略
	 * 可以使注解继承
	 */
	@Autowired
	public void rewriteSaStrategy() {
		// 重写Sa-Token的注解处理器，增加注解合并功能
		SaStrategy.me.getAnnotation = (element, annotationClass) -> {
			return AnnotatedElementUtils.getMergedAnnotation(element, annotationClass);
		};
	}

}