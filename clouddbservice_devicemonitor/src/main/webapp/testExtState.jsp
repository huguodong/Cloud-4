<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'testExtState.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@ include file="/page/global.jsp" %>
  </head>
  
  <body>
    <div>
    	<div><span>state:</span><span id="state"></span></div>
    	<div><span>message:</span><span id="message"></span></div>
    	<div><span>result:</span><span id="result"></span></div>
    </div>
    <button id="test">点击测试数据</button>
    <button id="add">点击增加数据</button>
    <button id="upd">点击测试更改</button>
    <button id="del">点击测试删除</button>
    <button id="exe">点击testCommand</button>
  </body>
  <script type="text/javascript">
	$(function(){
		var path="${pageContext.request.contextPath}";
		var extState={
				updatetime:"20160318155520",
				detail:[
			  	{ext_type:"Auth_RfidReader",ext_state:"Y"},
			  	{ext_type:"Return_RfidReader",ext_state:"Y"},
			  	{ext_type:"Printer",ext_state:"Y"},
			  	{ext_type:"PLC",ext_state:"Y"},
			  	{ext_type:"Register_RfidReader",ext_state:"Y"},
			  	{ext_type:"IDReader",ext_state:"Y"},
			  	{ext_type:"CardDispenser",ext_state:"Y"},
			  	{ext_type:"CashAcceptor",ext_state:"Y"},
			  	{ext_type:"ICCard",ext_state:"Y"},
			  	{ext_type:"Load_RfidReader",ext_state:"Y"},
			  	{ext_type:"BCL",ext_state:"Y"},
			  	{ext_type:"Shelf1",ext_state:"Y"},
			  	{ext_type:"Shelf2",ext_state:"Y"},
			  	{ext_type:"Shelf3",ext_state:"Y"},
			  	{ext_type:"Pusher1",ext_state:"Y"},
			  	{ext_type:"Checkout1",ext_state:"Y"},
			  	{ext_type:"Pusher2",ext_state:"Y"},
			  	{ext_type:"Checkout2",ext_state:"Y"},
			  	{ext_type:"Pusher3",ext_state:"Y"},
			  	{ext_type:"Checkout3",ext_state:"Y"},
			  	{ext_type:"NCIP_Connect",ext_state:"Y"},
			  	{ext_type:"Register",ext_state:"Y"},
			  	{ext_type:"ScanItem",ext_state:"Y"},
			  	{ext_type:"BinMoving",ext_state:"Y"},
			  	{ext_type:"Checkout1_Function",ext_state:"Y"},
			  	{ext_type:"Checkout2_Function",ext_state:"Y"},
			  	{ext_type:"Checkout3_Function",ext_state:"Y"},
			  	{ext_type:"Checkin_Function",ext_state:"Y"},
			  	{ext_type:"Pusher4",ext_state:"Y"},
			  	{ext_type:"Checkout",ext_state:"Y"},
			  	{ext_type:"Shelf",ext_state:"Y"},
			  	{ext_type:"Belt",ext_state:"Y"},
			  	{ext_type:"Clutch1",ext_state:"Y"},
			  	{ext_type:"Clutch2",ext_state:"Y"},
			  	{ext_type:"Clutch3",ext_state:"Y"},
			  	{ext_type:"Arm",ext_state:"Y"},
			  	{ext_type:"Alert",ext_state:"Y"},
			  	{ext_type:"PushHaYdle1",ext_state:"Y"},
			  	{ext_type:"PushHaYdle2",ext_state:"Y"},
			  	{ext_type:"PushHaYdle3",ext_state:"Y"},
			  	{ext_type:"Present",ext_state:"Y"}
			  ]
			};
		var addProgramLog={
				    proName:"ACSServer.bat",
				    oldVersion:"V1.00",
				    newVersion:"V1.03",
				    createTime:"20160305030506",
				    updateTime:"20160305030506"
				  };
		var updProgramLog={
				proName:"ACSServer.bat",
			    oldVersion:"V1.03",
			    newVersion:"V1.03",
			    createTime:"20160305030506",
			    updateTime:"20160305030506"
		};
		var whereInfoUpdProgramLog={
			  // proLID:"56efac3707641b0b04853888"
			   proName:"ACSServer.bat",
			   oldVersion:"V1.00",
			   newVersion:"V1.03",
			   createTime:"20160305030506",
			   updateTime:"20160305030506"
		};
		var delProgramLog={
			 proName:"ACSServer.bat",
			 oldVersion:"V1.03",
			 newVersion:"V1.03",
			 createTime:"20160305030506",
			 updateTime:"20160305030506"
		};
		var execute={
				"aggregate":"cashbox_log",
				"pipeline":[
					{
						"$group":{
							"_id":"sum",
							"count":{"$sum":"$Money"}
						}
					}
				]
			};
  var url=path+"/device/extstate/upd";
  var addProgramLogUrl=path+"/device/programlog/add";
  var updProgramLogUrl=path+"/device/programlog/upd";
  var delProgramLogUrl=path+"/device/programlog/del";
  var executeCommandUrl=path+"/basic/execute";
  $("#exe").click(function(){
	  var json=encodeURI(JSON.stringify(execute));
	  $.getJSON(executeCommandUrl,{"command":json},function(data){
		  console.log(data);   
	  });
  });
  $("#del").click(function(){
	  var json=encodeURI(JSON.stringify(delProgramLog));
	  $.ajax({
			 url:delProgramLogUrl,
			 type:"POST", 
			 //dataType:"json",
			 //contentType:"application/json;charset=UTF-8",
			 data:"json="+json,
			 success : function(data) {
			    console.log(data);   
			 },
			error:function(e){
			    alert("err");   
			}   
	  });
  });
  $("#upd").click(function(){
	  var json=encodeURI(JSON.stringify(updProgramLog));
	  var json1=encodeURI(JSON.stringify(whereInfoUpdProgramLog));
	  $.ajax({
			 url:updProgramLogUrl,
			 type:"POST", 
			 //dataType:"json",
			 //contentType:"application/json;charset=UTF-8",
			 data:"json="+json+"&json1="+json1,
			 success : function(data) {
			    console.log(data);   
			 },
			error:function(e){
			    alert("err");   
			}   
	  });
  });
  $("#add").click(function(){
	  var json = encodeURI(JSON.stringify(addProgramLog));  
		$.ajax({
				 url : addProgramLogUrl,
				 type : "POST", 
				 //dataType:"json",
				 //contentType:"application/json;charset=UTF-8",
				 data:"json="+json,
				 success : function(data) {
				    console.log(data);   
				 },
				error:function(e){
				    alert("err");   
				 }   
		  });
	  
	  
  });
  
  $("#test").click(function(){
	/*   $.post(url,stateDate,function(json){
		  if(json){
				console.log(json);
				$("#state").text(json.state);
				$("#message").text(json.message);
				$("#result").text(json.result);
		  }
	
	  }); */
	var json = encodeURI(JSON.stringify(extState));  
	$.ajax({
			 url : url,
			 type : "POST", 
			 //dataType:"json",
			 //contentType:"application/json;charset=UTF-8",
			 data:"command="+json,
			 success : function(data) {
			    console.log(data);   
			 },
			error:function(e){
			    alert(e+"err");   
			 }   
	  });

	
		
	});
})
  
  </script>
</html>
