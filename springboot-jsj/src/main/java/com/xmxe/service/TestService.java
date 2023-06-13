package com.xmxe.service;

import com.xmxe.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService {

	@Autowired
	TestMapper mapper;

	public void db(){
		List<Map<String, Object>> db = mapper.db();
		System.out.println(db);
	}
}