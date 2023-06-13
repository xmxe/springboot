package com.xmxe.service;

import com.xmxe.anno.DataSourceSwitcher;
import com.xmxe.dao.UserMapper;
import com.xmxe.enums.DataSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;

	// 指定数据源
	@DataSourceSwitcher(DataSourceEnum.MASTER)
	public Integer master() {
		return userMapper.count();
	}

	@DataSourceSwitcher(DataSourceEnum.SLAVE)
	public Integer slave() {
		return userMapper.xxcount();
	}
}