package com.mySpringBoot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mySpringBoot.dao.MainDao;
import com.mySpringBoot.entity.Book;
import com.mySpringBoot.entity.Dept;
import com.mySpringBoot.entity.User;


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
	
	public List<Book> querySome(String tj,int start,int end){
		return mainDao.querySome(tj,start, end);
	}
	
	public List<Dept> findDept() {
		return mainDao.findDept();
	}
}
