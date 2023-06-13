package com.xmxe.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(DruidProperties.class)
public class YamlDynamicDataSourceProvider implements DynamicDataSourceProvider {
	@Autowired
	DruidProperties druidProperties;

	/**
	 * 读取所有的数据源对象
	 * 数据源的相关属性都在druidProperties对象中，我们先根据基本的数据库连接信息创建一个DataSource对象，然后再调用
	 * druidProperties#dataSource方法为这些数据源连接池配置其他的属性（最大连接数、最小空闲数等），最后，以key-value
	 * 的形式将数据源存入一个Map集合中，每一个数据源的key就是你在YAML中配置的数据源名称
	 */
	@Override
	public Map<String, DataSource> loadDataSources() {
		Map<String, DataSource> ds = new HashMap<>(druidProperties.getDs().size());
		try {
			// 配置文件ds下的配置信息 key:master/slave
			Map<String, Map<String, String>> map = druidProperties.getDs();
			Set<String> keySet = map.keySet();
			for (String s : keySet) {
				// master.slave下的配置信息
				DruidDataSource dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(map.get(s));
				ds.put(s, druidProperties.dataSource(dataSource));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ds里面就是配置文件中配置的所有数据源
		return ds;
	}
}