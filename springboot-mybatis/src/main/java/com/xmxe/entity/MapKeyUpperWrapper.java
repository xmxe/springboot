package com.xmxe.entity;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Map;

/**
 * 重写map的包装器将Map的key全部转换为大写
 */
public class MapKeyUpperWrapper extends MapWrapper {

	public MapKeyUpperWrapper(MetaObject metaObject, Map<String, Object> map) {
		super(metaObject, map);
	}
	@Override
	public String findProperty(String name, boolean useCamelCaseMapping) {
		return name == null ? "" : name.toUpperCase() ;
	}
}