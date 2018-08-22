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
	<div class="page-title-bar">
		<span class="title">书架分组<span class="g-help"></span></span>
		<div class="form-wrap fr">
			<input type="text" name="" id="query" style="width:200px" class="input g-input" placeholder="组ID或组名">
			<div class="btn search" ><spring:message code="devicegroup.jsp.search"></spring:message></div>
			<div class="btn increase g-btn-green"><spring:message code="devicegroup.jsp.add"></spring:message></div>
			<div class="btn delete"><spring:message code="devicegroup.jsp.delete"></spring:message></div>
		</div>
	</div>
	<div class="main">
		<table class="g-table" style="table-layout:fixed">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
					<th class="col2">组ID</th>
					<th class="col3">组名称</th>
					<th class="col4">描述</th>
					<th class="col5" style="width:40px">操作</th>
					
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	
	<div class="paging-bar">
		<div class="left">
			<span class="t fl"><spring:message code="page_bar.jsf.select"></spring:message></span>
			<div class="btn g-chooseAll"><spring:message code="page_bar.jsf.checkall"></spring:message></div>
			<div class="btn g-noChooseAll"><spring:message code="page_bar.jsf.checkallinvert"></spring:message></div>
			<span class="t2 fl"><spring:message code="page_bar.jsf.show"></spring:message></span>
			<div class="g-select refresh">
				<select id="pagesize">
					<option value="" selected>10</option>
					<option value="">30</option>
					<option value="">60</option>
				</select>
				<span class="arr-icon"></span>
			</div>
			<span class="t2 fl " id="ChooseNum"><spring:message code="page_bar.jsf.alreadyselected"></spring:message><span class="total-choosen-num">0</span><spring:message code="page_bar.jsf.selectunit"></spring:message></span>
		</div>
		<div class="right" id="page">

		</div>
		<span class="total-page fr"></span>
	</div>
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
	<div class="form-wrap"  style="float:left">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">书架组ID</span></div>
				<div class="right">
					<input type="text" name="" id="group_id" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">书架组名称</span></div>
				<div class="right">
					<input type="text" name="" id="group_name" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li>
				<div class="left"><spring:message code="devicegroup.jsp.note"/></div>
				<div class="right">
					<textarea id="group_desc" class="g-textarea"></textarea>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">网络模板</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="shelf_config_idx">
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<div class="item" style="text-align: center;padding-bottom:30px;padding-top: 20px">
		<input type="submit" name="save" id="increaseBtn" value="<spring:message code="message.gloable.save"/>" class="g-submit g-btn-blue">
	</div>
</div>
<div class="form-dialog" id="modify">
	<div class="title"><spring:message code="devicegroup.jsp.editgroup"/></div>
	<div class="form-wrap" style="float:left">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">书架组ID</span></div>
				<div class="right">
					<input type="text" name="" id="edit_group_id" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<input type="hidden" name="" id="version_stamp" class="g-input"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">书架组名称</span></div>
				<div class="right">
					<input type="text" name="" id="edit_group_name" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li>
				<div class="left"><spring:message code="devicegroup.jsp.note"/></div>
				<div class="right">
					<textarea id="edit_group_desc" class="g-textarea"></textarea>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">网络模板</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="edit_shelf_config_idx">
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<div class="item" style="text-align: center;padding-bottom:30px;padding-top: 20px">
		<input type="submit" name="save" id="ineditBtn" value="<spring:message code="message.gloable.save"/>" class="g-submit g-btn-blue">
	</div>
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
	var group_idx = "";
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
	var shelf_group_idx = new Array();
	$(".delete").on("click",function(){
		shelf_group_idx.length=0;
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			shelf_group_idx.push($(this).attr("id"));
        }); 
         if(num>0){
        	$(".word").html("");
        	$(".word").append(devicegroup_jsp_curselect+"<span class='font-red'>"+num+"</span>条数据<br>是否要删除选择的书架组");
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
		shelf_group_idx.length = 0;
		shelf_group_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		$('.word').html("");
		$('.word').append("是否要删除选择的书架组");
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
		var Json1 = {}
		var Page1 = {"page":"1",
				"pageSize":"999"};
		var param1 = {json : Json1, page : Page1}
		$.ajax({
			 url:basePath+"/shelfConfig/queryAllShelfConfig",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param1)},
		     success:function(data){
				 if(data.state){
					 $("#shelf_config_idx").empty();
					 /* $("#shelf_config_idx").append(
								"<option value='0' selected>请选择</option>"
						); */
					 $.each(data.result.rows,function(i,item){
							$("#shelf_config_idx").append(
									"<option value='"+item.shelf_config_idx+"'>"+item.shelf_config_id+"</option>"
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
			area :["800px","400px"],
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
		var version_stamp = $(this).parent().parent().find(".version_stamp").text();
		group_idx = group[0];
		$("#edit_group_id").val(group[1]);
		$("#edit_group_name").val(group[2]);
		$("#edit_group_desc").val(group[3]);
		$("#version_stamp").val(version_stamp);
		var Json1 = {}
		var Page1 = {"page":"1",
				"pageSize":"999"};
		var param1 = {json : Json1, page : Page1}
		$.ajax({
			 url:basePath+"/shelfConfig/queryAllShelfConfig",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param1)},
		     success:function(data){
				 if(data.state){
					 $("#edit_shelf_config_idx").empty();
					 /* $("#edit_shelf_config_idx").append(
								"<option value='0' selected>请选择</option>"
						); */
					 $.each(data.result.rows,function(i,item){
					 		$("#edit_shelf_config_idx").append(
									"<option value='"+item.shelf_config_idx+"'>"+item.shelf_config_id+"</option>"
							);	
						});
					 
						param = {
								"shelf_group_idx" : group[0]
						};
						$.ajax({
							 url:basePath+"/shelfGroup/queryShelfGroupById",
						 	 type:"POST",
						 	 data:{"req":JSON.stringify(param)},
						     success:function(data){
								 if(data.state){
									 $("#edit_shelf_config_idx").val(data.result.shelf_config_idx);
					        	}else if(data.retMessage){
					        		showRetMessage(data.retMessage);
					        	}
				       	}
						});
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
			area :["800px","300px"],
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
		editCheck=false;
		checkInc(true);
		if(!editCheck){
			return;
		}
		GlobalGLoading();//loading界面
		var shelf_group_idx= group_idx;
		var shelf_group_id=$("#edit_group_id").val();
		var shelf_group_name=$("#edit_group_name").val();
		var shelf_group_desc=$("#edit_group_desc").val();
		var shelf_config_idx=$("#edit_shelf_config_idx").val();
		var version_stamp = $("#version_stamp").val();
		if(shelf_group_id.length>20 || shelf_group_name.length>100){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		var param={
				json1 : {
					"shelf_group_idx" : shelf_group_idx,
					"lib_id":'',
					"shelf_group_id":shelf_group_id,
					"shelf_group_name":shelf_group_name,
					"shelf_group_desc":shelf_group_desc,
					"version_stamp":version_stamp
				},
				json4 : {
					"shelf_idx" : shelf_group_idx,
					"shelf_config_idx" : shelf_config_idx
				}
		};
		$.ajax({
			url:basePath+"/shelfGroup/updateShelfGroup",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				if(data.state){
					var shelf_group_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size= $('#pagesize option:selected').text();
					var Json ={"lib_id":'',"shelf_group_id":shelf_group_id,};
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
		var idx = shelf_group_idx;
		if(!idx) return;
		var param=new Array();
		for(var i=0;i<idx.length;i++){
			param[i]={
				"shelf_group_idx":idx[i],
			};
		}
		$.ajax({
			url:basePath+"/shelfGroup/deleteShelfGroup",
			type:"POST",
			data:{"req":JSON.stringify(param)
			},
			success:function(data){
				if(data.state){
				 	var oper = <shiro:principal/>;
					var library_idx=null;
					if(oper.operator_type>=3)
					library_idx=oper.library_idx;
					var shelf_group_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").html();
					var size= $('#pagesize option:selected').text();
					var Json ={"lib_id":'',"shelf_group_id":shelf_group_id,};
					var Page={"page":currentpage,"pageSize":size};
					GlobalShowMsg({
						showText:message_gloable_delsuccess,
						status:true
					});
					$.select(Json,Page);
				}else if(data.retMessage){
	        		showRetMessage(data.retMessage);
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
			var group_id=$("#group_id").val();
			var group_name=$("#group_name").val();
			if(group_id==""){
				layer.tips('组ID不能为空', '#group_id', {tips: [2, '#ff6666']});
				return;
			}
			if(group_name == ""){
				layer.tips('组名不能为空', '#group_name', {tips: [2, '#ff6666']});
				return;
			}
		}
		increaseCheck=true;
	};
	var editCheck=false;
	//编辑操作检查
	var checkInc=function(incCheck){
		if(incCheck){
			var shelf_group_id=$("#edit_group_id").val();
			var device_group_name=$("#edit_group_name").val();
			if(!shelf_group_id){
				layer.tips('组ID不能为空', '#edit_group_id', {tips: [2, '#ff6666']});
				return;
			}
			if(!device_group_name){
				layer.tips('组名不能为空', '#edit_group_name', {tips: [2, '#ff6666']});
				return;
			}
		}
		editCheck=true;
	};
	/**
		新增保存按钮
	**/
	$("#increaseBtn").on("click",function(){
		increaseCheck = false;
		checkIncData(true);
		if(!increaseCheck){
			return;
		}
		GlobalGLoading();
		var shelf_group_id=$("#group_id").val();
		var shelf_group_name=$("#group_name").val();
		var shelf_group_desc=$("#group_desc").val();
		var shelf_config_idx=$("#shelf_config_idx").val();
		if(shelf_group_id.length>20 || shelf_group_name.length>100){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		var param={
				json1 : {
					"lib_id":'',
					"shelf_group_id":shelf_group_id,
					"shelf_group_name":shelf_group_name,
					"shelf_group_desc":shelf_group_desc
				},
				json4 : {
					"shelf_config_idx" : shelf_config_idx
				}
		};
		
		$.ajax({
			 url:basePath+"/shelfGroup/addShelfGroup",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
				 if(data.state){
				 	 var oper = <shiro:principal/>;
					var shelf_group_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size= $('#pagesize option:selected').text();
					var Json ={"shelf_group_id":shelf_group_id,"lib_id":''};
					var Page={"page":currentpage,"pageSize":size};
					GlobalShowMsg({
						showText:message_gloable_addsuccess,
						status:true
					}); 
					$.select(Json,Page);
					if(addLayer){
						layer.close(addLayer);
					}
					 clearVal();
	        	}else if(data.retMessage){
	        		showRetMessage(data.retMessage);
	        	}
        	}
		});
	});
	
	function clearVal(){
		$("#group_id").val("");
		$("#group_name").val("");
		$("#group_desc").val("");
	}
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var oper = <shiro:principal/>;
		var library_idx=null;
		library_idx=oper.library_idx;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var shelf_group_id=$("#query").val();
		var size= $('#pagesize option:selected').text();
		var Json = {"shelf_group_id":shelf_group_id,
					"lib_id": ''};
		var Page={	"page":"1",
					"pageSize":size}; 
		$.select(Json,Page);
	});
	//查询ajax封装
	jQuery.select=function (Json,Page){
		library_idx=oper.library_idx;
		//获取当前页数
			var size= $('#pagesize option:selected').text();
		
			var param={	json: Json , page : Page};
			$.ajax({
			url:basePath+"/shelfGroup/queryAllShelfGroup",
			type:"POST",
			data:{"req":JSON.stringify(param),},
			success:function(data){
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					$("tbody").append(
					"<tr>"+
	            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.shelf_group_idx+"' /></div></td>"+
						"<td class='col2'>"+item.shelf_group_id+"</td>"+
						"<td class='col3'>"+item.shelf_group_name+"</td>"+
						"<td class='col4'>"+item.shelf_group_desc+"</td>"+
	                    "<td class='col5' style='width:30px'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
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
		var oper = <shiro:principal/>;
		var library_idx=null;
		library_idx=oper.library_idx;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size= $('#pagesize option:selected').html();
		var shelf_group_id=$("#query").val();
		var Json = {"shelf_group_id":shelf_group_id,
					"lib_id": ''};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var oper = <shiro:principal/>;
		var library_idx=null;
		library_idx=oper.library_idx;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $('#pagesize option:selected').html();
		var shelf_group_id=$("#query").val();
		var Json = {"shelf_group_id":shelf_group_id,
					"lib_id": ''};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var page = $(this).html();
		if(page=="...") return;
		var size= $('#pagesize option:selected').html();
		var oper = <shiro:principal/>;
		var library_idx=null;
		library_idx=oper.library_idx;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var shelf_group_id=$("#query").val();
		var Json = {"shelf_group_id":shelf_group_id,
					"lib_id": ''};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
		
	});
	/**
		每页显示的条目数切换
	**/
	$("select#pagesize").on("change",function(){
		GlobalGLoading();
		var size= $('#pagesize option:selected').html();
		var oper = <shiro:principal/>;
		var library_idx=null;
		library_idx=oper.library_idx;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var shelf_group_id=$("#query").val();
		var Json = {"shelf_group_id":shelf_group_id,
					"lib_id": ''};
		var Page={	"page":"1",
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	  	/**
		 *	对应用户的图书馆数据
		 **/
		(function GetlibIdxAndNameOBJ(){
			var oper = <shiro:principal/>;
			library_idx=oper.library_idx;
			if(oper.operator_type>=3)
			library_idx=oper.library_idx;
			//获取当前页数
			var size= $('#pagesize option:selected').text();
			var Page = {"page":"1",
					"pageSize":size};
			var Json = { "lib_id": '' };
			var param={	json: Json , page : Page};
			$.ajax({
				url:basePath+"/shelfGroup/queryAllShelfGroup",
				type:"POST",
				data:{"req":JSON.stringify(param),},
				success:function(data){
					$("tbody").html("");
					$.each(data.result.rows,function(i,item){
						$("tbody").append(
						"<tr>"+
		            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.shelf_group_idx+"' /></div></td>"+
							"<td class='col2'>"+item.shelf_group_id+"</td>"+
							"<td class='col3'>"+item.shelf_group_name+"</td>"+
							"<td class='col4'>"+item.shelf_group_desc+"</td>"+
		                    "<td class='col5' styler='width:30px'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
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