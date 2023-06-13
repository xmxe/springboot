package com.xmxe.config.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Component
public class HttpTemplate {

	private static final Logger log = LoggerFactory.getLogger(HttpTemplate.class);

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * get请求，返回响应实体（响应业务对象不支持范型）
	 * 支持restful风格
	 */
	public <T> T get(String url, Map<String, String> headers, Class<T> responseType, Object... uriVariables){
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(headers)), responseType, uriVariables);
		return buildResponse(rsp);
	}


	/**
	 * get请求，返回响应实体（响应业务对象支持范型）
	 * 支持restful风格
	 */
	public <T> T get(String url, Map<String, String> headers, ParameterizedTypeReference<T> responseType, Object... uriVariables){
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(headers)), responseType, uriVariables);
		return buildResponse(rsp);
	}

	/**
	 * post请求，form表单提交（响应业务对象不支持范型）
	 * 支持restful风格
	 */
	public <T> T postByFrom(String url, Map<String, String> headers, Map<String, Object> paramMap, Class<T> responseType, Object... uriVariables){
		//指定请求头为表单类型
		HttpHeaders httpHeaders = createHeaders(headers);
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(createBody(paramMap), httpHeaders), responseType, uriVariables);
		return buildResponse(rsp);
	}

	/**
	 * post请求，form表单提交（响应业务对象支持范型）
	 * 支持restful风格
	 */
	public <T> T postByFrom(String url, Map<String, String> headers, Map<String, Object> paramMap, ParameterizedTypeReference<T> responseType, Object... uriVariables){
		//指定请求头为表单类型
		HttpHeaders httpHeaders = createHeaders(headers);
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(createBody(paramMap), httpHeaders), responseType, uriVariables);
		return buildResponse(rsp);
	}


	/**
	 * post请求，json提交（响应业务对象不支持范型）
	 * 支持restful风格
	 */
	public <T> T postByJson(String url, Map<String, String> headers, Object request, Class<T> responseType, Object... uriVariables){
		//指定请求头为json类型
		HttpHeaders httpHeaders = createHeaders(headers);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(request, httpHeaders), responseType, uriVariables);
		return buildResponse(rsp);
	}

	/**
	 * post请求，json提交（响应业务对象支持范型）
	 * 支持restful风格
	 */
	public <T> T postByJson(String url, Map<String, String> headers, Object request, ParameterizedTypeReference<T> responseType, Object... uriVariables){
		//指定请求头为json类型
		HttpHeaders httpHeaders = createHeaders(headers);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(request, httpHeaders), responseType, uriVariables);
		return buildResponse(rsp);
	}

	/**
	 * post请求，json提交，重定项
	 * 支持restful风格
	 */
	public String postForLocation(String url, Map<String, String> headers, Object request, Object... uriVariables){
		//指定请求头为json类型
		HttpHeaders httpHeaders = createHeaders(headers);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		URI uri = restTemplate.postForLocation(url, new HttpEntity<>(request, httpHeaders), uriVariables);
		if(Objects.nonNull(uri)){
			return uri.toString();
		}
		return null;
	}

	/**
	 * put请求，json提交（响应业务对象不支持范型）
	 */
	public <T> T put(String url, Map<String, String> headers, Object request, Class<T> responseType, Object... uriVariables){
		//指定请求头为json类型
		HttpHeaders httpHeaders = createHeaders(headers);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.PUT, new HttpEntity<>(request, httpHeaders), responseType, uriVariables);
		return buildResponse(rsp);
	}

	/**
	 * put请求，json提交（响应业务对象支持范型）
	 */
	public <T> T put(String url, Map<String, String> headers, Object request, ParameterizedTypeReference<T> responseType, Object... uriVariables){
		//指定请求头为json类型
		HttpHeaders httpHeaders = createHeaders(headers);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.PUT, new HttpEntity<>(request, httpHeaders), responseType, uriVariables);
		return buildResponse(rsp);
	}


	/**
	 * delete请求（响应业务对象不支持范型）
	 */
	public <T> T delete(String url, Map<String, String> headers, Class<T> responseType, Object... uriVariables){
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.DELETE, new HttpEntity<>(createHeaders(headers)), responseType, uriVariables);
		return buildResponse(rsp);
	}

	/**
	 * delete请求（响应业务对象支持范型）
	 */
	public <T> T delete(String url, Map<String, String> headers, ParameterizedTypeReference<T> responseType, Object... uriVariables){
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.DELETE, new HttpEntity<>(createHeaders(headers)), responseType, uriVariables);
		return buildResponse(rsp);
	}


	/**
	 * post请求，文件表单上传提交（响应业务对象不支持范型）
	 * 支持restful风格
	 */
	public <T> T uploadFile(String url, Map<String, String> headers, MultiValueMap<String, Object> paramMap, Class<T> responseType, Object... uriVariables){
		//指定请求头为文件&表单类型
		HttpHeaders httpHeaders = createHeaders(headers);
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(paramMap, httpHeaders), responseType, uriVariables);
		return buildResponse(rsp);
	}


	/**
	 * post请求，文件表单上传提交（响应业务对象支持范型）
	 * 支持restful风格
	 */
	public <T> T uploadFile(String url, Map<String, String> headers, MultiValueMap<String, Object> paramMap, ParameterizedTypeReference<T> responseType, Object... uriVariables){
		//指定请求头为文件&表单类型
		HttpHeaders httpHeaders = createHeaders(headers);
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(paramMap, httpHeaders), responseType, uriVariables);
		return buildResponse(rsp);
	}


	/**
	 * 下载文件
	 */
	public byte[] downloadFile(String url, Map<String, String> headers, Object... uriVariables){
		ResponseEntity<byte[]> rsp = commonExchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(headers)), byte[].class, uriVariables);
		return buildResponse(rsp);
	}


	/**
	 * 下载大文件
	 */
	public void downloadBigFile(String url, Map<String, String> headers, ResponseExtractor responseExtractor, Object... uriVariables){
		RequestCallback requestCallback = request -> {
			// 指定请求头信息
			request.getHeaders().addAll(createHeaders(headers));
			// 定义请求头的接收类型
			request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
		};
		restTemplate.execute(url, HttpMethod.GET, requestCallback,responseExtractor, uriVariables);
	}


	/**
	 * 公共http请求方法（响应业务对象不支持范型）
	 */
	public <T> ResponseEntity<T> commonExchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables){
		return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
	}


	/**
	 * 公共http请求方法（响应业务对象支持范型）
	 */
	public <T> ResponseEntity<T> commonExchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables){
		return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
	}



	/**
	 * 封装头部参数
	 */
	private HttpHeaders createHeaders(Map<String, String> headers){
		return new HttpHeaders(){{
			if(headers != null && !headers.isEmpty()){
				headers.entrySet().forEach(item -> {
					set(item.getKey(), item.getValue());
				});
			}
		}};
	}


	/**
	 * 封装请求体
	 */
	private MultiValueMap<String, Object> createBody(Map<String, Object> paramMap){
		MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
		if(paramMap != null && !paramMap.isEmpty()){
			paramMap.entrySet().forEach(item -> {
				valueMap.add(item.getKey(), item.getValue());
			});
		}
		return valueMap;
	}

	/**
	 * 返回响应对象
	 */
	private <T> T buildResponse(ResponseEntity<T> rsp){
		if(!rsp.getStatusCode().is2xxSuccessful()){
			throw new RuntimeException(rsp.getStatusCode().toString());
		}
		return rsp.getBody();
	}
}