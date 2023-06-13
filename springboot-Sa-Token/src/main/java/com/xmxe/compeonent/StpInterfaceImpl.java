package com.xmxe.compeonent;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

	// @Autowired
	// private UmsAdminService adminService;

	/**
	 * 返回一个账号所拥有的权限码集合
	 * @param loginId 账号id，即你在调用 StpUtil.login(id) 时写入的标识值。
	 * @param loginType 账号体系标识 多账户https://sa-token.dev33.cn/doc/index.html#/up/many-account
	 */
	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		System.out.printf("loginId=%s,loginType=%s",loginId,loginType);
//		AdminUser adminUser = adminService.getAdminById(Convert.toLong(loginId));
//		return adminUser.getRole().getPermissionList();

		// Sa-Token允许你根据通配符指定泛权限，例如当一个账号拥有user*的权限时，user-add、user-delete、user-update都将匹配通过
		return List.of("user:delete","user:add");
	}

	/**
	 * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
	 * @param loginId 账号id，即你在调用 StpUtil.login(id) 时写入的标识值。
	 * @param loginType 账号体系标识 多账户https://sa-token.dev33.cn/doc/index.html#/up/many-account
	 */
	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		System.out.printf("loginId=%s,loginType=%s",loginId,loginType);
//		AdminUser adminUser = adminService.getAdminById(Convert.toLong(loginId));
//		return Collections.singletonList(adminUser.getRole().getName());
		return List.of("ROLE_ADMIN");
	}

}