<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>layui</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery.min.js" ></script>
<script src="layui/layui.js"></script>
<link rel="stylesheet" href="layui/css/layui.css" media="all" />
<!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
<script type="text/javascript">
	layui.use('layer', function(){
		var layer = layui.layer;
	}); 
	$(document).ready(function(){
		$("#page").click(function(){
			window.location.href="/pageView.do";
		});
		
		$("#ztree").click(function(){
			window.location.href="/ztreeView.do";
		});
		
		$("#form").click(function(){
			window.location.href="/form.do";
		});
		
	})
</script>
</head>
<body>
	<button class="layui-btn layui-btn-primary" id="page">page分页</button>
	<button class="layui-btn" id="ztree">zTree</button>
	<button class="layui-btn layui-btn-normal" id="form">form</button>
	<button class="layui-btn layui-btn-warm">1</button>
	<button class="layui-btn layui-btn-danger">1</button>
	<button class="layui-btn layui-btn-disabled">1</button>
	<button class="layui-btn layui-btn-lg">1</button>
	<button class="layui-btn">2</button>
	<button class="layui-btn layui-btn-sm">2</button>
	<button class="layui-btn layui-btn-xs">2</button>
	<button class="layui-btn layui-btn-lg layui-btn-normal">3</button>
	<button class="layui-btn layui-btn-warm">3</button>
	<button class="layui-btn layui-btn-sm layui-btn-danger">3</button>
	<button class=""></button>
</body>
</html>