package com.xmxe.service;

import com.xmxe.anno.DataSource;
import com.xmxe.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;

	@DataSource("master")//指定数据源
	public Integer master() {
		return userMapper.count();
	}

	@DataSource("slave")
	public Integer slave() {
		return userMapper.xxcount();
	}
}