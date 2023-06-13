package com.xmxe.config.main;

import com.xmxe.entity.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 自定义校验器
 */
public class CustomValidator implements Validator {

	/**
	 * 判断校验类
	 */
	@Override
	public boolean supports(Class<?> clazz) {
//		父类.class.isAssignableFrom(子类.class),子类实例instanceof父类类型
		return User.class.isAssignableFrom(clazz);
	}

	/**
	 * 校验方式
	 */
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;
		// 方式1
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","","username is required");
		// 方式2
		if (user.getPassword().length() < 8){
			errors.rejectValue("password","possword length < 8");
		}
	}
}