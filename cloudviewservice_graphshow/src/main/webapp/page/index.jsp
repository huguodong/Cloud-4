<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>大屏展示</title>
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap3.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/skin_0.css" rel="stylesheet" media="screen">
<!--[if lt IE 9]>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/respond.min.js"></script>
<![endif]-->
</head>
<body>
	<div class="container-fluid" id="container">
		<div class="row title-div text-nowrap">
			<table style="width: 100%">
				<tr>
					<td class="logo" style="width: 20%;text-align: left;" nowrap="nowrap"></td>
					<td class="title" style="width: 60%;text-align: center;" nowrap="nowrap">${display_title }</td>
					<td class="weather" style="width: 20%;text-align: right;" nowrap="nowrap"></td>
				</tr>
			</table>
		</div>
		<div class="row">
			<div class="col-md-5">
				<div class="patron-div bg">
					<p>今日图书馆访问量：<span id="patron-today" class="text-nowrap"><label class="bg2">0</label></span></p>
					<p>全年图书馆访问量：<span id="patron-all" class="text-nowrap">0</span></p>
				</div>
				<div class="circulate-div bg">
					<table class="tab">
						<thead>
							<tr><th></th><th>借书量</th><th>还书量</th><th>办证</th></tr>
						</thead>
						<tbody>
							<tr><td>今日累计</td><td class="txt">0</td><td class="txt">0</td><td class="txt">0</td></tr>
							<tr><td>当月累计</td><td class="txt">0</td><td class="txt">0</td><td class="txt">0</td></tr>
							<tr><td>全年累计</td><td class="txt">0</td><td class="txt">0</td><td class="txt">0</td></tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="col-md-7">
				<div class="bg">
					<div id="chart1"></div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-5">
				<div class="hot bg">
					<div class="hot_title"><b>热</b>门图书排行</div>
					<ul class="list-unstyled hot_ul">
					</ul>
				</div>
			</div>
			<div class="col-md-7">
				<div class="bg">
					<div id="chart2"></div>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/js/custom.js"></script>
<script type="text/javascript">
$(function(){
	var library_idx="${library_idx}";
	//获取logo图片
	var logo="${logo_img }";
	if(logo){
		$(".logo").html('<img alt="logo" src="${pageContext.request.contextPath}/img/'+logo+'"/>');
	}
	
	var chart1 = echarts.init(document.getElementById('chart1'));
	initLine(chart1);
	var chart2 = echarts.init(document.getElementById('chart2'));
	initBar(chart2);
	
	$(window).resize(function() {
		if(chart1)setTimeout(chart1.resize, 1);
		if(chart2)setTimeout(chart2.resize, 1);
	});
	
	//获取日期、温度
	var getWeather = function(){
		//console.info("-------request title------");
		$.ajax({
			type: 'POST',
			data:{'weather_city':"${weather_city}"},
			url: '${pageContext.request.contextPath}/getTitle',
			dataType: 'json',
			success: function(data){
				var htm="";
				if(data.state){
					if(data.result.date)
						htm+="<p><span>"+data.result.date+"</span></p>";
					if(data.result.weather)
						htm+="<p><img title='"+data.result.type+"' src='${pageContext.request.contextPath}/img/"+data.result.weather+".png'/>&nbsp;&nbsp;";
					if(data.result.high)
						htm+="<span title='"+data.result.low+"--"+data.result.high+"'>"+data.result.temp+"</span></p>";
				}
				$(".weather").empty().html(htm);
				setTimeout(function(){getWeather()}, 3600000);//1小时
			}
		});
	};
	//获取图书排行
	var bookRank = function(){
		//console.info("-------request book rank data------");
		$.ajax({
			type: 'POST',
			url: '${pageContext.request.contextPath}/bookRank',
			data:{'type':'all','rows':'5','library_idx':library_idx},
			dataType: 'json',
			success: function(data){
				if(data.state){
					var htm="";
					$(data.result).each(function(i, item){
					    htm += "<li title='"+item.name+"'>"+item.name+"</li>";
				  	});
					$(".hot_ul").empty().html(htm);
				}
				setTimeout(function(){bookRank()}, 3600000);//1小时
			}
		});
	};

	//获取流通数据
	var loadCirculateData = function(){
		//console.info("-------request circulate data for table------");
		$.ajax({
			type: 'POST',
			url: '${pageContext.request.contextPath}/countCirculate',
			data:{'type':'today,month,year','library_idx':library_idx},
			dataType: 'json',
			success: function(data){
				if(data.state){
					var htm = "";
					$(data.result).each(function(i, item){
						var title='';
						if(item.type=='today'){
							title='今日累计';
							htm +='<tr><td width="120px;">'+title+'</td><td class="txt">'+item.type1_total+'</td><td class="txt">'+item.type2_total+'</td><td class="txt">'+item.type4_total+'</td></tr>';
						}else if(item.type=='month'){
							title='当月累计';
							htm += '<tr><td width="120px;">'+title+'</td><td class="txt">'+item.type1_total+'</td><td class="txt">'+item.type2_total+'</td><td class="txt">'+item.type4_total+'</td></tr>';
						}else if(item.type=='year'){
							title='全年累计';
							htm += '<tr><td width="120px;">'+title+'</td><td class="txt">'+item.type1_total+'</td><td class="txt">'+item.type2_total+'</td><td class="txt">'+item.type4_total+'</td></tr>';
						}
				  	});
					$(".tab tbody").empty().html(htm);
				}
				setTimeout(function(){loadCirculateData()}, 30000);//30秒
			}
		});
	};
	
	//获取实时人流量
	var isFirst= true;
	var loadPatronDataForRealtime = function(){
		//console.info("-------request patron data for line and table------");
		$.ajax({
			type: 'POST',
			url: '${pageContext.request.contextPath}/countPatronForRealtime',
			data:{'type':'realtime','library_idx':library_idx},
			dataType: 'json',
			success: function(data){
				if(data.state){
					//插入图表
					result = data.result;
					//init line
					if(isFirst){
						initLine(chart1);
						isFirst = false;
					}
					chart1.setOption({        //载入数据
						xAxis: {
			                data: result.date    //填入X轴数据
			            },
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
					initLine(chart1);
					chart1.setOption({        //载入数据
						xAxis: {
							data: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]    //填入X轴数据
			            }
			        });
				}
				setTimeout(function(){loadPatronDataForRealtime()}, 300000);//5分钟
			}
		});
	};
	//获取人流总量
	var loadPatronData = function(){
		//console.info("-------request patron data for month------");
		$.ajax({
			type: 'POST',
			url: '${pageContext.request.contextPath}/countPatron',
			data:{'type':'today,year','library_idx':library_idx},
			dataType: 'json',
			success: function(data){
				if(data.state){
					$(data.result).each(function(i, item){
						console.info(data.result);
						if(item.in_total){
							if(item.type=='today'){
								var total = item.in_total.toString();
								var htm = "";
							  	for (var i = 0; i < total.length; i++){
							      	htm+='<label class="bg2">'+total.charAt(i)+'</label>';
							   	}
								$("#patron-today").html(htm);
							}else if(item.type=='month'){
								$("#patron-all").html('<label class="text_label" style="margin-left: -5px">'+item.in_total+'</label>');
							}else if(item.type=='year'){
								$("#patron-all").html('<label class="text_label" style="margin-left: -5px">'+item.in_total+'</label>');
							}
						}
				  	});
				}
				setTimeout(function(){loadPatronData()}, 30000);//30秒
			}
		});
	};
	
	//获取流通数据组装柱状图
	var flag = true;
	var loadCirculateDataForBar = function(){
		//console.info("-------request circulate data for bar------");
		$.ajax({
			type: 'POST',
			url: '${pageContext.request.contextPath}/countCirculateForBar',
			data:{'type':'today','library_idx':library_idx},
			dataType: 'json',
			success: function(data){
				if(data.state){
					result = data.result
					if(flag){
						initBar(chart2);
						flag = false;
					}
					chart2.setOption({        //载入数据
	                    xAxis: {
	                        data: result.date    //填入X轴数据
	                    },
	                    series: [    //填入系列（内容）数据
	                       {
	                           data: result.type1_total
	                       },
	                       {
	                           data: result.type2_total
	                       },
	                       {
	                           data: result.type3_total
	                      }
	                   ]
	                });
				}else{
					initBar(chart2);
					chart2.setOption({        //载入数据
						xAxis: {
							data: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]    //填入X轴数据
			            }
	                });
				}
				setTimeout(function(){loadCirculateDataForBar()}, 300000);//5分钟
			}
		});
	};
	
	getWeather();
	bookRank();
	loadPatronDataForRealtime();
	loadPatronData();
	loadCirculateData();
	loadCirculateDataForBar();
});
</script>
</html>