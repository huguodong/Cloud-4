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
    <title>容器管理</title>
</head>
<body>
<div class="equipment-guanli">
        <div class="page-title-bar">
            <span class="title">容器管理<a href="${pageContext.request.contextPath}/static/help-page.html" target="_blank" class="g-help"></a></span>
            <div class="form-wrap fr">
                <input type="text" name="keyword" id="keyword" class="input g-input" placeholder="输入容器编号" />
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
	                    <th class="col2">容器编号</th>
	                    <th class="col3">容器名称</th>
	                    <th class="col4">所属主机</th>
	                    <th class="col5">端口</th>
	                    <th class="col6">协议类型</th>
	                    <th class="col7">存储路径</th>
	                    <th class="col8">是否使用</th>
	                    <th class="col9">操作</th>
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
        当前选择了 <span class="font-red"></span> 个项目<br>
        是否要删除选择的配置？
    </div>
    <div class="form-btn cancel g-btn-gray">取消</div>
    <div class="form-btn remove g-btn-red">删除</div>
</div>

<div class="form-dialog">
    <div class="title">新增容器</div>
    <div class="form-wrap">
   	 <input type="hidden" name="container_idx"  id="container_idx" value="" />
        <ul>
            <li>
                <div class="left"><span class="g-mustIn">容器编号</span></div>
                <div class="right">
                    <input type="text" name="container_id"  id="container_id" class="g-input"/>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">容器名称</span></div>
                <div class="right">
                    <input type="text" name="container_name"  id="container_name" class="g-input"/>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">所属主机</span></div>
                <div class="right">
                    <div class="g-select">
                        <select name="host_idx" id="host_idx">
                            <option value="" selected>请选择</option>
                        </select>
                        <span class="arr-icon"></span>
                    </div>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">协议类型</span></div>
                <div class="right">
                    <div class="g-select">
                        <select name="protocol_type" id="protocol_type">
                            <option value="http" selected>http</option>
                            <option value="https">https</option>
                        </select>
                        <span class="arr-icon"></span>
                    </div>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">端口</span></div>
                <div class="right">
                    <input type="text" name="container_port" id="container_port" class="g-input" placeholder="请输入"  />
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">存储路径</span></div>
                <div class="right">
                    <input type="text" name="save_path" id="save_path" class="g-input" placeholder="如:/usr/tomcat1"  />
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">是否使用</span></div>
                <div class="right">
                    <div class="g-select">
                        <select name="is_disable" id="is_disable">
                            <option value="1" selected>是</option>
                            <option value="0">否</option>
                        </select>
                        <span class="arr-icon"></span>
                    </div>
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
		var delLayer = null;
		main_node_idx =[] ;
		$(".delete").on("click", function() {
			main_node_idx.length = 0; 
			var num = $("tbody input[name='idx']:checked").length;
			$("tbody input[name='idx']:checked").each(function(){
				main_node_idx.push($(this).attr("id"));
			});
			if(num>0){
				$(".word").html("");
				$(".word").append("当前选择了<span class='font-red'> "+num+" </span>条数据<br>是否要删除选择的容器信息？");
				delLayer = layer.open({
					type : 1,
					shade : false,
					title : false, //不显示标题
					scrollbar : false,
					closeBtn : 0,
					shade : 0.5,
					shadeClose : true,
					area : [ "400px" ],
					offset : [ "195px" ],
					content : $('.g-delete-dialog')
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
		
		$("tbody").on("click",".edit",function(obj){
			$(".error-msg").text("");
			var type_idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
        	layerOpen({
                "title":"编辑容器",
                "btnText":"保存",
                "btnColorClass":"g-btn-blue",
                "status" : true,
                "index":type_idx
            });
        });
		//启动
		$("tbody").on("click",".start",function(obj){
			var container_idx=$(this).parent().parent().find("input[name='idx']").attr("id");
			var container=$(this).parent().parent().find("input[name='container']").attr("id");
			var baseURL=basePath+"/container/start";
			var param={
				"container":container,
				"container_idx":container_idx
			};
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
    				}else{
    					GlobalShowMsg({showText:"操作失败",status:false});
    				}
				}
			});
        });
		//停止
		$("tbody").on("click",".stop",function(obj){
			var container_idx=$(this).parent().parent().find("input[name='idx']").attr("id");
			var container=$(this).parent().parent().find("input[name='container']").attr("id");
			var baseURL=basePath+"/container/stop";
			var param={
				"container":container,
				"container_idx":container_idx
			};
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
					}else{
    					GlobalShowMsg({showText:"操作失败",status:false});
    				}
				}
			});
        });
		
		/**查询按钮操作**/
		$(".search").on("click",function(){
			GlobalGLoading();
			
			var size= $('#showSize option:selected').text();
			var container_id = $("#keyword").val();
			var param={
				"container_id":container_id,"page":1,"pageSize":size
			};
			$.select(param);
		});
		
		//上一页操作
		$("#page").on("click", ".prev-page", function() {
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) - 1;
			var size = $('#showSize option:selected').html();
			var container_id = $("#keyword").val();
			var param={
				"container_id":container_id,"page":page,"pageSize":size
			};
			$.select(param);
		});
		
		//下一页操作
		$("#page").on("click", ".next-page", function() {
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) + 1;
			var size = $('#showSize option:selected').html();
			var container_id = $("#keyword").val();
			var param={
				"container_id":container_id,"page":page,"pageSize":size
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
			var container_id = $("#keyword").val();
			var param={
				"container_id":container_id,"page":page,"pageSize":size
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
			var container_id = $("#keyword").val();
			var param={
				"container_id":container_id,"page":page,"pageSize":size
			};
			$.select(param);
		});

		$(".increase").on("click", function() {
			$(".error-msg").text("");
			layerOpen({
				"title" : "新增容器",
				"btnText" : "新增",
				"btnColorClass" : "g-btn-blue",
				"status" : false
			});
		});

		$(".form-btn.cancel").on("click", function() {
			if (delLayer) {
				layer.close(delLayer);
			}
		});
		$(".form-btn.remove").on("click", function() {
			/*执行删除操作*/
			if (delLayer) {
				layer.close(delLayer);
			}
			var baseURL=basePath+"/container/delete";
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
    						var Page = {
    							"page":"1",
    							"pageSize":size,
    						};
   							$.select(Page);
        				}
   						GlobalGLoading();
    				}
    			});
        	}
		});
		
		/**新增、修改弹出框保存操作**/
		$("input[name='save']").on("click", function() {
			var container_idx = $("input[name='container_idx']").val();
			var container_id = $("input[name='container_id']").val();
			var container_name = $("input[name='container_name']").val();
			var host_idx = $("select[name='host_idx']").val();
			var protocol_type = $("select[name='protocol_type']").val();
			var container_port = $("input[name='container_port']").val();
			var save_path = $("input[name='save_path']").val();
			var is_disable = $("select[name='is_disable']").val();
			if(!container_id){
				layer.tips('容器编号不能为空', '#container_id', {tips: [2, '#ff6666']});
				return;
			}else if(!container_name){
				layer.tips('容器名称不能为空', '#container_name', {tips: [2, '#ff6666']});
				return;
			}else if(!host_idx){
				layer.tips('请选择所属主机', '#host_idx', {tips: [2, '#ff6666']});
				return;
			}else if(!container_port){
				layer.tips('端口不能为空', '#container_port', {tips: [2, '#ff6666']});
				return;
			}else if(!isPort(container_port)){
				layer.tips('请填写正确的端口', '#container_port', {tips: [2, '#ff6666']});
				return;
			}else if(!save_path){
				layer.tips('存储路径不能为空', '#save_path', {tips: [2, '#ff6666']});
				return;
			}else if(!is_disable){
				layer.tips('请选择是否使用', '#is_disable', {tips: [2, '#ff6666']});
				return;
			}
			
			if (editlLayer) {
				layer.close(editlLayer);
			}
			var baseURL = basePath + "/container/add";
			if (isEdit) {//编辑
				baseURL = basePath + "/container/update";
				if(!container_idx){
					return;
				}
				var param = {
					"container_idx" : container_idx,
					"container_id" : container_id,
					"container_name" : container_name,
					"host_idx" : host_idx,
					"protocol_type" :protocol_type,
					"container_port" : container_port,
					"save_path" : save_path,
					"is_disable" : is_disable,
				};
			} else {//新增
				var param = {
					"container_id" : container_id,
					"container_name" : container_name,
					"host_idx" : host_idx,
					"protocol_type" :protocol_type,
					"container_port" : container_port,
					"save_path" : save_path,
					"is_disable" : is_disable,
				};
			}
			var size = $('#showSize option:selected').text();
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
							"page" : "1",
							"pageSize" : size,
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
		
		$.ajax({
			url : basePath + "/container/getHostList",
			type : "POST",
			success : function(data) {
				var _html="";
				if (data.state) {
					$("#host_idx").empty();//清空下拉框
					$("#host_idx").append("<option value='' selected>请选择</option>");
					$.each(data.result,function(i, item) {
						_html+="<option value='"+item.host_idx+"'>"+item.host_name+"</option>"
					});
					$("#host_idx").append(_html);
				}
			}
		});
	});

	var isEdit = false;
	/**封装的弹出操作**/
	function layerOpen(options) {
		var defaults = {
			title : "",
			btnText : "新增"
		}
		options = $.extend(defaults, options);

		var $submit = $(".form-dialog .submit");
		$(".form-dialog .title").text(options.title);
		if (options.status) {
			isEdit = true;
			var param = {
				"container_idx" : options.index,
			};
			$.ajax({
				url : basePath + "/container/queryById",
				type : "POST",
				data : {
					"req" : JSON.stringify(param)
				},
				success : function(data) {
					if (data.state) {
						$("input[name='container_idx']").val(data.result.container_idx);
						$("input[name='container_id']").val(data.result.container_id);
						$("input[name='container_id']").attr("disabled",true);
						$("input[name='container_name']").val(data.result.container_name);
						$("select[name='host_idx']").val(data.result.host_idx);
						$("select[name='protocol_type']").val(data.result.protocol_type);
						$("input[name='container_port']").val(data.result.container_port);
						$("input[name='save_path']").val(data.result.save_path);
						$("select[name='is_disable']").val(data.result.is_disable);
					}
				}
			});
		} else {
			isEdit = false;
			$("input[name='container_idx']").val("");
			$("input[name='container_id']").val("");
			$("input[name='container_id']").removeAttr("disabled");
			$("input[name='container_name']").val("");
			$("select[name='host_idx']").val("");
			$("select[name='protocol_type']").val("http");
			$("input[name='container_port']").val("");
			$("input[name='save_path']").val("");
			$("select[name='is_disable']").val("");
		}
		$submit.val(options.btnText);
		$submit.removeClass("g-btn-green").removeClass(".g-btn-blue");
		$submit.addClass(options.btnColorClass);
		editlLayer = layer.open({
			type : 1,
			shade : false,
			title : false, //不显示标题
			scrollbar : false,
			closeBtn : 1,
			shade : 0.5,
			shadeClose : true,
			area : [ "600px" ],
			offset : [ "200px" ],
			content : $('.form-dialog')
		});
	};

	/**封装的查询方法**/
	jQuery.select = function(param) {
		$.ajax({
			url : basePath + "/container/queryByParam",
			type : "POST",
			data : {
				"req" : JSON.stringify(param)
			},
			success : function(data) {
				$("tbody").html("");
				if (data.state) {
					if (data.result.rows&& data.result.rows.length == 0) {
						var index = param.page;
						if (index > 1) {
							param.page = index - 1;
							$.select(param);
						}
					} else {
						$.each(data.result.rows,function(i, item) {
							var isDisable='';
							var _html="";
							if(item.is_disable=='1'){
								isDisable='是';
								_html="<span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span><span class='btn-a start'>启动</span><span class='btn-a stop'>停止</span>";
							}else if(item.is_disable=='0'){
								isDisable='否';
								_html="<span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span>";
							}
							$("tbody").append(
							"<tr>"
							+ "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.container_idx+"' /></div></td>"
							+ "<td class='col2'><input type='hidden' name='container' id='"+item.container_id+"' />"
							+ item.container_id
							+ "</td>"
							+ "<td class='col3'>"
							+ item.container_name
							+ "</td>"
							+ "<td class='col4'>"
							+ item.host_name
							+ "</td>"
							+ "<td class='col5'>"
							+ item.container_port
							+ "</td>"
							+ "<td class='col6'>"
							+ item.protocol_type
							+ "</td>"
							+ "<td class='col7'>"
							+ item.save_path
							+ "</td>"
							+ "<td class='col8'>"
							+ isDisable
							+ "</td>"
							+ "<td class='col9'>"
							+ _html
							+ "</td>"
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
	
	function isPort(str){  
	    var parten=/^(\d)+$/g;  
	    if(parten.test(str)&&parseInt(str)<=65535&&parseInt(str)>=0){  
	        return true;  
	     }else{  
	        return false;  
	     }   
	}
</script>
</body>
</html>
