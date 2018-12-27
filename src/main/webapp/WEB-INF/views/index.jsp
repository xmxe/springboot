<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>红磨坊</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery.min.js" ></script>
<script src="layui/layui.js"></script>
<link rel="stylesheet" href="layui/css/layui.css" media="all" />
<!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->

<script type="text/javascript">
	$(function(){
		if (window.WebSocket) {
			var oladress = "127.0.0.1:8889";
			var websocket = new WebSocket(encodeURI('ws://'+oladress)); 
			websocket.onopen = function() {//连接成功	
				var user = "1";
				websocket.send('[join]'+user);
			};
			websocket.onerror = function() {//连接失败
				
			};
			websocket.onclose = function() {//连接断开
				
			};
			//消息接收
			websocket.onmessage = function(message) {
				var message = JSON.parse(message.data);
				if(message.type == 'goOut'){
					$("body").html("");
					goOut("1");
				}else if(message.type == 'thegoout'){
					$("body").html("");
					goOut("2");
				}
			};
		}
	});
	
	function goOut(msg){
		//var locat = (window.location+'').split('/'); 
		window.location.href="logout.do?msg="+msg;
	};
	
	layui.use('layer', function(){
		var layer = layui.layer;
	}); 
	/* $(document).ready(function(){
		$("#page").click(function(){
			window.location.href="/pageView.do";
		});
	}) */
	
	layui.use('element', function(){
	  var element = layui.element;	  
	});
	function returnIframe(url,id,titleName){
		//$("#myiframe").attr("src",url);
		$("#divf").hide();
		$(".layui-tab").show();
		layui.use('element', function(){
			var element = layui.element;
			var active = {
		            tabAdd: function (url,id) {
		                //新增一个Tab项
		                element.tabAdd('demo', {
		                    title: titleName,
		                    //frameborder 属性规定是否显示框架周围的边框。0无边框1有边框       scrolling 规定是否在 iframe 中显示滚动条 yes no auto
		                    content: '<iframe data-frameid="'+id+'" name="content" frameborder="0" width="100%" src="'+url+'"></iframe>',
		                    id: id //实际使用一般是规定好的id，这里以时间戳模拟下
		                })
		                //CustomRightClick(id);//绑定右键菜单
		                FrameWH();//计算框架高度
		            }, 
		           tabChange: function (id) {
		              //切换到指定Tab项
		              element.tabChange('demo', id); //切换到：用户管理
		              $("iframe[data-frameid='"+id+"']").attr("src",$("iframe[data-frameid='"+id+"']").attr("src"))//切换后刷新框架
		          },
		             tabDelete: function (id) {
		                element.tabDelete("demo", id);//删除
		            },
		              tabDeleteAll: function (ids) {//删除所有
		                $.each(ids, function (i,item) {
		                    element.tabDelete("demo", item);
		                })
		            }
		        };
			if ($(".layui-tab-title li[lay-id]").length <= 0) {
                active.tabAdd(url, id);
            }else {
                var isData = false;
                $.each($(".layui-tab-title li[lay-id]"), function () {
                    if ($(this).attr("lay-id") == id) {
                        isData = true;
                    }
                })
                if (isData == false) {
                    active.tabAdd(url, id);
                }
            }
			//active.tabAdd(url,id);
			active.tabChange(id);
		})
	}
	//iframe自适应高度
	function FrameWH() {
        var h = $(window).height() -41- 10 - 60 -10-44 -10;
        $("iframe").css("height",h+"px");
		/* var ifm= document.getElementById("myiframe"); 
	    ifm.height=document.documentElement.clientHeight; */
    }

	//窗口大小改变时触发
    $(window).resize(function () {
        FrameWH();
    })
</script>
</head>
<body>
	<div class="layui-layout layui-layout-admin">
  	<div class="layui-header">
    <div class="layui-logo">千里冰封万里雪飘</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item"><a href="javascript:void(0)"><i class="layui-icon">&#xe614;Goku</i></a></li>
      <li class="layui-nav-item"><a href="javascript:void(0)"><i class="layui-icon">&#xe705;Naruto</i></a></li>
      <li class="layui-nav-item"><a href="javascript:void(0)"><i class="layui-icon">&#xe612;Luffy</i></a></li>
      <li class="layui-nav-item">
        <a href="javascript:;"><i class="layui-icon">&#xe60c;Zoro</i></a>
        <dl class="layui-nav-child">
          <dd><a href="javascript:void(0)"><i class="layui-icon">&#xe628;Nami</i></a></dd>
          <dd><a href="javascript:void(0)"><i class="layui-icon">&#xe62c;Robin</i></a></dd>
          <dd><a href="javascript:void(0)"><i class="layui-icon">&#xe60e;Fraky</i></a></dd>
        </dl>
      </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="image/goku.jpg" class="layui-nav-img">
          然
        </a>
        <dl class="layui-nav-child">
          <dd><a href="javascript:void(0)">基本资料</a></dd>
          <dd><a href="javascript:void(0)">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="/logout.do">退出</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black layui-anim layui-anim-upbit">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;">One</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:;" onclick='returnIframe("/pageView.do","1","分页展示")'>page</a></dd>
            <dd><a href="javascript:;" onclick='returnIframe("/ztreeView.do","2","zTree")'>zTree</a></dd>
            <dd><a href="javascript:;" onclick='returnIframe("/form.do","3","表单")'>form</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item">
          <a href="javascript:;">Two</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:;" onclick='returnIframe("/vue.do","4","vue")'>vue.js</a></dd>
            <dd><a href="javascript:;" onclick='returnIframe("/html5/elephant.html","5","elephant")'>elephant</a></dd>
            <dd><a href="javascript:;" onclick='returnIframe("/html5/winder.html","6","winder")'>winder</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item"><a href="javascript:void(0)" onclick='returnIframe("/html5/cat.html","7","cat")'>cat</a></li>
        <li class="layui-nav-item"><a href="javascript:void(0)" onclick='returnIframe("/html5/winder.html","8","spring")'>spring</a></li>
      </ul>
    </div>
  </div>
  
  <div class="layui-body" style="width:89.4%;height:93%">
    <!-- 内容主体区域 -->
    <div id="divf" style="width:100%;height:100%">
    	<iframe scrolling="no" frameborder="0" width="100%" height="100%"  id="myiframe" src="html5/earth.html"></iframe>
    </div>
    <div class="layui-tab layui-tab-card" lay-filter="demo" lay-allowclose="true" style="display: none">
		<ul class="layui-tab-title"></ul>
		<div class="layui-tab-content"></div>
	</div>
  </div>
  
  <!-- <div class="layui-footer">
    底部固定区域
    © localhost - 心如止水、顺其自然
  </div> -->
</div>
</body>
</html>