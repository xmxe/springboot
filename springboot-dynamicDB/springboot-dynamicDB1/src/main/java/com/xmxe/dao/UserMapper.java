package com.xmxe.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
	@Select("select count(*) from user")
	Integer count();

	@Select("select count(*) from xxcl_user")
	Integer xxcount();
}