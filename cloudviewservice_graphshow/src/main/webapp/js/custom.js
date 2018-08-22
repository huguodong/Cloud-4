function initLine(chart){
	//var incount = data.series.incount;
	//var outcount = data.series.outcount;
	//var date = data.date;
	/* var incount = [];
	var outcount = [];
	var addData = function(shift) {
		if(!shift){
			date.push(i);
		}
		incount.push(Math.ceil(Math.random()* 101));
		outcount.push(Math.ceil(Math.random()* 52));
		if (shift) {
			date.shift();
			var val = date[date.length-1] + 1;
			if (val > 24) val = 1;
			date.push(val);
			incount.shift();
			outcount.shift();
		}
	} */
	
	/* for (var i = 1; i <= 24; i++) {
		//addData();
		date.push(i);
	} */

	option = {
		title : {
			text : '各时段进出馆人次',
			x : 'center',
			textStyle : {
				fontSize : 24,
				fontWeight : 'normal',
				color : '#23FFBB'
			}
		},
        tooltip : {
	        showDelay: 0,
	        hideDelay: 0,
	        transitionDuration:0,
	        padding: 15,
	        position : function(p) {
	            return [p[0] + 10, p[1] - 10];
	        },
	        formatter:'{b}<br/>{a}：{c0}'//<br/>{a1}：{c1}<br/>{a2}：{c2}
	    },
        toolbox: {  
            show: true,
            x: 'left',
            feature: {  
                magicType: {show: true, type: ['line', 'bar']},  
                restore: {show: true},  
                saveAsImage: {show: true}  
            }  
        },
        legend: {
	    	show: true,
			x : 'center',
			y : 'bottom',
			itemGap : 20,
			data : [{
				name : '进馆',	
				textStyle : {
					fontSize : 14,
					color : '#99CCCC'
				}
			},{
				name : '出馆',
				textStyle : {
					fontSize : 14,
					color : '#99CCCC'
				}
			}],
			selected: {  
                '进馆': true,  
                '出馆': true
            },
	        itemWidth: 20,             // 图例图形宽度
	        itemHeight: 20            // 图例图形高度
        },
		xAxis : {
			type : 'category',
			axisLine : {
				lineStyle : {
					width: 2,
					color : '#99CCCC'
				}
			},
			axisLabel : {
				formatter : '{value}'
			},
			boundaryGap : false,
			data : []
		},
		yAxis : [ {
			show : true,
			type : 'value',
			axisLine : {
				lineStyle : {
					width: 2,
					color : '#99CCCC'
				}
			},
			splitLine : {
				show : false
			},
			axisLabel : {
				formatter : '{value} 人'
			}
		} ],
		series : [{
			name : '进馆',
			type : 'line',
			barMaxWidth : 25,//固定柱子宽度
			symbol : 'none',
			itemStyle : {
				normal : {
					color : '#23FFBB',
					lineStyle: {        // 系列级个性化折线样式
                        width: 2
                  	},
                  	label : {
                        show : true,
                        position : 'top',
                        textStyle : {
                            fontSize : '15'
                        }
                    }
				}
			},
			data : []
		},{
			name : '出馆',
			type : 'line',
			barMaxWidth : 25,//固定柱子宽度
			symbol : 'none',
			itemStyle : {
				normal : {
					color : '#FFD54E',
					lineStyle: {        // 系列级个性化折线样式
                        width: 2
                  	},
                  	label : {
                        show : true,
                        position : 'top',
                        textStyle : {
                            fontSize : '15'
                        }
                    }
				}
			},
			data : []
		}]
	};

	chart.setOption(option);
};

function initBar(chart){
	option = {
		title : {
			text : '当天文献借还量',
			x : 'center',
			textStyle : {
				fontSize : 24,
				fontWeight : 'normal',
				color : '#23FFBB'
			}
		},
	    tooltip : {
	        //trigger: 'axis',
	        showDelay: 0,
	        hideDelay: 0,
	        transitionDuration:0,
	        padding: 15,
	        position : function(p) {
	            return [p[0] + 10, p[1] - 10];
	        },
	        formatter:'{b} <br/>{a}：{c0}'//<br/>{a1}：{c1}<br/>{a2}：{c2}
	    },
	    toolbox: {
	        show : true,
	        x: 'left',                // 水平安放位置，默认为全图右对齐，可选为：
	        feature : {
	            magicType : {show: true, type: ['line', 'bar']},//, 'stack', 'tiled'
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    legend: {
	    	show: true,
			x : 'center',
			y : 'bottom',
			itemGap : 20,
			data : [{
				name : '借书',	
				textStyle : {
					fontSize : 14,
					color : '#99CCCC'
				}
			},{
				name : '还书',
				textStyle : {
					fontSize : 14,
					color : '#99CCCC'
				}
			},{
				name : '续借',
				textStyle : {
					fontSize : 14,
					color : '#99CCCC'
				}
			}],
			selected: {  
                '借书': true,  
                '还书': true,
                '续借': true
            },
            //backgroundColor: 'rgba(0,0,0,0)',
            //borderWidth: 0,
	        //padding: 2,                // 图例内边距，单位px，默认各方向内边距为5，
	        itemWidth: 20,             // 图例图形宽度
	        itemHeight: 20            // 图例图形高度
        },
        xAxis : {
			axisLine : {
				lineStyle : {
					width: 2,
					color : '#99CCCC'
				}
			},
			axisLabel : {
				formatter : '{value}'
			},
			data : []
		},
		yAxis : [ {
			type : 'value',
			axisLine : {
				lineStyle : {
					width: 2,
					color : '#99CCCC'
				}
			},
			splitLine : {
				show : false
			},
			axisLabel : {
				formatter : '{value} 册'
			}
		}],
		series : [{
			name: '借书',
			type : 'bar',
			barMaxWidth : 20,//固定柱子宽度
			itemStyle : {
				normal : {
					color: new echarts.graphic.LinearGradient(0, 0, 0, 1,[{offset: 0, color: '#37CFFC'},{offset: 1, color: '#3784D9'}]),
					label : {
                        show : true,
                        position : 'top',
                        textStyle : {
                            fontSize : '15'
                        }
                    }
				}
			},
			data : []
		},{
			name: '还书',
			type : 'bar',
			barMaxWidth : 20,//固定柱子宽度
			itemStyle : {
				normal : {
					color: new echarts.graphic.LinearGradient(0, 0, 0, 1,[{offset: 0, color: '#9DFFAA'},{offset: 1, color: '#19FFBD'}]),
					label : {
                        show : true,
						position : 'top',
                        textStyle : {
                            fontSize : '15'
                        }
                    }
				}
			},
			data : []
		},{
			name: '续借',
			type : 'bar',
			barMaxWidth : 20,//固定柱子宽度
			itemStyle : {
				normal : {
					color: new echarts.graphic.LinearGradient(0, 0, 0, 1,[{offset: 0, color: '#FFEDB3'},{offset: 1, color: '#FFD54E'}]),
					label : {
                        show : true,
						position : 'top',
                        textStyle : {
                            fontSize : '15'
                        }
                    }
				}
			},
			data : []
		}]
	};
	// 使用刚指定的配置项和数据显示图表。
	chart.setOption(option);
};

function initPie(chart,legend,series){
	option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        x: 'left',
	        data:legend
	    },
	    series: [
	        {
	            name:'',
	            type:'pie',
	            radius: ['30%', '80%'],
	            center:['50%', '45%'],
	            avoidLabelOverlap: false,
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                },
	                emphasis: {
	                    show: true,
	                    textStyle: {
	                        fontSize: '30',
	                        fontWeight: 'bold'
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            data:series
	        }
	    ],
	    color:['#37CFFC','#FFA500','#3784D9','#607B8B','#FF83FA']
	};
	chart.setOption(option);
};

var category = ['小王', '小李', '小赵', '小马', '小刘', '小张', '小齐'];
var barData = [3100, 2142, 1218, 581, 431, 383, 163];
//横向柱状图
function initCategoryBar(chart){
	var option = {
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        top: '1%',
	        bottom:'5%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'value',
	        axisLine : {
				lineStyle : {
					width: 2,
					color : '#99CCCC'
				}
			},
			splitLine: {show: false},
	        axisTick: {
	            show: false
	        }
	    },
	    yAxis: {
	        type: 'category',
	        data: [],
	        axisLine : {
				lineStyle : {
					width: 2,
					color : '#99CCCC'
				}
			},
	        splitLine: {show: false},
	        axisTick: {
	            show: false
	        },
	        nameTextStyle: {
	            fontSize: 20
	        }
	    },
	    series: [
	        {
	            name: '数量',
	            type: 'bar',
	            data: [],
	            barMaxWidth: 25,
	            barGap: 5,
	            smooth: true,
	            label: {
	                normal: {
	                    show: true,
	                    position: 'right',
	                    textStyle: {
	                        color: '#F68300',
	                        fontSize: 13
	                    }
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 1, 0,
	                        [
	                            {offset: 0, color: '#3977E6'},
	                            {offset: 1, color: '#37BBF8'}
	                        ]
	                    )
	                }
	            }
	        }
	    ]
	};
	chart.setOption(option);
};


function formatter(num) {  
    var num = (num || 0).toString(), result = '';  
    while (num.length > 3) {  
        result = ',' + num.slice(-3) + result;  
        num = num.slice(0, num.length - 3);  
    }  
    if (num) { result = num + result; }  
    return result;  
}