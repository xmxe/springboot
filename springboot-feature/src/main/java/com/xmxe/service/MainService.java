package com.xmxe.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xmxe.entity.Book;
import com.xmxe.entity.Dept;
import com.xmxe.entity.Page;
import com.xmxe.entity.User;
import com.xmxe.mapper.master.MasterMapper;
import com.xmxe.mapper.slave.SlaveMapper;
import com.xmxe.util.FileConvertUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.*;

@Service
public class MainService {

	@Resource
	MasterMapper masterMapper;

	@Resource
	SlaveMapper slaveMapper;

	public User getUserById(Integer userId) {
		Map<String,Object> user = slaveMapper.getUserById(userId);
		System.out.println("slaveDB------"+user);
		return masterMapper.getUserById(userId);
	}

	/**
	 * 注解@Retryable
	 * value：抛出指定异常才会重试
	 * include：和value一样，默认为空，当exclude也为空时，默认所有异常
	 * exclude：指定不处理的异常
	 * maxAttempts：最大重试次数，默认3次
	 * backoff：重试等待策略，默认使用@Backoff，@Backoff的value默认为1000L，我们设置为2000L；multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试，如果把multiplier设置为1.5，则第一次重试为2秒，第二次为3秒，第三次为4.5秒
	 */
	@Retryable(value = Exception.class,maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 1.5))
	public void springRetry() throws Exception {
		throw new Exception("retry");
	}

	/**
	 * Recover注解，用于@Retryable重试失败后处理方法,如果不需要回调方法，可以直接不写回调方法，那么实现的效果是，重试次数完了后，如果还是没成功没符合业务判断，就抛出异常。
	 * 方法的返回值必须与@Retryable方法一致
	 * 方法的第一个参数，必须是Throwable类型的，建议是与@Retryable配置的异常一致，其他的参数，需要哪个参数，写进去就可以了（@Recover方法中有的）
	 * 该回调方法与重试方法写在同一个实现类里面
	 */
	@Recover
	public void springRetryCallBack(Exception e){
		e.printStackTrace();
		System.out.println("重试回调");
		/*
		 * 由于是基于AOP实现，所以不支持类里自调用方法
		 * 如果重试失败需要给@Recover注解的方法做后续处理，那这个重试的方法不能有返回值，只能是void
		 * 方法内不能使用try catch，只能往外抛异常
		 * @Recover注解来开启重试失败后调用的方法(注意,需跟重处理方法在同一个类中)，此注解注释的方法参数一定要是@Retryable抛出的异常，否则无法识别，可以在该方法中进行日志处理。
		 */
		/*由于retry用到了aspect增强，所有会有aspect的坑，就是方法内部调用，会使aspect增强失效，那么retry当然也会失效
		public class demo {
			public void A() {
				B();
			}
			//这里B不会执行
			@Retryable(Exception.class)
			public void B() {
				throw new RuntimeException("retry...");
			}
		}*/
	}

	public void page(HttpServletRequest request, HttpServletResponse response){
		try{
			//1.获得当前页参数
			request.setCharacterEncoding("UTF-8");
			String tj = request.getParameter("tj");
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			Page<Book> page = new Page<Book>();
			//设置当前页 currentPage next pre  pageSize  start
			page.setCurrentPage(currentPage);
			//2.查询总记录数 total   pageCount
			int total = masterMapper.queryUserCount(tj);
			page.setTotal(total);
			//PageHelper.startPage(2, 3);//分页插件
			//3.查询数据 rows
			List<Book> rows = masterMapper.querySome(tj,page.getStart(),page.getPageSize());
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
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public JSONObject aJsonObject(HttpServletRequest request) {
		List<Dept> depts = masterMapper.findDept();
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
		JSONObject json = JSONObject.parseObject(JSON.toJSONString(map));
		// JSONObject json = (JSONObject) JSONObject.toJSON(map);
		return json;
	}

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

	public char randomChar(){
		Random r = new Random();
		String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
		return s.charAt(r.nextInt(s.length()));
	}

	/**
	 * 系统文件在线预览接口
	 */
	public void onlinePreview(String url, HttpServletResponse response) throws Exception {
		//获取文件类型
//		String[] str = SmartStringUtil.split(url,"\\.");
		String[] str = url.split(".");

		if(str.length==0){
			throw new Exception("文件格式不正确");
		}
		String suffix = str[str.length-1];
		if(!suffix.equals("txt") && !suffix.equals("doc") && !suffix.equals("docx") && !suffix.equals("xls")
				&& !suffix.equals("xlsx") && !suffix.equals("ppt") && !suffix.equals("pptx")){
			throw new Exception("文件格式不支持预览");
		}
		InputStream in= FileConvertUtil.convertNetFile(url,suffix);
		OutputStream outputStream = response.getOutputStream();
		//创建存放文件内容的数组
		byte[] buff =new byte[1024];
		//所读取的内容使用n来接收
		int n;
		//当没有读取完时,继续读取,循环
		while((n=in.read(buff))!=-1){
			//将字节数组的数据全部写入到输出流中
			outputStream.write(buff,0,n);
		}
		//强制将缓存区的数据进行输出
		outputStream.flush();
		//关流
		outputStream.close();
		in.close();
	}

	/**
	 * cacheNames/value:用来指定缓存组件的名字
	 * key:缓存数据时使用的key，可以用它来指定。默认是使用方法参数的值。（这个key你可以使用spEL表达式来编写）
	 * keyGenerator:key的生成器。 key和keyGenerator二选一使用
	 * cacheManager:可以用来指定缓存管理器。从哪个缓存管理器里面获取缓存。
	 * cacheResolver: 缓存解析器。可以指定用哪个cacheManager
	 * condition:可以用来指定符合条件的情况下才缓存
	 * unless:否定缓存。当unless指定的条件为true ，方法的返回值就不会被缓存。当然你也可以获取到结果进行判断。（通过 #result 获取方法结果）
	 * sync:是否使用异步模式。
	 */
	@Cacheable()
	public String cache(Integer id){
		return "1";
	}
}