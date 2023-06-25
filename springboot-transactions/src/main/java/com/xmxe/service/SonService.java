package com.xmxe.service;

import com.xmxe.anno.SonTransaction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SonService {

	/**
	 * 参数说明: 以下4个方法参数和此相同
	 *
	 * @param args   业务中需要传递的参数
	 * @param thread 调用者的线程, 用于aop获取参数, 不建议以方法重写的方式简略此参数,
	 *               在调用者方法中可以以此参数为标识计算子线程的个数作为注解参数,避免线程参数计算错误导致锁表
	 *               传参时参数固定为: Thread.currentThread()
	 */
	@Transactional(rollbackFor = Exception.class)
	@Async("threadPoolTaskExecutor")
	@SonTransaction
	public void sonMethod1(String args, Thread thread) {
		System.out.println(args + "开启了线程");
	}

	@Transactional(rollbackFor = Exception.class)
	@Async("threadPoolTaskExecutor")
	@SonTransaction
	public void sonMethod2(String args1, String args2, Thread thread) {
		System.out.println(args1 + "和" + args2 + "开启了线程");
	}

	@Transactional(rollbackFor = Exception.class)
	@Async("threadPoolTaskExecutor")
	@SonTransaction
	public void sonMethod3(String args, Thread thread) {
		System.out.println(args + "开启了线程");
	}

	// sonMethod4方法没有使用线程池
	@Transactional(rollbackFor = Exception.class)
	public void sonMethod4(String args) {
		System.out.println(args + "没有开启线程");
	}
}