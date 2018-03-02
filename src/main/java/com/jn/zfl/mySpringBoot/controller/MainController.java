package com.jn.zfl.mySpringBoot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jn.zfl.mySpringBoot.bean.User;
import com.jn.zfl.mySpringBoot.service.MainService;
import com.jn.zfl.mySpringBoot.util.Page;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jn.zfl.mySpringBoot.bean.Dept;


//@RestController//@Controller+@ResponseBody
@Controller
@RequestMapping("**.do")
public class MainController {	
	Logger logger = Logger.getLogger(MainController.class);
	@Autowired
	MainService mainservice;

	//分页页面
	@RequestMapping("/pageView")
	public String pageView() {
		return "content/page";
	}
	
	//ztree页面
	@RequestMapping("/ztreeView")
	public String ztreeView() {
		return "content/ztree";
	}
	
	//form
	@RequestMapping("/form")
	public String form() {
		return "content/form";
	}
		
	
	@RequestMapping("/getUserById")
	public JSONObject getUserById(@RequestParam("userId") String userId) {
		User user = mainservice.getUserById(Integer.valueOf(userId));
		JSONObject json = new JSONObject();
		json.put("user",user);
		return json;
	}
	
	//上传
	@RequestMapping("/upload")
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request instanceof MultipartHttpServletRequest) {
				MultipartFile file =  ((MultipartHttpServletRequest) request).getFile("fileName");//文件名
				if(file != null) {
					SimpleDateFormat sff = new SimpleDateFormat("yyyyMMdd");//20180101
					String today = sff.format(new Date());
					File f = new File(request.getSession().getServletContext().getRealPath(File.separator) + today);//tomcat webapps路径+项目名 例:E:\apache-tomcat-7.0.82\webapps\club
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
	//分页后台逻辑
	@RequestMapping("/page")
	public void page(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获得当前页参数
		request.setCharacterEncoding("UTF-8");
		String tj = request.getParameter("tj");
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Page<User> page = new Page<User>();
		//设置当前页 currentPage next pre  pageSize  start
		page.setCurrentPage(currentPage);
		//2.查询总记录数 total   pageCount
		int total = mainservice.queryUserCount(tj);
		
		page.setTotal(total);
		//3.查询数据 rows
		List<User> rows = mainservice.querySome(page.getStart(),page.getPageSize());
		page.setRows(rows);
		//~~~~~~~~~~组装page对象 完毕 写出到客户端
		response.setContentType("text/plain;charset=UTF-8");
		//JSONObject jo = JSONObject.fromObject(page);
		JSONObject jo = new JSONObject();
		jo.put("jo", page);
		PrintWriter out = response.getWriter();
		out.print(jo);
		out.flush();
		out.close();
	}
	
	//ztree
	@RequestMapping(value="/dept/aJsonObject",method = RequestMethod.POST)
	@ResponseBody
	public  JSONObject aJsonObject(HttpServletRequest request) {
		List<Dept> depts = mainservice.findDept();
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> map = new HashMap<String,Object>();
		for (int i = 0; i < depts.size(); i++) {
			Map<String,Object> newMap = new HashMap<String,Object>();
			newMap.put("id",depts.get(i).getId());
			newMap.put("pId", depts.get(i).getFather_id());
			newMap.put("name", depts.get(i).getDept_name());
			newMap.put("sort", depts.get(i).getSort());
			newMap.put("dept_level", depts.get(i).getDept_level());
			if (depts.get(i).getDept_level() == 1) {
				newMap.put("open", true);
				newMap.put("iconSkin", "pIcon01");
			}else if (depts.get(i).getDept_level() == 2) {
				newMap.put("iconSkin", "icon02");
			}else {
				newMap.put("iconSkin", "icon03");
			}
			list.add(newMap);
		}
		map.put("zNodes", list);
		//JSONObject json = JSONObject.parseObject(JSON.toJSONString(map));
		JSONObject json = (JSONObject) JSONObject.toJSON(map);
		return json;
	}
		
}
