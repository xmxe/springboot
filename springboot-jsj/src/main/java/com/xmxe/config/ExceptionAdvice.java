package com.xmxe.config;

import com.xmxe.entity.Result;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.xmxe")
public class ExceptionAdvice {

	/**
	 * 顶级异常捕获并统一处理，当其他异常无法处理时候选择使用
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)// 当500-异常发生时 返回前端的状态码就不是200而是500了
	public Result<?> excetionHandler(Exception e){
		return Result.failed(e.getMessage());
	}

	/**
	 * 捕获NullPointerException异常
	 */
	@ExceptionHandler({NullPointerException.class})
	public Result<?> nullpointerException(NullPointerException ex) {
		return Result.failed(ex.getMessage());
	}


	/**
	 * 参数校验不通过时抛出的异常处理
	 */
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		StringBuilder sb = new StringBuilder("校验失败:");
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
		}
		String msg = sb.toString();
		if (StringUtils.hasText(msg)) {
			return Result.failed(msg);
		}
		return Result.failed(Result.ResultEnum.VALIDATE_FAILED);
	}

}