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
        	<input type="hidden" id="serverId" value="${server.id}"/>
        	<input type="hidden" id="databaseName" value="${table.databaseName }"/>
        	<input type="hidden" id="tableName" value="${table.name}"/>
            <span class="title">Server：${server.host}&nbsp;&nbsp;&nbsp;&nbsp;DateBase：${table.databaseName }&nbsp;&nbsp;&nbsp;&nbsp;Table：${table.name}<a href="${pageContext.request.contextPath}/static/help-page.html" target="_blank" class="g-help"></a></span>
            <div class="form-wrap fr">
              	<div class="btn lanwei">栏位</div>
                <div class="btn suoyin">索引</div>
                <div class="btn waijian">外键</div>
            </div>
        </div>
        <div class="main">
            <table class="g-table" id="tab1" style="display: none">
                <thead>
	                <tr>
	                    <th class="col1"><div class="g-checkbox"><input type="checkbox" name=""/></div></th>
	                    <th class="col2">名</th>
	                    <th class="col3">类型</th>
	                    <th class="col3">长度</th>
	                    <th class="col4">注释</th>
	                    <th class="col5" style="width:5%">允许空值</th>
	                    <th class="col6" style="width:3%">主键</th>
	                    <th class="col7">默认</th>
	                    <th class="col8" style="width:5%">自动递增</th>
	                    <th class="col9" style="width:3%">无符号</th>
	                    <th class="col10" style="width:10%"><span class='btn-a increase'>新增</span><span class='btn-a deleteMul'>批量删除</span></th>
	                </tr>
                </thead>
                <tbody>
	                
                </tbody>
            </table>
            <table class="g-table" id="tab2" style="display: none">
                <thead>
	                <tr>
	                	<th class="col1"><div class="g-checkbox"><input type="checkbox" name=""/></div></th>
	                    <th class="col2">名</th>
	                    <th class="col3">类型</th>
	                    <th class="col4">基数</th>
	                    <th class="col5">栏位</th>
	                    <th class="col6" style="width:10%"><span class='btn-a increase'>新增</span><span class='btn-a deleteMul'>批量删除</span></th>
	                </tr>
                </thead>
                <tbody>
	                
                </tbody>
            </table>
            <table class="g-table" id="tab3" style="display: none">
                <thead>
	                <tr>
	                	<th class="col1"><div class="g-checkbox"><input type="checkbox" name=""/></div></th>
	                    <th class="col2">名</th>
	                    <th class="col3">栏位</th>
	                    <th class="col4">参考数据库</th>
	                    <th class="col5">参考表</th>
	                    <th class="col6">参考栏位</th>
	                    <th class="col7">删除时</th>
	                    <th class="col8">更新时</th>
	                    <th class="col7" style="width:10%"><span class='btn-a increase'>新增</span><span class='btn-a deleteMul'>批量删除</span></th>
	                </tr>
                </thead>
                <tbody>
	                
                </tbody>
            </table>
        </div>
		<%-- <%@ include file="../include/page_bar.jsf" %> --%>
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

<div class="form-dialog" id = "form1">
    <div class="title">编辑</div>
    <div class="form-wrap">
   		<input type="hidden" name="oldfield_name"  id="oldfield_name" value="" />
   		<table>
   				 <thead>
   				  <tr>
	                    <th class="col1">名</th>
	                    <th class="col2">类型</th>
	                    <th class="col3">长度</th>
	                    <th class="col4">注释</th>
	                    <th class="col5">允许空值</th>
	                    <th class="col6">主键</th>
	                    <th class="col7">默认</th>
	                    <th class="col8">自动递增</th>
	                    <th class="col9">无符号</th>
	                </tr>
	                <tr>
	                	<td><input type="text" id = "field_name"/></td>
	                	<td>
	                		<select id = "field_type">
	                			<option value="VARCHAR">VARCHAR</option>
	                			<option value="TINYINT">TINYINT</option>
	                			<option value="TEXT">TEXT</option>
	                			<option value="DATE">DATE</option>
	                			<option value="SMALLINT">SMALLINT</option>
	                			<option value="MEDIUMINT">MEDIUMINT</option>
	                			<option value="INT">INT</option>
	                			<option value="BIGINT">BIGINT</option>
	                			<option value="FLOAT">FLOAT</option>
	                			<option value="DOUBLE">DOUBLE</option>
	                			<option value="DECIMAL">DECIMAL</option>
	                			<option value="DATETIME">DATETIME</option>
	                			<option value="TIMESTAMP">TIMESTAMP</option>
	                			<option value="TIME">TIME</option>
	                			<option value="YEAR">YEAR</option>
	                			<option value="CHAR">CHAR</option>
	                			<option value="TINYBLOB">TINYBLOB</option>
	                			<option value="TINYTEXT">TINYTEXT</option>
	                			<option value="BLOB">BLOB</option>
	                			<option value="MEDIUMBLOB">MEDIUMBLOB</option>
	                			<option value="MEDIUMTEXT">MEDIUMTEXT</option>
	                			<option value="LONGBLOB">LONGBLOB</option>
	                			<option value="LONGTEXT">LONGTEXT</option>
	                			<option value="ENUM">ENUM</option>
	                			<option value="SET">SET</option>
	                		</select>
	                	</td>
	                	<td><input type="text" id = "field_size"/></td>
	                	<td><input type="text" id = "field_attribute"/></td>
	                	<td><input type="checkbox" id="field_notNull"/></td>
	                	<td><input type="checkbox" id="field_key"/></td>
	                	<td><input type="text" id="field_defaultValue"/></td>
	                	<td><input type="checkbox" id="field_auto_increment"/></td>
	                	<td><input type="checkbox" id="field_unsigned"/></td>
	                </tr>
	                <tr>
	                </tr>
	               </thead>
   		</table>
    </div>
	<div class="item" style="text-align: center;padding-bottom:30px;padding-top: 20px" width="100%">
		<input type="submit" name="save" value="保存" class="g-submit1 g-btn-blue">
		<input type="button" name="return" value="返回" class="g-submit1 g-btn-gray">
	</div>
</div>

<div class="form-dialog" id = "form2">
    <div class="title">编辑</div>
    <div class="form-wrap">
   		<input type="hidden" name="oldindex_name"  id="oldindex_name" value="" />
   		<table>
   				 <thead>
   				  <tr>
	                    <th class="col1">名</th>
	                    <th class="col2">类型</th>
	                    <th class="col3">栏位</th>
	                </tr>
	                <tr>
	                	<td><input type="text" id = "index_name"/></td>
	                	<td>
	                		<select id = "index_type">
	                			<option value="INDEX">INDEX</option>
	                			<option value="UNIQUE">UNIQUE</option>
	                		</select>
	                	</td>
	                	<td><input type="text" id = "index_columns" readonly="readonly"/></td>
	                </tr>
	                <tr>
	                </tr>
	               </thead>
   		</table>
    </div>
	<div class="item" style="text-align: center;padding-bottom:30px;" width="100%">
		<input type="submit" name="save" value="保存" class="g-submit1 g-btn-blue">
		<input type="button" name="return" value="返回" class="g-submit1 g-btn-gray">
	</div>
</div>

<div class="form-dialog" id = "form3">
    <div class="title">编辑</div>
    <div class="form-wrap">
   		<input type="hidden" name="oldconstraint_key_name"  id="oldconstraint_key_name" value="" />
   		<table>
   				 <thead>
   				  <tr>
	                    <th class="col1">名</th>
	                    <th class="col2">栏位</th>
	                    <th class="col3">参考数据库</th>
	                    <th class="col4">参考表</th>
	                    <th class="col5">参考栏位</th>
	                    <th class="col6">删除时</th>
	                    <th class="col7">更新时</th>
	                </tr>
	                <tr>
	                	<td><input type="text" id = "constraint_key_name"/></td>
	                	<td><input type="text" id = "constraint_field" readonly="readonly"/></td>
	                	<td><input type="text" id = "constraint_ref_database" readonly="readonly"/></td>
	                	<td><input type="text" id = "constraint_ref_table" readonly="readonly"/></td>
	                	<td><input type="text" id = "constraint_ref_field" readonly="readonly"/></td>
	                	<td>
	                		<select id = "constraint_delete_relation">
	                			<option value="RESTRICT">RESTRICT</option>
	                			<option value="NO ACTION">NO ACTION</option>
	                			<option value="CASCADE">CASCADE</option>
	                			<option value="SET NULL">SET NULL</option>
	                		</select>
	                	</td>
	                	<td>
	                		<select id = "constraint_update_relation">
	                			<option value="RESTRICT">RESTRICT</option>
	                			<option value="NO ACTION">NO ACTION</option>
	                			<option value="CASCADE">CASCADE</option>
	                			<option value="SET NULL">SET NULL</option>
	                		</select>
	                	</td>
	                </tr>
	                <tr>
	                </tr>
	               </thead>
   		</table>
    </div>
	<div class="item" style="text-align: center;padding-bottom:30px;" width="100%">
		<input type="submit" name="save" value="保存" class="g-submit1 g-btn-blue">
		<input type="button" name="return" value="返回" class="g-submit1 g-btn-gray">
	</div>
</div>

<div class="form-dialog" id="imgPointDiv">
	<div class="chooiceText">
		<ul id="chooiceTextList">
		</ul>
	</div>
	<div class="chooiceDivBtn">
		<input type="button" id="saveChooice" value="保存"  class="saveChooice g-btn-green"/>
	</div>
</div>

<script type="text/javascript">
var table = {
	"name" : $("#tableName").val(),
	"databaseName" : $("#databaseName").val(),
	"server_id" : $("#serverId").val()
};
var editlLayer = null;
var basePath = null;
var fieldList = {};
var indexList = {};
var constraintList = {};
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
        
        $(".lanwei").on("click",function(){
			$(".lanwei").css("background","#10a544");
			$(".suoyin").css("background","#aaaaaa");
			$(".waijian").css("background","#aaaaaa");
			$("#tab2").hide();$("#tab1").show();$("#tab3").hide();
			$.ajax({
				url : basePath + "/table/tableField",
				type : "POST",
				data : {
					"req" : JSON.stringify(table)
				},
				success : function(data) {
					$("tbody").html("");
					if (data.state) {
						if (data.result.fields && data.result.fields.length == 0) {
							
						} else {
							fieldList = {};
							table = data.result;
							$("#tab1 tbody").html("");
							$.each(data.result.fields,function(i, item) {
								fieldList[i] = item;
								var keyImg =""; 
								if(item.key==true){
									keyImg="🔑";
								}
								
								$("#tab1 tbody").append(
									"<tr>"
									+ "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='field_idx' id='"+i+"' /></div></td>"
									+ "<td class='col2'>"
									+ item.name
									+ "</td>"
									+ "<td class='col3'>"
									+ item.type
									+ "</td>"
									+ "<td class='col4'>"
									+ item.size
									+ "</td>"
									+ "<td class='col5'>"
									+ item.attribute
									+ "</td>"
									+ "<td class='col6'>"
									+ item.notNull
									+ "</td>"
									+ "<td class='col7'>"
									+ keyImg
									+ "</td>"
									+ "<td class='col8'>"
									+ item.defaultValue
									+ "</td>"
									+ "<td class='col9'>"
									+ item.auto_increment
									+ "</td>"
									+ "<td class='col10'>"
									+ item.unsigned
									+ "</td>"
									+ "<td class='col11'>"
									+ "<span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span>"
									+ "</td>"
									+ "</tr>");
								
							});
						}
					}

					//$.pagination(data.result);
				}
			});
		});
        
        $(".suoyin").on("click",function(){
        	$(".suoyin").css("background","#10a544");
			$(".lanwei").css("background","#aaaaaa");
			$(".waijian").css("background","#aaaaaa");
			$("#tab1").hide();$("#tab2").show();$("#tab3").hide();
			$.ajax({
				url : basePath + "/table/tableIndex",
				type : "POST",
				data : {
					"req" : JSON.stringify(table)
				},
				success : function(data) {
					$("tbody").html("");
					if (data.state) {
						if (data.result.indexs && data.result.indexs.length == 0) {
							
						} else {
							indexList = {};
							table = data.result;
							$("#tab2 tbody").html("");
							$.each(data.result.indexs,function(i, item) {
								indexList[i]=item;
								
								$("#tab2 tbody").append(
									"<tr>"
									+ "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='index_idx' id='"+i+"' /></div></td>"
									+ "<td class='col2'>"
									+ item.keyName
									+ "</td>"
									+ "<td class='col3'>"
									+ item.type
									+ "</td>"
									+ "<td class='col4'>"
									+ item.cardinality
									+ "</td>"
									+ "<td class='col5'>"
									+ item.fields
									+ "</td>"
									+ "<td class='col6'>"
									+ "<span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span>"
									+ "</td>"
									+ "</tr>");
								
							});
						}
					}

					//$.pagination(data.result);
				}
			});
		});
        
        $(".waijian").on("click",function(){
        	$(".waijian").css("background","#10a544");
			$(".lanwei").css("background","#aaaaaa");
			$(".suoyin").css("background","#aaaaaa");
			$("#tab1").hide();$("#tab2").hide();$("#tab3").show();
			$.ajax({
				url : basePath + "/table/tableKey",
				type : "POST",
				data : {
					"req" : JSON.stringify(table)
				},
				success : function(data) {
					$("tbody").html("");
					if (data.state) {
						if (data.result.keys && data.result.keys.length == 0) {
							
						} else {
							constraintList = {};
							table = data.result;
							console.log(table);
							$("#tab3 tbody").html("");
							$.each(data.result.keys,function(i, item) {
								constraintList[i] = item;
								$("#tab3 tbody").append(
									"<tr>"
									+ "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='constraint_idx' id='"+i+"' /></div></td>"
									+ "<td class='col2'>"
									+ item.key_name
									+ "</td>"
									+ "<td class='col3'>"
									+ item.field
									+ "</td>"
									+ "<td class='col4'>"
									+ item.ref_database
									+ "</td>"
									+ "<td class='col5'>"
									+ item.ref_table
									+ "</td>"
									+ "<td class='col6'>"
									+ item.ref_field
									+ "</td>"
									+ "<td class='col7'>"
									+ item.delete_rule
									+ "</td>"
									+ "<td class='col8'>"
									+ item.update_rule
									+ "</td>"
									+ "<td class='col9'>"
									+ "<span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span>"
									+ "</td>"
									+ "</tr>");
								
							});
						}
					}

					//$.pagination(data.result);
				}
			});
		});
        
      //删除当前行的数据
		$("#tab1").on("click",".delete",function(){
			var field_idx=$(this).parent().parent().find("div input[name='field_idx']").attr("id");
			field = fieldList[field_idx];
			fields = [];
			fields.push(field);
			flag = 1;
			$('.word').html("");
			$('.word').append("是否要删除栏位？");
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
      
		//删除当前行的数据
		$("#tab2").on("click",".delete",function(){
			var index_idx=$(this).parent().parent().find("div input[name='index_idx']").attr("id");
			index = indexList[index_idx];
			indexs = [];
			indexs.push(index);
			flag = 2;
			$('.word').html("");
			$('.word').append("是否要删除索引？");
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
		
		//删除当前行的数据
		$("#tab3").on("click",".delete",function(){
			var constraint_idx=$(this).parent().parent().find("div input[name='constraint_idx']").attr("id");
			constraint = constraintList[constraint_idx];
			constraints = [];
			constraints.push(constraint);
			flag = 3;
			$('.word').html("");
			$('.word').append("是否要删除外键？");
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
		
      
		$("#tab1").on("click",".edit",function(obj){
			$(".error-msg").text("");
			var field_idx=$(this).parent().parent().find("div input[name='field_idx']").attr("id");
        	layerOpen({
        		"title":"编辑表字段",
                "btnText":"保存",
                "btnColorClass":"g-btn-blue",
                "status" : true,
                "field_idx":field_idx,
                "flag":"1"
             
            });
        });
		
		$("#tab2").on("click",".edit",function(obj){
			$(".error-msg").text("");
			var index_idx=$(this).parent().parent().find("div input[name='index_idx']").attr("id");
        	layerOpen({
        		"title":"编辑表索引",
                "btnText":"保存",
                "btnColorClass":"g-btn-blue",
                "status" : true,
                "index_idx":index_idx,
                "flag":"2"
            });
        });
		
		$("#tab3").on("click",".edit",function(obj){
			$(".error-msg").text("");
			var constraint_idx=$(this).parent().parent().find("div input[name='constraint_idx']").attr("id");
        	layerOpen({
        		"title":"编辑表外键",
                "btnText":"保存",
                "btnColorClass":"g-btn-blue",
                "status" : true,
                "constraint_idx":constraint_idx,
                "flag":"3"
            });
        });
		
        
         $("#tab1").on("click",".increase",function(obj){
 			$(".error-msg").text("");
         	layerOpen({
         		"title":"新增表字段",
                 "btnText":"新增",
                 "btnColorClass":"g-btn-blue",
                 "status" : false,
                 "flag":"1"
              
             });
         });
 		
 		$("#tab2").on("click",".increase",function(obj){
 			$(".error-msg").text("");
         	layerOpen({
         		"title":"新增表索引",
                 "btnText":"新增",
                 "btnColorClass":"g-btn-blue",
                 "status" : false,
                 "flag":"2"
             });
         });
 		
 		$("#tab3").on("click",".increase",function(obj){
 			$(".error-msg").text("");
         	layerOpen({
         		"title":"新增表外键",
                 "btnText":"新增",
                 "btnColorClass":"g-btn-blue",
                 "status" : false,
                 "flag":"3"
             });
         });
 		
        var delLayer = null;
        
        var fields =[] ;
       	$("#tab1").on("click",".deleteMul",function(obj){
       		flag = 1;
       		fields =[] ;
			var num = $("tbody input[name='field_idx']:checked").length;
			$("tbody input[name='field_idx']:checked").each(function(){
				var field_id = $(this).attr("id");
				fields.push(fieldList[field_id]);
			});
			if(num>0){
				$(".word").html("");
				$(".word").append("当前选择了<span class='font-red'> "+num+" </span>条数据<br>是否要删除选择的栏位？");
	            delLayer = layer.open({
	                type: 1,
	                shade: false,
	                title: false, //不显示标题
	                scrollbar :false,
	                closeBtn :0,
	                shade:0.5,
	                shadeClose :true,
	                area:["400px"],
	                offset :["195px"],
	                content: $('.g-delete-dialog')
	            });
			}else{
				layer.alert("请至少选择一条数据");
			}
        });
       	
       	var indexs =[] ;
       	$("#tab2").on("click",".deleteMul",function(obj){
       		flag = 2;
       		indexs =[] ;
			var num = $("tbody input[name='index_idx']:checked").length;
			$("tbody input[name='index_idx']:checked").each(function(){
				var index_id = $(this).attr("id");
				indexs.push(indexList[index_id]);
			});
			if(num>0){
				$(".word").html("");
				$(".word").append("当前选择了<span class='font-red'> "+num+" </span>条数据<br>是否要删除选择的索引？");
	            delLayer = layer.open({
	                type: 1,
	                shade: false,
	                title: false, //不显示标题
	                scrollbar :false,
	                closeBtn :0,
	                shade:0.5,
	                shadeClose :true,
	                area:["400px"],
	                offset :["195px"],
	                content: $('.g-delete-dialog')
	            });
			}else{
				layer.alert("请至少选择一条数据");
			}
        });
       	
       	var constraints =[] ;
       	$("#tab3").on("click",".deleteMul",function(obj){
       		flag = 3;
       		constraints =[] ;
			var num = $("tbody input[name='constraint_idx']:checked").length;
			$("tbody input[name='constraint_idx']:checked").each(function(){
				var constraint_id = $(this).attr("id");
				constraints.push(constraintList[constraint_id]);
			});
			if(num>0){
				$(".word").html("");
				$(".word").append("当前选择了<span class='font-red'> "+num+" </span>条数据<br>是否要删除选择的外键？");
	            delLayer = layer.open({
	                type: 1,
	                shade: false,
	                title: false, //不显示标题
	                scrollbar :false,
	                closeBtn :0,
	                shade:0.5,
	                shadeClose :true,
	                area:["400px"],
	                offset :["195px"],
	                content: $('.g-delete-dialog')
	            });
			}else{
				layer.alert("请至少选择一条数据");
			}
        });
        
        $(".form-btn.cancel").on("click",function(){
            if (delLayer) {
                layer.close(delLayer);
            }
        });
        
        $(".form-btn.remove").on("click",function(){
            /*执行删除操作*/
            if (delLayer) {
                layer.close(delLayer);
            }
            var param = "";
            if(flag == 1){
            	param = {
						json1 : JSON.stringify(table),
						json2 : JSON.stringify(fields)
				};
            	var baseURL=basePath+"/table/deleteField";
           		$.ajax({
       				url : baseURL,
       				type : "POST",
       				data : {
       					"req" : JSON.stringify(param)
       				},
       				success : function(data) {
       					if (data.state) {
    						GlobalShowMsg({showText:"操作成功",status:true});
    						$(".lanwei").click();
        				}else{
        					var message = data.message;
        					if(message){
        						layer.alert(message);
        					}else{
        						GlobalShowMsg({showText:"操作失败",status:false});
        					}
        				}
       				}
       			});
            }else if(flag == 2){
            	param = {
						json1 : JSON.stringify(table),
						json2 : JSON.stringify(indexs)
				};
            	var baseURL=basePath+"/table/deleteIndex";
           		$.ajax({
       				url : baseURL,
       				type : "POST",
       				data : {
       					"req" : JSON.stringify(param)
       				},
       				success : function(data) {
       					if (data.state) {
    						GlobalShowMsg({showText:"操作成功",status:true});
    						$(".suoyin").click();
        				}else{
        					var message = data.message;
        					if(message){
        						layer.alert(message);
        					}else{
        						GlobalShowMsg({showText:"操作失败",status:false});
        					}
        				}
       				}
       			});
            }else if(flag == 3){
            	param = {
						json1 : JSON.stringify(table),
						json2 : JSON.stringify(constraints)
				};
            	var baseURL=basePath+"/table/deleteConstraint";
           		$.ajax({
       				url : baseURL,
       				type : "POST",
       				data : {
       					"req" : JSON.stringify(param)
       				},
       				success : function(data) {
       					if (data.state) {
    						GlobalShowMsg({showText:"操作成功",status:true});
    						$(".waijian").click();
        				}else{
        					var message = data.message;
        					if(message){
        						layer.alert(message);
        					}else{
        						GlobalShowMsg({showText:"操作失败",status:false});
        					}
        				}
       				}
       			});
            }
            
        });
        
        /**新增、修改弹出框保存操作**/
		$("input[name='save']").on("click", function() {
			var param = "";
			if(flag ==1){
	        	var field_name = $("#field_name").val();
	        	var field_type = $("#field_type").val();
	        	var field_size = $("#field_size").val();
	        	var field_notNull = true;
	        	if($("#field_notNull").prop('checked')){
	        		field_notNull = false;
	        	}
				var field_defaultValue = $("#field_defaultValue").val();
				var field_extra = $("#field_extra").val();
				var field_attribute = $("#field_attribute").val();
				var field_key = false;
				if($("#field_key").prop('checked')){
	        		field_key = true;
	        	}
				var field_unsigned = false;
				if($("#field_unsigned").prop('checked')){
	        		field_unsigned = true;
	        	}
				var field_auto_increment = false;
				if($("#field_auto_increment").prop('checked')){
	        		field_auto_increment = true;
	        	}
				
				if(!field_name){
					layer.tips('名称不能为空', '#field_name', {tips: [2, '#ff6666']});
					return;
				}
				
				if (editlLayer) {
					layer.close(editlLayer);
				}
				var baseURL=basePath+"/table/addField";
				if (isEdit) {//编辑
					baseURL=basePath+"/table/updateField";
					if(!field_name){
						return;
					}
					param = {
							json1 : JSON.stringify(table),
							json2 : JSON.stringify(field),
							json3 : {
								"name" : field_name,
								"type":field_type,
								"size":field_size,
								"notNull":field_notNull,
								"defaultValue":field_defaultValue,
								"extra":field_extra,
								"attribute":field_attribute,
								"key":field_key,
								"unsigned":field_unsigned,
								"auto_increment":field_auto_increment
							}
					};
				}else{//新增
					param = {
							json1 : JSON.stringify(table),
							json2 : {
								"name" : field_name,
								"type":field_type,
								"size":field_size,
								"notNull":field_notNull,
								"defaultValue":field_defaultValue,
								"extra":field_extra,
								"attribute":field_attribute,
								"key":field_key
							}
					};
				}
				$.ajax({
					url : baseURL,
					type : "POST",
					data : {
						"req" : JSON.stringify(param)
					},
					success : function(data) {
						if (data.state) {
							GlobalShowMsg({showText:"操作成功",status:true});
							$(".lanwei").click();
	    				}else{
	    					var message = data.message;
	    					if(message){
	    						layer.alert(message);
	    					}else{
	    						GlobalShowMsg({showText:"操作失败",status:false});
	    					}
	    				}
					}
				});
			}else if(flag == 2){
	        	var index_name = $("#index_name").val();
	        	var index_type = $("#index_type").val();
	        	var columnStr = $("#index_columns").val();
				
				if(!index_name){
					layer.tips('名称不能为空', '#index_name', {tips: [2, '#ff6666']});
					return;
				}
				
				if(!index_columns){
					layer.tips('栏位不能为空', '#index_columns', {tips: [2, '#ff6666']});
					return;
				}
				
				if (editlLayer) {
					layer.close(editlLayer);
				}
				var baseURL=basePath+"/table/addIndex";
				if (isEdit) {//编辑
					baseURL=basePath+"/table/updateIndex";
					if(!index_name){
						return;
					}
					param = {
							json1 : JSON.stringify(table),
							json2 : JSON.stringify(index),
							json3 : {
								"keyName" : index_name,
								"type":index_type,
								"columnStr":columnStr
							}
					};
				}else{//新增
					param = {
							json1 : JSON.stringify(table),
							json2 : {
								"keyName" : index_name,
								"type":index_type,
								"columnStr":columnStr
							}
					};
				}
				$.ajax({
					url : baseURL,
					type : "POST",
					data : {
						"req" : JSON.stringify(param)
					},
					success : function(data) {
						if (data.state) {
							GlobalShowMsg({showText:"操作成功",status:true});
							$(".suoyin").click();
	    				}else{
	    					var message = data.message;
	    					if(message){
	    						layer.alert(message);
	    					}else{
	    						GlobalShowMsg({showText:"操作失败",status:false});
	    					}
	    				}
					}
				});
			}else if(flag == 3){
				//var oldconstraint_key_name = $("#oldconstraint_key_name").val();
	        	var constraint_key_name = $("#constraint_key_name").val();
	        	var constraint_field = $("#constraint_field").val();
	        	var constraint_ref_database = $("#constraint_ref_database").val();
	        	var constraint_ref_table = $("#constraint_ref_table").val();
	        	var constraint_ref_field = $("#constraint_ref_field").val();
	        	var constraint_delete_relation = $("#constraint_delete_relation").val();
	        	var constraint_update_relation = $("#constraint_update_relation").val();
	        	
				if(!constraint_key_name){
					layer.tips('名称不能为空', '#constraint_key_name', {tips: [2, '#ff6666']});
					return;
				}
				
				if (editlLayer) {
					layer.close(editlLayer);
				}
				var baseURL=basePath+"/table/addConstraint";
				if (isEdit) {//编辑
					baseURL=basePath+"/table/updateconstraint";
					param = {
							json1 : JSON.stringify(table),
							json2 : JSON.stringify(constraint),
							json3 : {
								"key_name" : constraint_key_name,
								"field":constraint_field,
								"ref_database":constraint_ref_database,
								"ref_table":constraint_ref_table,
								"ref_field":constraint_ref_field,
								"update_rule":constraint_update_relation,
								"delete_rule":constraint_delete_relation
							}
					};
				}else{//新增
					param = {
							json1 : JSON.stringify(table),
							json2 : {
								"key_name" : constraint_key_name,
								"field":constraint_field,
								"ref_database":constraint_ref_database,
								"ref_table":constraint_ref_table,
								"ref_field":constraint_ref_field,
								"update_rule":constraint_update_relation,
								"delete_rule":constraint_delete_relation
							}
					};
				}
				$.ajax({
					url : baseURL,
					type : "POST",
					data : {
						"req" : JSON.stringify(param)
					},
					success : function(data) {
						if (data.state) {
							GlobalShowMsg({showText:"操作成功",status:true});
							$(".waijian").click();
	    				}else{
	    					var message = data.message;
	    					if(message){
	    						layer.alert(message);
	    					}else{
	    						GlobalShowMsg({showText:"操作失败",status:false});
	    					}
	    				}
					}
				});
			}
			
			
		});
        
		/**取消新增、修改弹出框**/
		$("input[name='return']").on("click", function() {
			if (editlLayer) {
				layer.close(editlLayer);
			}

		});
    });
    
    var isEdit = false;
    var flag = 1;
    var field = null;
    var index = null;
    var constraint = null;
    function layerOpen(options){
        var defaults = {
            title:"",
            btnText : "新增"
        }
        options = $.extend(defaults,options);
        var $submit = $(".form-dialog .submit");
        $(".form-dialog .title").text(options.title);
        if(options.status){
        	isEdit = true;
        	if(options.flag ==1){
        		flag = 1;
        		field = fieldList[options.field_idx];
        		$("#oldfield_name").val(field.name);
    			$("#field_name").val(field.name);
    			$("#field_type").val(field.type.toUpperCase());
    			$("#field_size").val(field.size);
    			$("#field_notNull").attr("checked",field.notNull);
    			$("#field_defaultValue").val(field.defaultValue);
            	$("#field_extra").val(field.extra);
            	$("#field_attribute").val(field.attribute);
            	$("#field_key").attr("checked", field.key);
            	$("#field_unsigned").attr("checked", field.unsigned);
            	$("#field_auto_increment").attr("checked", field.auto_increment);
        	}else if(options.flag ==2){
        		flag = 2;
        		index = indexList[options.index_idx];
        		$("#oldindex_name").val(index.keyName);
    			$("#index_name").val(index.keyName);
    			$("#index_type").val(index.type.toUpperCase());
    			$("#index_columns").val(index.fields);
        	}else  if(options.flag ==3){
        		flag = 3;
        		constraint = constraintList[options.constraint_idx];
        		$("#oldconstraint_key_name").val(constraint.key_name);
        		$("#constraint_key_name").val(constraint.key_name);
    			$("#constraint_field").val(constraint.field);
    			$("#constraint_ref_database").val(constraint.ref_database);
    			$("#constraint_ref_table").val(constraint.ref_table);
    			$("#constraint_ref_field").val(constraint.ref_field);
    			$("#constraint_delete_relation").val(constraint.delete_rule);
    			$("#constraint_update_relation").val(constraint.update_rule);
        	}
        }
        else{
        	if(options.flag ==1){
        		flag = 1;
        		$("#oldfield_name").val("");
    			$("#field_name").val("");
    			$("#field_type").prop("selected", 'selected');  
    			$("#field_size").val("");
    			$("#field_notNull").attr("checked",false);
    			$("#field_defaultValue").val("");
            	$("#field_extra").val("");
            	$("#field_attribute").val("");
            	$("#field_key").attr("checked", false);
            	$("#field_unsigned").attr("checked", false);
            	$("#field_auto_increment").attr("checked", false);
        	}else if(options.flag ==2){
        		flag = 2;
        		$("#oldindex_name").val("");
    			$("#index_name").val("");
    			$("#index_type").prop("selected", 'selected');  
    			$("#index_columns").val("");
        	}else  if(options.flag ==3){
        		flag = 3;
        		$("#oldconstraint_key_name").val("");
        		$("#constraint_key_name").val("");
    			$("#constraint_field").val("");
    			$("#constraint_ref_database").val("");
    			$("#constraint_ref_table").val("");
    			$("#constraint_ref_field").val("");
    			$("#constraint_delete_relation").prop("selected", 'selected');  
    			$("#constraint_update_relation").prop("selected", 'selected');  
        	}
        	
        	isEdit = false;
        }
        $submit.val(options.btnText);
        $submit.removeClass("g-btn-green").removeClass(".g-btn-blue");
        $submit.addClass(options.btnColorClass);
        editlLayer = layer.open({
            type: 1,
            shade: false,
            title: false, //不显示标题
            scrollbar :false,
            closeBtn :1,
            shade:0.5,
            shadeClose :true,
            area:["1000px","280px"],
            offset :["180px"],
            content: $('#form'+options.flag)
        });
    };
    
    var openImgDiv = "";
    
	$("#saveChooice").on("click",function(){
		 var spCodesTemp = "";
		 var num = 0;
		 $("input:checkbox[name=fieldNameCheckBox]:checked").each(function(i){
			 num = num + 1;
			 if(i == 0){
				 spCodesTemp = $(this).attr("id");
			 }else{
				 spCodesTemp += ","+$(this).attr("id");
			 }
		 });
		 if(eleTxt == "constraint_ref_database" && num > 1){
			 layer.alert("只能勾选一个！");
			 return;
		 }else if(eleTxt == "constraint_ref_table" && num > 1){
			 layer.alert("只能勾选一个！");
			 return;
		 }
		 $("#"+eleTxt).val(spCodesTemp);
		 layer.close(openImgDiv);
    });
	var eleTxt = "";
	 $("#index_columns").on("click",function(){
		 	eleTxt = "index_columns";
		 	$("#chooiceTextList").html("");
			var html = "";
			for(var i in fieldList){
				html += "<li><input type=\"checkbox\" name=\"fieldNameCheckBox\" id="+fieldList[i].name+">"+fieldList[i].name+"</li>";
			}
			$("#chooiceTextList").append(html);
	    	
	    	openImgDiv=layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				scrollbar :false,
				closeBtn :1,
				shade:0.5,
				shadeClose :false,
				area :["250px","250px"],
				offset :["350px","1200px"],
				content: $('#imgPointDiv'), //捕获的元素
				cancel: function(index){
					layer.close(openImgDiv);
					this.content.hide();
				}
			});
	    });
	 
	 $("#constraint_field").on("click",function(){
		 	eleTxt = "constraint_field";
		 	$("#chooiceTextList").html("");
			var html = "";
			for(var i in fieldList){
				html += "<li><input type=\"checkbox\" name=\"fieldNameCheckBox\" id="+fieldList[i].name+">"+fieldList[i].name+"</li>";
			}
			$("#chooiceTextList").append(html);
	    	
	    	openImgDiv=layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				scrollbar :false,
				closeBtn :1,
				shade:0.5,
				shadeClose :false,
				area :["250px","250px"],
				offset :["350px"],
				content: $('#imgPointDiv'), //捕获的元素
				cancel: function(index){
					layer.close(openImgDiv);
					this.content.hide();
				}
			});
	    });
	 
	 var dbNames = {};
	 $("#constraint_ref_database").on("click",function(){
		 	eleTxt = "constraint_ref_database";
		 	var table = {
		 			"server_id" : $("#serverId").val()
		 		};
		 	$("#chooiceTextList").html("");
		 	$.ajax({
				url : basePath + "/table/serverInfo",
				type : "POST",
				data : {
					"req" : JSON.stringify(table)
				},
				success : function(data) {
					if (data.state) {
						if (data.result.dbNames && data.result.dbNames.length == 0) {
							return;
						} else {
							var html = "";
							var dbs_ = data.result.dbNames;
							for(var i in dbs_){
								html += "<li><input type=\"checkbox\" name=\"fieldNameCheckBox\" id="+dbs_[i]+">"+dbs_[i]+"</li>";
							}
							$("#chooiceTextList").append(html);
						}
					}
				}
			});
	    	openImgDiv=layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				scrollbar :false,
				closeBtn :1,
				shade:0.5,
				shadeClose :false,
				area :["250px","250px"],
				offset :["350px"],
				content: $('#imgPointDiv'), //捕获的元素
				cancel: function(index){
					layer.close(openImgDiv);
					this.content.hide();
				}
			});
	    });
	 
	 var tabNames = {};
	 $("#constraint_ref_table").on("click",function(){
			 eleTxt = "constraint_ref_table";
			 var table = {
						"databaseName" : $("#constraint_ref_database").val(),
						"server_id" : $("#serverId").val()
			 		};
			 $("#chooiceTextList").html("");
		 	$.ajax({
				url : basePath + "/table/serverInfo",
				type : "POST",
				data : {
					"req" : JSON.stringify(table)
				},
				success : function(data) {
					if (data.state) {
						if (data.result.tabNames && data.result.tabNames.length == 0) {
							return;
						} else {
							var html = "";
							var tabs_ = data.result.tabNames;
							for(var i in tabs_){
								html += "<li><input type=\"checkbox\" name=\"fieldNameCheckBox\" id="+tabs_[i]+">"+tabs_[i]+"</li>";
							}
							$("#chooiceTextList").append(html);
						}
					}
				}
			});
		 	openImgDiv=layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				scrollbar :false,
				closeBtn :1,
				shade:0.5,
				shadeClose :false,
				area :["250px","250px"],
				offset :["350px"],
				content: $('#imgPointDiv'), //捕获的元素
				cancel: function(index){
					layer.close(openImgDiv);
					this.content.hide();
				}
			});
	    });
	 
	 var fieldNames = {};
	 $("#constraint_ref_field").on("click",function(){
		 	eleTxt = "constraint_ref_field";
		 	var table = {
				 	"name" : $("#constraint_ref_table").val(),
					"databaseName" : $("#constraint_ref_database").val(),
					"server_id" : $("#serverId").val()
		 		};
		 	$("#chooiceTextList").html("");
		 	$.ajax({
				url : basePath + "/table/serverInfo",
				type : "POST",
				data : {
					"req" : JSON.stringify(table)
				},
				success : function(data) {
					if (data.state) {
						if (data.result.fieldNames && data.result.fieldNames.length == 0) {
							return;
						} else {
							var html = "";
							var fields_ = data.result.fieldNames;
							console.log(fields_);
							for(var i in fields_){
								html += "<li><input type=\"checkbox\" name=\"fieldNameCheckBox\" id="+fields_[i]+">"+fields_[i]+"</li>";
							}
							$("#chooiceTextList").append(html);
						}
					}
				}
			});
	    	openImgDiv=layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				scrollbar :false,
				closeBtn :1,
				shade:0.5,
				shadeClose :false,
				area :["250px","250px"],
				offset :["350px"],
				content: $('#imgPointDiv'), //捕获的元素
				cancel: function(index){
					layer.close(openImgDiv);
					this.content.hide();
				}
			});
	    });
    
    /**封装的查询方法**/
    jQuery.select = function(param) {
		
	};
	
	$(function() {
		 $(".lanwei").click();
	});
	
	function isIP(strIP) {   
	    var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g   
	    if(re.test(strIP)){   
	      if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) return true;   
	    }   
	    return false;   
	} 
	
</script>
</body>
</html>
