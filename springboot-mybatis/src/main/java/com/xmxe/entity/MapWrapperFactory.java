package com.xmxe.entity;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;
/**
 * 重写map的包装器工厂，返回已经实现的MapKeyLowerWrapper
 * 在mybatis的配置文件中配置
 */
public class MapWrapperFactory implements ObjectWrapperFactory {
	@Override
	public boolean hasWrapperFor(Object object) {
		return object != null && object instanceof Map;
	}

	@Override
	public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
		return new MapKeyUpperWrapper(metaObject, (Map) object);
	}
}