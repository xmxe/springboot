package com.xmxe.load;

import com.xmxe.anno.InvokeMethod;
import com.xmxe.controller.LifeCycleController;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

// @Component("springBeanLoadOrderName")
public class SpringBeanLoadOrder implements BeanNameAware,
											BeanFactoryAware,
											ApplicationContextAware,
											InitializingBean,
											SmartInitializingSingleton,
											DisposableBean
											// BeanPostProcessor
											// BeanFactoryPostProcessor
{

	private Logger logger = LoggerFactory.getLogger(SpringBeanLoadOrder.class);

	private ApplicationContext applicationContext;

	private BeanFactory beanFactory;

	private ConfigurableListableBeanFactory configurableListableBeanFactory;

	private String beanName = "";

	// 使MyHandlerMethodReturnValueHandler生效(对处理器的处理结果再进行一次二次加工)
	@Autowired
	RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	/**
	 * implements BeanNameAware
	 * 触发点在bean的初始化之前，也就是postProcessBeforeInitialization()之前
	 * 获取或者修改beanName
	 * order 1
	 */
	@Override
	public void setBeanName(String currentBeanName) {
		beanName = currentBeanName;
		logger.info("order 1 - beanName==[{}],BeanNameAware.setBeanName(),applicationContext==[{}]",beanName,applicationContext);
	}

	/**
	 * implements BeanFactoryAware
	 * 发生在bean的实例化之后，注入属性之前，也就是Setter之前
	 * 获取beanFactory
	 * order 2
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
		logger.info("order 2 - beanName==[{}],BeanFactoryAware.setBeanFactory(),beanFactory==[{}],applicationContext==[{}]",beanName,this.beanFactory,applicationContext);
	}

	/**
	 * implements ApplicationContextAware
	 * order 3
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// applicationContext继承自BeanFactory,获取bean上下文环境
		this.applicationContext = applicationContext;
		logger.info("order 3 - beanName==[{}],ApplicationContextAware.setApplicationContext(),applicationCntext==[{}]",beanName,applicationContext);
	}

	/**
	 * implements BeanPostProcessor
	 * bean初始化的前置方法,ioc的每个bean都会走一遍
	 * order 4
	 */
	// @Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// 经测试一个类同时实现BeanPostProcessor，InitializingBean时同一个bean只会走afterPropertiesSet()即InitializingBean实现的方法
		// 不会走BeanPostProcessor的前置与后置方法，不单单是当前bean，BeanPostProcessor的前置后置方法每个bean都会执行一遍，而其他的实现的方法则只是当前bean执行，其他bean不执行
		logger.info("order 4 - beanName==[{}],BeanPostProcessor前置方法bean==[{}]",beanName,bean);
		return bean;
	}

	/**
	 * order 5
	 */
	@PostConstruct
	public void init() {
		// Thread.sleep(3*1000);//这里如果方法执行过长会导致项目一直无法提供服务
		logger.info("order 5 - beanName==[{}],@PostConstruct method",beanName);
	}

	/**
	 * implements InitializingBean
	 * bean在它的所有必须属性被BeanFactory设置后，来执行初始化的工作,调用其afterPropertiesSet()方法
	 * order 6
	 */
	@Override
	public void afterPropertiesSet() {
		// 使MyHandlerMethodReturnValueHandler(对处理器的处理结果再进行一次二次加工)生效start

		/*
		 * 我们将RequestMappingHandlerAdapter中已经配置好的HandlerMethodReturnValueHandler拎出来挨个检查，
		 * 如果类型是RequestResponseBodyMethodProcessor，则重新构建，用我们自定义的MyHandlerMethodReturnValueHandler代替它，
		 * 最后给requestMappingHandlerAdapter重新设置HandlerMethodReturnValueHandler即可
		 */
		// List<HandlerMethodReturnValueHandler> originHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
		// List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<>(originHandlers.size());
		// for (HandlerMethodReturnValueHandler originHandler : originHandlers) {
		// 	if (originHandler instanceof RequestResponseBodyMethodProcessor) {
		// 		newHandlers.add(new MyHandlerMethodReturnValueHandler(originHandler));
		// 	}else{
		// 		newHandlers.add(originHandler);
		// 	}
		// }
		// requestMappingHandlerAdapter.setReturnValueHandlers(newHandlers);

		// 使MyHandlerMethodReturnValueHandler生效end

		logger.info("order 6 - beanName==[{}],InitializingBean.afterPropertiesSet()",beanName);
	}

	/**
	 * spring bean init method
	 * order 7
	 */
	public void methodInit(){
		logger.info("order 7 - beanName==[{}],execute bean init method",beanName);
	}


	/**
	 * implements BeanPostProcessor
	 * bean初始化后的后置方法,ioc的每个bean都会走一遍
	 * order 8
	 */
	// @Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// 经测试同时实现BeanPostProcessor，InitializingBean时同一个bean只会走afterPropertiesSet()即InitializingBean实现的方法
		// 不会走BeanPostProcessor的前置与后置方法，不单单是当前bean，BeanPostProcessor的前置后置方法每个bean都会执行一遍，而其他的实现的方法则只是当前bean执行,其他bean不执行
		logger.info("order 8 - beanName==[{}],BeanPostProcessor后置方法bean==[{}]",beanName,bean);
		return bean;
	}

	/**
	 * implements SmartInitializingSingleton
	 * 当所有单例bean都初始化完成以后，Spring的IOC容器会回调该接口的 afterSingletonsInstantiated()方法,
	 * 主要应用场合就是在所有单例 bean 创建完成之后，可以在该回调中做一些事情。
	 * 执行时机在ApplicationContextAware执行之后
	 * order 9
	 */
	@Override
	public void afterSingletonsInstantiated() {
		logger.info("order 9 - beanName==[{}],SmartInitializingSingleton.afterSingletonsInstantiated(),applicationCntext==[{}]",beanName,applicationContext);
		// 第一个参数type表示要查找的bean的类型,第二个蚕食includeNonSingletons是否考虑非单例bean,第三个参数allowEagerInit是否允许提早初始化
		String[] beanDefinitionsNames = applicationContext.getBeanNamesForType(LifeCycleController.class,false,true);
		for (String beanDefinitionName : beanDefinitionsNames){
//			logger.info("beanDefinitionName==[{}]",beanDefinitionName);
			Object bean = applicationContext.getBean(beanDefinitionName);
			// 注解及注解所在的方法集合
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
			if(annotatedMethods == null || annotatedMethods.isEmpty()){
				continue;
			}

			for(Map.Entry<Method,InvokeMethod> entry : annotatedMethods.entrySet()){
				Method method = entry.getKey();
				InvokeMethod invokeMethod = entry.getValue();
				if(invokeMethod == null){
					continue;
				}
				// 如果要执行的方法有参数的话可以传入
				String param = invokeMethod.param();
				try {
					// 参数1 ：方法所在的类
					// 参数2 ： 执行的方法的参数
					Object result = method.invoke(bean,param);
					logger.info("方法返回结果==[{}]",result);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @PreDestroy
	 * order 10
	 */
	@PreDestroy
	public void preDestory(){
		logger.info("order 10 - beanName==[{}],@PreDestroy method",beanName);
	}


	/**
	 * implements DisposableBean
	 * order 11
	 */
	@Override
	public void destroy() {
		logger.info("order 11 - beanName==[{}],'destory bean' -- DisposableBean.destory(),applicationCntext==[{}]",beanName,applicationContext);
	}

	/**
	 * spring baen destory method
	 * order 12
	 */
	public void methodDestory(){
		logger.info("order 12 - beanName==[{}],execute bean destory method",beanName);
	}

	public void doSomething(){
		logger.info("doSomething method");
	}


	/**
	 * 经测试实现BeanFactoryPostProcessor接口bean不再走
	 * order4(BeanPostProcessor.postProcessBeforeInitialization前置方法)
	 * order5(@PostConstruct方法)
	 * order8(BeanPostProcessor.postProcessAfterInitialization后置方法)
	 * order10(@PreDestroy方法)
	 * 顺序为order 8
	 */
	// @Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
		// ConfigurableListableBeanFactory继承自BeanFactory,里面有个getBeanDefinition返回BeanDefinition BeanDefinition里面的方法就是一些定义bean的操作
		this.configurableListableBeanFactory = configurableListableBeanFactory;
		logger.info("order 8 - beanName==[{}] ,configurableListableBeanFactory = [{}],BeanFactoryPostProcessor.postProcessBeanFactory()",beanName,this.configurableListableBeanFactory);
		/*
		 * BeanFactoryPostProcessor、BeanPostProcessor区别
		 * BeanFactoryPostProcessor:针对bean工厂,BeanFactory后置处理器,是对BeanDefinition对象进行修改。（BeanDefinition：存储bean标签的信息，用来生成bean实例）
		 * BeanPostProcessor:针对bean,Bean后置处理器，是对生成的Bean对象进行修改。
		 *
		 * BeanFactoryPostProcessor接口是针对bean容器的，它的实现类可以在当前BeanFactory初始化（spring容器加载bean定义文件）后，bean实例化之前修改bean的定义属性，达到影响之后实例化bean的效果。
		 * 也就是说，Spring允许BeanFactoryPostProcessor在容器实例化任何其它bean之前读取配置元数据，并可以根据需要进行修改，
		 * 例如可以把bean的scope从singleton改为prototype，也可以把property的值给修改掉。可以同时配置多个BeanFactoryPostProcessor，并通过设置’order’属性来控制各个BeanFactoryPostProcessor的执行次序
		 *
		 * BeanPostProcessor能在spring容器实例化bean之后，在执行bean的初始化方法前后，添加一些自己的处理逻辑。初始化方法包括以下两种：
		 * 1、实现InitializingBean接口的bean，对应方法为afterPropertiesSet
		 * 2、xml定义中，通过init-method设置的方法
		 * BeanPostProcessor是BeanFactoryPostProcessor之后执行的。
		 */

	}
}