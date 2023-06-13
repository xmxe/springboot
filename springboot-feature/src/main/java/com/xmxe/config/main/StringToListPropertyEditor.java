package com.xmxe.config.main;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * 自定义属性编辑器
 */
public class StringToListPropertyEditor extends PropertyEditorSupport {
	/**
	 * 将属性对象用一个字符串表示，以便外部的属性编辑器能以可视化的方式显示。缺省返回null，表示该属性不能以字符串表示
	 */
	@Override
	public String getAsText() {
	   Object value = getValue();
	   System.out.println("value = " + value);
	   return (value != null ? value.toString() : null);
	}

	/**
	 * 用一个字符串去更新属性的内部值，这个字符串一般从外部属性编辑器传入
	 * 处理请求的入参：test就是你传进来的值(并不是super.getValue())
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String[] resultArr = null;
		if (!StringUtils.isEmpty(text)) {
			resultArr = text.split("_");
		}
		setValue(resultArr);
	}
}