<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		.w-900 label, .form-dialog label {
   		 /* padding-left: 10px; */
   		 	padding-left:2px;
    		vertical-align: middle;
		}
		
		.form-wrap label {
   			 margin-right: 10px;
		}
		.logic-obj-li li{
			float: left;
			/*min-width: 130px;*/
		}
		.logic-obj-li label{
			min-width: 100px;
		}
		.form-dialog .form-wrap .logic-obj-li{
   			 margin-bottom: 2px;
		}
		.device-db-li li{
			float: left;
			/*min-width: 130px;*/
		}
		.device-db-li label{
			min-width: 100px;
		}
		.form-dialog .form-wrap .device-db-li{
   			 margin-bottom: 2px;
		}
		.equipment-leixing .col6 {
    		width: 500px;
		}
		.equipment-leixing .col4 {
    		min-width: 100px;
		}
		
.dev-table{
	/*table-layout:fixed;*/
	width: 100%;
	border-radius: 4px;
	background-color: #fff;
	text-align: left;
	word-break: break-all;
}
.dev-table .g-checkbox.on{
	background-image: url(../static/images/choosen1.png);
}
.dev-table tr{
	height: 40px;
}
.dev-table tbody tr:hover{
	background-color: #effcff;
}

.dev-table thead{
	background-color: #c2eafb;
	border-top:3px solid #00a2e9;
	color:#555555;
}
.dev-table th{
	text-align: left;/*IE不继承*/
	white-space: nowrap;
}
.dev-table td,
.dev-table th{
	padding-left: 12px;
	padding-right: 5px;
}
.dev-table td{
	padding:8px 5px 8px 12px;
	border:1px solid #eee;
    vertical-align: top;
}
.dev-table .col1{
	width: 45px;
	padding-left: 0;
	text-align: center;
}
.dev-table .btn-a{
	display: inline-block;
	padding:0 10px;
	margin:0 3px;
	height: 24px;
	line-height: 24px;
	color:#FFF;
	border-radius: 4px;
	cursor: pointer;
}
.dev-table .btn-a.edit{
	background-color: #00a2e9;
}
.dev-table .btn-a.config{
	background-color: #00a2e9;
}
.dev-table .btn-a.edit:hover{
	background-color: #0090d3;
}


.dev-table .btn-a.delete{
	background-color: #aaaaaa;
}
.dev-table .btn-a.delete:hover{
	background-color: #909090;
}
.dev-table .status{
	display: inline-block;
	padding-right: 15px;
}
.dev-table .green{
	background:url(../static/images/cir-status1.png) right center no-repeat;
}
.dev-table .red{
	background:url(../static/images/cir-status2.png) right center no-repeat;
}
.dev-table .yellow{
	background:url(../static/images/cir-status3.png) right center no-repeat;
}
.dev-table .gray{
	background:url(../static/images/cir-status4.png) right center no-repeat;
}
	</style>
</head>
<body>
<div class="equipment-leixing">
	
	<div class="page-title-bar">
		<span class="title">设备类型<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/devmgmt/devicetype.jsp" target="_blank" class="g-help"></a></span>
		<div class="form-wrap fr">
			
			<div class="g-select">
				<select id="select">
					<option value="-1" selected>选择类型</option>
					
				</select>
				<span class="arr-icon"></span>
			</div>
			<input type="text" name="" id="Keyword_deviceTypeDesc" class="input g-input" placeholder="描述" />
			<div class="btn search">查询</div>
			<!-- 修改权限 by huanghuang 20170215 -->
			<shiro:hasPermission name="0102020201">
				<div class="btn increase g-btn-green">新增</div>
			</shiro:hasPermission>
			<shiro:hasPermission name="0102020202">
				<div class="btn delete">删除</div>
			</shiro:hasPermission>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox" ><input type="checkbox" name="" id="" /></div></th>
					<th class="col2">名称</th>
					<th class="col3">描述</th>
					<th class="col6">逻辑部件</th>
					<th class="col7">设备端数据库</th>
					<th class="col4">备注</th>
					<th class="col5 hide">创建时间</th>
					
					<th class="col7">操作</th>
				</tr>
			</thead>
			<tbody>
			
						
			</tbody>
		</table>
	</div>
	<div class="paging-bar">
			<div class="left">
				<span class="t fl">选择</span>
				<div class="btn g-chooseAll">全选</div>
				<div class="btn g-noChooseAll">反选</div>
				<span class="t2 fl">显示</span>
				<div class="g-select refresh">
					<select id="pagesize">
						<option value="10" selected>10</option>
						<option value="30" >30</option>
						<option value="60">60</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<span class="t2 fl " id="ChooseNum">已选中<span class="total-choosen-num">0</span>个</span>
			</div>
			<div id="page" class="right">
			</div>
			<span class="total-page fr"></span>
			<span class="total-num fr"></span>
	</div>
</div>
<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		当前选择了<span class="font-red"></span>个项目<br>
		是否要删除选择的设备类型？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<div class="form-dialog" id="add">
	<div class="title">新增设备类型</div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">名称</span></div>
				<div class="right">
					<input type="text" name="" id="device_type" class="g-input" placeholder="请输入"  />
					<span class="error-msg">设备类型名称不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">描述</span></div>
				<div class="right">
					<input type="text" name="" id="device_type_desc" class="g-input" placeholder="请输入"  />
					<span class="error-msg">设备类型描述不能为空</span>
				</div>
			</li>
			<li class="logic-obj-li"></li>
			<li class="device-db-li"></li>
			<li>
				<div class="left">备注</div>
				<div class="right">
					<textarea class="g-textarea" name="" id="device_type_mark"></textarea>
					<span class="error-msg">备注超出限定长度，请修改</span>
				</div>
			</li>
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input id="increaseBtn" type="submit" value="保存" class="submit g-btn-blue" />
				</div>
			</li>
		</ul>
	</div>
</div>

<div class="form-dialog" id="modify">
	<div class="title">编辑设备类型</div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">名称</span></div>
				<div class="right">
					<input type="text" name="" id="edit_type" class="g-input" placeholder="请输入"  />
					<input type="hidden" name="" id="version_stamp" class="g-input" placeholder=""  />
					<span class="error-msg">设备类型名称不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">描述</span></div>
				<div class="right">
					<input type="text" name="" id="edit_type_desc" class="g-input" placeholder="请输入"  />
					<span class="error-msg">设备类型名称不能为空</span>
				</div>
			</li>
			<li>
				<div class="left">备注</div>
				<div class="right">
					<textarea class="g-textarea" id="edit_type_mark"></textarea>
					<span class="error-msg">备注超出限定长度，请修改</span>
				</div>
			</li>
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input id="ineditBtn" type="submit" value="保存" class="submit g-btn-blue" />
				</div>
			</li>
		</ul>
	</div>
</div>
<div class="form-dialog" id="extlistdiv">
	<div class="title">硬件列表</div>
	<input type="hidden" id="otherlist">
		<table class="dev-table" style="border-collapse:separate;"> 
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox" ><input type="checkbox" name="" id="selectAllExt" /></div></th>
					<th class="col2">型号</th>
					<th class="col3">名称</th>
				</tr>
			</thead>
			<tbody>
<!-- 				<tr> -->
<!-- 					<td class="col1"><div class="g-checkbox" ><input type="checkbox" name="" id="" /></div></td> -->
<!-- 					<td class="col2">名称</td> -->
<!-- 					<td class="col3">描述</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td class="col1"><div class="g-checkbox" ><input type="checkbox" name="" id="" /></div></td> -->
<!-- 					<td class="col2">名称</td> -->
<!-- 					<td class="col3">描述</td> -->
<!-- 				</tr> -->
			</tbody>
		</table>
	<div class="form-wrap" style="text-align:center;">
			<input id="comfirmExtBtn" type="submit" value="确定" class="submit g-btn-blue" style="margin:0 auto;"/>
	</div>
</div>

<script type="text/javascript">
var lang='zh_CN';
var flag="add";

var extList = {};<%-- 硬件列表 --%>

function jsonToObj(str){ 
	try{
		return JSON.parse(str); 
	}catch(e){
		return "";
	}
}
/**
	str={"zh_CN":"xxx"},{"zh_CN":"yyy"}
	lang="zh_CN"/"en_US"
**/
function commaJsonToObj(str,lang){
	var string="";
	try{
		if(str&&str.indexOf(",")>=-1){
			var stringarr=str.split(",");
			for(var i=0;i<stringarr.length;i++){
				var strobj=stringarr[i];
				var strs=jsonToObj(strobj)[lang];
				string+=strs+",";
			}
		}else if(str){
			string=str[lang];
		}
		if(string.indexOf(",")>=-1)
			string=string.substring(0,string.length-1);
		return string;
	}catch(e){
		console.log("commaJsonToObj error:"+e);
		return "";
	}
}
	



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

	
	<%-- qyeryDeviceExtList 查询设备的硬件列表 --%>
	qyeryDeviceExtList();
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];  
	var delLayer = null;
	/**
		删除按钮操作显示对话框
	**/
	var devicetype_idx = new Array() ;
	$(".delete").on("click",function(){
		devicetype_idx.length = 0;
		var num = $(".main tbody input[name='idx']:checked").length;
		$(".main tbody input[name='idx']:checked").each(function() {  
			devicetype_idx.push($(this).attr("id"));
        });  
		
		if(num>0){
			$(".g-delete-dialog .word").html("");
        	$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"</span>个项目<br>是否要删除选择的设备类型？");
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
			layer.alert("请选择要删除的设备类型");
		}			
	});
	
	//删除按钮操作
	$(".main tbody").on("click",".delete",function(){
		devicetype_idx.length = 0;
		devicetype_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
			$(".g-delete-dialog").find(".word").html(
		'是否删除 <span class="font-red">'+$(this).parents("tr").find(".device_type").html()+'</span>');
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
	/**
		删除按钮操作
	**/
	$(".form-btn.remove").on("click",function(){
		GlobalGLoading();
		var idx = devicetype_idx;
		if(!idx) return;
		var param=new Array();
		for(var i=0;i<idx.length;i++){
			param[i]={
			"meta_devicetype_idx":idx[i],
			};
		}
		$.ajax({
			url:basePath+"/metadevicetype/DeleteMetaDeviceType",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
			if(data.state){
				if(delLayer){
					layer.close(delLayer);
				}
				/**
				 * 新增 修改 删除 成功操作之后 弹窗 使用
				 * FUNCTION GlobalShowMsg
				 * 参数：
				 * {
				 * 	 showText:"",
				 *   timeout:3000
				 *   status:true/false
				 * }
				 * **/
				if(data.message=="ONE"){
					GlobalShowMsg({showText:"删除成功",status:true});
				}else if(data.retMessage){
					showRetMessage(data.retMessage);
				}
		     	var currentpage = $(".paging-bar li.active").text();
		     	var size= $('#pagesize option:selected').text();
				var device_type= $("#select").val();
				if($("#select").val()==-1) 
					device_type=null;
				var param={"device_type":device_type};
				var Page ={"page":currentpage,"pageSize":size};
				//调用页面的查询ajax
				$.select(param,Page);  
		     }else if(data.retMessage){
		     	showRetMessage(data.retMessage);
		     }
			}
		});
		
	});
	
    
	/**
		编辑按钮操作显示对话框
	**/
	var type = [];
	var editLayer=null;
	$(".main tbody").on("click",".edit",function(){
		flag = "edit";<%-- 编辑 --%>
		$("#add .title").html("编辑设备类型");
		type = [];
		type.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		type.push($(this).parent().parent().find(".col2").html());
		type.push($(this).parent().parent().find(".col3").html());
		type.push($(this).parent().parent().find(".col4").html());
		var version_stamp = $(this).parent().parent().find(".version_stamp").html();
		var logic_other = $(this).parent().parent().find(".logic_other").html();
		
		$("#device_type").val(type[1]);
		$("#device_type_desc").val(type[2]);
		$("#device_type_mark").val(type[3]); 
		$("#version_stamp").val(version_stamp); 
		$("#otherlist").val(logic_other); 
		/**
			清除 device_logic_list 数据
		**/
		$("#add").find(".logic-obj-li").find("input[type=checkbox]").removeAttr("checked").parents(".g-checkbox").removeClass("on");
		/**
			设置 device_logic_list 数据
		**/
		var device_logic_list=$.trim($(this).parents("tr").find(".device_logic_list").html());
		type.push(device_logic_list);
		type.push(logic_other);<%-- 保存原始数据 --%>
		if(device_logic_list){
			if(device_logic_list.indexOf(",")){
				var device_logic_list_arr=device_logic_list.split(",");
				for(var i=0;i<device_logic_list_arr.length;i++){
					var id=device_logic_list_arr[i];
// 					$("#modify").find(".logic-obj-li").find("input[type=checkbox][id="+id+"]").trigger("click");
					$("#add").find(".logic-obj-li").find("input[type=checkbox][id="+id+"]").prop("checked",true).parents(".g-checkbox").addClass("on");
				}
			}else{
				
			}
		}
		
		$("#add").find(".device-db-li").find("input[type=checkbox]").removeAttr("checked").parents(".g-checkbox").removeClass("on");
		/**
			设置 device_logic_list 数据
		**/
		var device_db_list=$.trim($(this).parents("tr").find(".device_db_list").html());
		type.push(device_db_list);
		if(device_db_list){
			if(device_db_list.indexOf(",")){
				var device_db_list_arr=device_db_list.split(",");
				for(var i=0;i<device_db_list_arr.length;i++){
					var id=device_db_list_arr[i];
					$("#add").find(".device-db-li").find("input[type=checkbox][id="+id+"]").prop("checked",true).parents(".g-checkbox").addClass("on");
				}
			}else{
				
			}
		}
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
			content: $("#add"), //捕获的元素
			cancel: function(index){
				layer.close(editLayer);
				this.content.hide();
			}
		});
	});
	
	<%-- 编辑modal的保存按钮 --%>
	<%--  新增以及编辑合并， 此方法不用
	$("#ineditBtn").on("click",function(){
		GlobalGLoading();
		var fieldCheck=true;
		increaseCheck=true;
		checkInc(increaseCheck);
		var meta_devicetype_idx =type[0];
		var device_type = $("#edit_type").val();
		var device_type_desc=$("#edit_type_desc").val();
		var device_type_mark=$("#edit_type_mark").val();
		var version_stamp=$("#version_stamp").val();
		
		var len_type = $("#edit_type").val().length;
		var len_desc = $("#edit_type_desc").val().length;
		var len_mark = $("#edit_type_mark").val().length;
		var otherlist = $("#otherlist").val();
		
		if(len_mark>200){
			$("#edit_type_mark").parent().parent().addClass("error");
			fieldCheck=false;
		}
		if(len_type>100){
			$li=$("#edit_type").parent().parent();
			$("#edit_type").siblings().remove();
			$("#edit_type").after('<span class="error-msg">设备类型名称超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		if(len_desc>100){
			$li=$("#edit_type_desc").parent().parent();
			$("#edit_type_desc").siblings().remove();
			$("#edit_type_desc").after('<span class="error-msg">描述超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		/* if(len_type>100 || len_desc>100 || len_mark>200){
			layer.alert("有字段超出设定长度，请修改！");
			return;
		} */
		var deviceLogicList="";
		/**获取逻辑部件信息**/
		$("#modify .logic-obj-li").find("input[type=checkbox]:checked").each(function(index,dom){
			deviceLogicList+=$(dom).attr("id")+",";
		});
		console.log("deviceLogicList",deviceLogicList);
		/**清除尾部的逗号**/
		if(deviceLogicList!=""){
			deviceLogicList=deviceLogicList.substring(0,deviceLogicList.length-1);
		}else{
			//layer.alert("请至少选择一个逻辑部件");
			$("li.logic-obj-li").each(function(index,dom){
				$(dom).addClass("error");
			});
			return;
		}
		if(!fieldCheck){
			return;
		}
		if(!device_type || !device_type_desc) 
			return;	
		if(device_type==type[1] && device_type_desc==type[2] && device_type_mark == type[3]&&type[4]==deviceLogicList
				&& otherlist==type[5]){
			console.log("未修改字段,return");
			if(editLayer){
				layer.close(editLayer);
			}
			return;
		}
		var param={
			"meta_devicetype_idx":meta_devicetype_idx,
			"device_type":device_type,
			"device_type_desc":device_type_desc,
			"device_type_mark":device_type_mark,
			"device_logic_list":deviceLogicList,
			"version_stamp":version_stamp,
			"device_logic_other":otherlist
		};
		$.ajax({
			url:basePath+"/metadevicetype/UpdMetaDeviceType",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				if(data.state){
					if(editLayer){
						layer.close(editLayer);
					}
					GlobalShowMsg({showText:"修改成功",status:true});
					var currentpage = $(".paging-bar li.active").html();
			     	var size= $('#pagesize option:selected').html();
					var device_type= $("#select").val();
					if($("#select").val()==-1) 
						device_type=null;
					var param={"device_type":device_type};
					var Page ={"page":currentpage,
							"pageSize":size};
					//调用页面的查询ajax
					$.select(param,Page);
				}else{
					var msg = data.retMessage;
					if(msg.indexOf("optimistic")>=0){
						layer.alert("当前选择的数据不是最新数据,请刷新之后再做编辑操作");
					}
				}
			}
		});
		
	});  --%>
	
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});

	$(".g-increase-dialog .form-wrap  .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	});
	
	$(":text").focus(function(){
		$(this).parent().parent().removeClass("error");
	});
	$("textarea").focus(function(){
		$(this).parent().parent().removeClass("error");
	});
	//编辑操作检查字段
	var increaseCheck=false;
	
	<%-- 
	var checkInc=function(incCheck){
		if(incCheck){
			var device_type=$("#edit_type").val();
			var device_type_desc=$("#edit_type_desc").val();
			if(!device_type){
				$li=$("#edit_type").parent().parent();
				$("#edit_type").siblings().remove();
				$("#edit_type").after('<span class="error-msg">设备类型名称不能为空</span>');
				$li.addClass("error");
			
			}
			if(!device_type_desc){
				$li=$("#edit_type_desc").parent().parent();
				$("#edit_type_desc").siblings().remove();
				$("#edit_type_desc").after('<span class="error-msg">描述不能为空</span>');
				$li.addClass("error");
			}
		}
		increaseCheck=false;
	};
	
	--%>
	//添加操作检查字段
	var checkIncData=function(incCheck){
		if(incCheck){ 
			var device_type=$("#device_type").val();
			var device_type_desc=$("#device_type_desc").val();
			if(!device_type){
				$li=$("#device_type").parent().parent();
				$("#device_type").siblings().remove();
				$("#device_type").after('<span class="error-msg">设备类型名称不能为空</span>');
				$li.addClass("error");
			}
			if(!device_type_desc){
				$li=$("#device_type_desc").parent().parent();
				$("#device_type_desc").siblings().remove();
				$("#device_type_desc").after('<span class="error-msg">描述不能为空</span>');
				$li.addClass("error");
			}
		}
		increaseCheck=false;
	};
	
	/**
		新增按钮操作显示对话框
	**/
	var increaseLayer=null;
	$(".increase").on("click",function(){
		flag = "add";<%-- 编辑 --%>
		$("#add .title").html("新增设备类型");
		//清空数据操作
		$("#add").find("input[type=text]").val("");
		$("#add").find("input[type=checkbox]").removeAttr("checked").parents(".g-checkbox").removeClass("on");
		$("#add").find("textarea").val("");
		$("#add").find("li").removeClass("error");
		$("#otherlist").val("");
		
		increaseLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["800px"],
			offset :["130px"],
			content: $("#add"), //捕获的元素
			cancel: function(index){
				layer.close(delLayer);
				this.content.hide();
				
			}
		});
	});
	//新增保存 按钮操作
	$("#increaseBtn").on("click",function(){
		GlobalGLoading();
		//debugger;
		var fieldCheck=true;
		increaseCheck=true;
		checkIncData(increaseCheck);
		var device_type =$("#device_type").val();
		var device_type_desc=$("#device_type_desc").val();
		var device_type_mark=$("#device_type_mark").val();
		
		var len_type = $("#device_type").val().length;
		var len_desc = $("#device_type_desc").val().length;
		var len_mark = $("#device_type_mark").val().length;
		
		var otherlist = $("#otherlist").val();
		
		if(len_mark>200){
			$("#device_type_mark").parent().parent().addClass("error");
			fieldCheck=false;
		}
		if(len_type>100){
			$li=$("#device_type").parent().parent();
			$("#device_type").siblings().remove();
			$("#device_type").after('<span class="error-msg">设备类型名称超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		if(len_desc>100){
			$li=$("#device_type_desc").parent().parent();
			$("#device_type_desc").siblings().remove();
			$("#device_type_desc").after('<span class="error-msg">描述超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		/* if(len_type>100 || len_desc>100 || len_mark>200){
			layer.alert("有字段超出设定长度，请修改！");
			increaseCheck=false;
		} */
		
		var deviceLogicList="";
		/**获取逻辑部件信息**/
		$("#add .logic-obj-li").find("input[type=checkbox]:checked").each(function(index,dom){
			deviceLogicList+=$(dom).attr("id")+",";
		});
		/**清除尾部的逗号**/
		if(deviceLogicList!=""){
			deviceLogicList=deviceLogicList.substring(0,deviceLogicList.length-1);
		}else{
			//layer.alert("请至少选择一个逻辑部件");
			$("li.logic-obj-li").each(function(index,dom){
				$(dom).addClass("error");
			});
			return;
		}
		
		var deviceDbList="";
		/**获取逻辑部件信息**/
		$("#add .device-db-li").find("input[type=checkbox]:checked").each(function(index,dom){
			deviceDbList+=$(dom).attr("id")+",";
		});
		/**清除尾部的逗号**/
		if(deviceDbList!=""){
			deviceDbList=deviceDbList.substring(0,deviceDbList.length-1);
		}else{
			//layer.alert("请至少选择一个逻辑部件");
			$("li.device-db-li").each(function(index,dom){
				$(dom).addClass("error");
			});
			return;
		}
		
		if(!fieldCheck){
			return;
		}
		
		if(!device_type || !device_type_desc) 
			return;
		var param={
			"device_type":device_type,
			"device_type_desc":device_type_desc,
			"device_type_mark":device_type_mark,
			"device_logic_list":deviceLogicList,
			"device_db_list":deviceDbList,
			"device_logic_other":otherlist
		};
		
		if(flag=="add"){
			$.ajax({
				 url:basePath+"/metadevicetype/AddMetaDeviceType",
			 	 type:"POST",
			 	 data:{"json":JSON.stringify(param)},
			     success:function(data){
				     if(data.state){
				     	if(increaseLayer){
				     		layer.close(increaseLayer);
				     	}
				     	GlobalShowMsg({showText:"新增成功",status:true});
				     	var currentpage = $(".paging-bar li.active").html();
				     	var size= $('#pagesize option:selected').html();
						var device_type= $("#select").val();
						if($("#select").val()==-1) 
							device_type=null;
						var param={"device_type":device_type,};
						var Page ={"page":currentpage,"pageSize":size};
						//调用页面的查询ajax
						$.select(param,Page);
						<%-- 更新设备类型下拉框 --%>
						selectAllDeviceType();
				     }else if(data.retMessage){
				     	showRetMessage(data.retMessage);
				     }
			 	}
			});
		}
		if(flag=="edit"){
			if(device_type==type[1] && device_type_desc==type[2] && device_type_mark == type[3]&&type[4]==deviceLogicList
					&& otherlist==type[5] && deviceDbList == type[6]){
				console.log("未修改字段,return");
				if(editLayer){
					layer.close(editLayer);
				}
				return;
			}
			var meta_devicetype_idx =type[0];
			param.meta_devicetype_idx = meta_devicetype_idx;
			$.ajax({
				url:basePath+"/metadevicetype/UpdMetaDeviceType",
				type:"POST",
				data:{"json":JSON.stringify(param)},
				success:function(data){
					if(data.state){
						if(editLayer){
							layer.close(editLayer);
						}
						GlobalShowMsg({showText:"修改成功",status:true});
						var currentpage = $(".paging-bar li.active").html();
				     	var size= $('#pagesize option:selected').html();
						var device_type= $("#select").val();
						if($("#select").val()==-1) 
							device_type=null;
						var param={"device_type":device_type};
						var Page ={"page":currentpage,
								"pageSize":size};
						//调用页面的查询ajax
						$.select(param,Page);
						<%-- 更新设备类型下拉框 --%>
						selectAllDeviceType();
					}else{
						var msg = data.retMessage;
						if(msg.indexOf("optimistic")>=0){
							layer.alert("当前选择的数据不是最新数据,请刷新之后再做编辑操作");
						}
					}
				}
			});
		}
	});
	
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var device_type= $("#select").val();
		var deviceTypeDesc=$("#Keyword_deviceTypeDesc").val();
		
		if($("#select").val()==-1) 
			device_type=null;
		var param={
			"device_type":device_type,
		};
		if(deviceTypeDesc){
			param.device_type_desc=deviceTypeDesc;
		}
		var size= $('#pagesize option:selected').text();
		var Page={"page":"1","pageSize":size}; 
		$.select(param,Page);
	});
	
	$(document).ready(function(){
		
		//获取当前页数
		var size= $('#pagesize option:selected').text();
		var Page={"page":"1","pageSize":size};
		var param ={};
		$(".main tbody").html("");
		$.ajax({
			url:basePath+"/metadevicetype/SelectMetaDeviceType",
			type:"POST",
			data:{"json":JSON.stringify(param),"page":JSON.stringify(Page)},
			success:function(data){
				$.each(data.result.rows,function(i,item){
					$(".main tbody").append(
					"<tr>"+
	            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.meta_devicetype_idx+"' /></div></td>" +
	            	"<td class='device_logic_list hide'>" + item.device_logic_list + "</td>" +
	            	"<td class='device_db_list hide'>" + item.device_db_list + "</td>" +
	           		"<td class='col2 device_type'>" + item.device_type    + "</td>" +
	           		"<td class='col3'>" + item.device_type_desc    + "</td>" +
	           		"<td class='col6'>" + commaJsonToObj(item.device_logic_list_desc,lang)+ "</td>" +
	           		"<td class='col8'>" + item.device_db_list+ "</td>" +
	                "<td class='col4'>" + item.device_type_mark + "</td>" +
	                "<td class='col5 hide'>" + item.createTime + "</td>"+
	                "<td class='col7'><span class='btn-a edit'>编辑</span>"+
	                "<span class='btn-a delete'>删除</span></td>" +
	                "<td class='version_stamp hide'>" + item.version_stamp    + "</td>" +
	                "<td class='logic_other hide'>" + item.device_logic_other    + "</td>" +
	                "</tr>");
	                });
				var t=0;
				// 0102031201 新增设备类型  
				// 0102031202 编辑 
				// 0102031203 删除
				<shiro:lacksPermission name="0102020201">
					$(".increase").attr("style","display:none;");
	   			</shiro:lacksPermission>
	   			<shiro:lacksPermission name="0102020202">
	   			 	t++;
					$(".delete").attr("style","display:none;");
	    		</shiro:lacksPermission>
	    		<shiro:lacksPermission name="0102020203">
	    			t++;
					$(".edit").attr("style","display:none;");
	    		</shiro:lacksPermission>
	    		if(t==2){
	    			$(".col7").attr("style","display:none;");
	    		}
	    		$.pagination(data.result);
			}
		});
	});
	
	$(document).ready(function() {
		selectAllDeviceType();
 	});
	
	function selectAllDeviceType(){
		$.ajax({
	   		url:basePath+"/metadevicetype/SelectType",
			type:"POST",
			success:function(data){
			var html="";
			$("#select").html("<option value='-1' selected>选择类型</option>");
				$.each(data.result,function(i,item){
					html +="<option value=\""+item.device_type+"\">"+item.device_type_desc+"</option>";
	            });
	            $("#select").append(html);
	   		}
    	});
	}
	
	
	//封装查询ajax
	jQuery.select=function(param,Page){
		$(".main tbody").html("");
		$.ajax({
			url:basePath+"/metadevicetype/SelectMetaDeviceType",
			type:"POST",
			data:{"json":JSON.stringify(param),"page":JSON.stringify(Page),},
			success:function(data){
				$.each(data.result.rows,function(i,item){
					$(".main tbody").append(
					"<tr>"+
            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.meta_devicetype_idx+"' /></div></td>" +
            	    "<td class='device_logic_list hide'>" + item.device_logic_list + "</td>" +
            	    "<td class='device_db_list hide'>" + item.device_db_list + "</td>" +
            	    "<td class='col2 device_type'>" + item.device_type    + "</td>" +
            	    "<td class='col3'>" + item.device_type_desc    + "</td>" +
            	    "<td class='col6'>" + commaJsonToObj(item.device_logic_list_desc,lang)+ "</td>" +
            	    "<td class='col8'>" + item.device_db_list+ "</td>" +
                    "<td class='col4'>" + item.device_type_mark + "</td>" +
                    "<td class='col5 hide'>" + item.createTime + "</td>"+
                    "<td class='col7'><span class='btn-a edit'>编辑</span>"+
                    "<span class='btn-a delete'>删除</span></td>" +
                    "<td class='version_stamp hide'>" + item.version_stamp    + "</td>" +
                    "<td class='logic_other hide'>" + item.device_logic_other    + "</td>" +
                	"</tr>");	
				});
				//一条都没有的情况下，尝试跳转带上一页
				if(data.result.rows.length==0&&Page.page!=1){
					Page.page-=1;
					$.select(param,Page);
				}
				var t=0;
				<shiro:lacksPermission name="0102020202">
   			 		t++;
					$(".delete").attr("style","display:none;");
    			</shiro:lacksPermission>
    			<shiro:lacksPermission name="0102020203">
    				t++;
					$(".edit").attr("style","display:none;");
    			</shiro:lacksPermission>
    			if(t==2){
    				$(".col7").attr("style","display:none;");
    			}
    			$.pagination(data.result);
			}
		});
	};

	
	/**
		<li class="logic-obj-li">
				<div class="left"><span class="g-mustIn">设备逻辑部件</span></div>
				<ul>
					<li>
						<div class="g-checkbox">
							<input type="checkbox" >
						</div>
						<label for="function_register">允许办证</label>
					</li>
					<li>
						<div class="g-checkbox">
							<input type="checkbox" >
						</div>
						<label for="function_register">允许办证</label>
					</li>
					<li>
						<div class="g-checkbox">
							<input type="checkbox" >
						</div>
						<label for="function_register">允许办证</label>
					</li>
					<li>
						<div class="g-checkbox">
							<input type="checkbox" >
						</div>
						<label for="function_register">允许办证</label>
					</li>
					<li>
						<div class="g-checkbox">
							<input type="checkbox" >
						</div>
						<label for="function_register">允许办证</label>
					</li>
				</ul>
			</li>
		获取设备类型对应的逻辑外设
	**/
	
	$.ajax({
		url:basePath+"/metadevicetype/selectMetadataLogicObj",
		data:{req:""},
		type:"GET"
	}).done(function(data){
		if(data&&data.state&&data.result){
			var metaLogicObjs=data.result;
			$(".logic-obj-li").remove();
			var li='<li class="logic-obj-li">'+
					'<div class="left"><span class="g-mustIn">设备逻辑部件</span></div>'+
					'<ul>';
			$.each(metaLogicObjs,function(index,metaLogicObj){
				if(index!=0&&(index+1)%4==0){/**每行最后一个**/
					//4的倍数
					li+='<li>';
					li+='<div class="g-checkbox">';
					li+='<input type="checkbox" id="'+metaLogicObj.logic_obj+'">';
					li+='</div>';
					li+='<label style="display:inline-block;" for="'+metaLogicObj.logic_obj+'">'+jsonToObj(metaLogicObj.logic_obj_desc)[lang]+'</label>';
					li+='</li>';
					li+='</ul></li>';
					if((index+1)<metaLogicObjs.length){
						li+='<li class="logic-obj-li"><div class="left"><span class="">&nbsp;</span></div><ul>';
					}
				}else if(index+1==metaLogicObjs.length){/**最后一个**/
					li+='<li>';
					li+='<div class="g-checkbox">';
					li+='<input type="checkbox" id="'+metaLogicObj.logic_obj+'">';
					li+='</div>';
					li+='<label style="display:inline-block;" for="'+metaLogicObj.logic_obj+'">'+jsonToObj(metaLogicObj.logic_obj_desc)[lang]+'</label>';
					li+='</li>';
					li+='</ul></li>';
				}else{/**其他**/
					li+='<li>';
					li+='<div class="g-checkbox">';
					li+='<input type="checkbox" id="'+metaLogicObj.logic_obj+'">';
					li+='</div>';
					li+='<label style="display:inline-block;" for="'+metaLogicObj.logic_obj+'">'+jsonToObj(metaLogicObj.logic_obj_desc)[lang]+'</label>';
					li+='</li>';
				}
			});
			li+='<li class="logic-obj-li">'+
				 '<div class="left">&nbsp;</div>'+
 				 '<div class="right"><span class="error-msg">至少需要选择一个逻辑部件</span></div>'+	
				'</li>';
			
			$("#add #device_type_desc").parents("li").after(li);
			$("#modify #edit_type_desc").parents("li").after(li);
			
			$("li.logic-obj-li").on("change","input[type=checkbox]",function(){
				$("li.logic-obj-li").each(function(index,d){
					$(d).removeClass("error");
				});
			});
		}
	});
		
	$.ajax({
		url:basePath+"/metadevicetype/selectMetadataDeviceDb",
		data:{req:""},
		type:"GET"
	}).done(function(data){
		if(data&&data.state&&data.result){
			var metaDeviceDbs=data.result;
			$(".device-db-li").remove();
			var li='<li class="device-db-li">'+
					'<div class="left"><span class="g-mustIn">设备本地数据库</span></div>'+
					'<ul>';
			$.each(metaDeviceDbs,function(index,metaDeviceDb){
				if(index!=0&&(index+1)%4==0){/**每行最后一个**/
					//4的倍数
					li+='<li>';
					li+='<div class="g-checkbox">';
					li+='<input type="checkbox" id="'+metaDeviceDb.db_name+'">';
					li+='</div>';
					li+='<label style="display:inline-block;" for="'+metaDeviceDb.db_name+'">'+metaDeviceDb.db_name+'</label>';
					li+='</li>';
					li+='</ul></li>';
					if((index+1)<metaDeviceDbs.length){
						li+='<li class="device-db-li"><div class="left"><span class="">&nbsp;</span></div><ul>';
					}
				}else if(index+1==metaDeviceDbs.length){/**最后一个**/
					li+='<li>';
					li+='<div class="g-checkbox">';
					li+='<input type="checkbox" id="'+metaDeviceDb.db_name+'">';
					li+='</div>';
					li+='<label style="display:inline-block;" for="'+metaDeviceDb.db_name+'">'+metaDeviceDb.db_name+'</label>';
					li+='</li>';
					li+='</ul></li>';
				}else{/**其他**/
					li+='<li>';
					li+='<div class="g-checkbox">';
					li+='<input type="checkbox" id="'+metaDeviceDb.db_name+'">';
					li+='</div>';
					li+='<label style="display:inline-block;" for="'+metaDeviceDb.db_name+'">'+metaDeviceDb.db_name+'</label>';
					li+='</li>';
				}
			});
			li+='<li class="device-db-li">'+
				 '<div class="left">&nbsp;</div>'+
 				 '<div class="right"><span class="error-msg">至少需要选择一个设备本地数据库</span></div>'+	
				'</li>';
			
			$("#add #device_type_desc").parents("li").after(li);
			$("#modify #edit_type_desc").parents("li").after(li);
			
			$("li.device-db-li").on("change","input[type=checkbox]",function(){
				$("li.device-db-li").each(function(index,d){
					$(d).removeClass("error");
				});
			});
		}
	});
	
	 //上一页操作
	$("#page").on("click",".prev-page",function(){
		var currentpage = $(".paging-bar li.active").text();
		var page=Number(currentpage)-1;
		var size= $('#pagesize option:selected').text();
		var device_type= $("#select").val();
		if($("#select").val()==-1) 
			device_type=null;
		var param={"device_type":device_type,};
		var Page ={"page":page,
				"pageSize":size};
		$.select(param,Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").text();
		page = Number(currentpage) + 1;
		var size= $('#pagesize option:selected').text();
		var device_type= $("#select").val();
		if($("#select").val()==-1) 
			device_type=null;
		var param={"device_type":device_type,};
		var Page ={"page":page,
				"pageSize":size};
		$.select(param,Page);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var size= $('#pagesize option:selected').text();
		var page = $(this).html();
		if(page=="...") return;
		var device_type= $("#select").val();
		if($("#select").val()==-1) 
			device_type=null;
		var param={"device_type":device_type,};
		var Page ={"page":page,
				"pageSize":size};
		$.select(param,Page);
		
	}); 
	/**
		每页显示的条目数切换
	**/
	$("select#pagesize").on("change",function(){
		GlobalGLoading();
		var size= $('#pagesize option:selected').text();
		var device_type= $("#select").val();
		if($("#select").val()==-1) 
			device_type=null;
		var param={"device_type":device_type,};
		var Page ={"page":1,"pageSize":size};
		$.select(param,Page);
	});
	
});

<%-- 查询设备的外设列表 --%>
<%--
{
                "meta_ext_idx":1,
                "ext_model":"CRV100D",
                "ext_model_desc":"{"zh_CN":"华视身份证阅读器"}",
                "ext_model_driver_path":"",
                "ext_model_version":"V3",
                "ext_type":"idreader"
            }
--%>
function qyeryDeviceExtList(){
	$.ajax({
		url:"${pageContext.request.contextPath}/metadevicetype/qyeryDeviceExtList",
		type:"POST",
		data:{}
	}).done(function(data){
		$("#extlistdiv tbody").html("");
		if(data && data.state){
			var result = data.result;
			if(result){
				extList = result.extModelList;
				if(extList){
					var html = "";
					for(var i=0; i<extList.length; i++){
						var item = extList[i]
						html += "<tr>";
						html += "<td class=\"col1\"><div class=\"g-checkbox\" ><input type=\"checkbox\" name=\"\" value=\""+item.ext_model+"\" /></div></td>";
						html += "<td class=\"col2\">"+item.ext_model+"</td>";
						html += "<td class=\"col3\">"+commaJsonToObj(item.ext_model_desc,lang)+"</td>";
						html += "</tr>";
					}
					$("#extlistdiv tbody").html(html);
					
				}
			}
		}
	})
}


var extlistdivIndex = null;

$(document).off("click","#Other");
$(document).on("click","#Other",function(){
	<%-- 先把所有的取消选择 --%>
	$("#extlistdiv tbody tr").eq(0).find(":checkbox").prop("checked",false).parents(".g-checkbox").removeClass("on");
	$("#extlistdiv tbody tr").eq(0).siblings().find(":checkbox").prop("checked",false).parents(".g-checkbox").removeClass("on");
	
	<%-- 填入所有数据 --%>
	var otherlist = $("#otherlist").val();
	if(otherlist!=""){
		var exts = otherlist.split(",");
		for(var i=0; i<exts.length; i++){
			$("#extlistdiv tbody :checkbox[value='"+exts[i]+"']").prop("checked",true).parents(".g-checkbox").addClass("on");
		}
	}
	
	extlistdivIndex = layer.open({
		type: 1,
		shade: true,
		title: false, //不显示标题
		move: "#extlistdiv .title",
		scrollbar :false,
		closeBtn :1,
// 		moveOut : true,
		shade:0.5,
		shadeClose :false,
		area:["400px","500px"],
		offset :["140px"],
		content: $("#extlistdiv"), //捕获的元素
		cancel: function(index){
			layer.close(extlistdivIndex);
// 			this.content.hide();

			validExtList();
		}
	});
})

<%-- 校验是否有选择硬件 --%>
function validExtList(){
	var otherlist = $("#otherlist").val();
	if(otherlist==""){
		$("#Other").prop("checked",false).parents(".g-checkbox").removeClass("on");
	}else{
		$("#Other").prop("checked",true).parents(".g-checkbox").addClass("on");
	}
}


<%-- 硬件列表选择事件,点击整行触发事件   --%>
<%--
$(document).off("click","#extlistdiv tbody tr");
$(document).on("click","#extlistdiv tbody tr",function(){
	$(this).find("td").eq(0).find(":checkbox").trigger("click");
});

--%>

<%-- 点击确定按钮 --%>
$("#comfirmExtBtn").on("click",function(){
	<%-- 关闭列表框 --%>
	if(extlistdivIndex){
		layer.close(extlistdivIndex);
	}
	<%-- 将数据保存到隐藏元素中 --%>
	var checkedExtList = [];
	$("#extlistdiv tbody :checkbox:checked").each(function(){
		checkedExtList.push($(this).val());
	});
	if(checkedExtList.length<=0){
		$("#Other").prop("checked",false).parents(".g-checkbox").removeClass("on");
	}else{
		$("#Other").prop("checked",true).parents(".g-checkbox").addClass("on");
	}
	$("#otherlist").val(checkedExtList);
	
	validExtList();
});


<%-- 全选按钮 --%>
$("#selectAllExt").on("click",function(){
	if($(this).prop("checked")){
		<%-- 操作自身元素以及兄弟节点则不需用循环处理 --%>
		$("#extlistdiv tbody tr").eq(0).find(":checkbox").prop("checked",true).parents(".g-checkbox").addClass("on");
		$("#extlistdiv tbody tr").eq(0).siblings().find(":checkbox").prop("checked",true).parents(".g-checkbox").addClass("on");
	}else{
		$("#extlistdiv tbody tr").eq(0).find(":checkbox").prop("checked",false).parents(".g-checkbox").removeClass("on");
		$("#extlistdiv tbody tr").eq(0).siblings().find(":checkbox").prop("checked",false).parents(".g-checkbox").removeClass("on");
	}
})

</script>
</body>
</html>


