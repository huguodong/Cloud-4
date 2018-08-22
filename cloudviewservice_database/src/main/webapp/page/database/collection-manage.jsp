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
    <title>集合管理</title>
</head>
<body>
<div class="equipment-guanli">
        <div class="page-title-bar">
        	<input type="text" id="serverId" value="${server.id}"/>
        	<input type="hidden" id="databaseName" value="${collection.databaseName }"/>
        	<input type="hidden" id="collectionName" value="${collection.name}"/>
            <span class="title">Server：${server.host}&nbsp;&nbsp;&nbsp;&nbsp;DateBase：${collection.databaseName }&nbsp;&nbsp;&nbsp;&nbsp;Collection：${collection.name}<a href="${pageContext.request.contextPath}/static/help-page.html" target="_blank" class="g-help"></a></span>
            <div class="form-wrap fr">
              	<div class="btn collectionData">数据</div>
                <div class="btn collectionIndex" style="display: none">索引</div>
            </div>
        </div>
        <div class="main">
            <table class="g-table" id="tab1" style="display: none">
                <thead>
	                <tr>
	                    <th class="col1" ><div class="g-checkbox"><input type="checkbox" name=""/></div></th>
	                    <th class="col2" >_ID</th>
	                    <th class="col3" >描述</th>
	                    <th class="col4" >长度</th>
	                    <th class="col5" style="width:10%"><span class='btn-a increase'>新增</span><span class='btn-a deleteMul'>批量删除</span></th>
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
		<%@ include file="../include/page_bar.jsf" %>
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
   		<div class="item">
			<ul>
				<li>
					<div class="left" style="padding-left: 5px">
						<textarea rows="18" cols="110" name="content" id="content" placeholder="描述" style="background-color: #d0d0d0"></textarea>
					</div>
				</li>
			</ul>
		</div>
    </div>
	<div class="item" style="text-align: center;padding-bottom:30px;" width="100%">
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
	                	<td><input type="text" id = "index_columns"/></td>
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
	                	<td><input type="text" id = "constraint_field"/></td>
	                	<td><input type="text" id = "constraint_ref_database"/></td>
	                	<td><input type="text" id = "constraint_ref_table"/></td>
	                	<td><input type="text" id = "constraint_ref_field"/></td>
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

<script type="text/javascript">
var collection = {
	"name" : $("#collectionName").val(),
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
        
        $(".collectionData").on("click",function(){
			$(".collectionData").css("background","#10a544");
			$(".collectionIndex").css("background","#aaaaaa");
			$("#tab2").hide();$("#tab1").show();
			var Page = {
					"page" : "1",
					"pageSize" : $('#showSize option:selected').text(),
				};
			var param={
				"json":JSON.stringify(collection),
				"page":JSON.stringify(Page)
			};
			$.select(param);
		});
        
        
      //上一页操作
		$("#page").on("click", ".prev-page", function() {
			GlobalGLoading();
			
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) - 1;
			var size = $('#showSize option:selected').html();
			
			var Page = {
					"page" : page,
					"pageSize" : size
				};
			var param={
				"json":JSON.stringify(collection),
				"page":JSON.stringify(Page)
			};
			$.select(param);
		});
		//下一页操作
		$("#page").on("click", ".next-page", function() {
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) + 1;
			var size = $('#showSize option:selected').html();

			var Page = {
					"page" : page,
					"pageSize" : size
				};
			var param={
				"json":JSON.stringify(collection),
				"page":JSON.stringify(Page)
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

			var Page = {
					"page" : page,
					"pageSize" : size
				};
			var param={
				"json":JSON.stringify(collection),
				"page":JSON.stringify(Page)
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

			var Page = {
					"page" : page,
					"pageSize" : size
				};
			var param={
				"json":JSON.stringify(collection),
				"page":JSON.stringify(Page)
			};
			$.select(param);

		});
		
		/**封装的查询方法**/
	    jQuery.select = function(param) {
	    	$.ajax({
				url : basePath + "/collection/collectionData",
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
							$("#tab1 tbody").html("");
							fieldList = {};
							$.each(data.result.rows,function(i, item) {
								fieldList[item.id] = item;
								var jsonStr = item.jsonStr;
								if(item.jsonStr.length>160){
									jsonStr = jsonStr.substring(0,160);
								}
								$("#tab1 tbody").append(
									"<tr>"
									+ "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.id+"' /></div></td>"
									+ "<td class='col2'>"
									+ item.id
									+ "</td>"
									+ "<td class='col3'>"
									+ jsonStr
									+ "</td>"
									+ "<td class='col4'>"
									+ item.fields.length
									+ "</td>"
									+ "<td class='col5'>"
									+ "<span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span>"
									+ "</td>"
									+ "</tr>");
								
							});
						}
					}

					$.pagination(data.result);
				}
			});
		};
        
        $(".suoyin").on("click",function(){
        	$(".collectionIndex").css("background","#10a544");
			$(".collectionData").css("background","#aaaaaa");
			$("#tab1").hide();$("#tab2").show();
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
        
      //删除当前行的数据
		$("#tab1").on("click",".delete",function(){
			var idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
			fields = [];
			fields.push(idx);
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
			var idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
        	layerOpen({
        		"title":"编辑Document",
                "btnText":"保存",
                "btnColorClass":"g-btn-blue",
                "status" : true,
                "idx":idx,
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
         		"title":"插入Document",
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
			var num = $("tbody input[name='idx']:checked").length;
			$("tbody input[name='idx']:checked").each(function(){
				var idx = $(this).attr("id");
				fields.push(idx);
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
            console.log(collection)
            var param = "";
            if(flag == 1){
            	param = {
						json1 : JSON.stringify(collection),
						json2 : JSON.stringify(fields)
				};
            	var baseURL=basePath+"/collection/deleteDocument";
           		$.ajax({
       				url : baseURL,
       				type : "POST",
       				data : {
       					"req" : JSON.stringify(param)
       				},
       				success : function(data) {
       					if (data.state) {
    						GlobalShowMsg({showText:"操作成功",status:true});
    						var currentpage = $(".paging-bar li.active").html();
    						var page = Number(currentpage);
    						var size = $('#showSize option:selected').html();
    						
    						var Page = {
    								"page" : page,
    								"pageSize" : size
    							};
    						var param={
    							"json":JSON.stringify(collection),
    							"page":JSON.stringify(Page)
    						};
    						$.select(param);
    						//$(".collectionData").click();
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
				var content = $("#content").val();
				var str = "";
				try{
					str = jQuery.parseJSON(content);
				}catch (e) {
					layer.alert(e.message);
					return;
				}
				if (editlLayer) {
					layer.close(editlLayer);
				}
				var baseURL=basePath+"/collection/addDocument";
				if (isEdit) {//编辑
					baseURL=basePath+"/collection/updateDocument";
				    var oldfield_name = $("#oldfield_name").val();
					if(!oldfield_name){
						return;
					}
					
					param = {
							json1 : JSON.stringify(collection),
							json2 : oldfield_name,
							json3 : JSON.stringify(str)
					};
				}else{//新增
					param = {
							json1 : JSON.stringify(collection),
							json2 : content
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
							var currentpage = $(".paging-bar li.active").html();
							var page = Number(currentpage);
							var size = $('#showSize option:selected').html();
							
							var Page = {
									"page" : page,
									"pageSize" : size
								};
							var param={
								"json":JSON.stringify(collection),
								"page":JSON.stringify(Page)
							};
							$.select(param);
							//$(".collectionData").click();
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
        		field = fieldList[options.idx];
        		$("#oldfield_name").val(field.id);
    			$("#content").val(formatJson(field.jsonStr));
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
    			$("#content").val("");
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
            area:["800px","550px"],
            offset :["180px"],
            content: $('#form'+options.flag)
        });
    };
	
	$(function() {
		 $(".collectionData").click();
	});
	
	function isIP(strIP) {   
	    var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g   
	    if(re.test(strIP)){   
	      if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) return true;   
	    }   
	    return false;   
	} 
	
	var formatJson = function(json, options) {
        var reg = null,
          formatted = '',
          pad = 0,
          PADDING = '    '; // one can also use '\t' or a different number of spaces
        // optional settings
        options = options || {};
        // remove newline where '{' or '[' follows ':'
        options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
        // use a space after a colon
        options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

        // begin formatting...

        // make sure we start with the JSON as a string
        if (typeof json !== 'string') {
            json = JSON.stringify(json);
        }
        // parse and stringify in order to remove extra whitespace
        json = JSON.parse(json);
        json = JSON.stringify(json);

        // add newline before and after curly braces
        reg = /([\{\}])/g;
        json = json.replace(reg, '\r\n$1\r\n');

        // add newline before and after square brackets
        reg = /([\[\]])/g;
        json = json.replace(reg, '\r\n$1\r\n');

        // add newline after comma
        reg = /(\,)/g;
        json = json.replace(reg, '$1\r\n');

        // remove multiple newlines
        reg = /(\r\n\r\n)/g;
        json = json.replace(reg, '\r\n');

        // remove newlines before commas
        reg = /\r\n\,/g;
        json = json.replace(reg, ',');

        // optional formatting...
        if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
            reg = /\:\r\n\{/g;
            json = json.replace(reg, ':{');
            reg = /\:\r\n\[/g;
            json = json.replace(reg, ':[');
        }
        if (options.spaceAfterColon) {
            reg = /\:/g;
            json = json.replace(reg, ': ');
        }

        $.each(json.split('\r\n'), function(index, node) {
        	var i = 0,
              indent = 0,
              padding = '';

            if (node.match(/\{$/) || node.match(/\[$/)) {
                indent = 1;
            } else if (node.match(/\}/) || node.match(/\]/)) {
                if (pad !== 0) {
                    pad -= 1;
                }
            } else {
                indent = 0;
            }

            for (i = 0; i < pad; i++) {
                padding += PADDING;
            }

            formatted += padding + node + '\r\n';
            //alert(formatted)
            pad += indent;
        });
        return formatted;
    };
	
</script>
</body>
</html>
