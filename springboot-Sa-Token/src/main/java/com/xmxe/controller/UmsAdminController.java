package com.xmxe.controller;

import cn.dev33.satoken.annotation.*;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.xmxe.entity.HttpResult;
import com.xmxe.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台用户管理
 */
@Controller
@RequestMapping("/admin")
public class UmsAdminController {
	@Autowired
	private UmsAdminService adminService;

	/**
	 * 登录以后返回token
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public HttpResult login(@RequestParam String username, @RequestParam String password) {
		SaTokenInfo saTokenInfo = adminService.login(username, password);
		if (saTokenInfo == null) {
			return new HttpResult(500,"用户名或密码错误",null);
		}
		Map<String, String> tokenMap = new HashMap<>();
		tokenMap.put("token", saTokenInfo.getTokenValue());
		tokenMap.put("tokenHead", saTokenInfo.getTokenName());
		return new HttpResult(200,"success",tokenMap);
	}

	/**
	 * 查询当前登录状态
	 */
	@RequestMapping(value = "/isLogin", method = RequestMethod.GET)
	@ResponseBody
	public HttpResult isLogin() {
		return new HttpResult(200,"success", StpUtil.isLogin());
	}

	/**
	 * 测试未设置白名单的url,未登陆的话会抛出NotLoginException异常
	 */
	@RequestMapping(value = "/whiteList", method = RequestMethod.GET)
	@ResponseBody
	public HttpResult whiteList() {
		return new HttpResult(200,"whiteList", "whiteList");
	}

	/**
	 * 拥有user:add权限才可以访问的url"
	 */
	@SaCheckPermission("user:add")
	@RequestMapping(value="/user/insert",method = RequestMethod.POST)
	public String insertUser() {
		// ...
		return "用户增加";
	}


	// 登录认证：只有登录之后才能进入该方法
	@SaCheckLogin
	// 角色认证：必须具有指定角色才能进入该方法
	@SaCheckRole("super-admin")
	// 权限认证：必须具有指定权限才能进入该方法
	@SaCheckPermission("user-add")
	// 二级认证：必须二级认证之后才能进入该方法
	@SaCheckSafe()
	// Http Basic 认证：只有通过 Basic 认证后才能进入该方法
	// @SaCheckBasic(account = "sa:123456")
	// @SaCheckRole与@SaCheckPermission注解可设置校验模式
	// @SaCheckPermission(value = {"user-add", "user-all", "user-delete"}, mode = SaMode.OR)
	// 写法一：orRole = "admin"，代表需要拥有角色 admin 。
	// 写法二：orRole = {"admin", "manager", "staff"}，代表具有三个角色其一即可。
	// 写法三：orRole = {"admin, manager, staff"}，代表必须同时具有三个角色。
	// @SaCheckPermission(value = "user-add", orRole = "admin")
	@RequestMapping("info")
	public String info() {
		return "查询用户信息";
	}

}