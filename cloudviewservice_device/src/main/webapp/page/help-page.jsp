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
				<shiro:hasPermission name="0102010000"><%-- 用户管理 --%>
					<div class="item">
						<div class="title">用户管理</div>
						<ul>
							<shiro:hasPermission name="0102010100"><%--用户管理 --%>
								<li class=""><a data-url="${pageContext.request.contextPath}/page/common/help/opermgmt/opermgmt.jsp" href="javascript:void(0);">用户管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102010200"><%--用户组管理 --%>
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/opermgmt/opergroup.jsp" href="javascript:void(0);">操作员分组管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102020300"><%--密码模板管理 --%>
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/opermgmt/soxtemp.jsp" href="javascript:void(0);">用户鉴权模板管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102020400">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/opermgmt/authority.jsp" href="javascript:void(0);">权限分组</a></li>
							</shiro:hasPermission>
						</ul>
					</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0102020000"><%-- 图书馆管理 --%>
					<div class="item">
						<div class="title">图书馆管理</div>
						<ul>
							<shiro:hasPermission name="0102020100">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/librarymgmt/librarymgmt.jsp" href="javascript:void(0);">图书馆管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102020200">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/librarymgmt/libraryconfig.jsp" href="javascript:void(0);">图书馆模板管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102030100">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/librarymgmt/config-ACS.jsp" href="javascript:void(0);">ACS服务模板配置</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102030200">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/librarymgmt/system-readertype.jsp" href="javascript:void(0);">读者流通类型管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102031300">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/librarymgmt/system-card.jsp" href="javascript:void(0);">维护卡管理</a></li>
							</shiro:hasPermission>
						</ul>
					</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0102030000"><%-- 设备管理--%>
					<div class="item">
						<div class="title">设备管理</div>
						<ul>
							<shiro:hasPermission name="0102030500">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/devmgmt/devicemgmt.jsp" href="javascript:void(0);">设备管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102031200">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/devmgmt/devicetype.jsp" href="javascript:void(0);">设备类型</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102030600">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/devmgmt/devicegroup.jsp" href="javascript:void(0);">设备分组</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102030700">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/devmgmt/devicemonitor-config.jsp" href="javascript:void(0);">设备监控模板配置</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102030800">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/devmgmt/device-exttemp-manage.jsp" href="javascript:void(0);">设备硬件模板配置</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102030900">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/devmgmt/device-runtemp-manage.jsp" href="javascript:void(0);">设备运行模板配置</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102031000">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/devmgmt/databasesync-config.jsp" href="javascript:void(0);">设备数据同步模板配置</a></li>
							</shiro:hasPermission>
						</ul>
					</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0102040000">
					<div class="item">
						<div class="title">系统管理</div>
						<ul>
							<shiro:hasPermission name="0102040200">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/sysmgmt/system-databackup.jsp" href="javascript:void(0);">数据库备份</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="0102040100">
								<li><a data-url="${pageContext.request.contextPath}/page/common/help/sysmgmt/system-log.jsp" href="javascript:void(0);">系统日志</a></li>
							</shiro:hasPermission>
						</ul>
					</div>
				</shiro:hasPermission>
				<c:if test="${sessionScope.oper.operator_type=='1'}">
					<div class="item">
						<div class="title">升级管理</div>
						<ul>
							<li><a data-url="${pageContext.request.contextPath}/page/common/help/upgrade/upgrade-manage.jsp" href="javascript:void(0);">升级模板配置</a></li>
						</ul>
					</div>
				</c:if>
				<shiro:hasPermission name="0101000000">
					<div class="item">
						<div class="title">设备注册</div>
						<ul>
							<li><a data-url="${pageContext.request.contextPath}/page/common/help/devregister/dev-register.jsp" href="javascript:void(0);">设备注册</a></li>
						</ul>
					</div>
				</shiro:hasPermission>
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


