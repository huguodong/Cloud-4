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
		.equipment-leixing .col6 {
    		width: 400px;
		}
		.equipment-leixing .col9 {
    		width: 100px;
		}
		.equipment-leixing .col2 {
    		min-width: 100px;
		}
		.equipment-leixing .col3 {
    		width: 200px;
		}
	</style>
</head>
<body>
<div class="equipment-leixing">
	
	<div class="page-title-bar">
		<span class="title">常用模板配置</span>
		<div class="form-wrap fr">
			<input type="text" name="keyword" id="Keyword_lib_idx" class="input g-input" placeholder="模板id或者模板名" />
			<div class="btn search">查询</div>
			<!-- 修改权限 by huanghuang 20170215 -->
			<shiro:hasPermission name="0107030101">
				<div class="btn increase g-btn-green">新增</div>
			</shiro:hasPermission>
			<shiro:hasPermission name="0107030102">
				<div class="btn delete">删除</div>
			</shiro:hasPermission>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox" ><input type="checkbox" name="" id="" /></div></th>
					<th class="col2">模板ID</th>
					<th class="col6">URL地址</th>
					<th class="col3">模板名称</th>
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
		是否要删除选择的常用模板配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<div class="form-dialog" id="add">
	<div class="title">新增常用模板配置</div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">模板选择</span></div>
				<div class="right">
					<div class="g-select">
						<select class="service_idx" id="service_idx">
							
						</select>
						<span class="arr-icon"></span>
					</div>
					    <span class="error-msg">模板选择错误，请至少选择一个模板</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="">模板名称</span></div>
				<div class="right">
					<input type="text" name="" id="setting_desc" class="g-input" placeholder="请输入"  />
					<span class="error-msg">模板名称不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="">URL地址</span></div>
				<div class="right">
					<input type="text" name="" id="setting_url" class="g-input" placeholder="请输入"  />
					<span class="error-msg">URL地址不能为空</span>
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
	<div class="title">编辑常用模板配置</div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">模板选择</span></div>
				<div class="right">
					<div class="g-select">
						<select class="edit_service_idx" id="edit_service_idx">
							
						</select>
						<span class="arr-icon"></span>
					</div>
					    <span class="error-msg">模板选择错误，请至少选择一个模板</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="">模板名称</span></div>
				<div class="right">
					<input type="text" name="" id="edit_setting_desc" class="g-input" placeholder="请输入"  />
					<span class="error-msg">模板名称不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="">URL地址</span></div>
				<div class="right">
					<input type="text" name="" id="edit_setting_url" class="g-input" placeholder="请输入"  />
					<span class="error-msg">URL地址不能为空</span>
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
<script type="text/javascript">
$(function(){
	var lang='zh_CN';
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
	/**
		删除按钮操作显示对话框
	**/
	var setting_idx = new Array() ;
	$(".delete").on("click",function(){
		setting_idx.length = 0;
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			setting_idx.push($(this).attr("id"));
        });  
		
		if(num>0){
			$(".g-delete-dialog .word").html("");
        	$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"</span>个项目<br>是否要删除选择的模板配置？");
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
			layer.alert("请选择要删除的模板配置");
		}			
	});
	
	//删除按钮操作
	$("tbody").on("click",".delete",function(){
		setting_idx.length = 0;
		setting_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		
			$(".g-delete-dialog").find(".word").html("是否删除?");
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
		//GlobalGLoading();
		var idx = setting_idx;
		if(!idx) return;
		var param=new Array();
		for(var i=0;i<idx.length;i++){
			param[i]={
			"setting_idx":idx[i],
			};
		}
		
		$.ajax({
			url:basePath+"/personalSetting/delPersonalSetting",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
			if(data.state){
				if(delLayer){
					layer.close(delLayer);
				}
				/**
				 * 新增 修改 删除 成功操作之后 弹窗 使用
				 * 
				 * **/
				if(data.message=="ONE"){
					GlobalShowMsg({showText:"删除成功",status:true});
				}else if(data.retMessage){
					showRetMessage(data.retMessage);
				}
		     	var currentpage = $(".paging-bar li.active").text();
		     	var size= $('#pagesize option:selected').text();
		     	var str=$("#Keyword_lib_idx").val();
				var Page={"page":currentpage,"pageSize":size,"operator_idx":operator_idx,"setting_desc":str};
				
				//调用页面的查询ajax
				$.select(Page);  
		     }else if(data.retMessage){
		     	showRetMessage(data.retMessage);
		     }
			}
		});
		
	});
	
    
	/**
		编辑按钮操作显示对话框
	**/
	var type = new Array();
	var editLayer=null;
	$("tbody").on("click",".edit",function(){
		setting_idx.length = 0;
		setting_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		
		type.length = 0;
		type.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		type.push($(this).parent().parent().find(".col2").html());
		type.push($(this).parent().parent().find(".col3").html());
		type.push($(this).parent().parent().find(".col6").html());
		$("#edit_setting_url").val(type[3]);
		$("#edit_setting_desc").val(type[2]);
		$("#edit_service_idx").val(type[1]).change();
		
		$("#modify").find("input[type=checkbox]").removeAttr("checked").parents(".g-checkbox").removeClass("on");
		$("#modify").find("li").removeClass("error");
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
			content: $("#modify"), //捕获的元素
			cancel: function(index){
				layer.close(delLayer);
				this.content.hide();
			}
		});
	});
	
	//编辑按钮操作
	$("#ineditBtn").on("click",function(){
		var fieldCheck=true;
		var idx = setting_idx;
		var setting_url = $("#edit_setting_url").val();
		var setting_desc = $("#edit_setting_desc").val();
		var service_idx = $("#edit_service_idx").val();
		var len_url =  $("#edit_setting_url").val().length;
		var len_desc = $("#edit_setting_desc").val().length;
		if(setting_desc ==null || len_desc<=0){
			setting_desc = $("#edit_service_idx option:selected").text();
		}
		if(service_idx ==-1){
			$(".edit_service_idx").parents("li").addClass("error");
			fieldCheck=false;
		}
		$("select").on("change",function(){
			$(this).parents("li").removeClass("error");
		});
		if(len_url>200){
			$li=$("#edit_setting_url").parent().parent();
			$("#edit_setting_url").siblings().remove();
			$("#edit_setting_url").after('<span class="error-msg">URL地址超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		if(len_desc>50){
			$li=$("#edit_setting_desc").parent().parent();
			$("#edit_setting_desc").siblings().remove();
			$("#edit_setting_desc").after('<span class="error-msg">模板名称设置超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		if(!fieldCheck){
			return;
		}
		var param={
			"setting_idx":idx[0],
			"setting_url" : setting_url,
			"setting_desc" : setting_desc,
			"operator_idx":operator_idx,
			"service_idx" : service_idx
		};
		$.ajax({
			url:basePath+"/personalSetting/updPersonalSetting",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				if(data.state){
					if(editLayer){
						layer.close(editLayer);
					}
					GlobalShowMsg({showText:"修改成功",status:true});
					var currentpage = $(".paging-bar li.active").html();
			     	var size= $('#pagesize option:selected').html();
			     	var str=$("#Keyword_lib_idx").val();
			     	var Page={"page":currentpage,"pageSize":size,"operator_idx":operator_idx,"setting_desc":str};
					$.select(Page);
				}else{
					layer.alert("你选的模板已存在");
				}
			}
		});
		
	});
	
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
	
	var operator_idx="";
	var templateArray ={};
	$(document).ready(function(){
		$.ajax({
			url:basePath+"/personalSetting/selectStatisticsTemplates",
			type:"POST",
			data:{},
			success:function(data){
				var html="<option value='-1' selected>请选择</option>";
				$.each(data.result,function(i,item){
					html+="<option value='"+item.statistics_tpl_idx+"'>"+item.statistics_tpl_desc+"</option>";
	            });
				$("#service_idx").html(html);
				$("#edit_service_idx").html(html);
			}
		});
		
		$.ajax({
			url:basePath+"/personalSetting/getoper",
			type:"POST",
			data:"",
			async:false,
			success:function(data){
				operator_idx = data.operator_idx;
				//alert(operator_idx);
			}
		});
		//获取当前页数
		var size= $('#pagesize option:selected').text();
		var Page={"page":"1","pageSize":size,"operator_idx":operator_idx,"setting_desc":""};
		$.ajax({
		url:basePath+"/personalSetting/selectPersonalSettingByPage",
		type:"POST",
		data:{"req":JSON.stringify(Page)},
		async:false,
		success:function(data){
			$("tbody").html("");
			$.each(data.result.rows,function(i,item){
				$("tbody").append(
				"<tr>"+
            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.setting_idx+"' /></div></td>" +
            	"<td class='col2'>" + item.service_idx + "</td>" +
            	"<td class='col6'>" + item.setting_url + "</td>" +
           		"<td class='col3'>" + item.setting_desc    + "</td>" +
                "<td><span class='btn-a edit'>编辑</span>"+
                "<span class='btn-a delete'>删除</span></td>" +
                "</tr>");
                });
			var t=0;
			// 0102031201 新增设备类型  
			// 0102031202 编辑 
			// 0102031203 删除
			<shiro:lacksPermission name="0107030101">
				$(".increase").attr("style","display:none;");
   			</shiro:lacksPermission>
   			<shiro:lacksPermission name="0107030102">
   			 	t++;
				$(".delete").attr("style","display:none;");
    		</shiro:lacksPermission>
    		<shiro:lacksPermission name="0107030103">
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
	
	/**
		新增按钮操作显示对话框
	**/
	var increaseLayer=null;
	$(".increase").on("click",function(){
		//清空数据操作
		$("#setting_url").val("");
		$("#setting_desc").val("");
		$("#service_idx").val("-1").change();
		
		$("#add").find("input[type=checkbox]").removeAttr("checked").parents(".g-checkbox").removeClass("on");
		$("#add").find("li").removeClass("error");
		increaseLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["600px"],
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
		//debugger;
		var fieldCheck=true;
		increaseCheck=true;
		var setting_url = $("#setting_url").val();
		var setting_desc = $("#setting_desc").val();
		var service_idx = $("#service_idx").val();
		
		var len_url =  $("#setting_url").val().length;
		var len_desc = $("#setting_desc").val().length;
		if(setting_desc ==null || len_desc<=0){
			setting_desc = $("#service_idx option:selected").text();
		}
		
		if(service_idx ==-1){
			$(".service_idx").parents("li").addClass("error");
			fieldCheck=false;
		}
		$("select").on("change",function(){
			$(this).parents("li").removeClass("error");
		});
		if(len_url>200){
			$li=$("#setting_url").parent().parent();
			$("#setting_url").siblings().remove();
			$("#setting_url").after('<span class="error-msg">URL地址超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		if(len_desc>50){
			$li=$("#setting_desc").parent().parent();
			$("#setting_desc").siblings().remove();
			$("#setting_desc").after('<span class="error-msg">模板名称设置超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		
		if(!fieldCheck){
			return;
		}
		var param={
			"setting_url" : setting_url,
			"setting_desc" : setting_desc,
			"operator_idx":operator_idx,
			"service_idx" : service_idx
		};
		$.ajax({
			 url:basePath+"/personalSetting/addPersonalSetting",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
			     if(data.state){
			     	if(increaseLayer){
			     		layer.close(increaseLayer);
			     	}
			     	GlobalShowMsg({showText:"新增成功",status:true});
			     	var currentpage = $(".paging-bar li.active").html();
			     	var size= $('#pagesize option:selected').html();
			     	var str=$("#Keyword_lib_idx").val();
			     	var Page={"page":currentpage,"pageSize":size,"operator_idx":operator_idx,"setting_desc":str};
					$.select(Page);
			     }else if(data.retMessage){
			    	 layer.alert("你选的模板已存在");
			     }
		 	}
		});
	});
	
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var str=$("#Keyword_lib_idx").val();
		var size= $('#pagesize option:selected').text();
		var Page={"page":"1","pageSize":size,"operator_idx":operator_idx,"setting_desc":str};
		$.select(Page);
	});
	
	
	//封装查询ajax
	jQuery.select=function(Page){
		$.ajax({
			url:basePath+"/personalSetting/selectPersonalSettingByPage",
			type:"POST",
			data:{"req":JSON.stringify(Page)},
			success:function(data){
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					
					$("tbody").append(
					"<tr>"+
	            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.setting_idx+"' /></div></td>" +
	            	"<td class='col2'>" + item.service_idx + "</td>" +
	            	"<td class='col6'>" + item.setting_url + "</td>" +
	           		"<td class='col3'>" + item.setting_desc    + "</td>" +
	                "<td><span class='btn-a edit'>编辑</span>"+
	                "<span class='btn-a delete'>删除</span></td>" +
	                "</tr>");
	                });
				//一条都没有的情况下，尝试跳转带上一页
				if(data.result.rows.length==0&&Page.page!=1){
					Page.page-=1;
					$.select(Page);
				}
				var t=0;
				<shiro:lacksPermission name="0107030102">
   			 		t++;
					$(".delete").attr("style","display:none;");
    			</shiro:lacksPermission>
    			<shiro:lacksPermission name="0107030103">
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

	function jsonToObj(str){ 
		try{
			return JSON.parse(str); 
		}catch(e){
			return "";
		}
	}
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
	
	
	
	 //上一页操作
	$("#page").on("click",".prev-page",function(){
		var currentpage = $(".paging-bar li.active").text();
		var page=Number(currentpage)-1;
		var size= $('#pagesize option:selected').text();
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":page,"pageSize":size,"operator_idx":operator_idx,"setting_desc":str};
		$.select(Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").text();
		page = Number(currentpage) + 1;
		var size= $('#pagesize option:selected').text();
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":page,"pageSize":size,"operator_idx":operator_idx,"setting_desc":str};
		$.select(Page);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var size= $('#pagesize option:selected').text();
		var page = $(this).html();
		if(page=="...") return;
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":page,"pageSize":size,"operator_idx":operator_idx,"setting_desc":str};
		$.select(Page);
		
	}); 
	/**
		每页显示的条目数切换
	**/
	$("select#pagesize").on("change",function(){
		var size= $('#pagesize option:selected').text();
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":1,"pageSize":size,"operator_idx":operator_idx,"setting_desc":str};
		$.select(Page);
	});
	/**返回顶部**/
	$(document).on("click","#topcontrol",function(){
		 $('html,body').animate({scrollTop:0},"fast");  
	});
	
	<%-- 回车事件 --%>
	$('#Keyword_lib_idx').keydown(function(e){
		if(e.keyCode==13){
			var str=$("#Keyword_lib_idx").val();
		    var size= $('#pagesize option:selected').text();
		    var Page={"page":1,"pageSize":size,"operator_idx":operator_idx,"setting_desc":str};
			$.select(Page);
		}
	});
	$(document).on("change"," .g-checkbox input",function(){
//		$(this).parents(".g-checkbox").toggleClass("on");
		if($(this).prop("checked")){
			$(this).parents(".g-checkbox").addClass("on");
		}else{
			$(this).parents(".g-checkbox").removeClass("on");
		}
	});

	$(document).on("click",".g-chooseAll",function(){
		/*全选*/
		allInputEach($(document),true);
	});
	$(document).on("click",".g-noChooseAll",function(){
		/*反选*/
		allInputEach($(document),false);
	});

	function allInputEach(container,toChecked){
		var $container = container || $(document);

		if(toChecked){
			$container.find("tbody input[type='checkbox']").each(function(){
				if(!$(this).is(":checked")){
					$("tbody input[type='checkbox']").each(function(){		
						$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
					});
					
				}
			});
		}else{
			$container.find("tbody input[type='checkbox']").each(function(){	
				if(!$(this).is(":checked")){
					$("tbody input[type='checkbox']").each(function(){		
						$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
					});
					
				}else{
					$("tbody input[type='checkbox']").each(function(){
						$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
					});
				}
			});
		}
		/*操作结束后进行计算当前选中的个数*/
		calculateInputNum();
	}

	/*底部计数功能*/
	function calculateInputNum(){
		var checkBoxLen = $(".g-table tbody [type='checkbox']:checked").length;
		$(".total-choosen-num").text(checkBoxLen);
		return  checkBoxLen;
	}

	$(document).on("click",".g-table [type='checkbox']",function(){
		calculateInputNum();
	});


	/*检查页面全部的checkbox元素，是选中状态的，就给父元素添加选中状态更换背景*/
	$("input[type='checkbox']").each(function(){
		if($(this).is(":checked")){
			$(this).prop("checked",true).parents(".g-checkbox").addClass("on");
		}
	});

	/* 勾选全选 */
	$(document).on("change",".col1:eq(0) .g-checkbox input",function(){
		if(!$(this).is(":checked")){
			$("tbody input[type='checkbox']").each(function(){		
				$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
			});
			
		}else{
			$("tbody input[type='checkbox']").each(function(){
				$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
			});
		}
		calculateInputNum();
	});
});
</script>

</body>
</html>


