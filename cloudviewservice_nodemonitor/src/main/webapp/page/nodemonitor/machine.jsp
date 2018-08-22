<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
<style type="text/css">
	/**sec3**/
	.machine-list .item.gray .sec3 .status {
   	 	background: url(${pageContext.request.contextPath}/static/images/cir-status4.png) right center no-repeat;
	}
	 .machine-list .item.blue .sec3 .status{
   	 	background: url(${pageContext.request.contextPath}/static/images/cir-status5.png) right center no-repeat;
	}
	.machine-list .item.alert .sec3 .status {
   	 	background: url(${pageContext.request.contextPath}/static/images/cir-status3.png) right center no-repeat;
	}
	/**sec2**/
	.machine-list .item.gray .sec2 .status {
    	background: url(${pageContext.request.contextPath}/static/images/cir-status4.png) right center no-repeat;
	}
	.machine-list .item.blue .sec2 .status {
    	background: url(${pageContext.request.contextPath}/static/images/cir-status5.png) right center no-repeat;
	}
	.machine-list .item.alert .sec2 .status {
    	background: url(${pageContext.request.contextPath}/static/images/cir-status3.png) right center no-repeat;
	}
</style>
</head>
<body>
<div class="g-loading"></div>
<%@include file="../common/header.jsf" %>
<div class="page-title-bar">
	<span class="title">监控机器<a href="help-page.html" target="_blank" class="g-help"></a></span>
	<c:if test="${operator.operator_type eq '1'}"><%--管理员 --%>
		<div class="choose-lib">
			<div class="select-btn">
				<span class="current-libName">显示全部</span>
				<span class="icon"></span>
			</div>
			<div class="drop-down">
				<div class="item place">盐田图书馆</div>
				<%--  
					<div class="item room">沙头角阅览室</div>
				--%>
			</div>
		</div>
	</c:if>
	<c:if test="${operator.operator_type eq '2'}"><%--维护员 --%>
		<shiro:hasPermission name="0103000000"><%--监控管理主页面 --%>
			<div class="choose-lib">
				<div class="select-btn">
					<span class="current-libName">显示全部</span>
					<span class="icon"></span>
				</div>
				<div class="drop-down">
				</div>
			</div>
		</shiro:hasPermission>	
	</c:if>
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
					<!-- 	
						<option value="-1" selected>选择类型</option>
					-->
				</select>
			<span class="arr-icon"></span>
		</div>
		<div class="g-select">
			<select id="machineState">
					<option value="-1" selected>选择状态</option>
					<option value="0">正常</option>
					<option value="1">报警</option>
				    <option value="2">失效</option>
			</select>
			<span class="arr-icon"></span>
		</div>
		<input type="text" name="keyWord" id="" class="input g-input" placeholder="输入设备名" />
		<div href="" class="btn search">查询</div>
	</div>
</div>
<c:choose>
<c:when test="${operator.operator_type eq '2'}">
	<shiro:hasPermission name="0103000000">
		<div class="main">
			<div class="machine-list">
				<div class="item-wrap">
				
				</div>
			</div>
		</div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="0103000000">
		<div class="main">
			<div class="machine-list">
				<div class="item-wrap">
					没有权限！
				</div>
			</div>
		</div>
	</shiro:lacksPermission>
</c:when>
<c:otherwise>
	<div class="main">
	<div class="machine-list">
			<div class="item-wrap">
			
			</div>
		</div>
	</div>
</c:otherwise>
</c:choose>
<%@ include file="../include/page_bar.jsf" %>


<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/devpagination.js"></script>
<script type="text/javascript">
$(function(){
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();
		
		$(".main").css({
			"min-height":winH - headerH - pagingBarH -pageTitleBar,
			"max-height":"auto"
		});
	})();
	var machine={};
	var library={};/**ID library_idx**/
	/**加载页面**/
	$itemWrap=$("div.main").find(".item-wrap");
		/**
		<div class="item active">error
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
			var key=library[device.library_idx].lib_id+"_"+device.device_id;
			var item='<div class="item gray">'
			 	+'<input type="text" name="_device_id" class="_device_id" hidden="hidden" value="'+key+'"/>'
			 	+'<input type="text" name="_device_idx" class="_device_idx" hidden="hidden" value="'+device.device_idx+'"/>'
			 	+'<input type="text" name="_library_idx" class="_library_idx" hidden="hidden" value="'+device.library_idx+'"/>'
			 	+'<div class="sec1">'
			 	+'<div class="g-checkbox"><input type="checkbox" name="'+device.device_id+'" id=""></div>'
			 	+'<span class="machine-name">'+device.device_name+'</span>'
			 	+'</div>';
			 	//现在只有自助借还图书馆显示以下信息
			 	if("SSL"==device.device_type){
				 	item+='<div class="sec2">'
				 	+'<ul class="device_state">'
				 	+'	<li>在架书：<span class="right level1">暂无数据</span></li>'
				 	+'	<li>卡数量：<span class="right level2">暂无数据</span></li>'
				 	+'	<li>还书箱：<span class="right level3">暂无数据</span></li>'
				 	+'</ul>'
				 	+'</div>';
					item+='<div class="sec3">'
					 	+'ID:'+device.device_id+'<span class="status">无状态</span>'
					 	+'<div class="check-detail">查看详情</div>'
					 	+'</div>'
					 	+'</div>';
			 	}else if("SCH"==device.device_type||"REG"==device.device_type||"STA"==device.device_type){
			 		//自助借还、办证、馆员工作站
			 		item+='<div class="sec2">'
					 	+'<ul class="device_state">'
					 	+'	<li>ID:'+device.device_id+'</li>'
					 	+'	<li><span class="status">无状态</span></li>'
					 	+'</ul>'
					 	+'</div>';
			 		item+='<div class="sec3">'
					 	+'<div class="check-detail">查看详情</div>'
					 	+'</div>'
					 	+'</div>';
			 	}else {
			 		item+='<div class="sec2">'
					 	+'<ul class="device_state">'
					 	+'</ul>'
					 	+'</div>';
			 		item+='<div class="sec3">'
					 	+'ID:'+device.device_id+'<span class="status">无状态</span>'
					 	+'<div class="check-detail">查看详情</div>'
					 	+'</div>'
					 	+'</div>';
			 	}
			 
				$itemWrap.append(item);
		};
	};
	/**
		获取颜色
	**/
	var colorArr=[];
	var getColorArr=function(func){
		$.ajax({
			url:"${pageContext.request.contextPath}/devicemonconf/queryMonitorColorConf",
			type:"GET"	
		}).done(function(data){
			console.log("获取颜色",data);
			if(data&&data.state){
				colorArr=data.result;
				if(func){
					func();
				}
			}
		});
	};
	getColorArr();
	
	

	/** 
		获取基础数据
	**/
	var metadataLogicOBJ={};
	var GetMetadataLogicOBJ=function(){
		$.ajax({
			url:"${pageContext.request.contextPath}/devicemonconf/SelMetadataLogicObject",
			type:"GET",
			data:{"req":"{}"}
		}).done(function(data){
			//console.log("GetMetadataLogicOBJ",data);
			if(data&&data.state&&data.result){
				for(var i=0;i<data.result.length;i++){
					var logicObj=data.result[i];
					metadataLogicOBJ[logicObj.meta_log_obj_idx]=logicObj.logic_obj.toLowerCase();
				}				
			}
		});
	}; 
	GetMetadataLogicOBJ();
	var currentDevColorRetMap={};
	//获取当前页的设备的监控颜色设置
	var GetCurrentDevColorSet=function(){
		var devIdxArr=[];
		$("input[name=_device_idx]").each(function(index,dom){
			devIdxArr.push($(dom).val());
		});
		$.ajax({
			url:"${pageContext.request.contextPath}/device/GetCurrentDevColorSet",
			type:"GET",
			data:{"req":JSON.stringify(devIdxArr)}
		}).done(function(data){
			if(data){
				console.log("GetCurrentDevColorSet",data);
				currentDevColorRetMap=data.result;
				if(!metadataLogicOBJ){
					GetMetadataLogicOBJ();
				}
				setStatus();
			}
		});
		
	};
	//获取设备状态,定时执行时间timeRefresh
	var setStatus=function(){
		 var arr=new Array();
		 $(document).find("input._device_id").each(function (index,domEle){
			var device_id=$(domEle).val();//实际上是 library_id_device_id
			arr.push(device_id);
		 }); 
		 $.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/device/selectDeviceState",
			data:{"req":JSON.stringify(arr)}
		}).done(function(data){
			if(data&&data.state==true){
				var device=data.result;// {device_id:state}
				for(device_id in device){
					$thisDev=$(document).find("input[name=_device_id][value="+device_id+"]");
					var device_idx=$thisDev.parent().find("input[name=_device_idx]").val();
					var stateObject=device[device_id];
					if(!stateObject){//没有上传过ext_state数据，认定为没有该设备
						$thisDev.parent()
							.removeClass("error")
							.removeClass("alert")
							.removeClass("blue").addClass("gray");
						$thisDev.parent().find("span.status").html("无数据");
						continue;
					}
					var state=stateObject.new_state;         //状态值
					var alertObj=stateObject.alert_obj;	     //逗号分割的异常部件名称
					var createTime=stateObject.create_time;  //20160823191003
					var curTimeLong=stateObject.current_time;//long类型的时间
					var exTime=0;
					if(createTime){
	    				var createTimeLong=new Date(createTime.substring(0,4),createTime.substring(4,6)-1,createTime.substring(6,8),createTime.substring(8,10),createTime.substring(10,12),createTime.substring(12,14));
						exTime=(curTimeLong-createTimeLong)/1000;
					}else{//state_log表 没有找到时间 ？这样也可以认定是超时
						
					}
					console.log("exTime",exTime);
					if(state=="0"){//正常状态
						$thisDev.parent().removeClass("error").removeClass("alert").removeClass("gray").removeClass("blue");
						$thisDev.parent().find("span.status").html("正常");
					}else
					//报警颜色 如红色、灰色、黄色、绿色  等）//其他值异常
					//green:0:绿色|yellow:1:黄色|red:2:红色|gray:3:灰色|blue:4:蓝色
					//这里需要查询设备配置的报警颜色。[报警的等级]
					if(state!="0"){
						/* $thisDev.parent()
						.removeClass("error")
						.removeClass("alert")
						.removeClass("gray")
						.removeClass("blue").addClass("error");
						$thisDev.parent().find("span.status").html("告警"); */
						if(currentDevColorRetMap){
							var monitorConfList=currentDevColorRetMap[device_idx];
							if(monitorConfList){
								var alertObjMap={};
								if(alertObj&&alertObj.indexOf(",")>=0){
									var alertObjArr=alertObj.split(",");
									for(var i=0;i<alertObjArr.length;i++){
										var alert=alertObjArr[i].toLowerCase();
										if(alert){
											alertObjMap[alert]=alert;
										}
									}
								}
								//mongodb(alertObjMap)报警上传的报警的部件，
								//以mysql(currentDevColorRetMap)配置报警部件的为准
								console.log("alertObjMap",alertObjMap);
								var maxColor=1;
								for(var i=0;i<monitorConfList.length;i++){
									var monitorConf=monitorConfList[i];
									if(monitorConf.table_name=="metadata_logic_obj"){
										if(metadataLogicOBJ){
											var logicObj=alertObjMap[metadataLogicOBJ[monitorConf.meta_log_obj_idx].toLowerCase()];
											if(!logicObj||logicObj==null||logicObj=="undefined"||logicObj==""){
												continue;
											}
										}
										var color=monitorConf.color;
										maxColor=getMaxAlertColor(color,maxColor);
										if(!colorArr||colorArr.length<=0){
											getColorArr(function(){
												var colorOBJ=colorArr[maxColor];
												if(colorOBJ){
													var colorEN=colorOBJ["colorEN"];
													if(!colorEN||colorEN=="") colorEN="error";
													$thisDev.parent()
														.removeClass("error")
														.removeClass("alert")
														.removeClass("gray")
														.removeClass("blue").addClass(colorEN);
													$thisDev.parent().find("span.status").html("告警");
												}
											});
										}else{
											var colorOBJ=colorArr[maxColor];
											if(colorOBJ){
												var colorEN=colorOBJ["colorEN"];
												if(!colorEN||colorEN=="") colorEN="error";
												$thisDev.parent()
													.removeClass("error")
													.removeClass("alert")
													.removeClass("gray")
													.removeClass("blue").addClass(colorEN);
												$thisDev.parent().find("span.status").html("告警");
											}
										}
									}
									//如果超时则 
									if("time_out"==monitorConf.table_name){
										var threshold=monitorConf.threshold;
										if(exTime&&threshold){
											if(exTime>threshold){
												//超时
												$thisDev.parent()
													.removeClass("error")
													.removeClass("alert")
													.removeClass("gray").addClass("blue");
												$thisDev.parent().find("span.status").html("超时");
												break;//
											}
										}
									}
							  }
							}
						} 
					}
				}
			}
			console.log("data:"+JSON.stringify(data));
		});
	};
	//获取对应 颜色 
	var getMaxAlertColor=function(color,maxColor){
		//0(绿色)正常  <1(提醒) <2(红色)告警 <4(蓝色)超时 <3(灰色) 该部件禁用状态
		console.log("color:"+color,"maxColor:"+maxColor);
		if(color==3){
			return maxColor;
		}
		if(color>maxColor){
			return color;
		}
		return maxColor;
	};
	/**
		切屏
	**/
	var changeScreen=function(){
		//获取当前页
		var currentpage = $("li.active").html();//当前页
		page = Number(currentpage) + 1;//下一页
		var maxPage=$("#page").find("li:last").html();//最大页数
		if(currentpage==maxPage){//最后一页
			//var Page=makeQueryParam(1, pageSize);
			//selectPage(Page);
			$("#page").find("li").each(function(index,dom){
				if($(dom).html()=="1"){
					$(dom).trigger("click");
				}
			});
			return;
		}
		$("#page").find("li").each(function(index,dom){
			if($(dom).html()==page){
				//表示有下一页
				//var Page=makeQueryParam(page, pageSize);
				//selectPage(Page);
				$(dom).trigger("click");
			}
		});
	};
	
	/**
		获取所有的没有不是子馆的馆、和一级子馆
		管理员使用此方法
	**/
	var queryAllMasterLibAndSlaveLib=function(library_idx){
		if(library_idx){
			var library={"library_idx":library_idx};
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/librarylocal/querySlaveLibraryByMasterIdx",
				data:{"req":JSON.stringify(library)},
			}).then(function(data){
				if(data&&data.state==true){
					var masterLibAndSlaveLibs=data.result;
					if(masterLibAndSlaveLibs){
						var masterLibrary=masterLibAndSlaveLibs.masterLibrary;
						$(".choose-lib").find(".current-libName").html('<div class="item place" lib-idx="'+masterLibrary.library_idx+'">'+masterLibrary.lib_name+'</div>');
					}
				}
				return $.ajax({
					url:"${pageContext.request.contextPath}/librarylocal/queryAllMasterLibAndSlaveLib",
					type:"GET",
					data:{"req":JSON.stringify()}
				});
			}).then(function(data){
					//console.log("queryAllMasterLibAndSlaveLib",data);
					if(data&&data.state){
						var libList=data.result;
						$("div.page-title-bar").find(".drop-down").html("");
						//选择图书馆
						var dropDownHtml='<div class="item place" lib-idx="0">显示全部</div>';
						for(var i=0;i<libList.length;i++){
							var msLibAndSlaveLib=libList[i];
							var masterLibrary=msLibAndSlaveLib.masterLibrary;
							var slaveLibraryArr=msLibAndSlaveLib.slaveLibrary;
							if(masterLibrary.library_idx==0){//云平台屏蔽
								continue;
							}
							//console.log(masterLibrary);
							dropDownHtml+='<div class="item place" lib-idx="'+masterLibrary.library_idx+'">';
							dropDownHtml+=masterLibrary.lib_name+'</div>';
							for(var j=0;j<slaveLibraryArr.length;j++){
								dropDownHtml+='<div class="item room" lib-idx="'+slaveLibraryArr[j].library_idx+'">'+slaveLibraryArr[j].lib_name+'</div>';
							}
						}
						$("div.page-title-bar").find(".drop-down").html(dropDownHtml);
					}else if(data.message="NO_PERMESSION"){<%--不是系统管理员的情况下 只能查看当前馆或以下--%>
						setLibraryName(library_idx);
					}
			});
		}
	};
	/**
		根据library_idx得到 Library数据
		非管理员使用此方法
	**/
	//设置图书馆名称函数
	var setLibraryName=function(library_idx){
		if(library_idx){
			var library={"library_idx":library_idx};
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/librarylocal/querySlaveLibraryByMasterIdx",
				data:{"req":JSON.stringify(library)},
			}).done(function(data){
				if(data&&data.state==true){
					var masterLibAndSlaveLibs=data.result;
					if(masterLibAndSlaveLibs){
						var masterLibrary=masterLibAndSlaveLibs.masterLibrary;
						var slaveLibraryArr=masterLibAndSlaveLibs.slaveLibrary;
					
						if(masterLibrary&&slaveLibraryArr){
							$("div.page-title-bar").find(".drop-down").html("");
							//选择图书馆
							var dropDownHtml='<div class="item place" lib-idx="0">选择图书馆</div>'
							+'<div class="item place" lib-idx="'+masterLibrary.library_idx+'">'
							+masterLibrary.lib_name+'</div>';
							
							$(".choose-lib").find(".current-libName").html('<div class="item place" lib-idx="'+masterLibrary.library_idx+'">'+masterLibrary.lib_name+'</div>');
							
							for(var i=0;i<slaveLibraryArr.length;i++){
								dropDownHtml+='<div class="item room" lib-idx="'+slaveLibraryArr[i].library_idx+'">'+slaveLibraryArr[i].lib_name+'</div>';
							}
							$("div.page-title-bar").find(".drop-down").html(dropDownHtml);
						}
					}else{
						//错误信息
						console.log(data);
					}
				}
			});
		}
	};
		
	/**
		点击图书馆名事件
		<div class="choose-lib">
		<div class="select-btn">
			<span class="current-libName">选择图书馆</span>
			<span class="icon"></span>
		</div>
	**/
	$(".drop-down").on("click",".item",function(){
		$(".drop-down").hide();
		var libIdx=$(this).attr("lib-idx");
		var libName=$(this).html();
		//console.log(libIdx);
		$(".choose-lib").find(".current-libName").html(libName);
		//if(libIdx>0){//==0表示查询所有
			//当做一个查询条件传参处理
			machine.curSelLibIdx=libIdx;
			var Page=makeQueryParam(1,12);
			selectPage(Page);
		//}
	});
	//收起左边下拉
	$("div.main").on("click",function(){
		$(".drop-down").hide();
	});
	
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
				console.log("箱子状态："+JSON.stringify(data));
				var result=data.result;
				for(var device_id in result){
					//device_id 现在实际上是 library_id+"_"+device_id
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
			"keyWord":keyWord,
		};
		if(machine.curSelLibIdx!=0){
			Page.library_idx=machine.curSelLibIdx;
		}
		return Page;
	};
	
	var _interval=null;
	var changeScreenInterval=null;
	var pageSize=12;//每页显示的条数
	//翻页函数 包括查询参数  selectPage
	var selectPage=function(obj){//
			$.ajax({
				url:"${pageContext.request.contextPath}/librarylocal/getLibIdAndLibIdx",
				data:{req:""},
				type:"GET"
			}).then(function(data){
				console.log("getLibIdAndLibIdx",data);
				if(data&&data.state){
					console.time("queryDeviceByParam");
					library=data.result;
					$.ajax({
						url:"${pageContext.request.contextPath}/device/queryDeviceByParam",
						type:"POST",
						data:{"req":JSON.stringify(obj)}
					}).done(function(data){
						console.timeEnd("queryDeviceByParam");
						if(data){
							var page=data.result;
							if(page&&page.rows){
								drawRow(page.rows);
								if(page.rows[0]){
									//setLibraryName(page.rows[0].library_idx);
									queryAllMasterLibAndSlaveLib(page.rows[0].library_idx);
								}
								$.diviceMainPagination(page);
								 if(page.rows.length==0){
				 					$(".machine-list").find(".item-wrap").html("没有查询到匹配的设备");
								 }
							}
						}
						//翻页 或者 加载之后先执行一次 setStatus
						//setStatus();
						GetCurrentDevColorSet();
						//每次选择页码结束，执行获取设备状态函数
						var seconds=$(".g-select.refresh select").val();//切屏频率
						clearInterval(changeScreenInterval);
						if(seconds=="-1"){
							//不切屏
						}else{
							changeScreenInterval=setInterval(changeScreen,seconds);
						}
						clearInterval(_interval);
						_interval=setInterval(setStatus,50000);//周期 暂时注释掉 2016年5月10日08:47:33
						setDeviceBinState();//设置箱子信息
						setBookrackState();//设置在架书
					});
				}
			});
    };
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $("li.active").html();//当前页
		page = Number(currentpage) + 1;//下一页
		var Page=makeQueryParam(page, pageSize);
		//调用页面的查询ajax
		selectPage(Page);
	});
	$("#page").on("click",".prev-page",function(){
		var currentpage = $("li.active").html();
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
					var device_type_desc=deviceTypeObj.device_type_desc;
					var opt='<option value="'+device_type+'">'+device_type_desc+'</option>';
					$("select#machineType").append(opt);
				}
			}
		}
	});
	
	function sleep(d){
  		for(var t = Date.now();Date.now() - t <= d;);
	}
	//循环调用 获取 执行结果 params =[{device_id:"",control:"",control_info:""},{device_id}]
	var HAS_RESULT=false;
	var getControlResult=function(params){
				$.ajax({
					url:"${pageContext.request.contextPath}/device/queryControlResult",
					type:"POST",
					data:{"req":JSON.stringify(params)}
				}).done(function(data){
					console.log("getControlResult:"+JSON.stringify(data));
					if(data&&data.state==true&&data.message=="CURRENT_NO_RESULT"){
						// 暂时没有结果时继续AJAX
						console.log("CURRENT_NO_RESULT");
					}
					if(data&&data.state&&data.message=="HAS_RESULT"){
						//这里只处理了一台设备有返回的结果的情况,需要作出修改
						var result=data.result;
						console.log("HAS_RESULT:"+JSON.stringify(result));
						if(result){
							for(var i=0;i<result.length;i++){
								var controlRes=result[i];
								var device_id=controlRes.device_id;
								if(controlRes.controlresult=="1"){//关机
									layer.alert(device_id+" 关机成功");
								}else if(controlRes.controlresult=="2"){//重启
									layer.alert(device_id+" 重启成功");
								}else if(controlRes.controlresult=="4"){//锁屏
									layer.alert(device_id+" 锁屏成功");
								}else if(controlRes.controlresult=="5"){//取消操作
									layer.alert(device_id+" 取消操作成功");
								}
							}
							HAS_RESULT=true;
						}
					}
				}).fail(function(e){
					layer.alert(e);
					HAS_RESULT=true;
				});
	};
	 function minusDate(date,days){ 
       var d=new Date(date); 
       d.setDate(d.getDate()-days); 
       var m=d.getMonth()+1; 
       return d.getFullYear()+'-'+m+'-'+d.getDate(); 
     } 
	/**
	*	发送机器执行命令	
	**/
	$("#sendbtn").on("click",function(){
		$cmd=$("#machineCmd");//order_idx
		var cmd=$cmd.val();
		//获取所有选择的checkbox
		var curCheckboxs = $(".item.active").find(".g-checkbox").find("input:checked");
		var params=new Array();
		if(curCheckboxs.length<1) {
			layer.alert("请选择至少一台设备");
			return;
		}
		if(cmd<0){
			layer.alert("请选择指令后再发送");
			return;
		}
		//获取当前页的图书馆IDX
		var library_idx=$(".current-libName").find(".item.place").attr("lib-idx");
		//获取ID
		for(var i=0;i<curCheckboxs.length;i++){
		    var dev=curCheckboxs[i];
		    var dev_control={
			    "device_id":dev.name,
			    "control":"1",
			    "control_info":cmd,
		    	"library_idx":library_idx
		    };
		    params.push(dev_control);
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/device/sendOrder",
			type:"POST",
			data:{"req":JSON.stringify(params)},
		}).done(function(data){
			if(data.state==true&&data.message!="isAlreadyHasSameOrders"){
				layer.alert("正在等待设备执行命令");
				
			}else if(data.state==true&&data.message=="isAlreadyHasSameOrders"){
				var dev_ids=data.result;
				layer.alert("正在等待设备执行命令，其中ID:\n"+dev_ids+" \n设备命令已经发送过了，正在等待在执行");
			}
			console.log(data);
		}).done(function(){
			HAS_RESULT=false;
			//采用周期循环处理
			var Interval=null;
			Interval=setInterval(function(){
				getControlResult(params);
				if(HAS_RESULT){
					clearInterval(Interval);//清除
				}
			},60000);
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
		var device_id=$item.find("._device_id").val();//lib_id and device_id
		var library_idx=$item.find("._library_idx").val();//lib_id and device_id
		var library_id=library[library_idx].lib_id;
		//卡数量
		var cardNum=$item.find("div.sec2").find(".right.level2").text().replace(/[^0-9]/ig,""); ;
		var url="${pageContext.request.contextPath}/device/deviceDetail?device_idx="+device_idx+"&device_id="+device_id+"&cardNum="+cardNum+"&library_id="+library_id;
		//这里先判断以下session有没有过期，过期则跳转到登陆页面
		$.getJSON("${pageContext.request.contextPath}/login/checkSession",{},function(data){
			if(data&&data.checkSession){
					layer.open({
						type: 2, 
						content: [url],
						title :false,
						closeBtn :1,
						offset :["20px"],
						area :["860px",dialogH+"px"],
						shade:0.5,
						shadeClose :true,
						scrollbar :false,
						move:false,
						skin:false,
						success :function(layero, index){
							var body = layer.getChildFrame('body', index);
							 // var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
							 // console.log(body.html()) //得到iframe页的body内容
						 	body.find("div.head").find(".name").prepend('<span>'+device_name+'</span>');
						    body.find("div.head").prepend('<input hidden="hidden" class="dev_id" value='+device_id+'></input>');
						}
					});
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
		var num=$(".machine-list").find("input[type=checkbox]:checked").length;
		$("#ChooseNum").find(".total-choosen-num").html(num);
	});
	
	//改变刷新时间
	$(document).on("change",".g-select.refresh select",function(){
		var seconds=$(this).val();//获取刷新周期
		//更改周期
		clearInterval(changeScreenInterval);
		if(seconds==-1){
			//不切屏
		}else{
			changeScreenInterval=setInterval(changeScreen,seconds);
		}
	});
	//
	//点击查询按钮，查询
	//
	$(document).on("click",".btn.search",function(){
		 var currentpage = $("li.active").html();
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





