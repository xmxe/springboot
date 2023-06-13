package com.xmxe.config.http.boot3;

import com.alibaba.fastjson2.JSONObject;
import com.xmxe.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * url为/user的话就请求http://localhost:8080/user, 为'/'的时候就请求http://localhost:8080/
 * 如果在WebClient.java里配置baseUrl的时候地址为http://localhost:8080/ 那么为'/'的时候就请求http://localhost:8080// 多了个'/'
 * 这时候请求getUserById路径就会变成http://localhost:8080///getUserById会报错 这里要注意下
 */
@HttpExchange(url = "", accept = "application/json", contentType = "application/json")
public interface UserClient {
	@GetExchange("/")
	Flux<User> getAll();

	@GetExchange("/getUserById")
	Flux<JSONObject> getUserById(@RequestParam String userId, @RequestParam String username);

	@GetExchange("/{id}")
	Mono<User> getById(@PathVariable("id") Long id);

	@PostExchange("/")
	Mono<ResponseEntity<Void>> save(@RequestBody User user);

	@PutExchange("/{id}")
	Mono<ResponseEntity<Void>> update(@PathVariable Long id, @RequestBody User user);

	@DeleteExchange("/{id}")
	Mono<ResponseEntity<Void>> delete(@PathVariable Long id);
}