package com.xmxe.component;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;


/**
 * 自定义监控端点
 */
// 使用@Endpoint注解相应的类，作为Actuator的一个endpoint。注解要指定id，这个id作为访问路径，比如这里是/actuator/myEndpoint
@Endpoint(id="myEndpoint")
@Component
public class MyEndpoint {
	private String STATUS = "up";
	private String DETAIL = "一切正常";

	// @ReadOperation来注解查询接口，如果要根据路径做查询，要用@Selector注解方法参数；注意这地方是@Selector String arg0，这个arg0不能改变，改成其他的，开放出去的接口还是/{arg0}，这就导致你的方法无法正常获取参数值。
	@ReadOperation
	public HashMap<String, Object> test(){
		HashMap<String,Object> jsonObject= new HashMap<>();
		jsonObject.put("status",STATUS);
		jsonObject.put("detail",DETAIL);
		return jsonObject;
	}

	@ReadOperation
	public HashMap<String, Object> test2(@Selector String name){
		HashMap<String, Object> jsonObject= new HashMap<String, Object>();
		if ("status".equals(name)){
			jsonObject.put("status",STATUS);
		}else if ("detail".equals(name)){
			jsonObject.put("detail",DETAIL);
		}
		return jsonObject;
	}

	// @WriteOperation来注解修改接口，注意请求数据必须是json，而且参数不像controller中那么灵活，不能将实体作为参数，要把实体中相应的属性拿出来做参数。
	@WriteOperation//动态修改指标
	public void test4(@Selector String name, @Nullable String value){
		if (!StringUtils.isEmpty(value)){
			if ("status".equals(name)){
				STATUS = value;
			}else if ("detail".equals(name)){
				DETAIL = value;
			}
		}
	}
}
/*
 * 自定义监控端口常用注解
 * 自定义一个监控端点主要有如下常用注解：
 * @Endpoint：定义一个监控端点，同时支持HTTP和JMX两种方式。
 * @WebEndpoint：定义一个监控端点，只支持HTTP方式。
 * @JmxEndpoint：定义一个监控端点，只支持JMX方式。
 * 以上三个注解作用在类上，表示当前类是一个监控端点，另外还有一些注解会用在方法和参数上：
 * @ReadOperation：作用在方法上，可用来返回端点展示的信息（通过Get方法请求）。
 * @WriteOperation：作用在方法上，可用来修改端点展示的信息（通过Post方法请求）。
 * @DeleteOperation：作用在方法上，可用来删除对应端点信息（通过Delete方法请求）。
 * @Selector：作用在参数上，用来定位一个端点的具体指标路由。
 */