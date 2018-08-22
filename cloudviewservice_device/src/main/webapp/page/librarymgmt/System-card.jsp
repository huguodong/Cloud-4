<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
.system-readertype .col1 {
    width: 30px;
    padding-left: 0;
    text-align: center;
}
.system-readertype .col9 {
    width: 100px;
}
</style>
</head>
<body>
<div class="system-readertype">

		<div class="page-title-bar">
			<span class="title">维护卡管理<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/librarymgmt/system-card.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<span class="fl" id="libname">图书馆ID</span>
				<input type="text" name="" id="search_lib_Id" class="input g-input" placeholder="输入关键词" />
				<span class="fl">维护卡ID</span>
				<input type="text" name="" id="search_type_Id" class="input g-input" placeholder="输入关键词" />
				<div class="btn search">查询</div>
				<!-- 修改权限 by huanghuang 20170215 -->
				<shiro:hasPermission name="0102010501">
					<div class="btn increase g-btn-green">新增</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0102010502">
					<div class="btn delete">删除</div>
				</shiro:hasPermission>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="col2">图书馆ID</th>
						<th class="col6">图书馆名</th>
						<th class="col3" style="display:none">类型</th>
						<th class="col4">维护卡号</th>
						<th class="col5">维护类型</th>
						<th class="col9">操作</th>
					</tr>
				</thead>
				<tbody>
				<%--
				<tr>
					<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
					<td class="col2">0001</td>
					<td class="col3">朝阳区图书馆</td>
					<td class="col4">0001</td>
					<td class="col5">物流卡</td>
					<td class="col7"></td>
					<td class="col8">
						<span class="btn-a edit">编辑</span>
						<span class="btn-a delete">删除</span>
					</td>
				</tr>
				 --%>
				</tbody>
			</table>
		</div>
		<%@include file ="../include/page_bar.jsf" %>	
</div>

<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red"></span> 个项目<br>
		是否要删除选择的配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<div id="add-card-div" class="form-dialog-2">
		<div class="title">
		新增维护卡
		</div>
		<div class="form-wrap">
		<div class="item">
			<ul>
				<li class="">
					<div class="left"><span class="g-mustIn">图书馆ID</span></div>
					<div class="right">
						<input type="text" name="lib_id" id="lib_id" class="g-input" placeholder="请输入"  />
						<span class="error-msg">请输入正确的图书馆ID</span>
					</div>
				</li>
				<li class="">
					<div class="left"><span class="g-mustIn">图书馆名称</span></div>
					<div class="right">
						<input type="text" name="lib_name" style="color:gray;background-color:#E0E0E0" readonly="readonly" id="lib_name" class="g-input" placeholder="请输入"  />
						<span class="error-msg"></span>
					</div>
				</li>
				<li>
					<div class="left"><span class="g-mustIn">维护卡类型</span></div>
					<div class="right">
						<div class="g-select">
							<select class="template_group" id="card_type">
								<option value="1" selected>普通维护卡</option>
								<option value="2">读者证做维护卡</option>
							</select>
							<span class="arr-icon"></span>
						</div>
					</div>
				</li>
				<li>
					<div class="left"><span class="g-mustIn">维护卡号</span></div>
					<div class="right">
						<input type="text" name="auth-group-id" id="card_id" class="g-input" placeholder="请输入"  autocomplete="off" />
						<span class="error-msg">维护卡号不能为空</span>
					</div>
				</li>
				<li class="">
					<div class="left"><span class="g-mustIn">维护卡密码</span></div>
					<div class="right">
						<input  type="text" id="card_pwd" class="g-input" placeholder="请输入"  autocomplete="off" />
						<span class="error-msg">维护卡密码不能为空</span>
					</div>
				</li>
			</ul>
		</div>
		<div class="item">
			<ul>
				<li>
					<div class="left">设置权限</div>
					<div class="right">
						<div class="select-area">
							<dl class="choose-area indexPermessions">
								<!--
								 <dd><div class="g-checkbox"><input type="checkbox"></div>系统管理</dd>
							 	-->
							</dl>
							<div class="handle-area">
								<div class="g-btn checkAll">全选</div>
								<div class="g-btn noCheckAll">反选</div>
							</div>
						</div>
						<div class="select-area">
							<dl class="choose-area permessions">
								<!-- 
								<dd><div class="g-checkbox"><input type="checkbox"></div>系统用户管理</dd>
							  	-->
							</dl>
							<div class="handle-area">
								<div class="g-btn checkAll">全选</div>
								<div class="g-btn noCheckAll">反选</div>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</div>
		<input type="submit" value="新增" class="submit g-submit" />
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
	var authorityGroup={};
	authorityGroup.operator = <shiro:principal/>;
	
	/**
		对应用户的图书馆数据
	**/
	var libIdAndNameObj={};
	var libIdxAndNameObj={};
	var masterlibIdxArr=[];
	function GetlibIdAndNameOBJ(param,Page){
		if(!typeof libIdAndNameObj=="undefined"&&!typeof libIdxAndNameObj =="undefined"&&param&&Page){
			SelectReaderType(param,Page);
			return;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/ascconfig/querylibInfoByCurUserEXT1",
			type:"GET",
			data:{}
		}).then(function(data){
			if(data){
				if(data.state && data.result){
					if(data.message=="_MASTER_SLAVE_"){//非云平台管理员 主馆
						var masterAndSlave=data.result;
						var masterLibrary=masterAndSlave.masterLibrary;
						var slaveLibrary=masterAndSlave.slaveLibrary;//arr
						libIdAndNameObj[masterLibrary.lib_id]={"lib_name":masterLibrary.lib_name,"library_idx":masterLibrary.library_idx};
						libIdxAndNameObj[masterLibrary.library_idx]={"lib_id":masterLibrary.lib_id,"lib_name":masterLibrary.lib_name};
						masterlibIdxArr.push(masterLibrary.library_idx);
						for(var i=0;i<slaveLibrary.length;i++){
							libIdAndNameObj[slaveLibrary[i].lib_id]={"lib_name":slaveLibrary[i].lib_name,"library_idx":slaveLibrary[i].library_idx};
							libIdxAndNameObj[slaveLibrary[i].library_idx]={"lib_id":slaveLibrary[i].lib_id,"lib_name":slaveLibrary[i].lib_name};
							masterlibIdxArr.push(slaveLibrary[i].library_idx);
						}
						if(masterlibIdxArr.length>0){
							param.library_idx_arr=masterlibIdxArr;
						}
						if(param&&Page){SelectReaderType(param,Page);}
					}else if(data.message=="_SLAVE_"){//子馆
							libIdAndNameObj[data.result.lib_id]={"lib_name":data.result.lib_name,"library_idx":data.result.library_idx};
							libIdxAndNameObj[data.result.library_idx]={"lib_id":data.result.lib_id,"lib_name":data.result.lib_name};
							masterlibIdxArr.push(data.result.library_idx);
							if(masterlibIdxArr.length>0){
								param.library_idx_arr=masterlibIdxArr;
							}
							if(param&&Page){SelectReaderType(param,Page);}
					}else{
						for(var i=0;i<data.result.length;i++){//云平台用户
							libIdAndNameObj[data.result[i].lib_id]={"lib_name":data.result[i].lib_name,"library_idx":data.result[i].library_idx};
							libIdxAndNameObj[data.result[i].library_idx]={"lib_id":data.result[i].lib_id,"lib_name":data.result[i].lib_name};
							masterlibIdxArr.push(data.result[i].library_idx);
						}
						console.log("masterlibIdxArr",masterlibIdxArr);
						if(param&&Page){SelectReaderType(param,Page);}
					}
					console.log("libIdxAndNameObj",libIdxAndNameObj);
				}
			}
		});
	}
	
	/**
		监听图书馆ID变化 改变对应的图书馆名
	**/
	$("#lib_id").on("keyup",function(){
		var lib_id=$(this).val();
		if(lib_id&&libIdAndNameObj[lib_id]){
			$("#lib_name").val(libIdAndNameObj[lib_id].lib_name);
		}else{
			$("#lib_name").val("");
		}
	});
	
	
	//删除选中的多个分组
	var readertype_idx = new Array();
	var delLayer = null;
	$(".delete").on("click",function(){
		readertype_idx.length = 0;
		var num = $("tbody input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {
			readertype_idx.push($(this).attr("id"));
		});
		if(num >0 ){
			$(".g-delete-dialog .word").html("");
			$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"</span>条数据<br>是否要删除选择的维护卡数据？");
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
				content: $('.g-delete-dialog')
			});	
		}else{
			layer.alert("请选择要删除的数据！");
		}
	});

	//删除当前行的设备分组
	$("tbody").on("click",".delete",function(){
		readertype_idx.length = 0;
		readertype_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		$(".g-delete-dialog").find(".word").html('是否删除 <span class="font-red">'+$(this).parents("tr").find(".col4").html()+'</span>');
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
			content: $('.g-delete-dialog') //捕获的元素
		});
	});
	
	/**
		执行删除按钮操作
	**/
	$(".form-btn.remove").on("click",function(){
		GlobalGLoading();
		var idx = readertype_idx;
		if(!idx)  return;
		var param = new Array();
		for(var i=0;i<idx.length;i++){
			param[i]={
			"maintenance_idx":idx[i]};
		}
		var data={"idx":param,"operator_idx":o.operator_idx,"type":"SYS_CARD_TYPE"};
		
		$.ajax({
			url:"${pageContext.request.contextPath}/maintenance/deleteMaintenanceCard",
			type:"POST",
			data:{"json":JSON.stringify(data)},
			success:function(data){
			 	var param = new Object;
				var currentpage = $(".paging-bar li.active").text();
				var size= $(".g-select.refresh").find("option:selected").text();
				param.type_id = $("#search_type_Id").val();
				var Page={"page":currentpage,"pageSize":size}; 
			    var oper = <shiro:principal/>;
				if(oper.operator_type>=3){
					param.library_idx = oper.library_idx;
				}else{
					param.library_idx = oper.library_idx;
					param.lib_id = $("#search_lib_Id").val();
				}
				if(data.state&&data.message=="ONE"){
					GlobalShowMsg({
						showText:"删除成功",
						status:true
					});
	     			$.select(param,Page);
				}else if(data.retMessage){
					showRetMessage(data.retMessage);
					$.select(param,Page);
				}
				if(delLayer){
					layer.close(delLayer);
				}
			}
		});
	});
	
	//编辑按钮操作
    var readertype = new Array();
	$("tbody").on("click",".edit",function(){
		$(".form-dialog-2 :text").val("");
		var $submit = $(".form-dialog-2 .submit");
		readertype.length = 0;
		readertype.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		var lib_id=$(this).parent().parent().find(".col2").html();
		readertype.push(lib_id);
		readertype.push($(this).parent().parent().find(".col3").html());
		readertype.push($(this).parent().parent().find(".col4").html());
		readertype.push($(this).parent().parent().find(".col5").html());
		readertype.push($(this).parent().parent().find(".col7").html());
		readertype.push($(this).parent().parent().find(".col8").html());
		var opercmds = $.trim($(this).parents("tr").find(".col3").html());
		$("#lib_id").val(readertype[1]);
		//$("#type_distinction").val(readertype[2]);
		$("#card_id").val(readertype[3]);
		$("#card_pwd").val(readertype[5]);
		$("#card_type").val(readertype[6]);
		$("#lib_name").val("");
		$("#version_stamp").val($(this).parent().parent().find(".version_stamp").html());
		if(libIdAndNameObj&&libIdAndNameObj[lib_id]){
			$("#lib_name").val(libIdAndNameObj[lib_id].lib_name);
		}
		
		$submit.attr("name","update");
		
		layerfromOpen=layer.open({
			type: 1, 
			content: $("#add-card-div"),
			title :false,
			closeBtn :1,
			area :["860px","660px"],
			shade:0.5,
			shadeClose :false,
			scrollbar :false,
			move:false,
			skin:false,
			success :function(layero, index){
				$("#add-card-div").find(".title").text("编辑维护卡");
				$("#add-card-div").find(".g-submit").val("保存").removeClass("g-btn-green").addClass("g-btn-blue");
				$("#add-card-div").find(".choose-area.indexPermessions").html("");	
				$("#add-card-div").find(".choose-area.permessions").html("");	
				if(indexPermessions&&permessions){
					//加载一级权限菜单
					var dl='';
					for(var i=0;i<indexPermessionsNoDuplicate.length;i++){
						var pcmd = indexPermessionsNoDuplicate[i].opercmd;<%--前四位一致  --%>
						dl+='<dd><div class="g-checkbox"><input type="checkbox" value="'+pcmd+'"></div>'+indexPermessionsNoDuplicate[i].operbusinesstype+'</dd>';
					}
					$(".form-dialog-2").find(".choose-area.indexPermessions").html(dl);	
				}
				//清空原来的数据
				$("#add-card-div").find("textarea").val("");
				if(authorityGroup.operator.operator_type>=3){<%--图书馆管理员或以下角色--%>
					var library_idx=authorityGroup.operator["library_idx"];
					var lib=libIdxAndNameObj[library_idx];
					if(lib){
						var lib_id=lib.lib_id;
						var lib_name=lib.lib_name;
						$("#add-card-div").find("input[name=lib_id]").val(lib_id);
						$("#add-card-div").find("input[name=lib_name]").val(lib_name);
					}else{
						console.error("library_idx !lib",library_idx);
					}
					$("#add-card-div").find("input[name=lib_id]").prop("readonly","readonly").prop("style","color:gray;background-color:#E0E0E0");
				}else if(authorityGroup.operator.operator_type<3){<%--云平台系统管理员--%>
					$("#add-card-div").find("input[name=lib_id]").attr("placeholder","如果要指定云平台权限组请填 0");
				}
				
				$permessionsArea=$("#add-card-div").find(".choose-area.permessions");
				//根据 idx然后选中左边框
				var cmdArr = [];
				if(opercmds){
					$indexPermessionsArea=$("#add-card-div").find(".choose-area.indexPermessions");
					var meta_opercmd_idx_arr = opercmds.split(",");
					for(var i = 0; i < meta_opercmd_idx_arr.length; i++){
						var pcmd = meta_opercmd_idx_arr[i].substring(0,4);<%-- 前四位一致 --%>
						if($.inArray(pcmd, cmdArr) == -1 ){
							cmdArr.push(pcmd);
						}
					}
					for(var i=0; i<cmdArr.length; i++){
						$indexPermessionsArea.find("input[type=checkbox][value^="+cmdArr[i]+"]").trigger("click");
					}
					for(var i=0;i<meta_opercmd_idx_arr.length;i++){
						$permessionsArea.find("input[type=checkbox][value="+meta_opercmd_idx_arr[i]+"]").trigger("click");
					}
				}
			}
		});
		
		
// 		layerOpen({
// 			"title":"编辑维护卡",
// 			"btnText":"保存",
// 			"btnColorClass":"g-btn-blue",
// 			"name":"update"
// 		});
		if(oper.operator_type>=3){
			$(".form-dialog").find("#lib_id").prop("readonly","readonly").prop("style","color:gray;background-color:#E0E0E0");
		}
	});
	
	/**
 	获取并整理权限的关系
	**/
	var indexPermessions={};
	var indexPermessionsNoDuplicate=new Array();
	var permessions={};
	//一级权限 一对多
	var relPermession=new Object();
	//查询维护卡一级权限，
	$.ajax({
		url:"${pageContext.request.contextPath}/servgroup/selectCardCmdType",
		data:{},
		type:"GET"
	}).then(function(data){
		if(data&&data.state==true){
			
			indexPermessions=data.result;
			//查询权限,仅限登陆用户所拥有的权限，这个operator判断可以放在后台其实
			permessions=authorityGroup.operator["userRolePermessions"];
			if(indexPermessions&&permessions){
				//indexPermessions 会存在重复的内容
				for(var j=0;j<indexPermessions.length;j++){
					if(relPermession[indexPermessions[j].opercmd]){
						continue;
					}
					//因为新的命令码有的是十位数，所以截取的字段需要改变当为8位时，截取4位，当为10位时，截取6位，modify by huanghuang 20170216
					//var indexCmd=indexPermessions[j].opercmd.substr(0,4);
					var indexCmd= 0;
					if(indexPermessions[j].opercmd.length==8){
						indexCmd=indexPermessions[j].opercmd.substr(0,4);
					}else if(indexPermessions[j].opercmd.length==10){
						indexCmd=indexPermessions[j].opercmd.substr(0,6);
					}
					
					var arr=new Array();
					for(var i=0;i<permessions.length;i++){
						//现在数据库中存在的问题是 同一个权限类型下的 权限码不统一前缀 问题如上
						if(indexCmd==permessions[i].opercmd.substr(0,4)){
							arr.push(permessions[i]);
						}
// 						if(indexCmd=="0002"||indexCmd=="0003"||indexCmd=="0004"){//设备端操作权限
// 							arr.push(permessions[i]);
// 						}else{
// 						}
				 	}
				 	//判断一级权限目录 是否包含 所拥有的权限
				 	//只有拥有权限 才会显示该权限的一级权限
				 	if(arr.length>0){
				 		relPermession[indexPermessions[j].opercmd]=arr;
				 		indexPermessionsNoDuplicate.push(indexPermessions[j]);
				 	}
				}
			}
		}
	});
	
	var layerfromOpen=null;
	authorityGroup.increaseDialog={};
	$(".increase").on("click",function(){
		$(".form-dialog-2 :text").val("");
		var $submit = $(".form-dialog-2 .submit");
		$submit.attr("name","add");
		layerfromOpen=layer.open({
			type: 1, 
			content: $("#add-card-div"),
			title :false,
			closeBtn :1,
			area :["860px","660px"],
			shade:0.5,
			shadeClose :false,
			scrollbar :false,
			move:false,
			skin:false,
			success :function(layero, index){
				$("#add-card-div").find(".title").text("新增维护卡");
				$("#add-card-div").find(".g-submit").val("保存").removeClass("g-btn-green").addClass("g-btn-blue");
				$("#add-card-div").find(".choose-area.indexPermessions").html("");	
				$("#add-card-div").find(".choose-area.permessions").html("");	
				if(indexPermessions&&permessions){
					//加载一级权限菜单
					var dl='';
					for(var i=0;i<indexPermessionsNoDuplicate.length;i++){
						dl+='<dd><div class="g-checkbox"><input type="checkbox" value="'+indexPermessionsNoDuplicate[i].opercmd+'"></div>'+indexPermessionsNoDuplicate[i].operbusinesstype+'</dd>';
					}
					$(".form-dialog-2").find(".choose-area.indexPermessions").html(dl);	
				}
				//清空原来的数据
				$("#add-card-div").find(":text").val("");
				$("#add-card-div").find("textarea").val("");
				if(authorityGroup.operator.operator_type>=3){<%--图书馆管理员或以下角色--%>
					var library_idx=authorityGroup.operator["library_idx"];
					var lib=libIdxAndNameObj[library_idx];
					if(lib){
						var lib_id=lib.lib_id;
						var lib_name=lib.lib_name;
						$("#add-card-div").find("input[name=lib_id]").val(lib_id);
						$("#add-card-div").find("input[name=lib_name]").val(lib_name);
					}else{
						console.error("library_idx !lib",library_idx);
					}
					$("#add-card-div").find("input[name=lib_id]").prop("readonly","readonly").prop("style","color:gray;background-color:#E0E0E0");
				}else if(authorityGroup.operator.operator_type<3){<%--云平台系统管理员--%>
					$("#add-card-div").find("input[name=lib_id]").attr("placeholder","如果要指定云平台权限组请填 0");
				}
			}
		});
// 		layerOpen({
// 			"title":"新增维护卡",
// 			"btnText":"保存",
// 			"btnColorClass":"g-btn-blue",
// 			"name":"add"
// 		});
// 		if(oper.operator_type>=3){
// 			$(".form-dialog").find("#lib_id").prop("readonly","readonly").prop("style","color:gray;background-color:#E0E0E0");
// 			$("#lib_id").val(libIdxAndNameObj[oper.library_idx].lib_id);
// 			$("#lib_name").val(libIdxAndNameObj[oper.library_idx].lib_name);
// 		}

	});
	
	function layerOpen(options){
		var defaults = {
			title:"",
			btnText : "新增"
		};
		options = $.extend(defaults,options);

		var $submit = $(".form-dialog .submit");
		$(".form-dialog .title").text(options.title);
		$submit.val(options.btnText);
		$submit.removeClass("g-btn-green").removeClass(".g-btn-blue");
		$submit.addClass(options.btnColorClass);
		$submit.attr("name",options.name);
		$(".form-dialog").find("li").removeClass("error");
		$(".form-dialog").find("#lib_id").prop("readonly","").prop("style","");

		layerfromOpen=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area :["860px","660px"],
			content: $('#add-card-div')
		});
	}

	//数据检查
	var checkInc=function(incCheck){
		if(incCheck){
			var lib_id=$("#lib_id").val();
			//var type_distinction=$("#type_distinction").val();
			var type_id=$("#card_id").val();
			var type_name=$("#card_pwd").val();

			if(!lib_id || lib_id.length>20||!libIdAndNameObj[lib_id]){
				$("#lib_id").parents("li").addClass("error");
				dataCheck=false;
			}
			/*if(type_distinction == -1){
				$("#type_distinction").parents("li").addClass("error");
				dataCheck=false;
			} */
			if(!type_id){
				$("#card_id").parents("li").addClass("error").find("span.error-msg").html("卡号不能为空");
				dataCheck=false;
			}
			if(type_id.length>50){
				$("#card_pwd").parents("li").addClass("error").find("span.error-msg").html("不能大于50个字符");
				dataCheck=false;
			}
			var reg=/[A-Za-z0-9]+/;
			if(!reg.test(type_id)){
				$("#card_id").parents("li").addClass("error").find("span.error-msg").html("只能输入英文或者数字");
				dataCheck=false;
			}
			if(!type_name || type_name.length>50){
				$("#card_pwd").parents("li").addClass("error");
				dataCheck=false;
			}
		}
		return dataCheck;
	};
	
	$(".form-dialog-2 .submit").on("click",function(){
		dataCheck=true;
		if(checkInc(dataCheck) == false) return;
		var lib_id=$("#lib_id").val();
		//var type_distinction=$("#type_distinction").val();
		var card_id=$("#card_id").val();
		var card_pwd=$("#card_pwd").val();
		var card_type=$("#card_type").val();
		var version_stamp=$("#version_stamp").val();
		
		
		<%-- 获取权限代码 --%>
		var servIdxArr=$("#add-card-div").find(".choose-area.permessions").find("input[type=checkbox]:checked");
		var idxArrStr="";
		if(servIdxArr){
			for(var i=0;i<servIdxArr.length;i++){
				idxArrStr+=$(servIdxArr[i]).val()+",";
			}
			idxArrStr=idxArrStr.substring(0, idxArrStr.length-1);
		}
		if(!idxArrStr||$.trim(idxArrStr)==""){
			layer.alert("请设置权限");
			return;
		}
		
		var param = {
						"lib_id":lib_id,
						"card_type":card_type,
						"card_id":card_id,
						"card_pwd":card_pwd,
						"opercmds":idxArrStr
// 						"version_stamp":version_stamp
					};
		//var name = $(this).val();
		var name = $(this).attr("name");
		if(name == "update"){
			param.maintenance_idx = readertype[0];
			var data = {
				"operator_idx":o.operator_idx,
				"readertype":param,
			};
			$.ajax({
				url:"${pageContext.request.contextPath}/maintenance/updateMaintenanceCard",
				type:"POST",
				data:{"json":JSON.stringify(data)},
				success:function(data){
					if(data.state){
						var info = new Object;
					 	var currentpage = $(".paging-bar li.active").text();
						var size= $(".g-select.refresh").find("option:selected").text();
						info.type_id = $("#search_type_Id").val();
						var Page={"page":currentpage,"pageSize":size}; 
					 	var oper = <shiro:principal/>;
						if(oper.operator_type>=3){
							info.library_idx = oper.library_idx;
						}else{
							info.lib_id = $("#search_lib_Id").val();
						}
						$.select(info,Page);
						GlobalShowMsg({
							showText:"修改成功",
							status:true
						});
						if(layerfromOpen){
		        			layer.close(layerfromOpen);
		        		}
					}else if(data.retMessage){
		        	 	showRetMessage(data.retMessage);
		        	}
			  }
			});	
		}
		
		if(name == "add"){
// 			param.type_deposit = 0;
// 			param.card_fee = 0;
// 			param.verification_fee = 0;
			var data = {
				"operator_idx":o.operator_idx,
				"readertype":param
			};
			$.ajax({
				 url:"${pageContext.request.contextPath}/maintenance/insertMaintenanceCard",
			 	 type:"POST",
			 	 data:{"json":JSON.stringify(data)},
			     success:function(data){
					 if(data.state){
					 	var info = new Object;
					 	var currentpage = $(".paging-bar li.active").text();
						var size= $(".g-select.refresh").find("option:selected").text();
						info.type_id = $("#search_type_Id").val();
						var Page={"page":currentpage,"pageSize":size}; 
					 	var oper = <shiro:principal/>;
						if(oper.operator_type>=3){
							info.library_idx = oper.library_idx;
						}else{
							info.lib_id = $("#search_lib_Id").val();
						}
						GlobalShowMsg({
							showText:"新增成功",
							status:true
						});
						$.select(info,Page);
						if(layerfromOpen){
			        		layer.close(layerfromOpen);
			        	}
		        	 }else if(data.retMessage){
		        	 	showRetMessage(data.retMessage);
		        	 }
	        	 }
			});
		}
	});
	
	
	
	$(":text").focus(function(){
		$(this).parents("li").removeClass("error");
	});
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	/**
		查询按钮操作
	**/
	$(".search").on("click",function(){
		var param = {};
		var size= $(".g-select.refresh").find("option:selected").text();
		param.card_id = $("#search_type_Id").val();
		var Page={"page":"1","pageSize":size,}; 
		var oper = <shiro:principal/>;
		if(oper.operator_type >=3){
			param.library_idx = oper.library_idx;
		}else{
			param.library_idx = oper.library_idx;
			param.lib_id = $("#search_lib_Id").val();
		}
		$.select(param,Page);
	});
	var oper = <shiro:principal/>;
	jQuery.select=function (param,Page){
		$("tbody").html("");
		//if(oper.operator_type>=3)
		param.library_idx = [oper.library_idx];
		console.log("masterlibIdxArr",masterlibIdxArr);
		if(masterlibIdxArr.length>0){
			param.library_idx_arr=masterlibIdxArr;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/maintenance/queryMaintenanceCardByFuzzy",
			type:"POST",
			data:{"json":JSON.stringify(param),"page":JSON.stringify(Page)},
			success:function(data){
				$("tbody").html("");
			if(data.state){
					if(data.result.rows == null){
						var index = Page.page;
						if(index >1){
							Page.page = index-1;
							$.select(param,Page);
						}		
					}else{
						$.each(data.result.rows,function(i,item){
							var library_idx=item.lib_idx;
							var libInfo=libIdxAndNameObj[library_idx];
							if(typeof libInfo=="undefined"){
								libInfo={'lib_id':''};
							}
							var cardType = "";
							if(item.card_type=="1"){
								cardType = "普通维护卡";
							}
							if(item.card_type=="2"){
								cardType = "读者证做为维护卡";
							}
							$("tbody").append(
							"<tr>"+
			            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.maintenance_idx+"' /></div></td>" +
			           		"<td class='col2'>"+libInfo['lib_id'] +"</td>" +
			           		"<td class='col6'>"+libInfo['lib_name'] +"</td>" +
			           		"<td class='col3' style='display:none'>" + item.opercmds + "</td>" +
			                "<td class='col4'>" + item.card_id + "</td>" +
			                "<td class='col5'>" + cardType + "</td>"+
			           		"<td class='col7' style='display:none'>" + item.card_pwd + "</td>" +
			           		"<td class='col8' style='display:none'>" + item.card_type + "</td>" +
			                "<td class='version_stamp' style='display:none;'>" + item.version_stamp + "</td>"+
			                "<td class='col9'><span class='btn-a edit'>编辑</span>"+
			                "<span class='btn-a delete'>删除</span></td>" +
			                "</tr>");
			                });
					}
					var t=0;
					<shiro:lacksPermission name="0102010502">
						t++;
						$(".delete").attr("style","display:none;");
	    			</shiro:lacksPermission>
	    			<shiro:lacksPermission name="0102010503">
						t++;
						$(".edit").attr("style","display:none;");
	    			</shiro:lacksPermission>
	    			if(t==2){
	    				$(".col9").attr("style","display:none;");
	    			}
	    			$.pagination(data.result);     
			    }
			}
		});
	};
	function SelectReaderType(param,Page){
		$.ajax({
			url:"${pageContext.request.contextPath}/maintenance/queryMaintenanceCardByFuzzy",
			type:"POST",
			data:{"json":JSON.stringify(param),
				"page":JSON.stringify(Page)},
				success:function(data){
				$("tbody").html("");
				if(data.result.rows){
					console.log("data.result.rows",data.result.rows);
				}
				$.each(data.result.rows,function(i,item){
					var library_idx=item.lib_idx;
					var libInfo=libIdxAndNameObj[library_idx];
					if(typeof libInfo=="undefined"){
						libInfo={'lib_id':''};
					}
					var cardType = "";
					if(item.card_type=="1"){
						cardType = "普通维护卡";
					}
					if(item.card_type=="2"){
						cardType = "读者证做为维护卡";
					}
					$("tbody").append(
					"<tr>"+
	            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.maintenance_idx+"' /></div></td>" +
	           		"<td class='col2'>"+libInfo['lib_id'] +"</td>" +
	           		"<td class='col6'>"+libInfo['lib_name'] +"</td>" +
	           		"<td class='col3' style='display:none'>" + item.opercmds + "</td>" +
	                "<td class='col4'>" + item.card_id + "</td>" +
	                "<td class='col5'>" + cardType + "</td>"+
	           		"<td class='col7' style='display:none'>" + item.card_pwd + "</td>" +
	           		"<td class='col8' style='display:none'>" + item.card_type + "</td>" +
	                "<td class='version_stamp' style='display:none;'>" + item.version_stamp + "</td>"+
	                "<td class='col9'><span class='btn-a edit'>编辑</span>"+
	                "<span class='btn-a delete'>删除</span></td>" +
	                "</tr>");
	                });
				var t=0;
				<shiro:lacksPermission name="0102010501">
					$(".increase").attr("style","display:none;");
	   			</shiro:lacksPermission>
	   			<shiro:lacksPermission name="0102010502">
	   			 	t++;
					$(".delete").attr("style","display:none;");
	    		</shiro:lacksPermission>
	    		<shiro:lacksPermission name="0102010503">
	    			t++;
					$(".edit").attr("style","display:none;");
	    		</shiro:lacksPermission>
	    		if(t==2){
	    			$(".col9").attr("style","display:none;");
	    		}
	    		$.pagination(data.result);
			}
			});
	}
	$(document).ready(function(){
		var oper = <shiro:principal/>;
		var library_idx = oper.library_idx;
		if(oper.operator_type >=3){
			$("#libname").attr("style","display:none;");
			$("#search_lib_Id").attr("style","display:none;");
		}
		//获取当前页数
		var size= $(".g-select.refresh").find("option:selected").html();
		var Page={	"page":"1","pageSize":size};
		var param ={
				"library_idx":library_idx
			};
	
		GetlibIdAndNameOBJ(param,Page);
	});
	$("#page").on("click",".prev-page",function(){
		var param = new Object;
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size= $(".g-select.refresh").find("option:selected").html();
		param.type_id = $("#search_type_Id").val();
		var Page={"page":page,"pageSize":size}; 
	 	var oper = <shiro:principal/>;
		if(oper.operator_type>=3){
			param.library_idx = oper.library_idx;
		}else{
			param.library_idx = oper.library_idx;
			param.lib_id = $("#search_lib_Id").val();
		}
		$.select(param,Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var param = new Object;
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $(".g-select.refresh").find("option:selected").html();
		param.type_id = $("#search_type_Id").val();
		var Page={"page":page,"pageSize":size,}; 
	 	var oper = <shiro:principal/>;
		if(oper.operator_type>=3){
			param.library_idx = oper.library_idx;
		}else{
			param.library_idx = oper.library_idx;
			param.lib_id = $("#search_lib_Id").val();
		}
		$.select(param,Page);
	});
	
	$("#page").on("click","li",function(){
		var param = new Object;
		if($(this).hasClass("active"))
			return;
		var size= $(".g-select.refresh").find("option:selected").html();
		var page = $(this).html();
		if(page=="...") return;
		param.type_id = $("#search_type_Id").val();
		var Page={"page":page,"pageSize":size}; 
	 	var oper = <shiro:principal/>;
		if(oper.operator_type>=3){
			param.library_idx = oper.library_idx;
		}else{
			param.library_idx = oper.library_idx;
			param.lib_id = $("#search_lib_Id").val();	
		}
		$.select(param,Page);
	}); 
	/**
		切换每页显示多少行
	**/
	$("div.paging-bar").on("change","#showSize",function(){
		var param = new Object;
		var size= $(".g-select.refresh").find("option:selected").html();
		var page = 1;
		param.type_id = $("#search_type_Id").val();
		var oper = <shiro:principal/>;
		if(oper.operator_type>=3){
			param.library_idx = oper.library_idx;
		}else{
			param.library_idx = oper.library_idx;
			param.lib_id = $("#search_lib_Id").val();	
		}
		var Page={"page":page,"pageSize":size}; 
		$.select(param,Page);
	});
	
	/**
		权限INDEX  全选反选 效果
	**/
	$(".indexPermessions").next().find(".checkAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,true,"indexPermessions");
	});
	$(".indexPermessions").next().find(".noCheckAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,false,"indexPermessions");
	});
	$(".permessions").next().find(".checkAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,true,"");
	});
	$(".permessions").next().find(".noCheckAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,false,"");
	});
	
	/**
		权限 全选反选操作
	**/
	function allInputEach(container,toChecked,type){
		var $container = container || $(document);
		if(type=="indexPermessions"){
			if(toChecked){
				$container.find("input[type='checkbox']").each(function(){
					if(!$(this).is(":checked")){
						$(this).trigger("click");
					}
				});
			}else{
				$container.find("input[type='checkbox']").each(function(){		
					$(this).trigger("click");
				});
			}
		}else{
			if(toChecked){
				$container.find("input[type='checkbox']").each(function(){
					if(!$(this).is(":checked")){
						$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
					}
				});
			}else{
				$container.find("input[type='checkbox']").each(function(){		
					if(!$(this).is(":checked")){
						$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
					}else{
						$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
					}
				});
			}
		}
	}
	
	/**
		新增权限组框
	 	点击左边选项事件，增加右边的权限显示
	**/
	$("#add-card-div").find(".choose-area.indexPermessions").on("click",".g-checkbox",function(){
			var opercmd=$(this).find("input[type=checkbox]").val();
			var permeArr=relPermession[opercmd];
			if($(this).find("input[type=checkbox]").is(":checked")){
				var dl='<dl class='+opercmd+'>';//opercmd 作为标记,用作删除
				for(var i=0;i<permeArr.length;i++){
					dl+='<dd><div class="g-checkbox"><input type="checkbox" value="'+permeArr[i].opercmd+'"></div>'+permeArr[i].opercmd_desc+'</dd>';
				}
				dl+='</dl>';
				//加载权限列表
				$("#add-card-div").find(".choose-area.permessions").append(dl);
			}
			if(!$(this).find("input[type=checkbox]").is(":checked")){
				$("#add-card-div").find(".choose-area.permessions").find("dl."+opercmd).remove();
			}
	});
	
	
});
</script>
</body>
</html>


