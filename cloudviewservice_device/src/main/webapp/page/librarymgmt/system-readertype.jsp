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
.system-readertype .col4 {
    width: 250px;
}
.system-readertype .col5 {
    width: 250px;
}
.system-readertype .col6 {
    width: 80px;
}
.system-readertype .col7 {
    width: 80px;
}
.system-readertype .col8 {
    width: 80px;
}
.system-readertype .col9 {
    width: 150px;
}
</style>
</head>
<body>
<div class="system-readertype">
		<div class="page-title-bar">
			<span class="title">读者流通类型管理<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/librarymgmt/system-readertype.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<span class="fl" id="libname">图书馆ID</span>
				<input type="text" name="" id="search_lib_Id" class="input g-input" placeholder="输入关键词" />
				<span class="fl">流通类型ID</span>
				<input type="text" name="" id="search_type_Id" class="input g-input" placeholder="输入关键词" />
				<div class="btn search">查询</div>
				<!-- 修改权限 by huanghuang 20170215 -->
				<shiro:hasPermission name="0102010401">
					<div class="btn increase g-btn-green">新增</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0102010402">
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
						<th class="col3" style="display:none">类型</th>
						<th class="col4">流通类型ID</th>
						<th class="col5">流通类型名</th>
						<th class="col6">押金</th>
						<th class="col7">工本费</th>
						<th class="col8">验证费</th>
						<th class="col9">操作</th>
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
						<option value="30">30</option>
						<option value="60">60</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<span class="t2 fl " id="ChooseNum">已选中<span class="total-choosen-num">0</span>个</span>
			</div>
			<div class="right" id="page">
			</div>
			<span class="total-page fr"></span>
		</div>
	</div>
<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		当前选择了<span class="font-red"></span>个项目<br>
		是否要删除选择的类型信息？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<div class="form-dialog">
	<div class="title">新增分组</div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left">图书馆ID</div>
				<div class="right">
					<input type="text" name="" id="lib_id" class="g-input" placeholder="请输入"  />
					<span class="error-msg">请输入正确的图书馆ID</span>
				</div>
			</li>
			<li>
				<div class="left">图书馆</div>
				<div class="right">
					<input type="text" name="" style="color:gray;background-color:#E0E0E0" readonly="readonly" id="lib_name" class="g-input" placeholder="图书馆" />
				</div>
			</li>
			<!-- <li>
				<div class="left">类型</div>
				<div class="right">
					<div class="g-select">
						<select id="type_distinction">
							<option value="-1" selected>选择类型</option>
							<option value="1">读者卡</option>
							<option value="2">维护卡</option>
						</select><span class="arr-icon"></span>
						<span class="error-msg">该字段不能为空</span>
					</div>
				</div>
			</li> -->
			<li>
				<div class="left">流通类型ID</div>
				<div class="right">
					<input type="text" name="" id="type_id" class="g-input" placeholder="请输入"  />
					<span class="error-msg">请填写正确的流通类型ID</span>
				</div>
			</li>
			<li>
				<div class="left">流通类型名</div>
				<div class="right">
					<input type="text" name="" id="type_name" class="g-input" placeholder="请输入"  />
					<span class="error-msg">请填写正确的流通类型名</span>
				</div>
			</li>
			<li class="short-input">
				<div class="left">押金</div>
				<div class="right">
					<input type="text" name="" id="type_deposit" class="g-input" placeholder="请输入"  />元
					<span class="error-msg">请填写正确数值</span>
				</div>
			</li>
			<li class="short-input">
				<div class="left">工本费</div>
				<div class="right">
					<input type="text" name="" id="card_fee" class="g-input" placeholder="请输入"  />元
					<span class="error-msg">请填写正确数值</span>
				</div>
			</li>
			<li>
			<li class="short-input">
				<div class="left">验证费</div>
				<div class="right">
					<input type="text" name="" id="verification_fee" class="g-input" placeholder="请输入"  />元
					<input type="hidden" name="" id="version_stamp" class="g-input"/>
					<span class="error-msg">请填写正确数值</span>
				</div>
			</li>
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input type="submit" name="" value="保存" class="submit g-btn-blue" />
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
	var oper = <shiro:principal/>;
	var dataCheck=false;
	//删除选中的多个设备分组
	var readertype_idx = new Array();
	$(".delete").on("click",function(){
		readertype_idx.length = 0;
		var num = $("tbody input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {
			readertype_idx.push($(this).attr("id"));
		});
		if(num >0 ){
			$(".g-delete-dialog .word").html("");
			$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"</span>条数据<br>是否要删除选择的读者流通类型数据？");
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
		//$('.word').html("");
		//$('.word').append("是否要删除当前行的数据？");
		$(".g-delete-dialog").find(".word").html(
		'是否删除 <span class="font-red">'+$(this).parents("tr").find(".col4").html()+'</span>');
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
			param[i]={"type_idx":idx[i]};
		}
		var data={"idx":param,"operator_idx":o.operator_idx};
		
		$.ajax({
			url:basePath+"/ReaderType/DeleteReaderType",
			type:"POST",
			data:{"json":JSON.stringify(data)},
			success:function(data){
			if(data.state){
				var info = new Object;
			 	var currentpage = $(".paging-bar li.active").text();
				var size= $('#pagesize option:selected').text();
				info.type_id = $("#search_type_Id").val();
				var Page={"page":currentpage,"pageSize":size}; 
			 	var oper = <shiro:principal/>;
				if(oper.operator_type>=3){
					info.library_idx = oper.library_idx;
				}else{
					info.lib_id = $("#search_lib_Id").val();
				}
				$.select(info,Page);
     			//$(".layui-layer-shade").click();
     			if(delLayer){
     				layer.close(delLayer);
     			}
     			GlobalShowMsg({showText:"删除成功",status:true});
			}else{
				if(delLayer){
     				layer.close(delLayer);
     			}
				showRetMessage(data.retMessage);
			}
			}
		});
	});
	
	/* //动态增加的表格中的 复选框
	$("tbody").on("change",":checkbox",function(){
		$(this).parents(".g-checkbox").toggleClass("on");
		var num = $("tbody Input[name='idx']:checked").length;
		$("#num").html("已选中"+num+"个");
    }); */
    /**
		对应用户的图书馆数据
	**/
	var libIdAndNameObj={};
	var libIdxAndNameObj={};
	var masterlibIdxArr=[];
	function GetlibIdxAndNameOBJ(callback){
		if(!libIdAndNameObj||!libIdxAndNameObj){
			if(typeof callback=="function"){
				callback();
			}
			return;
		}
	   $.ajax({
			url:"${pageContext.request.contextPath}/ascconfig/querylibInfoByCurUserEXT1",
			type:"GET",
			data:{}
		}).then(function(data){
			if(data){
				console.log("获取到图书馆信息",data);
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
						//if(masterlibIdxArr.length>0){
							//param.library_idx=masterlibIdxArr;
						//}
						//if(param&&Page){SelectReaderType(param,Page);}
					}else if(data.message=="_SLAVE_"){//子馆
							libIdAndNameObj[data.result.lib_id]={"lib_name":data.result.lib_name,"library_idx":data.result.library_idx};
							libIdxAndNameObj[data.result.library_idx]={"lib_id":data.result.lib_id,"lib_name":data.result.lib_name};
							masterlibIdxArr.push(data.result.library_idx);
							//if(masterlibIdxArr.length>0){
								//param.library_idx=masterlibIdxArr;
							//}
							//if(param&&Page){SelectReaderType(param,Page);}
					}else{
						for(var i=0;i<data.result.length;i++){//云平台用户
							libIdAndNameObj[data.result[i].lib_id]={"lib_name":data.result[i].lib_name,"library_idx":data.result[i].library_idx};
							libIdxAndNameObj[data.result[i].library_idx]={"lib_id":data.result[i].lib_id,"lib_name":data.result[i].lib_name};
							masterlibIdxArr.push(data.result[i].library_idx);
						}
						//if(param&&Page){SelectReaderType(param,Page);}
					}
					if(typeof callback=="function"){
						callback();
					}
					console.log("masterlibIdxArr",masterlibIdxArr);
					console.log("libIdAndNameObj",libIdAndNameObj);
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
    /**
    	编辑按钮操作
    **/
    var readertype = new Array();
	$("tbody").on("click",".edit",function(){
		$(".form-dialog :text").val("");
		readertype.length = 0;
		readertype.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		var lib_id=$(this).parent().parent().find(".col2").text();
		readertype.push(lib_id);
		readertype.push($(this).parent().parent().find(".col3").text());
		readertype.push($(this).parent().parent().find(".col4").text());
		readertype.push($(this).parent().parent().find(".col5").text());
		readertype.push($(this).parent().parent().find(".col6").text());
		readertype.push($(this).parent().parent().find(".col7").text());
		readertype.push($(this).parent().parent().find(".col8").text());
		$("#lib_id").val(readertype[1]);
		//$("#type_distinction").val(readertype[2]);
		$("#type_id").val(readertype[3]);
		$("#type_name").val(readertype[4]);
		$("#type_deposit").val(readertype[5]);
		$("#card_fee").val(readertype[6]);
		$("#verification_fee").val(readertype[7]);
		$("#lib_name").val("");
		$("#version_stamp").val($(this).parent().parent().find(".version_stamp").text());
		if(libIdAndNameObj&&libIdAndNameObj[lib_id]){
			$("#lib_name").val(libIdAndNameObj[lib_id].lib_name);
		}
		layerOpen({
			"title":"编辑读者流通类型",
			"btnText":"保存",
			"btnColorClass":"g-btn-blue",
			"operType":"update"
		});
		var oper = <shiro:principal/>;
		if(oper.operator_type >=3){
			$(".form-dialog").find("#lib_id").prop("readnoly","readnoly").prop("style","color:gray;background-color:#E0E0E0");
		}
	});
	$(".increase").on("click",function(){
		$(".form-dialog :text").val("");
		$("#reader_type").val("-1");
		layerOpen({
			"title":"新增读者流通类型",
			"btnText":"保存",
			"btnColorClass":"g-btn-blue",
			"operType":"add"
		});
		var oper = <shiro:principal/>;
		if(oper.operator_type >=3){
			$(".form-dialog").find("#lib_id").prop("readnoly","readnoly").prop("style","color:gray;background-color:#E0E0E0");
			$(".form-dialog").find("#lib_id").val(libIdxAndNameObj[oper.library_idx].lib_id);
			$(".form-dialog").find("#lib_name").val(libIdxAndNameObj[oper.library_idx].lib_name);
			$(".form-dialog").find("#lib_name").val(libIdxAndNameObj[oper.library_idx].lib_name);
		}
	});
	var layerfromOpen=null;
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
		$submit.attr("name",options.operType);
		$(".form-dialog").find("#lib_id").prop("readnoly","").prop("style","");
		RemoveErrorClass();
		layerfromOpen=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["600px"],
			offset :["130px"],
			content: $('.form-dialog')
		});
	}
	
	//数据检查
	var checkInc=function(incCheck){
		if(incCheck){
			var lib_id=$("#lib_id").val();
			//var type_distinction=$("#type_distinction").val();
			var type_id=$("#type_id").val();
			var type_name=$("#type_name").val();
			var type_deposit=$("#type_deposit").val();
			var card_fee=$("#card_fee").val();
			var verification_fee=$("#verification_fee").val();
			var len_libid =$("#lib_id").val().length;
			var len_typeid=$("#type_id").val().length;
			var len_name=$("#type_name").val().length;
			var len_deposit =$("#type_deposit").val().length;
			var len_cardfee=$("#card_fee").val().length;
			var len_verification=$("#verification_fee").val().length;
			if(!lib_id || len_libid>20||!libIdAndNameObj[lib_id]){
				$("#lib_id").parent().parent().addClass("error");
				dataCheck=false;
			}
			/* if(type_distinction == -1){
				$("#type_distinction").parent().parent().parent().addClass("error");
				dataCheck=false;
			} */
			if(!type_id || len_typeid>50){
				$("#type_id").parent().parent().addClass("error");
				dataCheck=false;
			}
			if(!type_name || len_name>50){
				$("#type_name").parent().parent().addClass("error");
				dataCheck=false;
			}
			if(!$.isNumeric(type_deposit) || len_deposit >11 || !type_deposit){
				$("#type_deposit").parent().parent().addClass("error");
				dataCheck=false;
			}
			if(!$.isNumeric(card_fee) || len_cardfee>11 || !card_fee){
				$("#card_fee").parent().parent().addClass("error");
				dataCheck=false;
			}
			if(!$.isNumeric(verification_fee) || len_verification>11 || !verification_fee){
				$("#verification_fee").parent().parent().addClass("error");
				dataCheck=false;
			}
		}
		return dataCheck;
	};
	
	function RemoveErrorClass(){
		$("#lib_id").parent().parent().removeClass("error");
		$("#type_id").parent().parent().removeClass("error");
		$("#type_name").parent().parent().removeClass("error");
		$("#type_deposit").parent().parent().removeClass("error");
		$("#card_fee").parent().parent().removeClass("error");
		$("#verification_fee").parent().parent().removeClass("error");
	}
	
	$(".form-dialog .submit").on("click",function(){
		GlobalGLoading();
		dataCheck=true;
		if(checkInc(dataCheck) == false) return;
		var lib_id=$("#lib_id").val();
		//var type_distinction=$("#type_distinction").val();
		var type_id=$("#type_id").val();
		var type_name=$("#type_name").val();
		var type_deposit=$("#type_deposit").val();
		var card_fee=$("#card_fee").val();
		var verification_fee=$("#verification_fee").val();
		var version_stamp=$("#version_stamp").val();
		var param = 
				{"lib_id":lib_id,
				"type_distinction":"1",
				"type_id":type_id,
				"type_name":type_name,
				"type_deposit":type_deposit,
				"card_fee":card_fee,
				"verification_fee":verification_fee,
				"version_stamp":version_stamp
				};
		//var name = $(this).val();
		var name=$(this).attr("name");
		if(name == "update"){
			param.type_idx = readertype[0];
			var data = {
				"operator_idx":o.operator_idx,
				"readertype":param,
			};
			$.ajax({
			url:basePath+"/ReaderType/UpdateReaderType",
			type:"POST",
			data:{"json":JSON.stringify(data)},
			success:function(data){
				console.log("UpdateReaderType",data);
				if(data.state){
					var info = new Object;
					 	var currentpage = $(".paging-bar li.active").text();
						var size= $('#pagesize option:selected').text();
						info.type_id = $("#search_type_Id").val();
						var Page={"page":currentpage,"pageSize":size}; 
					 	var oper = <shiro:principal/>;
						if(oper.operator_type>=3){
							info.library_idx = oper.library_idx;
						}else{
							info.lib_id = $("#search_lib_Id").val();
						}
						$.select(info,Page);
					GlobalShowMsg({showText:"更新成功",status:true});
					if(layerfromOpen){
						layer.close(layerfromOpen);
					}
				}else if(data.message){
		        	layer.alert(data.retMessage);
		        }
			}
		  });	
		}
		if(name == "add"){
			var data = {"operator_idx":o.operator_idx,"readertype":param};
			$.ajax({
				 url:basePath+"/ReaderType/InsertReaderType",
			 	 type:"POST",
			 	 data:{"json":JSON.stringify(data)},
			     success:function(data){
			    	 console.log("InsertReaderType",data);
					 if(data.state){
					 	var info = new Object;
					 	var currentpage = $(".paging-bar li.active").text();
						var size= $('#pagesize option:selected').text();
						info.type_id = $("#search_type_Id").val();
						var Page={"page":currentpage,"pageSize":size}; 
					 	var oper = <shiro:principal/>;
						if(oper.operator_type>=3){
							info.library_idx = oper.library_idx;
						}else{
							info.lib_id = $("#search_lib_Id").val();
						}
						$.select(info,Page);
						GlobalShowMsg({showText:"新增成功",status:true});
						if(layerfromOpen){
							layer.close(layerfromOpen);
						}
		        	 }else if(data.message=="CHECK_FALSE"){
		        	 	layer.alert(data.retMessage);
		        	 }
	        	   }
			});
		}
	});

	$(":text").focus(function(){
		$(this).parent().parent().removeClass("error");
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
		var param = new Object;
		var size= $('#pagesize option:selected').text();
		param.type_id = $("#search_type_Id").val();
		var Page={"page":"1","pageSize":size}; 
		if(oper.operator_type >=3){
			param.library_idx = oper.library_idx;
		}else{
			param.lib_id = $("#search_lib_Id").val();
		}
		$.select(param,Page);
	});
	jQuery.select=function (param,Page){
		GetlibIdxAndNameOBJ(function(){
			param.library_idx = [oper.library_idx];
			if(masterlibIdxArr.length>0){
				param.library_idx_arr=masterlibIdxArr;
			}
			param.type_distinction = 1;
			$.ajax({
				url:basePath+"/ReaderType/QueryReaderTypeByFuzzy",
				type:"POST",
				data:{"json":JSON.stringify(param),"page":JSON.stringify(Page)},
				success:function(data){
					//console.log("jQuery.select",data);
					$("tbody").html("");
					if(data.state){
						if(data.result.rows.length==0&&Page.page!=1){
							var index = Page.page;
							if(index >1){
								Page.page = index-1;
								$.select(param,Page);
								return;
							}		
						}else{
							$.each(data.result.rows,function(i,item){
								$("tbody").append(
									"<tr>"+
				       	    	 	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.type_idx+"' /></div></td>" +
				           			"<td class='col2'>"	+ libIdxAndNameObj[item.library_idx].lib_id+"</td>" +//library_idx
				          	 		"<td class='col3' style='display:none'>" + item.type_distinction+"</td>" +
				          		    "<td class='col4'>" + item.type_id + "</td>" +
				          	    	"<td class='col5'>" + item.type_name + "</td>"+
				          	        "<td class='col6'>" + item.type_deposit + "</td>"+
				          	        "<td class='col7'>" + item.card_fee + "</td>"+
				          	        "<td class='col8'>" + item.verification_fee + "</td>"+
				          	        "<td class='version_stamp' style='display:none;'>" + item.version_stamp + "</td>"+
				           	        "<td class='col9'><span class='btn-a edit'>编辑</span>"+
				                    "<span class='btn-a delete'>删除</span></td>" +
				                    "</tr>");
							});
						}
						 var t=0;
						<shiro:lacksPermission name="0102010402">
						t++;
						$(".delete").attr("style","display:none;");
		    			</shiro:lacksPermission>
		    			<shiro:lacksPermission name="0102010403">
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
		});
	};
	function SelectReaderType(param,Page){
		GetlibIdxAndNameOBJ(function(){
			$.ajax({
				url:basePath+"/ReaderType/SelectReaderType",
				type:"POST",
				data:{"json":JSON.stringify(param),
					"page":JSON.stringify(Page)},
					success:function(data){
					$("tbody").html("");
					$.each(data.result.rows,function(i,item){
						$("tbody").append(
						"<tr>"+
		            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.type_idx+"' /></div></td>" +
		           		"<td class='col2'>"	+ libIdxAndNameObj[item.library_idx].lib_id||'' +	"</td>" +
		           		"<td class='col3' style='display:none'>" + item.type_distinction + "</td>" +
		                "<td class='col4'>" + item.type_id + "</td>" +
		                "<td class='col5'>" + item.type_name + "</td>"+
		                "<td class='col6'>" + item.type_deposit + "</td>"+
		                "<td class='col7'>" + item.card_fee + "</td>"+
		                "<td class='col8'>" + item.verification_fee + "</td>"+
		                "<td class='col9'><span class='btn-a edit'>编辑</span>"+
		                "<span class='btn-a delete'>删除</span></td>" +
		                "</tr>");
		                });
					var t=0;
					<shiro:lacksPermission name="0102010401">
						$(".increase").attr("style","display:none;");
		   			</shiro:lacksPermission>
		   			<shiro:lacksPermission name="0102010402">
		   			 	t++;
						$(".delete").attr("style","display:none;");
		    		</shiro:lacksPermission>
		    		<shiro:lacksPermission name="0102010403">
		    			t++;
						$(".edit").attr("style","display:none;");
		    		</shiro:lacksPermission>
		    		if(t==2){
		    			$(".col9").attr("style","display:none;");
		    		}
		    		$.pagination(data.result);
				}
			});
		});
	};
	$(document).ready(function(){
		var library_idx = null;
		var oper = <shiro:principal/>;
		if(oper.operator_type >=3){
			library_idx = oper.library_idx;
			$("#libname").addClass("hide");
			$("#search_lib_Id").attr("style","display:none;");
		}
		//获取当前页数
		var size= $("#pagesize option:selected").text();
		var Page={"page":"1","pageSize":size};
		var param ={"library_idx":library_idx,"type_distinction":"1"};
		GetlibIdxAndNameOBJ(function(){
			$.select(param,Page);
		});
	});
	
	
	$("#page").on("click",".prev-page",function(){
		var param = new Object;
		var currentpage = $(".paging-bar li.active").text();
		var page=Number(currentpage)-1;
		var size= $('#pagesize option:selected').text();
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
		var size= $('#pagesize option:selected').html();
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
	
	$("#page").on("click","li",function(){
		var param = new Object;
		if($(this).hasClass("active"))
			return;
		var size= $('#pagesize option:selected').html();
		var page = $(this).html();
		if(page=="...") return;
		param.type_id = $("#search_type_Id").val();
		var Page={"page":page,"pageSize":size}; 
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
	$("div.paging-bar").on("change","#pagesize",function(){
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
});
</script>
</body>
</html>


