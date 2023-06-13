package com.xmxe.service;

import com.xmxe.config.XmxeProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class XmxeService {

	@Autowired
	XmxeProperties xmxeProperties;

	public String info(){
		return xmxeProperties.getName()+"-"+xmxeProperties.getAge()+"-"+xmxeProperties.getPlay();
	}
}
