package com.xmxe.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.xmxe.util.RequestDataHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态设置表名
 */
@Configuration
public class MyBatisPlusConfig {
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
		dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
			// 获取参数方法
			String newTableName = RequestDataHelper.getRequestData("tableName");
			RequestDataHelper.removeRequestData();
			return newTableName == null ? tableName : newTableName;
		});
		interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
		// 3.4.3.2 作废该方式
		// dynamicTableNameInnerInterceptor.setTableNameHandlerMap(map);
		return interceptor;
	}
}