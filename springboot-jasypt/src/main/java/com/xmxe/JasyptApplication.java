package com.xmxe;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JasyptApplication {

	@Value("${abc.username}")
	String username;

	@Value("${abc.password}")
	String password;

	// 经测试启动程序不会自动加密(会自动解密) 需要使用maven插件将待加密的内容去加密
	@Value("${abc.dec}")
	String dec;

	@Autowired
	StringEncryptor stringEncryptor;

	public static void main(String[] args) {
		SpringApplication.run(JasyptApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return (args) -> {
			// System.out.println("--"+stringEncryptor.encrypt("abc"));
			// System.out.println("=="+stringEncryptor.encrypt("456"));
			// System.out.println(stringEncryptor.decrypt("解密"));
			System.out.println(username + "--" + password + "--"+ dec);

		};
	}

}
