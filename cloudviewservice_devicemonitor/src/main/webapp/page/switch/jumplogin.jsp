<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
  </head>
    <script type="text/javascript" src="static/js/jquery.min.js" ></script>
  
  <body>
<script type="text/javascript">
 $(function(){
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		$(".page-switch").css({
			"min-height":winH - headerH
		});
	})();
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];  
	var username=getQueryString("name");
	var pwd = '<%=request.getParameter("pwd")%>';
	var url = '<%=request.getParameter("url")%>';
	if(username==null || username.length<=0){
		window.location.href = url+"/switchIndex.html";
	}
	if(pwd==null || pwd.length<=0){
		window.location.href = url+"/switchIndex.html";
	}
	var params={
			"operator_id":username,
			"operator_pwd":pwd,
			"rememberme":"false"
		}
      $.ajax({
          type: "post", // 提交方式 get/post
          url: basePath+"/login/logincheck",// 需要提交的 url
          data:{"json":JSON.stringify(params),"controllerinfo":"switch","url":url},
          success: function(result) { // data 保存提交后返回的数据，一般为 json 数据
              // 此处可对 data 作相关处理
          	if(result.state==true){
			window.location.href = basePath+"/device/main";
		}else if(result.state==false){
			window.location.href = url+"/switchIndex.html";
		}
       }
      });
});
 function getQueryString(name) { 
	 var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	 var r = window.location.search.substr(1).match(reg); 
	 if (r != null) return decodeURI(r[2]); return null;
}
</script>
</body>
</html>


