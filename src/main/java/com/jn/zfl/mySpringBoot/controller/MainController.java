package com.jn.zfl.mySpringBoot.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jn.zfl.mySpringBoot.bean.User;
import com.jn.zfl.mySpringBoot.config.redis.RedisUtils;
import com.jn.zfl.mySpringBoot.service.LambdaService;
import com.jn.zfl.mySpringBoot.service.MainService;
import com.jn.zfl.mySpringBoot.util.HttpClientUtil;
import com.jn.zfl.mySpringBoot.util.Page;
import com.alibaba.fastjson.JSONObject;
import com.jn.zfl.mySpringBoot.bean.Dept;
import com.jn.zfl.mySpringBoot.bean.HttpResult;


//@RestController//@Controller+@ResponseBody
@Controller
@RequestMapping("**.do")
public class MainController {	
	Logger logger = Logger.getLogger(MainController.class);
	@Autowired
	MainService mainservice;

	@Autowired
    private RedisUtils redisUtils;
	
	@Resource(name = "jobDetail")  
    private JobDetail jobDetail;

    @Resource(name = "jobTrigger")  
    private CronTrigger cronTrigger;  

    @Resource(name = "scheduler")  
    private Scheduler scheduler;   
    
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
	@ResponseBody
	public JSONObject getUserById(@RequestParam("username") String userId,@RequestParam("userId") String username) {
		User user = mainservice.getUserById(Integer.valueOf(username));
		JSONObject json = new JSONObject();
		json.put("user",user);
		System.err.println(userId);
		System.err.println(username);
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
	
	@RequestMapping(value="/code")
	public void generate(HttpServletRequest request,HttpServletResponse response){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String code = drawImg(output);
		request.getSession().setAttribute("code", code);	
		try {
			ServletOutputStream out = response.getOutputStream();
			output.writeTo(out);
			out.close();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	private String drawImg(ByteArrayOutputStream output){
		String code = "";
		for(int i=0; i < 4; i++){
			code += randomChar();
		}
		int width = 70;
		int height = 25;
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		Font font = new Font("Times New Roman",Font.PLAIN,20);
		Graphics2D g = bi.createGraphics();
		g.setFont(font);
		Color color = new Color(66,2,82);
		g.setColor(color);
		g.setBackground(new Color(226,226,240));
		g.clearRect(0, 0, width, height);
		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(code, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		g.drawString(code, (int)x, (int)baseY);
		g.dispose();
		try {
			ImageIO.write(bi, "jpg", output);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return code;
	}
	
	private char randomChar(){
		Random r = new Random();
		String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
		return s.charAt(r.nextInt(s.length()));
	}
	
	@RequestMapping(value="/check")
	@ResponseBody
	public JSONObject index(HttpServletRequest request) {
		String code = request.getParameter("code");
		String name =request.getParameter("username");
		String password = request.getParameter("password");
		String codeSession = (String) request.getSession().getAttribute("code");
		JSONObject json = new JSONObject();
		Map<String,String> map = new HashMap<>();
		map.put("username", "1");map.put("password", "1");
		request.getSession().setAttribute("user",map);
		if(code == null || "".equals(code)) {
			json.put("message", "请输入验证码");
			return json;
		}
		if(code.equalsIgnoreCase(codeSession)) {
			if(!("1").equals(name) || !("1").equals(password)) {
				json.put("message", "用户名或密码不正确");
			}else {
				json.put("message", "success");
			}		
		}else {
			json.put("message", "验证码不正确");
		}
		
		return json;
	} 
	
	@RequestMapping(value="/excell")
	@ResponseBody
	public void excel(HttpServletRequest request,HttpServletResponse response){
		String[] handers = {"id","name","sex","mobile"};
		List<User> list = mainservice.querySome(1,5);	
		try{
			//由浏览器指定下载路径
			response.reset();
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");
			response.setHeader("Content-dispostion","attachment;filename=test.xls");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			String filedisplay = "test.xls";
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
			HSSFWorkbook wb = new HSSFWorkbook();//创建工作簿
			HSSFSheet sheet = wb.createSheet("操作");//第一个sheet
			HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
			rowFirst.setHeight((short) 500);			   
            HSSFCellStyle cellStyle = wb.createCellStyle();// 创建单元格样式对象  
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			for (int i = 0; i < handers.length; i++) {
			   sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
			}
			//写标题了
			 for (int i = 0; i < handers.length; i++) {
				 //获取第一行的每一个单元格
				 HSSFCell cell = rowFirst.createCell(i);
				 //往单元格里面写入值
				 cell.setCellValue(handers[i]);
				 cell.setCellStyle(cellStyle);
			 }
			for (int i = 0; i < list.size(); i++) {
				User u = list.get(i);			
				//创建数据行
				HSSFRow row = sheet.createRow(i + 1);				
				row.setHeight((short) 400);   // 设置每行的高度
				//设置对应单元格的值
				row.createCell(0).setCellValue(u.getId());
				row.createCell(1).setCellValue(u.getUsername());
				row.createCell(2).setCellValue(u.getSex());
				row.createCell(3).setCellValue(u.getMobile());						
			}
			OutputStream os = response.getOutputStream();  
            wb.write(os);
            os.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/redis")
	@ResponseBody
    public String redis(){
        redisUtils.set("123", "hello world");
        System.err.println("进入了方法");
        String string= redisUtils.get("123").toString();
        return string;
    }
	
	@ResponseBody
    @RequestMapping("/quartz")
    public String quartzTest() throws SchedulerException{
         CronTrigger trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());  
         String currentCron = trigger.getCronExpression();// 当前Trigger使用的  
         System.err.println("当前trigger使用的-"+currentCron);
         //1秒钟执行一次
         CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");  
         // 按新的cronExpression表达式重新构建trigger  
         trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());  
         trigger = trigger.getTriggerBuilder().withIdentity(cronTrigger.getKey())  
                 .withSchedule(scheduleBuilder).build();  
         // 按新的trigger重新设置job执行  
         scheduler.rescheduleJob(cronTrigger.getKey(), trigger);  
        return "-这是quartz测试";
    }
	
	@ResponseBody
    @RequestMapping("/httpclient")
	public String httpclient() throws Exception {
		String url = "http://localhost:8080/zhongzhu/appnewhouse/newhouseLine.do";
		Map<String,Object> map = new HashMap<>();
		map.put("Id","6b340d4c17ed47449409f35a709ca298");//追加参数
		HttpClientUtil client = new HttpClientUtil();
		HttpResult result = client.doGet(url,map);
		//System.err.println(result.getBody());
		return result.getBody();
	}
				
	public static void main(String[] args) {
		LambdaService lambdaservice = (a,b)->{return a + b;};//相当于LambdaService的实现类
		int c = lambdaservice.lambdaTest(3,4);
		System.out.println(c);
		Set<Integer> set = new TreeSet<>();
		Collections.addAll(set, 22,3,51,44,20,6);
		set.stream().filter(x -> x>30).forEach(System.out::println);
		
		Set<Integer> set1 = new TreeSet<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer i,Integer o) {
				return i - o;
			}
		});
		Collections.addAll(set1, 22,3,51,44,20,6);
		set1.forEach(System.out::println);
	}
}
