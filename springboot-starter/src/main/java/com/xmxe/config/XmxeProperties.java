package com.xmxe.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * xmxe data
 */
@ConfigurationProperties(prefix = "xmxe")
public class XmxeProperties {

	/**
	 * 是否启动 默认为true
	 */
	private boolean enabled = true;
	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 年龄
	 */
	private String age;

	/**
	 * 爱好
	 */
	private String play;

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPlay() {
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
	}

}