package com.xmxe.config;

import com.xmxe.util.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类在DruidAutoConfiguration注册为bean
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	// 所有数据源
	DynamicDataSourceProvider dynamicDataSourceProvider;

	public DynamicDataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
		this.dynamicDataSourceProvider = dynamicDataSourceProvider;
		// ds key为数据源名称(如master,slave)
		Map<String, DataSource> ds = dynamicDataSourceProvider.loadDataSources();
		// targetDataSources的key值有很多，其中包含determineCurrentLookupKey()的返回值，这样才能根据返回值切换数据源
		Map<Object, Object> targetDataSources = new HashMap<>(ds);
		// 塞入全部的数据源
		super.setTargetDataSources(targetDataSources);
		// 设置默认数据源
		super.setDefaultTargetDataSource(ds.get(DynamicDataSourceProvider.DEFAULT_DATASOURCE));
		// 通过afterPropertiesSet()方法将数据源分别进行复制到resolvedDataSources和resolvedDefaultDataSource中。
		super.afterPropertiesSet();
	}

	/**
	 * 决定使用哪个数据源
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		// @DataSource注解的值，需要与配置文件的值(master或slave)对应，不对应的话无法从上面的targetDataSources中获取数据，就会走DefaultTargetDataSource
		String dataSourceType = DynamicDataSourceContextHolder.getDataSourceType();
		// return的是固定值的话就指定某个固定的数据源，真正配置切换数据源的方法
		return dataSourceType;
	}
}