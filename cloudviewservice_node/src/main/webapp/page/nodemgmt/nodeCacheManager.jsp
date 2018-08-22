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
    <title>云主机管理</title>
</head>
<body>
<div class="equipment-guanli">
        <div class="page-title-bar">
            <span class="title">云主机管理<a href="${pageContext.request.contextPath}/static/help-page.html" target="_blank" class="g-help"></a></span>
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
                <input type="text" id="node_name"  class="input g-input" placeholder="请输入切换节点名称" />
                <div class="btn search">查询</div>
                <div class="btn delete">清除缓存</div>
            </div>
        </div>
        <div class="main">
            <table class="g-table">
                <thead>
	                <tr>
	                    <th class="col1"><div class="g-checkbox"><input type="checkbox" name=""/></div></th>
	                    <th class="col2">上一级节点</th>
	                    <th class="col3">上一级节点IP</th>
	                    <th class="col4">上一级节点容器</th>
	                    <th class="col5">上一级节点端口</th>
	                    <th class="col6">协议</th>
	                    <th class="col7">被切换节点</th>
	                    <th class="col8">操作</th>
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
        是否要清除选择的节点的缓存？
    </div>
    <div class="form-btn cancel g-btn-gray">取消</div>
    <div class="form-btn remove g-btn-red">清除</div>
</div>

<script type="text/javascript">
var editlLayer = null;
var basePath = null;
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
        var detailLayer = null;
        var delLayer = null;
      
        var oper = <shiro:principal/>;
        var dataArray = new Array();
        $(".delete").on("click",function(){
        	
			var num = $("tbody input[name='node_checkbox']:checked").length;
			
			if(num <= 0){
				$(".word").html("");
				$(".word").append("是否清除全部节点缓存？");
			}
			
			if(num > 0){
				$(".word").html("");
				$(".word").append("当前选择了<span class='font-red'> "+num+" </span>条数据<br>是否要删除选择的节点缓存？");
				$("tbody input[name='node_checkbox']:checked").each(function(index,item){
					
					var ip =$(item).parent().parent().parent().find(".col3").text();
					var port = $(this).parent().parent().parent().find(".col5").text()
					var protocol = $(this).parent().parent().parent().find(".col6").text()
					var from_node = $(this).parent().parent().parent().find(".col2").text()
					var node = new Object();
					node.from_node = from_node;
					node.port = port;
					node.protocol = protocol;
					node.ip = ip;
					dataArray[index]=node;
						
				});
				
			}
			
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
			
        });
       
		$("tbody").on("click",".edit",function(obj){
			var ip =$(this).parent().parent().find(".col3").text();
			var port = $(this).parent().parent().find(".col5").text()
			var protocol = $(this).parent().parent().find(".col6").text()
			var from_node = $(this).parent().parent().find(".col2").text()
			var data = new Array();
			var node = new Object();
			node.from_node = from_node;
			node.port = port;
			node.protocol = protocol;
			node.ip = ip;
			data[0]=node;
			clearCache(data);
        });
		
		function clearCache(param){
			$.ajax({
				url : basePath+"/nodeInterface/clearNodeCache",
				type : "POST",
				data : {
					"req" : JSON.stringify(param)
				},
				success : function(data) {
					if (data.state) {
						GlobalShowMsg({showText:"清除成功",status:true});
    				}else{
    					alert("清除失败");
    				}
				},
				error:function(data){
					alert("清除失败");
				}
			});
			
		}
		
		
        /**查询按钮操作**/
		$(".search").on("click",function(){
			GlobalGLoading();
			var size= $('#showSize option:selected').text();
			var param={
				"page":1,"pageSize":size
			};
			
			$.select(param);
		});
        
		//上一页操作
		$("#page").on("click", ".prev-page", function() {
			GlobalGLoading();
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) - 1;
			var size = $('#showSize option:selected').html();
			var param={
				"page":page,"pageSize":size
			};
			$.select(param);
		});
		//下一页操作
		$("#page").on("click", ".next-page", function() {
			GlobalGLoading();
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) + 1;
			var size = $('#showSize option:selected').html();
			var param={
				"page":page,"pageSize":size
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
			var param={
				"page":page,"pageSize":size
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
			var param={
				"page":page,"pageSize":size
			};
			
			$.select(param);

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
            clearCache(dataArray);
        	
        });
       
      
       
    });
    
 
    /**封装的查询方法**/
    jQuery.select = function(param) {
    	var node_name = $("#node_name").val();
    	param['to_node']=node_name;
    	var type = $("#type").val();
    	if(type != -1){
    		param['node_mode'] = type;
    	}
		$.ajax({
			url : basePath + "/nodeInterface/queryPreNodes",
			type : "POST",
			data : {
				"req" : JSON.stringify(param)
			},
			success : function(data) {
				$("tbody").html("");
				if (data.state) {
					$.each(data.result.rows,function(i, item) {
						$("tbody").append(
							"<tr>"
							+ "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='node_checkbox'/></div></td>"
							+ "<td class='col2'>"
							+ item.from_node
							+ "</td>"
							+ "<td class='col3'>"
							+ item.ip
							+ "</td>"
							+ "<td class='col4'>"
							+ item.save_path
							+ "</td>"
							+ "</td>"
							+ "<td class='col5'>"
							+ item.port
							+ "</td>"
							+ "<td class='col6'>"
							+ item.protocol
							+ "</td>"
							+ "<td class='col7'>"
							+ item.to_node
							+ "</td>"
							+ "<td class='col8'><span class='btn-a edit'>清除缓存</span></td>"
							+ "</tr>"); 
					});
					
				}
				$.pagination(data.result);
			}
		});
	};
	
	$(function() {
		var Page = {
			"page" : "1",
			"pageSize" : $('#showSize option:selected').text(),
		};
		$.select(Page);
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
