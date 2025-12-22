package com.xmxe.entity;

import lombok.Data;

@Data
public class SerResult<T> {
	private int code;
	private String msg;
	private T data;

	public static <R> SerResult<R> success(R data) {
		SerResult<R> result = new SerResult<>();
		result.setCode(200);
		result.setMsg("操作成功");
		result.setData(data);
		return result;
	}
	public static <R> SerResult<R> fail(String msg) {
		SerResult<R> result = new SerResult<>();
		result.setCode(500);
		result.setMsg(msg);
		result.setData(null);
		return result;
	}

	public static SerResult<Void> fail(int code, String msg) {
		SerResult<Void> result = new SerResult<>();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(null);
		return result;
	}

	public static <R> SerResult<R> success(int code, String msg, R data) {
		SerResult<R> result = new SerResult<>();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(data);
		return result;
	}
}