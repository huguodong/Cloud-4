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
<%@ include file="../common/global.jsf" %>
</head>
<body>
<div class="help-page">
<div class="g-loading"></div>
	<%@include file="../common/header.jsf" %>
	<div class="page-title-bar">
		<span class="title">在线查询日志</span>
		<div class="form-wrap fr">
			<div class="g-inputdate"><input name="startDate" type="text" placeholder="输入日期" class="g-input datepicker"><span class="icon"></span></div> 
			<span class="fl">-</span>
			<div class="g-inputtime"><input name="startTime" type="text" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
			<div class="g-inputdate"><input name="endDate" type="text" placeholder="输入日期" class="g-input datepicker"><span class="icon"></span></div> 
			<span class="fl">-</span>
			<div class="g-inputtime"><input name="endTime" type="text" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
			<div class="btn search">查询</div>
		</div>
	</div>
	<div class="log-area">
		1. LICENSE GRANT.  
		TeamViewer Software is licensed and not sold.  TeamViewer grants you, from date of first use for the duration of the license you use or have purchased, or otherwise as set out in the applicable Software documentation or unless terminated in accordance with the terms herein, the following rights only if you comply with all of the terms of this EULA.   The license granted herein is subject to whether you use the Software for personal or commercial use and in accordance with the type of license you have purchased, as follows: (i) Private Use of TeamViewer, if your use of the Software is for your private use, you may install the Software on private computers to be used in any one session at any one time; (ii) TeamViewer Business, you are allowed to install the Software on one computer and are only allowed to run one session on any one computer at any one time and during this one time you are allowed to run up to three sessions concurrently on that one computer; (iii) on  TeamViewer Premium, you are allowed to install the Software on an unlimited number of computers, out of all of the computers, you are only allowed to run one concurrent session at any time.  However from that single computer you are allowed to establish  up to 10 connections at any one time; or (iv) TeamViewer Corporate, you are allowed to install the Software on an unlimited number of computers, out of all of the computers, you are only allowed to run three concurrent sessions at any one time. However from that single computer you are allowed to establish up to 15 connections at any one time . Additional concurrent users of the Software will require additional license purchases. This EULA covers any updates, new releases or enhancement(s) of the Product, which TeamViewer may make available to you from time to time for purchase.  

		2. SUPPORT. 
		Any support that TeamViewer抯 in its sole determination, may provide to you shall consist of: (i) telephone or electronic support to you in order to help you locate and, on your own, correct problems with the Product and / or (ii) supplying extensions, enhancements and other changes that TeamViewer may make to the Product and which is made publicly available, without additional charge, to other licensees of the Product that are enrolled in Support.  

		3. CONFIDENTALITY. 
		The Product, in all formats existing, are a trade secret of and proprietary to TeamViewer, its suppliers and / or licensors, including but not limited to, the specific internal code, design and structure of individual programs and software, the display and associated interface information.  You shall maintain Product confidence and prevent disclosure of the same by using a reasonable degree of care. You shall not disclose the confidential aspects of the Product, or part thereof, to anyone for any purpose.

		4. BETA TESTING. Beta versions of Product may be provided to you WITHOUT WARRANTY OF ANY KIND,IS?AND SUBJECT TO THE CONFIDENTIALITY CONDITIONS ABOVE.  Such provision is done so only for the purpose of assisting TeamViewer with testing functionality or compatibility and on the express condition that you provide TeamViewer with truthful, accurate and complete feedback, comments, analysis in whatever format you may wish, or are directed by, to provide such to TeamViewer (揅ONTRIBUTION?.  You expressly acknowledge that your participation in any beta testing is undertaken by you on a volunteer basis and that you shall have no right in the beta Product or Contribution, whether in original form (as provided to you) or in respect of any derivative work (whether or not based upon, in whole or in part, on any participation or feedback you may make).  Notwithstanding the foregoing, you agree to grant to TeamViewer a royalty ?free, perpetual, transferable license to commercially use and sub-license in TeamViewer抯 sole discretion, any and all Contribution.  

		5. INTELLECTUAL PROPERTY RIGHTS: The Product is protected by world-wide copyright, trademark and other intellectual property laws and treaties. You hereby acknowledge that third-party software may be incorporated into the Product.  TeamViewer, its licensors and any applicable third party, own all title, copyright, and other intellectual property rights in and to the Product.

		6. RESTRICTIONS. Except as otherwise expressly provided under this EULA, you shall have no right and you shall not permit any third party to: (i) transfer, assign or sublicense the limited license rights granted to you in this EULA to any other person, or entity, or use the Product on any equipment other than an authorised Unit, and you acknowledge that any attempted transfer, assignment, sublicense or unauthorised use shall be void; (ii) make error corrections to or otherwise modify or adapt the Product or decompile, decrypt, disassemble, reverse engineer or attempt to reconstruct or discover any source code or underlying ideas, algorithms, file formats or programming or interoperability interfaces of Product or of any files contained or generated using Product by any means whatsoever or otherwise reduce the Product to human-readable form, except to the minimum extent expressly permitted under applicable law notwithstanding this restriction;; or (iii) circumvent or provide the method or means to circumvent any TMP in the Product; or (iv) use the Product in any manner not expressly authorised herein.

		7. RESERVATION OF RIGHTS. You acknowledge that all intellectual property rights in the Product, throughout the world, belong to TeamViewer.  You acknowledge that rights in the Product are licensed (not sold) to you, and that you have no rights or title in, or to, the Product other than the right to use them in accordance with the terms of this EULA. TeamViewer and its licensors retain ownership of all copies of the Product and reserves all rights not expressly granted to you under this EULA.

		8. CONTENT UPDATES; TECHNOLOGICAL PROTECTION MEASURES (揟PM?. Some Products require, for optimum use and TeamViewer, in its sole discretion, may provide to you, in a number of formats (feeds, definition files etc), content that is automatically, synchronised or updated from time to time with TeamViewer抯 servers or systems (揅ONTENT?.  Such content may be provided for a limited time, from time to time, or in accordance with an applicable and valid Support agreement. The Products may also contain technological protection measures, for instance a license key or code, that prevents unlimited copying, or limit time of use or functionality in accordance with the type of license that you purchase (揟PM?. You consent that the Product will automatically contact TeamViewer to receive Content and, in addition, if and when any of the following events occur: (i) the Product is successfully installed by you; (ii) you fail to install the Product successfully; (iii) the Product has been successfully configured; (iv) there are changes to the Product抯 license key or TPM; and / or (v) the Product is uninstalled. TeamViewer has offices worldwide and you acknowledge and consent that any data collected may be sent to any TeamViewer office or affiliate for processing, including locations outside of the USA and European Union.

		9. TERMINATION. Without prejudice to any other rights, TeamViewer may terminate, without notice, this EULA and your right to use the Product, if you do not abide by its terms, in which case you must cease all use of the Product, destroy all copies (including any components) of the Product, or, at TeamViewer抯 request, return such copies to TeamViewer. Sections 1, 3, 4, 7, 10, 11, 12, 13, 14 and 15 shall survive any termination of this EULA.

		10. CONSENT TO USE OF DATA. TeamViewer respects your privacy and shall adhere to the terms of its Privacy Policy which can be found at: www.teamviewer.com/privacy.  You agree that TeamViewer, its subsidiaries and affiliates may collect and use personally identifiable information, not limited to technical information about your computer, system and application software that is gathered periodically and automatically to facilitate and improve upon the provision of software updates, Support, Content, TPM and other services to you. 

		11. DATA PROTECTION. Each party shall comply with its respective obligations under applicable data protection laws (揇PL?. Neither party shall do any act that puts the other party in breach of its obligations as per this Section nor shall nothing in this EULA be deemed to prevent any party from taking any action it reasonably deems necessary to comply with DPL. You agree that during the course of this EULA: (i) in respect of data you collect. you alone shall determine the purposes for which and the manner in which personal data are, or will be, processed; (ii) you are the data controller in respect of all personal data you may process; and (iii) you consent and have obtained consent from the data subject (third party) to send their personal data to TeamViewer in countries outside the European Union or USA. TeamViewer agrees that, with your express consent, it is the data processor in respect of the personal data processed as provided by you, during the course of this EULA. You warrant and undertake that any instructions given by you to TeamViewer will at all times be in accordance with the requirements of DPL. You shall fully indemnify TeamViewer against any loss, damages, liability and costs (including attorneys?fees) incurred by TeamViewer as a result of any breach of DPL by you. You agree that TeamViewer may disclose any information held about you or via you to legitimate judicial or legal authorities.

		12. EXPORT CONTROLS. You agree that the Product will not be used, shipped, transferred or exported into any country or to anyone: (i) which the EU or UN has embargoed goods; (ii) where the national legislation of the relevant EU Member State has embargoed goods; (iii) listed in any enacted Common Position on restrictive measures imposed by the EU; (iv) on the U.S. Treasury Department抯 list of Specially Designated Nationals or the U.S. Commerce Department抯 Table of Deny Orders; or (v) in any manner prohibited by the EU Common Foreign and Security Policy, the United States Export Administration Act, or any other export laws or regulations. By using the Product you represent that you are not located in, under the control of, or a national or resident of any such country or on any such list and you take full and sole responsibility for such use.
	</div>
</div>
<script type="text/javascript">
$(function(){
	var isSendOrder=false;
	var isLoadScroller=false;
	var operator = <shiro:principal/>;
	//日志查询按钮
	$(".btn.search").on("click",function(){
		if(!operator){
			alert("请先登录系统！");
			return;
		}
		var device_id="${device_id}";
		
		var startDate=$("input[name=startDate]").val();
		var endDate=$("input[name=endDate]").val();
		var startTime=$("input[name=startTime]").val();
		var endTime=$("input[name=endTime]").val();
		if(!device_id)return;
		if(!startDate){
			layer.alert("开始时间不能为空");
			return;
		}
		if(!endDate){
			layer.alert("结束时间不能为空");
			return;
		}
		console.log(startDate+" "+startTime);
		console.log(endDate+" "+endTime);
		return ;
		//获取信息 包括device_id device_idx
		//3 代表 查询日志
		if(!isSendOrder){
			var params={
					  "operator_id":operator.operator_id,
					  "device_id":device_id,
					  "control":"1",
					  "control_info":"3",
					  "start_time":start_time,
					  "end_time":end_time
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

			var current_page=1;
			var queryParam={
					"operator_id":operator.operator_id,
					"device_id":device_id,
					"start_time":start_time,
					"end_time":end_time,
					"current_page":current_page
			};
			console.log(queryParam);
			/*if(!isLoadScroller){
				var log_areaH=$(window).height()-80-20-150;
				$('div.log-area').scrollPagination({  
			        'contentPage': '${pageContext.request.contextPath}/device/queryDeviceLog',  
			        // the url you are fetching the results  
			        'contentData': {"req":JSON.stringify(queryParam)},  
			        // these are the variables you can pass to the request, for example: children().size() to know which page you are  
			        'scrollTarget': $(window),  
			        // who gonna scroll? in this example, the full window  
			        'heightOffset':20,  
			        // it gonna request when scroll is 10 pixels before the page ends  
			        'beforeLoad': function() {  
			        	//console.log($(this)[0].contentData);//setting currentPage
			        },  
			        'afterLoad': function(elementsLoaded) { // after loading content, you can use this function to animate your new elements  
			        	//queryParam["current_page"] = queryParam.current_page + 1;  
			        	//console.log("page:"+queryParam["current_page"]);
			        	$(elementsLoaded).fadeInWithDelay();  
			        },
			        loader:function(data){
			        	//$('div.log-area').append('<li><p>'+data+'</p></li>');
			        	console.log(data);
			        	if(data.state==true&&data.message=="SUCCESS"){
			        		$("div.file-check").append('<div class="log-area"><li><p>'+data+'</p></li></div>');
				        	$("div.file-check").find(".log-area").css("height",log_areaH);
			        	}else if(data.state==true&&data.message=="DELETE"){
			        		//alert("请等候");
			        	}else{
			        		$("div.file-check").append('<div class="log-area bottom"><li><p>没有消息</p></li></div>');
			        		$("div.file-check").remove(".log-area.bottom");
			        	}
			        }
			    }); 
				isLoadScroller=true;
			} */
			
		 	$.ajax({
					type:"POST",
					url:"${pageContext.request.contextPath}/device/queryDeviceLog",
					data:{"req":JSON.stringify(queryParam)},
					success:function(data){
						console.log(data);
						if(data&&data.state==true&&data.message!="DELETE"){
							var arr=data.result;
							//var page1=arr[0];//第0页内容
							//nowPage pages contents
							//$("div.log-area").html(page1.contents);
							console.log(arr);
							var html="";
							for(var page in arr){
								var pageObj=arr[page];
								if(pageObj){
									var contents=pageObj.contents;
									var contentDiv='<div class="log-area" style="word-wrap: break-word; word-break: normal;">';
									contentDiv+=contents+'</div>';
									html+=contentDiv;
								}
							}
							$("div.log-area-list").html(html);
							isSendOrder=false;
						} else{
							//$("div.log-area").html("");
							//alert("请稍等");//没有内容，查询过一次之后
							$("div.log-area-list").html("<div class=\"log-area\">请稍后继续点击查询按钮</div>");
							//test
						}
					}
				});				
	});
	$(".datepicker" ).datepicker({
		numberOfMonths:1,//显示几个月  
		showButtonPanel:false,//是否显示按钮面板  
		dateFormat: 'yy-mm-dd',//日期格式  
		clearText:"清除",//清除日期的按钮名称  
		closeText:"关闭",//关闭选择框的按钮名称  
		yearSuffix: '', //年的后缀  
		showMonthAfterYear:true,//是否把月放在年的后面  
		defaultDate:'2016-04-19',//默认日期  
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
		timeFormat: 'hh:mm tt',
		currentText:"现在",
		closeText:"完成",
		timeOnlyTitle:"",
		amNames:["AM"],
		pmNames:["PM"],
		timetext:"时间",
		hourText:"时",
		minuteText:"分",
		secondText:"秒"
	});


});
</script>
</body>
</html>


