package com.xmxe.config;

import com.xmxe.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.*;

/**
 * 自定义Realm
 */
public class MyRealm extends AuthorizingRealm{

    /**
     * 认证:验证用户 加入数据库有用户的话将真正的、正确的用户数据封装成AuthenticationInfo返回比对
     * 调用subject.login(usernamePasswordToken)就会进入此方法进行验证
     */
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户输入的token （用户名密码等信息）
	    UsernamePasswordToken utoken = (UsernamePasswordToken) token;
	    // 输入的密码
        // String inPassword = new String(utoken.getPassword());
        // 输入的用户名
        // String inUsername = utoken.getUsername();
        // Object username2 = token.getPrincipal();

        // 正常情况下根据用户名从数据库查询是否有此用户及获取此用户的密码角色权限等相关信息
        User user = new User();
        user.setUsername("test");
        user.setPassword("5dec08bfc8b7661d5d0b5713d4aa0d033a87bb91");// test
        // if(user == null)
        //     throw new UnknownAccountException("账号不存在");

        // if ("1".equals(username)){
	    //     //处理session 单用户登录
	    //     DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
	    //     DefaultWebSessionManager sessionManager = (DefaultWebSessionManager)securityManager.getSessionManager();
	    //     Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表
	    //     for(Session session:sessions){
	    //         //清除该用户以前登录时保存的session
	    //         if(username.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
	    //             sessionManager.getSessionDAO().delete(session);
	    //         }
	    //     }
        // }
        // 封装成SimpleAuthenticationInfo放入shiro.调用CredentialsMatcher进行检验密码
        return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword() , ByteSource.Util.bytes("qwert"),this.getClass().getName());
    }
    /**
     * 授权:对登录用户进行授权
     * 1.subject.isPermitted(String str)方法时会调用
     * 2.controller使用@RequiresRoles("admin")注解的时候调用
     * 3.<shiro:hasPermission name="admin"></shiro:hasPermission>在页面上加shiro标签的时候，即进这个页面的时候扫描到有这个标签的时候调用
     *
     * @param principal
     * PrincipalCollection是一个身份集合，因为我们可以在Shiro中同时配置多个Realm，所以呢身份信息可能就有多个；因此其提供了PrincipalCollection用于聚合这些身份信息
     * Object getPrimaryPrincipal(); //得到主要的身份
     * <T> T oneByType(Class<T> type); //根据身份类型获取第一个
     * <T> Collection<T> byType(Class<T> type); //根据身份类型获取一组
     * List asList(); //转换为List
     * Set asSet(); //转换为Set
     * Collection fromRealm(String realmName); //根据Realm名字获取
     * Set<String> getRealmNames(); //获取所有身份验证通过的Realm名字
     * boolean isEmpty(); //判断是否为空
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        // 得到的为new SimpleAuthenticationInfo(username, utoken.getPassword(),this.getClass().getName())第一个参数
    	// principal.getPrimaryPrincipal();

        // 角色列表
        List<String> roleidsList = new ArrayList<>(){{
            add("user");
        }};
        // 权限列表
        List<String> powersList = new ArrayList<>(){{
            add("user:add");
        }};

        Map<String,Collection<String>> map = new HashMap<>(){{
            put("roleIds",roleidsList);
            put("powers",powersList);

        }};

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleidsList);
        info.addStringPermissions(powersList);
        return info;
    }

    /**
     * 设置Realm名称
     * PrincipalCollection调用方法:Collection fromRealm(String realmName); //根据Realm名字获取
     */
    // @Override
    // public String getName() {
    //     return "myRealm";
    // }
    //
    // public boolean supports(AuthenticationToken token) {
    //     return token instanceof UsernamePasswordToken;
    // }


    /**
     * 重写方法,清除当前用户的的授权缓存
     */
    // @Override
    // public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
    //     super.clearCachedAuthorizationInfo(principals);
    // }

    /**
     * 重写方法，清除当前用户的认证缓存
     */
    // @Override
    // public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
    //     super.clearCachedAuthenticationInfo(principals);
    // }

    // @Override
    // public void clearCache(PrincipalCollection principals) {
    //     super.clearCache(principals);
    // }

    /**
     * 自定义方法：清除所有授权缓存
     */
    // public void clearAllCachedAuthorizationInfo() {
    //     getAuthorizationCache().clear();
    // }

    /**
     * 自定义方法：清除所有认证缓存
     */
    // public void clearAllCachedAuthenticationInfo() {
    //     getAuthenticationCache().clear();
    // }

    /**
     * 自定义方法：清除所有认证缓存和授权缓存
     */
    // public void clearAllCache() {
    //     clearAllCachedAuthenticationInfo();
    //     clearAllCachedAuthorizationInfo();
    // }
}