<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ztree</title>
<script src="/resources/js/jquery.min.js"></script>
<link rel="stylesheet" href="/resources/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/resources/zTree_v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/resources/zTree_v3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="/resources/zTree_v3/js/jquery.ztree.exedit.js"></script>
<style type="text/css">
.ztree li span.button.icon02_ico_docu,.ztree li span.button.icon02_ico_close{
	background: url(/resources/zTree_v3/css/zTreeStyle/img/diy/1_close.png)
}
.ztree li span.button.icon02_ico_open{
	background: url(/resources/zTree_v3/css/zTreeStyle/img/diy/1_open.png)
}
.ztree li span.button.icon03_ico_docu{
	background: url(/resources/zTree_v3/css/zTreeStyle/img/diy/3.png)
}
.ztree li span.button.pIcon01_ico_open,.ztree li span.button.pIcon01_ico_close{
	background: url(/resources/zTree_v3/css/zTreeStyle/img/diy/7.png)
}
.ztree li span.button.pIcon01_ico_docu{
	background: url(/resources/zTree_v3/css/zTreeStyle/img/diy/7.png)
}
</style>
<script type="text/javascript">
$(function() {
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0
			}
		},
		callback:{
			onClick:ck
		}
	};
	var zNodes;
	var treeNode_1;
	$.ajax({
		url : 'dept/aJsonObject.do',
		type : 'get', //GET
		async : true, //或false,是否异步
		dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data) {
			console.log(data);
			zNodes = data.zNodes;
			$.fn.zTree.init($("#ztree"), setting, zNodes);
		},
		error : function(data) {
			alert("错误");
		}
	})

})
function ck(){
	alert("click");
}
</script>
</head>
<body>
<div id="ztree" class="ztree"></div>
</body>
</html>