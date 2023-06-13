package com.xmxe.util;

// import com.jacob.activeX.ActiveXComponent;
// import com.jacob.com.Dispatch;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class DocUtil {

	private static Configuration configure = new Configuration();

	static {
//    	   Configuration configure1 = new Configuration();
//    	   configure = configure1;
		configure.setDefaultEncoding("utf-8");
		//设置对象包装器
		configure.setObjectWrapper(new DefaultObjectWrapper());
		//设置异常处理器
		configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		//加载模板文件
		configure.setClassForTemplateLoading(DocUtil.class, "/");
	}

	/**
	 * 根据Doc模板生成word文件
	 *
	 * @param dataMap      Map需要填入模板的数据
	 * @param downloadType 模板路径
	 * @param savePath     保存路径
	 */
	public static void createDoc(Map<String, Object> dataMap, String downloadType, String savePath) throws Exception {

		//加载需要装填的模板
		Template template = null;
		//定义Template对象,注意模板类型名字与downloadType要一致
		template = configure.getTemplate(downloadType);
		//输出文档
		File outFile = new File(savePath);
		//如果输出目标文件夹不存在，则创建
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}

		Writer out = null;
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
		template.process(dataMap, out);
		out.flush();
		out.close();

	}

	/**
	 * 如果需要生成word文档到FTP服务器，调用此方法
	 *
	 * @param dataMap
	 * @param downloadType
	 * @param os
	 * @throws Exception
	 */
	public static void createDoc(Map<String, Object> dataMap, String downloadType, OutputStream os) throws Exception {
//         try{
		//加载需要装填的模板
		Template template = null;
		//定义Template对象,注意模板类型名字与downloadType要一致
		template = configure.getTemplate(downloadType);

		// Writer out = null;
		// out = new BufferedWriter(new OutputStreamWriter(os,"utf-8"));

		OutputStreamWriter out = new OutputStreamWriter(os, "utf-8");

		template.process(dataMap, out);
		out.flush();
		out.close();
	}

	/**
	 * 将图片转为byte数组
	 *
	 * @param path
	 * @return
	 * @Title image2byte
	 */
	public static byte[] image2byte(String path) {
		byte[] data = null;
		FileImageInputStream input = null;
		try {
			input = new FileImageInputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		} catch (FileNotFoundException ex1) {
			ex1.printStackTrace();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		}
		return data;
	}

	public static String rPicStr() {
		String url = "D:/test.jpg";
		return GetImageStr(url);

//		byte[] imageByte = image2byte(url);
//		BASE64Encoder encode = new BASE64Encoder();
//		return encode.encode(imageByte);

	}


	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 *
	 * @param imgFile
	 * @return
	 * @Title GetImageStr
	 */
	public static String GetImageStr(String imgFile) {

		InputStream in = null;
		byte[] data = null;
		//读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Base64.Encoder encoder = Base64.getEncoder();
		return new String(encoder.encode(data), StandardCharsets.UTF_8);
	}

	/**
	 * 获取文件的后缀
	 *
	 * @param fileName
	 * @return
	 */
	public static String getFileSufix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(splitIndex + 1);
	}

	/**
	 * 获取文件后缀前的字符串
	 *
	 * @param fileName
	 * @return
	 */
	public static String getFileUrl(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex);
	}


	// public static void convert2PDF(String inputFile) throws Exception {
	// 	if (inputFile == null || "".equals(inputFile)) {
	// 		System.out.println("文件路径不能为空");
	// 	}
	//
	// 	File infile = new File(inputFile);
	// 	if (!infile.exists()) {
	// 		System.out.println("文件不存在");
	// 	}
	//
	// 	String suffix = getFileSufix(inputFile);
	// 	//为pdf的文件，直接返回原路径
	// 	if (suffix.equalsIgnoreCase("pdf")) {
	// 		System.out.println();
	// 	}
	//
	// 	String outurl = getFileUrl(inputFile);
	// 	String pdfFile = outurl + ".pdf";
	// 	File outfile = new File(pdfFile);
	// 	//转换过的文件，不再转换
	// 	if (outfile.exists()) {
	// 		System.out.println();
	// 	}
	//
	// 	if (suffix.equalsIgnoreCase("wps") || suffix.equalsIgnoreCase("doc") || suffix.equalsIgnoreCase("docx") || suffix.equalsIgnoreCase("txt")) {
	// 		Word2PdfUtil.doc2pdf(inputFile, pdfFile);
	// 	} else {
	// 		System.out.println("文件格式不支持转换!");
	// 	}
	//
	// }


	/**
	 * 转换word为pdf 需要安装office
	 *
	 * @param inputFile
	 * @param pdfFile
	 * @return
	 */
// 	public static void word2PDF(String inputFile, String pdfFile) throws Exception {
// //    	try{
// 		//打开word应用程序
// //	        System.out.println(System.getProperty("java.library.path"));
// 		ActiveXComponent app = new ActiveXComponent("Word.Application");
// 		//设置word不可见
// 		app.setProperty("Visible", false);
// 		//获得word中所有打开的文档,返回Documents对象
// 		Dispatch docs = app.getProperty("Documents").toDispatch();
// 		//调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
// 		Dispatch doc = Dispatch.call(docs, "Open", inputFile, false, true).toDispatch();
// 		Dispatch.call(doc, "ExportAsFixedFormat", pdfFile, 17);//word保存为pdf格式宏，值为17
// 		//关闭文档
// 		Dispatch.call(doc, "Close", false);
// 		//关闭word应用程序
// 		app.invoke("Quit", 0);
// 		System.out.println(pdfFile);
// //	    }catch(Exception e){
// //	    	e.printStackTrace();
// //	        return WebUtil.DescError(e.getMessage());
// //	    }finally{
// //
// //	    }
//
// 	}


	// public static void main(String[] args) {
	// 	try {
	// 		Word2PdfUtil.doc2pdf("E:\\一次调频月度分析报告.doc", "E:\\一次调频月度分析报告.pdf");
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// }


}