package com.xmxe;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShiroApplication {
	public static void main(String[] args) {
		String salt = "qwert";//盐值
        SimpleHash simpleHash = new SimpleHash("SHA-1", "test", ByteSource.Util.bytes(salt),1024);
		System.out.println("toHex=" + simpleHash.toHex());// toHex=5dec08bfc8b7661d5d0b5713d4aa0d033a87bb91
		// SimpleHash hash = new SimpleHash(Sha256Hash.ALGORITHM_NAME, "test", ByteSource.Util.bytes(salt), 1024);
		// String encryptionHash = hash.toBase64();
		System.out.println("toBase64=" + simpleHash.toBase64());// toBase64=XewIv8i3Zh1dC1cT1KoNAzqHu5E=

		// 启动
		SpringApplication.run(ShiroApplication.class, args);
	}
}