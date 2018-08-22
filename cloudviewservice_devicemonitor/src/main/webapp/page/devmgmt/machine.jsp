<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<style type="text/css">
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
	.machine-list .item.gray .sec3 .status {
   	 	background: url(${ctx}/static/images/cir-status4.png) right center no-repeat;
	}
	 .machine-list .item.blue .sec3 .status{
   	 	background: url(${ctx}/static/images/cir-status5.png) right center no-repeat;
	}
	.machine-list .item.alert .sec3 .status {
   	 	background: url(${ctx}/static/images/cir-status3.png) right center no-repeat;
	}
	/**sec2**/
	.machine-list .item.gray .sec2 .status {
    	background: url(${ctx}/static/images/cir-status4.png) right center no-repeat;
	}
	.machine-list .item.blue .sec2 .status {
    	background: url(${ctx}/static/images/cir-status5.png) right center no-repeat;
	}
	.machine-list .item.alert .sec2 .status {
    	background: url(${ctx}/static/images/cir-status3.png) right center no-repeat;
	}
	.page-title-bar .choose-lib .select-btn {
		width:300px;
	    height: 26px;
	    text-indent: 7px;
	    line-height: 26px;
	    border: 1px solid #DDD;
	    color: #888888;
	    background-color: #f6f6f6;
	    border-radius: 2px;
	}
	.page-title-bar .smalltitle {
	    float: left;
	    font-weight: bold;
	    color: #333333;
	}
	.page-title-bar .form-wrap1 {
	    padding-top: 11px;
	    color: #888;
	    line-height: 28px;
	}
	
	.fr {
	    float: right;
	}
	
	.machine-list .item-wrap {
		width:92%;
		height:100%;
		margin-left:8%;
	}
	
	.machine-list .item{
		float:center;
		padding-right: 8px;
	    padding-left: 8px;
		margin-bottom: 15px;
		margin:15px 15px 15px 15px;
		width: 330px;
		height: 200px;
		font-size: 12px;
		color:#888;
		text-align: left;
		border:3px solid #ddd;
		background-color: #fff;
		border-radius: 2px;
		display:inline-table;
	}
	.machine-list .item .sec2{
		/* padding:0 10px; */
		height: 80px;
		border-top:1px solid #ddd;
		border-bottom:1px solid #ddd;
	}
	
	.machine-list .item .sec3{
		padding:0 10px;
		height: 45px;
		line-height: 45px;
	}
	
	.machine-list .item .sec2 ul li.left{
		float: left;
		margin-right: 0px;
		margin-top: 10px;
		width: 160px;
		line-height: 2;
	}
	.machine-list .item .sec2 ul li.right{
		float: left;
		margin-right: 0px;
		margin-top: 10px;
		width:160px;
		line-height: 2;
	}
</style>
</head>
<body>

<div id="machineMain">
<div class="page-title-bar">
	<span class="smalltitle">监控机器<a href="${ctx}/help/main?url=/page/common/help/devmonitor/devmonitor.jsp" target="_blank" class="g-help"></a></span>
	<c:if test="${operator.operator_type eq '1'}"><%--管理员 --%>
		<div class="choose-lib">
			<div class="select-btn">
				<span class="current-libName">------显示全部------</span>
				<span class="icon"></span>
			</div>
			<div class="drop-down">
				<!-- <div class="item place"></div> -->
				<input type="text" class="item place" style='width:300px'/>
				<%--  
					<div class="item room">沙头角阅览室</div>
				--%>
			</div>
		</div>
	</c:if>
	<c:if test="${operator.operator_type != '1'}"><%--维护员 --%>
		<shiro:hasPermission name="0103000000">监控管理主页面
			<div class="choose-lib">
				<div class="select-btn">
					<span class="current-libName">------显示全部------</span>
					<span class="icon"></span>
				</div>
				<div class="drop-down">
					<!-- <div class="item place"></div> -->
					<input type="text" class="item place"/>
				</div>
			</div>
		</shiro:hasPermission>	 
	</c:if>
	<div class="form-wrap1 fr">
		<span class="t fl">维护</span>
		<div class="g-select">
			<select id="machineCmd">
					<option value="-1" selected>选择指令</option>
					<c:forEach items="${metaorders}" var="order">
						<!-- 通过命令码控制权限 add by huanghuang 20170216 -->
						<c:choose> 
							  <c:when test="${order.order_desc=='关机'}">
							  	<shiro:hasPermission name="0103020101">   
							    	<option value="${order.order_idx}">${order.order_desc}</option>
							    </shiro:hasPermission>
							  </c:when> 
							  <c:when test="${order.order_desc=='重启'}">   
							    <shiro:hasPermission name="0103020102">   
							    	<option value="${order.order_idx}">${order.order_desc}</option>
							    </shiro:hasPermission>  
							  </c:when> 
							  <c:when test="${order.order_desc=='查日志'}">   
							    <shiro:hasPermission name="0103020103">   
							    	<option value="${order.order_idx}">${order.order_desc}</option>
							    </shiro:hasPermission> 
							  </c:when> 
							  <%-- 
							  <c:when test="${order.order_desc=='远程维护锁屏'}">   
							    <shiro:hasPermission name="0103020104">   
							    	<option value="${order.order_idx}">${order.order_desc}</option>
							    </shiro:hasPermission>  
							  </c:when> 
							  <c:when test="${order.order_desc=='取消操作'}">   
							    <shiro:hasPermission name="0103020105">   
							    	<option value="${order.order_idx}">${order.order_desc}</option>
							    </shiro:hasPermission>  
							  </c:when>  --%>
							  <c:otherwise>
							  </c:otherwise> 
						</c:choose> 
						<%-- <option value="${order.order_idx}">${order.order_desc}</option> --%>
					</c:forEach>
			</select>
			<span class="arr-icon"></span>
		</div>
		<div id="sendbtn" class="btn send">发送</div> 
		<div class="g-select">
				<select id="machineType">
					<!-- 	
						<option value="-1" selected>选择类型</option>
					-->
				</select>
			<span class="arr-icon"></span>
		</div>
		<div class="g-select">
			<select id="machineState">
					<option value="-1" selected>选择状态</option>
					<option value="1">警告</option>
					<!--<option value="0">正常</option>
					<option value="1">警告</option>
					<option value="2">报警</option>
				    <option value="4">超时</option>
				    <option value="3">无状态</option> -->
			</select>
			<span class="arr-icon"></span>
		</div>
		<input type="text" name="keyWord" id="" class="input g-input" placeholder="输入设备名" />
		<div href="" class="btn search">查询</div>
	</div>
</div>
<c:if test="${operator.operator_type != '1'}">
	<shiro:hasPermission name="0103000000">
		<div class="main" style="overflow-y:scroll;">
			<div class="machine-info" id="lib_machine">设备总数：</div>
			<div class="machine-list">
				<div class="item-wrap" id="addMachine">
				
				</div>
			</div>
		</div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="0103000000">
		<div class="main" style="overflow-y:scroll;">
			<div class="machine-info" id="lib_machine">设备总数：</div>
			<div class="machine-list">
				<div class="item-wrap" id="addMachine">
					没有权限！
				</div>
			</div>
		</div>
	</shiro:lacksPermission>
</c:if>
<c:if test="${operator.operator_type == '1'}">
		<div class="main" style="overflow-y:scroll;">
			<div class="machine-info" id="lib_machine">设备总数：</div>
			<div class="machine-list">
				<div class="item-wrap" id="addMachine">
				
				</div>
			</div>
		</div>
</c:if>

<div class="paging-bar">
			<div class="left">
				<span class="t fl">选择</span>
				<div class="btn g-chooseAll machine">全选</div>
				<div class="btn g-noChooseAll machine">反选</div>
				<span class="t2 fl">切屏频率</span>
				<div class="g-select refresh" style="margin-top:12px">
					<select>
						<option value="-1" selected>不切换</option>
						<option value="30000" selected>30秒</option>
						<option value="60000">60秒</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<span class="t2 fl " id="ChooseNum">已选中<span class="total-choosen-num">0</span>个</span>
			</div>
			<div id="page" class="right">
			</div>
			<span class="total-page fr"></span>
			<span class="total-num fr"></span>
</div>
<%-- <%@ include file="../include/page_bar.jsf" %>  --%>
</div>
<script type="text/javascript">
$(function(){
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();
		
		$(".main").css({
			"min-height":winH - headerH - pagingBarH -pageTitleBar,
			"max-height":winH - headerH - pagingBarH -pageTitleBar//"auto"
		});
	})();
	$("#deviceStatus").text("4");
});
</script>
</body>
</html>





