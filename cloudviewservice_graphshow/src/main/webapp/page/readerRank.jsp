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
<title>大屏展示-读者借阅排行</title>
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap3.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/skin_2.css" rel="stylesheet" media="screen">
<!--[if lt IE 9]>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/respond.min.js"></script>
<![endif]-->
</head>
<body>
	<div class="container-fluid" id="container">
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-10">
				<div class="hot reader_hot bg">
					<div class="hot_title">读者借阅排行榜 <b>TOP10</b></div>
					<div class="row" style="text-align: center;">
						<span style="color:#999">过滤：</span>
						<select id="reader_time_type">
							<option value="all">全部</option>
							<option value="year">当年</option>
							<option value="quarter">本季度</option>
							<option value="month">本月</option>
							<option value="week">本周</option>
							<option value="today">当天</option>
							<option value="lastYear">去年</option>
							<option value="lastQuarter">上季度</option>
							<option value="lastMonth">上月</option>
							<option value="lastWeek">上周</option>
							<option value="yesterday">昨日</option>
						</select>
						<span style="color:#333"><input type="button" value="刷新" onclick="readerRank();"/></span>
					</div>
					<div class="row">
						<div class="col-md-5">
							<table class="list-unstyled reader_hot_list">
							</table>
						</div>
						<div class="col-md-1"></div>
						<div class="col-md-6">
							<div id="reader_hot_chart"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-1"></div>
		</div>
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-10">
				<div class="hot book_hot bg">
					<div class="hot_title">图书借阅排行榜 <b>TOP5</b></div>
					<div class="row" style="text-align: center;">
						<span style="color:#999">过滤：</span>
						<select id="book_time_type">
							<option value="all">全部</option>
							<option value="year">当年</option>
							<option value="quarter">本季度</option>
							<option value="month">本月</option>
							<option value="week">本周</option>
							<option value="today">当天</option>
							<option value="lastYear">去年</option>
							<option value="lastQuarter">上季度</option>
							<option value="lastMonth">上月</option>
							<option value="lastWeek">上周</option>
							<option value="yesterday">昨日</option>
						</select>
						<span style="color:#333"><input type="button" value="刷新" onclick="bookRank();"/></span>
					</div>
					<div class="row">
						<div class="col-md-5">
							<table class="list-unstyled book_hot_list">
							</table>
						</div>
						<div class="col-md-1"></div>
						<div class="col-md-6">
							<div id="book_hot_chart"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-1"></div>
		</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/js/custom.js"></script>
<script type="text/javascript">
var chart1 = echarts.init(document.getElementById('reader_hot_chart'));
var chart2 = echarts.init(document.getElementById('book_hot_chart'));

var library_idx="${library_idx}";
$(function(){
	$(window).resize(function() {
		if(chart1)setTimeout(chart1.resize, 1);
		if(chart2)setTimeout(chart2.resize, 1);
	});
	
	readerRank();
	bookRank();
});

//获取读者排行
function readerRank(){
	//console.info("-------request reader rank data------");
	var type=$("#reader_time_type option:selected").val();
	chart1.clear();
	$.ajax({
		type: 'POST',
		url: '${pageContext.request.contextPath}/readerRank',
		data: {"type" : type,"rows" : '10',"library_idx":library_idx},
		dataType: 'json',
		success: function(data){
			var htm="";
				var category = new Array;
				var datas = new Array;
				if (data.state) {
					var index = 1;
					var result = data.result;
					$(result).each(function(i, item) {
						htm += "<tr><td>";
						if (index == 1) {
							htm += "<div class='top1'>" + index + "</div>";
						} else if (index == 2 || index == 3) {
							htm += "<div class='top2'>" + index + "</div>";
						} else {
							htm += "<div class='top_other'>" + index + "</div>";
						}
						console.info(JSON.stringify(item));
						htm += "</td><td><div>" + item.name + "(" + item.cardNo + ")</div></td><td><div>" + item.total + "</div></td></tr>";
						category[i] = item.name;
						datas[i] = item.total;
						index++;
					});
					initCategoryBar(chart1);
					chart1.setOption({ //载入数据
						yAxis : {data : category.reverse()},	//填入X轴数据
						series : [ //填入系列（内容）数据
						{
							data : datas.reverse()
						}]
					});
				}
				$(".reader_hot_list").empty().html(htm);
			}
		});
	};

	//获取图书排行
	function bookRank() {
		//console.info("-------request book rank data------");
		var type = $("#book_time_type option:selected").val();
		chart2.clear();
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/bookRank',
			data : {
				"type" : type,
				"rows" : '5',
				"library_idx":library_idx
			},
			dataType : 'json',
			success : function(data) {
				var htm = "";
				var category = new Array;
				var datas = new Array;
				if (data.state) {
					var index = 1;
					var result = data.result;
					$(result).each(function(i, item) {
						htm += "<tr><td>";
						if (index == 1) {
							htm += "<div class='top1'>" + index	+ "</div>";
						} else if (index == 2 || index == 3) {
							htm += "<div class='top2'>" + index + "</div>";
						} else {
							htm += "<div class='top_other'>" + index + "</div>";
						}
						htm += "</td><td><div title='"+item.name+"' class='long_txt'>" + item.name + "</div></td><td><div>" + item.total + "</div></td></tr>";
						if(item.name&&item.name.length>6){
							category[i] = item.name.substr(0,6)+'...';
						}else{
							category[i] = item.name;
						}
						datas[i] = item.total;
						index++;
					});
					initCategoryBar(chart2);

					chart2.setOption({ //载入数据
						yAxis : {
							data : category.reverse()
						//填入X轴数据
						},
						series : [ //填入系列（内容）数据
						{
							data : datas.reverse()
						} ]
					});
				}
				$(".book_hot_list").empty().html(htm);
			}
		});
	};
</script>
</html>