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
		<span class="title">网络模板配置<span class="g-help"></span></span>
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
					<th class="col2">模板ID</th>
					<th class="col3">模板名称</th>
					<th class="col4">IP地址</th>
					<th class="col5" style="width:40px">端口号</th>
					<th class="col6" style="width:40px">操作</th>
					
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
		<spring:message code="devicegroup.jsp.curselect"/><span class="font-red"></span>模板<br>
		<spring:message code="devicegroup.jsp.isdelselectdev"/>
	</div>
	<div class="form-btn cancel g-btn-gray"><spring:message code="message.gloable.cancel"></spring:message></div>
	<div class="form-btn remove g-btn-red"><spring:message code="message.gloable.delete"></spring:message></div>
</div>
<div class="form-dialog" id="add">
	<div class="title">新增模板</div>
	<div class="form-wrap"  style="float:left">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">模板ID</span></div>
				<div class="right">
					<input type="text" name="" id="config_id" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">模板名称</span></div>
				<div class="right">
					<input type="text" name="" id="config_name" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">IP地址</span></div>
				<div class="right">
					<input type="text" name="" id="config_ip" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">端口号</span></div>
				<div class="right">
					<input type="text" name="" id="config_port" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
		</ul>
	</div>
	<div class="item" style="text-align: center;padding-bottom:30px;padding-top: 20px">
		<input type="submit" name="save" id="increaseBtn" value="<spring:message code="message.gloable.save"/>" class="g-submit g-btn-blue">
	</div>
</div>
<div class="form-dialog" id="modify">
	<div class="title">编辑模板</div>
	<div class="form-wrap" style="float:left">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">模板ID</span></div>
				<div class="right">
					<input type="text" name="" id="edit_config_id" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<input type="hidden" name="" id="version_stamp" class="g-input"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">模板名称</span></div>
				<div class="right">
					<input type="text" name="" id="edit_config_name" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">IP地址</span></div>
				<div class="right">
					<input type="text" name="" id="edit_config_ip" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">端口号</span></div>
				<div class="right">
					<input type="text" name="" id="edit_config_port" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
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
	var shelf_config_idx = new Array();
	$(".delete").on("click",function(){
		shelf_config_idx.length=0;
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			shelf_config_idx.push($(this).attr("id"));
        }); 
         if(num>0){
        	$(".word").html("");
        	$(".word").append(devicegroup_jsp_curselect+"<span class='font-red'>"+num+"</span>条数据<br>是否要删除选择的模板数据");
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
		shelf_config_idx.length = 0;
		shelf_config_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		$('.word').html("");
		$('.word').append("是否要删除选择的模板");
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
		addLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["800px"],
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
		group_idx = group[0];
		$("#edit_config_id").val(group[1]);
		$("#edit_config_name").val(group[2]);
		$("#edit_config_ip").val(group[3]);
		$("#edit_config_port").val(group[4]);
		$("#version_stamp").val(version_stamp);
		
		editLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["800px"],
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
		var shelf_config_idx= group_idx;
		var shelf_config_id=$("#edit_config_id").val();
		var shelf_config_name=$("#edit_config_name").val();
		var shelf_config_ip=$("#edit_config_ip").val();
		var shelf_config_port=$("#edit_config_port").val();
		var version_stamp = $("#version_stamp").val();
		if(shelf_config_id.length>20 || shelf_config_name.length>100){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		var param={
				"shelf_config_idx" : shelf_config_idx,
				"shelf_config_id":shelf_config_id,
				"shelf_config_name":shelf_config_name,
				"shelf_ip":shelf_config_ip,
				"shelf_port":shelf_config_port,
				"version_stamp":version_stamp
		};
		$.ajax({
			url:basePath+"/shelfConfig/updateShelfConfig",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				if(data.state){
					var shelf_config_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size= $('#pagesize option:selected').text();
					var Json ={"shelf_config_id":shelf_config_id};
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
		var idx = shelf_config_idx;
		if(!idx) return;
		var param=new Array();
		for(var i=0;i<idx.length;i++){
			param[i]={
				"shelf_config_idx":idx[i],
			};
		}
		$.ajax({
			url:basePath+"/shelfConfig/deleteShelfConfig",
			type:"POST",
			data:{"req":JSON.stringify(param)
			},
			success:function(data){
				if(data.state){
					var shelf_config_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").html();
					var size= $('#pagesize option:selected').text();
					var Json ={"shelf_config_id":shelf_config_id};
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
	
	reg_ip=/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
	reg_port=/^[0-9]*[1-9][0-9]*$/;
	//新增操作检查
	var increaseCheck=false;
	var checkIncData=function(incCheck){
		if(incCheck){
			var config_id=$("#config_id").val();
			var config_name=$("#config_name").val();
			var config_ip = $("#config_ip").val();
			var config_port = $("#config_port").val();
			if(config_id==""){
				layer.tips('模板ID不能为空', '#config_id', {tips: [2, '#ff6666']});
				return;
			}
			if(config_name == ""){
				layer.tips('模板名称不能为空', '#config_name', {tips: [2, '#ff6666']});
				return;
			}
			if(config_ip == ""){
				layer.tips('IP地址不能为空', '#config_ip', {tips: [2, '#ff6666']});
				return;
			}
			if(!reg_ip.test(config_ip)){
				layer.tips('IP格式有误', '#config_ip', {tips: [2, '#ff6666']});
				return;
			}
			if(config_port == ""){
				layer.tips('端口号不能为空', '#config_port', {tips: [2, '#ff6666']});
				return;
			}
			if(!reg_port.test(config_port)){
				layer.tips('端口号格式有误', '#config_port', {tips: [2, '#ff6666']});
				return;
			}
		}
		increaseCheck=true;
	};
	var editCheck=false;
	//编辑操作检查
	var checkInc=function(incCheck){
		if(incCheck){
			var shelf_config_id=$("#edit_config_id").val();
			var device_group_name=$("#edit_config_name").val();
			var edit_config_ip=$("#edit_config_ip").val();
			var edit_config_port=$("#edit_config_port").val();
			if(!shelf_config_id){
				layer.tips('组ID不能为空', '#edit_config_id', {tips: [2, '#ff6666']});
				return;
			}
			if(!device_group_name){
				layer.tips('组名不能为空', '#edit_config_name', {tips: [2, '#ff6666']});
				return;
			}
			if(edit_config_ip == ""){
				layer.tips('IP地址不能为空', '#edit_config_ip', {tips: [2, '#ff6666']});
				return;
			}
			if(!reg_ip.test(edit_config_ip)){
				layer.tips('IP格式有误', '#edit_config_ip', {tips: [2, '#ff6666']});
				return;
			}
			if(edit_config_port == ""){
				layer.tips('端口号不能为空', '#edit_config_port', {tips: [2, '#ff6666']});
				return;
			}
			if(!reg_port.test(edit_config_port)){
				layer.tips('端口号格式有误', '#edit_config_port', {tips: [2, '#ff6666']});
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
		var shelf_config_id=$("#config_id").val();
		var shelf_config_name=$("#config_name").val();
		var shelf_config_ip=$("#config_ip").val();
		var shelf_config_port=$("#config_port").val();
		if(shelf_config_id.length>20 || shelf_config_name.length>100){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		var param={
				"shelf_config_id":shelf_config_id,
				"shelf_config_name":shelf_config_name,
				"shelf_ip":shelf_config_ip,
				"shelf_port":shelf_config_port
		};
		
		$.ajax({
			 url:basePath+"/shelfConfig/addShelfConfig",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
				 if(data.state){
					var shelf_config_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size= $('#pagesize option:selected').text();
					var Json ={"shelf_config_id":shelf_config_id};
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
		$("#config_id").val("");
		$("#config_name").val("");
		$("#config_ip").val("");
		$("#config_port").val("");
	}
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var shelf_config_id=$("#query").val();
		var size= $('#pagesize option:selected').text();
		var Json = {"shelf_config_id":shelf_config_id};
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
			url:basePath+"/shelfConfig/queryAllShelfConfig",
			type:"POST",
			data:{"req":JSON.stringify(param),},
			success:function(data){
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					$("tbody").append(
					"<tr>"+
	            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.shelf_config_idx+"' /></div></td>"+
						"<td class='col2'>"+item.shelf_config_id+"</td>"+
						"<td class='col3'>"+item.shelf_config_name+"</td>"+
						"<td class='col4'>"+item.shelf_ip+"</td>"+
						"<td class='col5'>"+item.shelf_port+"</td>"+
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
		var size= $('#pagesize option:selected').html();
		var shelf_config_id=$("#query").val();
		var Json = {"shelf_config_id":shelf_config_id};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $('#pagesize option:selected').html();
		var shelf_config_id=$("#query").val();
		var Json = {"shelf_config_id":shelf_config_id};
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
		var shelf_config_id=$("#query").val();
		var Json = {"shelf_config_id":shelf_config_id};
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
		var shelf_config_id=$("#query").val();
		var Json = {"shelf_config_id":shelf_config_id};
		var Page={	"page":"1",
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	  	/**
		 *	对应用户的图书馆数据
		 **/
		(function GetlibIdxAndNameOBJ(){
			//获取当前页数
			var size= $('#pagesize option:selected').text();
			var shelf_config_id=$("#query").val();
			var Page = {"page":"1",
					"pageSize":size};
			var Json = {"shelf_config_id":shelf_config_id};
			var param={	json: Json , page : Page};
			$.ajax({
				url:basePath+"/shelfConfig/queryAllShelfConfig",
				type:"POST",
				data:{"req":JSON.stringify(param),},
				success:function(data){
					$("tbody").html("");
					$.each(data.result.rows,function(i,item){
						$("tbody").append(
						"<tr>"+
		            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.shelf_config_idx+"' /></div></td>"+
							"<td class='col2'>"+item.shelf_config_id+"</td>"+
							"<td class='col3'>"+item.shelf_config_name+"</td>"+
							"<td class='col4'>"+item.shelf_ip+"</td>"+
							"<td class='col5'>"+item.shelf_port+"</td>"+
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