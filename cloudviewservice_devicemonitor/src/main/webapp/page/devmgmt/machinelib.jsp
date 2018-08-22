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
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<%-- <%@ include file="../common/global.jsf" %>  --%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/style.css">
<style type="text/css">
	#machineMain{
		height:100%;
		width:100%
	}
	.w-900  .form-dialog{
		width: 900px;
		right:  0px;
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
	.page-title-bar .choose-lib .select-btn{
		width:300px;
	    height: 26px;
	    text-indent: 7px;
	    line-height: 26px;
	    border: 1px solid #DDD;
	    color: #888888;
	    background-color: #f6f6f6;
	    border-radius: 2px;
	}
	.page-title-bar .choose-lib .drop-down .item{
		text-align:center
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
	.machine-list .item .sec2{
		/* padding:0 20px; */
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
		width: 150px;
		line-height: 2;
	}
	
	.machine-list .item .sec2 ul li.right{
		float: left;
		margin-right: 0px;
		margin-top: 10px;
		width: 116px;
		line-height: 2;
	}
	
</style>
</head>
<body> 

<div id="machineMain">
<div class="page-title-bar">
	<span class="smalltitle">监控机器<a href="${ctx}/help/main?url=/page/common/help/devmonitor/devmonitor.jsp" target="_blank" class="g-help"></a></span>
	<c:if test="${operator.operator_type eq '1'}"><%--管理员--%>
		<div class="choose-lib">
			<div class="select-btn">
				<span class="current-libName">显示全部</span>
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
		<%-- <shiro:hasPermission name="0103000000">监控管理主页面 --%>
			<div class="choose-lib">
				<div class="select-btn">
					<span class="current-libName">显示全部</span>
					<span class="icon"></span>
				</div>
				<div class="drop-down">
					<!-- <div class="item place"></div> -->
					<input type="text" class="item place"/>
				</div>
			</div>
		<%-- </shiro:hasPermission> --%>
	</c:if>
	<div class="form-wrap1 fr">
		<span class="t fl">维护</span>
		<div class="g-select">
			<select id="machineCmd">
					<option value="-1" selected>选择指令</option>
					<%-- <c:forEach items="${metaorders}" var="order">
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
							  <c:when test="${order.order_desc=='远程维护锁屏'}">   
							    <shiro:hasPermission name="0103020104">   
							    	<option value="${order.order_idx}">${order.order_desc}</option>
							    </shiro:hasPermission>  
							  </c:when> 
							  <c:when test="${order.order_desc=='取消操作'}">   
							    <shiro:hasPermission name="0103020105">   
							    	<option value="${order.order_idx}">${order.order_desc}</option>
							    </shiro:hasPermission>  
							  </c:when> 
							  <c:otherwise>
							  </c:otherwise> 
						</c:choose> 
						<option value="${order.order_idx}">${order.order_desc}</option>
					</c:forEach> --%>
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
				    <option value="3">无状态</option> 
				    <option value="1">报警</option>-->
			</select>
			<span class="arr-icon"></span>
		</div>
		<input type="text" name="keyWord" id="" class="input g-input" placeholder="输入设备名" />
		<div href="" class="btn search">查询</div>
	</div>
</div>
<c:if test="${operator.operator_type != '1'}">
	<shiro:hasPermission name="0103000000">
		<div class="main" style="background-color:white;overflow-y:scroll;">
			<div class="machine-info">设备总数：</div>
			<div class="machine-list">
				<div class="item-wrap">
				
				</div>
			</div>
		</div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="0103000000">
		<div class="main" style="background-color:white;overflow-y:scroll;">
			<div class="machine-info">设备总数：</div>
			<div class="machine-list">
				<div class="item-wrap">
					没有权限！
				</div>
			</div>
		</div>
	</shiro:lacksPermission>
</c:if> 
<c:if test="${operator.operator_type == '1'}">
		<div class="main" style="background-color:white;overflow-y:scroll;">
			<div class="machine-info" style="text-align:center">设备总数：</div>
			<div class="machine-list">
				<div class="item-wrap">
				
				</div>
			</div>
		</div>
</c:if> 

<%@ include file="../include/page_bar.jsf" %>
</div>
<script type="text/javascript" src="${ctx}/page/js/devpagination.js"></script>
<script type="text/javascript">
$(function(){
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();
		var width = $("#machineMain").width();
		$(".main").css({
			"height":winH - headerH - pageTitleBar - pagingBarH
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
	$("#deviceStatus").text("4");
	var machine={};
	var library={};/**ID library_idx**/
	/**加载页面**/
	$itemWrap=$("div.main").find(".item-wrap");
	//机器状态使用颜色进行标注（如红色、灰色、黄色、绿色  等）
	//library_idx 换成lib_id
	var drawRow=function(rows){
		if(!rows)return;
		$itemWrap.html("");
		var ssls=0,schs=0,stacs=0,stas=0,dlvs=0,pors=0,bdrs=0,regs=0,other=0;
		for(var i=0;i<rows.length;i++){
			var device=rows[i];
			var key=library[device.library_idx].lib_id+"_"+device.device_id;
			var item='<div class="item gray" style="border:3px solid #AAAAAA;border-radius:15px;">'
				+'<input type="hidden" name="cardbin_amount"/>'
				+'<input type="text" name="rel_device_id" class="device_id" hidden="hidden" value="'+device.device_id+'"/>'
				+'<input type="text" name="_device_type" class="_device_type" hidden="hidden" value="'+device.device_type+'"/>'
			 	+'<input type="text" name="_device_id" class="_device_id" hidden="hidden" value="'+key+'"/>'
			 	+'<input type="text" name="_device_idx" class="_device_idx" hidden="hidden" value="'+device.device_idx+'"/>'
			 	+'<input type="text" name="_library_idx" class="_library_idx" hidden="hidden" value="'+device.library_idx+'"/>'
			 	+'<div class="sec1">'
			 	+'<div class="g-checkbox" style="float:left;margin-top:13px;"><input type="checkbox" name="'+device.device_id+'" id=""></div>'
			 	+'<a alt="这是提示" title="'+device.device_name+'"><span class="machine-name" style="display:block;width:150px;height:27px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;float:left;">'+device.device_name+'</span></a>'  /* '+device.device_name+' */
			 	+'<span class="status" style="float:right;">无状态<b></b></span>'
			 	+'</div>';
			 	//现在只有自助借还图书馆显示以下信息
			 	if(device.device_type.indexOf("SSL")!=-1){//
				 	item+='<div class="sec2">'
					 	+'<ul class="device_state">'
					 	+'	<li class="left">今日借还量：<span class="center">-</span></li>'
					 	+'	<li class="right">今日办证量：<span class="center">-</span></li>'
					 	+'	<li class="left">当月借还量：<span class="center">-</span></li>'
					 	+'	<li class="right">当月办证量：<span class="center">-</span></li>'
					 	+'</ul>'
					 	+'</div>';
				 	ssls++;
			 	}else if(device.device_type.indexOf("SCH")!=-1){//自助借还（借还）
			 		item+='<div class="sec2">'
					 	+'<ul class="device_state">'
					 	+'	<li class="left">今日借还量：<span class="center">-</span></li>'
					 	+'	<li class="right">今日续借量：<span class="center">-</span></li>'
					 	+'	<li class="left">当月借还量：<span class="center">-</span></li>'
					 	+'	<li class="right">当月续借量：<span class="center">-</span></li>'
					 	+'</ul>'
					 	+'</div>';
			 		schs++;
				}else if(device.device_type.indexOf("STA-C")!=-1){//移动点检车（）
					item+='<div class="sec2">'
					 	+'<ul class="device_state">'
					 	+'	<li class="left">当月上架量：<span class="center">-</span></li>'
					 	+'</ul>'
					 	+'</div>';
					stacs++;
				}else if(device.device_type.indexOf("STA")!=-1){//馆员工作站（）
					item+='<div class="sec2">'
					 	+'<ul class="device_state">'
					 	+'	<li class="left">今日借还量：<span class="center">-</span></li>'
					 	+'	<li class="right">今日续借量：<span class="center">-</span></li>'
					 	+'	<li class="left">当月借还量：<span class="center">-</span></li>'
					 	+'	<li class="right">当月续借量：<span class="center">-</span></li>'
					 	+'</ul>'
					 	+'</div>';
					stas++;
				}else if(device.device_type.indexOf("DLV")!=-1){//取书柜（）
					item+='<div class="sec2">'
					 	+'<ul class="device_state">'
					 	+'	<li class="left">今日取书量：<span class="center">-</span></li>'
					 	+'</ul>'
					 	+'</div>';
				 	dlvs++;
				}else if(device.device_type.indexOf("POR")!=-1){//安全门（）
			 		item+='<div class="sec2">'
					 	+'<ul class="device_state">'
					 	+'</ul>'
					 	+'</div>';
				 	pors++;
			 	}else if(device.device_type.indexOf("BDR")!=-1){//还书机（还书统计）
			 		item+='<div class="sec2">'
					 	+'<ul class="device_state">'
					 	+'	<li class="left">还书箱：<span class="center">-</span></li>'
					 	+'</ul>'
					 	+'</div>';
				 	bdrs++;
			 	}else if(device.device_type.indexOf("REG")!=-1){//办证机(办证、卡数量)
			 		item+='<div class="sec2">'
					 	+'<ul class="device_state">'
					 	+'	<li class="left">剩余卡数量：<span class="center">-</span></li>'
					 	+'	<li class="right">今日办证量：<span class="center">-</span></li>'
					 	+'	<li class="left">当月办证量：<span class="center">-</span></li>'
					 	+'</ul>'
					 	+'</div>';
					 regs++;
			 	}else {
			 		item+='<div class="sec2">'
					 	+'<ul class="device_state">'
					 	+'</ul>'
					 	+'</div>';
			 		other++;
			 	}
			 	item+='<div class="sec3">'
				 	+'ID:'+device.device_id
				 	+'<div class="check-detail" device_type="'+device.device_type+'">查看详情</div>'
				 	+'</div>'
				 	+'</div>';
				$itemWrap.append(item);
		};
		var machine=new Array;
		if(ssls>0){
			machine.push("<span>24小时自助图书馆数量："+ssls+"</span>");
		}
		if(schs>0){
			machine.push("<span>自助借还书机数量："+schs+" </span>");
		}
		if(stacs>0){
			machine.push("<span>移动点检车数量："+stacs+" </span>");
		}
		if(stas>0){
			machine.push("<span>馆员工作站数量："+stas+" </span>");
		}
		if(dlvs>0){
			machine.push("<span>取书柜数量："+dlvs+" </span>");
		}
		if(pors>0){
			machine.push("<span>安全门数量："+pors+" </span>");
		}
		if(bdrs>0){
			machine.push("<span>还书机数量："+bdrs+" </span>");
		}
		if(regs>0){
			machine.push("<span>自助办证机数量："+regs+" </span>");
		}
		if(other>0){
			machine.push("<span>其他设备数量："+other+"</span><br/>");
		}
		$(".machine-info").append(machine.join(''));
	};
	/**
		获取颜色
	**/
	var colorArr=[];
	var getColorArr=function(func){
		$.ajax({
			url:"${ctx}/devicemonconf/queryMonitorColorConf",
			type:"GET"	
		}).done(function(data){
			//console.log("获取颜色",data);
			if(data&&data.state){
				colorArr=data.result;
				if(func){
					func();
				}
			}
		});
	};
	getColorArr();
	
	/** 
		获取基础数据
	**/
	var metadataLogicOBJ={};
	var GetMetadataLogicOBJ=function(){
		$.ajax({
			url:"${ctx}/devicemonconf/SelMetadataLogicObject",
			type:"GET",
			data:{"req":"{}"}
		}).done(function(data){
			//console.log("GetMetadataLogicOBJ",data);
			if(data&&data.state&&data.result){
				for(var i=0;i<data.result.length;i++){
					var logicObj=data.result[i];
					metadataLogicOBJ[logicObj.meta_log_obj_idx]=logicObj.logic_obj.toLowerCase();
				}				
			}
		});
	}; 
	GetMetadataLogicOBJ();
	var currentDevColorRetMap={};
	//获取当前页的设备的监控颜色设置
	var GetCurrentDevColorSet=function(){
		var devIdxArr=[];
		$("input[name=_device_idx]").each(function(index,dom){
			devIdxArr.push($(dom).val());
		});
		$.ajax({
			url:"${ctx}/device/GetCurrentDevColorSet",
			type:"GET",
			data:{"req":JSON.stringify(devIdxArr)}
		}).done(function(data){
			if(data){
				//console.log("GetCurrentDevColorSet",data);
				currentDevColorRetMap=data.result;
				if(!metadataLogicOBJ){
					GetMetadataLogicOBJ();
				}
				setStatusProxy();
			}
		});
	};
	function setStatusProxy(){
		//getLastHeatBeatTime
		var rel_device_id_arr=[];
		$("input[name=rel_device_id]").each(function(index,dom){
			rel_device_id_arr.push($(dom).val());
		});
		$.ajax({
			url:"${ctx}/device/getLastHeatBeatTime",
			data:{"req":JSON.stringify(rel_device_id_arr)},//设备ID数组
			success:function(data){
				//console.log("getLastHeatBeatTime",data);
				setStatus(data.result);
			}
		});
	}
	
	var countBDRLoanLog = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countLoanLog",
	        type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result;
					if(result!=null){
						var return_total=0;
						console.info("自助还书机Today countBDRLoanLog",result);
						for(var data in result){
							if(result[data].name=="还书"){
								return_total = result[data].value;
							}
						}
						$(domEle).parent().find("ul").children("li:nth-child(1)").children("span").text(return_total);
					}
				}
			}
		  }); 
	}
	
	var countSSLTodayLoanLog = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countLoanLog",
            type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result;
					if(result!=null){
						var loan_total=0,return_total=0;
						console.info("自助图书馆Today countSSLTodayLoanLog",result);
						for(var data in result){
							//console.info("=========",result[data]);
							if(result[data].name=="借书"){
								loan_total = result[data].value;
							}
							if(result[data].name=="还书"){
								return_total = result[data].value;
							}
						}
						$(domEle).parent().find("ul").children("li:nth-child(1)").children("span").text(loan_total+"/"+return_total);
					}
				}else{
					//console.info("result-----",result);
					
				}
			}
		  });  
	}
	var countSSLMonthLoanLog = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countLoanLog",
            type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result
					if(result!=null){
						var loan_total=0,return_total=0;
						console.info("自助图书馆Year countSSLYearLoanLog",result);
						for(var data in result){
							//console.info("=========",result[data]);
							if(result[data].name=="借书"){
								loan_total = result[data].value;
							}
							if(result[data].name=="还书"){
								return_total = result[data].value;
							}
						}
						$(domEle).parent().find("ul").children("li:nth-child(3)").children("span").text(loan_total+"/"+return_total);
					}
				}else{
					//console.info("result-----",result);
					
				}
			}
		  });
	}
	var countSSLTodayIssCard = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countCardissueLog",
            type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result;
					if(result!=null){
						var issCard_total=0;
						console.info("****自助图书馆办证数量",result.total);
						for(var data in result){
							if(result[data].name=="办证"){
								issCard_total = result[data].value;
							}
						}
						$(domEle).parent().find("ul").children("li:nth-child(2)").children("span").text(issCard_total);
					}
				}else{
					//console.info("result-----",result);
					
				}
			}
		  });
	}
	var countSSLMonthIssCard = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countCardissueLog",
            type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result;
					if(result!=null){
						var issCard_total=0;
						console.info("****自助图书馆办证数量",result.total);
						for(var data in result){
							if(result[data].name=="办证"){
								issCard_total = result[data].value;
							}
						}
						$(domEle).parent().find("ul").children("li:nth-child(4)").children("span").text(issCard_total);
					}
				}else{
					//console.info("result-----",result);
					
				}
			}
		  });
	}
	
	 var countSCHTodayLoanLog = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countLoanLog",
	        type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result;
					if(result!=null){
						var loan_total=0,return_total=0,renew_total=0;
						console.info("自助借还机Today countSCHTodayLoanLog",result);
						for(var data in result){
							//console.info("=========",result[data]);
							if(result[data].name=="借书"){
								loan_total = result[data].value;
							}
							if(result[data].name=="还书"){
								return_total = result[data].value;
							}
							if(result[data].name=="续借"){
								renew_total = result[data].value;
							}
						}
						$(domEle).parent().find("ul").children("li:nth-child(1)").children("span").text(loan_total+"/"+return_total);
						$(domEle).parent().find("ul").children("li:nth-child(2)").children("span").text(renew_total);
					}
				}else{
					console.info("result-----",result);
					
				}
			}
		  });  
	}
	 var countSCHMonthLoanLog = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countLoanLog",
	        type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result;
					if(result!=null){
						var loan_total=0,return_total=0,renew_total=0;
						console.info("自助借还机 countSCHMonthLoanLog",result);
						for(var data in result){
							//console.info("=========",result[data]);
							if(result[data].name=="借书"){
								loan_total = result[data].value;
							}
							if(result[data].name=="还书"){
								return_total = result[data].value;
							}
							if(result[data].name=="续借"){
								renew_total = result[data].value;
							}
						}
						$(domEle).parent().find("ul").children("li:nth-child(3)").children("span").text(loan_total+"/"+return_total);
						$(domEle).parent().find("ul").children("li:nth-child(4)").children("span").text(renew_total);
					}
				}else{
					console.info("result-----",result);
					
				}
			}
		  });  
	} 
	var countSTACYearLoanLog = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countLoanLog",
	        type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result;
					if(result!=null){
						//没有相关数据
					}
				}else{
					console.info("result-----",result);
					
				}
			}
		  });  
	}
	var countREGTodayIssCard = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countCardissueLog",
            type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result
					if(result!=null){
						var issCard=0;
						console.info("****自助图书馆Year countSSLTodayIssCard",result);
						console.info("****自助图书馆办证数量",result.total);
						for(var data in result){
							//console.info("=========",result[data]);
							if(result[data].name=="办证"){
								issCard = result[data].value;
							}
						}
						$(domEle).parent().find("ul").children("li:nth-child(2)").children("span").text(issCard);
					}
			}else{
					//console.info("result-----",result);
				}
			}
		  });
	}
	var countREGMonthIssCard = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countCardissueLog",
            type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result;
					if(result!=null){
						var issCard=0;
						console.info("****自助图书馆Year countSSLMonthIssCard",result);
						console.info("****自助图书馆办证数量",result.total);
						for(var data in result){
							//console.info("=========",result[data]);
							if(result[data].name=="办证"){
								issCard = result[data].value;
							}
						}
						$(domEle).parent().find("ul").children("li:nth-child(3)").children("span").text(issCard);
					}
				}else{
					//console.info("result-----",result);
					
				}
			}
		  });
	}
	var countSTATodayLoanLog = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countLoanLog",
	        type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				console.info("馆员工作站：",data);
				if(data.state){
					result = data.result;
					if(result!=null){
						var loan_total=0,return_total=0,renew_total=0;
						console.info("馆员工作站Today countSCHTodayLoanLog",result);
						for(var data in result){
							//console.info("=========",result[data]);
							if(result[data].name=="借书"){
								loan_total = result[data].value;
							}
							if(result[data].name=="还书"){
								return_total = result[data].value;
							}
							if(result[data].name=="续借"){
								renew_total = result[data].value;
							}
						}
						$(domEle).parent().find("ul").children("li:nth-child(1)").children("span").text(loan_total+"/"+return_total);
						$(domEle).parent().find("ul").children("li:nth-child(2)").children("span").text(renew_total);
					}
				}else{
					console.info("result-----",result);
					
				}
			}
		  });  
	}
	var countSTAMonthLoanLog = function(domEle,param){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countLoanLog",
	        type:"POST",
			data: {"req":JSON.stringify(param)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result;
					if(result!=null){
						var loan_total=0,return_total=0,renew_total=0;
						console.info("馆员工作站 countSTAMonthLoanLog",result);
						for(var data in result){
							//console.info("=========",result[data]);
							if(result[data].name=="借书"){
								loan_total = result[data].value;
							}
							if(result[data].name=="还书"){
								return_total = result[data].value;
							}
							if(result[data].name=="续借"){
								renew_total = result[data].value;
							}
						}
						$(domEle).parent().find("ul").children("li:nth-child(3)").children("span").text(loan_total+"/"+return_total);
						$(domEle).parent().find("ul").children("li:nth-child(4)").children("span").text(renew_total);
					}
				}else{
					console.info("result-----",result);
					
				}
			}
		  });  
	} 
	//获取设备状态,定时执行时间timeRefresh
	var setStatus=function(lastHeatBeatTime){
		 var arr=new Array();
		 $(document).find("input._device_id").each(function (index,domEle){
			var device_id=$(domEle).val();/**实际上是 library_id_device_id**/
			var a = $(domEle).prev().val();
			var device_idx;
			var library_idx;
			if(a.indexOf("SSL")!=-1){//
				device_idx = $(domEle).next().val();
				library_idx = $(domEle).next().next().val();
				
				console.log("自助图书馆  device_id ",a+" "+device_idx,"library_idx",library_idx);
				
				var postData = {"type":"today","library_idx":library_idx,"device_idx":device_idx,"chart_type":"bar","forTotal":true};
				var postData1 = {"type":"month","library_idx":library_idx,"device_idx":device_idx,"chart_type":"bar","forTotal":true};
				//统计当日借还
				countSSLTodayLoanLog($(domEle),postData);
				//统计当月借还
				countSSLMonthLoanLog($(domEle),postData1);
				//统计今日办证
				countSSLTodayIssCard($(domEle),postData);
				//统计当月办证
				countSSLMonthIssCard($(domEle),postData1);
		 	}else if(a.indexOf("SCH")!=-1){//自助借还（借还）
		 		device_idx = $(domEle).next().val();
				library_idx = $(domEle).next().next().val();
				console.log("自助借还机  device_id ",a+" "+device_idx,"library_idx",library_idx);
				
				var postData = {"type":"today","library_idx":library_idx,"device_idx":device_idx,"chart_type":"bar","forTotal":true};
				var postData1 = {"type":"month","library_idx":library_idx,"device_idx":device_idx,"chart_type":"bar","forTotal":true};
				//统计当日借还
				countSCHTodayLoanLog($(domEle),postData);
				//统计当月借还
				countSCHMonthLoanLog($(domEle),postData1);
			}else if(a.indexOf("STA-C")!=-1){//移动点检车（）
				device_idx = $(domEle).next().val();
				library_idx = $(domEle).next().next().val();
				console.log("移动点检车   device_id ",a+" "+device_idx,"library_idx",library_idx);
				
				var postData = {"type":"month","library_idx":library_idx,"device_idx":device_idx,"chart_type":"bar","forTotal":true};
				countSTACYearLoanLog($(domEle),postData);
			}else if(a.indexOf("STA")!=-1){//馆员工作站（）
				device_idx = $(domEle).next().val();
				library_idx = $(domEle).next().next().val();
				console.log("馆员工作站 device_id ",a+" "+device_idx,"library_idx",library_idx);
				
				var postData = {"type":"today","library_idx":library_idx,"device_idx":device_idx,"chart_type":"bar","forTotal":true};
				var postData1 = {"type":"month","library_idx":library_idx,"device_idx":device_idx,"chart_type":"bar","forTotal":true};
				countSTATodayLoanLog($(domEle),postData);
				countSTAMonthLoanLog($(domEle),postData1);
			}else if(a.indexOf("DLV")!=-1){//取书柜（）
				device_idx = $(domEle).next().val();
				library_idx = $(domEle).next().next().val();
				console.log("取书柜 device_id ",a+" "+device_idx,"library_idx",library_idx);
			}else if(a.indexOf("POR")!=-1){//安全门（）
				device_idx = $(domEle).next().val();
				library_idx = $(domEle).next().next().val();
				console.log("安全门 device_id ",a+" "+device_idx,"library_idx",library_idx);
		 	}else if(a.indexOf("BDR")!=-1){//还书机（还书统计）
		 		device_idx = $(domEle).next().val();
				library_idx = $(domEle).next().next().val();
				console.log("还书机  device_id ",a+" "+device_idx,"library_idx",library_idx);
				var postData = {"type":"today","library_idx":library_idx,"device_idx":device_idx,"chart_type":"bar","forTotal":true};
				
				countBDRLoanLog($(domEle),postData);
		 	}else if(a.indexOf("REG")!=-1){//办证机(办证、卡数量)
		 		device_idx = $(domEle).next().val();
				library_idx = $(domEle).next().next().val();
				console.log("办证机device_id ",a+" "+device_idx,"library_idx",library_idx);
				var postData = {"type":"today","library_idx":library_idx,"device_idx":device_idx,"chart_type":"bar","forTotal":true};
				var postData1 = {"type":"month","library_idx":library_idx,"device_idx":device_idx,"chart_type":"bar","forTotal":true};
				
				countREGTodayIssCard($(domEle),postData);
				countREGMonthIssCard($(domEle),postData);
		 	}else {
		 		console.log("我是其他类");
		 	} 
			
			arr.push(device_id);
		 }); 
		 
		 $.ajax({
			type:"POST",
			url:"${ctx}/device/selectDeviceState",
			data:{"req":JSON.stringify(arr)}
		}).done(function(data){
			//console.log("selectDeviceState",data);
			if(data&&data.state==true){
				var device=data.result;// {device_id:state}
				for(var device_id in device){
					$thisDev=$(document).find("input[name=_device_id][value="+device_id+"]");
					var rel_device_id=$thisDev.parent().find("input[name=rel_device_id]").val();
					var device_idx=$thisDev.parent().find("input[name=_device_idx]").val();
					var stateObject=device[device_id];
					if(!stateObject){//没有上传过ext_state数据，认定为没有该设备
						$thisDev.parent()
							.removeClass("error")
							.removeClass("alert")
							.removeClass("blue").addClass("gray");
						$thisDev.parent().find("span.status").html("无数据");
						continue;
					}
					var state=stateObject.new_state;         //状态值
					var alertObj=stateObject.alert_obj;	     //逗号分割的异常部件名称,（报警部件）
					//console.log("DEV_ID:",device_id,"。状态值:",state,"报警部件:",alertObj);
                    			//console.log("报警状态颜色",currentDevColorRetMap);
					var createTime=stateObject.create_time;  //20160823191003
					var curTimeLong=stateObject.current_time;//long类型的时间
					var lastHeartBeatTime_=null;
					if(lastHeatBeatTime){
						for(var l=0;l<lastHeatBeatTime.length;l++){
							var inf=lastHeatBeatTime[l];
							if(inf.devId == rel_device_id){
								if(inf["lastHeartBeatTime"]){
									lastHeartBeatTime_=inf["lastHeartBeatTime"];
								}else{
                                    lastHeartBeatTime_=new Date(createTime.substring(0,4),createTime.substring(4,6)-1,createTime.substring(6,8),createTime.substring(8,10),createTime.substring(10,12),createTime.substring(12,14));
								}
								break;
							}						
						}
					}
					//console.log("lastHeartBeatTime",device_id,lastHeartBeatTime_);
					if(lastHeartBeatTime_){
                        var maxTime = getThresholdTime(currentDevColorRetMap,device_idx);
                        if(new Date().getTime() - lastHeartBeatTime_ > maxTime*1000){
                            state = "4";//设置报警状态为超时
                        }
					}
					var s = $("#deviceStatus").text();
					console.log("s"+s);
					if(state == "0"){//正常状态
						$thisDev.parent().css("border","3px solid #00FF33");
						$thisDev.parent().css("border-radius","15px");
						$thisDev.parent().removeClass("error").removeClass("alert").removeClass("gray").removeClass("blue");
						$thisDev.parent().find("span.status").html("正常");
					}else if(state == "4"){
						$thisDev.parent().css("border","3px solid #7075B5");
						$thisDev.parent().css("border-radius","15px");
                        $thisDev.parent().removeClass("error").removeClass("alert").removeClass("gray").addClass("blue");
                        $thisDev.parent().find("span.status").html("超时");
                    }else{//判断是否告警
                        //报警颜色 如红色、灰色、黄色、绿色  等）//其他值异常
                        //green:0:绿色|yellow:1:黄色|red:2:红色|gray:3:灰色|blue:4:蓝色
                        //这里需要查询设备配置的报警颜色。[报警的等级]
                        if (currentDevColorRetMap) {
                            var monitorConfList = currentDevColorRetMap[device_idx];
                            if (monitorConfList) {
                                var alertObjMap = {};
                                if (alertObj && alertObj.indexOf(",") >= 0) {
                                    var alertObjArr = alertObj.split(",");
                                    for (var i = 0; i < alertObjArr.length; i++) {
                                        var alert = alertObjArr[i].toLowerCase();
                                        if (alert) {
                                            alertObjMap[alert] = alert;
                                        }
                                    }
                                }
                                //mongodb(alertObjMap)报警上传的报警的部件，
                                //以mysql(currentDevColorRetMap)配置报警部件的为准
                                //console.log("alertObjMap", alertObjMap);
                                
                                
                                var maxColor = 1;
                                var status_flg = false;//表示有没有状态
                                var monitorObjNormal = true;//监控的设备正常
                                //console.log("metadataLogicOBJ", metadataLogicOBJ);
                                //console.log("monitorConfList", monitorConfList);
                                for (var i = 0; i < monitorConfList.length; i++) {
                                    var monitorConf = monitorConfList[i];
                                    if (monitorConf.table_name == "metadata_logic_obj" || monitorConf.table_name == "monitor_logic_obj") {
                                        if (metadataLogicOBJ) {
                                            var logicObj = alertObjMap[metadataLogicOBJ[monitorConf.meta_log_obj_idx].toLowerCase()];
                                            //console.log("logicObj",logicObj);
                                            //1.如果有报警的部件，则同步，没有则continue
                                            //2.这边再加一个判断，如果没有监控到的部件，则continue
                                            //console.log("logicObj", logicObj);
                                            if (!logicObj || logicObj == null || logicObj == "undefined" || logicObj == "") {
                                                continue;
                                            }
                                        }
                                        monitorObjNormal = false;//如果能通过上面的检查 表示不正常
                                        var color = monitorConf.color;
                                        maxColor = getMaxAlertColor(color, maxColor);
                                        if (state != "0" && (!colorArr || colorArr.length <= 0)) {
                                            getColorArr(function () {
                                                var colorOBJ = colorArr[maxColor];
                                                if (colorOBJ) {
                                                    var colorEN = colorOBJ["colorEN"];
                                                    if (!colorEN || colorEN == "") colorEN = "error";
                                                    $thisDev.parent()
                                                        .removeClass("error")
                                                        .removeClass("alert")
                                                        .removeClass("gray")
                                                        .removeClass("blue").addClass(colorEN);
                                                    $thisDev.parent().find("span.status").html("告警");
                                                    $thisDev.parent().css("border","3px solid #FF3333");
                                					$thisDev.parent().css("border-radius","15px");
                                                    status_flg = true;

                                                }
                                            });
                                        } else if (state != "0") {
                                            var colorOBJ = colorArr[maxColor];
                                            if (colorOBJ) {
                                                var colorEN = colorOBJ["colorEN"];
                                                if (!colorEN || colorEN == "") colorEN = "error";
                                                $thisDev.parent()
                                                    .removeClass("error")
                                                    .removeClass("alert")
                                                    .removeClass("gray")
                                                    .removeClass("blue").addClass(colorEN);
                                                $thisDev.parent().find("span.status").html("告警");
                                                $thisDev.parent().css("border","3px solid #FF3333");
                            					$thisDev.parent().css("border-radius","15px");
                                                status_flg = true;
                                            }
                                        }
                                    }
                                }
                                //上面不是 超时 ，也找不到 但是 state!=0 默认设置
                                if (!status_flg && state != "0") {
                                    $thisDev.parent().removeClass("error").removeClass("alert").removeClass("gray").removeClass("blue").addClass("error");
                                    $thisDev.parent().find("span.status").html("告警");
                                    $thisDev.parent().css("border","3px solid #FF3333");
                					$thisDev.parent().css("border-radius","15px");
                                }
                                if (monitorObjNormal) {
                                    $thisDev.parent().removeClass("error").removeClass("alert").removeClass("gray").removeClass("blue");
                                    $thisDev.parent().find("span.status").html("正常");
                                    $thisDev.parent().css("border","3px solid #00FF33");
                					$thisDev.parent().css("border-radius","15px");
                                }
                            }
                        }
                    }
				}
			}
			var s = $("#deviceStatus").text();
			if(s==="0"){
				$(".machine-list").children("div[class='item-wrap']").children("div[class!='item']").css("display","none");
			}else if(s==="1"){
				$(".machine-list").children("div[class='item-wrap']").children("div[class!='item alert']").css("display","none");
			}else if(s==="2"){
				$(".machine-list").children("div[class='item-wrap']").children("div[class!='item error']").css("display","none");
			}else if(s==="3"){
				$(".machine-list").children("div[class='item-wrap']").children("div[class!='item gray']").css("display","none");
			}else if(s==="4"){
				$(".machine-list").children("div[class='item-wrap']").children("div[class!='item blue']").css("display","none");
			}
		});
	};

	/**
     * 获取报警时间，若不存在返回300，单位秒
     */
	function getThresholdTime(monitorConf,devIdx) {
	    monitorConf = monitorConf || {};
	    var conf = monitorConf[""+devIdx];
	    if(conf){
	        for(var i = 0;i < conf.length;i++){
	            if(conf[i].table_name == "time_out"){
	                return conf[i].threshold;
                }
            }
        }

        return 300;
    }

	//获取对应 颜色 
	var getMaxAlertColor=function(color,maxColor){
		//0(绿色)正常  <1(提醒) <2(红色)告警 <4(蓝色)超时 <3(灰色) 该部件禁用状态
		//console.log("color:"+color,"maxColor:"+maxColor);
		if(color==3){
			return maxColor;
		}
		if(color>maxColor){
			return color;
		}
		return maxColor;
	};
	/**
		切屏
	**/
	/*var changeScreen=function(){
		//获取当前页
		var currentpage = $("li.active").html();//当前页
		page = Number(currentpage) + 1;//下一页
		var maxPage=$("#page").find("li:last").html();//最大页数
		if(currentpage==maxPage){//最后一页
			//var Page=makeQueryParam(1, pageSize);
			//selectPage(Page);
			$("#page").find("li").each(function(index,dom){
				if($(dom).html()=="1"){
					$(dom).trigger("click");
				}
			});
			return;
		}
		$("#page").find("li").each(function(index,dom){
			if($(dom).html()==page){
				//表示有下一页
				//var Page=makeQueryParam(page, pageSize);
				//selectPage(Page);
				$(dom).trigger("click");
			}
		});
	};*/
	/**
		切屏
	**/
	var changeScreen=function(){
		var lib_idx=$(".choose-lib").find(".current-libName").children().attr("lib-idx");
		if(typeof(lib_idx)!='undefined'){
			var items=$(".choose-lib").find(".drop-down").children();
			$(items).each(function(){
				var anthor_idx=$(this).attr("lib-idx");
				if(typeof(anthor_idx)!='undefined' && lib_idx==anthor_idx){
					lib_idx=$(this).attr("lib-idx");
					lib_name=$(this).text();
					$(".choose-lib").find(".current-libName").html("<input type='text' class='item place' lib-idx='"+lib_idx+"' value='"+lib_name+"'>");
					/* machine.curSelLibIdx=lib_idx;
					var Page=makeQueryParam(1,12);
					selectPage(Page);//查询 */
					return false;//跳出循环
				}
			});
		} 
	};  

	
	/**
		获取所有的没有不是子馆的馆、和一级子馆
		管理员使用此方法
	**/
	var proposals=new Array;//图书馆列表自动提示
	var queryAllMasterLibAndSlaveLib=function(library_idx){
		console.info("普通设备主馆idx:",library_idx);
		if(library_idx){
			var library={"library_idx":library_idx};
			$.ajax({
				type:"POST",
				url:"${ctx}/librarylocal/querySlaveLibraryByMasterIdx",
				data:{"req":JSON.stringify(library)},
			}).then(function(data){
				console.info("(普通设备)通过主机id查询子图书馆",data);
				if(data&&data.state==true){
					var masterLibAndSlaveLibs=data.result;
					if(masterLibAndSlaveLibs){
						var masterLibrary=masterLibAndSlaveLibs.masterLibrary;
						$(".choose-lib").find(".current-libName").html('<div class="item place" lib-idx="'+masterLibrary.library_idx+'">'+masterLibrary.lib_name+'</div>');
					}
				}
				return $.ajax({
					url:"${ctx}/librarylocal/queryAllMasterLibAndSlaveLib",
					type:"GET",
					data:{"req":JSON.stringify()}
				});
			}).then(function(data){
				console.log("(普通设备)查询所有主机和子图书馆:",data);
					if(data&&data.state){
					        proposals.length = 0;//清空一下数组
						var libList=data.result;
						$("div.page-title-bar").find(".drop-down").html("");
						//选择图书馆
						var dropDownHtml='<div class="item place" lib-idx="0">---请选择----</div>';
						for(var i=0;i<libList.length;i++){
							var msLibAndSlaveLib=libList[i];
							var masterLibrary=msLibAndSlaveLib.masterLibrary;
							var slaveLibraryArr=msLibAndSlaveLib.slaveLibrary;
							if(masterLibrary.library_idx==0){//云平台屏蔽
								continue;
							}
							proposals.push(masterLibrary.lib_name);
							//console.log(masterLibrary);
							dropDownHtml+='<div class="item place" lib-idx="'+masterLibrary.library_idx+'">';
							dropDownHtml+=masterLibrary.lib_name+'</div>';
							for(var j=0;j<slaveLibraryArr.length;j++){
								dropDownHtml+='<div class="item room" lib-idx="'+slaveLibraryArr[j].library_idx+'">'+slaveLibraryArr[j].lib_name+'</div>';
								proposals.push(slaveLibraryArr[j].lib_name);
							}
						}
						$("div.page-title-bar").find(".drop-down").html(dropDownHtml);
					}else if(data.message="NO_PERMESSION"){<%--不是系统管理员的情况下 只能查看当前馆或以下--%>
						checkQuerySlaveLibraryByMasterIdx(library_idx);
					}
			});
		}
	};
	/**
	 *	检查当前登录用户是不是主馆用户,不是执行 setLibraryName函数，
	 **/
	function  checkQuerySlaveLibraryByMasterIdx(library_idx){
		if(library_idx){
			var library={"library_idx":library_idx};
			$.ajax({
				type:"POST",
				url:"${ctx}/librarylocal/querySlaveLibraryByMasterIdx",
				data:{"req":JSON.stringify(library)}
			}).then(function(data){
				console.info("非系统管理员查询到主子馆：",data);
				if(data&&data.state==true){
					var masterLibAndSlaveLibs=data.result;
					if(masterLibAndSlaveLibs){
						var masterLibrary=masterLibAndSlaveLibs.masterLibrary;
						$(".choose-lib").find(".current-libName").html("<input type='text' class='item place' lib-idx='"+masterLibrary.library_idx+"' value='"+masterLibrary.lib_name+"'>");
					}
				}
				return $.ajax({
					url:"${ctx}/librarylocal/checkQuerySlaveLibraryByMasterIdx",
					data:{},
					type:"POST",
					success:function(data){
						if(data){
							if(data.state){
								isMaster=true;
								var masterLibAndSlaveLibs=data.result;
								if(masterLibAndSlaveLibs){
									var masterLibrary=masterLibAndSlaveLibs.masterLibrary;
									var slaveLibraryArr=masterLibAndSlaveLibs.slaveLibrary;
									if(masterLibrary&&slaveLibraryArr){
										console.info("主馆slaveLibraryArr:",masterLibrary);
										console.info("子馆slaveLibraryArr:",slaveLibraryArr);
										$("div.page-title-bar").find(".drop-down").html("");
										//选择图书馆
										var dropDownHtml='<div class="item place" lib-idx="0">---请选择----</div>'
										+'<div class="item place" lib-idx="'+masterLibrary.library_idx+'">'
										+masterLibrary.lib_name+'</div>';
										//$(".choose-lib").find(".current-libName").html('<div class="item place" autocomplete="off" class="item place autocomplete-input" lib-idx="'+masterLibrary.library_idx+'">'+masterLibrary.lib_name+'</div>');
										for(var i=0;i<slaveLibraryArr.length;i++){
											isMaster=true;
											dropDownHtml+='<div class="item room" lib-idx="'+slaveLibraryArr[i].library_idx+'">'+slaveLibraryArr[i].lib_name+'</div>';
										}
										$("div.page-title-bar").find(".drop-down").html(dropDownHtml);
									}
								}else{
									//错误信息
									console.log(data);
								}
							}
						}
						if(!isMaster){
							setLibraryName(library_idx);
						}
					}
				});
			});
		}
	}
	
	/**
		根据library_idx得到 Library数据
		非管理员使用此方法
	**/
	//设置图书馆名称函数
	var setLibraryName=function(library_idx){
		if(library_idx){
			var library={"library_idx":library_idx};
			$.ajax({
				type:"POST",
				url:"${ctx}/librarylocal/querySlaveLibraryByMasterIdx",
				data:{"req":JSON.stringify(library)},
			}).done(function(data){
				//console.log("setLibraryName",data);
				if(data&&data.state==true){
					var masterLibAndSlaveLibs=data.result;
					if(masterLibAndSlaveLibs){
						var masterLibrary=masterLibAndSlaveLibs.masterLibrary;
						var slaveLibraryArr=masterLibAndSlaveLibs.slaveLibrary;
					
						if(masterLibrary&&slaveLibraryArr){
							$("div.page-title-bar").find(".drop-down").html("");
							//选择图书馆
							var dropDownHtml='<div class="item place" lib-idx="0">---请选择----</div>'
							+'<div class="item place" lib-idx="'+masterLibrary.library_idx+'">'
							+masterLibrary.lib_name+'</div>';
							
							//$(".choose-lib").find(".current-libName").html('<div class="item place" lib-idx="'+masterLibrary.library_idx+'">'+masterLibrary.lib_name+'</div>');
							$(".choose-lib").find(".current-libName").html("<input type='text' class='item place' lib-idx='"+masterLibrary.library_idx+"' value='"+masterLibrary.lib_name+"'>");
						
							
							for(var i=0;i<slaveLibraryArr.length;i++){
								dropDownHtml+='<div class="item room" lib-idx="'+slaveLibraryArr[i].library_idx+'">'+slaveLibraryArr[i].lib_name+'</div>';
							}
							$("div.page-title-bar").find(".drop-down").html(dropDownHtml);
						}
					}else{
						//错误信息
						console.log(data);
					}
				}
			});
		}
	};
		
	/**
		名事件 （需要加上主馆 的 用户查询）
		<div class="choose-lib">
		<div class="select-btn">
			<span class="current-libName">选择图书馆</span>
			<span class="icon"></span>
		</div>
	**/
	$(".drop-down").on("click",".item",function(){
		$(".drop-down").hide();
		var libIdx=$(this).attr("lib-idx");
		var libName=$(this).html();
		//console.log(libIdx);
		//$(".choose-lib").find(".current-libName").html(libName);
		$(".choose-lib").find(".current-libName").html("<input type='text' class='item place' lib-idx='"+libIdx+"' value='"+libName+"'>");
	
		//if(libIdx>0){//==0表示查询所有
			//当做一个查询条件传参处理
			machine.curSelLibIdx=libIdx;
			var Page=makeQueryParam(1,12);
			selectPage(Page);
		//}
	});
	//收起左边下拉
	$("div.main").on("click",function(){
		$(".drop-down").hide();
	});
	
	//设置书架信息 参数为：device_id数组
	var setBookrackState=function(){
		var arr=new Array();
		 $(document).find("input._device_id").each(function (index,domEle){
			var device_id=$(domEle).val();
			arr.push(device_id);
		}); 

		$.ajax({
			type:"POST",
			url:"${ctx}/device/selectBookrackState",
			data:{"req":JSON.stringify(arr)}	
		}).done(function(data){
			if(data&&data.state==true){
				var result=data.result;
				for(var device_id in result){
					$thisDev=$(document).find("input[name=_device_id][value="+device_id+"]");
					var bookrack=result[device_id];
					var level1=bookrack.Level1||bookrack.level1;
					var level2=bookrack.Level2||bookrack.level2;
					var level3=bookrack.Level3||bookrack.level3;
					//var precheckout=result.precheckout;
					var exbox=bookrack.Exbox||(bookrack.exbox || '0/0');
					var level1Books=0,level2Books=0,level3Books=0,exboxBooks=0;
					if(level1)level1Books= Number(level1.split("/")[1]);
					if(level2)level2Books= Number(level2.split("/")[1]);
					if(level3)level3Books= Number(level3.split("/")[1]);
					if(exbox)exboxBooks= Number(exbox.split("/")[1]);
					//在架书
					var onRackBooks=level1Books+level2Books+level3Books+exboxBooks;			
					$thisDev.parent().find("div.sec2").find(".right.level1").html(onRackBooks+"册");
				}
			}
		});
	};
	
	
	//设置箱子信息
	var setDeviceBinState=function(){
		var arr=new Array();
		 $(document).find("input._device_id").each(function (index,domEle){
			var device_id=$(domEle).val();
			arr.push(device_id);
		}); 
		
		$.ajax({
			type:"POST",
			url:"${ctx}/device/selectBinState",
			data:{"req":JSON.stringify(arr)}
		}).done(function(data){
			if(data&&data.state==true){
				//console.log("箱子状态："+JSON.stringify(data));
				var result=data.result;
				for(var device_id in result){
					//device_id 现在实际上是 library_id+"_"+device_id
					$thisDev=$(document).find("input[name=_device_id][value="+device_id+"]");
					var binState=result[device_id];
					//console.log("device_id",device_id,"binstate",binState);
					var cardbin=binState.cardbin;//卡箱
					var cashbin=binState.cashbin;//钱箱
					var bookbin=binState.bookbin;//书箱
					//console.log("cardbin",cardbin);
					if(cardbin){
						$thisDev.parent().find("div.sec2").find(".right.level2").html(cardbin["amount"]+"张");
						$thisDev.parent().find("input[name=cardbin_amount]").val(cardbin.amount);
					}
					var sum=0;
					if(bookbin){
						for(var i=0;i<bookbin.length;i++){
							var amount=Number(bookbin[i].amount);
							sum+=amount;
						}
						$thisDev.parent().find("div.sec2").find(".right.level3").html(bookbin.length+"个/"+sum+"册");
					}
				
				}
			}
		});
	};
	
	//组装 翻页和查询 参数
	var makeQueryParam=function(page,pageSize){
	 	$inputText=$("div.page-title-bar").find("input[name=keyWord]");
	    var machineType=$("select#machineType").val();
		var machineState=$("select#machineState").val();
		var keyWord=$inputText.val();
		var Page ={
			"page":page,
			"pageSize":pageSize,
			"machineType":machineType,
			"machineState":machineState,
			"keyWord":keyWord,
		};
		if(machine.curSelLibIdx!=0 && (keyWord=="" || keyWord==null)){
			Page.library_idx=machine.curSelLibIdx;
		}
		return Page;
	};
	
	var _interval=null;
	var changeScreenInterval=null;
	var pageSize=12;//每页显示的条数
	//翻页函数 包括查询参数  selectPage
	var selectPage=function(obj){//
			$.ajax({
				url:"${ctx}/librarylocal/getLibIdAndLibIdx",
				data:{req:""},
				type:"GET"
			}).then(function(data){
				console.log("(普通设备)获取所有图书馆:",data);
				
				if(data&&data.state){
					library=data.result;
					$.ajax({
						url:"${ctx}/device/queryDeviceByParam",
						type:"POST",
						data:{"req":JSON.stringify(obj)}
					}).done(function(data){
						console.info("设备参数----",data);
						if(data){
							var page=data.result;
							if(page){
								console.info("(普通设备)page",page);
								console.info("(普通设备)machineServiceRows",page.rows);
								if(page.rows && page.rows.length>0){
									if(page.rows[0]){
										//setLibraryName(page.rows[0].library_idx);获取当前馆的library_idx
										queryAllMasterLibAndSlaveLib(page.rows[0].library_idx);
									}
									$(".machine-info").html("<span>设备总数："+page.total+"</span>").show();
									drawRow(page.rows);
								}else{
			 						$(".machine-info").empty().hide();
			 						$(".machine-list").find(".item-wrap").html("没有查询到匹配的设备");
								}
							 	$.pagination(page);
							}
						}
						//翻页 或者 加载之后先执行一次 setStatus
						//setStatus();
						GetCurrentDevColorSet();
						//每次选择页码结束，执行获取设备状态函数
						var seconds=$(".g-select.refresh select").val();//切屏频率
						clearInterval(changeScreenInterval);
						if(seconds!="-1"){
							changeScreenInterval=setInterval(changeScreen,seconds);
						}
						clearInterval(_interval);
						_interval=setInterval(setStatusProxy,50000);//周期 暂时注释掉 2016年5月10日08:47:33
						/* setDeviceBinState();//设置箱子信息
						setBookrackState();//设置在架书 */
					});
				}
			});
    };
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $("li.active").html();//当前页
		page = Number(currentpage) + 1;//下一页
		var Page=makeQueryParam(page, pageSize);
		//调用页面的查询ajax
		selectPage(Page);
	});
	$("#page").on("click",".prev-page",function(){
		var currentpage = $("li.active").html();
		var page=Number(currentpage)-1;
		var Page=makeQueryParam(page, pageSize);
		//带参数
		selectPage(Page);
	});
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))return;
		var page = $(this).html();
		if(page=="...") return;	
		var Page=makeQueryParam(page, pageSize);
		selectPage(Page);
	});
	
	var pageObj ={"page":1,"pageSize":12};
	var Page=makeQueryParam(pageObj);
	selectPage(pageObj);
	
	$.ajax({
		url:"${ctx}/device/queryMetadataOrder",
		type:"GET",
		data:{"req":"{}"}
	}).done(function(data){
		console.info("所有命令：",data);
			$("select#machineCmd").html('');
			var cmdArr = [];
			var o = <shiro:principal/>;
			o = o.userRolePermessions;
			console.info("o",o);
			for(var j in o){
				cmdArr.push(o[j].opercmd_desc);
			}
			console.info("cmdArr命令数组：",cmdArr);
			var html = "<option value=\"-1\" selected>选择状态</option>";
			for(var i=0;i<data.length;i++){
				var deviceTypeObj=data[i];
				var order_idx=deviceTypeObj.order_idx;
				var order_desc=deviceTypeObj.order_desc;
				
				if(order_desc=="关机"){
					if($.inArray("设备关机", cmdArr)){
						console.info("包含命令",order_desc);
						html += "<option value=\""+order_idx+"\">"+order_desc+"</option>"; 
					}
				}
				if(order_desc=="重启"){
					if($.inArray("设备重启", cmdArr)!=-1){
						console.info("包含命令",order_desc);
						html += "<option value=\""+order_idx+"\">"+order_desc+"</option>";
					}
				}
				/* if(order_desc=="查日志"){
					if($.inArray("下载运行日志", cmdArr)!=-1){
						console.info("包含命令","下载运行日志");
						html += "<option value=\""+order_idx+"\">"+order_desc+"</option>";
					}
				}
				if(order_desc=="远程维护锁屏"){
					if($.inArray("锁定客户端", cmdArr)!=-1){
						console.info("包含命令","锁定客户端");
						html += "<option value=\""+order_idx+"\">"+order_desc+"</option>";
					}
				}
				if(order_desc=="取消操作"){
					if($.inArray("取消", cmdArr)!=-1){
						console.info("包含命令","取消");
						html += "<option value=\""+order_idx+"\">"+order_desc+"</option>";
					}
				} */
			}
			$("select#machineCmd").html(html);
	});
	
	
	//SelectType获取设备类型
	$.ajax({
		url:"${ctx}/metadevicetype/selAllDeviceTypeGroupByType",
		data:{},
		type:"GET",
		success:function(data){
			if(data&&data.state==true){
				var deviceTypeArr=data.result;
				$("select#machineType").html('<option value="" selected>选择类型</option>');
				for(var i=0;i<deviceTypeArr.length;i++){
					var deviceTypeObj=deviceTypeArr[i];
					var device_type=deviceTypeObj.device_type;
					var device_type_desc=deviceTypeObj.device_type_desc;
					var opt='<option value="'+device_type+'">'+device_type_desc+'</option>';
					$("select#machineType").append(opt);
				}
			}
		}
	});
	
	function sleep(d){
  		for(var t = Date.now();Date.now() - t <= d;);
	}
	//循环调用 获取 执行结果 params =[{device_id:"",control:"",control_info:""},{device_id}]
	var HAS_RESULT=false;
	var getControlResult=function(params){
				$.ajax({
					url:"${ctx}/device/queryControlResult",
					type:"POST",
					data:{"req":JSON.stringify(params)}
				}).done(function(data){
					//console.log("getControlResult:"+JSON.stringify(data));
					if(data&&data.state==true&&data.message=="CURRENT_NO_RESULT"){
						// 暂时没有结果时继续AJAX
						console.log("CURRENT_NO_RESULT");
					}
					if(data&&data.state&&data.message=="HAS_RESULT"){
						//这里只处理了一台设备有返回的结果的情况,需要作出修改
						var result=data.result;
						//console.log("HAS_RESULT:"+JSON.stringify(result));
						if(result){
							for(var i=0;i<result.length;i++){
								var controlRes=result[i];
								var device_id=controlRes.device_id;
								if(controlRes.controlresult=="1"){//关机
									layer.alert(device_id+" 关机成功");
								}else if(controlRes.controlresult=="2"){//重启
									layer.alert(device_id+" 重启成功");
								}else if(controlRes.controlresult=="4"){//锁屏
									layer.alert(device_id+" 锁屏成功");
								}else if(controlRes.controlresult=="5"){//取消操作
									layer.alert(device_id+" 取消操作成功");
								}
							}
							HAS_RESULT=true;
						}
					}
				}).fail(function(e){
					layer.alert(e);
					HAS_RESULT=true;
				});
	};
	 function minusDate(date,days){ 
       var d=new Date(date); 
       d.setDate(d.getDate()-days); 
       var m=d.getMonth()+1; 
       return d.getFullYear()+'-'+m+'-'+d.getDate(); 
     } 
	/**
	*	发送机器执行命令	
	**/
	$("#sendbtn").on("click",function(){
		$cmd=$("#machineCmd");//order_idx
		var cmd=$cmd.val();
		//获取所有选择的checkbox
		var curCheckboxs = $(".item.active").find(".g-checkbox").find("input:checked");
		var params=new Array();
		if(curCheckboxs.length<1) {
			layer.alert("请选择至少一台设备");
			return;
		}
		if(cmd<0){
			layer.alert("请选择指令后再发送");
			return;
		}
		//获取当前页的图书馆IDX
		var library_idx=$(".current-libName").find(".item.place").attr("lib-idx");
		//获取ID
		for(var i=0;i<curCheckboxs.length;i++){
		    var dev=curCheckboxs[i];
		    var dev_control={
			    "device_id":dev.name,
			    "control":"1",
			    "control_info":cmd,
		    	"library_idx":library_idx
		    };
		    params.push(dev_control);
		}
		$.ajax({
			url:"${ctx}/device/sendOrder",
			type:"POST",
			data:{"req":JSON.stringify(params)},
		}).done(function(data){
			if(data.state==true&&data.message!="isAlreadyHasSameOrders"){
				layer.alert("正在等待设备执行命令");
				
			}else if(data.state==true&&data.message=="isAlreadyHasSameOrders"){
				var dev_ids=data.result;
				layer.alert("正在等待设备执行命令，其中ID:\n"+dev_ids+" \n设备命令已经发送过了，正在等待在执行");
			}else if(data.state==false&&data.result==-1){
				layer.alert(data.retMessage);
			}
			//console.log(data);
		}).done(function(){
			HAS_RESULT=false;
			//采用周期循环处理
			var Interval=null;
			Interval=setInterval(function(){
				getControlResult(params);
				if(HAS_RESULT){
					clearInterval(Interval);//清除
				}
			},60000);
			//这里执行轮询获取执行结果controlResult
		});
	});

	$(document).on("change",":checkbox",function(){
		var $parentItem = $(this).parents(".item");
		if($(this).is(":checked")){
			$parentItem.addClass("active");
		}else{
			$parentItem.removeClass("active");
		}
	});

	(function checkAllCheckbox(){
		$(document).find(".item.active").find(".g-checkbox").addClass("on").find("input").prop("checked",true);
	})();

	var winH = $(window).height();
	var dialogH = winH-20;
	
	/**
		详细页面
	**/
	$(".machine-list").on("click",".check-detail",function(){
		//获取到IDX
        $item=$(this).parent().parent();
        var device_type=$item.find("._device_type").val();
        var device_idx=$item.find("._device_idx").val();
		var device_name=$item.find(".sec1").find(".machine-name").text();
		var device_id=$item.find("._device_id").val();//lib_id and device_id
		var library_idx=$item.find("._library_idx").val();//lib_id and device_id
		var library_id=library[library_idx].lib_id;
		//卡数量
		var cardNum=$item.find("div.sec2").find(".right.level2").text().replace(/[^0-9]/ig,"");
		if(cardNum==null || cardNum==""){
			cardNum=$item.find("input[name=cardbin_amount]").val();
		}
		var url="${ctx}/device/deviceDetail?device_idx="+device_idx+"&device_id="+encodeURIComponent(device_id)+"&cardNum="+cardNum+"&library_id="+encodeURIComponent(library_id)+"&device_type="+device_type;
		//这里先判断以下session有没有过期，过期则跳转到登陆页面
		$.getJSON("${ctx}/login/checkSession",{},function(data){
			if(data&&data.checkSession){
				layer.open({
						type: 2, 
						content: [url],
						title :false,
						//closeBtn :1,
						//offset :["20px"],
						area :["860px",'98%'],
						shade:0.5, 
						shadeClose :true,
						scrollbar :true,	
						move:false,
						//skin:false,
						success :function(layero, index){
							var body = layer.getChildFrame('body', index);
							 // var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
							 // console.log(body.html()) //得到iframe页的body内容
						 	body.find("div.head").find(".name").prepend('<span>'+device_name+'</span>');
							body.find("div.head").prepend('<input hidden="hidden" id="library_idx" value="'+library_idx+'"/>');
							body.find("div.head").prepend('<input hidden="hidden" id="device_id" class="dev_id" value="'+device_id+'"/>');
						    body.find("div.head").prepend('<input hidden="hidden" id="device_type" value="'+device_type+'"/>');	
						}					
					});
	  	  }
		});
		//alert("ddfdsf");
		//$(".check-detail").unbind("click"); 
	});

	var old_lib_name;
	$(document).on("click",".choose-lib .select-btn",function(){
		old_lib_name=$(this).find("input").val();
		$(this).find("input").val("");
		$(this).find("input").focus();
		$(this).next(".drop-down").toggle();
		clearInterval(changeScreenInterval);
	});
	
	$(".choose-lib .select-btn").on("keyup", function(e){
		if(e.which != 13 && e.which != 27 && e.which != 38 && e.which != 40){				
			var word = "^" + $(this).find("input").val() + ".*";
			for(var i in proposals){
				var txt = proposals[i];
				if(txt.match(word)){
					var selectItem=$(this).next(".drop-down").find(".item:contains("+txt+"):first");
					if(selectItem){
						selectItem.siblings().css({"background-color":"#FFF","color":"#888888"});
						selectItem.css({"background-color":"#00a2e9","color":"#FFF"});
						$(this).next(".drop-down").animate({scrollTop:$(selectItem).offset().top-$(this).next(".drop-down").offset().top});
						return false;
					}
				}else{
					$(this).next(".drop-down").find(".item").css({"background-color":"#FFF","color":"#888888"});
				}
			}
		}else if(e.which == 13){
			var $that=$(this);
			$that.next(".drop-down").find(".item").each(function() {
				if($(this).css("color")=="rgb(255, 255, 255)"){
					$(this).trigger("click");
					$(this).parents(".drop-down").hide();
				}
			});
			var seconds=$(".g-select.refresh select").val();//切屏频率
			if(seconds!="-1"){
				clearInterval(changeScreenInterval);
				changeScreenInterval=setInterval(changeScreen,seconds);
			}
		}
	});
	
	//点击输入后刷新
	$(document).on("click",".choose-lib .select-btn input",function(){
		var seconds=$(".g-select.refresh select").val();//切屏频率
		if(seconds!="-1"){
			clearInterval(changeScreenInterval);
			changeScreenInterval=setInterval(changeScreen,seconds);
		}
	}); 
	
	//选择好图书馆后隐藏下拉框
	$(document).on("click",".choose-lib .drop-down .item",function(){
		$(this).parents(".drop-down").hide();
	});
	//鼠标移出输入框，隐藏下拉框
	$(document).on("mouseout",".select-btn",function(){
		$(this).next().hide();
	}); 
	//鼠标移到输入框，显示下拉框
	$(document).on("mouseover",".select-btn",function(){
		$(this).next().show();
	}); 
	//鼠标移到下拉框，显示下拉框本身
	$(document).on("mouseover",".drop-down",function(){
		$(this).show();
	}); 
    //鼠标移除下拉item，将鼠标移到的位置item，显示到当前的current-name，并显示下拉框(很重要)
	$(document).on("mouseover",".drop-down .item",function(){
		$(".drop-down").show();
		lib_name=$(this).text();
		lib_idx=$(this).attr("lib-idx");
		$(".choose-lib").find(".current-libName input").val(lib_name);//html("<input type='text' class='item2 place2' lib-idx='"+lib_idx+"' value='"+lib_name+"'>");
	});
	//鼠标移出下拉框(并未点击)，将input中原始值的设置到输入框
	$(document).on("mouseout",".drop-down",function(){
		var lib_idx=$(".choose-lib").find(".current-libName").children().attr("lib-idx");
		if(typeof(lib_idx)!='undefined'){
			var items=$(".choose-lib").find(".drop-down").children();
			$(items).each(function(){
				var anthor_idx=$(this).attr("lib-idx");
				if(typeof(anthor_idx)!='undefined' && lib_idx==anthor_idx){
					lib_idx=$(this).attr("lib-idx");
					lib_name=$(this).text();
					$(".choose-lib").find(".current-libName").find("input").attr("initValue",lib_name);//html("<input type='text' class='item2 place2' lib-idx='"+lib_idx+"' value='"+initVal+"'>");
					$(".choose-lib").find(".current-libName").find("input").attr("initLibidx",lib_idx);
				}
			});
		}
	}); 
	$(document).on("mouseout",".drop-down",function(){
		var lib_idx = $(".choose-lib").find(".current-libName").find("input").attr("initLibidx");
		var initval = $(".choose-lib").find(".current-libName").find("input").attr("initValue");
		//alert(lib_idx+initval);
		$(".choose-lib").find(".current-libName").html("<input type='text' class='item place' lib-idx='"+lib_idx+"' value='"+initval+"'>");
		$(this).hide();
	}); 


	$(".play-btn,.pause-btn").on("click",function(){
		$(this).hide().siblings().show();
	});
	
	$(document).on("click",".machine-name",function(){
		var curCheckbox = $(this).siblings(".g-checkbox").find("input");

		if(curCheckbox.is(":checked")){
			curCheckbox.prop("checked",false);
		}else{
			curCheckbox.prop("checked",true);
		}

		var $parentItem = $(this).parents(".item");
		if(curCheckbox.is(":checked")){
			$parentItem.addClass("active").find(".g-checkbox").addClass("on");
		}else{
			$parentItem.removeClass("active").find(".g-checkbox").removeClass("on");
		}
		var num=$(".machine-list").find("input[type=checkbox]:checked").length;
		$("#ChooseNum").find(".total-choosen-num").html(num);
	});
	//改变刷新时间
	$(document).on("change",".g-select.refresh select",function(){
		var seconds=$(this).val();//获取刷新周期
		//更改周期
		clearInterval(changeScreenInterval);
		if(seconds==-1){
			//不切屏
		}else{
			changeScreenInterval=setInterval(changeScreen,seconds);
		}
	}); 
	//
	//点击查询按钮，查询
	//
	$(".btn.search").on("click",function(){
		 //查询条件 设备类型 设备状态 关键字（device_name）
		 var Page=makeQueryParam(1, pageSize);
		 console.info("查询时参数:",Page);
		 selectPage(Page);
	});
	
});
</script>
</body>
</html> 





