<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>

.form-wrap .reset .btn-a {
	display: inline-block;
    padding: 0 10px;
    margin: 2px 20px;
    height: 24px;
    line-height: 24px;
    color: #FFF;
    border-radius: 5px;
    cursor: pointer;
    background-color: #00a2e9;
}

.form-wrap .reset input {
	display: inline-block;
    padding: 0 10px;
    margin: 0px 20px;
}
.form-dialog-2 .title{
	position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 998;
}
.form-dialog-2 .form-wrap {
	margin-top: 80px;
}
.layui-layer-setwin .layui-layer-close2 {
	z-index: 999;
}

div.group li.error div.g-select {
    background-color: #ffeeee;
    border-color: #ff2323;
}

div.group span.error-msg{
	display: none;
}

div.item li.error div.g-select{
	background-color: #ffeeee;
    border-color: #ff2323;
}

div.group div.item li.error span.error-msg{
	margin-left: 10px;
	display:inline;
	color: #ff2323;
}

input.disa{
	color:gray;
	background-color: #E0E0E0;
}
</style>

<div class="user-manage">
<div class="g-loading"></div>
	<div class="">
		<div class="page-title-bar">
			<span class="title">用户管理<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/opermgmt/opermgmt.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-select">
					<select id="paramType">
						<option value="-1" selected>选择类型</option>
						<option value="operator_id" >用户名</option>
						<option value="operator_name" >姓名</option>
						<%-- <option value="lib_name" >所属馆</option>--%>
						<%--<option value="operator_type" >用户类型</option>--%>
						<%--<option value="isActive" >是否激活</option>--%>
						<%--<option value="isLock" >是否锁定</option>--%>
					</select>
					<span class="arr-icon"></span>
				</div>
				<input type="text" name="" id="keyword" class="input g-input" placeholder="输入关键词" />
				<div class="btn search">查询</div>
				<!-- 修改权限 by huanghuang 20170215 -->
				<shiro:hasPermission name="0101010101">
					<div class="btn increase">新增</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0101010102">
					<div class="btn delete">删除</div>
				</shiro:hasPermission>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="col2">用户名</th>
						<th class="col3">姓名</th>
						<th class="col4">用户类型</th>
						<th class="col5">所属馆</th>
						<th class="col6">状态</th>
						<th class="col6">是否锁定</th>
						<th class="col7">操作</th>
					</tr>
				</thead>
				<tbody>
<!-- 					<tr> -->
<!-- 						<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td> -->
<!-- 						<td class="col2">0001</td> -->
<!-- 						<td class="col3">zhangsan</td> -->
<!-- 						<td class="col4">张三</td> -->
<!-- 						<td class="col5">系统管理部</td> -->
<!-- 						<td class="col6"><span class="status red">无效</span></td> -->
<!-- 						<td class="col6"><span class="status red">锁定</span></td> -->
<!-- 						<td class="col7"> -->
<!-- 							<span class="btn-a edit">编辑</span> -->
<!-- 							<span class="btn-a delete">删除</span> -->
<!-- 						</td> -->
<!-- 					</tr> -->
				</tbody>
			</table>
		</div>
		<%@include file="../include/page_bar.jsf"%>
	</div>
</div>
<div class="form-dialog-2">
	<div class="title">
		新增操作员
	</div>
	<div class="form-wrap">
		<div class="item">
			<ul>
				<li>
					<div class="left"><span class="g-mustIn">用户名</span></div>
					<div class="right">
					<input type="hidden" id="operator_idx" class="g-input">
					<input type="text" placeholder="请输入" id="operator_id" class="g-input">
					<input type="hidden" id="version_stamp" class="g-input">
					<span class="error-msg">请填写用户名</span></div>
					<!-- 修改重置密码权限 by huanghuang 20170216 -->
					<shiro:hasPermission name="0101010105">
						<div class="reset pwd"><span class="btn-a edit" id="resetPwd">重置密码</span></div>
					</shiro:hasPermission>
					
				</li>
				<li class="hide pwd">
					<div class="left"><span class="g-mustIn">密码</span></div>
					<div class="right"><input type="password" placeholder="请输入" id="operator_pwd1" class="g-input">
					<span class="error-msg">请填写密码</span></div>
				</li>
				<li class="hide pwd">
					<div class="left"><span class="g-mustIn">确认密码</span></div>
					<div class="right"><input type="password" placeholder="请输入" id="operator_pwd2" class="g-input">
					<span class="error-msg">请填写确认密码</span></div>
				</li>
				<li>
					<div class="left"><span class="g-mustIn">姓名</span></div>
					<div class="right"><input type="text" placeholder="请输入" id="operator_name" class="g-input">
					<span class="error-msg">请填写姓名</span></div>
				</li>
				<li>
					<div class="left"><span class="g-mustIn">所属图书馆ID</span></div>
					<div class="right"><input type="text" placeholder="请输入" data-id="" id="library_id" class="g-input" >
					<span class="error-msg">请填写正确的图书馆ID</span></div>
					<div class="reset"><input type="text" placeholder="所属馆名称" readonly="readonly" id="library_name" class="g-input" style="color: gray;background-color: #E0E0E0"></div>
				</li>
				<li>
					<div class="left"><span class="g-mustIn">操作员类型</span></div>
					<div class="right">
						<div class="g-select">
							<select id="operator_type">
								<option value="" selected>请选择</option>
							</select>
							<span class="arr-icon"></span>
						</div>
					</div>
				</li>
				<li>
					<div class="left"><span class="g-mustIn">密码模板</span></div>
					<div class="right">
						<div class="g-select">
							<select id="soxTemp">
<!-- 								<option value="" selected>方案1</option> -->
<!-- 								<option value="">方案2</option>          -->
							</select>
							<span class="arr-icon"></span>
						</div>
					</div>
				</li>
				<li>
					<div class="left"><span class="g-mustIn">是否激活</span></div>
					<div class="right">
						<div class="g-radio on"><input type="radio" id="invalid" name="isActive" value="1" checked="checked"></div><label for="invalid">是</label>
						<div class="g-radio"><input type="radio" id="valid" name="isActive" value="0"></div><label for="valid">否</label>
					</div>
				</li>
				<li>
					<div class="left"><span class="g-mustIn">是否锁定</span></div>
					<div class="right">
						<div class="g-radio"><input type="radio" id="lock" name="isLock"></div><label for="lock">是</label>
						<div class="g-radio on"><input type="radio" id="unlock" name="isLock" checked="checked"></div><label for="unlock">否</label>
					</div>
				</li>
				<li>
					<div class="left"><span class="">维护卡</span></div>
					<div class="right">
						<div class="g-select">
							<select id="maintenance_card">
								<option value="" selected>请选择</option>
							</select>
							<span class="arr-icon"></span>
						</div>
					</div>
				</li>
			</ul>
		</div>
		<div class="item group">
			<ul>
				<li >
					<div class="left"><span class="g-mustIn">选择操作员分组</span></div>
					<div class="right">
						<div class="g-select">
							<select id="libraryGroup">
								<option value="-1" selected>请选择</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						<span class="error-msg">请选择操作员分组,否则没有权限</span>
					</div>
				</li>
				<li>
					<div class="left">查看权限</div>
					<div class="right">
						<div class="select-area">
							<dl class="choose-jurisdiction" id="cmdtype">
							</dl>
						</div>
						<div class="select-area">
							<dl class="choose-jurisdiction" id="cmdlist">
							</dl>
						</div>
					</div>
				</li>
				
			</ul>
		</div>
		<div class="item" id="infolist">
			<ul>
			<%--
				<li>
					<div class="left">生日</div>
					<div class="right">
						<div class="g-inputdate"><input type="text" placeholder="请输入" class="g-input timepicker"><span class="icon"></span></div> 
					</div>
				</li>
				<li>
					<div class="left">手机</div>
					<div class="right"><input type="text" placeholder="请输入" class="g-input"></div>
				</li>
				<li>
					<div class="left">身份证号</div>
					<div class="right"><input type="text" placeholder="请输入" class="g-input"></div>
				</li>
				<li>
					<div class="left">办公室名</div>
					<div class="right"><input type="text" placeholder="请输入" class="g-input"></div>
				</li>
				<li>
					<div class="left">部门</div>
					<div class="right"><input type="text" placeholder="请输入" class="g-input"></div>
				</li>
				<li>
					<div class="left">港澳通行证</div>
					<div class="right"><input type="text" placeholder="请输入" class="g-input"></div>
				</li>
				<li>
					<div class="left">市民卡号</div>
					<div class="right"><input type="text" placeholder="请输入" class="g-input"></div>
				</li>
				<li>
					<div class="left">工作单位</div>
					<div class="right"><input type="text" placeholder="请输入" class="g-input"></div>
				</li>
				<li>
					<div class="left">公司地址</div>
					<div class="right"><input type="text" placeholder="请输入" class="g-input"></div>
				</li>
				<li>
					<div class="left">家庭地址</div>
					<div class="right"><input type="text" placeholder="请输入" class="g-input"></div>
				</li>
				<li>
					<div class="left">网址</div>
					<div class="right"><input type="text" placeholder="请输入" class="g-input"></div>
				</li>
				 --%>
				<li id="addinfo">
					<div class="left">&nbsp;</div>
					<div class="right">
						<div class="add-li">
							<div class="li-list">
<!-- 								<div class="add-phone li">电话</div> -->
<!-- 								<div class="add-email li">邮箱</div> -->
<!-- 								<div class="add-address li">地址</div> -->
							</div>
						</div>
						添加更多项
					</div>
				</li>
			</ul>
		</div>
		
		<input type="submit" value="保存" class="g-submit g-btn-blue">
	</div>
</div>

<div class="g-delete-dialog multi">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red"></span> 个用户<br>
		是否要删除选择的用户？
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


<div class="g-delete-dialog resetpwd">
	<span class="line"></span>
	<div class="word">
		将该用户的密码重置为：888888？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-blue">确认</div>
</div>
<!-- <link rel="stylesheet" href="css/jqueryui.css"> -->
<!-- <link rel="stylesheet" href="css/theme.css"> -->
<!-- <script src="js/jqueryui.js"></script> -->
<!-- <script src="js/datepicker.js"></script> -->
<!-- <script src="plugins/layer/layer.js"></script> -->
<script type="text/javascript">
$tbody=$("div.main").find(".g-table").find("tbody");
var o = <shiro:principal/>;
var operatorSubmit = {};<%-- 操作提交的数据 --%>

var pageSize=10;
var Page={"page":1,"pageSize":pageSize,};
var param = {}; <%-- 保存查询条件参数 --%>

var global_libid = null;
var global_operType = null;

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
	
});


$(function(){
	
	param = makeQueryParam(1, pageSize);
	selectOperatorPage(param);
	
	<%-- 获取sox模板 --%>
	queryAllSoxTemp();
	<%-- 获取操作员类型 --%>
	queryOperatorTypes();
	
	<%-- 查询所有的用户组信息 --%>
// 	queryAllServiceGroup();

	<%-- 查询所有的读者信息,以及可以新增的信息 --%>
	queryAllOperatorInfo();
	
	<%-- 查询当前用户的所在馆信息 --%>
	queryCurrentOperInfo();
	
	<%-- 查询维护卡信息 --%>
// 	queryMaintenanceCard();
	
// 	queryLibraryServiceGroup();
})


<%-- 查询当前用户的图书馆信息 --%>
function queryCurrentOperInfo(type){
	$.ajax({
		url:"${pageContext.request.contextPath}/operator/queryCurrentOperInfo",
		type:"GET",
		timeout:8000
	}).done(function(data){
		if(data!=null && data.state){
			global_libid = data.result.libId;
			global_operType = data.result.operType;
			if(type=="1"){
				$("#library_id").val(global_libid).trigger("change");
			}
		}
	});
}


function clearData(){
	<%-- 清除错误消息的提示 --%>
	$("li.error").removeClass("error");
	$("#cmdtype").html("");
	$("#cmdlist").html("");
	<%-- 清除输入框 --%>
	$(".form-wrap").find(":text").val("");
	$("#operator_idx").val("");
	$("#operator_pwd1").val("");
	$("#operator_pwd2").val("");
	
	$(".form-wrap select").prop("selectedIndex","0");
	
	$("#library_id").trigger("change");
	$("#library_id").attr("data-id","");
	
}

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

function closeLayer(index){
	if (index) {
		layer.close(index);
	}
}


<%-- 删除事件 --%>
var delLayer = null;

<%-- 单条删除事件 --%>
var deleteIdx = "";
var deleteName = "";
var version_stamp ="";
$("tbody").on("click",".delete",function(){
	deleteIdx = $(this).attr("data-id");
	deleteName = $(this).attr("data-name");
	version_stamp = $.trim($(this).parents("tr").find(".version_stamp").html());
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
	var len = 0;
	$("tbody input[type='checkbox']:checked").each(function(){
		var obj = {};
		var idx = $(this).prop("id");
		var operName = $(this).prop("value");
		obj.idx=idx;
		obj.operName=operName;
		deleteList.push(obj);
	});
	len = deleteList.length;
	if(len<=0){
		layer.alert("请先选择要删除的用户！");
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

<%-- 删除多条信息失败时的提示框的确定按钮 --%>
$(".form-btn.cancel.g-btn-green").on("click",function(){
	closeLayer(multiLayer);
});


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
	var operator = {};
	operator.operator_idx = deleteIdx;
	operator.version_stamp = version_stamp;
	$.ajax({
		url:"<%=basePath%>operator/delOperator",
		type:"POST",
		data:{"json":JSON.stringify(operator)}
	}).done(function(data){
		console.log("delete result",data);
		if(data!=null && data!=""){
			if(data.state && data.message=="success"){
				showMsg({
					timeout : 1000,
					showText : '删除成功',
					status : true
				});
			}else{
				var msg = "";
				msg = data.message;
				closeLayer(delLayer);
				if(msg.indexOf("optimistic")>=0){
					layer.alert("当前选择的数据不是最新数据,请刷新之后再做删除操作");
				}else{
					$(".g-delete-dialog.alert .word").html("删除失败,"+msg);
					multiLayer = layer.open({
						type: 1,
						shade: false,
						title: false, //不显示标题
						scrollbar :false,
						closeBtn :0,
						shade:0.5,
						shadeClose :false,
						area:["400px"],
						offset :["195px"],
						content: $('.g-delete-dialog.alert')
					});
				}
			}
		}
	}).always(function(){
		$delete.removeClass("disabled");
		refreshCurrent();
		closeLayer(delLayer);
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
		url:"<%=basePath%>operator/delMultiOperator",
		type:"POST",
		data:{"json":JSON.stringify(deleteList)}
	}).done(function(data){
		if(data!=null && data!=""){
			if(data.state && data.message=="success"){
				if(!_.isEmpty(data.result.cannotDel)){
					closeLayer(delLayer);
					$(".g-delete-dialog.alert .word").html("删除成功，其中"+data.result.cannotDel+"，无法删除！");
					multiLayer = layer.open({
						type: 1,
						shade: false,
						title: false, //不显示标题
						scrollbar :false,
						closeBtn :0,
						shade:0.5,
						shadeClose :false,
						area:["400px"],
						offset :["195px"],
						content: $('.g-delete-dialog.alert')
					});				
				}else{
					showMsg({
						timeout : 1000,
						showText : '删除成功',
						status : true
					});
				}
			}else{
				showMsg({
					timeout : 3000,
					showText : "删除失败,"+data.message,
					status : false
				});
			}
		}
	}).always(function(){
		$delete.removeClass("disabled");
		refreshCurrent();
		closeLayer(delLayer);
	});
});


//组装 翻页和查询 参数
function makeQueryParam(page,pageSize){
	var keyword=$("#keyword").val();
	var type=$("#paramType").val();
	var Page ={
		"page":page,
		"pageSize":pageSize,
		"type":type,
		"keyword":keyword
	};
	return Page;
};

//下一页操作
$("div.paging-bar").on("click",".next-page",function(){
	var currentpage = $("#page").find("li.active").html();//当前页
	var cpage = Number(currentpage) + 1;//下一页
	param = makeQueryParam(cpage, pageSize);
	selectOperatorPage(param);
});

//上一页操作
$("div.paging-bar").on("click",".prev-page",function(){
	var currentpage = $("#page").find("li.active").html();
	var page=Number(currentpage)-1;
	param=makeQueryParam(page, pageSize);
	//带参数
	selectOperatorPage(param);
});

//点击某一页
$("div.paging-bar").on("click","li",function(){
	if($(this).hasClass("active")) return;
	var page = $(this).html();
	if(page=="...") return;	
	param=makeQueryParam(page, pageSize);
	selectOperatorPage(param);
});

<%-- 选择显示页码 --%>
$("div.paging-bar").on("change",".refresh select",function(){
	var pages = $(this).val();
	pageSize = pages;
	var Page=makeQueryParam(1, pageSize);
	selectOperatorPage(Page);
});

//刷新当前页
function refreshCurrent(){
	var currentpage = $("#page").find("li.active").html();
	param=makeQueryParam(currentpage, pageSize);
	//带参数
	selectOperatorPage(param);
}

//查询按钮
$(".btn.search").on("click",function(){
	param = makeQueryParam(1,pageSize)
	selectOperatorPage(param);
});

var editLayer = null;
var nowAjax=null;
$tbody.on("click",".edit",function(){
	clearData();
	<%-- 隐藏密码 --%>
	$("li.pwd").addClass("hide");
	<%-- --%>
	$("div.pwd").removeClass("hide");
	var idx = $(this).prop("id");
	
	<%-- 判断是否禁用输入框 --%>
	if(global_operType=="1" || global_operType=="2"){
		$("#library_id").removeClass("disa");
		$("#library_id").prop("readonly",false);
	}else{
		$("#library_id").prop("readonly",true);
		$("#library_id").addClass("disa");
	}
	var version_stamp=$.trim($(this).parents("tr").find(".version_stamp").html());
	$("#version_stamp").val(version_stamp);
	<%-- 停止当前的ajax请求,防止网络延迟造成的加载错误问题 --%>
	if(nowAjax!=null){
		nowAjax.abort();
	}
	editLayer = layer.open({
		type: 1, 
		content: $(".form-dialog-2"),
		title :false,
		closeBtn :1,
		area :["860px","660px"],
		shade:0.5,
		shadeClose :false,
		scrollbar :false,
		move:false,
		skin:false,
		success :function(layero, index){
			$(".form-dialog-2 .title").text("编辑操作员");
// 			$(".form-dialog-2 .g-submit").val("保存").removeClass("g-btn-green").addClass("g-btn-blue");
			
			nowAjax = $.ajax({
				url:"${pageContext.request.contextPath}/operator/queryOperatorDetailByIdx",
				type:"POST",
				data:{idx:idx}
			}).done(function(data){
				if(data!=null && data!=""){
					if(data.state){
						var o = data.result.operator;
						var l = data.result.library;
						var infolist = data.result.infoList;
						var addlist = data.result.addList;
						var typeList = data.result.typeList;
						var g = data.result.group;
						console.log("操作员分组信息",g);
						var m = data.result.mCard;
						setInfoList(infolist,globalAddList);
// 						setAddList(addlist);
						setOperatorType(typeList);
						$("#operator_idx").val(o.operator_idx);
						$("#operator_id").val(o.operator_id);
						$("#operator_name").val(o.operator_name);
						$("#operator_type").val(o.operator_type);
						if(o.isActive==1){
							$("#invalid").trigger("change");
						}else{
							$("#valid").trigger("change");
						}
						if(o.isLock==1){
							$("#lock").prop("checked",true).trigger("change");
						}else{
							$("#unlock").prop("checked",true).trigger("change");
						}
						$("#library_id").val(l.lib_id);
						var groupId = null;
						<%-- 设置用户所在用户组 --%>
						if(g!=null && g.state && g.result!=null){
							groupId = g.result.operator_group_idx;
						}
						<%-- 设置用户的维护卡信息 --%>
						var mId = null;
						if(m!=null && m.state && m.result!=null){
							mId = m.result.maintenance_idx;
						}
						<%-- 传递参数用户组参数 ,[groupId,mId]--%>
						$("#library_id").attr("data-id",l.library_idx).trigger("change",[groupId,mId]);
						$("#library_name").val(l.lib_name);
						$("#soxTemp").val(o.sox_tpl_id);
						
						
					}else{
						layer.alert(data.message);
					}
				}
			});
		}
	}); 
});

<%-- 设置infolist  --%>
function setInfoList(list,addList){
	if(list!=null && list.length>0){
		var infoHtml = "";
		var arr = [];
		if(addList!=null){
			for(var i=0;i<addList.length;i++){
				arr.push(addList[i].infotype_idx);
			}
		}
		<%-- console.log($.inArray(6, arr)); --%>
		
		for(var i=0;i<list.length;i++){
			var info = list[i];
			<%-- 生日 --%>
			var val = info.info_value==null?"":info.info_value;
			if(info.infotype_idx=="1"){
				infoHtml += "<li>";
				infoHtml += "<div class=\"left\">"+info.info_type_desc+"</div>";
				infoHtml += "<div class=\"right\">";
				infoHtml += "	<div class=\"g-inputdate\"><input readonly=\"readonly\" data-type=\""+info.infotype_idx+"\" type=\"text\" placeholder=\"请输入\" value=\""+val+"\" class=\"g-input timepicker\"><span class=\"icon\"></span></div>"; 
				infoHtml += "</div>";
				infoHtml += "</li>";
			}else{
				infoHtml += "<li>";
				infoHtml += "<div class=\"left\">"+info.info_type_desc+"</div>";
				infoHtml += "<div class=\"right\"><input data-type=\""+info.infotype_idx+"\" maxlength=\"100\" type=\"text\" placeholder=\"请输入\" value=\""+val+"\" class=\"g-input\"></div>";
				if($.inArray(info.infotype_idx,arr)>=0){
					infoHtml += "<div class=\"delete-li\"></div>";
				}
				infoHtml += "</li>";
			}
		}
		
		$("#addinfo").siblings("li").remove();
		$("#addinfo").before(infoHtml);
		
		<%-- 日期控件生效 --%>
		initTimepicker();
	}
}

<%-- 设置可新增的infolist --%>
function setAddList(list){
	if(list!=null && list.length>0){
		var addHtml = "";
		for(var i=0;i<list.length;i++){
			var info = list[i];
			addHtml += "<div class=\"add-info li\" data-type=\""+info.infotype_idx+"\">"+info.info_type_desc+"</div>";
		}
		$("#addinfo .li-list").html(addHtml);
	}
}

<%-- 操作员类型 --%>
function setOperatorType(typeList){
	if(typeList!=null && typeList.length>0){
		var typeHtml = "";
		for(var i=0;i<typeList.length;i++){
			var type = typeList[i];
			typeHtml += "<option value=\""+type.type+"\" >"+type.name+"</option> ";
		}
		$("#operator_type").html(typeHtml);
	}
}

<%-- 保存按钮 --%>
$(".g-submit").on("click",function(){
	if(!checkData()){
		return ;
	}
	var operIdx = $("#operator_idx").val();
	
	operatorSubmit = {};
	
	operatorSubmit = saveOperatorData();
	
	if(!_.isEmpty(operIdx)){
		<%-- 如果不为空则为编辑 --%>
// 		var flag = compareSoxTemp(allSoxTempList[sox_id],soxTempSubmit);
// 		if(flag){
// 			直接提示保存成功
// 			showMsg({
// 				timeout : 1000,
// 				showText : '保存成功',
// 				status : true,
// 				callback:function(){
// 					var thisDom = $(".g-submit");
// 					/*应该是成功的时候收回吧，酌情处理*/
// 					var thisRight =  thisDom.parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
// 					thisDom.parents(".form-dialog-fix").find(".form-dialog").animate({
// 						"right":thisRight
// 					},function(){
// 						thisDom.parents(".form-dialog-fix").fadeOut(100);
// 					});
// 				}
// 			});
// 		}else{
			//更新到数据库
			uploadOperatorData(operatorSubmit,"update");
// 		}
	}else{
		var pwd = operatorSubmit.operator.operator_pwd;
		var soxid = operatorSubmit.operator.sox_tpl_id;
		<%-- 新用户检测密码模板 --%>
		$.ajax({
			url:"<%=basePath%>operator/checkPwdFormat",
			type:"POST",
			data:{pwd:pwd,soxid:soxid},
			timeout:10000
		}).done(function(data){
			if(data!=null) {
				if(data.state){
					<%-- 新增 --%>
 					uploadOperatorData(operatorSubmit,"add");
				}else{
					if(data.retMessage=="charset"){
						var msg = data.message;
						var char = msg.substring(msg.indexOf(":")+1);
						var info = "";
						if(char.indexOf("1")>-1){
							info += "大写字母,";
						}
						if(char.indexOf("2")>-1){
							info += "小写字母,";
						}
						if(char.indexOf("3")>-1){
							info += "数字,";
						}
						if(char.indexOf("4")>-1){
							info += "特殊字符(^%&',;=?$/_@),";
						}
						if(info.length>0){
							info = info.substring(0,info.length-1);
						}
						info = "密码由"+info+"组成";
						$("#operator_pwd1").closest("li").addClass("error");
						$("#operator_pwd1").closest("li").find("span.error-msg").html(info);
						$("#operator_pwd1").focus();
					}else if(data.retMessage == "length"){
						$("#operator_pwd1").closest("li").addClass("error");
						$("#operator_pwd1").closest("li").find("span.error-msg").html(data.message);
						$("#operator_pwd1").focus();
					}else{
						layer.alert(data.message);
					}
				}
			}
		}).always(function(data,status){
			if(status=="timeout"){
				layer.alert("连接超时，请重试");
			}
		});
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

<%-- 保存用户的信息 --%>
function saveOperatorData(){
	var obj = {};
	var operator = {};
	var infoList = [];
	operator.operator_idx = $("#operator_idx").val();
	operator.operator_id = $("#operator_id").val();
	operator.operator_name = $("#operator_name").val();
	operator.operator_pwd = $("#operator_pwd1").val();
	operator.library_id = $("#library_id").val();
	operator.library_idx = $("#library_id").attr("data-id");
	operator.operator_type = $("#operator_type").val();
	operator.groupId = $("#libraryGroup").val();
	operator.maintenanceCard = $("#maintenance_card").val();
	operator.sox_tpl_id = $("#soxTemp").val();
	operator.version_stamp = $("#version_stamp").val();
	if($("#invalid").prop("checked")){
		operator.isActive = 1;
	}else{
		operator.isActive = 0;
	}
	if($("#lock").prop("checked")){
		operator.isLock = 1;
	}else{
		operator.isLock = 0;
	}
	
	$("#infolist li:not('#addinfo')").each(function(i,n){
		var infoObj = {};
		$input = $(this).find("input");
		var infotype_idx = $input.attr("data-type");
		var val = $input.val();
		if(val!=null && val!=""){
			infoObj.infotype_idx = infotype_idx;
			infoObj.value = val;
			infoList.push(infoObj);
		}
	});
	
	obj.operator = operator;
	obj.infoList = infoList;
	
	return obj;
	
}

function checkData(){
	var flag = true;
	
	var operator_idx = $("#operator_idx").val();
	
	var operator_id = $("#operator_id").val();
	var operator_name = $("#operator_name").val();
	var library_id = $("#library_id").val();
	var library_idx = $("#library_id").attr("data-id");
	var pwd1 = $("#operator_pwd1").val();
	var pwd2 = $("#operator_pwd2").val();
	var soxTemp = $("#soxTemp").val();
	if(soxTemp==null){
		layer.alert("请选择密码模板，如果没有，请鉴权模板配置中新增！");
		return false;
	}
	
	var groupid = $("#libraryGroup").val();
	
	if(_.isEmpty(groupid) || groupid=="-1"){
		$("#libraryGroup").closest("li").addClass("error");
		$("#libraryGroup").focus();
		flag = false;
	}else{
		$("#libraryGroup").closest("li").removeClass("error");
	}
	
	
	if(_.isEmpty(library_id) || _.isEmpty(library_idx)){
		$("#library_id").closest("li").addClass("error");
		$("#library_id").focus();
		flag = false;
	}else{
		$("#library_id").closest("li").removeClass("error");
	}
	
	
	if(_.isEmpty(operator_name)){
		$("#operator_name").closest("li").addClass("error");
		$("#operator_name").focus();
		flag = false;
	}else{
		$("#operator_name").closest("li").removeClass("error");
	}
	
	if(_.isEmpty(operator_idx)){
		<%-- 新增用户要检测密码 --%>
		if(_.isEmpty(pwd2)){
			$("#operator_pwd2").closest("li").addClass("error");
			$("#operator_pwd2").closest("li").find("span.error-msg").html("请输入确认密码");
			$("#operator_pwd2").focus();
			flag = false;
		}else{
			$("#operator_pwd2").closest("li").removeClass("error");
		}
		if(_.isEmpty(pwd1)){
			$("#operator_pwd1").closest("li").addClass("error");
			$("#operator_pwd1").closest("li").find("span.error-msg").html("请输入密码");
			$("#operator_pwd1").focus();
			flag = false;
		}else{
			$("#operator_pwd1").closest("li").removeClass("error");
		}
		if(!_.isEmpty(pwd1) && !_.isEmpty(pwd2)){
			if(!_.isEqual(pwd1,pwd2)){
				$("#operator_pwd2").closest("li").addClass("error");
				$("#operator_pwd2").closest("li").find("span.error-msg").html("密码不一致，请确认");
				$("#operator_pwd2").focus();
				flag = false;
			}else{
				$("#operator_pwd2").closest("li").removeClass("error");
			}
		}
		
	}
	
	if(_.isEmpty(operator_id)){
		$("#operator_id").closest("li").addClass("error");
		$("#operator_id").focus();
		flag = false;
	}else{
		$("#operator_id").closest("li").removeClass("error");
	}
	
	return flag;
}

<%-- 上传操作员信息 --%>
function uploadOperatorData(obj,type){
	if(type=="update"){
		$.ajax({
			url:"<%=basePath%>operator/updateOperator",
			type:"POST",
			timeout:8000,
			data:{"json":JSON.stringify(obj)}
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state && data.message=="success"){
					refreshCurrent();
					showMsg({
						timeout : 1000,
						showText : '修改成功',
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
							/**清空数据 2016年12月17日14:52:56**/
							$(".form-dialog-2").find(":text").val();
							$(".form-dialog-2").find("select:eq(0)").trigger();
						}

					});
					closeLayer(editLayer);
				}else{
					var msg = data.message;
					if(msg.indexOf("optimistic")>=0){
						layer.alert("当前选择的数据不是最新数据,请刷新之后再做编辑操作");
					}else{
						showMsg({
							timeout : 3000,
							showText : "修改失败！"+data.message,
							status : false
						});
					}
				}
			}
			
		}).fail(function(data){
			console.log(data);
		}).always(function(data,status){
			if(status=="timeout"){
				layer.alert("请求超时，请重试！");
			}
			console.log(data);
		});
	}
	
	if(type=="add"){
		$.ajax({
			url:"<%=basePath%>operator/addOperator",
			type:"POST",
			timeout:8000,
			data:{"json":JSON.stringify(obj)}
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state && data.message=="success"){
					refreshCurrent();
					showMsg({
						timeout : 1000,
						showText : '新增成功',
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
					closeLayer(editLayer);
				}else{
					showMsg({
						timeout : 3000,
						showText : "新增失败！"+data.message,
						status : false
					});
				}
			}
			
		}).fail(function(data){
			console.log(data);
		}).always(function(data,status){
			if(status=="timeout"){
				layer.alert("请求超时，请重试！");
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

$(".increase").on("click",function(){
	clearData();
	<%-- 显示用户密码 --%>
	$("li.pwd").removeClass("hide");
	<%-- 隐藏重置密码 --%>
	$("div.pwd").addClass("hide");
	
	<%-- 默认图书馆ID为当前管理员的馆ID --%>
	if(global_libid==null){
		queryOperLibraryInfo("1");
	}else{
		$("#library_id").val(global_libid).trigger("change");
		if(global_operType=="1" || global_operType=="2"){
			$("#library_id").removeClass("disa");
			$("#library_id").prop("readonly",false);
		}else{
			$("#library_id").prop("readonly",true);
			$("#library_id").addClass("disa");
			
		}
	}
	
	<%-- 重置用户信息 --%>
	setInfoList(globalInfoList,globalAddList);
	
	editLayer = layer.open({
		type: 1, 
		content: $(".form-dialog-2"),
		title :false,
		closeBtn :1,
		area :["860px","660px"],
		shade:0.5,
		shadeClose :false,
		scrollbar :false,
		move:false,
		skin:false,
		success :function(layero, index){
			$(".form-dialog-2 .title").text("新增操作员");
// 			$(".form-dialog-2 .g-submit").val("新增").removeClass("g-btn-blue").addClass("g-btn-green");
		}
	}); 

});


function initTimepicker(){
	/*form-dialog-2*/
	$( ".timepicker" ).datepicker({
		numberOfMonths:1,//显示几个月  
		showButtonPanel:false,//是否显示按钮面板  
		changeYear: true,//年份可选
		changeMonth: true,//月份可选
		dateFormat: 'yy-mm-dd',//日期格式  
		clearText:"清除",//清除日期的按钮名称  
		closeText:"关闭",//关闭选择框的按钮名称  
		yearSuffix: '', //年的后缀  
		showMonthAfterYear:true,//是否把月放在年的后面  
		defaultDate:+0,//默认日期  
// 		minDate:'1950-01-01',//最小日期  
		maxDate:new Date(),//最大日期  
		yearRange:"1970:2030",//年份范围
		monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
		monthNamesShort:['1','2','3','4','5','6','7','8','9','10','11','12'],//选择月份显示的内容
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
		dayNamesMin: ['日','一','二','三','四','五','六'],  
		onSelect: function(selectedDate) {//选择日期后执行的操作  
	
		}  
	});
}

<%-- 图书馆ID修改之后，根据id查询图书馆名称，并且查询图书馆的用户组信息 --%>
<%-- groupId:用户所在的用户组id --%>
<%-- groupId mId 在queryOperatorDetailByIdx的ajax中传递过来 --%>
$("#library_id").on("change",function(e,groupId,mId){
	var libId = $(this).val();
	queryLibName(libId,groupId,mId);
	if(libId=="0"){
		$("#operator_type").find("option").each(function(index,dom){
			if($(dom).val()=="3" || $(dom).val()=="4"){
				$(dom).attr("style","display:none;");
			}else{
				$(dom).attr("style","");
			}
		});
		$("#operator_type").val("1");
	}else{
		 $("#operator_type").find("option").each(function(index,dom){
			if($(dom).val()=="1" || $(dom).val()=="2"){
				$(dom).attr("style","display:none;");
			}else{
				$(dom).attr("style","");
			}
		});
		$("#operator_type").val("3");
	}
}).on("keyup",function(e,groupId,mId){
	var libId = $(this).val();
	if(libId=="0"){
		$("#operator_type").find("option").each(function(index,dom){
			if($(dom).val()=="3" || $(dom).val()=="4"){
				$(dom).attr("style","display:none;");
			}else{
				$(dom).attr("style","");
			}
		});
		$("#operator_type").val("1");
	}else{
		 $("#operator_type").find("option").each(function(index,dom){
			if($(dom).val()=="1" || $(dom).val()=="2"){
				$(dom).attr("style","display:none;");
			}else{
				$(dom).attr("style","");
			}
		});
		$("#operator_type").val("3");
	}
	queryLibName(libId,groupId,mId);
});

var loaddingLibName = false;
function queryLibName(libId,groupId,mId){
	if(!_.isEmpty(libId) && !loaddingLibName){
		loaddingLibName=true;
		$.ajax({
			url:"<%=basePath%>operator/getLibNameByIdx",
			type:"POST",
			data:{json:libId},
			success:function(data){
				loaddingLibName=false;
				if(data!=null && data!=""){
					if(data.state){
						if(data.result.lib_name!=null){
							$("#library_name").val(data.result.lib_name);
							$("#library_id").val(data.result.lib_id);
							$("#library_id").attr("data-id",data.result.library_idx);
							<%-- 根据图书馆idx查询对应图书馆的用户分组信息 ，并且传递当前用户所属的图书馆分组idx--%>
							queryLibraryServiceGroup(data.result.library_idx,groupId);
							<%-- 根据图书馆idx查询对应图书馆的维护卡信息，并且传递当前用户的维护卡idx --%>
							queryMaintenanceCard(data.result.library_idx,mId);
						}else{
							$("#library_name").val(data.result);
							$("#library_id").attr("data-id","");
						}
					}
				}
			}
		});
	}else{
		$("#libraryGroup").html("<option value=\"-1\">请选择</option>");
		$("#maintenance_card").html("<option value=\"-1\">请选择</option>");
		
	}
}


$("#infolist ul").on("click",".add-li",function(){
	$(".li-list").toggle();
	var bodyShade = '<div class="body-shade"></div>';
	if($(".body-shade").length){
		return;
	}
	$(this).append(bodyShade);
});

$("#infolist ul").on("click",".add-info",function(){
	var text = $(this).text();
	var type = $(this).attr("data-type");
	var html = "";
	html += "<li>";
	html += "<div class=\"left\">"+text+"</div>";
	html += "<div class=\"right\"><input data-type=\""+type+"\" type=\"text\" placeholder=\"请输入\" class=\"g-input\"></div>";
	html += "<div class=\"delete-li\"></div>";
	html += "</li>";
	$(this).parents("li").before(html);
});

<%--
$("#infolist ul").on("click",".add-phone",function(){
	var html = '<li><div class="left">手机</div><div class="right"><input type="text" placeholder="请输入" class="g-input"></div></li>';
	$(this).parents("li").before(html);
});
$("#infolist ul").on("click",".add-email",function(){
	var html = ' <li><div class="left">Email</div><div class="right"><input type="text" placeholder="请输入" class="g-input"></div></li>';
	$(this).parents("li").before(html);
});
$("#infolist ul").on("click",".add-address",function(){
	var html = '<li><div class="left">家庭住址</div><div class="right"><input type="text" placeholder="请输入" class="g-input"></div></li>';
	$(this).parents("li").before(html);
});
--%>

<%-- 自定义脚本 --%>

// <tr>
// <td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
// <td class="col2">0001</td>
// <td class="col3">zhangsan</td>
// <td class="col4">张三</td>
// <td class="col5">系统管理部</td>
// <td class="col6"><span class="status gray">无效</span></td>
// <td class="col7">
// 	<span class="btn-a edit">编辑</span>
// 	<span class="btn-a delete">删除</span>
// </td>
// </tr>
	function drawRow(rows){
		if(!rows){return;}
		var tbody='';
		var perNum=0;
		for(var i=0;i<rows.length;i++){
			var tr='<tr>';
			var row=rows[i];
			if(o.operator_idx==row.operator_idx){
				tr+='<td class="col1"></td>';
			}else{
			   tr+='<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="'+row.operator_idx+'" value="'+row.operator_name+'" /></div></td>';
			}
			tr+='<td class="col2">'+row.operator_id+'</td>';
			tr+='<td class="col3">'+row.operator_name+'</td>';
			var optype = "";
			if(row.operator_type=="1") optype = "云平台管理员";
			if(row.operator_type=="2") optype = "云平台维护人员";
			if(row.operator_type=="3") optype = "图书馆管理员";
			if(row.operator_type=="4") optype = "图书馆馆员";
			tr+='<td class="col4">'+optype+'</td>';
			var libName=row.lib_name==null?"":row.lib_name;
			tr+='<td class="col5">'+libName+'</td>';
			if(row.isActive==1){
				tr+='<td class="col6"><span class="status green">已激活</span></td>';
			}else{
				tr+='<td class="col6"><span class="status red">未激活</span></td>';
			}
			if(row.isLock==1){
				tr+='<td class="col6"><span class="status red">已锁定</span></td>';
			}else{
				tr+='<td class="col6"><span class="status green">未锁定</span></td>';
			}
			tr+='<td class="col7">';
			<shiro:hasPermission name="0101010103">
				tr+='<span class="btn-a edit" id="'+row.operator_idx+'">编辑</span>';
				perNum++;
			</shiro:hasPermission>
			<shiro:hasPermission name="0101010102">
				if(o.operator_idx!=row.operator_idx){
					tr+='<span class="btn-a delete" data-id="'+row.operator_idx+'" data-name="'+row.operator_name+'">删除</span>';
				}
				perNum++;
			</shiro:hasPermission>
			tr+='</td>';
			tr+='<td class="version_stamp hide">'+row.version_stamp+'</td>';
			tr+='</tr>';
			tbody+=tr;
		}
		$tbody.html(tbody);
		if(perNum==0){
			$(".col7").attr("style","display:none");
		}else{
			$(".col7").attr("style","");
		}
	};
	
	
	//分页查询
	function selectOperatorPage(obj){
		startLoading();
		$tbody.html("");
		$(".col1 :checkbox").prop("checked",false).trigger("change");
		
		$.ajax({
			url:"${pageContext.request.contextPath}/operator/queryOperatorByParam",
			type:"POST",
			data:{"req":JSON.stringify(obj)},
			timeout:8000
		}).done(function(data){
			if(data){
				var page=data.result;
				if(page && page.rows){
					drawRow(page.rows);
					$.pagination(page);
				}
			}
		}).always(function(data,status){
			if(status=="timeout"){
				layer.alert("请求超时，请重试！");
			}
			endLoading(1);
		});
	};

	<%-- 查询所有的用户模板 --%>
	function queryAllSoxTemp(){
		$.ajax({
			url:"${pageContext.request.contextPath}/operator/queryAllSoxTemp",
			type:"POST",
			data:""
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state){
					var list = data.result;
					var soxHtml = "";
					for(var i=0;i<list.length;i++){
						soxHtml += "<option value=\""+list[i].sox_tpl_id+"\">"+list[i].sox_tpl_name+"</option>";
					}
					$("#soxTemp").html(soxHtml);
				}else{
					console.log(data.message);
				}
			}
		});
	}
	
	<%-- 查询所有的用户组信息， 以及可以新增的选项 --%>
	var globalInfoList; <%-- 全局变量，用户信息项 --%>
	var globalAddList;  <%-- 全局变量，可以新增的图书信息 --%>
	function queryAllOperatorInfo(){
		$.ajax({
			url:"${pageContext.request.contextPath}/operator/queryAllOperatorInfo",
			type:"POST",
			data:""
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state){
					if(!_.isEmpty(data.result) && !_.isEmpty(data.result.infoList)){
						globalInfoList = data.result.infoList;
						globalAddList = data.result.addList;
						setInfoList(globalInfoList,globalAddList);
					}
					if(!_.isEmpty(data.result) && !_.isEmpty(data.result.addList)){
						var addList = data.result.addList;
						setAddList(addList);
					}
				}else{
					console.log(data.message);
				}
			}
		});
	}
	
	<%-- 根据当前用户的权限查询可以显示的用户类型 --%>
	function queryOperatorTypes(){
		$.ajax({
			url:"${pageContext.request.contextPath}/operator/queryOperatorTypes",
			type:"POST",
			data:""
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state){
					var list = data.result;
					setOperatorType(list);
				}else{
					console.log(data.message);
				}
			}
		});
	}
	
	<%-- 暂时不用， 查询所有用户组权限 --%>
	function queryAllServiceGroup(){
		var lib = {};
		lib.library_idx = "1";
		<%-- 查询用户所在图书馆的的所有用户组 --%>
		$.ajax({
			url:"${pageContext.request.contextPath}/opergroup/queryAllServiceGroup",
			type:"POST",
			data:{"req":lib}
		}).done(function(data){
			
		});
	}
	$("#cmdtype").on("click","dd",function(){
		$("#cmdlist").html("");
		var id = $(this).prop("id");
		if(id!=null){
			setCmdList(globalCmdList[id]);
		}
		
	});
	
	<%-- 选择权限列表之后设置对应的权限显示 --%>
	$("#libraryGroup").change(function(){
		globalCmdList = {};
		$("#cmdtype").html("");
		$("#cmdlist").html("");
		var group_idx = $(this).val();
		if(group_idx!=null && group_idx!="" && group_idx!="-1"){
			var typelist = globalCmdTypeList[group_idx];
			if(typelist!=null){
				var dd = "";
				for(var i=0;i<typelist.length;i++){
					var type = typelist[i];
					dd+="<dd id=\""+type.opercmd+"\">"+type.operbusinesstype+"</dd>"
					globalCmdList[type.opercmd] = type[type.opercmd];
					if(i==0){
						setCmdList(globalCmdList[type.opercmd]);
					}
				}
				$("#cmdtype").html(dd);
			}
		}else{
			
		}
	});
	
	<%-- 权限列表第二个表 --%>
	function setCmdList(cmdObj){
		if(cmdObj!=null){
			var dd = "";
			for(var i=0;i<cmdObj.length;i++){
				var obj = cmdObj[i];
				dd += "<dd>"+obj.opercmd_desc+"</dd>";
			}
			$("#cmdlist").html(dd);
		}
	}
	
	
	<%-- 选择了图书馆之后，查询该图书馆的用户组 groupId:该用户所在图书馆的组--%>
	var globalCmdTypeList = {}; <%-- 权限的主目录类型 --%>
	var globalCmdList = {};<%-- 权限的次目录类型 --%>
	function queryLibraryServiceGroup(library_idx,groupId){
		var lib = {};
		lib.library_idx = library_idx+"";
		<%-- 查询用户所在图书馆的的所有用户组 --%>
		$.ajax({
			url:"${pageContext.request.contextPath}/opergroup/queryLibraryServiceGroup",
			type:"POST",
			data:{"req":JSON.stringify(lib)}
		}).done(function(data){
			var html = "<option value=\"\" selected>请选择</option>";
			if(data!=null && data!="" && data.state && data.result!=null){
				var list = data.result;
				for(var i=0; i< list.length;i++){
					var item = list[i];
					globalCmdTypeList[item.operator_group_idx] = item.typelist;
					//当前用户的权限小于操作员组的权限时，则不显示该操作员组 (云管理员除外) add by huanghuang 20170217
					var o = <shiro:principal/>;
					if(item.typelist.length!=0||o.operator_idx==1)
						html += "<option value=\""+item.operator_group_idx+"\" >"+item.operator_group_name+"</option>";
				}
			}
			$("#libraryGroup").html(html);
			if(groupId!=null){
				<%-- 设置该用户的用户组 --%>
				$("#libraryGroup").val(groupId).trigger("change");
			}
		});
	}
	
	<%-- 根据图书馆ID获取维护卡信息 --%>
	function queryMaintenanceCard(library_idx,mId){
		var lib = {};
		lib.library_idx = library_idx+"";
		<%-- 查询用户所在图书馆的的所有用户组 --%>
		$.ajax({
			url:"${pageContext.request.contextPath}/operator/queryMaintenanceCard",
			type:"POST",
			data:{"req":JSON.stringify(lib)}
		}).done(function(data){
			var html = "<option value=\"\" selected>请选择</option>";
			if(data!=null && data!="" && data.state && data.result!=null){
				var list = data.result;
				for(var i=0; i< list.length;i++){
					var item = list[i];
					html += "<option value=\""+item.maintenance_idx+"\" >"+item.card_id+"</option>";
				}
			}
			$("#maintenance_card").html(html);
			if(mId!=null){
				$("#maintenance_card").val(mId);
			}
		});
	}
	
	
	
	<%-- 重置密码 --%>
	var resetlayer = null
	$("#resetPwd").click(function(){
		<%-- 获取修改的密码 --%>
		$.ajax({
			url:"${pageContext.request.contextPath}/operator/getDefaultPwd",
			type:"GET"
		}).done(function(data){
			$(".resetpwd .word").html("将该用户的密码重置为："+data.message+"？");
			resetlayer = layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				scrollbar :false,
				closeBtn :0,
				shade:0.5,
				shadeClose :false,
				area:["400px"],
				offset :["195px"],
				content: $('.g-delete-dialog.resetpwd')
			});
		})
	});
	
	<%-- 取消按钮 --%>
	$(".resetpwd .form-btn.cancel").on("click",function(){
		closeLayer(resetlayer);
	});
	
	$(".resetpwd .form-btn.remove").on("click",function(){
		var idx = $("#operator_idx").val();
		if(idx!=null && idx!=""){
			var oper = {};
			oper.operator_idx = idx;
			$.ajax({
				url:"${pageContext.request.contextPath}/operator/resetPassword",
				type:"POST",
				data:{"req":JSON.stringify(oper)},
				timeout:8000
			}).done(function(data){
				if(!_.isEmpty(data) && data.state){
					layer.alert("重置成功！");
				}else{
					layer.alert("重置失败！"+data.message);
				}
			}).always(function(data,status){
				if(status=="timeout"){
					layer.alert("请求超时，请重试！");
				}
				closeLayer(resetlayer);
			});
		}
		
	});
	
	<%-- 确认密码修改事件 --%>
	$("#operator_pwd1,#operator_pwd2").on("blur",function(){
		if($("#operator_pwd1").closest("li").hasClass("error")
				|| $("#operator_pwd2").closest("li").hasClass("error")){
			var pwd1 = $("#operator_pwd1").val();
			var pwd2 = $("#operator_pwd2").val();
			if(_.isEmpty(pwd2)){
				$("#operator_pwd2").closest("li").addClass("error");
				$("#operator_pwd2").closest("li").find("span.error-msg").html("请输入确认密码");
				flag = false;
			}else{
				$("#operator_pwd2").closest("li").removeClass("error");
			}
			if(_.isEmpty(pwd1)){
				$("#operator_pwd1").closest("li").addClass("error");
				$("#operator_pwd1").closest("li").find("span.error-msg").html("请输入密码");
				flag = false;
			}else{
				$("#operator_pwd1").closest("li").removeClass("error");
			}
			if(!_.isEmpty(pwd1) && !_.isEmpty(pwd2)){
				if(!_.isEqual(pwd1,pwd2)){
					$("#operator_pwd2").closest("li").addClass("error");
					$("#operator_pwd2").closest("li").find("span.error-msg").html("密码不一致，请确认");
					flag = false;
				}else{
					$("#operator_pwd2").closest("li").removeClass("error");
				}
			}
		}
	});
	
	


	<%-- 回车事件 --%>
	$('#keyword').keydown(function(e){
		if(e.keyCode==13){
			param = makeQueryParam(1,pageSize)
			selectOperatorPage(param);
		}
	});
</script>




