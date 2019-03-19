package com.mySpringBoot.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.function.Consumer;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.mySpringBoot.service.LambdaService;

public class MineTest {

	protected Logger logger = Logger.getLogger(MineTest.class);
	@Test
	public void lambda() {	
		Consumer<String> methodParam = System.out::println;
		
		LambdaService lambdaservice = (a,b)->{return a + b;};//相当于LambdaService的实现类
		int c = lambdaservice.lambdaTest(3,4);
		System.out.println("①---"+c);		
		/*java.util.Stream表示了某一种元素的序列，在这些元素上可以进行各种操作。Stream操作可以是中间操作，也可以是完结操作。
		 * 完结操作会返回一个某种类型的值，而中间操作会返回流对象本身，并且你可以通过多次调用同一个流操作方法来将操作结果串起来。
		 * Stream是在一个源的基础上创建出来的，例如java.util.Collection中的list或者set（map不能作为Stream的源）。
		 * Stream操作往往可以通过顺序或者并行两种方式来执行。		 
		 */
		Set<Integer> set = new TreeSet<>();
		Collections.addAll(set, 22,3,51,44,20,6);
		set.stream().filter(x -> x>30).sorted((x,y)->(y-x)).forEach(x->methodParam.accept("②---"+x));
			
		/*jdk7写法
		 * Set<Integer> set1 = new TreeSet<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer i,Integer o) {
				return i - o;
			}
		});*/
		//jdk8写法
		Set<Integer> set1 = new TreeSet<>((x,y)->(x-y));			
		Collections.addAll(set1, 22,3,51,44,20,6);
		set1.forEach(x->methodParam.accept("③---"+x));
		
		List<Integer> list = new ArrayList<>();
		Collections.addAll(list,5,16,41,10);
		/* stream().map()可以看作对象转换*/
		list.stream().sorted((x,y)->(x-y)).map(String::valueOf).filter(x->x.startsWith("1")).forEach(x->methodParam.accept("④---"+x));
	}
	@Test
	public void log() {
		logger.info("记录手动将信息输出到文件");
	}
	@Test
    public void testSend1(){

        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8080/jn/ws/user?wsdl");

        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        Object[] objects = new Object[0];
        try {

            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("add1", "1","小明");
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
	@Test
	public void createTest() {
		Timer timer;
		timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("---");
			}}, 0, 60000);
		// 三个参数
		// 第一个执行内容：内容是定时任务的执行内容，通过实现抽象类来完成这个动作
		// 第二个参数内容：是在第多少时间之后开始执行定时任务内容，该参数不能小于0
		// 第三个参数内容：是在两个任务之间的执行时间间隔，该参数不能小于等于0
	}
}
