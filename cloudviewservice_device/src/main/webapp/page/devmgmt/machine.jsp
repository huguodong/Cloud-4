<%@page import="com.ssitcloud.view.common.util.JsonUtils"%>
<%@page import="com.ssitcloud.view.devmgmt.entity.DeviceEntity"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<%@ include file="../common/global.jsf" %>
</head>
<body>
<div class="g-loading"></div>
<%@include file="../common/header.jsf" %>
<div class="page-title-bar">
	<span class="title">监控机器<a href="help-page.html" target="_blank" class="g-help"></a></span>
	<div class="choose-lib">
		<div class="select-btn">
			选择图书馆
			<span class="icon"></span>
		</div>
		<div class="drop-down">
			<div class="item place">盐田图书馆</div>
			<div class="item room">沙头角阅览室</div>
			<div class="item room">沙头角阅览室</div>
			<div class="item room">沙头角阅览室</div>
			<div class="item room">沙头角阅览室</div>
		</div>
	</div>
	<div class="form-wrap fr">
		<span class="t fl">维护</span>
		<div class="g-select">
			<select id="machineCmd">
					<option value="-1" selected>选择指令</option>
					<c:forEach items="${metaorders}" var="order">
						<option value="${order.order_idx}">${order.order_desc}</option>
					</c:forEach>
			</select>
			<span class="arr-icon"></span>
		</div>
		<div id="sendbtn" class="btn send">发送</div>
		<div class="g-select">
				<select id="machineType">
				<!-- 	<option value="-1" selected>选择类型</option>
					<option value="">类型1</option> -->
				</select>
			<span class="arr-icon"></span>
		</div>
		<div class="g-select">
			<select id="machineState">
					<option value="-1" selected>选择状态</option>
					<option value="0">正常</option>
					<option value="1">报警</option>
					<!-- <option value="2">失效</option> -->
			</select>
			<span class="arr-icon"></span>
		</div>
		<input type="text" name="keyWord" id="" class="input g-input" placeholder="输入关键词" />
		<div href="" class="btn search">查询</div>
	</div>
</div>
<div class="main">
	<div class="machine-list">
		<div class="item-wrap">
			<%--
			 <div class="item active">
				<div class="sec1">
					<div class="g-checkbox"><input type="checkbox" name="" id=""></div>
					<span class="machine-name">酒仙桥九隆百货</span>
				</div>
				<div class="sec2">
					<ul>
						<li>在架书：<span class="right">394册</span></li>
						<li>卡数量：<span class="right">39张</span></li>
						<li>在架书：<span class="right">14个/81册</span></li>
					</ul>
				</div>
				<div class="sec3">
					ID：0006 <span class="status">正常</span>
					<div class="check-detail">查看详情</div>
				</div>
			</div> 
			--%>
		
		</div>
	</div>
</div>
	<div class="paging-bar">
		<div class="left">
			<span class="t fl">选择</span>
			<div class="btn g-chooseAll machine">全选</div>
			<div class="btn g-noChooseAll machine">反选</div>
			<span class="t2 fl">刷新</span>
			<div class="g-select refresh">
				<select>
					<option value="30000" selected>30秒</option>
					<option value="60000">60秒</option>
					<!-- <option value="5000">5秒</option> -->
				</select>
				<span class="arr-icon"></span>
			</div>
		</div>
		<div id="page" class="right">
		</div>
	</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/devpagination.js"></script>
<script type="text/javascript">
$(function(){
	/**加载页面**/
	$itemWrap=$("div.main").find(".item-wrap");
	/**
		<div class="item active"> error
				<div class="sec1">
					<div class="g-checkbox"><input type="checkbox" name="" id=""></div>
					<span class="machine-name">酒仙桥九隆百货</span>
				</div>
				<div class="sec2">
					<ul>
						<li>在架书：<span class="right">394册</span></li>
						<li>卡数量：<span class="right">39张</span></li>
						<li>在架书：<span class="right">14个/81册</span></li>
					</ul>
				</div>
				<div class="sec3">
					ID：0006 <span class="status">正常</span>
					<div class="check-detail">查看详情</div>
				</div>
		</div>
	**/
	//机器状态使用颜色进行标注（如红色、灰色、黄色、绿色  等）
	//library_idx 换成lib_id
	var drawRow=function(rows){
		if(!rows)return;
		$itemWrap.html("");
		for(var i=0;i<rows.length;i++){
			var device=rows[i];
			var item='<div class="item">'
			 	+'<input type="text" name="_device_id" class="_device_id" hidden="hidden" value="'+device.device_id+'"/>'
			 	+'<input type="text" name="_device_idx" class="_device_idx" hidden="hidden" value="'+device.device_idx+'"/>'
			 	+'<div class="sec1">'
			 	+'<div class="g-checkbox"><input type="checkbox" name="'+device.device_id+'" id=""></div>'
			 	+'<span class="machine-name">'+device.device_name+'</span>'
			 	+'</div>'
			 	+'<div class="sec2">'
			 	+'<ul class="device_state">'
			 	+'	<li>在架书：<span class="right level1">暂无数据</span></li>'
			 	+'	<li>卡数量：<span class="right level2">暂无数据</span></li>'
			 	+'	<li>还书箱：<span class="right level3">暂无数据</span></li>'
			 	+'</ul>'
			 	+'</div>'
			 	+'<div class="sec3">'
			 	+'ID:'+device.device_id+'<span class="status">正常</span>'
			 	+'	<div class="check-detail">查看详情</div>'
			 	+'</div>'
			 	+'</div>';
				$itemWrap.append(item);
		};
	};
	
	
	//获取设备状态,定时执行时间timeRefresh
	var setStatus=function(){
		var arr=new Array();
		$(document).find("input._device_id").each(function (index,domEle){
			var device_id=$(domEle).val();
			arr.push(device_id);
		});
		//console.log("dev_arr:"+JSON.stringify(arr));
		 $.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/device/selectDeviceState",
			data:{"req":JSON.stringify(arr)}
		}).done(function(data){
			if(data&&data.state==true){
				var device=data.result;// {device_id:state}
				for(device_id in device){
					$thisDev=$(document).find("input[name=_device_id][value="+device_id+"]");
					var state=device[device_id];
					if(state=="0"){//正常状态
						$thisDev.parent().removeClass("error").removeClass("alert");
						$thisDev.parent().find(".sec3").find("span.status").html("正常");
					}
					//报警颜色 如红色、灰色、黄色、绿色  等）
					if(state=="1"){//报警
						$thisDev.parent().removeClass("error").addClass("alert");
						$thisDev.parent().find(".sec3").find("span.status").html("报警");
					}
					if(state=="2"){//失效
						$thisDev.parent().removeClass("alert").addClass("error");
						$thisDev.parent().find(".sec3").find("span.status").html("失效");
					}
				}
			}
			//console.log("data:"+JSON.stringify(data));
	});
	};
	/**
	根据library_idx得到 Library数据
	**/
	//设置图书馆名称函数
	var setLibraryName=function(library_idx){
		if(library_idx){
			var library={"library_idx":library_idx};
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/device/selLibraryByIdxOrId",
				data:{"req":JSON.stringify(library)},
			}).done(function(data){
				if(data&&data.state==true){
					var library=data.result;
					if(library){
						$("div.page-title-bar").find(".title").find(".lib_name").html(library.lib_name);
					}else{
						//library表中没有查到数据
						$("div.page-title-bar").find(".title").find(".lib_name").html("暂无数据");
					}
				}
			});
		}
	};
	
	//设置书架信息 参数为：device_id数组
	var setBookrackState=function(){
		var arr=new Array();
		$(document).find("input._device_id").each(function (index,domEle){
			var device_id=$(domEle).val();
			arr.push(device_id);
		});
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/device/selectBookrackState",
			data:{"req":JSON.stringify(arr)}	
		}).done(function(data){
			if(data&&data.state==true){
				var result=data.result;
				for(var device_id in result){
					$thisDev=$(document).find("input[name=_device_id][value="+device_id+"]");
					var bookrack=result[device_id];
					var level1=bookrack.level1;
					var level2=bookrack.level2;
					var level3=bookrack.level3;
					//var precheckout=result.precheckout;
					var exbox=bookrack.exbox;
					var level1Books= Number(level1.split("/")[1]);
					var level2Books= Number(level2.split("/")[1]);
					var level3Books= Number(level3.split("/")[1]);
					var exboxBooks= Number(exbox.split("/")[1]);
					//在架书
					var onRackBooks=level1Books+level2Books+level3Books+exboxBooks;			
					$thisDev.parent().find("div.sec2").find(".right.level1").html(onRackBooks+"册");
				}
			}
		});
	};
	
	
	//设置箱子信息
	var setDeviceBinState=function(){
		var arr=new Array();
		$(document).find("input._device_id").each(function (index,domEle){
			var device_id=$(domEle).val();
			arr.push(device_id);
		});
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/device/selectBinState",
			data:{"req":JSON.stringify(arr)}
		}).done(function(data){
			if(data&&data.state==true){
				//console.log("箱子状态："+JSON.stringify(data));
				var result=data.result;
				//var cardbin=result.cardbin;
				//var cashbin=result.cashbin;//钱箱
				//var bookbin=result.bookbin;//书箱
				for(var device_id in result){
					$thisDev=$(document).find("input[name=_device_id][value="+device_id+"]");
					var binState=result[device_id];
					//console.log(binState);
					var cardbin=binState.cardbin;//卡箱
					var cashbin=binState.cashbin;//钱箱
					var bookbin=binState.bookbin;//书箱
					$thisDev.parent().find("div.sec2").find(".right.level2").html(cardbin.amount+"张");
					var sum=0;
					for(var i=0;i<bookbin.length;i++){
						var amount=Number(bookbin[i].amount);
						sum+=amount;
					}
					$thisDev.parent().find("div.sec2").find(".right.level3").html(bookbin.length+"个/"+sum+"册");
				}
			}
		});
	};
	
	//组装 翻页和查询 参数
	var makeQueryParam=function(page,pageSize){
	 	$inputText=$("div.page-title-bar").find("input[name=keyWord]");
	    var machineType=$("select#machineType").val();
		var machineState=$("select#machineState").val();
		var keyWord=$inputText.val();
		var Page ={
			"page":page,
			"pageSize":pageSize,
			"machineType":machineType,
			"machineState":machineState,
			"keyWord":keyWord
		};
		//alert(keyWord);
		return Page;
	};
	
	var _interval;
	var pageSize=12;
	
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $("li.active").text();//当前页
		page = Number(currentpage) + 1;//下一页
		var Page=makeQueryParam(page, pageSize);
		//调用页面的查询ajax
		selectPage(Page);
	});
	$("#page").on("click",".prev-page",function(){
		var currentpage = $("li.active").text();
		var page=Number(currentpage)-1;
		var Page=makeQueryParam(page, pageSize);
		//带参数
		selectPage(Page);
	});
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))return;
		var page = $(this).html();
		
		if(page=="...") return;	
		var Page=makeQueryParam(page, pageSize);
		selectPage(Page);
	});
	
	//翻页函数 包括查询参数  selectPage
	var selectPage=function(obj){//
		$.ajax({
			url:"${pageContext.request.contextPath}/device/queryDeviceByParam",
			type:"POST",
			data:{"req":JSON.stringify(obj)}
		}).done(function(data){
			if(data){
				var page=data.result;
				//console.log(page);
				if(page.rows){
					drawRow(page.rows);
					if(page.rows[0]){
						//setLibraryName(page.rows[0].library_idx);
					}
					$.diviceMainPagination(page);
				}
			}
		}).done(function(){
			//翻页 或者 加载之后先执行一次 setStatus
			setStatus();
			//每次选择页码结束，执行获取设备状态函数
			var seconds=$(".g-select.refresh select").val();
			//_interval=setInterval(setStatus,seconds);//周期 暂时注释掉 2016年5月10日08:47:33
			setDeviceBinState();//设置箱子信息
			setBookrackState();//设置在架书
		});
  };	
	var pageObj ={"page":1,"pageSize":12};
	selectPage(pageObj);
	
	//SelectType获取设备类型
	$.ajax({
		url:"${pageContext.request.contextPath}/metadevicetype/selAllDeviceTypeGroupByType",
		data:{},
		type:"GET",
		success:function(data){
			if(data&&data.state==true){
				var deviceTypeArr=data.result;
				$("select#machineType").html('<option value="" selected>选择类型</option>');
				for(var i=0;i<deviceTypeArr.length;i++){
					var deviceTypeObj=deviceTypeArr[i];
					var device_type=deviceTypeObj.device_type;
					var opt='<option value="'+device_type+'">'+device_type+'</option>';
					$("select#machineType").append(opt);
				}
			}
		}
	});
	
	$deivce_ids=$("input._device_id");
	//console.log($deivce_ids);
	function sleep(d){
  		for(var t = Date.now();Date.now() - t <= d;);
	}
	//循环调用 获取 执行结果 params =[{device_id:"",control:"",control_info:""},{device_id}]
	var getControlResult=function(params){
			//var deviceInfo={"device_id":device_id};
				$.ajax({
					url:"${pageContext.request.contextPath}/device/queryControlResult",
					type:"POST",
					data:{"req":JSON.stringify(params)}
				}).done(function(data){
					//console.log("getControlResult:"+JSON.stringify(data));
					if(data&&data.state==true&&data.message=="CURRENT_NO_RESULT"){
						// 暂时没有结果时继续AJAX
						//console.log("setTimeout getControlResult");	
						sleep(5000);
						getControlResult(params);
						//setTimeout(getControlResult(params),3000);
					}
					if(data&&data.state&&data.message=="HAS_RESULT"){
						var result=data.result;
						//console.log("HAS_RESULT:"+JSON.stringify(result));
						if(result){
							for(var i=0;i<result.length;i++){
								var controlRes=result[i];
								var device_id=controlRes.device_id;
								if(controlRes.controlresult=="1"){//关机
									alert(device_id+" 关机成功");
								}else if(controlRes.controlresult=="2"){//重启
									alert(device_id+" 重启成功");
								}else if(controlRes.controlresult=="4"){//锁屏
									alert(device_id+" 锁屏成功");
								}else if(controlRes.controlresult=="5"){//取消操作
									alert(device_id+" 取消操作成功");
								}
							}
						}
						return;
					}
				}).fail(function(e){
					alert(e);
					return;
				});
	};
	/**
	*	发送机器执行命令	
	**/
	$("#sendbtn").on("click",function(){
		layer.alert("发送命令操作");
		$cmd=$("#machineCmd");//order_idx
		var cmd=$cmd.val();
		//获取所有选择的checkbox
		var curCheckboxs = $(".item.active").find(".g-checkbox").find("input:checked");
		var params=new Array();
		if(curCheckboxs.length<1) {
			alert("请选择至少一台设备");
			return;
		}
		if(cmd<0){
			alert("请选择指令后再发送");
			return;
		}
		//var operator = <shiro:principal/>;
		//获取ID
		for(var i=0;i<curCheckboxs.length;i++){
		    var dev=curCheckboxs[i];
		    var dev_control={
		    "device_id":dev.name,
		    "control":"1",
		    "control_info":cmd
		    //"operator_id":operator.operator_id
		    };
		    params.push(dev_control);
		}
		 $.ajax({
			url:"${pageContext.request.contextPath}/device/sendOrder",
			type:"POST",
			data:{"req":JSON.stringify(params)},
		}).done(function(data){
			if(data.state==true&&data.message!="isAlreadyHasSameOrders"){
				alert("正在等待设备执行命令");
				
			}else if(data.state==true&&data.message=="isAlreadyHasSameOrders"){
				var dev_ids=data.result;
				alert("正在等待设备执行命令，其中:\n"+dev_ids+" \n设备命令已经发送过了，正在等待在执行");
			}
			//console.log(data);
		}).done(function(){
			getControlResult(params);
			//这里执行轮询获取执行结果controlResult
		});
	});




	$(document).on("change",":checkbox",function(){
		var $parentItem = $(this).parents(".item");
		if($(this).is(":checked")){
			$parentItem.addClass("active");
		}else{
			$parentItem.removeClass("active");
		}
	});

	(function checkAllCheckbox(){
		$(document).find(".item.active").find(".g-checkbox").addClass("on").find("input").prop("checked",true);
	})();

	var winH = $(window).height();
	var dialogH = winH-20;
	/**
		详细页面
	**/
	$(document).on("click",".check-detail",function(){
		// device
		//获取到IDX
		$item=$(this).parent().parent();
		var device_idx=$item.find("._device_idx").val();
		var device_name=$item.find(".sec1").find(".machine-name").text();
		var device_id=$item.find("._device_id").val();
		//卡数量
		var cardNum=$item.find("div.sec2").find(".right.level2").text().replace(/[^0-9]/ig,""); ;
		var url="${pageContext.request.contextPath}/device/deviceDetail?device_idx="+device_idx+"&device_id="+device_id+"&cardNum="+cardNum;
		layer.open({
			type: 2, 
			content: [url],
			title :false,
			closeBtn :1,
			offset :["20px"],
			area :["860px",dialogH+"px"],
			shade:0.5,
			shadeClose :false,
			scrollbar :false,
			move:false,
			skin:false,
			success :function(layero, index){
				var body = layer.getChildFrame('body', index);
				 // var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				 // console.log(body.html()) //得到iframe页的body内容
			 	body.find("div.head").find(".name").prepend('<span>'+device_name+'</span>');
			    body.find("div.head").prepend('<input name="dev_id" hidden="hidden" class="dev_id" value='+device_id+'></input>');
			}
		}); 
	});

	$(".choose-lib .select-btn").on("click",function(){
		$(this).next(".drop-down").toggle();
	});
	$(".choose-lib .drop-down .item").on("click",function(){
		$(this).parents(".drop-down").hide();
	});

	$(".play-btn,.pause-btn").on("click",function(){
		$(this).hide().siblings().show();
	});
	
	$(document).on("click",".machine-name",function(){
		var curCheckbox = $(this).siblings(".g-checkbox").find("input");

		if(curCheckbox.is(":checked")){
			curCheckbox.prop("checked",false);
		}else{
			curCheckbox.prop("checked",true);
		}

		var $parentItem = $(this).parents(".item");
		if(curCheckbox.is(":checked")){
			$parentItem.addClass("active").find(".g-checkbox").addClass("on");
		}else{
			$parentItem.removeClass("active").find(".g-checkbox").removeClass("on");
		}
	});

	
	//改变刷新时间
	$(document).on("change",".g-select.refresh select",function(){
		var seconds=$(this).val();//获取刷新周期
		//更改周期
		clearInterval(_interval);
		_interval=setInterval(setStatus, seconds);
	});
	//
	//点击查询按钮，查询
	//
	$(document).on("click",".btn.search",function(){
		 var currentpage = $("li.active").text();
		 if(!currentpage){
			 currentpage=1;
		 }
		 //查询条件 设备类型 设备状态 关键字（device_name）
		 var Page=makeQueryParam(currentpage, pageSize);
		 selectPage(Page);
	});
	
});
</script>
</body>
</html>





