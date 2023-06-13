package com.xmxe.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.xmxe.enums.DataSourceEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 主从配置
 */
@Configuration
@MapperScan(basePackages = "com.xmxe.dao", sqlSessionTemplateRef = "sqlTemplate")
public class DataSourceConfig {

	/**
	 * 主库
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource master() {
		return DruidDataSourceBuilder.create().build();
	}

	/**
	 * 从库
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource slaver() {
		return DruidDataSourceBuilder.create().build();
	}


	/**
	 * 实例化数据源路由
	 */
	@Bean
	public DataSourceRouter dynamicDB(@Qualifier("master") DataSource masterDataSource,
	                                  @Autowired(required = false) @Qualifier("slaver") DataSource slaveDataSource) {
		DataSourceRouter dynamicDataSource = new DataSourceRouter();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceEnum.MASTER.getDataSourceName(), masterDataSource);
		if (slaveDataSource != null) {
			targetDataSources.put(DataSourceEnum.SLAVE.getDataSourceName(), slaveDataSource);
		}
		dynamicDataSource.setTargetDataSources(targetDataSources);
		dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
		return dynamicDataSource;
	}


	/**
	 * 配置sessionFactory
	 */
	@Bean
	public SqlSessionFactory sessionFactory(@Qualifier("dynamicDB") DataSource dynamicDataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*Mapper.xml"));
		bean.setDataSource(dynamicDataSource);
		return bean.getObject();
	}


	/**
	 * 创建sqlTemplate
	 */
	@Bean
	public SqlSessionTemplate sqlTemplate(@Qualifier("sessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}


	/**
	 * 事务配置
	 */
	@Bean(name = "dataSourceTx")
	public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dynamicDB") DataSource dynamicDataSource) {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dynamicDataSource);
		return dataSourceTransactionManager;
	}
}