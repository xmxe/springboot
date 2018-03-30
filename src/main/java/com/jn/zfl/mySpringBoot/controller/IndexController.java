package com.jn.zfl.mySpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jn.zfl.mySpringBoot.config.quarts.QuartzManager;

@Controller
public class IndexController {
    @Autowired
    QuartzManager quartManager;
    
	@RequestMapping("/")
	public String login() {
		return "login";
	} 
	@RequestMapping("index")
	public String index() {
		quartManager.shutdownJobs();
		return "index";
	} 
}
