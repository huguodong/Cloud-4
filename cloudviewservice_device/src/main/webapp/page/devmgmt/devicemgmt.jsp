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
<div class="upload-dialog dialog1 form-dialog">
	<span class="line"></span>
	<div class="form-wrap">
 	请输入路径：	 		
 	<input type="file" name="file" id="file" accept=".csv,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,text/plain"/>
		<!--  </form> -->
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn upload g-btn-green">上传</div>
</div>
<div class="equipment-guanli">
	<div class="page-title-bar">
		<!-- <a href="" class="back-btn"></a> -->
		<span class="title"><sp:message code="devicemgmt.jsp.devmgmt"></sp:message><a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/devmgmt/devicemgmt.jsp" target="_blank" class="g-help"></a></span>
		<div class="form-wrap fr">
			<div class="g-select" id="library_div" style="display: none;">
				<select id="library">
					<option value="-1"><sp:message code="devicemgmt.jsp.searchlibrary"></sp:message></option>
				</select>
				<span class="arr-icon"></span>
			</div>
			
			<div class="g-select">
				<select id="type">
					<option value="-1"><sp:message code="devicemgmt.jsp.selectdevtype"></sp:message></option>
				</select>
				<span class="arr-icon"></span>
			</div>
			<div class="g-select">
				<select id="group">
					<option value="-1"><sp:message code="devicemgmt.jsp.selectgroup"></sp:message></option>
				</select>
				<span class="arr-icon"></span>
			</div>
			<input type="text" name="" id="deviceId_tx" class="input g-input" placeholder="<sp:message code="devicemgmt.jsp.searchplaceholder"></sp:message>" />
			<input type="text" name="" id="pid_tx" class="input g-input" placeholder="<sp:message code="devicemgmt.jsp.searchpid"></sp:message>" />
			<div class="btn search" ><sp:message code="message.gloable.search"></sp:message></div>
			<!-- 设备修改根据权限代码控制 modify by huanghuang 20170215 -->
			<%-- <c:if test="${operatorType==1 or operatorType==2}">
				<div class="btn increase g-btn-green" ><sp:message code="批处理"></sp:message></div>
			</c:if> --%>
			<shiro:hasPermission name="0102020105">
				<div class="btn increase g-btn-green" ><sp:message code="批处理"></sp:message></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="0102020105">
				<div class="btn upload g-btn-green" id="devicesRegister"><sp:message code="批量注册设备"></sp:message></div>
			</shiro:hasPermission>
			<div class="btn delete"><sp:message code="message.gloable.delete"></sp:message></div>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
					<th class="col2"><sp:message code="devicemgmt.jsp.devtype"></sp:message></th>
					<th class="col3"><sp:message code="devicemgmt.jsp.devgroup"></sp:message></th>
					<th class="col4"><sp:message code="devicemgmt.jsp.devid"></sp:message></th>
					<th class="col12"><sp:message code="devicemgmt.jsp.pId"></sp:message></th>
					<th class="col5"><sp:message code="devicemgmt.jsp.devname"></sp:message></th>
					<th class="col13" id="library_row" style="display: none;"><sp:message code="devicemgmt.jsp.library"></sp:message></th>
					<th class="col6"><sp:message code="devicemgmt.jsp.usedevmonitormodel"></sp:message></th>
					<th class="col7"><sp:message code="devicemgmt.jsp.usedevconfmodel"></sp:message></th>
					<th class="col8"><sp:message code="devicemgmt.jsp.usedevrunmodel"></sp:message></th>
					<th class="col9"><sp:message code="devicemgmt.jsp.usedatasyncmodel"></sp:message></th>
					<th class="col10"><sp:message code="devicemgmt.jsp.operation"></sp:message></th>
					<th class="col11" style = "display:none"><sp:message code="devicemgmt.jsp.libid"></sp:message></th>
					<th class="col14" style = "display:none">设备特征码</th> 
				</tr>
			</thead>
			<tbody>
			<%-- <tr>
				<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
				<td class="col2">24小时街区图书馆</td>
				<td class="col3">朝阳区图书馆</td>
				<td class="col4">0001</td>
				<td class="col5">机场社区</td>
				<td class="col6"></td>
				<td class="col8"></td>
				<td class="col9"></td>
				<td class="col10"></td>
				<td class="col7">
					<span class="btn-a edit">编辑</span>
					<span class="btn-a delete">删除</span>
				</td>
			</tr> --%>
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
	var message_gloable_yes='<sp:message code="message.gloable.yes"/>';
	var message_gloable_no='<sp:message code="message.gloable.no"/>';
	var message_gloable_edit='<sp:message code="message.gloable.edit"/>';
	var message_gloable_delete='<sp:message code="message.gloable.delete"/>';
	var message_gloable_delsuccess='<sp:message code="message.gloable.delsuccess"/>';
	var message_gloable_config='<sp:message code="message.gloable.config"/>';  
	
	var devicemgmt_jsp_selectdevtype='<sp:message code="devicemgmt.jsp.selectdevtype"/>';
	var devicemgmt_jsp_selectgroup='<sp:message code="devicemgmt.jsp.selectgroup"/>';
	var devicemgmt_jsp_curselect='<sp:message code="devicemgmt.jsp.curselect"/>';
	var devicemgmt_jsp_selectdellink='<sp:message code="devicemgmt.jsp.selectdellink"/>';
	var devicemgmt_jsp_selectdeldev='<sp:message code="devicemgmt.jsp.selectdeldev"/>';
	var devicemgmt_jsp_selectunit='<sp:message code="devicemgmt.jsp.selectunit"/>';
	var devicemgmt_jsp_searchlibrary='<sp:message code="devicemgmt.jsp.searchlibrary"/>';
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
	var bakUpload={};
	//bakUpload.isClicked=false;
	//bakUpload.ShowDialog;
	bakUpload.uploadDialog;
	$("#devicesRegister").on("click",function(){
		//import_tpl_idx = $(this).parent().parent().find("td[class='col1']").find("input").attr("id");
		//import_tpl_type = $(this).parent().parent().find("td[class='col1']").find("input").val();
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
		$.ajaxFileUpload({
		   	 url:"${pageContext.request.contextPath}/device/devicesRegister",
		   	 secureuri :false,
			 fileElementId :'file',//file控件id
			 data:{
				 "import_tpl_idx":"",//import_tpl_idx,
				 "import_tpl_type":""//import_tpl_type	 
			 },
			 type:'post',
			 dataType:'json',
			 success : function(data,status){
				 console.info("data",data);
				 var reg=new RegExp("<[^>]*>","gi");
			     var dateTxt=data.replace(reg,"").replace(reg,"");
			     var obj=JSON.parse(dateTxt);
			     console.log("flag:",obj.state);
			     layer.close(bakUpload.uploadDialog);
			     if(obj.state){
			    	 loadingDialog({
							loadText:"注册成功！"
					 })
			     }else{
			    	 
			     }
			 }
		});
	})
	var delLayer = null;
	/**
	 * 删除操作按钮
	 **/
	var device_idx= new Array();
	var device_id=new Array();
	$(".delete").on("click",function(){
		device_idx.length = 0;
		device_id.length=0;
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			device_idx.push($(this).attr("id"));
        }); 
        $("tbody input[name='idx']:checked").each(function() {  
			device_id.push($(this).parents("tr").find(".device_id").html());
        }); 
		if(num>0){
			$(".g-delete-dialog .word").html("");
        	$(".g-delete-dialog .word").append(devicemgmt_jsp_curselect+"<span class='font-red'>"+num+devicemgmt_jsp_selectunit+"<br>"+devicemgmt_jsp_selectdellink);
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
			layer.alert(devicemgmt_jsp_selectdeldev);
		}		
	});
	//删除当前行的设备分组
	$("tbody").on("click",".delete",function(){
		device_id.length = 0;
		device_idx.length = 0;
		device_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		device_id.push($(this).parents("tr").find(".device_id").html());
		$('.g-delete-dialog .word').html("");
		$('.g-delete-dialog .word').append(devicemgmt_jsp_selectdellink);
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
	//配置
	$("tbody").on("click",".config",function(){
		var idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
		var device_typex = $(this).parent().parent().find("td.col2").text();
		var device_idx = $(this).parent().parent().find("td.col4").text();
		var device_name = $(this).parent().parent().find("td.col5").text();
		var monitor = $(this).parent().parent().find("td.col6").text();
		var ext = $(this).parent().parent().find("td.col7").text();
		var run = $(this).parent().parent().find("td.col8").text();
		var dbsync = $(this).parent().parent().find("td.col9").text();
		var monitoridx = monitor.substring(monitor.indexOf("(")+1,monitor.indexOf(")"));
		var extidx=ext.substring(ext.indexOf("(")+1,ext.indexOf(")"));
		var runidx=run.substring(run.indexOf("(")+1,run.indexOf(")"));
		var dbsyncidx=dbsync.substring(dbsync.indexOf("(")+1,dbsync.indexOf(")"));
		var version_stamp = $(this).parent().parent().find("td.version_stamp").text();
		//获取当前页码
		var currentpage = $(".paging-bar li.active").text();
		//查询条件也需要
		var oper = <shiro:principal/>;
		var library_idx="";
		if(oper.operator_type>=3) library_idx=oper.library_idx;
		var size= $('#showSize option:selected').text();
		var device_type = $("#type").val();
		if($("#type").val()==-1) device_type="";
		var device_group_idx=$("#group").val();
		if($("#group").val()==-1) device_group_idx=null;
		var device_id=$(":text").val();
		
		var param = {
			"idx":idx,
			"device_idx":device_idx,
			"device_name":device_name,
			"device_typex":device_typex,
			"device_type":device_type,
			"monitoridx":monitoridx,
			"extidx":extidx,
			"runidx":runidx,
			"dbsyncidx":dbsyncidx,
			//一下返回按钮用到
			"currentpage":currentpage,
			"library_idx":library_idx,
			"device_type":device_type,
			"device_group_idx":device_group_idx,
			"device_id":device_id,
			"pageSize":size,
			"version_stamp":version_stamp
		};
		
		
		$("#mainframe").load(basePath+"/ConfigDeviceInfo",{"id":JSON.stringify(param)},function(){
			
		});
	});
	//编辑操作
	$("tbody").on("click",".edit",function(){
		var idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
		var monitor = $(this).parent().parent().find("td.col6").text();
		var ext = $(this).parent().parent().find("td.col7").text();
		var run = $(this).parent().parent().find("td.col8").text();
		var dbsync = $(this).parent().parent().find("td.col9").text();
		var device_code = $(this).parent().parent().find("td.col14").text();
		var monitoridx = monitor.substring(monitor.indexOf("(")+1,monitor.indexOf(")"));
		var extidx=ext.substring(ext.indexOf("(")+1,ext.indexOf(")"));
		var runidx=run.substring(run.indexOf("(")+1,run.indexOf(")"));
		var dbsyncidx=dbsync.substring(dbsync.indexOf("(")+1,dbsync.indexOf(")"));
		var version_stamp = $(this).parent().parent().find("td.version_stamp").text();
		//获取当前页码
		var currentpage = $(".paging-bar li.active").text();
		//查询条件也需要
		var oper = <shiro:principal/>;
		var library_idx="";
		if(oper.operator_type>=3) library_idx=oper.library_idx;
		var size= $('#showSize option:selected').text();
		var device_type = $("#type").val();
		if($("#type").val()==-1) device_type="";
		var device_group_idx=$("#group").val();
		if($("#group").val()==-1) device_group_idx=null;
		var device_id=$("#deviceId_tx").val();
		var pid = $("#pid_tx").val();
		if(oper.operator_type == oper.cloud_admin){
			if(library_idx == null || library_idx == ''){
				library_idx = $("#library").val();
				if(library_idx == -1) library_idx = "";
			}
		}
		var param = {
			"idx":idx,
			"monitoridx":monitoridx,
			"extidx":extidx,
			"runidx":runidx,
			"dbsyncidx":dbsyncidx,
			//以下返回按钮用到
			"currentpage":currentpage,
			"library_idx":library_idx,
			"device_type":device_type,
			"device_group_idx":device_group_idx,
			"device_id":device_id,
			"pageSize":size,
			"version_stamp":version_stamp,
			"pid":pid,
			"device_code":device_code
		};
		
		
		<%-- 加载设备编辑页面 deviceedit.jsp--%>
		$("#mainframe").load(basePath+"/EditDeviceInfo",{"id":JSON.stringify(param)},function(){
			$(".form-dialog-fix .form-dialog").each(function(){
				$(this).attr("date-right",$(this).css("right"));
			});
		});
		
		/* $.ajax({
			url:basePath+"/EditDeviceInfo",
			type:"POST",
			data:{"json":JSON.stringify(param)},
		}); */

		//window.location.href="deviceedit.jsp?id="+JSON.stringify(param);
		
	});

	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	//删除操作
	$(".form-btn.remove").on("click",function(){
		GlobalGLoading();
		var idx = device_idx;
		var id=device_id;
		if(!idx) return;
		var param = new Array();
		for(var i=0;i<idx.length;i++){
			 param[i]={
				"device_idx":idx[i],
				"device_id":id[i]
			};
		}
		$.ajax({
			url:basePath+"/device/DeleteDevice",
			type:"POST",
			data:{
				"json":JSON.stringify(param)
			},
			success:function(data){
				//console.log(".form-btn.remove ",data);
				if(data.state){
					var oper = <shiro:principal/>;
					var library_idx=null;
					if(oper.operator_type>=3)
						library_idx=oper.library_idx;
					var currentpage = $(".paging-bar li.active").html();
					var size= $('#showSize option:selected').html();
					var device_type = $("#type").val();
					if($("#type").val()==-1) 
						device_type=null;
					
					var device_group_name=$("#group").val();
					if($("#group").val()==-1)
						device_group_name=null;
					
					var device_id=$("#deviceId_tx").val();
					if(library_idx == null || library_idx == ''||library_idx == -1){
						library_idx = $("#library").val();
					}
					if(library_idx == -1){
						library_idx = "";
					}
					var param={
							"library_idx":library_idx,
							"device_type":device_type,
							"device_group_name":device_group_name,
							"device_id":device_id,
					};
					if(!size){
						size=10;
					}
					var Page = {
							"page":currentpage,
							"pageSize":size,
					};
					if(data.message=="ONE"){//批量删除 全部成功  和单个删除成功的
						GlobalShowMsg({
							showText:message_gloable_delsuccess,
							status:true
						});
					}else if(data.retMessage){
						var failIdxs="";
						if(data.result){
							var arr=data.result.deleteFailDeviceIds;
							for(var i=0;i<arr.length;i++){
								failIdxs+=arr[i]+",";
							}
						}
						if(failIdxs!=""){
							showRetMessage("删失败的IDX："+failIdxs+" "+data.retMessage);
						}else{
							showRetMessage(data.retMessage);
						}
					}
					console.log("删除成功 刷新页面");
					$.select(param,Page);
				}else if(data.retMessage){
					showRetMessage(data.retMessage);
				}
				if(delLayer){
					layer.close(delLayer);
				}
				//thead checkbox 去掉打钩
				$("thead").find("input[name=checkbox]").prop("checked");
				$("thead").find(".g-checkbox").removeClass("on");
			}
		});
		
	});
	//封装的查询操作
	jQuery.select=function(param,Page){
		$("tbody").html("");
		var oper = <shiro:principal/>;
		$.ajax({
			url:basePath+"/device/SelectDevice",
			type:"POST",
			data:{"json":JSON.stringify(param),
			"page":JSON.stringify(Page)},
			success:function(data){
				$.each(data.result.rows,function(i,item){
					$("tbody").append( "<tr>"+
			            "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.device_idx+"' /></div></td>"+
						"<td class='col2'>"+item.device_type_desc+"</td>"+
						"<td class='col3'>"+(item.device_group_name?item.device_group_name:"")+"</td>"+
						"<td class='col4 device_id'>"+item.device_id+"</td>"+
						"<td  class='col11'>"+isNull(item.pid)+"</td>"+
						"<td class='col5'>"+item.device_name+"</td>"+
						"<td class='col12' id='"+item.device_idx+"_lib' style='display:none'></td>"+
						"<td class='col6'>"+(item.device_monitor_tpl_flg==0?message_gloable_no:message_gloable_yes+'('+item.device_monitor_tpl_idx+')')+"</td>"+
						"<td class='col7'>"+(item.device_ext_tpl_flg==0?message_gloable_no:message_gloable_yes+'('+item.device_ext_tpl_idx+')')+"</td>"+
						"<td class='col8'>"+(item.device_run_tpl_flg==0?message_gloable_no:message_gloable_yes+'('+item.device_run_tpl_idx+')')+"</td>"+
						"<td class='col9'>"+(item.device_dbsync_tpl_flg==0?message_gloable_no:message_gloable_yes+'('+item.device_dbsync_tpl_idx+')')+"</td>"+					
	                    "<td class='col10'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
	                    "<span class='btn-a config'>配置</span>"+
	                    "<span class='btn-a delete'>"+message_gloable_delete+"</span></td>" +
	                    "<td class='version_stamp hide'>"+item.version_stamp+"</td>"+
	                    "<td class='col14 hide'>"+item.device_code+"</td>"+
	                	"</tr>");
					if(oper.operator_type == oper.cloud_admin){
					 $.each(libraryInfos,function(i,libInfo){
						if(libInfo.library_idx == item.library_idx){
							$("#"+item.device_idx+"_lib").html(libInfo.lib_name);
						}
				      }); 
					 $("#"+item.device_idx+"_lib").show();
					}
				});
				//一条都没有的情况下，尝试跳转带上一页
				if(data.result.rows.length==0&&Page.page!=1){
					Page.page-=1;
					$.select(param,Page);
				}
				var t=0;
				//修改权限 by huanghuang 20170215
				<shiro:lacksPermission name="0102020102">
				t++;
				$(".delete").attr("style","display:none;");
				</shiro:lacksPermission>
				<shiro:lacksPermission name="0102020103">
				t++;
				$(".edit").attr("style","display:none;");
				</shiro:lacksPermission>
				if(t==2){
				$(".col10").attr("style","display:none;");
				}
				$.pagination(data.result);  
			}
		});	
	};
	
	var libraryInfos={};
	jQuery.findLibraryInfos=function(){
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
		var oper = <shiro:principal/>;
		
		var library_idx="";
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		
		var size= $('#showSize option:selected').text();
		var device_type = $("#type").val();
		if($("#type").val()==-1) 
			device_type="";
		
		var device_group_idx=$("#group").val();
		if($("#group").val()==-1)
			device_group_idx=null;
		
		var device_id=$("#deviceId_tx").val();
		var pid = $("#pid_tx").val();
		if(library_idx == null || library_idx == ''||library_idx == -1){
			library_idx = $("#library").val();
		}
		if(library_idx == -1){
			library_idx = "";
		}
			
		var param={
				"library_idx":library_idx,
				"device_type":device_type,
				"device_group_idx":device_group_idx,
				"device_id":device_id,
				"pid":pid
		};
		var Page = {
				"page":"1",
				"pageSize":size,
		};
		$.select(param,Page);
	});
	
	
	
	
	
	$(document).ready(function(){
   		if(returnInfo){
   			if(returnInfo.device_id) $("#deviceId_tx").val(returnInfo.device_id);
   			if(returnInfo.pid) $("#pid_tx").val(returnInfo.pid);
   		}
		//获取当前用户name
		var oper = <shiro:principal/>;
		$(".status").html("");
		$(".status").append("<span class='icon'></span>"+oper.operator_name);
		//判断权限
		var library_idx="";
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var size= $('#showSize option:selected').text();//选中的文本
		var Page={"page":"1","pageSize":size};
		var param ={"library_idx":library_idx};
		<%--如果是在编辑详情页面返回的，则会带有一下信息--%>
   		if(returnInfo){
			var currentpage=returnInfo.currentpage;//currentpage
			var library_idx=returnInfo.library_idx;
			var device_type=returnInfo.device_type;
			var device_group_idx=returnInfo.device_group_idx;
			var device_id=returnInfo.device_id;
			var pageSize=returnInfo.pageSize;
			var pid = returnInfo.pid;
			param={
					"library_idx":library_idx,
					"device_type":device_type,
					"device_group_idx":device_group_idx,
					"device_id":device_id,
					"pid":pid
			};
			Page = {
					"page":currentpage,
					"pageSize":pageSize,
			};
			//$.select(param,Page);
		}
   		//只能在管理员权限下显示
   		if(oper.operator_type == oper.cloud_admin){
   			$("#library_row").show();
   			$("#library_div").show();
			$.findLibraryInfos();
   		}
   		
		$.ajax({
			url:basePath+"/device/SelectDevice",
			type:"POST",
			data:{"json":JSON.stringify(param),
			"page":JSON.stringify(Page)},
			success:function(data){
			$("tbody").html("");
			$.each(data.result.rows,function(i,item){
				$("tbody").append("<tr>"+
	            "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.device_idx+"' /></div></td>"+
				"<td class='col2'>"+item.device_type_desc+"</td>"+
				"<td class='col3'>"+(item.device_group_name?item.device_group_name:"")+"</td>"+
				"<td class='col4 device_id'>"+item.device_id+"</td>"+
				"<td  class='col11'>"+isNull(item.pid)+"</td>"+
				"<td class='col5'>"+item.device_name+"</td>"+
				"<td class='col12' id='"+item.device_idx+"_lib' style='display: none;'></td>"+
				"<td class='col6'>"+(item.device_monitor_tpl_flg==0?message_gloable_no:message_gloable_yes+'('+item.device_monitor_tpl_idx+')')+"</td>"+
				"<td class='col7'>"+(item.device_ext_tpl_flg==0?message_gloable_no:message_gloable_yes+'('+item.device_ext_tpl_idx+')')+"</td>"+
				"<td class='col8'>"+(item.device_run_tpl_flg==0?message_gloable_no:message_gloable_yes+'('+item.device_run_tpl_idx+')')+"</td>"+
				"<td class='col9'>"+(item.device_dbsync_tpl_flg==0?message_gloable_no:message_gloable_yes+'('+item.device_dbsync_tpl_idx+')')+"</td>"+					
	            "<td class='col10'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
		        "<span class='btn-a config'>"+message_gloable_config+"</span>"+
	            "<span class='btn-a delete'>"+message_gloable_delete+"</span></td>" +
	            "<td class='version_stamp hide'>"+item.version_stamp+"</td>"+
	            "<td class='col14 hide'>"+item.device_code+"</td>"+
	             "</tr>");
				if(oper.operator_type == oper.cloud_admin){
				 $.each(libraryInfos,function(i,libInfo){
					if(libInfo.library_idx == item.library_idx){
						$("#"+item.device_idx+"_lib").html(libInfo.lib_name);
					}
				 });
				 $("#"+item.device_idx+"_lib").show();
				}
			});
			var t=0;
			<shiro:lacksPermission name="0102020102">
			t++;
			$(".delete").attr("style","display:none;");
			</shiro:lacksPermission>
			<shiro:lacksPermission name="0102020103">
			t++;
			$(".edit").attr("style","display:none;");
			</shiro:lacksPermission>
			if(t==2){
			$(".col10").attr("style","display:none;");
			}
			$.pagination(data.result);  
			}
		});
	});
	
	function isNull(param){
		if(param == null){
			return "";
		}else{
			return param;
		}
	}
	
	$(document).ready(function() {
		$.ajax({
	   		url:basePath+"/metadevicetype/SelectType",
			type:"POST",
			success:function(data){
			$("#type").html("<option value='-1' selected>"+devicemgmt_jsp_selectdevtype+"</option>");
				$.each(data.result,function(i,item){
					$("#type").append(
					"<option value='"+item.device_type+"'>"+item.device_type_desc+"</option>" 
					);
	            });
				if(returnInfo&&returnInfo.device_type){
					$("#type").val(returnInfo.device_type);
				}
	   		}
    	});
   		var param = {};
 		if(o.operator_type>=3)
 			param.library_idx = o.library_idx;
   		$.ajax({
	   		url:basePath+"/devicegroup/SelectGroup",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
			$("#group").html("<option value='-1' selected>"+devicemgmt_jsp_selectgroup+"</option>");
				$.each(data.result,function(i,item){
					$("#group").append(
					"<option value='"+item.device_group_idx+"'>"+item.device_group_name+"</option>" 
					);
	            });
				if(returnInfo&&returnInfo.device_group_idx){
					$("#group").val(returnInfo.device_group_idx);
				}
				
	   		}
    	});
    	
    	$.ajax({
	   		url:basePath+"/library/querylibInfoByCurUserEXT1",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
			$("#library").html("<option value='-1' selected>"+devicemgmt_jsp_searchlibrary+"</option>");
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
		var oper = <shiro:principal/>;
		var library_idx="";
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size= $('#showSize option:selected').html();
		var device_type = $("#type").val();
		if($("#type").val()==-1) 
			device_type="";
			
		var device_group_name=$("#group").val();
		if($("#group").val()==-1)
			device_group_name="";
					
		var device_id=$("#deviceId_tx").val();
		if(library_idx == null || library_idx == ''||library_idx == -1){
			library_idx = $("#library").val();
		}
		if(library_idx == -1){
			library_idx = "";
		}
		var param={
			"library_idx":library_idx,
			"device_type":device_type,
			"device_group_name":device_group_name,
			"device_id":device_id,
			};
		var Page = {
			"page":page,
			"pageSize":size,
			};
			
		$.select(param,Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var oper = <shiro:principal/>;
		var library_idx="";
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $('#showSize option:selected').html();
		var device_type = $("#type").val();
		if($("#type").val()==-1) 
			device_type="";
			
		var device_group_name=$("#group").val();
		if($("#group").val()==-1)
			device_group_name="";
					
		var device_id=$("#deviceId_tx").val();
		
		if(library_idx == null || library_idx == ''||library_idx == -1){
			library_idx = $("#library").val();
		}
		if(library_idx == -1){
			library_idx = "";
		}
		
		var param={
			"library_idx":library_idx,
			"device_type":device_type,
			"device_group_name":device_group_name,
			"device_id":device_id,
			};
		var Page = {
			"page":page,
			"pageSize":size,
			};
			
		$.select(param,Page);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var page = $(this).html();
		if(page=="...") return;
		
		var oper = <shiro:principal/>;
		var library_idx=null;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var size= $('#showSize option:selected').text();
		var device_type = $("#type").val();
		if($("#type").val()==-1) 
			device_type=null;
			
		var device_group_name=$("#group").val();
		if($("#group").val()==-1)
			device_group_name=null;
					
		var device_id=$("#deviceId_tx").val();
		if(library_idx == null || library_idx == ''||library_idx == -1){
			library_idx = $("#library").val();
		}
		if(library_idx == -1){
			library_idx = "";
		}
		var param={
			"library_idx":library_idx,
			"device_type":device_type,
			"device_group_name":device_group_name,
			"device_id":device_id,
			};
		var Page = {
			"page":page,
			"pageSize":size,
			};
			
		$.select(param,Page);
		
	}); 
	
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var page = $("#page").find("li.active").html();//当前页
		var oper = <shiro:principal/>;
		var library_idx=null;
		if(oper.operator_type>=3)
			library_idx=oper.library_idx;
		var size= $('#showSize option:selected').html();
		var device_type = $("#type").val();
		if($("#type").val()==-1) 
			device_type=null;
			
		var device_group_name=$("#group").val();
		if($("#group").val()==-1)
			device_group_name=null;
					
		var device_id=$("#deviceId_tx").val();
		if(library_idx == null || library_idx == ''||library_idx == -1){
			library_idx = $("#library").val();
		}
		if(library_idx == -1){
			library_idx = "";
		}
		var param={
			"library_idx":library_idx,
			"device_type":device_type,
			"device_group_name":device_group_name,
			"device_id":device_id,
		};
		var Page = {
			"page":page,
			"pageSize":size,
		};
		$.select(param,Page);
	});
	
	
	
	$(".increase").on("click",function(){
		var url = "${pageContext.request.contextPath}/device/devicebatching";
		$("#mainframe").load(url,"",function(){
			$(".form-dialog-fix .form-dialog").each(function(){
				$(this).attr("date-right",$(this).css("right"));
			});
			
		});
	});
	
});
</script>
</body>
</html>


