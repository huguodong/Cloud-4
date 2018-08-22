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
                <input type="text" name="keyword" id="keyword"  class="input g-input" placeholder="输入云主机编号" />
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
	                    <th class="col2">云主机编号</th>
	                    <th class="col3">云主机名称</th>
	                    <th class="col4">云主机IP1</th>
	                    <th class="col5">云主机IP2</th>
	                    <th class="col6">备注</th>
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
    <div class="form-wrap">
   		<input type="hidden" name="host_idx"  id="host_idx" value="" />
        <ul>
            <li>
                <div class="left"><span class="g-mustIn">主机编号</span></div>
                <div class="right">
                    <input type="text" name="host_id"  id="host_id" class="g-input" value=""/>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">主机名称</span></div>
                <div class="right">
                    <input type="text" name="host_name"  id="host_name" class="g-input" value=""/>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">主机IP1</span></div>
                <div class="right">
                    <input type="text" name="host_ip1"  id="host_ip1" class="g-input" value=""/>
                </div>
            </li>
            <li>
                <div class="left">主机IP2</div>
                <div class="right">
                    <input type="text" name="host_ip2"  id="host_ip2" class="g-input" value=""/>
                </div>
            </li>
            <li>
                <div class="left">备注</div>
                <div class="right">
                	<textarea class="g-textarea" style="width:360px" cols="150" rows="" name="host_desc" id="host_desc"  placeholder="输入内容"></textarea>
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
        main_host_idx =[] ;
        $(".delete").on("click",function(){
        	main_host_idx.length = 0; 
			var num = $("tbody input[name='idx']:checked").length;
			$("tbody input[name='idx']:checked").each(function(){
				main_host_idx.push($(this).attr("id"));
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
			main_host_idx.length = 0; 
			main_host_idx.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
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
			var host_idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
        	layerOpen({
        		"title":"编辑云主机",
                "btnText":"保存",
                "btnColorClass":"g-btn-blue",
                "status" : true,
                "index":host_idx
            });
        });
		

        /**查询按钮操作**/
		$(".search").on("click",function(){
			GlobalGLoading();
			
			var size= $('#showSize option:selected').text();
			var host_id = $("#keyword").val();
			var param={
				"host_id":host_id,"page":1,"pageSize":size
			};
			$.select(param);
		});
        
		//上一页操作
		$("#page").on("click", ".prev-page", function() {
			GlobalGLoading();
			
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) - 1;
			var size = $('#showSize option:selected').html();
			var host_id = $("#keyword").val();
			var param={
				"host_id":host_id,"page":page,"pageSize":size
			};
			$.select(param);
		});
		//下一页操作
		$("#page").on("click", ".next-page", function() {
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) + 1;
			var size = $('#showSize option:selected').html();
			var host_id = $("#keyword").val();
			var param={
				"host_id":host_id,"page":page,"pageSize":size
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
			var host_id = $("#keyword").val();
			var param={
				"host_id":host_id,"page":page,"pageSize":size
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
			var host_id = $("#keyword").val();
			var param={
				"host_id":host_id,"page":page,"pageSize":size
			};
			$.select(param);

		});
        
        $(".increase").on("click",function(){
        	$(".error-msg").text("");
            layerOpen({
                "title":"新增云主机",
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
            var baseURL=basePath+"/host/delete";
        	var size= $('#showSize option:selected').text();
        	if(main_host_idx.length>0){
        		$.ajax({
    				url : baseURL,
    				type : "POST",
    				data : {
    					"req" : main_host_idx.join(",")
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
        
        /**新增、修改弹出框保存操作**/
		$("input[name='save']").on("click", function() {
			var host_idx = $("input[name='host_idx']").val();
			var host_id = $("input[name='host_id']").val();
			var host_name = $("input[name='host_name']").val();
			var host_ip1 = $("input[name='host_ip1']").val();
			var host_ip2 = $("input[name='host_ip2']").val();
			var host_desc = $("#host_desc").val();
			
			if(!host_id){
				layer.tips('主机编号不能为空', '#host_id', {tips: [2, '#ff6666']});
				return;
			}else if(!host_name){
				layer.tips('主机名称不能为空', '#host_name', {tips: [2, '#ff6666']});
				return;
			}else if(!host_ip1){
				layer.tips('主机IP1不能为空', '#host_ip1', {tips: [2, '#ff6666']});
				return;
			}else if(!isIP(host_ip1)){
				layer.tips('主机IP1不正确', '#host_ip1', {tips: [2, '#ff6666']});
				return;
			}else if(host_ip2&&!isIP(host_ip2)){
				layer.tips('主机IP2不正确', '#host_ip2', {tips: [2, '#ff6666']});
				return;
			}
			
			if (editlLayer) {
				layer.close(editlLayer);
			}
			
			var baseURL=basePath+"/host/add";
			if (isEdit) {//编辑
				baseURL=basePath+"/host/update";
				if(!host_idx){
					return;
				}
				var param = {
					"host_idx" : host_idx,
					"host_id" : host_id,
					"host_name" : host_name,
					"host_ip1" : host_ip1,
					"host_ip2" : host_ip2,
					"host_desc" : host_desc,
				};
			}else{//新增
				var param = {
					"host_id" : host_id,
					"host_name" : host_name,
					"host_ip1" : host_ip1,
					"host_ip2" : host_ip2,
					"host_desc" : host_desc,
				};
			}
			var size= $('#showSize option:selected').text();
			$.ajax({
				url : baseURL,
				type : "POST",
				data : {
					"req" : JSON.stringify(param)
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
        }
        options = $.extend(defaults,options);

        var $submit = $(".form-dialog .submit");
        $(".form-dialog .title").text(options.title);
        if(options.status){
        	isEdit = true;
        	var param = {
   				"host_idx" : options.index,
   			};
        	$.ajax({
    			url : basePath + "/host/queryById",
    			type : "POST",
    			data : {
    				"req" : JSON.stringify(param)
    			},
    			success : function(data) {
    				if (data.state) {
    					$("#host_idx").val(data.result.host_idx);
    					$("#host_id").val(data.result.host_id);
    					$("#host_id").attr("disabled",true);
    					$("#host_name").val(data.result.host_name);
    					$("#host_ip1").val(data.result.host_ip1);
    					$("#host_ip2").val(data.result.host_ip2);
    		        	$("#host_desc").val(data.result.host_desc);
    				}
    			}
        	});
        }
        else{
        	$("#host_idx").val("");
        	$("#host_id").val("");
        	$("#host_id").removeAttr("disabled");
        	$("#host_name").val("");
        	$("#host_ip1").val("");
        	$("#host_ip2").val("");
        	$("#host_desc").val("");
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
		$.ajax({
			url : basePath + "/host/queryByParam",
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
						$.each(data.result.rows,function(i, item) {
							$("tbody").append(
								"<tr>"
								+ "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.host_idx+"' /></div></td>"
								+ "<td class='col2'>"
								+ item.host_id
								+ "</td>"
								+ "<td class='col3'>"
								+ item.host_name
								+ "</td>"
								+ "<td class='col4'>"
								+ item.host_ip1
								+ "</td>"
								+ "<td class='col5'>"
								+ (item.host_ip2==null?"":item.host_ip2)
								+ "</td>"
								+ "<td class='col6'>"
								+ (item.host_desc==null?"":item.host_desc)
								+ "</td>"
								+ "<td class='col7'><span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span></td>"
								+ "</tr>");
							
						});
					}
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
