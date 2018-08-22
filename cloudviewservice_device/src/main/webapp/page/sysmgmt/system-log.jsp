<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
.system-log .col9{
	width: 156px;
}
.system-log .col5 {
    width: 150px;
}
/*tbody{width:400px;line-height:20px;padding:10px;border-bottom:1px dashed #ccc;font-size:12px;text-indent:25px;}*/
tbody .all{display:none;}
tbody .more{text-decoration:none;color:#0066CC;cursor:pointer;}
</style>
<!DOCTYPE html>

<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->

 <head>
  
 </head>
  
  <body>
   <div class="system-log">
	<div class="g-loading"></div>
		<div id="page-title-bar" class="page-title-bar">
			<span class="title">系统日志<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/sysmgmt/system-log.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-select">
					<select id="choose-type">
						<option value="-1" selected>选择日志类型</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<div class="g-select">
					<select id="choose-oper">
						<option value="-1" selected>选择操作者</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<div class="g-inputdate"><input type="text" id="begin_time" placeholder="起始时间" class="g-input datepicker"><span class="icon"></span></div> 
				<span class="fl">-</span>
				<div class="g-inputdate"><input type="text" id="end_time" placeholder="结束时间" class="g-input datepicker"><span class="icon"></span></div> 
				<div class="btn search">查询</div>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col2">操作时间</th>
						<th class="col3">操作类型</th>
						<th class="col4">操作说明</th>
						<th class="col5">客户端</th>
						<th class="col6">操作成功</th>
						<!--<th class="col7">查询错误码</th>-->
						<th class="col8">操作者ID</th>
						<th class="col9">操作者名称</th>
					</tr>
				</thead>
				<tbody>
					<%--
						<tr>
							<td>2013-2-20 13:20:50</td>
							<td>数据1</td>
							<td>数据2</td>
							<td>127.0.0.1：8080</td>
							<td>是</td>
							<td>ERR_005</td>
							<td>管理员ID</td>
							<td>Name</td>
						</tr> 
					--%>
				</tbody>
			</table>
		</div>
		<div class="paging-bar">
			<div class="left">
				<span class="t2 fl">显示</span>
				<div class="g-select refresh">
					<select id="showSize">
						<option value="10" selected>10</option>
						<option value="30" >30</option>
						<option value="60">60</option>
					</select>
					<span class="arr-icon"></span>
				</div>
			</div>
			<div id="page" class="right">
			</div>
			<span class="total-page fr"></span>
			<span class="total-num fr"></span>
	</div>	
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
	
	$(".datepicker" ).datepicker({
		numberOfMonths:1,//显示几个月  
		showButtonPanel:false,//是否显示按钮面板  
		dateFormat: 'yy-mm-dd',//日期格式  
		clearText:"清除",//清除日期的按钮名称  
		closeText:"关闭",//关闭选择框的按钮名称  
		yearSuffix: '', //年的后缀  
		showMonthAfterYear:true,//是否把月放在年的后面  
		defaultDate:new Date(),//默认日期  
		//minDate:'2011-03-05',//最小日期  
		//maxDate:'2011-03-20',//最大日期  
		monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
		dayNamesMin: ['日','一','二','三','四','五','六'],  
		onSelect: function(selectedDate) {//选择日期后执行的操作  
	
	
		}  
	});
	
	$(".search").on("click",function(){
		var param =GetSearchInfo();
		var size= $(".g-select.refresh").find("option:selected").html();
		param.page=1;
		param.pageSize=size;
		if(o.operator_type >= 3){
			param.library_idx = o.library_idx;	
		}
		$.select(param);
	});
	
	jQuery.select=function(param){
		$.ajax({
			url:"${pageContext.request.contextPath}/systemlog/SelectSystemLog",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				//console.log("data",data);
				$("tbody").html("");
				var html="";
				$.each(data.result.rows,function(i,item){
					var operation=item.operation;
					if(operation&&operation.indexOf("<")==-1&&operation.length>100){
						//console.log("operation.length",operation.length);
						operation='<span class="short">'+operation.substring(0,100)+'...</span><span class="all">'+operation+'</span><a class="more">(更多)</a>';
						//console.log(operation);
					}
					html+="<tr>"+
					"<td>"+ item.operation_time +"</td>"+
					"<td>"+ item.log_desc +"</td>"+
					"<td>"+ operation +"</td>"+
					"<td>"+ item.client_ip +":"+item.client_port +"</td>"+
					"<td>"+ (item.operation_result=="true"?'是':'否')+"</td>"+
					//"<td>"+ item.error_code +"</td>"+
					"<td>"+ item.operator_id +"</td>"+
					"<td>"+ item.operator_name +"</td>"+
					"</tr>"; 
				});
				$("tbody").append(html); 
				$.pagination(data.result);
				 //对于每一个评论中查看内容的超链接：
				 $("tbody").find("a.more").each(function(){
				  	//如果点击该链接则：
				  	$(this).on("click",function(){
					    //把不需要显示的内容隐藏，需要显示的内容展开。
					    $(this).parent().children('.all,.short').toggle();
					    //替换超链接的文字
					    if($(this).html()=='(更多)'){
					    	$(this).html('(收起)');
					    }else{
					    	$(this).html('(更多)');
					    } 
				  });
	 			});
			}
		});
	};
	
	$(document).ready(function(){
		var size= $(".g-select.refresh").find("option:selected").html();
		var param={	"page":"1",
				"pageSize":size};
		if(o.operator_type >= 3){
			param.library_idx = o.library_idx;		
		}
		$.select(param);
	});
	
	$(document).ready(function(){
			//获取系统日志里面的操作员信息
		var data = {};
		if(o.operator_type >= 3){
			data.library_idx = o.library_idx;	
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/systemlog/SelectOperatorList",
			type:"POST",
			data:{"json":JSON.stringify(data)},
			success:function(data){
				var html = "";
				$.each(data.result,function(i,item){
					html += "<option value="+ item.operator_idx +">"+item.operator_id+"</option>";
				});
				$("#choose-oper").append(html);
			}	
		});	
	});
	/**
		获取log_message 操作类型，并且设置下拉框
	**/
	$.ajax({
		url:"${pageContext.request.contextPath}/systemlog/getOperationLogType",
		data:{req:""},
		type:"GET"
	}).done(function(data){
		//console.log("getOperationLogType",data);
		if(data&&data.state){
			var result=data.result;
			if(result){
				var html='<option value="-1" selected>选择日志类型</option>';
				$.each(result,function(index,item){
					html+='<option value="'+item.log_code+'">'+item.log_desc+'</option>';
				});
				$("#choose-type").html(html);
			}
		}
	});
	
	
	//上一页操作
	$("#page").on("click",".prev-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size= $(".g-select.refresh").find("option:selected").html();
		//获取查询条件
		var param =GetSearchInfo();
		param.page=page;
		param.pageSize=size;
		
		$.select(param);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $(".g-select.refresh").find("option:selected").html();
		//获取查询条件
		var param =GetSearchInfo();
		param.page=page;
		param.pageSize=size;
		
		$.select(param);
	});
	//点击跳转页面 
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var size= $(".g-select.refresh").find("option:selected").html();
		var page = $(this).html();
		if(page=="...") return;
		//获取查询条件
		var param =GetSearchInfo();
		param.page=page;
		param.pageSize=size;
	 	
		$.select(param);
	}); 
	
	
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var size=$(this).val();
		var page = $("#page").find("li.active").html();
		var data= GetSearchInfo();
		data.page = page;
		data.pageSize = size;
		$.select(data);
	});
	
	//获取查询条件函数
	function GetSearchInfo(){
		var oper = $("#choose-oper").val();
		var log_code=$("select#choose-type").val();
		
		var begin_time = $("#begin_time").val();
		var end_time = $("#end_time").val();
		var param ={};
		if(oper!=-1){
			param.operator_idx = oper;
		}
		if(log_code>-1){
			param.log_code = log_code;
		}
		if(begin_time!="" && end_time!=""){
			//查询初始时间
			var begin_date=new Date(begin_time);
			var maxend_date =begin_date;
			//最大查询截止时间
			maxend_date.setMonth(maxend_date.getMonth()+1);
			//查询截止时间
			var end_date = new Date(end_time);
			if(begin_date == end_date){
				end_date.setDate(end_date.getDate()+1);
			}
			if(maxend_date >= end_date){
				param.begin_time = begin_time;
				param.end_time = end_time+" 23:59:59";
			}else{
				layer.alert("查询日期段不能超过1个月！");
				//param.begin_time = begin_time;
				return null;
			}
		}else if(begin_time!="" && end_time=="" ||begin_time=="" && end_time!=""){
			layer.alert("查询时间段必须填写完整");
			return null;
		}
		return param;
	}

	
});
</script>
  </body>
</html>
