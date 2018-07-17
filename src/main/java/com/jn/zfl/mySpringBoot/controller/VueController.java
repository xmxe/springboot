package com.jn.zfl.mySpringBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VueController {

	@RequestMapping("vue")
	public String goVuePage() {
		return "vue/vue";
	}
}
