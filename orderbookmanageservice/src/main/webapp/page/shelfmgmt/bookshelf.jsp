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
	
</head>
<style>
.g-1{
	height: 26px;
    line-height: 26px;
	width: 300px;
}
.g-input-1{
	display: block;
	padding-left: 6px;
	border-radius: 2px;
	width: 50px;
    height: 26px;
    line-height: 26px;
	color:#333;
	background-color:#f6f6f6;
	border:1px solid #DDD;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
	float : left;
	
}
#lab{
	background-color:white;
	border: 0px;
}
</style>
<body>
<div class="equipment-fenzu">
	<div class="page-title-bar">
		<span class="title">书架管理<span class="g-help"></span></span>
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
					<th class="col3">书架名称</th>
					<th class="col4">书架层数</th>
					<th class="col5">书架层排序</th>
					<th class="col6">所在楼层图</th>
					<th class="col7">书架示意图</th>
					<th class="col8">描点数据</th>
					<th class="col9">书架类型</th>
					<th class="col10">操作</th>
				</tr>
			</thead>
			<tbody>
			
			
			</tbody>
		</table>
	</div>
	
	<div class="paging-bar">
		<div class="left">
			<span class="t fl"><spring:message code="page_bar.jsf.select"></spring:message></span>
			<div class="btn g-chooseAll"><spring:message code="page_bar.jsf.checkall"></spring:message></div>
			<div class="btn g-noChooseAll"><spring:message code="page_bar.jsf.checkallinvert"></spring:message></div>
			<span class="t2 fl"><spring:message code="page_bar.jsf.show"></spring:message></span>
			<div class="g-select refresh">
				<select id="pagesize">
					<option value="" selected>10</option>
					<option value="">30</option>
					<option value="">60</option>
				</select>
				<span class="arr-icon"></span>
			</div>
			<span class="t2 fl " id="ChooseNum"><spring:message code="page_bar.jsf.alreadyselected"></spring:message><span class="total-choosen-num">0</span><spring:message code="page_bar.jsf.selectunit"></spring:message></span>
		</div>
		<div class="right" id="page">

		</div>
		<span class="total-page fr"></span>
	</div>
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
	<div class="title"><spring:message code="devicegroup.jsp.addnewgroup"/></div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">书架ID</span></div>
				<div class="right">
					<input type="text" name="" id="shelf_id" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">书架名称</span></div>
				<div class="right">
					<input type="text" name="" id="shelf_name" class="g-input" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
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
						<select class="libtemp-type" id="layercount">
							<option value="1" >1</option>
							<option value="2" >2</option>
							<option value="3" >3</option>
							<option value="4" >4</option>
							<option value="5" >5</option>
							<option value="6" >6</option>
							<option value="7" selected>7</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">层架排序</span></div>
				<div class="right">
					<div class="g-select" id="layersort">
						<select class="libtemp-type">
							<option value="0" >从下到上递增</option>
							<option value="1" selected>从上到下递增</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li  style="display: none"><!--红色框体 li  class="error" -->
				<div class="left"><span>楼层图路径</span></div>
				<div class="right">
					<input type="text" name="" id="floorimage_url" class="g-input" placeholder="如:images\XXX.png"  />
				</div>
			</li>
			<li style="display: none"><!--红色框体 li  class="error" -->
				<div class="left"><span>书架图路径</span></div>
				<div class="right">
					<input type="text" name="" id="shelfimage_url" class="g-input" placeholder="如:images\XXX.png"  />
				</div>
			</li>
			<li style="display: none"><!--红色框体 li  class="error" -->
				<div class="left"><span>描点数据</span></div>
				<div class="right">
					<input type="text" name="" id="shelfpoint" class="g-input" />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span>书架类型</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="issmartshelf">
							<option value="0" selected>普通书架</option>
							<option value="1" >智能书架</option>
							<option value="2" >SSL书架</option>
							<option value="3" >取书柜</option>
							<option value="4" >密集书架</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">位置信息</span></div>
				<div class="right">
					<div class="g-1">
						<input type="text" name="" id="info_type_1" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="馆" readonly/>
						<input type="text" name="" id="info_type_2" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="区" readonly/>
						<input type="text" name="" id="info_type_3" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="列" readonly/>
					</div>
					<div class="g-1">
						<input type="text" name="" id="info_type_4" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="列名" readonly/>
						<input type="text" name="" id="info_type_5" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="左/右" readonly/>
						<input type="text" name="" id="info_type_6" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="架" readonly/>
					</div>
					<div class="g-1">
						<input type="text" name="" id="lab" class="g-input-1" value="（1代表左，0代表右）" readonly style="width:200px;font-size: 8px;color:gray;"/>
					</div>
				</div>
			</li>
			<li></li>
		</ul>
	</div>
	<div class="item" style="padding-bottom:30px;padding-top:0px;float:right;padding-right:250px;">
		<input type="submit" name="save" id="increaseBtn" value="<spring:message code="message.gloable.save"/>" class="g-submit g-btn-blue">
	</div>
</div>
<div class="form-dialog" id="modify">
	<div class="title"><spring:message code="devicegroup.jsp.editgroup"/></div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">书架ID</span></div>
				<div class="right">
					<input type="text" name="" id="edit_shelf_id" class="g-input" style="color:gray;background-color:#E0E0E0" readonly="readonly" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
					<input type="hidden" name="" id="version_stamp" class="g-input"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">书架名称</span></div>
				<div class="right">
					<input type="text" name="" id="edit_shelf_name" class="g-input" style="color:gray;background-color:#E0E0E0" readonly="readonly" placeholder="<spring:message code="message.gloable.pleaseinput"/>"  />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
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
						<select class="libtemp-type" id="edit_layercount">
							<option value="1" >1</option>
							<option value="2" >2</option>
							<option value="3" >3</option>
							<option value="4" >4</option>
							<option value="5" >5</option>
							<option value="6" >6</option>
							<option value="7" selected>7</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span class="g-mustIn">层架排序</span></div>
				<div class="right">
					<div class="g-select" id="edit_layersort">
						<select class="libtemp-type">
							<option value="0" >从下到上递增</option>
							<option value="1" selected>从上到下递增</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li style="display: none"><!--红色框体 li  class="error" -->
				<div class="left"><span>楼层图路径</span></div>
				<div class="right">
					<input type="text" name="" id="edit_floorimage_url" class="g-input" placeholder="如:images\XXX.png"  />
				</div>
			</li>
			<li style="display: none"><!--红色框体 li  class="error" -->
				<div class="left"><span>书架图路径</span></div>
				<div class="right">
					<input type="text" name="" id="edit_shelfimage_url" class="g-input" placeholder="如:images\XXX.png"  />
				</div>
			</li>
			<li style="display: none"><!--红色框体 li  class="error" -->
				<div class="left"><span>描点数据</span></div>
				<div class="right">
					<input type="text" name="" id="edit_shelfpoint" class="g-input" />
				</div>
			</li>
			<li><!--红色框体 li  class="error" -->
				<div class="left"><span>书架类型</span></div>
				<div class="right">
					<div class="g-select">
						<select class="libtemp-type" id="edit_issmartshelf">
							<option value="0" selected>普通书架</option>
							<option value="1" >智能书架</option>
							<option value="2" >SSL书架</option>
							<option value="3" >取书柜</option>
							<option value="4" >密集书架</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">位置信息</span></div>
				<div class="right">
					<div class="g-1">
						<input type="text" name="" id="edit_info_type_1" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="馆" readonly/>
						<input type="text" name="" id="edit_info_type_2" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="区" readonly/>
						<input type="text" name="" id="edit_info_type_3" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="列" readonly/>
					</div>
					<div class="g-1">
						<input type="text" name="" id="edit_info_type_4" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="列名" readonly/>
						<input type="text" name="" id="edit_info_type_5" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="左/右" readonly/>
						<input type="text" name="" id="edit_info_type_6" class="g-input-1"/><input type="text" name="" id="lab" class="g-input-1" value="架" readonly/>
					</div>
					<div class="g-1">
						<input type="text" name="" id="lab" class="g-input-1" value="（1代表左，0代表右）" readonly style="width:200px;font-size: 8px;color:gray;"/>
					</div>
				</div>
			</li>
			<li></li>
		</ul>
	</div>
	<div class="item" style="padding-bottom:30px;padding-top:0px;float:right;padding-right:250px;">
		<input type="submit" name="save" id="ineditBtn" value="<spring:message code="message.gloable.save"/>" class="g-submit g-btn-blue">
	</div>
</div>

<div class="upload-dialog dialog1 form-dialog">
	<span class="line"></span>
	<div class="form-wrap">
 	请输入路径：	 		
 	<input type="file" name="file" id="file" accept="image/*,text/plain"/>
	<div id="layercount_upload">
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">书架层数</div>
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">
			<div>
				<select id="img-layercount" style="width:100px;border-color: #DDD">
						<option value="1" selected>1</option>
						<option value="2" >2</option>
						<option value="3" >3</option>
						<option value="4" >4</option>
						<option value="5" >5</option>
						<option value="6" >6</option>
				</select>
				<span class="arr-icon"></span>
			</div>
		</div>
	</div>
	<div id="count_upload">
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">所在楼层:</div>
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">
			<div>
				<input type="text" class="g-input" style="width:100px;float:left" id="img-count" >&nbsp&nbsp楼
			</div>
		</div>
	</div>
	<div id="point_upload">
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">导入方式</div>
		<div style="left:20px;float:left;top:80px;padding-top:20px;padding-left:20px">
			<div>
				<select id="point_type" style="width:100px;border-color: #DDD">
						<option value="0" selected>请选择</option>
						<option value="1" >按书架导入</option>
						<option value="2" >按层架标导入</option>
				</select>
				<span class="arr-icon"></span>
			</div>
		</div>
	</div>
	</div>
	<div class="item" style="padding-bottom:30px;padding-top: 20px;float:left;">
		<div class="form-btn upload g-btn-green">上传</div>
		<div class="form-btn cancel g-btn-gray">取消</div>
	</div>
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
	var bookshelf_idx = new Array();
	$(".delete").on("click",function(){
		bookshelf_idx.length=0;
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			bookshelf_idx.push($(this).attr("id"));
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
		bookshelf_idx.length = 0;
		bookshelf_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
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

	var addLayer=null;
	/**
		新增按钮操作
	**/
	$(".increase").on("click",function(){
		var Json = {"lib_id":''}
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
					 $.each(data.result.rows,function(i,item){
							$("#shelf_group_idx").append(
									"<option value='"+item.shelf_group_idx+"' selected>"+item.shelf_group_id+"</option>"
							);	
						}); 
	        	}else if(data.retMessage){
	        		showRetMessage(data.retMessage);
	        	}
      	}
		});
		addLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area :["800px","360px"],
			offset :["130px"],
			content: $('#add'), //捕获的元素
			cancel: function(index){
				layer.close(delLayer);
				this.content.hide();
			}
		});
	});
	var editLayer=null;
	/**
		编辑按钮操作
	**/
	var group = new Array();
	var shelf_idx ='';
	$("tbody").on("click",".edit",function(){
		group.length = 0;
		group.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		var version_stamp = $(this).parent().parent().find(".version_stamp").text();
		 $("#version_stamp").val(version_stamp);
		var Json = {"lib_id":''}
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
					 $.each(data.result.rows,function(i,item){
							$("#edit_shelf_group_idx").append(
									"<option value='"+item.shelf_group_idx+"' selected>"+item.shelf_group_id+"</option>"
							);	
						}); 
					 
						 param = {
									"shelf_idx" : group[0]
							};
							$.ajax({
								 url:basePath+"/bookshelf/queryBookshelfById",
							 	 type:"POST",
							 	 data:{"req":JSON.stringify(param)},
							     success:function(data){
									 if(data.state){
										 shelf_idx = data.result.shelf_idx;
										 $("#edit_shelf_id").val(data.result.shelf_id);
										 $("#edit_shelf_name").val(data.result.shelf_name);
										 $("#edit_layercount").val(data.result.layercount);
										 $("#edit_layersort").val(data.result.layersort);
										 $("#edit_floorimage_url").val(data.result.floorimage_url);
										 $("#edit_shelfimage_url").val(data.result.shelfimage_url);
										 $("#edit_shelfpoint").val(data.result.shelfpoint);
										 $("#edit_issmartshelf").val(data.result.issmartshelf);
										 $("#edit_shelf_group_idx").val(data.result.shelf_group_idx);
										 param = {
													"shelf_id" : data.result.shelf_id,
													"lib_id":''
											}
											$.ajax({
												 url:basePath+"/bookshelfinfo/queryBookshelfinfoById",
											 	 type:"POST",
											 	 data:{"req":JSON.stringify(param)},
											     success:function(data){
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
			area :["800px","360px"],
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
			}
			bookshelfinfo.push(bookshelfinfo1);
		}
		if(info_type_2!=""||info_type_2!=null){
			var bookshelfinfo2 = {
					lib_id : lib_id,
					shelf_id : shelf_id,
					info_type : 2,
					info_value : info_type_2
			}
			bookshelfinfo.push(bookshelfinfo2);
		}
		if(info_type_3!=""||info_type_3!=null){
			var bookshelfinfo3 = {
					lib_id : lib_id,
					shelf_id : shelf_id,
					info_type : 3,
					info_value : info_type_3
			}
			bookshelfinfo.push(bookshelfinfo3);
		}
		if(info_type_4!=""||info_type_4!=null){
			var bookshelfinfo4 = {
					lib_id : lib_id,
					shelf_id : shelf_id,
					info_type : 4,
					info_value : info_type_4
			}
			bookshelfinfo.push(bookshelfinfo4);
		}
		if(info_type_5!=""||info_type_5!=null){
			var bookshelfinfo5 = {
					lib_id : lib_id,
					shelf_id : shelf_id,
					info_type : 5,
					info_value : info_type_5
			}
			bookshelfinfo.push(bookshelfinfo5);
		}
		if(info_type_6!=""||info_type_6!=null){
			var bookshelfinfo6 = {
					lib_id : lib_id,
					shelf_id : shelf_id,
					info_type : 6,
					info_value : info_type_6
			}
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
		var shelf_id=$("#edit_shelf_id").val();
		var shelf_name=$("#edit_shelf_name").val();
		var layercount=$("#edit_layercount").val();
		var layersort =$("#edit_layersort").val();
		var floorimage_url=$("#edit_floorimage_url").val();
		var shelfimage_url=$("#edit_shelfimage_url").val();
		var shelfpoint=$("#edit_shelfpoint").val();
		var issmartshelf=$("#edit_issmartshelf").val();
		var shelf_group_idx=$("#edit_shelf_group_idx").val();
		var info_type_1 = $("#edit_info_type_1").val();
		var info_type_2 = $("#edit_info_type_2").val();
		var info_type_3 = $("#edit_info_type_3").val();
		var info_type_4 = $("#edit_info_type_4").val();
		var info_type_5 = $("#edit_info_type_5").val();
		var info_type_6 = $("#edit_info_type_6").val();
		var version_stamp = $("#version_stamp").val();
		var bookshelfinfo = setBookshelfinfo('',shelf_id,info_type_1,info_type_2,info_type_3,info_type_4,info_type_5,info_type_6);
		if(shelf_name.length>100){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		param = {
				json1 : {
					"shelf_idx" : shelf_idx,
					"lib_id":'',
					"shelf_id":shelf_id,
					"shelf_name":shelf_name,
					"layercount":layercount,
					"layersort":layersort,
					"floorimage_url":floorimage_url,
					"shelfimage_url":shelfimage_url,
					"shelfpoint":shelfpoint,
					"issmartshelf" :issmartshelf,
					"version_stamp" :version_stamp
				},
				json2 : {
					"shelf_idx" : shelf_idx,
					"shelf_group_idx" : shelf_group_idx,
					"library_idx" : ''
				},
				json3 : bookshelfinfo
		}
		$.ajax({
			url:basePath+"/bookshelf/updateBookshelf",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				if(data.state){
					var shelf_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size= $('#pagesize option:selected').text();
					var Json = {"lib_id":'',"shelf_id":shelf_id}
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
		var idx = bookshelf_idx;
		if(!idx) return;
		var param=new Array();
		for(var i=0;i<idx.length;i++){
			param[i]={
				"shelf_idx":idx[i],
			};
		}
		$.ajax({
			url:basePath+"/bookshelf/deleteBookshelf",
			type:"POST",
			data:{"req":JSON.stringify(param)
			},
			success:function(data){
				if(data.state){
					var currentpage = $(".paging-bar li.active").html();
					var size= $('#pagesize option:selected').text();
					var Json = {"lib_id":'',"shelf_id":shelf_id}
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
		var parten=/^\+?[1-9][0-9]*$/;  
		if(incCheck){
			var shelf_id=$("#shelf_id").val();
			var shelf_name=$("#shelf_name").val();
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
			if(info_type_1 != "" && !parten.test(info_type_1)){
				layer.tips('只能输入数字', '#info_type_1', {tips: [2, '#ff6666']});
				return;
			}
			if(info_type_2 != "" && !parten.test(info_type_2)){
				layer.tips('只能输入数字', '#info_type_2', {tips: [2, '#ff6666']});
				return;
			}
			if(info_type_3 != "" && !parten.test(info_type_3)){
				layer.tips('只能输入数字', '#info_type_3', {tips: [2, '#ff6666']});
				return;
			}
			if(info_type_4 != "" && !parten.test(info_type_4)){
				layer.tips('只能输入数字', '#info_type_4', {tips: [2, '#ff6666']});
				return;
			}
			if(info_type_5 != "" && !parten.test(info_type_5)){
				layer.tips('只能输入数字', '#info_type_5', {tips: [2, '#ff6666']});
				return;
			}
			if(info_type_6 != "" && !parten.test(info_type_6)){
				layer.tips('只能输入数字', '#info_type_6', {tips: [2, '#ff6666']});
				return;
			}
		}
		increaseCheck=true;
	};
	var ineditCheck=false;
	//编辑操作检查
	var checkInc=function(incCheck){
		var parten=/^\+?[1-9][0-9]*$/;  
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
			if(edit_info_type_1 != "" && !parten.test(edit_info_type_1)){
				layer.tips('只能输入数字', '#edit_info_type_1', {tips: [2, '#ff6666']});
				return;
			}
			if(edit_info_type_2 != "" && !parten.test(edit_info_type_2)){
				layer.tips('只能输入数字', '#edit_info_type_2', {tips: [2, '#ff6666']});
				return;
			}
			if(edit_info_type_3 != "" && !parten.test(edit_info_type_3)){
				layer.tips('只能输入数字', '#edit_info_type_3', {tips: [2, '#ff6666']});
				return;
			}
			if(edit_info_type_4 != "" && !parten.test(edit_info_type_4)){
				layer.tips('只能输入数字', '#edit_info_type_4', {tips: [2, '#ff6666']});
				return;
			}
			if(edit_info_type_5 != "" && !parten.test(edit_info_type_5)){
				layer.tips('只能输入数字', '#edit_info_type_5', {tips: [2, '#ff6666']});
				return;
			}
			if(edit_info_type_6 != "" && !parten.test(edit_info_type_6)){
				layer.tips('只能输入数字', '#edit_info_type_6', {tips: [2, '#ff6666']});
				return;
			}
		}
		ineditCheck=true;
	};
	/**
		新增保存按钮
	**/
	$("#increaseBtn").on("click",function(){
		GlobalGLoading();
		checkIncData(true);
		if(!increaseCheck){
			return;
		}
		var shelf_id=$("#shelf_id").val();
		var shelf_name=$("#shelf_name").val();
		var layercount=$("#layercount").val();
		var layersort =$("#layersort").val();
		var floorimage_url=$("#floorimage_url").val();
		var shelfimage_url=$("#shelfimage_url").val();
		var shelfpoint=$("#shelfpoint").val();
		var issmartshelf=$("#issmartshelf").val();
		var shelf_group_idx=$("#shelf_group_idx").val();
		var info_type_1 = $("#info_type_1").val();
		var info_type_2 = $("#info_type_2").val();
		var info_type_3 = $("#info_type_3").val();
		var info_type_4 = $("#info_type_4").val();
		var info_type_5 = $("#info_type_5").val();
		var info_type_6 = $("#info_type_6").val();
		var bookshelfinfo = setBookshelfinfo('',shelf_id,info_type_1,info_type_2,info_type_3,info_type_4,info_type_5,info_type_6);
		if(shelf_name.length>100){
			layer.alert(devicegroup_jsp_hasfieldoutofLength);
			return;
		}
		param = {
				json1 : {
					"lib_id":'',
					"shelf_id":shelf_id,
					"shelf_name":shelf_name,
					"layercount":layercount,
					"layersort":layersort,
					"floorimage_url":floorimage_url,
					"shelfimage_url":shelfimage_url,
					"shelfpoint":shelfpoint,
					"issmartshelf" :issmartshelf
				},
				json2 : {
					"shelf_group_idx" : shelf_group_idx,
					"library_idx" : ''
				},
				json3 : bookshelfinfo
		}
		if(shelf_id==""||shelf_name=="") return;
		$.ajax({
			 url:basePath+"/bookshelf/addBookshelf",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
				 if(data.state){
					var shelf_id=$("#query").val();
					var currentpage = $(".paging-bar li.active").text();
					var size= $('#pagesize option:selected').text();
					var Json = {"lib_id":'',"shelf_id":shelf_id}
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
	        	}else if(data.retMessage){
	        		showRetMessage(data.retMessage);
	        	}
        	}
		});
	});
	
	function clearVal(){
		$("#shelf_id").val("");
		$("#shelf_name").val("");
		$("#layercount").val(6);
		$("#layersort").val(1);
		$("#floorimage_url").val("");
		$("#shelfimage_url").val("");
		$("#shelfpoint").val("");
		$("#issmartshelf").val(0);
	}
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var shelf_id=$("#query").val();
		var size= $('#pagesize option:selected').text();
		var Json = {"shelf_id":shelf_id,
				"lib_id":''};
		var Page={	"page":"1",
					"pageSize":size}; 
		$.select(Json,Page);
	});
	//查询ajax封装
	jQuery.select=function (Json,Page){
		library_idx=oper.library_idx;
		//获取当前页数
			var size= $('#pagesize option:selected').text();
		
			var param={	json: Json , page : Page};
			$.ajax({
			url:basePath+"/bookshelf/queryAllBookshelf",
			type:"POST",
			data:{"req":JSON.stringify(param),},
			success:function(data){
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					var shelftype = '普通书架';
					if(item.issmartshelf == 1) shelftype = '智能书架';
					if(item.issmartshelf == 2) shelftype = 'SSL书架';
					if(item.issmartshelf == 3) shelftype = '取书柜';
					if(item.issmartshelf == 4) shelftype = '密集书架';
					$("tbody").append(
					"<tr>"+
	            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.shelf_idx+"' /></div></td>"+
						"<td class='col2'>"+item.shelf_id+"</td>"+
						"<td class='col3'>"+item.shelf_name+"</td>"+
						"<td class='col4'>"+item.layercount+"</td>"+
						"<td class='col5'>"+(item.layersort!=1?"从下到上递增":"从上到下递增")+"</td>"+
						"<td class='col6'>"+(item.floorimage_url == null || item.floorimage_url == ""?"无":item.floorimage_url)+"</td>"+
						"<td class='col7'>"+(item.shelfimage_url == null || item.shelfimage_url == ""?"无":item.shelfimage_url)+"</td>"+
						"<td class='col8'>"+(item.shelfpoint== null || item.shelfpoint== "" ?"无":item.shelfpoint)+"</td>"+
						"<td class='col9'>"+shelftype+"</td>"+
	                    "<td class='col10'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
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
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size= $('#pagesize option:selected').html();
		var shelf_id=$("#query").val();
		var Json = {"shelf_id":shelf_id,
				"lib_id":''};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $('#pagesize option:selected').html();
		var shelf_id=$("#query").val();
		var Json = {"shelf_id":shelf_id,
				"lib_id":''};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var page = $(this).html();
		if(page=="...") return;
		var size= $('#pagesize option:selected').html();
		var shelf_id=$("#query").val();
		var Json = {"shelf_id":shelf_id,
				"lib_id":''};
		var Page={	"page":page,
				"pageSize":size,}; 
		$.select(Json,Page);
		
	});
	/**
		每页显示的条目数切换
	**/
	$("select#pagesize").on("change",function(){
		GlobalGLoading();
		var size= $('#pagesize option:selected').html();
		var shelf_id=$("#query").val();
		var Json = {"shelf_id":shelf_id,
				"lib_id":''};
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
		floorcount = false;
		pointselect = false;
		$("#layercount_upload").show();
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
		pointselect = false;
		$("#layercount_upload").hide();
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
		floorcount =false;
		pointselect = true;
		$("#layercount_upload").hide();
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
		   //var fileName = getPath( document.getElementById("file"));
		   if(!$("#file").val()){
		   		layer.alert("请选择文件后上传");
		   		bakUpload.isClicked=false;
		   		return;
		   }
		   var dialogIndex = loadingDialog({
				loadText:"请稍等，上传中..."
		   });
		   $.ajaxFileUpload({
			   	url:basePath+"/common/uploadImg",
			   	secureuri :false,
				 fileElementId :'file',//file控件id
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
								 var Json = {"layercount":$("#img-count").val(),
											"lib_id":'',
											"floorimage_url":obj.result};
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
												var size= $('#pagesize option:selected').text();
												var Json = {"lib_id":'',"shelf_id":shelf_id}
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
							 else if(!floorcount&&pointselect){
								 pointselect = false;
								 var Json = {
										 "lib_id":'',
										 "file":obj.result,
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
												var size= $('#pagesize option:selected').text();
												var Json = {"shelf_id":shelf_id,"lib_id":''}
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
								 var Json = {"layercount":$("#img-layercount").val(),
											"lib_id":'',
											"shelfimage_url":obj.result};
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
												var size= $('#pagesize option:selected').text();
												var Json = {"shelf_id":shelf_id,"lib_id":''}
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
	  	/**
		 *	对应用户的图书馆数据
		 **/
		(function GetlibIdxAndNameOBJ(){
			//获取当前页数
				var size= $('#pagesize option:selected').text();
				var Page = {"page":"1",
						"pageSize":size};
				var Json = {"lib_id":''}
				var param={	json: Json , page : Page};
				$.ajax({
				url:basePath+"/bookshelf/queryAllBookshelf",
				type:"POST",
				data:{"req":JSON.stringify(param),},
				success:function(data){
					$("tbody").html("");
					$.each(data.result.rows,function(i,item){
						var shelftype = '普通书架';
						if(item.issmartshelf == 1) shelftype = '智能书架';
						if(item.issmartshelf == 2) shelftype = 'SSL书架';
						if(item.issmartshelf == 3) shelftype = '取书柜';
						if(item.issmartshelf == 4) shelftype = '密集书架';
						$("tbody").append(
						"<tr>"+
		            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.shelf_idx+"' /></div></td>"+
							"<td class='col2'>"+item.shelf_id+"</td>"+
							"<td class='col3'>"+item.shelf_name+"</td>"+
							"<td class='col4'>"+item.layercount+"</td>"+
							"<td class='col5'>"+(item.layersort!=1?"从下到上递增":"从上到下递增")+"</td>"+
							"<td class='col6'>"+(item.floorimage_url == null || item.floorimage_url == ""?"无":item.floorimage_url)+"</td>"+
							"<td class='col7'>"+(item.shelfimage_url == null || item.shelfimage_url == ""?"无":item.shelfimage_url)+"</td>"+
							"<td class='col8'>"+(item.shelfpoint== null || item.shelfpoint== "" ?"无":item.shelfpoint)+"</td>"+
							"<td class='col9'>"+shelftype+"</td>"+
		                    "<td class='col10'><span class='btn-a edit'>"+message_gloable_edit+"</span>"+
		                    "<span class='btn-a delete'>"+message_gloable_delete+"</span></td>" +
		                    "<td class='version_stamp hide'>"+item.version_stamp+"</td>"+
		                	"</tr>");	
					}); 
		    		$.pagination(data.result);       
					}
				});
		})();
		
});
</script>
</body>
</html>