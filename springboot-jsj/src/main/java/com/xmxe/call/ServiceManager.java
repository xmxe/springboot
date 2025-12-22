package com.xmxe.call;

import com.xmxe.entity.SerResult;
import com.xmxe.util.LambdaUtil;
import com.xmxe.util.SpringContextHolder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.SerializedLambda;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ServiceManager {
	// 缓存初始化大小
	private static final int INIT_COUNT = 1000;

	// 缓存Lambda对应的Service信息，key是Lambda，value是Service元数据
	private static final Map<SerialBiFunction<?, ?, ?>, LambdaMeta<?>> CACHE_LAMBDA;

	// 静态代码块，项目启动时就初始化缓存
	static {
		CACHE_LAMBDA = new ConcurrentHashMap<>(INIT_COUNT);
	}
	/**
	 * 对外提供的调用方法
	 * @param fn 服务函数，如 UserService::queryUser
	 * @param param 函数参数
	 * @return 执行结果
	 */
	public static <T, U, R> SerResult<R> call(SerialBiFunction<T,U,R> fn, U param){
		// 先检查：Lambda不能传空
		if (fn == null) {
			return SerResult.fail("服务函数不能为空！");
		}
		try{
			// 1. 从缓存拿Service信息
			LambdaMeta<T> lambdaMeta = getLambdaMeta(fn);
			// 2. 创建执行器，把Lambda、参数、Service信息传进去
			ServiceExecutor executor = InstBuilder.of(ServiceExecutor.class)
					.set(ServiceExecutor::setServiceFn, fn)    // 传Lambda方法
					.set(ServiceExecutor::setParam, param)      // 传参数
					.set(ServiceExecutor::setLambdaMeta, lambdaMeta)  // 传Service信息
					.build();  // 构建执行器
			// 3. 执行方法，返回结果
			return executor.callService();
		}catch (Exception e){
			log.error("服务调用失败", e);
			return SerResult.fail("服务调用失败: " + e.getMessage());
		}
	}

	/**
	 * 获取Lambda元数据，缓存中不存在则解析并缓存
	 */
	@SuppressWarnings("unchecked")
	private static <T, U, R> LambdaMeta<T> getLambdaMeta(SerialBiFunction<T, U, R> fn) {
		return (LambdaMeta<T>) CACHE_LAMBDA.computeIfAbsent(fn, k -> {
			// 解析Lambda，拿到Service实例、类名这些信息
			LambdaMeta<T> meta = parseSerialFunction((SerialBiFunction<T, U, R>) k);
			log.debug("缓存Service信息：{}", meta.getServiceName());
			return meta;
		});
	}

	/**
	 * 解析Lambda：从Lambda里拿到Service类名、实例、方法名
	 */
	private static <T, U, R> LambdaMeta<T> parseSerialFunction(SerialBiFunction<T,U,R> fn) {
		// 用LambdaUtil拿到Lambda的元数据
		SerializedLambda lambda = LambdaUtil.valueOf(fn);

		// 1. 解析Service类名：Lambda里的类名是“com/example/UserService”，要改成“com.example.UserService”
		String tClassName = lambda.getImplClass().replaceAll("/", ".");
		try {
			// 2. 拿到Service的Class对象（比如UserService.class）
			Class<?> serviceClass = Class.forName(tClassName);
			// 3. 从Spring里拿Service实例（不用@Autowired就是靠这行）
			T serviceInstance = (T)SpringContextHolder.getBean(serviceClass);
			// 4. 把信息存到lambdaMeta里 封装Service信息的对象
			LambdaMeta<T> lambdaMeta = new LambdaMeta<>();
			lambdaMeta.setClazz(serviceClass);    // 存Service的Class
			lambdaMeta.setInst(serviceInstance);  // 存Service实例
			lambdaMeta.setServiceName(lambda.getImplMethodName());  // 存方法名（比如queryUser）
			lambdaMeta.setMethodSignature(lambda.getImplMethodSignature());
			return lambdaMeta;
		} catch (ClassNotFoundException e) {
			// 找不到类就抛异常
			throw new RuntimeException("没找到Service类：" + tClassName, e);
		}catch (ClassCastException e) {
			throw new RuntimeException("Service类型转换失败，类：" + tClassName, e);
		}
	}

	/**
	 * 清除缓存
	 */
	public static void clearCache() {
		CACHE_LAMBDA.clear();
		log.info("ServiceManager缓存已清除");
	}

	/**
	 * 获取缓存大小
	 */
	public static int getCacheSize() {
		return CACHE_LAMBDA.size();
	}

	/**
	 * 封装Service信息的内部类
	 */
	@Data
	public static class LambdaMeta<T> {
		private Class<?> clazz;           // Service的Class
		private T inst;                   // Service实例
		private String serviceName;       // 方法名
		private String methodSignature;   // 方法签名（可选）
	}
}