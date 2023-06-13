package com.xmxe.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.xmxe.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UmsAdminService{
	public SaTokenInfo login(String username, String password) {

		// 数据库查询用户信息
		// AdminUser adminUser = getAdminByUsername(username);

		// if (adminUser == null) {
		// 	return null;
		// }
		// if (!SaSecureUtil.md5(password).equals(adminUser.getPassword())) {
		// 	return null;
		// }
		// 模拟测试
		User adminUser = new User();
		adminUser.setId(1);
		adminUser.setUsername(username);
		adminUser.setPassword(password);

		// 密码校验成功后登录，一行代码实现登录
		StpUtil.login(adminUser.getId());
		// 然后在任意需要校验登录处调用以下API 如果当前会话未登录，这句代码会抛出 `NotLoginException`异常
		StpUtil.checkLogin();

		// 获取当前登录用户Token信息
		SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
		return saTokenInfo;
	}


}