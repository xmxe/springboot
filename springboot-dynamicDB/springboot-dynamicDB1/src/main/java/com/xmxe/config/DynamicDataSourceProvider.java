package com.xmxe.config;

import javax.sql.DataSource;
import java.util.Map;

public interface DynamicDataSourceProvider {
	String DEFAULT_DATASOURCE = "master";
	/**
	 * 加载所有的数据源
	 */
	Map<String, DataSource> loadDataSources();
}
