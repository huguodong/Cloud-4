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
    <title>数据表管理</title>
</head>
<body>
<div class="equipment-guanli">
        <div class="page-title-bar">
        	新建数据表
        	<input type="hidden" id="serverId" value="${server.id}"/>
        	<input type="hidden" id="databaseName" value="${table.databaseName }"/>
        </div>
        <div class="main">
           <div class="addtable">
           		<div class="controlDiv"><button type="button" class="controlBtnUpDown" id="closeTableInput">▲</button>&nbsp;&nbsp;<button type="button" class="controlBtnUpDown" id="openTableInput">▼</button></div>
           		<div class="tabnameinput" id="tabnameinput">
           			请输入数据表名称：<input type="text" name="tableName" id="tableName" class="tablename" placeholder="填写数据表名称" /><button type="submit" class="saveTableBtn" onclick="saveTable()">></button>
           		</div>
           		<div class="controlDiv"><button type="button" class="controlBtnUpDown" id="closeExcuteSqlDiv">▲</button>&nbsp;&nbsp;<button type="button" class="controlBtnUpDown" id="openExcuteSqlDiv">▼</button></div>
           		<div class="excuteSqlDiv" id="excuteSqlDiv">
           			<textarea class="sqltextarea" id="sqltxt" placeholder="执行sql文本域"></textarea><button type="submit" class="saveTableBtn" onclick="excuteSql()">执行</button>
           		</div>
           		<div class="controlDiv"><button type="button" class="controlBtnUpDown" id="closeSqlResultDiv">▲</button>&nbsp;&nbsp;<button type="button" class="controlBtnUpDown" id="openSqlResultDiv">▼</button></div>
           		<div class="sqlResultDiv" id ="sqlResultDiv">
           			<span id = "successInfo"></span>
           			<table class="g-table">
		                <thead>
		                </thead>
		                <tbody>
		                </tbody>
	               	</table>
           		</div>
			</div>
        </div>
		<%-- <%@ include file="../include/page_bar.jsf" %> --%>
</div>


<script type="text/javascript">
$("#closeTableInput").on("click", function() {
	$("#tabnameinput").hide();
});

$("#openTableInput").on("click", function() {
	$("#tabnameinput").show();
});

$("#closeExcuteSqlDiv").on("click", function() {
	$("#excuteSqlDiv").hide();
});

$("#openExcuteSqlDiv").on("click", function() {
	$("#excuteSqlDiv").show();
});

$("#closeSqlResultDiv").on("click", function() {
	$("#sqlResultDiv").hide();
});

$("#openSqlResultDiv").on("click", function() {
	$("#sqlResultDiv").show();
});

function saveTable(){
	var tableName = $("#tableName").val();
	var dbname = $("#databaseName").val();
	if(!tableName){
		GlobalShowMsg({showText:"表名不能为空",status:false});
		return;
	}
	
	var table = {
			"name" : tableName,
			"databaseName" : dbname,
			"server_id" : $("#serverId").val()
		};
	
	$.ajax({
		url:"${pageContext.request.contextPath}/table/addtabAction",
		type:"POST",
		data:{"req":JSON.stringify(table)}
	}).done(function(data){
		if(data!=null && data.state){
			GlobalShowMsg({showText:"操作成功",status:true});
			//toNewTable(dbname,tableName);
		}else{
			GlobalShowMsg({showText:"操作失败",status:false});
		}
	});
}


function excuteSql(){
	var sqltxt = $("#sqltxt").val();
	var dbname = $("#databaseName").val();
	if(!tableName){
		return;
	}
	
	var table = {
			"databaseName" : dbname,
			"server_id" : $("#serverId").val()
		};
	
	var param={
			"json":JSON.stringify(table),
			"sqltxt":JSON.stringify(sqltxt)
		};
	
	$.ajax({
		url:"${pageContext.request.contextPath}/table/excuteSql",
		type:"POST",
		data:{"req":JSON.stringify(param)}
	}).done(function(data){
		if(data!=null && data.state){
			$("#successInfo").text("");
			if(data.result == null || data.result.length == 0){
				$("#successInfo").text("操作成功");
			}else{
				$("thead").html("");
				$("tbody").html("");
				if (data.state) {
					var columns = data.result.column;
					if(columns && columns.length == 0){
						return;
					}else{
						//加载列
						var _html = "<tr>";
						for(var key in columns){
							_html += "<th class=\"col"+key+"\">"+columns[key]+"</th>";
						}
						_html += "</tr>";
						$("thead").append(_html);
						var values = data.result.value;
						if(values && values.size == 0){
							return;
						}else{
							$.each(values,function(i, item) {
								var html_ = "<tr>";
								for(var key in columns){
									html_ += "<td class='col3'>"+item[key]+"</td>";
								}
								html_ += "</tr>";
								$("tbody").append(html_);
							});
						}
					}
				}
			}
			$("#tabnameinput").hide();
			$("#excuteSqlDiv").hide();
			$("#sqlResultDiv").show();
		}else{
			var message = data.message;
			if(message){
				layer.alert(message);
			}else{
				GlobalShowMsg({showText:"操作失败",status:false});
			}
		}
	});
}

  $(function(){
      (function mainHeightController(){
          var winH = $(window).height();
          var headerH = $(".g-header").outerHeight();
          var pageTitleBar = $(".page-title-bar").outerHeight();
          var pagingBarH = $(".paging-bar").outerHeight();
          var location = (window.location+'').split('/');  
	basePath = location[0]+'//'+location[2]+'/'+location[3];
          $(".main").css({
              "min-height":winH - headerH - pagingBarH -pageTitleBar
          });
          $("#sqlResultDiv").hide();
      })();
  });
    
  
	
	
</script>
</body>
</html>
