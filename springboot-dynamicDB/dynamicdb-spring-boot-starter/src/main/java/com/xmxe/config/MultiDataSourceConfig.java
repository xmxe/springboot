package com.xmxe.config;

import com.xmxe.DynamicDataSourceAdviser;
import com.xmxe.MultiDataSource;
import com.xmxe.common.Const;
import com.xmxe.properties.DataSourceProp;
import com.xmxe.properties.MultiDataSourceProperties;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties(MultiDataSourceProperties.class)
public class MultiDataSourceConfig {

    public static final String DS_TYPE = "dsType";

    @Resource
    private MultiDataSourceProperties multiDataSourceProperties;

    private DataSource createDs(DataSourceProp dsProp) {
        DataSource dataSource = null;
        try {
            Class<?> dsClass = Class.forName(dsProp.get(DS_TYPE));
            if (DataSource.class.isAssignableFrom(dsClass)) {
                dataSource = (DataSource) dsClass.getConstructor().newInstance();

                DataSource finalDataSource = dataSource;
                ReflectionUtils.doWithFields(dsClass, field -> {
                    field.setAccessible(true);
                    field.set(finalDataSource, dsProp.get(field.getName()));
                }, field -> {
                    if (Objects.equals(dsProp.get(DS_TYPE), field.getName())) {
                        return false;
                    }
                    return Objects.nonNull(dsProp.get(field.getName()));
                });

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }

    @Bean(Const.MULTI_DS)
    @Primary
    public DataSource multiDataSource() {
        MultiDataSource multiDataSource = new MultiDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>(multiDataSourceProperties.getDataSourcePropMap().size());
        Map<String, DataSourceProp> dataSourcePropMap = multiDataSourceProperties.getDataSourcePropMap();
        dataSourcePropMap.forEach((lookupKey,dsProp) -> {
            dataSourceMap.put(lookupKey, createDs(dsProp));
        });

        multiDataSource.setTargetDataSources(dataSourceMap);
        multiDataSource.setDefaultTargetDataSource(dataSourceMap.get(Const.DEFAULT));
        return multiDataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(
            @Qualifier(Const.MULTI_DS) DataSource multiDataSource) {

        DataSourceTransactionManager tx = new DataSourceTransactionManager();
        tx.setDataSource(multiDataSource);
        return tx;
    }

    @Bean
    public DynamicDataSourceAdviser dynamicDataSourceAdviser() {
        return new DynamicDataSourceAdviser();
    }

}