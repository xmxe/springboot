package com.xmxe.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	Integer count();

	Integer xxcount();
}