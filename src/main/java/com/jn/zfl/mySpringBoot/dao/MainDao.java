package com.jn.zfl.mySpringBoot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jn.zfl.mySpringBoot.bean.Dept;
import com.jn.zfl.mySpringBoot.bean.User;

/*@Mapper*/
public interface MainDao {

	/*@Select("select * from xxcl_user where id = #{user_id}")*/
	User getUserById(@Param("user_id") Integer id);
	
	int queryUserCount(@Param(value = "tj") String tj);
	
	List<User> querySome(@Param(value="start") int start,@Param(value="end") int end);
	
	List<Dept> findDept(); 
}
