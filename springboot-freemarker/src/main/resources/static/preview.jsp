<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String bb = (String)request.getSession().getAttribute("myurl");
//bb = "";
//String bb = (String)request.getAttribute("myurl");
//request.getSession().removeAttribute("myurl");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<title>文件预览</title>

    <script type="text/javascript" src="js/common/pdfobject.js"></script>
    <script type="text/javascript" src="web-resources/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript">

      window.onload = function (){
    	    
    	  var ff = '<%=bb%>';
    	 if(ff != null && ff != '' && ff != 'null'){
    		 
	       var myPDF = new PDFObject({ 
	        	url: ff
	        }).embed("test"); 
	       
	       //document.getElementById("test").EnablePrint=false;

	    }
    	 
      };
      

	</script>
    
    
</head>




<body >
	<div id='test' style="height: 99%">
		不合法参数,请重新预览
    </div>
</body>

	<script type="text/javascript">

	</script>

</html>
