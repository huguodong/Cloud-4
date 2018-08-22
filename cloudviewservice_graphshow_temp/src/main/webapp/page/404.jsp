<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>出错啦~</title>
<style type="text/css">
a,fieldset,img{border:0;}
body{font-family:serif;font-size:24px;color:#BABABA;}
a{color:#221919;text-decoration:none;outline:none;font-size:18px;}
a:hover{color:#3366cc;text-decoration:underline;}
a.link,p,img,.error_code{-webkit-transition:opacity 1s ease-in-out;-moz-transition:opacity 1s ease-in-out;transition:opacity 1s ease-in-out;opacity:0;}
.small{font-size:18px;}
#wrapper{text-align:center;margin:100px auto;width:594px;}
a.link{text-shadow:0px 1px 2px white;font-weight:400;color:#3366cc;}
div{margin-left:auto;margin-right:auto;}
.error_code{height:200px;width:200px;border:8px solid #ddd;border-radius:50%;-webkit-border-radius:50%;line-height:200px;vertical-align:middle;text-align:center;font-size:100px;font-weight:800;}
p{text-shadow:0px 1px 2px white;font-weight:normal;font-weight:200;}
.fade{opacity:1;}
@media only screen and (min-device-width:320px) and (max-device-width:480px){
	#wrapper{margin:40px auto;text-align:center;width:280px;}
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<!--解决 IE6 背景缓存-->
<!--[if IE 6]><script type="text/javascript">document.execCommand("BackgroundImageCache", false, true);</script><![endif]-->
<script type="text/javascript">
$(document).ready(function(){
	$(".error_code").addClass('fade').delay(800).queue(function(next){
		$("h1,p").addClass("fade");
		$("a.link").css("opacity", 1);
		next();
	});
});
</script>
</head>

<body>
<div id="wrapper">
   <div class="error_code">404</div>
   <div>
      <p>唉呀!</p>
      <p>您访问的页面无法找到！请尝试重新访问</p>
      <p class="small">您可以：
		<a href="javascript:;" class="link" onclick="history.go(-1)">返回上一页</a>
		<span class="ml-20">|</span>
		<a class="link" href="${pageContext.request.contextPath}/index">返回首页</a>
	  </p>
      <p class="small"><span id="sec">10</span> 秒后自动跳转到首页</p>
   </div>
</div>

<script type="text/javascript">
$(function () {            
   setTimeout("lazyGo();", 1000);
});
function lazyGo() {
	var sec = $("#sec").text();
	$("#sec").text(--sec);
	if (sec > 0)
		setTimeout("lazyGo();", 1000);
	else
		window.location.href = "${pageContext.request.contextPath}/index";
}
</script>
</body>
</html>
