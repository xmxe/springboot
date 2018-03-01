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

	function PAGE(){
		window.location.href="/pageView.do";
	}
	function ztree(){
		window.location.href="/ztreeView.do";
	}	
	function forma(){
		window.location.href="/form.do";
	}
</script>
</head>
<body>
	<button class="layui-btn layui-btn-primary" onclick="PAGE()">page分页</button>
	<button class="layui-btn" onclick="ztree()">zTree</button>
	<button class="layui-btn layui-btn-normal" onclick="forma()">form</button>
	<button class="layui-btn layui-btn-warm" ></button>
	<button class="layui-btn layui-btn-danger"></button>
	<button class="layui-btn layui-btn-disabled"></button>
	<button class="layui-btn layui-btn-lg"></button>
	<button class="layui-btn"></button>
	<button class="layui-btn layui-btn-sm"></button>
	<button class="layui-btn layui-btn-xs"></button>
	<button class="layui-btn layui-btn-lg layui-btn-normal"></button>
	<button class="layui-btn layui-btn-warm"></button>
	<button class="layui-btn layui-btn-sm layui-btn-danger"></button>
	<button class=""></button>
</body>
</html>