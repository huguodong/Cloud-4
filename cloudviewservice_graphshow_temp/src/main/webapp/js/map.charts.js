function initMapCharts() {
    var dom = document.getElementById("charts_map");
    var myChart = echarts.init(dom);
    var data = [
        {name: '曹妃甸职业技术学院图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '山西医科大学图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '贺兰县图书馆', value: Math.random() > 0.5 ? 100 : 99},
        // {name: '贺兰图书馆居安社区馆', value: Math.random()>0.5?100:99},
        // {name: '兰州大学榆中校区', value: Math.random()>0.5?100:99},
        {name: '兰州大学图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '天水师范学院', value: Math.random() > 0.5 ? 100 : 99},
        {name: '西南科技大学图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '西南财经大学图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '南京市溧水区图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '昆山图书馆', value: Math.random() > 0.5 ? 100 : 99},
        // {name: '昆山图书馆九方城分馆', value: Math.random()>0.5?100:99},
        // {name: '昆山图书馆澞和苑分馆', value: Math.random()>0.5?100:99},
        {name: '东阳市图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '莲都区图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '深圳图书馆', value: Math.random() > 0.5 ? 100 : 99},
        // 新增的图书馆
        {name: '广州图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '梅州图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '北京图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '汉中图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '长沙图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '大理图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '抚州图书馆', value: Math.random() > 0.5 ? 100 : 99},
        {name: '上海图书馆', value: Math.random() > 0.5 ? 100 : 99}
    ];
    var geoCoordMap = {
        '曹妃甸职业技术学院图书馆': [118.02, 39.63],
        '山西医科大学图书馆': [112.53, 37.87],
        '贺兰县图书馆': [106.27, 38.47],
        // '贺兰图书馆居安社区馆':[106.27,38.47],
        // '兰州大学榆中校区':[103.73,36.03],
        '兰州大学图书馆': [103.49, 36.03],
        '天水师范学院': [105.69, 34.6],
        '西南科技大学图书馆': [104.73, 31.48],
        '西南财经大学图书馆': [104.06, 30.67],
        '南京市溧水区图书馆': [118.78, 32.04],
        '昆山图书馆': [118.95, 33.39],
        // '昆山图书馆九方城分馆':[120.95,31.39],
        // '昆山图书馆澞和苑分馆':[120.95,31.39],
        '东阳市图书馆': [119.64, 29.12],
        '莲都区图书馆': [119.92, 28.45],
        '深圳图书馆': [114.46, 22.27],
        // 新增的图书馆
        '广州图书馆' : [113.30, 23.20],
        '梅州图书馆' : [116.12, 24.28],
        '北京图书馆' : [116.46, 39.92],
        '汉中图书馆' : [107.01, 34.16],
        '长沙图书馆' : [112.59, 28.21],
        '大理图书馆' : [100.27, 25.26],
        '抚州图书馆' : [116.34, 28.00],
        '上海图书馆' : [121.40, 31.20]
    };
    $("#library_total").html("图书馆接入总数：" + data.length + "个");
    $("#eq_total").html("设备接入总数：65个");
    var slice = parseInt((Math.random() * 10)) + 4;
    if (slice > 10) {
        slice = 10; // 先写死，最多亮起地图上的10个点
    }
    var convertData = function (data) {
        var res = [];
        for (var i = 0; i < data.length; i++) {
            var geoCoord = geoCoordMap[data[i].name];
            if (geoCoord) {
                res.push({
                    // name: data[i].name,
                    value: geoCoord.concat(data[i].value)
                });
            }
        }
        return res;
    };
    var option = {
        backgroundColor: 'rgba(30,30,47, 0.7)',
        geo: {
            aspectScale:0.85,
            map: 'china',
            label: {
                emphasis: {
                    show: false
                }
            },
            roam: false,
            scaleLimit:{
                max:1.2,
                min:1.2
            },
            itemStyle: {
                normal: {
                    areaColor: '#25576c',
                    borderColor: '#6ca0b9',
                    borderWidth: '2'
                }
            }
        },
        series: [
            {
                name: 'pm2.5',
                type: 'scatter',
                coordinateSystem: 'geo',
                data: convertData(data),
                symbolSize: function (val) {
                    return val[2] / 10;
                },
                label: {
                    normal: {
                        formatter: '{b}',
                        position: 'right',
                        show: true,
                        textStyle: {
                            color: 'rgb(222,222,231)'
                        }
                    },
                    emphasis: {
                        show: true
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#93dffc'
                    }
                },
                animationDurationUpdate: function (idx) {
                    // 越往后的数据延迟越大
                    return 3000;
                }
            },
            {
                name: 'Top 1',
                type: 'effectScatter',
                coordinateSystem: 'geo',
                data: convertData(data.sort(function (a, b) {
                    return b.value - a.value;
                }).slice(0, slice)),
                symbolSize: function (val) {
                    return val[2] / 10;
                },
                showEffectOn: 'render',
                rippleEffect: {
                    brushType: 'stroke'
                },
                hoverAnimation: false,
                label: {
                    normal: {
                        formatter: '{b}',
                        position: 'right',
                        show: true,
                        textStyle: {
                            color: 'rgb(222,222,231)'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#f4e925',
                        shadowBlur: 10,
                        shadowColor: '#333'
                    }
                },
                zlevel: 1,
                animationDurationUpdate: function (idx) {
                    // 越往后的数据延迟越大
                    return 4000;
                }
            }
        ]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}

initMapCharts();
setInterval('initMapCharts()', 60000);