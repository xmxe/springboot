package com.xmxe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回数据结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
	private Integer code;
	private String message;
	private T data;

	public static <T> Result<T> success(T data) {
		return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
	}

	public static <T> Result<T> success(String message, T data) {
		return new Result<>(ResultEnum.SUCCESS.getCode(), message, data);
	}

	public static Result<?> failed() {
		return new Result<>(ResultEnum.COMMON_FAILED.getCode(), ResultEnum.COMMON_FAILED.getMessage(), null);
	}

	public static Result<?> failed(String message) {
		return new Result<>(ResultEnum.COMMON_FAILED.getCode(), message, null);
	}

	public static Result<?> failed(ResultEnum errorResult) {
		return new Result<>(errorResult.getCode(), errorResult.getMessage(), null);
	}

	public static <T> Result<T> instance(Integer code, String message, T data) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setMessage(message);
		result.setData(data);
		return result;
	}

	/**
	 * 常用结果的枚举
	 */
	public enum ResultEnum {
		SUCCESS(200, "接口调用成功"),
		VALIDATE_FAILED(4001, "参数校验失败"),
		COMMON_FAILED(5001, "接口调用失败"),
		FORBIDDEN(4002, "没有权限访问资源");

		ResultEnum(Integer code, String message){
			this.code = code;
			this.message = message;
		}
		private Integer code;
		private String message;

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}