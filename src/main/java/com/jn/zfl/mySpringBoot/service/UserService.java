package com.jn.zfl.mySpringBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jn.zfl.mySpringBoot.bean.User;
import com.jn.zfl.mySpringBoot.dao.UserDao;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	public User getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}
}
