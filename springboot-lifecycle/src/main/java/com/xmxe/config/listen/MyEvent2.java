package com.xmxe.config.listen;

public class MyEvent2 {
	// 该类型事件携带的信息
	private Object obj ;

	public MyEvent2(Object obj){this.obj = obj;}

	public String msg(){
		return this.obj.toString();
	}
}
