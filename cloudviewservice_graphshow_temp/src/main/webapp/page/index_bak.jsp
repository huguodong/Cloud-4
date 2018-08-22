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
<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/css/imageflow.css" />
<link href="${pageContext.request.contextPath}/css/bootstrap3.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/skin_0.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/imageflow.css" type="text/css" />
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
					<td class="title" style="width: 60%;text-align: center;font-weight:900;" nowrap="nowrap">云平台即时显示系统</td>
					<td class="weather" style="width: 20%;text-align: right;" nowrap="nowrap">${date}</td>
				</tr>
			</table>
		</div>
		<div class="row" style="margin-bottom:0px;">
			<div class="col-md-5">
				<div class="patron-div bg">
					<p style="color: rgb(147,223,252);font-size:25px;width:20%;" class="visit_today"></p>
					<p style="color: rgb(147,223,252);font-size:25px;width:50%;" class="visit_month"></p><br/>
					<p style="color:white;font-size:20px;width:50%;">今日到馆人数</p>
					<p style="color:white;font-size:20px;width:50%;padding-left: 0%">当月到馆人数</p>
				</div>
				<div class="circulate-div bg">
					<div style="padding-left: 20%">
						<div style="width: 12px;height: 12px;border-radius: 2px;background: rgb(255,94,123);float:left;margin-left:10%;margin-top:0%"></div><p style="font-size: 15px;float:left;color: white">&nbsp;当日</p>
						<div style="width: 12px;height: 12px;border-radius: 2px;background: rgb(192,99,253);float:left;margin-left:10%;margin-top:0%;"></div><p style="font-size: 15px;float:left;color: white">&nbsp;当月</p>
						<div style="width: 12px;height: 12px;border-radius: 2px;background: rgb(106,102,250);float:left;margin-left:10%;margin-top:0%;"></div><p style="color: white">&nbsp;全年</p>
					</div>
					<div class="statistics" style="height: 100%;width: 100%;clear: both">
						<div id="statisticsChart_borrow" style="width: 28%;height: 70%;float:left;padding-left:2%">

						</div>
						<div id="statisticsChart_return" style="width: 28%;height: 70%;float:left;">

						</div>
						<div id="statisticsChart_card" style="width: 28%;height: 70%;float:left;">

						</div>
					</div>
				</div>
			</div>
			<div class="col-md-5" style="width:40%;margin-left:35%;height: 600px;position: absolute">
				<p style="margin-top:11px;font-size:23px;text-align: left;height:20px;position: absolute;color: white;padding-left:20px">图书馆接入总数：15个&nbsp;&nbsp;设备接入总数：35个</p>
				<div id="charts_map" style="height: 100%"></div>
			</div>
			<div class="col-md-5" style="width:23%;margin-left:35%;height: 410px;">
				<div style="margin-left: 30px;margin-top: 10px"><p style="color:white;font-size:25px;">流通分类</p></div>
				<div style="height:20%;margin-left:8%">
					<ul style="float:left;color:white;font-size: 15px;margin-top: 8%;" class="category_li1">
					</ul>
					<ul style="margin-left: 38%;color:white;font-size: 15px;padding-top: 8%;clear: right" class="category_li2">
					</ul>
				</div>
				<div id="charts_bookCategory" style="width: 100%;height:100%;">

				</div>
			</div>
		</div>
		<div class="row" id="LoopDiv">
			<div class="col-md-5" style="width: 35%">
				<div class="hot bg">
					<p style="font-size:23px;text-align: left;height:20px;position: absolute;color: white;padding-left:80px">当天人流量</p>
					<div id="chart_visit_today" style="padding-top: 10%;padding-left: 5%;width: 100%;height: 95%;"></div>
				</div>
			</div>
			<p style="font-size:23px;text-align: left;height:20px;position: absolute;color: white;padding-left:37%;padding-top: 6%">热门图书排行榜</p>
			<input id="S_Num" type="hidden" value="5" />
			<div class="col-md-7 zy-Slide" style="height:30%;width:30%;margin-left:35%;margin-top: 9%;position: absolute;z-index: 3;"></div>
			<div id="starsIF" class="col-md-7 zy-Slide imageflow" style="height:30%;width:30%;margin-top: 9%;z-index: 1">
				<img longdesc="#" width="180" height="184" src="${pageContext.request.contextPath}/img/mingaicisheng.png"/>
				<img longdesc="#" width="180" height="184" src="${pageContext.request.contextPath}/img/tebiedemao.png"/>
				<img longdesc="#" width="180" height="184" src="${pageContext.request.contextPath}/img/wodebingrenshiyingdi.png"/>
				<img longdesc="#" width="180" height="184" src="${pageContext.request.contextPath}/img/wocengaininameduo.png"/>
				<img longdesc="#" width="180" height="184" src="${pageContext.request.contextPath}/img/chengshi.png"/>
			</div>
			<div class="clear"></div>
			<div class="col-md-7 zy-Slide" style="height:30%;width:35%;margin-top: 100px;margin-left: 0px">
				<div class="hot_title" style="text-align: left;height:20px;"><p style="color: white;padding-left:10%">当天文献借还量</p></div>
				<div id="chart_borrowAndReturn" style="margin-top: 40px;padding-left: 30px;width: 100%;height: 95%;"></div>
			</div>
		</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/js/custom.js"></script>
<script src="${pageContext.request.contextPath}/js/imageflow.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts-gl.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ecStat.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dataTool.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/china.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/world.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bmap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/simplex.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/map.charts.js"></script>

<script type="text/javascript">
$(function(){
    var dataStyle = {
        normal: {
            label: {show:false},
            labelLine: {show:false}
        }
    };
    var placeHolderStyle = {
        normal : {
            color: 'rgba(0,0,0,0)',
            label: {show:false},
            labelLine: {show:false}
        },
        emphasis : {
            color: 'rgba(0,0,0,0)'
        }
    };

    //获取统计数据
    var isFirst= true;
    var loadStatisticsData = function(){
        //console.info("-------request patron data for line and table------");
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/statistics',
            data:{},
            dataType: 'json',
            success: function(data){
                if(data.state){
                    //插入图表
                    var result = data.result;
                    var statisticsChart_borrow = echarts.init(document.getElementById('statisticsChart_borrow'));
                    var statisticsChart_return = echarts.init(document.getElementById('statisticsChart_return'));
                    var statisticsChart_card = echarts.init(document.getElementById('statisticsChart_card'));
                    // console.log(JSON.stringify(result));
                    //init line
                    if(isFirst){
                        initStatisticsData(statisticsChart_borrow, document.getElementById('statisticsChart_borrow'));
                        initStatisticsData(statisticsChart_return, document.getElementById('statisticsChart_return'));
                        initStatisticsData(statisticsChart_card, document.getElementById('statisticsChart_card'));
                        isFirst = false;
                    }
                    var colors=['rgb(255,94,123)', 'rgb(192,99,253)', 'rgb(106,102,250)'];
                    //借书
                    var borrowTotal = result.bookBorrowYearTotal;
                    statisticsChart_borrow.setOption({        //载入数据
                        title: {
                            text: result.bookBorrowTodayTotal,
                            subtext: '当日借书',
                            sublink: '',
                            x: 'center',
                            y: 'center',
                            itemGap: 20,
                            textStyle : {
                                color : 'white',
                                fontFamily : '微软雅黑',
                                fontSize : 20,
                                fontWeight : ''
                            }
                        },
                        series : [
                            {
                                name:'',
                                type:'pie',
                                clockWise:true,
                                radius : [60, 70],
                                itemStyle : {
                                    normal : {
                                        color:function(){
                                            return colors[2];
                                        },
                                        label : {
                                            show : false
                                        },
                                        labelLine : {
                                            show : false
                                        }
                                    }
                                },
                                data:[
                                    {
                                        value:100
                                    },
                                    {
                                        value:0,
                                        name:'invisible',
                                        itemStyle : placeHolderStyle
                                    }
                                ]
                            },
                            {
                                name:'',
                                type:'pie',
                                clockWise:true,
                                radius : [50, 60],
                                itemStyle : {
                                    normal : {
                                        color:function(){
                                            return colors[1];
                                        },
                                        label : {
                                            show : false
                                        },
                                        labelLine : {
                                            show : false
                                        }
                                    }
                                },
                                data:[
                                    {
                                        value:(result.bookBorrowMonthTotal/borrowTotal)*100
                                    },
                                    {
                                        value:100-(result.bookBorrowMonthTotal/borrowTotal)*100,
                                        name:'invisible',
                                        itemStyle : placeHolderStyle
                                    }
                                ]
                            },
                            {
                                name:'',
                                type:'pie',
                                clockWise:true,
                                radius : [40, 50],
                                itemStyle : {
                                    normal : {
                                        color:function(){
                                            return colors[0];
                                        },
                                        label : {
                                            show : false
                                        },
                                        labelLine : {
                                            show : false
                                        }
                                    }
                                },
                                data:[
                                    {
                                        value:(result.bookBorrowTodayTotal/borrowTotal)*100
                                    },
                                    {
                                        value:100-(result.bookBorrowTodayTotal/borrowTotal)*100,
                                        name:'invisible',
                                        itemStyle : placeHolderStyle
                                    }
                                ]
                            }
                        ]
                    });

                    //还书
                    var returnTotal = result.bookReturnYearTotal;
                    statisticsChart_return.setOption({        //载入数据
                        title: {
                            text: result.bookReturnTodayTotal,
                            subtext: '当日还书',
                            sublink: '',
                            x: 'center',
                            y: 'center',
                            itemGap: 20,
                            textStyle : {
                                color : 'white',
                                fontFamily : '微软雅黑',
                                fontSize : 20,
                                fontWeight : ''
                            }
                        },
                        series : [
                            {
                                name:'',
                                type:'pie',
                                clockWise:true,
                                radius : [60, 70],
                                itemStyle : {
                                    normal : {
                                        color:function(){
                                            return colors[2];
                                        },
                                        label : {
                                            show : false
                                        },
                                        labelLine : {
                                            show : false
                                        }
                                    }
                                },
                                data:[
                                    {
                                        value:100
                                    },
                                    {
                                        value:0,
                                        name:'invisible',
                                        itemStyle : placeHolderStyle
                                    }
                                ]
                            },
                            {
                                name:'',
                                type:'pie',
                                clockWise:true,
                                radius : [50, 60],
                                itemStyle : {
                                    normal : {
                                        color:function(){
                                            return colors[1];
                                        },
                                        label : {
                                            show : false
                                        },
                                        labelLine : {
                                            show : false
                                        }
                                    }
                                },
                                data:[
                                    {
                                        value:(result.bookReturnMonthTotal/returnTotal)*100
                                    },
                                    {
                                        value:100-(result.bookReturnMonthTotal/returnTotal)*100,
                                        name:'invisible',
                                        itemStyle : placeHolderStyle
                                    }
                                ]
                            },
                            {
                                name:'',
                                type:'pie',
                                clockWise:true,
                                radius : [40, 50],
                                itemStyle : {
                                    normal : {
                                        color:function(){
                                            return colors[0];
                                        },
                                        label : {
                                            show : false
                                        },
                                        labelLine : {
                                            show : false
                                        }
                                    }
                                },
                                data:[
                                    {
                                        value:(result.bookReturnTodayTotal/returnTotal)*100
                                    },
                                    {
                                        value:100-(result.bookReturnTodayTotal/returnTotal)*100,
                                        name:'invisible',
                                        itemStyle : placeHolderStyle
                                    }
                                ]
                            }
                        ]
                    });
					//办证
                    var cardTotal = result.cardYearTotal;
                    statisticsChart_card.setOption({        //载入数据
                        title: {
                            text: result.cardTodayTotal,
                            subtext: '当日办证',
                            sublink: '',
                            x: 'center',
                            y: 'center',
                            itemGap: 20,
                            textStyle : {
                                color : 'white',
                                fontFamily : '微软雅黑',
                                fontSize : 20,
                                fontWeight : ''
                            }
                        },
                        series : [
                            {
                                name:'',
                                type:'pie',
                                clockWise:true,
                                radius : [60, 70],
                                itemStyle : {
                                    normal : {
                                        color:function(){
                                            return colors[2];
                                        },
                                        label : {
                                            show : false
                                        },
                                        labelLine : {
                                            show : false
                                        }
                                    }
                                },
                                data:[
                                    {
                                        value:100
                                    },
                                    {
                                        value:0,
                                        name:'invisible',
                                        itemStyle : placeHolderStyle
                                    }
                                ]
                            },
                            {
                                name:'',
                                type:'pie',
                                clockWise:true,
                                radius : [50, 60],
                                itemStyle : {
                                    normal : {
                                        color:function(){
                                            return colors[1];
                                        },
                                        label : {
                                            show : false
                                        },
                                        labelLine : {
                                            show : false
                                        }
                                    }
                                },
                                data:[
                                    {
                                        value:(result.cardMonthTotal/cardTotal)*100
                                    },
                                    {
                                        value:100-(result.cardMonthTotal/cardTotal)*100,
                                        name:'invisible',
                                        itemStyle : placeHolderStyle
                                    }
                                ]
                            },
                            {
                                name:'',
                                type:'pie',
                                clockWise:true,
                                radius : [40, 50],
                                itemStyle : {
                                    normal : {
                                        color:function(){
                                            return colors[0];
                                        },
                                        label : {
                                            show : false
                                        },
                                        labelLine : {
                                            show : false
                                        }
                                    }
                                },
                                data:[
                                    {
                                        value:(result.cardTodayTotal/cardTotal)*100
                                    },
                                    {
                                        value:100-(result.cardTodayTotal/cardTotal)*100,
                                        name:'invisible',
                                        itemStyle : placeHolderStyle
                                    }
                                ]
                            }
                        ]
                    });
                }
                setTimeout(function(){loadStatisticsData()}, 300000);//5分钟
            }
        });
    };

    var loadVisitData = function(){
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/visit/record',
            data:{},
            dataType: 'json',
            success: function(data) {
                if (data.state) {
                    var result = data.result;
                    $(".visit_today").text(result.todayTotal);
                    $(".visit_month").text(result.monthTotal);
                }
            }
		});
    };

    var loadVisitTodayData = function (){
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/visit/record/today',
            data:{},
            dataType: 'json',
            success: function(data) {
                if (data.state) {
                    var chart_visit_today = echarts.init(document.getElementById('chart_visit_today'));
                    var result = data.result;
                    initVisitTodayData(chart_visit_today, result);
                }
            }
        });
    };

    var loadBookData = function (){
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/book/circulate',
            data:{},
            dataType: 'json',
            success: function(data) {
                if (data.state) {
                    var chart_borrowAndReturn = echarts.init(document.getElementById('chart_borrowAndReturn'));
                    var result = data.result;
                    initBookData(chart_borrowAndReturn, result);
                }
            }
        });
    };

    var loadBookCategoryData = function (){
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/book/category',
            data:{},
            dataType: 'json',
            success: function(data) {
                if (data.state) {
                    var charts_bookCategory = echarts.init(document.getElementById('charts_bookCategory'));
                    var result = data.result;
                    var liHtml1 = '';
                    var liHtml2 = '';
                    var total = 0;
                    for(var i in result){
                        total += result[i].circulateTotal;
                        switch(parseInt(i)) {
                            case 0:
                                liHtml1 += '<li><div style="border-radius: 2px;margin-top:6px;width: 12px;height: 12px;background-color: rgb(255,202,103);float:left;"></div>&nbsp;&nbsp;'+result[i].name+result[i].proportion+'%</li>';
                                break;
                            case 1:
                                liHtml1 += '<li><div style="border-radius: 2px;margin-top:6px;width: 12px;height: 12px;background-color: rgb(252,95,162);float:left;"></div>&nbsp;&nbsp;'+result[i].name+result[i].proportion+'%</li>';
                                break;
                            case 2:
                                liHtml1 += '<li><div style="border-radius: 2px;margin-top:6px;width: 12px;height: 12px;background-color: rgb(108,101,251);float:left;"></div>&nbsp;&nbsp;'+result[i].name+result[i].proportion+'%</li>';
                                break;
                            case 3:
                                liHtml2 += '<li><div style="border-radius: 2px;margin-top:6px;width: 12px;height: 12px;background-color: rgb(251,145,95);float:left;"></div>&nbsp;&nbsp;'+result[i].name+result[i].proportion+'%</li>';
                                break;
                            case 4:
                                liHtml2 += '<li><div style="border-radius: 2px;margin-top:6px;width: 12px;height: 12px;background-color: rgb(191,100,255);float:left;"></div>&nbsp;&nbsp;'+result[i].name+result[i].proportion+'%</li>';
                                break;
                            case 5:
                                liHtml2 += '<li><div style="border-radius: 2px;margin-top:6px;width: 12px;height: 12px;background-color: rgb(63,67,164);float:left;"></div>&nbsp;&nbsp;'+result[i].name+result[i].proportion+'%</li>';
                                break;
                        }
                    }
                    result.total = total;
                    $(".category_li1").html(liHtml1);
                    $(".category_li2").html(liHtml2);
                    initBookCategoryData(charts_bookCategory, result);
                }
            }
        });
    };

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

    loadStatisticsData();
    loadVisitData();
    loadVisitTodayData();
    loadBookData();
    loadBookCategoryData();
    getWeather();
});
</script>
</html>