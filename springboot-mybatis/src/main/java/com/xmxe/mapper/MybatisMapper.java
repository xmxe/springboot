package com.xmxe.mapper;

import com.xmxe.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MybatisMapper {
	@Select("select * from user where id = #{user_id}")
	User getUserById(Integer id);

	void insertList(List<Integer> data);

	void updateBatch(List<Integer> data);
}