package com.xmxe.controller;

import com.xmxe.anno.InvokeMethod;
import com.xmxe.anno.UserId;
import com.xmxe.config.listen.MyEvent;
import com.xmxe.config.listen.MyEvent2;
import com.xmxe.load.SpringBeanLoadOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class LifeCycleController {

	@Autowired
	SpringBeanLoadOrder springBeanLoadOrder;

	@Resource
	ApplicationContext context;

	@RequestMapping("/index")
	public String index() {
		// 增加监听器 当跳转到首页的时候触发监听器事件
		MyEvent myEvent = new MyEvent(this);
		// 发布事件，这样才能在CustomListener监听到
		context.publishEvent(myEvent);
		context.publishEvent(new MyEvent2("hhhhhhhhh"));
		return "index";
	}

	@GetMapping("paramResolver")
	public String paramResolver(@UserId String userId){
		// 接收的参数在自定义解析器里面+0
		return userId;
	}


	@InvokeMethod(param="ppppp")
	public String invoke(String param){
		springBeanLoadOrder.doSomething();
		System.out.println("结果"+param);
		return param;
	}

	@GetMapping("returnValue")
	public Map<String,Object> returnValue(){
		return Map.of("code",200,"msg","请求成功","data","book");
	}
}