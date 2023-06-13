package com.xmxe.entity;

public class MyMessage {
	// 消息类型
	private String type;
	// 内容
	private String content;
	// 谁发的
	private String from;
	// 给谁发
	private String to;
	// 频道
	private String channel;


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}