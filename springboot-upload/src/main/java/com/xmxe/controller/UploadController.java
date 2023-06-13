package com.xmxe.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class UploadController {

	@RequestMapping(value="/download")
	@ResponseBody
	public ResponseEntity<byte[]> down(HttpServletRequest request) throws Exception{
		String path = request.getParameter("path") == null ?null:request.getParameter("path");
		String filename = request.getParameter("filename") == null ?null:request.getParameter("filename");
		try {
			//filename = new String(filename.getBytes("ISO8859-1"),"UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String downloadPath = request.getSession().getServletContext().getRealPath("/")+"/download/";

		File file = new File(downloadPath);
		if(!file.exists()){
			file.mkdirs();
		}
//		FtpClient ftpClient=Ftp.connect();
//		Ftp.downloadFile(ftpClient, path, downloadPath+filename);
//		Ftp.closeServer(ftpClient);
//		File f = new File(downloadPath+filename);
		File localFile = new File("C:\\E\\file123\\网源文档\\R模式负荷变化率.docx");
		InputStream is = new FileInputStream(localFile);
		byte[] body = new byte[is.available()];
//		byte[] body = FileUtils.readFileToByteArray(f);
		is.read(body);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attchement;filename="+new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentLength(body.length);
		HttpStatus status = HttpStatus.OK;
		is.close();
		ResponseEntity<byte[]> entity = new ResponseEntity<>(body,headers,status);
		return entity;
	}

	@RequestMapping("/downLoadFromFTP")
	@ResponseBody
	public void downLoadFromFTP(HttpServletRequest request,HttpServletResponse response){
		String fileurl = request.getParameter("fileurl") == null ?null:request.getParameter("fileurl").toString();
		String name = request.getParameter("name") == null ?null:request.getParameter("name");
		try {
			name = new String(name.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedInputStream bis = null;
		BufferedOutputStream os = null;
		InputStream newinputstream = null;
		try{
			URL url = new URL(fileurl);
			URLConnection conn = url.openConnection();
			int filesize = conn.getContentLength(); // 取数据长度
			try{
				newinputstream = conn.getInputStream();
			}catch(Exception e){
				newinputstream = null;
			}
			if(newinputstream !=null){
				bis = new BufferedInputStream(conn.getInputStream());
				// 清空response
				response.reset();
				// 文件名称转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes("utf-8"), "iso8859-1"));
				response.addHeader("Content-Length", "" + filesize);
				os = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				// 从输入流中读入字节流，然后写到文件中
				byte[] buffer = new byte[1024];
				int nRead;
				while ((nRead = bis.read(buffer, 0, 1024)) > 0) { // bis为网络输入流
					os.write(buffer, 0, nRead);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(bis !=null){
					bis.close();
				}
				if(os !=null){
					os.flush();
					os.close();
				}
				if(newinputstream !=null){
					newinputstream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 批量下载 转zip
	 * @param name js生成form表单提交的参数name
	 * @param code js生成form表单提交的参数code
	 * @param ts js生成form表单提交的参数ts
	 * @param response 返回响应
	 * @throws Exception
	 */
	@RequestMapping("batchDown")
	public void batchExcel(String[] name,String[] code,String[] ts,HttpServletResponse response) throws Exception{
		if(name!=null && name.length > 0){
			// 创建临时压缩文件 浏览器下载的就是这个文件
			File zipFile = File.createTempFile("zip", ".zip");
			// 当文件不存在时创建成功返回true
//			boolean iscreated = zipFile.createNewFile();
			FileOutputStream out = new FileOutputStream(zipFile);
			// 创建zip流 等待写入
			ZipOutputStream zipout = new ZipOutputStream(out);

			for(int i = 0;i < name.length;i++){
				File fileTemp = null;
				try {
					fileTemp = File.createTempFile(name[i], ".xlsx");
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 将excel文件添加到zip
				zipFile(fileTemp,zipout);
			}
			zipout.close();
			out.close();
			response.reset();
			// 下载zip
			InputStream fis = new BufferedInputStream(new FileInputStream(zipFile));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			String filedisplay = name[0];
			filedisplay = URLEncoder.encode(filedisplay + "等数据.zip", "UTF-8");
			response.addHeader("Content-dispostion", "attachment;filename="+filedisplay);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment;filename=".concat(filedisplay));
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			os.write(buffer);
			os.close();
			zipFile.delete();
		}
	}

	/**
	 *
	 * @param file 将要添加进压缩包的excel文件
	 * @param outputStream zip压缩流
	 * @throws Exception
	 */
	public void zipFile(File file,ZipOutputStream outputStream) throws Exception{
		FileInputStream IN = new FileInputStream(file);
		BufferedInputStream buffer = new BufferedInputStream(IN,1024);
		ZipEntry entry = new ZipEntry(file.getName());
		// 将文件放入压缩包
		outputStream.putNextEntry(entry);
		int number;
		byte[] bytes = new byte[1024];
		while((number = buffer.read(bytes)) != -1){
			outputStream.write(bytes,0,number);
		}
		buffer.close();
		IN.close();
	}

	@RequestMapping("/upload")
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		try{
			if (request instanceof MultipartHttpServletRequest) {
				MultipartFile file =  ((MultipartHttpServletRequest) request).getFile("fileName");//文件名
				if(file != null) {
					SimpleDateFormat sff = new SimpleDateFormat("yyyyMMdd");//20180101
					String today = sff.format(new Date());
					File f = new File(request.getSession().getServletContext().getRealPath(File.separator) + today);//tomcat webapps路径+项目名 例:E:\apache-tomcat-7.0.82\webapps\club
					if(!f.exists()) {f.mkdirs();}
					String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());//自定义文件名
					/*①
					 * file.transferTo(new File(f,fileName.replace("-", "")));
					 */

					/*②
					 * FileUtils.writeByteArrayToFile(new File(f,fileName.replace("-", "")), file.getBytes());
					 */
					InputStream is = file.getInputStream();
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@RequestMapping("/pic")
	@ResponseBody
	public ResponseEntity<String> pic(MultipartFile [] pictures) throws Exception {
		HttpStatus httpStatus = HttpStatus.OK;
		ResponseEntity<String> responseEntity = new ResponseEntity<>(httpStatus);
		long count = Arrays.asList(pictures).stream().
				map(MultipartFile::getOriginalFilename).
				filter(String::isEmpty).count();
		if (count == pictures.length){
			throw new NullPointerException("图片不能同时为空");
		}

		return responseEntity;
	}

}