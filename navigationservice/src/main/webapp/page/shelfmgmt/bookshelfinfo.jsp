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
<body>
<div class="equipment-fenzu">
	<form method="post" id="submit" action="../common/exportExcel">
		<input type="hidden" name="req" id="req" />
  		<input type="hidden" name="title" id="title" />
  		<input type="hidden" name="headers" id="headers" />
  		<input type="hidden" name="fileName" id="fileName">
  </form>
	<div class="page-title-bar">
		<span class="title">层架标管理<span class="g-help"></span></span>
		<div class="form-wrap fr">
			<input type="text" name="" id="query" style="width:200px" class="input g-input" placeholder="层架标号或层架标名或书架">
			<div class="btn search" ><spring:message code="devicegroup.jsp.search"></spring:message></div>
			<div class="btn increase g-btn-green"><spring:message code="devicegroup.jsp.add"></spring:message></div>
			<div class="btn delete"><spring:message code="devicegroup.jsp.delete"></spring:message></div>
			<div class="btn backup g-btn-green" id="export">导出</div>
			<div class="btn upload g-btn-blue" id="upload">导入</div>
			<!-- <div class="btn import g-btn-green" id="importMarc">MARC数据导入</div>
			<div class="btn import g-btn-blue" id="importLocation">图书馆藏数据导入</div> -->
			<div class="btn import g-btn-green" id="import">图书数据导入</div>
		</div>
	</div>
	<div class="main">
		<table class="g-table" style="table-layout:fixed">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
					<th class="col2">层架标号</th>
					<th class="col3">层架标名称</th>
					<th class="col4">书架ID</th>
					<th class="col5"  style="width:30px">所在层数</th>
					<th class="col6"  style="width:40px">操作</th>
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
<div class="form-dialog" id="add">
	<div class="title"><spring:message code="devicegroup.jsp.addnewgroup"/></div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">层架标号</span></div>
				<div class="right">
					<input type="text" name="" id="shelflayer_barcode" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">层架标名称</span></div>
				<div class="right">
					<input type="text" name="" id="shelflayer_name" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">所属书架组</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="shelf_id">
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">所在层数</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="shelf_layer">
							<option value="1" selected>1</option>
							<option value="2" >2</option>
							<option value="3" >3</option>
							<option value="4" >4</option>
							<option value="5" >5</option>
							<option value="6" >6</option>
							<!-- <option value="7" >7</option> -->
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<div class="item" style="text-align: center;padding-bottom:30px;padding-top: 20px">
		<input type="submit" name="increaseBtn" id="increaseBtn" value="<spring:message code="message.gloable.save"/>" class="g-submit g-btn-blue">
	</div>
</div>
<div class="form-dialog" id="modify">
	<div class="title"><spring:message code="devicegroup.jsp.editgroup"/></div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">层架标号</span></div>
				<div class="right">
					<input type="text" name="" id="edit_shelflayer_barcode" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<input type="hidden" name="" id="version_stamp" />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">层架标名称</span></div>
				<div class="right">
					<input type="text" name="" id="edit_shelflayer_name" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">所属书架组</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="edit_shelf_id">
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">所在层数</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="edit_shelf_layer">
							<option value="1" selected>1</option>
							<option value="2" >2</option>
							<option value="3" >3</option>
							<option value="4" >4</option>
							<option value="5" >5</option>
							<option value="6" >6</option>
							<!-- <option value="7" >7</option> -->
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<div class="item" style="text-align: center;padding-bottom:30px;padding-top: 20px">
		<input type="submit" name="ineditBtn" id="ineditBtn" value="<spring:message code="message.gloable.save"/>" class="g-submit g-btn-blue">
	</div>
</div>
<div class="upload-dialog dialog1 form-dialog">
	<span class="line"></span>
	<div class="form-wrap">
<%-- <form id="uploadForm" action="${pageContext.request.contextPath}/database/bakUpload" enctype="multipart/form-data" method="post" target="nm_iframe"> 			
 --%>
 	请输入路径：	 		
 	<input type="file" name="file" id="file" accept=".csv,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,text/plain"/>
		<!--  </form> -->
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn upload g-btn-green">上传</div>
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
	var shelflayer_idx = "";
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
	var layer_idx = new Array();
	$(".delete").on("click",function(){
		layer_idx.length=0;
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			layer_idx.push($(this).attr("id"));
        }); 
         if(num>0){
        	$(".word").html("");
        	$(".word").append(devicegroup_jsp_curselect+"<span class='font-red'>"+num+"</span>条数据<br>是否要删除选择的书架");
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
        }else{
       	 	layer.alert(devicegroup_jsp_selectdeldev);
        }
	});
	
	//删除当前行的设备分组
	$("tbody").on("click",".delete",function(){
		layer_idx.length = 0;
		layer_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
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

	var addLayer=null;
	/**
		新增按钮操作
	**/
	$(".increase").on("click",function(){
		clearVal();
		var Json = {"lib_id":''}
		var Page = {"page":"1",
				"pageSize":"99999"};
		var param = {json : Json, page : Page}
		$.ajax({
			 url:basePath+"/bookshelf/queryAllBookshelf",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
				 if(data.state){
					 $("#shelf_id").empty();
					 $.each(data.result.rows,function(i,item){
							$("#shelf_id").append(
									"<option value='"+item.shelf_id+"'>"+item.shelf_name+"</option>"
							);	
						}); 
	        	}else if(data.retMessage){
	        		showRetMessage(data.retMessage);
	        	}
      	}
		});
		addLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area :["800px"],
			offset :["130px"],
			content: $('#add'), //捕获的元素
			cancel: function(index){
				layer.close(delLayer);
				this.content.hide();
			}
		});
	});
	var editLayer=null;
	/**
		编辑按钮操作
	**/
	var group = new Array();
	$("tbody").on("click",".edit",function(){
		group.length = 0;
		group.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		group.push($(this).parent().parent().find(".col2").text());
		group.push($(this).parent().parent().find(".col3").text());
		group.push($(this).parent().parent().find(".col4").text());
		group.push($(this).parent().parent().find(".col5").text());
		var version_stamp = $(this).parent().parent().find(".version_stamp").text();
		 $("#version_stamp").val(version_stamp);
		shelflayer_idx = group[0];
		var Json = {"lib_id":''}
		var Page = {"page":"1",
				"pageSize":"99999"};
		var param = {json : Json, page : Page}
		$.ajax({
			 url:basePath+"/bookshelf/queryAllBookshelf",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
				 if(data.state){
					 $("#edit_shelf_id").empty();
					 $.each(data.result.rows,function(i,item){
							$("#edit_shelf_id").append(
									"<option value='"+item.shelf_id+"'>"+item.shelf_name+"</option>"
							);	
						}); 
					 $("#edit_shelflayer_barcode").val(group[1]);
					 $("#edit_shelflayer_name").val(group[2]);
					 $("#edit_shelf_id").val(group[3]);
					 $("#edit_shelf_layer").val(group[4]);
	        	}else if(data.retMessage){
	        		showRetMessage(data.retMessage);
	        	}
      	}
		});
		editLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area :["800px"],
			offset :["130px"],
			content: $('#modify'), //捕获的元素
			cancel: function(index){
				layer.close(delLayer);
				this.content.hide();
			}
		});
	});
	
	//编辑操作
	$("#ineditBtn").on("click",function(){
		GlobalGLoading();//loading界面
		checkInc(increaseCheck);
		if(!ineditCheck){
			return false;
		}
		var shelf_id=$("#edit_shelf_id").val();
		var shelflayer_barcode = $("#edit_shelflayer_barcode").val();
		var shelflayer_name = $("#edit_shelflayer_name").val();
		var shelf_layer = $("#edit_shelf_layer").val();
		var version_stamp = $("#version_stamp").val();
		if(shelflayer_barcode.length>20 || shelflayer_name.length>100){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		param = {
					"shelflayer_barcode" : shelflayer_barcode,
					"lib_id":'',
					"shelflayer_name":shelflayer_name,
					"shelf_id":shelf_id,
					"shelf_layer" : shelf_layer,
					"version_stamp" :version_stamp
		}
		$.ajax({
			url:basePath+"/bookshelflayer/updateBookshelflayer",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				if(data.state){
					var currentpage = $(".paging-bar li.active").text();
					var shelflayer_barcode=$("#query").val();
					var size=$(".g-select.refresh").find("option:selected").text();
					var Json = {"shelflayer_barcode":shelflayer_barcode,
							"lib_id":''};
					var Page={"page":currentpage,"pageSize":size};
					if(editLayer){
						layer.close(editLayer);
					}
					GlobalShowMsg({
						showText:devicegroup_jsp_updatesuccess,
						status:true
					});
					$.select(Json,Page);
				}else if(data.retMessage){
					var msg = data.retMessage;
					if(msg.indexOf("optimistic")>=0){
						showRetMessage("当前选择的数据不是最新数据,请修改之后再做编辑");
					}else{
						showRetMessage(data.retMessage);
					}
				}
			}
		});	
	});
	
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	
	
	/**
		删除按钮操作
	**/
	$(".form-btn.remove").on("click",function(){
		GlobalGLoading();
		var idx = layer_idx;
		if(!idx) return;
		var param=new Array();
		for(var i=0;i<idx.length;i++){
			param[i]={ 
				"shelflayer_barcode":idx[i],
			};
		}
		$.ajax({
			url:basePath+"/bookshelflayer/deleteBookshelflayer",
			type:"POST",
			data:{"req":JSON.stringify(param)
			},
			success:function(data){
				if(data.state){
					var currentpage = $(".paging-bar li.active").html();
					var shelflayer_barcode=$("#query").val();
					var size=$(".g-select.refresh").find("option:selected").text();
					var Json = {"shelflayer_barcode":shelflayer_barcode,
							"lib_id":''};
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
			}
			
		});
		
	});

	$(".g-increase-dialog .form-wrap  .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	});
	
	$(":text").focus(function(){
		$(this).parent().parent().removeClass("error");
	});
	//新增操作检查
	var increaseCheck=false;
	var checkIncData=function(incCheck){
		if(incCheck){
			var shelflayer_barcode=$("#shelflayer_barcode").val();
			var shelflayer_name=$("#shelflayer_name").val();
			if(shelflayer_barcode==""){
				layer.tips('层架标标号不能为空', '#shelflayer_barcode', {tips: [2, '#ff6666']});
				return;
			}
			if(shelflayer_name == ""){
				layer.tips('层架标名称不能为空', '#shelflayer_name', {tips: [2, '#ff6666']});
				return;
			}
		}
		increaseCheck=true;
	};
	var ineditCheck = false;
	//编辑操作检查
	var checkInc=function(incCheck){
		if(incCheck){
			var shelflayer_barcode=$("#edit_shelflayer_barcode").val();
			var shelflaye_name=$("#edit_shelflaye_name").val();
			if(shelflayer_barcode==""){
				layer.tips('层架标标号不能为空', '#edit_shelflayer_barcode', {tips: [2, '#ff6666']});
				return;
			}
			if(shelflaye_name == ""){
				layer.tips('层架标名称不能为空', '#edit_shelflaye_name', {tips: [2, '#ff6666']});
				return;
			}
		}
		ineditCheck=true;
	};
	/**
		新增保存按钮
	**/
	$("#increaseBtn").on("click",function(){
		GlobalGLoading();
		checkIncData(true);
		if(!increaseCheck){
			return;
		}
		var shelf_id=$("#shelf_id").val();
		var shelflayer_barcode = $("#shelflayer_barcode").val();
		var shelflayer_name = $("#shelflayer_name").val();
		var shelf_layer = $("#shelf_layer").val();
		if(shelflayer_barcode.length>20 || shelflayer_name.length>100){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		param = {
					"lib_id":'',
					"shelf_id":shelf_id,
					"shelflayer_barcode":shelflayer_barcode,
					"shelflayer_name":shelflayer_name,
					"shelf_layer":shelf_layer
		}
		if(shelflayer_barcode==""||shelflayer_name=="") return;
		$.ajax({
			 url:basePath+"/bookshelflayer/addBookshelflayer",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
				 if(data.state){
					var currentpage = $(".paging-bar li.active").text();
					var shelflayer_barcode=$("#query").val();
					var size=$(".g-select.refresh").find("option:selected").text();
					var Json = {"shelflayer_barcode":shelflayer_barcode,
							"lib_id":''};
					var Page={"page":currentpage,"pageSize":size};
					GlobalShowMsg({
						showText:message_gloable_addsuccess,
						status:true
					}); 
					$.select(Json,Page);
					if(addLayer){
						layer.close(addLayer);
					}
	        	}else if(data.message=="CHECK_FALSE"){
	        	 	layer.alert(data.retMessage);
	        	 }
        	}
		});
	});
	
	function clearVal(){
		$("#shelf_id").val("");
		$("#shelf_layer").val(1);
		$("#shelflayer_barcode").val("");
		$("#shelflayer_name").val("");
	}
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var shelflayer_barcode=$("#query").val();
		var size=$(".g-select.refresh").find("option:selected").text();
		var Json = {"shelflayer_barcode":shelflayer_barcode,
				"lib_id":''};
		var Page={	"page":"1",
					"pageSize":size}; 
		$.select(Json,Page);
	});
	//查询ajax封装
	jQuery.select=function (Json,Page){
		//获取当前页数
			var param={	json: Json , page : Page};
			$.ajax({
			url:basePath+"/bookshelflayer/queryAllBookshelflayer",
			type:"POST",
			data:{"req":JSON.stringify(param),},
			success:function(data){
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					$("tbody").append(
					"<tr>"+
	            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.shelflayer_barcode+"' /></div></td>"+
						"<td class='col2'>"+item.shelflayer_barcode+"</td>"+
						"<td class='col3'>"+item.shelflayer_name+"</td>"+
						"<td class='col4'>"+item.shelf_id+"</td>"+
						"<td class='col5'>"+item.shelf_layer+"</td>"+
	                    "<td class='col6'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
	                    "<span class='btn-a delete'>"+message_gloable_delete+"</span></td>" +
	                    "<td class='version_stamp hide'>"+item.version_stamp+"</td>"+
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
		var shelflayer_barcode=$("#query").val();
		var Page={	"page":page,
				"pageSize":size,}; 
		var Json = {"shelflayer_barcode":shelflayer_barcode,
				"lib_id":''};
		$.select(Json,Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size=$(".g-select.refresh").find("option:selected").text();
		var shelflayer_barcode=$("#query").val();
		var Json = {"shelflayer_barcode":shelflayer_barcode,
				"lib_id":''};
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
		var oper = <shiro:principal/>;
		var shelflayer_barcode=$("#query").val();
		var Json = {"shelflayer_barcode":shelflayer_barcode,
				"lib_id":''};
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
		var oper = <shiro:principal/>;
		var shelflayer_barcode=$("#query").val();
		var Json = {"shelflayer_barcode":shelflayer_barcode,
				"lib_id":''};
		var Page={	"page":"1",
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	$("#export").on("click",function(){
		
		var title = "书架数据";
		var headers={};
		headers.shelflayer_barcode = "层架标号";
		headers.shelflayer_name = "层架标名";
		headers.shelf_id = "书架ID";
		headers.shelf_name = "书架名";
		headers.shelf_layer = "所在层数";
		headers.layercount = "书架层数";
		headers.layersort = "排序方式";
		headers.floorimage_url = "书架所在楼层图";
		headers.shelfimage_url= "书架层次示意图";
		headers.shelfpoint = "描点数据";
		headers.issmartshelf  = "书架类型";
		headers.info_value  = "位置信息";
		
		$("#headers").val(JSON.stringify(headers));
		$("#title").val(title);
		$("#fileName").val(title);
		
		var shelflayer_barcode=$("#query").val();
		var param = {"shelflayer_barcode":shelflayer_barcode,
				"lib_id":''};
		$("#req").val(JSON.stringify(param));
		$("#submit").submit();
	});
	
	var bakUpload={};
	bakUpload.isClicked=false;
	bakUpload.uploadDialog;
	var importState = false;
	var fileType = 3;
	$("#upload").on("click",function(){
	bakUpload.uploadDialog=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.upload-dialog'), //捕获的元素
			success : function(index,layero){
				var file = $("#file") ;//清空file
				file.after(file.clone().val(""));      
				file.remove();
				$(".cancel").on("click",function(){
					if(bakUpload.uploadDialog){
						layer.close(bakUpload.uploadDialog);
					}
				});  
			},
			end:function(){
				$(".cancel").unbind("click");
				$(".backup-btn").unbind("click");
			}
		});
	});
	$("#importMarc").on("click",function(){
		importState = true;
		fileType=1;
		bakUpload.uploadDialog=layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				scrollbar :false,
				closeBtn :0,
				shade:0.5,
				shadeClose :false,
				area:["400px"],
				offset :["195px"],
				content: $('.upload-dialog'), //捕获的元素
				success : function(index,layero){
					var file = $("#file") ;//清空file
					file.after(file.clone().val(""));      
					file.remove();
					$(".cancel").on("click",function(){
						if(bakUpload.uploadDialog){
							layer.close(bakUpload.uploadDialog);
						}
					});  
				},
				end:function(){
					$(".cancel").unbind("click");
					$(".backup-btn").unbind("click");
				}
			});
		});
	$("#importLocation").on("click",function(){
		importState = true;
		fileType=2;
		bakUpload.uploadDialog=layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				scrollbar :false,
				closeBtn :0,
				shade:0.5,
				shadeClose :false,
				area:["400px"],
				offset :["195px"],
				content: $('.upload-dialog'), //捕获的元素
				success : function(index,layero){
					var file = $("#file") ;//清空file
					file.after(file.clone().val(""));      
					file.remove();
					$(".cancel").on("click",function(){
						if(bakUpload.uploadDialog){
							layer.close(bakUpload.uploadDialog);
						}
					});  
				},
				end:function(){
					$(".cancel").unbind("click");
					$(".backup-btn").unbind("click");
				}
			});
		});
	$("#import").on("click",function(){
		importState = true;
		fileType=3;
		bakUpload.uploadDialog=layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				scrollbar :false,
				closeBtn :0,
				shade:0.5,
				shadeClose :false,
				area:["400px"],
				offset :["195px"],
				content: $('.upload-dialog'), //捕获的元素
				success : function(index,layero){
					var file = $("#file") ;//清空file
					file.after(file.clone().val(""));      
					file.remove();
					$(".cancel").on("click",function(){
						if(bakUpload.uploadDialog){
							layer.close(bakUpload.uploadDialog);
						}
					});  
				},
				end:function(){
					$(".cancel").unbind("click");
					$(".backup-btn").unbind("click");
				}
			});
		});
	//确认上传
	$("div.upload-dialog").on("click",".form-btn.upload",function(){
		
		   if(bakUpload.isClicked){return;}
		   bakUpload.isClicked=true;

		   var fileName = getPath( document.getElementById("file"));
		   if(!$("#file").val()){
		   		layer.alert("请选择文件后上传");
		   		bakUpload.isClicked=false;
		   		return;
		   }
		   else if(!importState && fileName.substring(fileName.length-3,fileName.length) != "xls"){
			   layer.alert("请选择.csv文件上传");
			   bakUpload.isClicked=false;
		   		return;
		   }
		   var dialogIndex = loadingDialog({
				loadText:"请稍等，上传中..."
		   });
		   if(!importState){
			   $.ajaxFileUpload({
				   	url:basePath+"/common/upload",
				   	secureuri :false,
					 fileElementId :'file',//file控件id
					 type:'post',
					 dataType:'json',
					 success : function (data,status){
						if(data){
					    //去掉<pre></pre>
					    //<pre style="word-wrap: break-word; white-space: pre-wrap;">{"state":false,"message":"","retMessage":"","result":null}</pre>
					    	var reg=new RegExp("<[^>]*>","gi");
					     	var dateTxt=data.replace(reg,"").replace(reg,"");
					     	console.log(dateTxt);
					     	var obj=JSON.parse(dateTxt);
					     	if(obj.state){
							     layer.close(dialogIndex);
							     bakUpload.isClicked=false;
								 layer.close(bakUpload.uploadDialog);	
								var currentpage = $(".paging-bar li.active").text();
								var size=$(".g-select.refresh").find("option:selected").text();
								var Json = {"lib_id":''}
								var Page={"page":currentpage,"pageSize":size};
								$.select(Json,Page);
							    GlobalShowMsg({showText:"上传成功",status:true});
					     	}
					     	else{
								showRetMessage("上传失败");
							}
						     
					  	}
						
					},
					 error: function(data, status, e){
					      console.log(data,status,e);
					       bakUpload.isClicked=false;
					       layer.close(bakUpload.uploadDialog); 	
					 } 
				}); 
		   }
		   else{
			   $.ajaxFileUpload({
				   	url:basePath+"/common/uploadPoint",
				   	secureuri :false,
					 fileElementId :'file',//file控件id
					 type:'post',
					 dataType:'json',
					 success : function (data,status){
						if(data){
							importState = false;
					    	var reg=new RegExp("<[^>]*>","gi");
					     	var dateTxt=data.replace(reg,"").replace(reg,"");
					     	console.log(dateTxt);
					     	var obj=JSON.parse(dateTxt);
					     	if(obj.state){
							     layer.close(dialogIndex);
							     bakUpload.isClicked=false;
								 layer.close(bakUpload.uploadDialog);
								 pointselect = false;
								 var Json = {
									 "lib_id":'',
									 "file":obj.result};
								 var url = basePath+"/bookitem/importBookitem"
								 if(fileType == 1){
									 url = basePath+"/biblios/importMarcData";
								 }else if(fileType == 2){
									 url = basePath+"/bookitem/importBookitemLocation";
								 }else if(fileType == 3){
									 url = basePath+"/bookitem/importBookitem";
								 }
								 $.ajax({
									url:url,
									type:"POST",
									data:{"req":JSON.stringify(Json),},
									success:function(data){
										if(data.state){
										     layer.close(dialogIndex);
										     bakUpload.isClicked=false;
											 layer.close(bakUpload.uploadDialog);	
										    GlobalShowMsg({showText:"导入成功",status:true});
										}
										else{
											showRetMessage("导入失败");
										}
									},
									 error: function(data, status, e){
									      console.log(data,status,e);
									       bakUpload.isClicked=false;
									       layer.close(bakUpload.uploadDialog); 	
									 }
								 });

					     	}
					     	else{
								showRetMessage("导入失败");
							}
						     
					  	}
						
					},
					 error: function(data, status, e){
					      console.log(data,status,e);
					       bakUpload.isClicked=false;
					       layer.close(bakUpload.uploadDialog); 	
					 } 
				}); 
		   }
		   
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
	  	/**
		 *	对应用户的图书馆数据
		 **/
		(function GetlibIdxAndNameOBJ(){
			//获取当前页数
				var size=$(".g-select.refresh").find("option:selected").text();
				var Page = {"page":"1",
						"pageSize":size};
				var Json = {"lib_id":''}
				var param={	json: Json , page : Page};
				$.ajax({
				url:basePath+"/bookshelflayer/queryAllBookshelflayer",
				type:"POST",
				data:{"req":JSON.stringify(param),},
				success:function(data){
					$("tbody").html("");
					$.each(data.result.rows,function(i,item){
						$("tbody").append(
								"<tr>"+
			            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.shelflayer_barcode+"' /></div></td>"+
								"<td class='col2'>"+item.shelflayer_barcode+"</td>"+
								"<td class='col3'>"+item.shelflayer_name+"</td>"+
								"<td class='col4'>"+item.shelf_id+"</td>"+
								"<td class='col5'>"+item.shelf_layer+"</td>"+
			                    "<td class='col6'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
			                    "<span class='btn-a delete'>"+message_gloable_delete+"</span></td>" +
			                    "<td class='version_stamp hide'>"+item.version_stamp+"</td>"+
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