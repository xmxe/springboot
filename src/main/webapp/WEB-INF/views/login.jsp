<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String code = (String) request.getSession().getAttribute("code");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>登录</title>
<script src="js/jquery-1.7.2.js"></script>
<script src="layui/layui.js"></script>
<script tsrc="camera/jquery.mobile.customized.min.js"></script>
<script src="js/jquery.cookie.js"></script>
<script src="camera/jquery.easing.1.3.js"></script>
<script src="camera/camera.min.js"></script>
<link rel="stylesheet" href="layui/css/layui.css" />
<link rel="stylesheet" href="camera/camera.css" />
<style type="text/css">
.center {
	position: absolute; /*绝对定位*/
	top: 43%; /*距顶部50%*/
	left: 45%;
	margin: -100px 0 0 -150px;
	/*设定这个div的margin-top的负值为自身的高度的一半,margin-left的值也是自身的宽度的一半的负值.(感觉在绕口令)*/
	text-align: center;
}

#title {
	position: absolute; /*绝对定位*/
	top: 10%;
	left: 30%;
	text-align: center;
}

<%-- body {
	background-image: url(<%=basePath%>image/timg.jpg)
} --%>
</style>
<script type="text/javascript">
if (window != top)   
    top.location.href = location.href;
</script>
<script type="text/javascript">
	layui.use('layer', function(){
		var layer = layui.layer;
	}); 
	$(function(){
		changeCode();
		var username = $.cookie("username");
		var password = $.cookie("password");
		if (typeof(username) != "undefined"
			&& typeof(password) != "undefined") {
			$("#username").val(username);
			$("#password").val(password);
			$("#yyy").attr("checked", true);
		}
		layui.use('form', function(){
			  var form = layui.form;
			  form.on('switch(switchTest)', function(data){
				if(data.elem.checked){//开关是否开启，true或者false
					$.cookie("username",$("#username").val(),{expires : 1});
					$.cookie("password",$("#password").val(),{expires : 1});
				}else{
					$.cookie("username",'',{expires : -1});
					$.cookie("password",'',{expires : -1});
				}
				}); 
			});  
		$("#codeImg").bind("click", changeCode);
		
		 $('#templatemo_banner_slide > div').camera({
		        height: 'auto',
		        loader: 'bar',
		        playPause: false,
		        pagination: false,
		        thumbnails: false,
		        hover: false,
		        opacityOnGrid: false,
		        imagePath: 'image/'
		    });
		    changebg();	

	})
	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			$("#ind").trigger("click");
		}
	});
	function ind(){		  
		if($("#username").val() == null || $("#username").val()==""){
			layer.msg("请输入用户名");
			return;
		}
		if($("#password").val() == null || $("#password").val() == ""){
			layer.msg("请输入密码");
			return;
		}
		$.ajax({
			url:'check.do',
			data:{code:$("#code").val(),username:$("#username").val(),password:$("#password").val()},
			dataType:'json',
			success:function(data){
				if(data.message == "success"){
					window.location.href="index";
				}else{
					layer.msg(data.message);
				}
						
			},
			error:function(){
				alert("exception");
			}
		})
	}
	
	function changeCode(){
		$("#codeImg").attr("src","code.do?t=" + genTimestamp());
	}
	function genTimestamp() {
		var time = new Date();
		return time.getTime();
	}
	function changebg(){
	    banner_slider_height = $(window).outerHeight()-285;
		
		var bheight = document.documentElement.clientHeight;
		if(bheight == 0){bheight = 1000;}
	    banner_slider_height = (banner_slider_height<bheight) ? bheight : banner_slider_height;
	    $("#templatemo_banner_slide > div").height(banner_slider_height);
	    $("#templatemo_banner_slide").height(banner_slider_height);
	    $(window).resize(function(){
	        banner_slider_height = $(window).outerHeight()-285;
	        banner_slider_height = (banner_slider_height<bheight) ? bheight : banner_slider_height;
	        $("#templatemo_banner_slide > div").height(banner_slider_height);
	        $("#templatemo_banner_slide").height(banner_slider_height);
	    });
		
	}
</script>
</head>
<body>
	<div id="templatemo_banner_slide">
		<div>
			<!-- 背景图片 -->
			<div data-src="image/timg.jpg"></div>
			<div data-src="image/goku.jpg"></div>
			<div data-src="image/time.jpg"></div>
			<div data-src="image/ace.jpg"></div>			
		</div>
	</div>
	<div id="title">
		<h1 style="font-size: 100px;color:#C5DAF7">xxx后台管理系统</h1>
	</div>
	<div class="center" id="center">
		<div class="layui-form-item">
			<label class="layui-form-label" style="font-family: '微软雅黑';color:#DCE5D7">用户名:</label>
			<div class="layui-input-inline">
				<input type="text" id="username" lay-verify="required"
					placeholder="请输入" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="color: #CCC7F5">密码:</label>
			<div class="layui-input-inline">
				<input type="password" id="password" placeholder="请输入密码"
					autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="color:#DCE5D7">验证码:</label>
			<div class="layui-input-inline">
				<input type="text" name="code" id="code" class="layui-input"
					style="width: 100px; height: 25px; float: left"> <i><img
					id="codeImg" alt="点击更换" title="点击更换" src=""></i>
			</div>
		</div>
		<div class="layui-form" lay-filter="test1">
			<label style="position:absolute;right:200px;color:#DCE5D7">记住密码:</label>
			<div style="position:absolute;left:120px;top:150px">
				<input type="checkbox" id="yyy" lay-skin="switch" lay-text="ON|OFF" lay-filter="switchTest">
			</div>
			
		</div>
		<button class="layui-btn layui-btn-normal" id="ind"
			onclick="ind()" style="position:absolute;top:200px">登录</button>
	</div>
	
</body>
</html>