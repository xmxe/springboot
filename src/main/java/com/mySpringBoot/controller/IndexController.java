package com.mySpringBoot.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mySpringBoot.config.quartz.QuartzManager;

@Controller
public class IndexController {
    @Autowired
    QuartzManager quartManager;
    
	@RequestMapping("/")
	public String login() {
		return "login";
	} 
	/**
	 * 用户注销
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request,ModelMap mm) throws Exception{
		//shiro销毁登录
		String msg = request.getParameter("msg");
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		mm.addAttribute("msg", msg);
		return "login";
	}
	
	@RequestMapping("/index")
	public String index() {
		quartManager.shutdownJobs();
		return "index";
	} 
}
