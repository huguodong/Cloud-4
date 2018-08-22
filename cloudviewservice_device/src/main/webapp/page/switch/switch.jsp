<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>主功能页</title>
<link rel="stylesheet" type="text/css" href="../static/css/style.css" />
<script type="text/javascript" src="../static/js/jquery.min.js" ></script>
<script type="text/javascript" src="../static/js/common.js" ></script>
<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="css/ie.css" />
<![endif]-->
<!--[if lt IE 9]>
	<script type="text/javascript" src="js/html5.js" ></script>
	<script type="text/javascript" src="js/ie.js" ></script>
<![endif]-->
<%@include file="../common/global.jsf"%>
<style>
* a {
	color: #000;
}
</style>
</head>
<body>
<%@include file="../common/header.jsf"%>
<div class="page-switch">
	<div class="title">欢迎使用海恒智能云平台系统</div>
	<div class="version">版本 V 1.0</div>
	<div class="page-switch-list">
		<ul>
			<li style="background-color:#A9DBF1">
				<a id="cloudviewservice_device">
					<img src="../static/images/switch-list-1.png" alt="" />
					后台管理
				</a>
			</li>
			<li style="background-color:#A9DBF1">
				<a id="cloudviewservice_devicemonitor">
					<img src="../static/images/switch-list-2.png" alt="" />
					设备监控
				</a>
			</li>
			<li style="background-color:#A9DBF1">
				<a id="cloudviewservice_register">
					<img src="../static/images/switch-list-3.png" alt="" />
					设备注册
				</a>
			</li>
			<li style="background-color:#A9DBF1">
				<a id="cloudviewservice_statistics">
					<img src="../static/images/switch-list-4.png" alt="" />
					查询统计
				</a>
			</li>
		</ul>
	</div>
	<div class="company">深圳市海恒智能技术有限公司</div>
</div>
<style>
.page-switch{

	background-color: #D0F1FF;
	color:  #00A2E9;
	text-align: center;
}
.page-switch .company{
	position: fixed;
	bottom:50px;
	width: 100%;
	text-align: center;
}
.page-switch .title{
	padding: 50px 0 10px;
	font-size: 24px;
}
.page-switch-list {
	padding-left: 20px;
	overflow: hidden;
	
	width: 828px;
	margin: 0 auto;
	margin-top: 60px;
}
.page-switch-list li{
	margin-right: 20px;
	width: 185px;
	border:1px solid #00A2E9;
	float: left;
}
.page-switch-list li a{
	display: block;
	padding: 30px 0;
	color:#333;
	font-size: 16px;
}
.page-switch-list li a:hover{
	background-color: #A9DBF1;
}
.page-switch-list li img{
	padding: 25px 0;
	display: block;
	margin: 0 auto;
}
</style>
<script type="text/javascript">
$(function(){
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		$(".page-switch").css({
			"min-height":winH - headerH
		});
	})();
	$("#systemLogout").attr("href","../switchlogin/logout");
	var params={
			"node_name":"cloudviewservice_device,cloudviewservice_devicemonitor,cloudviewservice_register,cloudviewservice_statistics"
		}
    $.ajax({
        type: "post", // 提交方式 get/post
        url: "../switchlogin/testAuthority",// 需要提交的 url
        data:{"json":JSON.stringify(params)},
        success: function(data) {
            console.log(data);
        	if(data.result !=null){
        		var name = data.result.split(",");
          	    for(var i=0;i<name.length;i++){
            		var id ="#"+name[i];
            		$(id).attr("href","../switchlogin/jumplogin?node_name="+name[i]);
            		$(id).parent().removeAttr("style");
            	  }
        	}
        	if(data.retMessage !=null){
	        	var ret = data.retMessage.split(";");
	        	for(var n=0;n<ret.length;n++){
	        		var re = ret[n].split("#");
	        		var id ="#"+re[0];
	        		$(id).attr("href","javascript:prompt('"+re[1]+"');");
	        	}
        	}
        }
    });
});
function prompt(content){
	layer.alert(content);
}
</script>
</body>
</html>