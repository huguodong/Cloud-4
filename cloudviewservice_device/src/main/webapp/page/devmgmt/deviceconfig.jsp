<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
System.out.println(path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println(basePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<script type="text/javascript" src="<%=basePath %>/js/jquery.min.js"></script> 
<%-- 
<link rel="stylesheet" type="text/css" href="<%=basePath %>/static/css/style.css" /> --%>


<style type="text/css">
/* 设置配置-----fieldset--------下方部分 */
.equipment-zhuce .field-title{
	position: relative;
	text-align: center;
	margin-bottom: 30px;
	width: 800px;
	height: 2px;
	background-color: #dddddd;
}
.equipment-zhuce .field-title .word{
	position: absolute;
	margin-top: -7px;
	margin-left: -50px;
	width: 100px;
	line-height: 1;
	background-color: #FFF;
}
/* 设置配置-----fieldset--------下方部分 */
.equipment-zhuce .form-area{
	width: 800px;
	margin:auto;
}
.equipment-zhuce .form-wrap .item{
	margin-bottom: 22px;
	overflow:hidden;zoom:1;
}
/* .equipment-zhuce .form-areaDiv{
	width: 800px;
	margin:17px 100px 17px 100px;
} */
.equipment-zhuce .form-topbottom{
	padding: 12px 1px;
	overflow:hidden;zoom:1;
	height:12px;
}
.equipment-zhuce .form-wrap li{
	width: 100%;
}
.equipment-zhuce .form-wrap li .left{
	float: left;
	padding-right: 10px;
	text-align: left;
	width: 220px;
	line-height: 28px;
}
.equipment-zhuce .form-wrap li .right{
	margin-left:20px;
	float: left; 
	width: 200px;
}
.equipment-zhuce .form-wrap li .errormsgright{
	margin-left:20px;
	float: left; 
	width: 300px;
}
.equipment-zhuce .form-wrap li .default{
    margin-left: 50px;
	float: left;
	width: 300px;
}

.error-msg{
	float: right;
	width: 150%;
}
.equipment-fenzu .page-title-bar .g-select{
	width: 128px;
}
.upload-dialog .form-wrap {
    padding: 60px 0 120px;
}
.page-title-bar .form-wrap{
	padding-top: 4px;
	padding-bottom:4px;
	color:#888;
	line-height: 28px;
}
.equipment-zhuce .form-area .g-select select{
	width: 230px;
}
.equipment-zhuce .form-wrap li .right .g-select g-input{
	display: block;
	padding-left: 6px;
	height: 35px;
	border-radius: 2px;
	color:#333;
	background-color:#f6f6f6;
	border:1px solid #DDD;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;x;
}
.equipment-zhuce .content-area{
	border:1px solid #DDD;
	background-color: #FFF;
	border-radius: 1px;	
	height:99%;
	overflow:auto;
	-webkit-border-radius:10px;
	-moz-border-radius:10px;
	border-radius: 15px;
}
.main-top{
	box-sizing: border-box;
	padding:10px 20px 0px 20px;
	height:60px;
	width:100%;
}
.main{
	box-sizing: border-box;
	padding:10px 20px 0px 20px;
	/* height: 900px;  */
	width:100%;
}
.main-bottom{
	/* position:fixed; 
	bottom :1px; */
	box-sizing: border-box;
	padding:10px 20px 6px 20px;
	height:60px;
	width:auto;
	width:100%;
}
.equipment-zhuce .g-submit {
    display: block;
    margin: 6px auto 0px; 
}
.page-bottom{
	padding:0 20px;
	height: 100%;
	line-height: 50px;
	border-top: 1px solid #ddd;
	background-color: #fff;
	overflow: hidden;
} 
</style>
</head>
<body>
<div id="main">
<div class="page-title-bar">
	<a href="#" class="back-btn"></a>
	<span class="title">设备端界面显示配置<a href="#" class="g-help"></a></span>
</div>
<div class="main-top equipment-zhuce">
	<div class="content-area width-limit86">
		<div class="form-area form-areaDiv">
			<div id="cfghtml" class="form-wrap form-topbottom">
				<!-- <div  class="cfgDiv"></div> -->
				<!-- <div  class="contentDiv"></div> -->
			</div>
		</div>
	</div>
</div>
<div id="mainDiv" class="main equipment-zhuce">
	<div id="innerDiv" class="content-area width-limit86"">
		<div class="form-area">
			<div id="content" class="form-wrap">
			</div>
		</div>
	</div>
</div>
<div class="main-bottom equipment-zhuce">
	<div class="content-area width-limit86"">
		<div class="form-area">
			<div class="page-bottom">
				<div class="item">
					<li>
						<input type="button" class="g-submit g-btn-blue" value="保存" onclick="submit()"></input>
					</li>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="zhuce-result">
	<div class="status-word">
		<!-- 请稍等，设备数据正在匹配 -->
		<span>配置正在保存....</span><%-- <s:message code="deviceedit.jsp.dataismatching"/> --%>
	</div>
	<div class="tips-word">
		<!-- 你的设备数据正在更新，请稍等片刻。 -->
		<span>数据正在更新，请稍等片刻!</span><%-- <s:message code="deviceedit.jsp.dataisupdate"/> --%>
	</div>
</div>
<div class="load-dialog">
	<span class="line"></span>
	<div class="load-gif"></div>
	<div class="word">请稍等，正在还原文件···</div>
</div>
</div>
</body> 


<script type="text/javascript">
function copyText(id){
	console.log(this);
	var value = $("#"+id+"1").value;
	console.log(value);
	if(value == "$S"){
		$("#"+id).css("display","block");
	}
	
}
var library_idx;
var device_type;
var deviceId;
var library_idx_d;
var device_idx_d;
var cfghtml = '';
var returnInfo=new Object();
$(function(){
			  <%-- <%--用于点击返回时间返回当前的页码的设备管理页面-%> --%>
			var id=<%=request.getParameter("id")%>;
		
			/* param={"device_idx":id.idx}; */
			//页面点击返回按钮
			returnInfo.currentpage=id.currentpage;
			returnInfo.library_idx=id.library_idx;
			returnInfo.device_type=id.device_type;
			returnInfo.device_group_idx=id.device_group_idx;
			returnInfo.device_id=id.device_id;
			returnInfo.pageSize=id.pageSize;
			
			returnInfo.device_typex=id.device_typex;
			returnInfo.device_idx=id.device_idx;
			
			var winH = $(window).height();
			var headerH = $(".g-header").outerHeight();
			var pageTitleBar = $(".page-title-bar").outerHeight();
			var maintop = $(".main-top").outerHeight();
			var mainbottom = $(".main-bottom").outerHeight();
			
			$(".main").css(
					{
						"height" : winH - headerH - maintop
								- pageTitleBar - mainbottom
					});
			
			 cfghtml += '<div id="device_div" class="item" style="display:none"><ul><li>';
			 cfghtml += '<div class="left"><s:message code="deviceedit.jsp.devname"/></div>';
			 cfghtml += '<div class="right">';
			 cfghtml += '<div class="g-select"><select id="device"></select>';
			 cfghtml += '<span class="arr-icon"></span>';
			 cfghtml += '</div></div>';
			 cfghtml += '</li></ul></div>';
				 
			 
			 
			 
			 $("#cfghtml").html(cfghtml);
			 
		     var oper = <shiro:principal/>;
		     var operator_type =  oper.operator_type; 
		     var libid = id.library_idx;
		     
		     if(operator_type=="3"||operator_type=="0"||operator_type=="1"){
					var data= {};
					
					//从页面获取device_id、device_typex
					var device_id = returnInfo.device_idx;
					var device_idx = returnInfo.device_id;
					//alert(device_id);
					var device_typex = returnInfo.device_typex;
					//通过deviceIdx查询设备的deviceId
					param = {
							"device_id":device_id
					   		//"library_idx":library_idx	
					},
					//查询id为device_id的设备的详细信息
					$.ajax({
					 	type:"post",
					 	url:"${pageContext.request.contextPath}/json/getLibDevData",
					 	data:{"libdeviceId":JSON.stringify(param)},
					 	success:function(data){
					 		data = data.result;
					 		console.info("data",data);
					 		//查询后获得设备所属的library_idx、device_name、device_model_idx
						   	library_idx = data[0].library_idx;
						   	var device_name = data[0].device_name;
						   	var device_model_idx = data[0].device_model_idx;
						   	var device_idx = data[0].device_idx;
						   	console.info("data[0]",data[0]);
					 		//将deice的所有option项先删除
					 		$("#device").find("option").remove();
					 		//显示设备显示的div
							$("#device_div").show();
							$("select[id='device']").css("display","block");	
					
												 			
					 		$("#device").find("option").remove();
					 			
					 		op = new Option(device_id+"("+device_name+")",device_model_idx);
					 		deviceSel = document.getElementById("device").add(op);
												 			
							showInterface(library_idx,device_idx);
					 	}
					});
			}
			
		    
			$("#version_stamp").val(id.version_stamp);
			
		    $("a.back-btn").on("click",function(){
		 		if(returnInfo){ 
		 			$("#mainframe").load('<%=basePath%>device/main',{"returnInfo":JSON.stringify(returnInfo)},function(){
		 			});
		 		} 
		 	});
});
function showInterface(library_idx,deviceIdx){
	var data1;
	var html = '';
	
	library_idx_d = library_idx;
	device_idx_d = deviceIdx;
	
	getParam = {
			"lib_id":library_idx_d,
			"flag":"device"
	},
	//发送设备的图书馆所属libId，获取相关的界面配置选项，遍历的将获取的数据放入obj数组中
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/json/getData",
		data:{
				"libdeviceParam":JSON.stringify(getParam)
		},
		success:function(data){
		     var obj = {};
			
		     console.info("data",data);
		     console.log("获取界面配置成功");
		     
		     data = data.result;      //将data中result中的数据取出来
		     for(var i in data){
		    	 if(data[i].options.value.indexOf("|")>0){
		    		 data[i].type = 0;
		    		 data[i].options.value = data[i].options.value.split("|");
		    		 data[i].options.value.unshift(-1);
		    		 data[i].options.caption = data[i].options.caption.split("|");
		    		 data[i].options.caption.unshift("默认");
		    		 data[i].options.data = [];
		    	
		    		 for(var k in data[i].options.value){
		    			 //如果默认值等于value数组中的第k个值，则将caption数组中对应的第k个值赋值
		    			 if(data[i].defaultValue==data[i].options.value[k]){
		    					 data[i].defaultValue = data[i].options.caption[k];
		    			 }
		    			 
		    			 //option后添加data数组存储value/caption
		    			 data[i].options.data[k] = [data[i].options.value[k],data[i].options.caption[k]];
		    		 }
		    		 console.info("data:",data[i].options.data);
		    	 }else{
		    		 data[i].type = 1;
		    		 data[i].options.data = [];
		    		
		    		 data[i].options.data.push([-1,'默认']);
		    		 data[i].options.data.push([data[i].options.value,data[i].options.caption]);
		    	 }
		    	 //如果fieldset字段存在
		    	 if(data[i].fieldset != null){
		    		//如果fieldset_label不存在，则将label值赋给fieldset_label
		    		 if(data[i].fieldset_label == null || data[i].fieldset_label == undefined){
		    			 data[i].fieldset_label = data[i].label;
		    		 }
		    		 
		    		 //objs数组中key为fieldset_label的value为空，则赋给key为fieldset_label的value为空数组
		    		 if(obj[data[i].fieldset_label] == null || obj[data[i].fieldset_label] == undefined){
		    			 obj[data[i].fieldset_label] = [];
		    		 }
		    		 //填充value值
		    		 obj[data[i].fieldset_label].push(data[i]);
		    	 }else{
		    		 data[i].fieldset = data[i].name;
		    		 //如果fieldset_label不存在，则将label值赋给fieldset_label
		    		 if(data[i].fieldset_label == null || data[i].fieldset_label == undefined){
		    			 data[i].fieldset_label = data[i].label;
		    		 }
		    		 if(obj[data[i].label] == null || obj[data[i].label] == undefined){
		    			 obj[data[i].label] = [];
		    		 }
		    		 obj[data[i].label].push(data[i]);
		    	 }
		   }
		   console.log("处理后初始化数据：",obj);
		   var list = obj;
		    //将获取数据简化存入obj中后，进行html页面的拼接操作
		    for(i in list){
		    	 html += '<div class="field-title"><span class="word"><span>'+i+'</span></span></div>';
		    	 for(z in list[i]){
		    			 html += '<div class="item"><ul>';
		    			 
		    			 html += '<li>';
	    				 html += '<div class="left"><span>'+list[i][z].label+'</span></div>';
	    				
	    				 html += '<div class="right">';
	    				 html += '<div class="g-select">';
	    				 html += '<select autocomplete="off" id="'+list[i][z].name+'" label="'+list[i][z].fieldset+'">';
		    			 for(j in list[i][z].options.data){
		    				 if(list[i][z].options.data[j][0]==-1)
		    				 	html += '<option value="'+list[i][z].options.data[j][0]+'" info='+list[i][z].defaultValue+'><span>'+list[i][z].options.data[j][1]+'</span></option>';
		    				 else
		    					 html += '<option value="'+list[i][z].options.data[j][0]+'"><span>'+list[i][z].options.data[j][1]+'</span></option>';
		    			 }
		    			 html += '</select>';
		    			 html += '<span class="arr-icon"></span>'; 
		    			 html += '</div>';
		    			 html += '</div>';
		    			 html += '<div class="default"><span class="defaultSpan">(默认：'+list[i][z].defaultValue+')</span></div>';
		    			 html += '</li>';
		    			 
		    			 //自定义输入
		    			 html += '<li>';
		    			 html += '<div class="left" style="display:none">';
		    			 html += '<span >自定义输入：</span>';
		    			 html += '</div>'; 
		    			 //自定义的input输入框
		    			 html += '<div class="right" style="display:none">';
	    				 if(list[i][z].name=="pwdMinLenght"||list[i][z].name=="pwdMaxLenght"||list[i][z].name=="defaultValuePwd")
	    				 	html += '<input class="g-input" id="check'+list[i][z].name+'"  style="display:none" placeholder="请输入" onfocus="checkIn(this)" onblur="checkOut(this)" reg="'+list[i][z].validator+'"></input>';
	    				 else
	    					 html += '<input class="g-input"  style="display:none" placeholder="请输入" onfocus="checkIn(this)" onblur="checkOut(this)" reg="'+list[i][z].validator+'"></input>';
	    				
	    				 html += '</div>'; 
	    				 html += '</li>';
	    				 
	    				 //上传提示
	    				 html += '<li>';
		    			 html += '<div class="left" style="display:none">';
		    			 html += '<span >文件选择：</span>';
		    			 html += '</div>';
		    			 //上传
		    			 html += '<div class="right">';
	    				 html += '</div>';
	    				 html += '</li>';
	    				 
	    				 //上传错误提示
	    				 html += '<li>';
		    			 html += '<div class="left" style="display:none">';
		    			 html += '<span >错误信息：</span>';
		    			 html += '</div>';
		    			 //错误信息
		    			 html += '<div class="right errormsgright"  style="display:none">';
		    			 html += '<span class="error-mgs" id="errormsg">* '+list[i][z].guide+'</span>';
	    				 html += '</div>';
	    				 html += '</li>';
		    			 
		    			 html += '</div>';
	    				 html += '</ul></div>';
		    	 }
		    } 
	   	 	//加载内容前，先清空id为content的div中的内容
	  	 	$("#content").empty();
	   	 	//alert(html);
		    $("#content").html(html);
		    
		  
		    /*
		           	将页面中的div的select选项标签添加事件，如果有选项发生改变，触发相应的事件
		                                    如果选中的option的value是$S，则说明其为自定义的类型，要将自定义的输入框进行显示，同时正则表达式也会开始生效
		           	如果选中的option的value是$F,则说明其为上传文件类型，要显示上传文件或者图片进行显示
		    */
		    $("#mainDiv #content div div div").delegate("select","change",function(){
		    	 	console.log(this);
		    	 	var rule;
		    		var	value = $(this).val();
		    		var id = $(this).attr("id");
		    		//获取select的id
		    		var uploadId = $(this).attr("id");
		    		
		    		
		    		if((id=="pwdMinLenght")||(id=="pwdMaxLenght")){
		    			$("#checkdefaultValuePwd").parent().parent().next().next().css("display","none");
		    			$("#checkdefaultValuePwd").parent().parent().next().next().children("div.left").css("display","none");
		    			$("#checkdefaultValuePwd").parent().parent().next().next().children("div.right").css("display","none");
		    			//$("#checkdefaultValuePwd").parent().parent().next().next().children("div.left").css("display","none");
		    			
		    			$("#checkpwdMinLenght").parent().parent().next().next().css("display","none");
		    			$("#checkpwdMinLenght").parent().parent().next().next().children("div.left").css("display","none");
		    			$("#checkpwdMinLenght").parent().parent().next().next().children("div.right").css("display","none");
		    			
		    			$("#checkpwdMaxLenght").parent().parent().next().next().css("display","none");
		    			$("#checkpwdMaxLenght").parent().parent().next().next().children("div.left").css("display","none");
		    			$("#checkpwdMaxLenght").parent().parent().next().next().children("div.right").css("display","none");
		    		}
		    		//如果选中的为自定义
		    		if(value == "$S"){				
		    			//显示input的<li>标签
		    			$(this).parent().parent().parent().next().children("div.right").children("input").val("");
		    			$(this).parent().parent().parent().next().children("div.right").children("input").focus();
		    			
		    			//先去除input之前的样式,隐藏提示栏
		    			$(this).parent().parent().parent().next().children("div.right").children("input").css("border-color","");
		    			$(this).parent().parent().parent().next().children("div.right").children("input").css("display","block");
		    			//$(this).parent().parent().next().next().children("span").children("span").css("display","none");
		    			//显示select后的arr-icon
		    			$(this).parent().parent().parent().next().children().css("display","block");
		    			
		    			//隐藏上传文件<li>标签
		    			$(this).parent().parent().parent().next().next().css("display","none");
		    			//隐藏错误提示<li>标签
		    			$(this).parent().parent().parent().next().next().next().css("display","none");
		    			//选择自定义，隐藏默认值信息
		    			$(this).parent().parent().next().css("display","none");
		    			//添加option中的info为自定义值
		    			$(this).children("option:selected").attr("info",$(this).parent().parent().parent().next().children("div.right").children("input").val());
		    		}else if(value == "$F"){
		    			var insertHtml;
		    			var len = $(this).parent().parent().parent().next().next().children("div.right").children().length;
		    			//alert(len);
		    			if(len>0){
		    				insertHtml = '';
		    			}else{
		    				var id = $(this).attr("id");
		    				if(id=="uploadJSFile")
		    					;///rule = "text/javascript,application/javascript";
		    				else if(id=="uploadImage")
		    					rule = "image/png,image/jpeg";
		    				insertHtml = '<input type="file" name="file" id=file'+id+' '+
		    							'accept='+rule+'></input>';
		    			}
						//alert(insertHtml);
						//将上传的html片段添加到后面
		    			$(this).parent().parent().parent().next().next().children("div.right").prepend(insertHtml);
						insertHtml = '';
		    			//显示上传提示条
		    			$(this).parent().parent().parent().next().next().children("div.left").css("display","block");
		    			//显示上传按钮
		    			$(this).parent().parent().parent().next().next().children("div.right").css("display","block");
		    			//将input置空
		    			$(this).parent().parent().parent().next().children("div.right").children("input").val("");
		    			$(this).parent().parent().parent().next().children("div.right").children("input").css("display","none");
		    			//隐藏自定义输入
		    			$(this).parent().parent().parent().next().css("display","none");
		    			//隐藏右边输入input框div隐藏
		    			$(this).parent().parent().parent().next().children("div.right").css("display","none");
		    			//隐藏错误提示按钮
		    			$(this).parent().parent().parent().next().next().next().children("div.left").css("display","none");
		    			$(this).parent().parent().parent().next().next().next().children("div.right").css("display","none");
		    			//选择上传文件，隐藏默认值提示
		    			$(this).parent().parent().next().css("display","none");
		    			
		    			if(len==0)
		    				uploadClick($(this).parent().parent().parent().next().next().children("div.right"),uploadId);
		    		}else if(value == "-1"){
		    			//选择上传文件，显示默认值提示
		    			$(this).parent().parent().next().css("display","block");
		    			
		    			console.info("input",$(this).parent().parent().parent().next().children("div.right").children("input"));
		    			//将input输入框置空
		    			$(this).parent().parent().parent().next().next().children("div.right").children("input").val("");
		    			
		    			//隐藏自定义输入
		    			//$(this).parent().parent().parent().next().css("display","none");
		    			$(this).parent().parent().parent().next().children("div.left").css("display","none");
		    			//隐藏输入的input框
		    			$(this).parent().parent().parent().next().children("div.right").css("display","none");
		    			
		    			//隐藏文件上传文字
		    			$(this).parent().parent().parent().next().next().children("div.left").css("display","none");
		    			//隐藏文件上传按钮
		    			$(this).parent().parent().parent().next().next().children("div.right").css("display","none");
		    			
		    			//选择默认，隐藏默认值提示
		    			$(this).parent().parent().next().css("display","block");
		    			//隐藏错误提示按钮
		    			$(this).parent().parent().parent().next().next().next().children("div.left").css("display","none");
		    			$(this).parent().parent().parent().next().next().next().children("div.right").css("display","none");
			    	
		    		}else{
		    			//将input输入框置空
		    			$(this).parent().parent().parent().next().children("div.right").children("input").val("");
		    			
		    			//隐藏自定义输入
		    			//$(this).parent().parent().parent().next().css("display","none");
		    			$(this).parent().parent().parent().next().children("div.left").css("display","none");
		    			//隐藏输入的input框
		    			$(this).parent().parent().parent().next().children("div.right").css("display","none");
		    			
		    			//隐藏文件上传文字
		    			//$(this).parent().parent().parent().next().next().css("display","none");
		    			$(this).parent().parent().parent().next().next().children("div.left").css("display","none");
		    			//隐藏文件上传按钮
		    			$(this).parent().parent().parent().next().next().children("div.right").css("display","none");
		    			
		    			//选择上传文件，隐藏默认值提示
		    			$(this).parent().parent().next().css("display","none");
		    			//隐藏错误提示按钮
		    			$(this).parent().parent().parent().next().next().next().children("div.left").css("display","none");
		    			$(this).parent().parent().parent().next().next().next().children("div.right").css("display","none");
			    	}
			});
			
			param = {
					"libId":library_idx_d,
					"deviceIdx":device_idx_d
			},
			$.ajax({
		 		type:"post",
		 		url:"${pageContext.request.contextPath}/json/getInitData",
		 		data:{
		 				"library_device":JSON.stringify(param)
		 		},
		 		success:function(data){
		 			data1 = data.result;
		 			console.log("初始化数据：",data1);
		 			if(data1!=null){
		 				//data.result获取的初始化数组，为空时data.length=1,大于1时则不为空
			 			if(data1[0]!=null){
			 				for(var i in data1){
				 				console.log($("#"+data1[i].name+"[label='"+data1[i].fieldset+"']"));
				 				
				 				if(data1[i].value[0] == "$P"){
				 					//当值为$P时，对应着值为1或者0
				 					$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").val(data1[i].value[1]);
				 					$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").parent().parent().parent().next().children("div.left").css("display","none");
				 					$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").parent().parent().parent().next().children("div.right").css("display","none");
				 					//初始化时，如果为1或者0时，隐藏默认值提示栏
				 					$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").parent().parent().next().css("display","none");
				 				}else if(data1[i].value[0] == "$F"){
				 					//当值为$F时，对应这自定义输入的方式
				 					//先隐藏input框
				 					$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").parent().parent().parent().next().css("display","none");
				 					$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").val("-1");//data1[i].value[0]
				 					
				 					//初始化时，如果为1或者0时，隐藏默认值提示栏
				 					$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").parent().parent().next().css("display","none");
				 				}else{
				 					//当值为$S时：1.显示input输入框  2.隐藏上传的按钮
				 					$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").val(data1[i].value[0]);
				 					
				 					//将自定义的数据从数据库取出，对input回显
				 					$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").parent().parent().parent().next().children("div.right").children("input").val(data1[i].value[1]);
					    			//显示input标签，隐藏错误提示
					    			$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").parent().parent().parent().next().children("div.left").css("display","block");
					    			$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").parent().parent().parent().next().children("div.right").css("display","block");
					    			$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").parent().parent().parent().next().children("div.right").children("input").css("display","block");
					    			$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").next().css("display","block");
					    			
					    			//初始化时，如果为1或者0时，隐藏默认值提示栏
				 					$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").parent().parent().next().css("display","none");
					    			
				 					$("#"+data1[i].name+"[label='"+data1[i].fieldset+"']").children("option:selected").attr("info",data1[i].value[1]);
				 				}
				 			}
			 			}else{
			 				if(data1[0]==null){
			 					//如果设备未
			 					$("select[id!='device']  option[value='-1']").prop("selected",true);
			 					$("select[id!='device']").next("input").css("display","none");
			 				}
			 			}
		 	 		}
		 		},
		 		error:function(data){
		 			alert("getInitData失败！");
		 		}
			});
		}
	  });
};
function checkIn(obj){
	var _val=$(obj).val();
	var _reg=$(obj).attr("reg");
	var reg=new RegExp(_reg);
	

	//隐藏错误提示框
	$(obj).parent().parent().next().next().children("div.left").css("display","none");  //如果正则匹配，不显示提示内容
	$(obj).parent().parent().next().next().children("div.right").css("display","none");
	
	console.info($(obj).attr("id"));
	if($(obj).attr("id")=="checkpwdMinLenght"){
		if(!(typeof($("#pwdMaxLenght"))=="undefined")){
			$("#pwdMaxLenght").parent().parent().parent().next().next().next().children("div.left").css("display","none");
			$("#pwdMaxLenght").parent().parent().parent().next().next().next().children("div.right").css("display","none");
			if(!(typeof($("#checkdefaultValuePwd"))=="undefined")){
				$("#checkdefaultValuePwd").parent().parent().next().next().css("display","none");
				$("#checkdefaultValuePwd").parent().parent().next().next().children("div.left").css("display","none");
				$("#checkdefaultValuePwd").parent().parent().next().next().children("div.right").css("display","none");
			}
		}
	}else if($(obj).attr("id")=="checkpwdMaxLenght"){
		if(!(typeof($("#pwdMinLenght"))=="undefined")){
			$("#pwdMinLenght").parent().parent().parent().next().next().next().children("div.left").css("display","none");
			$("#pwdMinLenght").parent().parent().parent().next().next().next().children("div.right").css("display","none");
			if(!(typeof($("#checkdefaultValuePwd"))=="undefined")){
				$("#checkdefaultValuePwd").parent().parent().next().next().css("display","none");
				$("#checkdefaultValuePwd").parent().parent().next().next().children("div.left").css("display","none");
				$("#checkdefaultValuePwd").parent().parent().next().next().children("div.right").css("display","none");
			}
		}
	}else if($(obj).attr("id")=="checkdefaultValuePwd"){
			$("#checkdefaultValuePwd").parent().parent().next().next().children("div.right").children("span").text("只能输入数字或字母");
	}
}
function checkOut(obj){
	var _val=$(obj).val();
	var _reg=$(obj).attr("reg");
	var reg=new RegExp(_reg);
	//alert(_val+"  :  "+_reg);
	$(obj).removeAttr("style");
	$(obj).siblings("span").css("display","none");
	
	if(!reg.test(_val)){
		//如果正则不匹配，将错误提示显是出来
		$(obj).css("border-color","red");
		//显示错误提示<li>标签
		$(obj).parent().parent().next().next().css("display","block");
		//显示左边错误提示
		$(obj).parent().parent().next().next().children("div.left").css("color","red");
		$(obj).parent().parent().next().next().children("div.left").css("display","block");
		//显示右边
		$(obj).parent().parent().next().next().children("div.right").css("color","red");
		$(obj).parent().parent().next().next().children("div.right").css("display","block");
	}else{
		//将input中的内容添加到select option的info属性中
		$(obj).parent().parent().prev("li").children("div.right").children("div.g-select").children("select").children("option:checked").attr("Info",$(obj).val());
	}
}
function submit(){
	var jsonStr;
	var id;
	var label;
	var option;
	var optionVal;
	var key;
	
	var device_idx = $("select[id='device']").val();
	
	var selectArr = $("mainDiv content div select");
	var objs = [];
	
	var diy_none = true;
	//1 判断页面时否有错误提示信息，如果有则不能进行页面数据保存
	$("span[id='errormsg']").each(function(){
		//注意这里设置的是span的父标签是否显示，即class="right"的div块
		if($(this).parent().css('display') != 'none'){
			console.info("errormsgId",$(this).text());
			diy_none = false;
		}
	});
	
	if(!diy_none){
		//除file文件上传的自定义输入框，存在不符合规则的情况
		layer.alert("页面存在错误，检查后重新提交！");
	}else{
		var flag;
		//2 检测密码的是否符合要求
		flag = checkpwd();
		
		if(flag){
			//3 上传文件的保存，如果上传条件有错误，flag置false
			flag = uploadFile();
		}
		
		if(flag){
			//将所有select的option选中，以备存储
			jsonStr = '[';
			$("#mainDiv div select[id!='device']").each(function(index){   
				console.log(index+":::"+this);
				id = $(this).attr("id");
				label = $(this).attr("label");
				optionVal = $(this).children("option:checked").val();
				
				
				if(optionVal=="$S"){
					//将自定义的值获取到存入数据库
					optionVal = $(this).parent().parent().parent().next().children("div.right").children("input").val();//children("option:checked").attr("Info");
					key = "$S";
				}else if(optionVal=="-1"){
					key = "$D";
				}else if(optionVal=="$F"){
					key = "$F";
				}else{
					key = "$P";
				}
				
				if(optionVal!="-1"){
					var obj = {};
					var obj2 ={};
					
					var str ;
					obj.name = id;
					obj.fieldset = label;
					 
					obj.value = [];
					obj.value[0] = key;
					obj.value[1] = optionVal;
					
					objs.push(obj);
				}
			});
				
			if(jsonStr.lastIndexOf(",")!=-1){
				jsonStr=jsonStr.substring(0,jsonStr.length-1);
			}
			jsonStr += ']';
	
			console.log("存储数据"+objs);
		
		
		
			//将json数据存入数据库中
			$.ajax({
				type:"post",
				url:"../json/saveData",
				data:{
					"libId":library_idx_d, 
					"deviceIdx":device_idx_d,
					"jsonStr" : JSON.stringify(objs)	
				},
				dataType: "json", 
				success:function(data){
					if(data){
						/* GlobalShowMsg({showText:"提交成功",status:true}); */
						//$(".zhuce-result").fadeIn();
						/* $(".zhuce-result").fadeOut(3000) */
						zhuceResult(flag);
						$(".zhuce-result").fadeOut(500).delay(2000,function(){ 
							$("#mainframe").load('<%=basePath%>device/main',{"returnInfo":JSON.stringify(returnInfo)},function(){
				 			});
			             });  
				  	}
				},
				error:function(data){
					alert("失败");
				}
			});  
		}
	}
}
function zhuceResult(state){
	if(!state){
		$(".zhuce-result").addClass("fail");
		$(".zhuce-result").find("status-word").text(deviceedit_jsp_devUpdateFail);
		$(".zhuce-result").find("tips-word").text(deviceedit_jsp_afterUpdateFailInfo);
		$(".zhuce-result").fadeOut(5000);
		$(".zhuce-result").removeClass("fail");
	}

	$(".zhuce-result").fadeIn();
	$(".zhuce-result").fadeOut(2000);
}
function checkpwd(){
	var min,max;
	if(!(typeof($("#pwdMinLenght"))=="undefined")){
		if($("#pwdMinLenght").val()=="-1"||$("#pwdMinLenght").val()=="$S"){
			min = $("#pwdMinLenght").children("option:selected").attr("info");
		}else{
			min =  $("#pwdMinLenght").val();
		}
	}
	if(!(typeof($("#pwdMaxLenght"))=="undefined")){
		if($("#pwdMaxLenght").val()=="-1"||$("#pwdMaxLenght").val()=="$S"){
			max = $("#pwdMaxLenght").children("option:selected").attr("info");
		}else{
			max =  $("#pwdMaxLenght").val();
		}
	}
	//alert("min:"+min+" max:"+max);
	if(parseInt(min)>parseInt(max)){
		//显示最小密码和最大密码长度错误提示
		$("#pwdMinLenght").parent().parent().parent().next().next().next().css("display","block");
		$("#pwdMinLenght").parent().parent().parent().next().next().next().children("div.left").css("color","red");
		$("#pwdMinLenght").parent().parent().parent().next().next().next().children("div.left").css("display","block");
		$("#pwdMinLenght").parent().parent().parent().next().next().next().children("div.right").css("color","red");
		$("#pwdMinLenght").parent().parent().parent().next().next().next().children("div.right").css("display","block"); 
		$("#pwdMaxLenght").parent().parent().parent().next().next().next().css("display","block");
		$("#pwdMaxLenght").parent().parent().parent().next().next().next().children("div.left").css("color","red");
		$("#pwdMaxLenght").parent().parent().parent().next().next().next().children("div.left").css("display","block");
		$("#pwdMaxLenght").parent().parent().parent().next().next().next().children("div.right").css("color","red");
		$("#pwdMaxLenght").parent().parent().parent().next().next().next().children("div.right").css("display","block");
		
		return false;
	}
	if(!(typeof($("#defaultValuePwd"))=="undefined")){
		var defaultLen;
		var defaultVal = $("#defaultValuePwd").val();
		if(defaultVal=="-1"){
			defaultLen = 8;
		}else if(defaultVal=="0"){
			defaultLen = 8;
		}else if(defaultVal=="1"){
			defaultLen = 6;
		}else if(defaultVal=="2"){
			defaultLen = 6;
		}else if(defaultVal=="3"){
			defaultLen = 8;
		}else if(defaultVal=="$S"){
			defaultLen = $("#defaultValuePwd").parent().parent().parent().next().children("div.right").children("input").val().length;
		}
		//alert("defaultLen:  "+defaultLen);
		if((parseInt(min)>parseInt(defaultLen))||(parseInt(max)<parseInt(defaultLen))){
			$("#defaultValuePwd").parent().parent().parent().next().next().next().css("display","block");
			$("#defaultValuePwd").parent().parent().parent().next().next().next().children("div.left").css("color","red");
			$("#defaultValuePwd").parent().parent().parent().next().next().next().children("div.left").css("display","block");
			$("#defaultValuePwd").parent().parent().parent().next().next().next().children("div.right").css("color","red");
			$("#defaultValuePwd").parent().parent().parent().next().next().next().children("div.right").css("display","block");
			$("#defaultValuePwd").parent().parent().parent().next().next().next().children("div.right").children("span").html("自定义密码长度不在最小和最大密码范围之内");
			return false;
		}
	}
	return true;
}
function uploadClick(obj,uploadId){
	var bakUpload={};
	bakUpload.isClicked=false;
	bakUpload.uploadDialog;
	
	var openDialog;
	var openDialogId;
   
	obj.children("#file"+uploadId).on("click",function(){
		//显示提示信息
   		$(this).parent().parent().next().children("div.left").css("display","none"); //隐藏提示文字
   		$(this).parent().parent().next().children("div.right").css("display","none"); //隐藏提示信息
	});
}
function uploadFile(){
	var flag1 = true;
	var importState = false;
	var id;
	
	var dialogIndex = loadingDialog({
		loadText:"请稍等，上传中..."
   	});
	
	$("input[id^='file']").each(function(){
		value = $(this).parent().parent().prev().prev().children("div.right").find("select").find("option:selected").attr("value");
		var uploadId = $(this).attr("id");
		//如果input选择的为上传文件选项，对其进行判断是否符合要求
		if(value=="$F"){
			var importState = false;
			var fileNamePrex;
			var fileNamePrexLen;
			
			if(uploadId=="fileuploadJSFile"){
				fileNamePrex = ".js";
				fileNamePrexLen = 3;
			}else if(uploadId == "fileuploadImage"){
				fileNamePrex = ".jpg";
				fileNamePrexLen = 4;	
			}
			console.info($("#"+uploadId));
			
			//openDialogId为变量，根据不同的选择文件（js或img）而不同
			var fileId = uploadId;
			var fileName = getPath(document.getElementById(fileId));
			
			//如果上传的文件为空提示
			if(!$("#"+fileId).val()){
				    flag1 = false;
			   		
			   		//显示提示信息
			   		$(this).parent().parent().next().children("div.left").css("display","block");
			   		$(this).parent().parent().next().children("div.right").css("display","block");
			   		$(this).parent().parent().next().children("div.left").children("span").css("color","red");
			   		$(this).parent().parent().next().children("div.right").children("span").html("请选择文件后上传");
			   		$(this).parent().parent().next().children("div.right").children("span").css("color","red");
			 
			}else{
				var fileSize = $("#"+uploadId)[0].files[0].size;
				var totalSize = fileSize/1024;
				
				//如果上传的文件不符合上传文件要求，进行提示
				if(!importState && fileName.substring(fileName.length-fileNamePrexLen,fileName.length) != fileNamePrex){
					   flag1 = false;
					   
					   //显示提示信息
				   	   $(this).parent().parent().next().children("div.left").css("display","block");
				   	   $(this).parent().parent().next().children("div.right").css("display","block");
				   	   $(this).parent().parent().next().children("div.left").children("span").css("color","red");
				   	   $(this).parent().parent().next().children("div.right").children("span").html("请选择"+fileNamePrex+"文件上传");
				   	   $(this).parent().parent().next().children("div.right").children("span").css("color","red");
				//如果上传的文件超过文件大小
				}else if(totalSize > 2048){
					
					   flag1 = false;
					   
					   //显示提示信息
				   	   $(this).parent().parent().next().children("div.left").css("display","block");
				   	   $(this).parent().parent().next().children("div.right").css("display","block");
				   	   $(this).parent().parent().next().children("div.left").children("span").css("color","red");
				   	   $(this).parent().parent().next().children("div.right").children("span").html("上传的"+fileNamePrex+"文件太大,默认为2M");
				   	   $(this).parent().parent().next().children("div.right").children("span").css("color","red");
				}
			} 
		}else if(value == "-1"){
			$(this).parent().parent().next().children("div.left").css("display","none");
			$(this).parent().parent().next().children("div.right").css("display","none");
	    }
	 });
	
	//如果两个文件都符合上传条件，没有未选择文件同时文件选择正确
	if(flag1==true){
		$("input[id^='file']").each(function(){
			var uploadId = $(this).attr("id");
			var fileId = uploadId;
			
			var oper=<shiro:principal/>;
			   var library_idx=null;
				var lib_id = null;
				if(oper.operator_type>=3){
					library_idx=oper.library_idx;
					lib_id = libIdxAndNameObj[library_idx].lib_id;
				}
				var location = (window.location+'').split('/');  
				var basePath = location[0]+'//'+location[2]+'/'+location[3];
				
				
				
				param = {
						"library_idx":library_idx_d
						//"deviceIdx":device_idx_d
				},
				$.ajax({
			 		type:"post",
			 		url:"${pageContext.request.contextPath}/library/selectLibinfoByParam",
			 		data:{
			 				"json":JSON.stringify(param)
			 		},
			 		success:function(data){
			 			console.info("图书馆信息：",data);
			 			console.info("图书馆信息：",data.result.rows[0].lib_id);
			 			var lib_id = data.result.rows[0].lib_id;
			 			
			 			param = {
								"device_idx":device_idx_d
								//"deviceIdx":device_idx_d
						},
			 			$.ajax({
					 		type:"post",
					 		url:"${pageContext.request.contextPath}/device/SelectInfo",
					 		data:{
					 				"json":JSON.stringify(param)
					 		},
					 		success:function(data){
					 			console.info("设备信息：",data);
					 			console.info("设备信息：",data.result.device_id);
					 			var device_id = data.result.device_id;
					 			if(!importState){
									   $.ajaxFileUpload({
										   	url:basePath+"/common/uploadJS",
										   	secureuri :false,
											 fileElementId :fileId,//file控件id
											 type:'post',
											 data: { libId: lib_id, deviceId: device_id},
											 dataType:'json',
											 success : function (data,status){
												
												
											},
											 error: function(data, status, e){
											      console.log(data,status,e);
											 } 
										}); 
								 }
					 		}
			 			});
			 			
			 			
			 			
			 		},
			 		error:function(data){
			 			alert("获取图书馆的信息失败！");
			 		}
				});
			});
		}else{
			layer.close(dialogIndex);
			return flag1;
		}
	
	layer.close(dialogIndex);
    return true;
}
function loadingDialog(options){
	var defaults = {
		loadText:"正在删除···"
	};
	defaults = $.extend(defaults,options);

	$('.load-dialog .word').text(defaults.loadText);

	var dialogIndex = layer.open({
		type: 1,
		shade: false,
		title: false, //不显示标题
		scrollbar :false,
		closeBtn :0,
		shade:0.5,
		shadeClose :false,
		area:["400px"],
		offset :["195px"],
		content: $('.load-dialog'), //捕获的元素
		success : function(index,layero){
		
		}
	});

	return dialogIndex;
}
function getPath(obj) {
    if(obj)
	{  
    	if (window.navigator.userAgent.indexOf("MSIE")>=1)
	   {
	       obj.select();
	       return document.selection.createRange().text;
	   }   
	    else if(window.navigator.userAgent.indexOf("Firefox")>=1)
	    {
	       if(obj.files)
	        {
	               return obj.files.item(0).getAsDataURL();
	         }
	         return obj.value;
	    }
   			return obj.value;
	}
}
$(document).on("click","#topcontrol",function(){
	 $('html,#innerDiv').animate({scrollTop:0},"fast");  
});
</script>
</html>