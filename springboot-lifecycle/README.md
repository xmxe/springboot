## Aware接口

<img alt="" src="https://mmbiz.qpic.cn/mmbiz_png/GvtDGKK4uYnMAdNBhDEt2oKT6McDSjsYtiarVn6kWpCtRp05sbErlm1icevfuowMiakiayqDtMf7jTjkryYlIxicTlA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1" style="zoom:50%;" />

每一个Aware的作用如下：

- **ApplicationEventPublisherAware**：实现该接口的对象可以获取事件发布的能力。
- **ServletContextAware**：实现该接口的对象可以获取到ServletContext对象。
- **MessageSourceAware**：实现该接口的对象可以获取到MessageSource对象，MessageSource支持多消息源，主要用于主要用于国际化。
- **ResourceLoaderAware**：实现该接口的对象可以获取到一个ResourceLoader，Spring ResourceLoader则为我们提供了一个统一的getResource()方法来通过资源路径检索外部资源，例如文本文件、XML文件、属性文件或图像文件等。
- **ApplicationStartupAware**：实现该接口的对象可以获取到一个ApplicationStartup对象，这个比较新，是Spring5.3中新推出的，通过ApplicationStartup可以标记应用程序启动期间的步骤，并收集有关执行上下文或其处理时间的数据。
- **NotificationPublisherAware**：实现该接的对象可以获取到一个NotificationPublisher对象，通过该对象可以实现通知的发送。
- **EnvironmentAware**：实现该接口的对象可以获取到一个Environment对象，通过Environment可以获取到容器的环境信息。
- **BeanFactoryAware**：实现该接口的对象可以获取到一个BeanFactory对象，通过BeanFactory可以完成Bean的查询等操作。
- **ImportAware**：实现该接口的对象可以获取到一个AnnotationMetadata对象，ImportAware接口是需要和@Import注解一起使用的。在@Import作为元注解使用时，通过@Import导入的配置类如果实现了ImportAware接口就可以获取到导入该配置类接口的数据配置。
- **EmbeddedValueResolverAware**：实现该接口的对象可以获取到一个StringValueResolver对象，通过StringValueResolver对象，可以读取到Spring容器中的properties配置的值（YAML配置也可以）。
- **ServletConfigAware**：实现该接口的对象可以获取到一个ServletConfig对象，不过这个似乎没什么用，我们很少自己去配置ServletConfig。
- **LoadTimeWeaverAware**：实现该接口的对象可以获取到一个LoadTimeWeaver对象，通过该对象可以获取加载Spring Bean时织入的第三方模块，如AspectJ等。
- **BeanClassLoaderAware**：实现该接口的对象可以获取到一个ClassLoader对象。
- **BeanNameAware**：实现该接口的对象可以获取到一个当前Bean的名称。
- **ApplicationContextAware**：实现该接口的对象可以获取到一个ApplicationContext对象，通过-ApplicationContext可以获取容器中的Bean、环境等信息。


## Spring Boot监听器

Spring Boot启动事件顺序ApplicationListener中的泛型的类型,可以在MyEvent里面实现某一个接口来监听不同的事件

1. **ApplicationStartingEvent**
这个事件在Spring Boot应用运行开始时，且进行任何处理之前发送（除了监听器和初始化器注册之外）。
2. **ApplicationEnvironmentPreparedEvent**
这个事件在当已知要在上下文中使用Spring环境（Environment）时，在Spring上下文（context）创建之前发送。
3. **ApplicationContextInitializedEvent**
这个事件在当Spring应用上下文（ApplicationContext）准备好了，并且应用初始化器（ApplicationContextInitializers）已经被调用，在bean的定义（bean definitions）被加载之前发送。
4. **ApplicationPreparedEvent**
这个事件是在Spring上下文（context）刷新之前，且在bean的定义（bean definitions）被加载之后发送。
5. **ApplicationStartedEvent**
这个事件是在Spring上下文（context）刷新之后，且在application/command-line runners被调用之前发送。
6. **AvailabilityChangeEvent**
这个事件紧随上个事件之后发送，状态：ReadinessState.CORRECT，表示应用已处于活动状态。
7. **ApplicationReadyEvent**
这个事件在任何application/ command-line runners调用之后发送。
8. **AvailabilityChangeEvent**
这个事件紧随上个事件之后发送，状态：ReadinessState.ACCEPTING_TRAFFIC，表示应用可以开始准备接收请求了。
9. **ApplicationFailedEvent**
这个事件在应用启动异常时进行发送

注册监听器方式1：
`implements ApplicationListener<T>`接口在类上增加@Component注册bean
自定义事件代码如下：
```java
@Component
public class CustomListener implements ApplicationListener<MyEvent>{
    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        myEvent.printMsg();
    }
}
@SuppressWarnings("serial")
public class MyEvent extends ApplicationEvent{
	public MyEvent(Object source){super(source);}
}
```
注册监听器方式2：
使用监听器的时候如果不将监听器的类注册为spring bean则使用这种方式添加
```java
ConfigurableApplicationContext context = SpringApplication.run(XmxeApplication.class, args);
// 装载监听
context.addApplicationListener(new com.xmxe.config.listen.CustomListener());
```
注册监听器方式3：
在`application.properties`中配置监听`context.listener.classes=com.xmxe.config.listen.CustomListener`

注册监听器方式4：
创建MyListener4类，该类无需实现ApplicationListener接口，使用@EventListener装饰具体方法
```java
@Component
public class MyListener4{
	Logger logger = Logger.getLogger(MyListener4.class);
	@EventListener
    public void listener(MyEvent event){
    logger.info(String.format("%s监听到事件源：%s.", MyListener4.class.getName(), event.getSource()));}
}

// 进行测试(在启动类中加入发布事件的逻辑)：
@SpringBootApplication
public class LisenterApplication{
	public static void main(String[] args){
		ConfigurableApplicationContext context = SpringApplication.run(LisenterApplication.class, args);
		//装载事件
		context.addApplicationListener(new MyListener1());
		//发布事件
		context.publishEvent(new MyEvent("测试事件."));}
}
```
- [SpringBoot项目实现发布订阅模式，真的很简单！](https://mp.weixin.qq.com/s/NVdxbd07kVLThlO6NujwNA)

## ConfigurableApplicationContext方法

> ConfigurableApplicationContext即为SpringApplication.run()返回值

```java

static interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {

/**
 * 应用上下文配置时，这些符号用于分割多个配置路径
 */
String CONFIG_LOCATION_DELIMITERS = ",; \t\n";

/**
 * BeanFactory中，ConversionService类所对应的bean的名字。如果没有此类的实例的话则使用默认的转换规则
 */
String CONVERSION_SERVICE_BEAN_NAME = "conversionService";

/**
 * LoadTimeWaver类所对应的Bean在容器中的名字。如果提供了该实例，上下文会使用临时的ClassLoader,这样，LoadTimeWaver就可以使用bean确切的类型了
 */
String LOAD_TIME_WEAVER_BEAN_NAME = "loadTimeWeaver";

/**
 * Environment类在容器中实例的名字
 */
String ENVIRONMENT_BEAN_NAME = "environment";

/**
 * System系统变量在容器中对应的Bean的名字
 */
String SYSTEM_PROPERTIES_BEAN_NAME = "systemProperties";

/**
 * System环境变量在容器中对应的Bean的名字
 */
String SYSTEM_ENVIRONMENT_BEAN_NAME = "systemEnvironment";

/**
 * 设置容器的唯一ID
 */
void setId(String id);

/**
 * 设置此容器的父容器,需要注意的是，父容器一经设定就不应该修改。并且一般不会在构造方法中对其进行配置，因为很多时候其父容器还不可用。比如WebApplicationContext。
 */
void setParent(ApplicationContext parent);

/**
 * 设置容器的Environment变量
 */
void setEnvironment(ConfigurableEnvironment environment);

/**
 * 以COnfigurableEnvironment的形式返回此容器的环境变量。以使用户更好的进行配置
 */
@Override
ConfigurableEnvironment getEnvironment();

/**
 * 此方法一般在读取应用上下文配置的时候调用，用以向此容器中增加BeanFactoryPostProcessor。增加的Processor会在容器refresh的时候使用。
 */
void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

/**
 * 向容器增加一个ApplicationListener，增加的Listener用于发布上下文事件如refresh和shutdown等,需要注意的是，如果此上下文还没有启动，那么在此注册的Listener将会在上下文refresh的时候，全部被调用,如果上下文已经是active状态的了，就会在multicaster中使用
*/
void addApplicationListener(ApplicationListener<?> listener);

/**
 * 向容器中注入给定的Protocol resolver，允许多个实例同时存在。在此注册的每一个resolver都将会在上下的标准解析规则之前使用。因此，某种程度上来说这里注册的resolver可以覆盖上下文的resolver
 */
void addProtocolResolver(ProtocolResolver resolver);

/**
 * 加载资源配置文件（XML、properties,Whatever）。由于此方法是一个初始化方法，因此如果调用此方法失败的情况下，要将其已经创建的Bean销毁。换句话说，调用此方法以后，要么所有的Bean都实例化好了，要么就一个都没有实例化
 */
void refresh() throws BeansException, IllegalStateException;

/**
 * 向JVM注册一个回调函数，用以在JVM关闭时，销毁此应用上下文。
 */
void registerShutdownHook();

/**
 * 关闭此应用上下文，释放其所占有的所有资源和锁。并销毁其所有创建好的singleton Beans,实现的时候，此方法不应该调用其父上下文的close方法，因为其父上下文具有自己独立的生命周期.多次调用此方法，除了第一次，后面的调用应该被忽略。
 */
@Override
void close();

/**
 * 检测此FactoryBean是否被启动过。
 */
boolean isActive();

/**
 * 返回此应用上下文的容器。千万不要使用此方法来对BeanFactory生成的Bean做后置处理，因为单例Bean在此之前已经生成,这种情况下应该使用BeanFactoryPostProcessor来在Bean生成之前对其进行处理。通常情况下，内容容器只有在上下文是激活的情况下才能使用。因此，在使用此方法前，可以调用isActive来判断容器是否可用
 */
ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

}

```

## 相关文章

- [SpringBoot 优雅停机的正确姿势](https://mp.weixin.qq.com/s/EasYsc9ixVVCPRvPTCCkYg)
- [Spring Boot自定义监听事件](https://mp.weixin.qq.com/s/ylmU2rT0JlnYJA9f1w065A)
- [Spring Boot如何使用拦截器、过滤器、监听器？](https://mp.weixin.qq.com/s/V0c6aZKih_luH6Qoqfgp4Q)
- [Spring Boot过滤器、拦截器、监听器对比及使用场景](https://mp.weixin.qq.com/s/aUgTwv4iQNr3BFku_qhwvw)
- [手把手教你在SpringBoot中自定义参数解析器](https://mp.weixin.qq.com/s/mbstcEEtwOS9ZRSSjusbfA)
- [SpringBoot初始化几大招式，看了终于明白了](https://mp.weixin.qq.com/s/YNFFBuokPHfQxcWTbdVfwQ)
- [Spring Boot中如何统一API接口响应格式？--HandlerMethodReturnValueHandler](https://mp.weixin.qq.com/s/TMleRgC5CSPIByoDpUaatw)
- [如何优雅记录HTTP请求/响应数据？](https://mp.weixin.qq.com/s/fYCnSSe00AOJJi02Rj2z6w)
- [聊聊Spring中最常用的11个扩展点](https://mp.weixin.qq.com/s/lxpCNGChQUsN9etLONXdoQ)
- [16个有用的SpringBoot扩展接口](https://mp.weixin.qq.com/s/dj9pRBy5EIpJiJHVDuXkIw)
- [HandlerMethod](https://mp.weixin.qq.com/s/LfIDxs8Pr1GhsSk3vsS5QA)
- [Spring MVC中处理Request和Response的策略](https://mp.weixin.qq.com/s/VCBy5eLlqL6nbIURW8iDDA)