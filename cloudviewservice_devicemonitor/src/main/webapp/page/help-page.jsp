<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<%@include file="common/global.jsf" %>
<style type="text/css">
.help-page .main-container {
    position: relative;
    border: 1px solid #DDD;
    border-radius: 4px;
    background-color: #FFF;
    overflow: scroll;
    zoom: 1;
}

</style>
</head>
<body>
<div class="help-page">
<%@include file="common/header.jsf" %>
	<div class="page-title-bar">
		<span class="title">帮助</span>
		<!-- <div class="form-wrap fr">
			<input type="text" name="" id="" class="input g-input" placeholder="搜索问题" />
			<div class="btn search">查询</div>
		</div> -->
	</div>
	<div class="main">
		<div class="main-container">
			<div class="con-left">
				<shiro:hasPermission name="0103000000">
					<div class="item">
						<div class="title">设备监控</div>
						<ul>
							<li><a data-url="${pageContext.request.contextPath}/page/common/help/devmonitor/devmonitor.jsp" href="javascript:void(0);">设备监控</a></li>
						</ul>
					</div>
				</shiro:hasPermission>
			</div>
			<div class="con-right">
		<!-- 		<ul>
					<li>
						在企业内新建项目
						<div class="con-box">
							点击「创建」－创建项目，输入项目名称，在拥有者下拉菜单内选择企业名称。在企业内创建的项目都属于企业项目。
企业项目享有更多功能：快速导入企业成员到项目，参与企业统计，项目分组、排序等。
						</div>
					</li>
				</ul> -->
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	var leftH = $(".con-left").height();
	var rightH = $(".con-right").height();
	if(leftH<900){
		leftH=900;
	}
	if(leftH > rightH){
		$(".con-right").css("min-height",leftH);
	}
	var url="${url}";
	var contextPath="${pageContext.request.contextPath}";
	console.log(contextPath+url);
	if(url){
		$("a[data-url='"+contextPath+url+"']").trigger("click");
	}
});

$(document).on("click","ul li a",function(data){
	var url = $(this).attr("data-url");
	console.log("url",url);
	if(url){
		$(".con-right").load(url,"",function(){
			/* 	
				$(".form-dialog-fix .form-dialog").each(function(){
					$(this).attr("date-right",$(this).css("right"));
				}); 
			*/
		});
		$("li").removeClass("active");
		$(this).parent().addClass("active");
	}
});
</script>
</body>
</html>


