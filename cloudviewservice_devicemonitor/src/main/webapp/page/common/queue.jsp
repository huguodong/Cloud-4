<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<%@ include file="../common/global.jsf" %>
<style type="text/css">
</style>
</head>
<body>
<div class="system-databackup">
<%@include file="../common/header.jsf" %>
<div class="g-loading"></div>
	<div class=""><%--main-content --%>
		<div class="page-title-bar">
			<span class="title">数据监控<a href="help-page.html" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-select">
						<select id="selectQueue" class="">
							<option value="">请选择</option>
							<option  value="heartBeatChangeTableData">heartBeatChangeTableData</option>
							<option  value="heartBeatDeviceOrder">heartBeatDeviceOrder</option>
							<option  value="heartBeatMidVersionData">heartBeatMidVersionData</option>
							<option  value="controlResultContainer">controlResultContainer</option>
							<option  value="uploadRunLogContainer">uploadRunLogContainer</option>
							<option  value="queryRunningLogOrder">queryRunningLogOrder</option>
						</select>
						<span class="arr-icon"></span>
				</div>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
					<!-- 	<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="col2">名称</th>
						<th class="col3">文件大小</th>
						<th class="col4">备份时间</th>
						<th class="col5">操作</th> -->
					</tr>
				</thead>
				<tbody>
				<%-- <tr>
					<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
					<td class="col2">海恒智能数据库备份</td>
					<td class="col3">60mb</td>
					<td class="col4">2013-2-20 13:20:50</td>
					<td class="col5">
						<span class="btn-a download g-btn-green">下载</span>
						<span class="btn-a restore g-btn-yellow">还原</span>
						<span class="btn-a delete">删除</span>
					</td>
				</tr> --%>
				</tbody>
			</table>
			<div class="log-area">
					
			</div>
		</div>
	
		<%@include file="../include/page_bar.jsf" %>
	</div>
</div>
<div class="delete-dialog dialog1">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red">7</span> 个项目<br>
		是否要删除选择的配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<div class="upload-dialog dialog1 form-dialog">
	<span class="line"></span>
	<div class="form-btn upload g-btn-green">上传</div>
</div>
<iframe id="id_iframe" name="nm_iframe" style="display:none;"></iframe>

<div class="backup-dialog dialog1 form-dialog">
	<span class="line"></span>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left">数据库类型</div>
				<div class="right">
					<div class="g-select">
						<select id="dbType-sel" class="dbType need-change">
							<option  value="MySQL">MySQL</option>
							<option  value="MongoDB">MongoDB</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li>
				<div class="left">数据库名</div>
				<div class="right">
					<div val="MySQL" date-id="dbType-sel" class="g-select">
						<select  class="dbName">
							<option value="ssitcloud_device">设备库</option>
							<option value="ssitcloud_authentication">用户鉴权库</option>
						</select>
						<span class="arr-icon"></span>
					</div>
					<div val="MongoDB" date-id="dbType-sel" class="g-select">
						<select class="dbName">
								
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<div class="word">
		<!-- 是否要对当前的数据库进行备份？<br> -->
		<span class="w1">上一次备份：2013-2-50 15:50:40</span>
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn backup-btn g-btn-green">备份</div>
</div>
<%--还原对话框 --%>
<div class="restore-dialog dialog1">
	<span class="line"></span>
	<div class="word">
		是否还原选中的数据库文件？<br>
		<span class="w1">海恒智能数据库备份 2013-2-3 13:20:50</span>
	</div>

	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn restore-btn g-btn-yellow">还原</div>
</div>
<div class="load-dialog">
	<span class="line"></span>
	<div class="load-gif"></div>
	<div class="word">请稍等，正在还原文件···</div>
</div>
<div class="result-state dialog1">
	<span class="line"></span>
	<div class="status-icon"></div>
	<div class="status-word">数据库备份成功</div>
	<div class="w1">你可以请重新尝试备份，或者联系<br>系统管理员解决问题。</div>
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
	
	/**
			<tr>
					<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
					<td class="col2">海恒智能数据库备份</td>
					<td class="col3">60mb</td>
					<td class="col4">2013-2-20 13:20:50</td>
					<td class="col5">
						<span class="btn-a download g-btn-green">下载</span>
						<span class="btn-a restore g-btn-yellow">还原</span>
						<span class="btn-a delete">删除</span>
					</td>
				</tr>
	**/
	$tbody=$("div.main").find(".g-table").find("tbody");
	var drawRow=function(rows){
		if(!rows){return;}
		var tbody='';
		for(var i=0;i<rows.length;i++){
			var tr='<tr>';
			var row=rows[i];
			tr+='<td class="col5">';
			tr+=JSON.stringify(row);
			tr+='</td>';
			tr+='</tr>';
			tbody+=tr;
		}
		$tbody.html(tbody);
	};
	
	
	var selectBakPage=function(obj){
		$.ajax({
			url:"${pageContext.request.contextPath}/device/QueryContainerInfo",
			type:"POST",
			data:{"req":JSON.stringify(obj)}
		}).done(function(data){
			if(data){
				var page=data.result;
				console.log("QueryContainerInfo:"+JSON.stringify(data));
				
				if(page){
					
					//$(".log-area").html(JSON.stringify(page));
					//console.log(page.rows);
					//drawRow(page.rows);
					//$.pagination(page);
					drawRow(page);
				}
			}
		});
	};
	//组装 翻页和查询 参数
	var makeQueryParam=function(page,pageSize){
		var startDate=$("input[name=startDate]").val();
		var endDate=$("input[name=endDate]").val();
		var Page ={
			"page":page,
			"pageSize":pageSize,
			//开始时间 结束时间
			"startDate":startDate,
			"endDate":endDate
		};
		//alert(keyWord);
		return Page;
	};
	var pageSize=10;
	//下一页操作
	$("div.paging-bar").on("click",".next-page",function(){
		var currentpage = $("#page").find("li.active").html();//当前页
		page = Number(currentpage) + 1;//下一页
		var Page=makeQueryParam(page, pageSize);
		selectBakPage(Page);
	});
	//上一页操作
	$("div.paging-bar").on("click",".prev-page",function(){
		var currentpage = $("#page").find("li.active").html();
		var page=Number(currentpage)-1;
		var Page=makeQueryParam(page, pageSize);
		//带参数
		selectBakPage(Page);
	});
	$("div.paging-bar").on("click","li",function(){
		if($(this).hasClass("active")) return;
		var page = $(this).html();
		if(page=="...") return;	
		var Page=makeQueryParam(page, pageSize);
		selectBakPage(Page);
	});
	//var Page={"page":1,"pageSize":pageSize};
	
	//selectBakPage({"container_name":"heartBeatChangeTableData"});
	$("#selectQueue").on("change",function(){
		var container_name=$(this).val();
		if(container_name){
			selectBakPage({"container_name":container_name});
		}
	});
	/**
		查询操作
	**/
	$("div.btn.search").on("click",function(){
			//layer.alert("查询");
		//var page = $("#page").find("li.active").html();
		var page=1;
		var Page=makeQueryParam(page, pageSize);
		selectBakPage(Page);
	});
	
});
</script>
</body>
</html>


