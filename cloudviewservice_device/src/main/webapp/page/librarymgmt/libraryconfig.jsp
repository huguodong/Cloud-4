<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
	<%-- <%@include file="common/global.jsf" %> --%>
</head>
<body>
<div class="config-ACS">
	<%-- <%@include file="common/header.jsf" %> --%>
		<div class="page-title-bar">
			<span class="title">图书馆模板配置<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/librarymgmt/libraryconfig.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-select">
					<select id = "condition">
<!-- 						<option value="-1" selected>选择类型</option> -->
						<option value="select_name">模板名称</option>
<!-- 						<option value="select_cycle">服务周期</option> -->
					</select>
					<span class="arr-icon"></span>
				</div>
				<input type="text" name="" id="search_item" class="input g-input" placeholder="输入模板名称" />
				<div class="btn search">查询</div>
				<!-- 修改权限 by huanghuang 20170215 -->
				<shiro:hasPermission name="0102010201">
					<div class="btn increase">新增</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0102010202">
					<div class="btn delete">删除</div>
				</shiro:hasPermission>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="hide">模板ID</th>
						<th class="col2">模板名称</th>
						<th class="col3">服务周期(月)</th>
						<th class="col4">最大设备数</th>
						<th class="col5">最大用户数</th>
						<th class="col6">分馆数</th>
						<th class="col7">操作</th>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
		</div>
		<%@include file ="../include/page_bar.jsf" %>	
	</div>

<div class="form-dialog-fix">
	<div class="shade"></div>
	<div class="form-dialog">
		<div class="title">
			新增配置
			<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
			<input type="submit" placeholder="" class="g-submit2 g-btn-green" value="保存">
		</div>
		<div class="form-wrap">
			<ul>
			<!--	<li>
					<div class="left">类型</div>
					<div class="right">
						<span class="btn active">模板</span>
						<span class="btn">数据</span>
					</div>
				</li>-->
				<li>
					<div class="left">模板名称</div>
					<div class="right">
						<input type="hidden" name="" id="tpl_idx"  />
						<input type="text" name="" id="tpl_name" maxlength="100" class="g-input" placeholder="请输入" />
						<span class="error-msg">错误提示</span>
					</div>
				</li>
				<li>
					<div class="left">服务周期</div>
					<div class="right">
						<input type="text" name="" id="service_cycle"  maxlength="11" class="g-input w80 dib mr10" placeholder="请输入" />个月
						<span class="error-msg">错误提示</span>
					</div>
				</li>
				<li>
					<div class="left">最大设备数</div>
					<div class="right">
						<input type="text" name="" id="max_devcount" maxlength="11" class="g-input w80" placeholder="请输入" />
						<span class="error-msg">错误提示</span>
					</div>
				</li>
				<li>
					<div class="left">最大用户数</div>
					<div class="right">
						<input type="text" name="" id="max_opercount" maxlength="11" class="g-input w80" placeholder="请输入" />
						<span class="error-msg">错误提示</span>
					</div>
				</li>
				<li>
					<div class="left">分馆数</div>
					<div class="right">
						<input type="text" name="" id="max_sublib" maxlength="11" class="g-input w80" placeholder="请输入" />
						<input type="hidden" name="" id="version_stamp"class="g-input" placeholder="" />
						<span class="error-msg">错误提示</span>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>

<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		当前选择了<span class="font-red"></span>个项目<br>
		是否要删除选择的配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
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
	var delLayer = null;
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
	var tempconf = new Array();
	var dataCheck = false;
	var o=<shiro:principal/>;
	var tpl_idx= new Array();
	var libconfig={};
	//删除选中的多条数据
	$(".delete").on("click",function(){
		tpl_idx.length = 0;
		libconfig.deleteIdxArr=new Array();
		libconfig.isDelelteOne=false;
		var num = $("tbody input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function(){
			tpl_idx.push($(this).attr("id"));
			var ascconfigEntity={
					"lib_service_tpl_id":$(this).attr("id"),
					"version_stamp":$(this).parents("tr").find(".version_stamp").html()
			};
			libconfig.deleteIdxArr.push(ascconfigEntity);
		});
		
		if(num>0){
			$(".g-delete-dialog .word").html("");
			$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"</span>条数据<br>是否要删除选择的图书馆模板配置？");
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
			layer.alert("请勾选要删除的模板！");
		}
	});
	
	//删除当前行的设备分组
	$("tbody").on("click",".delete",function(){
		tpl_idx.length = 0;
		libconfig=new Array();
		libconfig.isDelelteOne=true;
		tpl_idx.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		libconfig.deleteIdx=$(this).parent().parent().find("div input[name='idx']").attr("id");
		libconfig.version_stamp=$(this).parents("tr").find(".version_stamp").html();
		$(".g-delete-dialog").find(".word").html(
		'是否删除 <span class="font-red">'+$(this).parents("tr").find(".lib_service_tpl_id").html()+'</span>');
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
	});
	
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	
	$("div.form-dialog").on("click",":reset",function(){
		var thisRight = $(this).parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
	  	$(this).parents(".form-dialog-fix").find(".form-dialog").animate({
	    	"right": thisRight
	  	}, function() {
	    	$(this).parents(".form-dialog-fix").fadeOut(100);
	  	});
	  	$("body").css("overflow", "visible");
	});
	
	$(".form-btn.remove").on("click",function(){
		var idx = tpl_idx;
		if(!idx) return;
		var data=new Array();
		if(libconfig.isDelelteOne==true){
			if(libconfig.deleteIdx){
				var configEntity={
					"lib_service_tpl_id":libconfig.deleteIdx,
					"version_stamp":libconfig.version_stamp
				};
				data.push(configEntity);
			}
		}else{
			if(libconfig.deleteIdxArr){
				data=libconfig.deleteIdxArr;
			}
		}
		
		$.ajax({
			url:basePath+"/libtempconf/Deletelibtplconf",
			type:"POST",
			data:{"req":JSON.stringify(data)},
			success:function(data){
				if(data&&data.state==true){
				//全部删除成功
				showMsg({
					timeout : 1000,
					showText : '删除成功',
					status : true,
					callback:function(){
						layer.close(delLayer);
					}
				});
				$(".layui-layer-shade").click();
				 	var currentpage = $(".paging-bar li.active").html();
					var size= $(".g-select.refresh").find("option:selected").html();
					var param = {	
						"page":currentpage,
						"pageSize":size
					};
					if($("#condition").val() == "select_name"){
						param.lib_service_tpl_desc = $("#search_item").val();
					}
					if($("#condition").val() == "select_cycle"){
						param.service_cycle = $("#search_item").val();
					}
	     			$.select(param);
			}else if(data.state==false&&data.retMessage){
				var idx=data.retMessage;
				if(idx.indexOf("optimistic") >= 0){
					layer.alert("当前选择的数据不是最新数据，请刷新之后再做删除");
				}else{
					layer.alert("ID:"+idx+"删除失败,数据已被占用!");
				}
				if (delLayer) {
				layer.close(delLayer);
				}
			}
			}
		});
		
	});
	

	//点击新增按钮，打开数据对话框
	$(".increase").on("click",function(){
		var oper = <shiro:principal/>;
		if(oper.operator_type>=3)
			return ;
		$(".form-dialog div.title").html("新增配置"+
			"<input value='取消' class='g-cancel2 g-btn-gray' type='reset'>"+
			"<input placeholder='' class='g-submit2 g-btn-green' value='保存' type='submit'>");
		$(".form-dialog-fix :text").val("");
		$("#tpl_idx").val("");
		<%-- 清除错误提示， --%>
		$("li.error").removeClass("error");
		$(".form-dialog-fix").fadeIn(100);
		$(".form-dialog-fix").find(".form-dialog").animate({
			"right":0
		});
	});
	
	//var tempconf = new Array();
	//点击编辑按钮，打开对话框
	$("tbody").on("click",".edit",function(){
		$(".form-dialog div.title").html("修改配置"+
			"<input value='取消' class='g-cancel2 g-btn-gray' type='reset'>"+
			"<input placeholder='' class='g-submit2 g-btn-green' value='保存' type='submit'>");
		$(".form-dialog-fix :text").val("");
		<%-- 清除错误提示， --%>
		$("li.error").removeClass("error");
		tempconf.length = 0;
		tempconf.push($(this).parent().parent().find("div input[name ='idx']").attr("id"));
		tempconf.push($(this).parent().parent().find(".col2").html());
		tempconf.push($(this).parent().parent().find(".col3").html());
		tempconf.push($(this).parent().parent().find(".col4").html());
		tempconf.push($(this).parent().parent().find(".col5").html());
		tempconf.push($(this).parent().parent().find(".col6").html());
		$("#tpl_idx").val(tempconf[0]);
		$("#tpl_name").val(tempconf[1]);
		$("#service_cycle").val(tempconf[2]);
		$("#max_devcount").val(tempconf[3]);
		$("#max_opercount").val(tempconf[4]);
		$("#max_sublib").val(tempconf[5]);
		$("#version_stamp").val($(this).parent().parent().find(".version_stamp").html());
		
		$(".form-dialog-fix").fadeIn(100);
		$(".form-dialog-fix").find(".form-dialog").animate({
			"right":0
		});
	});

	/* $(".form-dialog .form-wrap  .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	}); */
	//数据检查
	var checkdata=function(libCheck){
		if(libCheck){
			var tpl_name=$("#tpl_name").val();
			var service_cycle=$("#service_cycle").val();
			var max_devcount=$("#max_devcount").val();
			var max_opercount=$("#max_opercount").val();
			var max_sublib=$("#max_sublib").val();
	
			var len_tplname =$("#tpl_name").val().length;
			var len_cycle=$("#service_cycle").val().length;
			var len_devcount=$("#max_devcount").val().length;
			var len_opercount =$("#max_opercount").val().length;
			var len_sublib=$("#max_sublib").val().length;
			
			if(!tpl_name || len_tplname>100){
				$("#tpl_name").closest("li").addClass("error");
				$("#tpl_name").closest("li").find(".error-msg").html("请输入模板名称");
				libCheck=false;
			}else{
				$("#tpl_name").closest("li").removeClass("error");	
			}
			
			var regNum = /^[0-9]*$/;
			if(len_cycle>11 || !service_cycle){
				$("#service_cycle").closest("li").addClass("error");
				$("#service_cycle").closest("li").find(".error-msg").html("请输入服务周期");
				libCheck=false;
			}else{
				if(!regNum.test(service_cycle)){
					$("#service_cycle").closest("li").addClass("error");
					$("#service_cycle").closest("li").find("span").html("请填写整数");
					libCheck = false;
				}else{
					$("#service_cycle").closest("li").removeClass("error");
				}
			}
			
			if(len_devcount>11 || !max_devcount){
				$("#max_devcount").closest("li").addClass("error");
				$("#max_devcount").closest("li").find("span").html("请输入最大设备数");
				libCheck=false;
			}else{
				if(!regNum.test(max_devcount)){
					$("#max_devcount").closest("li").addClass("error");
					$("#max_devcount").closest("li").find("span").html("请填写整数");
					libCheck = false;
				}else{
					$("#max_devcount").closest("li").removeClass("error");
				}
			}
			
			if( len_opercount >11 || !max_opercount){
				$("#max_opercount").closest("li").addClass("error");
				$("#max_opercount").closest("li").find("span").html("请输入最大用户数");
				libCheck=false;
			}else{
				if(!regNum.test(max_opercount)){
					$("#max_opercount").closest("li").addClass("error");
					$("#max_opercount").closest("li").find("span").html("请填写整数");
					libCheck = false;
				}else{
					$("#max_opercount").closest("li").removeClass("error");
				}
			}
			
			if( len_sublib>11 || !max_sublib){
				$("#max_sublib").closest("li").addClass("error");
				$("#max_sublib").closest("li").find("span").html("请输入分馆数");
				libCheck=false;
			}else{
				if(!regNum.test(max_sublib)){
					$("#max_sublib").closest("li").addClass("error");
					$("#max_sublib").closest("li").find("span").html("请填写整数");
					libCheck = false;
				}else{
					$("#max_sublib").closest("li").removeClass("error");
				}
			}
		}
		return libCheck;
		
	};

	$(":text").focus(function(){
		$(this).parent().parent().removeClass("error");
	});

	//提交按钮事件
	$("div.form-dialog").on("click",".g-submit2",function(){
		//数据检查
		dataCheck = true;
		if(checkdata(dataCheck) == false) return;
		var tpl_name=$("#tpl_name").val();
		var service_cycle=$("#service_cycle").val();
		var max_devcount=$("#max_devcount").val();
		var max_opercount=$("#max_opercount").val();
		var max_sublib=$("#max_sublib").val();
		var version_stamp=$("#version_stamp").val();
		var param = {"lib_service_tpl_desc":tpl_name,
				"service_cycle":service_cycle,
				"max_device_count":max_devcount,
				"max_operator_count":max_opercount,
				"max_sublib_count":max_sublib};
		var thisDom = $(this);
		var name = "";
		if($("#tpl_idx").val().trim()!=null && $("#tpl_idx").val().trim()!=""){
			name = "update";
		}else{
			name = "add";
		}
		if(name == "add"){
			var data = {
			"operator_idx":o.operator_idx,
			"libconfig":param,
			};
			$.ajax({
				 url:basePath+"/libtempconf/Insertlibtplconf",
			 	 type:"POST",
			 	 data:{"json":JSON.stringify(data)},
			     success:function(data){
					 if(data.state){
					 	showMsg({
							timeout : 1000,
							showText : '添加成功',
							status : true,
							callback:function(){
								/*应该是成功的时候收回吧，酌情处理*/
								var thisRight =  thisDom.parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
			
								thisDom.parents(".form-dialog-fix").find(".form-dialog").animate({
									"right":thisRight
								},function(){
									thisDom.parents(".form-dialog-fix").fadeOut(100);
								});
							}
			
						});
					 	var currentpage = $(".paging-bar li.active").html();
						var size= $(".g-select.refresh").find("option:selected").html();
						var info = {"page":currentpage,"pageSize":size}; 		
						if($("#condition").val() == "select_name")
							info.lib_service_tpl_desc = $("#search_item").val();
						if($("#condition").val() == "select_cycle")
							info.service_cycle = $("#search_item").val();
						$.select(info);
// 						$(".shade").click();
		        	 }else{
		        		 //layer.alert("新增失败！"+data.message);
		        		showRetMessage(data.retMessage);
// 		        	 	showMsg({
// 							timeout : 1000,
// 							showText : '添加失败',
// 							status : false,
// 							callback:function(){
// 								/*应该是成功的时候收回吧，酌情处理*/
// 								var thisRight =  thisDom.parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
			
// 								thisDom.parents(".form-dialog-fix").find(".form-dialog").animate({
// 									"right":thisRight
// 								},function(){
// 									thisDom.parents(".form-dialog-fix").fadeOut(100);
// 								});
// 							}
		
// 						});
		        	 }
	        	 }
			});
			
		}
		if(name == "update"){
			var data = {
				"lib_service_tpl_id":tempconf[0],
				"lib_service_tpl_desc":tpl_name,
				"service_cycle":service_cycle,
				"max_device_count":max_devcount,
				"max_operator_count":max_opercount,
				"max_sublib_count":max_sublib,
				"version_stamp":version_stamp
				};
			$.ajax({
				url:basePath+"/libtempconf/Updatelibtplconf",
				type:"POST",
				data:{"json":JSON.stringify(data)},
				success:function(data){
					if(data.state){
						showMsg({
							timeout : 1000,
							showText : '修改成功',
							status : true,
							callback:function(){
								/*应该是成功的时候收回吧，酌情处理*/
								var thisRight =  thisDom.parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
		
								thisDom.parents(".form-dialog-fix").find(".form-dialog").animate({
								"right":thisRight},function(){
									thisDom.parents(".form-dialog-fix").fadeOut(100);
								});
							}
						});
						var currentpage = $(".paging-bar li.active").html();
						var size= $(".g-select.refresh").find("option:selected").html();
						var param = {	
							"page":currentpage,
							"pageSize":size
						};
						if($("#condition").val() == "select_name"){
							param.lib_service_tpl_desc = $("#search_item").val();
						}
						if($("#condition").val() == "select_cycle"){
							param.service_cycle = $("#search_item").val();
						}
		     			$.select(param);
					}else{
						var message=data.retMessage;
						if(message.indexOf("optimistic")>=0){
							showRetMessage("当前选择的数据不是最新数据,请刷新之后再做编辑");
						}else{
							showRetMessage(data.retMessage);
						}
					}	
				}
			});	
		
		}
	});
	
	function showMsg(option){
		var defaults = {
			timeout : option.timeout || 1000,
			showText : option.showText || '添加配置成功',
			backgroundColor : option.status === true ? "#2ab65b" :"#ff3d3d",
			callback:function(){
				if(option.status == true){
					var thisRight =  thisDom.parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
					thisDom.parents(".form-dialog-fix").find(".form-dialog").animate({"right":thisRight},function(){
						thisDom.parents(".form-dialog-fix").fadeOut(100);
					});
				}
			}
		};
		defaults = $.extend(defaults,option);
		layer.msg(defaults.showText, {
			area:["240px"],
			offset:["110px"],
		  	time: defaults.timeout,
		  	success: function(layero, index){
		  	    $(".layui-layer-hui").css("background-color",defaults.backgroundColor);
		  	}
		}, function(){
			defaults.callback();
		});  
	}
	
	$(".search").on("click",function(){
		var size = $(".g-select.refresh").find("option:selected").html();
		var param ={
			"page":"1",
			"pageSize":size
		};
		if($("#condition").val() == "select_name")
			param.lib_service_tpl_desc = $("#search_item").val();
		if($("#condition").val() == "select_cycle")
			param.service_cycle = $("#search_item").val();
		$.select(param);
	});
	
	jQuery.select=function (param){
		$("tbody").html("");
		$(".col1 :checkbox").prop("checked",false).trigger("change");
		$.ajax({
			url:basePath+"/libtempconf/Selectlibtplconf",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				$("tbody").html("");
			if(data.state){
				if(data.result.rows == null){
					var index = param.page;
					if(index >1){
						param.page = index-1;
						$.select(param);
					}		
				}else{
				$.each(data.result.rows,function(i,item){
					$("tbody").append(
					"<tr>"+
        	    	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.lib_service_tpl_id+"' /></div></td>" +
        	   		"<td class='lib_service_tpl_id hide'>"	+ item.lib_service_tpl_id +	"</td>" +
        	   		"<td class='col2'>"	+ item.lib_service_tpl_desc +	"</td>" +
           			"<td class='col3'>" + item.service_cycle + "</td>" +
           		    "<td class='col4'>" + item.max_device_count + "</td>" +
           	     	"<td class='col5'>" + item.max_operator_count + "</td>"+
           	     	"<td class='col6'>" + item.max_sublib_count + "</td>"+
                	"<td class='col7'><span class='btn-a edit'>编辑</span>"+
                	"<span class='btn-a delete'>删除</span></td>" +
                	"<td class='version_stamp hide'>" + item.version_stamp + "</td>"+
                	"</tr>");
				});
				}
				var t=0;
				<shiro:lacksPermission name="0102010202">
				t++;
				$(".delete").attr("style","display:none;");
    			</shiro:lacksPermission>
    			<shiro:lacksPermission name="0102010203">
				t++;
				$(".edit").attr("style","display:none;");
    			</shiro:lacksPermission>
    			if(t==2){
    			$(".col7").attr("style","display:none;");
    			} 
    			$.pagination(data.result);     
		    }
			}
		});
	};
	
	$(document).ready(function(){
		//获取当前页面显示数量
		var size= $(".g-select.refresh").find("option:selected").html();
		var param={"page":"1","pageSize":size};
		
		$.ajax({
			url:basePath+"/libtempconf/Selectlibtplconf",
			type:"POST",
			data:{"json":JSON.stringify(param)},
				success:function(data){
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					$("tbody").append(
					"<tr>"+
	            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.lib_service_tpl_id+"' /></div></td>" +
	           		"<td class='lib_service_tpl_id hide'>"	+ item.lib_service_tpl_id +	"</td>" +
	           		"<td class='col2'>"	+ item.lib_service_tpl_desc +	"</td>" +
	           		"<td class='col3'>" + item.service_cycle + "</td>" +
	                "<td class='col4'>" + item.max_device_count + "</td>" +
	                "<td class='col5'>" + item.max_operator_count + "</td>"+
	                "<td class='col6'>" + item.max_sublib_count + "</td>"+
	                "<td class='col7'><span class='btn-a edit'>编辑</span>"+
	                "<span class='btn-a delete'>删除</span></td>" +
	                "<td class='version_stamp hide'>" + item.version_stamp + "</td>"+
	                "</tr>");
	                });
				var t=0;
				<shiro:lacksPermission name="0102010201">
					$(".increase").attr("style","display:none;");
	   			</shiro:lacksPermission>
	   			<shiro:lacksPermission name="0102010202">
	   			 	t++;
					$(".delete").attr("style","display:none;");
	    		</shiro:lacksPermission>
	    		<shiro:lacksPermission name="0102010203">
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
	
	//上一页操作
	$("#page").on("click",".prev-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size= $(".g-select.refresh").find("option:selected").html();
		var param = {	"page":page,
				"pageSize":size}; 
		if($("#condition").val() == "select_name")
			param.lib_service_tpl_desc = $("#search_item").val();
		if($("#condition").val() == "select_cycle")
			param.service_cycle = $("#search_item").val();
		$.select(param);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $(".g-select.refresh").find("option:selected").html();
		var param = {	"page":page,
				"pageSize":size,}; 
		if($("#condition").val() == "select_name")
			param.lib_service_tpl_desc = $("#search_item").val();
		if($("#condition").val() == "select_cycle")
			param.service_cycle = $("#search_item").val();
		$.select(param);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var size= $(".g-select.refresh").find("option:selected").html();
		var page = $(this).html();
		if(page=="...") return;
		var param={	"page":page,
				"pageSize":size,}; 
	 	if($("#condition").val() == "select_name")
			param.lib_service_tpl_desc = $("#search_item").val();
		if($("#condition").val() == "select_cycle")
			param.service_cycle = $("#search_item").val();
		$.select(param);
	}); 
});
</script>
</body>
</html>


