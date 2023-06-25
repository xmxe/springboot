package com.xmxe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * SpringBoot提供了注解@Async来使用线程池，具体使用方法如下:
 * (1) 在启动类(配置类)添加@EnableAsync来开启线程池
 * (2) 在需要开启子线程的方法上添加注解@Async
 * 注意:框架默认来一个请求开启一个线程，在高并发下容易内存溢出。所以使用时需要配置自定义线程池，如下↓
 * 开启子线程方法:在需要开启线程的方法上添加注解@Async("threadPoolTaskExecutor")即可，其中注解中的参数为自定义线程池的名称。
 */
@Configuration
@EnableAsync
public class ThreadPoolTaskConfig {

	@Bean("threadPoolTaskExecutor")// 自定义线程池名称
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		// 线程池创建的核心线程数，线程池维护线程的最少数量，即使没有任务需要执行，也会一直存活
		executor.setCorePoolSize(16);

		// 如果设置allowCoreThreadTimeout=true（默认false）时，核心线程会超时关闭
		// executor.setAllowCoreThreadTimeOut(true);

		// 阻塞队列 当核心线程数达到最大时，新任务会放在队列中排队等待执行
		executor.setQueueCapacity(124);

		// 最大线程池数量，当线程数>=corePoolSize，且任务队列已满时。线程池会创建新线程来处理任务
		// 任务队列已满时, 且当线程数=maxPoolSize，线程池会拒绝处理任务而抛出异常
		executor.setMaxPoolSize(64);

		// 当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize
		// 允许线程空闲时间30秒，当maxPoolSize的线程在空闲时间到达的时候销毁
		// 如果allowCoreThreadTimeout=true，则会直到线程数量=0
		executor.setKeepAliveSeconds(30);

		// spring提供的ThreadPoolTaskExecutor线程池，是有setThreadNamePrefix()方法的。
		// jdk提供的ThreadPoolExecutor线程池是没有setThreadNamePrefix()方法的
		executor.setThreadNamePrefix("自定义线程池-");

		// rejection-policy：拒绝策略：当线程数已经达到maxSize的时候，如何处理新任务
		// CallerRunsPolicy()：交由调用方线程运行，比如main线程；如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行, (个人推荐)
		// AbortPolicy()：该策略是线程池的默认策略，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
		// DiscardPolicy()：如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常
		// DiscardOldestPolicy()：丢弃队列中最老的任务，队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

		// 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
		executor.setAwaitTerminationSeconds(60);
		executor.initialize();
		return executor;
	}
}