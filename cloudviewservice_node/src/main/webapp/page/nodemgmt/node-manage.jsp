<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <meta HTTP-EQUIV="expires" CONTENT="0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="//res.layui.com/layui/build/css/layui.css"  media="all">
    <title>节点管理</title>
</head>
<body>
<div class="equipment-guanli">
        <div class="page-title-bar">
            <span class="title">节点管理<a href="${pageContext.request.contextPath}/static/help-page.html" target="_blank" class="g-help"></a></span>
            <div class="form-wrap fr">
                <div class="g-select">
                    <select name="type" id="type">
                        <option value="-1" selected>选择类型</option>
                        <option value="view">视图层</option>
                        <option value="business">业务层</option>
                        <option value="db">数据层</option>
                    </select>
                    <span class="arr-icon"></span>
                </div>
                <input type="text" name="keyword" id="keyword" class="input g-input" placeholder="输入节点编号" />
                <div class="btn search">查询</div>
                <div class="btn increase g-btn-green">新增</div>
                <div class="btn delete">删除</div>
                <div class="btn switch">切换主备</div>
            </div>
        </div>
        <div class="main">
            <table class="g-table1">
                <thead>
	                <tr>
	                    <th class="col1"><div class="g-checkbox"><input type="checkbox" name=""/></div></th>
	                    <th class="col2">节点编号</th>
	                    <th class="col3">节点名称</th>
	                    <th class="col4">节点属性</th>
	                    <th class="col5">节点类型</th>
	                    <th class="col6">业务类型</th>
	                    <th class="col7">所属容器</th>
	                   <th class="col8">是否启用</th>
	                    <!-- <th class="col8">服务图书馆列表</th>
	                    <th class="col8">关联节点列表</th>
	                    <th class="col8">当前状态</th> -->
	                    <th class="col9">操作</th>
	                </tr>
                </thead>
                <tbody>
	                
                </tbody>
            </table>
        </div>
        <%@ include file="../include/page_bar.jsf" %>
</div>
<div class="g-lib-detail-dialog">
    <div class="title">服务图书管列表</div>
    <div class="list">
        <div style="width:100%;height:500px;overflow:auto;padding-top: 30px">
            <ul id="libs_detail">
            </ul>
        </div>
    </div>
</div>
<div class="g-comm-detail-dialog">
    <div class="title">关联节点列表</div>
    <div class="list">
        <div style="width:100%;height:500px;overflow:auto;padding-top: 30px">
            <ul id="nodes_detail">
            </ul>
        </div>
    </div>
</div>
<div class="g-delete-dialog">
    <span class="line"></span>
    <div class="word">
        当前选择了 <span class="font-red">7</span> 个项目<br>
        是否要删除选择的配置？
    </div>
    <div class="form-btn cancel g-btn-gray">取消</div>
    <div class="form-btn remove g-btn-red">删除</div>
</div>
<div class="g-info-dialog">
    <span class="line"></span>
    <div class="word">
    </div>
    <div class="form-btn cancel g-btn-gray">确定</div>
</div>

<%@include file="../include/node_conf.jsf"%>

<script type="text/javascript">
	var editlLayer = null;
	var basePath = null;
	$(function() {
		(function mainHeightController() {
			var winH = $(window).height();
			var headerH = $(".g-header").outerHeight();
			var pageTitleBar = $(".page-title-bar").outerHeight();
			var pagingBarH = $(".paging-bar").outerHeight();
			var location = (window.location+'').split('/');  
			basePath = location[0]+'//'+location[2]+'/'+location[3];
			$(".main").css({
				"min-height" : winH - headerH - pagingBarH - pageTitleBar
			});
		})();
		var detailLayer = null;
		var createLayer = null;
		var delLayer = null;
		var infoLayer = null;
		main_node_idx =[] ;
		/*显示删除div操作*/
		$(".delete").on("click",function(){
			main_node_idx.length = 0; 
			var num = $("tbody input[name='idx']:checked").length;
			$("tbody input[name='idx']:checked").each(function(){
				main_node_idx.push($(this).attr("id"));
			});
			if(num>0){
				$(".word").html("");
				$(".word").append("当前选择了<span class='font-red'> "+num+" </span>条数据<br>是否要删除选择的节点信息？");
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
				layer.alert("请至少选择一条数据");
			}
		});
		
		//删除当前行的数据
		$("tbody").on("click",".delete",function(){
			main_node_idx.length = 0; 
			main_node_idx.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
			$('.word').html("");
			$('.word').append("是否要删除当前行的数据？");
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
		
		/*弹出编辑节点div操作*/
		$("tbody").on("click",".primary-edit",function(){
			$(".error-msg").text("");
			var node_idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
        	layerOpen({
                "title":"编辑节点",
                "btnText":"保存",
                "btnColorClass":"g-btn-blue",
                "status" : true,
                "index":node_idx
            });
        });
		
		/*弹出创建备节点div操作*/
		$("tbody").on("click",".create",function(){
			$(".primary").hide();
			$(".node_detail").hide();
			$(".secondary").show();
			$(".error-msg").text("");
			var node_idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
			//清除tips
			$(".form-dialog").find("li").removeClass("error");
			//显示  新增模板基本信息填写
			$basicInfoLi=$(".form-dialog-fix.w-900").find("div.left-tab").find("ul").find(".basic-info");
			$basicInfoLi.removeClass("hide");
			$basicInfoLi.trigger("click");
			$(".right-form #secondary_idx").removeClass("hide");
			$(".form-dialog-fix.w-900").find("#secondary_idx").removeClass("hide");
			$(".form-dialog-fix.w-900").fadeIn(100);
			$(".form-dialog-fix.w-900").find(".form-dialog").animate({
				"right":0
			});
			
			$("#node_idx").val(node_idx);
			$("#secondary_node_idx").val("");
			$("#secondary_node_id").val("");
			$("#secondary_node_id").removeAttr("disabled");
			$("#secondary_node_name").val("");
			$("#secondary_container_idx").val("");
			$(".secondary .char").text("创建备节点");

			$.loadContainerList($("#secondary_container_idx"));
        });
		
		//编辑备节点
		$("tbody").on("click",".secondary-edit",function(){
			$(".primary").hide();
			$(".node_detail").hide();
			$(".secondary").show();
			$(".error-msg").text("");
			$("#node_idx").val("");
			var node_idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
			var param = {
   				"node_idx" : node_idx,
   			};
			$.loadContainerList($("#secondary_container_idx"));
        	$.ajax({
    			url : basePath + "/node/queryById",
    			type : "POST",
    			data : {
    				"req" : JSON.stringify(param)
    			},
    			success : function(data) {
    				if (data.state) {
    					$("#secondary_node_idx").val(data.result.node_idx);
    					$("#secondary_node_id").val(data.result.node_id);
    					$("#secondary_node_id").attr("disabled",true);
    					$("#secondary_node_name").val(data.result.node_name);
    					$("#secondary_container_idx").val(data.result.container_idx);
    					$("#secondary_enable").val(data.result.enable);
    				}
    			}
        	});
			
        	//清除tips
			$(".form-dialog").find("li").removeClass("error");
			//显示  新增模板基本信息填写
			$basicInfoLi=$(".form-dialog-fix.w-900").find("div.left-tab").find("ul").find(".basic-info");
			$basicInfoLi.removeClass("hide");
			$basicInfoLi.trigger("click");
			$(".right-form #secondary_idx").removeClass("hide");
			$(".form-dialog-fix.w-900").find("#secondary_idx").removeClass("hide");
			$(".form-dialog-fix.w-900").fadeIn(100);
			$(".form-dialog-fix.w-900").find(".form-dialog").animate({
				"right":0
			});
			$(".secondary .char").text("编辑备节点");
        });
		
		
		/*弹出节点详细信息div操作*/
		$("tbody").on("click",".item_code",function(){
			$(".primary").hide();
			$(".secondary").hide();
			$(".node_detail").show();
			$(".error-msg").text("");
			var node_idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
			var param = {
   				"node_idx" : node_idx,
   			};
        	$.ajax({
    			url : basePath + "/node/queryById",
    			type : "POST",
    			data : {
    				"req" : JSON.stringify(param)
    			},
    			success : function(data) {
    				if (data.state) {
    					$("#detail_node_idx").val(data.result.node_idx);
    					$("#detail_node_id").val(data.result.node_id);
    					$("#detail_node_name").val(data.result.node_name);
    					$("#detail_container_idx").val(data.result.container_idx);
    					$("#detail_node_model").val(data.result.node_model);
    					$("#detail_node_type_idx").val(data.result.node_type_idx);
    					$("#detail_node_attributes").val(data.result.node_attributes);
    					
    					$.loadTypeList($("#detail_node_type_idx"));
    					$.loadContainerList($("#detail_container_idx"));
    					
						var libs=data.result.library_idxs.split(",");
						$.findLibs(libs,$("#detail_library_idxs"));
						var nodes=data.result.node_relations.split(",");
						$.findNodes(nodes,$("#detail_node_relations"));
    				}
    			}
        	});
        	
			//清除tips
			$(".form-dialog").find("li").removeClass("error");
			//显示  新增模板基本信息填写
			$basicInfoLi=$(".form-dialog-fix.w-900").find("div.left-tab").find("ul").find(".basic-info");
			$basicInfoLi.removeClass("hide");
			$basicInfoLi.trigger("click");
			$(".right-form #detail_idx").removeClass("hide");
			$(".form-dialog-fix.w-900").find("#detail_idx").removeClass("hide");
			$(".form-dialog-fix.w-900").fadeIn(100);
			$(".form-dialog-fix.w-900").find(".form-dialog").animate({
				"right":0
			});
			
			$(".node_detail .char").text("节点详细信息");
        });
		
		
		/**切换主备按钮操作**/
		$(".switch").on("click",function(){
			var num = $("tbody input[name='idx']:checked").length;
			if(num!=1){
				if(num > 1) layer.alert("最多只能选择一条数据!");
				else if(num < 1) layer.alert("请选择一条数据!");
				return;
			}
			var node_id; 
			var attributes;
			$("tbody input[name='idx']:checked").each(function(){
				node_id=$(this).attr("id");
				attributes =$(this).parent().parent().parent().find(".col4").text();
			});
			
			if(attributes == '备节点'){
				layer.alert("请选择主节点进行切换!");
				return ;
			}
			
		/* 	var dialogIndex = loadingDialog({
			
				loadText:"请稍等，正在切换主备···"
			}); */
			
			layer.msg('请稍等，正在切换主备···', {
  			icon: 16
  			,shade: 0.01
  			,time:100000000
			});
			var param={
				"type":"manual",
				"node_idx":node_id
			};
			$.ajax({
				url : basePath + "/switchController/switch",
				type : "POST",
				timeout:1200000,
				data : {
    				"req" : JSON.stringify(param)
    			},
				success : function(data) {
					if (data.state) {
						//layer.close(dialogIndex);
						layer.msg('', {
			  			time:1000
						});
						layer.alert("切换成功！");
						var size= $('#showSize option:selected').text();
						var node_type = $("#type").val();
						if($("#type").val()==-1) 
							node_type="";
						var node_id = $("#keyword").val();
						var Page = {
    							"page":1,
    							"pageSize":size,
    							"node_model":node_type,
    							"node_id":node_id
    						};
   						$.select(Page);
   						GlobalGLoading();
					}else{
						//layer.close(dialogIndex);
						var message = data.message;
						if(message != null && message.length>0){
							layer.alert("切换失败,"+data.message);
						}
						else{
							layer.alert("切换失败！");
						}
					}
				},
				error:function(data){
					layer.msg('', {
			  			time:1000
					});
					layer.alert("切换失败！");
				}
			});
		});
		
		/**查询按钮操作**/
		$(".search").on("click",function(){
			var size= $('#showSize option:selected').text();
			var node_type = $("#type").val();
			if($("#type").val()==-1) 
				node_type="";
			var node_id = $("#keyword").val();
			var param={
				"node_model":node_type,
				"node_id":node_id,
				"page":1,
				"pageSize":size,
			};
			$.select(param);
		});
		
		/*初始化主节点新建、编辑页面*/
		$("#primary-left-tab").on("click","li",function(){
			var thisIndex=$(this).attr("date-id");
			if(!thisIndex) thisIndex= $(this).index();
			$(this).addClass("active").siblings("li").removeClass("active");
			$(".primary").find(".right-content").hide().eq(thisIndex).show();
		});
		
		/*初始化备节点新建、编辑页面*/
		$("#secondary-left-tab").on("click","li",function(){
			var thisIndex=$(this).attr("date-id");
			if(!thisIndex) thisIndex= $(this).index();
			$(this).addClass("active").siblings("li").removeClass("active");
			$(".secondary").find(".right-content").hide().eq(thisIndex).show();
		});
		
		/*初始化详细页面*/
		$("#detail-left-tab").on("click","li",function(){
			var thisIndex=$(this).attr("date-id");
			if(!thisIndex) thisIndex= $(this).index();
			$(this).addClass("active").siblings("li").removeClass("active");
			$(".node_detail").find(".right-content").hide().eq(thisIndex).show();
		});
		
		//点击新增按钮
		$(".increase").on("click",function(){
			layerOpen({
                "title":"新增节点",
                "btnText":"保存",
                "btnColorClass":"g-btn-blue",
                "status" : false
            });
		});
		
		
		/*隐藏删除div操作*/
		$(".form-btn.cancel").on("click", function() {
			if (delLayer) {
				layer.close(delLayer);
			}else if (infoLayer) {
				layer.close(infoLayer);
			}
		});
		$(".form-btn.remove").on("click", function() {
			GlobalGLoading();
			/*执行删除操作*/
			if (delLayer) {
				layer.close(delLayer);
			}
			var baseURL=basePath+"/node/delete";
        	var size= $('#showSize option:selected').text();
        	if(main_node_idx.length>0){
        		$.ajax({
    				url : baseURL,
    				type : "POST",
    				data : {
    					"req" : main_node_idx.join(",")
    				},
    				success : function(data) {
    					if (data.state) {
    						GlobalShowMsg({showText:"操作成功",status:true});
    						var node_type = $("#type").val();
    						if($("#type").val()==-1) 
    							node_type="";
    						var node_id = $("#keyword").val();
    						var Page = {
        							"page":1,
        							"pageSize":size,
        							"node_model":node_type,
        							"node_id":node_id
        						};
	   						$.select(Page);
	   						GlobalGLoading();
        				}
    				}
    			});
        	}
		});
		
		/**备节点的新增、修改弹出框保存操作**/
		$("input[name='secondary_save']").on("click", function() {
			var primary_node_idx = $("#node_idx").val();
			var node_idx = $("#secondary_node_idx").val();
			var node_id = $("#secondary_node_id").val();
			var node_name = $("#secondary_node_name").val();
			var node_attributes = $("#secondary_node_attributes").val();
			var container_idx = $("#secondary_container_idx").val();
			var enable =$("#secondary_enable").val();
			if(!node_id){
				layer.tips('节点编号不能为空', '#secondary_node_id', {tips: [2, '#ff6666']});
				return;
			}else if(!node_name){
				layer.tips('节点名称不能为空', '#secondary_node_name', {tips: [2, '#ff6666']});
				return;
			}else if(!node_attributes){
				layer.tips('节点属性不能为空', '#secondary_node_attributes', {tips: [2, '#ff6666']});
				return;
			}else if(!container_idx){
				layer.tips('请选择所属容器', '#secondary_container_idx', {tips: [2, '#ff6666']});
				return;
			}else if(!enable){
				layer.tips('请选择是否启用', '#secondary_enable', {tips: [2, '#ff6666']});
				return;
			}
			$(".form-dialog-fix.w-900").fadeOut(100);
			$(".form-dialog-fix.w-900").find(".form-dialog").animate({
				"right":0
			});
			var baseURL=basePath+"/node/add";
			if (!primary_node_idx) {//编辑
				baseURL=basePath+"/node/update";
				if(!node_idx){
					return;
				}
				var param = {
					"node_idx" : node_idx,
					"primary_node_idx" : primary_node_idx,
					"node_id" : node_id,
					"node_name" : node_name,
					"node_attributes" : node_attributes,
					"container_idx" : container_idx,
					"enable":enable
				};
			}else{//新增
				var param = {
					"primary_node_idx" : primary_node_idx,
					"node_id" : node_id,
					"node_name" : node_name,
					"node_attributes" : node_attributes,
					"container_idx" : container_idx,
				};
			}
			var size= $('#showSize option:selected').text();
			$.ajax({
				url : baseURL,
				type : "POST",
				dataType: "json",
				data : {
					"req" : JSON.stringify(param)
				},
				success : function(data) {
					
					if (data.state) {
						
						GlobalShowMsg({showText:"操作成功",status:true});
						var node_type = $("#type").val();
						if($("#type").val()==-1) 
							node_type="";
						var node_id = $("#keyword").val();
						var Page = {
    							
    							
    						};
						$.select(Page);
						GlobalGLoading();
    				}
				},
				error:function(data){alert("error")}
			});
		});
		
		/**主节点的新增、修改弹出框保存操作**/
		$("input[name='primary_save']").on("click", function() {
			var node_idx = $("#primary_node_idx").val();
			var node_id = $("#primary_node_id").val();
			var node_name = $("#primary_node_name").val();
			var node_model = $("input:radio[name='primary_node_model']:checked").val();
			var node_type_idx = $("#primary_node_type_idx").val();
			var node_attributes = $("#primary_node_attributes").val();
			var container_idx = $("#primary_container_idx").val();
			var enable =$("#primary_enable").val();
			library_idx=[];
			node_relation=[];
			
			$("input:checkbox[name='library_idx']").each(function(i, n){
				if(n.checked) library_idx.push($(this).val());
	     	});
			var library_idxs=library_idx.join(",");
			
			$("input:checkbox[name='node_relation']").each(function(i, n){
				if(n.checked) node_relation.push($(this).val());
	     	});
			var node_relations=node_relation.join(",");
			
			var _relation=$("input:checkbox[name='node_relation']").length;
			
			if(!node_id){
				$("#NodeInfo").trigger("click");
				layer.tips('节点编号不能为空', '#primary_node_id', {tips: [2, '#ff6666']});
				return;
			}else if(!node_name){
				$("#NodeInfo").trigger("click");
				layer.tips('节点名称不能为空', '#primary_node_name', {tips: [2, '#ff6666']});
				return;
			}else if(!node_model){
				$("#NodeInfo").trigger("click");
				layer.tips('请选择节点类型', '#primary_node_model', {tips: [3, '#ff6666']});
				return;
			}else if(!node_type_idx){
				$("#NodeInfo").trigger("click");
				layer.tips('请选择业务类型', '#primary_node_type_idx', {tips: [2, '#ff6666']});
				return;
			}else if(!container_idx){
				$("#NodeInfo").trigger("click");
				layer.tips('请选择所属容器', '#primary_container_idx', {tips: [2, '#ff6666']});
				return;
			}else if(!library_idxs){
				var _offset=$("#library_idxs").offset();
				var _msg='服务图书馆列表不能为空!';
				$.alert($("#LibInfo"),_offset,_msg);
				return;
			/* }else if(!node_relations&&_relation>0){
				var _offset=$("#node_relations").offset();
				var _msg='关联节点列表不能为空!';
				$.alert($("#LinkInfo"),_offset,_msg);
				return; */
			}else if(!enable){
				$("#NodeInfo").trigger("click");
				layer.tips('请选择是否启用', '#primary_enable', {tips: [2, '#ff6666']});
				return;
			}
			
			$(".form-dialog-fix.w-900").fadeOut(100);
			$(".form-dialog-fix.w-900").find(".form-dialog").animate({
				"right":0
			});
			
			var baseURL=basePath+"/node/add";
			if (isEdit) {//编辑
				baseURL=basePath+"/node/update";
				var param = {
					"node_idx" : node_idx,
					"node_id" : node_id,
					"node_name" : node_name,
					"node_model" : node_model,
					"node_type_idx" : node_type_idx,
					"node_attributes" : node_attributes,
					"container_idx" : container_idx,
					"library_idxs" : library_idxs,
					"node_relations" : node_relations,
					"enable":enable
				};
			}else{//新增
				var param = {
					"node_id" : node_id,
					"node_name" : node_name,
					"node_model" : node_model,
					"node_type_idx" : node_type_idx,
					"node_attributes" : node_attributes,
					"container_idx" : container_idx,
					"library_idxs" : library_idxs,
					"node_relations" : node_relations,
					"enable":enable
				};
			}
			
			var size= $('#showSize option:selected').text();
			$.ajax({
				url : baseURL,
				type : "POST",
				dataType: "json",
				data : {
					"req" : JSON.stringify(param)
				},
				success : function(data) {
					
					if (data.state) {
						GlobalShowMsg({showText:"操作成功",status:true});
						var node_type = $("#type").val();
						if($("#type").val()==-1) 
							node_type="";
						var node_id = $("#keyword").val();
						var Page = {
    							"page":1,
    							"pageSize":size,
    							"node_model":node_type,
    							"node_id":node_id
    						};
						$.select(Page);
						GlobalGLoading();
    				}
				}
			});
		});
		
		jQuery.alert=function(obj,_offset,_msg){
			$(obj).trigger("click");
			layer.msg(_msg, {
			  time: 2000, //2s后自动关闭
			  offset: [_offset.top,_offset.left]
			});
		}
		
		/**取消新增、修改弹出框**/
		$("input[name='return']").on("click", function() {
			if (editlLayer) {
				layer.close(editlLayer);
			}
			if(createLayer){
				layer.close(createLayer);
			}
		});

		//上一页操作
		$("#page").on("click", ".prev-page", function() {
			GlobalGLoading();
			
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) - 1;
			var size = $('#showSize option:selected').html();
			var node_type = $("#type").val();
			if($("#type").val()==-1) 
				node_type="";

			var node_id = $("#keyword").val();
			var param={
				"node_model":node_type,
				"node_id":node_id,
				"page":page,
				"pageSize":size,
			};
			$.select(param);
		});
		//下一页操作
		$("#page").on("click", ".next-page", function() {
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) + 1;
			var size = $('#showSize option:selected').html();
			var node_type = $("#type").val();
			if($("#type").val()==-1) 
				node_type="";

			var node_id = $("#keyword").val();
			var param={
				"node_model":node_type,
				"node_id":node_id,
				"page":page,
				"pageSize":size,
			};
			$.select(param);
		});

		$("#page").on("click", "li", function() {
			if ($(this).hasClass("active"))
				return;
			var page = $(this).html();
			if (page == "...")
				return;

			var size = $('#showSize option:selected').text();
			var node_type = $("#type").val();
			if($("#type").val()==-1) 
				node_type="";

			var node_id = $("#keyword").val();
			var param={
				"node_model":node_type,
				"node_id":node_id,
				"page":page,
				"pageSize":size,
			};
			$.select(param);

		});

		/**
			每页显示的条目数切换
		 **/
		$("select#showSize").on("change", function() {
			GlobalGLoading();
			var page = $("#page").find("li.active").html();//当前页
			var size = $('#showSize option:selected').html();
			var node_type = $("#type").val();
			if($("#type").val()==-1) 
				node_type="";

			var node_id = $("#keyword").val();
			var param={
				"node_model":node_type,
				"node_id":node_id,
				"page":page,
				"pageSize":size,
			};
			$.select(param);
		});
		
		//图书馆列表全选
		 $("#checkall").click(function(){
			 _isCheck=$(this).is(":checked");
			 $("input:checkbox[name='library_idx']").each(function(i, n){
			 	if(_isCheck){
					$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
				}else{
					$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
				}
	     	});
		 });
		

		var isEdit = false;
		/**主节点--封装的弹出操作**/
		function layerOpen(options) {
			$(".secondary").hide();
			$(".node_detail").hide();
			$(".primary").show();
			var defaults = {
				title : "",
				btnText : "新增"
			}
			$(".primary .title .char").text(options.title);
			$.loadTypeList($("#primary_node_type_idx"));
			$.loadLibList($("#library_idxs"));
			$.loadNodeList($("#node_relations"));
			$.loadContainerList($("#primary_container_idx"));//"#secondary_container_idx"
			if (options.status) {
				isEdit = true;
				var param = {
	   				"node_idx" : options.index,
	   			};
	        	$.ajax({
	    			url : basePath + "/node/queryById",
	    			type : "POST",
	    			async: false,
	    			data : {
	    				"req" : JSON.stringify(param)
	    			},
	    			success : function(data) {
	    				if (data.state) {
	    					$("#primary_node_idx").val(data.result.node_idx);
	    					$("#primary_node_id").val(data.result.node_id);
	    					$("#primary_node_id").attr("disabled",true);
	    					$("#primary_node_name").val(data.result.node_name);
	    					if(data.result.node_model){
	    						$("input:radio[name='primary_node_model']").each(function (){
	    							if($(this).val()==data.result.node_model){
		    							$(this).attr("checked",true);
		    							$(this).parent().addClass("on").siblings("div").removeClass("on");
	    							}
	        				    });
	    					}
	    					$("#primary_node_type_idx").val(data.result.node_type_idx);
	    					//$("#primary_node_attributes").val(data.result.node_attributes);
	    					$("#primary_container_idx").val(data.result.container_idx);
	    					$("#primary_library_idxs").val(data.result.library_idxs);
	    					$("#primary_enable").val(data.result.enable);
	    					$("input:checkbox[name='library_idx']").parent().removeClass("on");
	    					$("#checkall").removeAttr("checked").parents(".g-checkbox").removeClass("on");
	    					if(data.result.library_idxs){
		    					library_idx=data.result.library_idxs.split(",");
		    					var i=library_idx.length;
		    					var j=$("input:checkbox[name='library_idx']").length;
		    					$(library_idx).each(function (i,item){
		    						$("input:checkbox[name='library_idx']").each(function (){
		    							if($(this).val()==item){
		    								$(this).attr("checked",true);
		    								$(this).parent().addClass("on");
		    							}
		        				    });
		    				    });
		    					if(i==j){
	    						 	$("#checkall").prop("checked",true).parents(".g-checkbox").addClass("on");
		    					}
	    					}
	    					$("input:checkbox[name='node_relation']").parent().removeClass("on");
	    					if(data.result.node_relations){
		    					node_idx=data.result.node_relations.split(",");
		    					$(node_idx).each(function (i,item){
		    						$("input:checkbox[name='node_relation']").each(function (){
		    							if($(this).val()==item){
		    								$(this).attr("checked",true);
		    								$(this).parent().addClass("on");
		    							}
		        				    });
		    				    });
	    					}
	    				}
	    			}
	        	});
			} else {
				//先全部清空选择过的数据
				//$("#primary_node_idx").removeAttr("disabled").val("");
				$("#primary_node_id").attr("disabled",false);
				$("#primary_node_id").val("");
				$("#primary_node_name").val("");
				$("input:radio[name='primary_node_model']").attr("checked",false).parent().removeClass("on");
				$("#primary_node_type_idx").val("");
				$("#primary_container_idx").val("");
				$("#primary_library_idxs").val("");
				$("#checkall").removeAttr("checked").parents(".g-checkbox").removeClass("on");
				$("input:checkbox[name='library_idx']").removeAttr("checked").parents(".g-checkbox").removeClass("on");
				$("input:checkbox[name='node_relation']").removeAttr("checked").parents(".g-checkbox").removeClass("on");
				$("#primary_enable").val("");
			}
			
			//清除tips
			$(".form-dialog").find("li").removeClass("error");
			//显示  新增模板基本信息填写
			$basicInfoLi=$(".form-dialog-fix.w-900").find("div.left-tab").find("ul").find(".basic-info");
			$basicInfoLi.removeClass("hide");
			$basicInfoLi.trigger("click");
			$(".right-form #primary_idx").removeClass("hide");
			$(".form-dialog-fix.w-900").find("#primary_idx").removeClass("hide");
			$(".form-dialog-fix.w-900").fadeIn(100);
			$(".form-dialog-fix.w-900").find(".form-dialog").animate({
				"right":0
			});
			
		};
		
		//加载节点类型列表
		jQuery.loadTypeList = function(obj) {//"#primary_node_type_idx"
			$.ajax({
				url : basePath + "/node/getTypeList",
				type : "POST",
				async: false,
				success : function(data) {
					if (data.state) {
						$(obj).empty();//清空下拉框
						var _html="<option value='' selected>---请选择---</option>";
						$.each(data.result,function(i, item) {
							_html+="<option value='"+item.node_type_idx+"'>"+item.node_type_name+"</option>";
						});
						$(obj).append(_html);
					}
				}
			});
		};
		
		//加载图书馆列表
		jQuery.loadLibList = function(obj) {//"#library_idxs"
			$.ajax({
				url : basePath + "/node/getLibList",
				type : "POST",
				async: false,
				success : function(data) {
					if (data.state) {
						$(obj).empty();//清空li
						var _html="";
						$.each(data.result,function(i, item) {
							_html+="<div class='com-item2'><div class='g-checkbox'><input type='checkbox' name='library_idx' value='"+item.library_idx+"'></div><label><span>"+item.lib_name+"</span></label></div>";
						});
						$(obj).append(_html);
					}
				}
			});
		};
		
		
		//加载节点列表
		jQuery.loadNodeList = function(obj) {//"#node_relations"
			$.ajax({
				url : basePath + "/node/getNodeList",
				type : "POST",
				async: false,
				success : function(data) {
					console.info(data);
					if (data.state) {
						$(obj).empty();//清空li
						var _html="";
						$.each(data.result,function(i, item) {
							_html+="<div class='com-item2'><div class='g-checkbox'><input type='checkbox' name='node_relation' value='"+item.node_idx+"'></div><label><span>"+item.node_id+"("+item.node_name+")</span></label></div>";
						});
						$(obj).append(_html);
					}
				}
			});
		};
		
		
		//加载容器列表
		jQuery.loadContainerList = function(obj) {//"#primary_container_idx"、"#secondary_container_idx"
			$.ajax({
				url : basePath + "/node/getContainerList",
				type : "POST",
				async: false,
				success : function(data) {
					if (data.state) {
						$(obj).empty();//清空li
						var _html="<option value='' selected>---请选择---</option>";
						$.each(data.result,function(i, item) {
							_html+="<option value='"+item.container_idx+"' port='"+item.container_port+"' path='"+item.save_path+"'>"+item.container_name+"</option>";
						});
						$(obj).append(_html);
					}
				}
			});
		};
		
		/*图书馆列表*/
		jQuery.findLibs = function(libs,obj) {
			if(libs){
				//加载图书馆列表
				$.ajax({
					url : basePath + "/node/getLibList",
					type : "POST",
					success : function(data) {
						if (data.state) {
							$(obj).empty();//清空li
							var _html="";
							$(libs).each(function (i,lib){
								$.each(data.result,function(i, item) {
									if(lib==item.library_idx){
										_html+="<item><label>"+item.lib_name+"</label></item>";
									}
								});
						    });
							$(obj).append(_html);
						}
					}
				});
			}
		}
		
		/*关联节点列表*/
		jQuery.findNodes = function(nodes,obj) {
			if(nodes){
				//加载节点列表
				$.ajax({
					url : basePath + "/node/getNodeList",
					type : "POST",
					success : function(data) {
						if (data.state) {
							$(obj).empty();//清空li
							var _html="";
							$(nodes).each(function (i,node){
								$.each(data.result,function(i, item) {
									if(node==item.node_idx){
										_html+="<item><label>"+item.node_name+"</label></item>";
									}
								});
						    });
							$(obj).append(_html);
						}
					}
				});
			}
		}
	
	});
	
	/**封装的查询方法**/
	jQuery.select = function(param) {
		$.ajax({
			url : basePath + "/node/queryByParam",
			type : "POST",
			data : {
				"req" : JSON.stringify(param)
			},
			success : function(data) {
				$("tbody").html("");
				if (data.state) {
					if (data.result.rows && data.result.rows.length == 0) {
						var index = param.page;
						if (index > 1) {
							param.page = index - 1;
							$.select(param);
						}
					} else {
						var flag=false;
						if(param.node_id){
							flag=true;
						}
						$.innerHtml(data.result.rows,flag);
					}
				}
				//$.pagination(data.result);
			}
		});
	};
	
	jQuery.innerHtml = function(rows,flag) {
		$.each(rows,function(i, item) {
			var model='';
			if(item.node_model=='view'){
				model='视图层';
			}else if(item.node_model=='business'){
				model='业务层';
			}else if(item.node_model=='db'){
				model='数据层';
			}
			/* var status='未启用';
			if(item.node_status=='1'){
				status='未启用';
			}else if(item.node_status=='0'){
				status='已启用';
			} */
			var attributes='';
			if(item.node_attributes=='primary'){
				attributes='主节点';
			}else if(item.node_attributes=='secondary'){
				attributes='备节点';
			}
			var _text="";
			if(flag){
				_text="<a class='item_code' href='#'>"+item.node_id+"</a>";
			}else{
				if(item.node_attributes=='primary'){
					_text="<i class='icon-table table-open'></i><a class='item_code' href='#'>"+item.node_id+"</a>";
				}else{
					if(i==rows.length-1){
						_text="<i class='icon-table table-end'></i><a class='item_code' href='#'>"+item.node_id+"</a>";
					}else{
						_text="<i class='icon-table table-include'></i><a class='item_code' href='#'>"+item.node_id+"</a>";
					}
				}
			}
			var _html="<span class='btn-a edit secondary-edit'>编辑</span><span class='btn-a delete'>删除</span>";
			if(item.node_attributes=='primary'){
				_html="<span class='btn-a edit primary-edit'>编辑</span><span class='btn-a delete'>删除</span><span class='btn-a g-btn-green create'>创建备节点</span>";
			}
			//+ "<td class='col8'><span class='btn-a detail lib' libs='"+item.library_idxs+"'>列表详情</span></td>"
			//+ "<td class='col8'><span class='btn-a detail node' nodes='"+item.node_relations+"'>列表详情</span></td>"
			var _enableHtml ="否";
			if(item.enable == 1){
				_enableHtml ="是";
			}
			
			$("tbody").append(
				"<tr>"
				+ "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.node_idx+"' /></div></td>"
				+ "<td class='col2'>"
				+ _text
				+ "</td>"
				+ "<td class='col3'>"
				+ item.node_name
				+ "</td>"
				+ "<td class='col4'>"
				+ attributes
				+ "</td>"
				+ "<td class='col5'>"
				+ model
				+ "</td>"
				+ "<td class='col6'>"
				+ item.node_type_name
				+ "</td>"
				+ "<td class='col7'>"
				+ item.container_name
				+ "</td>"
				+ "<td class='col8'>"
				+ _enableHtml
				+ "</td>"
				/* + "<td class='col8'>"
				+ status
				+ "</td>" */
				+ "<td class='col9'>"
				+ _html
				+ "</td>"
				+ "</tr>");
			
			if(item.secondaryList){
				$.innerHtml(item.secondaryList);
			}
		});
	};
	
	
	$(function() {
		var Page = {
			"page" : 1,
			"pageSize" : $('#showSize option:selected').text(),
		};
		$.select(Page);
	});
</script>
</body>
</html>
