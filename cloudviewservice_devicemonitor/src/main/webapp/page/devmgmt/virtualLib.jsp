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
<%@ include file="../include/machine.jsf" %> 
<%@ include file="../include/machineServer.jsf" %>
<style type="text/css">
	.machine-list .item .sec3 .checkleftdetail{
	float: left;
	padding-left:16px;
	cursor: pointer;
	background:url(${ctx}/static/images/arr-left.png) left center no-repeat;
}

.page-title-bar .btn {
    color: #888888;
}
</style>
</head>
<body> 

<div>
<div class="page-title-bar">
	<span class="smalltitle" id="lib_title">华南虚拟图书馆</span>
	<div id="mainDiv" class="form-dialog-fix w-900 yingjian">
		<div class="shade"></div>
		<div class="form-dialog" style="width:85%;left:-185%;padding-top: 0px;">
			<jsp:include page="machine.jsp"/>
	  	</div>
  	</div> 
  	
  	<div id="rllDiv" class="rllStyle w-900 yingjian">
		<div class="shade2"></div>
		<div class="rll-dialog" style="width:85%;left:-185%;padding-top: 0px;" date-right="0px">
			<jsp:include page="machineService.jsp"/>
	  	</div>
  	</div>	
</div>

<div class="main" style="background-color:white;overflow-y:scroll;">
		<div class="machine-info" id="library-info">图书馆总数：</div>
		<div class="machine-list">
			<div class="item-wrap" id="library_list">
				
			</div>
		</div>
</div>
	
</div>
<script type="text/javascript">

$(function(){
	var oper = <shiro:principal/>;
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
	
	/**加载页面**/
	$itemWrap3=$("#library_list");
	querySlaveLibByMasterLib();
	
	var drawLibRow=function(rows){
		if(!rows)return;
		for(var i=0;i<rows.length;i++){
			var library=rows[i];
			var item='<div class="item" style="border:3px solid #AAAAAA;border-radius:15px;">'
			 	+'<div class="sec1">'
			 	+'<a title="'+library.lib_name+'"><span class="machine-name" style="display:block;width:150px;height:27px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;float:left;">'+library.lib_name+'</span></a>'
			 	+'</div>';
			 	//现在只有自助借还图书馆显示以下信息
				item+='<div class="sec2" style="height:120px;width:420px">'
					+'<ul class="device_state">'
					+'	<li class="left" style="width:205px">今日借还量：<span class="center">-</span></li>'
					+'	<li class="right" style="width:205px">当月借还量 ：<span class="center">-</span></li>'
					+'	<li class="left" style="width:205px">今日办证量：<span class="center">-</span></li>'
					+'	<li class="right" style="width:205px">当月办证量：<span class="center">-</span></li>'
					+'	<li class="left" style="width:205px">今日人流量(进/出)：<span class="center">-</span></li>'
					+'	<li class="right" style="width:205px">当月人流量(进/出)：<span class="center">-</span></li>'
					+'</ul>' 
					+'</div>';
			 	item+='<div class="sec3">'
			 		+'<div class="checkleftdetail" name="lib_idx_txt_rll" id="'+library.library_idx+'">查看服务</div>'
				 	//+'ID:'+library.lib_id
				 	+'<div class="check-detail" name="lib_idx_txt" id="'+library.library_idx+'">查看设备</div>'
				 	+'</div>'
				 	+'</div>';
				$itemWrap3.append(item);
		};
		$("#library-info").html("<span>图书馆总数："+rows.length+"</span>").show();
		getLibState();
	};
	
	function querySlaveLibByMasterLib(){
		
		$itemWrap3.empty();
		$("#lib_title").text(oper.lib_name);
		var params = {"library_idx":oper.library_idx};
		$.ajax({
			type:"POST",
			url:"${ctx}/librarylocal/querySlaveLibraryByMasterIdx",
			data:{"req":JSON.stringify(params)},
			success:function(resultData){
				if(resultData.state){
					drawLibRow(resultData.result.slaveLibrary);
				}
			}
		});
	}
	
	//获取设备状态
	function getLibState(){
		
		$(document).find("div[name=lib_idx_txt]").each(function (index,domEle){
			
			
			var library_idx = $(this).attr("id");
			var param = {"library_idx":library_idx};
			//今日借还量。
			var object1 = $(domEle).parent().parent().find("ul").children("li:nth-child(1)").children("span");
			var params1 = {"type":"today","library_idx":library_idx,"forTotal":true};
			countLibLoanLog(object1,params1);
			//当月借还量
			var object2 = $(domEle).parent().parent().find("ul").children("li:nth-child(2)").children("span");
			var params2 = {"type":"month","library_idx":library_idx,"forTotal":true};
			countLibLoanLog(object2,params2);
			
			//今日办证
			var object3 = $(domEle).parent().parent().find("ul").children("li:nth-child(3)").children("span");
			var params3 = {"type":"today","library_idx":library_idx,"forTotal":true};
			
			countLibCardissueLog(object3,params3);
			
			//当月办证
			var object4 = $(domEle).parent().parent().find("ul").children("li:nth-child(4)").children("span");
			var params4 = {"type":"month","library_idx":library_idx,"forTotal":true};
			countLibCardissueLog(object4,params4);
			
			//今日人流量
			var object5 = $(domEle).parent().parent().find("ul").children("li:nth-child(5)").children("span");
			var params5 = {"type":"today","library_idx":library_idx,"forTotal":true};
			countLibVisitorLog(object5,params5);
			//当月人流量
			var object6 = $(domEle).parent().parent().find("ul").children("li:nth-child(6)").children("span");
			var params6 = {"type":"month","library_idx":library_idx,"forTotal":true};
			countLibVisitorLog(object6,params6);
			
			getLibDeviceCount($(this),param);
			var rllObject = $(domEle).parent().find("div[name=lib_idx_txt_rll]");
			selectCountDeviceService(rllObject,param);
			
		});
	}
	
	
	function getLibDeviceCount(object,params){
		
		$.ajax({
			url:"${pageContext.request.contextPath}/device/selectDeviceCountByLibraryIdx",
	        type:"POST",
			data: {"req":JSON.stringify(params)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					if(data.result != null){
						object.text("查看设备("+data.result+")");
					}
				}
			}
		});
		
	}
	
	function selectCountDeviceService(object,params){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/selectCountDeviceService",
	        type:"POST",
			data: {"req":JSON.stringify(params)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					if(data.result != null){
						object.text("查看服务("+data.result+")");
					}
				}
			}
		});
	}
	
	function countLibLoanLog(object,params){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countLoanLog",
	        type:"POST",
			data: {"req":JSON.stringify(params)},
			dataType: 'json',
			//async: false,
			success: function(data){
				if(data.state){
					var resultData = data.result;
					if(resultData!=null){
						var loan_total=0,return_total=0;
						for(var data in resultData){
							if(resultData[data].name=="借书"){
								loan_total = resultData[data].value;
							}
							if(resultData[data].name=="还书"){
								return_total = resultData[data].value;
							}
						}
						$(object).text(loan_total+"/"+return_total);
					}
				}
			}
		});  
	} 
	
	
	function countLibCardissueLog(object,params){
		
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countCardissueLog",
            type:"POST",
			data: {"req":JSON.stringify(params)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					var resultData = data.result;
					if(resultData!=null){
						var issCard_total=0;
						for(var data in resultData){
							if(resultData[data].name=="办证"){
								
								issCard_total = resultData[data].value;
							}
						}
						
						$(object).text(issCard_total);
					}
				}
			}
		});	
	}
	
	function countLibVisitorLog(object,params){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/countVisitorLog",
            type:"POST",
			data: {"req":JSON.stringify(params)},
			dataType: 'json',
			success: function(data){
				if(data.state){
					var resultData = data.result;
					if(resultData!=null){
						var in_totals = 1;
						var out_totals=1;
						if(resultData.in_total){
							for(var count in resultData.in_total){
								in_totals +=resultData.in_total[count];
							}
						}
						if(resultData.out_total){
							for(var count in resultData.out_total){
								out_totals +=resultData.out_total[count];
							}
						} 
						$(object).text(in_totals+"/"+out_totals);
					}
				}
			}
		});
	}
	
	$(".machine-list").on("click","div[name=lib_idx_txt]",function(){
		var Page=makeQueryParam(1, 12);
		var library_idx = $(this).attr("id");
		Page.library_idx=library_idx;
		selectPage(Page);
		$(".form-dialog-fix .shade").show();
		$(".form-dialog-fix.w-900.yingjian").fadeIn(1000);
		 $(".form-dialog-fix.w-900.yingjian").find(".form-dialog").animate({
			"right":"-170%"
		}); 
	});
	
	
	
	
	$("#library_list").on("click",".checkleftdetail",function(){
		var Page=makeQueryParam2(1, 12);
		var library_idx = $(this).attr("id");
		Page.library_idx=library_idx;
		selectPage2(Page);
		$(".rllStyle .shade2").show();
		$(".rllStyle").fadeIn(1000);
		$(".rllStyle").find(".rll-dialog").animate({
			"right":"-170%"
		});
		$("#rllDiv").css("top",60);
	});
	
	window.setInterval(getLibState, 6000);
	
});
</script>
</body>
</html> 





