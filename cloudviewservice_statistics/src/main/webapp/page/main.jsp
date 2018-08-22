<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
    <%@include file="common/global.jsf" %>
    <style>
        * a{color:#000;}
    </style>
</head>
<body>
<%@include file="common/header.jsf" %>

<div class="main-content">
    <div class="left-side">
        <div class="control-btn"></div>
        <div class="absolute-wrap">
            <div class="title"><spring:message code="main.jsp.menu"></spring:message></div>
            <div class="item" id="mainIndex">
                <div class="t icon1"><a href="javascript:void(0)" onclick="gotoMain()"><spring:message code="main.jsp.index"/></a></div>
            </div>
        </div>
    </div>
    <div id="mainframe">
        <div class="page-title-bar">
            <span class="title"><spring:message code="main.jsp.index"/></span>
            <div class="form-wrap fr">
            	<div class="g-select" id="library_div" style="display: none;">
					<select id="library">
						<option value="-1">选择图书馆</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<div class="g-select" id="containSubLib_div" style="display: none;">
					<select id=containSubLib>
						<option value="-1">选择是否包含子馆数据</option>
						<option value="Y">包含子馆数据</option>
						<option value="N">不包含子馆数据</option>
					</select>
					<span class="arr-icon"></span>
				</div>
                <div class="g-select">
                    <select id="type">
                        <option value="year">本年度数据</option>
                        <option value="month">当月数据</option>
                        <option value="week">本周数据</option>
                        <option value="today">当天数据</option>
                        <option value="lastYear">去年数据</option>
                        <option value="lastMonth">上月数据</option>
                        <option value="yesterday">昨天数据</option>
                        <option value="all">全部数据</option>
                    </select>
                    <span class="arr-icon"></span>
                </div>
                <div class="btn search">查询</div>
            </div>
        </div>
        <div class="main">
            <div class="main-page">
	            <div id="chart1_1" class="chart"></div>
	            <div id="chart1_2" class="chart_pie"></div>
	            <div id="chart2_1" class="chart"></div>
	            <div id="chart2_2" class="chart_pie"></div>
	            <div id="chart3_1" class="chart"></div>
	            <div id="chart3_2" class="chart_pie"></div>
	            <div id="chart4_1" class="chart"></div>
	            <div id="chart4_2"></div>
            </div>
            <div style="clear: both;"></div>
            <div class="page-footer" style="text-align: center;margin-top: 50px;">
            	<div class="version"><spring:message code="main.jsp.version"/> V 1.0</div>
            	<div class="company"><spring:message code="main.jsp.haihengcompany"/></div>
            </div>
        </div>
    </div>
</div>
<%--返回顶部图标 --%>
<div id="topcontrol" title="回到顶部" style="position: fixed; bottom: 40px; right: 25px; cursor: pointer; display: block;">
    <img src="${pageContext.request.contextPath}/static/css/images/backTop.png" style="width:31px; height:31px;">
</div>
<script src="${pageContext.request.contextPath}/static/js/echarts.custom.js"></script>
<script type="text/javascript">
    $(function(){
        (function mainHeightController(){
            var winH = $(window).height();
            var headerH = $(".g-header").outerHeight();
            var pageTitleBar = $(".page-title-bar").outerHeight();
            var pagingBarH = $(".paging-bar").outerHeight();

            $(".main-page").css({
                "min-height":winH - headerH - pagingBarH -pageTitleBar - 40
            });
        })();

        //设置服务器时间
        $.ajax({
            url:"${pageContext.request.contextPath}/login/getTime",
            type:"GET",
        }).done(function(data){
            if(data){
                $(".today").html(data.result);
            }
        });
    });

    var html = "";
    var operator_idx="";
    var loop;
    $(function(){
        $.ajax({
            url:"${pageContext.request.contextPath}/personalSetting/getoper",
            type:"POST",
            data:"",
            async:false,
            success:function(data){
                operator_idx = data.operator_idx;

            }
        });

        var Page={"operator_idx":operator_idx};
        $.ajax({
            url:"${pageContext.request.contextPath}/personalSetting/selOperatorByOperIdOrIdx",
            type:"POST",
            async:false,
            data:{"req":JSON.stringify(Page)}
        }).done(function(data){
            if(data!=null && data.state){
                var list = data.result;
                html+="<div class=\"item\">";
                html+="<div class='t icon-search-single'>常用查询与统计<span class=\"arr-icon\"></span></div>";
                html+="<ul>";
                for(var i=0;i<list.length;i++){
                    var item = list[i];
                    //console.log(item);
                    if(item.setting_url !=null && item.setting_url.length >0){
                        var url = item.setting_url;
                        if(url.indexOf("http://") != 0){
                            url ="http://"+item.setting_url;
                        }
                        html+="	<li><a target=\"view_window\" href=\""+url+"\">"+item.setting_desc+"</a></li>";
                    }else{
                        html+="	<li><a href=\"javascript:void(0);\" data-url=\"${pageContext.request.contextPath}/templatemanagement/commontongji?req="+item.service_idx+"\">"+item.setting_desc+"</a></li>";
                    }
                }
                html+="</ul>";
                html+="</div>";
            }
        });

        $.ajax({
            url:"${pageContext.request.contextPath}/statisticsTemplate/selectTemplateMenuByOperidx",
            type:"POST",
            async:false,
            data:""
        }).done(function(data){
            if(data!=null && data.state){
                var list = data.result;
                html+="<div class=\"item\">";
                html+="<div class='t icon-search'>全部查询与统计<span class=\"arr-icon\"></span></div>";
                html+="<ul>";
                for(var i=0;i<list.length;i++){
                    var item = list[i];
                    html+="	<li><a href=\"javascript:void(0)\" data-url=\"${pageContext.request.contextPath}/templatemanagement/commontongji?req="+item.statistics_tpl_idx+"\">"+item.statistics_tpl_desc+"</a></li>";
                }
                html+="</ul>";
                html+="</div>";
            }
        });
        getUserMenu();
        
        var param = {};
		param.library_idx = o.library_idx;
		param.lib_name = o.lib_name;
        param.operator_type = o.operator_type;
        
		if(param.operator_type != o.cloud_admin){
        	$("#library").append("<option value='"+param.library_idx+"'>"+param.lib_name+"</option>");
   		}
        
		$.ajax({
	   		url:"${pageContext.request.contextPath}/mainPage/querylibInfoByCurUserEXT1",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				if(data.state){
					if(data.result.length > 0){
						$("#library_div").show();
   						$("#containSubLib_div").show();
					}
					$.each(data.result,function(i,item){
   						$("#library").append("<option value='"+item.library_idx+"'>"+item.lib_name+"</option>");
   		            });
				}
	   		}
    	});
   		
   		var chart1_1 = echarts.init(document.getElementById('chart1_1'));
   		var chart1_2 = echarts.init(document.getElementById('chart1_2'));
   		var chart2_1 = echarts.init(document.getElementById('chart2_1'));
   		var chart2_2 = echarts.init(document.getElementById('chart2_2'));
   		var chart3_1 = echarts.init(document.getElementById('chart3_1'));
   		var chart3_2 = echarts.init(document.getElementById('chart3_2'));
   		var chart4_1 = echarts.init(document.getElementById('chart4_1'));
   		var chart4_2 = echarts.init(document.getElementById('chart4_2'));
   		
   		$(window).resize(function() {
   	    	$(".chart").css({width : $(".main-page").width()*0.5});
   	    	$(".chart_pie").css({width : $(".main-page").width()*0.4});
   	    	chart1_1.resize({width : "auto"});
   	    	chart1_2.resize({width : "auto"});
   	    	chart2_1.resize({width : "auto"});
   	    	chart2_2.resize({width : "auto"});
   	    	chart3_1.resize({width : "auto"});
   	    	chart3_2.resize({width : "auto"});
   	    	chart4_1.resize({width : "auto"});
   		});
  		
   		
        jQuery.search = function(){
        	var lib_idx=o.library_idx;
        	var type=$("#type").find("option:selected").val(); 
        	if(!type) type = "year";
        	
        	var containSubLib=$("#containSubLib").find("option:selected").val(); 
    		if(containSubLib==-1){
    			containSubLib = "Y";
    		}
    		
       		var sellib_idx=$("#library").find("option:selected").val(); 
        	if(o.operator_type == o.cloud_admin){ 
	       		if(sellib_idx!=-1){
	       			lib_idx = sellib_idx;
	       		}else{
	       			lib_idx = 0;
	       		}
        	}else{
        		if(sellib_idx!=-1){
	       			lib_idx = sellib_idx;
	       		}
        	}
        	
	    	var postData1 = lib_idx==0?{"type":type,"chart_type":"bar","containSubLib":containSubLib}:{"type":type,"library_idx":lib_idx,"chart_type":"bar","containSubLib":containSubLib};
	    	loadLoanData(postData1,chart1_1);
	    	loadFinanceData(postData1,chart2_1);
	    	loadCardissueData(postData1,chart3_1);
	    	loadVisitorData(postData1,chart4_1);
	    	var postData = lib_idx==0?{"type":type,"chart_type":"pie","containSubLib":containSubLib}:{"type":type,"library_idx":lib_idx,"chart_type":"pie","containSubLib":containSubLib};
	    	loadLoanDataForPie(postData,chart1_2);
	    	loadFinanceDataForPie(postData,chart2_2);
	    	loadCardissueDataForPie(postData,chart3_2);
        }
        
        $.search();
        
    	loop = setInterval(function(){
    		$.search();
    	}, 60000);//1分钟
    	
    	$(".search").on("click",function(){
            $.search();
        });

   	    $(document).on("click","ul li a",function(data){
   	        var url = $(this).attr("data-url");
   	        <%--保存当前访问菜单--%>
   	        sessionStorage.setItem("currentMenu", url);
   	        $("#mainframe").load(url,"",function(){
   	            $(".form-dialog-fix .form-dialog").each(function(){
   	                $(this).attr("date-right",$(this).css("right"));
   	            });
   	            clearInterval(loop);
   	        });
   	    })

    });
    
    <%--跳转到之前访问的菜单--%>
    function toUrl(){
    	var currentMenu = sessionStorage.getItem("currentMenu");
        if(!_.isEmpty(currentMenu)){
            $("ul li a[data-url='"+currentMenu+"']").closest(".item").find("div.t").trigger("click");
            $("ul li a[data-url='"+currentMenu+"']").trigger("click");
            clearInterval(loop);
        }
    }
    
    function gotoMain(){
        sessionStorage.removeItem("currentMenu");
        window.location.href = "${pageContext.request.contextPath}/login/main";
        clearInterval(loop);
        loop = setInterval(function(){
    		$.search();
    	}, 60000);//1分钟
    }
    
    function loadLoanDataForPie(postData,chart){
		$.ajax({
			url:"${pageContext.request.contextPath}/mainPage/countLoanLog",
            type:"POST",
			data: {"req":JSON.stringify(postData)},
			dataType: 'json',
			success: function(data){
				chart.clear();
				var dom = chart.getDom();
				if(data.state){
					result = data.result;
					initPie(chart,'流通类型统计',result.opercmds,result.datas);
					chart.hideLoading();
					if(dom && dom.id){
						$("#"+dom.id).show();
					}
				}else{
					if(dom && dom.id){
						$("#"+dom.id).hide();
					}
				}
			}
		});
    };
	
    //借还数据统计
    function loadLoanData(postData,chart){
		$.ajax({
			url:"${pageContext.request.contextPath}/mainPage/countLoanLog",
            type:"POST",
			data: {"req":JSON.stringify(postData)},
			dataType: 'json',
			success: function(data){
				chart.clear();
				var dom = chart.getDom();
				if(data.state){
					result = data.result;
					initLoanBar(chart);
					chart.hideLoading();
					chart.setOption({        //载入数据
	                    xAxis: {
	                    	//splitLine:{show: true},
	                        data: result.date    //填入X轴数据
	                    },
	                    series: [    //填入系列（内容）数据
	                       {
	                           data: result.loan_total,
	                       },
	                       {
	                           data: result.return_total,
	                       },
	                       {
	                           data: result.renew_total,
	                      }
	                   ]
	                });
					if(dom && dom.id){
						$("#"+dom.id).show();
					}
				}else{
					//initLoanBar(chart);
					if(dom && dom.id){
						$("#"+dom.id).hide();
					}
				}
			}
		});
	};
    
    //人流量数据统计
    function loadVisitorData(postData,chart){
		$.ajax({
			url:"${pageContext.request.contextPath}/mainPage/countVisitorLog",
            type:"POST",
			data: {"req":JSON.stringify(postData)},
			dataType: 'json',
			success: function(data){
				chart.clear();
				var dom = chart.getDom();
				if(data.state){
					result = data.result;
					initVisitorBar(chart);
					chart.hideLoading();
					chart.setOption({        //载入数据
	                    xAxis: {
	                    	//splitLine:{show: true},
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
					if(dom && dom.id){
						$("#"+dom.id).show();
					}
				}else{
					//initVisitorBar(chart);
					if(dom && dom.id){
						$("#"+dom.id).hide();
					}
				}
			}
		});
	};
	
	function loadFinanceDataForPie(postData,chart){
		$.ajax({
			url:"${pageContext.request.contextPath}/mainPage/countFinanceLog",
            type:"POST",
			data: {"req":JSON.stringify(postData)},
			dataType: 'json',
			success: function(data){
				chart.clear();
				var dom = chart.getDom();
				if(data.state){
					result = data.result;
					initPie(chart,'扣费类型统计',result.opercmds,result.datas);
					chart.hideLoading();
					if(dom && dom.id){
						$("#"+dom.id).show();
					}
				}else{
					if(dom && dom.id){
						$("#"+dom.id).hide();
					}
				}
			}
		});
    };
	
	//财经数据统计
	function loadFinanceData(postData,chart){
		$.ajax({
			url:"${pageContext.request.contextPath}/mainPage/countFinanceLog",
            type:"POST",
			data: {"req":JSON.stringify(postData)},
			dataType: 'json',
			success: function(data){
				chart.clear();
				var dom = chart.getDom();
				if(data.state){
					result = data.result;
					initFinanceBar(chart);
					chart.hideLoading();
					chart.setOption({        //载入数据
	                    xAxis: {
	                    	//splitLine:{show: true},
	                        data: result.date    //填入X轴数据
	                    },
	                    series: [    //填入系列（内容）数据
	                      {
	                           data: result.total
	                      }
	                   ]
	                });
					if(dom && dom.id){
						$("#"+dom.id).show();
					}
				}else{
					//initFinanceBar(chart);
					if(dom && dom.id){
						$("#"+dom.id).hide();
					}
				}
			}
		});
	};
	
	function loadCardissueDataForPie(postData,chart){
		$.ajax({
			url:"${pageContext.request.contextPath}/mainPage/countCardissueLog",
            type:"POST",
			data: {"req":JSON.stringify(postData)},
			dataType: 'json',
			success: function(data){
				chart.clear();
				var dom = chart.getDom();
				if(data.state){
					result = data.result;
					initPie(chart,'读者性别统计',result.opercmds,result.datas);
					chart.hideLoading();
					if(dom && dom.id){
						$("#"+dom.id).show();
					}
				}else{
					if(dom && dom.id){
						$("#"+dom.id).hide();
					}
				}
			}
		});
    };
	
	//办证数据统计
	function loadCardissueData(postData,chart){
		$.ajax({
			url:"${pageContext.request.contextPath}/mainPage/countCardissueLog",
            type:"POST",
			data: {"req":JSON.stringify(postData)},
			dataType: 'json',
			success: function(data){
				chart.clear();
				var dom = chart.getDom();
				if(data.state){
					result = data.result;
					initCardissueBar(chart);
					chart.hideLoading();
					chart.setOption({        //载入数据
	                    xAxis: {
	                    	//splitLine:{show: true},
	                        data: result.date    //填入X轴数据
	                    },
	                    series: [    //填入系列（内容）数据
     	                   {
   	                           data: result.success
   	                       },
   	                       {
   	                           data: result.failure
   	                       }
   	                   ]
	                });
					if(dom && dom.id){
						$("#"+dom.id).show();
					}
				}else{
					if(dom && dom.id){
						$("#"+dom.id).hide();
					}
				}
			}
		});
	};

    <%-- 获取用户的菜单 --%>
    function getUserMenu(){
        $.ajax({
            url:"${pageContext.request.contextPath}/login/getUserMenus",
            type:"POST",
            async:false,
            data:""
        }).done(function(data){
            if(data!=null && data.state){
                var list = data.result;
                for(var i=0;i<list.length;i++){
                    var item = list[i];
                    var subMenu = item.subMenu;
                    if(subMenu!=null){
                        var iconClass = item.menu_mark==null?"icon1":item.menu_mark;
                        html+="<div class=\"item\">";
                        html+="<div class=\"t "+iconClass+"\">"+item.name+"<span class=\"arr-icon\"></span></div>";
                        html+="<ul>";
                        for(var j=0;j<subMenu.length;j++){
                            var sitem = subMenu[j];
                            html+="	<li><a href=\"javascript:void(0)\" data-url=\"${pageContext.request.contextPath}/"+sitem.url+"\">"+sitem.name+"</a></li>";
                        }
                        html+="</ul>";
                        html+="</div>";
                    }
                }
                $("#mainIndex").after(html);
                toUrl();
            }
        });
        $(".main-content").addClass("unfold");
    }
    
    function GlobalShowMsg(option){
        var defaults = {
            timeout : option.timeout || 1000,
            showText : option.showText || '添加配置成功',
            backgroundColor : option.status === true ? "#2ab65b" :"#ff3d3d",
            callback:function(){

            }
        };
        defaults = $.extend(defaults,option);
        layer.msg(defaults.showText, {
            area:["240px","60px"],
            offset:["110px"],
            time: defaults.timeout,
            success: function(layero, index){
                $(".layui-layer-hui").css("background-color",defaults.backgroundColor);
            }
        }, function(){
            defaults.callback();
        });
    }
</script>

</body>
</html>