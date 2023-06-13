package com.xmxe.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfiguration {

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(){
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		// 配置登录的url
		bean.setLoginUrl("/loginPage");
		// 当session里面没有我们在认证之前的访问路径时successUrl才生效 shiro登陆后没有返回设置的successUrl？https://www.cnblogs.com/ginponson/p/5205962.html
		bean.setSuccessUrl("/index");
		// 设置未授权的页面,如果登陆后访问接口发现subject没有权限，则跳转到未授权页面
		bean.setUnauthorizedUrl("/noauth");
        bean.setSecurityManager(securityManager());
		// 配置访问权限
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// anno表示无需认证即可访问
		filterChainDefinitionMap.put("/login", "anon");//不加这个的话login接口会报302
		// filterChainDefinitionMap.put("/favicon.ico","anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		// 访问/user接口必须用用记住我功能才能使用 rememberMe=true user用户可以直接访问无需再次登录
		filterChainDefinitionMap.put("/user", "user");
		// perms拥有对某个资源的权限才能用
		filterChainDefinitionMap.put("/hello", "perms[user:add]");
		// roles拥有某个角色权限才能访问
		// 使用权限注解@RequiresRoles()时bean.setUnauthorizedUrl("/noauth")失效 无法跳转到未认证页面 https://blog.csdn.net/bicheng4769/article/details/86680955
		filterChainDefinitionMap.put("/requiresRoles", "roles[abc]");
		// 注销登录用户的方式有两种，一种是下面这样,一种是使用subject.logout();
		// filterChainDefinitionMap.put("/logout","logout");
		// authc表示需要认证才可以访问,如果下面的定义与上面的发生冲突，那按照谁先定义谁说了算，所以/**一定要配置在最后面
		filterChainDefinitionMap.put("/**", "authc");
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return bean;
	}

    // @Bean
    // public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    //     DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
    //     LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
	//     // anno表示无需认证即可访问
    //     filterChainDefinitionMap.put("/loginCheck", "anon");
	// 	   filterChainDefinitionMap.put("/static/**", "anon");
	//     // authc表示需要认证才可以访问
    //     filterChainDefinitionMap.put("/**", "authc");
	//     // user必须用用记住我功能才能使用 rememberMe user用户可以直接访问无需再次登录
    //     filterChainDefinitionMap.put("/**", "user");
	// 	   // perms拥有对某个资源的权限才能用
	//     // filterChainDefinitionMap.put("/hello", "perms[user:add]");
	// 	   // role拥有某个角色权限才能访问
	//     // filterChainDefinitionMap.put("/world", "role[abc]");
    //     chainDefinition.addPathDefinitions(filterChainDefinitionMap);
	//
    //     return chainDefinition;
    // }

    /**
     * 配置自定义的权限登录器
     */
    @Bean
    public MyRealm authRealm() {
        MyRealm authRealm = new MyRealm();
		// 设置缓存
        // authRealm.setCachingEnabled(true);
	    // 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
	    // authRealm.setAuthenticationCachingEnabled(true);
	    // 缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
	    // authRealm.setAuthenticationCacheName("authenticationCache");
	    // 启用授权缓存，即缓存AuthorizationInfo信息，默认false
	    // authRealm.setAuthorizationCachingEnabled(true);
	    // 缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
	    // authRealm.setAuthorizationCacheName("authorizationCache");

        // 配置自定义密码比较器
        // authRealm.setCredentialsMatcher(new CredentialsMatcher());
	    authRealm.setCredentialsMatcher(hashedCredentialsMatcher());

        return authRealm;
    }

	/**
	 * session管理器
	 */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManage = new DefaultWebSessionManager();
        // session超时时间
		sessionManage.setGlobalSessionTimeout(1000 * 60 * 30);
		// shiro中通过sessionDao来实现对session的持久化，简单的说就是CRUD.
        sessionManage.setSessionDAO(new MemorySessionDAO());
		// 启动定时校验session调度
        sessionManage.setSessionValidationSchedulerEnabled(true);
		// 设置校验session调度器
        sessionManage.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());

		// sessionManage.setSessionIdCookieEnabled(true);
        // sessionManage.setSessionIdCookie(getSessionIdCookie());//防止与rememberMe 设置cookie冲突 所以注释掉

	    /*
	     * 用了记住我之后关闭浏览器再次打开，第一次访问的时候总会出现错误页面，刷新之后才正常，而注意到url上总有一个jsession=******** 的东西，查了好久终于解决
		 * 解决Shiro第一次重定向url携带jsessionid问题，Shiro在进行第一次重定向时会在url后携带jsessionid，导致访问400
		 * 解决办法：
		 * 创建一个DefaultWebSessionManager类实例，并将它的sessionIdUrlRewritingEnabled属性设置成false
		 * 再在DefaultWebSecurityManager类中将上面的实例设置为它的SessionManage
	     */
        // 解决Shiro第一次重定向url携带jsessionid问题 https://blog.csdn.net/Ruanes/article/details/108417460
        sessionManage.setSessionIdUrlRewritingEnabled(false);
        return sessionManage;
    }

    /**
     * 配置核心安全事务管理器
     */
    @Bean(name="securityManager")
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 设置Realm
		manager.setRealm(authRealm());
		// 设置session管理器
        manager.setSessionManager(sessionManager());
		// 设置remeberme管理器
        manager.setRememberMeManager(rememberMeManager());

	    // 设置缓存管理
	    // 第一种缓存
	    // securityManager.setCacheManager(cacheManager);
	    // ehcache缓存，推荐
	    // manager.setCacheManager(ehCacheManager());
        return manager;
    }

	/**
	 * remeberMe管理器
	 */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
	    /*
	     * 关于shiro报错 there is no session with id的相关问题
	     * 登陆页面不记住密码就不会报这个错 或者关闭浏览器（只要浏览器地址没有jsessionid就不会报错）
	     */
	    SimpleCookie cookie = new SimpleCookie("rememberMe");
	    // 表示js脚本无法读取cookie信息
	    // cookie.setHttpOnly(true);

	    // -1表示关闭浏览器 cookie就会消失 单位是秒
	    cookie.setMaxAge(100);
	    // 正常的cookie只能在一个应用中共享，即：一个cookie只能由创建它的应用获得。可在同一应用服务器内共享cookie的方法：设置cookie.setPath("/");
	    cookie.setPath("/");
        cookieRememberMeManager.setCookie(cookie);
        // rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * 校验session验证调度器 检测会话(session)是否过期
     */
	public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
		ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
		// 15分钟校验一次
		scheduler.setInterval(15 * 60 * 1000);
		return scheduler;
	}

	/**
	 * html引用shiro标签所需要注册的bean
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

   /**
    * 管理shiro bean生命周期
    */
    @Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 开启cglib代理
	 * 使用shiro-spring-boot-web-starter 启用权限注解无需注册为bean 否则引入spring-boot-starter-aop才能生效
	 */
    // @Bean
    // public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
    //     DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
    //     /*
    //      * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
    //      * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。 加入这项配置能解决这个bug
    //      */
    //     creator.setUsePrefix(true);
    //     //以cglib动态代理方式生成代理类
    //     creator.setProxyTargetClass(true);
    //     return creator;
    // }

	/**
	 * 开启shiro注解支持 基于aop
	 * 使用代理方式 所以需要开启代码支持
	 * 使用shiro-spring-boot-web-starter 启用权限注解无需注册为bean 否则引入spring-boot-starter-aop才能生效
	 */
    // @Bean
    // public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
    //     AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    //     advisor.setSecurityManager(securityManager());
    //     return advisor;
    // }


	/**
	 * 缓存配置
	 * shiro自带的MemoryConstrainedCacheManager作缓存
	 * 但是只能用于本机，在集群时就无法使用，需要使用ehcache
	 */
	// @Bean(name = "cacheManager")
	// public CacheManager cacheManager() {
	// 	MemoryConstrainedCacheManager cacheManager=new MemoryConstrainedCacheManager();//使用内存缓存
	// 	return cacheManager;
	// }

	/**
	 * 配置ehcache,推荐使用
	 */
	// @Bean(name = "ehCacheManager")
	// public EhCacheManager ehCacheManager(){
	// 	EhCacheManager ehCacheManager = new EhCacheManager();
	// 	ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
	// 	return ehCacheManager;
	// }

	/**
	 * 密码比较器
	 */
	// @Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		// 散列算法
		hashedCredentialsMatcher.setHashAlgorithmName("SHA-1");
		// 散列的次数，比如散列两次，相当于md5(md5("")); 默认1
		hashedCredentialsMatcher.setHashIterations(1024);
		// true:hex false:base64 看源码
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);

		// 盐值的话已经在Realm中doGetAuthenticationInfo()方法返回中带着:new SimpleAuthenticationInfo(user.getUsername(),user.getPassword() , ByteSource.Util.bytes("qwert"),this.getClass().getName());
		return hashedCredentialsMatcher;
	}

}