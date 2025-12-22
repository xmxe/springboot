package com.xmxe.service;

import com.xmxe.call.SerialBiFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class UserService {
	public String queryUser(Long userId){
		return String.valueOf(userId);
	}

	public static SerialBiFunction<UserService, Map<String,Object>,Object> updateUser(Long userId){
		return (UserService service, Map<String,Object> map) -> {
			log.info("userId->{},map->{}",userId,map);
			return map;
		};
	}
}