package com.xmxe;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.logging.Logger;

public class MultiDataSource extends AbstractRoutingDataSource {

    private static final Logger LOGGER = Logger.getLogger(MultiDataSource.class.getName());

    @Override
    protected Object determineCurrentLookupKey() {
        String key = DynamicDataSourceHolder.getDataSource();
        LOGGER.info("DataSource key ---> " + key);
        return key;
    }

}