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
        	新建集合
        	<input type="hidden" id="serverId" value="${server.id}"/>
        	<input type="hidden" id="databaseName" value="${collection.databaseName }"/>
        </div>
        <div class="main">
           <div class="addtable">
           		<div class="tabnameinput">
           			请输入集合名称：<input type="text" name="tableName" id="tableName" class="tablename" placeholder="填写集合名称" /><button type="submit" class="saveTableBtn" onclick="saveTable()">></button>
           		</div>
			</div>
        </div>
		<%-- <%@ include file="../include/page_bar.jsf" %> --%>
</div>


<script type="text/javascript">
function saveTable(){
	var tableName = $("#tableName").val();
	var dbname = $("#databaseName").val();
	if(!tableName){
		GlobalShowMsg({showText:"集合名不能为空",status:false});
		return;
	}
	
	var table = {
			"name" : tableName,
			"databaseName" : dbname,
			"server_id" : $("#serverId").val()
		};
	
	$.ajax({
		url:"${pageContext.request.contextPath}/collection/addcollectionAction",
		type:"POST",
		data:{"req":JSON.stringify(table)}
	}).done(function(data){
		if(data!=null && data.state){
			GlobalShowMsg({showText:"操作成功",status:true});
			//toNewCollection(dbname,tableName);
		}else{
			GlobalShowMsg({showText:"操作失败",status:false});
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
      })();
  });
    
  
	
	
</script>
</body>
</html>
