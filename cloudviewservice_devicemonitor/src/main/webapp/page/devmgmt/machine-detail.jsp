<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
    <%@include file="../common/global.jsf" %>
    <style type="text/css">
        .machine-detail .zhibixiang .t-box ul li {
            width: auto;
        }

        .g-inputtime .error-msg {
            position: absolute;
            left: 180px;
            top: 0;
            width: 150px;
            height: 35px;
            text-align: center;
            line-height: 35px;
            border-radius: 2px;
            background-color: #ffdddd;
            display: block;
        }
        
        .machine-detail .chart{
			width:700px;
			height:450px;
			margin:10px 50px;
			display: inline-block;
			float: left;
		}

        /*cir-status4.png*/
        .machine-detail .status-box .ul .li.grey:after {
            background: rgba(64, 84, 222, 0.31) url(${ctx}/static/images/error.png) center center no-repeat;
        }
    </style>
</head>
<body style="background:#FFF;min-width: 500px;overflow: hidden;">
<div class="machine-detail" >
    <div class="head">
        <input hidden="hidden" id="libId_deviceId" value="${device.device_id}">
        <!-- 设备名字、设备ID -->
        <div class="name"><span class="id">${rel_device_id}</span></div>
        <div class="time">
            <div class="item startupTime">
                <%-- 系统启动时间：2015-11-06 12:07:51 --%>
            </div>
            <div class="item updateTime">
                <%-- 系统链接时间：2015-11-06 12:07:51 --%>
            </div>
        </div>
        <div class="time">
            <div class="item logistics_time">
                <%-- 维护时间：2015-11-06 12:07:51 至 2015-11-06 12:07:51  --%>
            </div>
        </div>
        <div class="tab">
            <ul>
                <li class="btn1 active">设备状态</li>
                <li class="btn2">远程日志</li>
                <li class="btn3">业务数据</li>
            </ul>
        </div>
    </div>
    <div class="main">
        <div class="book-info hide">
            <div class="float-wrap">
                <div class="item item1" style="display: none">
                    <div class="sec1">
                        <span class="t">在架书：</span>
                        <span class="num">-册</span>
                    </div>
                    <div class="sec2">
                        <ul>
                            <li>一层：<span class="right level1">-册</span></li>
                            <li>二层：<span class="right level2">-册</span></li>
                            <li>三层：<span class="right level3">-册</span></li>
                            <li>特型书盒：<span class="right exbox">-册</span></li>
                        </ul>
                    </div>
                </div>
                <div class="item item2" style="display: none">
                    <div class="sec1">
                        <span class="t">空书盒：</span>
                        <span class="num">-个</span>
                    </div>
                    <div class="sec2">
                        <ul>
                            <li>一层：<span class="right level1">-册</span></li>
                            <li>二层：<span class="right level2">-册</span></li>
                            <li>三层：<span class="right level3">-册</span></li>
                            <li>特型书盒：<span class="right exbox">-册</span></li>
                        </ul>
                    </div>
                </div>
                <div class="item item3">
                    <div class="sec1" style="display: none">
                        <span class="t">预借书：</span>
                        <span class="num">-册</span>
                    </div>
                    <div class="sec2" <c:if test="${cardNum == null || cardNum == ''}">style="display: none"</c:if> >
                        <div class="right">
                            <span class="t">卡证箱：</span>
                         	 剩余<span class="num">${cardNum}&nbsp;张</span>
                        </div>
                    </div>
                </div>
                <div class="item item4" style="display: none">
                    <div class="sec2">
                        <ul>
                            <li>异常格口：<span class="right exception">-格</span></li>
                            <li>标准格口：<span class="right stand">-格 / -格</span></li>
                            <li>超大格口：<span class="right biggest">-格 / -格</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="zhibixiang hide">
            <div class="t-box" id="cardbox">卡箱：</div>
            <div class="t-box" id="cashbox">纸币箱：</div>
            <ul class="box-list">
                <!--
                 <li>
                    <div class="name">B01箱</div>
                    <span>4册</span><span>其他</span>
                 </li>
                -->
            </ul>
        </div>
        <div class="status-box super-layer ext_state">
            <span class="t">外设状态</span>
            <div class="line"></div>
            <div class="padding-wrap">
                <%-- <div class="one-line">
                        <div class="ul">
                            <div class="li can-unfold" date-id="1">外设状态</div>
                            <div class="li error">外设状态</div>
                            <div class="li can-unfold" date-id="11">外设状态</div>
                            <div class="li">外设状态</div>
                        </div>
                    </div>
                    <div class="border-wrap" data-id="1">
                        <div class="status-box">
                            <span class="t">PLC状态1</span>
                            <div class="ul">
                                <div class="li">PLC状态</div>
                                <div class="li error">PLC状态</div>
                                <div class="li">PLC状态</div>
                                <div class="li">PLC状态</div>
                                <div class="li">PLC状态</div>
                            </div>
                        </div>
                    </div>
                    <div class="border-wrap" data-id="11">
                        <div class="status-box">
                            <span class="t">PLC状态2</span>
                            <div class="ul">
                                <div class="li">PLC状态</div>
                                <div class="li error">PLC状态</div>
                                <div class="li">PLC状态</div>
                                <div class="li">PLC状态</div>
                                <div class="li">PLC状态</div>
                            </div>
                        </div>
                    </div>
                    <div class="one-line">
                        <div class="ul">
                            <div class="li can-unfold"  date-id="2">外设状态</div>
                        </div>
                    </div>
                    <div class="border-wrap" data-id="2">
                        <div class="status-box">
                            <span class="t">PLC状态</span>
                            <div class="ul">
                                <div class="li">PLC状态</div>
                                <div class="li error">PLC状态</div>
                                <div class="li">PLC状态</div>
                                <div class="li">PLC状态</div>
                                <div class="li">PLC状态</div>
                            </div>
                        </div>
                    </div> --%>
            </div>
        </div>
        <div class="status-box super-layer function_state">
            <span class="t">功能状态</span>
            <div class="line"></div>
            <div class="padding-wrap">

            </div>
        </div>
    </div>
    <div class="file-check">
        <div class="handle-bar">
            <div class="item ">
                <div class="word">开始时间</div>
                <div class="g-inputdate"><input name="startDate" class="g-input datepicker" type="text"
                                                placeholder="选择日期"><span class="icon"></span></div>
                <div class="g-inputtime"><input name="startTime" class="g-input timepicker" type="text"
                                                placeholder="选择时间"><span class="icon"></span>
                    <span class="error-msg">请填写开始时间</span>
                </div>
            </div>
            <div class="item">
                <div class="word">结束时间</div>
                <div class="g-inputdate"><input name="endDate" class="g-input datepicker" type="text"
                                                placeholder="选择日期"><span class="icon"></span></div>

                <div class="g-inputtime"><input name="endTime" class="g-input timepicker" type="text"
                                                placeholder="选择时间"><span class="icon"></span>
                    <span class="error-msg">请填写结束时间</span>
                </div>
            </div>

            <div class="word"></div>
            <a href="#" target="_blank"
               class="g-btn search g-btn-blue">在线查询</a>
            <div class="g-btn download g-btn-blue">下载日志</div>
        </div>
    </div>
    <div class="biz-data">
        <div class="handle-bar">
            <div class="item ">
                <div id="chart1"></div>
            </div>
            <div class="item">
                <div id="chart2"></div>
            </div>
            <div class="item">
                <div id="chart3"></div>
            </div>
            <div class="item">
                <div id="chart4"></div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	$(window).resize(function () {          //当浏览器大小变化时
		var winH = $(window).height();
		var headH = $(".head").height();
		var mainH = winH - headH;
		$(".main,.biz-data").css({
			"overflow-y":"scroll",
			"height":mainH+"px"
	    });
	});
	
	function dispDiv(event,x,text){
		var xx=event.clientX;
		var yy=event.clientY;
		var divx1 = x.offsetLeft+20; //返回元素的水平偏移位置
		var divy1 = x.offsetTop;  //返回元素的垂直偏移位置
		var divx2 = x.offsetLeft+x.offsetWidth; //返回元素的水平偏移位置+返回元素的宽度
		var divy2 = x.offsetTop+x.offsetHeight;  //返回元素的垂直偏移位置+返回元素的高度
			
		$("#tipDiv").css("display","block");
		$("#tipDiv").css("position","fixed");
		$("#tipDiv").css("left",""+xx+"px");
		$("#tipDiv").css("top",""+yy+"px");
		$("#tipDiv").css("z-index",99999);
		$("#tipDiv").css("border","1px solid #ccc");
		$("#tipDiv").css("border-radius","2px");
		$("#tipDiv").css("box-shadow","0px 0px 4px #999");
		$("#tipDiv").css("cursor","pointer");
		$("#tipDiv").css("line-height","30px");
		$("#tipDiv").css("text-align","center");
		$("#tipDiv").text(text);
		$("#tipDiv").text(text);
    }  
    
    function hideDiv(x,event){
    	$("#tipDiv").css("display","none");
    }
    $(function () {
		var winH = $(window).height();
		var headH = $(".head").height();
		var mainH = winH - headH;
		$(".main,.biz-data").css({
			"overflow-y":"auto",
			"height":mainH+"px"
	    });
<%--//var devType="${device_type}";--%>
        /**
         根据设备类型自动显示对应的对应的状态
         根据部件判断是否存在这些外设信息
         (在架书 .book-info .item1 书盒 /item2   预借书 item3/sec1 卡证箱 sec2   纸币箱  .zhibixiang)
         AuthorityBarcode：读者证条码枪
         AuthorityReader： 读者证阅读器
         CardDispenser   发卡机 （卡证箱）
         CashAcceptor   :收钞机 （纸币箱）
         IdentityReader 身份证阅读器
         ItemBarcode    图书条码枪
         ItemLoadReader 补书阅读器
         ItemRfidReader 图书阅读器
         PlcReturn      还书机PLC     （书盒）
         PlcSorter      分拣机PLC
         PlcSSL         自助图书馆PLC  （show all）
         Printer        打印机
         RegisterReader 发卡阅读器
         **/
            //设备ID  实际上是 library_id _ device_id
        var device_id = $("#libId_deviceId").val();
        var library_id = "${library_id}";
        var device_idx = "${device.device_idx}";
        var rel_device_id = "${rel_device_id}";
        
        var DEVICE_TYPE = '${device_type}'.toLowerCase();//设备类型
        $(".g-btn.search.g-btn-blue").click(function (e) {
        	var startDate=$("input[name='startDate']").val();
            var startTime=$("input[name='startTime']").val();
            var endDate=$("input[name='endDate']").val();
            var endTime=$("input[name='endTime']").val();
            var url="${ctx}/device/checklogonline?device_id=" + rel_device_id +"&startDate="+startDate+"&startTime="+startTime+"&endDate="+endDate+"&endTime="+endTime;
        	this.href=url;
        });

        $("div.status-box.plc_state").click(function (e) {
            //$(this).slideUp("fast");
            //slideToggle target content and adjust bottom border if necessary
            $(this).find("ul").slideToggle("fast", function () {
                //$(".accordion :visible:last").css("border-radius","0 0 10px 10px");
            });
        });

        //设置书架信息 参数为：device_id数组
        var setBookrackState = function () {
            device_id = $.trim(device_id);
            var arr = new Array();
            arr.push(device_id);
            $.ajax({
                type: "POST",
                url: "${ctx}/device/selectBookrackState",
                data: {"req": JSON.stringify(arr)}
            }).done(function (data) {
            	//console.info("data---",data);
                if (data && data.state == true && data.result && data.result[device_id]) {
                    console.log('******************selectBookrackState*********************');
                    console.log(data);
                    var result = data.result[device_id];
                    console.log("setBookrackState", result);
                    console.log("deviceType",DEVICE_TYPE);
                    $(".book-info").show();
                    if (isDevice(DEVICE_TYPE,'dlv')){
                        var exception = result.Abnormal || '0';
                        var biggest = result.Oversized || '0/0';
                        var stand = result.Standardized || '0/0';
                        $item4 = $("div.main").find(".item.item4");
                        $item4.find("span.exception").html(exception+"格");
                        $item4.find("span.biggest").html(biggest.split("/")[1]+"格&nbsp;/&nbsp;"+biggest.split("/")[0]+"格");
                        $item4.find("span.stand").html(stand.split("/")[1]+"格&nbsp;/&nbsp;"+stand.split("/")[0]+"格");
                        $("div.main").find(".item.item4").show();
                    } else {
                        var level1 = result.Level1 || (result.level1 || '0/0');
                        console.log("level1", level1);
                        var level2 = result.Level2 || (result.Level2 || '0/0');
                        console.log("level2", level2);
                        var level3 = result.Level3 || (result.Level3 || '0/0');
                        console.log("level3", level3);
                        var precheckout = result.Precheckout || 0;
                        var exbox = result.Exbox || (result.exbox || '0/0');

                        var level1Books = Number(level1.split("/")[1]);
                        var level2Books = Number(level2.split("/")[1]);
                        var level3Books = Number(level3.split("/")[1]);
                        var exboxBooks = Number(exbox.split("/")[1]);

                        var level1Empty = Number(level1.split("/")[0]) - level1Books;
                        var level2Empty = Number(level2.split("/")[0]) - level2Books;
                        var level3Empty = Number(level3.split("/")[0]) - level3Books;
                        var exboxEmpty = Number(exbox.split("/")[0]) - exboxBooks;

                        $item1sec2 = $("div.main").find(".item.item1").find(".sec2");
                        $item1sec2.find("span.right.level1").html(level1Books + "册");
                        $item1sec2.find("span.right.level2").html(level2Books + "册");
                        $item1sec2.find("span.right.level3").html(level3Books + "册");
                        $item1sec2.find("span.right.exbox").html(exboxBooks + "册");
                        $("div.main").find(".item.item1").show();//显示书盒状态

                        //在架书
                        var onRackBooks = level1Books + level2Books + level3Books + exboxBooks;
                        $("div.main").find(".item.item1").find(".sec1").find(".num").html(onRackBooks + "册");

                        $item2sec2 = $("div.main").find(".item.item2").find(".sec2");
                        $item2sec2.find("span.right.level1").html(level1Empty + "个");
                        $item2sec2.find("span.right.level2").html(level2Empty + "个");
                        $item2sec2.find("span.right.level3").html(level3Empty + "个");
                        $item2sec2.find("span.right.exbox").html(exboxEmpty + "个");
                        $("div.main").find(".item.item2").show();//显示空书盒状态

                        var emptyBoxs = level1Empty + level2Empty + level3Empty + exboxEmpty;

                        $("div.main").find(".item.item2").find(".sec1").find(".num").html(emptyBoxs + "个");

                        $item3sec1 = $("div.main").find(".item.item3").find(".sec1");
                        $item3sec1.find(".num").html(precheckout + "册");
                        $item3sec1.show();//显示预借数
                    }
                }
                console.log('******************selectBookrackState2*********************');
            });
        };
        //设置箱子信息
        var setDeviceBinState = function () {
            var arr = new Array();
            arr.push(device_id);
            $.ajax({
                type: "POST",
                url: "${ctx}/device/selectBinState",
                data: {"req": JSON.stringify(arr)}
            }).done(function (data) {
                console.log("setDeviceBinState", data);
                console.log("device_id", device_id);
                if (data && data.state == true && data.result[device_id] && !isDevice(DEVICE_TYPE,'dlv')) {
                    var binState = data.result[device_id];
                    var cardbin=binState.cardbin;//卡箱
                    var cashbin = binState.cashbin;//钱箱
                    var bookbin = binState.bookbin;//书箱
                    
                    console.info("cardbin----",cardbin);
                    console.info("cashbin----",cashbin);
                    console.info("bookbin----",bookbin);
                    
					//设置还书箱信息。
                    /**
                     <li>
                     <div class="name">B01箱</div>
                     <span>4册</span><span>其他</span>
                     </li>
                     **/
					if(bookbin){
						var bookBinHtml = '<ul>';
						for (var j = 0; j < bookbin.length; j++) {
							var binno = bookbin[j].binno;
							var desc = bookbin[j].desc;//海洋类？工具类？
							var amount = bookbin[j].amount;//箱子 书本册数
							bookBinHtml += '<li><div class="name">' + binno + '箱</div>';
							bookBinHtml += '<span>' + amount + '册</span><span>' + desc + '</span></li>';
						}
						bookBinHtml += '</ul>';
						$("div.zhibixiang").find(".box-list").html(bookBinHtml);
					}
                    
					
                    //设置纸币箱信息
                    if(cashbin){
                    	var cashBinhtml = "纸币箱：<ul>";
                        for (var i = 0; i < cashbin.length; i++) {
                            var subtype = Number(cashbin[i].subtype);
                            var amount = Number(cashbin[i].amount);
                            var subtypeSum = subtype * amount;
                            cashBinhtml += "<li style='padding:0 5px'>" + subtype + "面额: " + amount + "张</li>";
                        }
                        cashBinhtml += "</ul>";
                        $("div.zhibixiang").find("#cashbox").html(cashBinhtml);
                    }
                    
                    
                  //设置卡箱信息
                  if(cardbin){
					  console.log("卡箱：",cardbin.amount)
                	  var cardBinhtml = "卡槽箱：<ul>";
					  var amount = Number(cardbin.amount);
					  cardBinhtml += "<li style='padding:0 5px'> " + amount + "张</li>";
                      cardBinhtml += "</ul>";  
                      $("div.zhibixiang").find("#cardbox").html(cardBinhtml);
                  }
                    
                  $(".zhibixiang").show();
                    
                } else {
                    console.warn("setDeviceBinState error:data.result[" + device_id + "] 不存在", data);
                }
            });
        };

        //功能状态
        var setSoftState = function (functions) {
            var arr = new Array();
            arr.push(device_id);
            $.ajax({
                type: "POST",
                url: "${ctx}/device/selectSoftState",
                data: {"req": JSON.stringify(arr)}
            }).done(function (data) {
                console.log("----------------------------");
                console.log("setSoftState:", data);
                console.log("functions", functions);
                if (data && data.state == true && data.result) {
                    var softState = data.result[device_id];
                    if (softState) {
                        var main = softState.Main;
                        if (main != null && main != "") {
                            if (main.StartupTime) {
                                $("div.head").find(".item.startupTime").html("系统启动时间：" + main.StartupTime);
                            }
                            if (main.UpdateTime) {
                                $("div.head").find(".item.updateTime").html("系统最后修改时间："+main.UpdateTime);
                            }
                            if (main.Logistics_starttime && main.Logistics_endtime) {
                                $("div.head").find(".item.logistics_time").html("物流时间：" + main.Logistics_starttime + " 至  " + main.Logistics_endtime);
                            }

                        } else {
                            console.error("系统启动时间、最后修改时间、物流时间 为空");
                        }
                        /**
                         <div class="one-line">
                         <div class="ul">
                         <div class="li can-unfold" date-id="1">外设状态</div>
                         <div class="li error">外设状态</div>
                         <div class="li can-unfold" date-id="11">外设状态</div>
                         <div class="li">外设状态</div>
                         </div>
                         </div>
                         **/
                        /**
                         <div class="one-line">
                         <div class="ul">
                         <div class="li can-unfold" date-id="1">外设状态</div>
                         <div class="li error">外设状态</div>
                         <div class="li can-unfold" date-id="11">外设状态</div>
                         <div class="li">外设状态</div>
                         </div>
                         </div>
                         <div class="border-wrap" data-id="1">
                         <div class="status-box">
                         <span class="t">PLC状态</span>
                         <div class="ul">
                         <div class="li">PLC状态</div>
                         <div class="li error">PLC状态</div>
                         <div class="li">PLC状态</div>
                         <div class="li">PLC状态</div>
                         <div class="li">PLC状态</div>
                         </div>
                         </div>
                         </div>
                         **/

                            //$lastLine=$(document).find(".padding-wrap").find(".one-line:last");
                            //$lastLine.find(".ul").append('<div class="li can-unfold" date-id="11">功能状态</div>');
                            //var border_wrap='<div class="border-wrap" data-id="11"><div class="status-box">'+'<span class="t">功能状态</span>'+'<div class="ul">';
                            //$(".status-box.super-layer.function_state").find(".padding-wrap");
                        var border_wrap = '<div class="one-line"><div class="ul">';
                        var funcs = softState["Function"] || {};
                        console.log("funcs", funcs);
                        var functionNames = functions || {};
                        for(var opercmd in funcs){
                            var operName = functionNames[opercmd] || opercmd;
                            var funcState = funcs[opercmd];
                            if("0" == funcState){
                                border_wrap += '<div class="li">' + operName + '</div>';
                            }else{
                                border_wrap += '<div class="li error">' + operName + '</div>';
                            }
                        }
                        //留着防止叕叕叕叕叕叕叕叕叕叕叕叕叕叕改变需求
//                        for (var opercmd in functions) {
//                            var oper = opercmd;
//                            if (functions) {
//                                var cmdName = functions[oper] || "NONE";
//                                if (cmdName == "NONE") {
//                                    continue;
//                                }
//                                if (oper.indexOf("state") != -1) {
//                                    var funcState = funcs[oper] || "0";
//                                    border_wrap += '<div class="li">' + cmdName + ' [' + funcState + ']</div>';
//                                } else {
//                                    if (oper.substring(0, 1) != "0") {
//                                        oper = "0" + oper;// 海恒中间件数据库opercmd为主键，前面不能加0。这里需要补上
//                                    }
//                                    var funcState = funcs[oper] || "0";
//                                    if (funcState == "0") {//normal
//                                        border_wrap += '<div class="li">' + cmdName + '</div>';
//                                    } else if (funcState == "1") {//alert
//                                        border_wrap += '<div class="li error">' + cmdName + '</div>';
//                                    } else if (funcState == "2") {//error
//                                        border_wrap += '<div class="li error">' + cmdName + '</div>';
//                                    }
//                                }
//                                delete functions[oper];
//                            } else {
//                                if (funcState == "0") {//normal
//                                    border_wrap += '<div class="li">' + oper + '</div>';
//                                } else if (funcState == "1") {//alert
//                                    border_wrap += '<div class="li error">' + oper + '</div>';
//                                } else if (funcState == "2") {//error
//                                    border_wrap += '<div class="li error">' + oper + '</div>';
//                                }
//                            }
//                        }
//                        var greyHtml = "";
//                        if (functions) {
//                            console.log("functions leave", functions);
//                            for (var c in functions) {
//                                greyHtml += '<div class="li grey">' + functions[c] + '</div>';
//                            }
//                        }
//                        border_wrap += greyHtml;
                        border_wrap += '</div></div>';

                        //$(document).find(".padding-wrap").append(border_wrap);
                        $(".status-box.super-layer.function_state").find(".padding-wrap").append(border_wrap);
                    }
//                    else {//没有查询到的话mongo数据的话
//                        var greyHtml = '<div class="one-line"><div class="ul">';
//                        if (functions) {
//                            console.log("functions leave", functions);
//                            for (var c in functions) {
//                                greyHtml += '<div class="li grey">' + functions[c] + '</div>';
//                            }
//                        }
//                        greyHtml += '</div></div>';
//                        $(".status-box.super-layer.function_state").find(".padding-wrap").append(greyHtml);
//                    }
                }
            });
        };
        /**
         获取设备功能中文名SelPermissionByDeviceId
         **/
        var functions = new Object();
        //var operatorQueryParams={"operator_id":device_id,"library_id":library_id};//这里要把 library_id给去掉
        var operatorQueryParams = {"device_idx": device_idx};
        var setSoftStateProxy = function (functions, operatorQueryParams) {//db层 SelUserCmdsByIdxAndRestriDevice函数
            $.ajax({
                //url:"${ctx}/device/SelPermissionByDeviceId",
                url: "${ctx}/metadataOperCmd/getMetadataOperCmd",
                type: "GET",
                cache:true
                //,data: {"req": JSON.stringify(operatorQueryParams)}
            }).done(function (data) {
                console.log("SelFunctionByDeviceIdx", data);
                if (data && data.state) {
                    var result = data.result;
                    if (result) {
                        for (var index in result) {
                            var opercmd = result[index].opercmd;
                            if (opercmd) {
                                functions[opercmd] = result[index].opercmd_desc;
                            }
                        }

                    }
                }
                console.log("functions", functions);
                /* if(data&&data.result){
                    var result=data.result;
                    for(var i=0;i<result.length;i++){
                        var opercmd=result[i].opercmd;
                        var opercmd_desc=result[i].opercmd_desc;
                        if(!functions[opercmd]){
                             functions[opercmd]=opercmd_desc;
                        }
                    }
                }else{
                    console.error("setSoftStateProxy error",data);
                } */
            }).done(function () {
                setSoftState(functions);//设置功能状态
            });
        };
        var extState;
        //设置设备外设PLC状态
        var setDeviceExtState = function () {
            var arr = new Array();
            arr.push(device_id);
            $.ajax({
                type: "POST",
                url: "${ctx}/device/selectDeviceExtState",//clouddbservice_devicemonitor
                data: {"req": JSON.stringify(arr)}
            }).done(function (data) {
                console.log("device_id", device_id);
                console.log("selectDeviceExtState", data);
                if (data && data.state == true && data.result[device_id]) {
                    extState = data.result[device_id];
                    console.log("extState", extState);//具体的mongodb外设数据
                    if (extState.createTime)
                        delete extState.createTime;//清除创建时间
                    /**
                     <div class="one-line">
                     <div class="ul">
                     <div class="li can-unfold" date-id="1">外设状态</div>
                     <div class="li error">外设状态</div>
                     <div class="li can-unfold" date-id="11">外设状态</div>
                     <div class="li">外设状态</div>
                     </div>
                     </div>
                     **/
                    
                    $.ajax({
            			url:"${ctx}/device/queryDevStatusCode",
            			type:"POST",
            			data : {
            				//"req" : devicetype_idx.join(",")
            			},
            			success:function(data){
            				console.info(data);
            				debugger;
            				//alert("cccc");
            				if(data.state){
            					var devStatusCode = data.result;
            					var extHtml = '<div class="one-line"><div class="ul"><div id="tipDiv" hidden></div>';
                                /**
                                 plc_state
                                 **/
                                var plcHtml = '<div class="border-wrap" data-id="1">' +
                                    '<div class="status-box">' +
                                    '<span class="t">PLC状态</span>' +
                                    '<div class="ul">';

                                var num = 0;//ext 外设状态个数
                                //'undefined' 'string' 'number' 'boolean'  'function'  'object'
                                //AuthorityReader /CardDispenser /CashAcceptor /IdentityReader /ItemLoadReader....
                                //(在架书 .book-info .item1 书盒 /item2   预借书 item3/sec1 卡证箱 sec2   纸币箱  .zhibixiang)
                                console.debug("extStateNames",extStateNames);
                                for (var ext in extState) {
                                    num++;
                                    var extObj = extState[ext];//ext 硬件逻辑名. extObj 状态 0 1 2

                                    if (typeof extObj == "string") {
                                        console.debug(ext+"->"+extObj);
                                        var lowerExt = ext.toLowerCase();
                                        var extName = extStateNames[lowerExt] || "NONE";
                                        if (extName == "NONE") {
                                            extName = ext;
                                        }

                                        if (!isDevice(DEVICE_TYPE, 'dlv') && lowerExt == "cashacceptor") {//收钞机
                                            //$(".book-info").show();
                                            $(".zhibixiang").show();
                                        } else if (!isDevice(DEVICE_TYPE, 'dlv') && lowerExt == "carddispenser") {//发卡机
                                            //$(".book-info").show();
//                                                $(".book-info .item1").hide();
//                                                $(".book-info .item2").hide();
//                                                $(".book-info .item3 .sec1").hide();
                                        }
                                        if (typeof extName == 'string' && extName.indexOf("disable|") >= 0) {
                                            extHtml += '<div class="li">' + extName.split("|")[1] + '（失效）</div>';
                                        } else {
                                        	var ext_model_info = "";
                                        	for(var index in devStatusCode){
                        						if(extObj == devStatusCode[index].ext_model_code){
                        							extObj = devStatusCode[index].ext_code_type;
                        							ext_model_info = devStatusCode[index].ext_model_info;
                        							break;
                        			 			}
                        					}
                                            extName = typeof extName == 'object' ? ext : extName;
                                            if (extObj == 0) {
                                            	var textVal = "正常";
                                                extHtml += '<div class="li" onmouseenter="dispDiv(event,this,\''+ext_model_info+'\')" onmouseleave="hideDiv(this)">' + extName + '</div>';
                                            } else if (extObj == 1) {//报警,临时
                                            	extHtml += '<div class="li  error" onmouseenter="dispDiv(event,this,\''+ext_model_info+'\')" onmouseleave="hideDiv(this)">' + extName + '</div>';                  
                                                //extHtml += '<div class="li error">' + extName + '</div>';
                                            } else if (extObj == 2) {//失效
                                            	extHtml += '<div class="li  error" onmouseenter="dispDiv(event,this,\''+ext_model_info+'\')" onmouseleave="hideDiv(this)">' + extName + '</div>';                  
                                                //extHtml += '<div class="li error">' + extName + '</div>';
                                            } else {
                                            	extHtml += '<div class="li grey" onmouseenter="dispDiv(event,this,\'无状态\')" onmouseleave="hideDiv(this)">' + extName + '</div>';                  
                                                //extHtml += '<div class="li grey">' + extName + '</div>';
                                            }
                                        }
                                        delete extStateNames[lowerExt];
                                    } else if (typeof extObj == "object") {//自助借还 plcssl
                                        /**
                                         <div class="one-line">
                                         <div class="ul">
                                         <div class="li can-unfold" date-id="1">外设状态</div>
                                         <div class="li error">外设状态</div>
                                         <div class="li can-unfold" date-id="11">外设状态</div>
                                         <div class="li">外设状态</div>
                                         </div>
                                         </div>
                                         <div class="border-wrap" data-id="1">
                                         <div class="status-box">
                                         <span class="t">PLC状态</span>
                                         <div class="ul">
                                         <div class="li">PLC状态</div>
                                         <div class="li error">PLC状态</div>
                                         <div class="li">PLC状态</div>
                                         <div class="li">PLC状态</div>
                                         <div class="li">PLC状态</div>
                                         </div>
                                         </div>
                                         </div>
                                         **/
                                        if (ext == "PlcSSL") {//固定
                                            //$(".book-info").show();
                                            var obj = extStateNames["plcssl"];
                                            if (!obj) {
                                                continue;
                                            }
                                            var allPlcState = '';//检测plc下属部件是否有错误
                                            for (var extObjName in extObj) {
                                                var plcState = extObj[extObjName];
                                                var plcSSLNames = obj;
                                                //console.log("extObjName",extObjName);

                                                var plcObj = plcSSLNames[extObjName.toLowerCase()];
                                                if (!plcObj) {
                                                    plcObj = extObjName;
                                                }
                                                if (plcObj.indexOf("disable|") >= 0) {
                                                    plcHtml += '<div class="li">' + plcObj.split("|")[1] + '（失效）</div>';
                                                } else {
                                                	var ext_plc_model_info = "";
                                                	debugger;
                                                	
                                                	for(var index in devStatusCode){
                                						if(plcState == devStatusCode[index].ext_model_code){
                                							plcState = devStatusCode[index].ext_code_type;
                                							ext_plc_model_info = devStatusCode[index].ext_model_info;
                                							break;
                                			 			}
                                					}
                                                    if (plcState == 0) {
                                                    	plcHtml += '<div class="li" onmouseenter="dispDiv(event,this,\''+ext_plc_model_info+'\')" onmouseleave="hideDiv(this)">' + plcObj + '</div>';
                                                        //plcHtml += '<div class="li">' + plcObj + '</div>';
                                                    } else if (plcState == 1) {//报警
                                                    	plcHtml += '<div class="li error" onmouseenter="dispDiv(event,this,\''+ext_plc_model_info+'\')" onmouseleave="hideDiv(this)">' + plcObj + '</div>';
                                                        //plcHtml += '<div class="li error">' + plcObj + '</div>';
                                                        allPlcState = 'error';
                                                    } else if (plcState == 2) {//失效
                                                    	plcHtml += '<div class="li error" onmouseenter="dispDiv(event,this,\'无状态\')" onmouseleave="hideDiv(this)">' + plcObj + '</div>';
                                                        //plcHtml += '<div class="li error">' + plcObj + '</div>';
                                                    }
                                                }
                                            }
                                            delete extStateNames["plcssl"];
                                            extHtml += '<div class="li can-unfold '+allPlcState+'" date-id="1">PLC状态</div>';
                                        }
                                    }
                                    //
                                    if (num == 4) {
                                        num = 0;
                                        //等于4的时候换行
                                        extHtml += '</div></div><div class="one-line"><div class="ul">';
                                    }
                                }
                                var greyHtml = "";
                                if (extStateNames) {
                                    console.log("extStateNames leave", extStateNames);
                                    for (var extName in extStateNames) {
                                        var extObj = extStateNames[extName];
                                        if(typeof extObj == "string") {
                                        	greyHtml += '<div class="li grey" onmouseenter="dispDiv(event,this,\'无状态\')" onmouseleave="hideDiv(this)">' + extObj + '</div>';                  
                                            //greyHtml += '<div class="li grey">' + extObj + '</div>';
                                        }
                                    }
                                }
                                extHtml += greyHtml;
                                plcHtml += '</div></div></div>';//plcHtml plc 内容
                                extHtml += '</div>';
                                $(".status-box.super-layer.ext_state").find(".padding-wrap").html(extHtml).append(plcHtml);
                                //收起
                                $(".status-box.super-layer.ext_state").find("div.status-box.plc_state").trigger("click");
                            } else {
                                console.warn("设置设备外设PLC状态出现问题或者暂无状态数据：Data", data);
                                console.log("extStateNames", extStateNames);
                                var extHtml = '<div class="one-line"><div class="ul">';
                                if (extStateNames) {
                                    var num = 0;
                                    for (var name in extStateNames) {
                                        console.log("name", name);
//                                        if (name == "cashacceptor") {//收钞机
//                                            $(".zhibixiang").show();
//                                        }
//                                        else if (name == "carddispenser") {//发卡机
//                                            $(".book-info").show();
//                                            $(".book-info .item1").hide();
//                                            $(".book-info .item2").hide();
//                                            $(".book-info .item3 .sec1").hide();
//                                        } else if (name == "plcssl") {//自助图书馆PLC
//                                            $(".book-info .item1").hide();
//                                            $(".book-info .item2").hide();
//                                            $(".book-info .item3 .sec1").hide();//预借书
//                                        } else if (name == "plcreturn") {//还书机PLC
//                                            $(".book-info").show();
//                                            $(".book-info .item1").hide();
//                                            $(".book-info .item2").show();
//                                            $(".book-info .item3").hide();
//                                        }


                                        if (typeof extStateNames[name] == "object") {
                                            //extHtml+='<div class="li grey">'+name+'</div>'; //pass
                                        } else {
                                        	extHtml += '<div class="li grey"  onmouseenter="dispDiv(event,this,\'无状态\')" onmouseleave="hideDiv(this)">' + extStateNames[name] + '</div>';
                                            //extHtml += '<div class="li grey">' + extStateNames[name] + '</div>';
                                        }
                                        num++;
                                        if (num > 1 && num % 4 == 0) {
                                            //等于4的时候换行
                                            extHtml += '</div></div><div class="one-line"><div class="ul">';
                                        }
                                    }
                                    extHtml += '</div></div>';
                                    $(".status-box.super-layer.ext_state").find(".padding-wrap").html(extHtml);
                                }

            				}
            			}
                    });
                }
            }).done(function () {
                //软状态放在这里
                setSoftStateProxy(functions, operatorQueryParams);
            });

        };

        setBookrackState();

        setDeviceBinState();

        initView(DEVICE_TYPE);

        //setSoftState();加载完获取设备功能中文名后执行
        var lang = "zh_CN";
        var extStateNames = {};//key全部使用使用小写字母
        //获取当前设备的
        (function GetDevExtModel() {
            //获取设备的逻辑外设部件
            var devIdxArr = [device_idx];
            $.ajax({
                url: "${ctx}/device/GetDevExtModel",
                type: "GET",
                data: {"req": JSON.stringify(devIdxArr)}
            }).done(function (data) {
                console.log("GetDevExtModel", data);
                if (data && data.state) {
                    var deviceExtConfigs = data.result.deviceExtConfigs;//外设配置
                    var plcLogicOBJList = data.result.plcLogicOBJList;
                    var deviceMonConfigs = data.result.deviceMonConfigs;//需要监控的外设配置
                    //只有 外设配置 和 需要监控的外设配置 同时存在 则 表示需要显示的外设部件
                    var LogicOBJ = {};
                    console.log("deviceExtConfigs(外设配置)", deviceExtConfigs);
                    console.log("deviceMonConfigs(需要监控的外设配置)", deviceMonConfigs);
                    if (deviceExtConfigs) {
                        for (var i = 0; i < deviceExtConfigs.length; i++) {
                            var logic_obj = deviceExtConfigs[i].logic_obj.toLowerCase();
                            extStateNames[logic_obj] = jsonToObj(deviceExtConfigs[i].logic_obj_desc)[lang];
                        }
                    }
                    if (deviceMonConfigs) {
                        for (var i = 0; i < deviceMonConfigs.length; i++) {
                            var alert = deviceMonConfigs[i].alert;
                            var tableName = deviceMonConfigs[i].table_name;
                            if (alert == "0") {//不监控的
                                if (tableName == "plc_logic_obj") {
                                    LogicOBJ[deviceMonConfigs[i].plc_logic_obj.toLowerCase()] = "disable|" + deviceMonConfigs[i].plc_logic_obj_desc;
                                } else {
                                    var logic_obj = deviceMonConfigs[i].logic_obj.toLowerCase();
                                    extStateNames[logic_obj] = "disable|" + extStateNames[logic_obj];
                                }
                            } else {
                                if (tableName == "plc_logic_obj") {//表示有PLC
                                    LogicOBJ[deviceMonConfigs[i].plc_logic_obj.toLowerCase()] = deviceMonConfigs[i].plc_logic_obj_desc;
                                }
                            }
                        }
                        if (!_.isEmpty(LogicOBJ)) {
                            extStateNames["plcssl"] = LogicOBJ;
                        }
                    }
                    /*if(plcLogicOBJList){
                        for(var i=0;i<plcLogicOBJList.length;i++){
                            LogicOBJ[plcLogicOBJList[i].plc_logic_obj.toLowerCase()]=plcLogicOBJList[i].plc_logic_obj_desc;
                        }
                        extStateNames["plcSSL"]=LogicOBJ;
                    } */

                }
                console.log("extStateNames", extStateNames);//identityreader authorityreader itemrfidreader printer
                setDeviceExtState();
            });
        })();
        <%--字符串转JSON格式--%>

        function jsonToObj(str) {
            try {
                return JSON.parse(str);
            } catch (e) {
                return "";
            }
        }


        // code for fade in element by element
        $.fn.fadeInWithDelay = function () {
            var delay = 0;
            return this.each(function () {
                $(this).delay(delay).animate({
                        opacity: 1
                    },
                    200);
                delay += 100;
            });
        };

        /**
         PLC状态详情
         **/
        $(document).on("click", ".can-unfold", function () {
            var dataId = $(this).attr("date-id");
            //layer.alert("点击事件");
            if ($(this).hasClass("active")) {
                $(this).removeClass("active");
                $(".border-wrap[data-id=" + dataId + "]").hide();
            } else {
                $(".super-layer .can-unfold").removeClass("active");
                $(this).addClass("active");

                $(".border-wrap").hide();
                $(".border-wrap[data-id=" + dataId + "]").show();
            }
        });

        var isSendOrder = false;
        /**
         下载日志 按钮
         **/
        $(".g-btn.download.g-btn-blue").on("click", function () {
            var device_id = $("div.head").find(".name").find(".id").text();
            //step one get the query time
            var startDate = $("input[name=startDate]").val();
            var startTime = $("input[name=startTime]").val();
            var endDate = $("input[name=endDate]").val();
            var endTime = $("input[name=endTime]").val();
            if (!startDate) {
                layer.alert("请填写开始时间");
                return;
            }
            if (!endDate) {
                layer.alert("请填写结束时间");
                return;
            }
            var utcStartTime = new Date($.trim(startDate + " " + startTime)).toUTCString();
            var utcEndTime = new Date($.trim(endDate + " " + endTime)).toUTCString();
            //下载日志
            //在后端加上 operator_idx
            var req = {
                "utcStartTime": utcStartTime,
                "utcEndTime": utcEndTime,
                "device_id": device_id
            };
            $.ajax({
                url: "${ctx}/device/checkDownLoadLog",
                data: {"req": JSON.stringify(req)},
                type: "GET"
            }).done(function (data) {
                console.log(data);
                if (data) {
                    if (data.state == true && !data.message) {
                        console.log("downLoadFilePath");
                        console.log(data.result);
                        //存在数据，可以下载了
                        var url = "${ctx}/device/downloadRunLogOperation";
                        var path = "filePath=" + data.result;
                        download(url, path, "get");
                    } else if (data.message == "DELETE_NEED_SEND_ORDER" || data.message == "NEED_SEND_ORDER") {
                        if ("DELETE_NEED_SEND_ORDER" == data.message) {
                            //切换时间的情况下需要再次发送查询命令
                            isSendOrder = false;
                        }
                        //需要发送指令
                        //获取信息 包括device_id device_idx
                        //3 代表 查询日志
                        if (!isSendOrder) {
                            var params = {
                                //"operator_id":operator.operator_id,
                                "device_id": device_id,
                                "control": "1",
                                "control_info": "3",
                                "start_time": utcStartTime,
                                "end_time": utcEndTime
                            };
                            var arrParams = new Array();
                            arrParams.push(params);
                            console.log(params);
                            $.ajax({
                                type: "POST",
                                url: "${ctx}/device/sendOrder",
                                data: {"req": JSON.stringify(arrParams)},
                                success: function (data) {
                                    console.log(data);
                                    if (data) {
                                        isSendOrder = true;
                                    }
                                }
                            });
                        }
                        if (data.retMessage) {
                            layer.alert(data.retMessage);
                        }
                    } else if (data.retMessage) {
                        layer.alert(data.retMessage);
                    }
                }
            });

        });
        /**
         下载
         **/
        var download = function (url, data, method) {
            // 获得url和data
            if (url && data) {
                // data 是 string 或者 array/object
                data = typeof data == 'string' ? data : jQuery.param(data);        // 把参数组装成 form的  input
                var inputs = '';
                jQuery.each(data.split('&'), function () {
                    var pair = this.split('=');
                    inputs += '<input type="hidden" name="' + pair[0] + '" value="' + pair[1] + '" />';
                });
                // request发送请求
                jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>')
                    .appendTo('body').submit().remove();
            }
            ;
        };

        /*详细页JS*/
        $(".head .btn1").on("click", function () {
            $(this).addClass("active").siblings("li").removeClass("active");
            $(".machine-detail .main").show();
            $(".machine-detail .file-check").hide();
            $(".machine-detail .biz-data").hide();
        });
        $(".head .btn2").on("click", function () {
            $(this).addClass("active").siblings("li").removeClass("active");
            $(".machine-detail .main").hide();
            $(".machine-detail .file-check").show();
            $(".machine-detail .biz-data").hide();
        });
        $(".head .btn3").on("click", function () {
            $(this).addClass("active").siblings("li").removeClass("active");
            $(".machine-detail .main").hide();
            $(".machine-detail .file-check").hide();
            $(".machine-detail .biz-data").show();
            var lib_idx=$("body").find("div.head").find("#library_idx").val();
            //var device_id=$("body").find("div.head").find("#device_id").val();
            var device_type=$("body").find("div.head").find("#device_type").val();
            //截取设备id，去除图书馆
            var firststr=device_id.indexOf('_');
            //var device_id=device_id.substring(firststr+1,device_id.length);
            
            var postData = {"type":"today","library_idx":lib_idx,"device_idx":device_idx,"chart_type":"bar","operresult":"0"};
            var postData1 = {"type":"month","library_idx":lib_idx,"device_idx":device_idx,"chart_type":"bar","operresult":"0"};
            console.info("postData-->",postData);
           	//loadVisitorDataForBar(postData,'chart4');//人流量
            if(device_type.indexOf("SSL")!=-1){//ssl
            	loadLoanDataTodayForBar(postData,'chart1');
            	loadCardissueDataForBar(postData,'chart2');
                loadFinanceDataForBar(postData,'chart3');
		 	}else if(device_type.indexOf("SCH")!=-1){//自助借还（借还）
		 		loadLoanDataTodayForBar(postData,'chart1');
		 		loadLoanDataMonthForBar(postData1,'chart2');
		 	}else if(device_type.indexOf("BDR")!=-1){//还书机（还书统计）
		 		loadLoanDataTodayForBar(postData,'chart1');
		 		loadLoanDataMonthForBar(postData1,'chart2');
		 	}else if(device_type.indexOf("REG")!=-1){//办证机(办证、卡数量)
		 		loadCardissueDataForBar(postData,'chart2');
                loadFinanceDataForBar(postData,'chart3');
		 	}else if(device_type.indexOf("STA")!=-1){
		 		loadLoanDataTodayForBar(postData,'chart1');
            	loadCardissueDataForBar(postData,'chart2');
                loadLoanDataMonthForBar(postData1,'chart3');
                loadCardissueDataForBar(postData1,'chart4');
		 	}
        });
        $(".datepicker").datepicker({
            numberOfMonths: 1,//显示几个月
            showButtonPanel: false,//是否显示按钮面板
            dateFormat: 'yy-mm-dd',//日期格式
            clearText: "清除",//清除日期的按钮名称
            closeText: "关闭",//关闭选择框的按钮名称
            yearSuffix: '', //年的后缀
            showMonthAfterYear: true,//是否把月放在年的后面
            defaultDate: new Date(),//默认日期
            //minDate:'2011-03-05',//最小日期
            //maxDate:'2011-03-20',//最大日期
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
            dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
            onSelect: function (selectedDate) {//选择日期后执行的操作

            }
        });

        $(".timepicker").timepicker({
            controlType: 'select',
            oneLine: true,
            timeFormat: 'HH:mm',
            currentText: "现在",
            closeText: "完成",
            timeOnlyTitle: "",
            //amNames:["AM"],
            //pmNames:["PM"],
            timetext: "时间",
            hourText: "时",
            minuteText: "分",
            secondText: "秒"
        });
        
        
        var loadCardissueDataForBar = function(postData,chartId){
    		$.ajax({
    			url:"${pageContext.request.contextPath}/device/countCardissueLog",
                type:"POST",
    			data: {"req":JSON.stringify(postData)},
    			dataType: 'json',
    			success: function(data){
    				if(data.state){
    					result = data.result;
    					if(result){
    						$("#"+chartId).addClass("chart"); 
				    		var chart = echarts.init(document.getElementById(chartId));
    						initCardissueBar(chart);
        					chart.setOption({        //载入数据
        	                    xAxis: {
        	                        data: result.date    //填入X轴数据
        	                    },
        	                    series: [    //填入系列（内容）数据
        	                      {
        	                           data: result.total,
        	                           barWidth : 10,//柱图宽度
        	                      }
        	                   ]
        	                });
    					}else{
    						$("#"+chartId).addClass("chart"); 
				    		var chart = echarts.init(document.getElementById(chartId));
    						initCardissueBar(chart);
    					}
    				}else{
    					$("#"+chartId).hide();
    				}
    			}
    		});
    	};
    	var loadLoanDataTodayForBar = function(postData,chartId){
    		$.ajax({
    			url:"${pageContext.request.contextPath}/device/countLoanLog",
                type:"POST",
    			data: {"req":JSON.stringify(postData)},
    			dataType: 'json',
    			success: function(data){
    				console.info("todaydata",data);
    				if(data.state){
    					result = data.result;
    					
   						$("#"+chartId).addClass("chart"); 
			    		var chart = echarts.init(document.getElementById(chartId));
   						initLoanBar(chart);
    					if(result){
        					chart.setOption({ 
        						title : {
        							text : '今日借还量',
        							x : 'center',
        							textStyle : {
        								fontSize : 24,
        								fontWeight : 'normal',
        								color : '#3784D9'
        							}
        						},
        						//载入数据
        	                    xAxis: {
        	                        data: result.date    //填入X轴数据
        	                    },
        	                    series: [    //填入系列（内容）数据
        	                       {
        	                           data: result.loan_total,
        	                           barWidth : 10,//柱图宽度
        	                       },
        	                       {
        	                           data: result.return_total,
        	                           barWidth : 10,//柱图宽度
        	                       },
        	                       {
        	                           data: result.renew_total,
        	                           barWidth : 10,//柱图宽度
        	                      }
        	                   ]
        	                });
    					}else{
        					chart.setOption({ 
        						title : {
        							text : '今日借还量',
        							x : 'center',
        							textStyle : {
        								fontSize : 24,
        								fontWeight : 'normal',
        								color : '#3784D9'
        							}
        						}
        	                });
    					}
    				}else{
    					//$("#"+chartId).hide();
    				}
    			}
    		});
    	};
    	var loadLoanDataMonthForBar = function(postData,chartId){
    		$.ajax({
    			url:"${pageContext.request.contextPath}/device/countLoanLog",
                type:"POST",
    			data: {"req":JSON.stringify(postData)},
    			dataType: 'json',
    			success: function(data){
    				console.info("monthdata",data);
    				if(data.state){
    					result = data.result;
    					
   						$("#"+chartId).addClass("chart"); 
			    		var chart = echarts.init(document.getElementById(chartId));
   						initLoanBar(chart);
    					if(result){
        					chart.setOption({      
        						title : {
        							text : '本月借还量',
        							x : 'center',
        							textStyle : {
        								fontSize : 24,
        								fontWeight : 'normal',
        								color : '#3784D9'
        							}
        						},
        						//载入数据
        	                    xAxis: {
        	                        data: result.date    //填入X轴数据
        	                    },
        	                    series: [    //填入系列（内容）数据
        	                       {
        	                           data: result.loan_total,
        	                           barWidth : 10,//柱图宽度
        	                       },
        	                       {
        	                           data: result.return_total,
        	                           barWidth : 10,//柱图宽度
        	                       },
        	                       {
        	                           data: result.renew_total,
        	                           barWidth : 10,//柱图宽度
        	                      }
        	                   ]
        	                });
    					}else{
        					chart.setOption({      
        						title : {
        							text : '本月借还量',
        							x : 'center',
        							textStyle : {
        								fontSize : 24,
        								fontWeight : 'normal',
        								color : '#3784D9'
        							}
        						}
        	                });
    					}
    				}else{
    					//$("#"+chartId).hide();
    				}
    			}
    		});
    	};
    	
        var loadVisitorDataForBar = function(postData,chartId){
    		$.ajax({
    			url:"${pageContext.request.contextPath}/device/countVisitorLog",
                type:"POST",
    			data: {"req":JSON.stringify(postData)},
    			dataType: 'json',
    			success: function(data){
    				if(data.state){
    					result = data.result;
    					if(result){
    						$("#"+chartId).addClass("chart"); 
				    		var chart = echarts.init(document.getElementById(chartId));
    						initVisitorBar(chart);
        					chart.setOption({        //载入数据
        	                    xAxis: {
        	                        data: result.date    //填入X轴数据
        	                    },
        	                    series: [    //填入系列（内容）数据
        	                       {
        	                           data: result.in_total,
        	                           barWidth : 10,//柱图宽度
        	                       },
        	                       {
        	                           data: result.out_total,
        	                           barWidth : 10,//柱图宽度
        	                       }
        	                   ]
        	                });
    					}else{
    						$("#"+chartId).addClass("chart"); 
				    		var chart = echarts.init(document.getElementById(chartId));
    						initVisitorBar(chart);
    					}
    				}else{
    					$("#"+chartId).hide();
    				}
    			}
    		});
    	};
    	
    	var loadFinanceDataForBar = function(postData,chartId){
    		$.ajax({
    			url:"${pageContext.request.contextPath}/device/countFinanceLog",
                type:"POST",
    			data: {"req":JSON.stringify(postData)},
    			dataType: 'json',
    			success: function(data){
    				if(data.state){
    					result = data.result;
    					if(result){
    						$("#"+chartId).addClass("chart"); 
				    		var chart = echarts.init(document.getElementById(chartId));
    						initFinanceBar(chart);
    						chart.setOption({        //载入数据
        	                    xAxis: {
        	                        data: result.date    //填入X轴数据
        	                    },
        	                    series: [    //填入系列（内容）数据
        	                      {
        	                           data: result.total,
        	                           barWidth : 10,//柱图宽度
        	                      }
        	                   ]
        	                });
    					}else{
    						$("#"+chartId).addClass("chart"); 
				    		var chart = echarts.init(document.getElementById(chartId));
    						initFinanceBar(chart);
    					}
    				}else{
    					$("#"+chartId).hide();
    				}
    			}
    		});
    	};

    });

    function initView(deviceType) {
        if(isDevice(deviceType,'dlv')){
            $("div.main").find(".item.item1").hide();
            $("div.main").find(".item.item2").hide();
            $("div.main").find(".item.item3").hide();
            $("div.main").find(".item.item4").show();
            $(".zhibixiang").hide();
        }else{
            $("div.main").find(".item.item1").show();
            $("div.main").find(".item.item2").show();
            $("div.main").find(".item.item3").show();
        }
    }

    function isDevice(deviceType,type) {
        return deviceType.indexOf(type) != -1
    }
</script>
</body>
</html>