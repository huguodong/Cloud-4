<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <title>大屏展示</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/imageflow.css">
</head>
<body>
<div class="wrp">
    <div class="head tc">
        <span class="s-title">云平台即时显示系统</span>
        <p class="fr fr-time">
        </p>
    </div>
    <!-- 内容 -->
    <div class="main-content clearfix">
        <div class="fl fl-content">
            <div class="view-item c-bg view-1" style="position:relative;">
                <div class="fl cw cw1" style="text-align: right;position: absolute;top: 50%;transform: translateY(-50%);">
                    <img src="${pageContext.request.contextPath}/img/icon1.png">
                </div>
                <div class="fl cw cw2" style="text-align: right;position: absolute;top: 50%;transform: translateY(-50%);margin-left: 35%;">
                    <p>
                        <span class="f35 c1 visit_today">&nbsp;</span>
                        <span class="f18">今日到馆人数</span>
                    </p>
                </div>
                <div class="fl cw cw2" style="text-align: right;position: absolute;top: 50%;transform: translateY(-50%);margin-left: 60%;">
                    <p>
                        <span class="f35 c1 visit_month">&nbsp;</span>
                        <span class="f18">当月到馆人数</span>
                    </p>
                </div>
            </div>
            <div class="view-item c-bg view-2 mt15">
                <div class="view-2-div1">
                    <div class="view-2-div1-p1" style="margin-top:0.51%;background: rgb(255,94,123);float:left;"></div>
                    <p style="font-size: 15px;">&nbsp;当日</p>
                    <div class="view-2-div1-p2" style="margin-top:0.51%;background: rgb(192,99,253);float:left;"></div>
                    <p style="font-size: 15px;">&nbsp;当月</p>
                    <div class="view-2-div1-p2" style="margin-top:0.51%;background: rgb(106,102,250);float:left;"></div>
                    <p style="font-size: 15px;">&nbsp;全年</p>
                </div>
                <div id="statisticsChart_borrow" class="statisticsChart-borrow"></div>
                <div id="statisticsChart_return" class="statisticsChart"></div>
                <div id="statisticsChart_card" class="statisticsChart"></div>
                <div class="f18 statisticsChart-borrow-content"></div>
                <div class="f18 statisticsChart-return-content"></div>
                <div class="f18 statisticsChart-card-content"></div>
            </div>
            <div class="view-item view-3 mt15">
                <div class="c-bg2">当天人流量</div>
                <div class="c-bg view-4" id="chart_visit_today"></div>
            </div>
        </div>
        <div class="fr fr-content ">
            <div class="view-5 clearfix">
                <div class="view-item view-5-1 fl">
                    <div class="c-bg2 title"><span id="library_total"></span><span id="eq_total"></span></div>
                    <div class="c-bg view-6" id="charts_map"></div>
                </div>
                <div class="view-item view-5-2 fr">
                    <div class="c-bg2 title">流通分类</div>
                    <div class="c-bg view-6">
                        <div style="height: 20%;">
                            <ul class="category_li1" style="margin-top: 5%;"></ul>
                            <ul class="category_li2" style="margin-top: 5%;"></ul>
                        </div>
                        <div style="height: 80%;" id="charts_bookCategory"></div>
                    </div>
                </div>
            </div>
            <div class="view-7  mt15 clearfix">
                <div class="view-item view-7-1 fl">
                    <input id="S_Num" type="hidden" value="5"/>
                    <div class="c-bg2 title">热门图书排行</div>
                    <div class="c-bg view-8 imageflow" id="starsIF">
                        <img src="${pageContext.request.contextPath}/img/mingaicisheng.png"/>
                        <img src="${pageContext.request.contextPath}/img/tebiedemao.png"/>
                        <img src="${pageContext.request.contextPath}/img/wodebingrenshiyingdi.png"/>
                        <img src="${pageContext.request.contextPath}/img/wocengaininameduo.png"/>
                        <img src="${pageContext.request.contextPath}/img/chengshi.png"/>
                    </div>
                </div>
                <div class="view-item view-7-1 fl" style="z-index: 555;position: absolute;background:#000; filter:alpha(opacity:30); opacity:0;">
                    <div class="c-bg2 title"></div>
                    <div class="c-bg view-8 imageflow" id="starsIF_temp">
                    </div>
                </div>
                <div class="view-item view-7-2 fr">
                    <div class="c-bg2 title">当天文献借还量</div>
                    <div class="c-bg view-8" id="chart_borrowAndReturn"></div>
                </div>
            </div>
        </div>
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
    $(function () {

        var placeHolderStyle = {
            normal: {
                color: 'rgba(0,0,0,0)',
                label: {show: false},
                labelLine: {show: false}
            },
            emphasis: {
                color: 'rgba(0,0,0,0)'
            }
        };

        //获取统计数据
        var isFirst = true;
        function loadStatisticsData() {
            //console.info("-------request patron data for line and table------");
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/statistics',
                data: {},
                dataType: 'json',
                success: function (data) {
                    if (data.state) {
                        //插入图表
                        var result = data.result;
                        var statisticsChart_borrow = echarts.init(document.getElementById('statisticsChart_borrow'));
                        var statisticsChart_return = echarts.init(document.getElementById('statisticsChart_return'));
                        var statisticsChart_card = echarts.init(document.getElementById('statisticsChart_card'));
                        //init line
                        if (isFirst) {
                            initStatisticsData(statisticsChart_borrow, document.getElementById('statisticsChart_borrow'));
                            initStatisticsData(statisticsChart_return, document.getElementById('statisticsChart_return'));
                            initStatisticsData(statisticsChart_card, document.getElementById('statisticsChart_card'));
                            isFirst = false;
                        }
                        var colors = ['rgb(255,94,123)', 'rgb(192,99,253)', 'rgb(106,102,250)'];
                        //借书
                        var borrowTotal = result.bookBorrowYearTotal;
                        $(".statisticsChart-borrow-content").html('<span style="font-size: 26px;line-height:10px">'+borrowTotal+'</span>册<br/><span style="line-height:25px">总借书量</span>');
                        statisticsChart_borrow.setOption({        //载入数据
                            title: {
                                text: result.bookBorrowTodayTotal,
                                subtext: '当日借书',
                                subtextStyle:{
                                    color : '#fff',
                                    fontSize : 16
                                },
                                top:'40%',
                                sublink: '',
                                x: 'center',
                                y: 'center',
                                itemGap: 5,
                                textStyle: {
                                    color: '#fff',
                                    fontFamily: '微软雅黑',
                                    fontSize: 30,
                                    fontWeight: 'normal'
                                }
                            },
                            series: [
                                {
                                    name: '',
                                    type: 'pie',
                                    clockWise: true,
                                    radius: ['70%', '80%'],
                                    itemStyle: {
                                        normal: {
                                            color: function () {
                                                return colors[2];
                                            },
                                            label: {
                                                show: false
                                            },
                                            labelLine: {
                                                show: false
                                            }
                                        }
                                    },
                                    data: [
                                        {
                                            value: 100
                                        },
                                        {
                                            value: 0,
                                            name: 'invisible',
                                            itemStyle: placeHolderStyle
                                        }
                                    ]
                                },
                                {
                                    name: '',
                                    type: 'pie',
                                    clockWise: true,
                                    radius: ['60%', '68%'],
                                    itemStyle: {
                                        normal: {
                                            color: function () {
                                                return colors[1];
                                            },
                                            label: {
                                                show: false
                                            },
                                            labelLine: {
                                                show: false
                                            }
                                        }
                                    },
                                    data: [
                                        {
                                            value: (result.bookBorrowMonthTotal / borrowTotal) * 100
                                        },
                                        {
                                            value: 100 - (result.bookBorrowMonthTotal / borrowTotal) * 100,
                                            name: 'invisible',
                                            itemStyle: placeHolderStyle
                                        }
                                    ]
                                },
                                {
                                    name: '',
                                    type: 'pie',
                                    clockWise: true,
                                    radius: ['50%', '58%'],
                                    itemStyle: {
                                        normal: {
                                            color: function () {
                                                return colors[0];
                                            },
                                            label: {
                                                show: false
                                            },
                                            labelLine: {
                                                show: false
                                            }
                                        }
                                    },
                                    data: [
                                        {
                                            value: (result.bookBorrowTodayTotal / borrowTotal) * 100
                                        },
                                        {
                                            value: 100 - (result.bookBorrowTodayTotal / borrowTotal) * 100,
                                            name: 'invisible',
                                            itemStyle: placeHolderStyle
                                        }
                                    ]
                                }
                            ]
                        });

                        //还书
                        var returnTotal = result.bookReturnYearTotal;
                        $(".statisticsChart-return-content").html('<span style="font-size: 26px;line-height:10px">'+returnTotal+'</span>册<br/><span style="line-height:25px">总还书量</span>');
                        statisticsChart_return.setOption({        //载入数据
                            title: {
                                text: result.bookReturnTodayTotal,
                                subtext: '当日还书',
                                subtextStyle:{
                                    color : '#fff',
                                    fontSize : 16
                                },
                                top:'40%',
                                sublink: '',
                                x: 'center',
                                y: 'center',
                                itemGap: 5,
                                textStyle: {
                                    color: 'white',
                                    fontFamily: '微软雅黑',
                                    fontSize: 30,
                                    fontWeight: ''
                                }
                            },
                            series: [
                                {
                                    name: '',
                                    type: 'pie',
                                    clockWise: true,
                                    radius: ['70%', '80%'],
                                    itemStyle: {
                                        normal: {
                                            color: function () {
                                                return colors[2];
                                            },
                                            label: {
                                                show: false
                                            },
                                            labelLine: {
                                                show: false
                                            }
                                        }
                                    },
                                    data: [
                                        {
                                            value: 100
                                        },
                                        {
                                            value: 0,
                                            name: 'invisible',
                                            itemStyle: placeHolderStyle
                                        }
                                    ]
                                },
                                {
                                    name: '',
                                    type: 'pie',
                                    clockWise: true,
                                    radius: ['60%', '68%'],
                                    itemStyle: {
                                        normal: {
                                            color: function () {
                                                return colors[1];
                                            },
                                            label: {
                                                show: false
                                            },
                                            labelLine: {
                                                show: false
                                            }
                                        }
                                    },
                                    data: [
                                        {
                                            value: (result.bookReturnMonthTotal / returnTotal) * 100
                                        },
                                        {
                                            value: 100 - (result.bookReturnMonthTotal / returnTotal) * 100,
                                            name: 'invisible',
                                            itemStyle: placeHolderStyle
                                        }
                                    ]
                                },
                                {
                                    name: '',
                                    type: 'pie',
                                    clockWise: true,
                                    radius: ['50%', '58%'],
                                    itemStyle: {
                                        normal: {
                                            color: function () {
                                                return colors[0];
                                            },
                                            label: {
                                                show: false
                                            },
                                            labelLine: {
                                                show: false
                                            }
                                        }
                                    },
                                    data: [
                                        {
                                            value: (result.bookReturnTodayTotal / returnTotal) * 100
                                        },
                                        {
                                            value: 100 - (result.bookReturnTodayTotal / returnTotal) * 100,
                                            name: 'invisible',
                                            itemStyle: placeHolderStyle
                                        }
                                    ]
                                }
                            ]
                        });
                        //办证
                        var cardTotal = result.cardYearTotal;
                        $(".statisticsChart-card-content").html('<span style="font-size: 26px;line-height:10px">'+cardTotal+'</span>张<br/><span style="line-height:25px">总办证量</span>');
                        statisticsChart_card.setOption({        //载入数据
                            title: {
                                text: result.cardTodayTotal,
                                subtext: '当日办证',
                                subtextStyle:{
                                    color : '#fff',
                                    fontSize : 16
                                },
                                top:'40%',
                                sublink: '',
                                x: 'center',
                                y: 'center',
                                itemGap: 5,
                                textStyle: {
                                    color: 'white',
                                    fontFamily: '微软雅黑',
                                    fontSize: 30,
                                    fontWeight: ''
                                }
                            },
                            series: [
                                {
                                    name: '',
                                    type: 'pie',
                                    clockWise: true,
                                    radius: ['70%', '80%'],
                                    itemStyle: {
                                        normal: {
                                            color: function () {
                                                return colors[2];
                                            },
                                            label: {
                                                show: false
                                            },
                                            labelLine: {
                                                show: false
                                            }
                                        }
                                    },
                                    data: [
                                        {
                                            value: 100
                                        },
                                        {
                                            value: 0,
                                            name: 'invisible',
                                            itemStyle: placeHolderStyle
                                        }
                                    ]
                                },
                                {
                                    name: '',
                                    type: 'pie',
                                    clockWise: true,
                                    radius: ['60%', '68%'],
                                    itemStyle: {
                                        normal: {
                                            color: function () {
                                                return colors[1];
                                            },
                                            label: {
                                                show: false
                                            },
                                            labelLine: {
                                                show: false
                                            }
                                        }
                                    },
                                    data: [
                                        {
                                            value: (result.cardMonthTotal / cardTotal) * 100
                                        },
                                        {
                                            value: 100 - (result.cardMonthTotal / cardTotal) * 100,
                                            name: 'invisible',
                                            itemStyle: placeHolderStyle
                                        }
                                    ]
                                },
                                {
                                    name: '',
                                    type: 'pie',
                                    clockWise: true,
                                    radius: ['50%', '58%'],
                                    itemStyle: {
                                        normal: {
                                            color: function () {
                                                return colors[0];
                                            },
                                            label: {
                                                show: false
                                            },
                                            labelLine: {
                                                show: false
                                            }
                                        }
                                    },
                                    data: [
                                        {
                                            value: (result.cardTodayTotal / cardTotal) * 100
                                        },
                                        {
                                            value: 100 - (result.cardTodayTotal / cardTotal) * 100,
                                            name: 'invisible',
                                            itemStyle: placeHolderStyle
                                        }
                                    ]
                                }
                            ]
                        });
                    }
                }
            });
        };

        function loadVisitData() {
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/visit/record',
                data: {},
                dataType: 'json',
                success: function (data) {
                    if (data.state) {
                        var result = data.result;
                        $(".visit_today").text(result.todayTotal);
                        $(".visit_month").text(result.monthTotal);
                    }
                }
            });
        };

        function loadVisitTodayData() {
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/visit/record/today',
                data: {},
                dataType: 'json',
                success: function (data) {
                    if (data.state) {
                        var chart_visit_today = echarts.init(document.getElementById('chart_visit_today'));
                        var result = data.result;
                        initVisitTodayData(chart_visit_today, result);
                    }
                }
            });
        };

        function loadBookData() {
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/book/circulate',
                data: {},
                dataType: 'json',
                success: function (data) {
                    if (data.state) {
                        var chart_borrowAndReturn = echarts.init(document.getElementById('chart_borrowAndReturn'));
                        var result = data.result;
                        initBookData(chart_borrowAndReturn, result);
                    }
                }
            });
        };

        function loadBookCategoryData() {
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/book/category',
                data: {},
                dataType: 'json',
                success: function (data) {
                    if (data.state) {
                        var charts_bookCategory = echarts.init(document.getElementById('charts_bookCategory'));
                        var result = data.result;
                        var liHtml1 = '';
                        var liHtml2 = '';
                        var total = 0;
                        var allProp = 100;//完整的百分比，演示效果需要100%完整，需要计算
                        for (var i in result) {
                            total += result[i].circulateTotal;
                            allProp -= parseFloat(result[i].proportion).toFixed(1);
                            switch (parseInt(i)) {
                                case 0:
                                    liHtml1 += '<li><div style="left:30%;position:relative"><div style="position: absolute;top: 50%;transform: translateY(-50%);float:left;background-color: rgb(255,202,103);width: 12px;height: 12px;border-radius:2px"></div>&nbsp;&nbsp;&nbsp;&nbsp;' + result[i].name + result[i].proportion + '%</div></li>';
                                    break;
                                case 1:
                                    liHtml1 += '<li style="margin-top:10%"><div style="left:30%;position:relative"><div style="position: absolute;top: 50%;transform: translateY(-50%);float:left;background-color: rgb(252,95,162);width: 12px;height: 12px;border-radius:2px"></div>&nbsp;&nbsp;&nbsp;&nbsp;' + result[i].name + result[i].proportion + '%</div></li>';
                                    break;
                                case 2:
                                    liHtml1 += '<li style="margin-top:10%"><div style="left:30%;position:relative"><div style="position: absolute;top: 50%;transform: translateY(-50%);float:left;background-color: rgb(108,101,251);width: 12px;height: 12px;border-radius:2px"></div>&nbsp;&nbsp;&nbsp;&nbsp;' + result[i].name + result[i].proportion + '%</div></li>';
                                    break;
                                case 3:
                                    liHtml2 += '<li><div style="left:40%;position:relative"><div style="position: absolute;top: 50%;transform: translateY(-50%);float:left;background-color: rgb(251,145,95);width: 12px;height: 12px;border-radius:2px"></div>&nbsp;&nbsp;&nbsp;&nbsp;' + result[i].name + result[i].proportion + '%</div></li>';
                                    break;
                                case 4:
                                    liHtml2 += '<li style="margin-top:10%"><div style="left:40%;position:relative"><div style="position: absolute;top: 50%;transform: translateY(-50%);float:left;background-color: rgb(191,100,255);width: 12px;height: 12px;border-radius:2px"></div>&nbsp;&nbsp;&nbsp;&nbsp;' + result[i].name + result[i].proportion + '%</div></li>';
                                    break;
                                case 5:
                                    if(allProp > 0){
                                        result[i].proportion = parseFloat(result[i].proportion+allProp).toFixed(1);
                                    }
                                    liHtml2 += '<li style="margin-top:10%"><div style="left:40%;position:relative"><div style="position: absolute;top: 50%;transform: translateY(-50%);float:left;background-color: rgb(63,67,164);width: 12px;height: 12px;border-radius:2px"></div>&nbsp;&nbsp;&nbsp;&nbsp;' + result[i].name + result[i].proportion + '%</div></li>';
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
        function getWeather() {
            $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function() {
                if (remote_ip_info.ret == '1') {
                    $.ajax({
                        type: 'POST',
                        data: {'weather_city': remote_ip_info.city},
                        url: '${pageContext.request.contextPath}/getTitle',
                        dataType: 'json',
                        success: function (data) {
                            var htm = "";
                            if (data.state) {
                                if (data.result.date)
                                    htm += '<span class="f16">' + data.result.date + '</span>';
                                if (data.result.high)
                                    htm += '<span class="f20">' + data.result.temp;
                                if (data.result.weather)
                                    htm += '&nbsp;&nbsp;&nbsp;<i><img width="22" height="23" title="' + data.result.type + '" src="${pageContext.request.contextPath}/img/' + data.result.weather + '.png"/></i></span>';
                            }
                            $(".fr-time").empty().html(htm);
                            setTimeout(function () {
                                getWeather()
                            }, 3600000);//1小时
                        }
                    });
                }
            });
        };

        function loadAll(){
            loadStatisticsData();
            loadVisitData();
            loadVisitTodayData();
            loadBookData();
            loadBookCategoryData();
            getWeather();
        }
        loadAll();
        setInterval(function(){loadAll()}, 60000);

    });
    setTimeout('location.href="${pageContext.request.contextPath}/video?videoCode=${videoCode}"', 6000000);
</script>
</html>