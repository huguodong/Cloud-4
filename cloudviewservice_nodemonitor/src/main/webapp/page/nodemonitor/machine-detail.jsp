<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
	<%@include file="../common/global.jsf" %>
	<style type="text/css">
		.machine-detail .zhibixiang .t-box ul li{
			width: auto;
		}
		.g-inputtime .error-msg{
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
	</style>
</head>
<body style="background:#FFF;min-width: 500px">
<div class="machine-detail">
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
			</ul>
		</div>
	</div>
	<div class="main">
		<div class="book-info">
			<div class="float-wrap">
				<div class="item item1">
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
				<div class="item item2">
					<div class="sec1">
						<span class="t">空书盒：</span>
						<span class="num">-个</span>
					</div>
					<div class="sec2">
						<ul>
							<li>一层：<span class="right level1">-册</span></li>
							<li>二层：<span class="right level1">-册</span></li>
							<li>三层：<span class="right level3">-册</span></li>
							<li>特型书盒：<span class="right exbox">-册</span></li>
						</ul>
					</div>
				</div>
				<div class="item item3">
					<div class="sec1">
						<span class="t">预借书：</span>
						<span class="num">-册</span>
					</div>
					<div class="sec2">
						<div class="right">
							<span class="t">卡证箱：</span>
							剩余<span class="num">${cardNum}&nbsp;张</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="zhibixiang">
			<div class="t-box">
				纸币箱：
				<!-- <ul>
					<li>M00：0元</li>
				</ul> -->
			</div>
			<ul class="box-list">
				<!--
				 <li>
					<div class="name">B01箱</div>
					<span>4册</span><span>其他</span>
				 </li>
				-->
			</ul>
		</div>
		<div class="status-box super-layer">
			<span class="t">外设状态</span>
			<div class="line"></div>
			<div class="padding-wrap">
			<%-- 	<div class="one-line">
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
		
	</div>
	<div class="file-check">
		<div class="handle-bar">
			<div class="item ">
				<div class="word">开始时间</div>
				<div class="g-inputdate"><input name="startDate" class="g-input datepicker" type="text" placeholder="选择日期"><span class="icon"></span></div>
				<div class="g-inputtime"><input name="startTime" class="g-input timepicker" type="text" placeholder="选择时间"><span class="icon"></span>
					<span class="error-msg">请填写开始时间</span>
				</div>
			</div>
			<div class="item">
				<div class="word">结束时间</div>
				<div class="g-inputdate"><input name="endDate" class="g-input datepicker" type="text" placeholder="选择日期"><span class="icon"></span></div>
				
				<div class="g-inputtime"><input name="endTime" class="g-input timepicker" type="text" placeholder="选择时间"><span class="icon"></span>
					<span class="error-msg">请填写结束时间</span>
				</div>
			</div>
			
			<div class="word"></div>
			<a href="${pageContext.request.contextPath}/device/checklogonline" target="_blank" class="g-btn search g-btn-blue">在线查询</a>
			<div class="g-btn download g-btn-blue">下载日志</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	//设备ID  实际上是 library_id _ device_id
	var device_id=$("#libId_deviceId").val();
	var library_id="${library_id}";
	var device_idx="${device.device_idx}";
	$(".g-btn.search.g-btn-blue").attr("href","${pageContext.request.contextPath}/device/checklogonline?device_id="+device_id);

	$("div.status-box.plc_state").click(function(e) {
	    //$(this).slideUp("fast");
	    //slideToggle target content and adjust bottom border if necessary
	    $(this).find("ul").slideToggle("fast",function() {
	        //$(".accordion :visible:last").css("border-radius","0 0 10px 10px");
	    });
	 });
	
	//设置书架信息 参数为：device_id数组
	var setBookrackState=function(){
		device_id=$.trim(device_id);
		var arr=new Array();
		arr.push(device_id);
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/device/selectBookrackState",
			data:{"req":JSON.stringify(arr)}	
		}).done(function(data){
			if(data&&data.state==true&&data.result[device_id]){
				console.log(data);
				var result=data.result[device_id];
				var level1=result.level1;
				var level2=result.level2;
				var level3=result.level3;
				var precheckout=result.precheckout;
				var exbox=result.exbox;
				
				var level1Books= Number(level1.split("/")[1]);
				var level2Books= Number(level2.split("/")[1]);
				var level3Books= Number(level3.split("/")[1]);
				var exboxBooks= Number(exbox.split("/")[1]);
				
				var level1Empty=Number(level1.split("/")[0])-level1Books;
				var level2Empty=Number(level2.split("/")[0])-level2Books;
				var level3Empty=Number(level3.split("/")[0])-level3Books;
				var exboxEmpty=Number(exbox.split("/")[0])-exboxBooks;
				
				$item1sec2=$("div.main").find(".item.item1").find(".sec2");
				$item1sec2.find("span.right.level1").html(level1Books+"册");
				$item1sec2.find("span.right.level2").html(level2Books+"册");
				$item1sec2.find("span.right.level3").html(level3Books+"册");
				$item1sec2.find("span.right.exbox").html(exboxBooks+"册");
				//在架书
				var onRackBooks=level1Books+level2Books+level3Books+exboxBooks;			
				$("div.main").find(".item.item1").find(".sec1").find(".num").html(onRackBooks+"册");
				
				$item2sec2=$("div.main").find(".item.item2").find(".sec2");
				$item2sec2.find("span.right.level1").html(level1Empty+"个");
				$item2sec2.find("span.right.level2").html(level2Empty+"个");
				$item2sec2.find("span.right.level3").html(level3Empty+"个");
				$item2sec2.find("span.right.exbox").html(exboxEmpty+"个");
				
				var emptyBoxs=level1Empty+level2Empty+level3Empty+exboxEmpty;
				
				$("div.main").find(".item.item2").find(".sec1").find(".num").html(emptyBoxs+"个");

				$item3sec1=$("div.main").find(".item.item3").find(".sec1");
				$item3sec1.find(".num").html(precheckout+"册");
				
			}
		});
	};
	//设置箱子信息
	var setDeviceBinState=function(){
		var arr=new Array();
		arr.push(device_id);
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/device/selectBinState",
			data:{"req":JSON.stringify(arr)}
		}).done(function(data){
				console.log(data);
			if(data&&data.state==true&&data.result[device_id]){
				var binState=data.result[device_id];
				//var cardbin=binState.cardbin;//卡箱
				var cashbin=binState.cashbin;//钱箱
				var bookbin=binState.bookbin;//书箱
				//设置纸币箱信息
				var cashBinhtml="纸币箱：<ul>";
				for(var i=0;i<cashbin.length;i++){
					var subtype=Number(cashbin[i].subtype);
					var amount=Number(cashbin[i].amount);
					var subtypeSum=subtype*amount;
					cashBinhtml+="<li>"+subtype+"面额："+subtypeSum+"元</li>";
				}
				cashBinhtml+="</ul>";
				$("div.zhibixiang").find(".t-box").html(cashBinhtml);
				
				//设置还书箱信息。
				/**
				<li>
					<div class="name">B01箱</div>
					<span>4册</span><span>其他</span>
				</li>
				**/
				var bookBinHtml='<ul>';
				for(var j=0;j<bookbin.length;j++){
					var binno=bookbin[j].binno;
					var desc=bookbin[j].desc;//海洋类？工具类？
					var amount=bookbin[j].amount;//箱子 书本册数
					bookBinHtml+='<li><div class="name">'+binno+'箱</div>';
					bookBinHtml+='<span>'+amount+'册</span><span>'+desc+'</span></li>';
				}
				bookBinHtml+='</ul>';
				$("div.zhibixiang").find(".box-list").html(bookBinHtml);
			}else{
				console.error("setDeviceBinState error:data.result["+device_id+"] 不存在",data);
			}
		});
	};
	
	//功能状态
	var setSoftState=function(functions){
		var arr=new Array();
		arr.push(device_id);
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/device/selectSoftState",
			data:{"req":JSON.stringify(arr)}
		}).done(function(data){
			if(data&&data.state==true&&data.result){
				//console.log("setSoftState:"+JSON.stringify(data));
				var softState=data.result[device_id];
				if(softState){
					var main=softState.main;
					$("div.head").find(".item.startupTime").html("系统启动时间："+main.startupTime);
					$("div.head").find(".item.updateTime").html("系统最后修改时间："+main.updateTime);
					$("div.head").find(".item.logistics_time").html("物流时间："+main.logistics_starttime+" 至  "+main.logistics_endtime);
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
			
					$lastLine=$(document).find(".padding-wrap").find(".one-line:last");
					$lastLine.find(".ul").append('<div class="li can-unfold" date-id="11">功能状态</div>');
					
					var border_wrap='<div class="border-wrap" data-id="11"><div class="status-box">'+
									'<span class="t">功能状态</span>'+'<div class="ul">';
				
					var funcs=softState["function"];
					console.log("functions",functions);
					console.log("funcs",funcs);
					for(var opercmd in funcs){
						var funcState=funcs[opercmd];
						if(functions){
							var cmdName=functions[opercmd]||"NONE";
							if(cmdName=="NONE"){
								continue;
							}
							if(funcState=="0"){//normal
								border_wrap+='<div class="li">'+cmdName+'</div>';
							}else if(funcState=="1"){//alert
								border_wrap+='<div class="li error">'+cmdName+'</div>';
							}else if(funcState=="2"){//error
								border_wrap+='<div class="li error">'+cmdName+'</div>';
							}
						}else{
							if(funcState=="0"){//normal
								border_wrap+='<div class="li">'+opercmd+'</div>';
							}else if(funcState=="1"){//alert
								border_wrap+='<div class="li error">'+opercmd+'</div>';
							}else if(funcState=="2"){//error
								border_wrap+='<div class="li error">'+opercmd+'</div>';
							}
						}
					
					}
					border_wrap+='</div></div></div>';
					$(document).find(".padding-wrap").append(border_wrap);
				}
			}
		});
	};
 	/**
 		获取设备功能中文名SelPermissionByDeviceId
 	**/
 	var functions=new Object();
 	var operatorQueryParams={"operator_id":device_id,"library_id":library_id};//这里要把 library_id给去掉
 	var setSoftStateProxy=function(functions,operatorQueryParams){
	 	$.ajax({
	 		url:"${pageContext.request.contextPath}/device/SelPermissionByDeviceId",
	 		type:"POST",
	 		data:{"req":JSON.stringify(operatorQueryParams)}
	 	}).done(function(data){
	 		if(data&&data.result){
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
	 		}
	 	}).done(function(){
	 		setSoftState(functions);//设置功能状态
	 	});
 	};
 	
 	
 	
 	
 	
 	
	//设置设备外设PLC状态
	var setDeviceExtState=function(){
		var arr=new Array();
		arr.push(device_id);
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/device/selectDeviceExtState",//clouddbservice_devicemonitor
			data:{"req":JSON.stringify(arr)}
		}).done(function(data){
			if(data&&data.state==true&&data.result[device_id]){
				var extState=data.result[device_id];
				if(extState.createTime)
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
				var extHtml='<div class="one-line"><div class="ul">';
				/**
					plc_state
				**/
				var plcHtml='<div class="border-wrap" data-id="1">'+
							'<div class="status-box">'+
								'<span class="t">PLC状态</span>'+
								'<div class="ul">';
				
				var num=0;//ext 外设状态个数
				//'undefined' 'string' 'number' 'boolean'  'function'  'object' 
				for(var ext in extState){
					num++;
					//ext 硬件逻辑名 extObj 状态 0 1 2
					 var extObj=extState[ext];//
					 if(typeof extObj=="string"){//BASE 状态 0 1 2
						 if(extStateNames){
							 var extName=extStateNames[ext.toLowerCase()]||"NONE";
							 if(extName=="NONE"){
								 continue;
							 }
							 if(extObj=="0"){
							 	 extHtml+='<div class="li">'+extName+'</div>';
							 }else if(extObj=="1"){//报警,临时
								 extHtml+='<div class="li error">'+extName+'</div>';
						 	 }else if(extObj=="2"){//失效
								 extHtml+='<div class="li error">'+extName+'</div>';
						 	 }
						 }else{
							 if(extObj=="0"){
								extHtml+='<div class="li">'+ext+'</div>';
							 }else if(extObj=="1"){//报警
								extHtml+='<div class="li error">'+ext+'</div>';
						 	 }else if(extObj=="2"){//失效
								extHtml+='<div class="li error">'+ext+'</div>';
						 	 }
						 }
					}else if(typeof extObj=="object"){//自助借还 plcSSL
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
						if(ext=="plcSSL"){//固定
							if(!extStateNames["plcSSL"]){continue;}
							extHtml+='<div class="li can-unfold" date-id="1">PLC状态</div>';
							for(var extObjName in extObj){
								var plcState=extObj[extObjName];
								if(extStateNames){
									var plcSSLNames=extStateNames["plcSSL"];
									//console.log("extObjName",extObjName);
									var plcObj=plcSSLNames[extObjName.toLowerCase()];
									if(plcObj){
										if(plcState=="0"){
											 plcHtml+='<div class="li">'+plcObj+'</div>';
										}else if(plcState=="1"){//报警
									 		 plcHtml+='<div class="li error">'+plcObj+'</div>';
									 	}else if(plcState=="2"){//失效
									 	     plcHtml+='<div class="li error">'+plcObj+'</div>';
									 	} 
									}
								}else{
									if(plcState=="0"){
										plcHtml+='<div class="li">'+extObjName+'</div>';
									}else if(plcState=="1"){//报警
										plcHtml+='<div class="li error">'+extObjName+'</div>';
								 	}else if(plcState=="2"){//失效
								 		plcHtml+='<div class="li error">'+extObjName+'</div>';
								 	}
								}
							}
						}
					}
					//
				    if(num>1&&(num-1)%4==0){
						//等于4的时候换行
						extHtml+='</div></div><div class="one-line"><div class="ul">';
					}
				}
				plcHtml+='</div></div></div>';
				extHtml+='</div>';
				//plcHtml plc 内容   
				$(document).find(".padding-wrap").html(extHtml).append(plcHtml);
				//收起
				$(document).find("div.status-box.plc_state").trigger("click");
			}else{
				console.error("设置设备外设PLC状态出现问题：",data);
			}
		}).done(function(){
			//软状态放在这里
			setSoftStateProxy(functions, operatorQueryParams);
		});
		
	};
	
	
	setBookrackState();
	
	setDeviceBinState();
	
	//setSoftState();加载完获取设备功能中文名后执行
	var lang="zh_CN";
    var extStateNames={};
	//获取当前设备的
	(function GetDevExtModel(){
		//获取设备的逻辑外设部件
		var devIdxArr=[device_idx];
		$.ajax({
			url:"${pageContext.request.contextPath}/device/GetDevExtModel",
			type:"GET",
			data:{"req":JSON.stringify(devIdxArr)}
		}).done(function(data){
			console.log("GetDevExtModel",data);
			if(data&&data.state){
				var deviceExtConfigs=data.result.deviceExtConfigs;
				var plcLogicOBJList=data.result.plcLogicOBJList;
				var deviceMonConfigs=data.result.deviceMonConfigs;
				var LogicOBJ={};
				console.log("deviceExtConfigs",deviceExtConfigs);
				console.log("deviceMonConfigs",deviceMonConfigs);
				if(deviceExtConfigs){
					for(var i=0;i<deviceExtConfigs.length;i++){
						var logic_obj=deviceExtConfigs[i].logic_obj.toLowerCase();
						extStateNames[logic_obj]=jsonToObj(deviceExtConfigs[i].logic_obj_desc)[lang];
					}
				}
				if(deviceMonConfigs){
					for(var i=0;i<deviceMonConfigs.length;i++){
						var tableName=deviceMonConfigs[i].table_name;
						if(tableName=="plc_logic_obj"){//表示有PLC
							LogicOBJ[deviceMonConfigs[i].plc_logic_obj.toLowerCase()]=deviceMonConfigs[i].plc_logic_obj_desc;
						}
					}
					if(!_.isEmpty(LogicOBJ)){
						extStateNames["plcSSL"]=LogicOBJ;
					}
				}
				/*if(plcLogicOBJList){
					for(var i=0;i<plcLogicOBJList.length;i++){
						LogicOBJ[plcLogicOBJList[i].plc_logic_obj.toLowerCase()]=plcLogicOBJList[i].plc_logic_obj_desc;
					}
					extStateNames["plcSSL"]=LogicOBJ;
				} */
				
			}
			console.log("extStateNames",extStateNames);
			setDeviceExtState();
		});
	})();
	<%--字符串转JSON格式--%>
	function jsonToObj(str){ 
		try{
			return JSON.parse(str); 
		}catch(e){
			return "";
		}
	} 	
	
	
    // code for fade in element by element  
    $.fn.fadeInWithDelay = function() {  
        var delay = 0;  
        return this.each(function() {  
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
   $(document).on("click",".can-unfold",function(){
		var dataId = $(this).attr("date-id");
		//layer.alert("点击事件");
		if($(this).hasClass("active")){
			$(this).removeClass("active");
			$(".border-wrap[data-id="+dataId+"]").hide();
		}else{
			$(".super-layer .can-unfold").removeClass("active");
			$(this).addClass("active");

			$(".border-wrap").hide();
			$(".border-wrap[data-id="+dataId+"]").show();
		}
	});
	
	var isSendOrder=false;
    /**
    	下载日志 按钮
    **/
    $(".g-btn.download.g-btn-blue").on("click",function(){
    	var device_id=$("div.head").find(".name").find(".id").text();
    	//step one get the query time
    	var startDate=$("input[name=startDate]").val();
    	var startTime=$("input[name=startTime]").val();
    	var endDate=$("input[name=endDate]").val();
    	var endTime=$("input[name=endTime]").val();
    	if(!startDate){
    		layer.alert("请填写开始时间");
    		return;
    	}
    	if(!endDate){
    		layer.alert("请填写结束时间");
    		return;
    	}
    	var utcStartTime=new Date($.trim(startDate+" "+startTime)).toUTCString();
    	var utcEndTime=new Date($.trim(endDate+" "+endTime)).toUTCString();
		//下载日志
    	//在后端加上 operator_idx
    	var req={
    		"utcStartTime":utcStartTime,
    		"utcEndTime":utcEndTime,
    		"device_id":device_id 
    	};
    	$.ajax({
    		url:"${pageContext.request.contextPath}/device/checkDownLoadLog",
    		data:{"req":JSON.stringify(req)},
    		type:"GET"
    	}).done(function(data){
    		console.log(data);
    		if(data){
    			if(data.state==true&&!data.message){
    				console.log("downLoadFilePath");
    				console.log(data.result);
    				//存在数据，可以下载了
    				var url="${pageContext.request.contextPath}/device/downloadRunLogOperation";
					var path="filePath="+data.result;
					download(url,path,"get");
    			}else if(data.message=="DELETE_NEED_SEND_ORDER"||data.message=="NEED_SEND_ORDER"){
						if("DELETE_NEED_SEND_ORDER"==data.message){
		    				//切换时间的情况下需要再次发送查询命令
		    				isSendOrder=false;
		    			}
		    			//需要发送指令
		    			//获取信息 包括device_id device_idx
						//3 代表 查询日志
						if(!isSendOrder){
							var params={
									  //"operator_id":operator.operator_id,
									  "device_id":device_id,
									  "control":"1",
									  "control_info":"3",
									  "start_time":utcStartTime,
									  "end_time":utcEndTime
							};
							var arrParams=new Array();
							arrParams.push(params);
							console.log(params);
							$.ajax({
								type:"POST",
								url:"${pageContext.request.contextPath}/device/sendOrder",
								data:{"req":JSON.stringify(arrParams)},
								success:function(data){
									console.log(data);
									if(data){
										isSendOrder=true;
									}
								}
							});
						 }
					if(data.retMessage){
						layer.alert(data.retMessage);
					}
				}else if(data.retMessage){
    				layer.alert(data.retMessage);
    			}
    		}
    	});
		
    });
    /**
		下载
	**/
	var download = function(url, data, method){    
		// 获得url和data
	    if( url && data ){ 
	    	 // data 是 string 或者 array/object
	        data = typeof data == 'string' ? data : jQuery.param(data);        // 把参数组装成 form的  input
	    	var inputs = '';
	    	jQuery.each(data.split('&'), function(){ 
	            var pair = this.split('=');
	            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />'; 
	        });        
	        // request发送请求
	        jQuery('<form action="'+ url +'" method="'+ (method||'post') +'">'+inputs+'</form>')
	        .appendTo('body').submit().remove();
	   	 };
	};
    
	/*详细页JS*/
	$(".head .btn1").on("click",function(){
		$(this).addClass("active").siblings("li").removeClass("active");
		$(".machine-detail .main").show();
		$(".machine-detail .file-check").hide();
	});
	$(".head .btn2").on("click",function(){
		$(this).addClass("active").siblings("li").removeClass("active");
		$(".machine-detail .main").hide();
		$(".machine-detail .file-check").show();
	});
	$(".datepicker" ).datepicker({
		numberOfMonths:1,//显示几个月  
		showButtonPanel:false,//是否显示按钮面板  
		dateFormat: 'yy-mm-dd',//日期格式  
		clearText:"清除",//清除日期的按钮名称  
		closeText:"关闭",//关闭选择框的按钮名称  
		yearSuffix: '', //年的后缀  
		showMonthAfterYear:true,//是否把月放在年的后面  
		defaultDate:new Date(),//默认日期  
		//minDate:'2011-03-05',//最小日期  
		//maxDate:'2011-03-20',//最大日期  
		monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
		dayNamesMin: ['日','一','二','三','四','五','六'],  
		onSelect: function(selectedDate) {//选择日期后执行的操作  
	
		}  
	});

	$(".timepicker").timepicker({
		controlType: 'select',
		oneLine: true,
		timeFormat: 'HH:mm',
		currentText:"现在",
		closeText:"完成",
		timeOnlyTitle:"",
		//amNames:["AM"],
		//pmNames:["PM"],
		timetext:"时间",
		hourText:"时",
		minuteText:"分",
		secondText:"秒"
	});
	
});
</script>
</body>
</html>