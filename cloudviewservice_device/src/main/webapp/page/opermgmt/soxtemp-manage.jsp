<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
.form-dialog-fix .form-wrap{
	width:380px;
}
.form-dialog-fix .form-dialog .form-wrap .left {
	width:125px;
}
.config-ACS .col3 {
	width:150px;
}
.config-ACS .col4 {
	width:200px;
}
.config-ACS .col5 {
	width:150px;
}


</style>
<div class="config-ACS">
<div class="g-loading"></div>
	<div class="">
		<div class="page-title-bar">
			<span class="title">密码模板配置<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/opermgmt/soxtemp.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
<!-- 				<div class="g-select"> -->
<!-- 					<select id="queryType"> -->
<!-- 						<option value="-1" selected>选择类型</option> -->
<!-- 						<option value="sox_tpl_name">模板名称</option> -->
<!-- 					</select> -->
<!-- 					<span class="arr-icon"></span> -->
<!-- 				</div> -->
				<input type="text" name="" id="keyword" class="input g-input" placeholder="输入模板名称" />
				<div class="btn search">查询</div>
				<!-- 修改权限 by huanghuang 20170215 -->
				<shiro:hasPermission name="0101010301">
					<div class="btn increase">新增</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0101010302">
					<div class="btn delete">删除</div>
				</shiro:hasPermission>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="col2">模板名称</th>
						<th class="col3">密码最小长度</th>
						<th class="col4">字符集</th>
						<th class="col5">失败次数</th>
						<th class="col6">锁定时长(小时)</th>
						<th class="col6">密码有效时长(天)</th>
						<th class="col6">可用时间段</th>
						<th class="col7">操作</th>
					</tr>
				</thead>
				<tbody>
					<%--
					<tr>
						<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
						<td>云系统管理员密码</td>
						<td>8</td>
						<td>大写、小写、数字、特殊字符</td>
						<td>3</td>
						<td>24小时</td>
						<td>180天</td>
						<td>6：00-18：00</td>
						<td>
							<span class="btn-a edit">编辑</span>
							<span class="btn-a delete">删除</span>
						</td>
					</tr>
					 --%>
				</tbody>
			</table>
		</div>
		
		<%@include file="../include/page_bar.jsf" %>
		
		<%-- 
		<div class="paging-bar">
			<div class="left">
				<span class="t fl">选择</span>
				<div class="btn g-chooseAll">全选</div>
				<div class="btn g-noChooseAll">反选</div>
				<span class="t2 fl">显示</span>
				<div class="g-select refresh">
					<select>
						<option value="" selected>30</option>
						<option value="">60</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<span class="t2 fl">已选中7个</span>
			</div>
			<div class="right">
				<div class="prev-page">上一页</div>
				<ul>
					<li class="active">1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li>...</li>
					<li>50</li>
					<li>120</li>
				</ul>
				<div class="next-page">下一页</div>
			</div>
			<span class="total-page fr">共500</span>
		</div>
		--%>
	</div>
</div>
<div class="form-dialog-fix">
	<div class="shade"></div>
	<div class="form-dialog">
		<div class="title">
			<span>新增配置</span>
			<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
			<input type="submit" placeholder="" class="g-submit2 g-btn-green" value="保存">
		</div>
		<div class="form-wrap">
			<ul>
<!-- 				<li> -->
<!-- 					<div class="left">模板ID</div> -->
<!-- 					<div class="right"> -->
<!-- 						<input type="text" name="" id="" class="g-input" placeholder="请输入" /> -->
<!-- 						<span class="error-msg">错误提示</span> -->
<!-- 					</div> -->
<!-- 				</li> -->
				<li>
					<div class="left">模板名称</div>
					<div class="right">
						<input type="hidden" name="" id="sox_id" />
						<input type="text" name="" id="sox_name" class="g-input" placeholder="请输入" />
						<input type="hidden" name="" id="version_stamp" class="g-input" placeholder="" />
						<span class="error-msg">请填写模板名称</span>
					</div>
				</li>
				<li>
					<div class="left">密码最小长度</div>
					<div class="right">
						<div class="g-select">
							<select id="pwd_length">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6" selected>6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
								<option value="13">13</option>
								<option value="14">14</option>
								<option value="15">15</option>
								<option value="16">16</option>
								<option value="17">17</option>
								<option value="18">18</option>
								<option value="19">19</option>
								<option value="20">20</option>
							</select>
							<span class="arr-icon"></span>
						</div>
					</div>
				</li>
				<li >
					<div class="left">密码字符集</div>
					<div class="right">
						<ul>
							<li><div class="g-checkbox"><input type="checkbox" name="pwd_char" value="1" id="pwd1"></div><label for="pwd1">大写字母</label></li>
							<li><div class="g-checkbox"><input type="checkbox" name="pwd_char" value="2" id="pwd2"></div><label for="pwd2">小写字母</label></li>
							<li><div class="g-checkbox"><input type="checkbox" name="pwd_char" value="3" id="pwd3"></div><label for="pwd3">数字</label></li>
							<li><div class="g-checkbox"><input type="checkbox" name="pwd_char" value="4" id="pwd4"></div><label for="pwd4">特殊字符</label></li>
						</ul>
						<span class="error-msg">至少选择一种字符集</span>
					</div>
				</li>
				<li>
					<div class="left">失败次数最多</div>
					<div class="right">
						<input type="text" name="" id="failed_times" class="g-input w80 dib mr10" placeholder="请输入" />次
						<span class="error-msg">请填写失败次数</span>
					</div>
				</li>
				<li>
					<div class="left">锁定时长</div>
					<div class="right">
						<input type="text" name="" id="lock_time" class="g-input w80 dib mr10" placeholder="请输入" />小时
						<span class="error-msg">请填写自动锁定时长</span>
					</div>
				</li>
				<li>
					<div class="left">&nbsp;</div>
					<div class="right">
						<ul>
							<li><div class="g-checkbox"><input type="checkbox" name="" value="" id="first_pwd"></div><label for="first_pwd">首次登录需修改密码</label></li>
						</ul>
					</div>
				</li>
				<li>
					<div class="left">密码有效时间</div>
					<div class="right">
						<input type="text" name="" id="password_validdays" class="g-input w80 dib mr10" placeholder="请输入" />天
						<span class="error-msg">请填写密码有效时间</span>
					</div>
				</li>
				<li>
					<div class="left">历史密码保留个数</div>
					<div class="right">
						<div class="g-select">
							<select id="count_history">
								<option value="1" selected>1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
							</select>
							<span class="arr-icon">请选择密码保留个数</span>
						</div>
					</div>
				</li>
				<li>
					<div class="left">提前提醒时间(天)</div>
					<div class="right">
						<div class="g-select">
							<select id="password_remind">
								<option value="1" selected>1</option>
								<option value="3">3</option>
								<option value="5">5</option>
							</select>
							<span class="arr-icon"></span>
						</div>
					</div>
				</li>
				<li>
					<div class="left">可用时间段</div>
					<div class="right time-wrap">
						<div class="g-inputtime"><input type="text" readonly="readonly" id="startTime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
						<span class="dib">-</span>
						<div class="g-inputtime"><input type="text" readonly="readonly" id="endTime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
						<span class="error-msg">请选择有效时间段</span>
					</div>
				</li>
				
			</ul>
		</div>
	</div>
</div>

<div class="g-delete-dialog multi">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red"></span> 个项目<br>
		是否要删除选择的模板数据？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>

<div class="g-delete-dialog single">
	<span class="line"></span>
	<div class="word">
		是否要删除 <span class="font-red"></span>？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>

<div class="g-delete-dialog alert">
	<span class="line"></span>
	<div class="word">
	</div>
	<div class="form-btn cancel g-btn-green">确定</div>
</div>

<script type="text/javascript">
$(function(){
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();

		$(".main").css({
			"min-height":winH - headerH - pagingBarH -pageTitleBar
		});
	})();
	


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
		timeFormat: 'HH:mm',
		currentText:"现在",
		closeText:"关闭",
		timeOnlyTitle:"",
		amNames:["AM"],
		pmNames:["PM"],
		timetext:"时间",
		hourText:"时",
		minuteText:"分",
		secondText:"秒",
		minute: null,
        second: null,
	});


});


<%-- 删除事件 --%>
var delLayer = null;

<%-- 单条删除事件 --%>
var deleteIdx = "";
var deleteName = "";
var dels={};
$("tbody").on("click",".delete",function(){
	deleteIdx = $(this).attr("data-id");
	deleteName = $(this).attr("data-name");
	dels.deleteIdx=$(this).attr("data-id");
	dels.version_stamp=$(this).parents("tr").find(".version_stamp").html();
	$(".g-delete-dialog.single .font-red").html(deleteName);
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
		content: $('.g-delete-dialog.single')
	});
});

<%-- 点击 批量删除 按钮 --%>
var deleteList = [];
$("div.page-title-bar").on("click",".delete",function(){
	deleteList = [];
	dels.deleteIdxArr=new Array();
	var len = 0;
	$("tbody input[type='checkbox']:checked").each(function(){
		var obj = {};
		var idx = $(this).prop("id");
		var soxName = $(this).attr("data-name");
		obj.idx=idx;
		obj.soxName=soxName;
		deleteList.push(obj);
		var entity={
				"sox_tpl_id":idx,
				"version_stamp":$(this).parents("tr").find(".version_stamp").html()
			};
		dels.deleteIdxArr.push(entity);
		
	});
	len = deleteList.length;
	if(len<=0){
		layer.alert("请先选择要删除的模板！");
		return;
	}
	$(".g-delete-dialog.multi .font-red").html(len);
	
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
		content: $('.g-delete-dialog.multi')
	});
});


<%-- 取消按钮 --%>
$(".form-btn.cancel").on("click",function(){
	closeLayer(delLayer);
});

<%-- 取消按钮 --%>
$(".form-btn.cancel.g-btn-green").on("click",function(){
	closeLayer(multiLayer);
});



$(".form-btn.cancel").on("click",function(){
	if (delLayer) {
		layer.close(delLayer);
	}
});

function closeLayer(index){
	if (index) {
		layer.close(index);
	}
}


<%-- 执行单条删除删除操作 --%>
$(".g-delete-dialog.single .form-btn.remove").on("click",function(){
	if(deleteIdx==""){
		closeLayer(delLayer);
		return;
	}
	$delete = $(this);
	if($delete.hasClass("disabled")){
		return;
	}else{
		$delete.addClass("disabled");
	}
	var data=new Array();
	var entity={
				"sox_tpl_id":dels.deleteIdx,
				"version_stamp":dels.version_stamp
			};
	data.push(entity);
	$.ajax({
		url:"<%=basePath%>soxtemp/delSoxTemp",
		type:"POST",
		data:{"json":JSON.stringify(data)}
	}).done(function(data){
		if(data!=null && data!=""){
			if(data.state){
				showMsg({
					timeout : 1000,
					showText : '删除成功',
					status : true
				});
				$delete.removeClass("disabled");
				refreshCurrent();
				closeLayer(delLayer);
			}else{
				var msg = "";
				if(data.message=="1"){
					msg = deleteName+"正在被使用！";
					layer.alert("删除失败,"+msg);
				}else{
					msg = data.message;
					if(msg.indexOf("optimistic")>=0){
						layer.alert("当前选择的数据不是最新数据，请刷新之后再做删除");
					}
					else{
						layer.alert("删除失败,"+msg);
					}
				}
				$delete.removeClass("disabled");
				//refreshCurrent();
				closeLayer(delLayer);
				
			}
		}
	});
	
});

var multiLayer = null;
<%-- 执行批量删除操作 --%>
$(".g-delete-dialog.multi .form-btn.remove").on("click",function(){
	if(deleteList.length<=0){
		closeLayer(multiLayer);
		return;
	}
	$delete = $(this);
	if($delete.hasClass("disabled")){
		return;
	}else{
		$delete.addClass("disabled");
	}
	$.ajax({
		url:"<%=basePath%>soxtemp/delSoxTemp",
		type:"POST",
		data:{"json":JSON.stringify(dels.deleteIdxArr)}
	}).done(function(data){
		if(data!=null && data!=""){
			if(data.state){
				showMsg({
					timeout : 1000,
					showText : '删除成功',
					status : true
				});
				$delete.removeClass("disabled");
				refreshCurrent();
				closeLayer(delLayer);
			}else{
				var msg = "";
				if(data.message=="1"){
					msg = "所选择的数据正在被使用！";
					layer.alert("删除失败,"+msg);
				}else{
					msg = data.message;
					if(msg.indexOf("optimistic")>=0){
						layer.alert("当前选择的数据不是最新数据，请刷新之后再做删除");
					}
					else{
						layer.alert("删除失败,"+msg);
					}
				}
				$delete.removeClass("disabled");
				//refreshCurrent();
				closeLayer(delLayer);
			}
		}
	});
});



$(".increase").on("click",function(){
	$("div.title span").html("新增配置");
	
	$(".form-dialog-fix").fadeIn(100);
	$(".form-dialog-fix").find(".form-dialog").animate({
		"right":0
	});
	
	clearData();
});

$("tbody").on("click",".edit",function(){
	$("div.title span").html("修改配置")
	
	$(".form-dialog-fix").fadeIn(100);
	$(".form-dialog-fix").find(".form-dialog").animate({
		"right":0
	});
	
	clearData();
	var id = $(this).attr("data-id");
	if(!_.isEmpty(allSoxTempList[id])){
		loadSoxTempData(allSoxTempList[id]);
	}
	
});

<%-- 保存按钮 --%>
$(".g-submit2").on("click",function(){
	
	if(!checkData()){
		return ;
	}
	var sox_id = $("#sox_id").val();
	
	soxTempSubmit = {};
	
	soxTempSubmit = saveSoxTemp();
	
	if(!_.isEmpty(sox_id)){
		<%-- 如果不为空则为编辑 --%>
		soxTempSubmit.sox_tpl_id = sox_id;
		var flag = compareSoxTemp(allSoxTempList[sox_id],soxTempSubmit);
		if(flag){
			//直接提示保存成功
			showMsg({
				timeout : 1000,
				showText : '保存成功',
				status : true,
				callback:function(){
					var thisDom = $(".g-submit2");
					/*应该是成功的时候收回吧，酌情处理*/
					var thisRight =  thisDom.parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
					thisDom.parents(".form-dialog-fix").find(".form-dialog").animate({
						"right":thisRight
					},function(){
						thisDom.parents(".form-dialog-fix").fadeOut(100);
					});
				}
			});
		}else{
			//更新到数据库
			uploadSoxTempData(soxTempSubmit,"update");
		}
	}else{
		<%-- 新增 --%>
		uploadSoxTempData(soxTempSubmit,"add");
	}
	
	
	
	<%--
	
	var thisDom = $(this);
	if(true){
		showMsg({
			timeout : 1000,
			showText : '添加配置成功',
			status : true,
			callback:function(){
				/*应该是成功的时候收回吧，酌情处理*/
				var thisRight =  thisDom.parents(".form-dialog-fix").find(".form-dialog").attr("date-right");

				thisDom.parents(".form-dialog-fix").find(".form-dialog").animate({
					"right":thisRight
				},function(){
					thisDom.parents(".form-dialog-fix").fadeOut(100);
				});
			}

		});
	}
	
	--%>
	/*
	提交成功  showMsg("添加配置成功",1000,true);
	提交失败  showMsg("添加配置失败",1000,false);
	 */
	
});

<%-- 上传数据进行保存 --%>
function uploadSoxTempData(obj,type){
	<%-- 更新模板信息 --%>
	if(type=="update"){
		$.ajax({
			url:"<%=basePath%>soxtemp/updateSoxTemp",
			type:"POST",
			data:{"json":JSON.stringify(obj)}
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state && data.message=="success"){
					showMsg({
						timeout : 1000,
						showText : '修改成功',
						status : true,
						callback:function(){
							refreshCurrent();
							
							var thisDom = $(".g-submit2");
							/*应该是成功的时候收回吧，酌情处理*/
							var thisRight =  thisDom.parents(".form-dialog-fix").find(".form-dialog").attr("date-right");

							thisDom.parents(".form-dialog-fix").find(".form-dialog").animate({
								"right":thisRight
							},function(){
								thisDom.parents(".form-dialog-fix").fadeOut(100);
							});
						}

					});
				}else{
					var message = data.message;
					if(message.indexOf("optimistic")>=0){
						layer.alert("当前选择数据不是最新数据,请刷新之后再做编辑");
					}else{
						layer.alert("修改失败！"+data.message);
					}
				}
			}
			
		});
	}
	<%-- 新增模板信息 --%>
	if(type=="add"){
		$.ajax({
			url:"<%=basePath%>soxtemp/addSoxTemp",
			type:"POST",
			data:{"json":JSON.stringify(obj)}
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state && data.message=="success"){
					showMsg({
						timeout : 1000,
						showText : '新增成功！',
						status : true,
						callback:function(){
							refreshCurrent();								
							var thisDom = $(".g-submit2");
							/*应该是成功的时候收回吧，酌情处理*/
							var thisRight =  thisDom.parents(".form-dialog-fix").find(".form-dialog").attr("date-right");

							thisDom.parents(".form-dialog-fix").find(".form-dialog").animate({
								"right":thisRight
							},function(){
								thisDom.parents(".form-dialog-fix").fadeOut(100);
							});
						}

					});
				}else{
// 					showMsg({
// 						timeout : 3000,
// 						showText : "新增失败！"+data.message,
// 						status : false
// 					});
					layer.alert("新增失败！"+data.message);
				}
			}
		});
	}
}

function showMsg(option){
	var defaults = {
		timeout : option.timeout || 1000,
		showText : option.showText || '添加配置成功',
		backgroundColor : option.status === true ? "#2ab65b" :"#ff3d3d",
		callback:function(){

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



//组装 翻页和查询 参数
function makeQueryParam(page,pageSize){
	var keyword=$("#keyword").val();
// 	var type=$("#queryType").val();
	pageSize = $("#showSize").val();
	var Page ={
		"page":page,
		"pageSize":pageSize,
		"queryType":"sox_tpl_name",
		"keyword":$.trim(keyword)
	};
	return Page;
};

//下一页操作
$("div.paging-bar").on("click",".next-page",function(){
	var currentpage = $("#page").find("li.active").html();//当前页
	var cpage = Number(currentpage) + 1;//下一页
	var Page = makeQueryParam(cpage, pageSize);
	getSoxTempListByParam(Page);
});

//上一页操作
$("div.paging-bar").on("click",".prev-page",function(){
	var currentpage = $("#page").find("li.active").html();
	var page=Number(currentpage)-1;
	var Page=makeQueryParam(page, pageSize);
	//带参数
	getSoxTempListByParam(Page);
});

//点击某一页
$("div.paging-bar").on("click","li",function(){
	if($(this).hasClass("active")) return;
	var page = $(this).html();
	if(page=="...") return;	
	var param=makeQueryParam(page, pageSize);
	getSoxTempListByParam(param);
});

<%-- 选择显示页码 --%>
$("div.paging-bar").on("change",".refresh select",function(){
	var pageSize = $(this).val();
	param=makeQueryParam(1, pageSize);
	getSoxTempListByParam(param);
});

//刷新当前页
function refreshCurrent(){
	var currentpage = $("#page").find("li.active").html();
	param=makeQueryParam(currentpage, pageSize);
	//带参数
	getSoxTempListByParam(param);
}


<%-- 查询按钮 --%>
$("div.btn.search").off("click").on("click",function(){
	param = makeQueryParam(1,pageSize)
	getSoxTempListByParam(param);
});


</script>

<%-- 自定义脚本  --%>
<script type="text/javascript">

function startLoading(){
	$(".g-loading").stop();
	$(".g-loading").fadeIn();
}

function endLoading(val){
	if(val=="1"){
		$(".g-loading").hide();
	}else{
		$(".g-loading").fadeOut();
	}
}

var pageSize=10;
var Page={"page":1,"pageSize":pageSize,};
var param = {}; <%-- 保存查询条件参数 --%>

var soxTempSubmit = {};<%-- 保存要提交的模板数据 --%>
var allSoxTempList = {};<%-- 保存所有的鉴权模板信息 --%>


$(function(){
	param = makeQueryParam(1, pageSize);
	getSoxTempListByParam(param);
});


function clearData(){
	<%-- 清除错误消息的提示 --%>
	$("li.error").removeClass("error");
	
	$("#sox_id").val("");
	
	$(".form-dialog").find(":text").val("");
	$(".form-dialog select").prop("selectedIndex",0);
	$(".form-dialog").find(":checkbox").prop("checked",false).parents(".g-checkbox").removeClass("on");
}

function checkData(){
	var flag = true;
	
	var sox_name = $("#sox_name").val();
	if(_.isEmpty(sox_name)){
		$("#sox_name").closest("li").addClass("error");
		flag = false;
	}else{
		$("#sox_name").closest("li").removeClass("error");
	}
	
	var pwd_length = $("#pwd_length").val();
	if(_.isEmpty(pwd_length)){
		$("#pwd_length").closest("li").addClass("error");
		flag = false;
	}else{
		$("#pwd_length").closest("li").removeClass("error");
	}
	
	var regNum = /^[0-9]*$/;
	var lock_time = $("#lock_time").val();
	if(_.isEmpty(lock_time)){
		$("#lock_time").closest("li").addClass("error");
		$("#lock_time").closest("li").find("span").html("请填写自动锁定时长");
		flag = false;
	}else{
		if(!regNum.test(lock_time)){
			$("#lock_time").closest("li").addClass("error");
			$("#lock_time").closest("li").find("span").html("请填写整数");
			flag = false;
		}else if(parseInt(lock_time) > 32000){
			$("#lock_time").closest("li").addClass("error");
			$("#lock_time").closest("li").find("span").html("不能大于32000");
			flag = false;
		}else{
			$("#lock_time").closest("li").removeClass("error");
		}
	}
	
	var failed_times = $("#failed_times").val();
	if(_.isEmpty(failed_times)){
		$("#failed_times").closest("li").addClass("error");
		$("#failed_times").closest("li").find("span").html("请填写失败次数");
		flag = false;
	}else{
		if(!regNum.test(failed_times)){
			$("#failed_times").closest("li").addClass("error");
			$("#failed_times").closest("li").find("span").html("请填写整数");
			flag = false;
		}else if(parseInt(failed_times) > 32000){
			$("#failed_times").closest("li").addClass("error");
			$("#failed_times").closest("li").find("span").html("不能大于32000");
			flag = false;
		}else{
			$("#failed_times").closest("li").removeClass("error");
		}
	}
	
	var password_validdays = $("#password_validdays").val();
	if(_.isEmpty(password_validdays)){
		$("#password_validdays").closest("li").addClass("error");
		$("#password_validdays").closest("li").find("span").html("请填写密码有效时间");
		flag = false;
	}else{
		if(!regNum.test(password_validdays)){
			$("#password_validdays").closest("li").addClass("error");
			$("#password_validdays").closest("li").find("span").html("请填写整数");
			flag = false;
		}else if(parseInt(password_validdays) > 32000){
			$("#password_validdays").closest("li").addClass("error");
			$("#password_validdays").closest("li").find("span").html("不能大于32000");
			flag = false;
		}else{
			$("#password_validdays").closest("li").removeClass("error");
		}
	}
	
	var count_history = $("#count_history").val();
	if(_.isEmpty(count_history)){
		$("#count_history").closest("li").addClass("error");
		flag = false;
	}else{
		$("#count_history").closest("li").removeClass("error");
	}
	
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(_.isEmpty(startTime) || _.isEmpty(endTime)){
		$("#startTime").closest("li").addClass("error");
		flag = false;
	}else{
		$("#startTime").closest("li").removeClass("error");
	}
	
	var charset = "";
	if($("#pwd1").prop("checked")){
		charset += "1,";
	}
	if($("#pwd2").prop("checked")){
		charset += "2,";
	}
	if($("#pwd3").prop("checked")){
		charset += "3,";
	}
	if($("#pwd4").prop("checked")){
		charset += "4,";
	}
	
	if(charset.length<=0){
		$("#pwd1").closest("ul").closest("li").addClass("error");
		flag = false;
	}else{
		$("#pwd1").closest("ul").closest("li").removeClass("error");
	}
	
	return flag;
	
}

function saveSoxTemp(){
	var obj = {};
	obj.sox_tpl_name = $("#sox_name").val();
	obj.password_length = $("#pwd_length").val();
	obj.password_validdays = $("#password_validdays").val();
	obj.count_history_password = $("#count_history").val();
	obj.vaild_time = $("#startTime").val()+"-"+$("#endTime").val()
	obj.login_fail_times = $("#failed_times").val();
	obj.lock_time = $("#lock_time").val();
	obj.password_remind = $("#password_remind").val();
	obj.version_stamp=$("#version_stamp").val();

	if($("#first_pwd").prop("checked")){
		obj.first_login_chgpwd = "1";
	}else{
		obj.first_login_chgpwd = "0";
	}
	
	var charset = "";
	if($("#pwd1").prop("checked")){
		charset += "1,";
	}
	if($("#pwd2").prop("checked")){
		charset += "2,";
	}
	if($("#pwd3").prop("checked")){
		charset += "3,";
	}
	if($("#pwd4").prop("checked")){
		charset += "4,";
	}
	if(charset.length>0){
		charset = charset.substring(0,charset.length-1);
	}
	obj.password_charset = charset;
	return obj
}

function compareSoxTemp(tempObj,tempSubmit){
	if(!_.isEmpty(tempObj) && !_.isEmpty(tempSubmit)){
		if(tempObj.sox_tpl_name != tempSubmit.sox_tpl_name)	{
			return false;
		}
		
		if(tempObj.password_length != tempSubmit.password_length)	{
			return false;
		}
		
		if(tempObj.password_validdays != tempSubmit.password_validdays)	{
			return false;
		}
		
		if(tempObj.count_history_password != tempSubmit.count_history_password)	{
			return false;
		}
		
		if(tempObj.password_remind != tempSubmit.password_remind)	{
			return false;
		}
		
		if(tempObj.vaild_time != tempSubmit.vaild_time)	{
			return false;
		}
		
		if(tempObj.login_fail_times != tempSubmit.login_fail_times)	{
			return false;
		}
		
		if(tempObj.lock_time != tempSubmit.lock_time)	{
			return false;
		}
		
		if(tempObj.first_login_chgpwd != tempSubmit.first_login_chgpwd)	{
			return false;
		}
		
		if(tempObj.password_charset != tempSubmit.password_charset)	{
			return false;
		}
		
		return true;
	}
}


function getSoxTempListByParam(param){
	startLoading();
	$(".g-table tbody").html("");
	$(".col1 :checkbox").prop("checked",false).trigger("change");
	$.ajax({
		url:"<%=basePath%>soxtemp/getSoxTempListByParam",
		type:"POST",
		data:{json:JSON.stringify(param)},
		timeout:8000
	}).done(function(data){
		if(data!=null && data.result!=null && data.result.rows!=null){
			if(data.state){
				var list = data.result.rows;
				var html = "";
				<%-- 分页 --%>
				$.pagination(data.result);
				var perNum=0;
				for(var i=0;i<list.length;i++){
					var item = list[i];
					allSoxTempList[item.sox_tpl_id] = item;
					<%-- 密码字符集1大写，2小写，3数字，4特殊字符 --%>
					var charset = item.password_charset;
					var chartext = "";
					if(charset.indexOf("1")>-1){chartext+="大写、";	}
					if(charset.indexOf("2")>-1){chartext+="小写、";	}
					if(charset.indexOf("3")>-1){chartext+="数字、";	}
					if(charset.indexOf("4")>-1){chartext+="特殊字符、";	}
					if(chartext.length>0){
						chartext = chartext.substring(0,chartext.length-1);
					}
					html+="<tr>";
					html+="<td class=\"col1\"><div class=\"g-checkbox\"><input type=\"checkbox\" name=\"\" id=\""+item.sox_tpl_id+"\" data-name=\""+item.sox_tpl_name+"\"/></div></td>";
					html+="<td>"+item.sox_tpl_name+"</td>";
					html+="<td>"+item.password_length+"</td>";
					html+="<td>"+chartext+"</td>";
					html+="<td>"+item.login_fail_times+"</td>";
					html+="<td>"+item.lock_time+"</td>";
					html+="<td>"+item.password_validdays+"</td>";
					html+="<td>"+item.vaild_time+"</td>";
					html+="<td class='col7'>";
					<shiro:hasPermission name="0101010303">
						html+="	<span class=\"btn-a edit\" data-id=\""+item.sox_tpl_id+"\">编辑</span>";
						perNum++;
					</shiro:hasPermission>
					<shiro:hasPermission name="0101010302">
						html+="	<span class=\"btn-a delete\" data-id=\""+item.sox_tpl_id+"\" data-name=\""+item.sox_tpl_name+"\">删除</span>";
						perNum++;
					</shiro:hasPermission>
					html+="</td>";
					html+="<td class='version_stamp hide'>"+item.version_stamp+"</td>";
					html+="</tr>";
				}
				$(".g-table tbody").html(html);
				if(perNum==0){
					$(".col7").attr("style","display:none;");
				}else{
					$(".col7").attr("style","");
				}
			}
		}
		
	}).always(function(data,status){
		if(status=="timeout"){
			layer.alert("请求超时，请重试！");
		}
		endLoading("1");
	});
	
}

function loadSoxTempData(obj){
	$("#sox_id").val(obj.sox_tpl_id);
	$("#sox_name").val(obj.sox_tpl_name);
	$("#pwd_length").val(obj.password_length);
	$("#version_stamp").val(obj.version_stamp);
	if(obj.password_charset!=null){
		var pwd = obj.password_charset;
		if(pwd.indexOf("1")>-1){
			$("#pwd1").prop("checked",true).parents(".g-checkbox").addClass("on");
		}
		if(pwd.indexOf("2")>-1){
			$("#pwd2").prop("checked",true).parents(".g-checkbox").addClass("on");
		}
		if(pwd.indexOf("3")>-1){
			$("#pwd3").prop("checked",true).parents(".g-checkbox").addClass("on");
		}
		if(pwd.indexOf("4")>-1){
			$("#pwd4").prop("checked",true).parents(".g-checkbox").addClass("on");
		}
	}
	$("#failed_times").val(obj.login_fail_times);
	$("#password_remind").val(obj.password_remind);
	$("#lock_time").val(obj.lock_time);
	if(obj.first_login_chgpwd=="1"){
		$("#first_pwd").prop("checked",true).parents(".g-checkbox").addClass("on");
	}
	$("#password_validdays").val(obj.password_validdays);
	$("#count_history").val(obj.count_history_password);
	if(obj.vaild_time!=null){
		var startTime = obj.vaild_time.substring(0,obj.vaild_time.indexOf("-"))
		var endTime = obj.vaild_time.substring(obj.vaild_time.indexOf("-")+1,obj.vaild_time.length);
		$("#startTime").val(startTime);
		$("#endTime").val(endTime);
	}
	
}

<%-- 回车事件 --%>
$('#keyword').keydown(function(e){
	if(e.keyCode==13){
		param = makeQueryParam(1,pageSize)
		getSoxTempListByParam(param);
	}
});

</script>

