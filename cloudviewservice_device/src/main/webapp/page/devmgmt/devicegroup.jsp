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
	<style type="text/css">
		.equipment-fenzu .col3 {
    		width: auto;
		}
		.equipment-fenzu .col2 {
    		width: 200px;
		}
		.equipment-fenzu .col5 {
    		width: 200px;
		}
		.equipment-fenzu .col4 {
    		width: 250px;
		}
	</style>
</head>
<body>
<div class="equipment-fenzu">
	<div class="page-title-bar">
		<span class="title"><spring:message code="devicegroup.jsp.devgroup"></spring:message><a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/devmgmt/devicegroup.jsp" target="_blank" class="g-help"></a></span>
		<div class="form-wrap fr">
			<input type="text" name="" id="query" class="input g-input" placeholder="<spring:message code="devicegroup.jsp.searchplaceholder"></spring:message>" />
			<div class="btn search" ><spring:message code="devicegroup.jsp.search"></spring:message></div>
			<div class="btn increase g-btn-green"><spring:message code="devicegroup.jsp.add"></spring:message></div>
			<div class="btn delete"><spring:message code="devicegroup.jsp.delete"></spring:message></div>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
					<th class="col2"><spring:message code="devicegroup.jsp.groupid"></spring:message></th>
					<th class="col3"><spring:message code="devicegroup.jsp.groupname"></spring:message></th>
					<th class="col5"><spring:message code="devicegroup.jsp.libid"></spring:message></th>
					<th class="col4"><spring:message code="devicegroup.jsp.note"></spring:message></th>
					<th class="col6"><spring:message code="devicegroup.jsp.operation"></spring:message></th>
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
		<spring:message code="devicegroup.jsp.curselect"/><span class="font-red"></span><spring:message code="devicegroup.jsp.curselectunit"></spring:message><br>
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
				<div class="left"><span class="g-mustIn"><spring:message code="devicegroup.jsp.groupid"/></span></div>
				<div class="right">
					<input type="text" name="" id="groupId" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<span class="error-msg"><spring:message code="devicegroup.jsp.groupidnotnullmsg"/></span>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn"><spring:message code="devicegroup.jsp.groupname"/></span></div>
				<div class="right">
					<input type="text" name="" id="groupName" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<span class="error-msg"><spring:message code="devicegroup.jsp.groupnamenotnullmsg"/></span>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn"><spring:message code="devicegroup.jsp.libid"/></span></div>
				<div class="right">
					<input type="text" name="" id="libid" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<span class="error-msg"><spring:message code="devicegroup.jsp.libidnotnullmsg"/></span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn"><spring:message code="devicegroup.jsp.libname"/></span></div>
				<div class="right">
					<input type="text" name="" style="color:gray;background-color:#E0E0E0" readonly="readonly" id="libName" class="g-input" placeholder="<spring:message code="devicegroup.jsp.libname"/>"  />
				</div>
			</li>
			<li>
				<div class="left"><spring:message code="devicegroup.jsp.note"/></div>
				<div class="right">
					<textarea id="groupDesc" class="g-textarea"></textarea>
				</div>
			</li>
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input id="increaseBtn"  type="submit" value="<spring:message code="message.gloable.save"/>" class="submit g-btn-blue" />
				</div>
			</li>
		</ul>
	</div>
</div>
<div class="form-dialog" id="modify">
	<div class="title"><spring:message code="devicegroup.jsp.editgroup"/></div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn"><spring:message code="devicegroup.jsp.groupid"/></span></div>
				<div class="right">
					<input type="text" name="" id="edit_groupId" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<input type="hidden" name="" id="version_stamp" class="g-input" />
					<span class="error-msg"><spring:message code="devicegroup.jsp.groupidnotnullmsg"/></span>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn"><spring:message code="devicegroup.jsp.groupname"/></span></div>
				<div class="right">
					<input type="text" name="" id="edit_groupName" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<span class="error-msg"><spring:message code="devicegroup.jsp.groupnamenotnullmsg"/></span>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn"><spring:message code="devicegroup.jsp.libid"/></span></div>
				<div class="right">
					<input type="text" name="" id="edit_libid" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<span class="error-msg"><spring:message code="devicegroup.jsp.libidnotnullmsg"/></span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn"><spring:message code="devicegroup.jsp.libname"/></span></div>
				<div class="right">
					<input type="text" name="" style="color:gray;background-color:#E0E0E0" readonly="readonly" id="libName" class="g-input" placeholder="<spring:message code="devicegroup.jsp.libname"/>"  />
				</div>
			</li>
			<li>
				<div class="left"><spring:message code="devicegroup.jsp.note"/></div>
				<div class="right">
					<textarea id="edit_groupDesc" class="g-textarea"></textarea>
					<span class="error-msg">备注超过限定长度，请修改</span>
				</div>
			</li>
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input id="ineditBtn"  type="submit" value="<spring:message code="message.gloable.save"/>" class="submit g-btn-blue" />
				</div>
			</li>
		</ul>
	</div>
</div>

<script type="text/javascript">
$(function(){
	//i18n message
	var devicegroup_jsp_curselect='<spring:message code="devicegroup.jsp.curselect"/>';
	var devicegroup_jsp_curselectunit='<spring:message code="devicegroup.jsp.curselectunit"/>';
	var devicegroup_jsp_isdelselectdev='<spring:message code="devicegroup.jsp.isdelselectdev"/>';
	var devicegroup_jsp_isdeloneselectdev='<spring:message code="devicegroup.jsp.isdelselectdev"/>';
	var devicegroup_jsp_selectdeldev='<spring:message code="devicegroup.jsp.selectdeldev"/>';
	var devicegroup_jsp_hasfieldoutofLength='<spring:message code="devicegroup.jsp.hasfieldoutofLength"/>';
	var devicegroup_jsp_updatesuccess='<spring:message code="message.gloable.updatesuccess"/>';
	
	var message_gloable_delsuccess='<spring:message code="message.gloable.delsuccess"/>';
	var message_gloable_addsuccess='<spring:message code="message.gloable.addsuccess"/>';
	var message_gloable_edit='<spring:message code="message.gloable.edit"/>';
	var message_gloable_delete='<spring:message code="message.gloable.delete"/>';
	
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
	var devicegroup_idx = new Array();
	$(".delete").on("click",function(){
		devicegroup_idx.length=0;
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			devicegroup_idx.push($(this).attr("id"));
        }); 
         if(num>0){
        	$(".g-delete-dialog .word").html("");
        	$(".g-delete-dialog .word").append(devicegroup_jsp_curselect+"<span class='font-red'>"+num+"</span>"+devicegroup_jsp_curselectunit+"<br>"+devicegroup_jsp_isdelselectdev);
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
		devicegroup_idx.length = 0;
		devicegroup_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		var col2="<span class='font-red'>"+$(this).parent().parent().find(".col2").html()+"</span>";
		$('.g-delete-dialog .word').html("");
		$('.g-delete-dialog .word').append(devicegroup_jsp_isdeloneselectdev+col2);
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
	
	/* //全选复选框操作
	$(":checkbox").on("click",function(){
		$(this).parents(".g-checkbox").toggleClass("on");
		if($(this).is(":checked")){
			$("tbody input[type='checkbox']").each(function() {
    			if (!$(this).is(":checked")) {
      				$(this).click();
    			}
  			});
		}else
		{
			$("tbody input[type='checkbox']").each(function() {
    			if($(this).is(":checked")){
				$(this).click();
			}
  			});
		}	
  	}); */

	var addLayer=null;
	/**
		新增按钮操作
	**/
	$(".increase").on("click",function(){
		$("#add").find("li").removeClass("error");
		if(oper.operator_type>=3){
			$('#add').find("#libid").prop("readonly","readonly").prop("style","color:gray;background-color:#E0E0E0");
			$('#add').find("#libid").val(libIdxAndNameObj[oper.library_idx].lib_id);
			$('#add').find("#libName").val(libIdxAndNameObj[oper.library_idx].lib_name);
		}
		addLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["600px"],
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
		$("#modify").find("li").removeClass("error");
	
		group.length = 0;
		group.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		group.push($(this).parent().parent().find(".col2").text());
		group.push($(this).parent().parent().find(".col3").text());
		group.push($(this).parent().parent().find(".col5").text());
		group.push($(this).parent().parent().find(".col4").text());
		var version_stamp = $(this).parent().parent().find(".version_stamp").text();
		
		$("#edit_groupId").val(group[1]);
		$("#edit_groupName").val(group[2]);
		$("#edit_libid").val(group[3]);
		$("#edit_groupDesc").text(group[4]);
		$("#version_stamp").val(version_stamp);
		if(oper.operator_type>=3){
			$('#modify').find("#edit_libid").prop("readonly","readonly").prop("style","color:gray;background-color:#E0E0E0");
		}
		
		if(libIdAndNameObj&&libIdAndNameObj[group[3]]){
			$("#modify").find("#libName").val(libIdAndNameObj[group[3]].lib_name);
		}
		editLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["600px"],
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
		increaseCheck=true;
		checkInc(increaseCheck);
		var devicegroup_idx =group[0];
		var device_group_id=$("#edit_groupId").val();
		var device_group_name=$("#edit_groupName").val();
		var lib_id = $("#edit_libid").val();
		var device_group_desc=$("#edit_groupDesc").val();
		var version_stamp=$("#version_stamp").val();
		
		var len_id =$("#edit_groupId").val().length;
		var len_name=$("#edit_groupName").val().length;
		var len_libid=$("#edit_libid").val().length;
		var len_desc=$("#edit_groupId").val().length;
		if(len_id>20 || len_name>100 || len_libid>20 ||len_desc>100){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		if(!device_group_id ||!device_group_name||!lib_id) 
			return;	
		if(device_group_id==group[1] && device_group_name ==group[2] && lib_id==group[3] && device_group_desc==group[4]){
			console.log("未修改数据，请修改相关数据！");
			if(editLayer){
				layer.close(editLayer);
			}
			return;
		}
		var param={
			"device_group_idx":devicegroup_idx,
			"device_group_id":device_group_id,
			"device_group_name":device_group_name,
			"lib_id":lib_id,
			"device_group_desc":device_group_desc,
			"library_idx":"",
			"version_stamp":version_stamp
		};
		$.ajax({
			url:basePath+"/devicegroup/UpdDeviceGroup",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				console.log("UpdDeviceGroup",data);
				if(data.state){
					var oper = <shiro:principal/>;
					var library_idx=null;
					if(oper.operator_type>=3)
					library_idx=oper.library_idx;
					var device_group_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").html();
					var size= $('#pagesize option:selected').text();
					var param={
						"library_idx":library_idx,
						"device_group_id":device_group_id,
					};
					var Page={"page":currentpage,"pageSize":size};
					if(data.message=="ONE"){
						GlobalShowMsg({
							showText:message_gloable_delsuccess,
							status:true
						});
					}else{
						showRetMessage(data.retMessage+"。"+data.result);
					}
					$.select(param,Page);
					if(editLayer){
						layer.close(editLayer);
					}
					GlobalShowMsg({
						showText:devicegroup_jsp_updatesuccess,
						status:true
					});
				}else if(data.retMessage){
					var msg =data.retMessage;
					if(msg.indexOf("optimistic")>=0){
						showRetMessage("当前选择的数据不是最新数据,请刷新之后再做编辑操作");
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
		var idx = devicegroup_idx;
		if(!idx) return;
		var param=new Array();
		for(var i=0;i<idx.length;i++){
			param[i]={
				"device_group_idx":idx[i],
			};
		}
		$.ajax({
			url:basePath+"/devicegroup/DeleteDeviceGroup",
			type:"POST",
			data:{"json":JSON.stringify(param)
			},
			success:function(data){
				//console.log(data);
				if(data.state){
				 	var oper = <shiro:principal/>;
					var library_idx=null;
					if(oper.operator_type>=3)
					library_idx=oper.library_idx;
					var device_group_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").html();
					var size= $('#pagesize option:selected').text();
					var param={
						"library_idx":library_idx,
						"device_group_id":device_group_id,
					};
					var Page={"page":currentpage,"pageSize":size};
					if(data.message=="ONE"){
						GlobalShowMsg({
							showText:message_gloable_delsuccess,
							status:true
						});
					}else{
						showRetMessage(data.retMessage+"。"+data.result);
					}
					$.select(param,Page);
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
			var device_group_id=$("#groupId").val();
			var device_group_name=$("#groupName").val();
			var device_group_desc=$("#groupDesc").val();
			var lib_id=$("#libid").val();
			if(!device_group_id || device_group_id.length>19){// 增加长度检查
				$("#groupId").parent().parent().addClass("error");
			}
			if(!device_group_name || device_group_name.length>80){
				$("#groupName").parent().parent().addClass("error");
			}
			if(device_group_desc.length>300){
				$("#groupDesc").parent().parent().addClass("error");
			}
			if(!lib_id){
				$("#libid").parent().parent().addClass("error");
			}
		}
		increaseCheck=false;
	};
	//编辑操作检查
	var checkInc=function(incCheck){
		if(incCheck){
			var device_group_id=$("#edit_groupId").val();
			var device_group_name=$("#edit_groupName").val();
			var lib_id=$("#edit_libid").val();
			if(!device_group_id || device_group_id.length>20){
				$("#edit_groupId").parent().parent().addClass("error");
			}
			if(!device_group_name){
				$("#edit_groupName").parent().parent().addClass("error");
			}
			if(!lib_id){
				$("#edit_libid").parent().parent().addClass("error");
			}
		}
		increaseCheck=false;
	};
	/**
		新增保存按钮
	**/
	$("#increaseBtn").on("click",function(){
		GlobalGLoading();
		var fieldCheck=true;
		increaseCheck=true;
		checkIncData(increaseCheck);
		var lib_id=$("#libid").val();
		var device_group_id=$("#groupId").val();
		var device_group_name=$("#groupName").val();
		var device_group_desc=$("#groupDesc").val();
		var len_id =$("#edit_groupId").text().length;
		var len_name=$("#edit_groupName").text().length;
		var len_libid=$("#edit_libid").text().length;
		var len_desc=$("#edit_groupId").text().length;
		
		if(device_group_desc.length>299){
			$li=$("#groupDesc").parent().parent();
			$li.addClass("error");
			fieldCheck=false;
		}
		if(!device_group_id){
			$li=$("#groupId").parent().parent();
			$("#groupId").siblings().remove();
			$("#groupId").after('<span class="error-msg">请填写正确的组ID</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		if(device_group_id.length>20){
			$li=$("#groupId").parent().parent();
			$("#groupId").siblings().remove();
			$("#groupId").after('<span class="error-msg">组ID超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		if(!device_group_name){
			$li=$("#groupName").parent().parent();
			$("#groupName").siblings().remove();
			$("#groupName").after('<span class="error-msg">请填写正确的组名</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		if(device_group_name.length>100){
			$li=$("#groupName").parent().parent();
			$("#groupName").siblings().remove();
			$("#groupName").after('<span class="error-msg">组名超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		//if(!$("#libName").val()){
			
		//}
			
		if(!fieldCheck){
			return;
		}
		
		if(len_id>20 || len_name>100 || len_libid>20 ||len_desc>100){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		var groupIdLen=device_group_id.length;
		var device_group_name_len=device_group_name.length;
	
		
		if(groupIdLen>20||device_group_name_len > 100){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		var param={
			"lib_id":lib_id,
			"device_group_id":device_group_id,
			"device_group_name":device_group_name,
			"device_group_desc":device_group_desc,
			"library_idx":"",
		};
		
		if(!device_group_id||!device_group_name ||!lib_id) return;
		$.ajax({
			 url:basePath+"/devicegroup/AddDeviceGroup",
		 	 type:"POST",
		 	 data:{"json":JSON.stringify(param)},
		     success:function(data){
				 if(data.state){
				 	var oper = <shiro:principal/>;
					var library_idx=null;
					if(oper.operator_type>=3)
					library_idx=oper.library_idx;
					var device_group_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size= $('#pagesize option:selected').text();
					var param={
						"library_idx":library_idx,
						"device_group_id":device_group_id,
					};
					var Page={"page":currentpage,"pageSize":size};
					GlobalShowMsg({
						showText:message_gloable_addsuccess,
						status:true
					});
					$.select(param,Page);
					if(addLayer){
						layer.close(addLayer);
					}
					$("div#add").find(":text").val("");
					$("div#add").find("textarea").val("");
	        	}else if(data.retMessage){
	        		showRetMessage(data.retMessage);
	        	}
        	}
		});
	});
	
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var oper = <shiro:principal/>;
		var library_idx=null;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var device_group_id=$("#query").val();
		var size= $('#pagesize option:selected').text();
		var param={
			"library_idx":library_idx,
			"device_group_id":device_group_id,
		};
		var Page={	"page":"1",
					"pageSize":size}; 
		$.select(param,Page);
	});
	//查询ajax封装
	jQuery.select=function (param,Page){
		$.ajax({
			url:basePath+"/devicegroup/SelectDeviceGroup",
			type:"POST",
			data:{"json":JSON.stringify(param),
			"page":JSON.stringify(Page)},
			success:function(data){
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					$("tbody").append(
					"<tr>"+
            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.device_group_idx+"' /></div></td>"+
					"<td class='col2'>"+item.device_group_id+"</td>"+
					"<td class='col3'>"+item.device_group_name+"</td>"+
					"<td class='col5'>"+item.lib_id+"</td>"+
					"<td class='col4'>"+item.device_group_desc+"</td>"+
                    "<td class='col6'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
                    "<span class='btn-a delete'>"+message_gloable_delete+"</span></td>" +
                    "<td class='version_stamp hide'>"+item.version_stamp+"</td>"+
                	"</tr>");	
				});
				var t=0;
				<shiro:lacksPermission name="0102020302">
				t++;
				$(".delete").attr("style","display:none;");
    			</shiro:lacksPermission>
    			<shiro:lacksPermission name="0102020303">
				t++;
				$(".edit").attr("style","display:none;");
    			</shiro:lacksPermission>
    			if(t==2){
    			$(".col6").attr("style","display:none;");
    			}
    			$.pagination(data.result);    
			}
		});
	};
	
	//打开页面自动加载
	$(document).ready(function(){
	//判断用户类型
	var oper = <shiro:principal/>;
	var library_idx=null;
	if(oper.operator_type>=3)
		library_idx=oper.library_idx;
	//获取当前页数
		var size= $('#pagesize option:selected').text();
		var Page={	"page":"1",
				"pageSize":size,};
		var param={	"library_idx":library_idx, };
		$.ajax({
		url:basePath+"/devicegroup/SelectDeviceGroup",
		type:"POST",
		data:{"json":JSON.stringify(param),
				"page":JSON.stringify(Page),},
		success:function(data){
			$("tbody").html("");
			$.each(data.result.rows,function(i,item){
				$("tbody").append(
				"<tr>"+
            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.device_group_idx+"' /></div></td>"+
					"<td class='col2'>"+item.device_group_id+"</td>"+
					"<td class='col3'>"+item.device_group_name+"</td>"+
					"<td class='col5'>"+item.lib_id+"</td>"+
					"<td class='col4'>"+item.device_group_desc+"</td>"+
                    "<td class='col6'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
                    "<span class='btn-a delete'>"+message_gloable_delete+"</span></td>" +
                    "<td class='version_stamp hide'>"+item.version_stamp+"</td>"+
                	"</tr>");	
			});
			var t=0;
			<shiro:lacksPermission name="0102020301">
			$(".increase").attr("style","display:none;");
   			</shiro:lacksPermission> 
			<shiro:lacksPermission name="0102020302">
				t++;
			$(".delete").attr("style","display:none;");
    		</shiro:lacksPermission>
    		<shiro:lacksPermission name="0102020303">
				t++;
			$(".edit").attr("style","display:none;");
    		</shiro:lacksPermission>
    		if(t==2){
    			$(".col6").attr("style","display:none;");
    		}
    		$.pagination(data.result);       
			}
		});
	}); 
	 //上一页操作
	$("#page").on("click",".prev-page",function(){
		var oper = <shiro:principal/>;
		var library_idx=null;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var device_group_id=$("#query").val();
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size= $('#pagesize option:selected').html();
		var param={
			"library_idx":library_idx,
			"device_group_id":device_group_id,
		};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(param,Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var oper = <shiro:principal/>;
		var library_idx=null;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var device_group_id=$("#query").val();
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $('#pagesize option:selected').html();
		var param={
			"library_idx":library_idx,
			"device_group_id":device_group_id,
		};
		var Page={	"page":page,
				"pageSize":size}; 
		$.select(param,Page);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var page = $(this).html();
		if(page=="...") return;
		var size= $('#pagesize option:selected').html();
		var oper = <shiro:principal/>;
		var library_idx=null;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var device_group_id=$("#query").val();
		var param={
			"library_idx":library_idx,
			"device_group_id":device_group_id,
		};
		var Page ={"page":page,"pageSize":size};
		$.select(param,Page);
		
	});
	/**
		每页显示的条目数切换
	**/
	$("select#pagesize").on("change",function(){
		GlobalGLoading();
		var size= $('#pagesize option:selected').html();
		var oper = <shiro:principal/>;
		var library_idx=null;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var device_group_id=$("#query").val();
		var param={
			"library_idx":library_idx,
			"device_group_id":device_group_id,
		};
		var Page ={"page":1,"pageSize":size};
		$.select(param,Page);
	});
	  	/**
		 *	对应用户的图书馆数据
		 **/
		var libIdAndNameObj={};
		var libIdxAndNameObj={};
		(function GetlibIdxAndNameOBJ(){
			if(!typeof libIdAndNameObj=="undefined"&&!typeof libIdxAndNameObj =="undefined"){
				return;
			}
		    $.ajax({
				url:"${pageContext.request.contextPath}/ascconfig/querylibInfoByCurUserEXT1",
				type:"GET",
				data:{}
			}).then(function(data){
				console.log("lib data",data);
				if(data){
					if(data.message=="_MASTER_SLAVE_"){//非云平台管理员 主馆
						var masterAndSlave=data.result;
						var masterLibrary=masterAndSlave.masterLibrary;
						var slaveLibrary=masterAndSlave.slaveLibrary;//arr
						libIdAndNameObj[masterLibrary.lib_id]={"lib_name":masterLibrary.lib_name,"library_idx":masterLibrary.library_idx};
						libIdxAndNameObj[masterLibrary.library_idx]={"lib_id":masterLibrary.lib_id,"lib_name":masterLibrary.lib_name};
						//masterlibIdxArr.push(masterLibrary.library_idx);
						for(var i=0;i<slaveLibrary.length;i++){
							libIdAndNameObj[slaveLibrary[i].lib_id]={"lib_name":slaveLibrary[i].lib_name,"library_idx":slaveLibrary[i].library_idx};
							libIdxAndNameObj[slaveLibrary[i].library_idx]={"lib_id":slaveLibrary[i].lib_id,"lib_name":slaveLibrary[i].lib_name};
							//masterlibIdxArr.push(slaveLibrary[i].library_idx);
						}
					}else if(data.message=="_SLAVE_"){//子馆
							libIdAndNameObj[data.result.lib_id]={"lib_name":data.result.lib_name,"library_idx":data.result.library_idx};
							libIdxAndNameObj[data.result.library_idx]={"lib_id":data.result.lib_id,"lib_name":data.result.lib_name};
					}else{
						for(var i=0;i<data.result.length;i++){//云平台用户
							libIdAndNameObj[data.result[i].lib_id]={"lib_name":data.result[i].lib_name,"library_idx":data.result[i].library_idx};
							libIdxAndNameObj[data.result[i].library_idx]={"lib_id":data.result[i].lib_id,"lib_name":data.result[i].lib_name};
							//masterlibIdxArr.push(data.result[i].library_idx);
						}
					}
					console.log("libIdxAndNameObj",libIdxAndNameObj);
				}
			});
		})();
		
		/**
		 *	监听图书馆ID变化 改变对应的图书馆名
		 **/
		$("#libid").on("keyup",function(){
			var lib_id=$(this).val();
			if(lib_id&&libIdAndNameObj[lib_id]){
				$("#libName").val(libIdAndNameObj[lib_id].lib_name);
			}else{
				$("#libName").val("");
			}
		});
		 /**
		 *	监听图书馆ID变化 改变对应的图书馆名
		 **/
		$("#edit_libid").on("keyup",function(){
			var lib_id=$(this).val();
			if(lib_id&&libIdAndNameObj[lib_id]){
				$("#modify").find("#libName").val(libIdAndNameObj[lib_id].lib_name);
			}else{
				$("#modify").find("#libName").val("");
			}
		});
		
});
</script>
</body>
</html>


