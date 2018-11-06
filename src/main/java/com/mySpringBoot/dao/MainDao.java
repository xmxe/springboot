package com.mySpringBoot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mySpringBoot.entity.Book;
import com.mySpringBoot.entity.Dept;
import com.mySpringBoot.entity.User;

/*@Mapper*/
public interface MainDao {

	/*@Select("select * from xxcl_user where id = #{user_id}")*/
	User getUserById(@Param("user_id") Integer id);
	
	int queryUserCount(@Param(value = "tj") String tj);
	
	List<Book> querySome(@Param(value="tj")String tj, @Param(value="start") int start,@Param(value="end") int end);
	
	List<Dept> findDept(); 
}
