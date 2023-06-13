package com.xmxe.config;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import org.springframework.stereotype.Component;

/**
 * 自定义侦听器的实现
 */
@Component
public class MySaTokenListener implements SaTokenListener {

	/** 每次登录时触发 */
	@Override
	public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
		// ...
	}

	/** 每次注销时触发 */
	@Override
	public void doLogout(String loginType, Object loginId, String tokenValue) {
		// ...
	}

	/** 每次被踢下线时触发 */
	@Override
	public void doKickout(String loginType, Object loginId, String tokenValue) {
		// ...
	}

	/** 每次被顶下线时触发 */
	@Override
	public void doReplaced(String loginType, Object loginId, String tokenValue) {
		// ...
	}

	@Override
	public void doDisable(String s, Object o, String s1, int i, long l) {

	}

	@Override
	public void doUntieDisable(String s, Object o, String s1) {

	}

	@Override
	public void doOpenSafe(String s, String s1, String s2, long l) {

	}

	@Override
	public void doCloseSafe(String s, String s1, String s2) {

	}

	/** 每次创建Session时触发 */
	@Override
	public void doCreateSession(String id) {
		// ...
	}

	/** 每次注销Session时触发 */
	@Override
	public void doLogoutSession(String id) {
		// ...
	}

	@Override
	public void doRenewTimeout(String s, Object o, long l) {

	}

}