package com.xmxe.config;

import com.xmxe.util.DataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceRouter extends AbstractRoutingDataSource {

	/**
	 * 最终的determineCurrentLookupKey返回的是从DataSourceContextHolder中拿到的,因此在动态切换数据源的时候注解应该给DataSourceContextHolder设值
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.get();
	}
}