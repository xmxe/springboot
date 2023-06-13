package com.xmxe.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 数据库分片算法
 */
// @Component
@Slf4j
public class DatabasePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

	@Override
	public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
		Long curValue = preciseShardingValue.getValue();
		log.info("curValue: {}", curValue);
		String curBase = "";
		if (curValue % 2 == 0) {
			curBase =  "ds0";
		}else{
			curBase = "ds1";
		}
		return curBase;
	}

}