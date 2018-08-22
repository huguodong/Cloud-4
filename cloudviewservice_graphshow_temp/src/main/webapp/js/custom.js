function initStatisticsData(chart, obj) {
    option = {
        tooltip: {
            show: false
        },
        legend: {
            orient: 'vertical',
            x: obj.offsetWidth / 2,
            y: 45,
            itemGap: 12,
            data: []
        },
        toolbox: {
            show: false
        }
    };
    chart.setOption(option);
}

function initVisitTodayData(chart, data) {
    var xAxisData = [];
    var inData = [];
    var outData = [];
    for (var i in data) {
        xAxisData.push(data[i].time);
        inData.push(data[i].inTotal);
        outData.push(data[i].outTotal);
    }
    var colors = ['rgba(252,103,165, 0.7)', 'rgba(255,202,103, 0.7)'];
    option = {
        backgroundColor: 'rgba(30,30,47, 0.7)',
        title: {
            text: '',
            subtext: ''
        },
        // 悬浮显示
        // tooltip : {
        //     trigger: 'axis'
        // },
        legend: {
            data: ['进馆', '出馆'],
            top: '5%',
            textStyle: {
                color: 'white',
                fontSize: 15
            }
        },
        toolbox: {
            show: false
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: xAxisData,
                axisLine: {
                    lineStyle: {
                        color: 'rgb(144,144,193)'
                    }
                },
                axisTick: {
                    inside: false
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLine: {
                    lineStyle: {
                        width: 0,
                        color: 'rgb(144,144,193)'
                    }
                },
                axisTick: {
                    inside: true,
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                }
            }
        ],
        series: [
            {
                name: '进馆',
                type: 'line',
                smooth: true,
                symbol: 'none',
                itemStyle: {normal: {areaStyle: {type: 'default'}, color: colors[0]}},
                data: inData
            },
            {
                name: '出馆',
                type: 'line',
                smooth: true,
                symbol: 'none',
                itemStyle: {normal: {areaStyle: {type: 'default'}, color: colors[1]}},
                data: outData
            }
        ]
    };
    chart.setOption(option);
}

function initBookData(chart, data) {
    var xAxisData = [];
    var borrowData = [];
    var returnData = [];
    var continueData = [];
    for (var i in data) {
        xAxisData.push(data[i].time);
        borrowData.push(data[i].borrowTotal);
        returnData.push(data[i].returnTotal);
        continueData.push(data[i].continueTotal);
        // console.log(data[i].borrowTotal + "\t" + data[i].returnTotal + "\t"+ data[i].continueTotal + "\t");
    }
    var colors = ['rgb(252,103,165)', 'rgb(255,202,103)', 'rgb(106,102,250)'];
    option = {
        // 悬浮显示
        // tooltip : {
        //     trigger: 'axis'
        // },
        backgroundColor: 'rgba(30,30,47, 0.7)',
        color: ['rgb(252,103,165)', 'rgb(255,202,103)', 'rgb(106,102,250)'],
        legend: {
            data: ['借书', '还书', '续借'],
            textStyle: {
                color: 'white',
                fontSize: 15
            },
            top: '5%',
            selectedMode: false
        },
        toolbox: {
            show: false
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: xAxisData,
                axisLine: {
                    lineStyle: {
                        color: 'rgb(144,144,193)'
                    }
                },
                axisTick: {
                    inside: false
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLine: {
                    lineStyle: {
                        width: 0,
                        color: 'rgb(144,144,193)'
                    }
                },
                axisTick: {
                    inside: true,
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                }
            }
        ],
        series: [
            {
                name: '借书',
                type: 'line',
                stack: '借书总量',
                symbol: 'none',
                itemStyle: {
                    normal: {
                        lineStyle: {
                            color: colors[0],
                            width: 5
                        }
                    }
                },
                data: borrowData
            },
            {
                name: '还书',
                type: 'line',
                stack: '还书总量',
                symbol: 'none',
                itemStyle: {
                    normal: {
                        lineStyle: {
                            color: colors[1],
                            width: 5
                        }
                    }
                },
                data: returnData
            },
            {
                name: '续借',
                type: 'line',
                stack: '续借总量',
                symbol: 'none',
                itemStyle: {
                    normal: {
                        lineStyle: {
                            color: colors[2],
                            width: 5
                        }
                    }
                },
                data: continueData
            }
        ]
    };
    chart.setOption(option);
}

function initBookCategoryData(charts, data) {
    var chartsData = [];
    for (var i in data) {
        var dataInfo = {};
        dataInfo.value = data[i].circulateTotal;
        dataInfo.name = data[i].name;
        chartsData.push(dataInfo);
    }
    option = {
        backgroundColor: 'rgba(30,30,47, 0.7)',
        title: {
            show: false
        },
        calculable: true,
        series: [
            {
                name: '面积模式',
                type: 'pie',
                radius: ['30%', '60%'],
                center: ['50%', '50%'],
                roseType: 'area',
                // x: '0%',               // for funnel
                max: 40,                // for funnel
                sort: 'desc',     // for funnel
                color: ['rgb(255,202,103)', 'rgb(252,95,162)', 'rgb(108,101,251)', 'rgb(251,145,95)', 'rgb(191,100,255)', 'rgb(63,67,164)'],
                data: chartsData,
                //标线的属性设置，以及显示的文字
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                fontSize: 18,
                                fontWeight: '700'
                            }
                        },
                        //标线长度，宽度
                        labelLine: {
                            show: true,
                            length: 0,
                            lineStyle: {
                                width: 1
                            }
                        }
                    }
                }
            }
        ],
        graphic: {            //echarts饼图中间放字
            type: 'text',
            left: 'center',
            top: 'center',
            z: 2,
            style: {
                text: data.total + '\n流通量',
                textAlign: 'center',
                fill: '#fff',
                font: ' 22px 微软雅黑 '
            }
        }
    };
    charts.setOption(option);
}