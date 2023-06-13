package com.xmxe;

import com.xmxe.common.Const;

public class DynamicDataSourceHolder {

    private static final ThreadLocal<String> DATA_SOURCE_THEAD_LOCAL =
            ThreadLocal.withInitial(() -> Const.DEFAULT);


    public static String getDataSource() {
        return DATA_SOURCE_THEAD_LOCAL.get();
    }

    public static void setDataSource(String dataSource) {
        DATA_SOURCE_THEAD_LOCAL.set(dataSource);
    }

    public static void remove() {
        DATA_SOURCE_THEAD_LOCAL.remove();
    }

}