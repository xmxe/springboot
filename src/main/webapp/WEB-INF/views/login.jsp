<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>登录</title>
<script src="js/jquery.min.js" ></script>
<script src="layui/layui.js"></script>
<link rel="stylesheet" href="layui/css/layui.css" />
<style type="text/css">
	.center{
		position:absolute; /*绝对定位*/ 
		top:30%; /*距顶部50%*/ 
		left:45%; 
		margin:-100px 0 0 -150px; /*设定这个div的margin-top的负值为自身的高度的一半,margin-left的值也是自身的宽度的一半的负值.(感觉在绕口令)*/ 
		text-align:center;
	}
	#title{
		position:absolute; /*绝对定位*/ 
		top:10%;
		left:40%;
		text-align:center;
	}
	body{
		background-image: url(<%=basePath%>image/timg.jpg)
	}
</style>
<script type="text/javascript">
	$(function(){
		$("#in").click(function(){
			window.location.href="index";
		})
	})
</script>
</head>
<body>
<div id="title">
	<h1>xxx后台管理系统</h1>
</div>

	<div class="center">
		<div class="layui-form-item">
			<label class="layui-form-label" style="font-family: '微软雅黑'">用户名:</label>
			<div class="layui-input-inline">
				<input type="text" name="username" lay-verify="required"
					placeholder="请输入" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">密码:</label>
			<div class="layui-input-inline">
				<input type="password" name="password" placeholder="请输入密码"
					autocomplete="off" class="layui-input">
			</div>
		</div>
		<button class="layui-btn layui-btn-radius layui-btn-warm" id="in">index</button>
	</div>
</body>
</html>