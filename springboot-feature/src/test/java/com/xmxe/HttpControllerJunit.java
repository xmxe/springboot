package com.xmxe;

import com.xmxe.config.http.HttpTemplate;
import com.xmxe.config.http.boot3.UserClient;
import com.xmxe.entity.RequestBean;
import com.xmxe.entity.ResponseBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpControllerJunit {

	@Autowired
	private HttpTemplate httpTemplate;

	@Autowired
	private UserClient userClient;

	/**
	 * get请求测试
	 */
	@Test
	public void testGet(){
		//请求地址
		String url = "http://localhost:8080/testGet";

		//发起请求,直接返回对象
		ResponseBean responseBean = httpTemplate.get(url, createHeader("get"), ResponseBean.class);
		System.out.println(responseBean.toString());
	}

	/**
	 * get请求测试，restful风格
	 */
	@Test
	public void testGetByRestFul(){
		//请求地址
		String url = "http://localhost:8080/testGetByRestFul/{1}";

		//发起请求,直接返回对象（restful风格）
		ResponseBean responseBean = httpTemplate.get(url, createHeader("testGetByRestFul"), ResponseBean.class, "张三");
		System.out.println(responseBean.toString());
	}


	/**
	 * 模拟表单提交，post请求
	 */
	@Test
	public void testPostByForm(){
		//请求地址
		String url = "http://localhost:8080/testPostByFormAndObj";

		//表单参数
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", "唐三藏");
		paramMap.put("userPwd", "123456");

		//发起请求
		ResponseBean responseBean = httpTemplate.postByFrom(url, createHeader("testPostByFormAndObj"), paramMap, ResponseBean.class);
		System.out.println(responseBean.toString());
	}

	/**
	 * 模拟JSON提交，post请求
	 */
	@Test
	public void testPostByJson(){
		//请求地址
		String url = "http://localhost:8080/testPostByJson";

		//入参
		RequestBean request = new RequestBean();
		request.setUserName("唐三藏");
		request.setUserPwd("123456789");

		//发送post请求，并打印结果，以String类型接收响应结果JSON字符串
		ResponseBean responseBean = httpTemplate.postByJson(url, createHeader("testPostByJson"), request, ResponseBean.class);
		System.out.println(responseBean.toString());
	}

	/**
	 * 重定向，post请求，json方式提交
	 */
	@Test
	public void testPostByLocation(){
		//请求地址
		String url = "http://localhost:8080/testPostByLocation";

		//入参
		RequestBean request = new RequestBean();
		request.setUserName("唐三藏");
		request.setUserPwd("123456789");

		//用于提交完成数据之后的页面跳转
		String uri = httpTemplate.postForLocation(url,  createHeader("testPostByLocation"), request);
		System.out.println(uri);
	}


	/**
	 * put请求，json方式提交
	 */
	@Test
	public void testPutByJson(){
		//请求地址
		String url = "http://localhost:8080/testPutByJson";

		//入参
		RequestBean request = new RequestBean();
		request.setUserName("唐三藏");
		request.setUserPwd("123456789000");

		//模拟JSON提交，put请求
		ResponseBean responseBean = httpTemplate.put(url,  createHeader("testPutByJson"), request, ResponseBean.class);
		System.out.println(responseBean.toString());
	}

	/**
	 * delete请求，json方式提交
	 */
	@Test
	public void testDeleteByJson(){
		//请求地址
		String url = "http://localhost:8080/testDeleteByJson";

		//模拟JSON提交，delete请求
		ResponseBean responseBean = httpTemplate.delete(url,  createHeader("testDeleteByJson"), ResponseBean.class);
		System.out.println(responseBean.toString());
	}

	/**
	 * 文件上传，post请求
	 */
	@Test
	public void uploadFile(){
		//需要上传的文件
		String filePath = "/Users/panzhi/Desktop/Jietu20220205-194655.jpg";

		//请求地址
		String url = "http://localhost:8080/upload";

		//提交参数设置
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("uploadFile", new FileSystemResource(new File(filePath)));
		//服务端如果接受额外参数，可以传递
		param.add("userName", "张三");

		ResponseBean responseBean = httpTemplate.uploadFile(url, createHeader("uploadFile"), param, ResponseBean.class);
		System.out.println(responseBean.toString());
	}

	/**
	 * 小文件下载
	 * @throws IOException
	 */
	@Test
	public void downloadFile() throws IOException {
		String userName = "张三";
		String fileName = "f9057640-90b2-4f86-9a4b-72ad0e253d0d.jpg";
		//请求地址
		String url = "http://localhost:8080/downloadFile/{1}/{2}";

		//发起请求,直接返回对象（restful风格）
		byte[] stream = httpTemplate.downloadFile(url, createHeader("downloadFile"), userName,fileName);

		//将下载下来的文件内容保存到本地
		String targetPath = "/Users/panzhi/Desktop/"  + fileName;
		Files.write(Paths.get(targetPath), Objects.requireNonNull(stream, "未获取到下载文件"));
	}

	/**
	 * 大文件下载
	 * @throws IOException
	 */
	@Test
	public void downloadBigFile() {
		String userName = "张三";
		String fileName = "f9057640-90b2-4f86-9a4b-72ad0e253d0d.jpg";
		String targetPath = "/Users/panzhi/Desktop/"  + fileName;

		//请求地址
		String url = "http://localhost:8080/downloadFile/{1}/{2}";

		//对响应进行流式处理而不是将其全部加载到内存中
		httpTemplate.downloadBigFile(url, createHeader("downloadBigFile"), clientHttpResponse -> {
			Files.copy(clientHttpResponse.getBody(), Paths.get(targetPath));
			return null;
		}, userName, fileName);
	}


	/**
	 * 自定义请求头部
	 * @param value
	 * @return
	 */
	private Map<String, String> createHeader(String value){
		Map<String, String> headers = new HashMap<>();
		headers.put("token", value);
		return headers;
	}

	/**
	 * 模拟JSON提交，post请求，范型返回对象测试
	 */
	@Test
	public void testPostByJsonObj(){
		//请求地址
		String url = "http://localhost:8080/testPostByJsonObj";

		//入参
		RequestBean request = new RequestBean();
		request.setUserName("唐三藏");
		request.setUserPwd("123456789");

		//发送post请求
		// ParameterizedTypeReference<ResponseBeanObj<ResponseBean>> typeRef = new ParameterizedTypeReference<ResponseBeanObj<ResponseBean>>() {};
		// //范型测试
		// ResponseBeanObj<ResponseBean> responseBean = httpTemplate.postByJson(url, createHeader("testPostByJsonObj"), request, typeRef);
		// System.out.println(JSON.toJSONString(responseBean));
	}

	@Test
	public void boot3HttpClient(){
		//Get All Users
		userClient.getUserById("1","1").subscribe(
				data -> System.out.println("User:" + data)
		);

		/*
		 * 报错 executor not accepting a task
		 * 单元测试时主线程调用新线程执行任务，调用完主线程就关闭了，此时spring生命周期也结束了。而此时子线程任务可能没执行完，可能需要spring容器提供的bean或连接（如mongoTemplate，redisTemplate），但是主线程已经退出了。
		 * 解决办法就是不让主线程提前退出
		 */
		try {
			TimeUnit.SECONDS.sleep(3L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		//Get User By Id
		// userClient.getById(1L).subscribe(
		// 		data -> System.out.println("User:" + data)
		// );
		// //Create a New User
		// userClient.save(new User(1, "Lokesh", "lokesh"))
		// 		.subscribe(
		// 				data -> System.out.println("User:" + data)
		// 		);
		// //Delete User By Id
		// userClient.delete(1L).subscribe(
		// 		data -> System.out.println("User:" + data)
		// );
	}
}