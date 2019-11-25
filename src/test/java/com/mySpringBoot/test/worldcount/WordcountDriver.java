package com.mySpringBoot.test.worldcount;

public class WordcountDriver {
	
	public static void main(String[] args) throws Exception {		
		if (args == null || args.length == 0) {
			args = new String[2];
			args[0] = "hdfs://master:9000/wordcount/input/wordcount.txt";
			args[1] = "hdfs://master:9000/wordcount/output8";
		}
		
//		Configuration conf = new Configuration();
		
		//设置的没有用!  ??????
//		conf.set("HADOOP_USER_NAME", "hadoop");
//		conf.set("dfs.permissions.enabled", "false");
		
		
		/*conf.set("mapreduce.framework.name", "yarn");
		conf.set("yarn.resoucemanager.hostname", "mini1");*/
//		Job job = Job.getInstance(conf);
		
		/*job.setJar("/home/hadoop/wc.jar");*/
		//指定本程序的jar包所在的本地路径
//		job.setJarByClass(WordcountDriver.class);
		
		//指定本业务job要使用的mapper/Reducer业务类
//		job.setMapperClass(WordcountMapper.class);
//		job.setReducerClass(WordcountReducer.class);
		
		//指定mapper输出数据的kv类型
//		job.setMapOutputKeyClass(Text.class);
//		job.setMapOutputValueClass(IntWritable.class);
		
		//指定最终输出的数据的kv类型
//		job.setOutputKeyClass(Text.class);
//		job.setOutputValueClass(IntWritable.class);
		
		//指定job的输入原始文件所在目录
//		FileInputFormat.setInputPaths(job, new Path(args[0]));
		//指定job的输出结果所在目录
//		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
		/*job.submit();*/
//		boolean res = job.waitForCompletion(true);
//		System.exit(res?0:1);
		
	}

}
