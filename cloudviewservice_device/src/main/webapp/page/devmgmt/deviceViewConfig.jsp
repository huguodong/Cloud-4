<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
	<style type="text/css">
		
		.cycle-input{
			position: relative;
			top: 0px;
			right: 0px;
		}
		.form-dialog-fix .form-dialog .form-wrap .short-select .g-input{
			 width: 100px;
			 margin-left:15px;
		}
		 .cycle{
			float: left;	
		}
		.form-dialog-fix .form-dialog .form-wrap .right.short-select {
   			 width: 280px;
   			 margin-left:30px;
		}
		/*同步周期选择 end*/
		.g-checkbox {
		    margin-left: 15px;
		    width: 16px;
		    height: 16px;
		    background: url(${pageContext.request.contextPath}/static/images/checkbox.png) center center no-repeat;
		}
		/* 设置配置-----fieldset--------下方部分 */
		.equipment-zhuce .form-area{
			width: 800px;
			margin:auto;
		}
		.equipment-zhuce .form-topbottom{
			padding: 12px 1px;
			overflow:hidden;zoom:1;
			height:12px;
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
		.equipment-zhuce .field-title {
		    position: relative;
		    text-align: center;
		    margin-bottom: 30px;
		    width: 800px;
		    height: 2px;
		    background-color: #dddddd;
		}
		.equipment-zhuce .form-wrap li{
			float: left;
			width: 100%;
		}
		/* .equipment-zhuce .content-area .form-area li input{
			margin-left:20px; 
			vertical-align:middle;
			width: 200px;
		} */
		.equipment-zhuce .form-wrap li .left{
			float: left;
			padding-right: 14px;
			text-align: left;
			width: 220px;
			line-height: 28px;
		}
		.equipment-zhuce .form-wrap li .right{
			margin-left:20px;
			float: left; 
			width: 200px;
		}
		/* .equipment-zhuce .g-submit {
    		 display: inline; 
    		 margin: 15px auto 
		} */
		
		.equipment-zhuce .form-wrap.acs {
			padding-top:10px;
		}
		
		.equipment-zhuce .form-wrap.acs .item {
			margin-bottom:10px;
		}
		.equipment-zhuce .field-title{
			margin-top:30px;
		}
		.equipment-zhuce .form-wrap.acs label {
			display: inline-block;
			margin-right:20px;
			margin-left:3px;
			width: 30px;
		} 
		.logic-obj-li {
			margin-bottom: 20px;
			
		}  
		.logic-obj-li label{
		/**min-width: 100px;*/
		padding-left:2px;
		vertical-align: middle;
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
			/*height: 1000px;*/ 
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
		.g-table .tdcolor{
			background-color:#dcdcdc;/* //url(${pageContext.request.contextPath}/static/images/index-bg.jpg) left center no-repeat; */
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
<div class="equipment-leixing">
	<div class="page-title-bar">
		<span class="title">设备软件配置设定<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/devmgmt/devicetype.jsp" target="_blank" class="g-help"></a></span>
	</div>
	<div class="main-top equipment-zhuce">
		<div class="content-area width-limit86">
			<div class="form-area">
				<div class="form-wrap form-topbottom">
					<div class="item">
							<ul>
								<li style="padding-left:50px">
									<div class="left"><span>选择图书馆:</span></div>
									<div class="right" style="padding-left:-20px">
										<div class="g-select">
											<select id="library">
												<option value="-1" selected>选择图书馆</option>
											</select>
											<span class="arr-icon"></span>
										</div>
									</div>
								</li>
							</ul>
					</div>	
				</div>
			</div>			
		</div>
	</div>
	<div class="main equipment-zhuce">
		<div class="content-area width-limit86">
			<div class="form-area">
			    <div class="form-wrap">
					<div class="item">
							<div id="deviceData" class="form-wrap">
					
							</div>
					</div>	
				</div>
			</div>
		</div>
	</div>
	<!-- <div class="main-bottom equipment-zhuce">
		<div class="content-area width-limit86"">
			<div class="form-area form-areaDiv">
				<div class="form-wrap form-topbottom">
					<div class="item">
						<div class="page-bottom">
							<input type="submit" name="save" value="保存" id="saveData" class="g-submit g-btn-blue">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> -->
	<div class="main-bottom equipment-zhuce">
		<div class="content-area width-limit86"">
			<div class="form-area">
					<div class="page-bottom">
						<div class="item">
							<li>
						 		<input type="submit" name="save" value="保存" id="saveData" class="g-submit g-btn-blue">
							</li>
						</div>
						<!-- <input type="button" class="g-submit g-btn-blue" value="保存" onclick="submit()"></input> -->
					</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function(){
	var lang='zh_CN';
	/* (function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();
		$(".main").css({
			"min-height":winH - headerH - pagingBarH -pageTitleBar
		});
	})(); */
	var winH = $(window).height();
	var headerH = $(".g-header").outerHeight();
	var pageTitleBar = $(".page-title-bar").outerHeight();
	var maintop = $(".main-top").outerHeight();
	var mainbottom = $(".main-bottom").outerHeight();

	$(".main").css({
				"height" : winH - headerH - maintop
						- pageTitleBar - mainbottom
	});
	
	
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];  
	var delLayer = null; 
	
	getLibarys();
	var oper = <shiro:principal/>;
	
	//保存
	$("#saveData").on("click",function(){
		
		var deviceLogicList="";
		var dataArray = new Array();
		var checkedRidaos = $("#deviceData .logic-obj-li").find("input[type=checkbox]:checked");
		if(checkedRidaos.length <= 0){
			alert("请至少选择一项!");
			return false;
		}
		
		var libraryId = $("#library").val();
		if(libraryId == null || libraryId == -1){
			alert("请选择图书馆！");
			return false;
		}
		
		$("#deviceData .logic-obj-li").find("input[type=checkbox]:checked").each(function(index,dom){
			
			var id = $(dom).attr("id");
			var deviceView_description = '';
			var operator_idx = oper.operator_idx;
			
			var label = $("#"+id).parents(".g-checkbox").next().text();
			
			var name = $("#"+id).val();
			var content = {"name":name,"label":label};
			
			var param = {"view_config_id":id,"library_idx":libraryId,
					"content":JSON.stringify(content),"description":deviceView_description
					,"operator_idx":operator_idx};
			dataArray[index] = param;
		});
		$.ajax({
			url:basePath+"/deviceViewConfig/updateDeviceViewConfig",
			type:"POST",
			data:{"req":JSON.stringify(dataArray)},
			success:function(data){
				
				if(!data.state){
					alert("更新失败！");
					return;
				} 
				GlobalShowMsg({showText:"修改成功",status:true});
			}
		}); 
		
	});
	
	
	function queryDeviceViewConfigSet(){
		var libraryId = $("#library").val();
		var params = {"library_idx":libraryId};
		if(libraryId == null || libraryId == -1){
			var checkBoxs = $("#deviceData").find(".logic-obj-li").find("input[type=checkbox]:checked");
			$.each(checkBoxs,function(i,cb){
				$(cb).trigger("click");
			});
			return false;
		}
		$.ajax({
			url:basePath+"/deviceViewConfig/queryDeviceViewConfigSet",
			type:"POST",
			data:{"req":JSON.stringify(params)},
			success:function(data){
				
				var checkBoxs = $("#deviceData").find(".logic-obj-li").find("input[type=checkbox]:checked");
				$.each(checkBoxs,function(i,cb){
					$(cb).trigger("click");
				});
				$.each(data.result,function(index,jsonData){
					var id = jsonData.view_config_id;
					if($("#deviceData").find(".logic-obj-li").find("input[type=checkbox][id="+id+"]").prop('checked') == false){
						$("#deviceData").find(".logic-obj-li").find("input[type=checkbox][id="+id+"]").trigger("click");
					}
				});
			}
		}); 
	}
	
	$(document).ready(function(){
		var param ={};
		$.ajax({
		url:basePath+"/deviceViewConfig/queryDeviceViewConfig",
		type:"POST",
		data:{"req":JSON.stringify(param)},
		success:function(data){
			var html='';
			$.each(data.result,function(index,jsonData){
				console.info("jsonData",jsonData);
				html += '<div class="item">';
				html +='<li class="logic-obj-li"><ul><table class="g-table"><tr>';
				$.each(jsonData,function(i,result){
					var resultJson = jsonToObj(result.operation);
					if(jsonData.length > 0 && i == 0){
						if(resultJson.fieldset_label!=''&&resultJson.fieldset_label!=null){
							html +='<div class="field-title"><span class="word">'+resultJson.fieldset_label+'</span></div> ';
						}else{
							html +='<div class="field-title"><span class="word">'+resultJson.label+'</span></div> ';
						}
						//html +='<div class="field-title"><span class="word">'+resultJson.fieldset_label+'</span></div> ';
					}
					html+='<td class="tdcolor" style="width:50px"><div class="g-checkbox">';
					html+='<input type="checkbox" id="'+result.id+'" value="'+resultJson.name+'">';
					html+='</div>';
					html+='<label for="'+result.id+'">'+resultJson.label+'</label></td>';
					if((i!=0&&(i+1)%3==0)||i+1==jsonData.length){
						html+='</tr></ul>';
						html+='</li>';
						/* if((i+1)<jsonData.length){
							html +='<li class="logic-obj-li"><ul><tr>';
						} */
					}
				});
				html += '</table></div>';
			});
			$("#deviceData").append(html);
		}
		});
	});
	
	$("#library").on("change",function(){
		queryDeviceViewConfigSet();
		
	});
	
	function jsonToObj(str){ 
		try{
			return JSON.parse(str); 
		}catch(e){
			return "";
		}
	}
	
	function getLibarys(){
		var param = {};
		$.ajax({
	   		url:basePath+"/library/querylibInfoByCurUserEXT1",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
			console.info("LibData",data);
			$.each(data.result,function(i,item){
					$("#library").append(
					"<option value='"+item.library_idx+"'>"+item.lib_name+"</option>" 
					);
	            });
	   		}
		});
	}
});
</script>
</body>
</html>


