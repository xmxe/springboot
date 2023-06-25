package com.xmxe.service;

import com.xmxe.anno.MainTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MainService {

	@Resource
	private SonService sonService;

	@MainTransaction(3)// 调用的方法中sonMethod1/sonMethod2/sonMethod3使用@Async开启了线程,所以参数为: 3
	@Transactional(rollbackFor = Exception.class)
	public void test1() {
		sonService.sonMethod1("路飞", Thread.currentThread());
		sonService.sonMethod2("索隆", "山治", Thread.currentThread());
		sonService.sonMethod3("娜美", Thread.currentThread());
		sonService.sonMethod4("罗宾");
	}

	/*
	 * 有的业务中存在if的多种可能, 每一种走向调用的方法(开启线程的方法)数量如果不同, 这时可以选择放弃使用@MainTransaction注解避免锁表
	 * 这时候如果发生异常会导致多线程不能同时回滚, 可根据业务自己权衡是否使用
	 */
	@Transactional(rollbackFor = Exception.class)
	public void test2() {
		sonService.sonMethod1("路飞", Thread.currentThread());
		sonService.sonMethod2("索隆", "山治", Thread.currentThread());
		sonService.sonMethod3("娜美", Thread.currentThread());
		sonService.sonMethod4("罗宾");
	}

}