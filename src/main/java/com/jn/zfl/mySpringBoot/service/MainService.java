package com.jn.zfl.mySpringBoot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jn.zfl.mySpringBoot.bean.Dept;
import com.jn.zfl.mySpringBoot.bean.User;
import com.jn.zfl.mySpringBoot.dao.MainDao;


@Service
public class MainService {

	@Autowired
	MainDao mainDao;
	
	public User getUserById(Integer userId) {
		return mainDao.getUserById(userId);
	}
	
	public int queryUserCount(String tj) {
		return mainDao.queryUserCount(tj);
	}
	
	public List<User> querySome(int start,int end){
		return mainDao.querySome(start, end);
	}
	
	public List<Dept> findDept() {
		// TODO Auto-generated method stub
		return mainDao.findDept();
	}
}
