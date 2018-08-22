<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
.form-dialog-fix .changebox {
	width:80%;
	margin: auto;
	margin-bottom: 60px;
}
.left1{
	float: left;
	width: 45%;
}
.left1 .g-input{
	width:90%!important;
	display: inline-block;
	margin-left: 15px;
}
.right1{
	float:left;
	width: 45%;
}
.right1 .g-input{
	width:90%!important;
	display: inline-block;
	margin-left: 15px;
}

.form-dialog .form-wrap{
	width:100%;
	height:100%;
	overflow: auto;
}
.form-dialog-fix .form-dialog .form-wrap .g-select.dev{
	margin: auto;
	margin-left:30%;
	width:175px;
}
</style>

<div class="user-manage">
<div class="g-loading"></div>
	<div class="">
		<div class="page-title-bar">
			<span class="title">升级模板配置<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/upgrade/upgrade-manage.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-select">
					<select id="patchType">
						<option value="" selected>版本类型</option>
						<option value="1">全网</option>
						<option value="2">馆约束</option>
						<option value="3">设备类型约束</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<div class="g-select">
					<select id="searchType">
						<option value="" selected>检索类型</option>
						<option value="patch_version">版本号</option>
						<option value="patch_desc">版本说明</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<input type="text" name="" id="keyword" class="input g-input" placeholder="输入检索值" />
				<div class="btn search">查询</div>
				<!-- 修改权限 by huanghuang 20170215 -->
				<shiro:hasPermission name="0110010101">
					<div class="btn increase">新增</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0110010102">
					<div class="btn delete">删除</div>
				</shiro:hasPermission>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="col2">版本号</th>
						<th class="col3">版本说明</th>
						<th class="col4">版本类型</th>
						<th class="col5">约束说明</th>
						<th class="col5">远程下载路径</th>
						<th class="col7">操作</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>
		</div>
		<%@include file="../include/page_bar.jsf"%>
	</div>
</div>
<div class="form-dialog-fix">
		<div class="shade"></div>
		<div class="form-dialog">
			<div class="title">
				<span>新增升级模板</span>
				<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
				<input type="submit" placeholder="" class="g-submit2 g-btn-green" value="保存">
			</div>
			<div class="form-wrap">
				<ul>
					<!--<li>
						<div class="left">模板ID</div>
						<div class="right">
							<input type="text" name="" id="" class="g-input" placeholder="请输入" />
							<span class="error-msg">错误提示</span>
						</div>
					</li>-->
					 <li class="">
						<div class="left">版本号</div>
						<div class="right">
							<input type="hidden" name="" id="p_idx" >
							<input type="hidden" name="" id="version_stamp" >
							<input type="text" name="" id="patch_version" class="g-input" placeholder="请输入" />
							<span class="error-msg">请输入版本号</span>
						</div>
					</li>
					<li>
						<div class="left">版本说明</div>
						<div class="right">
							<textarea id="patch_desc" class="g-textarea" placeholder="请输入"></textarea>
						</div>
					</li>
					<li>
						<div class="left">版本类型</div>
						<div class="right">
							<div class="g-select" style="float:left;">
								<select id="patch_type" class="need-change">
									<option value="1" selected>全网</option>
									<option value="2">馆约束</option>
									<option value="3">设备类型约束</option>
								</select>
								<span class="arr-icon"></span>
							</div>
							<span class="error-msg">请选择版本类型</span>
						</div>
					</li>
<!-- 					<li> -->
<!-- 						<div class="left">约束说明</div> -->
<!-- 						<div class="right"> -->
<!-- 							<textarea class="g-textarea" placeholder="请输入"></textarea> -->
<!-- 						</div> -->
<!-- 					</li>  -->
					<div date-id="patch_type" val="2" class="changebox">
						<li id="addLib" >
							<div class="left">&nbsp;</div>
							<div class="right">
								<div class="add-li">
								</div>
								<label for="addLib" style="cursor: pointer;">添加馆约束</label>
							</div>
						</li>
<!-- 						<li> -->
<!-- 							<div class="left1"> -->
<!-- 								<input class="g-input w50" placeholder="图书馆id"> -->
<!-- 							</div> -->
<!-- 							<div class="right1"> -->
<!-- 								<input class="g-input w50" placeholder="图书馆名称"> -->
<!-- 							</div> -->
<!-- 							<div class="delete-li"></div> -->
<!-- 						</li> -->
					</div>
					<div date-id="patch_type" val="3" class="changebox">
						<li id="addDev">
							<div class="left">&nbsp;</div>
							<div class="right">
								<div class="add-li">
								</div>
								<label for="addDev" style="cursor: pointer;">添加设备类型约束</label>
							</div>
						</li>
<!-- 						<li> -->
<!-- 							<div class="left"> -->
<!-- 							</div> -->
<!-- 							<div class="right"> -->
<!-- 								<div class="g-select dev" > -->
<!-- 									<select id="" > -->
<!-- 									</select> -->
<!-- 									<span class="arr-icon"></span> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="delete-li"></div> -->
<!-- 						</li> -->
					</div>
					<!-- <li>
						<div class="left">远程下载路径</div>
						<div class="right">
							<input type="text" name="" id="" class="g-input" placeholder="请输入" />
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="browse-btn">浏览</div>
						</div>
					</li> -->
				</ul>
			</div>
		</div>
	</div>
	</div>
<div class="g-delete-dialog multi">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red"></span> 个项目<br>
		是否要删除选择的配置？
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
<!-- <link rel="stylesheet" href="css/jqueryui.css"> -->
<!-- <link rel="stylesheet" href="css/theme.css"> -->
<!-- <script src="js/jqueryui.js"></script> -->
<!-- <script src="js/datepicker.js"></script> -->
<!-- <script src="plugins/layer/layer.js"></script> -->
<script type="text/javascript">
<%--字符串转JSON格式--%>
function jsonToObj(str){ 
	try{
		return JSON.parse(str); 
	}catch(e){
		return "";
	}
} 

$tbody=$("div.main").find(".g-table").find("tbody");

var pageSize=10;
var Page={"page":1,"pageSize":pageSize,};
var deviceTypeList = {};<%-- 保存设备类型 --%>
var deviceTypeHtml = "";<%-- 保存设备类型下拉框html --%>
var patchList = {};<%-- 保存当前页面的list --%>
var libIdAndNameObj={};
var libIdxAndNameObj={};
var flag="";
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
	
	
	$(".need-change").on("change",function(){
		/*
			每个需要交换显示的select框，都必须有
			类名need-change，
			唯一的ID名 对应着 将需要显示的那个DIV的date-id属性，
			select框 的value值 对应着 DIV的val属性
			如示例
		 */
		var thisId =  $(this).attr("id");
		var thisVal = $(this).val();

		var $lei = $("[date-id='"+thisId+"']");

		$lei.each(function(){
			var boxVal = $(this).attr("val");

			if(boxVal===thisVal){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	});
	
	queryAllSoxTemp();
	
	<%-- 查询设备类型 --%>
	getDeviceType(function(){
		selectUpgradePage(Page);
	});
	
	<%-- 查询图书馆信息 --%>
	getLibraryInfo();
	
});

var devicetype_idx_desc_pair={};


function getLibraryInfo(){
	$.ajax({
		url:"${pageContext.request.contextPath}/ascconfig/querylibInfoByCurUser",
		type:"GET",
		data:{}
	}).then(function(data){
		if(data){
			//获取到图书馆信息
			if(data.result){
				for(var i=0;i<data.result.length;i++){
					libIdAndNameObj[data.result[i].lib_id]={"lib_name":data.result[i].lib_name,"library_idx":data.result[i].library_idx};
					libIdxAndNameObj[data.result[i].library_idx]={"lib_id":data.result[i].lib_id,"lib_name":data.result[i].lib_name};
				}
			}
		}
	});
}


<%--获取所有设备类型--%>
function getDeviceType(callback){
	$.ajax({
		url:"<%=basePath%>deviceext/getDeviceTypes",
		type:"POST",
		data:""
	}).done(function(data){
		if(data!=null && data.state){
			var list = data.result;
			var html = "";//<option value=\"-1\" selected>选择类型</option>
			deviceTypeList = list;
			//deviceType
			for(var i=0;i<list.length;i++){
				var meta_devicetype_idx=list[i].meta_devicetype_idx;
				var device_type_desc=list[i].device_type_desc;
				devicetype_idx_desc_pair[meta_devicetype_idx]=device_type_desc;
				html += "<option value=\""+meta_devicetype_idx+"\">"+device_type_desc+"</option>"
			}
			console.log(devicetype_idx_desc_pair);
			deviceTypeHtml = html;
		}else{
			console.log(data.message);
		}
		if( typeof callback=="function"){
			callback();
		}
	});
}


<%-- 自定义脚本 --%>
	//拼接查询主页面
	function drawRow(rows){
		if(!rows){return;}
		var tbody='';
		for(var i=0;i<rows.length;i++){
			var tr='<tr>';
			var row=rows[i];
			patchList[row.patch_idx] = row;
			tr+='<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="'+row.patch_idx+'" data-name="'+row.patch_desc+'" value="'+row.patch_idx+'" /></div></td>';
			tr+='<td class="col2">'+row.patch_version+'</td>';
			tr+='<td class="col3">'+row.patch_desc+'</td>';
			var optype = "";
			if(row.patch_type=="1") optype = "全网";
			if(row.patch_type=="2") optype = "馆约束";
			if(row.patch_type=="3") optype = "设备类型约束";
			tr+='<td class="col4">'+optype+'</td>';
			tr+='<td class="col5">'+jsonStrToTrShow(row.restrict_info,row.patch_type)+'</td>';
			tr+='<td class="col5">'+row.patch_directory+'</td>';
			tr+='<td class="col7">';
			//通过权限码判断权限	add by huanghuang 20170215
			<shiro:hasPermission name="0110010103">
				tr+='<span class="btn-a edit" id="'+row.patch_idx+'">编辑</span>';
			</shiro:hasPermission>
			<shiro:hasPermission name="0110010102">
				tr+='<span class="btn-a delete" data-id="'+row.patch_idx+'" data-name="'+row.patch_desc+'">删除</span>';
			</shiro:hasPermission>
			tr+='</td>';
			tr+='<td class="version_stamp hide">'+row.version_stamp+'</td>';
			tr+='</tr>';
			tbody+=tr;
		}
		$tbody.html(tbody);
	};
	function jsonStrToTrShow(str,type){
		var obj=jsonToObject(str);
		//device_type library_id
		var type_str=null;
		var desc=null;
		if(type=="2"){
			type_str="library_id";
			desc="图书馆ID";
		}else if(type=="3"){
			type_str="device_type";
			desc="设备类型";
		}else if(type=="1"){
			return "";
		}
		if(type_str==null){
			return str;
		}
		var html=desc+"（";
		if(obj && obj instanceof Array){
			for(var i=0;i<obj.length;i++){
				var o=obj[i];
				var value=o[type_str];//lib id or device_type 
				console.log("value",value);
				if(type_str=="device_type"){
					var device_type_desc=devicetype_idx_desc_pair[value];
					if(device_type_desc){//undefined
						html+=device_type_desc+"，";
					}else{
						html+=value+"，";
					}
				}else{
					html+=value+"，"
				}
			}
			if(html.indexOf("，")>=0){
				html=html.substring(0, html.length-1)+"）";
			}
			return html;
		}else{
			return obj?"":obj;
		}
	}
	function jsonToObject(str){
		if(!str) return "";
		try{
			return JSON.parse(str);
		}catch(e){
			console.error(e,str);
			return "";
		}
	}
	//分页查询
	function selectUpgradePage(obj){
		$tbody.html("");
		$.ajax({
			url:"${pageContext.request.contextPath}/upgrade/queryUpgradeByParam",
			type:"POST",
			data:{"req":JSON.stringify(obj)}
		}).done(function(data){
			if(data){
				var page=data.result;
				if(page.rows){
					drawRow(page.rows);
					<%-- 分页 --%>
					$.pagination(page);
				}
			}
		});
	};

//组装 翻页和查询 参数
function makeQueryParam(page,pageSize){
	var keyword=$("#keyword").val();
	var type=$("#searchType").val();
	var patchtype=$("#patchType").val();
	var Page ={
		"page":page,
		"pageSize":pageSize,
		"type":type,
		"keyword":keyword,
		"patch_type":patchtype
	};
	return Page;
};

//下一页操作
$("div.paging-bar").on("click",".next-page",function(){
	var currentpage = $("#page").find("li.active").html();//当前页
	var cpage = Number(currentpage) + 1;//下一页
	var Page = makeQueryParam(cpage, pageSize);
	selectUpgradePage(Page);
});

//上一页操作
$("div.paging-bar").on("click",".prev-page",function(){
	var currentpage = $("#page").find("li.active").html();
	var page=Number(currentpage)-1;
	var Page=makeQueryParam(page, pageSize);
	//带参数
	selectUpgradePage(Page);
});

//点击某一页
$("div.paging-bar").on("click","li",function(){
	if($(this).hasClass("active")) return;
	var page = $(this).html();
	if(page=="...") return;	
	var Page=makeQueryParam(page, pageSize);
	selectUpgradePage(Page);
});

<%-- 选择显示页码 --%>
$("div.paging-bar").on("change",".refresh select",function(){
	pageSize = $(this).val();
	var Page=makeQueryParam(1, pageSize);
	selectUpgradePage(Page);
});

//刷新当前页
function refreshCurrent(){
	var currentpage = $("#page").find("li.active").html();
	var Page=makeQueryParam(currentpage, pageSize);
	//带参数
	selectUpgradePage(Page);
}

//查询按钮
$(".btn.search").on("click",function(){
	page = makeQueryParam(1,pageSize)
	selectUpgradePage(page);
});


$(".increase").on("click",function(){
	flag="increase";
	clearData();
	//新增操作
	$(".form-dialog-fix").fadeIn(100);
	$(".form-dialog-fix").find(".form-dialog").animate({
		"right":0
	});
	//修改按钮及title
	$(".form-dialog div.title span").html("新增升级模板");
});
var editLayer = null;
var nowAjax=null;
$tbody.on("click",".edit",function(){
	flag="update";
	clearData();
	//编辑操作
	$(".form-dialog-fix").fadeIn(100);
	$(".form-dialog-fix").find(".form-dialog").animate({
		"right":0
	});
	//修改按钮及title
	$(".form-dialog div.title span").html("修改升级模板");
	
	var id = $(this).attr("id");
	var item = patchList[id];
	
	loadPatchInfo(item);
	
});

function loadPatchInfo(item){
	if(!_.isEmpty(item)){
		$("#p_idx").val(item.patch_idx);
		$("#patch_version").val(item.patch_version);
		$("#patch_desc").val(item.patch_desc);
		$("#version_stamp").val(item.version_stamp);
		var patch_type = item.patch_type;
		$("#patch_type").val(patch_type).trigger("change");
// 		restrict_info
// 		:
// 		"[{"library_id": "1"},{"library_id": "0002"}]"
		var restrict_info = item.restrict_info;
		if(!_.isEmpty(restrict_info)){
			var list = jsonToObj(restrict_info);
			if(!_.isEmpty(list)){
				var html = "";
				for(var i=0;i<list.length;i++){
					var obj = list[i];
					if(patch_type=="2"){
						if(_.isEmpty(libIdAndNameObj[obj.library_id])) continue;
						html+="<li>";
						html+="	<div class=\"left1\">";
						html+="		<input class=\"g-input w50\" value=\""+obj.library_id+"\" name=\"libid\" placeholder=\"图书馆id\">";
						html+="	</div>";
						html+="	<div class=\"right1\">";
						html+="		<input class=\"g-input w50\" value=\""+libIdAndNameObj[obj.library_id].lib_name+"\" readonly=\"\" name=\"libname\" placeholder=\"图书馆名称\">";
						html+="	</div>";
						html+="	<div class=\"delete-li\"></div>";
						html+="</li>";
					}
					
					if(patch_type=="3"){
						var typeList = deviceTypeList;
						if(_.isEmpty(deviceTypeList)) break;
						var typeHtml = "";
						for(var j=0;j<typeList.length;j++){
							var typeObj = typeList[j];
							if(obj.device_type == typeObj.meta_devicetype_idx){
								typeHtml += "<option selected=\"selected\" value=\""+typeObj.meta_devicetype_idx+"\">"+typeObj.device_type_desc+"</option>"
							}else{
								typeHtml += "<option value=\""+typeObj.meta_devicetype_idx+"\">"+typeObj.device_type_desc+"</option>"
							}
						}
						html += "<li>";
						html += "	<div class=\"left\">";
						html += "	</div>";
						html += "	<div class=\"right\">";
						html += "		<div class=\"g-select dev\" >";
						html += "			<select id=\"\" >";
						html += typeHtml;
						html += "			</select>";
						html += "			<span class=\"arr-icon\"></span>";
						html += "		</div>";
						html += "	</div>";
						html += "	<div class=\"delete-li\"></div>";
						html += "</li>";
					}
				}
				if(patch_type=="2"){
					$("#addLib").after(html);
				}
				if(patch_type=="3"){
					$("#addDev").after(html);
				}
			}
		}
	}
}

<%-- 清空数据 --%>
function clearData(){
	$("li.error").removeClass("error");
	$("#patch_version").val("");
	$("#patch_desc").val("");
	$("#patch_type").val("1").trigger("change");
	$("#addLib").siblings().remove();
	$("#addDev").siblings().remove();
}
<%-- 删除事件 --%>
var delLayer = null;

<%-- 单条删除事件 --%>
var deleteIdx = "";
var deleteName = "";
$("tbody").on("click",".delete",function(){
	deleteIdx = $(this).attr("data-id");
	deleteName = $(this).attr("data-name");
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
		var desc = $(this).attr("data-name");
		obj.idx=idx;
		obj.desc=desc;
		deleteList.push(obj);
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
	$(".g-delete-dialog.single .form-btn.remove").removeClass("disabled");
	$(".g-delete-dialog.multi .form-btn.remove").removeClass("disabled");
	closeLayer(delLayer);
});

<%-- 取消按钮 --%>
$(".form-btn.cancel.g-btn-green").on("click",function(){
	closeLayer(multiLayer);
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
	$.ajax({
		url:"<%=basePath%>/upgrade/delUpgrade",
		type:"POST",
		data:{"json":deleteIdx}
	}).done(function(data){
		if(data!=null && data!=""){
			if(data.state && data.message=="success"){
				showMsg({
					timeout : 1000,
					showText : '删除成功',
					status : true
				});
			}else{
				var msg = "";
				if(data.message=="1"){
					msg = "删除失败,"+data.message;
				}
//					showMsg({
//						timeout : 3000,
//						showText : "删除失败,"+msg,
//						status : false
//					});
				layer.alert(msg);
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
		url:"<%=basePath%>/upgrade/delMultiUpgrade",
		type:"POST",
		data:{"json":JSON.stringify(deleteList)}
	}).done(function(data){
		if(data!=null && data!=""){
			if(data.state && data.message=="success"){
				if(!_.isEmpty(data.result.cannotDel)){
					closeLayer(delLayer);
					$(".g-delete-dialog.alert .word").html(""+data.result.cannotDel+"，无法删除！");
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
//					showMsg({
//						timeout : 3000,
//						showText : "删除失败,"+data.message,
//						status : false
//					});
				layer.alert(msg);
			}
		}
	}).always(function(){
		$delete.removeClass("disabled");
		refreshCurrent();
		closeLayer(delLayer);
	});
});

function checkData(){
	var flag = true;
	var patch_version = $("#patch_version").val();
	if(_.isEmpty(patch_version)){
		$("#patch_version").closest("li").find("span").html("请输入版本号");
		$("#patch_version").closest("li").addClass("error");
		flag = false;
	}else{
		$("#patch_version").closest("li").removeClass("error");
		var reg = /^[vV][0-9]+\.[0-9]+$/;
		if(!reg.test(patch_version)){
			$("#patch_version").closest("li").addClass("error");
			$("#patch_version").closest("li").find("span").html("格式不正确，如V1.1、v2.0");
			flag = false;
		}else{
			$("#patch_version").closest("li").removeClass("error");
		}
	}
	return flag;
}

//详细页面数据保存
var nowAjax=null;
$(".g-submit2").on("click",function(){
	if(!checkData()){
		return;
	}
	if($(this).hasClass("disabled")){
		return;
	}else{
		$(this).addClass("disabled");
	}
	var submitObj = {};
	var patch_idx = $("#p_idx").val();
	var patch_version = $("#patch_version").val();
	var patch_desc = $("#patch_desc").val();
	var patch_type = $("#patch_type").val();
	var restrict_info = "";
	var arr = [];
	if(patch_type=="2"){//按馆区分
		$("#addLib").siblings().each(function(){
			var obj = {};
			var library_id = $(this).find(".left1 input").val();
			if(!_.isEmpty(library_id)){
				if(!libIdAndNameObj[library_id]){
					return;//馆ID不正确的情况下 也要求提示出来。
				}
				obj.library_id = library_id;
				arr.push(obj);
			}
		});
		if(arr.length==0){
			$(this).removeClass("disabled");
			layer.alert("至少需要填写一个馆ID");
			return;		
		}
	}
	if(patch_type=="3"){//按设备类型
		$("#addDev").siblings().each(function(){
			var obj = {};
			var device_type = $(this).find(".right select").val();
			if(!_.isEmpty(device_type)){
				var hasType=false; 
				for(var j=0;j<arr.length;j++){
					if(device_type==arr[j].device_type){
						hasType=true;
						break;
					}
				}
				if(hasType){
					return;
				}
				//判断有没有重复的
				obj.device_type = device_type;
				arr.push(obj);
			}
		});
	}
	if(arr.length>0){
		restrict_info = JSON.stringify(arr);
	}
	submitObj.patch_idx = patch_idx;
	submitObj.patch_version = patch_version;
	submitObj.patch_desc = patch_desc;
	submitObj.patch_type = patch_type;
	submitObj.restrict_info = restrict_info;
	if(_.isEmpty(patch_idx) && flag=="increase"){
		updatePatchInfo("add",submitObj);		
	}else if(flag=="update"){
		var version_stamp = $("#version_stamp").val();
		submitObj.version_stamp=version_stamp;
		updatePatchInfo("update",submitObj);		
	}
	
});
<%-- 新增或者修改 --%>
function updatePatchInfo(type,obj){
	//检测

	<%-- 修改 --%>
	if(type=="update"){
		var flag = comparePatchInfo(obj,patchList[obj.patch_idx]);
		if(flag){
			showMsg({
				timeout : 1000,
				showText : '修改成功',
				status : true,
				callback:function(){
					$(".g-submit2").removeClass("disabled");
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
			$.ajax({
				url:"${pageContext.request.contextPath}/upgrade/UpdUpgrade",
				type:"POST",
				data:{"json":JSON.stringify(obj)},
				timeout:8000
			}).done(function(data){
				if(data!=null){
					if(data.state){
						showMsg({
							timeout : 1000,
							showText : '修改成功',
							status : true,
							callback:function(){
								$(".g-submit2").removeClass("disabled");
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
						$(".g-submit2").removeClass("disabled");
						var msg = data.message;
						if(msg!=null && msg.indexOf("optimistic")>=0){
							layer.alert("当前选择的数据不是最新数据,请修改之后再做编辑");
						}else{
							layer.alert(data.message);
						}
					}
				}
			});
		}
	}
	
	<%-- 新增 --%>
	if(type=="add"){
		$.ajax({
			url:"${pageContext.request.contextPath}/upgrade/AddUpgrade",
			type:"POST",
			data:{"json":JSON.stringify(obj)},
			timeout:8000
		}).done(function(data){
			if(data!=null){
				if(data.state){
					showMsg({
						timeout : 1000,
						showText : '新增成功',
						status : true,
						callback:function(){
							$(".g-submit2").removeClass("disabled");
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
					$(".g-submit2").removeClass("disabled");
					layer.alert(data.message);
				}
			}
			
		});
	}
}

<%-- 跟原来的数据比较 --%>
function comparePatchInfo(submitObj,patchObj){
	if(submitObj.patch_idx!=patchObj.patch_idx){
		return false;
	}
	if(submitObj.patch_version!=patchObj.patch_version){
		return false;
	}
	
	if(submitObj.patch_desc!=patchObj.patch_desc){
		return false;
	}
	
	if(submitObj.patch_type!=patchObj.patch_type){
		return false;
	}
	
	if(!_.isEqual(jsonToObj(submitObj.restrict_info),jsonToObj(patchObj.restrict_info))){
		return false;
	}
	
	return true;
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
	
	<%-- 查询所有用户组权限 --%>
	function queryAllServiceGroup(){
		var lib = {};
		lib.library_idx = "1";
		$.ajax({
			url:"${pageContext.request.contextPath}/operator/queryAllServiceGroup",
			type:"POST",
			data:{"req":lib}
		}).done(function(data){
			
		});
	}

	
	$("#addLib").on("click",function(){
		var html = "";
		html+="<li>";
		html+="	<div class=\"left1\">";
		html+="		<input class=\"g-input w50\" name=\"libid\" placeholder=\"图书馆id\">";
		html+="	</div>";
		html+="	<div class=\"right1\">";
		html+="		<input class=\"g-input w50\" readonly=\"\" name=\"libname\" placeholder=\"图书馆名称\">";
		html+="	</div>";
		html+="	<div class=\"delete-li\"></div>";
		html+="</li>";
		$(this).after(html);
	});
	
	$("#addLib .delete-li").on("click",function(){
		$(this).parent().remove();
	})
	
	
	$("#addDev").on("click",function(){
		var html = "";
		html += "<li>";
		html += "	<div class=\"left\">";
		html += "	</div>";
		html += "	<div class=\"right\">";
		html += "		<div class=\"g-select dev\" >";
		html += "			<select id=\"\" >";
		html += deviceTypeHtml;
		html += "			</select>";
		html += "			<span class=\"arr-icon\"></span>";
		html += "		</div>";
		html += "	</div>";
		html += "	<div class=\"delete-li\"></div>";
		html += "</li>";
		$(this).after(html);
	});
	
	$("#addDev .delete-li").on("click",function(){
		$(this).parent().remove();
	})
	
	<%-- 绑定事件到父节点 --%>
	$("#addLib").parent().on("change",".left1 input[name='libid']",function(){
		var libid = $(this).val();
		if(!_.isEmpty(libid)){
			var libname = libIdAndNameObj[libid];
			if(!_.isEmpty(libname)){
				$(this).parent().siblings(".right1").find("input").val(libname.lib_name);
			}else{
				$(this).parent().siblings(".right1").find("input").val("无此图书馆信息");
			}
		}else{
			$(this).parent().siblings(".right1").find("input").val("");
		}
	});


</script>




