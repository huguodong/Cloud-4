<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<style>



.page-title-bar .form-wrap{
	width:80%;
}

.page-title-bar .back-btn {
	width: 15px;
	height: 65px;
}
.page-title-bar span.title {
	display: inline-block;
	margin-top: 23px;
}
</style>

<div class="equipment-guanli">
	<div class="page-title-bar" style="height:90px;">
		<a href="" class="back-btn"></a>
		<span class="title"><sp:message code="设备批处理" text="设备批处理"></sp:message><a href="help-page.html" class="g-help"></a></span>
		<div class="form-wrap fr">
			<div style="float:right;">
				<input type="text" placeholder="设备原始馆ID" data-id="" id="old_library_id" class="g-input" style="width:140px;">
				<input type="text" placeholder="原始馆名称" readonly="readonly" id="old_library_name" class="g-input" style="width:140px;color: gray;background-color: #E0E0E0">
				<input type="text" placeholder="设备新馆ID" data-id="" id="new_library_id" class="g-input" style="width:140px;">
				<input type="text" placeholder="新馆名称" readonly="readonly" id="new_library_name" class="g-input" style="width:140px;color: gray;background-color: #E0E0E0">
				<div class="btn search " id="transfer"><sp:message code="转到新馆"></sp:message></div>
			</div>
		</div>
		
		<div class="form-wrap fr">
			<div style="float:right;">
				<div class="g-select">
					<select id="deviceType">
						<option value="-1"><sp:message code="devicemgmt.jsp.selectdevtype"></sp:message></option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<div class="g-select">
					<select id="deviceGroup">
						<option value="-1"><sp:message code="devicemgmt.jsp.selectgroup"></sp:message></option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<input type="text" name="" id="keyword" class="input g-input" placeholder="<sp:message code="devicemgmt.jsp.searchplaceholder"></sp:message>" />
				<div class="btn search" id="filter"><sp:message code="筛选"></sp:message></div>
				<%-- <div class="btn increase g-btn-green" ><sp:message code="批处理"></sp:message></div> --%>
			</div>
		</div>
	</div>
<!-- 		<div class="head_main"> -->
<!-- 			<div class="content-area"> -->
<!-- 				<div class="item"> -->
<!-- 					<ul> -->
<!-- 						<li> -->
<!-- 							<div class="left"><span class="g-mustIn">设备原始馆ID</span></div> -->
<!-- 							<div class="right"> -->
<!-- 								<input type="text" placeholder="请输入" data-id="" id="old_library_id" class="g-input" > -->
<!-- 								<input type="text" placeholder="馆名称" readonly="readonly" id="old_library_name" class="g-input" style="color: gray;background-color: #E0E0E0"> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 						<li> -->
<!-- 							<div class="left"><span class="g-mustIn">设备新馆ID</span></div> -->
<!-- 							<div class="right"> -->
<!-- 								<input type="text" placeholder="设备新馆ID" data-id="" id="new_library_id" class="g-input" > -->
<!-- 								<input type="text" placeholder="馆名称" readonly="readonly" id="new_library_name" class="g-input" style="color: gray;background-color: #E0E0E0"> -->
<!-- 							</div> -->
<!-- 						</li> -->
							
<!-- 					</ul> -->
<!-- 				</div> -->
<!-- 				<div class="btn search" ><sp:message code="批处理"></sp:message></div> -->
<!-- 			</div> -->
<!-- 		</div> -->
	
	<div class="main" style="background-color:#FFF;">
		
		
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
					<th class="col2"><sp:message code="设备类型名"></sp:message></th>
					<th class="col3"><sp:message code="设备分组"></sp:message></th>
					<th class="col4"><sp:message code="设备号"></sp:message></th>
					<th class="col5"><sp:message code="设备名称"></sp:message></th>
					<th class="col6"><sp:message code="设备IP"></sp:message></th>
<!-- 					<th class="col7"><sp:message code="操作"></sp:message></th> -->
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
var param = {};
var page = 1;
var pageSize = 10;
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
	
	
	getDeviceType();
});


$(".increase").on("click",function(){
	var url = "${pageContext.request.contextPath}/device/devicebatching";
	$("#mainframe").load(url,"",function(){
		$(".form-dialog-fix .form-dialog").each(function(){
			$(this).attr("date-right",$(this).css("right"));
		});
		
	});
});	

<%-- 筛选 --%>
$("#filter").on("click",function(){
	var libIdx=$("#old_library_id").attr("data-id");
	if(libIdx!=null && libIdx!=""){
		param = makeQueryParam(page, pageSize);
		param.libIdx = libIdx;
		queryLibraryDevices(param);
	}else{
		layer.alert("请输入原始馆ID");
	}
})



$("#old_library_id").on("change",function(){
	var libId = $(this).val();
	
	if(libId==null || libId==""){
		return;
	}
	
	$old = $(this);
	var param = {};
	getLibraryInfo(libId,function(data){
		$old.val(data.library_id);
		$old.attr("data-id",data.library_idx);
		$old.siblings("#old_library_name").val(data.library_name);
		
		if(data.library_idx!=null && data.library_idx!==""){
			getDeviceGroup(data.library_idx);
		}
		
		param = makeQueryParam(1, pageSize);
		param.deviceGroup = "-1";//图书馆分组为-1
		if(data.library_idx!=null && data.library_idx!==""){
			param.libIdx = data.library_idx;
			queryLibraryDevices(param);
		}
		
	});
});

function queryLibraryDevices(param){
	<%-- 通过图书馆idx去查其设备信息 --%>
	$("tbody").html("");
	$.ajax({
		url:"${pageContext.request.contextPath}/device/getLibraryDevicesByPage",
		type:"POST",
		data:{"json":JSON.stringify(param)}
	}).done(function(data){
		if(data!=null && data.state){
			var html = "";
			var devids = [];
			var list = data.result.rows;
			for (var i=0;i<list.length;i++){
				var item = list[i];
				devids.push(item.device_id);
				html += "<tr>";
				html += "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='"+item.device_id+"' id='"+item.device_idx+"' /></div></td>";
				html += "<td class='col2'>"+item.device_type_desc+"</td>";
				html += "<td class='col3'>"+(item.device_group_name?item.device_group_name:"")+"</td>";
				html += "<td class='col4'>"+item.device_id+"</td>";
				html += "<td class='col5'>"+item.device_name+"</td>";
				html += "<td class='col6'><input class=\"g-input\" style=\"height: 28px;\"></td>";
// 					html += "<td class='col7'><div class=\"btn search\" style=\"height: 28px;\">修改IP</div></td>";
				html += "</tr>";
			}
			queryDeviceIps(devids);
			$("tbody").html(html);
			
			$.pagination(data.result);  
		}
	});
}

<%-- 根据设备ids 查询设备的ip --%>
function queryDeviceIps(devids){
	if(devids!=null && devids.length>0){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/queryDeviceIps",
			type:"POST",
			data:{"json":JSON.stringify(devids)}
		}).done(function(data){
			if(data!=null && data!="" && data.state){
				var item = data.result;
				$("tbody tr").each(function(i,n){
					$col = $(this).find(".g-checkbox input");
					var name  = $col.attr("name");
					var ip = item[name];
					$(this).find(".g-input").val(ip);
				});
			}
		});
	}
}

<%-- 输入新馆id，查询新馆相关信息 --%>
$("#new_library_id").on("change",function(){
	var libId = $(this).val();
	$new = $(this);
	if(libId!=null && libId!=""){
		getLibraryInfo(libId,function(data){
			$new.val(data.library_id);
			$new.attr("data-id",data.library_idx);
			$new.siblings("#new_library_name").val(data.library_name);
		});
	}else{
		$new.attr("data-id","");
		$new.siblings("#new_library_name").val("");
	}
});


<%-- 根据图书馆id获取图书馆信息 --%>
function getLibraryInfo(libId,callback){
	$.ajax({
		url:"<%=basePath%>operator/getLibNameByIdx",
		type:"POST",
		data:{json:libId}
	}).done(function(data){
		var libObj  = {};
		if(data!=null && data!=""){
			if(data.state){
				if(data.result.lib_name!=null){
					libObj.library_idx = data.result.library_idx;
					libObj.library_name = data.result.lib_name;
					libObj.library_id = data.result.lib_id;
				}else{
					libObj.library_idx = "";
					libObj.library_name = data.result;
					libObj.library_id = libId;
				}
			}else{
				libObj.library_idx = "";
				libObj.library_name = "";
				libObj.library_id = libId;
			}
		}else{
			libObj.library_idx = "";
			libObj.library_name = "无法连接服务器";
			libObj.library_id = libId;
		}
		callback(libObj);
	}).fail(function(data){
		var libObj  = {};
		libObj.library_idx = "";
		libObj.library_name = "无法连接服务器";
		libObj.library_id = "";
		
		callback(libObj);
	});
}

//组装 翻页和查询 参数
function makeQueryParam(page,pageSize){
	var keyword=$("#keyword").val();
	var deviceType=$("#deviceType").val();
	var deviceGroup = $("#deviceGroup").val();
	var Page ={
		"page":page,
		"pageSize":pageSize,
		"deviceType":deviceType,
		"deviceGroup":deviceGroup,
		"keyword":keyword
	};
	return Page;
};


 //上一页操作
$("#page").on("click",".prev-page",function(){
	var currentpage = $(".paging-bar li.active").html();
	var page=Number(currentpage)-1;
	pageSize = $('#showSize option:selected').html();
	
	var libIdx=$("#old_library_id").attr("data-id");
	if(libIdx!=null && libIdx!=""){
		param = makeQueryParam(page, pageSize);
		param.libIdx = libIdx;
		queryLibraryDevices(param);
	}else{
		layer.alert("请输入原始馆ID");
	}
});
 

//下一页操作
$("#page").on("click",".next-page",function(){
	var currentpage = $(".paging-bar li.active").html();
	var page=Number(currentpage)+1;
	pageSize= $('#showSize option:selected').html();
	
	var libIdx=$("#old_library_id").attr("data-id");
	if(libIdx!=null && libIdx!=""){
		param = makeQueryParam(page, pageSize);
		param.libIdx = libIdx;
		queryLibraryDevices(param);
	}else{
		layer.alert("请输入原始馆ID");
	}
});


$("#page").on("click","li",function(){
	if($(this).hasClass("active"))
		return;
	var page = $(this).html();
	if(page=="...") return;
	pageSize= $('#showSize option:selected').html();
	
	var libIdx=$("#old_library_id").attr("data-id");
	if(libIdx!=null && libIdx!=""){
		param = makeQueryParam(page, pageSize);
		param.libIdx = libIdx;
		queryLibraryDevices(param);
	}else{
		layer.alert("请输入原始馆ID");
	}
}); 

/**
	每页显示的条目数切换
**/
$("select#showSize").on("change",function(){
	GlobalGLoading();
	var page = $("#page").find("li.active").html();//当前页
	pageSize = $('#showSize option:selected').html();
	var libIdx=$("#old_library_id").attr("data-id");
	if(libIdx!=null && libIdx!=""){
		param = makeQueryParam(page, pageSize);
		param.libIdx = libIdx;
		queryLibraryDevices(param);
	}else{
		layer.alert("请输入原始馆ID");
	}
});


<%-- 获取设备类型 --%>
function getDeviceType(){
	$.ajax({
   		url:"${pageContext.request.contextPath}/metadevicetype/SelectType",
		type:"POST",
		success:function(data){
			var html = "<option value='-1' selected>选择类型</option>";
			$.each(data.result,function(i,item){
				html += "<option value='"+item.device_type+"'>"+item.device_type_desc+"</option>";
	        });
			$("#deviceType").html(html);
   		}
	});
}

<%-- 获取图书馆设备组信息 --%>
function getDeviceGroup(library_idx){
	var param = {};
	param.library_idx = library_idx;
	$.ajax({
  		url:"${pageContext.request.contextPath}/devicegroup/SelectGroup",
		type:"POST",
		data:{"json":JSON.stringify(param)},
		success:function(data){
			var html = "<option value='-1' selected>选择分组</option>";
			$.each(data.result,function(i,item){
				html += "<option value='"+item.device_group_idx+"'>"+item.device_group_name+"</option>" ;
	        });
			$("#deviceGroup").html(html);
	  	}
	});
}

<%-- 将旧馆设备转移到新馆的操作 --%>
$("#transfer").on("click",function(){
	if($(this).hasClass("disabled")){
		return;
	}else{
		$(this).addClass("disabled");
	}
	var oldLibid = $("#old_library_id").val();
	var oldLibidx = $("#old_library_id").attr("data-id");
	var newLibid = $("#new_library_id").val();
	var newLibidx = $("#new_library_id").attr("data-id");
	if(oldLibid==null || oldLibid==="" || oldLibidx==null || oldLibidx===""){
		layer.alert("请填写原始馆信息");
		$(this).removeClass("disabled");
		return;
	}
	if(newLibid==null || newLibid==="" || newLibidx==null || newLibidx===""){
		layer.alert("请填写新馆信息");
		$(this).removeClass("disabled");
		return;
	}
	
	var transferList = [];
	var count = 0;
	$("tbody input[type='checkbox']:checked").each(function(){
		var obj = {};
		var idx = $(this).prop("id");
		var device_id = $(this).prop("name");
		var ip = $(this).closest("tr").find(".g-input").val();
		obj.device_idx=idx;
		obj.device_id=device_id;
		obj.ip=ip;
		transferList.push(obj);
		count ++;
	});
	
	var transferObj = {};
	transferObj.oldLibid = $("#old_library_id").val();
	transferObj.oldLibidx = $("#old_library_id").attr("data-id");
	transferObj.newLibid = $("#new_library_id").val();
	transferObj.newLibidx = $("#new_library_id").attr("data-id");
	transferObj.count = count;
	transferObj.transferList = transferList;
	
	transferToNewLib(transferObj);
	
	$(this).removeClass("disabled");
});

<%-- 将图书馆一的设备转移到图书馆二 --%>
function transferToNewLib(transferObj){
	if(transferObj!=null && transferObj.transferList!=null && transferObj.transferList.length>0){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/transferToNewLib",
			type:"POST",
			data:{"json":JSON.stringify(transferObj)}
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state){
					layer.alert("操作成功");
					$("#old_library_id").trigger("change");
				}else{
					layer.alert("操作失败，"+data.message);
				}
			}
		});
	}
}
</script>

</html>


