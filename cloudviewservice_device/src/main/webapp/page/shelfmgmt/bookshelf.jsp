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
<head>
<style>
.g-table .col8{
	max-width:50px;
}
</style>
</head>
<body>
<div class="equipment-fenzu">
	<div class="page-title-bar">
		<span class="title">书架馆理<span class="g-help"></span></span>
		<div class="form-wrap fr">
			<input type="text" name="" id="query" style="width:200px" class="input g-input" placeholder="书架ID或书架名">
			<div class="btn search" ><spring:message code="devicegroup.jsp.search"></spring:message></div>
			<div class="btn increase g-btn-green"><spring:message code="devicegroup.jsp.add"></spring:message></div>
			<div class="btn delete"><spring:message code="devicegroup.jsp.delete"></spring:message></div>
			<div class="btn  g-btn-green" id="upload_shelfimg">上传书架图</div>
			<div class="btn" id="upload_floorimg">上传楼层图</div>
			<div class="btn  g-btn-green" id="upload_point">导入描点</div>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
					<th class="col2">书架ID</th>
					<th class="col2">书架名称</th>
					<th class="col9">图书馆ID</th>
					<th class="col1">书架层数</th>
					<th class="col6">书架层排序</th>
					<th class="col8">所在楼层图</th>
					<th class="col8">书架示意图</th>
					<th class="col8">瞄点数据</th>
					<th class="col10" hidden>是否为智能书架</th>
					<th class="col11">操作</th>
				</tr>
			</thead>
			<tbody>
			
			
			</tbody>
		</table>
	</div>
	
	
	<%@include file ="../include/page_bar.jsf" %>	
</div>
<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		<spring:message code="devicegroup.jsp.curselect"/><span class="font-red"></span>书架<br>
		<spring:message code="devicegroup.jsp.isdelselectdev"/>
	</div>
	<div class="form-btn cancel g-btn-gray"><spring:message code="message.gloable.cancel"></spring:message></div>
	<div class="form-btn remove g-btn-red"><spring:message code="message.gloable.delete"></spring:message></div>
</div>
<div class="form-dialog" id="add">
	<div class="title">新增书架</div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">书架ID</span></div>
				<div class="right">
					<input type="text" name="" id="shelf_id" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<span class="error-msg">书架ID不能为空</span>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">书架名称</span></div>
				<div class="right">
					<input type="text" name="" id="shelf_name" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<span class="error-msg">书架名称不能为空</span>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">图书馆ID</span></div>
				<div class="right">
					<input type="text" name="" id="libid" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<span class="error-msg">图书馆ID不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn"><spring:message code="devicegroup.jsp.libname"/></span></div>
				<div class="right">
					<input type="text" name="" style="color:gray;background-color:#E0E0E0" readonly="readonly" id="libName" class="g-input" placeholder="<spring:message code="devicegroup.jsp.libname"/>"  />
				</div>
			</li>
			<li hidden><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">所属书架组</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="shelf_group_idx">
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">书架层数</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="layerCount">
							<option value="1" selected>1</option>
							<option value="2" selected>2</option>
							<option value="3" selected>3</option>
							<option value="4" selected>4</option>
							<option value="5" selected>5</option>
							<option value="6" selected>6</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">层架排序</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type"  id="layerSort">
							<option value="0" selected>从下到上递增</option>
							<option value="1" selected>从上到下递增</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span>楼层图路径</span></div>
				<div class="right">
					<input type="text" name="" id="floorImage_url" class="g-input" placeholder="<spring:message code="添加完成后请上传图片"/>"  readonly/>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span>书架图路径</span></div>
				<div class="right">
					<input type="text" name="" id="shelfImage_url" class="g-input" placeholder="<spring:message code="添加完成后请上传图片"/>"  readonly/>
				</div>
			</li>
			<li>
				<div class="left"><span >位置信息</span></div>
				<div class="right">
					<div class="g-1"><input type="text" name="" id="info_type_1" class="g-input-1"/><span>&nbsp馆</span></div>
					<div class="g-1"><input type="text" name="" id="info_type_2" class="g-input-1"/><span>&nbsp楼</span></div>
					<div class="g-1"><input type="text" name="" id="info_type_3" class="g-input-1"/><span>&nbsp区</span></div>
					<!-- <div class="g-1"><lable style="color:red">没有选项可以不填</lable></div> -->
				</div>
			</li>
			<li>
				<div class="left"><span >&nbsp</span></div>
				<div class="right">
					<div class="g-1"><input type="text" name="" id="info_type_4" class="g-input-1"/><span>&nbsp排</span></div>
					<div class="g-1"><input type="text" name="" id="info_type_5" class="g-input-1"/><span>&nbsp面</span></div>
					<div class="g-1"><input type="text" name="" id="info_type_6" class="g-input-1"/><span>&nbsp架</span></div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span>描点数据</span></div>
				<div class="right">
					<input type="hidden" name="" id="shelfPoint" class="g-input" readonly/>
					<span class="openImg"><img alt="" src="${pageContext.request.contextPath}/static/images/dt.jpg" width="30px" height="30px"></span>
				</div>
			</li>
			<li hidden><!--红色框体 li  class="error" -->
				<div class="left"><span>是否为智能书架</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="isSmartShelf">
							<option value="1" selected>是</option>
							<option value="0" selected>否</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input id="increaseBtn"  type="submit" value="<spring:message code="message.gloable.save"/>" class="submit g-btn-blue" />
				</div>
			</li>
		</ul>
	</div>
</div>
<div class="form-dialog" id="modify">
	<div class="title">编辑书架</div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">书架ID</span></div>
				<div class="right">
					<input type="text" name="" id="edit_shelf_id" class="g-input" style="color:gray;background-color:#E0E0E0" readonly="readonly" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<input type="hidden" name="" id="version_stamp" class="g-input"  />
					<span class="error-msg">书架ID不能为空</span>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">书架名称</span></div>
				<div class="right">
					<input type="text" name="" id="edit_shelf_name" class="g-input" style="color:gray;background-color:#E0E0E0" readonly="readonly" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<span class="error-msg">书架名称不能为空</span>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">图书馆ID</span></div>
				<div class="right">
					<input type="text" name="" id="edit_libid" class="g-input" style="color:gray;background-color:#E0E0E0" readonly="readonly" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<span class="error-msg">图书馆ID不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn"><spring:message code="devicegroup.jsp.libname"/></span></div>
				<div class="right">
					<input type="text" name="" style="color:gray;background-color:#E0E0E0" readonly="readonly" id="edit_libName" class="g-input" placeholder="<spring:message code="devicegroup.jsp.libname"/>"  />
				</div>
			</li>
			<li hidden><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">所属书架组</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="edit_shelf_group_idx">
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">书架层数</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="edit_layerCount">
							<option value="1" selected>1</option>
							<option value="2" selected>2</option>
							<option value="3" selected>3</option>
							<option value="4" selected>4</option>
							<option value="5" selected>5</option>
							<option value="6" selected>6</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">层架排序</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="edit_layerSort">
							<option value="0" selected>从下到上递增</option>
							<option value="1" selected>从上到下递增</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span>楼层图路径</span></div>
				<div class="right">
					<input type="text" name="" id="edit_floorImage_url" class="g-input" placeholder="添加完成后请上传图片"  readonly/>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span>书架图路径</span></div>
				<div class="right">
					<input type="text" name="" id="edit_shelfImage_url" class="g-input" placeholder="添加完成后请上传图片"  readonly/>
				</div>
			</li>
			<li>
				<div class="left"><span >位置信息</span></div>
				<div class="right">
					<div class="g-1"><input type="text" name="" id="edit_info_type_1" class="g-input-1"/><span>&nbsp馆</span></div>
					<div class="g-1"><input type="text" name="" id="edit_info_type_2" class="g-input-1"/><span>&nbsp楼</span></div>
					<div class="g-1"><input type="text" name="" id="edit_info_type_3" class="g-input-1"/><span>&nbsp区</span></div>
					<!-- <div class="g-1"><lable style="color:red">没有选项可以不填</lable></div> -->
				</div>
			</li>
			<li>
				<div class="left"><span >&nbsp</span></div>
				<div class="right">
				    <div class="g-1"><input type="text" name="" id="edit_info_type_4" class="g-input-1"/><span>&nbsp排</span></div>
					<div class="g-1"><input type="text" name="" id="edit_info_type_5" class="g-input-1"/><span>&nbsp面</span></div>
					<div class="g-1"><input type="text" name="" id="edit_info_type_6" class="g-input-1"/><span>&nbsp架</span></div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span>描点数据</span></div>
				<div class="right">
					<input type="hidden" name="" id="edit_shelfPoint" class="g-input" readonly/>
					<span class="openImg"><img alt="" src="${pageContext.request.contextPath}/static/images/dt.jpg" width="30px" height="30px" style="cursor:pointer"></span>
				</div>
			</li>
			<li hidden><!--红色框体 li  class="error" -->
				<div class="left"><span>是否为智能书架</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="edit_isSmartShelf">
							<option value="1" selected>是</option>
							<option value="0" selected>否</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input id="ineditBtn"  type="submit" value="<spring:message code="message.gloable.save"/>" class="submit g-btn-blue" />
				</div>
			</li>
		</ul>
	</div>
</div>

<div class="form-dialog" id="imgPointDiv">
	<div class="form-wrap">
		<%-- <img alt="" src="${pageContext.request.contextPath}/static/images/one.jpg" style="height: 100%;width: 100%;"> --%>
		<div style="border: 0px green solid;">
		<canvas id="imgCanvas" style="background: url('${pageContext.request.contextPath}/static/images/noFloorImage.png');background-size:100% 100%;-moz-background-size:100% 100%;" width="1600px" height="800px"></canvas>
		</div>
		<input type="text" name="" id="lab" class="g-input-1" value="X:" readonly/><input type="text" name="" id="lc_X" class="g-input-1"/>
		<input type="text" name="" id="lab" class="g-input-1" value="Y:" readonly/><input type="text" name="" id="lc_Y" class="g-input-1"/>
		<input type="hidden" name="" id="lc_OX" class="g-input-1"/>
		<input type="hidden" name="" id="lc_OY" class="g-input-1"/>
		<input type="hidden" name="" id="lc_points" class="g-input-1"/>
		
	</div>
	<div class="item" style="padding-bottom:30px;padding-top:20px;float:right;padding-right:500px;">
		<input type="button" id="restartPoint" value="重新描点"  class="g-submit g-btn-green"/>
		<input type="submit" name="save" id="savePoint" value="<spring:message code="message.gloable.save"/>" class="g-submit g-btn-blue">
	</div>
</div>

<div class="upload-dialog dialog1 form-dialog">
	<span class="line"></span>
	<div class="form-wrap">
<%-- <form id="uploadForm" action="${pageContext.request.contextPath}/database/bakUpload" enctype="multipart/form-data" method="post" target="nm_iframe"> 			
 --%>
 	请输入路径：	 		
 	<input type="file" name="file" id="file" accept="image/*,text/plain"/>
	<div id="layerCount_upload">
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">书架层数</div>
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">
			<div>
				<select id="layercount" style="width:100px">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
				</select>
				<span class="arr-icon"></span>
			</div>
		</div>
	</div>
	<div id="count_upload">
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">所在楼层:</div>
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">
			<div>
				<input type="text" class="g-input" style="width:100px;float:left" id="img-count_guan" >&nbsp&nbsp馆
			</div>
			<div>
				<input type="text" class="g-input" style="width:100px;float:left" id="img-count" >&nbsp&nbsp楼
			</div>
		</div>
		<span style="color:red;float:left">(图片规格：3840*2487)</span>
	</div>
	<div id="point_upload">
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">导入方式</div>
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">
			<div>
				<select id="point_type" style="width:100px">
						<option value="0" selected>请选择</option>
						<option value="1" >按书架导入</option>
						<option value="2" >按层架标导入</option>
				</select>
				<span class="arr-icon"></span>
			</div>
		</div>
	</div>
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn upload g-btn-green">上传</div>
</div>
<div class="load-dialog">
	<span class="line"></span>
	<div class="load-gif"></div>
	<div class="word">请稍等，正在还原文件···</div>
</div>
<script type="text/javascript">
$(function(){
	//i18n message
	var devicegroup_jsp_curselect='<spring:message code="devicegroup.jsp.curselect"/>';
	var devicegroup_jsp_curselectunit='devicegroup.jsp.curselectunit';
	var devicegroup_jsp_isdelselectdev='<spring:message code="devicegroup.jsp.isdelselectdev"/>';
	var devicegroup_jsp_isdeloneselectdev='<spring:message code="devicegroup.jsp.isdelselectdev"/>';
	var devicegroup_jsp_selectdeldev='<spring:message code="devicegroup.jsp.selectdeldev"/>';
	var devicegroup_jsp_hasfieldoutofLength='<spring:message code="devicegroup.jsp.selectdeldev"/>';
	var devicegroup_jsp_updatesuccess='<spring:message code="message.gloable.updatesuccess"/>';
	
	var message_gloable_delsuccess='<spring:message code="message.gloable.delsuccess"/>';
	var message_gloable_addsuccess='<spring:message code="message.gloable.addsuccess"/>';
	var message_gloable_edit='<spring:message code="message.gloable.edit"/>';
	var message_gloable_delete='<spring:message code="message.gloable.delete"/>';
	var shelf_id = "";
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
	var delLayer = null;
    var oper=<shiro:principal/>;
	//删除选中的多个设备分组
	var Delbookshelfs = new Array();
	$(".delete").on("click",function(){
		Delbookshelfs.length=0;
		var num = $("tbody Input[name='idx']:checked").length;
		//var lib_id = $(this).parent().parent().find(".col9").text();
		$("tbody input[name='idx']:checked").each(function() {  
			var bookshelf = {
					lib_id : $(this).parent().parent().parent().find(".col4").text(),
					shelf_id : $(this).attr("id")
			};
			Delbookshelfs.push(bookshelf);
        }); 
         if(num>0){
        	$(".word").html("");
        	$(".word").append(devicegroup_jsp_curselect+"<span class='font-red'>"+num+"</span>条数据<br>是否要删除选择的书架");
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
			content: $('.g-delete-dialog'), //捕获的元素
			cancel: function(index){
				layer.close(curLayer);
				this.content.hide();
			}
		});
        }else{
       	 	layer.alert(devicegroup_jsp_selectdeldev);
        }
	});
	
	//删除当前行的设备分组
	$("tbody").on("click",".delete",function(){
		Delbookshelfs.length = 0;
		var bookshelf = {
				lib_id : $(this).parent().parent().find(".col4").text(),
				shelf_id : $(this).parent().parent().find("div input[name='idx']").attr("id")
		};
		Delbookshelfs.push(bookshelf);
		$('.word').html("");
		$('.word').append("是否要删除选择的书架");
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
			content: $('.g-delete-dialog'), //捕获的元素
			cancel: function(index){
				layer.close(curLayer);
				this.content.hide();
			}
		});
	});
	
	/* //全选复选框操作
	$(":checkbox").on("click",function(){
		$(this).parents(".g-checkbox").toggleClass("on");
		if($(this).is(":checked")){
			$("tbody input[type='checkbox']").each(function() {
    			if (!$(this).is(":checked")) {
      				$(this).click();
    			}
  			});
		}else
		{
			$("tbody input[type='checkbox']").each(function() {
    			if($(this).is(":checked")){
				$(this).click();
			}
  			});
		}	
  	}); */

	var addLayer=null;
	/**
		新增按钮操作
	**/
	$(".increase").on("click",function(){
		isEditflag = false;
		clearAllData();
		if(oper.operator_type>=3){
			$('#add').find("#libid").prop("readonly","readonly").prop("style","color:gray;background-color:#E0E0E0");
			$('#add').find("#libid").val(libIdxAndNameObj[oper.library_idx].lib_id);
			$('#add').find("#libName").val(libIdxAndNameObj[oper.library_idx].lib_name);
			var lib_id = libIdxAndNameObj[oper.library_idx].lib_id;
			var Json = {"lib_id":lib_id}
			var Page = {"page":"1",
					"pageSize":"999"};
			var param = {json : Json, page : Page}
			$.ajax({
				 url:basePath+"/shelfGroup/queryAllShelfGroup",
			 	 type:"POST",
			 	 data:{"req":JSON.stringify(param)},
			     success:function(data){
					 if(data.state){
						 $("#shelf_group_idx").empty();
						 $("#shelf_group_idx").append(
									"<option value='' selected>请选择</option>"
							);	
						 $.each(data.result.rows,function(i,item){
								$("#shelf_group_idx").append(
										"<option value='"+item.shelf_group_id+"'>"+item.shelf_group_id+"</option>"
								);	
							}); 
		        	}else if(data.retMessage){
		        		showRetMessage(data.retMessage);
		        	}
	      	}
			});
		}else{
			$("#shelf_group_idx").append(
					"<option value='' selected>请选择</option>"
			);
		}
			
		addLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["600px"],
			offset :["130px"],
			content: $('#add'), //捕获的元素
			cancel: function(index){
				layer.close(delLayer);
				this.content.hide();
			}
		});
	});
	var editLayer=null;
	var isEditflag = false;
	/**
		编辑按钮操作
	**/
	var group = new Array();
	$("tbody").on("click",".edit",function(){
		isEditflag = true;
		group.length = 0;
		group.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		
		var shelf_id = $(this).parent().parent().find("div input[name='idx']").attr("id");
		var lib_id = $(this).parent().parent().find(".col4").text();
		var version_stamp = $(this).parent().parent().find(".version_stamp").text();
		
		var Json = {"lib_id":lib_id}
		var Page = {"page":"1",
				"pageSize":"999"};
		var param = {json : Json, page : Page}
		$.ajax({
			 url:basePath+"/shelfGroup/queryAllShelfGroup",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
				 if(data.state){
					 $("#edit_shelf_group_idx").empty();
					 $("#edit_shelf_group_idx").append(
								"<option value='' selected>请选择</option>"
						);	
					 $.each(data.result.rows,function(i,item){
							$("#edit_shelf_group_idx").append(
									"<option value='"+item.shelf_group_id+"'>"+item.shelf_group_id+"</option>"
							);	
						}); 
					 param = {
								"shelf_id" : shelf_id,
								"lib_id" : lib_id,
						}
						$.ajax({
							 url:basePath+"/bookshelf/queryBookshelfById",
						 	 type:"POST",
						 	 data:{"req":JSON.stringify(param)},
						     success:function(data){
								 if(data.state){
									 $("#edit_shelf_id").val(data.result.shelf_id);
									 $("#edit_shelf_name").val(data.result.shelf_name);
									 $("#edit_libid").val(data.result.lib_id);
									 $("#edit_layerCount").val(data.result.layerCount);
									 $("#edit_layerSort").val(data.result.layerSort);
									 $("#edit_floorImage_url").val(data.result.floorImage_url);
									 $("#edit_shelfImage_url").val(data.result.shelfImage_url);
									 $("#edit_shelfPoint").val(data.result.shelfPoint);
									 $("#edit_isSmartShelf").val(data.result.isSmartShelf);
									 $("#edit_shelf_group_idx").val(data.result.shelf_group_id);
									 $("#version_stamp").val(version_stamp);
									 if(libIdAndNameObj[data.result.lib_id]){
										$("#edit_libName").val(libIdAndNameObj[data.result.lib_id].lib_name);
									 }else{
										$("#edit_libName").val("");
									}
									 param = {
												"shelf_id" : data.result.shelf_id,
												"lib_id":data.result.lib_id
										}
										$.ajax({
											 url:basePath+"/bookshelfinfo/queryBookshelfinfoById",
										 	 type:"POST",
										 	 data:{"req":JSON.stringify(param)},
										     success:function(data){
										    	 console.log(data);
												 if(data.state){
													 $.each(data.result,function(i,item){
															$("#edit_info_type_"+item.info_type).val(item.info_value);
														}); 
									        	}else if(data.retMessage){
									        		showRetMessage(data.retMessage);
									        	}
								       	}
										});
					        	}else if(data.retMessage){
					        		showRetMessage(data.retMessage);
					        	}
				       	}
						});
	        	}else if(data.retMessage){
	        		showRetMessage(data.retMessage);
	        	}
      	}
		});
		
		editLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["600px"],
			offset :["130px"],
			content: $('#modify'), //捕获的元素
			cancel: function(index){
				layer.close(delLayer);
				this.content.hide();
			}
		});
	});
	
	function setBookshelfinfo(lib_id,shelf_id,info_type_1,info_type_2,info_type_3,info_type_4,info_type_5,info_type_6){
		var bookshelfinfo=new Array();
		if(info_type_1!=""||info_type_1!=null){
			var bookshelfinfo1 = {
					lib_id : lib_id,
					shelf_id : shelf_id,
					info_type : 1,
					info_value : info_type_1
			};
			bookshelfinfo.push(bookshelfinfo1);
		}
		if(info_type_2!=""||info_type_2!=null){
			var bookshelfinfo2 = {
					lib_id : lib_id,
					shelf_id : shelf_id,
					info_type : 2,
					info_value : info_type_2
			};
			bookshelfinfo.push(bookshelfinfo2);
		}
		if(info_type_3!=""||info_type_3!=null){
			var bookshelfinfo3 = {
					lib_id : lib_id,
					shelf_id : shelf_id,
					info_type : 3,
					info_value : info_type_3
			};
			bookshelfinfo.push(bookshelfinfo3);
		}
		if(info_type_4!=""||info_type_4!=null){
			var bookshelfinfo4 = {
					lib_id : lib_id,
					shelf_id : shelf_id,
					info_type : 4,
					info_value : info_type_4
			};
			bookshelfinfo.push(bookshelfinfo4);
		}
		if(info_type_5!=""||info_type_5!=null){
			var bookshelfinfo5 = {
					lib_id : lib_id,
					shelf_id : shelf_id,
					info_type : 5,
					info_value : info_type_5
			};
			bookshelfinfo.push(bookshelfinfo5);
		}
		if(info_type_6!=""||info_type_6!=null){
			var bookshelfinfo6 = {
					lib_id : lib_id,
					shelf_id : shelf_id,
					info_type : 6,
					info_value : info_type_6
			};
			bookshelfinfo.push(bookshelfinfo6);
		}
		return bookshelfinfo;
	}
	
	//编辑操作
	$("#ineditBtn").on("click",function(){
		GlobalGLoading();//loading界面
		checkInc(true);
		if(!ineditCheck){
			return;
		}
		var lib_id=$("#edit_libid").val();
		var shelf_id=$("#edit_shelf_id").val();
		var shelf_name=$("#edit_shelf_name").val();
		var layerCount=$("#edit_layerCount").val();
		var layerSort =$("#edit_layerSort").val();
		var floorImage_url=$("#edit_floorImage_url").val();
		var shelfImage_url=$("#edit_shelfImage_url").val();
		var shelfPoint=$("#edit_shelfPoint").val();
		var isSmartShelf=$("#edit_isSmartShelf").val();
		var shelf_group_idx=$("#edit_shelf_group_idx").val();
		var library_idx = libIdAndNameObj[lib_id].library_idx;
		var info_type_1 = $("#edit_info_type_1").val();
		var info_type_2 = $("#edit_info_type_2").val();
		var info_type_3 = $("#edit_info_type_3").val();
		var info_type_4 = $("#edit_info_type_4").val();
		var info_type_5 = $("#edit_info_type_5").val();
		var info_type_6 = $("#edit_info_type_6").val();
		var version_stamp = $("#version_stamp").val();
		var bookshelfinfo = setBookshelfinfo(lib_id,shelf_id,info_type_1,info_type_2,info_type_3,info_type_4,info_type_5,info_type_6);
		if(lib_id.length>20 || shelf_name.length>100 || lib_id.length>20){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		if(lib_id.length>20 || shelf_name.length>100 || lib_id.length>20){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		param = {
				json1 : {
					"shelf_id" : shelf_id,
					"lib_id":lib_id,
					"shelf_id":shelf_id,
					"shelf_name":shelf_name,
					"layerCount":layerCount,
					"layerSort":layerSort,
					"shelf_group_id":shelf_group_idx,
					"floorImage_url":floorImage_url,
					"shelfImage_url":shelfImage_url,
					"shelfPoint":shelfPoint,
					"isSmartShelf" :isSmartShelf,
					"version_stamp" :version_stamp
				},
				json2 : bookshelfinfo
		}
		$.ajax({
			url:basePath+"/bookshelf/updateBookshelf",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				if(data.state){
					 var oper = <shiro:principal/>;
					var library_idx=null;
					var lib_id = null;
					if(oper.operator_type>=3){
						library_idx=oper.library_idx;
						lib_id = libIdxAndNameObj[library_idx].lib_id;
					}
					
					var shelf_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size=$(".g-select.refresh").find("option:selected").text();
					var Json = {"lib_id":lib_id,"shelf_id":shelf_id}
					var Page={"page":currentpage,"pageSize":size};
					if(editLayer){
						layer.close(editLayer);
					}
					GlobalShowMsg({
						showText:devicegroup_jsp_updatesuccess,
						status:true
					});
					$.select(Json,Page);
				}else if(data.retMessage){
					var msg = data.retMessage;
					if(msg.indexOf("optimistic")>=0){
						showRetMessage("当前选择的数据不是最新数据,请修改之后再做编辑");
					}else{
						showRetMessage(data.retMessage);
					}
				}
			}
		});	
	});
	
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	/**
		删除按钮操作
	**/
	$(".form-btn.remove").on("click",function(){
		GlobalGLoading();
		if(!Delbookshelfs) return;
		$.ajax({
			url:basePath+"/bookshelf/deleteBookshelf",
			type:"POST",
			data:{"req":JSON.stringify(Delbookshelfs)
			},
			success:function(data){
				if(data.state){
				 	var oper = <shiro:principal/>;
				 	var library_idx=null;
					var lib_id = null;
					if(oper.operator_type>=3){
						library_idx=oper.library_idx;
						lib_id = libIdxAndNameObj[library_idx].lib_id;
					}
					var shelf_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size=$(".g-select.refresh").find("option:selected").text();
					var Json = {"lib_id":lib_id,"shelf_id":shelf_id}
					var Page={"page":currentpage,"pageSize":size};
					GlobalShowMsg({
						showText:message_gloable_delsuccess,
						status:true
					});
					$.select(Json,Page);
				}else if(data.retMessage&&data.result){
					showRetMessage(data.retMessage+"。"+data.result);
				}else if(data.retMessage){//单个删除失败
					showRetMessage(data.retMessage);
				}else if(data.result){
					showRetMessage(data.result);
				}
				if (delLayer) {
					layer.close(delLayer);
				}
			}
			
		});
		
	});

	$(".g-increase-dialog .form-wrap  .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	});
	
	$(":text").focus(function(){
		$(this).parent().parent().removeClass("error");
	});
	//新增操作检查
	var increaseCheck=false;
	var checkIncData=function(incCheck){
		if(incCheck){
			var shelf_id=$("#shelf_id").val();
			var shelf_name=$("#shelf_name").val();
			var lib_id=$("#libid").val();
			var info_type_1 = $("#info_type_1").val();
			var info_type_2 = $("#info_type_2").val();
			var info_type_3 = $("#info_type_3").val();
			var info_type_4 = $("#info_type_4").val();
			var info_type_5 = $("#info_type_5").val();
			var info_type_6 = $("#info_type_6").val();
			if(shelf_id==""){
				layer.tips('书架ID不能为空', '#shelf_id', {tips: [2, '#ff6666']});
				return;
			}
			if(shelf_name == ""){
				layer.tips('书架名称不能为空', '#shelf_name', {tips: [2, '#ff6666']});
				return;
			}
			if(lib_id == ""){
				layer.tips('图书馆ID不能为空', '#shelf_name', {tips: [2, '#ff6666']});
				return;
			}
			
			if(info_type_1==""||info_type_2==""||info_type_3==""||info_type_4==""||info_type_5==""||info_type_6==""){
				if(info_type_1==""){
					layer.tips('数据不能为空', '#info_type_1', {tips: [2, '#ff6666']});
					return;
				}
				if(info_type_2==""){
					layer.tips('数据不能为空', '#info_type_2', {tips: [2, '#ff6666']});
					return;
				}
				if(info_type_3==""){
					layer.tips('数据不能为空', '#info_type_3', {tips: [2, '#ff6666']});
					return;
				}
				if(info_type_3==""){
					layer.tips('数据不能为空', '#info_type_3', {tips: [2, '#ff6666']});
					return;
				}
				if(info_type_5==""){
					layer.tips('数据不能为空', '#info_type_5', {tips: [2, '#ff6666']});
					return;
				}
				if(info_type_6==""){
					layer.tips('数据不能为空', '#info_type_6', {tips: [2, '#ff6666']});
					return;
				}
				
			}
		}
		increaseCheck=true;
	};
	var ineditCheck = false;
	//编辑操作检查
	var checkInc=function(incCheck){
		if(incCheck){
			var edit_shelf_id=$("#edit_shelf_id").val();
			var edit_shelf_name=$("#edit_shelf_name").val();
			var edit_info_type_1 = $("#edit_info_type_1").val();
			var edit_info_type_2 = $("#edit_info_type_2").val();
			var edit_info_type_3 = $("#edit_info_type_3").val();
			var edit_info_type_4 = $("#edit_info_type_4").val();
			var edit_info_type_5 = $("#edit_info_type_5").val();
			var edit_info_type_6 = $("#edit_info_type_6").val();
			
			if(!edit_shelf_id){
				layer.tips('书架ID不能为空', '#edit_shelf_id', {tips: [2, '#ff6666']});
				return;
			}
			if(!edit_shelf_name){
				layer.tips('书架名称不能为空', '#edit_shelf_name', {tips: [2, '#ff6666']});
				return;
			}
			if(edit_info_type_1==""||edit_info_type_2==""||edit_info_type_3==""||edit_info_type_4==""||edit_info_type_5==""||edit_info_type_6==""){
				if(edit_info_type_1==""){
					layer.tips('数据不能为空', '#edit_info_type_1', {tips: [2, '#ff6666']});
					return;
				}
				if(edit_info_type_2==""){
					layer.tips('数据不能为空', '#edit_info_type_2', {tips: [2, '#ff6666']});
					return;
				}
				if(edit_info_type_3==""){
					layer.tips('数据不能为空', '#edit_info_type_3', {tips: [2, '#ff6666']});
					return;
				}
				if(edit_info_type_4==""){
					layer.tips('数据不能为空', '#edit_info_type_4', {tips: [2, '#ff6666']});
					return;
				}
				if(edit_info_type_5==""){
					layer.tips('数据不能为空', '#edit_info_type_5', {tips: [2, '#ff6666']});
					return;
				}
				if(edit_info_type_6==""){
					layer.tips('数据不能为空', '#edit_info_type_6', {tips: [2, '#ff6666']});
					return;
				}
				
			}
			
		}
		ineditCheck=true;
	};
	function clearAllData()
	{
		$("#libid").val("");
		$("#shelf_id").val("");
		$("#shelf_name").val("");
		$("#layerCount").val(6);
		$("#layerSort").val(1);
		$("#floorImage_url").val("");
		$("#shelfImage_url").val("");
		$("#shelfPoint").val("");
		$("#isSmartShelf").val("");
		$("#shelf_group_idx").val("");
		$("#info_type_1").val("");
		$("#info_type_2").val("");
		$("#info_type_3").val("");
		$("#info_type_4").val("");
		$("#info_type_5").val("");
		$("#info_type_6").val("");
	}
	/**
		新增保存按钮
	**/
	$("#increaseBtn").on("click",function(){
		GlobalGLoading();
		checkIncData(true);
		if(!increaseCheck){
			return;
		}
		var lib_id=$("#libid").val();
		var shelf_id=$("#shelf_id").val();
		var shelf_name=$("#shelf_name").val();
		var layerCount=$("#layerCount").val();
		var layerSort =$("#layerSort").val();
		var floorImage_url=$("#floorImage_url").val();
		var shelfImage_url=$("#shelfImage_url").val();
		var shelfPoint=$("#shelfPoint").val();
		var isSmartShelf=$("#isSmartShelf").val();
		var shelf_group_idx=$("#shelf_group_idx").val();
		var info_type_1 = $("#info_type_1").val();
		var info_type_2 = $("#info_type_2").val();
		var info_type_3 = $("#info_type_3").val();
		var info_type_4 = $("#info_type_4").val();
		var info_type_5 = $("#info_type_5").val();
		var info_type_6 = $("#info_type_6").val();
		var bookshelfinfo = setBookshelfinfo(lib_id,shelf_id,info_type_1,info_type_2,info_type_3,info_type_4,info_type_5,info_type_6);
		var library_idx = libIdAndNameObj[lib_id].library_idx;
		if(lib_id.length>20 || shelf_name.length>100 || lib_id.length>20){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		param = {
				json1 : {
					"lib_id":lib_id,
					"shelf_id":shelf_id,
					"shelf_name":shelf_name,
					"layerCount":layerCount,
					"layerSort":layerSort,
					"floorImage_url":floorImage_url,
					"shelfImage_url":shelfImage_url,
					"shelf_group_id":shelf_group_idx,
					"shelfPoint":shelfPoint,
					"isSmartShelf" :isSmartShelf
				},
				json2 : bookshelfinfo
		}
		if(shelf_id==""||shelf_name=="" ||!lib_id) return;
		$.ajax({
			 url:basePath+"/bookshelf/addBookshelf",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
				 if(data.state){
				 	 var oper = <shiro:principal/>;
				 	var library_idx=null;
					var lib_id = null;
					if(oper.operator_type>=3){
						library_idx=oper.library_idx;
						lib_id = libIdxAndNameObj[library_idx].lib_id;
					}
					var shelf_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size=$(".g-select.refresh").find("option:selected").text();
					var Json = {"lib_id":lib_id,"shelf_id":shelf_id}
					var Page={"page":currentpage,"pageSize":size};
					GlobalShowMsg({
						showText:message_gloable_addsuccess,
						status:true
					}); 
					$.select(Json,Page);
					if(addLayer){
						layer.close(addLayer);
					}
					 clearVal();
	        	}else if(data.message=="CHECK_FALSE"){
	        	 	layer.alert(data.retMessage);
	        	 }
        	}
		});
	});
	
	function clearVal(){
		$("#libid").val("");
		$("#shelf_id").val("");
		$("#shelf_name").val("");
		$("#layerCount").val(6);
		$("#layerSort").val(1);
		$("#floorImage_url").val("");
		$("#shelfImage_url").val("");
		$("#shelfPoint").val("");
		$("#isSmartShelf").val(0);
	}
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var oper = <shiro:principal/>;
		var library_idx=null;
		var lib_id = null;
		if(oper.operator_type>=3){
			library_idx=oper.library_idx;
			lib_id = libIdxAndNameObj[library_idx].lib_id;
		}
		var shelf_id=$("#query").val();
		var size=$(".g-select.refresh").find("option:selected").text();
		var Json = {"shelf_id":shelf_id,
				"lib_id":lib_id};
		var Page={	"page":"1",
					"pageSize":size}; 
		$.select(Json,Page);
	});
	//查询ajax封装
	jQuery.select=function (Json,Page){
		library_idx=oper.library_idx;
		//获取当前页数
			var size=$(".g-select.refresh").find("option:selected").text();
		
			var param={	json: Json , page : Page};
			$.ajax({
			url:basePath+"/bookshelf/queryAllBookshelf",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					$("tbody").append(
					"<tr>"+
	            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.shelf_id+"' /></div></td>"+
						"<td class='col2'>"+item.shelf_id+"</td>"+
						"<td class='col3'>"+item.shelf_name+"</td>"+
						"<td class='col4'>"+item.lib_id+"</td>"+
						"<td class='col5'>"+item.layerCount+"</td>"+
						"<td class='col6'>"+(item.layerSort!=1?"从下到上递增":"从上到下递增")+"</td>"+
						"<td class='col7'>"+(item.floorImage_url == null || item.floorImage_url == ""?"无":item.floorImage_url)+"</td>"+
						"<td class='col8'>"+(item.shelfImage_url == null || item.shelfImage_url == ""?"无":item.shelfImage_url)+"</td>"+
						"<td class='col9'>"+(item.shelfPoint== null || item.shelfPoint== "" ?"无":"已瞄点")+"</td>"+
						"<td class='col10' hidden>"+(item.isSmartShelf==1?"是":"否")+"</td>"+
	                    "<td class='col11'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
	                    "<span class='btn-a delete'>"+message_gloable_delete+"</span></td>" +
	                    "<td class='version_stamp hide'>"+item.version_stamp+"</td>"+
	                	"</tr>");	
				}); 
	    		$.pagination(data.result);       
				}
			});
	};
	
	 //上一页操作
	$("#page").on("click",".prev-page",function(){
		var oper = <shiro:principal/>;
		var library_idx=null;
		var lib_id = null;
		if(oper.operator_type>=3){
			library_idx=oper.library_idx;
			lib_id = libIdxAndNameObj[library_idx].lib_id;
		}
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size=$(".g-select.refresh").find("option:selected").text();
		var shelf_id=$("#query").val();
		var Json = {"shelf_id":shelf_id,
				"lib_id":lib_id};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var oper = <shiro:principal/>;
		var library_idx=null;
		var lib_id = null;
		if(oper.operator_type>=3){
			library_idx=oper.library_idx;
			lib_id = libIdxAndNameObj[library_idx].lib_id;
		}
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size=$(".g-select.refresh").find("option:selected").text();
		var shelf_id=$("#query").val();
		var Json = {"shelf_id":shelf_id,
				"lib_id":lib_id};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var page = $(this).html();
		if(page=="...") return;
		var size=$(".g-select.refresh").find("option:selected").text();
		var oper = <shiro:principal/>;
		var library_idx=null;
		var lib_id = null;
		if(oper.operator_type>=3){
			library_idx=oper.library_idx;
			lib_id = libIdxAndNameObj[library_idx].lib_id;
		}
		var shelf_id=$("#query").val();
		var Json = {"shelf_id":shelf_id,
				"lib_id":lib_id};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
		
	});
	
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var size=$(".g-select.refresh").find("option:selected").text();
		var oper = <shiro:principal/>;
		var library_idx=null;
		var lib_id = null;
		if(oper.operator_type>=3){
			library_idx=oper.library_idx;
			lib_id = libIdxAndNameObj[library_idx].lib_id;
		}
		var shelf_id=$("#query").val();
		var Json = {"shelf_id":shelf_id,
				"lib_id":lib_id};
		var Page={	"page":"1",
				"pageSize":size,}; 
		$.select(Json,Page);
	}); 
		
	var bakUpload={};
	var floorcount = false;
	var pointselect = false;
	bakUpload.isClicked=false;
	bakUpload.uploadDialog;
	$("#upload_shelfimg").on("click",function(){
		$("#layerCount_upload").show();
		$("#count_upload").hide();
		$("#point_upload").hide();
		bakUpload.uploadDialog=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.upload-dialog'), //捕获的元素
			success : function(index,layero){
				var file = $("#file") ;//清空file
				file.after(file.clone().val(""));      
				file.remove();
				$(".cancel").on("click",function(){
					if(bakUpload.uploadDialog){
						layer.close(bakUpload.uploadDialog);
					}
				});  
			},
			end:function(){
				$(".cancel").unbind("click");
				$(".backup-btn").unbind("click");
			}
		});
	});
	$("#upload_floorimg").on("click",function(){
		floorcount = true;
		$("#layerCount_upload").hide();
		$("#count_upload").show();
		$("#point_upload").hide();
		
		bakUpload.uploadDialog=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.upload-dialog'), //捕获的元素
			success : function(index,layero){
				var file = $("#file") ;//清空file
				file.after(file.clone().val(""));      
				file.remove();
				$(".cancel").on("click",function(){
					if(bakUpload.uploadDialog){
						layer.close(bakUpload.uploadDialog);
					}
				});  
			},
			end:function(){
				$(".cancel").unbind("click");
				$(".backup-btn").unbind("click");
			}
		});
	});
	
	$("#upload_point").on("click",function(){
		pointselect = true;
		$("#layerCount_upload").hide();
		$("#count_upload").hide();
		$("#point_upload").show();
		
		bakUpload.uploadDialog=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.upload-dialog'), //捕获的元素
			success : function(index,layero){
				var file = $("#file") ;//清空file
				file.after(file.clone().val(""));      
				file.remove();
				$(".cancel").on("click",function(){
					if(bakUpload.uploadDialog){
						floorcount =false;
						pointselect = false;
						layer.close(bakUpload.uploadDialog);
					}
				});  
			},
			end:function(){
				$(".cancel").unbind("click");
				$(".backup-btn").unbind("click");
			}
		});
	});
	//确认上传
	$("div.upload-dialog").on("click",".form-btn.upload",function(){
		
		   var oper = <shiro:principal/>;
		   var library_idx=null;
		   var lib_id = null;
		   if(oper.operator_type>=3){
			   library_idx=oper.library_idx;
			   lib_id = libIdxAndNameObj[library_idx].lib_id;
		   }else{
			   showRetMessage("请使用图书馆管理员账号进行操作！");
			   return;
		   }
		   
		   if(bakUpload.isClicked){return;}
		   bakUpload.isClicked=true;
		   if(floorcount && $("#img-count").val()==""){
			   showRetMessage("请填写所在楼层数");
			   bakUpload.isClicked=false;
		   	   return;
		   }
		   if(pointselect && $("#point_type").val() == "0"){
			   showRetMessage("请选择导入方式");
			   bakUpload.isClicked=false;
		   	   return;
		   }
		   var fileName = getPath( document.getElementById("file"));
		   if(!$("#file").val()){
		   		layer.alert("请选择文件后上传");
		   		bakUpload.isClicked=false;
		   		return;
		   }
		   var dialogIndex = loadingDialog({
				loadText:"请稍等，上传中..."
		   });
		   
		   if(!floorcount&&pointselect){
			   $.ajaxFileUpload({
				   	url:basePath+"/common/uploadPoint",
				   	secureuri :false,
					 fileElementId :'file',//file控件id
					 data: { libId: lib_id},
					 type:'post',
					 dataType:'json',
					 success : function (data,status){
						if(data){
					    	var reg=new RegExp("<[^>]*>","gi");
					     	var dateTxt=data.replace(reg,"").replace(reg,"");
					     	console.log(dateTxt);
					     	var obj=JSON.parse(dateTxt);
					     	if(obj.state){
							     layer.close(dialogIndex);
							     bakUpload.isClicked=false;
								 layer.close(bakUpload.uploadDialog);
								 pointselect = false;
								 var Json = {
										 "libId":lib_id,
										 "filePath":obj.result,
										 "point_type" : $("#point_type").val()};
								 $.ajax({
										url:basePath+"/bookshelf/uploadPoint",
										type:"POST",
										data:{"req":JSON.stringify(Json),},
										success:function(data){
											if(data.state){
											     layer.close(dialogIndex);
											     bakUpload.isClicked=false;
												 layer.close(bakUpload.uploadDialog);	
												var currentpage = $(".paging-bar li.active").text();
												var size=$(".g-select.refresh").find("option:selected").text();
												var shelf_id=$("#query").val();
												var Json = {"shelf_id":shelf_id,"lib_id":lib_id}
												var Page={"page":currentpage,"pageSize":size};
												$.select(Json,Page);
											    GlobalShowMsg({showText:"上传成功",status:true});
											}
											
										},
										 error: function(data, status, e){
										      console.log(data,status,e);
										       bakUpload.isClicked=false;
										       layer.close(bakUpload.uploadDialog); 	
										 }
								 });
					     	}
					     	else{
								showRetMessage("上传失败");
							}
						     
					  	}
						
					},
					 error: function(data, status, e){
					      console.log(data,status,e);
					       bakUpload.isClicked=false;
					       layer.close(bakUpload.uploadDialog); 	
					 } 
				}); 
		   }else{
			   $.ajaxFileUpload({
				   	url:basePath+"/common/uploadImg",
				   	secureuri :false,
					 fileElementId :'file',//file控件id
					 data: { libId: lib_id},
					 type:'post',
					 dataType:'json',
					 success : function (data,status){
						if(data){
					    	var reg=new RegExp("<[^>]*>","gi");
					     	var dateTxt=data.replace(reg,"").replace(reg,"");
					     	console.log(dateTxt);
					     	var obj=JSON.parse(dateTxt);
					     	if(obj.state){
							     layer.close(dialogIndex);
							     bakUpload.isClicked=false;
								 layer.close(bakUpload.uploadDialog);
								 if(floorcount&&!pointselect){
									 floorcount = false;
									 var Json = {"info_1":$("#img-count_guan").val(),
												 "info_2":$("#img-count").val(),
												"lib_id":lib_id,
												"floorImage_url":obj.result};
									 $.ajax({
											url:basePath+"/bookshelf/updateFloorimage",
											type:"POST",
											data:{"req":JSON.stringify(Json),},
											success:function(data){
												if(data.state){
												     layer.close(dialogIndex);
												     bakUpload.isClicked=false;
													 layer.close(bakUpload.uploadDialog);	
													var currentpage = $(".paging-bar li.active").text();
													var size=$(".g-select.refresh").find("option:selected").text();
													var shelf_id=$("#query").val();
													var Json = {"lib_id":lib_id,"shelf_id":shelf_id}
													var Page={"page":currentpage,"pageSize":size};
													$.select(Json,Page);
												    GlobalShowMsg({showText:"上传成功",status:true});
												}
												
											},
											 error: function(data, status, e){
											      console.log(data,status,e);
											       bakUpload.isClicked=false;
											       layer.close(bakUpload.uploadDialog); 	
											 }
									 });
								 }
								 else{
									 var Json = {"layerCount":$("#layercount").val(),
												"lib_id":lib_id,
												"shelfImage_url":obj.result};
									 $.ajax({
											url:basePath+"/bookshelf/updateShelfimage",
											type:"POST",
											data:{"req":JSON.stringify(Json),},
											success:function(data){
												if(data.state){
												     layer.close(dialogIndex);
												     bakUpload.isClicked=false;
													 layer.close(bakUpload.uploadDialog);	
													var currentpage = $(".paging-bar li.active").text();
													var size=$(".g-select.refresh").find("option:selected").text();
													var shelf_id=$("#query").val();
													var Json = {"shelf_id":shelf_id,"lib_id":lib_id}
													var Page={"page":currentpage,"pageSize":size};
													$.select(Json,Page);
												    GlobalShowMsg({showText:"上传成功",status:true});
												}
												
											},
											 error: function(data, status, e){
											      console.log(data,status,e);
											       bakUpload.isClicked=false;
											       layer.close(bakUpload.uploadDialog); 	
											 }
									 });
								 }
								
					     	}
					     	else{
								showRetMessage("上传失败");
							}
						     
					  	}
						
					},
					 error: function(data, status, e){
					      console.log(data,status,e);
					       bakUpload.isClicked=false;
					       layer.close(bakUpload.uploadDialog); 	
					 } 
				}); 
		   }
	});
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
	var openImgDiv = "";
	var openImgDivPro = false;
	var lib_id=$("#edit_libid").val();
	$(".openImg").on("click",function(){
		var floor_img = "";
		if(isEditflag){
			floor_img = $('#edit_floorImage_url').val();
		}else{
			floor_img = $('#floorImage_url').val();
		}
		if(floor_img.length == 0){
			layer.alert("请先上传楼层图片！");
			return;
		}
		//$("#imgCanvas").css("background-image", 'url("${pageContext.request.contextPath}/static/images/' + floor_img + '")')
		$("#imgCanvas").css("background-image", 'url(/temp/'+lib_id+'/'+floor_img+')')
		openImgDiv=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area :["1610px","950px"],
			offset :["10px"],
			content: $('#imgPointDiv'), //捕获的元素
			cancel: function(index){
				layer.close(openImgDiv);
				this.content.hide();
				openImgDivPro = false;
			}
		});
		
		openImgDivPro = true;
		ctx.clearRect(0,0,1600,800);
		$('#lc_X').val("");
        $('#lc_Y').val("");
        $('#lc_OX').val("");
        $('#lc_OY').val("");
        $('#lc_points').val("");
		
		var per = 1; //百分比
		var pots = "";
		if(isEditflag){
			pots = $('#edit_shelfPoint').val();
		}else{
			pots = $('#shelfPoint').val();
		}
		var arr = new Array();
		var parr = new Array();
		if(pots.length >0 ){
			arr = pots.split('-');
			 for(var i=0;i<arr.length;i++){
				 parr = arr[i].split(',');
				 var xxxx = parr[0].substring(1,parr[0].length);
				 var yyyy = parr[1].substring(0,parr[1].length-1);
				 console.log("yuan"+xxxx+","+yyyy)
				 var xxxx_int = parseInt(parseInt(xxxx)*per);
				 var yyyy_int = parseInt(parseInt(yyyy)*per);
				 console.log(xxxx_int+","+yyyy_int)
				 if(i==0){
					 ctx.beginPath();
			         ctx.fillStyle="#FF0000";
			         ctx.arc(xxxx_int,yyyy_int,5,0,Math.PI*2,true);
			         ctx.closePath();
			         ctx.fill();
				 }else{
					 parr = arr[i-1].split(',');
					 var xxxx_o = parr[0].substring(1,parr[0].length);
					 var yyyy_o = parr[1].substring(0,parr[1].length-1);
					 var xxxx_o_int = parseInt(parseInt(xxxx_o)*per);
					 var yyyy_o_int = parseInt(parseInt(yyyy_o)*per);
					 ctx.beginPath();
			         ctx.strokeStyle = 'red';
			         ctx.lineWidth = 2;
			         ctx.lineCap = 'round';
			         ctx.moveTo(xxxx_o_int, yyyy_o_int);
			         ctx.lineTo(xxxx_int, yyyy_int);
			         ctx.stroke();
				 }
				 if(i==arr.length-1){
					 $('#lc_OX').val(xxxx);
				     $('#lc_OY').val(yyyy);
				 }
			}
			$('#lc_points').val(pots);
		}
	});
	
	$("#imgCanvas").mousemove(function(e){
        var widthh=e.originalEvent.x-$(this).offset().left||e.originalEvent.layerX-$(this).offset().left||0;//
        var heightt=e.originalEvent.y-$(this).offset().top||e.originalEvent.layerY-$(this).offset().top||0;//
        if(parseInt(widthh)>0 && parseInt(heightt)){
        	$('#lc_X').val(widthh);
            $('#lc_Y').val(heightt);
        }else{
        	$('#lc_X').val("");
            $('#lc_Y').val("");
        }
    }).mouseout(function(){
    	$('#lc_X').val("");
        $('#lc_Y').val("");
    })
	
    var canvas = document.getElementById('imgCanvas');
	var ctx=canvas.getContext("2d");
	ctx.width = 1600;
	ctx.height = 800;
		
	$("#imgCanvas").on("click",function(){
		
        var o_xx = $('#lc_OX').val();
        var o_yy = $('#lc_OY').val();
        var xx = $('#lc_X').val();
        var yy = $('#lc_Y').val();
        if(xx&&yy){
        	$('#lc_OX').val(xx);
            $('#lc_OY').val(yy);
        }else{
        	return;
        }
        var per = 1; //百分比
        var ac_x = parseInt(parseInt(xx)*per);
    	var ac_y = parseInt(parseInt(yy)*per);
    	var ac_ox = parseInt(parseInt(o_xx)*per);
    	var ac_oy = parseInt(parseInt(o_yy)*per);
    	var p_ds = $('#lc_points').val();
        if(o_xx&&o_yy){
        	ctx.beginPath();
        	ctx.strokeStyle = 'red';
            ctx.lineWidth = 2;
            ctx.lineCap = 'round';
        	ctx.moveTo(ac_ox, ac_oy);
        	ctx.lineTo(ac_x, ac_y);
        	ctx.stroke();
            p_ds = p_ds+"-("+xx+","+yy+")"
        }else{
        	ctx.beginPath();
        	ctx.fillStyle="#FF0000";
        	ctx.arc(ac_x,ac_y,5,0,Math.PI*2,true);
        	ctx.closePath();
        	ctx.fill();
        	ctx.font="10px sans-serif";
        	ctx.strokeText('入口',ac_x-12, ac_y+20);
        	p_ds = p_ds+"("+xx+","+yy+")"
        }
        $('#lc_points').val(p_ds);
	});
	
	
	$("#restartPoint").on("click",function(){
		ctx.clearRect(0,0,1600,800);
		$('#lc_X').val("");
        $('#lc_Y').val("");
        $('#lc_OX').val("");
        $('#lc_OY').val("");
        $('#lc_points').val("");
	});
	
	$("#savePoint").on("click",function(){
		var sdfs = $('#lc_points').val();
		if(isEditflag){
			$('#edit_shelfPoint').val(sdfs);
		}else{
			$('#shelfPoint').val(sdfs);
		}
		if(openImgDiv){
			layer.close(openImgDiv);
		}
		openImgDivPro = false;
	});
	
	function locationLeft(element){
        offsetTotal = element.offsetLeft;
        scrollTotal = 0; //element.scrollLeft but we dont want to deal with scrolling - already in page coords
        if (element.tagName != "BODY"){
            if (element.offsetParent != null)
                return offsetTotal+scrollTotal+locationLeft(element.offsetParent);
        }
        return offsetTotal+scrollTotal;
    }
    //find the screen location of an element
    function locationTop(element){
        offsetTotal = element.offsetTop;
        scrollTotal = 0; //element.scrollTop but we dont want to deal with scrolling - already in page coords
        if (element.tagName != "BODY"){
            if (element.offsetParent != null)
                return offsetTotal+scrollTotal+locationTop(element.offsetParent);
        }
        return offsetTotal+scrollTotal;
    }
    
    $(document).keyup(function(e){
    	if(openImgDivPro == true){
        	if(e.which == 82) {
        		$("#restartPoint").trigger("click");//触发button的click事件
        	}
        	if(e.which == 83) {
        		$("#savePoint").trigger("click");//触发button的click事件
        	}
    	}
   	});
	
	  	/**
		 *	对应用户的图书馆数据
		 **/
		var libIdAndNameObj={};
		var libIdxAndNameObj={};
		(function GetlibIdxAndNameOBJ(){
			if(!typeof libIdAndNameObj=="undefined"&&!typeof libIdxAndNameObj =="undefined"){
				return;
			}
		    $.ajax({
				url:"${pageContext.request.contextPath}/ascconfig/querylibInfoByCurUser",
				type:"GET",
				data:{}
			}).then(function(data){
				if(data){
					//console.log("获取到图书馆信息",data);		
					if(data.result){
						for(var i=0;i<data.result.length;i++){
							libIdAndNameObj[data.result[i].lib_id]={"lib_name":data.result[i].lib_name,"library_idx":data.result[i].library_idx};
							libIdxAndNameObj[data.result[i].library_idx]={"lib_id":data.result[i].lib_id,"lib_name":data.result[i].lib_name};
						}
					}
					var oper = <shiro:principal/>;
					var library_idx=null;
					var lib_id = null;
					if(oper.operator_type>=3){
						library_idx=oper.library_idx;
						lib_id = libIdxAndNameObj[library_idx].lib_id;
					}
					//获取当前页数
						//var size=$(".g-select.refresh").find("option:selected").text();
					
						var size=$(".g-select.refresh").find("option:selected").text();
						
						var Page = {"page":"1",
								"pageSize":size};
						var Json = {"lib_id":lib_id}
						var param={	json: Json , page : Page};
						$.ajax({
						url:basePath+"/bookshelf/queryAllBookshelf",
						type:"POST",
						data:{"req":JSON.stringify(param)},
						success:function(data){
							$("tbody").html("");
							console.log(data)
							$.each(data.result.rows,function(i,item){
								$("tbody").append(
								"<tr>"+
								"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.shelf_id+"' /></div></td>"+
								"<td class='col2'>"+item.shelf_id+"</td>"+
								"<td class='col3'>"+item.shelf_name+"</td>"+
								"<td class='col4'>"+item.lib_id+"</td>"+
								"<td class='col5'>"+item.layerCount+"</td>"+
								"<td class='col6'>"+(item.layerSort!=1?"从下到上递增":"从上到下递增")+"</td>"+
								"<td class='col7'>"+(item.floorImage_url == null || item.floorImage_url == ""?"无":item.floorImage_url)+"</td>"+
								"<td class='col8'>"+(item.shelfImage_url == null || item.shelfImage_url == ""?"无":item.shelfImage_url)+"</td>"+
								"<td class='col9'>"+(item.shelfPoint== null || item.shelfPoint== "" ?"无":"已瞄点")+"</td>"+
								"<td class='col10' hidden>"+(item.isSmartShelf==1?"是":"否")+"</td>"+
			                    "<td class='col11'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
			                    "<span class='btn-a delete'>"+message_gloable_delete+"</span></td>" +
			                    "<td class='version_stamp hide'>"+item.version_stamp+"</td>"+
				                	"</tr>");	
							}); 
				    		$.pagination(data.result);    
				    		console.log(data.result);
							}
						});
				}
			});
		})();
		
		/**
		 *	监听图书馆ID变化 改变对应的图书馆名
		 **/
		$("#libid").on("keyup",function(){
			var lib_id=$(this).val();
			if(lib_id&&libIdAndNameObj[lib_id]){
				$("#libName").val(libIdAndNameObj[lib_id].lib_name);
			}else{
				$("#libName").val("");
			}
			
			var Json = {"lib_id":lib_id}
			var Page = {"page":"1",
					"pageSize":"999"};
			var param = {json : Json, page : Page}
			$.ajax({
				 url:basePath+"/shelfGroup/queryAllShelfGroup",
			 	 type:"POST",
			 	 data:{"req":JSON.stringify(param)},
			     success:function(data){
					 if(data.state){
						 $("#shelf_group_idx").empty();
						 $("#shelf_group_idx").append(
									"<option value='' selected>请选择</option>"
							);	
						 $.each(data.result.rows,function(i,item){
								$("#shelf_group_idx").append(
										"<option value='"+item.shelf_group_id+"'>"+item.shelf_group_id+"</option>"
								);	
							}); 
		        	}else if(data.retMessage){
		        		showRetMessage(data.retMessage);
		        	}
	      	}
			});
		});
});
</script>
</body>
</html>