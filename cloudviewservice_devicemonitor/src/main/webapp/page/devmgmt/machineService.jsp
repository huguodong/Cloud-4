<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><!--<html><![endif]-->
<head><%-- 
<%@ include file="../common/global.jsf" %> --%><%-- 
<%@ include file="../../static/js/machineServer.jsf" %> --%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/style2.css">
<style type="text/css">
	.rllStyle{
		display: none;
		position: fixed;
		z-index: 900;
		top:0;
		left:0;
		width: 100%;
		height: 100%;
	}
	.rllStyle .shade2{
		position: absolute;
		top:0;
		left:60px;
		width: 100%;
		height: 100%;
		background-color: rgba(0,0,0,.5);
	}
	.rllStyle .rll-dialog{
	    display: block;
	    position: absolute;
	    top: 0;
	    bottom: 0;
	    padding-top: 0px;
	    width: 90%;
		left:-190%;
	    -webkit-box-sizing: border-box;
	    -moz-box-sizing: border-box;
	    box-sizing: border-box;
	}
	.rll-dialog{
		display: none;
		margin:auto;
		width: 100%;
		background-color: #fff;
		/* overflow: auto;
		zoom: 1; */
	}
	.w-900  .rll-dialog{
		width: 900px;
		right:  0px;
		/* left:-100%; */
	}
	.w-900  .form-dialog{
		width: 900px;
		right:  0px;
	} 
	.form-dialog-fix .shade{
		position: absolute;
		top:60px;
		left:0;
		width: 100%;
		height: 100%;
		background-color: rgba(0,0,0,.5);
	}
	/**sec3**/
	.machine-list2 .item2.gray .sec3 .status2 {
   	 	background: url(${ctx}/static/images/cir-status4.png) right center no-repeat;
	}
	 .machine-list2 .item2.blue .sec3 .status2{
   	 	background: url(${ctx}/static/images/cir-status5.png) right center no-repeat;
	}
	.machine-list2 .item2.alert .sec3 .status2 {
   	 	background: url(${ctx}/static/images/cir-status3.png) right center no-repeat;
	}
	/**sec2**/
	.machine-list2 .item2.gray .sec2 .status2 {
    	background: url(${ctx}/static/images/cir-status4.png) right center no-repeat;
	}
	.machine-list2 .item2.blue .sec2 .status2 {
    	background: url(${ctx}/static/images/cir-status5.png) right center no-repeat;
	}
	.machine-list2 .item2.alert .sec2 .status2 {
    	background: url(${ctx}/static/images/cir-status3.png) right center no-repeat;
	}
	.page-title-bar2 .choose-lib2 .select-btn2 {
		width:300px;
	    height: 26px;
	    text-indent: 7px;
	    line-height: 26px;
	    border: 1px solid #DDD;
	    color: #888888;
	    background-color: #f6f6f6;
	    border-radius: 2px;
	}
	.page-title-bar2 .choose-lib2 .drop-down2 .item2{
		text-align:center
	}
	.page-title-bar2 .smalltitle2 {
	    float: left;
	    font-weight: bold;
	    color: #333333;
	}
	.page-title-bar2 .form-wrap1 {
	    padding-top: 11px;
	    color: #888;
	    line-height: 28px;
	}
	.fr {
	    float: right;
	}
	.machine-list2 .item-wrap2 {
		width:92%;
		height:100%;
		margin-left:8%; 
	}
	.machine-list2 .item2{
		float:center;
		padding-right: 8px;
	    padding-left: 8px;
		margin-bottom: 15px;
		margin:15px 15px 15px 15px;
		width: 290px;
		height: 200px;
		font-size: 12px;
		color:#888; 
		text-align: left;
		border:3px solid #ddd;
		background-color: #fff;
		border-radius: 2px;
		display:inline-table;
	}
	.machine-list2 .item2 .sec2{
		/* padding:0 20px; */
		height: 80px;
		border-top:1px solid #ddd;
		border-bottom:1px solid #ddd;
	}
	
	.machine-list2 .item2 .sec3{
		padding:0 10px;
		height: 45px;
		line-height: 45px;
	}
	
	.machine-list2 .item2 .sec2 ul li.left{
		float: left;
		margin-right: 0px;
		margin-top: 10px;
		width: 150px;
		line-height: 2;
	}
	
	.machine-list2 .item2 .sec2 ul li.right{
		float: left;
		margin-right: 0px;
		margin-top: 10px;
		width: 116px;
		line-height: 2;
	}
</style>
</head>
<%-- <script type="text/javascript" src="${ctx}/page/js/devpagination.js"></script> --%>
<body>
<div id="machineMain2">
<div class="page-title-bar2">
	<span class="smalltitle2">监控服务<a href="${ctx}/help/main?url=/page/common/help/devmonitor/devmonitor.jsp" target="_blank" class="g-help"></a></span>
	<c:if test="${operator.operator_type eq '1'}"><%--管理员--%> 
		<div class="choose-lib2">
			<div class="select-btn2">
				<span class="current-libName2">显示全部</span>
				<span class="icon2"></span>
			</div>
			<div class="drop-down2">
				<div class="item2 place2"></div>
				<%--  
					<div class="item2 room2">沙头角阅览室</div>
				--%>
			</div>
		</div>
	</c:if> 
	<c:if test="${operator.operator_type != '1'}"><%--维护员 --%>
		<%-- <shiro:hasPermission name="0103000000">监控管理主页面  --%>
			<div class="choose-lib2">
				<div class="select-btn2">
					<span class="current-libName2">显示全部</span>
					<span class="icon2"></span>
				</div>
				<div class="drop-down2">
				</div>
			</div>
		<%-- </shiro:hasPermission> --%>
	</c:if> 
	<!-- <div class="form-wrap2 fr">
		<div class="g-select2">
				<select id="machineType">	
						<option value="-1" selected>选择类型</option>
				</select>
			<span class="arr-icon2"></span>
		</div>
		<div class="g-select2">
			<select id="machineState">
				<option value="-1" selected>选择状态</option>
					<option value="0">正常</option>
					<option value="1">警告</option>
					<option value="2">报警</option>
				    <option value="4">超时</option>
				    <option value="3">无状态</option>
				    <option value="1">报警</option>
			</select>
			<span class="arr-icon2"></span>
		</div>
		<input type="text" name="keyWord" id="" class="input2 g-input2" placeholder="输入设备名" />
		<div href="" class="btn2 search2">查询</div>
	</div> -->
	<!-- <div class="form-wrap1 fr">
		<div href="" class="btn2 search2">查询</div>
	</div> -->
</div>
<c:if test="${operator.operator_type != '1'}">
	<%-- <shiro:hasPermission name="0103000000"> --%>
		<div class="main2" style="background-color:white;overflow-y:scroll;">
			<div class="machine-list2">
				<div class="item-wrap2" id="server_mechine">
				
				</div>
			</div>
		</div>
</c:if> 
<c:if test="${operator.operator_type == '1'}">
		<div class="main2" style="background-color:white;overflow-y:scroll;">
			<div class="machine-list2" >
				<div class="item-wrap2" id="server_mechine">
				
				</div>
			</div>
		</div>
</c:if>
<!-- <div class="paging-bar2">
			<div class="left">
				<span class="t fl">选择</span>
				<div class="btn g-chooseServiceDeviceAll machine">全选</div>
				<div class="btn g-noChooseServiceDeviceAll machine">反选</div>
				<span class="t2 fl">切屏频率</span>
				<div class="g-select refresh">
					<select>
						<option value="-1" selected>不切换</option>
						<option value="30000" selected>30秒</option>
						<option value="60000">60秒</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<span class="t2 fl " id="ChooseNum">已选中<span class="total-choosen-num2">0</span>个</span>
			</div>
			<div id="page2" class="right">
			</div>
			<span class="total-page fr"></span>
			<span class="total-num fr"></span>
</div> -->
</div>
<script type="text/javascript" src="${ctx}/page/js/devpagination.js"></script> 
<script type="text/javascript">
(function mainHeightController(){
	var winH = $(window).height();
	var headerH = $(".g-header").outerHeight();
	var pageTitleBar = $(".page-title-bar2").outerHeight();
	var pagingBarH = $(".paging-bar2").outerHeight();
	var width = $("#machineMain").width();
	  
	$(".main2").css({
		"height":winH - pageTitleBar - headerH - pagingBarH
	}); 
	$(".main-content").css({
		"height":winH - headerH
	});
	$(".left-side").css({
		"height":winH - headerH
	});
	$("#machineMain").css({
		"height":winH - headerH
	});
})();
</script> 
</body>
</html>

