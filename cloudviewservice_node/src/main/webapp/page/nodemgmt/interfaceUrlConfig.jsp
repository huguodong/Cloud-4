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
	                    <select id="paramType">
	                    	<option value="-1" selected>选择类型</option>
	                        <option value="from_node">所属节点</option>
	                        <option value="to_node">访问节点</option>
	                        <option value="request_url">请求URL</option>
	                        <option value="request_id">接口编号</option>
	                    </select>
	                    <span class="arr-icon"></span>
                </div>
                <input type="text" name="keyword" id="keyword"  class="input g-input" placeholder="输入" />
                <div class="btn search">查询</div>
                <div class="btn increase g-btn-green">新增</div>
                <div class="btn delete">删除</div>
            </div>
        </div>
        <div class="main">
            <table class="g-table">
                <thead>
	                <tr>
	                    <th class="col1"><div class="g-checkbox"><input type="checkbox" name=""/></div></th>
	                    <th class="col2">接口编号</th>
	                    <th class="col2">所属节点</th>
	                     <th class="col3">访问节点</th>
	                    <th class="col6">访问URL</th>
	                    <th class="col7">操作</th>
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

<div class="form-dialog">
    <div class="title">新增云主机</div>
    <input type="hidden" value="" id="interface_idx">
    <div class="form-wrap">
        <ul>
            <li>
                <div class="left"><span class="g-mustIn">所属节点</span></div>
                <div class="right">
                   <div class="g-select">
	                    <select id="belongNode">
	                        <option value="-1" selected>选择所属节点</option>
	                    </select>
	                    <span class="arr-icon"></span>
                	</div>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">访问节点</span></div>
                <div class="right">
                    <div class="g-select">
	                    <select id="toNode">
	                        <option value="-1" selected>选择访问节点</option>
	                    </select>
	                    <span class="arr-icon"></span>
                	</div>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">访问URL</span></div>
                <div class="right">
                    <input type="text"  id="request_url" class="g-input" value=""/>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">接口编号</span></div>
                <div class="right">
                    <input type="text"  id="request_id" class="g-input" value=""/>
                </div>
            </li>
        </ul>
    </div>
	<div class="item" style="text-align: center;padding-bottom:30px;" width="100%">
		<input type="submit" name="save" value="保存" class="g-submit1 g-btn-blue">
		<input type="button" name="return" value="返回" class="g-submit1 g-btn-gray">
	</div>
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
        node_interface_idx =[] ;
        var oper = <shiro:principal/>;
       
        $(".delete").on("click",function(){
        	node_interface_idx.length = 0; 
			var num = $("tbody input[name='interface_idx']:checked").length;
			
			$("tbody input[name='interface_idx']:checked").each(function(){
				node_interface_idx.push($(this).attr("id"));
			});
			if(num>0){
				$(".word").html("");
				$(".word").append("当前选择了<span class='font-red'> "+num+" </span>条数据<br>是否要删除选择的主机信息？");
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
        
        
      //删除当前行的数据
		$("tbody").on("click",".delete",function(){
			node_interface_idx.length = 0; 
			node_interface_idx.push($(this).parent().parent().find("div input[name='interface_idx']").attr("id"));
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
      
		$("tbody").on("click",".edit",function(obj){
			$(".error-msg").text("");
			var interface_idx=$(this).parent().parent().find("div input[name='interface_idx']").attr("id");
			
        	layerOpen({
        		"title":"编辑节点URL",
                "btnText":"保存",
                "btnColorClass":"g-btn-blue",
                "status" : true,
                "index":interface_idx
            });
        });
		

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
        
        $(".increase").on("click",function(){
        	queryNodeByParam();
        	$(".error-msg").text("");
        	
            layerOpen({
                "title":"新增节点URL",
                "btnText":"新增",
                "btnColorClass":"g-btn-blue",
                "status" : false
            });
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
            var baseURL=basePath+"/nodeInterface/deleteNodeInterface";
        	var size= $('#showSize option:selected').text();
        	if(node_interface_idx.length>0){
        		$.ajax({
    				url : baseURL,
    				type : "POST",
    				data : {
    					"req" : node_interface_idx.join(",")
    				},
    				success : function(data) {
    					if (data.state) {
    						GlobalShowMsg({showText:"操作成功",status:true});
    						
    						var Page = {
    							"page":"1",
    							"pageSize":size,
    						};
	   						$.select(Page);
	   						GlobalGLoading();
        				}
    				}
    			});
        	}
        });
       
       function queryNodeByParam(){
    	   $("#belongNode").empty();
    	   $("#toNode").empty();
        	$.ajax({
				url : basePath+"/node/queryNodeGroupByName",
				type : "POST",
				success : function(data) {
					$("#belongNode").append(
							"<option value=-1>选择所属节点</option>" );
					$("#toNode").append(
							"<option value=-1>选择访问节点</option>" );
					$(data.result).each(function(index,node){
						$("#belongNode").append(
								"<option value='"+node.node_name+"'>"+node.node_name+"</option>" );
						$("#toNode").append(
								"<option value='"+node.node_name+"'>"+node.node_name+"</option>" );
					});
				},
				error:function(data){
					alert("error");
				}
			});
        	
        } 
        
        
        /**新增、修改弹出框保存操作**/
		$("input[name='save']").on("click", function() {
			
			var from_node = $("#belongNode").val();
			var to_node = $("#toNode").val();
			var request_id = $("#request_id").val();
			var request_url = $("#request_url").val();
			var operator_idx = oper.operator_idx;
			
			if(from_node == -1){
				
				layer.tips('所属节点不能为空', '#belongNode', {tips: [2, '#ff6666']});
				return;
			}else if(to_node == -1){
				layer.tips('访问节点不能为空', '#toNode', {tips: [2, '#ff6666']});
				return;
			}else if(!request_url){
				layer.tips('节点URL不能为空', '#request_url', {tips: [2, '#ff6666']});
				return;
			} else if(!request_id){
				layer.tips('接口编号不能为空', '#request_id', {tips: [2, '#ff6666']});
				return;
			}
			
			var baseURL=basePath+"/nodeInterface/addNodeInterface";
			var interface_idx =0;
			if(isEdit){
				baseURL = basePath+"/nodeInterface/editNodeInterface";
				interface_idx = $("#interface_idx").val();
			}
			//新增
			var param = {
					"from_node" : from_node,
					"to_node" : to_node,
					"request_id" : request_id,
					"request_url" : request_url,
					"operator":operator_idx,
					"interface_idx":interface_idx
			};
			var size= $('#showSize option:selected').text();
			$.ajax({
				url : baseURL,
				type : "POST",
				data : {
					"req" : JSON.stringify(param)
				},
				success : function(data) {
					if (data.state) {
						if (editlLayer) {
							layer.close(editlLayer);
						}
						GlobalShowMsg({showText:"操作成功",status:true});
						var Page = {
							"page":"1",
							"pageSize":size,
						};
						$.select(Page);
						GlobalGLoading();
    				}else{
    					if(data.message != null && data.message != ''){
    						alert(data.message);
    					}else{
    						alert("操作失败！");
    					}
    				}
				},
				error:function(data){
					alert("操作失败");
					
				}
			});
		});
        
		/**取消新增、修改弹出框**/
		$("input[name='return']").on("click", function() {
			if (editlLayer) {
				layer.close(editlLayer);
			}

		});
    });
    
    var isEdit = false;
    function layerOpen(options){
        var defaults = {
            title:"",
            btnText : "新增"
        };
        $("#belongNode").empty();
    	$("#toNode").empty();
        options = $.extend(defaults,options);
        var $submit = $(".form-dialog .submit");
        $(".form-dialog .title").text(options.title);
        if(options.status){
        	isEdit = true;
        	var param = {
   				"interface_idx" : options.index
   			};
        	$.ajax({
    			url : basePath + "/nodeInterface/queryNodeInterfaceByPage",
    			type : "POST",
    			data : {
    				"req" : JSON.stringify(param)
    			},
    			success : function(data) {
    				if (data.state) {
    					
    					var result = data.result.rows[0];
    					$("#interface_idx").val(result.interface_idx);
    					
    					$("#request_url").val(result.request_url);
    					
    					$.ajax({
    						url : basePath+"/node/queryNodeGroupByName",
    						type : "POST",
    						success : function(data) {
    							$(data.result).each(function(index,node){
    								
    								if(node.node_name == result.from_node){
    									
    									$("#belongNode").append(
    											"<option value='"+node.node_name+"' selected=true>"+node.node_name+"</option>" );
    								}else{
    									$("#belongNode").append(
    											"<option value='"+node.node_name+"'>"+node.node_name+"</option>" );
    								}
    								if(result.to_node == node.node_name){
    									$("#toNode").append(
    											"<option value='"+node.node_name+"' selected=true>"+node.node_name+"</option>" );
    								}else{
    									$("#toNode").append(
    											"<option value='"+node.node_name+"'>"+node.node_name+"</option>" );
    								} 
    							});
    						},
    						error:function(data){
    							alert("error");
    						}
    					});
    					
    					$("#request_id").val(result.request_id);
    				}
    			}
        	});
        }
        else{
        	$("#interface_idx").val("");
        	$("#request_url").val("");
        	$("#from_node").removeAttr("disabled");
        	$("#to_node").val("");
        	$("#request_id").val("");
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
            area:["650px"],
            offset :["180px"],
            content: $('.form-dialog')
        });
    };
    
    
    /**封装的查询方法**/
    jQuery.select = function(param) {
    	var paramType = $("#paramType").val();
    	var value = $("#keyword").val();
    	if(value != null && value.length > 0){
    		if(paramType == -1){
        		paramType = 'from_node';
    		}
    		param[paramType] = value;
    		$("#paramType").val(paramType);
    	}
    	
		$.ajax({
			url : basePath + "/nodeInterface/queryNodeInterfaceByPage",
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
							+ "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='interface_idx' id='"+item.interface_idx+"' /></div></td>"
							+ "<td class='col2'>"
							+ item.request_id
							+ "</td>"
							+ "<td class='col3'>"
							+ item.from_node
							+ "</td>"
							+ "<td class='col4'>"
							+ item.to_node
							+ "</td>"
							+ "<td class='col6'>"
							+ item.request_url
							+ "</td>"
							+ "<td class='col7'><span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span></td>"
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
