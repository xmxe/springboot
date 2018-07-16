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
<title>page分页</title>
<script src="js/jquery.min.js" ></script>
<script src="layui/layui.js"></script>
<link rel="stylesheet" href="layui/css/layui.css" />
<script type="text/javascript">
	var next,pre,pageCount;
	//当整个页面加载完毕时执行
	$(function(){
		queryData(1);
		//~~~~~~~注册点击事件
		$("#first").click(function(){
			queryData(1);
		});
		$("#next").click(function(){
			queryData(next);
		});
		$("#pre").click(function(){
			queryData(pre);
		});
		$("#end").click(function(){
			queryData(pageCount);
		});
	})
		function queryData(current){
			$.ajax({
				type:"post",
				url:"page.do", //currentPage=1&tj=zhangsan
				data:"currentPage="+current,
				dataType:"json",
				success:function(msg){// msg======>page
					console.log(msg);
					next = msg.jo.next;
					pre = msg.jo.pre;
					pageCount = msg.jo.pageCount;
					var trs="";
					$.each(msg.jo.rows,function(i,book){
						trs+="<tr><td>"+book.bookname+"</td><td>"+book.bookauthor
						+"</td><td>"+book.bookprice+"</td></tr>";
					});
					$("#data").html(trs);
					$("#pageCount").html("共"+pageCount+"页"+ msg.jo.total+"条记录 " + "当前为第"+current+"页");
				}
			});	
		}
	var tj="";
	function searchbyconditions(){
		tj = $("input[name='tj']").val();
		if(tj!="" && tj!=null){
			tj="&tj="+tj;
		}
		queryData(1,tj);
	}
	</script>
  </head>
  
  <body>
  	<div style="text-align:center">
  		<button type="button" class="layui-btn layui-btn-normal" onclick="searchbyconditions()">查询</button>
	    <table class="layui-table" lay-even="" lay-skin="row">
	    	<thead>
	    		<tr style="height:50px">
	    			<th style="text-align:center;font-family: 宋体;font-size:30px">名字</th>
	    			<th style="text-align:center;font-family: 宋体;font-size:30px">作者</th>
	    			<th style="text-align:center;font-family: 宋体;font-size:30px">价格</th>
	    		</tr>
	    	</thead>
	    	<tbody id="data"></tbody>	
	    </table>
	    <span id="pageCount"></span>
	    <button type="button" id="first" class="layui-btn layui-btn-radius">首页</button>
	    <button type="button" id="pre" class="layui-btn layui-btn-radius">上一页</button>
	    <button type="button" id="next" class="layui-btn layui-btn-radius">下一页</button>
	    <button type="button" id="end" class="layui-btn layui-btn-radius">尾页</button>
    </div>
  </body>
</html>