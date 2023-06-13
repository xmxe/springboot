package com.xmxe.config.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 启动时加载
 */
@Component
@Order(2)//@Order 注解中，数字越小，优先级越大，默认情况下，优先级的值为 Integer.MAX_VALUE，表示优先级最低。
public class MyApplicationRunner implements ApplicationRunner {

	Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// args是命令行一些自定义的参数
		// 判断从参数中解析的option参数是否包含指定名称的选项
		boolean debug = args.containsOption("debug");
		logger.warn("containsOption debug is:{}", debug);

		// 获取args中的所有non option参数
		List<String> nonOptionArgs = args.getNonOptionArgs();
		logger.info("ApplicationArguments.getNonOptionArgs====>{}",nonOptionArgs);

		// 获取args中所有的option参数的name
		Set<String> optionNames = args.getOptionNames();
		for (String optionName : optionNames) {
			List<String> optionValues = args.getOptionValues(optionName);
			logger.info("ApplicationArguments.getOptionNames====>optionName={},value={}",optionName, args.getOptionValues(optionName));
		}
		// 获取传递给应用程序的原始未处理参数
		String[] sourceArgs = args.getSourceArgs();
		// for (String sourceArg : sourceArgs) {
		// 	logger.warn("这是传过来sourceArgs[{}]", sourceArg);
		// }
		logger.info("ApplicationArguments.getSourceArgs====>{}",Arrays.toString(sourceArgs));

	}
}