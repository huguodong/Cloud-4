<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
	<style type="text/css">
		.equipment-guanli .col2 {
    		width: 150px;
		}
		.equipment-guanli .col10 {
    		width: 200px;
		}
	</style>
</head>
<body>
<div class="equipment-guanli">
	<div class="page-title-bar">
		<!-- <a href="" class="back-btn"></a> -->
		<span class="title"><sp:message code="服务管理"></sp:message><a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/devmgmt/devicemgmt.jsp" target="_blank" class="g-help"></a></span>
		<div class="form-wrap fr">
			
			<div class="g-select" id="library_div">
				<select id="library">
					<option value="-1"><sp:message code="devicemgmt.jsp.searchlibrary"></sp:message></option>
				</select>
				<span class="arr-icon"></span>
			</div>
			
			<input type="text" name="" id="service_name" class="input g-input" placeholder="<sp:message code="请输入服务名称"></sp:message>" />
			<div class="btn search" ><sp:message code="message.gloable.search"></sp:message></div>
			<!-- 设备修改根据权限代码控制 modify by huanghuang 20170215 -->
			<%-- <c:if test="${operatorType==1 or operatorType==2}">
				<div class="btn increase g-btn-green" ><sp:message code="批处理"></sp:message></div>
			</c:if> --%>
			
			<div class="btn increase g-btn-green" ><sp:message code="服务注册"></sp:message></div>
			
			
			<div class="btn delete"><sp:message code="message.gloable.delete"></sp:message></div>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
					<th class="col2"><sp:message code="服务类型"></sp:message></th>
					<th class="col3"><sp:message code="服务号"></sp:message></th>
					<th class="col4"><sp:message code="服务名称"></sp:message></th>
					<th class="col5"><sp:message code="图书馆"></sp:message></th>	
					<th class="col6"><sp:message code="操作"></sp:message></th>
				</tr>
			</thead>
			<tbody>
			
			</tbody>
		</table>
	</div>
	<%@ include file="../include/page_bar.jsf" %>
</div>
<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		<sp:message code="devicemgmt.jsp.curselect"></sp:message> <span class="font-red"></span> <sp:message code="devicemgmt.jsp.selectunit"></sp:message><br>
		<sp:message code="devicemgmt.jsp.delmonitortip"></sp:message>
	</div>
	<div class="form-btn cancel g-btn-gray"><sp:message code="message.gloable.cancel"></sp:message></div>
	<div class="form-btn remove g-btn-red"><sp:message code="message.gloable.delete"></sp:message></div>
</div>


<script type="text/javascript">


$(function(){
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();

		$(".main").css({
			"min-height":winH - headerH - pagingBarH -pageTitleBar
		});
	})();
	
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
	<%--点击返回按钮 返回的信息--%>
	var returnInfo=<%=request.getParameter("returnInfo")%>;
	
	var delLayer = null;
	/**
	 * 删除操作按钮
	 **/

	var service_idx = new Array();
	var libraryInfos={};
	$(".delete").on("click",function(){
		service_idx.length = 0;
		
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			service_idx.push($(this).attr("id"));
        }); 
		if(num>0){
			$(".g-delete-dialog .word").html("");
        	$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"个项目<br>是否要删除选择的服务");
			delLayer = layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				scrollbar :false,
				closeBtn :0,
				shade:0.5,
				shadeClose :false,
				area:["400px"],
				offset :["195px"],
				content: $('.g-delete-dialog'), //捕获的元素
		   });
		}else{
			layer.alert("请选择要删除的服务");
		}		
	});
	
	//删除当前行的设备分组
	$("tbody").on("click",".delete",function(){
		
		service_idx.length = 0;
		service_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		
		$('.g-delete-dialog .word').html("");
		$('.g-delete-dialog .word').append("是否要删除选择的服务");
		delLayer = layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.g-delete-dialog'), //捕获的元素
			cancel: function(index){
				layer.close(curLayer);
				this.content.hide();
			}
		});
	});
	
	//编辑操作
	$("tbody").on("click",".edit",function(){
		
		var library_idx=$("#library").val()
		
		var service_name=$("#service_name").val();
		if(library_idx == -1){
			library_idx = "";
		}	
		var page =$(".paging-bar li.active").html();
		var size= $('#showSize option:selected').html();
		var returnParams={
				"library_idx":library_idx,
				"service_name":service_name,
				"page":page,
				"pageSize":size
		};	
		
		var idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
		var param ={"service_idx":idx};
		
		<%-- 加载设备编辑页面 deviceedit.jsp--%>
		$("#mainframe").load(basePath+"/deviceService/editDeviceServiceRegister",{"req":JSON.stringify(param),"returnInfo":JSON.stringify(returnParams)},function(){
			$(".form-dialog-fix .form-dialog").each(function(){
				$(this).attr("date-right",$(this).css("right"));
			});
		});
		
		
	});

	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	//删除操作
	$(".form-btn.remove").on("click",function(){
		GlobalGLoading();
		if(!service_idx) return;
		var param = new Array();
		for(var i=0;i<service_idx.length;i++){
			 param[i]={
				"service_idx":service_idx[i]
			};
		}
		$.ajax({
			url:basePath+"/deviceService/deleteDeviceService",
			type:"POST",
			data:{
				"req":JSON.stringify(param)
			},
			success:function(data){
				//console.log(".form-btn.remove ",data);
				if(delLayer){
					layer.close(delLayer);
				}
				if(data.state){
					queryDeviceService();
				}
			}
		});
		
	});
	
	//封装的查询操作
	jQuery.select=function(param){
		
		$("tbody").html("");
		$.ajax({
			url:basePath+"/deviceService/queryDeviceServiceByPage",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				
				$.each(data.result.rows,function(i,item){
					$("tbody").append( "<tr>"+
			            "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.service_idx+"' /></div></td>"+
						"<td class='col2'>"+item.service_type_name+"</td>"+
						"<td class='col3'>"+item.service_id+"</td>"+
						"<td class='col4 device_id'>"+item.service_name+"</td>"+
						"<td  class='col11' id='"+item.service_idx+"_lib' style='display:none'>"+item.library_idx+"</td>"+
						"<td class='col10'><span class='btn-a edit'>编辑</span>"+
	                    "<span class='btn-a delete'>删除</span></td>" +
	                	"</tr>");
						$.each(libraryInfos,function(i,libInfo){
						if(libInfo.library_idx == item.library_idx){
							$("#"+item.service_idx+"_lib").html(libInfo.lib_name);
						}
				      }); 
					 $("#"+item.service_idx+"_lib").show();
					
				});
				
				$.pagination(data.result);  
			}
		});	
	};
	
	function findLibraryInfos(){
		
		libraryInfos = null;
		var param = {};
		$.ajax({
	   		url:basePath+"/library/querylibInfoByCurUserEXT1",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			async:false,
			success:function(data){
				libraryInfos = data.result;
	   		}
    	});
	}; 
	
	/**
		查询按钮操作
	**/
	$(".search").on("click",function(){
		
		queryDeviceService();
	});
	
	function queryDeviceService(){
		var library_idx=$("#library").val()
		var size= $('#showSize option:selected').text();
		var service_name=$("#service_name").val();
		if(library_idx == -1){
			library_idx = "";
		}	
		var param={
				"library_idx":library_idx,
				"service_name":service_name,
				"page":"1",
				"pageSize":size
		};
		$.select(param);
	}
	
	
	function isNull(param){
		if(param == null){
			return "";
		}else{
			return param;
		}
	}
	
	$(document).ready(function() {
    	$.ajax({
	   		url:basePath+"/library/querylibInfoByCurUserEXT1",
			type:"POST",
			success:function(data){
			$("#library").html("<option value='-1' selected>选择图书馆</option>");
				$.each(data.result,function(i,item){
					$("#library").append(
					"<option value='"+item.library_idx+"'>"+item.lib_name+"</option>" 
					);
	            });
				if(returnInfo&&returnInfo.library_idx){
					$("#library").val(returnInfo.library_idx);
				}
				
	   		}
    	});
 	});
 	
 	 //上一页操作
	$("#page").on("click",".prev-page",function(){
		
		var library_idx=$("#library").val()
		
		var service_name=$("#service_name").val();
		if(library_idx == -1){
			library_idx = "";
		}	
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size= $('#showSize option:selected').html();
		
		var param={
				"library_idx":library_idx,
				"service_name":service_name,
				"page":page,
				"pageSize":size
		};	
		$.select(param);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		
		var library_idx=$("#library").val()
		
		var service_name=$("#service_name").val();
		if(library_idx == -1){
			library_idx = "";
		}	
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $('#showSize option:selected').html();
		
		var param={
				"library_idx":library_idx,
				"service_name":service_name,
				"page":page,
				"pageSize":size
		};	
		$.select(param);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var page = $(this).html();
		if(page=="...") return;
		var library_idx=$("#library").val()
		var service_name=$("#service_name").val();
		if(library_idx == -1){
			library_idx = "";
		}	
		var size= $('#showSize option:selected').html();
		
		var param={
				"library_idx":library_idx,
				"service_name":service_name,
				"page":page,
				"pageSize":size
		};	
		$.select(param);
		
	}); 
	
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var library_idx=$("#library").val()
		var service_name=$("#service_name").val();
		if(library_idx == -1){
			library_idx = "";
		}
		var size= $('#showSize option:selected').html();
		
		var param={
				"library_idx":library_idx,
				"service_name":service_name,
				"page":1,
				"pageSize":size
		};	
		$.select(param);
	});
	
	$(function() {
		
		findLibraryInfos()
		var param = {
			"page" : "1",
			"pageSize" : $('#showSize option:selected').text(),
		};
		var info=<%=request.getAttribute("returnInfo")%>;
		if(info != null&& info != ''){
			param.library_idx = info.library_idx;
			param.service_name = info.service_name;
			param.page = info.page;
			param.pageSize = info.pageSize;
		}
		$.select(param);
	});
	
	$(".increase").on("click",function(){
		
		var library_idx=$("#library").val()
		
		var service_name=$("#service_name").val();
		if(library_idx == -1){
			library_idx = "";
		}	
		var page =$(".paging-bar li.active").html();
		var size= $('#showSize option:selected').html();
		var param={
				"library_idx":library_idx,
				"service_name":service_name,
				"page":page,
				"pageSize":size
		};	
		
		$("#mainframe").load(basePath+"/deviceService/deviceServiceRegister",{"req":JSON.stringify(param)},function(){
			$(".form-dialog-fix .form-dialog").each(function(){
				$(this).attr("date-right",$(this).css("right"));
			});
		});
		
		
	});
	
});
</script>
</body>
</html>


