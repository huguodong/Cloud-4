<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
<%-- 根据数据类型，编辑按钮选用不同的颜色，-1:未选择，0：模板数据，1：自定义数据 --%>
	function runTempType(val){
		if(val=="-1"){
			$(".yunxing-dialog .edit")
			.addClass("disabled")
			.addClass("g-btn-gray")
			.removeClass("g-btn-blue")
			.removeClass("g-btn-green");
		}
		if(val=="0"){
			$(".yunxing-dialog .edit")
			.removeClass("disabled")
			.removeClass("g-btn-gray")
			.addClass("g-btn-blue")
			.removeClass("g-btn-green");
		}
		if(val=="1"){
			$(".yunxing-dialog .edit")
			.removeClass("disabled")
			.removeClass("g-btn-gray")
			.removeClass("g-btn-blue")
			.addClass("g-btn-green");
		}
	}
	
	<%-- 点击保存按钮的等待效果 --%>
	function startSaveRun(){
		$(".g-loading.save").fadeIn();
	}
	
	<%-- 取消等待效果 --%>
	function endSaveRun(){
		$("input[type=reset]").trigger("click");
		$(".item.yunxing-dialog").find(".edit").removeClass(".g-btn-gray").addClass("g-btn-green");
	}
	//废除
	$("#runTempRestore").on("click",function(){
		layer.confirm("恢复至模板数据后，该选项的自定义数据将被清除，确认是否将该模板选项恢复至模板数据？",{icon:1,title:"提示"},function(index){
			startSaveRun();
			<%-- 获取当前选中的模板 --%>
			var idx = $("#runTemp").val();
			if(idx!=null&&idx!=""&&idx!="-1"){
				delete runTempCustom[idx] <%-- 删除该选项的自定义数据 --%>
				$("#runTemp").trigger("change");
			}else{
				layer.alert("请先选择模板");
			}
			layer.close(index);
			endSaveRun();
		});
	})
	
	<%-- 打印设置里面的勾选启用功能 --%>
	$("div[data-type='print'] input[type='checkbox']").on("change",function(){
		if($(this).prop("checked")){
			$(this).closest(".left").siblings().find("input,textarea")
			.removeClass("disabled").removeAttr("disabled");
		}else{
			$(this).closest(".left").siblings().find("input,textarea")
			.addClass("disabled").attr("disabled","disabled");
		}
	})
	
	<%-- 清除模板数据内容 --%>
	function clearRunConfData(){
		<%-- 清空文本框 --%>
		$("div.yunxing input[type='text']").val("");
		$("div.yunxing input[type='password']").val("");
		$("div.yunxing textarea").val("");
		<%-- 选择框默认选中第一个 --%>
		$("div.yunxing select").prop("selectedIndex", "0"); 
		<%-- 复选框默认不勾选 --%>
		$("div.yunxing input[type='checkbox']").prop("checked",false).trigger("change");
		<%-- 记录日志：不记录 --%>
		$("input[name='runlog_level'][value='none']").trigger("change");
		<%-- 分拣类型 --%>
		$("input[name='function_sorttype'][value='0']").trigger("change");
		<%-- PIN类型 --%>
		$("input[name='pin_type'][value='1']").trigger("change");
		<%-- PIN设备输入 --%>
		$("input[name='pin_input'][value='digitsoftpin']").trigger("change");
		<%-- PIN输入超出次数之后 --%>
		$("input[name='pin_retrydo'][value='0']").trigger("change");
		<%-- 打印配置里面的radio默认选中询问读者 --%>
		$("div[data-type='print'] input[type='radio'][name$='_type'][value='2']").trigger("change");
	}
	
	<%-- 选择运行配置模板之后触发的事件 --%>
	$("#runTemp").on("change",function(){
		<%-- 先清除原来数据 --%>
		clearRunConfData();
		var index = this.selectedIndex;
		if($("#runTemp").val()!="-1"){
			if(runTempList.length>0){
				runTempObj = runTempList[index-1];
				if(!_.isEmpty(runTempObj)){
					var idx = runTempObj.device_tpl_idx;
					if(_.isEmpty(runTempCustom[idx])){
						<%-- 如果为空则使用模板数据 --%>
						<%--深度克隆对象runTempObj--%>
						runTempSubmit = $.extend(true,{}, runTempObj);
						//runTempType("0");
					}else{
						<%-- 如果自定义数据不为空则使用自定义数据 --%>
						runTempSubmit = runTempCustom[idx];
						//runTempType("1");
					}
				}
				<%-- 装载运行配置的数据 --%>
				loadAllRunTempData(runTempSubmit,0);
			}
		}else{
			<%--  选择了模板之后修改编辑按钮样式  --%>
			//加载自定义数据
			console.log("加载自定义数据",rundata);
			loadAllRunTempData(rundata,1);
			//runTempType("-1");
		}
	});
	
	/**
		加载自定义数据
	**/
	function loadAllRunTempData(runObj,deviceTplType){
		if(!_.isEmpty(runObj)){
			var list=[];
			if(deviceTplType==0){//模板数据
				if(_.isEmpty(runObj.runDetailList)){
					return;
				}
				list= runObj.runDetailList;
			}else if(deviceTplType==1){//自定数据
				list=rundata;
			}
			<%-- 先初始化输入框 --%>
				for(var i=0;i<list.length;i++){
					var item = list[i];
					//console.log("加载自定义数据",item);//自定义数据要取出来
					var conf_type = item.run_conf_type;
					var conf_value = {};
					if(typeof item.run_conf_value == "object"){
						conf_value = item.run_conf_value;
					}else{
						conf_value = jsonToObj(item.run_conf_value);
					}
					
					<%-- 语言配置 --%>
					if(conf_type=="language_config"){
						$("#basic_language").val(conf_value.LANGUAGE);
						continue;
					}
					
					<%-- 运行日志配置 --%>
					if(conf_type=="runlog_config"){
						$("input[name='runlog_level'][value='"+conf_value.RUNLOG_LEVEL+"']").trigger("change");
						if(conf_value.RUNLOG_TYPE.indexOf("file")>-1){
							$("#file").prop("checked",true).trigger("change");
						}else{
							$("#file").prop("checked",false).trigger("change");
						}
						
						if(conf_value.RUNLOG_TYPE.indexOf("db")>-1){
							$("#db").prop("checked",true).trigger("change");
						}else{
							$("#db").prop("checked",false).trigger("change");
						}
						
						$("#runlog_filesize").val(conf_value.RUNLOG_FILESIZE.replace(/[a-zA-Z]*/g,""));
						console.log(conf_value.RUNLOG_FILESIZE);
						continue;
					}
					
					<%-- 功能配置 --%>
					if(conf_type=="function_config"){
						$("input[name='function_sorttype'][value='"+conf_value.SORTTYPE+"']").trigger("change");
						$("#function_dailycheckoutlimit").val(conf_value.DAILYCHECKOUTLIMIT);
						
						if(!_.isEmpty(conf_value.FUNCTION)){
							var func = conf_value.FUNCTION;
							var keys = _.keys(func);
							for(var j=0;j<keys.length;j++){
								if(func[keys[j]]=="1"){
									$("#function_"+keys[j].toLowerCase()).prop("checked",true).trigger("change");
								}else{
									$("#function_"+keys[j].toLowerCase()).prop("checked",false).trigger("change");
								}
							}
						}
						
						if(!_.isEmpty(conf_value.PIN)){
							var pin = conf_value.PIN;
							$("input[name='pin_type'][value='"+pin.TYPE+"']").trigger("change");
							$("input[name='pin_input'][value='"+pin.INPUT+"']").trigger("change");
							$("input[name='pin_retrydo'][value='"+pin.RETRYDO+"']").trigger("change");
							
							$("#pin_minlength").val(pin.MINLENGTH);
							$("#pin_maxlength").val(pin.MAXLENGTH);
							$("#pin_retrytimes").val(pin.RETRYTIMES);
						}
						
						if(!_.isEmpty(conf_value.CARDINPUT)){
							var card = conf_value.CARDINPUT;
							var keys = _.keys(card);
							for(var j=0;j<keys.length;j++){
								if(card[keys[j]]=="1"){
									$("#card_"+keys[j].toLowerCase()).prop("checked",true).trigger("change");
								}else{
									$("#card_"+keys[j].toLowerCase()).prop("checked",false).trigger("change");
								}
							}
						}
						
						if(!_.isEmpty(conf_value.BOOKINPUT)){
							var book = conf_value.BOOKINPUT;
							var keys = _.keys(book);
							for(var j=0;j<keys.length;j++){
								if(book[keys[j]]=="1"){
									$("#book_"+keys[j].toLowerCase()).prop("checked",true).trigger("change");
								}else{
									$("#book_"+keys[j].toLowerCase()).prop("checked",false).trigger("change");
								}
							}
						}
						continue;
					}
					
					<%-- 超时配置 --%>
					if(conf_type=="timeout_config"){
						if(conf_value.DISPLAY=="1"){
							$("#display").prop("checked",true).trigger("change");
						}else{
							$("#display").prop("checked",false).trigger("change");
						}
						if(conf_value.PLAYSOUND=="1"){
							$("#playsound").prop("checked",true).trigger("change");
						}else{
							$("#playsound").prop("checked",false).trigger("change");
						}
						
						if(!_.isEmpty(conf_value.INFO)){
							var info = conf_value.INFO;
							var keys = _.keys(info);
							for(var j=0;j<keys.length;j++){
								$("#info_"+keys[j].toLowerCase()).val(info[keys[j]].replace(/s*S*/g,""));
							}
						}
						
						if(!_.isEmpty(conf_value.WAIT)){
							var wait = conf_value.WAIT;
							var keys = _.keys(wait);
							for(var j=0;j<keys.length;j++){
								$("#wait_"+keys[j].toLowerCase()).val(wait[keys[j]].replace(/s*S*/g,""));
							}
						}
						
						if(!_.isEmpty(conf_value.TIMED)){
							var timed = conf_value.TIMED;
							var keys = _.keys(timed);
							for(var j=0;j<keys.length;j++){
								$("#timed_"+keys[j].toLowerCase()).val(timed[keys[j]].replace(/s*S*/g,""));
							}
						}
						
						if(!_.isEmpty(conf_value.SWITCHSCREEN)){
							var scre = conf_value.SWITCHSCREEN;
							if(scre.ENABLE=="1"){
								$("#switchscreen_enable").prop("checked",true).trigger("change");
							}else{
								$("#switchscreen_enable").prop("checked",false).trigger("change");
							}
							$("#switchscreen_cycle").val(scre.CYCLE.replace(/[a-zA-Z]*/g,""));
							$("#switchscreen_waittime").val(scre.WAITTIME.replace(/[a-zA-Z]*/g,""));
						}
						continue;
					}
					
					<%-- 定时任务配置 --%>
					if(conf_type=="scheduletask_config"){
						if(conf_value.ENABLEWEEKSCHEDLER=="1"){
							$("#enableweekschedler").prop("checked",true).trigger("change");
						}else{
							$("#enableweekschedler").prop("checked",false).trigger("change");
						}
						if(conf_value.ENABLEBALANCE=="1"){
							$("#enablebalance").prop("checked",true).trigger("change");
							var time = "";
							if(!_.isEmpty(conf_value.BALANCETIME) && conf_value.BALANCETIME.length >= 6){
								time += conf_value.BALANCETIME.substring(0,2) + ":";
								time += conf_value.BALANCETIME.substring(2,4) + ":";
								time += conf_value.BALANCETIME.substring(4);
							}
							$("#balancetime").val(time);
						}else{
							$("#enablebalance").prop("checked",false).trigger("change");
							$("#balancetime").val("");
						}
						
						if(conf_value.ENABLEREBOOT=="1"){
							$("#enablereboot").prop("checked",true).trigger("change");
							var time = "";
							if(!_.isEmpty(conf_value.REBOOTTIME) && conf_value.REBOOTTIME.length >= 6){
								time += conf_value.REBOOTTIME.substring(0,2) + ":";
								time += conf_value.REBOOTTIME.substring(2,4) + ":";
								time += conf_value.REBOOTTIME.substring(4);
							}
							$("#reboottime").val(time);
						}else{
							$("#enablereboot").prop("checked",false).trigger("change");
							$("#reboottime").val("");
						}
						
						if(conf_value.ENABLESHUTDOWN=="1"){
							$("#enableshutdown").prop("checked",true).trigger("change");
							var time = "";
							if(!_.isEmpty(conf_value.SHUTDOWNTIME) && conf_value.SHUTDOWNTIME.length >= 6){
								time += conf_value.SHUTDOWNTIME.substring(0,2) + ":";
								time += conf_value.SHUTDOWNTIME.substring(2,4) + ":";
								time += conf_value.SHUTDOWNTIME.substring(4);
							}
							$("#shutdowntime").val(time);
						}else{
							$("#enableshutdown").prop("checked",false).trigger("change");
							$("#shutdowntime").val("");
						}
						
						var week = ["MON","TUE","WED","THU","FRI","SAT","SUN"];
						for(var j=0;j<week.length;j++){
							if( !_.isEmpty( conf_value[week[j]] ) ){
								var thisday = conf_value[week[j]];
								if(thisday.ENABLECLOSE == "1"){
									$("#"+week[j].toLowerCase()+"_"+"enableclose").prop("checked",true).trigger("change");
									var stime = "";
									var etime = "";
									if(!_.isEmpty(thisday.STARTUPTIME) && thisday.STARTUPTIME.length >=6){
										stime += thisday.STARTUPTIME.substring(0,2) + ":";
										stime += thisday.STARTUPTIME.substring(2,4) + ":";
										stime += thisday.STARTUPTIME.substring(4);
									}
									if(!_.isEmpty(thisday.SHUTDOWNTIME) && thisday.SHUTDOWNTIME.length >=6){
										etime += thisday.SHUTDOWNTIME.substring(0,2) + ":";
										etime += thisday.SHUTDOWNTIME.substring(2,4) + ":";
										etime += thisday.SHUTDOWNTIME.substring(4);
									}
									$("#"+week[j].toLowerCase()+"_"+"startuptime").val(stime);
									$("#"+week[j].toLowerCase()+"_"+"shutdowntime").val(etime);
									
								}else{
									$("#"+week[j].toLowerCase()+"_"+"enableclose").prop("checked",false).trigger("change");
									$("#"+week[j].toLowerCase()+"_"+"startuptime").val("");
									$("#"+week[j].toLowerCase()+"_"+"shutdowntime").val("");
								}
							}
						}
						continue;
					}
					
					<%-- 打印配置--%>
					if(conf_type=="print_config"){
						var keys = _.keys(conf_value);
						for(var j=0;j<keys.length;j++){
							var thistype = keys[j].toLowerCase();
							var thisobj = conf_value[keys[j]];
							if(_.isEmpty(thisobj)) continue;
							
							if(thistype=="head" || thistype=="tail" || thistype=="content"){
								$("#"+thistype+"_font").val(thisobj.FONT);	
								$("#"+thistype+"_size").val(thisobj.SIZE);	
							}else{
								<%-- 打印类型 --%>
								$("input[name='"+thistype+"_type'][value='"+thisobj.TYPE+"']").trigger("change");
								<%-- 超时打印 --%>
								if(thisobj.TIMEOUT=="1"){
									$("#"+thistype+"_"+"timeout").prop("checked",true).trigger("change");
								}else{
									$("#"+thistype+"_"+"timeout").prop("checked",false).trigger("change");
								}
								
								<%-- 打印日期描述，时间描述，凭证头，凭证尾 --%>
								$("#"+thistype+"_"+"date").val(thisobj.DATE);
								$("#"+thistype+"_"+"time").val(thisobj.TIME);
								$("#"+thistype+"_"+"head").val(thisobj.HEAD);
								$("#"+thistype+"_"+"tail").val(thisobj.TAIL);
								
								<%-- 换汇凭证 --%>
								if(thistype == "checkin"){
									$("#"+thistype+"_"+"barcode").val(thisobj.BARCODE);
									$("#"+thistype+"_"+"title").val(thisobj.TITLE);
									
									if(thisobj.ENABLEAMOUNT == "1"){
										$("#"+thistype+"_"+"enableamount").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
									}else{
										$("#"+thistype+"_"+"enableamount").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"amount").val("");
									}
									
									if(thisobj.ENABLECOMMENT == "1"){
										$("#"+thistype+"_"+"enablecomment").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"comment").val(thisobj.COMMENT);
									}else{
										$("#"+thistype+"_"+"enablecomment").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"comment").val("");
									}
								}
								<%-- 借出凭证 --%>
								if(thistype == "checkout") {
									$("#"+thistype+"_"+"patron").val(thisobj.PATRON);
									$("#"+thistype+"_"+"barcode").val(thisobj.BARCODE);
									$("#"+thistype+"_"+"title").val(thisobj.TITLE);
									$("#"+thistype+"_"+"duedate").val(thisobj.DUEDATE);
									
									if(thisobj.ENABLENAME == "1"){
										$("#"+thistype+"_"+"enablename").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"name").val(thisobj.NAME);
									}else{
										$("#"+thistype+"_"+"enablename").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"name").val("");
									}
									if(thisobj.ENABLEAMOUNT == "1"){
										$("#"+thistype+"_"+"enableamount").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
									}else{
										$("#"+thistype+"_"+"enableamount").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"amount").val("");
									}
									if(thisobj.ENABLEFEE == "1"){
										$("#"+thistype+"_"+"enablefee").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"fee").val(thisobj.FEE);
									}else{
										$("#"+thistype+"_"+"enablefee").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"fee").val("");
									}
								}
								<%-- 续借凭证 --%>
								if(thistype == "renew") {
									$("#"+thistype+"_"+"patron").val(thisobj.PATRON);
									$("#"+thistype+"_"+"barcode").val(thisobj.BARCODE);
									$("#"+thistype+"_"+"title").val(thisobj.TITLE);
									$("#"+thistype+"_"+"duedate").val(thisobj.DUEDATE);
									
									<%-- 续借书描述  --%>
									if(thisobj.ENABLERENEWBIBLIO == "1"){
										$("#"+thistype+"_"+"enablerenewbiblio").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"renewbiblio").val(thisobj.RENEWBIBLIO);
									}else{
										$("#"+thistype+"_"+"enablerenewbiblio").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"renewbiblio").val("");
									}
									<%-- 续借数描述 --%>
									if(thisobj.ENABLERENEWCOUNT == "1"){
										$("#"+thistype+"_"+"enablerenewcount").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"renewcount").val(thisobj.RENEWCOUNT);
									}else{
										$("#"+thistype+"_"+"enablerenewcount").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"renewcount").val("");
									}
									<%-- 未续借书描述 --%>
									if(thisobj.ENABLEUNRENEWBIBLIO == "1"){
										$("#"+thistype+"_"+"enableunrenewbiblio").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"unrenewbiblio").val(thisobj.UNRENEWBIBLIO);
									}else{
										$("#"+thistype+"_"+"enableunrenewbiblio").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"unrenewbiblio").val("");
									}
									<%-- 未续借数描述 --%>
									if(thisobj.ENABLEUNRENEWCOUNT == "1"){
										$("#"+thistype+"_"+"enableunrenewcount").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"unrenewcount").val(thisobj.UNRENEWCOUNT);
									}else{
										$("#"+thistype+"_"+"enableunrenewcount").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"unrenewcount").val("");
									}
								}
								<%-- 收款打印凭证 --%>
								if(thistype == "pay") {
									$("#"+thistype+"_"+"patron").val(thisobj.PATRON);
									$("#"+thistype+"_"+"fee").val(thisobj.FEE);
									
									if(thisobj.ENABLENAME == "1"){
										$("#"+thistype+"_"+"enablename").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"name").val(thisobj.NAME);
									}else{
										$("#"+thistype+"_"+"enablename").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"name").val("");
									}
									
									if(thisobj.ENABLEIDCARD == "1"){
										$("#"+thistype+"_"+"enableidcard").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"idcard").val(thisobj.IDCARD);
									}else{
										$("#"+thistype+"_"+"enableidcard").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"idcard").val("");
									}
									
									if(thisobj.ENABLECARDTYPE == "1"){
										$("#"+thistype+"_"+"enablecardtype").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"cardtype").val(thisobj.CARDTYPE);
									}else{
										$("#"+thistype+"_"+"enablecardtype").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"cardtype").val("");
									}
									
									if(thisobj.ENABLEDEDUCTION == "1"){
										$("#"+thistype+"_"+"enablededuction").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"deduction").val(thisobj.DEDUCTION);
									}else{
										$("#"+thistype+"_"+"enablededuction").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"deduction").val("");
									}
									
									if(thisobj.ENABLEDEPOSIT == "1"){
										$("#"+thistype+"_"+"enabledeposit").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"deposit").val(thisobj.DEPOSIT);
									}else{
										$("#"+thistype+"_"+"enabledeposit").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"deposit").val("");
									}
									
									if(thisobj.ENABLEIMPREST == "1"){
										$("#"+thistype+"_"+"enableimprest").prop("checked",true).trigger("change");
										$("#"+thistype+"_"+"imprest").val(thisobj.IMPREST);
									}else{
										$("#"+thistype+"_"+"enabledeposit").prop("checked",false).trigger("change");
										$("#"+thistype+"_"+"imprest").val("");
									}
								}
								<%-- 取款箱 --%>
								if(thistype == "cashbin") {
									$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
									$("#"+thistype+"_"+"notevalue").val(thisobj.NOTEVALUE);
									$("#"+thistype+"_"+"notecount").val(thisobj.NOTECOUNT);
									$("#"+thistype+"_"+"money").val(thisobj.MONEY);
									$("#"+thistype+"_"+"bincount").val(thisobj.BINCOUNT);
									$("#"+thistype+"_"+"binmoney").val(thisobj.BINMONEY);
								}
								<%-- 取书箱 --%>
								if(thistype == "bookbin") {
									$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
									$("#"+thistype+"_"+"binno").val(thisobj.BINNO);
									$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
									$("#"+thistype+"_"+"tolamount").val(thisobj.TOLAMOUNT);
								}
								<%-- 补取书凭证 --%>
								if(thistype == "bookrack") {
									$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
									$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
								}
								<%-- 补取卡凭证 --%>
								if(thistype == "cardbin") {
									$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
									$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
								}
							}
						}
					}
					
					<%-- 办证配置 --%>
					if(conf_type=="register_config"){
						if(conf_value.ENABLEIDCARD == "1"){
							$("#register_enableidcard").prop("checked",true).trigger("change");
						}else{
							$("#register_enableidcard").prop("checked",false).trigger("change");
						}
						
						if(conf_value.ENABLEOTHERCARD == "1"){
							$("#register_enableothercard").prop("checked",true).trigger("change");
						}else{
							$("#register_enableothercard").prop("checked",false).trigger("change");
						}
						
						if(conf_value.ENABLETEMPCARD == "1"){
							$("#register_enabletempcard").prop("checked",true).trigger("change");
							$("#register_tempcardid").val(conf_value.TEMPCARDID);
						}else{
							$("#register_enabletempcard").prop("checked",false).trigger("change");
							$("#register_tempcardid").val("");
						}
					}
					
					<%-- 本地数据库配置 --%>
					if(conf_type=="localdb_config"){
						$("#localdb_ip").val(conf_value.IP);
						$("#localdb_port").val(conf_value.PORT);
						$("#localdb_user").val(conf_value.USER);
						$("#localdb_pwd").val(conf_value.PWD);
					}
					
					<%-- 云中心配置 --%>
					if(conf_type=="center_config"){
						$("#center_ip").val(conf_value.IP);
						$("#center_port").val(conf_value.PORT);
					}
					
					<%-- ACS服务配置 --%>
					if(conf_type=="ACS_config"){
						$("input[name='ACS_sipversion'][value='"+conf_value.SIPVERSION+"']").trigger("change");
						$("#ACS_charset").val(conf_value.CHARSET);
						$("#ACS_currency").val(conf_value.CURRENCY);
						if(conf_value.NEEDLOGIN == "1"){
							$("#ACS_needlogin").prop("checked",true).trigger("change");
						}else{
							$("#ACS_needlogin").prop("checked",false).trigger("change");
						}
						
						if(conf_value.DEFAULTDECOLLATOR == "1"){
							$("#ACS_defaultdecollator").prop("checked",true).trigger("change");
							$("#ACS_decollator").val(conf_value.DECOLLATOR);
						}else{
							$("#ACS_defaultdecollator").prop("checked",false).trigger("change");
							$("#ACS_decollator").val("");
						}
						
						if(conf_value.ENABLECMD63 == "1"){
							$("#ACS_enablecmd63").prop("checked",true).trigger("change");
						}else{
							$("#ACS_enablecmd63").prop("checked",false).trigger("change");
						}
					}
				}
			}
		}
	
    <%-- 运行配置保存事件  --%>
	$("#runTempSave").on("click",function(){
		startSaveRun();
		var detailList = [];
		if(_.isEmpty(runTempObj)){ 
			
		}
		var _obj={};
		if(!_.isEmpty(runTempObj) && !_.isEmpty(runTempObj.runDetailList)){
			var list = runTempObj.runDetailList;
			for(var i=0;i<list.length;i++){
				_obj[list[i].run_conf_type] = jsonToObj(list[i].run_conf_value);
			}
		}
		$("#run_right_form .right-content").each(function(){
			var obj = {};
			var thistype = $(this).attr("data-type");
			var conftype = "";
			var confvalue = {};
			
			<%-- 基础配置，language_config、runlog_config --%>
			if(thistype=="basic"){
				confvalue.LANGUAGE = $("#basic_language").val();
				obj.run_conf_type = "language_config";
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				
				obj = {};
				confvalue = {};
				confvalue.RUNLOG_LEVEL = $("input[name='runlog_level']:checked").val();
				if($("#file").prop("checked")){
					confvalue.RUNLOG_TYPE = "file";
					confvalue.RUNLOG_FILESIZE = $("#runlog_filesize").val().replace(/[a-zA-Z]*/g,"")+"M";
				}else{
					confvalue.RUNLOG_TYPE = "";
					confvalue.RUNLOG_FILESIZE = "";
				}
				obj.run_conf_type = "runlog_config";
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				return true;
			}
			
			<%-- 功能配置function_config --%>
			if(thistype=="function"){
				conftype = thistype+"_config";
				confvalue.DAILYCHECKOUTLIMIT = $("#function_dailycheckoutlimit").val();
				confvalue.SORTTYPE = $("input[name='function_sorttype']:checked").val();
				
				var pin = {};
				var func = {};
				var card = {};
				var book = {};
				
				pin.TYPE = $("input[name='pin_type']:checked").val();
				pin.INPUT = $("input[name='pin_input']:checked").val();
				pin.RETRYDO = $("input[name='pin_retrydo']:checked").val();
				pin.RETRYTIMES = $("#pin_retrytimes").val();
				pin.MAXLENGTH = $("#pin_maxlength").val();
				pin.MINLENGTH = $("#pin_minlength").val();
				
				$("input[type='checkbox'][id^='function_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					if($(this).prop("checked")){
						func[thisname] = "1";
					}else{
						func[thisname] = "0";
					}
				});
				
				$("input[type='checkbox'][id^='card_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					if($(this).prop("checked")){
						card[thisname] = "1";
					}else{
						card[thisname] = "0";
					}
				});
				
				$("input[type='checkbox'][id^='book_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					if($(this).prop("checked")){
						book[thisname] = "1";
					}else{
						book[thisname] = "0";
					}
				});
				
				confvalue.PIN = pin;				
				confvalue.FUNCTION = func;				
				confvalue.CARDINPUT = card;				
				confvalue.BOOKINPUT = book;		
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				
			}
			
			<%-- 超时配置timeout_config --%>
			if(thistype == "timeout"){
				conftype = thistype + "_config";
				
				if($("#playsound").prop("checked")){
					confvalue.PLAYSOUND = "1";
				}else{
					confvalue.PLAYSOUND = "0";
				}
				
				if($("#display").prop("checked")){
					confvalue.DISPLAY = "1";
				}else{
					confvalue.DISPLAY = "0";
				}
				
				var info = {};
				var timed = {};
				var wait = {};
				var switchscreen = {};
				
				$("select[id^='info_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					info[thisname] = $(this).val()+"s";
				});
				
				$("select[id^='timed_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					timed[thisname] = $(this).val()+"s";
				});
				
				$("select[id^='wait_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					wait[thisname] = $(this).val()+"s";
				});
				
				if($("#switchscreen_enable").prop("checked")){
					switchscreen.ENABLE = "1"; 
					switchscreen.CYCLE = $("#switchscreen_cycle").val()+"min";
					switchscreen.WAITTIME = $("#switchscreen_waittime").val()+"s";
				}else{
					switchscreen.ENABLE = "0"; 
					switchscreen.CYCLE = "0min";
					switchscreen.WAITTIME = "0s";
				}
				
				confvalue.INFO = info;
				confvalue.TIMED = timed;
				confvalue.WAIT = wait;
				confvalue.SWITCHSCREEN = switchscreen;
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				
			}
			
			<%-- 定时任务配置scheduletask_config --%>
			if(thistype == "scheduletask"){
				conftype = thistype + "_config";
				if($("#enableweekschedler").prop("checked")){
					confvalue.ENABLEWEEKSCHEDLER = "1";
				}else{
					confvalue.ENABLEWEEKSCHEDLER = "0";
				}
				
				if($("#enablebalance").prop("checked")){
					confvalue.ENABLEBALANCE = "1";
					confvalue.BALANCETIME = $("#balancetime").val().replace(/:/g,"");
				}else{
					confvalue.ENABLEBALANCE = "0";
					confvalue.BALANCETIME = "";
				}
				
				if($("#enablereboot").prop("checked")){
					confvalue.ENABLEREBOOT = "1";
					confvalue.REBOOTTIME = $("#reboottime").val().replace(/:/g,"");
				}else{
					confvalue.ENABLEREBOOT = "0";
					confvalue.REBOOTTIME = "";
				}
				
				if($("#enableshutdown").prop("checked")){
					confvalue.ENABLESHUTDOWN = "1";
					confvalue.SHUTDOWNTIME = $("#shutdowntime").val().replace(/:/g,"");
				}else{
					confvalue.ENABLESHUTDOWN = "0";
					confvalue.SHUTDOWNTIME = "";
				}
				
				var week = ["MON","TUE","WED","THU","FRI","SAT","SUN"];
				for(var i=0;i<week.length;i++){
					var thisday = week[i];
					var thisd = thisday.toLowerCase();
					var day = {}
					if ($("#"+thisd+"_enableclose").prop("checked")){
						day.ENABLECLOSE = "1";
						day.STARTUPTIME = $("#"+thisd+"_startuptime").val().replace(/:/g,"");
						day.SHUTDOWNTIME = $("#"+thisd+"_shutdowntime").val().replace(/:/g,"");
					}else{
						day.ENABLECLOSE = "0";
						day.STARTUPTIME = "";
						day.SHUTDOWNTIME = "";
					}
					confvalue[thisday] = day;
				}
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				
			}
			
			<%-- 打印机配置 print_config --%>
			if(thistype == "print"){
				conftype = thistype + "_config";
				
				var head = {};
				var tail = {};
				var content = {};
				
				$("div[data-type='print'] div[data-type]").each(function(){
					var ptype = $(this).attr("data-type");
					var thisobj = {};
					if(ptype == "head"){
						head.FONT = $("#head_font").val();
						head.SIZE = $("#head_size").val();
						
						tail.FONT = $("#tail_font").val();
						tail.SIZE = $("#tail_size").val();
						
						content.FONT = $("#content_font").val();
						content.SIZE = $("#content_size").val();
						
						confvalue.HEAD = head;
						confvalue.TAIL = tail;
						confvalue.CONTENT = content;
					}else{
						thisobj.TYPE = $("input[name='"+ptype+"_type']:checked").val();
						if($("#"+ptype+"_"+"timeout").prop("checked")){
							thisobj.TIMEOUT = "1";
						}else{
							thisobj.TIMEOUT = "0";
						}
						thisobj.DATE = $("#"+ptype+"_"+"date").val();
						thisobj.TIME = $("#"+ptype+"_"+"time").val();
						thisobj.HEAD = $("#"+ptype+"_"+"head").val();
						thisobj.TAIL = $("#"+ptype+"_"+"tail").val();
							
						if(ptype == "checkin"){
							thisobj.BARCODE = $("#"+ptype+"_"+"barcode").val();
							thisobj.TITLE = $("#"+ptype+"_"+"title").val();
							if( $("#"+ptype+"_"+"enableamount").prop("checked")){
								thisobj.ENABLEAMOUNT = "1";
								thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
							}else{
								thisobj.ENABLEAMOUNT = "0";
								thisobj.AMOUNT = "";
							}
							if( $("#"+ptype+"_"+"enablecomment").prop("checked")){
								thisobj.ENABLECOMMENT = "1";
								thisobj.COMMENT = $("#"+ptype+"_"+"comment").val();
							}else{
								thisobj.ENABLECOMMENT = "0";
								thisobj.COMMENT = "";
							}
						}
						
						if(ptype == "checkout"){
							thisobj.BARCODE = $("#"+ptype+"_"+"barcode").val();
							thisobj.TITLE = $("#"+ptype+"_"+"title").val();
							thisobj.PATRON = $("#"+ptype+"_"+"patron").val();
							thisobj.DUEDATE = $("#"+ptype+"_"+"duedate").val();
							
							if( $("#"+ptype+"_"+"enablename").prop("checked")){
								thisobj.ENABLENAME = "1";
								thisobj.NAME = $("#"+ptype+"_"+"name").val();
							}else{
								thisobj.ENABLENAME = "0";
								thisobj.NAME = "";
							}
							
							if( $("#"+ptype+"_"+"enableamount").prop("checked")){
								thisobj.ENABLEAMOUNT = "1";
								thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
							}else{
								thisobj.ENABLEAMOUNT = "0";
								thisobj.AMOUNT = "";
							}
							
							if( $("#"+ptype+"_"+"enablefee").prop("checked")){
								thisobj.ENABLEFEE = "1";
								thisobj.FEE = $("#"+ptype+"_"+"fee").val();
							}else{
								thisobj.ENABLEFEE = "0";
								thisobj.FEE = "";
							}
							
						}
						if(ptype == "renew"){
							thisobj.BARCODE = $("#"+ptype+"_"+"barcode").val();
							thisobj.TITLE = $("#"+ptype+"_"+"title").val();
							thisobj.PATRON = $("#"+ptype+"_"+"patron").val();
							thisobj.DUEDATE = $("#"+ptype+"_"+"duedate").val();

							if( $("#"+ptype+"_"+"enablerenewbiblio").prop("checked")){
								thisobj.ENABLERENEWBIBLIO = "1";
								thisobj.RENEWBIBLIO = $("#"+ptype+"_"+"renewbiblio").val();
							}else{
								thisobj.ENABLERENEWBIBLIO = "0";
								thisobj.RENEWBIBLIO = "";
							}

							if( $("#"+ptype+"_"+"enableunrenewbiblio").prop("checked")){
								thisobj.ENABLEUNRENEWBIBLIO = "1";
								thisobj.UNRENEWBIBLIO = $("#"+ptype+"_"+"unrenewbiblio").val();
							}else{
								thisobj.ENABLEUNRENEWBIBLIO = "0";
								thisobj.UNRENEWBIBLIO = "";
							}

							if( $("#"+ptype+"_"+"enablerenewcount").prop("checked")){
								thisobj.ENABLERENEWCOUNT = "1";
								thisobj.RENEWCOUNT= $("#"+ptype+"_"+"renewcount").val();
							}else{
								thisobj.ENABLERENEWCOUNT = "0";
								thisobj.RENEWCOUNT = "";
							}

							if( $("#"+ptype+"_"+"enableunrenewcount").prop("checked")){
								thisobj.ENABLEUNRENEWCOUNT = "1";
								thisobj.UNRENEWCOUNT = $("#"+ptype+"_"+"unrenewcount").val();
							}else{
								thisobj.ENABLEUNRENEWCOUNT = "0";
								thisobj.UNRENEWCOUNT = "";
							}
						}
						if(ptype == "pay"){
							thisobj.PATRON = $("#"+ptype+"_"+"patron").val();
							thisobj.FEE = $("#"+ptype+"_"+"fee").val();
							
							if( $("#"+ptype+"_"+"enablename").prop("checked")){
								thisobj.ENABLENAME = "1";
								thisobj.NAME = $("#"+ptype+"_"+"name").val();
							}else{
								thisobj.ENABLENAME = "0";
								thisobj.NAME = "";
							}
							
							if( $("#"+ptype+"_"+"enableidcard").prop("checked")){
								thisobj.ENABLEIDCARD = "1";
								thisobj.IDCARD = $("#"+ptype+"_"+"idcard").val();
							}else{
								thisobj.ENABLEIDCARD = "0";
								thisobj.IDCARD = "";
							}
							
							if( $("#"+ptype+"_"+"enablecardtype").prop("checked")){
								thisobj.ENABLECARDTYPE = "1";
								thisobj.CARDTYPE = $("#"+ptype+"_"+"cardtype").val();
							}else{
								thisobj.ENABLECARDTYPE = "0";
								thisobj.CARDTYPE = "";
							}
							
							if( $("#"+ptype+"_"+"enablededuction").prop("checked")){
								thisobj.ENABLEDEDUCTION = "1";
								thisobj.DEDUCTION = $("#"+ptype+"_"+"deduction").val();
							}else{
								thisobj.ENABLEDEDUCTION = "0";
								thisobj.DEDUCTION = "";
							}
							
							if( $("#"+ptype+"_"+"enabledeposit").prop("checked")){
								thisobj.ENABLEDEPOSIT = "1";
								thisobj.DEPOSIT = $("#"+ptype+"_"+"deposit").val();
							}else{
								thisobj.ENABLEDEPOSIT = "0";
								thisobj.DEPOSIT = "";
							}
							
							if( $("#"+ptype+"_"+"enableimprest").prop("checked")){
								thisobj.ENABLEIMPREST = "1";
								thisobj.IMPREST = $("#"+ptype+"_"+"imprest").val();
							}else{
								thisobj.ENABLEIMPREST = "0";
								thisobj.IMPREST = "";
							}
						}
						
						if(ptype == "cashbin"){
							thisobj.OPERATOR = $("#"+ptype+"_"+"operator").val();
							thisobj.NOTEVALUE = $("#"+ptype+"_"+"notevalue").val();
							thisobj.NOTECOUNT = $("#"+ptype+"_"+"notecount").val();
							thisobj.MONEY = $("#"+ptype+"_"+"money").val();
							thisobj.BINCOUNT = $("#"+ptype+"_"+"bincount").val();
							thisobj.BINMONEY = $("#"+ptype+"_"+"binmoney").val();
						}
						
						if(ptype == "bookbin"){
							thisobj.OPERATOR = $("#"+ptype+"_"+"operator").val();
							thisobj.BINNO = $("#"+ptype+"_"+"binno").val();
							thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
							thisobj.TOLAMOUNT = $("#"+ptype+"_"+"tolamount").val();
						}
						
						if(ptype == "bookrack"){
							thisobj.OPERATOR = $("#"+ptype+"_"+"operator").val();
							thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
						}
						
						if(ptype == "cardbin"){
							thisobj.OPERATOR = $("#"+ptype+"_"+"operator").val();
							thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
						}
						
						confvalue[ptype.toUpperCase()] = thisobj;
					}

				});
				
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				
			}
			
			<%-- 办证配置 register_config --%>
			if(thistype == "register"){
				conftype = thistype + "_config";
				if($("#"+thistype+"_"+"enableidcard").prop("checked")){
					confvalue.ENABLEIDCARD = "1";
				}else{
					confvalue.ENABLEIDCARD = "0";
				}
				
				if($("#"+thistype+"_"+"enableothercard").prop("checked")){
					confvalue.ENABLEOTHERCARD = "1";
				}else{
					confvalue.ENABLEOTHERCARD = "0";
				}
				
				if($("#"+thistype+"_"+"enabletempcard").prop("checked")){
					confvalue.ENABLETEMPCARD = "1";
					confvalue.TEMPCARDID = $("#"+thistype+"_"+"tempcardid").val();
				}else{
					confvalue.ENABLETEMPCARD = "0";
					confvalue.TEMPCARDID = "";
				}
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
			}
			<%-- 本地数据库配置 localdb_config --%>
			if(thistype == "localdb"){
				conftype = thistype + "_config";
				confvalue.IP = $("#"+thistype+"_"+"ip").val();
				confvalue.PORT = $("#"+thistype+"_"+"port").val();
				confvalue.USER = $("#"+thistype+"_"+"user").val();
				confvalue.PWD = $("#"+thistype+"_"+"pwd").val();
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
			}
			<%-- 云中心配置 center_config --%>
			if(thistype == "center"){
				conftype = thistype + "_config";
				confvalue.IP = $("#"+thistype+"_"+"ip").val();
				confvalue.PORT = $("#"+thistype+"_"+"port").val();
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
			}
			
			<%--  acs服务配置 ACS_config --%>
			if(thistype == "ACS"){
				conftype = thistype + "_config";
				confvalue.CHARSET =  $("#"+thistype+"_"+"charset").val();
				confvalue.CURRENCY =  $("#"+thistype+"_"+"currency").val();
				confvalue.SIPVERSION =  $("input[name='ACS_sipversion']:checked").val();
				if($("#"+thistype+"_"+"defaultdecollator").prop("checked")){
					confvalue.DEFAULTDECOLLATOR =  "1";
					confvalue.DECOLLATOR =  $("#"+thistype+"_"+"decollator").val();
				}else{
					confvalue.DEFAULTDECOLLATOR =  "0";
					confvalue.DECOLLATOR = "";
					
				}
				if($("#"+thistype+"_"+"enablecmd63").prop("checked")){
					confvalue.ENABLECMD63 =  "1";
				}else{
					confvalue.ENABLECMD63 =  "0";
					
				}
				if($("#"+thistype+"_"+"needlogin").prop("checked")){
					confvalue.NEEDLOGIN =  "1";
				}else{
					confvalue.NEEDLOGIN =  "0";
				}
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
			}
		})
		
		runTempSubmit.runDetailList = detailList;
		
		//这里的话原来是模板的情况下才需要执行compareRunTemp 函数
		if($("#runTemp").val()!="-1"){
			compareRunTemp();
		}else{
			//不是选中模板的话直接删除 、新增操作
			runTempSubmit.device_tpl_type="1";
		}
		UpdateRundata();
		
		endSaveRun();
	})
	
	
	
	<%-- 对比数据，判断是否使用模板 --%>
	function compareRunTemp(){
		if(!_.isEmpty(runTempObj) && !_.isEmpty(runTempSubmit)){
			var idx = runTempObj.device_tpl_idx;
			if(runTempObj.runDetailList.length==runTempSubmit.runDetailList.length){
				var list1 = runTempObj.runDetailList;
				var list2 = runTempSubmit.runDetailList;
				
				<%-- 按照类型排序 --%>
				list1 = _.sortBy(list1,"run_conf_type");
				list2 = _.sortBy(list2,"run_conf_type");
				
				for(var i=0;i<list1.length;i++){
					var conf_type1 = list1[i].run_conf_type;
					var conf_type2 = list2[i].run_conf_type;
					
					if(conf_type1!=conf_type2){
						<%-- 如果不相等相等，则使用自定义数据 --%>
						runTempSubmit.device_tpl_type = "1";
						runTempCustom[idx] = runTempSubmit;<%-- 保存到该选项的自定义数据中 --%>
						//runTempType("1");
						break;
					}
					
					var conf_value1 = list1[i].run_conf_value;
					var conf_value2 = list2[i].run_conf_value;
					
					if(typeof conf_value1 != 'object'){
						conf_value1 = jsonToObj(conf_value1);
					}
					
					var eql = _.isEqual(conf_value1,conf_value2);
					if(!eql){
						<%-- 如果不相等相等，则使用自定义数据 --%>
						runTempSubmit.device_tpl_type = "1";
						runTempCustom[idx] = runTempSubmit;<%-- 保存到该选项的自定义数据中 --%>
						//runTempType("1");
						break;
					}else{
						
					}
					<%-- 如果相等，则使用模板 --%>
					runTempSubmit.device_tpl_type = "0";
					delete runTempCustom[idx];<%-- 删除该选项的自定义数据 --%>
					//runTempType("0");
				}
			}else{
				<%-- 如果不相等相等，则使用自定义数据 --%>
				runTempSubmit.device_tpl_type = "1";
				runTempCustom[idx] = runTempSubmit;<%-- 保存到该选项的自定义数据中 --%>
				//runTempType("1");
			}
		}else{
			//如果保存不是模板的话 ，就是自定义数据
			//layer.alert("请选择模板");
		}
	}
	
	

</script>