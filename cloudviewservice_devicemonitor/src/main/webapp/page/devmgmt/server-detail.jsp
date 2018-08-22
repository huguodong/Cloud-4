<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
	<%@include file="../common/global.jsf" %>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/style2.css">
	<style type="text/css">
        .server-detail .zhibixiang .t-box ul li {
            width: auto;
        }
        .main-page{
			padding-top: 0px;
			padding-bottom: 50px;
			text-align: center;
			height: 100%;
			line-height: 26px;
			color:#68c9f4;
			background-color:white;
			background-image:none!important;
			-webkit-box-sizing: border-box;
			-moz-box-sizing: border-box;
			box-sizing: border-box;
		}
		
		.main-page .chart{
			width:700px;
			height:400px;
			margin:30px 50px 10px 10px;
			display: inline-block;
			float: center;
		}
		#nav {
			margin-left:50px;
			margin-left:50px;
			margin-top: 10px;
			margin-bottom: 0px;
			/* float: center; */
			text-align: center;
		}
		#nav ul{
			display: table;
			margin:0 auto;
			height:40px;
			text-align: center;
			padding:0;
		}
		#nav li {
			list-style-type: none;
			display: inline;
			padding: 0px;
			margin: 0;
			float: left; 
			text-align: center;
		}
		
		#nav a {
			display: block;
			font-size: 11px;
			color: #D6ECAE;
			text-decoration: none;
			background-color: #294E56;
			padding: 2px 2px 2px 3px;
			width: 8em;
			margin-left: 2px;
			border-right: 1px solid #73AFB7;
			border-bottom: 1px solid #73AFB7;
			font-weight: bold;
			margin-bottom: 2px;
		}

		nav a:hover {
			background: #73AFB7;
			border-right: 1px solid #14556B;
			border-bottom: 1px solid #14556B;
			color: #FBEF99;
		}
		#extState{
			margin-top:30px
		}
		.server-detail .main2{
			padding:30px 40px;
			background-color: #fff;
		}
</style>
</head>
<body style="background:#FFF;min-width: 500px;overflow: auto;">
<div class="server-detail">
	<div class="head2">
    	<input  id="libId_deviceId2" value="${device_id}"/>
        <!-- 设备名字、设备ID -->
        <div class="name2"><span class="id2">${device_id}</span></div>
        <div class="time2">
            <div class="item2 startupTime2">
                <%-- 系统启动时间：2015-11-06 12:07:51 --%>
            </div>
            <div class="item2 updateTime2">
                <%-- 系统链接时间：2015-11-06 12:07:51 --%>
            </div>
        </div>
        <div class="tab2">
            <ul>
                <li class="btn2 active2">服务器状态</li>
                <!-- <li class="btn2">远程日志</li> -->
            </ul>
        </div>
      </div>
      <!-- <div style="margin-top:15px;text-align:center;font-size:22px;font-weight:800"><span>功能使用状态</span></div>
      <div id="softState">
        	<ul class="basicFunc"></ul>
      </div>-->
      <div style="overflow:auto;">
      <div style="margin-top:25px;margin-bottom:25px;text-align:center;font-size:22px;font-weight:800"><span>服务器设备状态</span></div>
      <div id="nav">
        <ul id="group">
         <!--  <li><a href="index.html" id="homeLink">Home</a></li> -->
      </div>
      <div id="extState" style="padding-left:20%;margin-bottom:5%">
        	<table width="70%" cellpadding="2" cellspacing="0" class="deviceTable"></table>
      </div>
      <div class="main2">
            <div class="main-page">
	            <div id="chart1_1" class="chart"></div>
            </div>
       </div>
       </div> 
</div>
<script type="text/javascript">
	var library_id = "${library_id}";//$(".library_id").val();
	var library_idx = "${library_idx}";//$(".library_id").val();
	var service_id = "${service_id}";//$(".device_ids").text();
	
	var deviceRows =parent.data;   //上一个链接页面的所有人流量服务器，包括了服务器中的设备device_names
	var deviceIds = [];
	//alert(library_id+"  "+library_idx+"  "+service_id+"  "+a);
	deviceIds.push(library_id+'_'+service_id);

	var temp = document.getElementById('nav');
	//alert(temp.getElementsByTagName("li").length);
	//alert((parseInt((temp.getElementsByTagName("li").length-1)/7)+1)*23);
	$("#nav").css("height",(parseInt((temp.getElementsByTagName("li").length-1)/7)+1)*23);
	
	
	
	var deviceLibAndId = [];
	var deviceNames = [] ;
	deviceRows = deviceRows.data;
	
	var liHtml="";
	for(var deviceIndex in deviceRows){
		console.info("deviceRows[deviceIndex].device_model_idx",deviceRows[deviceIndex].device_model_idx);
		
		//通过人流量服务类型和service_id来过滤rows不符合的条件
		if(deviceRows[deviceIndex].service_type_id.toUpperCase()=="RLLSERVER"&&deviceRows[deviceIndex].service_id==service_id){
			var arr = deviceRows[deviceIndex].device_ids.split(",");
			var arrName = deviceRows[deviceIndex].device_names.split(",");
			
			//将选中人流量服务器的所有设备遍历放入数组
			for(var i=0;i<arr.length-1;i++){
				//console.info("arr i",arr[i]);
				deviceLibAndId.push(library_id+"_"+arr[i]);
				deviceNames.push(arr[i]);
				liHtml +=  "<li><a title=\""+arrName[i]+"\" id=\""+arr[i]+"\" style=\"display:block;width:80px;height:20px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;float:left;\">"+arrName[i]+"</a></li>";
			}
		}
		console.info("deviceLibAndId i",deviceNames);
	}
	$("#nav ul").append(liHtml);
	
	//点击标签时加载
	var devId;
	var devName;
	$('#nav').find('a').each(function(){ 
		$(this).on("click",function(){
			//先设置全部li a为原来的颜色
			$("#group").find("a").css("background-color","#294E56");
			devId = $(this).attr("id");
			$(this).css("background-color","#0172ac");
			devName = $(this).text();
			//从moogodb中查询点击设备的状态
			$.ajax({
				type:"POST",
				url:"${ctx}/device/selectDeviceExtState",
				data:{"req":JSON.stringify(deviceIds)}
			}).done(function(data){
				console.log("selectExtState: ",data);
				data = data.result;
				//console.info("deviceIds[0]",deviceIds[0]);
				//console.info("data[deviceIds[0]]",data[deviceIds[0]]);
				var devices = data[deviceIds[0]];
				console.info("devices",devices);
				var html="";
				html+= '<tr><th><b>设备名</b></th><th><b>设备状态</b></th><th><b>当天进入人数</b></th><th><b>本周进入人数</b></th><th><b>本月进入人数</b></th></tr>';
				
				
				var flag = false;
				//for(var deviceId in deviceNames){
					console.info("人流量服务器下所有设备:",devices);
					for(device in devices){
					  if(device!=="createTime"){
						console.info("人流量设备:",device);
						console.info("对比：",devId,device);
						if(devId==device){
							flag  = true;
							if(device!=="createTime"){
								if(devices[device]=="0"){
									state = "设备正常";
									html += '<tr style="text-align:center;margin-top:8px;border:1px solid #ffffff;"><td style=\"display:block;width:80px;height:20px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;float:left;\"><a title="'+devName+'"><b>'+devName+'</b></a></td><td><b>'+state+'</b></td><td id=\'dayCount\'></td><td id=\'weekCount\'></td><td id=\'monthCount\'></td></tr>';
								}else{
									state = "设备异常";
									html += '<tr style="text-align:center;margin-top:8px;border:1px solid #ffffff;"><td style=\"display:block;width:80px;height:20px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;float:left;\"><a title="'+devName+'"><b>'+devName+'</b></a></td><td><b>'+state+'</b></td><td id=\'dayCount\'></td><td id=\'weekCount\'></td><td id=\'monthCount\'></td></tr>';
								}
							}
						}
					   }
				  	}
					if(flag==false){
								state = "无数据";
								html += '<tr style="text-align:center;margin-top:8px;border:1px solid #ffffff;"><td style=\"display:block;width:80px;height:20px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;float:left;\"><a title="'+devName+'"><b>'+devName+'</b></a></td><td><b>'+state+'</b></td><td id=\'dayCount\'></td><td id=\'weekCount\'></td><td id=\'monthCount\'></td></tr>';
					}
					flag = false;
				//}
				$(".deviceTable").html(html);
				
				//2 加载设备当日、本周、本月的通过人数
				//当日的数据
				var lib_idx="${library_idx}";
				var postTodayParam = lib_idx==0?{"type":type,"chart_type":"bar"}:{"type":"today","library_idx":lib_idx,"device_id":devId};
				var postWeekParam = lib_idx==0?{"type":type,"chart_type":"bar"}:{"type":"week","library_idx":lib_idx,"device_id":devId};
				var postMonthParam = lib_idx==0?{"type":type,"chart_type":"bar"}:{"type":"month","library_idx":lib_idx,"device_id":devId};
				
				getDWMData(postTodayParam,$("#dayCount"));
				getDWMData(postWeekParam,$("#weekCount"));
				getDWMData(postMonthParam,$("#monthCount"));
				function getDWMData(param,Obj){
					$.ajax({
						url:"${pageContext.request.contextPath}/device/countVisitorLog",
			            type:"POST",
						data: {"req":JSON.stringify(param)},
						dataType: 'json',
						success: function(data){
							console.info("人流量访问数据:",data);
							data = data.result;
							if(data!=null){
								var dayTotal=0;
								data = data.in_total;
								if(data){
									//console.info("<<<<<<<<",);
									for(index in data){
										dayTotal += data[index];
									}
								}
								//alert(dayTotal);
								//$("#dayCount").text(dayTotal);
								Obj.text(dayTotal);
							}else{
								Obj.text("0");
							}
						}
					});
				}
			}); 
			
			
			//3 将人流量的日统计数据进行展示
			var chart1_1 = echarts.init(document.getElementById('chart1_1'));
		   		
			$(window).resize(function() {
		    	$(".chart").css({width : $(".main-page").width()*0.9});
		    	chart1_1.resize({width : "auto"});
			});
			
			jQuery.search=function(){
				var type="today"; 
				var lib_idx="${library_idx}";
				var postData1 = lib_idx==0?{"type":type,"chart_type":"bar"}:{"type":type,"library_idx":lib_idx,"chart_type":"bar","device_id":devId};
		    	console.info("postData1",postData1);
				loadVisitorData(postData1,chart1_1);
		    	
		    }
		   
		    $.search();
		});
	    
	   var loop=setInterval(function(){
			$.search();
	   }, 60000);
	    
	  	//人流量数据统计
	    function loadVisitorData(postData,chart){
			$.ajax({
				url:"${pageContext.request.contextPath}/device/countVisitorLog",
	            type:"POST",
				data: {"req":JSON.stringify(postData)},
				dataType: 'json',
				success: function(data){
					console.info("人流量数据：",data);
					chart.clear();
					if(data.state){
						result = data.result;
						if(result){
							initVisitorBar(chart);//initVisitorMultiLine(chart);
							chart.setOption({        //载入数据
			                    xAxis: {
			                        data: result.date    //[1,2,3,4,5,6,7,8,9,10,11,12]//填入X轴数据
			                    },
			                    yAxis : [
			                             {
			                                 type : 'value'
			                             }
			                    ],
			                    series: [    //填入系列（内容）数据
			                       {
			                           data: result.in_total
			                       },
			                       {
			                           data: result.out_total
			                       }
			                   ]
			                });
						}else{
							initVisitorBar(chart);
						}
					}else{
						initVisitorBar(chart);
					}
				}
			});
		};
	});
	//触发页面上第一个li a被点击事件
	$("#nav").find("ul").children("li:nth-child(1)").children("a").trigger("click");
</script>
</body>
</html>