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
<title>home</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery.min.js" ></script>
<script src="layui/layui.js"></script>
<link rel="stylesheet" href="layui/css/layui.css" media="all" />
<!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->

<script type="text/javascript">
	layui.use('layer', function(){
		var layer = layui.layer;
	}); 
	/* $(document).ready(function(){
		$("#page").click(function(){
			window.location.href="/pageView.do";
		});
		
		$("#ztree").click(function(){
			window.location.href="/ztreeView.do";
		});
		
		$("#form").click(function(){
			window.location.href="/form.do";
		});
		
	}) */
	
	layui.use('element', function(){
	  var element = layui.element;	  
	});
	function returnIframe(url){
		$("#myiframe").attr("src",url);
	}
</script>
</head>
<body>
	<!-- <button class="layui-btn layui-btn-primary" id="page">page分页</button>
	<button class="layui-btn" id="ztree">zTree</button>
	<button class="layui-btn layui-btn-normal" id="form">form</button> -->
	
	<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">XXX管理系统</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item"><a href="javascript:void(0)">控制台</a></li>
      <li class="layui-nav-item"><a href="javascript:void(0)">商品管理</a></li>
      <li class="layui-nav-item"><a href="javascript:void(0)">用户</a></li>
      <li class="layui-nav-item">
        <a href="javascript:;">其它系统</a>
        <dl class="layui-nav-child">
          <dd><a href="javascript:void(0)">邮件管理</a></dd>
          <dd><a href="javascript:void(0)">消息管理</a></dd>
          <dd><a href="javascript:void(0)">授权管理</a></dd>
        </dl>
      </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          贤心
        </a>
        <dl class="layui-nav-child">
          <dd><a href="javascript:void(0)">基本资料</a></dd>
          <dd><a href="javascript:void(0)">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="">退了</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;">所有商品</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:;" onclick='returnIframe("/pageView.do")'>page</a></dd>
            <dd><a href="javascript:;" onclick='returnIframe("/ztreeView.do")'>ztree</a></dd>
            <dd><a href="javascript:;" onclick='returnIframe("/form.do")'>form</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item">
          <a href="javascript:;">解决方案</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:;">列表一</a></dd>
            <dd><a href="javascript:;">列表二</a></dd>
            <dd><a href="javascript:;">超链接</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item"><a href="javascript:void(0)">云市场</a></li>
        <li class="layui-nav-item"><a href="javascript:void(0)">发布商品</a></li>
      </ul>
    </div>
  </div>
  
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <iframe scrolling="no" frameborder="0" width="100%" height="100%" id="myiframe" src="image/ace.jpg"></iframe>
  </div>
  
  <div class="layui-footer">
    <!-- 底部固定区域 -->
    © www.baidu.com - 百度公司
  </div>
</div>
</body>
</html>