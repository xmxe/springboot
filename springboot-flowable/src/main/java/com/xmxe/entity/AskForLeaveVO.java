package com.xmxe.entity;

public class AskForLeaveVO {
	// 用户名
	private String name;
	// 请假天数
	private Integer days;
	// 请假理由
	private String reason;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}