package com.jn.zfl.mySpringBoot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jn.zfl.mySpringBoot.bean.User;
import com.jn.zfl.mySpringBoot.service.UserService;

//@RestController//@Controller+@ResponseBody
@Controller
public class UserController {	
	Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	UserService userservice;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	@RequestMapping("/getUserById")
	public JSONObject getUserById(@RequestParam("userId") String userId) {
		User user = userservice.getUserById(Integer.valueOf(userId));
		JSONObject json = new JSONObject();
		json.put("user",user);
		return json;
	}
	
	@RequestMapping("upload")
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request instanceof MultipartHttpServletRequest) {
				MultipartFile file =  ((MultipartHttpServletRequest) request).getFile("fileName");//文件名
				if(file != null) {
					SimpleDateFormat sff = new SimpleDateFormat("yyyyMMdd");//20180101
					String today = sff.format(new Date());
					File f = new File("e://"+today);
					if(!f.exists()) {f.mkdirs();}
					String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());//自定义文件名
					/*①							
					file.transferTo(new File(f,fileName.replace("-", "")));
					*/			
					 /*②
					 FileUtils.writeByteArrayToFile(new File(f,fileName.replace("-", "")), file.getBytes());
					 */
					InputStream is = (FileInputStream) file.getInputStream();
					OutputStream os = new FileOutputStream(new File(f,fileName.replace("-", "")));
					byte[] data = new byte[1024];
					int len;
					while((len = is.read(data)) != -1) {
						os.write(data, 0, len);
					}
					is.close();
					os.flush();
					os.close();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
