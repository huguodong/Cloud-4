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
		.form-dialog .form-wrap{
			padding:20px 0 50px;
		}
		.form-dialog .form-wrap .left{
			width:180px;
		}
		.form-dialog .form-wrap .right{
			width: 380px;
		}
	</style>
</head>
<body>
<div class="equipment-guanli">
	<div class="page-title-bar">
		<!-- <a href="" class="back-btn"></a> -->
		<span class="title"><sp:message code="第三方应用管理"></sp:message><a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/devmgmt/devicemgmt.jsp" target="_blank" class="g-help"></a></span>
		<div class="form-wrap fr">
			
			<div class="g-select" id="library_div">
				<select id="library">
					<option value="-1"><sp:message code="devicemgmt.jsp.searchlibrary"></sp:message></option>
				</select>
				<span class="arr-icon"></span>
			</div>
			
			<input type="text" name="" id="service_name_query" class="input g-input" placeholder="<sp:message code="请输入应用名称"></sp:message>" />
			<div class="btn search" ><sp:message code="message.gloable.search"></sp:message></div>
			<div class="btn increase g-btn-green" ><sp:message code="message.gloable.add"></sp:message></div>
			<div class="btn delete"><sp:message code="message.gloable.delete"></sp:message></div>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
					<th class="col2"><sp:message code="AppKey"></sp:message></th>
					<th class="col2"><sp:message code="应用名称"></sp:message></th>
					<th class="col2"><sp:message code="图书馆"></sp:message></th>	
					<th class="col2"><sp:message code="请求IP"></sp:message></th>	
					<th class="col2"><sp:message code="限制次数"></sp:message></th>	
					<th class="col2"><sp:message code="是否启用"></sp:message></th>	
					<th class="col2"><sp:message code="操作"></sp:message></th>
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

<div class="form-dialog" id="add">
	<div class="title"><sp:message code="编辑第三方应用"></sp:message></div>
	<input type="hidden" name="service_idx" id="service_idx"/>
	<div class="form-wrap">
		<ul>
			<li><!--红色框体 li  class="error" -->
				<div class="left">AppKey</div>
				<div class="right">
					<input type="text" name="app_key" id="app_key" class="g-input" style="color:gray;background-color:#E0E0E0" readonly/>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">应用名称</span></div>
				<div class="right">
					<input type="text" name="service_name" id="service_name" class="g-input" placeholder='请填写第三方应用名称'  />
					<span class="error-msg">应用名称不能为空</span>
				</div>
			</li>
			<li>
				<div class="left">应用描述</div>
				<div class="right">
					<textarea name="service_desc" id="service_desc" class="g-textarea" placeholder="请输入应用描述"></textarea>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">图书馆ID</span></div>
				<div class="right">
					<input type="hidden" name="library_idx" id="library_idx"/>
					<input type="text" name="library_id" id="library_id" class="g-input" placeholder='请填写图书馆ID'  />
					<span class="error-msg">图书馆ID不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">图书馆名称</span></div>
				<div class="right">
					<input type="text" name="library_name" style="color:gray;background-color:#E0E0E0" readonly="readonly" id="library_name" class="g-input"/>
				</div>
			</li>
			<li>
				<div class="left">请求IP</div>
				<div class="right">
					<input type="text" name="request_ip" id="request_ip" class="g-input" placeholder='请填写请求IP'  />
				</div>
			</li>
			<li>
				<div class="left"><span>限制访问次数</span></div>
				<div class="right">
					<input type="text" name="access_times" id="access_times" class="g-input" style="width:100px;display: inline;" value="0"/>
					<label for="queryType-1" class="label-type">* 数值类型，默认为0，表示不限制</label>
				</div>
			</li>
			<li>
				<div class="left"><span>是否启用</span></div>
				<div class="right">
					<div class="g-radio on"><input name="isDisable" value="1" type="radio"></div>
					<label for="queryType-1" class="label-type">启用</label>
					<div class="g-radio"><input name="isDisable" value="0" type="radio"></div>
					<label for="queryType-1" class="label-type">禁用</label>
				</div>
			</li>
			<li>&nbsp;</li>
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input id="increaseBtn"  type="button" value="保存" class="submit g-btn-blue" />
				</div>
			</li>
		</ul>
	</div>
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
	
	var delLayer = null;
	var editLayer = null;
	var libraryInfos={};
	/**
	 * 删除操作按钮
	 **/
	var service_idx = new Array();
	$(".delete").on("click",function(){
		service_idx.length = 0;
		
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			service_idx.push($(this).attr("id"));
        }); 
		if(num>0){
			$(".g-delete-dialog .word").html("");
        	$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"个项目<br>是否要删除选择的应用");
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
			layer.alert("请选择要删除的应用");
		}		
	});
	
	//删除当前行的应用
	$("tbody").on("click",".delete",function(){
		
		service_idx.length = 0;
		service_idx.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		
		$('.g-delete-dialog .word').html("");
		$('.g-delete-dialog .word').append("是否要删除选择的应用");
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
				layer.close(delLayer);
				this.content.hide();
			}
		});
	});
	
	//编辑操作
	$("tbody").on("click",".edit",function(){
		var idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
		var param ={"service_idx":idx};
		reset();
		$.ajax({
			url:basePath+"/thirdPartyService/queryThirdPartyServiceByParams",
			type:"POST",
			data:{
				"req":JSON.stringify(param)
			},
			success:function(data){
				if(data&&data.state){
					var result = data.result;
					
					$("#service_idx").val(result[0].service_idx);
					$("#app_key").val(result[0].app_key);
					$("#service_name").val(result[0].service_name);
					$("#service_desc").val(result[0].service_desc);
					
					var library_idx=result[0].library_idx;
					$("#library_idx").val(library_idx);
					if(typeof(library_idx)!='undefined' && libraryInfos){
						$.each(libraryInfos,function(i,item){
							if(item.library_idx==library_idx){
								$("#library_name").val(item.lib_name);
								$("#library_id").val(item.lib_id);
								$("#library_id").attr("readonly",true);
								return true;
							}
						});
					}
					
					$("#request_ip").val(result[0].request_ip);
					$("#access_times").val(result[0].access_times);
					$("input[name='isDisable'][value='"+result[0].isDisable+"']").prop("checked",true).parents(".g-radio").addClass("on").siblings(".g-radio").removeClass("on");
					editLayer=layer.open({
						type: 1,
						shade: false,
						title: false, //不显示标题
						scrollbar :false,
						closeBtn :1,
						shade:0.5,
						shadeClose :false,
						area:["600px"],
						offset :["195px"],
						content: $('#add'), //捕获的元素
						cancel: function(index){
							layer.close(editLayer);
							this.content.hide();
						}
					});
				}
			}
		});
		
	});

	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	
	//批量删除操作
	$(".form-btn.remove").on("click",function(){
		GlobalGLoading();
		if(!service_idx) return;
		var param={
			"service_idx":service_idx
		};
		$.ajax({
			url:basePath+"/thirdPartyService/deleteThirdPartyService",
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
					layer.alert(data.message);
					queryThirdPartyService();
				}
			}
		});
	});
	
	//封装的查询操作
	jQuery.select=function(param){
		$("tbody").empty();
		$.ajax({
			url:basePath+"/thirdPartyService/queryThirdPartyServiceByPage",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				if(data&&data.state){
					$.each(data.result.rows,function(i,item){
						$("tbody").append( "<tr>"+
				            "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.service_idx+"' /></div></td>"+
							"<td>"+item.app_key+"</td>"+
							"<td>"+item.service_name+"</td>"+
							"<td id='"+item.service_idx+"_lib'>"+item.library_idx+"</td>"+
							"<td>"+item.request_ip+"</td>"+
							"<td>"+(item.access_times==0?"无限制":item.access_times)+"</td>"+
							"<td>"+(item.isDisable==0?"禁用":"启用")+"</td>"+
							"<td><span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span></td>"+
		                	"</tr>");
						//console.info(JSON.stringify(libraryInfos));
						$.each(libraryInfos,function(i,libInfo){
							if(libInfo.library_idx == item.library_idx){
								$("#"+item.service_idx+"_lib").text(libInfo.lib_name);
							}
					    });
					});
					$.pagination(data.result); 
				}
			}
		});	
	};
	
	/**
		查询按钮操作
	**/
	$(".search").on("click",function(){
		queryThirdPartyService();
	});
	
	function queryThirdPartyService(){
		var library_idx=$("#library").val()
		var size= $('#showSize option:selected').text();
		var service_name=$("#service_name_query").val();
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
	
	//初始化弹出框
	function reset(){
		$("#service_idx,#app_key,#service_name,#service_desc,#request_ip,#library_name,#library_id,#library_idx").val("");
		$("#access_times").val(0);
		$("input[name='isDisable'][value='1']").prop("checked",true).parents(".g-radio").addClass("on").siblings(".g-radio").removeClass("on");
		$("#app_key").closest("li").show();
		$(".form-wrap ul").find("li").each(function () {
			$(this).removeClass("error");
		});		
		$("#library_id").removeAttr("readonly");
	}
	
	/**
	 *	监听图书馆ID变化 改变对应的图书馆名
	 **/
	 $(document).on("change","#library_id",function(){
		var lib_id=$(this).val();
		if(lib_id && libraryInfos){
			$.each(libraryInfos,function(i,item){
				if(item.lib_id==lib_id){
					$("#library_name").val(item.lib_name);
					$("#library_idx").val(item.library_idx);
					return true;
				}
			});
		}else{
			$("#library_name").val("");
			$("#library_idx").val("");
		}
	});
	
	$(document).ready(function() {
		$.ajax({
	   		url:basePath+"/library/querylibInfoByCurUserEXT1",
			type:"POST",
			async: false,
			success:function(data){
				libraryInfos = data.result;
				$("#library").html("<option value='-1' selected>选择图书馆</option>");
				$.each(libraryInfos,function(i,item){
					$("#library").append("<option value='"+item.library_idx+"'>"+item.lib_name+"</option>");
		        });
	   		}
    	});
		
    	var param = {
   			"page" : "1",
   			"pageSize" : $('#showSize option:selected').text(),
   		};
    	$.select(param);
 	});
	
	$(".increase").on("click",function(){
		reset();
		editLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["600px"],
			offset :["195px"],
			content: $('#add'), //捕获的元素
			cancel: function(index){
				layer.close(editLayer);
				this.content.hide();
			}
		});
		$("#app_key").closest("li").hide();
	});
 	
	
	$("#increaseBtn").on("click",function(){
		var service_name=$("#service_name").val();
		var library_id=$("#library_id").val();
		var library_name=$("#library_name").val();
		var access_times=$("#access_times").val();
		if(_.isEmpty(service_name)){
			$("#service_name").closest("li").addClass("error");
			return false;
		}else if(_.isEmpty(library_id)){
			$("#library_id").closest("li").addClass("error");
			return false;
		}else if(_.isEmpty(library_name)){
			$("#library_name").closest("li").addClass("error");
			return false;
		}else if(isNaN(access_times)){
			$("#access_times").closest("li").addClass("error");
			return false;
		}
		var service_idx=$("#service_idx").val();
		var app_key=$("#app_key").val();
		var service_desc=$("#service_desc").val();
		var request_ip=$("#request_ip").val();
		var library_idx=$("#library_idx").val();
		var isDisable=$("input[name='isDisable']:checked").val();
		if(!isDisable)isDisable="1";
		
		var param={
			"service_idx":service_idx,
			"service_name":service_name,
			"app_key":app_key,
			"service_desc":service_desc,
			"library_idx":library_idx,
			"request_ip":request_ip,
			"access_times":access_times,
			"isDisable":isDisable
		};	
		//console.info(JSON.stringify(param));
		$.ajax({
	   		url:basePath+"/thirdPartyService/editThirdPartyService",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				//console.info(data);
				if(data.state){
					if(data.message)layer.alert(data.message);
					if(editLayer)layer.close(editLayer);
					queryThirdPartyService();
				}else{
					if(data.message)layer.alert(data.message);
					else if(data.retMessage)layer.alert(data.retMessage);
				}
	   		}
    	});
		
	});
 	 //上一页操作
	$("#page").on("click",".prev-page",function(){
		
		var library_idx=$("#library").val()
		
		var service_name=$("#service_name_query").val();
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
		
		var service_name=$("#service_name_query").val();
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
		var service_name=$("#service_name_query").val();
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
		var service_name=$("#service_name_query").val();
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
});
</script>
</body>
</html>


