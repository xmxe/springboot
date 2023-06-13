package com.xmxe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_order")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "order_id")
	private Long orderId;

	@TableField("user_id")
	private Long userId;

	@TableField("order_code")
	private String orderCode;

	@TableField("address_id")
	private String addressId;

}