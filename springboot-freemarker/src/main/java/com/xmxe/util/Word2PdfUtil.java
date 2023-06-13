// package com.xmxe.util;
//
// import com.aspose.words.Document;
// import com.aspose.words.License;
// import com.aspose.words.SaveFormat;
//
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.InputStream;
//
// /**
//  * 不需要安装office
//  */
// public class Word2PdfUtil {
//
// 	public static boolean getLicense() {
// 		boolean result = false;
// 		try {
// 			InputStream is = com.aspose.words.Document.class.getResourceAsStream("/com.aspose.words.lic_2999.xml");
// 			License aposeLic = new License();
// 			aposeLic.setLicense(is);
// 			result = true;
// 		} catch (Exception e) {
// 			System.out.println(e.toString());
// 		}
// 		return result;
// 	}
//
// 	public static void doc2pdf(String inPath, String outPath) {
// 		if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
// 			System.out.println("License验证不通过");
// 		}
// 		try {
// 			Long old = System.currentTimeMillis();
// 			File file = new File(inPath.replaceAll("doc", "docx")); // 新建一个空白pdf文档
// 			FileOutputStream os = new FileOutputStream(file);
// 			Document doc = new Document(inPath); // Address是将要被转化的word文档
// 			doc.save(os, SaveFormat.DOCX);
// 			doc = new Document(inPath.replaceAll("doc", "docx"));
// 			os = new FileOutputStream(new File(outPath));
// 			doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
// 			// EPUB, XPS, SWF 相互转换
// 			Long now = System.currentTimeMillis();
// 			System.out.println("转pdf成功耗时" + (now - old) + "ms");
// 			file.delete();
//
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
// 	}
//
// 	public static void main(String[] args) {
// 		Word2PdfUtil.doc2pdf("E:\\一次调频月度分析报告.doc", "E:\\一次调频月度分析报告.pdf");
// 	}
// }