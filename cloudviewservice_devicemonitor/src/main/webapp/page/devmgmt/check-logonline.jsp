<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
</head>
<body>
<div class="help-page">
<div class="g-loading"></div>
	<%@include file="../common/header.jsf" %>
	<div class="page-title-bar">
		<span class="title">在线查询日志</span>
		<div class="form-wrap fr">
			<div class="g-inputdate"><input name="startDate" value="<%=request.getParameter("startDate")==null?"":request.getParameter("startDate")%>" type="text" placeholder="输入日期" class="g-input datepicker"><span class="icon"></span></div> 
			<span class="fl">-</span>
			<div class="g-inputtime"><input name="startTime" value="<%=request.getParameter("startTime")==null?"":request.getParameter("startTime")%>" type="text" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
			~
			<div class="g-inputdate"><input name="endDate" value="<%=request.getParameter("endDate")==null?"":request.getParameter("endDate")%>" type="text" placeholder="输入日期" class="g-input datepicker"><span class="icon"></span></div> 
			<span class="fl">-</span>
			<div class="g-inputtime"><input name="endTime" value="<%=request.getParameter("endTime")==null?"":request.getParameter("endTime")%>" type="text" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
			<div class="btn search">查询</div>
		</div>
	</div>
	<div class="log-area">
		
	</div>
</div>
<script type="text/javascript">
$(function(){
	var isSendOrder=false;
	//var isLoadScroller=false;
	var operator = <shiro:principal/>;
	//日志查询按钮
	$(".btn.search").on("click",function(){
		if(!operator){
			layer.alert("请先登录系统！");
			return;
		}
		var device_id="${device_id}";
		
		var startDate=$("input[name=startDate]").val();
		var endDate=$("input[name=endDate]").val();
		var startTime=$("input[name=startTime]").val();
		var endTime=$("input[name=endTime]").val();
		if(!device_id) {
			console.log("device_id不存在");
			return;
		}
		if(!startDate){
    		layer.alert("请填写开始时间");
    		return;
    	}
    	if(!endDate){
    		layer.alert("请填写结束时间");
    		return;
    	}
		var utcStartTime=new Date(startDate+" "+startTime).toUTCString();
    	var utcEndTime=new Date(endDate+" "+endTime).toUTCString();
	    //下载日志到临时文件目录
    	//在后端加上 operator_idx
    	var req={
    		"utcStartTime":utcStartTime,
    		"utcEndTime":utcEndTime,
    		"device_id":device_id 
    	};
	    $.ajax({
    		url:"${pageContext.request.contextPath}/device/checkDownLoadLog",
    		data:{"req":JSON.stringify(req)},
    		type:"GET"
    	}).done(function(data){
    		console.log(data);
    		if(data){
    			if(data.state==true&&!data.message){
    				console.log("downLoadFilePath");
    				console.log(data.result);
    				var path=data.result;
    				//存在数据，可以查询数据了
    				$.ajax({
    					url:"${pageContext.request.contextPath}/device/queryDeivceLogOnline",
    					type:"GET",
    					data:{"req":path}
    				}).done(function(data){
			    		if(data.state==true){
			    			//查询出结果
			    			//console.log(data.result);
			    			var logHtml="";
			    			if(data.result){
			    				for(var i=0;i<data.result.length;i++){
									logHtml+=data.result[i]+"<br/>";		    				
			    				}
			    			}
			    			$(".log-area").html(logHtml);
			    			
			    		}else if(data.retMessage||data.message){
			    			//没有结果
			    			layer.alert(data.retMessage);
			    		}
    				});
    			}else if(data.message=="NEED_SEND_ORDER"||"DELETE_NEED_SEND_ORDER"==data.message){
		    			if("DELETE_NEED_SEND_ORDER"==data.message){
		    				//切换时间的情况下需要再次发送查询命令
		    				isSendOrder=false;
		    			}
		    			//需要发送指令
		    			//获取信息 包括device_id device_idx
						//3 代表 查询日志
						if(!isSendOrder){
							var params={
									  "operator_id":operator.operator_id,
									  "device_id":device_id,
									  "control":"1",
									  "control_info":"3",
									  "start_time":utcStartTime,
									  "end_time":utcEndTime
							};
							var arrParams=new Array();
							arrParams.push(params);
							console.log(params);
							$.ajax({
								type:"POST",
								url:"${pageContext.request.contextPath}/device/sendOrder",
								data:{"req":JSON.stringify(arrParams)},
								success:function(data){
									console.log(data);
									if(data){
										isSendOrder=true;
									}
								}
							});
						 }
					if(data.retMessage){
						layer.alert(data.retMessage);
					}
    			}else if(data.retMessage){
    				layer.alert(data.retMessage);
    			}
    		}
    	});
		
		
		
		/* 
			var current_page=1;
			var queryParam={
					"operator_id":operator.operator_id,
					"device_id":device_id,
					"start_time":start_time,
					"end_time":end_time,
					"current_page":current_page
			};
			console.log(queryParam); */
			/*if(!isLoadScroller){
				var log_areaH=$(window).height()-80-20-150;
				$('div.log-area').scrollPagination({  
			        'contentPage': '${pageContext.request.contextPath}/device/queryDeviceLog',  
			        // the url you are fetching the results  
			        'contentData': {"req":JSON.stringify(queryParam)},  
			        // these are the variables you can pass to the request, for example: children().size() to know which page you are  
			        'scrollTarget': $(window),  
			        // who gonna scroll? in this example, the full window  
			        'heightOffset':20,  
			        // it gonna request when scroll is 10 pixels before the page ends  
			        'beforeLoad': function() {  
			        	//console.log($(this)[0].contentData);//setting currentPage
			        },  
			        'afterLoad': function(elementsLoaded) { // after loading content, you can use this function to animate your new elements  
			        	//queryParam["current_page"] = queryParam.current_page + 1;  
			        	//console.log("page:"+queryParam["current_page"]);
			        	$(elementsLoaded).fadeInWithDelay();  
			        },
			        loader:function(data){
			        	//$('div.log-area').append('<li><p>'+data+'</p></li>');
			        	console.log(data);
			        	if(data.state==true&&data.message=="SUCCESS"){
			        		$("div.file-check").append('<div class="log-area"><li><p>'+data+'</p></li></div>');
				        	$("div.file-check").find(".log-area").css("height",log_areaH);
			        	}else if(data.state==true&&data.message=="DELETE"){
			        		//alert("请等候");
			        	}else{
			        		$("div.file-check").append('<div class="log-area bottom"><li><p>没有消息</p></li></div>');
			        		$("div.file-check").remove(".log-area.bottom");
			        	}
			        }
			    }); 
				isLoadScroller=true;
			} */
			
		 	/* $.ajax({
					type:"POST",
					url:"${pageContext.request.contextPath}/device/queryDeviceLog",
					data:{"req":JSON.stringify(queryParam)},
					success:function(data){
						console.log(data);
						if(data&&data.state==true&&data.message!="DELETE"){
							var arr=data.result;
							//var page1=arr[0];//第0页内容
							//nowPage pages contents
							//$("div.log-area").html(page1.contents);
							console.log(arr);
							var html="";
							for(var page in arr){
								var pageObj=arr[page];
								if(pageObj){
									var contents=pageObj.contents;
									var contentDiv='<div class="log-area" style="word-wrap: break-word; word-break: normal;">';
									contentDiv+=contents+'</div>';
									html+=contentDiv;
								}
							}
							$("div.log-area-list").html(html);
							isSendOrder=false;
						} else{
							//$("div.log-area").html("");
							//alert("请稍等");//没有内容，查询过一次之后
							$("div.log-area-list").html("<div class=\"log-area\">请稍后继续点击查询按钮</div>");
							//test
						}
					}
				}); */				
	});
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
	$(".timepicker").timepicker({
		controlType: 'select',
		oneLine: true,
		timeFormat: 'HH:mm',
		currentText:"现在",
		closeText:"完成",
		timeOnlyTitle:"",
		//amNames:["AM"],
		//pmNames:["PM"],
		timetext:"时间",
		hourText:"时",
		minuteText:"分",
		secondText:"秒"
	});


});
</script>
</body>
</html>


