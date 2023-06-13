package com.xmxe.controller;

import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理 404
 */
@RestController
public class NotFoundHandle {

	@RequestMapping("/error")
	public Object error(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(200);
		return SaResult.get(404, "not found", null);
	}

}