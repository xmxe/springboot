package com.xmxe.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ShiroController {

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	// @GetMapping("favicon.ico")
	// @ResponseBody
	// void returnNoFavicon() {}

    @RequestMapping("/loginPage")
	public String login() {
		return "login";
	}

	@RequestMapping("/shirospan")
	public String shiroSpan(){
		return "shirospan";
	}

	@RequestMapping("noauth")
	public String noauth(){
		return "noauth";
	}

	/**
	 *
	 * 登录校验
	 */
	@RequestMapping("/login")
	public String loginCheck(HttpServletRequest request, ModelMap mm){// Model是一个接口，其实现类继承了ModelMap类
		Subject subject = SecurityUtils.getSubject();
		// Object user = subject.getPrincipal();
		// 只有认证后才能访问，如果只是记住我则需要先登录
		// if(!subject.isAuthenticated()){
		// 	return "redirect:/toLogin";
		// }

		String name = request.getParameter("username");
		String password = request.getParameter("password");
		String rememberMe = request.getParameter("rememberMe");

		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name, password);
		// 这里的rememberme并不是记住用户名和密码。shiro的记住我是一种基于cookie实现的方式，特定的页面在关掉浏览器后（session消失）也可以进行访问的功能
		usernamePasswordToken.setRememberMe(rememberMe == null ? false : true);
		try {
			// 通过login方法进入MyRealm验证用户和权限
			subject.login(usernamePasswordToken);
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			String url = "index";
			if(savedRequest != null){
				// 认证跳转前的url
				url = WebUtils.getSavedRequest(request).getRequestUrl();
			}
			mm.addAttribute("msg","text");
			// 默认请求转发 浏览器url还是/login 使用重定向让浏览器显示真正的url
			return "redirect:" + url;
		}catch (Exception e) {
			e.printStackTrace();
			mm.addAttribute("msg",e.getMessage());
			return "login";
		}
	}

	/**
	 * 用户注销
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request,ModelMap mm) throws Exception{
		//shiro销毁登录
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "login";
	}

	/**
	 * @RequiresAuthentication:验证用户是否登录，等同于方法subject.isAuthenticated() 结果为true时。*
	 * 如果用户没登陆系统，系统给出的是异常 //UnauthenticatedException 没登陆
	 * 如果是用户已经登陆但是没某个操作的权限，我在方法@RequiresPermissions(value=“user:create”)让他具有权限，否则抛异常 //UnauthorizedException 没权限
	 *
	 * @RequiresUser
	 * 验证用户是否被记忆，user有两种含义：一种是成功登录的（subject.isAuthenticated() 结果为true）；另外一种是被记忆的（subject.isRemembered()结果为true）。
	 *
	 * @RequiresGuest
	 * 验证是否是一个guest的请求，与@RequiresUser完全相反。
	 * 换言之，RequiresUser == !RequiresGuest。此时subject.getPrincipal() 结果为null.
	 */
	/**
	 * 指定角色才可以执行的权限
	 * 使用注解 无权限时无法跳转到shiro配置的未认证页面 可以使用@ControllerAdvice进行统一异常处理
	 */
	@RequiresRoles("admin")
	@GetMapping("/requiresRoles")
	@ResponseBody
	public String requiresRoles() {
		return "hasRole";
	}

	/**
	 * 指定权限访问
	 */
	@RequiresPermissions("user:adds")//指定拥有此权限的才可以执行
	@RequestMapping(value="requiresPermissions")
	@ResponseBody
	public String requiresPermissions() {
		return "hasPermissions";
	}

	/**
	 * 选择rememberMe时可以不用登录访问
	 * 这里的rememberme并不是记住用户名和密码。shiro的记住我是一种基于cookie实现的方式，特定的页面在关掉浏览器后（session消失）也可以进行访问的功能
	 */
	@GetMapping("user")
	@ResponseBody
	public String user(){
		return "user";
	}

}