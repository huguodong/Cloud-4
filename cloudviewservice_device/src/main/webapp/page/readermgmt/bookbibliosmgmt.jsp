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
		.equipment-leixing .form-wrap .g-select {
    		margin-right: 20px;
		} 
		.equipment-leixing .col2 {
    		min-width: 80px;
		}
		.equipment-leixing .col3 {
    		width: 120px;
		}
		.equipment-leixing .col4 {
    		width: 100px;
		}
		.equipment-leixing .col5 {
    		width: 120px;
		}
		.equipment-leixing .col6 {
    		width: 200px;
		}
		.equipment-leixing .col8 {
    		width: 300px;
		}
		.page-title-bar .g-select select {
		    padding-left: 5px;
		    width: 240px;
		    color: #888;
		}
		.page-title-bar .g-select {
		    float: left;
		    margin-left: 10px;
		    width: 150px;
		    height: 28px;
		    line-height: 28px;
		    border: 1px solid #dddddd;
		    border-radius: 2px;
		    background-color: #f6f6f6;
		}
		.page-title-bar .input, .page-title-bar .g-input {
		    float: left;
		    margin-left: 5px;
		    height: 28px;
		    line-height: 28px;
		}
		.g-table .btn-a.exportTemplate{
			background-color: #00a2e9;
		}
		.g-table .btn-a.importTemplate{
			background-color: #00a2e9;
		}
		.g-table .btn-a.exportTemplate:hover{
			background-color: #0090d3;
		}
		.g-table .btn-a.importTemplate:hover{
			background-color: #0090d3;
		}
		.increase-list {
		    position: absolute;
		    right: 50px;
		    top: 40px;
		    background: #27C178;
		    border-radius: 2px;
		    display: none;
		}
		.increase-list li a:hover {
    		background: #10a544;
		}
		.increase-list .icon {
		    width: 0px;
		    height: 0px;
		    border-left: 4px solid transparent;
		    border-right: 4px solid transparent;
		    border-bottom: 4px solid #27C178;
		    margin-top: -4px;
		    margin-left: 20px;
		}
		.increase-list li a {
		    height: 35px;
		    color: #fff;
		    line-height: 35px;
		    padding: 0 5px;
		    display: block;
		    width: 160px;
		}
		a{
    		cursor: pointer;
    		text-decoration: none;
    		color: #888888;
		}
		a:link{text-decoration:none ; color:white ;}
		a:visited {text-decoration:none ; color:white;}
		a:hover {text-decoration:underline ; color:white;}
		a:active {text-decoration:none ; colorwhite;} 
		a.current {color:red;}
	</style>
</head>
<body>
<div class="equipment-leixing">
	<div class="page-title-bar">
		<span class="title">书目馆藏模板管理</span>
		<div class="form-wrap fr">
			<div class="g-select">
					<select id="statType">
						<option value="-1" selected>---选择类型---</option>
						<!-- <option value="1" >学生导入配置模板</option>
						<option value="2" >教工导入配置模板</option> -->
						<option value="3" >馆藏导入配置模板</option>
						<!-- <option value="4" >书目馆藏导入配置模板</option> -->
					</select>
					<span class="arr-icon"></span>
			</div>
			<input type="text" name="keyword" id="Keyword_lib_idx" class="input g-input" placeholder="模板id或者图书馆名称" />
			<div class="btn search">查询</div> 
			<div class="btn increase increase-layer">
				<span class="add">新增</span>
				<div class="increase-list">
					<div class="icon"></div>
					<ul>
						<!-- <li><a href="javascript:template(1);"><strong>+</strong> 学生导入配置模板</a></li>
						<li><a href="javascript:template(2);"><strong>+</strong> 教工导入配置模板</a></li> -->
						<li><a href="javascript:template(3);"><strong>+</strong> 书目馆藏导入配置模板</a></li> 
						<!--<li><a href="javascript:template(4);"><strong>+</strong> 书目馆藏导入配置模板</a></li>-->
					</ul>
				</div>
			</div>
			<div class="btn delete">删除</div>
			<!-- <div class="btn" id="copyRow">复制</div> -->
			<!-- <div class="btn delete">删除</div> -->
		</div>
	</div>
	<!-- <div><input type="button" value="点击跳转" id="testPage"></div>  -->
	<!-- <url><li><a id="aaa" href="javascript:void(0)" data-url="/cloudviewservice_device/reader/commontongji?req=6">跳转入口</a></li></url>-->
	<div class="main">
		<table class="g-table g-table-v2">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox" ><input type="checkbox" name="" id="" /></div></th>
					<th class="col2" >ID</th>
					<th class="col3">模板类型</th>
					<th class="col4">模板名称</th>
					<th class="col5">图书馆名称</th>
					<th class="col6">简介</th>
					<th class="col8">操作</th>
					<th class="col9" style="display:none">操作</th>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
	</div>
	<!-- <a href="javascript:void(0)" id="chaxun"></a> -->
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
<div class="export-dialog dialog1 form-dialog">
	<div class="word">
		请选择将要导出模板 <span class="font-red">文件类型</span>？<br>
	</div>
	<div class="form-btn text g-btn-gray">Text</div>
	<div class="form-btn excel g-btn-green">Excel</div>
</div>
<div class="upload-dialog dialog1 form-dialog">
	<span class="line"></span>
	<div class="form-wrap">
<%-- <form id="uploadForm" action="${pageContext.request.contextPath}/database/bakUpload" enctype="multipart/form-data" method="post" target="nm_iframe"> 			
 --%>
 	请输入路径：	 		
 	<input type="file" name="file" id="file" accept=".csv,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,text/plain"/>
		<!--  </form> -->
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn upload g-btn-green">上传</div>
</div>
<div class="show-dialog dialog1 form-dialog" style="height:140px">
	 <ul>
	 	<li style="margin-top:10px"><b>成功数：</b><input type= "text" class="short-input"  id="successNum" disable/></li>
	 	<li style="margin-top:10px"><b>失败数：</b><input type= "text" class="short-input" id="failNum"  disable/></li>
	 	<li style="margin-top:10px;margin-left:-50px"><b>下载错误日志：</b><a id="download_error_log" class="current" href="javascript:void(0)" target="_blank"><b><u>点击下载</u></b></a></li>
	 	<li><div class="form-btn cancel g-btn-green" style="margin-top:20px">取消</div></li>
	 </ul>
</div>
<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red">7</span> 个项目<br>
		是否要删除选择的模板？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<div class="load-dialog">
	<span class="line"></span>
	<div class="load-gif"></div>
	<div class="word">请稍等，正在还原文件···</div>
</div>
<div id="formDiv"></div>
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

	var o = <shiro:principal/>;
	var lib_id = null;
	if(o.operator_type == 3){
		lib_id = o.lib_id;
	}
	/**
	删除按钮操作显示对话框
	**/
	var import_tpl_idx = new Array() ;
	//编辑模板
	var editData = {};
	$("tbody").on("click",".edit",function(){
		var o = <shiro:principal/>;
		var library_id = null;
		var library_idx = null;
		var operator_type = null;
		operator_type = o.operator_type;
		if(operator_type == 3){
			library_id = o.lib_id;
			library_idx = o.library_idx;
		}else{
		}
		import_tpl_idx.length = 0;
		import_tpl_idx.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		editData["import_tpl_idx"] = import_tpl_idx[0];
		editData["import_type"] = $(this).parent().parent().find("div input[name='idx']").val();//获取模板类型
		editData["library_id"] = $(this).parent().next().text();
		editData["library_idx"] = library_idx;
		editData["operator_type"] = operator_type;
		console.info("editData:",editData);
		var currentpage = $(".paging-bar li.active").text();
	    editData["currentpage"] = currentpage;
		$("#mainframe").load("${pageContext.request.contextPath }/configField/editimport_tpl_idx",{"req":JSON.stringify(editData)},function(){
			$(".form-dialog-fix .form-dialog").each(function(){
				$(this).attr("date-right",$(this).css("right"));
			});
			
		});
	});
	/**
	删除按钮操作显示对话框
	**/
	var import_tpl_idx = new Array() ;
	var libs={};
	main_lib_idx = [];
	var o = <shiro:principal/>;
	//$(".delete").on("click",function(){
	$(".btn.delete").on("click",function(){
		main_lib_idx = [];
		libs.isDelelteOne=false;
		libs.deleteIdxArr=new Array();
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			libs.deleteIdxArr.push($(this).attr("id"));
			main_lib_idx.push($(this).attr("id"));
		});  
		for(var d=0;d<main_lib_idx.length;d++){
			if(main_lib_idx[d] == o.library_idx){
				layer.alert("不能删除本馆信息！");
				return;
			}
		}
		console.info("libs.deleteIdxArr:",libs.deleteIdxArr);
		console.info("num----:",num+" "+num>0);
		if(num>0){
			$(".g-delete-dialog .word").html("");
			$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"</span>个项目<br>是否要删除选择的模板？");
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
	
	//删除当前行的数据
	$("tbody").on("click",".delete",function(){
		main_lib_idx =[] ;
		libs=new Array();
		libs.isDelelteOne=true;
		libs.deleteIdx=$(this).parent().parent().find("div input[name='idx']").attr("id");
		//libs.deleteId=$(this).parent().parent().children("td:nth-child(2)").text();
		//libs.version_stamp=$(this).parents("tr").find(".version_stamp").html();
		main_lib_idx.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		$(".g-delete-dialog").find(".word").html(
		'是否删除 <span class="font-red">'+$(this).parents("tr").find("td:eq(1)").html()+'</span>');
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
	
	/**
	删除按钮操作
	**/
	$(".form-btn.remove").on("click",function(){
		var Library_idx = main_lib_idx;
		if(!Library_idx)  return;
		var idx = libs.deleteIdxArr;
		var param=new Array();
		if(libs.isDelelteOne==true){
			if(libs.deleteIdx){
				param[0]={
						"import_tpl_idx":libs.deleteIdx,
						};
			}
		}else{
			for(var i=0;i<idx.length;i++){
				param[i]={
				"import_tpl_idx":idx[i],
				};
			}
		}
		console.info("param:",param);
		$.ajax({
			url:"${pageContext.request.contextPath }/configField/deleteImportTemplate",
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
				var statType = $("#statType").val();
				if(statType ==-1){
					statType="";
				}
		     	var currentpage = $(".paging-bar li.active").text();
		     	var size= $('#pagesize option:selected').text();
		     	var str=$("#Keyword_lib_idx").val();
				var Page={"page":currentpage,"pageSize":size,"import_tpl_type":"3","import_tpl_desc":"","type":"bookbiblios","lib_id":lib_id};
				//调用页面的查询ajax
				$.select(Page);  
		     }else if(data.retMessage){
		     	showRetMessage(data.retMessage);
		     }
			}
		});
	
	});
	
	//加载初始化页面
	var startpage = '${page}';
	$(document).ready(function(){
		var size= $('#pagesize option:selected').text();
		if(startpage ==null || startpage.length <=0){
            startpage="1";
        }
		
		var Page={"page":startpage,"pageSize":size,"import_tpl_type":"3","import_tpl_desc":"","type":"bookbiblios","lib_id":lib_id};
		$.ajax({
			url:basePath+"/configField/selectConfigTemplatePage",
			type:"POST",
			data:{"req":JSON.stringify(Page)},
			async:false,
			success:function(data){
				console.info("data:",data);
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					var type="";
					/* if(item.import_tpl_type=="1"){
						type="学生导入配置模板";
					}else if(item.import_tpl_type=="2"){
						type="教工导入配置模板";
					}else  */if(item.import_tpl_type=="3"){
						type="书目馆藏导入配置模板";
					}else if(item.import_tpl_type=="4"){
						type="书目馆藏导入配置模板";
					}else{
						return true;
					}
					var value=item.import_tpl_value;
					var json = {};
					var templateName;
					if(value !=null){
						json = JSON.parse(value);
						if(value.length >100){
						 value = value.substring(0,60)+"...}"+"<textarea hidden>"+value+"</textarea>";
						}
						templateName = json.headerData.templateName;
						//console.info("templateName",templateName);
					}
					$("tbody").append(
					"<tr>"+
	            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.import_tpl_idx+"' value='"+item.import_tpl_type+"'/></div></td>" +
	            	"<td class='col2'>" + item.import_tpl_id + "</td>" +
	            	"<td class='col3'>" + type + "</td>" +
	           		"<td class='col4'>" + templateName + "</td>" +
	           		"<td class='col5'>" + item.lib_name + "</td>" +
	           		"<td class='col6'>" + value + "</td>" +
	                "<td><span class='btn-a edit'>编辑</span>"+
	                "<span id='delete' class='btn-a delete'>删除</span>" +
	                "<span class='btn-a exportTemplate'>导出模板</span>" +
	                "<span class='btn-a importTemplate'>导入数据</span></td>" +
	                "<td class='col9' style='display:none'>" + item.lib_id + "</td>"+
	                "</tr>");
	            });
				
	    		$.pagination(data.result);
			}
       });
     }); 
	
	//新增列表
	$(".increase-layer .add").on("click",function(){
		$(this).next().toggle();
	});
	
	//封装查询ajax
	jQuery.select=function(Page){
		$.ajax({
			url:"${pageContext.request.contextPath}/configField/selectConfigTemplatePage",
			type:"POST",
			data:{"req":JSON.stringify(Page)},
			success:function(data){
				console.info("data:",data);
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					var type="";
					/* if(item.import_tpl_type=="1"){
						type="学生导入配置模板";
					}else if(item.import_tpl_type=="2"){
						type="教工导入配置模板";
					}else */ if(item.import_tpl_type=="3"){
						type="书目馆藏导入配置模板";
					}else if(item.import_tpl_type=="4"){
						type="书目馆藏导入配置模板";
					}else{
						return true;
					}
					var value=item.import_tpl_value;
					if(value !=null){
						json = JSON.parse(value);
						if(value.length >100){
						 value = value.substring(0,60)+"...}"+"<textarea hidden>"+value+"</textarea>";
						}
						templateName = json.headerData.templateName;
					}
					$("tbody").append(
					"<tr>"+
	            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.import_tpl_idx+"' value='"+item.import_tpl_type+"'/></div></td>" +
	            	"<td class='col2'>" + item.import_tpl_id + "</td>" +
	            	"<td class='col3'>" + type + "</td>" +
	           		"<td class='col4'>" + templateName + "</td>" +
	           		"<td class='col5'>" + item.lib_name + "</td>" +
	           		"<td class='col6'>" + value + "</td>" +
	                "<td><span class='btn-a edit'>编辑</span>"+
	                "<span id='delete' class='btn-a delete'>删除</span>" +
	                "<span class='btn-a exportTemplate'>导出模块</span>" +
	                "<span class='btn-a importTemplate'>导入数据</span></td>" +
	                "<td class='col9' style='display:none'>" + item.lib_id + "</td>"+
	                "</tr>");
	            });
				
	    		$.pagination(data.result);
			}
		});
	};
	
	$(".increase-layer").hover(function(){
		//移上去逻辑
		//$(".increase-list").css("display","block");
	},function(){
		//移出逻辑
		$(".increase-list").css("display","none");
	});
	
	$(".increase-list").hover(function(){
		$(".increase-list").css("display","block");
	},function(){
		//移出逻辑
		$(".increase-list").css("display","none");
	});
	
	//上一页操作
	$("#page").on("click",".prev-page",function(){
		var statType = $("#statType").val();
		if(statType ==-1){
			statType="";
		}
		var currentpage = $(".paging-bar li.active").text();
		var page=Number(currentpage)-1;
		var size= $('#pagesize option:selected').text();
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":page,"pageSize":size,"import_tpl_type":"3","import_tpl_desc":str,"type":"bookbiblios","lib_id":lib_id};
		$.select(Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var statType = $("#statType").val();
		if(statType ==-1){
			statType="";
		}
		var currentpage = $(".paging-bar li.active").text();
		page = Number(currentpage) + 1;
		var size= $('#pagesize option:selected').text();
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":page,"pageSize":size,"import_tpl_type":"3","import_tpl_desc":str,"type":"bookbiblios","lib_id":lib_id};
		$.select(Page);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var statType = $("#statType").val();
		if(statType ==-1){
			statType="";
		}
		var size= $('#pagesize option:selected').text();
		var page = $(this).html();
		if(page=="...") return;
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":page,"pageSize":size,"import_tpl_type":"3","import_tpl_desc":str,"type":"bookbiblios","lib_id":lib_id};
		$.select(Page);
		
	}); 
	
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var statType = $("#statType").val();
		if(statType ==-1){
			statType=null;
		}
		var Page;
		var str=$("#Keyword_lib_idx").val();
		var size= $('#pagesize option:selected').text();
		if((statType==null||statType==undefined||statType=="")&&(str==null||str==undefined||str=="")){
			Page={"page":"1","pageSize":size,"import_tpl_type":"3","import_tpl_desc":str,"type":"bookbiblios","lib_id":lib_id};
		}else{
			Page={"page":"1","pageSize":size,"import_tpl_type":statType,"import_tpl_desc":str,"type":"","lib_id":lib_id};
		} 
		$.select(Page);
	});
	
	/**
	每页显示的条目数切换
	**/
	$("select#pagesize").on("change",function(){
		var statType = $("#statType").val();
		if(statType ==-1){
			statType="";
		}
		var size= $('#pagesize option:selected').text();
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":1,"pageSize":size,"import_tpl_type":"3","import_tpl_desc":str,"type":"bookbiblios","lib_id":lib_id};
		$.select(Page);
	});
});
	var bakUpload={};
	bakUpload.isClicked=false;
	bakUpload.ShowDialog;
	bakUpload.uploadDialog;
	var importState = false;
	var import_tpl_idx,import_tpl_type;
	//$(".importTemplate").on("click",function(){
	$("tbody").on("click",".importTemplate",function(){
		import_tpl_idx = $(this).parent().parent().find("td[class='col1']").find("input").attr("id");
		import_tpl_type = $(this).parent().parent().find("td[class='col1']").find("input").val();
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
	var oper=<shiro:principal/>;
	//确认上传
	$("div.upload-dialog").on("click",".form-btn.upload",function(){
			console.info("import_tpl_idx:",import_tpl_idx);
			console.info("import_tpl_type:",import_tpl_type);
			var library_idx=null;
			var lib_id = null;
			/* if(oper.operator_type>=3){
				library_idx=oper.library_idx;
				lib_id = libIdxAndNameObj[library_idx].lib_id;
			} else{
				showRetMessage("请使用图书馆管理员账号进行操作！");
				return;
			} */
		   if(bakUpload.isClicked){return;}
		   bakUpload.isClicked=true;
	
		   var fileName = getPath( document.getElementById("file"));
		   if(!$("#file").val()){
		   		layer.alert("请选择文件后上传");
		   		bakUpload.isClicked=false;
		   		return;
		   }
		   /* else if(!importState && fileName.substring(fileName.length-3,fileName.length) != "xls"){
			   layer.alert("请选择.csv文件上传");
			   bakUpload.isClicked=false;
		   		return;
		   } */
		   var dialogIndex = loadingDialog({
				loadText:"请稍等，上传中..."
		   }); 
		   var param = {"import_tpl_idx":import_tpl_idx,"import_tpl_type":import_tpl_type};
		   if(!importState){
			   $.ajaxFileUpload({
				   	 url:"${pageContext.request.contextPath}/reader/uploadReaderFile",
				   	 secureuri :false,
					 fileElementId :'file',//file控件id
					 data:{
						 "import_tpl_idx":import_tpl_idx,
						 "import_tpl_type":import_tpl_type	 
					 },
					 type:'post',
					 dataType:'json',
					 success : function (data,status){
						 if(data){
							 	console.info("data:",data);
						    	var reg=new RegExp("<[^>]*>","gi");
						     	var dateTxt=data.replace(reg,"").replace(reg,"");
						     	
						     	var obj=JSON.parse(dateTxt);
						     	console.log("flag:",obj.state);
						     	if(obj.state){
							        layer.close(dialogIndex);
							        bakUpload.isClicked=false;
								    layer.close(bakUpload.uploadDialog);
								    //GlobalShowMsg({showText:"上传成功",status:true});
								    bakUpload.ShowDialog = layer.open({
										type: 1,
										shade: false,
										title: false, //不显示标题
										scrollbar :false,
										closeBtn :0,
										shade:0.5,
										shadeClose :false,
										area:["480px"],
										offset :["220px"],
										content: $('.show-dialog'), //捕获的元素
										success : function(index,layero){
											//ShowDialog.isClicked=true;
											$("#successNum").val(obj.result.successNum);
											$("#failNum").val(obj.result.failNum);
											var url = "../reader/downloadReaderErrorLog?fileName="+obj.result.fileName;
											$("#download_error_log").attr("href",url);
											$(".cancel").on("click",function(){
												layer.close(bakUpload.ShowDialog);
											});  
										},
										end:function(){
											$(".cancel").unbind("click");
											$(".backup-btn").unbind("click");
										}
									});
						     	}	
						     }
									   
					},
					 error: function(data, status, e){
					       bakUpload.isClicked=false;
					       layer.close(bakUpload.uploadDialog); 	
					 } 
				});
		   }
	})
	function getPath(obj) {
        if(obj)
    {  
        if (window.navigator.userAgent.indexOf("MSIE")>=1)
       {
           obj.select();
           return document.selection.createRange().text;
       }   
        else if(window.navigator.userAgent.indexOf("Firefox")>=1)
        {
           if(obj.files)
            {
                   return obj.files.item(0).getAsDataURL();
             }
             return obj.value;
        }
       return obj.value;
   		}
	}
	function loadingDialog(options){
		var defaults = {
			loadText:"正在删除···"
		};
		defaults = $.extend(defaults,options);

		$('.load-dialog .word').text(defaults.loadText);

		var dialogIndex = layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.load-dialog'), //捕获的元素
			success : function(index,layero){
			
			}
		});

		return dialogIndex;
	}
	bakUpload.ExportDialog;
	var configInfo;
	var importType;
	var dataFilter;
	var postData = {};
	//$(".exportTemplate").click(function(data){
	$("tbody").on("click",".exportTemplate",function(){
		var import_tpl_idx = $(this).parent().parent().find("td[class='col1']").find("input").attr("id");
		configInfo = $(this).parent().prev().find("textarea").text();
		bakUpload.ExportDialog=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.export-dialog'), //捕获的元素
			success : function(index,layero){
				postData["import_tpl_idx"] = import_tpl_idx;
				console.info("import_tpl_id:",import_tpl_idx);
				
				$(".export-dialog .text").on("click",function(){
					if(bakUpload.ExportDialog){
						layer.close(bakUpload.ExportDialog);
						configJson = JSON.parse(configInfo);
						dataFilter = configJson.searchinfo[0].dataFilter;  
						importType = "text";
						
						findImportTemplateById();
					}
				});  
				$(".export-dialog .excel").on("click",function(){
					if(bakUpload.ExportDialog){
						layer.close(bakUpload.ExportDialog);
						configJson = JSON.parse(configInfo);
						dataFilter = configJson.searchinfo[0].dataFilter; 
						importType = "excel";
						
						findImportTemplateById();
					}
				}); 
			},
			end:function(){
				$(".export-dialog .text").unbind("click");
				$(".export-dialog .backup-btn").unbind("click");
			}
		});
	});
	
	function findImportTemplateById(){
		console.info("postData",postData);
		$.ajax({
			url:"${pageContext.request.contextPath}/reader/findImportTemplateById",
			type:"POST",
			data:{"req":JSON.stringify(postData)},
			async:false,
			success:function(data){
				var wData = JSON.parse(data.result.import_tpl_value);
				console.info("JSON.stringify(wData)",JSON.stringify(wData));
				var tsearchArray = jQuery.makeArray(wData.searchinfo);
				console.info("tsearchArray",tsearchArray);
				var searchArray = new Array();
				for(var t1=0;t1<tsearchArray.length;t1++){
					   //searchArray.push(tsearchArray[t1].toString().replace(/^"|"$/g, ""));
					   searchArray.push(tsearchArray[t1]);
				}
				console.info("searchArray",searchArray);
				var cNameData = new Array();
				
				for(var s=0;s<searchArray.length;s++){
					var tmp = searchArray[s];
					console.info("tmp:",tmp);
					var data_source_select = tmp.data_source_select.split(" ");
					var cloumnRank = tmp.columnRank;
					var rkNameJson = {};
					rkNameJson["data_source_select"]=data_source_select[1];
					rkNameJson["cloumnRank"]=cloumnRank;
					//console.info("data_source_select",data_source_select);
					cNameData.push(JSON.stringify(rkNameJson));
				}
				console.info("cNameData:",cNameData);
				
				var pd = {};
				pd["data_source_select"] = "["+cNameData+"]";
				pd["importType"] = importType;
				pd["dataFilter"] = dataFilter;
				console.info("pd",pd);
				var timestamp = Date.parse(new Date()); 
				$("#formDiv").html("");
				var html = "<form action='../configField/exportConfigTemplate?time="+timestamp+"' method='post' id='form1' name='form1' style='display:none'>";  
				html += "<input type='hidden' name='reqName' value='"+JSON.stringify(pd)+"'/>";  
				html += "</form>";
				$("#formDiv").append(html);
				$("#form1").submit();
				var currentpage = $(".paging-bar li.active").text();
		     	var size= $('#pagesize option:selected').text();
		     	var str=$("#Keyword_lib_idx").val();
				var Page={"page":currentpage,"pageSize":size,"import_tpl_type":"3","import_tpl_desc":str,"type":"bookbiblios"};
				console.info("Page:",Page);
				//调用页面的查询ajax
				$.select(Page); 
			}
		});
	}

	function template(import_tpl_type){
			var o = <shiro:principal/>;
			var library_id = null;
			var library_idx = null;
			var operator_type = null;
			operator_type = o.operator_type;
			if(operator_type == 3){
				library_id = o.lib_id;
				library_idx = o.library_idx;
			}else{
			}
			//console.info("o:",o);
		    var currentpage = $(".paging-bar li.active").text();
		    var endpage = $(".total-page.fr").html().substring(1,2);
		    //console.info("library_id:",library_id);
		    //console.info("library_idx:",library_idx);
		    $("#mainframe").load("${pageContext.request.contextPath}/reader/template?currentpage="+currentpage+"&endpage="+endpage+"&import_tpl_type="+import_tpl_type+"&library_id="+library_id+"&library_idx="+library_idx+"&operator_type="+operator_type,"",function(){
		        $(".form-dialog-fix .form-dialog").each(function(){
		            $(this).attr("date-right",$(this).css("right"));
		        });
		    });
	}
</script>