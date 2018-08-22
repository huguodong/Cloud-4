<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<title>
<style type="text/css">
.search-template-md .col-4 .item10{
		width: 10%;
		float: left;
		vertical-align: middle;
		line-height: 36px;
}
.search-template-md .col-4 .item1{
		width: 100%;
		float: left;
		vertical-align: middle;
		line-height: 36px;
}
.search-template-md .col-6 .item10{
		width: 10%;
		float: left;
		vertical-align: middle;
		line-height: 36px;
}
.search-template-md .col-6 .item1{
		width: 100%;
		float: left;
		vertical-align: middle;
		line-height: 36px;
}

.table-form .w200{
	width: 200px !important;
}

.divBorder{
	border:1px solid #f6f6f6
} 
</style>
</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style_reader.css"> 
<head>
</head>
<body>
<input type="hidden" id="import_tpl_idx" value="${import_tpl_idx }">
<input type="hidden" id="copyRecord" value="${copyRecord }">
<input type="hidden" id="currentpage" value="${currentpage}">
<input type="hidden" id="endpage" value="${endpage}">
<input type="hidden" id="import_tpl_type" value="${import_tpl_type}">
<input type="hidden" id="library_id" value="${library_id}">
<input type="hidden" id="library_idx" value="${library_idx}">
<input type="hidden" id="operator_type" value="${operator_type}">
<div class="equipment-leixing">
	<div class="page-title-bar">
		<span class="title" id="title">新建导入配置模板</span>
		<div class="form-wrap fr">
			<div class="btn" id="saveTemplate">保存模板</div>
			<div class="btn cancel" id="templateCancel">取消</div>
			<!-- <div class="btn review">
				<a href="javascript:;">预览</a>
			</div> -->
		</div>
	</div>
	<div class="main">
		<div class="content-md">
				<table class="table-search" width="100%">
					<tr>
						<td width="70" class="sub-color"><span class="g-mustIn">模板名称</span></td>
						<td width="250"><input type="text" placeholder="请输入"
							class="g-input" id="templateName"/></td>
						<td width="70" class="sub-color"><span class="g-mustIn">所属图书馆</span></td>
						<td width="250"><select class="g-mustIn" id="lib_id"></td>
					</tr>
					<tr>
						<td class="sub-color"><span class="g-mustIn">模板ID</span></td>
						<td width="210"><input type="text" placeholder="请输入"
							class="g-input" id="templateID"/></td>
						<td width="70" class="sub-color"></td>
					</tr>
					<tr>
						<td class="sub-color"><span class="g-mustIn">数据源</span></td>
						<td>
							<div class="g-select inline-block">
								<select id="dataSource">
									
								</select> <span class="arr-icon"></span>
							</div>
						</td>
						<!-- <td class="sub-color">数据导出</td>
						<td>
							<div class="g-radio">
								<input type="radio" name="data-output" id="data_output" value="0">
							</div> <label for="data-output1" class="label-type">允许</label>
							<div class="g-radio">
								<input type="radio" name="data-output" id="data_output_2" value="1">
							</div> <label for="data-output2" class="label-type">禁用</label>
						</td> -->
						<!-- <td class="sub-color" width="70">导出格式</td>
						<td>
							<div class="g-checkbox">
								<input type="checkbox" name="" value="Txt" id="checkbox_format">
							</div> <label class="label-type" for="checkbox-format-1">Txt</label>

							<div class="g-checkbox">
								<input type="checkbox" name="" value="Excel" id="checkbox_format_2">
							</div> <label class="label-type" for="checkbox-format-2">Excel</label>

						</td> -->
					</tr>
				</table>
				<div class="panel-md mt20">
					<div class="panel-title">
						<div class="fr">
							<div class="add-field" id="add-field" >
								<a href="javascript:;"><strong>+</strong> 添加字段</a>
							</div>
						</div>
						<ul>
							<li class="active" data-field="add-field-search">查询条件模块</li>
						</ul>
					</div>
					<div class="panel-content">
						<div class="panel">
							<div class="normal-table normal-table-template">
								<table width="100%" cellpadding="0" cellspacing="0" id="searchTable">
									<thead>
										<tr>
											<th width="50">ID</th>
											<th width="100">列号</th>
											<th width="100">列名</th>
											<th>配置描述</th>
											<th width="150">操作</th>
										</tr>
									</thead>
									查询模块的列数插入
									<tbody>

									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div> 
			</div>
	</div>
	<%@include file ="../include/page_bar.jsf" %>
</div>
<div class="form-dialog-fix w-600 form-dialog-fix-search">
	<div class="shade"></div>
	<div class="form-dialog">
		<div class="title">
			导入配置字段编辑
			<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
			<input type="submit" placeholder="" class="g-submit2 g-btn-green save-dialog-search save-field" value="保存">
		</div>
		<div class="form-container">
		<div id="data-source-dom">
			<div class="table-form">
				<table width="100%">
					<tr>
						<th width="110">字段类型</th>
						<td colspan="3">
							<div class="g-select">
								<select class="g-select-field-multiple" id="cOptionType">
									<option value="text" selected="">单列字段</option>
					                <option value="multiple-text">多列字段</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</td>
						<th width="30">列数</th>
						<td colspan="3">
							<input type="text" name="" id="columnRank" class="g-input inline-block w100" placeholder="请选择列数" value=""/>
						</td>
					</tr>
				</table>
			</div>
			<div class="table-form mt10 mb10"></div> 
			<div class="table-form">
				<table width="100%">
					<tr>
						<th width="110">分隔符</th>
						<td colspan="3">
							<div class="g-select">
								<select class="g-reg-filter" id="dataFilter">
									<option value="">选择分割符</option>
									<option value="pound">#</option>
									<option value="verticalbar">|</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</td>
					</tr>
				</table>
			</div>
			</div>
			<!-- 字段在js/field.js -->
			<div class="table-form">
				<table width="100%">
					<tbody>
						<tr>
							<th width="110"><span class="g-mustIn">选择字段</span></th>
							<td>
								<div class="g-select">
									<select id="data_source_select">
									</select> <span class="arr-icon"></span>
								</div>
							</td>
						</tr>
						<!-- <tr>
							<td>设置排序</td>
							<td colspan="3"><input type="text" name="" id="cAttrSort" class="g-input" placeholder="请设置排序" /></td>
						</tr> -->
					</tbody>
				</table>
			</div>
			<div class="table-form">
				<span class="g-mustIn" width="100%" style="align-text:center">字段预览</span>
				<table cellspacing="0" cellpadding="0" style="border:0px;margin:0px;padding:0px"  id="showFile">
					<tbody>
						<tr id="viewTr">
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<div class="g-delete-dialog" id="delDialog">
	<span class="line"></span>
	<div class="word">
		是否要删除当前字段？
	</div>
	<div class="form-btn cancel g-btn-gray" id="delCancel">取消</div>
	<div class="form-btn remove g-btn-red" id="delSure">删除</div>
</div>
<!-- 添加字段js -->
<script src="${pageContext.request.contextPath }/page/readermgmt/js/field.js"></script>
<script src="${pageContext.request.contextPath }/page/readermgmt/js/public.js"></script> 
<!-- 对应的本地文件,必须放在最后，要不会出现页面拉长的情况 -->
<script src="${pageContext.request.contextPath }/page/readermgmt/js/import_template_config.js"></script>
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
	
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
	
	<%-- function getTempIdx(){
		var o = <shiro:principal/>;
		var data= {};
		data.library_idx = o.library_idx;
		$.ajax({
			url:"<%=basePath%>/library/selectLibinfoByParam",
			type:"POST",
			data:{"json":JSON.stringify(data)},
			success:function(data){
				console.info("data",data);
				tmpIdx = data.result.rows[0].lib_service_tpl_id;
				startTime = data.result.rows[0].service_start_date;
				endTime = data.result.rows[0].service_expire_date;
			}
		});
	}
	
	$(document).ready(function(){
		getTempIdx();
		//$("tbody").html("");
		var lib_idx = null;
		if(o.operator_type == 3){
			lib_idx = o.library_idx;
			$("div.btn-wrap").attr("style","display:none");
		}
		var size=$(".g-select.refresh").find("option:selected").text();
		var param = {"page":"1",
					 "pageSize":size,
					 "library_idx":lib_idx};
		$.ajax({
			url:basePath+"/library/selectLibinfoByParam",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				console.info("data--:",data);
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
						$("#mainShow").append(
						"<tr>"+
						"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.library_idx+"' /></div></td>"+
						"<td class=''>" + item.lib_id + "</td>"+
						"<td class='col2'>" + item.lib_name + "</td>"+
						"<td class='col3'>" + address + "</td>"+
						"<td class='col4'>" + phone + "</td>"+
						"<td class='col5'>" + item.service_start_date + "</td>"+
						"<td class='col6'>" + item.service_expire_date+ "</td>"+
						"<td class='col7'>" +(item.lib_service_tpl_id==null?'否':'是('+item.lib_service_tpl_id+')') + "</td>"+
						"<td class='col8'>"+
							"<span class='btn-a edit'>学生导入模板配置</span>"+
							"<span class='btn-a config g-btn-blue'>教工导入模板配置</span>"+
						"</td>"+
						"<td class='version_stamp hide'>" + item.version_stamp+ "</td>"+
						"</tr>");
					}else{
						$("#mainShow").append(
						"<tr>"+
						"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.library_idx+"' /></div></td>"+
						"<td class=''>" + item.lib_id + "</td>"+
						"<td class='col2'>" + item.lib_name + "</td>"+
						"<td class='col3'>" + address + "</td>"+
						"<td class='col4'>" + phone + "</td>"+
						"<td class='col5'>" + item.service_start_date + "</td>"+
						"<td class='col6'>" + item.service_expire_date+ "</td>"+
						"<td class='col7'>" +(item.lib_service_tpl_id==null?'否':'是('+"<span class='color'>"+item.lib_service_tpl_id+"</span>"+')') + "</td>"+
						"<td class='col8'>"+
							"<span class='btn-a edit'>学生导入模板配置</span>"+
							"<span class='btn-a config g-btn-blue'>教工导入模板配置</span>"+
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
	}); --%>
	
	$("tbody").on("click",".edit",function(){
		editRow=this;
		//GetDataToLayer(this);
		operationFlag="edit";
		layerOpen({
			"title":"编辑图书馆",
			"btnText":"保存",
			"btnColorClass":"g-btn-blue",
			"operType":"update"
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
		console.info("options:",options);
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
			area:["1000px",dialogH+400+"px"],

			offset :[dialogTop],
			content: $('.equipment-leixing'),
			success:function(layero,index){
				
			}
		});
	}
});
</script>
</body>
</html>