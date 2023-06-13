package com.xmxe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class I18nController {

	@Autowired
	MessageSource messageSource;

	@GetMapping("/hello")
	public String hello() {
		return messageSource.getMessage("user.name", null, LocaleContextHolder.getLocale());
	}
}