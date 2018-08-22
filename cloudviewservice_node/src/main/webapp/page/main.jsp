<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<%@include file="common/global.jsf" %>
<style>
* a{color:#000;}
</style>
</head>
<body>
<%@include file="common/header.jsf" %>

<div class="main-content">
	<div class="left-side">
		<div class="control-btn"></div>
		<div class="absolute-wrap">
			<div class="title">菜单</div>
			<div class="item" id="mainIndex">
				<div class="t icon1"><a href="javascript:void(0)" onclick="gotoMain()">首页</a></div>
			</div>
		<%-- <shiro:hasPermission name="0105000000">
			<div class="item">
				<div class="t icon7">节点管理<span class="arr-icon"></span></div>
				<ul>
					<shiro:hasPermission name="0105010000">
						<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/node/queryByPage">节点管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="0105020000">
						<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/nodeType/queryByPage">节点类型管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="0105030000">
						<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/container/queryByPage">容器管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="0105040000">
						<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/host/queryByPage">云主机管理</a></li>
					</shiro:hasPermission>
				</ul>
			</div>
			</shiro:hasPermission> --%>
		</div>
	</div>
	<div id="mainframe">
		<div class="page-title-bar">
			<span class="title">首页</span>
			<div class="today">今天是 2016年5月12日 星期四</div>
		</div>
		<div class="main main-v2">
			<div class="main-page main-page-v2">
				<div class="title">欢迎使用海恒智能云平台系统</div>
				<div class="version">版本 V 1.1</div>
				<div class="company">深圳市海恒智能技术有限公司</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function(){
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();

		$(".main-page").css({
			"min-height":winH - headerH - pagingBarH -pageTitleBar - 40
		});
	})();
	var url = sessionStorage.getItem("currentMenu");
	
	<%--保存当前访问菜单--%>
/* 	if(!_.isEmpty(url)){
		$("ul li a[data-url='"+url+"']").closest(".item").find("div.t").trigger("click");
		$("ul li a[data-url='"+url+"']").trigger("click");
	} */
	//设置服务器时间
	$.ajax({
		url:"${pageContext.request.contextPath}/login/getTime",
		type:"GET",
	}).done(function(data){
		if(data){
			$(".today").html(data.result);
		}
	});
});

<%--跳转到之前访问的菜单--%>
function toUrl(){
	
	var url = sessionStorage.getItem("currentMenu");
	if(!_.isEmpty(url)){
		$("ul li a[data-url='"+url+"']").closest(".item").find("div.t").trigger("click");
		$("ul li a[data-url='"+url+"']").trigger("click");
	}
}

$(document).on("click","ul li a",function(data){
	var url = $(this).attr("data-url");
	<%--保存当前访问菜单--%>
	sessionStorage.setItem("currentMenu", url);
	$("#mainframe").load(url,"",function(){
		$(".form-dialog-fix .form-dialog").each(function(){
			$(this).attr("date-right",$(this).css("right"));
		});
		
	});
})

function gotoMain(){
	sessionStorage.removeItem("currentMenu");
	window.location.href = "main.jsp";
}

$(function(){
	getUserMenu();
})


<%-- 获取用户的菜单 --%>
function getUserMenu(){
	$.ajax({
		url:"${pageContext.request.contextPath}/login/getUserMenus",
		type:"POST",
		data:""
	}).done(function(data){
		if(data!=null && data.state){
			var list = data.result;
			var html = "";
			for(var i=0;i<list.length;i++){
				
				var item = list[i];
				var subMenu = item.subMenu;
				
				if(subMenu!=null){
					var iconClass = item.menu_mark==null?"icon1":item.menu_mark;
					html+="<div class=\"item\">";
					html+="<div class=\"t "+iconClass+"\">"+item.name+"<span class=\"arr-icon\"></span></div>";
					html+="<ul>";
					for(var j=0;j<subMenu.length;j++){
						var sitem = subMenu[j];
						html+="	<li><a href=\"javascript:void(0)\" data-url=\"${pageContext.request.contextPath}/"+sitem.url+"\">"+sitem.name+"</a></li>";
					}
					html+="</ul>";
					html+="</div>";
				}
			}
			$("#mainIndex").after(html);
			//toUrl();
		}
	});
}
	
</script>
	<%--返回顶部图标 --%>
	<div id="topcontrol" title="回到顶部" style="position: fixed; bottom: 105px; right: 25px; cursor: pointer; display: block;">
		<img src="${pageContext.request.contextPath}/static/css/images/backTop.png" style="width:31px; height:31px;">
	</div>
</body>
</html>