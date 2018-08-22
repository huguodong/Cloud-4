<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
	<base href="<%=basePath%>">
    <%@include file="../common/global.jsf" %>
    <style>
        * a{color:#000;}
    </style>
</head>
<body>
<%@include file="../common/header.jsf" %>
<%@ include file="../include/machineServer.jsf" %>
<div class="main-content" style="backgroud-color:white">
    <div class="left-side">
        <div class="control-btn"></div>
        <div class="absolute-wrap">
            <div class="title"><span>菜单</span></div>
            <div class="item" id="mainIndex">
                <!-- <div class="t icon1"><a href="javascript:void(0)" onclick="gotoMain()">aaa</a></div> -->
            </div>
        </div>
    </div>
    <div id="mainframe">
        <div class="page-title-bar">
            <div class="today">今天是 2016年5月12日 星期四</div>
        </div>
        <div class="main" style="backgroud-color:white">
            <div class="main-page">
                <div class="version"> V 1.0</div>
                <div class="company">深圳市海恒智能技术有限公司</div>
            </div>
        </div>
    </div>
</div>
<%--返回顶部图标 --%>
<div id="topcontrol" title="回到顶部" style="position: fixed; bottom: 40px; right: 25px; cursor: pointer; display: block;">
    <img src="${pageContext.request.contextPath}/static/css/images/backTop.png" style="width:31px; height:31px;">
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
	});
	var layerOpen;
	<%--跳转到之前访问的菜单--%>
    function toUrl(){
    	var currentMenu = sessionStorage.getItem("currentMenu");
        if(!_.isEmpty(currentMenu)){
        	//$("#mainIndex").html("");
            $("ul li a[data-url='"+currentMenu+"']").closest(".item").find("div.t").trigger("click");
            $("ul li a[data-url='"+currentMenu+"']").trigger("click");
        }
    }
    
    $(document).on("click","ul li a",function(data){
        var url = $(this).attr("data-url");
        var machineServer = $(this).attr("id");
        <%--保存当前访问菜单--%>
        sessionStorage.setItem("currentMenu", url);
        $("#mainframe").load(url,"",function(){
            $(".form-dialog-fix .form-dialog").each(function(){
                $(this).attr("date-right",$(this).css("right"));
            });
           
            //如果点击的为服务器设备
            if(machineServer=="machineServerId"){
            	var Page=makeQueryParam2(1,12);
    			selectPage2(Page);
            } 
        });
    })
    
    function gotoMain(){
        sessionStorage.removeItem("currentMenu");
        window.location.href = "${pageContext.request.contextPath}/login/main";
    }
    var html = "";
    getUserMenu();
    
    <%-- 获取用户的菜单 --%>
    function getUserMenu(){
        html+="<div class=\"item\">";
       	html+="<div class=\"t icon1\">监控<span class=\"arr-icon\"></span></div>";
       	html+="<ul id=\"left_menu\">";
       	
       	html+="	<li><a href=\"javascript:void(0)\" data-url=\"${pageContext.request.contextPath}/page/devmgmt/machinelib.jsp\">设备监控</a></li>";
       	html+="	<li><a href=\"javascript:void(0)\" id=\"machineServerId\" data-url=\"${pageContext.request.contextPath}/page/devmgmt/machineService.jsp\">服务监控</a></li>";
       	
       	html+="</ul>";
       	html+="</div>";
       	$("#mainIndex").after(html);
       	toUrl();
       	$(".main-content").addClass("unfold");
    }
    
    var initUrl = $("#left_menu li:first a").attr("data-url");
    $("#mainframe").load(initUrl,"",function(){
        	
    });
</script>
</body>
</html>