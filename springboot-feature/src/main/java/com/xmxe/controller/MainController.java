package com.xmxe.controller;

import com.alibaba.fastjson2.JSONObject;
import com.xmxe.anno.Decrypt;
import com.xmxe.anno.Encrypt;
import com.xmxe.config.main.CustomValidator;
import com.xmxe.config.main.StringToListPropertyEditor;
import com.xmxe.entity.Book;
import com.xmxe.entity.User;
import com.xmxe.service.MainService;
import com.xmxe.service.XmxeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Controller
@CrossOrigin(origins = "*")// 跨域注解
@Validated //加上这个注解校验才生效
public class MainController {

	// zookeeper分布式锁测试
	// @Autowired
	private ZookeeperLockRegistry zookeeperLockRegistry;

	@Resource
	ApplicationContext context;

	// 引入自定义spring-boot-starter
	XmxeService xmxeService;

	private MainService mainService;

	/**
	 * 构造器注入保证依赖不可变(final关键字),保证依赖不为空(省去了我们对其检查),保证返回客户端(调用)的代码的时候是完全初始化的状态,
	 * 避免了循环依赖,提升了代码的可复用性,推荐使用构造器注入，因为类在加载时先加载构造方法后加载@Autowired
	 * 不使用构造器注入如果想在构造方法里使用bean的某个方法 会出现NPE异常,因为bean还没有被加载
	 */
	@Autowired
	public MainController(XmxeService xmxeService){
		this.xmxeService = xmxeService;
	}

	/**
	 * 方法注入
	 */
	@Autowired
	public void methodInjection(MainService mainService){
		// 可以做一些初始化的工作
		// mainService.setA(2);
		// mainService.setB("qw");
		this.mainService = mainService;
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * 用户注销
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request, ModelMap mm) throws Exception{
		String msg = request.getParameter("msg");
		mm.addAttribute("msg", msg);
		return "login";
	}

	@RequestMapping({"/","/index"})
	public String index() {
		return "index";
	}

	// thymeleaf只能被控制器访问
	@RequestMapping("/pageView")
	public String pageView() {
		return "content/page";
	}

	@RequestMapping("/ztreeView")
	public String ztreeView() {
		return "content/ztree";
	}

	@RequestMapping("/form")
	public String form() {
		return "content/form";
	}

	@RequestMapping("/getUserById")
	@ResponseBody
	//@CrossOrigin(value = "http://localhost:8081") //指定具体ip允许跨域
	public JSONObject getUserById(@RequestParam(value = "username",required = false) String userId,
	                              @RequestParam(value = "userId",defaultValue="1") String username) {
		//如果加了@RequestParam注解，那么请求url里必须包含这一参数，否则会报400。那么如果允许不传呢？有两种办法：1.使用default值 2.使用required值
		User user = mainService.getUserById(Integer.valueOf(username));
		JSONObject json = new JSONObject();
		json.put("user",user);
		// 测试自定义的springboot-starter
		System.out.println(xmxeService.info());
		return json;
	}

	@GetMapping("/page")//相当于@RequestMapping(value="/page",method = RequestMethod.GET)
	public void page(HttpServletRequest request, HttpServletResponse response) {
		try{
			mainService.page(request,response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/dept/aJsonObject",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject ztree(HttpServletRequest request) {
		JSONObject json = null;
		try{
			json = mainService.aJsonObject(request);
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 验证码
	 */
	@RequestMapping(value="/code2")
	public void generate(HttpServletRequest request,HttpServletResponse response){
		try{
			mainService.generate(request,response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 使用工具类生成图片验证码
	 */
	@RequestMapping("/code")
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 不支持Spring Boot3 包名为javax.servlet
		// CaptchaUtil.out(request, response);
	}

	/**
	 * 系统文件在线预览接口
	 */
	@PostMapping("/api/file/onlinePreview")
	public void onlinePreview(@RequestParam("url") String url, HttpServletResponse response) throws Exception{
		mainService.onlinePreview(url,response);
	}

	/**
	 * Spring重试机制 调用Service是遇到异常自动重试
	 */
	@GetMapping("retry")
	@ResponseBody
	public void retry(){
		try{
			mainService.springRetry();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@GetMapping("/zookeeper")
	public void test2() {
		Lock lock = zookeeperLockRegistry.obtain("zookeeper");
		try{
			//尝试在指定时间内加锁，如果已经有其他锁锁住，获取当前线程不能加锁，则返回false，加锁失败；加锁成功则返回true
			if(lock.tryLock(3, TimeUnit.SECONDS)){
				System.out.println("lock is ready");
				TimeUnit.SECONDS.sleep(5);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 自定义的starter里面使用注解加密解密,在@ResponseBody返回前对数据加密
	 */
	@GetMapping("encry")
	@ResponseBody
	@Encrypt
	public String encry(){
		// 返回的不是asdad,而是加密后的"v0YvbVVNdcy2RbbdWZgSkw==",因为在@ResponseBody之前处理了
		return "asdad";
	}

	/**
	 * 前端发送的是密文"v0YvbVVNdcy2RbbdWZgSkw==",打印结果应该是“asdad”
	 */
	@GetMapping("decry")
	@ResponseBody
	public void decry(@RequestBody @Decrypt String str){
		System.out.println(str);
	}

	/**
	 * 自定义属性编辑器
	 * ip:port/binder?date=2020-12-12 10:54:45&text=11_22_33
	 */
	@GetMapping("binder")
	@ResponseBody
	public Object binder(Date date, String[] text){
		System.out.println("date="+date);
		System.out.println("array="+ Arrays.toString(text));
		return "binder";
	}

	/**
	 * InitBinder methods should return void
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		// 将传过来的参数转换为Date格式
		parseDate(webDataBinder);
		// 带"_"的String转数组
		string2array(webDataBinder);
	}

	/**
	 * 将传过来的参数转换为Date格式
	 */
	private void parseDate(WebDataBinder webDataBinder){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// allowEmpty = true允许date字段为null
		CustomDateEditor dateEditor = new CustomDateEditor(df, true);
		// 当参数需要是Date类型时使用CustomDateEditor
		webDataBinder.registerCustomEditor(Date.class, dateEditor);
		// 当参数需要是Date类型时,属性名是date时使用CustomDateEditor
		// webDataBinder.registerCustomEditor(Date.class, "date", dateEditor);
	}

	/**
	 * 注册属性编辑器,将参数带"_"的String转数组
	 */
	private void string2array(WebDataBinder webDataBinder){
		// 当参数是String[]类型时使用StringToListPropertyEditor
		webDataBinder.registerCustomEditor(String[].class, new StringToListPropertyEditor());
		// 当参数是String[]类型时,属性名是str时使用StringToListPropertyEditor
		// webDataBinder.registerCustomEditor(String[].class, "str",new StringToListPropertyEditor());
	}

	@InitBinder(value = {"abc","def"})// 若指定了value值，那么只有方法参数名（或者模型名）匹配上了此注解方法才会执行（若不指定，都执行）。
	public void initBinder_(WebDataBinder webDataBinder){
		webDataBinder.setValidator(new CustomValidator());
//		webDataBinder.addValidators();
		// 禁用属性
		webDataBinder.setDisallowedFields("password");
		// 在属性中去除pre_前缀 访问http://xxxx/a?pre_username=a User对象里填充username=a
		webDataBinder.setFieldDefaultPrefix("pre_");
		// 设置前缀 访问http://xxxx/a?pre_name=a 去掉pre_前缀,添加user前缀,User对象里就会填充成username
		// webDataBinder.setFieldMarkerPrefix("user");
//		webDataBinder.addCustomFormatter();

	}
	/*
	 * @InitBinder注解可以作用在被@Controller注解的类的方法上，表示为当前控制器注册一个属性编辑器，用于对WebDataBinder进行初始化，且只对当前的Controller有效。@InitBinder标注的方法会被多次执行的，也就是说来一次请求就会执行一次@InitBinder注解方法的内容。
	 * A. @InitBinder注解是在其所标注的方法执行之前被解析和执行；
	 * B. @InitBinder的value属性，控制的是模型Model里的KEY，而不是方法名；
	 * C. @InitBinder标注的方法也不能有返回值；
	 * D. @InitBinder对@RequestBody这种基于消息转换器的请求参数是失效的
	 */

	@GetMapping("a")
	@ResponseBody
	public void a(@ModelAttribute("def") User abc){
		System.out.println("abc = " + abc);
	}

	/*
	 * @ModelAttribute用于将方法的参数或方法的返回值绑定到指定的模型属性上，并返回给Web视图
	 * @ModelAttribute注释的方法会在此controller每个方法执行前被执行，因此对于一个controller映射多个URL的用法来说，要谨慎使用。
	 */
	/*
	 * @RequestMapping和@ModelAndView同时标注在一个方法上
	 * 这时这个方法的返回值并不是表示一个视图名称，而是model属性的值，视图名称由RequestToViewNameTranslator根据请求"/index"转换为逻辑视图index
	 * Model属性名称由@ModelAttribute("attributeName")指定，相当于在request中封装了key=attributeName，value=index
	 * 如果@ModelAttribute和@RequestMapping注解在同一个方法上，那么代表给这个请求单独设置Model参数。此时返回的值是Model的参数值，而不是跳转的地址。跳转的地址是根据请求的url自动转换而来的
	 */
	@GetMapping("/index")
	@ModelAttribute("attributeName")
	public String i(){
		// 当使用@RestController时想要返回视图使用ModelAndView
		/*ModelAndView mv = new ModelAndView("index");
		return mv;*/
		return "index";
	}

	/*
	 * @ModelAttribute注释void返回值的方法
	 * 在请求/index后,method_void方法在index方法之前先被调用，它把请求参数（/index?a=abc）加入到一个名为attributeName的model属性中，
	 * 在它执行后method_void被调用，返回视图名index和model已由@ModelAttribute方法生产好了。
	 * 这个例子中model属性名称和model属性对象有model.addAttribute()实现，不过前提是要在方法中加入一个Model类型的参数。
	 * 当URL或者post中不包含参数时，会报错，其实不需要这个方法，完全可以把请求的方法写成下面的样子，这样缺少此参数也不会出错：
	 * @GetMapping(value = "/index")
	 * public ModelAndView index(String a) { return new ModelAndView("index");}
	 */
	@ModelAttribute
	public void method_void(@RequestParam(value = "a",required = false) String a, Model model){
		model.addAttribute("a",a);
	}

	/*
	 * 注解@ModelAttribute注释有返回值的方法
	 * 相当于model.addAttribute("messages",List<Map<String,String>>)
	 */
	@ModelAttribute("messages")
	public List<Map<String,String>> messages() {
		return List.of(Map.of("k1","v1","k2","v2"));
	}

	/*
	 * @ModelAttribute注释在参数上说明了该方法参数的值将由model中取得。如果model中找不到，那么该参数会先被实例化，然后被添加到model中。
	 * 在model中存在以后，请求中所有名称匹配的参数都会填充到该参数中。
	 */
	@ModelAttribute("userM")
	public User addAccount() {
		return new User(2,"jz","jz");
	}

	@RequestMapping(value = "/userModel")// http://localhost:9081/userModel?a=q 请求路径带上参数a 上面的示例中要求必须带上参数a
	public void userModel(@ModelAttribute("userM") User user) {
		System.out.println("user={}"+user.toString());
	}

	@GetMapping(value="/global")//ip:port/global?date=2020-12-12 12:12:12
	@ResponseBody
	public void customGlobal(Date date, Model model){
		try{
			System.out.println(date);
			System.out.println(model.getAttribute("global"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// --------------- validated校验 要加上@Validated -----------------
	// @GetMapping("/{num}")
	// @ResponseBody
	// public Integer detail(@PathVariable("num") @Min(1) @Max(20) Integer num) {
	// 	return num * num;
	// }

	@GetMapping("book")
	@ResponseBody
	public String book(@Validated Book book){
		return "book";
	}

	@GetMapping("/getByEmail")
	@ResponseBody
	public String getByAccount(@RequestParam @NotBlank @Email String email) {
		return email;
	}

	/**
	 * 校验字段
	 */
	@GetMapping("validated")
	@ResponseBody
	public String validated(@Validated(Book.group1.class) Book book){
		return "success";
	}

}