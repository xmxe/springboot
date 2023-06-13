package com.xmxe.enums;

public enum DataSourceEnum {
	MASTER("主库-study"),
	SLAVE("从库-jnhouse");

	private String dataSourceName;

	DataSourceEnum(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}
}