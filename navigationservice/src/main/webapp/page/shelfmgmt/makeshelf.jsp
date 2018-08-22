<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
	
</head>
<style>
.g-1{
	height: 26px;
    line-height: 26px;
	width: 300px;
}
.g-input-1{
	display: block;
	padding-left: 6px;
	border-radius: 2px;
	width: 50px;
    height: 26px;
    line-height: 26px;
	color:#333;
	background-color:#f6f6f6;
	border:1px solid #DDD;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
	float : left;
	
}
#lab{
	background-color:white;
	border: 0px;
}
.g-table .col8{
	max-width:50px;
}
</style>
<body>
<div class="equipment-fenzu">
	<div class="page-title-bar">
		<span class="title">批量制作书架<span class="g-help"></span></span>
		<div class="form-wrap fr">
			<input type="text" name="" id="query" style="width:200px" class="input g-input" placeholder="区名">
			<div class="btn search" ><spring:message code="devicegroup.jsp.search"></spring:message></div>
			<div class="btn g-btn-green" id="markShelfBtn">批量制作书架</div>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
					<th class="col2">馆名(标识)</th>
					<th class="col3">楼名(标识)</th>
					<th class="col4">区名(标识)</th>
					<th class="col5">排编号</th>
					<th class="col6">列总数(所在位数)</th>
					<th class="col7">面</th>
					<th class="col8">总层数</th>
					<th class="col9">操作</th>
				</tr>
			</thead>
			<tbody>
			
			
			</tbody>
		</table>
	</div>
	<%@include file ="../include/page_bar.jsf" %>	
</div>
<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		<spring:message code="devicegroup.jsp.curselect"/><span class="font-red"></span>书架<br>
		<spring:message code="devicegroup.jsp.isdelselectdev"/>
	</div>
	<div class="form-btn cancel g-btn-gray"><spring:message code="message.gloable.cancel"></spring:message></div>
	<div class="form-btn remove g-btn-red"><spring:message code="message.gloable.delete"></spring:message></div>
</div>

<div class="form-dialog" id="shelfMark">
	<div class="title">批量制作书架层架数据</div>
	<div class="form-wrap">
		<span style="padding-left: 100px;">******** 制作过程可能出现错误异常，中途出现异常请先删除制作产生的数据 ***********</span>
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">馆名称</span></div>
				<div class="right">
					<input type="text" name="" id="markshelf_libName" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">馆对应标识</span></div>
				<div class="right">
					<input type="text" name="" id="markshelf_libFlag" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">楼名称</span></div>
				<div class="right">
					<input type="text" name="" id="markshelf_floorName" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">楼对应标识</span></div>
				<div class="right">
					<input type="text" name="" id="markshelf_floorFlag" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">区名称</span></div>
				<div class="right">
					<input type="text" name="" id="markshelf_zoneName" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">区对应标识</span></div>
				<div class="right">
					<input type="text" name="" id="markshelf_zoneFlag" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">排编码</span></div>
				<div class="right">
					<input type="text" name="" id="markshelf_rowCode" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">面总数</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="markshelf_faceNum">
							<option value="1">1</option>
							<option value="2" selected>2</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">列总数</span></div>
				<div class="right">
					<input type="text" name="" id="markshelf_colNum" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">列所占位数</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="markshelf_colSize">
							<option value="1">1</option>
							<option value="2" selected>2</option>
							<option value="3">3</option>
							<option value="4">4</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">书架总层数</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="markshelf_shelfLayerNum">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6" selected>6</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<div class="item" style="padding-bottom:30px;padding-top:30px;float:right;padding-right:250px;">
		<input type="submit" name="make" id="makeStart" value="开始制作书架层架" class="g-submit g-btn-blue">
	</div>
</div>
<div class="load-dialog">
	<span class="line"></span>
	<div class="load-gif"></div>
	<div class="word">请稍等，正在还原文件···</div>
</div>
<script type="text/javascript">
$(function(){
	//i18n message
	var devicegroup_jsp_curselect='<spring:message code="devicegroup.jsp.curselect"/>';
	var devicegroup_jsp_curselectunit='devicegroup.jsp.curselectunit';
	var devicegroup_jsp_isdelselectdev='<spring:message code="devicegroup.jsp.isdelselectdev"/>';
	var devicegroup_jsp_isdeloneselectdev='<spring:message code="devicegroup.jsp.isdelselectdev"/>';
	var devicegroup_jsp_selectdeldev='<spring:message code="devicegroup.jsp.selectdeldev"/>';
	var devicegroup_jsp_hasfieldoutofLength='<spring:message code="devicegroup.jsp.selectdeldev"/>';
	var devicegroup_jsp_updatesuccess='<spring:message code="message.gloable.updatesuccess"/>';
	
	var message_gloable_delsuccess='<spring:message code="message.gloable.delsuccess"/>';
	var message_gloable_addsuccess='<spring:message code="message.gloable.addsuccess"/>';
	var message_gloable_edit='<spring:message code="message.gloable.edit"/>';
	var message_gloable_delete='<spring:message code="message.gloable.delete"/>';
	var shelf_id = "";
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
    var oper=<shiro:principal/>;
	//删除选中的多个设备分组
	var bookshelf_idx = new Array();
	
	//删除当前行的设备分组
	$("tbody").on("click",".delete",function(){
		bookshelf_idx.length = 0;
		bookshelf_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		$('.word').html("");
		$('.word').append("是否要删除选择的书架");
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
	
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	var dialogIndex = null;
	/**
		删除按钮操作
	**/
	$(".form-btn.remove").on("click",function(){
		var idx = bookshelf_idx;
		if(!idx) return;
		var param=new Array();
		for(var i=0;i<idx.length;i++){
			param[i]={
				"markshelf_idx":idx[i],
			};
		}
		$.ajax({
			url:basePath+"/makeshelf/deleteMakeShelfRecord",
			type:"POST",
			data:{"req":JSON.stringify(param)
			},
			beforeSend: function () {  
		        // 禁用按钮防止重复提交  
		        $(".form-btn.remove").attr({ disabled: "disabled" });  
		        dialogIndex = loadingDialog({
					loadText:"请稍等，后台正在删除书架层架数据···"
				});
			 },
			success:function(data){
				if(data.state){
					layer.close(dialogIndex);
					var markshelf_zoneName=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size=$(".g-select.refresh").find("option:selected").text();
					var Json = {"markshelf_libName":'',"markshelf_zoneName":markshelf_zoneName}
					var Page={"page":currentpage,"pageSize":size};
					GlobalShowMsg({
						showText:message_gloable_delsuccess,
						status:true
					}); 
					$.select(Json,Page);
				}else if(data.retMessage&&data.result){
					showRetMessage(data.retMessage+"。"+data.result);
				}else if(data.retMessage){//单个删除失败
					showRetMessage(data.retMessage);
				}else if(data.result){
					showRetMessage(data.result);
				}
				if (delLayer) {
					layer.close(delLayer);
				}
			},
			complete: function () {  
			    $(".form-btn.remove").removeAttr("disabled");  
			},  
			error: function (data) {  
				showRetMessage("删除中途出错！");
			} 
			
		});
		
	});

	var markshelfLayer=null;
	$("#markShelfBtn").on("click",function(){
		markshelfLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area :["800px","460px"],
			offset :["130px"],
			content: $('#shelfMark'), //捕获的元素
			cancel: function(index){
				layer.close(markshelfLayer);
				this.content.hide();
			}
		});
	});
	
	
	
	$("#makeStart").on("click",function(){
		checkIncData(true);
		if(increaseCheck == false){
			return;
		}
		var markshelf_libName=$("#markshelf_libName").val();
		var markshelf_libFlag=$("#markshelf_libFlag").val();
		var markshelf_floorName=$("#markshelf_floorName").val();
		var markshelf_floorFlag =$("#markshelf_floorFlag").val();
		var markshelf_zoneName=$("#markshelf_zoneName").val();
		var markshelf_zoneFlag=$("#markshelf_zoneFlag").val();
		var markshelf_rowCode=$("#markshelf_rowCode").val();
		var markshelf_colNum=$("#markshelf_colNum").val();
		var markshelf_colSize = $("#markshelf_colSize").val();
		var markshelf_faceNum = $("#markshelf_faceNum").val();
		var markshelf_shelfLayerNum = $("#markshelf_shelfLayerNum").val();
		
		param = {
				"markshelf_libId":"",
				"markshelf_libName":markshelf_libName,
				"markshelf_libFlag":markshelf_libFlag,
				"markshelf_floorName":markshelf_floorName,
				"markshelf_floorFlag":markshelf_floorFlag,
				"markshelf_zoneName":markshelf_zoneName,
				"markshelf_zoneFlag":markshelf_zoneFlag,
				"markshelf_rowCode":markshelf_rowCode,
				"markshelf_colNum" :markshelf_colNum,
				"markshelf_colSize":markshelf_colSize,
				"markshelf_faceNum":markshelf_faceNum,
				"markshelf_shelfLayerNum":markshelf_shelfLayerNum
				}
		$.ajax({
			 url:basePath+"/makeshelf/makeshelf",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
			 beforeSend: function () {  
			        // 禁用按钮防止重复提交  
			        $("#makeStart").attr({ disabled: "disabled" });  
			        dialogIndex = loadingDialog({
						loadText:"请稍等，后台正在制作书架层架数据···"
					});
			 },
		     success:function(data){
				 if(data.state){
					layer.close(dialogIndex);
					var markshelf_zoneName=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size=$(".g-select.refresh").find("option:selected").text();
					var Json = {"markshelf_libName":'',"markshelf_zoneName":markshelf_zoneName}
					var Page={"page":currentpage,"pageSize":size};
					GlobalShowMsg({
						showText:message_gloable_addsuccess,
						status:true
					}); 
					$.select(Json,Page);
					if(markshelfLayer){
						layer.close(markshelfLayer);
					}
					clearValMakeShelfDiv();
	        	}else if(data.retMessage){
	        		showRetMessage(data.retMessage);
	        	}
        	},
			complete: function () {  
			    $("#makeStart").removeAttr("disabled");  
			},  
			error: function (data) {  
				showRetMessage("制作中途出错，请先删除重新制作");
			} 
		});
	});
	
	function loadingDialog(options){
		var defaults = {
			loadText:"正在删除···"
		};
		defaults = $.extend(defaults,options);

		$('.load-dialog .word').text(defaults.loadText);

		var dialogIndex = layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.load-dialog'), //捕获的元素
			success : function(index,layero){
			
			}
		});

		return dialogIndex;
	}
	
	var editLayer=null;
	

	$(".g-increase-dialog .form-wrap  .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	});
	
	$(":text").focus(function(){
		$(this).parent().parent().removeClass("error");
	});
	//新增操作检查
	var increaseCheck=false;
	var checkIncData=function(incCheck){
		var parten=/^\+?[1-9][0-9]*$/;  
		if(incCheck){
			var markshelf_libName=$("#markshelf_libName").val();
			var markshelf_libFlag=$("#markshelf_libFlag").val();
			var markshelf_floorName=$("#markshelf_floorName").val();
			var markshelf_floorFlag =$("#markshelf_floorFlag").val();
			var markshelf_zoneName=$("#markshelf_zoneName").val();
			var markshelf_zoneFlag=$("#markshelf_zoneFlag").val();
			var markshelf_rowCode=$("#markshelf_rowCode").val();
			var markshelf_colNum=$("#markshelf_colNum").val();
			var markshelf_colSize = $("#markshelf_colSize").val();
			var markshelf_faceNum = $("#markshelf_faceNum").val();
			var markshelf_shelfLayerNum = $("#markshelf_shelfLayerNum").val();
			if(markshelf_libName==""){
				layer.tips('数据不能为空', '#markshelf_libName', {tips: [2, '#ff6666']});
				return;
			}
			if(markshelf_libFlag==""){
				layer.tips('数据不能为空', '#markshelf_libFlag', {tips: [2, '#ff6666']});
				return;
			}
			if(markshelf_floorName==""){
				layer.tips('数据不能为空', '#markshelf_floorName', {tips: [2, '#ff6666']});
				return;
			}
			if(markshelf_floorFlag==""){
				layer.tips('数据不能为空', '#markshelf_floorFlag', {tips: [2, '#ff6666']});
				return;
			}
			if(markshelf_zoneName==""){
				layer.tips('数据不能为空', '#markshelf_zoneName', {tips: [2, '#ff6666']});
				return;
			}
			if(markshelf_zoneFlag==""){
				layer.tips('数据不能为空', '#markshelf_zoneFlag', {tips: [2, '#ff6666']});
				return;
			}
			if(markshelf_rowCode==""){
				layer.tips('数据不能为空', '#markshelf_rowCode', {tips: [2, '#ff6666']});
				return;
			}
			if(markshelf_colNum==""){
				layer.tips('数据不能为空', '#markshelf_colNum', {tips: [2, '#ff6666']});
				return;
			}
			
			if(markshelf_colNum.length > parseInt(markshelf_colSize,10) || !parten.test(markshelf_colNum)){
				layer.tips('数据不合理', '#markshelf_colNum', {tips: [2, '#ff6666']});
				return;
			}
			
		}
		increaseCheck=true;
	};
	
	function clearValMakeShelfDiv(){
		$("#markshelf_libName").val("");
		$("#markshelf_libFlag").val("");
		$("#markshelf_floorName").val("");
		$("#markshelf_floorFlag").val("");
		$("#markshelf_zoneName").val("");
		$("#markshelf_zoneFlag").val("");
		$("#markshelf_rowCode").val("");
		$("#markshelf_colNum").val("");
		$("#markshelf_colSize").val(2);
		$("#markshelf_faceNum").val(2);
		$("#markshelf_shelfLayerNum").val(6);
	}
	
	
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var markshelf_zoneName=$("#query").val();
		var size=$(".g-select.refresh").find("option:selected").text();
		var Json = {"markshelf_zoneName":markshelf_zoneName,
				"markshelf_libName":''};
		var Page={	"page":"1",
					"pageSize":size}; 
		$.select(Json,Page);
	});
	//查询ajax封装
	jQuery.select=function (Json,Page){
		//获取当前页数
			var size=$(".g-select.refresh").find("option:selected").text();
		
			var param={	json: Json , page : Page};
			$.ajax({
				url:basePath+"/makeshelf/queryAllMakeShelfRecord",
				type:"POST",
				data:{"req":JSON.stringify(param),},
			success:function(data){
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					$("tbody").append(
							"<tr>"+
			            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.markshelf_idx+"' /></div></td>"+
								"<td class='col2'>"+item.markshelf_libName+"("+item.markshelf_libFlag+")</td>"+
								"<td class='col3'>"+item.markshelf_floorName+"("+item.markshelf_floorFlag+")</td>"+
								"<td class='col4'>"+item.markshelf_zoneName+"("+item.markshelf_zoneFlag+")</td>"+
								"<td class='col5'>"+item.markshelf_rowCode+"</td>"+
								"<td class='col6'>"+item.markshelf_colNum+"("+item.markshelf_colSize+")</td>"+
								"<td class='col7'>"+item.markshelf_faceNum+"</td>"+
								"<td class='col8'>"+item.markshelf_shelfLayerNum+"</td>"+
			                    "<td class='col9'><span class='btn-a delete'>"+message_gloable_delete+"</span></td>" +
			                    "</tr>");	
				}); 
	    		$.pagination(data.result);       
				}
			});
	};
	 //上一页操作
	$("#page").on("click",".prev-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size=$(".g-select.refresh").find("option:selected").text();
		var markshelf_zoneName=$("#query").val();
		var Json = {"markshelf_zoneName":markshelf_zoneName,
				"markshelf_libName":''};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size=$(".g-select.refresh").find("option:selected").text();
		var markshelf_zoneName=$("#query").val();
		var Json = {"markshelf_zoneName":markshelf_zoneName,
				"markshelf_libName":''};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var page = $(this).html();
		if(page=="...") return;
		var size=$(".g-select.refresh").find("option:selected").text();
		var markshelf_zoneName=$("#query").val();
		var Json = {"markshelf_zoneName":markshelf_zoneName,
				"markshelf_libName":''};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
		
	});
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var size=$(".g-select.refresh").find("option:selected").text();
		var markshelf_zoneName=$("#query").val();
		var Json = {"markshelf_zoneName":markshelf_zoneName,
				"markshelf_libName":''};
		var Page={	"page":"1",
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	

	function getPath(obj) {
        if(obj)
    {
        if (window.navigator.userAgent.indexOf("MSIE")>=1)
       {
           obj.select();
           return document.selection.createRange().text;
       }   
        else if(window.navigator.userAgent.indexOf("Firefox")>=1)
        {
           if(obj.files)
            {
                   return obj.files.item(0).getAsDataURL();
             }
             return obj.value;
        }
       return obj.value;
   		}
	}
	
	  	/**
		 *	对应用户的图书馆数据
		 **/
		(function GetlibIdxAndNameOBJ(){
			//获取当前页数
				var size=$(".g-select.refresh").find("option:selected").text();
				var Page = {"page":"1",
						"pageSize":size};
				var Json = {"markshelf_libName":''}
				var param={	json: Json , page : Page};
				$.ajax({
				url:basePath+"/makeshelf/queryAllMakeShelfRecord",
				type:"POST",
				data:{"req":JSON.stringify(param),},
				success:function(data){
					$("tbody").html("");
					$.each(data.result.rows,function(i,item){
						$("tbody").append(
						"<tr>"+
		            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.markshelf_idx+"' /></div></td>"+
							"<td class='col2'>"+item.markshelf_libName+"("+item.markshelf_libFlag+")</td>"+
							"<td class='col3'>"+item.markshelf_floorName+"("+item.markshelf_floorFlag+")</td>"+
							"<td class='col4'>"+item.markshelf_zoneName+"("+item.markshelf_zoneFlag+")</td>"+
							"<td class='col5'>"+item.markshelf_rowCode+"</td>"+
							"<td class='col6'>"+item.markshelf_colNum+"("+item.markshelf_colSize+")</td>"+
							"<td class='col7'>"+item.markshelf_faceNum+"</td>"+
							"<td class='col8'>"+item.markshelf_shelfLayerNum+"</td>"+
		                    "<td class='col9'><span class='btn-a delete'>"+message_gloable_delete+"</span></td>" +
		                    "</tr>");	
					}); 
		    		$.pagination(data.result);       
					}
				});
		})();
		
});
</script>
</body>
</html>