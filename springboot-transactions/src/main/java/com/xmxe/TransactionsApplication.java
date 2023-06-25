package com.xmxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 我们开发的时候常常会遇到多线程事务的问题。以为添加了@Transactional注解就行了，其实你加了注解之后会发现事务失效。
 * 原因：数据库连接spring是放在threadLocal里面，多线程场景下，拿到的数据库连接是不一样的，即是属于不同事务。
 * 本文是基于springboot的@Async注解开启多线程,，并通过自定义注解和AOP实现的多线程事务，避免繁琐的手动提交/回滚事务(CV即用、参数齐全、无需配置)
 */
@SpringBootApplication
public class TransactionsApplication {
	public static void main(String[] args) {
		SpringApplication.run(TransactionsApplication.class, args);
	}
}