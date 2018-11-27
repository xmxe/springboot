<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="privilege" uri="/WEB-INF/privilege.tld"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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
<script src="js/jquery-1.7.2.js"></script>
<script src="js/jquery.tips.js"></script>
<script src="layui/layui.js"></script>
<link rel="stylesheet" href="layui/css/layui.css" media="all" />
<!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
<style type="text/css">
	<%-- body {
		background-image: url(<%=basePath%>image/timg.jpg)
	} --%>
</style>
</head>
<body>
	<form class="layui-form" action="">
		<div class="layui-form-item">
			<label class="layui-form-label">输入框</label>
			<div class="layui-input-block">
				<input type="text" name="title" required lay-verify="required"
					placeholder="请输入标题" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">密码框</label>
			<div class="layui-input-inline">
				<input type="password" name="password" required
					lay-verify="required" placeholder="请输入密码" autocomplete="off"
					class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">辅助文字</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">选择框</label>
			<div class="layui-input-block">
				<select name="city" lay-verify="required">
					<option value=""></option>
					<option value="0">北京</option>
					<option value="1">上海</option>
					<option value="2">广州</option>
					<option value="3">深圳</option>
					<option value="4">杭州</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">复选框</label>
			<div class="layui-input-block">
				<input type="checkbox" name="like[write]" title="写作"> <input
					type="checkbox" name="like[read]" title="阅读" checked> <input
					type="checkbox" name="like[dai]" title="发呆">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">开关</label>
			<div class="layui-input-block">
				<input type="checkbox" name="switch" lay-skin="switch">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">单选框</label>
			<div class="layui-input-block">
				<input type="radio" name="sex" value="男" title="男"> <input
					type="radio" name="sex" value="女" title="女" checked>
			</div>
		</div>
		<div class="layui-form-item layui-form-text">
			<label class="layui-form-label">文本域</label>
			<div class="layui-input-block">
				<textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>				
			</div>
		</div>
		
	</form>
	<button id="tip" class="layui-btn layui-btn-primary" onclick="tip()" style="text-align: center">提示框(quartz)</button>
	<button id="kuayu" class="layui-btn layui-btn-primary" onclick="kuayu()" style="text-align: center">跨域测试</button>
	<button id="httpclient" class="layui-btn layui-btn-normal" onclick="httpclient()" style="text-align: center">httpclient</button>
	<privilege:operation operationId="1" clazz="layui-btn layui-btn-normal" onClick="down()" name="下载"></privilege:operation>
	<shiro:hasPermission name="user:add">
		<button class="layui-btn layui-btn-primary" style="text-align: center">shiro:hasPermission</button>
	</shiro:hasPermission>
	<shiro:hasRole name="a">
		<button class="layui-btn layui-btn-primary" style="text-align: center">shiro:hasRole</button>
	</shiro:hasRole>
	<shiro:hasAnyRoles name="a,b">
		<button class="layui-btn layui-btn-primary" style="text-align: center">shiro:hasAnyRoles</button>
	</shiro:hasAnyRoles>
	<script type="text/javascript">
//Demo
layui.use('form', function(){
  var form = layui.form;
  
  //监听提交
  form.on('submit(formDemo)', function(data){
    layer.msg(JSON.stringify(data.field));
    return false;
  });
});
function down(){
	window.location.href="/excel.do"
}
function tip(){
	$.ajax({
		url:"/quartz.do",
		type:"post",
		dataType:"json",
		success:function(data){
			$("#tip").tips({
				side:3,//1:上2:右3:下4:左
				msg:data.msg,
				time:5,//5秒后关闭
				bg:'#AE81FF',//背景色
				color:'#FFF',//文字颜色，默认为白色
				
			});
		}
	})
	
}
function kuayu(){
	$.ajax({
		url:'http://127.0.0.1:8080/zhongzhu/appnewhouse/newhouseList.do',
		data:{},
		dataType : 'jsonp',
		jsonp:"qqq",//此代码代表在路径后追加字段qqq: http://127.0.0.1:8080/zhongzhu/appnewhouse/newhouseList.do?qqq=success_jsonp,不加的话默认为callback
		jsonpCallback:"success_jsonp", //代表追加字段的值，不写的话有默认值,jsonp默认会在路径后面追加参数，以便服务器调用
		type:'get',//jsonp必须为get请求
		success:function(data){
			//服务器端返回示例:String qqq = request.getParameter("qqq");return qqq+"("+json+")";最终返回格式success_jsonp({"id":"3", "name":"zhangsan", "telephone":"13612345678"})    
			console.log(data);
			alert(data);
		},
		error:function(){
			alert("why error?");
		}
	});
}
function httpclient(){
	$.ajax({
		url:'/httpclient.do',
		data:{},
		type:'post',
		dataType:'json',
		success:function(data){
			console.log(data);
			alert(data.doorList[0].address);
		},
		error:function(){
			alert("why error");
		}
	});
}
</script>

</body>
</html>