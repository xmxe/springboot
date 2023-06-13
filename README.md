## 项目简介

|                                               项目名称                                               |                        介绍                         |                             备注                             |
|:------------------------------------------------------------------------------------------------:| :-------------------------------------------------: | :----------------------------------------------------------: |
|      [springboot-actuator](https://github.com/xmxe/springboot/tree/2.x/springboot-actuator)      |               springboot应用健康监测                | [springboot actuator](https://www.baidu.com/s?tn=39042058_22_oem_dg&ie=utf-8&wd=springboot%20actuator) |
|         [springboot-admin](https://github.com/xmxe/springboot/tree/2.x/springboot-admin)         |                                                     |                                                              |
|           [springboot-aop](https://github.com/xmxe/springboot/tree/2.x/springboot-aop)           |                         aop                         |                    面向切面，基于动态代理                    |
|           [springboot-cas](https://github.com/xmxe/springboot/tree/2.x/springboot-cas)           |                      单点登录                       |                       需部署cas server                       |
|     [springboot-dynamicDB](https://github.com/xmxe/springboot/tree/2.x/springboot-dynamicDB)     |                      多数据源                       |                                                              |
|       [springboot-feature](https://github.com/xmxe/springboot/tree/2.x/springboot-feature)       |                   测试一些新功能                    |              集成、测试、学习的spring boot项目               |
|      [springboot-flowable](https://github.com/xmxe/springboot/tree/2.x/springboot-flowable)      |                       工作流                        |                                                              |
|    [springboot-freemarker](https://github.com/xmxe/springboot/tree/2.x/springboot-freemarker)    |                 freemarker模板引擎                  | spring boot中，模板引擎以thymeleaf使用居多，生成word文档也可使用[poi-tl](http://deepoove.com/poi-tl/)代替 |
|          [springboot-i18n](https://github.com/xmxe/springboot/tree/2.x/springboot-i18n)          |                       国际化                        |                                                              |
|        [springboot-jasypt](https://github.com/xmxe/springboot/tree/2.x/springboot-jasypt)        |            集成jasypt，保护系统敏感信息             |         启动时自动解密配置文件信息，保护配置文件安全         |
|           [springboot-jsj](https://github.com/xmxe/springboot/tree/2.x/springboot-jsj)           |            一个通用spring boot项目脚手架            |            集成了全局异常处理，校验，统一返回格式            |
|         [springboot-kafka](https://github.com/xmxe/springboot/tree/2.x/springboot-kafka)         |                      集成kafka                      |                kafka生产者及消费者的配置信息                 |
|     [springboot-lifecycle](https://github.com/xmxe/springboot/tree/2.x/springboot-lifecycle)     |          spring生命周期及自定义的一些组件           |         spring bean的加载顺序，过滤器、监听器等配置          |
|          [springboot-mail](https://github.com/xmxe/springboot/tree/2.x/springboot-mail)          |               使用spring boot发送邮件               |         需要在邮箱端，如QQ邮箱，163邮箱配置好授权码          |
|       [springboot-mybatis](https://github.com/xmxe/springboot/tree/2.x/springboot-mybatis)       |                     集成mybatis                     |       此项目通过扫描不同的包实现主从读取数据，不够优雅       |
|      [springboot-Sa-Token](https://github.com/xmxe/springboot/tree/2.x/springboot-Sa-Token)      |      shiro,spring security之外的另一款权限框架      | Sa-Token是一款轻量级的Java权限认证框架，可以用来解决登录认证、权限认证、Session会话、单点登录、OAuth2.0、微服务网关鉴权等一系列权限相关问题。 |
| [springboot-sharding-jdbc](https://github.com/xmxe/springboot/tree/2.x/springboot-sharding-jdbc) |                      分库分表                       | 通过客户端实现分库分表，sharding-server,mycat之类的是通过服务端实现分库分表 |
|         [springboot-shiro](https://github.com/xmxe/springboot/tree/2.x/springboot-shiro)         |                  Springboot+Shiro                   |                                                              |
|       [springboot-starter](https://github.com/xmxe/springboot/tree/2.x/springboot-starter)       |            自定义一个SpringBoot Starter             |  在springboot-feature中配置xmxe.enabled=true后自动加载bean   |
| [springboot-state-machine](https://github.com/xmxe/springboot/tree/2.x/springboot-state-machine) |                                                     |                                                              |
|        [springboot-upload](https://github.com/xmxe/springboot/tree/2.x/springboot-upload)        |                 上传文件的几种方式                  | 基本上都自建ftp服务器实现文件上传，也可搭建类似于fastdfs之类的分布式文件系统实现文件管理，直接上传的方式大多数情况不在采用 |
|     [springboot-websocket](https://github.com/xmxe/springboot/tree/2.x/springboot-websocket)     |                    集成websocket                    |                  实现服务端主动与客户端通信                  |
|        [springboot-webssh](https://github.com/xmxe/springboot/tree/2.x/springboot-webssh)        | fork from [this](https://github.com/NoCortY/WebSSH) |    在webssh.html配置IP端口等信息,访问/websshpage 连接ssh     |


## SpringApplication.run()(3.0版本)

```java
// SpringApplication构造方法
new SpringApplication(Application.class){
    this((ResourceLoader)null, primarySources);
}
// 创建一个新的实例，这个应用程序的上下文将要从指定的来源加载Bean
public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
    // 资源初始化资源加载器，默认为null
    this.resourceLoader = resourceLoader;
    // 断言主要加载资源类不能为null，否则报错
    Assert.notNull(primarySources, "PrimarySources must not be null");
    // 初始化主要加载资源类集合并去重
    this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
    // 推断当前 WEB 应用类型，一共有三种：NONE(非web项目),SERVLET(servlet web项目),REACTIVE(响应式web项目)
    this.webApplicationType = WebApplicationType.deduceFromClasspath();
    // 设置应用上线文初始化器,从"META-INF/spring.factories"读取ApplicationContextInitializer类的实例名称集合并去重，并进行set去重。（一共7个）
    setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
    // 设置监听器,从"META-INF/spring.factories"读取ApplicationListener类的实例名称集合并去重，并进行set去重。（一共11个）
    setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
    // 推断主入口应用类，通过当前调用栈，获取Main方法所在类，并赋值给mainApplicationClass
    this.mainApplicationClass = deduceMainApplicationClass();
    }

// run()方法
public ConfigurableApplicationContext run(String... args) {
    long startTime = System.nanoTime();
    DefaultBootstrapContext bootstrapContext = createBootstrapContext();
    ConfigurableApplicationContext context = null;
    this.configureHeadlessProperty();
    // 创建所有spring运行监听器并发布应用启动事件,加载所有SpringApplicationRunListener的实现类
    SpringApplicationRunListeners listeners = getRunListeners(args);
    // 调用了starting
    listeners.starting(bootstrapContext, this.mainApplicationClass);
    try {
        // 创建ApplicationArguments对象,获取应用程序启动参数
        ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
        // 根据运行监听器和应用参数来准备spring环境,自定义监听器加载配置信息和系统环境变量,调用了environmentPrepared
        ConfigurableEnvironment environment = prepareEnvironment(listeners, bootstrapContext, applicationArguments);
        Banner printedBanner = printBanner(environment);
        // 创建Spring上下文并加载Bean
        context = createApplicationContext();
        context.setApplicationStartup(this.applicationStartup);
        // 准备ApplicationContext,该步骤包含一个非常关键的操作，将启动类注入容器，为后续开启自动化提供基础,内部调用了contextPrepared、contextLoaded
        prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);
        // 刷新应用上下文（自动装配，初始化IOC容器）
        this.refreshContext(context);
        // 应用上下文刷新后置处理，做一些扩展功能
        this.afterRefresh(context, applicationArguments);
        Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - startTime);
        if (this.logStartupInfo) {
            (new StartupInfoLogger(this.mainApplicationClass)).logStarted(this.getApplicationLog(), timeTakenToStartup);
        }
 		// 执行所有的Runner运行器,调用了started
        listeners.started(context, timeTakenToStartup);
        // 执行所有的Runner运行器
        callRunners(context, applicationArguments);
    } catch (Throwable var12) {
        if (var12 instanceof AbandonedRunException) {
            throw var12;
        }
 		// 内部调用了failed
        this.handleRunFailure(context, var12, listeners);
        throw new IllegalStateException(var12);
    }

    try {
        if (context.isRunning()) {
            Duration timeTakenToReady = Duration.ofNanos(System.nanoTime() - startTime);
             // 发布应用上下文就绪事件,调用了reday
            listeners.ready(context, timeTakenToReady);
        }
		// 返回应用上下文
        return context;
    } catch (Throwable var11) {
        if (var11 instanceof AbandonedRunException) {
            throw var11;
        } else {
            this.handleRunFailure(context, var11, (SpringApplicationRunListeners)null);
            throw new IllegalStateException(var11);
        }
    }
}

public interface SpringApplicationRunListener {
	// run方法第一次被执行时调用，早期初始化工作
	void starting();
	// environment创建后，ApplicationContext创建前
	void environmentPrepared(ConfigurableEnvironment environment);
	// ApplicationContext实例创建，部分属性设置了
	void contextPrepared(ConfigurableApplicationContext context);
	// ApplicationContext加载后，refresh前
	void contextLoaded(ConfigurableApplicationContext context);
	// refresh后
	void started(ConfigurableApplicationContext context);
	// 所有初始化完成后，run结束前
	void running(ConfigurableApplicationContext context);
	// 初始化失败后
	void failed(ConfigurableApplicationContext context, Throwable exception);
}
```

## Spring MVC配置相关

Spring Boot中，SpringMVC相关的自动化配置是在`WebMvcAutoConfiguration`配置类中实现的，它的生效条件有一条，就是当不存在WebMvcConfigurationSupport的实例时，这个自动化配置才会生生效。因此，**如果我们在Spring Boot中自定义SpringMVC配置时选择了继承WebMvcConfigurationSupport，就会导致Spring Boot中SpringMVC的自动化配置失效**。Spring Boot给我们提供了很多自动化配置，很多时候当我们修改这些配置的时候，并不是要全盘否定Spring Boot提供的自动化配置，我们可能只是针对某一个配置做出修改，其他的配置还是按照Spring Boot默认的自动化配置来，而继承WebMvcConfigurationSupport来实现对SpringMVC的配置会导致所有的SpringMVC自动化配置失效，因此，一般情况下我们不选择这种方案。

> 若直接继承WebMvcConfigurationSupport,会导致application.properties配置文件不生效，需要增加以下等相关配置代替配置文件
> 例:
>
> ```java
> @Bean
> public InternalResourceViewResolver resourceViewResolver(){
>     InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
>     // 请求视图文件的前缀地址
>     internalResourceViewResolver.setPrefix("/WEB-INF/views/");
>     // 请求视图文件的后缀
>     internalResourceViewResolver.setSuffix(".jsp");
>     return internalResourceViewResolver;
> }
> 
> @Override
> public void configureViewResolvers(ViewResolverRegistry registry) {
>     super.configureViewResolvers(registry);
>     registry.viewResolver(resourceViewResolver());
>     registry.jsp("/WEB-INF/views/",".jsp");
> }
> ```

在SpringBoot2.0及Spring 5.0**WebMvcConfigurerAdapter已被废弃,标记为过时**。使用方式：
1. 直接实现WebMvcConfigurer（官方推荐）[WebMvcConfigurer详解](https://blog.csdn.net/zhangpower1993/article/details/89016503)
2. 直接继承WebMvcConfigurationSupport(继承此方法会导致application.properties不生效)

```java
// WebMvcConfigurer常用的方法 
// 解决跨域问题 
public void addCorsMappings(CorsRegistry registry) ;
// 添加拦截器
void addInterceptors(InterceptorRegistry registry);
// 这里配置视图解析器 
void configureViewResolvers(ViewResolverRegistry registry);
// 配置内容裁决的一些选项
void configureContentNegotiation(ContentNegotiationConfigurer configurer);
// 视图跳转控制器 
void addViewControllers(ViewControllerRegistry registry);
// 静态资源处理 
void addResourceHandlers(ResourceHandlerRegistry registry);
// 默认静态资源处理器
void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);
```

> 跟自定义SpringMVC相关的类和注解主要有如下四个：
> - WebMvcConfigurerAdapter
> - WebMvcConfigurer
> - WebMvcConfigurationSupport
> - @EnableWebMvc
>
> Spring Boot1.x中，自定义SpringMVC配置可以通过继承WebMvcConfigurerAdapter来实现。
> Spring Boot2.x中，自定义SpringMVC配置可以通过实现WebMvcConfigurer接口来完成。
> 如果在Spring Boot中使用继承WebMvcConfigurationSupport来实现自定义SpringMVC配置，或者在Spring Boot中使用了@EnableWebMvc注解，都会导致Spring Boot中默认的SpringMVC自动化配置失效。在纯Java配置的SSM环境中，如果我们要自定义SpringMVC配置，有两种办法，第一种就是直接继承自WebMvcConfigurationSupport来完成SpringMVC配置，还有一种方案就是实现WebMvcConfigurer接口来完成自定义SpringMVC配置，如果使用第二种方式，则需要给SpringMVC的配置类上额外添加@EnableWebMvc注解，表示启用WebMvcConfigurationSupport，这样配置才会生效。换句话说，在纯Java配置的SSM中，如果你需要自定义SpringMVC配置，你离不开WebMvcConfigurationSupport，所以在这种情况下建议通过继承WebMvcConfigurationSupport来实现自动化配置。


## 扫描注解

1. implements SmartInitializingSingleton

```java
String[] beanDefinitionsNames=applicationContext.getBeanNamesForType(LifeCycleController.class,false,true);
for (String beanDefinitionName : beanDefinitionsNames){
	Object bean = applicationContext.getBean(beanDefinitionName);
    // map = 注解及注解所在的方法
    Map<Method, InvokeMethod> annotatedMethods = null;
    try{
        // 查找bean里指定注解的方法
        annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(), new MethodIntrospector.MetadataLookup<InvokeMethod>() {
            @Override
            public InvokeMethod inspect(Method method) {
                return AnnotatedElementUtils.findMergedAnnotation(method,InvokeMethod.class);
            }
        });
    }catch (Exception e){
        e.printStackTrace();
    }
```

2. implements BeanPostProcessor

```java
Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
for (Method method : methods) {
    MsgEvent myMsgEvent = AnnotationUtils.findAnnotation(method, MsgEvent.class)
}
```
3. `HandlerMethod.getMethodAnnotation(Class<A> annotationType)`,底层`AnnotatedElementUtils.findMergedAnnotation(method,InvokeMethod.class)`实现

4. HandlerMethodSelector在spring5.0中废弃
```java
Class<?> handlerType = applicationContext.getType(beanName);
final Class<?> userType = ClassUtils.getUserClass(handlerType);
Set<Method> = HandlerMethodSelector.selectMethods(userType, new ReflectionUtils.MethodFilter() {
	public boolean matches(Method method) {
    	OpanApi methodAnnotation = AnnotationUtils.findAnnotation(method, OpanApi.class);
        if (methodAnnotation != null) {
            return true;
        }
        return false;
    }
});

```

## 相关文章

- [Spring Boot核心知识剖析，写得太好了](https://mp.weixin.qq.com/s/MX2YxMASHfz4dr3a4sgFcw)
- [为什么SpringBoot的jar可以直接运行？](https://mp.weixin.qq.com/s/JoEmiVP1lp9OVO7x1-x4zw)
- [Spring Boot为何可以使用Jar包启动？](https://mp.weixin.qq.com/s/kXHc35ZvdoHAQYZ6pGvAsw)
- [Spring Boot手动配置@Enable的秘密](https://mp.weixin.qq.com/s/w1zRHJqayRycIAxmnonrTA)
- [Spring Boot源码分析(2.1.0.RELEASE)](https://github.com/yuanmabiji/spring-boot-2.1.0.RELEASE)
- [Spring Boot相关漏洞学习资料](https://github.com/LandGrey/SpringBootVulExploit)
- [springboot源码解析(一)：启动过程](https://mp.weixin.qq.com/s/y1GQ6hrZ3MOEozj25MBknw)
- [Spring Boot面试：说说自动配置的原理？](https://mp.weixin.qq.com/s/sjn847TZ814FcKCF3MPBqA)
- [Spring Boot启动注解分析](https://mp.weixin.qq.com/s/n0BdIl6KGSuwxMqy77iugg)


## 致谢
![](https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.svg) <br/>
感谢Jetbrains开源免费许可