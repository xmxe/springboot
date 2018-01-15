package com.jn.zfl.mySpringBoot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jn.zfl.mySpringBoot.bean.User;

/*@Mapper*/
public interface UserDao {

	/*@Select("select * from xxcl_user where id = #{user_id}")*/
	User getUserById(@Param("user_id") Integer id);
}
