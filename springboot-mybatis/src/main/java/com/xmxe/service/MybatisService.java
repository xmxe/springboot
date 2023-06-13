package com.xmxe.service;

import com.xmxe.config.MybatisBatchExecutor;
import com.xmxe.entity.User;
import com.xmxe.mapper.MybatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MybatisService {

	@Autowired
	MybatisMapper mapper;

	@Autowired
	MybatisBatchExecutor mybatisBatchExecutor;

	public User getUserById(Integer userId) {
		User user = mapper.getUserById(userId);
		return user;
	}

	/**
	 * 批量插入/更新
	 */
	public void batch(){
		var list = List.of(1,2,3);
		//批量插入
		mybatisBatchExecutor.insertOrUpdateBatch(list, MybatisMapper.class, (mapper, data)-> {
			mapper.insertList(data);
		});

		//批量更新
		mybatisBatchExecutor.insertOrUpdateBatch(list, MybatisMapper.class, (mapper, data)-> {
			mapper.updateBatch(data);
		});
	}
}