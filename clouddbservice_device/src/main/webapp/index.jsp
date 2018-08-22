<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <title>Welcome</title>

    <link href="bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.12.2.min.js"></script>
    <script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>


  </head>
  <body >
<h1>CloudServer v1.0</h1>
	<ul>
		<li>
		    <a href="<%=basePath%>dbsynctest.jsp">模板配置同步</a>
		</li>
		<li>
		    <a href="<%=basePath%>selfcheck.jsp">自助设备模板</a>
		</li>
		<li>
		    <a href="<%=basePath%>monitorconf.jsp">监控模板</a>
		</li>
		<li>
			<a href="<%=basePath%>register.jsp">设备注册</a>
		</li>
		<li>
			<a href="<%=basePath%>apitest.jsp">测试分页一</a>
		</li>
		<li>
			<a href="<%=basePath%>apitest1.jsp">测试分页二</a>
		</li>
		<li>
			<a href="<%=basePath%>uploadcfg.jsp">设备端上传数据测试</a>
		</li>
		
		<li>
			<a href="<%=basePath%>shelf.jsp">书架测试</a>
		</li>
	</ul>
</html>