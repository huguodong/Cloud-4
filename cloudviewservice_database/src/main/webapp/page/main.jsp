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
		</div>
	</div>
	<div id="mainframe">
		<div class="page-title-bar">
			<span class="title">首页</span>
			<div class="today">今天是 2016年5月12日 星期四</div>
		</div>
		<div class="main">
			<div class="main-page">
				<div class="machine-list" id="machine-list">
				</div>
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
	
	$("#topcontrol").click(function() {
		   $("html,body .main").animate({scrollTop:0}, 500);
	 }); 

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
		var dbName = sessionStorage.getItem("dbName");
		if(!_.isEmpty(dbName)){
			$("ul li a[data-url='"+url+"']").closest(".item2").find("#"+dbName).trigger("click");
			var tabId = sessionStorage.getItem("tabId");
			if(!_.isEmpty(tabId)){
				$("ul li a[data-url='"+url+"']").closest(".item2").find("#"+tabId).trigger("click");
			}
		}
	}
}

$(document).on("click","ul .addtab a",function(data){
	var url = $(this).attr("data-url");
	var tabId = $(this).attr("id");
	var dbName = $(this).closest(".item2").find("div.t2").attr("id");
	<%--保存当前访问菜单--%>
	sessionStorage.setItem("currentMenu", url);
	sessionStorage.setItem("dbName", dbName);
	sessionStorage.setItem("tabId", tabId);
	var databaseName = $(this).parents(".item2").find(".t2").attr("id");
	var server_id = $(this).parents(".item2").parents(".item").find(".t").attr("id");
	var param = {
			"databaseName" : databaseName,
			"server_id" : server_id
		};
	var data={
			"req":JSON.stringify(param)
	};
	$("#mainframe").load(url,data,function(){
		$(".form-dialog-fix .form-dialog").each(function(){
			$(this).attr("date-right",$(this).css("right"));
		});
		
	});
});

$(document).on("click","ul .tabxx a",function(data){
	var url = $(this).attr("data-url");
	var tabId = $(this).attr("id");
	var dbName = $(this).closest(".item2").find("div.t2").attr("id");
	<%--保存当前访问菜单--%>
	sessionStorage.setItem("currentMenu", url);
	sessionStorage.setItem("dbName", dbName);
	sessionStorage.setItem("tabId", tabId);
	var tableName = $(this).text();
	var databaseName = $(this).parents(".item2").find(".t2").attr("id");
	var server_id = $(this).parents(".item2").parents(".item").find(".t").attr("id");
	var param = {
			"name" : tableName,
			"databaseName" : databaseName,
			"server_id" : server_id
		};
	var data={
			"req":JSON.stringify(param)
	};
	$("#mainframe").load(url,data,function(){
		$(".form-dialog-fix .form-dialog").each(function(){
			$(this).attr("date-right",$(this).css("right"));
		});
		
	});
});

function gotoMain(){
	sessionStorage.removeItem("currentMenu");
	sessionStorage.removeItem("dbName");
	sessionStorage.removeItem("tabId");
	window.location.href = "main.jsp";
}

$(function(){
	getUserMenu();
	getServerConfig();
})

var isOpenAddPage = false;
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
				var server = list[i];
				var type = server.type;
				if(server!=null){
					if(type == "Mysql"){
						var dbs = server.dbs;
						var conClass = "icon2";
						html+="<div class=\"item\">";
						html+="<div class=\"t "+conClass+"\" id=\""+server.id+"\">"+server.host+"<span class=\"arr-icon\"></span></div>";
						
						if(dbs!=null){
							for(var j=0;j<dbs.length;j++){
								var db = dbs[j];
								var databaseClass = "icon3";
								html+="<div class=\"item2\">";
								html+="<div class=\"t2 "+databaseClass+"\" id=\""+db.name+"\">"+db.name+"<span class=\"arr-icon2\"></span></div>";
								var tables = db.tables;
								if(tables!=null){
									html+="<ul>";
									html+="<li class=\"addtab\"><a href=\"javascript:void(0)\" id=\""+db.name+"addtab\" data-url=\"${pageContext.request.contextPath}/table/addtab\">新建</a></li>";
									for(var k=0;k<tables.length;k++){
										var tab = tables[k];
										html+="	<li class=\"tabxx\"><a href=\"javascript:void(0)\" id=\""+db.name+tab.name+"\" data-url=\"${pageContext.request.contextPath}/table/properties\">"+tab.name+"</a></li>";
									}
									html+="</ul>";
								}
								html+="</div>";
							}
						}
						html+="</div>";
					}else if(type == "Mongo"){
						var dbs = server.mdbs;
						var conClass = "icon4";
						html+="<div class=\"item\">";
						html+="<div class=\"t "+conClass+"\" id=\""+server.id+"\">"+server.host+"<span class=\"arr-icon\"></span></div>";
						
						if(dbs!=null){
							for(var j=0;j<dbs.length;j++){
								var db = dbs[j];
								var databaseClass = "icon3";
								html+="<div class=\"item2\">";
								html+="<div class=\"t2 "+databaseClass+"\" id=\""+db.name+"\">"+db.name+"<span class=\"arr-icon2\"></span></div>";
								var collections = db.collections;
								if(collections!=null){
									html+="<ul>";
									html+="<li class=\"addtab\"><a href=\"javascript:void(0)\" id=\""+db.name+"addcollection\" data-url=\"${pageContext.request.contextPath}/collection/addcollection\">新建</a></li>";
									for(var k=0;k<collections.length;k++){
										var col = collections[k];
										html+="	<li class=\"tabxx\"><a href=\"javascript:void(0)\" id=\""+db.name+col.name+"\" data-url=\"${pageContext.request.contextPath}/collection/properties\">"+col.name+"</a></li>";
									}
									html+="</ul>";
								}
								html+="</div>";
							}
						}
						html+="</div>";
					}
					
				}
			}
			$("#mainIndex").after(html);
			toUrl();
		}
	});
}

<%-- 获取用户的菜单 --%>
function getServerConfig(){
	$.ajax({
		url:"${pageContext.request.contextPath}/login/getServerConfig",
		type:"POST",
		data:""
	}).done(function(data){
		if(data!=null && data.state){
			var list = data.result;
			var html = "";
			for(var i=0;i<list.length;i++){
				var server = list[i];
				if(server!=null){
					html+="<div class=\"item-wrap\">";
					html+="<div class=\"item active\">";
					html+="&nbsp;&nbsp;"+server.type;
					html+="<div class=\"sec1\">";
					html+="<span class=\"machine-name\">"+server.host+":"+server.port+"</span>";
					html+="</div>";
					html+="<div class=\"sec2\">";
					html+="<ul>";
					/* html+="<li>用户名：<span class=\"right\">"+server.user+"</span></li>";
					html+="<li>密码：<span class=\"right\">"+server.password+"</span></li>"; */
					html+="<li>用户名：<span class=\"right\">******</span></li>";
					html+="<li>密码：<span class=\"right\">******</span></li>";
					html+="<li>描述：<span class=\"right\">"+server.description+"</span></li>";
					html+="</ul>";
					html+="</div>";
					html+="<div class=\"sec3\">";
					html+="<div class=\"check-detail\" onclick=\"removeServerBtnEven("+server.id+",'"+server.type+"')\">关闭</div>";
					html+="</div>";
					html+="</div>";
					html+="</div>";
				}
			}
			html+="<div class=\"item-wrap\">";
			html+="<div class=\"item active\">";
			html+="<button class=\"addServer\" onclick=\"addServerBtnEven()\">点击添加一个数据库连接</button>";
			html+="</div>";
			html+="</div>";
			$("#machine-list").append(html);
		}
	});
}

function addServerBtnEven(){
	if(isOpenAddPage){
		GlobalShowMsg({showText:"请先保存",status:false});
		return;
	}
	isOpenAddPage = true;
	var html = "";
	html+="<div class=\"item-wrap\">";
	html+="<div class=\"item active\">";
	html+="	&nbsp;&nbsp;新建连接";
		html+="	<div class=\"sec4\">";
		html+="<ul>";
			html+="<li>数据库类型：";
				html+="<div class=\"right\">";
				html+="<div class=\"g-select-1\">";
					html+="<select id=\"db_type\">";
						html+="<option value=\"\">请选择</option>";
						html+="<option value=\"Mysql\">Mysql</option>";
						html+="<option value=\"Mongo\">Mongo</option>";
					html+="</select>";
					html+="<span class=\"arr-icon\"></span>";
				html+="</div>";
				html+="</div>";
			html+="</li>";
			html+="<li>IP地址：";
				html+="<div class=\"right\">";
				html+="<input type=\"text\" name=\"host\" id=\"host\" class=\"g-input-2\" placeholder=\"请输入\"  />";
				html+="</div>";
			html+="</li>";
			html+="<li>端口：";
				html+="<div class=\"right\">";
				html+="<input type=\"text\" name=\"port\" id=\"port\" class=\"g-input-2\" placeholder=\"请输入\"  />";
				html+="</div>";
			html+="</li>";
			html+="<li>用户名：";
				html+="<div class=\"right\">";
				html+="<input type=\"text\" name=\"user\" id=\"user\" class=\"g-input-2\" placeholder=\"请输入\"  />";
				html+="</div>";
			html+="</li>";
			html+="<li>密码：";
				html+="<div class=\"right\">";
				html+="<input type=\"text\" name=\"password\" id=\"password\" class=\"g-input-2\" placeholder=\"请输入\"  />";
				html+="</div>";
			html+="</li>";
			html+=" <li>描述：";
				html+="<div class=\"right\">";
				html+="<input type=\"text\" name=\"description\" id=\"description\" class=\"g-input-2\" placeholder=\"请输入\"  />";
				html+="</div>";
			html+="</li>";
		html+="</ul>";
		html+="</div>";
		html+="<div class=\"sec5\">";
			html+="<div class=\"check-detail\" onclick=\"addSaveBtnEven()\">保存</div>";
			html+="</div>";
		html+="</div>";
	html+="</div> ";
	$("#machine-list").append(html);
}

function addSaveBtnEven(){
	var host = $("#host").val();
	var port = $("#port").val();
	var user = $("#user").val();
	var password = $("#password").val();
	var description = $("#description").val();
	var type = $("#db_type  option:selected").val();
	var param={
			"host":host,
			"port":port,
			"user":user,
			"password":password,
			"description":description,
			"type":type
		};
	$.ajax({
		url:"${pageContext.request.contextPath}/server/addDBConnect",
		type:"POST",
		data:{"req":JSON.stringify(param)}
	}).done(function(data){
		if(data!=null && data.state){
			GlobalShowMsg({showText:"操作成功",status:true});
			gotoMain();
		}
	});
}

function removeServerBtnEven(id,type){
	var param={
			"id":id,
			"type":type
		};
	$.ajax({
		url:"${pageContext.request.contextPath}/server/removeDBConnect",
		type:"POST",
		data:{"req":JSON.stringify(param)}
	}).done(function(data){
		if(data!=null && data.state){
			GlobalShowMsg({showText:"操作成功",status:true});
			gotoMain();
		}
	});
}

function toNewTable(dbname,tabname){
	gotoMain();
	var url = '${pageContext.request.contextPath}/table/properties';
	var tabId = dbname+tabname;
	var dbName = dbname;
	<%--保存当前访问菜单--%>
	sessionStorage.setItem("currentMenu", url);
	sessionStorage.setItem("dbName", dbName);
	sessionStorage.setItem("tabId", tabId);
	toUrl();
}

function toNewCollection(dbname,tabname){
	gotoMain();
	var url = '${pageContext.request.contextPath}/collection/properties';
	var tabId = dbname+tabname;
	var dbName = dbname;
	<%--保存当前访问菜单--%>
	sessionStorage.setItem("currentMenu", url);
	sessionStorage.setItem("dbName", dbName);
	sessionStorage.setItem("tabId", tabId);
	toUrl();
}
	
</script>
	<%--返回顶部图标 --%>
	<div id="topcontrol" title="回到顶部" style="position: fixed; bottom: 105px; right: 25px; cursor: pointer; display: block;">
		<img src="${pageContext.request.contextPath}/static/css/images/backTop.png" style="width:31px; height:31px;">
	</div>
</body>
</html>