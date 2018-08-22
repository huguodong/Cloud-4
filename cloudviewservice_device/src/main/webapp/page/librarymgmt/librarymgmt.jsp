<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<%-- <%@include file="common/global.jsf" %> --%>
	<style type="text/css">
		.library-guanli .col7 {
   		 	width: 0px;
		}
		.library-guanli .col6 {
 		   width: 190px;
		}
		.library-guanli .col5 {
    	  width: 190px;
		}
		.library-guanli .col8{
			width: 240px;
		}
		.edit-lib-temp {
		    position: relative;
		    float: left;
		    left: 270px;
		    top: -25px;
		    border-radius: 2px;
		    cursor: pointer;
		    width: 50px;
    		height: 24px;
    		margin-bottom: -20px;
		}	
		.layui-layer-dialog .layui-layer-content {
		    position: relative;
		    line-height: 24px;
		    /*padding: 0px;*/
		    word-break: break-all;
		    font-size: 14px;
		    overflow: auto;
		}
		.form-dialog .g-select select {
   			width: 255px;
		}
		.page-title-bar .template-filter .left {
		    float: left;
		    width: 110px;
		    padding-left: 20px;
		    margin-right: 16px;
		    -webkit-box-sizing: border-box;
		    -moz-box-sizing: border-box;
		    box-sizing: border-box;
		}
		.page-title-bar .template-filter .drop-box {
		    display: none;
		    position: absolute;
		    top: 100%;
		    left: 0;
		    padding: 10px 0;
		    width: 320px;
		    height: 300px;
		    color: #888888;
		    text-align: left;
		    background-color: #FFF;
		    border: 1px solid #DDD;
		    border-radius: 2px;
		}
		.drop-box .btn {
		    float: left;
		    margin-left: -8px;
		    margin-top: 120px;
		    padding: 0 16px;
		    height: 28px;
		    color: #FFF;
		    text-align: center;
		    background-color: #00a2e9;
		    border-radius: 2px;
		    cursor: pointer;
		    width: 60px;
		}
		
		.form-dialog .form-wrap .right{
			width: 260px;
		}
	   .form-dialog  .lib-temp-dialog .g-input {
		    width: 160px;
		    height: 26px;
		    line-height: 26px;
		}

        .layui-layer {
            width: 740px!important;
        }
        .diqu{
            width:111px !important;
        }
        .rdiqu{
            width:117px !important;
        }
	</style>
</head>
<body>
<div class="library-guanli">
		<div class="page-title-bar">
			<span class="title">图书馆管理<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/librarymgmt/librarymgmt.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="template-filter"><div class="btn-wrap">模板筛选</div>
					<div class="drop-box">
						<div class="left">
							<ul>
								<li class="active">服务周期</li>
								<li>最大设备数</li>
								<li>最大用户数</li>
								<li>分馆数</li>
							</ul>
							<input name="clearTempKeyword" class="btn" value="清除条件"/>
						</div>
						<div class="right">
							<ul class="active">
								<li>数据1</li>
							</ul>
							<ul>
								<li>数据2</li>
							</ul>
							<ul>
								<li>数据2</li>
							</ul>
							<ul>
								<li>数据2</li>
							</ul>
						</div>
					</div>
				</div>
				 <!--  <div class="g-select">
					<select id="query">
						<option value="-1">查询条件</option>
						<option value="1">馆名称</option>
						<option value="2">馆类型</option>
					</select>
					<span class="arr-icon"></span>
				</div> -->
				<input type="text" name="" id="query_data" class="input g-input" placeholder="输入馆id或名称" />
				<div class="btn search">查询</div>
				<div class="btn increase g-btn-green">新增</div>
				<div class="btn delete">删除</div>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="">图书馆ID</th>
						<th class="col2">图书馆名称</th>
						<th class="col3">地址</th>
						<th class="col4">服务电话</th>
						<th class="col5">开始时间</th>
						<th class="col6">结束时间</th>
						<th class="col7">使用模板</th>
						<th class="col8">操作</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>
		</div>
	<%@include file ="../include/page_bar.jsf" %>	

	<div class="relation-map form-dialog">
		
		<div class="title">关系图</div>
		<div class="example">				
			<div class="item">——— 主从关系</div>
			<div class="item">------ 关系2</div>
		</div>
		<div class="tac-box">
			<div class="con-area">
				<div class="con-left full-line" id="left_main">
				<!--去除上面的full-line类名就是虚线啦 -->
					<ul>
						<li><div class="g-guan">深圳市图书馆</div></li>
					</ul>
				</div>
				<div class="con-left full-line" id="mid-main">
				<!--去除上面的full-line类名就是虚线啦 -->
					<ul>
						<li><div class="g-guan" >深圳市图书馆</div></li>
					</ul>
				</div>
				<div class="con-right full-line" id="right_slave">
				<!--去除上面的full-line类名就是虚线啦 -->
					<div class="item"><div class="g-guan">深圳市图书馆</div></div>
					
				</div>
			</div>
			<div class="con-area" >
				<div class="con-left full-line" id="left_main2">
				<!--去除上面的full-line类名就是虚线啦 -->
					<ul>
						<li><div class="g-guan theme-green">深圳市图书馆</div></li>
					</ul>
				</div>
				<div class="con-left mid-box full-line" id ="right_slave2">
				<!--去除上面的full-line类名就是虚线啦 -->
					<ul>
						<li><div class="g-guan">深圳市图书馆</div></li>
					</ul>
				</div>
			</div>
		</div>
		
	</div>
</div>

<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red"></span> 个项目<br>
		是否要删除选择的配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<div class="form-dialog fenzu-dialog" id="fenzu-dialog">
	<div class="title">新增分组</div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">图书馆ID</span></div>
				<div class="right">
					<input type="text" name="" id="libid" class="g-input" placeholder="请输入"  />
					<span class="error-msg">请输入正确信息</span>
				</div>
			</li>
            <li>
                <div class="left"><span class="g-mustIn">图书馆名称</span></div>
                <div class="right">
                    <input type="text" name="" id="libname" class="g-input" placeholder="请输入"  />
                    <input type="hidden" name="" id="version_stamp" class="g-input" placeholder=""  />
                    <span class="error-msg">请输入正确信息</span>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">图书馆地址</span></div>
                <div class="right rdiqu">
                    <div class="g-select diqu">
                        <select id="nation">
                            <option value="-1">国家</option>
                        </select>
                        <span class="arr-icon"></span>
                    </div>
                </div>
                <div class="right rdiqu">
                    <div class="g-select diqu">
                        <select id="province">
                            <option value="-1">省</option>
                        </select>
                        <span class="arr-icon"></span>
                    </div>
                </div>
                <div class="right rdiqu">
                    <div class="g-select diqu">
                        <select id="city">
                            <option value="-1">市</option>
                        </select>
                        <span class="arr-icon"></span>
                    </div>
                    <span class="error-msg" style="float:left;">至少选择到市!</span>
                </div>
                <div class="right rdiqu">
                    <div class="g-select diqu">
                        <select id="area">
                            <option value="-1">区</option>
                        </select>
                        <span class="arr-icon"></span>
                    </div>
                </div>
            </li>
			<li>
				<div class="left">设备地理位置</div>
				<div class="right rdiqu">
					<div class="g-select diqu">
						<select id="devicePosition" style="color:#ff9933;background-color: #E0E0E0;width:160px">
                            <option value="-1">点击添加位置</option>
                        </select>
						<!-- <input type="text" id="devicePosition" value="点击添加位置信息"  readonly="readonly" class="g-input" style="color:#ff9933;background-color: #E0E0E0;width:160px" > -->
					</div>
				</div>
				<div class="right rdiqu">
                    <div class="g-select diqu">
                        <input type="text" id="jingdu"  name="device_lng" maxlength="5" style="background-color:#ffffff;height:28px" readonly="readonly"/>   
                    </div>
                </div>
                <div class="right rdiqu">
                    <div class="g-select diqu">
                       <input type="text" id="weidu" name="device_lat" maxlength="5" style="background-color:#ffffff;height:28px" readonly="readonly"/>
                    </div>
                </div>
			</li>
			<li>
				<div class="left">模版名称</div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type">
							<option value="-1" selected>选择模版</option>
						</select>
						<span class="arr-icon"></span>
					</div>
					 <%-- 管理员才能操作--%>
					 <%-- <c:if test="${operator!=null and operator.operator_type==1}">
						 <input type="button" value="编辑" class="edit-lib-temp g-btn-blue"/>
					 </c:if> --%>
					 <!-- 通过权限码判断权限  add by huanghuang 20170215 -->
					 <!-- 只有云平台管理人员和维护人员可以修改图书馆服务期限	modify by huanghuang 20170221 -->
					 <%-- <shiro:hasPermission name="0102010106"> --%>
					 <c:if test="${operator!=null and (operator.operator_type==1 or operator.operator_type==2) }">
						 <input type="button" value="编辑" class="edit-lib-temp g-btn-blue"/>
					 </c:if>
					 <%-- </shiro:hasPermission> --%>
					<span class="error-msg">请选择模版</span>
				</div>
			</li>
			<li>
				<div class="left">馆类型</div>
				<div class="right">
					<div class="g-select">
						<select class="choose-type">
							<option value="-1" selected>选择馆类型</option>
							<option value="1">子馆</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li class="choose-main hide">
				<div class="left">选择主馆</div>
				<div class="right">
					<div class="g-select">
						<select class="main_lib">
							<option value="">深圳图书馆</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<%-- <li>
				<div class="left">地址</div>
				<div class="right">
					<input type="text" name="" id="address" class="g-input" placeholder="请输入"  />
					
				</div>
			</li>
			<li>
				<div class="left">服务电话</div>
				<div class="right">
					<input type="text" name="" id="phone" class="g-input " placeholder="请输入"  />
					<span class="error-msg">错误信息</span>
				</div>
			</li> --%>
			<!-- 这之前关系是and，因为主馆可以更改子馆,不显示日期，提交按钮保存不了数据，故更改为or modify by huanghuang 20170221 -->
			<c:choose>
				<c:when test="${operator.operator_type==1 }">
					<li>
						<div class="left">开始时间</div>
						<div class="right">
							<div class="g-inputdate">
								<input type="text" id="start_date"  placeholder="输入日期"  class="g-input datepicker">
									<span class="icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">结束时间</div>
						<div class="right">
							<div class="g-inputdate">
								<input type="text" readonly="readonly" id="end_date" placeholder="输入日期"  class="g-input"><!-- datepicker -->
									<span class="icon"></span>
							</div>
						</div>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<div class="left">开始时间</div>
						<div class="right">
							<div class="g-inputdate">
								<input type="text" id="start_date"  placeholder="输入日期"  class="g-input">
									<span class="icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">结束时间</div>
						<div class="right">
							<div class="g-inputdate">
								<input type="text" readonly="readonly" id="end_date" placeholder="输入日期"  class="g-input"><!-- datepicker -->
									<span class="icon"></span>
							</div>
						</div>
					</li>
				</c:otherwise>
			</c:choose>
			<li id="addinfo">
				<div class="left">&nbsp;</div>
				<div class="right">
					<div class="add-li">
						<div class="li-list">
<!-- 							<div class="add-phone li">服务电话</div> -->
<!-- 							<div class="add-email li">服务邮箱</div> -->
						</div>
					</div>
					添加更多项
				</div>
			</li>
			
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input type="submit" value="保存" class="submit g-btn-blue" />
				</div>
			</li>
		</ul>
	</div>
</div>
<div class="lib-temp-dialog">
<div class="form-dialog-fix" id="">
	<div class="form-dialog">
		<div class="title">
			修改配置
			<input value='取消' name="cancel-save-temp" class='g-cancel2 g-btn-gray' type='reset'>
			<input placeholder='' name="save-temp"  class='g-submit2 g-btn-green' value='保存' type='submit'>
		</div>
		<div class="form-wrap lib-temp-dialog">
			<ul>
				<li>
					<div class="left">模板名称</div>
					<div class="right">
						<input type="text" name="" id="tpl_name" class="g-input" placeholder="请输入" />
						<input type="hidden" name="" id="version_stamp_sec" class="g-input" placeholder="请输入" />
						<span class="error-msg">错误提示</span>
					</div>
				</li>
				<li>
					<div class="left">服务周期</div>
					<div class="right">
						<input type="text" name="" id="service_cycle" class="g-input w80 dib mr10" placeholder="请输入" />个月
						<span class="error-msg">错误提示</span>
					</div>
				</li>
				<li>
					<div class="left">最大设备数</div>
					<div class="right">
						<input type="text" name="" id="max_devcount" class="g-input w80" placeholder="请输入" />
						<span class="error-msg">错误提示</span>
					</div>
				</li>
				<li>
					<div class="left">最大用户数</div>
					<div class="right">
						<input type="text" name="" id="max_opercount" class="g-input w80" placeholder="请输入" />
						<span class="error-msg">错误提示</span>
					</div>
				</li>
				<li>
					<div class="left">分馆数</div>
					<div class="right">
						<input type="text" name="" id="max_sublib" class="g-input w80" placeholder="请输入" />
						<span class="error-msg">错误提示</span>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>
</div>

<script type="text/javascript">
var tmpIdx = -1;//图书馆的模板暂存字段,新增图书馆时，会用到。add by huanghuang 20170221
var libPosLayer;
$(function(){
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();
        queryAllRegion();
		$(".main").css({
			"min-height":winH - headerH - pagingBarH -pageTitleBar
		});
	})();
	var operator=<shiro:principal/>;
	var delLayer = null;
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
	var operationFlag="";//add 表示新增 edit表示编辑
	//保存数据库返回的数据	LibInfo
	//保存主馆数据	master_lib
	//保存馆模版数据	LibTemp
	var LibInfo=null;//2016年12月13日 17:35:21
	//var main_lib_idx = new Array();
	main_lib_idx = [];
	var libs={};
	$(".delete").on("click",function(){
		//alert(o.library_idx);
		main_lib_idx = [];
		libs.isDelelteOne=false;
		libs.deleteIdxArr=new Array();
		var num = $("tbody input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function(){
			main_lib_idx.push($(this).attr("id"));
			var entity={
					"library_idx":$(this).attr("id"),
					"lib_id":$(this).parent().parent().next().text(),
					"version_stamp":$(this).parents("tr").find(".version_stamp").html()
			};
			libs.deleteIdxArr.push(entity);
		});
		console.log(main_lib_idx);
		for(var d=0;d<main_lib_idx.length;d++){
			if(main_lib_idx[d] == o.library_idx){
				layer.alert("不能删除本馆信息！");
				return;
			}
		}
		if(num>0){
			$(".g-delete-dialog .word").html("");
			$(".g-delete-dialog .word").append("当前选择了<span class='font-red'> "+num+" </span>条数据<br>是否要删除选择的图书馆信息？");
			delLayer = layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				scrollbar :false,
				closeBtn :0,
				shade:0.5,
				shadeClose :false,
				area:["400px"],
				offset :["195px"],
				content: $('.g-delete-dialog')
			});
		}else{
			layer.alert("请选择要删除的图书馆信息");
		}
	});
	
	//删除当前行的数据
	$("tbody").on("click",".delete",function(){
		main_lib_idx =[] ;
		libs=new Array();
		libs.isDelelteOne=true;
		libs.deleteIdx=$(this).parent().parent().find("div input[name='idx']").attr("id");
		libs.deleteId=$(this).parent().parent().children("td:nth-child(2)").text();
		libs.version_stamp=$(this).parents("tr").find(".version_stamp").html();
		main_lib_idx.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		$(".g-delete-dialog").find(".word").html(
		'是否删除 <span class="font-red">'+$(this).parents("tr").find("td:eq(1)").html()+'</span>');
		delLayer = layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.g-delete-dialog')
		});
	});
	
	libtem_idx = null;
	function GetDataToLayer(_this){
			//初始化弹出层
		$("#fenzu-dialog :text").val("");
		$("#fenzu-dialog").find("select").val(-1).change();
		$("li[name='add']").remove();
		$("#start_date").attr("disabled","disabled");
		$("#end_date").attr("disabled","disabled");
		$(".libtemp-type").attr("disabled","disabled");
		
		//隐藏上面的地址和服务电话栏
		//$("#address").attr("style","display:none");
		//$("#address").parent().parent().find(".left").attr("style","display:none");
		//$("#phone").attr("style","display:none");
		//$("#phone").parent().parent().find(".left").attr("style","display:none");
		//清除错误提示信息
		$("#fenzu-dialog").find("li").removeClass("error");
		
		//通过权限控制修改内容
		var oper = <shiro:principal/>;
		if(oper.operator_type == 1){
			$("#start_date").attr("disabled",false); 
			$("#end_date").attr("disabled",false); 
			$(".libtemp-type").attr("disabled",false);
		}
	    //修改图书馆服务模板权限 by huanghuang 20170215
		/* <shiro:hasPermission name="0102010105">
			$(".libtemp-type").attr("disabled",false);
   		</shiro:hasPermission> */
		//显示当前图书馆馆的信息
		var lib_idx=$(_this).parent().parent().find("div input[name='idx']").attr("id");
		libtem_idx = lib_idx;
		$(".main_lib option[value='"+libtem_idx+"']").attr("disabled",true);
		$.each(LibInfo,function(i,item){
			if(item.library_idx == lib_idx){
				$("#libid").removeAttr("disabled");
		    	$("#libid").removeAttr("style");
		    	$(".choose-type").parent().parent().parent().removeAttr("style");
			    if(item.library_idx==o.library_idx){
			    	$("#libid").attr("disabled","disabled");
			    	$("#libid").attr("style","color: gray;background-color: #E0E0E0");
			    	$(".choose-type").parent().parent().parent().attr("style","display:none;");
			    }
				$("#libid").val(item.lib_id);
				$("#libname").val(item.lib_name);
				$(".libtemp-type").val(item.lib_service_tpl_id);
				$("#start_date").val(formatDate(item.service_start_date,"yyyy-MM-dd"));
				$("#end_date").val(formatDate(item.service_expire_date,"yyyy-MM-dd"));
				$("#version_stamp").val(item.version_stamp);
				$.each(item.relLibs,function(m,rel){
					if(rel.slave_lib_id == lib_idx){
						$(".choose-type").val(1);
						$(".main_lib").val(rel.master_lib_id);
						$(".choose-main").show();
					}	
				});
				$.each(item.libInfo,function(m,info){
				    var html="";
				    if("24"!=info.infotype_idx){
                        html = "<li name='add'><div class='left'>"+info.info_type_desc +"</div><div class='right'>";
                    }
					if("23"==info.infotype_idx){
						html += "<input type='text'"+ "value='"+info.info_value +"' readonly='readonly' name='"+ info.infotype_idx +"' placeholder='请输入' class='g-input datatimepicker'></div>";
					}else if("24"==info.infotype_idx){
					    var infoval = info.info_value;
					    var nation =infoval.substring(0,2);
                        var province =infoval.substring(0,4);
                        var city =infoval.substring(0,6);
                        var area ="-1";
                        if(infoval.length > 7){
                            area =infoval.substring(0,8);
                        }
                        $("#nation").val(nation);
                        $("#nation").trigger("change");
                        $("#province").val(province);
                        $("#province").trigger("change");
                        $("#city").val(city);
                        $("#city").trigger("change");
                        $("#area").val(area);
                    }else{
						html += "<input type='text'"+ "value='"+info.info_value +"' name='"+ info.infotype_idx +"' placeholder='请输入' class='g-input'></div>";
					}
                    if("24"!=info.infotype_idx){
                        html += "<div class=\"delete-li\" ></div>";
                    }
					html += "</li>";
					$(".li-list").parents("li").before(html);
					$(".datatimepicker").datetimepicker({
				 		currentText: "现在",
			            closeText: "完成",
			            timeFormat: "HH:mm",
			            minDateTime: null,
			            maxDateTime: null,
			            maxTime: null,
			            minTime: null,
			            monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
						dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
						dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
						dayNamesMin: ['日','一','二','三','四','五','六'], 
						timeText: "时间",
			            hourText: "小时",
			            minuteText: "分钟",
					});	
				});
			}
		});
	
	}
	
	$("tbody").on("click",".config",function(){
		var libIdx = $(this).parent().parent().find("td.col1").children("div").children("input").attr("id");
		//获取当前页码
		var currentpage = $(".paging-bar li.active").text();
		var size= $('#showSize option:selected').text();
		
		var param = {
				"library_idx":libIdx,//"QJTSG",
				"currentpage":currentpage,
				"pageSize":size,
				"lib_type":0
			};
		//alert("libIdx--:--"+libIdx);
		$("#mainframe").load(basePath+"/library/libConfigPage",{"id":JSON.stringify(param)},function(){
			
		});
	});
	
	$("tbody").on("click",".edit",function(){
		editRow=this;
		GetDataToLayer(this);
		operationFlag="edit";
		var library_id = $(this).parent().parent().find("td[class='col1']").next().text();
		var param = {library_id : library_id};
		$("#device_lng").val("");
   	 	$("#device_lat").val("");
		$.ajax({
			 url:basePath+"/getLibPosition",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
		    	 console.info(data);
		    	 if(data){
		    		 data = data.result;
		    		 console.info(data);
		    		 if(data){
				    	 $("#device_lng").val(data[0].longitude);
				    	 $("#device_lat").val(data[0].latitude);
				    	 $("#jingdu").val(data[0].longitude);
				    	 $("#weidu").val(data[0].latitude);
		    		 }
		    	 }
		     }
		})
		layerOpen({
			"title":"编辑图书馆",
			"btnText":"保存",
			"btnColorClass":"g-btn-blue",
			"operType":"update"
		});
		$("#libid").attr("disabled","disabled");
	});
	$(".increase").on("click",function(){
		$("#libid").removeAttr("disabled","disabled");
        $("#nation").val(nation);
        $("#nation").trigger("change");
		$(".main_lib option").attr("disabled",false);
		$(".form-dialog :text").val("");
		$("li[name='add']").remove();
		//主馆新增子馆时显示的是主馆的模板 add by huanghuang 20170221
		var op = <shiro:principal/>;
		if(op.operator_type == 1){
			$(".libtemp-type").val("-1");
			$(".libtemp-type").attr("disabled",false);
		}else{
			var service_start_d = startTime;
			var service_expire_d = endTime;
			if(service_start_d.indexOf(" ")>=0){
				service_start_d = service_start_d.split(" ")[0];
			}
			if(service_expire_d.indexOf(" ")>=0){
				service_expire_d = service_expire_d.split(" ")[0];
			}
			$(".libtemp-type").val(tmpIdx);
			$(".libtemp-type").attr("disabled",true);
			$("#start_date").val(service_start_d);
			$("#end_date").val(service_expire_d);
		}
		
		//初始化类型选择
		$(".choose-type").val("-1");
		$(".choose-main").hide();
		//服务时间栏禁止操作
		$("#start_date").attr("disabled","disabled");
		$("#end_date").attr("disabled","disabled");
		//显示上面的地址和服务电话栏
		$("#address").attr("style","display:false");
		$("#address").parent().parent().find(".left").attr("style","display:false");
		$("#phone").attr("style","display:false");
		$("#phone").parent().parent().find(".left").attr("style","display:false");
		operationFlag="add";
		layerOpen({
			"title":"新增图书馆",
			"btnText":"保存",
			"btnColorClass":"g-btn-blue",
			"operType":"add"
		});
	});

	var dialogH,dialogTop = 130;

	$(window).on("resize",function(){
		var winH = $(window).height();
		var formH = $('.fenzu-dialog').height();
		dialogH = (formH+dialogTop) < winH ? formH : (winH -dialogTop);
	}());
	var layerOpenH=null;
	function layerOpen(options){
		var defaults = {
			title:"",
			btnText : "新增"
		};
		options = $.extend(defaults,options);

		var $submit = $(".fenzu-dialog .submit");
		$(".fenzu-dialog .title").text(options.title);
		$submit.val(options.btnText);
		$submit.removeClass("g-btn-green").removeClass(".g-btn-blue");
		$submit.addClass(options.btnColorClass);
		$submit.attr("name",options.operType);
		$(".fenzu-dialog").find("li").removeClass("error");
		layerOpenH=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题 
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["600px",dialogH+"px"],

			offset :[dialogTop],
			content: $('.fenzu-dialog'),
			success:function(layero,index){
				
			}
		});
	}
	
	$(".form-wrap").on("focus","input[type=text]",function(){
		$(this).parent().parent().removeClass("error");
	});
	
	$(".libtemp-type").focus(function(){
		$(this).parent().parent().parent().removeClass("error");
	});
	/**数据检查 QQ号 **/
	$("#fenzu-dialog").on("keyup","input[name=19]",function(){
		var qqCode=$(this).val();
		if(qqCode&&!$.isNumeric(qqCode)){
			$(this).parent().parent("li").find(".error-msg").remove();
			$(this).after("<span class='error-msg'>请输入数字</span>");
			$(this).parent().parent("li").addClass("error");
		}else if(!qqCode||$.isNumeric(qqCode)){
			$(this).parent().parent("li").removeClass("error");
		}
	});
	
	//EMAIL校验正则
	var emailReg=/(^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\.[a-zA-Z0-9_-]+$)|(^$)/;
	//网址 校验
	var _urlReg = /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/;
	var urlReg= new RegExp(_urlReg);  
	//固话校验
	//var phoneReg = /(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
	//var phoneReg=/^((\d{3,4}\-)|)\d{7,8}(|([-\u8f6c]{1}\d{1,5}))$/;
	var phoneReg=/^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
	var mPhoneReg=/(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/;
	/**服务电话**/
	$("#fenzu-dialog").on("focusout","input[name=20]",function(){
		var phone=$(this).val();
		var ismPhone= mPhoneReg.test(phone);
		var isPhone= phoneReg.test(phone);
		if(phone &&((!ismPhone) && (!isPhone))){
			$(this).parent().parent("li").find(".error-msg").remove();
			$(this).after("<span class='error-msg'>请输入正确的服务电话</span>");
			$(this).parent().parent("li").addClass("error");
		}else{
			$(this).parent().parent("li").removeClass("error");
		}
	});
	/**数据检查服务邮箱 **/
	$("#fenzu-dialog").on("focusout","input[name=18]",function(){
		var mail=$(this).val();
		if(mail&&!emailReg.test(mail)){
			$(this).parent().parent("li").find(".error-msg").remove();
			$(this).after("<span class='error-msg'>请输入正确的邮箱格式</span>");
			$(this).parent().parent("li").addClass("error");
		}else{
			$(this).parent().parent("li").removeClass("error");
		}
	});
	/**数据检查服务邮箱 **/
	$("#fenzu-dialog").on("focusin","input[name=18]",function(){
		$(this).parents("li").removeClass("error");
	});
	/**网址 数据检查**/
	$("#fenzu-dialog").on("focusout","input[name=17]",function(){
		var url=$(this).val();
		if(url&&!urlReg.test(url)){
			$(this).parent().parent("li").find(".error-msg").remove();
			$(this).after("<span class='error-msg'>请输入正确的网址格式，http://</span>");
			$(this).parent().parent("li").addClass("error");
		}else if(!url){
			$(this).parent().parent("li").removeClass("error");
		}
	});
	$("#fenzu-dialog").on("focusin","input[name=17]",function(){
		$(this).parents("li").removeClass("error");
	});
	/**固话数据检查**/
	$("#fenzu-dialog").on("focusout","input[name=15]",function(){
		var phone=$(this).val();
		if(phone&&!phoneReg.test(phone)){
			$(this).parent().parent("li").find(".error-msg").remove();
			$(this).after("<span class='error-msg'>请输入正确的电话格式</span>");
			$(this).parent().parent("li").addClass("error");
		}else{
			$(this).parent().parent("li").removeClass("error");
		}
	});
	$("#fenzu-dialog").on("focusin","input[name=15]",function(){
		$(this).parents("li").removeClass("error");
	});
	//数据检查
	var checkdata=function(libCheck){
		var returnData=true;
		if(libCheck){
			var libname = $("#libname").val();
			var libid = $("#libid").val();
			var temp = $("select.libtemp-type").val();
            var region = $("#city").val();

			var len_name = $("#libname").val().length;
			var len_libid = $("#libid").val().length;
			if(!libname || len_name>100){
				$("#libname").parent().parent().addClass("error");
				returnData=false;
			}
			
			if(!libid || len_libid>100){
				$("#libid").parent().parent().addClass("error");
				returnData=false;
				
			}
            if(region=="-1"){
                $("#city").closest("li").addClass("error");
                returnData=false;
            }else{
                $("#city").closest("li").removeClass("error");
            }
			if(temp == -1){
				$("select.libtemp-type").parent().parent().parent().addClass("error");
				returnData=false;
			}
			//检查QQ号
			var qqCode=$("#fenzu-dialog").find("input[name=19]").val();
			if(qqCode&&!$.isNumeric(qqCode)){
				$("input[name=19]").parent().parent("li").find(".error-msg").remove();
				$("input[name=19]").after("<span class='error-msg'>请输入数字</span>");
				$("input[name=19]").parent().parent("li").addClass("error");
				returnData=false;
			}
			/**服务电话**/
				var phone=$("#fenzu-dialog").find("input[name=20]").val();
				var ismPhone= mPhoneReg.test(phone);
				var isPhone= phoneReg.test(phone);
				console.log("isPhone",isPhone);
				console.log("ismPhone",ismPhone);
				if(phone&&(!ismPhone && !isPhone)){
					$("input[name=20]").parent().parent("li").find(".error-msg").remove();
					$("input[name=20]").after("<span class='error-msg'>请输入正确的服务电话</span>");
					$("input[name=20]").parent().parent("li").addClass("error");
					returnData=false;
				}
			//检查邮箱
			var mail=$("#fenzu-dialog").find("input[name=18]").val();
			if(mail&&!emailReg.test(mail)){
				$("input[name=18]").parent().parent("li").find(".error-msg").remove();
				$("input[name=18]").after("<span class='error-msg'>请输入正确的邮箱格式</span>");
				$("input[name=18]").parent().parent("li").addClass("error");
				returnData=false;
			}
			//网址检查
			var url=$("#fenzu-dialog").find("input[name=17]").val();
			if(url&&!urlReg.test(url)){
				$("input[name=17]").parent().parent("li").find(".error-msg").remove();
				$("input[name=17]").after("<span class='error-msg'>请输入正确的网址格式</span>");
				$("input[name=17]").parent().parent("li").addClass("error");
				returnData=false;
			}
			//固话检查
			var phone=$("#fenzu-dialog").find("input[name=15]").val();
			if(phone&&!phoneReg.test(phone)){
				$("input[name=15]").parent().parent("li").find(".error-msg").remove();
				$("input[name=15]").after("<span class='error-msg'>请输入正确的固定电话</span>");
				$("input[name=15]").parent().parent("li").addClass("error");
				returnData=false;
			}
			//var shutdownLibTime=$("#fenzu-dialog").find("input[name=23]").val();
			
			
		}
		return returnData;
	};
	
	//提交按钮操作
	$("#fenzu-dialog .submit").on("click",function(){
		GlobalGLoading();
		dataCheck=true;
		if(checkdata(dataCheck)==false) 
			return;
		var opertype=$(this).attr("name");
		//获取弹出层页面数据
		var lib_name = $("#libname").val();
		var lib_id = $("#libid").val();
		var jingdu = $("#jingdu").val();
		var weidu = $("#weidu").val();
		var lib_tpl_id = $(".libtemp-type").val();
		var service_start_date = $("#start_date").val();
		var service_expire_date =$("#end_date").val();
		var version_stamp=$("#version_stamp").val();
        var region = $("#area").val();
        if(region == "-1"){
            region = $("#city").val();
        }
		var lib_param = {
			"operator_idx":o.operator_idx,
			"lib_id":lib_id,
			"lib_name":lib_name,
			"lib_service_tpl_id":lib_tpl_id,
		};
		var libPosParam = {
				"lib_id":lib_id,
				"lib_name":lib_name,
				"jingdu":jingdu,
				"weidu":weidu
		};
		var rel_lib = {};
		var lib_type = $(".choose-type").val();
		var main_lib = $(".main_lib").val();
		if(lib_type == 1){
			rel_lib.master_lib_id = main_lib;
			rel_lib.rel_type = "1";  <%-- 1:主从关系   --%>
			lib_param.rellib_info = rel_lib;
		}

		var library_info = [];
		if($("#address").val() !=null && $("#address").val()!=""){
			var param = {"infotype_idx":$("#address").attr("name"),
					 "info_value":$("#address").val()};
			library_info.push(param);
		}
		if($("#phone").val() !=null && $("#phone").val()!=""){
			param = {"infotype_idx":$("#phone").attr("name"),
				 	"info_value":$("#phone").val()};
			library_info.push(param);
		}
		
		$("li[name='add']").each(function(){
			var info_idx = $(this).find(":text").attr("name");
			var info_value = $(this).find(":text").val();
			if(info_value){
				var data ={
					"infotype_idx":info_idx,
					"info_value":info_value,
				};
				library_info.push(data);
			}
		});
        var rdata ={
            "infotype_idx":24,
            "info_value":region,
        };
        library_info.push(rdata);
		lib_param.library_infos = library_info;
		lib_param.lib_type = 0;
		if(opertype == "add"){
			lib_param.service_start_date = service_start_date;
			lib_param.service_expire_date = service_expire_date;
			$.ajax({
				url:basePath+"/library/addLibraryInfo",
				type:"POST",
				data:{
					"json":JSON.stringify(lib_param),
					"jsonPos":JSON.stringify(libPosParam)
					},
				success:function(data){
					if(data.state){
						showMsg({
							timeout : 1000,
							showText : '添加图书馆信息成功',
							status : true,
							callback:function(){
								if(layerOpenH){
									layer.close(layerOpenH);
								}
								var size = $(".g-select.refresh").find("option:selected").text();
								var currentpage = $(".paging-bar li.active").text();
								var dat= GetData();
								dat.page = currentpage;
								dat.pageSize = size;
								$.select(dat);
							}
						});
						
					}else if(data.retMessage){
					   showRetMessage(data.retMessage);
					}
				}
			});
		}
		if(opertype == "update"){
			lib_param.service_start_date = service_start_date;
			lib_param.service_expire_date = service_expire_date;
			lib_param.library_idx = libtem_idx;
			lib_param.version_stamp = version_stamp;
			console.log(lib_param);
			$.ajax({
				url:basePath+"/library/updateLibraryInfo",
				type:"POST",
				data:{
					"json":JSON.stringify(lib_param),
					"jsonPos":JSON.stringify(libPosParam)
				},
				success:function(data){
					console.log(data);
					if(data.state){
						showMsg({
							timeout : 1000,
							showText : '修改图书馆信息成功',
							status : true,
							callback:function(){
								if(layerOpenH){
									layer.close(layerOpenH);
								}
								var size = $(".g-select.refresh").find("option:selected").text();
								var currentpage = $(".paging-bar li.active").text();
								var dat= GetData();
								dat.page = currentpage;
								dat.pageSize = size;
								$.select(dat);
							}
						});
					
					}else{
						var message = data.retMessage;
						if(message.indexOf("optimistic")>=0){
							showRetMessage("当前选择的数据不是最新数据,请刷新之后再做编辑");
						}else{
							showRetMessage(data.retMessage);
						}
					}
				}
			});
		}
	});
		
	$(".libtemp-type").on("change",function(){
		var begin = $("#start_date").val();
		var end =null;
		if(!begin){ //新增的时候 服务时间是根据模板算出来的
			//设置服务开始时间
			var datetime = new Date();
			var year = datetime.getFullYear();  
 			var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;  
  			var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();  
   			//var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();  
    		//var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();  
    		//var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();  
    		begin = year + "-" + month + "-" + date;//+" "+hour+":"+minute+":"+second;
    		$("#start_date").val(begin);
    		
    		var tp_id=$(".libtemp-type").val();
    		var service_month = null;
    		//设置服务结束时间
    		$.each(LibTemp,function(i,item){
    			if(item.lib_service_tpl_id == tp_id)
    				service_month = item.service_cycle;
    		});
    		datetime.setMonth(datetime.getMonth()+service_month);
    		month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;  
  			date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();  
   			//hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();  
    		//minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();  
    		//second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds(); 
    		year=datetime.getFullYear();
    		if(year>2030){year=2030;}
    		end = year + "-" + month + "-" + date;//+" "+hour+":"+minute+":"+second;
    		console.log(end);
    		$("#end_date").val(end);
		}else{
			//设置服务结束时间
			var datetime = new Date();
			var tp_id=$(".libtemp-type").val();
    		var service_month = null;
    		//设置服务结束时间
    		$.each(LibTemp,function(i,item){
    			if(item.lib_service_tpl_id == tp_id)
    				service_month = item.service_cycle;
    		});
    		datetime.setMonth(datetime.getMonth()+service_month);
			var year = datetime.getFullYear();  
 			if(year>2030){year=2030;}
 			var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;  
  			var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();  
   			//var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();  
    		//var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();  
    		//var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();  
    		end = year + "-" + month + "-" + date;//+" "+hour+":"+minute+":"+second;
    		$("#end_date").val(end);
		}
	});
		
	function onstart_date_change(){
		console.log("start_time change");
		//var begin = $("#start_date").val();
		var end =null;
		
		//var datetime = new Date();//后去当前时间
		var data=$("#start_date").val();
		var datetime=new Date(Date.parse(data.replace(/-/g,  "/")));
		console.log("datetime",data.replace(/-/g,  "/"));
		var tp_id=$(".libtemp-type").val();
   		var service_month = null;
   		//设置服务结束时间
   		$.each(LibTemp,function(i,item){
   			if(item.lib_service_tpl_id == tp_id)
   				service_month = item.service_cycle;
   		});
   		datetime.setMonth(datetime.getMonth()+service_month);
		var year = datetime.getFullYear();  
		if(year>2030){year=2030;}
		var month = (datetime.getMonth() + 1) < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;  
 		var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();  
  		//var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();  
   		//var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();  
   		//var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();  
   		end = year + "-" + month + "-" + date;//+" "+hour+":"+minute+":"+second;
   		$("#end_date").val(end);
   	
	};	
		
		
		
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});

	$(".form-btn.remove").on("click",function(){
		var Library_idx = main_lib_idx;
		if(!Library_idx)  return;
		var data=new Array();
		if(libs.isDelelteOne==true){
			if(libs.deleteIdx){
				var entity={
					"library_idx":libs.deleteIdx,
					"lib_id":libs.deleteId,
					"version_stamp":libs.version_stamp
				};
				data.push(entity);
			}
		}else{
			if(libs.deleteIdxArr){
				data=libs.deleteIdxArr;
			}
		}
		console.info("libs-----",libs);
		//先删除图书馆地址位置
		$.ajax({
			url:basePath+"/device/deleteLibraryPosition",
			type:"POST",
			data:{"json":JSON.stringify(data)},
			success:function(data){
				
		   }
		});
		//删除图书馆信息
		$.ajax({
			url:basePath+"/library/deleteLibraryInfo",
			type:"POST",
			data:{"json":JSON.stringify(data)},
			success:function(data){
				var size = $(".g-select.refresh").find("option:selected").text();
				var currentpage = $(".paging-bar li.active").text();
				var param ={"page":currentpage,"pageSize":size};
				if(data.state){
					showMsg({
							timeout : 1000,
							showText : '删除图书馆信息成功',
							status : true,
							callback:function(){
									$(".layui-layer-shade").click();
									if (delLayer) {
										layer.close(delLayer);
									}
								}
						});
						var temp = $(".drop-box div.right ul li.active").text();
						var temp_select = temp.replace(/[^0-9]+/g, '');
						var temp_desc = $(".drop-box div.left ul li.active").index();
						var query_type = $("#query").val();
						if(query_type == 1){
							param.lib_name = $("#query_data").val();
						}else if(query_type == 2){
							if($("#query_data").val() == "子馆")
								param.slave_lib_id = 0;
								
							if($("#query_data").val() == "主馆")
								param.master_lib_id = 0;
						}
						if(temp!=null &&temp!=""){
							if(temp_desc == 0)
								param.service_cycle = temp_select;
							if(temp_desc == 1)
								param.max_device_count = temp_select;
							if(temp_desc == 2)
								param.max_operator_count = temp_select;
							if(temp_desc == 3)
								param.max_sublib_count = temp_select;
						}
						param.lib_type = 1;
						$.select(param);
				}else if(data.retMessage){
					var message=data.retMessage;
					if(message.indexOf("optimistic")>=0){
						showRetMessage("当前选择的数据不是最新数据,请刷新之后再做删除");
					}else{
						showRetMessage(data.retMessage);
					}
					/* showRetMessage(data.retMessage);
					$.select(param);
					$(".layui-layer-shade").trigger("click"); */
					if (delLayer) {
							layer.close(delLayer);
					}
				}
			}
		});
	});

	function showMsg(option){
		var defaults = {
			timeout : option.timeout || 1000,
			showText : option.showText || '添加配置成功',
			backgroundColor : option.status === true ? "#2ab65b" :"#ff3d3d",
			callback:function(){
				if(option.status == true){
					$(".layui-layer-shade").click();
				}
			}
		};
		defaults = $.extend(defaults,option);
		layer.msg(defaults.showText, {
			area:["240px"],
			offset:["110px"],
		  	time: defaults.timeout,
		  	success: function(layero, index){
		  	    $(".layui-layer-hui").css("background-color",defaults.backgroundColor);
		  	}
		}, function(){
			defaults.callback();
		});  
	}
	
	$(".datepicker" ).datepicker({
		numberOfMonths:1,//显示几个月  
		showButtonPanel:false,//是否显示按钮面板  
		dateFormat: 'yy-mm-dd',//日期格式  yy-mm-dd 00:00:00
		clearText:"清除",//清除日期的按钮名称  
		closeText:"关闭",//关闭选择框的按钮名称  
		yearSuffix: '', //年的后缀  
		showMonthAfterYear:true,//是否把月放在年的后面  
		//defaultDate:'2016-04-19',//默认日期  
		//minDate:'2011-03-05',//最小日期  
		//maxDate:'2011-03-20',//最大日期  
		monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
		dayNamesMin: ['日','一','二','三','四','五','六'],  
		onSelect: function(selectedDate) {//选择日期后执行的操作  
			onstart_date_change();
		}  
	});

	$(".add-li").on("click",function(){
		console.log("~~~~~~~add-li~~~~~~");
		$(".li-list").toggle();
	});
	$(document).bind("click",function(e){
		if($(e.target).closest(".add-li").length == 0){
			$(".li-list").hide();
        }
	});
	$(".li-list").on("click",".add-item",function(e){
		e.stopPropagation();//终止事件冒泡
		var idx = $(this).attr("name");
		var desc = $(this).text();
		var html = "<li name='add'><div class='left'>"+desc +"</div>";
		if(idx=="23"){//休馆时间，需要用到时间插件 
			html += "<div class='right'><input type='text' name='"+ idx +"' readonly='readonly' placeholder='请输入' class='g-input datepicker'>";
		}else{
			html += "<div class='right'><input type='text' name='"+ idx +"' placeholder='请输入' class='g-input'>";
		}
		html += "</div>";
		html += "<div class=\"delete-li\" ></div>";
		html += "</li>";
		$(this).parents("li").before(html);
		
		$(".datepicker").datetimepicker({
	 		currentText: "现在",
            closeText: "完成",
            timeFormat: "HH:mm",
            minDateTime: null,
            maxDateTime: null,
            maxTime: null,
            minTime: null,
            monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
			dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
			dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
			dayNamesMin: ['日','一','二','三','四','五','六'], 
			timeText: "时间",
            hourText: "小时",
            minuteText: "分钟",
             
		});
		
	});

	$(".choose-type").on("change",function(){
		if($(this).val()==1){
			$(".choose-main").show();
		}else{
			$(".choose-main").hide();
		}
	});
	/**
		点击显示筛选条件 或者 隐藏筛选条件
	**/
	$(".template-filter .btn-wrap").on("click",function(){
		$(".drop-box").toggle();
	});
	/**
		点击左边 筛选条件
	**/
	$(".drop-box .left li").on("click",function(){
		var thisIndex = $(this).index();
		$(this).addClass("active").siblings("li").removeClass("active");//同级元素去除效果
		$(".drop-box .right ul").eq(thisIndex).addClass("active").siblings("ul").removeClass("active");
	});
	/**
		点击右边 筛选条件
	**/
	$(".drop-box .right ul").on("click","li",function(){
		$(this).addClass("active").siblings("li").removeClass("active");
	});

	var relationH = 600;
	$(window).on("resize",function(){
		var winH = $(window).height();
		relationH = (relationH+dialogTop) < winH ? relationH : (winH -dialogTop);
	}());
	
	$("tbody").on("click",".relation",function(){
		console.log("去除上面的full-line类名就是虚线啦");
		$(".con-left ul").html("");
		$(".con-right").html("");
		var main_left = "";
		var middle = "";
		var slave_right ="";
		//主馆idx
		var idx= $(this).parent().parent().find("div input[name='idx']").attr("id");
		console.log("LibInfo!!!!!",LibInfo);
		$.each(LibInfo,function(i,item){
			if(item.library_idx == idx){
				middle ="<div class='g-guan theme-green'>"+item.lib_name+"</div>";
				$.each(item.relLibs,function(j,elem){
					if(elem.master_lib_id == idx){
						console.log("master_lib",master_lib);
						$.each(master_lib,function(m,lib_info){
							if(lib_info.library_idx == elem.slave_lib_id){
								slave_right +="<div class='item'><div class='g-guan'>"+lib_info.lib_name+"</div></div>";
							}
						});
					}
					if(elem.slave_lib_id == idx){
						$.each(master_lib,function(m,lib_info){
							if(lib_info.library_idx == elem.master_lib_id){
								main_left +="<li><div class='g-guan'>"+lib_info.lib_name+"</div></li>";
							}
						});
					}
				});
			}
		});
		if(slave_right != "" && main_left !=""){
			$(".con-area").eq(1).attr("style","display:none");
			$(".con-area").eq(0).attr("style","display:false");
			$("#left_main ul").html(main_left);
			$("#mid-main ul").html("<li>"+middle+"</li>");
			$("#right_slave").html(slave_right);
		}else{
			
			if(slave_right == ""){
				$(".con-area").eq(0).attr("style","display:none");
				$(".con-area").eq(1).attr("style","display:false");
				$("#left_main2 ul").html(main_left);
				$("#right_slave2").html("<div class='item'>"+middle+"</div>");
			}else if(main_left == ""){
				$(".con-area").eq(1).attr("style","display:none");
				$(".con-area").eq(0).attr("style","display:false");
				$("#mid-main ul").html("<li>"+middle+"</li>");
				$("#right_slave").html(slave_right);
			}
		}
		layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题 
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["860px",relationH+"px"],
			offset :[dialogTop],
			content: $('.relation-map'),
			success:function(layero,index){
				
			}
		});

	});
	
	$(".search").on("click",function(){
		var size = $(".g-select.refresh").find("option:selected").text();
		var data= GetData();
		data.page = "1";
		data.pageSize = size;
		$.select(data);
	});
	
	jQuery.select= function(param){
		$("tbody").html("");
		selectMasterLib();
		//var oper =<shiro:principal/>;
		if(o.operator_type == 3){
			param.library_idx = o.library_idx;
			$("div.btn-wrap").attr("style","display:none");
		} 
		$.ajax({
			url:basePath+"/library/selectLibinfoByParam",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
					if(data.state){
						if(data.result.rows&&data.result.rows.length==0){
							var index = param.page;
							if(index >1){
								param.page = index-1;
								$.select(param);
							}		
						}else{
							LibInfo = data.result.rows;
							console.log("LibInfo Select",LibInfo);
							$.each(data.result.rows,function(i,item){
							//遍历取地址和电话
							var phone = new Array();
							var address = new Array();
							$.each(item.libInfo,function(j,info){
								if(info.info_type_desc == "服务电话"){
									$("#phone").attr("name",info.infotype_idx);
									phone.push(info.info_value);
								}
								if(info.info_type_desc == "地址"){
									$("#address").attr("name",info.infotype_idx);
									address.push(info.info_value);
								}
							});
							var sty="";
							if(param.library_idx==item.library_idx){
								sty="display:none;";
							}
							//formatDate(
							 if(item.relLibs.length !=0){
								var service_expire_date=item.service_expire_date;
								var service_start_date=item.service_start_date;
								if(service_expire_date.indexOf(" ")>=0){
									service_expire_date=service_expire_date.split(" ")[0];
								}
								if(service_start_date.indexOf(" ")>=0){
									service_start_date=service_start_date.split(" ")[0];
								}
								$("tbody").append(
								"<tr>"+
								"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.library_idx+"' /></div></td>"+
								"<td class=''>" + item.lib_id + "</td>"+
								"<td class='col2'>" + item.lib_name + "</td>"+
								"<td class='col3'>" + address + "</td>"+
								"<td class='col4'>" + phone + "</td>"+
								"<td class='col5'>" + service_start_date + "</td>"+
								"<td class='col6'>" + service_expire_date+ "</td>"+
								"<td class='col7'>" +(item.lib_service_tpl_id==null?'否':'是('+item.lib_service_tpl_id+')') + "</td>"+
								"<td class='col8'>"+
									"<span class='btn-a edit'>编辑</span>"+
									"<span class='btn-a config g-btn-blue'>配置</span>"+
									"<span class='btn-a relation g-btn-blue'>关系</span>"+
									"<span class='btn-a delete' style='"+sty+"'>删除</span>"+
								"</td>"+
								"<td class='version_stamp hide'>" + item.version_stamp+ "</td>"+
								"</tr>");
							}else{
								var service_expire_date=item.service_expire_date;
								var service_start_date=item.service_start_date;
								if(service_expire_date.indexOf(" ")>=0){
									service_expire_date=service_expire_date.split(" ")[0];
								}
								if(service_start_date.indexOf(" ")>=0){
									service_start_date=service_start_date.split(" ")[0];
								}
								$("tbody").append(
								"<tr>"+
								"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.library_idx+"' /></div></td>"+
								"<td class=''>" + item.lib_id + "</td>"+
								"<td class='col2'>" + item.lib_name + "</td>"+
								"<td class='col3'>" + address + "</td>"+
								"<td class='col4'>" + phone + "</td>"+
								"<td class='col5'>" + service_start_date + "</td>"+
								"<td class='col6'>" + service_expire_date+ "</td>"+
								"<td class='col7'>" +(item.lib_service_tpl_id==null?'否':'是('+"<span class='color'>"+item.lib_service_tpl_id+"</span>"+')') + "</td>"+
								"<td class='col8'>"+
									"<span class='btn-a edit'>编辑</span>"+
									"<span class='btn-a config g-btn-blue'>配置</span>"+
									//"<span class='btn-a relation g-btn-blue'>关系</span>"+
									"<span class='btn-a delete'>删除</span>"+
								"</td>"+
								"<td class='version_stamp hide'>" + item.version_stamp+ "</td>"+
								"</tr>");
							
							} 
							});
							
						}
						
				    }
		    var t=0;
			<shiro:lacksPermission name="0102010102">
				t++;
				$(".delete").attr("style","display:none;");
	   		</shiro:lacksPermission>
	    	<shiro:lacksPermission name="0102010103">
					t++;
					$(".edit").attr("style","display:none;");
	    	</shiro:lacksPermission>
	    	//查看图书馆关系权限判断，add by huanghuang 20170216
	    	<shiro:lacksPermission name="0102010107">
					t++;
					$(".relation").attr("style","display:none;");
	    	</shiro:lacksPermission>
	    	
	    	$.pagination(data.result);      
			}
		});
		
	};
	
	function getTempIdx(){
		var o = <shiro:principal/>;
		var data= {};
		data.library_idx = o.library_idx;
		$.ajax({
			url:basePath+"/library/selectLibinfoByParam",
			type:"POST",
			data:{"json":JSON.stringify(data)},
			success:function(data){
				tmpIdx = data.result.rows[0].lib_service_tpl_id;
				startTime = data.result.rows[0].service_start_date;
				endTime = data.result.rows[0].service_expire_date;
			}
		});
	}
	
	$(document).ready(function(){
		getTempIdx();
		$("tbody").html("");
		var lib_idx = null;
		if(o.operator_type == 3){
			lib_idx = o.library_idx;
			$("div.btn-wrap").attr("style","display:none");
		}
		var size=$(".g-select.refresh").find("option:selected").text();
		var param = {"page":"1",
					 "pageSize":size,
					 "library_idx":lib_idx,
					 "lib_type":0};
		$.ajax({
			url:basePath+"/library/selectLibinfoByParam",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				LibInfo = [] ;
				LibInfo = data.result.rows;
				console.log("LibInfo",LibInfo);
				$.each(data.result.rows,function(i,item){
					//遍历取地址和电话
					var phone = new Array();
					var address = new Array();
					$.each(item.libInfo,function(j,info){
						if(info.info_type_desc == "服务电话"){
							$("#phone").attr("name",info.infotype_idx);
							phone.push(info.info_value);
						}
						if(info.info_type_desc == "地址"){
							$("#address").attr("name",info.infotype_idx);
							address.push(info.info_value);
						}
					});
					//console.log(item.library_idx);
					var sty="";
					if(lib_idx==item.library_idx){
						sty="display:none;";
					}
					if(item.relLibs.length !=0){
						var service_expire_date=item.service_expire_date;
						var service_start_date=item.service_start_date;
						if(service_expire_date.indexOf(" ")>=0){
							service_expire_date=service_expire_date.split(" ")[0];
						}
						if(service_start_date.indexOf(" ")>=0){
							service_start_date=service_start_date.split(" ")[0];
						}
						$("tbody").append(
						"<tr>"+
						"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.library_idx+"' /></div></td>"+
						"<td class=''>" + item.lib_id + "</td>"+
						"<td class='col2'>" + item.lib_name + "</td>"+
						"<td class='col3'>" + address + "</td>"+
						"<td class='col4'>" + phone + "</td>"+
						"<td class='col5'>" + service_start_date + "</td>"+
						"<td class='col6'>" + service_expire_date+ "</td>"+
						"<td class='col7'>" +(item.lib_service_tpl_id==null?'否':'是('+item.lib_service_tpl_id+')') + "</td>"+
						"<td class='col8'>"+
							"<span class='btn-a edit'>编辑</span>"+
							"<span class='btn-a config g-btn-blue'>配置</span>"+
							"<span class='btn-a relation g-btn-blue'>关系</span>"+
							"<span class='btn-a delete' style='"+sty+"'>删除</span>"+
						"</td>"+
						"<td class='version_stamp hide'>" + item.version_stamp+ "</td>"+
						"</tr>");
					}else{
						var service_expire_date=item.service_expire_date;
						var service_start_date=item.service_start_date;
						if(service_expire_date.indexOf(" ")>=0){
							service_expire_date=service_expire_date.split(" ")[0];
						}
						if(service_start_date.indexOf(" ")>=0){
							service_start_date=service_start_date.split(" ")[0];
						}
						$("tbody").append(
						"<tr>"+
						"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.library_idx+"' /></div></td>"+
						"<td class=''>" + item.lib_id + "</td>"+
						"<td class='col2'>" + item.lib_name + "</td>"+
						"<td class='col3'>" + address + "</td>"+
						"<td class='col4'>" + phone + "</td>"+
						"<td class='col5'>" + service_start_date + "</td>"+
						"<td class='col6'>" + service_expire_date+ "</td>"+
						"<td class='col7'>" +(item.lib_service_tpl_id==null?'否':'是('+"<span class='color'>"+item.lib_service_tpl_id+"</span>"+')') + "</td>"+
						"<td class='col8'>"+
							"<span class='btn-a edit'>编辑</span>"+
							"<span class='btn-a config g-btn-blue'>配置</span>"+
							//"<span class='btn-a relation g-btn-blue'>关系</span>"+
							"<span class='btn-a delete'>删除</span>"+
						"</td>"+
						"<td class='version_stamp hide'>" + item.version_stamp+ "</td>"+
						"</tr>");
					}
				});
    		
			if(o.operator_type == 2){
			$(".increase").attr("style","display:none;");
			$(".delete").attr("style","display:none;");
			$("div.btn-wrap").attr("style","display:none");
			}
			var t=0;
			<shiro:lacksPermission name="0102010101">
				$(".increase").attr("style","display:none;");
   			</shiro:lacksPermission>
   			<shiro:lacksPermission name="0102010102">
   			 	t++;
				$(".delete").attr("style","display:none;");
    		</shiro:lacksPermission>
    		<shiro:lacksPermission name="0102010103">
    			t++;
				$(".edit").attr("style","display:none;");
    		</shiro:lacksPermission>
    		//查看图书馆关系权限判断，add by huanghuang 20170216
	    	<shiro:lacksPermission name="0102010107">
					t++;
					$(".relation").attr("style","display:none;");
	    	</shiro:lacksPermission>
    		
    		$.pagination(data.result); 
		}	
		});
		
		
	});
	
	//数据去重
	function unique(arr){
		arr.sort(compareNumbers);
    	var result = [];
    	var hash = {};
    	for (var i = 0, elem; (elem = arr[i]) != null; i++) {
    	    if (!hash[elem]) {
    	        result.push(elem);
    	        hash[elem] = true;
       	 	}
    	}
    	return result;
	}
	//比较数字
	function compareNumbers(a, b) {
		return a - b;
	}
	
	//获取模版数据到	模板筛选 和	模板类型下拉框  
	$(document).ready(function(){
		$.ajax({
			url:basePath+"/libtempconf/selectAllTemp",
			type:"POST",
			success:function(data){
				LibTemp = [] ;
				LibTemp = data.result;
				var cycle = new Array();
				var dev_count = new Array();
				var oper_count = new Array();
				var sublib_count = new Array();
				var temp_name = "";
				$(".drop-box .right ul").html("");
				$.each(data.result,function(i,item){
					if("0"==item.lib_service_tpl_id){//云平台模板不显示出来
						return;
					}
					cycle.push(item.service_cycle);
					dev_count.push(item.max_device_count);
					oper_count.push(item.max_operator_count);
					sublib_count.push(item.max_sublib_count);
					temp_name +="<option value=\""+item.lib_service_tpl_id+"\">"+item.lib_service_tpl_desc+"</option>";
				});
				
				//添加数据到模版类型
				$(".libtemp-type").append(temp_name);
				
				var serv_cycle=unique(cycle);
				var html ="";
				for(var i =0;i<serv_cycle.length;i++){
					html +="<li>"+serv_cycle[i]+"个月</li>";
				}
				$(".drop-box .right ul").eq(0).html(html);
					
				var dev_num = unique(dev_count);
				html="";
				for(var i =0;i<dev_num.length;i++){
					html +="<li>"+dev_num[i]+"台</li>";
				}
				$(".drop-box .right ul").eq(1).html(html);
					
				var oper_num = unique(oper_count);
				html="";
				for(var i =0;i<oper_num.length;i++){
					html +="<li>"+oper_num[i]+"人</li>";
				}
				$(".drop-box .right ul").eq(2).html(html);
				
				var sublib_num = unique(sublib_count);
				html="";
				for(var i =0;i<sublib_num.length;i++){
					html +="<li>"+sublib_num[i]+"个</li>";
				}
				$(".drop-box .right ul").eq(3).html(html);
			}
		});
	});
	function selectMasterLib(){
			$.ajax({
			url:basePath+"/library/selectMasterLib",
			type:"POST",
			success:function(data){
				master_lib = [];
				master_lib = data.result;
				var html ="";
				//var oper = <shiro:principal/>;
				if(o.operator_type == 3){
					$.each(master_lib,function(i,item){
						if(item.library_idx==0 || item.lib_type == 1)
							return;
						if(item.library_idx == o.library_idx){
							html = "<option value='"+item.library_idx+"'>"+item.lib_name+"</option>";
						}
					});
				}
				if(o.operator_type < 3){
					$.each(master_lib,function(i,item){
					if(item.library_idx==0 || item.lib_type == 1)
							return;	
						html += "<option value='"+item.library_idx+"'>"+item.lib_name+"</option>";
					});
				}
				$(".main_lib").html(html);	
			}
		});
	
	}
	$(document).ready(function(){
		$.ajax({
			url:basePath+"/library/selectLibInfoType",
			type:"POST",
			success:function(data){
				var info_type="";
				$.each(data.result,function(i,item){
				    if("24" != item.infotype_idx){
                        info_type +="<div class='add-item li' name='"+item.infotype_idx+"' >"+item.info_type_desc+"</div>";
                    }
				});
				$(".li-list").html(info_type);
			}
		});
		selectMasterLib();
		
	});
	
	//上一页操作
	$("#page").on("click",".prev-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size= $(".g-select.refresh").find("option:selected").html();
		
		var data= GetData();
		data.page = page;
		data.pageSize = size;
	 	
		$.select(data);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $(".g-select.refresh").find("option:selected").html();
		
		var data= GetData();
		data.page = page;
		data.pageSize = size;
	 	
		$.select(data);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var size= $(".g-select.refresh").find("option:selected").html();
		var page = $(this).html();
		if(page=="...") return;
		
		var data= GetData();
		data.page = page;
		data.pageSize = size;
	 	
		$.select(data);
	}); 
	
	 function GetData(){
	 	var param ={};
	 	var temp_arr=[];
	 	
	 	//取得选中的
		$(".drop-box div.right ul li.active").each(function(index,dom){
			var index=$(dom).parent().index();//对应左边的li index
			var temp=$(dom).text();
			var temp_select = temp.replace(/[^0-9]+/g, '');
			if(temp!=null &&temp!=""){
				if(index == 0)
					param.service_cycle = temp_select;
				if(index == 1)
					param.max_device_count = temp_select;
				if(index == 2)
					param.max_operator_count = temp_select;
				if(index == 3)
					param.max_sublib_count = temp_select;
			}
		});
		//var temp_desc = $(".drop-box div.left ul li.active").index();
		//var query_type = $("#query").val();
		var query_type=1;//固定是按馆名搜索
		if(query_type == 1){
			param.lib_name = $("#query_data").val().replace(/\s/g, "");
		}else if(query_type == 2){
			if($("#query_data").val() == "子馆")
				param.slave_lib_id = 0;
			
			if($("#query_data").val() == "主馆")
				param.master_lib_id = 0;	
		}	
		param.lib_type = 0;
		//console.log(param);
		return param;
	};
	<%-- 
		云平台管理员 点击编辑模板 可以编辑图书馆模板
	--%>
	var lib_service_tpl_id=null;
	var editLibTempLayer=null;
	$(".edit-lib-temp.g-btn-blue").on("click",function(){
		$(".form-dialog-fix :text").val("");//清除数据
		//获取模板IDX
		lib_service_tpl_id=$("select.libtemp-type").val();
		if(lib_service_tpl_id==-1){//是否选择模板
			$(this).parent().parent("li").addClass("error");
			return;
		}
		var lib_temp={"lib_service_tpl_id":lib_service_tpl_id};
		//需要查询廚选定的模板的数据
		$.ajax({
			url:basePath+"/libtempconf/selLibraryServiceTemplateByIdx",
			data:{"req":JSON.stringify(lib_temp)},
			type:"GET"
		}).done(function(data){
			if(data&&data.state&&data.result){
				var temp=data.result;
				editLibTempLayer=layer.open({
						title: false,
						closeBtn :0,
						scrollbar :false,
						offset :[dialogTop],
						yes: function(index, layero){
							//点击确定
						    layer.close(index); //如果设定了yes回调，需进行手工关闭
			  			},
			  			success:function(index, layero){
							$content=$(index).find(".layui-layer-content");
							$content.html($(".lib-temp-dialog").find(".form-dialog-fix").html());
							$content.find("#tpl_name").val(temp.lib_service_tpl_desc);
							$content.find("#service_cycle").val(temp.service_cycle);
							$content.find("#max_devcount").val(temp.max_device_count);
							$content.find("#max_opercount").val(temp.max_operator_count);
							$content.find("#max_sublib").val(temp.max_sublib_count);
							$content.find("#version_stamp_sec").val(temp.version_stamp);
							$content.find(".form-dialog").show();
							$(index).find(".layui-layer-btn").addClass("hide");
							$content.css("padding","0px");//调整样式
							/**
								编辑图书馆模板  保存模板
							**/
							$content.find("input[name=save-temp]").on("click",function(){
								var lib_service_tpl_desc=$content.find("#tpl_name").val();
								var service_cycle=$content.find("#service_cycle").val();
								var max_device_count=$content.find("#max_devcount").val();
								var max_operator_count=$content.find("#max_opercount").val();
								var max_sublib_count=$content.find("#max_sublib").val();
								var version_stamp_sec=$content.find("#version_stamp_sec").val();
								if(!lib_service_tpl_id||lib_service_tpl_id==-1){
									layer.alert("模板ID为空");
									return;
								}
								var lib_temp={
									"lib_service_tpl_id":lib_service_tpl_id,
									"lib_service_tpl_desc":lib_service_tpl_desc,
									"service_cycle":service_cycle,
									"max_device_count":max_device_count,
									"max_operator_count":max_operator_count,
									"max_sublib_count":max_sublib_count,
									"version_stamp":version_stamp_sec
								};
								var updData={
									"libconfig":lib_temp,
									"operator_idx":operator.operator_idx
								};
								//获取填充数据
								$.ajax({
									url:basePath+"/libtempconf/Updatelibtplconf",
									data:{"json":JSON.stringify(lib_temp)},
									type:"POST"
								}).then(function(data){
									if(data&&data.state==true){
										//收起面板 刷新数据
										refreshCurrentPage();
										$(index).find(".layui-layer-btn0").trigger("click");
									}else{
										console.log(data);
										var msg = data.retMessage;
										if(msg.indexOf("optimistic")>=0){
											layer.alert("当前图书馆模板数据已经被其他人修改，请刷新之后操作");
										}
									}
								}).then(function(){
									//console.log("LibInfo",LibInfo,"libtem_idx",libtem_idx);
									//修改年限
									var page = $("#page").find("li.active").html();
									var size=$("select#showSize").val();
									var data= GetData();
									data.page = page;
									data.pageSize = size;
									$.ajax({
										url:basePath+"/library/selectLibinfoByParam",
										type:"POST",
										data:{"json":JSON.stringify(data)},
										success:function(data){
											if(data.state){
												if(data.result.rows){
													$.each(data.result.rows,function(i,item){
													if(item.library_idx == libtem_idx){
														$("#version_stamp").val(item.version_stamp);
														$("#end_date").val(formatDate(item.service_expire_date,"yyyy-MM-dd"));
													}
												});
											  }
											}
										}
									});
								});
							});
							/**
								点击取消保存图书馆模板按钮
							**/
							$content.find("input[name=cancel-save-temp]").on("click",function(){
								$(index).find(".layui-layer-btn0").trigger("click");
							});
						}
					});
			}
		});
	});
    <%-- 查询所有地点 --%>
    function queryAllRegion(){
        $.ajax({
            url:"${pageContext.request.contextPath}/device/queryAllRegion",
            type:"POST",
            data:{}
        }).done(function(data){
            if(data && data.state){
                regionNode = data.result;
                if(regionNode['root']){
                    var item = regionNode['root'];
                    var html = "";
                    for(var i in item){
                        html += "<option value=\""+item[i].code+"\">"+item[i].name+"</option>";
                    }
                    $("#nation").append(html)
                }
            }
        });
    }

    $("#nation").on("change", function(){
        var nCode = $("#nation").val();
        var html = "<option value=\"-1\">省</option>";
        if(regionNode[nCode]){
            var item = regionNode[nCode];
            for(var i in item){
                html += "<option value=\""+item[i].code+"\">"+item[i].name+"</option>";
            }
        }
        $("#province").html(html).trigger("change");
    });

    $("#province").on("change", function(){
        var nCode = $("#province").val();
        var html = "<option value=\"-1\">市</option>";
        if(regionNode[nCode]){
            var item = regionNode[nCode];
            for(var i in item){
                html += "<option value=\""+item[i].code+"\">"+item[i].name+"</option>";
            }
        }
        $("#city").html(html).trigger("change");
    });


    $("#city").on("change", function(){
        var nCode = $("#city").val();
        var html = "<option value=\"-1\">区</option>";
        if(regionNode[nCode]){
            var item = regionNode[nCode];
            for(var i in item){
                html += "<option value=\""+item[i].code+"\">"+item[i].name+"</option>";
            }
        }
        $("#area").html(html);
    });
	/**
		刷新当前页
	**/
	function refreshCurrentPage(){
		var page = $("#page").find("li.active").html();
		var size=$("select#showSize").val();
		var data= GetData();
		data.page = page;
		data.pageSize = size;
		$.select(data);
	}
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var size=$(this).val();
		var page = $("#page").find("li.active").html();
		var data= GetData();
		data.page = page;
		data.pageSize = size;
		$.select(data);
	});
	/**
		失去焦点 关闭筛选面板
	**/
	$("div.library-guanli").on("click",function(event){
		 // IE支持 event.srcElement ， FF支持 event.target    
    	var evt = event.srcElement ? event.srcElement : event.target;
    	if($(evt).parents(".template-filter").length>0) 
    		return; // 如果是元素本身，则返回    
		if($(".drop-box").css("display")=="block"){
			$(".drop-box").hide();
		}
	});
	/**
		清除模板筛选条件
	**/
	$("input[name=clearTempKeyword]").on("click",function(){
		$(".drop-box .right").find("li").removeClass("active");
	});
	
	
	$("#devicePosition").on("click",function(){
		libPosLayer= layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :true,
			area:["200px"],
			offset :["195px"],
			content: $('.mapDiv')
		});
	});
});
</script>
<jsp:include page="mapDiv.jsp"/>
</body>
</html>


