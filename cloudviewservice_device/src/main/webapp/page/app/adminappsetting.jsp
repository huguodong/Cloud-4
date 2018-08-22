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
    		width: 200px;
		}
		.equipment-leixing .col4 {
    		min-width: 50px;
		}
		.equipment-leixing .col9 {
    		width: 250px;
		}
		.equipment-leixing .col2 {
    		min-width: 50px;
		}
		.equipment-leixing .col3 {
    		width: 160px;
		}
		.equipment-leixing .col5 {
    		min-width: 300px;
		}
		.equipment-leixing .col7 {
    		width: 300px;
		}
		.equipment-leixing .col8 {
    		min-width: 100px;
		}
		.equipment-leixing .col11 {
    		min-width: 100px;
		}
	</style>
</head>
<body>
<div class="equipment-leixing">
	
	<div class="page-title-bar">
		<span class="title">馆员APP功能设置</span>
		<div class="form-wrap fr">
			<input type="text" name="" id="Keyword_lib_idx" class="input g-input" placeholder="馆id或者馆名" />
			<div class="btn search">查询</div>
			<!-- 修改权限 by huanghuang 20170215 -->
			<shiro:hasPermission name="0112010101">
				<div class="btn increase g-btn-green">新增</div>
			</shiro:hasPermission>
			<shiro:hasPermission name="0112010102">
				<div class="btn delete">删除</div>
			</shiro:hasPermission>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox" ><input type="checkbox" name="" id="" /></div></th>
					<th class="col2">馆ID</th>
					<th class="col3">馆名称</th>
					<th class="col6">主页面上图片地址</th>
					<th class="col5">功能名称</th>
					<th class="col8">排序</th>
					<th class="col7">功能面描述</th>
					
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
		是否要删除选择的APP功能配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<div class="form-dialog" id="add">
	<div class="title">新增馆员APP功能设置</div>
	<div class="form-wrap">
		<ul>
		   <li>
				<div class="left"><span class="g-mustIn">所属图书馆ID</span></div>
				<div class="right"><input type="text" placeholder="请输入" data-id="" id="libid" class="g-input" >
				  <input type="hidden" id="library_idx" name="library_idx"/>
				  <span class="error-msg">请填写正确的图书馆ID</span>
				</div>
			</li>
			<li>
				  <div class="left"><span class="g-mustIn">所属馆名称</span></div>
				  <div class="right">
				 	  <input type="text" placeholder="所属馆名称" readonly="readonly" id="libName" class="g-input" style="color: gray;background-color: #E0E0E0">
				  </div>
			</li>
			<div id="menu_group">
		       
			</div>
			<li>
				<div class="left"><span>主页面上图片地址</span></div>
				<div class="right">
					<input type="text" name="" id="image_url" class="g-input" placeholder="请输入"  />
					<span class="error-msg">主页面上图片地址不能为空</span>
				</div>
			</li>
			
			<li>
				<div class="left">功能面描述</div>
				<div class="right">
					<textarea id="setting_desc" class="g-textarea"></textarea>
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
	<div class="title">编辑馆员APP功能设置</div>
	<div class="form-wrap">
		<ul>
		   <li>
				<div class="left"><span class="g-mustIn">所属图书馆ID</span></div>
				<div class="right"><input type="text" placeholder="请输入" data-id="" id="edit_libid" class="g-input" >
				  <input type="hidden" id="edit_library_idx" name="edit_library_idx"/>
				  <span class="error-msg">请填写正确的图书馆ID</span>
				</div>
			</li>
			<li>
				  <div class="left"><span class="g-mustIn">所属馆名称</span></div>
				  <div class="right">
				 	  <input type="text" placeholder="所属馆名称" readonly="readonly" id="libName" class="g-input" style="color: gray;background-color: #E0E0E0">
				  </div>
			</li>
			<div id="edit_menu_group">
		       
			</div>
			<li>
				<div class="left"><span>主页面上图片地址</span></div>
				<div class="right">
					<input type="text" name="" id="edit_image_url" class="g-input" placeholder="请输入"  />
					<span class="error-msg">主页面上图片地址不能为空</span>
				</div>
			</li>
			
			<li>
				<div class="left">功能面描述</div>
				<div class="right">
					<textarea id="edit_setting_desc" class="g-textarea"></textarea>
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
	var lib_idx = new Array() ;
	$(".delete").on("click",function(){
		lib_idx.length = 0;
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			lib_idx.push($(this).attr("id"));
        });  
		
		if(num>0){
			$(".g-delete-dialog .word").html("");
        	$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"</span>个项目<br>是否要删除选择的APP功能配置？");
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
			layer.alert("请选择要删除的功能配置项");
		}			
	});
	
	//删除按钮操作
	$("tbody").on("click",".delete",function(){
		lib_idx.length = 0;
		lib_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
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
		GlobalGLoading();
		var idx = lib_idx;
		if(!idx) return;
		var param=new Array();
		for(var i=0;i<idx.length;i++){
			param[i]={
			"lib_idx":idx[i],
			"user_type":"1"
			};
		}
		$.ajax({
			url:basePath+"/appSetting/deleteAppSettingBylibidx",
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
				}
		     	var currentpage = $(".paging-bar li.active").text();
		     	var size= $('#pagesize option:selected').text();
		     	var str=$("#Keyword_lib_idx").val();
				var lib_idx = "";
				if(libNameAndIdObj[str]){
					lib_idx=libNameAndIdObj[str].library_idx;
				}else if(libIdAndNameObj[str]){
					lib_idx=libIdAndNameObj[str].library_idx;
				}
				var Page ={"page":currentpage,"pageSize":size,"user_type":"1","lib_idx":lib_idx};
				//调用页面的查询ajax
				$.select(Page);  
		     }else if(data.retMessage){
		     	showRetMessage(data.retMessage);
		     }
			}
		});
		
	});
	/**
	 *查询功能菜单
     **/
	var menu={};
	var lt=0;
	(function GetcodeAndMenuOBJ(){
		if(!typeof menu=="undefined"){
			return;
		}
		var param={
			"main_menu_code":"0203"
		};
	    $.ajax({
	    	url:basePath+"/appSetting/selectByCode",
			type:"POST",
			data:{"req":JSON.stringify(param)}
		}).then(function(data){
			if(data){
				var masterAndSlave=data.result;
				lt = masterAndSlave.length;
				for(var i=0;i<lt;i++){
					menu[i]={"sub_menu_name":masterAndSlave[i].sub_menu_name,"sub_menu_code":masterAndSlave[i].sub_menu_code};
				}
			}
		});
	})();
    
	/**
		编辑按钮操作显示对话框
	**/
	var type = new Array();
	var editLayer=null;
	$("tbody").on("click",".edit",function(){
		for(var i=0;i<lt;i++){
			 $(".m-group"+i).val(-1);
		}
		lib_idx.length = 0;
		lib_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		
		type.length = 0;
		type.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		type.push($(this).parent().parent().find(".col2").html());
		type.push($(this).parent().parent().find(".col3").html());
		type.push($(this).parent().parent().find(".col4").html());
		type.push($(this).parent().parent().find(".col5").html());
		type.push($(this).parent().parent().find(".col6").html());
		type.push($(this).parent().parent().find(".col7").html());
		type.push($(this).parent().parent().find(".col8").html());
		var t = type[1];
		if(t){
			$("#edit_libid").val(t);
			$("#edit_libid").attr("readonly", true);
			$("#modify").find("#libName").val(libIdAndNameObj[t].lib_name);
			$("#edit_library_idx").val(libIdAndNameObj[t].library_idx);
		}else{
			$("#edit_libid").val("");
			$("#modify").find("#libName").val("");
			$("#edit_library_idx").val("");
		}
		if(type[5] == null || type[5].length<=0 || type[5] == "null"){
			$("#edit_image_url").val("");
		}else{
			$("#edit_image_url").val(type[5]);
		}
		if(type[6] == null || type[6].length<=0 || type[6] == "null"){
			$("#edit_setting_desc").val("");
		}else{
			$("#edit_setting_desc").val(type[6]);
		}
		var seridx = $("#service_id"+t).val();
		var serarr = seridx.split(",");
		var sortarr = type[7].split(",");
		for(var i=0;i<serarr.length;i++){
			var s = serarr[i];
			$("#edit_menu_group").find("select.m-group"+(sortarr[i]-1)).val(s);
		}
		for(var i=0;i<lt;i++){
		   $("#edit_menu_group").find("select.m-group"+i).parents("li").removeClass("error");
		}
		$("input[name=edit_library_idx]").parents("li").removeClass("error");
		$("#edit_image_url").parents("li").removeClass("error");
		$("#edit_setting_desc").parents("li").removeClass("error");
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
		GlobalGLoading();
		var fieldCheck=true;
		var param_code=new Array();
		var pc=new Array();
		for(var i=0;i<lt;i++){
			var sub_menu_code=$("#edit_menu_group").find("select.m-group"+i).val();
			if(sub_menu_code != -1){
				pc[i]=sub_menu_code;
				var s = {"service_id":sub_menu_code,"setting_sort":(i+1)};
			    param_code[i]=s;
			}
		}
		var code_length = param_code.length;
		var hash={};
		
		for(var i in pc) {
		  if(hash[pc[i]]){
			  $("#edit_menu_group").find("select.m-group"+i).parents("li").addClass("error");
			  fieldCheck=false;
		  }
		  hash[pc[i]] = true;
	    }
		var library_id = $("#edit_library_idx").val();
		
		var user_type = 1;
		var image_url = $("#edit_image_url").val();
		var setting_desc = $("#edit_setting_desc").val();
		
		var len_url = $("#edit_image_url").val().length;
		var len_desc = $("#edit_setting_desc").val().length;
		var len_lib = $("#edit_library_idx").val().length;
		
		
		if(library_id == null || len_lib<=0){
			$("input[name=edit_library_idx]").parents("li").addClass("error");
			fieldCheck=false;
		}
		if(param_code == null || code_length <=0){
			$(".m-group0").parents("li").addClass("error");
			fieldCheck=false;
		}
		$("select").on("change",function(){
			for(var i=0;i<lt;i++){
			 $(".m-group"+i).parents("li").removeClass("error");
			}
		});
		
		if(len_url>200){
			$li=$("#edit_image_url").parent().parent();
			$("#edit_image_url").siblings().remove();
			$("#edit_image_url").after('<span class="error-msg">图片地址超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		if(len_desc>50){
			$li=$("#edit_setting_desc").parent().parent();
			$("#edit_setting_desc").siblings().remove();
			$("#edit_setting_desc").after('<span class="error-msg">功能描述超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}  
		if(!fieldCheck){
			return;
		}
		var param={
			"lib_idx" : library_id,
			"service_id" : param_code,
			"user_type" : user_type,
			"image_url" : image_url,
			"setting_desc" : setting_desc
		};
		$.ajax({
			url:basePath+"/appSetting/updateAppSettingByPk",
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
					var lib_idx = "";
					if(libNameAndIdObj[str]){
						lib_idx=libNameAndIdObj[str].library_idx;
					}else if(libIdAndNameObj[str]){
						lib_idx=libIdAndNameObj[str].library_idx;
					}
					var Page ={"page":currentpage,"pageSize":size,"user_type":"1","lib_idx":lib_idx};
					$.select(Page);
				}else{
					var msg = data.retMessage;
					if(msg.indexOf("optimistic")>=0){
						layer.alert("当前选择的数据不是最新数据,请刷新之后再做编辑操作");
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

	$(".g-increase-dialog .form-wrap  .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	});
	
	$(":text").focus(function(){
		$(this).parent().parent().removeClass("error");
	});
	$("textarea").focus(function(){
		$(this).parent().parent().removeClass("error");
	});
	
	
	/**
	 *	对应用户数据
	 **/
	var libIdAndNameObj={};
	var libIdxAndNameObj={};
	var libNameAndIdObj={};
	(function GetlibIdxAndNameOBJ(){
		if(!typeof libIdAndNameObj=="undefined"&&!typeof libIdxAndNameObj =="undefined"&&!typeof libNameAndIdObj =="undefined"){
			return;
		}
	    $.ajax({
			url:"${pageContext.request.contextPath}/ascconfig/querylibInfoByCurUserEXT1",
			type:"GET",
			data:{}
		}).then(function(data){
			if(data){
				if(data.message=="_MASTER_SLAVE_"){//非云平台管理员 主馆
					var masterAndSlave=data.result;
					var masterLibrary=masterAndSlave.masterLibrary;
					var slaveLibrary=masterAndSlave.slaveLibrary;//arr
					libIdAndNameObj[masterLibrary.lib_id]={"lib_name":masterLibrary.lib_name,"library_idx":masterLibrary.library_idx};
					libIdxAndNameObj[masterLibrary.library_idx]={"lib_id":masterLibrary.lib_id,"lib_name":masterLibrary.lib_name};
					libNameAndIdObj[masterLibrary.lib_name]={"lib_id":masterLibrary.lib_id,"library_idx":masterLibrary.library_idx};
					//masterlibIdxArr.push(masterLibrary.library_idx);
					for(var i=0;i<slaveLibrary.length;i++){
						libIdAndNameObj[slaveLibrary[i].lib_id]={"lib_name":slaveLibrary[i].lib_name,"library_idx":slaveLibrary[i].library_idx};
						libIdxAndNameObj[slaveLibrary[i].library_idx]={"lib_id":slaveLibrary[i].lib_id,"lib_name":slaveLibrary[i].lib_name};
						libNameAndIdObj[slaveLibrary[i].lib_name]={"lib_id":slaveLibrary[i].lib_id,"library_idx":slaveLibrary[i].library_idx};
						//masterlibIdxArr.push(slaveLibrary[i].library_idx);
					}
				}else if(data.message=="_SLAVE_"){//子馆
						libIdAndNameObj[data.result.lib_id]={"lib_name":data.result.lib_name,"library_idx":data.result.library_idx};
						libIdxAndNameObj[data.result.library_idx]={"lib_id":data.result.lib_id,"lib_name":data.result.lib_name};
						libNameAndIdObj[data.result.lib_name]={"lib_id":data.result.lib_id,"library_idx":data.result.library_idx};
				}else{
					for(var i=0;i<data.result.length;i++){//云平台用户
						libIdAndNameObj[data.result[i].lib_id]={"lib_name":data.result[i].lib_name,"library_idx":data.result[i].library_idx};
						libIdxAndNameObj[data.result[i].library_idx]={"lib_id":data.result[i].lib_id,"lib_name":data.result[i].lib_name};
						libNameAndIdObj[data.result[i].lib_name]={"lib_id":data.result[i].lib_id,"library_idx":data.result[i].library_idx};
						//masterlibIdxArr.push(data.result[i].library_idx);
					}
				}
				var str="";
				for(var i=0;i<lt;i++){
					var cla="";
					if(i == 0){
						cla="class='g-mustIn'";
					}
					str += "<li><div class='left'><span "+cla+">选择功能"+(i+1)+"</span></div><div class='right'>";
					str+="<div class='g-select'><select class='m-group"+i+"'><option value='-1' selected>请选择</option>";
					for(var j=0;j<lt;j++){
						str+="<option value='"+menu[j].sub_menu_code+"'>"+menu[j].sub_menu_name+"</option>";
					}
					str+="</select><span class='arr-icon'></span></div>";
					if(i==0){
						str+="<span class='error-msg'>请至少选择一项功能</span>";
					}else{
						str+="<span class='error-msg'>功能重复，请重新选择</span>";
					}
					str+="</div></li>";
				}
				$("#menu_group").html(str);
				$("#edit_menu_group").html(str);
				/**
				*查询全部数据
				*/
				//获取当前页数
				var size= $('#pagesize option:selected').text();
				var Page={"page":"1","pageSize":size,"user_type":"1"};
				$.ajax({
				url:basePath+"/appSetting/selectAppSettingByPage",
				type:"POST",
				data:{"req":JSON.stringify(Page)},
				success:function(data){
					$("tbody").html("");
					$.each(data.result.rows,function(i,item){
						var lib_name="";
						var lib_id="";
						var t = item.lib_idx;
						var sty="";
						if(libIdxAndNameObj[t]){
							lib_id = libIdxAndNameObj[t].lib_id;
							lib_name = libIdAndNameObj[lib_id].lib_name;
						}else{
							sty="display:none;";
						}
						$("tbody").append(
						"<tr style='"+sty+"'>"+
		            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.lib_idx+"' /></div></td>" +
		            	"<td class='col2'>" + lib_id + "</td>" +
		            	"<td class='col3'>" + lib_name + "</td>" +
		           		"<td class='col6'>" + item.image_url    + "</td>" +
		           		"<input type='hidden' value='"+item.service_id+"' id='service_id"+lib_id+"'/>" +
		                "<td class='col5'>" + item.sub_menu_name + "</td>" +
		                "<td class='col8'>" + item.setting_sort_str + "</td>"+
		                "<td class='col7'>" + item.setting_desc + "</td>"+
		                "<td><span class='btn-a edit'>编辑</span>"+
		                "<span class='btn-a delete'>删除</span></td>" +
		                "</tr>");
		                });
					var t=0;
					<shiro:lacksPermission name="0112010101">
						$(".increase").attr("style","display:none;");
		   			</shiro:lacksPermission>
		   			<shiro:lacksPermission name="0112010102">
		   			 	t++;
						$(".delete").attr("style","display:none;");
		    		</shiro:lacksPermission>
		    		<shiro:lacksPermission name="0112010103">
		    			t++;
						$(".edit").attr("style","display:none;");
		    		</shiro:lacksPermission>
		    		if(t==2){
		    			$(".col7").attr("style","display:none;");
		    		}
		    		$.pagination(data.result);
				}
				});
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
			$("#library_idx").val(libIdAndNameObj[lib_id].library_idx);
		}else{
			$("#libName").val("");
			$("#library_idx").val("");
		}
	});
	 /**
	 *	监听图书馆ID变化 改变对应的图书馆名
	 **/
	$("#edit_libid").on("keyup",function(){
		var lib_id=$(this).val();
		if(lib_id&&libIdAndNameObj[lib_id]){
			$("#modify").find("#libName").val(libIdAndNameObj[lib_id].lib_name);
			$("#edit_library_idx").val(libIdAndNameObj[lib_id].library_idx);
		}else{
			$("#modify").find("#libName").val("");
			$("#edit_library_idx").val("");
		}
	});
	/**
		新增按钮操作显示对话框
	**/
	var increaseLayer=null;
	$(".increase").on("click",function(){
		
		//清空数据操作
		for(var i=0;i<lt;i++){
		 $(".m-group"+i).val(-1);
		}
		$("#image_url").val("");
		$("#setting_desc").val("");
		$("#library_idx").val("");
		$("#libid").val("");
		$("#libName").val("");
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
		var param_code=new Array();
		var pc=new Array();
		for(var i=0;i<lt;i++){
			var sub_menu_code=$("#menu_group").find("select.m-group"+i).val();
			if(sub_menu_code != -1){
				pc[i]=sub_menu_code;
				var s = {"service_id":sub_menu_code,"setting_sort":(i+1)};
			    param_code[i]=s;
			}
		}
		var code_length = param_code.length;
		var hash={};
		
		for(var i in pc) {
		  if(hash[pc[i]]){
			  $("#menu_group").find("select.m-group"+i).parents("li").addClass("error");
			  fieldCheck=false;
		  }
		  hash[pc[i]] = true;
	    }
		var library_id = $("#library_idx").val();
		
		var user_type = 1;
		var image_url = $("#image_url").val();
		var setting_desc = $("#setting_desc").val();
		
		
		var len_url = $("#image_url").val().length;
		var len_desc = $("#setting_desc").val().length;
		var len_lib = $("#library_idx").val().length;
		
		
		if(library_id == null || len_lib<=0){
			$("input[name=library_idx]").parents("li").addClass("error");
			fieldCheck=false;
		}
		if(param_code == null || code_length <=0){
			$(".m-group0").parents("li").addClass("error");
			fieldCheck=false;
		}
		$("select").on("change",function(){
			for(var i=0;i<lt;i++){
			 $(".m-group"+i).parents("li").removeClass("error");
			}
		});
		
		if(len_url>200){
			$li=$("#image_url").parent().parent();
			$("#image_url").siblings().remove();
			$("#image_url").after('<span class="error-msg">图片地址超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}
		if(len_desc>50){
			$li=$("#setting_desc").parent().parent();
			$("#setting_desc").siblings().remove();
			$("#setting_desc").after('<span class="error-msg">功能描述超出限定长度，请修改</span>');
			$li.addClass("error");
			fieldCheck=false;
		}  
		if(!fieldCheck){
			return;
		}
		var param={
			"lib_idx" : library_id,
			"service_id" : param_code,
			"user_type" : user_type,
			"image_url" : image_url,
			"setting_desc" : setting_desc,
			"opertype" : 1
		};
		$.ajax({
			 url:basePath+"/appSetting/updateAppSettingByPk",
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
					var lib_idx = "";
					if(libNameAndIdObj[str]){
						lib_idx=libNameAndIdObj[str].library_idx;
					}else if(libIdAndNameObj[str]){
						lib_idx=libIdAndNameObj[str].library_idx;
					}
					var Page ={"page":currentpage,"pageSize":size,"user_type":"1","lib_idx":lib_idx};
					$.select(Page);
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
		var libstr = "";
		var str=$("#Keyword_lib_idx").val();
		var param={
			"lib_name":str	
		};
		$.ajax({
			url:basePath+"/appSetting/selLibraryByNameORLibId",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				$.each(data.result,function(i,item){
					libstr +=item.library_idx+",";
				  });
			 libstr=libstr.substring(0,(libstr.length-1));
		     var size= $('#pagesize option:selected').text();
			 var Page={"page":"1","pageSize":size,"user_type":"1","lib_idx_str":libstr}; 
			 $.select(Page);
			}
		});
		
	});
	
	$(document).ready(function(){
		
	});
	
	//封装查询ajax
	jQuery.select=function(Page){
		$.ajax({
			url:basePath+"/appSetting/selectAppSettingByPage",
			type:"POST",
			data:{"req":JSON.stringify(Page)},
			success:function(data){
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					var lib_name="";
					var lib_id="";
					var t = item.lib_idx;
					var sty="";
					if(libIdxAndNameObj[t]){
						lib_id = libIdxAndNameObj[t].lib_id;
						lib_name = libIdAndNameObj[lib_id].lib_name;
					}else{
						sty="display:none;";
					}
					$("tbody").append(
					"<tr style='"+sty+"'>"+
	            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.lib_idx+"' /></div></td>" +
	            	"<td class='col2'>" + lib_id + "</td>" +
	            	"<td class='col3'>" + lib_name + "</td>" +
	           		"<td class='col6'>" + item.image_url    + "</td>" +
	           		"<input type='hidden' value='"+item.service_id+"' id='service_id"+lib_id+"'/>" +
	                "<td class='col5'>" + item.sub_menu_name + "</td>" +
	                "<td class='col8'>" + item.setting_sort_str + "</td>"+
	                "<td class='col7'>" + item.setting_desc + "</td>"+
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
				<shiro:lacksPermission name="0112010102">
   			 		t++;
					$(".delete").attr("style","display:none;");
    			</shiro:lacksPermission>
    			<shiro:lacksPermission name="0112010103">
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
		var libstr = "";
		var str=$("#Keyword_lib_idx").val();
		var param={
			"lib_name":str	
		};
		$.ajax({
			url:basePath+"/appSetting/selLibraryByNameORLibId",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				$.each(data.result,function(i,item){
					libstr +=item.library_idx+",";
				  });
			 libstr=libstr.substring(0,(libstr.length-1));
			 var Page={"page":page,"pageSize":size,"user_type":"1","lib_idx_str":libstr}; 
			 $.select(Page);
			}
		});
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").text();
		page = Number(currentpage) + 1;
		var size= $('#pagesize option:selected').text();
		var libstr = "";
		var str=$("#Keyword_lib_idx").val();
		var param={
			"lib_name":str	
		};
		$.ajax({
			url:basePath+"/appSetting/selLibraryByNameORLibId",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				$.each(data.result,function(i,item){
					libstr +=item.library_idx+",";
				  });
			 libstr=libstr.substring(0,(libstr.length-1));
			 var Page={"page":page,"pageSize":size,"user_type":"1","lib_idx_str":libstr}; 
			 $.select(Page);
			}
		});
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var size= $('#pagesize option:selected').text();
		var page = $(this).html();
		if(page=="...") return;
		var libstr = "";
		var str=$("#Keyword_lib_idx").val();
		var param={
			"lib_name":str	
		};
		$.ajax({
			url:basePath+"/appSetting/selLibraryByNameORLibId",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				$.each(data.result,function(i,item){
					libstr +=item.library_idx+",";
				  });
			 libstr=libstr.substring(0,(libstr.length-1));
			 var Page={"page":page,"pageSize":size,"user_type":"1","lib_idx_str":libstr}; 
			 $.select(Page);
			}
		});
		
	}); 
	/**
		每页显示的条目数切换
	**/
	$("select#pagesize").on("change",function(){
		GlobalGLoading();
		var size= $('#pagesize option:selected').text();
		var libstr = "";
		var str=$("#Keyword_lib_idx").val();
		var param={
			"lib_name":str	
		};
		$.ajax({
			url:basePath+"/appSetting/selLibraryByNameORLibId",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				$.each(data.result,function(i,item){
					libstr +=item.library_idx+",";
				  });
			 libstr=libstr.substring(0,(libstr.length-1));
			 var Page={"page":1,"pageSize":size,"user_type":"1","lib_idx_str":libstr}; 
			 $.select(Page);
			}
		});
	});
	<%-- 回车事件 --%>
	$('#Keyword_lib_idx').keydown(function(e){
		if(e.keyCode==13){
			var libstr = "";
			var str=$("#Keyword_lib_idx").val();
			var param={
				"lib_name":str	
			};
			$.ajax({
				url:basePath+"/appSetting/selLibraryByNameORLibId",
				type:"POST",
				data:{"req":JSON.stringify(param)},
				success:function(data){
					$.each(data.result,function(i,item){
						libstr +=item.library_idx+",";
					  });
				 libstr=libstr.substring(0,(libstr.length-1));
			     var size= $('#pagesize option:selected').text();
				 var Page={"page":"1","pageSize":size,"user_type":"1","lib_idx_str":libstr}; 
				 $.select(Page);
				}
			});
		}
	});
});
</script>
</body>
</html>


