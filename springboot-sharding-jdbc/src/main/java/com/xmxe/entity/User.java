package com.xmxe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class User {

	@TableId(value = "user_id")
	private Long userId;

	@TableField(value = "user_name")
	private String userName;
}
