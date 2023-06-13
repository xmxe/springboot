package com.xmxe.entity;

public class HttpResult {
	// 响应的状态码
	private int code;
	// 响应的响应体
	private String body;
	// 返回的数据
	private Object data;

	public HttpResult(int code, String body, Object data) {
		this.code = code;
		this.body = body;
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Object getData(){return data;}
	public void setData(Object data){this.data = data;}

	
}
