<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<%@ include file="../common/global.jsf" %>
<style type="text/css">
	.machine-list .item.gray .sec3 .status {
   	 	background: url(${pageContext.request.contextPath}/static/images/cir-status4.png) right center no-repeat;
	}
	 .machine-list .item.blue .sec3 .status{
   	 	background: url(${pageContext.request.contextPath}/static/images/cir-status5.png) right center no-repeat;
	}
</style>
</head>
<body>
<div class="g-loading"></div>
<%@include file="../common/header.jsf" %>
<div class="page-title-bar">
	<span class="title">节点监控<a href="/static/help-page.html" target="_blank" class="g-help"></a></span>

	<div class="choose-lib">
		<div class="g-select">
			<select id="nodeModel">
				<option value="" selected>选择节点类型</option>
				<option value="view">视图节点</option>
				<option value="business">业务节点</option>
				<option value="db">数据节点</option>
			</select> <span class="arr-icon"></span>
		</div>
		<div class="g-select">
			<select id="nodeType">
				<option value="" selected>选择业务类型</option>
			</select> <span class="arr-icon"></span>
		</div>
	</div>


	<div class="form-wrap fr">
		<div class="g-select">
			<select id="machineState">
				<option value="-1" selected>选择状态</option>
				<option value="0">正常</option>
				<option value="1">报警</option>
				<option value="2">失效</option>
			</select> <span class="arr-icon"></span>
		</div>
		<input type="text" name="keyWord" id="" class="input g-input" placeholder="输入节点名" />
		<div href="" class="btn search">查询</div>
	</div>
</div>
<c:choose>
<c:when test="${operator.operator_type eq '2'}">
	<shiro:hasPermission name="0103000000">
		<div class="main">
			<div class="machine-list">
				<div class="item-wrap">
				
				</div>
			</div>
		</div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="0103000000">
		<div class="main">
			<div class="machine-list">
				<div class="item-wrap">
					没有权限！
				</div>
			</div>
		</div>
	</shiro:lacksPermission>
</c:when>
<c:otherwise>
	<div class="main">
	<div class="machine-list">
			<div class="item-wrap">
			</div>
		</div>
	</div>
</c:otherwise>
</c:choose>

<!-- 弹框 -->
<div class="form-device-dialog">
    <div class="device-header"><span class="title"></span></div>
    <div class="device-content">
    </div>
</div>

<!-- 页脚-分页 -->
<%@ include file="../include/page_bar.jsf" %>


<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/devpagination.js"></script>
<script type="text/javascript">
var basePath=null;
$(function(){
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();
		var location = (window.location+'').split('/');  
		basePath = location[0]+'//'+location[2]+'/'+location[3];
		$(".main").css({
			"min-height":winH - headerH - pagingBarH -pageTitleBar,
			"max-height":"auto"
		});
	})();
	
	var detailLayer = null;

	//加载节点类型列表
	$.ajax({
		url : basePath + "/nodeMonitor/getTypeList",
		type : "POST",
		success : function(data) {
			if (data.state) {
				$("#nodeType").empty();//清空下拉框
				var _html="<option value='' selected>选择业务类型</option>";
				$.each(data.result,function(i, item) {
					_html+="<option value='"+item.node_type_idx+"'>"+item.node_type_name+"</option>";
				});
				$("#nodeType").append(_html);
			}
		}
	});
	

	/**
		切屏
	**/
	var changeScreen=function(){
		//获取当前页
		var currentpage = $("li.active").html();//当前页
		page = Number(currentpage) + 1;//下一页
		var maxPage=$("#page").find("li:last").html();//最大页数
		if(currentpage==maxPage){//最后一页
			var Page=makeQueryParam(1, pageSize);
			$.select(Page);
			$("#page").find("li").each(function(index,dom){
				if($(dom).html()=="1"){
					$(dom).trigger("click");
				}
			});
			return;
		}
		$("#page").find("li").each(function(index,dom){
			if($(dom).html()==page){
				//表示有下一页
				var Page=makeQueryParam(page, pageSize);
				$.select(Page);
				$(dom).trigger("click");
			}
		});
	};
	
	
	//组装 翻页和查询 参数
	var makeQueryParam=function(page,pageSize){
		var nodeModel=$("select#nodeModel").val();
		var nodeType=$("select#nodeType").val();
	 	$inputText=$("div.page-title-bar").find("input[name=keyWord]");
		var machineState=$("select#machineState").val();
		var keyWord=$inputText.val();
		var Page ={
			"page":page,
			"pageSize":pageSize,
			"node_model":nodeModel,
			"node_type_idx":nodeType,
			"node_status":machineState,
			"node_name":keyWord,
		};
		return Page;
	};
	
	var _interval;
	var changeScreenInterval;
	var pageSize=12;//每页显示的条数
	
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $("li.active").html();//当前页
		page = Number(currentpage) + 1;//下一页
		var Page = makeQueryParam(page, pageSize);
		//调用页面的查询ajax
		$.select(Page);
	});
	$("#page").on("click",".prev-page",function(){
		var currentpage = $("li.active").html();
		var page=Number(currentpage)-1;
		var Page=makeQueryParam(page, pageSize);
		//带参数
		$.select(Page);
	});
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))return;
		var page = $(this).html();
		
		if(page=="...") return;	
		var Page=makeQueryParam(page, pageSize);
		$.select(Page);
	});
	
	var pageObj ={"page":1,"pageSize":12};
	$.select(pageObj);
	
	
	function sleep(d){
  		for(var t = Date.now();Date.now() - t <= d;);
	}
	
	function minusDate(date,days){ 
		var d=new Date(date); 
		d.setDate(d.getDate()-days); 
		var m=d.getMonth()+1; 
		return d.getFullYear()+'-'+m+'-'+d.getDate(); 
	} 

	$(document).on("change",":checkbox",function(){
		var $parentItem = $(this).parents(".item");
		if($(this).is(":checked")){
			$parentItem.addClass("active");
		}else{
			$parentItem.removeClass("active");
		}
	});

	(function checkAllCheckbox(){
		$(document).find(".item.active").find(".g-checkbox").addClass("on").find("input").prop("checked",true);
	})();

	$(".play-btn,.pause-btn").on("click",function(){
		$(this).hide().siblings().show();
	});
	
	$(document).on("click",".machine-name",function(){
		var curCheckbox = $(this).siblings(".g-checkbox").find("input");

		if(curCheckbox.is(":checked")){
			curCheckbox.prop("checked",false);
		}else{
			curCheckbox.prop("checked",true);
		}

		var $parentItem = $(this).parents(".item");
		if(curCheckbox.is(":checked")){
			$parentItem.addClass("active").find(".g-checkbox").addClass("on");
		}else{
			$parentItem.removeClass("active").find(".g-checkbox").removeClass("on");
		}
		var num=$(".machine-list").find("input[type=checkbox]:checked").length;
		$("#ChooseNum").find(".total-choosen-num").html(num);
	});
	
	//改变刷新时间
	$(document).on("change",".g-select.refresh select",function(){
		var seconds=$(this).val();//获取刷新周期
		//更改周期
		clearInterval(changeScreenInterval);
		if(seconds==-1){
			//不切屏
		}else{
			changeScreenInterval=setInterval(changeScreen,seconds);
		}
	});
	//
	//点击查询按钮，查询
	//
	$(document).on("click",".btn.search",function(){
		 var currentpage = $("li.active").html();
		 if(!currentpage){
			 currentpage=1;
		 }
		 //查询条件 设备类型 设备状态 关键字（nodeMonitor_name）
		 var Page=makeQueryParam(currentpage, pageSize);
		 $.select(Page);
	});
	
	
	$(document).on("click","div.check-detail",function(){
		var id=$(this).parent().find("#node_idx").text();
		var title=$(this).parent().parent().find(".machine-name").attr("title");
		
		var param = {
			"node_idx" : id,
		};
		$.ajax({
			url : basePath + "/nodeMonitor/queryById",
			type : "POST",
			data : {
				"req" : JSON.stringify(param)
			}, 
			success : function(data) {
				if (data.state) {
					detailLayer = layer.open({
			            type: 1,
			            shade: false,
			            title: false, //不显示标题
			            scrollbar :false,
			            closeBtn :1,
			            shade:0.5,
			            shadeClose :true,
			            area:["800px"],
			            offset :["150px"],
			            content: $('.form-device-dialog')
			        });
			        $(".form-device-dialog .title").text(title);
			        
			        var node=data.result;
			        
			        var node_status=node.node_status;
			        var status='';
					if(node_status==0){
						status='正常';
				 	}else if(node_status==1){
				 		status='报警';
				 	}else if(node_status==2){
						status='失效';
				 	}
			        
					var type='主节点';
					if(node.node_attributes=='primary'){
						type='主节点';
					}else if(node.node_attributes=='secondary'){
						type='从节点';
					}
					
					var processTime='';
					if(node.process_info){
						processTime = node.process_info+"ms";
					}
					var queueLen=0;
					if(node.queue_length){
						queueLen = node.queue_length;
					}
					var sendTime='无记录';
					if(node.send_time){
						sendTime = node.send_time;
					}
			        
			        var html='';
			        html+='<table>'
			        	+'<tbody>'
			        	+'<tr>'
			        	+'<td class="title">节点名称</td>'
			        	+'<td class="context">'+node.node_name+'</td>'
			        	+'<td class="title">节点属性</td>'
			        	+'<td class="context">'+type+'</td>'
			        	+'</tr>'
			        	+'<tr>'
			        	+'<td class="title">所属容器</td>'
			        	+'<td class="context">'+node.container_name+'</td>'
			        	+'<td class="title">节点类型</td>'
			        	+'<td class="context">'+node.node_type_name+'</td>'
			        	+'</tr>'
			        	+'<tr>'
			        	+'<td class="title">节点状态</td>'
			        	+'<td class="context">'+status+'</td>'
						+'<td class="title">当前队列长度</td>'
						+'<td class="context">'+queueLen+'</td>'
						+'</tr>'
						+'<tr>'
						+'<td class="title">处理时长</td>'
						+'<td class="context">'+processTime+'</td>'
						+'<td class="title">最新接收时间</td>'
						+'<td class="context">'+sendTime+'</td>'
						+'</tr>'
						+'</tbody>'
						+'</table>';
						
					$("div.device-content").empty().html(html);
						
				}
			}
		});
    });
	
});


/**封装的查询方法**/
jQuery.select = function(param) {
	$.ajax({
		url : basePath + "/nodeMonitor/queryByParam",
		type : "POST",
		data : {
			"req" : JSON.stringify(param)
		},
		success : function(data) {
			$("tbody").html("");
			if (data.state) {
				var page=data.result;
				if(page&&page.rows){
					drawRow(page.rows);
					$.diviceMainPagination(page);
					 if(page.rows.length==0){
	 					$(".machine-list").find(".item-wrap").html("没有查询到匹配的设备");
					 }
				}
			}
			$.pagination(data.result);
		}
	});
};

//机器状态使用颜色进行标注（如红色、灰色、黄色、绿色  等）
/**加载页面**/

var drawRow=function(rows){
	if(!rows)return;
	$("div.main").find(".item-wrap").html("");
	for(var i=0;i<rows.length;i++){
		var node=rows[i];
		var node_name=node.node_name;
		var node_id=node.node_id;
		var node_status=node.node_status;
		var type='主节点';
		if(node.node_attributes=='primary'){
			type='主节点';
		}else if(node.node_attributes=='secondary'){
			type='从节点';
		}
		
		var time='';
		if(node.send_time==null||node.send_time==''){
			time='';
		}else{
			time=node.send_time;
		}
		
		var item='';
		if(node_status==0){
	 		item+='<div class="item">';
	 	}else if(node_status==1){
	 		item+='<div class="item error">';
	 	}else if(node_status==2){
	 		item+='<div class="item gray">';
	 	}
		
		item+='<div class="sec1">'
	 	+'<div class="g-checkbox"><input type="checkbox" name="'+node_name+'"></div>'
	 	+'<span class="machine-name" title="'+node_name+'">'+node_name+'</span>'
	 	+'</div>'
	 	+'<div class="sec2"><ul class="nodeMonitor_state">'
	 	+'<li>业务类型：'+node.node_type_name+'</li>'
	 	+'<li>节点属性：'+type+'</li>'
	 	+'<li>最后时间：'+time+'</li>'
	 	+'</ul></div>'
	 	+'<div class="sec3"><span id="node_idx" style="display:none">'
	 	+node.node_idx
	 	+'</span>'
	 	+'ID：'+node_id
	 	if(node_status==0){
	 		item+='<span class="status">正常</span>';
	 	}else if(node_status==1){
	 		item+='<span class="status">报警</span>';
	 	}else if(node_status==2){
	 		item+='<span class="status">失效</span>';
	 	}
	 	item+='<div class="check-detail">查看详情</div></div></div>';
	 	//console.info(item);
	 	$("div.main").find(".item-wrap").append(item);
	};
};

</script>
</body>
</html>





