<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <%@ include file="../common/global.jsf" %>
    <base href="<%=basePath%>">
    
    <title>My JSP 'main.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--从当前页面，引入模块加载器esl.js-->
	<%-- <script src="<%=basePath%>/static/js/all-province.js"></script> 
	<script src="<%=basePath%>/static/js/china.js"></script>  --%>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=HLY8UYq0rnxe47hg9oNzmV2F7yk1wzai"></script> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js" ></script>
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/machine.jsf" ></script> --%>
	<script type="text/javascript" src="http://lbsyun.baidu.com/jsdemo/data/points-sample-data.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/style.css">
	
    <%@ include file="../include/machine.jsf" %> 
    <%@ include file="../include/machineServer.jsf" %>
	<%-- <%@ include file="../common/machineCommon.jsf" %>  --%>
	<style type="text/css">
	.form-dialog-fix{
		display: none;
		position: fixed;
		z-index: 900;
		top:0;
		left:0;
		width: 100%;
		height: 100%;
	}
	.form-dialog-fix .shade{
		position: absolute;
		top:60px;
		left:0;
		width: 100%;
		height: 100%;
		background-color: rgba(0,0,0,.5);
	}
	
	.form-dialog-fix .form-dialog{
		display: block;
		position: absolute;
		top:0;
		bottom:0;
		margin-top:60px;
	
		width: 100%;
		left:-100%; 
		right:0px;
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
	}
	
	.w-900  .form-dialog{
		width: 1200px;
		right:  0px;
	}
	body{ 
    	text-align:center;
    	background-color: #AAAAAA;
    }
	.page-title-bar .choose-lib .select-btn {
		width:300px;
	    height: 26px;
	    text-indent: 7px;
	    line-height: 26px;
	    border: 1px solid #DDD;
	    color: #888888;
	    background-color: #f6f6f6;
	    border-radius: 2px;
	}
	.test{
		width:300px;
		height:300px;
		background:red
	}
	.page-title-bar .title {
	    float: left;
	    font-weight: bold;
	    color: #333333;
	}
	.bySide{
		position:absolute;
		left:0;
		width:24px;
		height:100px;
		line-height:25px; 
		background-color:#00a2e9;
		color:black;
	}
	.bySide.top{
		top:200;
	}
	.innerMain{
		background-color:#ffffff;
		/* height:100%;
		width:100% */
	} 
    #allmap{
    	float:left;
    	margin:6% 0% 0% 12%; 
    	/* padding-top:15%; */
    	padding-left:15%;
    	height:80%;
    	width:45%;
    }
    .anchorBL{display:none;}
    #infoLeft{
    	/* postion:absolute */
    	float:left;
    	margin-top:10%; 
    	margin-bottom:0%;
    	height:30%;
    	width:25%;
    }
    #infoLeft li span.word{margin-left:80px}
    #infoLeft .libTitle{
    	margin-top:65%;
    	margin-bottom:2%;
    	color:#006633;
    	font-weight:bold;
    	text-align:center;
    	text-indent: 40px;
    	font-size : 15px;
    	height: 50px;
    }
    .vintage{
		letter-spacing: 0;
		text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135 
    }
    .vintage2{
		letter-spacing: 0;
	}
	#infoLeft .all{
    	margin-top:2%;
    	margin-bottom:2%;
    	color:#000000;
    	font-family："SimHei";
    	font-weight:bold;
    	text-align:left;
    	text-indent: 0px;
    	font-size : 20px;
    }
    #infoLeft .all li input{margin-left:0%;color:#006633;font-family："SimHei";font-weight:bold;}
    #infoLeft .normal{
    	margin-bottom:2%;
    	color:#006633;
    	font-family："SimHei";
    	font-weight:bold;
    	text-align:left;
    	text-indent: 0px;
    	font-size : 20px;
    }
    #infoLeft .normal li input{margin-left:0%;color:#006633;font-family："SimHei";font-weight:bold;}
     #infoLeft .error{
    	margin-bottom:2%;
    	color:#FF0033;
    	font-family："楷体";
    	font-weight:bold;
    	text-align:left;
    	text-indent: 0px;
    	font-size : 20px;
    }
    #infoLeft .error li input{margin-left:0%;color:#707070;font-family："楷体";font-weight:bold;}
    #infoLeft .overtime{
    	margin-bottom:2%;
    	color:#33CCFF;
    	font-family："楷体";
    	font-weight:bold;
    	text-align:left;
    	text-indent: 0px;
    	font-size : 20px;
    }
    #infoLeft .overtime li input{margin-left:0%;color:#707070;font-family："楷体";font-weight:bold;}
    #infoLeft .nolink{
    	margin-bottom:2%;
    	color:#CCCCFF;
    	font-family："楷体";
    	font-weight:bold;
    	text-align:left;
    	text-indent:0px;
    	font-size : 20px;
    }
    #infoLeft .nolink li input{margin-left:0%;color:#CCCCFF;font-family："楷体";font-weight:bold;}
    #infoLeft .special_device{
    	margin-bottom:2%;
    	color:#6699FF;
    	font-family："楷体";
    	font-weight:bold;
    	text-align:left;
    	text-indent:0px;
    	font-size : 20px;
    }
    #infoLeft .special_device li input{margin-left:0%;color:#006633;font-family："SimHei";font-weight:bold;}
	.optionpanel{margin: 10px;}
	#r-result{width:100%;}
	#r-result p{margin:5px 0 0 10px;}
	</style>
	
  </head>
  
  <body>
  	<div>
  	<%@include file="../common/header.jsf"%>
  	<div class="innerMain">
		<div id="allmap"></div>
		<div id="infoLeft">
			<ul class="libTitle">
		    	<h1 class="vintage"><span id="libName" style="color: #FFCC00"></span></h1></li>
		    </ul>
			<ul class="all">
		    	<li class="green"><span class="word vintage2" style="width:80px">➤ 全部设备 </span><input type="text" value="点击查看" readonly="readonly" style="text-align:center;font-weight:bold;color:#000000;width: 200px;height:24px;background-color: #E0E0E0"/></li>
		    	<!-- <li><span class="line"></span></li> -->
		    </ul>
		    <ul class="normal">
		    	<li class="green"><span class="word vintage2" style="width:80px">➤ 正常  </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="normalInput" value="0" readonly="readonly" style="text-align:center;font-weight:bold;color:#006633;width: 200px;height:24px;background-color: #E0E0E0"/></li>
		    	<!-- <li><span class="line"></span></li> -->
		    </ul>
		    <ul class="error">
		    	<li class="red"><span class="word vintage2" style="width:80px">➤ 错误  </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="errorInput" value="0" readonly="readonly" style="text-align:center;font-weight:bold;color:#FF0033;width: 200px;height:24px;background-color: #E0E0E0"/></li>
		    	<!-- <li><span class="line"></span></li> -->
		    </ul>
		    <ul class="overtime">
		    	<li class="shallow"><span class="word vintage2" style="width:80px">➤ 超时  </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="overtimeInput" value="0" readonly="readonly" style="text-align:center;font-weight:bold;color:#33CCFF;width: 200px;height:24px;background-color: #E0E0E0"/></li>
		    	<!-- <li><span class="line"></span></li> -->
		    </ul>
		    <ul class="nolink">
		    	<li class="gray"><span class="word vintage2" style="width:80px">➤ 无连接  </span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="nolinkInput" value="0" readonly="readonly" style="text-align:center;font-weight:bold;color:#585858;width: 200px;height:24px;background-color: #E0E0E0"/></li>
		    	<!-- <li><span class="line"></span></li> -->
		    </ul>
		    <ul class="special_device">
		    	<li class="green"><span class="word vintage2" style="width:80px">➤ 特殊设备  </span>&nbsp;<input type="text" value="3D导航、人流量服务器信息" readonly="readonly" style="text-align:center;font-weight:bold;color:#6699FF;width: 200px;height:24px;background-color: #E0E0E0"></li>
		    	<!-- <li><span class="line"></span></li> -->
		    </ul>
		</div>
	</div>
    
    <div><input type="text" id="deviceStatus" style="display:none" value="4" default="4"/></div> 
  	<div id="mainDiv" class="form-dialog-fix w-900 yingjian">
		<div class="shade"></div>
		<div class="form-dialog" style="width:85%;left:-185%;padding-top: 0px;">
			<jsp:include page="machine.jsp"/>
	  	</div>
  	</div>
  	<div id="rllDiv" class="rllStyle w-900 yingjian">
		<div class="shade2"></div>
		<div class="rll-dialog" style="width:85%;left:-185%;padding-top: 0px;" date-right="0px">
			<jsp:include page="machineService.jsp"/>
	  	</div>
  	</div>
  	</div>
    <%-- <jsp:include page="machine.jsp"/>  --%>
    <script type="text/javascript">
    	$(function(){
    		var winH = $(window).height();
    		var headerH = $(".g-header").outerHeight();

    		$(".innerMain").css("height",winH - headerH); 
    		
    		$(".shade").hide();
    		$(".shade2").hide();
    		//$("#mainDiv").css("top",60);
    		$(".all").on("click",function(){
    			$("#deviceStatus").text("9");
    			//$(".total-choosen-num2").text("0");
    			$("input[type='checkbox']:checked").removeAttr("checked");
    			var Page=makeQueryParam(1, 12);
   			 	selectPage(Page);  
    			//$("#Click").hide();
    			$(".form-dialog-fix .shade").show();
    			$(".form-dialog-fix.w-900.yingjian").fadeIn(1000);
    			$(".form-dialog-fix.w-900.yingjian").find(".form-dialog").animate({
    				"right":"-170%"
    			});
    			
    		});
    		$("#infoLeft .normal").on("click",function(){
    			$("#deviceStatus").text("0");
    			//$(".total-choosen-num2").text("0");
    			$("input[type='checkbox']:checked").removeAttr("checked");
    			var Page=makeQueryParam(1, 12);
   			 	selectPage(Page);  
   			 	
    			$(".form-dialog-fix.shade").show();
    			$(".form-dialog-fix.w-900.yingjian").fadeIn(1000);
    			$(".form-dialog-fix.w-900.yingjian").find(".form-dialog").animate({
    				"right":"-170%"
    			});
    			
    		}); 
    		$("#infoLeft .error").on("click",function(){
    			$("#deviceStatus").text("2");
    			//$(".total-choosen-num2").text("0");
    			$("input[type='checkbox']:checked").removeAttr("checked");
    			var Page=makeQueryParam(1, 12);
   			 	selectPage(Page);  
   			 	
    			$(".form-dialog-fix .shade").show();
    			$(".form-dialog-fix.w-900.yingjian").fadeIn(1000);
    			$(".form-dialog-fix.w-900.yingjian").find(".form-dialog").animate({
    				"right":"-170%"
    			});
    			//$("#mainDiv").css("top",60);
    		});
    		$("#infoLeft .overtime").on("click",function(){
    			$("#deviceStatus").text("4");
    			//$(".total-choosen-num2").text("0");
    			$("input[type='checkbox']:checked").removeAttr("checked");
    			var Page=makeQueryParam(1, 12);
   			 	selectPage(Page);  
   			 	
    			$(".form-dialog-fix .shade").show();
    			$(".form-dialog-fix.w-900.yingjian").fadeIn(1000);
    			$(".form-dialog-fix.w-900.yingjian").find(".form-dialog").animate({
    				"right":"-170%"
    			});
    			
    		}); 
    		$("#infoLeft .nolink").on("click",function(){
    			$("#deviceStatus").text("3");
    			//$(".total-choosen-num2").text("0");
    			$("input[type='checkbox']:checked").removeAttr("checked");
    			var Page=makeQueryParam(1, 12);
   			 	selectPage(Page);  
   			 
    			$(".form-dialog-fix .shade").show();
    			$(".form-dialog-fix.w-900.yingjian").fadeIn(1000);
    			$(".form-dialog-fix.w-900.yingjian").find(".form-dialog").animate({
    				"right":"-170%"
    			});
    			
    		});
    		$(".special_device").on("click",function(){
    			/* var Page=makeQueryParam2(1, 12);
   			 	selectPage2(Page);  */
   			    $(".total-choosen-num").text("0");
   			 	$("input[type='checkbox']:checked").removeAttr("checked");
    			$(".rllStyle .shade2").show();
    			$(".rllStyle.w-900.yingjian").fadeIn(1000);
    			$(".rllStyle.w-900.yingjian").find(".rll-dialog").animate({
    				"right":"-170%"
    			});
    			$("#rllDiv").css("top",60);
    		});  
    	});
    	
    	//地图初始化
        var bm = new BMap.Map("allmap");
    	bm.setMapStyle({style:'googlelite'});
    	bm.setMapStyle({
    		styleJson:[
			{
			    'featureType': 'water',
			    'elementType': 'all',
			    'stylers': {
			      'color': '#ffffff' 
			    }
			  },
			  {
				    'featureType': 'highway',
				    'elementType': 'all',
				    'stylers': {
				      'visibility': 'off'
				    }
			  },
			  {
				    'featureType': 'railway',
				    'elementType': 'geometry.fill',
				    'stylers': {
				      'color': '#000000'
				    }
				  },
				  {
				    'featureType': 'railway',
				    'elementType': 'geometry.stroke',
				    'stylers': {
				      'color': '#08304b'
				    }
				  },
				  {
				    'featureType': 'subway',
				    'elementType': 'geometry',
				    'stylers': {
				      'lightness': -70
				    }
				  },
				  {
					    'featureType': 'boundary',
					    'elementType': 'all',
					    'stylers': {
					      'color': '#CC6600'   
					    }
				  },
				  {
					    'featureType': 'land',
					    'elementType': 'geometry',
					    'stylers': {
					      'color': '#ffffff' 
					    }
				   }
			]
    	} );
    	bm.centerAndZoom("兰州",5);
    	
    	var bdary = new BMap.Boundary();
    	bdary.get("中国", function (rs) {
    	    var count = rs.boundaries.length; //行政区域的点有多少个
    	    for (var i = 0; i < count; i++) {
    	        var ply = new BMap.Polygon(rs.boundaries[i], { strokeWeight: 2, strokeColor: "#CC6600", fillColor: "" }); //建立多边形覆盖物
    	        bm.addOverlay(ply);
    	    }
    	});
    	var machine={};
    	var machine2={};
    	var oper = <shiro:principal/>;
    	/**
		 *	对应用户的图书馆数据
		 **/
     	var libIdAndNameObj={};
     	var libIdxAndNameObj={};
     	
		$.ajax({
				url:"${pageContext.request.contextPath}/ascconfig/querylibInfoByCurUser",
				type:"GET",
				data:{}
			}).then(function(data){
				if(data){
					if(debugFlag==1)
						console.log("获取当前图书馆信息",data);		
					if(data.result){
						for(var i=0;i<data.result.length;i++){
							libIdAndNameObj[data.result[i].lib_id]={"lib_name":data.result[i].lib_name,"library_idx":data.result[i].library_idx};
							libIdxAndNameObj[data.result[i].library_idx]={"lib_id":data.result[i].lib_id,"lib_name":data.result[i].lib_name};
						}
					}
				}
				if(debugFlag==1){
					console.info("libIdAndNameObj****",libIdAndNameObj);
				}
		    	function showLibOnMap(){
		    		var param ={
			        		/* library_id:"QJTSG",
			        		device_id:"" */
			        };
			    	var url;
			    	var lib_id = '${operator.lib_id}';
			     	var operator_type = '${operator.operator_type}';
			     	var lib_name = '${operator.lib_name}';
			     	if(operator_type=="3"||operator_type=="4"||operator_type=="5"){
			     		param.library_id = lib_id;
			     		
			     	}
			     	if(operator_type=="1"||operator_type=="2"){
			     		param.library_id = "";
			     	}
			     	
			    	
			     	bm.clearOverlays(); 
			     	$("#normalInput").val("0");
			     	$("#overtimeInput").val("0");
		        	$("#alertInput").val("0");
		        	$("#nolinkInput").val("0");
			    	bm.enableScrollWheelZoom();
			    	
			    	var count =0;
			    	//获取到图书馆的位置信息
			    	$.ajax({
						url:"${pageContext.request.contextPath}/device/getLibPosition",//"${pageContext.request.contextPath}/device/getDevicePosition",
						type:"GET"	,
						data:{"req":JSON.stringify(param)}
					}).done(function(data){
						data = data.result;
						if(debugFlag==1)
							console.info("查询所有图书馆位置信息",data);
						for(var i=0;i<data.length;i++){
							var new_point = new BMap.Point(data[i].longitude,data[i].latitude);
			    			var label = new BMap.Label(data[i].lib_name,{offset:new BMap.Size(20,-10)});
			    			var marker = new BMap.Marker(new_point);  // 创建标注
			   
			    			marker.setLabel(label);
			    			bm.addOverlay(marker);              // 将标注添加到地图中
			    			
			    			marker.flag = [];
			    			marker.lib_name = data[i].lib_name;
			    			marker.library_id = data[i].library_id;
			    			//点击图书馆，显示所有设备位置
			    			marker.addEventListener("click",attribute);
			    			function attribute(e){	
			    				//marker.hide();
			    				var p = e.target;
			    				var flag=true;
			    				
			    				var j = marker.flag.length;  
			    				//如果覆盖物已经被点击后，数值中有该值，则点击没反应
			    			    while (j--) {  
			    			        if (marker.flag[i] == p.getLabel().content) {  
			    			           flag=false;  
			    			        }  
			    			    }  
			    			    
			    				if(flag){
			    				 //var allOverlay = map.getOverlays();
			    				 if(p instanceof BMap.Marker){
			    					 
			    				 }
			    				 marker.flag.push(p.getLabel().content);
			    				}
			    				
			    				
			    				var bdary = new BMap.Boundary();
						    	bdary.get("中国", function (rs) {
						    	    var count = rs.boundaries.length; //行政区域的点有多少个
						    	    for (var i = 0; i < count; i++) {
						    	        var ply = new BMap.Polygon(rs.boundaries[i], { strokeWeight: 2, strokeColor: "#CC6600", fillColor: "" }); //建立多边形覆盖物
						    	        bm.addOverlay(ply);
						    	    }
						    	});
						    	
						    	if(countContrl==1){
		    						//改变全局标志控制位
		    						countContrl = 0;
				    				//将设备监控页面显示地图选定的某个图书馆
				    				$(".drop-down").hide();
				    				$(".choose-lib").find(".current-libName").html(p.lib_name);
				    				$(".choose-lib2").find(".current-libName2").html(p.lib_name);
				    				$("#libName").text(p.lib_name);
				    				$.ajax({
				    					url:"${pageContext.request.contextPath}/device/getLibPosition",//"${pageContext.request.contextPath}/device/getDevicePosition",
				    					type:"GET"	,
				    					data:{"req":JSON.stringify(param)}
				    				}).done(function(data){
				    					if(debugFlag==1)
			    							console.info("图书馆library_idx",libIdAndNameObj[p.library_id].library_idx);
				    					machine.curSelLibIdx=libIdAndNameObj[p.library_id].library_idx+"";//p.library_id;
				    					machine2.curSelLibIdx=libIdAndNameObj[p.library_id].library_idx+"";//p.library_id;
					    				var Page=makeQueryParam(1,12);
					    				var Page2=makeQueryParam2(1,12);
					    				selectPage(Page);
					    				selectPage2(Page2);
				    					
					    				//显示图书馆的位置
					    				showLibOnMap();
					    				//显示某个图书馆的所有设备
					    				//showDeviceOnMap(p.lib_name,p.library_id,p.library_idx);
				    				});
						    	}
			    			};
						}
					});  
		    	}
			    //在地图上显示图书馆的位置
		    	showLibOnMap();
			    
		    	//初始化页面
		    	var Page=makeQueryParam(1, pageSize);
		    	selectPage(Page);
		});
</script>
</body>
</html>
				
    	
    	
    	