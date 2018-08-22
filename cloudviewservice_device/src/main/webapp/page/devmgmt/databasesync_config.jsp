<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
		/*同步周期选择 start*/
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
		    margin-left: 10px;
		    width: 16px;
		    height: 16px;
		    background: url(${pageContext.request.contextPath}/static/images/checkbox.png) center center no-repeat;
		}
		.config-shujuku .col3 {
   		    width: 150px;
		}
		.config-shujuku .col2 {
    		width: 100px;
		}
		.config-shujuku .col5 {
    		width: 100px;
		}
		/*tbody{width:400px;line-height:20px;padding:10px;border-bottom:1px dashed #ccc;font-size:12px;text-indent:25px;}*/
		tbody .all{display:none;}
		tbody .more{text-decoration:none;color:#0066CC;cursor:pointer;}
		.form-wrap .g-textarea{
			width: 540px;
		}
		.form-wrap .select-area{
			position: relative;
			float: left;
			margin-left: 10px;
			width: 260px;
			height: 310px;
			background-color: #f6f6f6;
		    border: 1px solid #DDD;
			-webkit-box-sizing: border-box;
			-moz-box-sizing: border-box;
			box-sizing: border-box;
		}
		.form-wrap dd{
			height: 30px;
			line-height: 30px;
		}
		.form-wrap .select-area .g-checkbox.on{
			background: url(${pageContext.request.contextPath}/static/images/choosen1.png) center center no-repeat;
		}
		.form-wrap .choose-area{
			padding:10px 10px;
			height: 300px;
			-webkit-box-sizing: border-box;
			-moz-box-sizing: border-box;
			box-sizing: border-box;
			overflow: auto;
		}
		.form-wrap .choose-jurisdiction{
			padding:5px 0;
			height: 305px;
			-webkit-box-sizing: border-box;
			-moz-box-sizing: border-box;
			box-sizing: border-box;
			overflow: auto;
		}
		.form-wrap .choose-jurisdiction dd{
			padding-left: 12px;
		}
		.form-wrap .choose-jurisdiction dd:hover{
			background-color: #00a2e9;
			color:#FFF;
		}
		.form-wrap .handle-area{
			position: absolute;
			bottom:0;
			left:0;
			width: 100%;
			height: 40px;
			line-height: 40px;
			background-color: #e2e2e2;
		}
		.form-wrap .handle-area .g-btn{
			margin-left:10px;
		}
	</style>
</head>
<body>
<div class="config-shujuku">
<div class="g-loading"></div>
		<div class="page-title-bar">
			<span class="title">设备数据同步模板配置<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/devmgmt/databasesync-config.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-select">
					<select id="search_type">
						<!-- <option value="-1" selected>选择字段</option> -->
						<option value="1">模版ID</option>
						<option value="2">模版名称</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<input type="text" name="" id="search_info" class="input g-input" placeholder="输入关键词" />
				<div class="btn search">查询</div>
				<div class="btn increase">新增</div>
				<div class="btn delete">删除</div>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="col2">模板ID</th>
						<th class="col3">模板名</th>
						<th class="col4">数据库名</th>
						<th class="col5">表名</th>
						<th class="col7">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
						<td class="col2">10001</td>
						<td class="col3">24小时自助借还机</td>
						<td class="col4">device_config</td>
						<td class="col5">device_dbsync_config</td>
						<td class="col7">
							<span class="btn-a edit">编辑</span>
							<span class="btn-a delete">删除</span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
	<%@include file ="../include/page_bar.jsf" %>	
</div>
<div class="form-dialog-fix w-900 dbsync">
	<div class="shade"></div>
	<div class="form-dialog">
		<div class="title">
			新增配置
			<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
			<input type="submit" placeholder="" class="g-submit2 g-btn-green" value="保存">
		</div>
		<div class="left-tab">
			<ul id="deviceDbList">
				<li class="basicInfo" active>基本信息</li>
			</ul>
		</div>
		<div class="right-form" id="rightHTMlDiv">
		  <div class="right-content">
			<div class="submenu-segmentation-line">
				<div class="t">基本信息</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">设备类型</div>
						<div class="right">
							<div class="g-select">
								<select id="type">
									<option value="-1" selected>请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
							<span class="error-msg">请选择模板的设备类型</span>
						</div>
					</li> 
					<li>
						<div class="left">模板ID</div>
						<div class="right">
							<input type="text" name="" id="temp_ID" class="g-input" maxlength="10" placeholder="请输入" />
							<span class="error-msg">ID不能为空</span>
						</div>
					</li>
					<li>
						<div class="left">模板名</div>
						<div class="right">
							<input type="text" name="" id="temp_name" class="g-input" maxlength="100" maxlplaceholder="请输入" />
							<span class="error-msg">模板名不能为空</span>
						</div>
					</li>
					
				</ul>
			  </div>
		    </div>
		  </div>
		</div>
	</div>
</div>

<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red"> </span> 个项目<br>
		是否要删除选择的配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
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
	
	var indexPermessions = {};
	$.ajax({
		url:"${pageContext.request.contextPath}/servgroup/selectDeviceOperLogCmd",
		data:{},
		asysc:false,
		type:"GET"
	}).then(function(data){
		if(data&&data.state==true){
			indexPermessions=data.result;
		}
	});
	
	var deviceTypeList = {};
	
	TempConfig = [];
	var delLayer = null;
	
	var dbsync_idx = new Array();
	var dbsync_desc = new Array();
	$(".delete").on("click",function(){
		dbsync_idx = [];
		var num = $("tbody input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {
			dbsync_idx.push($(this).attr("id"));
			dbsync_desc.push($(this).attr("data-name"));
		});
		if(num >0 ){
			$(".g-delete-dialog .word").html("");
			$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"</span>条数据<br>是否要删除选择的设备同步模板数据？");
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
			layer.alert("请选择要删除的数据！");
		}
	});
	/**
		点击删除
	**/
	$("tbody").on("click",".delete",function(){
		dbsync_idx = [];
		dbsync_desc = [];
		dbsync_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		dbsync_desc.push($(this).attr("data-name"));
		$(".g-delete-dialog").find(".word").html(
		'是否删除 <span class="font-red">'+$(this).parents("tr").find("td:eq(1)").html()+'</span>');
		delLayer = layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false ,
			area:["400px"],
			offset :["195px"],
			content: $('.g-delete-dialog'), //捕获的元素
		});
	});
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	var deleting = false;
	$(".form-btn.remove").on("click",function(){
		if(deleting){
			return;
		}
		deleting = true;
		var idx = dbsync_idx;
		var desc = dbsync_desc;
		if(!idx)  return;
		var param = new Array();
		for(var i=0;i<idx.length;i++){
			param[i]={
			"device_tpl_idx":idx[i],
			"device_tpl_desc":desc[i]};
		}
		var data={"idx":param,
				"operator_idx":o.operator_idx};
		$.ajax({
			url:"${pageContext.request.contextPath}/dbsync/DelDBsynctemp",
			type:"POST",
			data:{json:JSON.stringify(data)},
			success:function(data){
				if(data.state){
					if(data.result.cannotdel!=null && data.result.cannotdel!=""){
						layer.alert(data.result.cannotdel+"已经被占用，不能删除");
					}else{
						showMsg({
							timeout : 1000,
							showText : '删除成功',
							status : true,
							callback:function(){
								//$(".layui-layer-shade").click();
								if(delLayer){
									layer.close(delLayer);
								}
							}
						});
					}
					//获取条件更新页面
					var size = $(".g-select.refresh").find("option:selected").html();
					var currentpage = $(".paging-bar li.active").html();
					var param ={
						"page":currentpage,
						"pageSize":size,
					};
					var search_type = $("#search_type").val();
					if(search_type == 1)
						param.device_tpl_ID = $("#search_info").val();
					if(search_type == 2)
						param.device_tpl_desc = $("#search_info").val();
					$.select(param);	
					
				}else{
// 					showMsg({
// 					timeout : 1000,
// 					showText : '删除配置失败',
// 					status : false,
// 					callback:function(){
// 						$(".layui-layer-shade").click();
// 					}
// 					});
					layer.alert("删除失败，"+data.message);
				}
			}
		}).always(function(){
			deleting = false;
		});
	});
	
	var temp_idx = null;
	/**
		点击编辑按钮，需要将数据全部清除
	**/
	$("tbody").on("click",".edit",function(){
		//修改按钮及title
		$(".form-dialog div.title").html("修改配置"+
		"<input value='取消' class='g-cancel2 g-btn-gray' type='reset'>"+
		"<input placeholder=''  name='edit' class='g-submit2 g-btn-green' value='保存' type='submit'>");
		var idx = $(this).parent().parent().find("div input[name ='idx']").attr("id");
		temp_idx = idx;
		$.each(TempConfig,function(i,item){
			if(item.device_tpl_idx== idx){
				var id = item.device_type;
				var dbTabRightDiv = dbTabRightDivRet;
				var li = liLeft;
				$("#deviceDbList").html("");
				$("#rightHTMlDiv").html("");
				if(id!="-1"){
					var item1 = deviceTypeList[id];
					var device_db_list = item1.device_db_list;
					var device_logic_list = item1.device_logic_list;
					if(!_.isEmpty(device_db_list)){
						var list = device_db_list.split(",");
						for(i in list){
							//组装左侧
							li += "<li>"+list[i]+"</li>";
							//组装右侧
							var param={
								"db_name":list[i]
							};
							
							dbTabRightDiv += "<div class=\"right-content\" hidden>";
							
							$.ajax({
								url:"${pageContext.request.contextPath}/metadevicetype/selectMetadataDeviceDbAndTableInfo",
								data:{req:JSON.stringify(param)},
								async: false,//设置成同步
								type:"GET"
							}).done(function(data){
								if(data&&data.state&&data.result){
									var deviceDbTables=data.result;
									$.each(deviceDbTables,function(index,dbtab){
										dbTabRightDiv += "<div class=\"submenu-segmentation-line\">";
										dbTabRightDiv += "<div class=\"t\">"+dbtab.table_name+"</div>";
										dbTabRightDiv += "</div>";
										dbTabRightDiv += "<div class=\"form-wrap\">";
										dbTabRightDiv += "<ul>";
										dbTabRightDiv += "<li>";
										dbTabRightDiv += "<div class=\"left\">数据库名</div>";
										dbTabRightDiv += "<div class=\"right\">";
										dbTabRightDiv += "<div class=\"g-select\">";
										dbTabRightDiv += "<select class=\"db\">";
										dbTabRightDiv += "<option value=\"\" selected>"+dbtab.db_name+"</option>";
										dbTabRightDiv += "</select>";
										dbTabRightDiv += "<span class=\"arr-icon\"></span>";
										dbTabRightDiv += "</div>";
										dbTabRightDiv += "</div>";
										dbTabRightDiv += "</li>";
										dbTabRightDiv += "<li>";
										dbTabRightDiv += "<div class=\"left\">表名</div>";
										dbTabRightDiv += "<div class=\"right\">";
										dbTabRightDiv += "<div class=\"g-select\">";
										dbTabRightDiv += "<select class=\"table\">";
										dbTabRightDiv += "<option value=\"1\" >"+dbtab.table_name+"</option>";
										dbTabRightDiv += "<option value=\"-1\">请选择</option>";
										dbTabRightDiv += "</select>";
										dbTabRightDiv += "<span class=\"arr-icon\"></span>";
										dbTabRightDiv += "</div>";
										dbTabRightDiv += "</div>";
										dbTabRightDiv += "</li>";
										dbTabRightDiv += "<li>";
										dbTabRightDiv += "<div class=\"left\">&nbsp;</div>";
										dbTabRightDiv += "<div class=\"right\">";
										dbTabRightDiv += "<div class=\"g-checkbox\"><input type=\"checkbox\"  name=\"\" value=\"\" class=\"tongbu\" /></div><label for=\"tongbu\">是否同步</label>";
										dbTabRightDiv += "</div>";
										dbTabRightDiv += "</li>";
										
										dbTabRightDiv += "<li>";
										dbTabRightDiv += "<form action=\"\">";
										/* dbTabRightDiv += "<div class=\"left\">&nbsp;</div>"; */
										dbTabRightDiv += "<div class=\"right short-select\">";
										dbTabRightDiv += "<span class=\"fl\">";
										dbTabRightDiv += "同步周期";
										dbTabRightDiv += "<input type=\"text\" class=\"cycle-input g-input\" name=\"cycle-num\" value=\"\"/>";
										dbTabRightDiv += "</span>";
										dbTabRightDiv += "<div class=\"g-select\">";
										dbTabRightDiv += "<select class=\"cycle\">";
										dbTabRightDiv += "<option value=\"\">一天</option>";
										dbTabRightDiv += "</select>";
										dbTabRightDiv += "<div class=\"arr-icon\"></div>";
										dbTabRightDiv += "</div>";
										dbTabRightDiv += "</div>";
										dbTabRightDiv += "</form>";
										dbTabRightDiv += "</li>";
										
										dbTabRightDiv += "<li>";
										dbTabRightDiv += "<form action=\"\">";
										/* dbTabRightDiv += "<div class=\"left\">&nbsp;</div>"; */
										dbTabRightDiv += "<div class=\"right short-select\">";
										dbTabRightDiv += "<span class=\"fl\">";
										dbTabRightDiv += "同步方式";
										/* dbTabRightDiv += "<input type=\"text\" class=\"cycle-input g-input\" name=\"sync_type\" value=\"\"/>"; */
										dbTabRightDiv += "</span>";
										dbTabRightDiv += "<div class=\"g-select\">";
										dbTabRightDiv += "<select class=\"syncType\">";
										dbTabRightDiv += "<option value=\"\">请选择</option>";
										dbTabRightDiv += "</select>";
										dbTabRightDiv += "<div class=\"arr-icon\"></div>";
										dbTabRightDiv += "</div>";
										dbTabRightDiv += "</div>";
										dbTabRightDiv += "</form>";
										dbTabRightDiv += "</li>";
										
										
										dbTabRightDiv += "<li>";
										dbTabRightDiv += "<div class=\"left\">同步字段设置</div>";
										dbTabRightDiv += "<div class=\"right\">";
										dbTabRightDiv += "<div class=\"select-area\">";
										dbTabRightDiv += "<dl class=\"choose-area indexPermessions\">";
										
										//if("operation_log_".equal(dbtab.table_name.substr(0,dbtab.table_name.length-1))){
										if(dbtab.table_name.indexOf("soft_state")!= -1 || dbtab.table_name.indexOf("operation_log_")!= -1){
											if(indexPermessions != null){
												for(var i=0;i<indexPermessions.length;i++){
													dbTabRightDiv += "<dd><div class=\"g-checkbox\"><input type=\"checkbox\" value="+indexPermessions[i].opercmd+" class=\"fieldCheck\"></div>"+indexPermessions[i].opercmd_desc+"</dd>";
												}
											}
										}else if(dbtab.table_name.indexOf("ext_state")!= -1){
											
											if(device_logic_list != null){
												var fields = device_logic_list.split(",");
												for(var i=0;i<fields.length;i++){
													dbTabRightDiv += "<dd><div class=\"g-checkbox\"><input type=\"checkbox\" value="+fields[i]+" class=\"fieldCheck\"></div>"+fields[i]+"</dd>";
												}
											}
										}else{
											if(dbtab.table_field_list != null){
												var fields = dbtab.table_field_list.split(",");
												for(var i=0; i<fields.length; i++){
													dbTabRightDiv += "<dd><div class=\"g-checkbox\"><input type=\"checkbox\" value="+fields[i]+" class=\"fieldCheck\"></div>"+fields[i]+"</dd>";
												}
											}
										}
									    dbTabRightDiv += "</dl>";
									    /* dbTabRightDiv += "<div class=\"handle-area\">";
									    dbTabRightDiv += "<div class=\"g-btn checkAll\">全选</div>";
									    dbTabRightDiv += "<div class=\"g-btn noCheckAll\">反选</div>";
									    dbTabRightDiv += "</div>"; */
									    dbTabRightDiv += "</div>";
									    dbTabRightDiv += "</div>";
									    dbTabRightDiv += "</li>";
									    dbTabRightDiv += "</ul>";
									    dbTabRightDiv += "</div>";
									});
								}
							});
							dbTabRightDiv += "</div>";
						}
					}
					$("#deviceDbList").html(li);
					$("#rightHTMlDiv").html(dbTabRightDiv);
					$("div.right-content .cycle").html(cycle_html); 
					$("div.right-content .syncType").html(dataTypeHtml); 
				}
				
				//$(".right-content .table").val(-1);
				//默认选中表不可改
				$(".right-content .table").attr("disabled", true);
				$(".right-content .table").attr("style", "color: gray;background-color: #E0E0E0");
				//默认数据库不可改
				$(".right-content .db").attr("disabled", true);
				$(".right-content .db").attr("style", "color: gray;background-color: #E0E0E0");
				$(".right-content .cycle").val(-1);
				$(".right-content .tongbu").each(function(){
					if($(this).is(':checked'))
						$(this).click();
				});
				$(".right-content .select-area .fieldCheck").each(function(){
					if($(this).is(':checked'))
						$(this).click();
				});
				//清空周期数据
				$(".right-content input[name=cycle-num]").val("");
				//点击device_config li
				//$(".left-tab").find(".device_config").trigger("click");
				//填充数据
				
				$("#temp_ID").val(item.device_tpl_ID);
				$("#temp_name").val(item.device_tpl_desc);
				$("#type").val(item.device_type);
				
				$.each(item.TempConfigInfo,function(j,elem){
					$("select.db").each(function(){
						if($(this).find("option:selected").html() == elem.db_name){
							var tablename = $(this).parents(".form-wrap").find(".table").find("option").html();
							if(tablename == elem.table_name){
								$(this).parents(".form-wrap").find(".table").val(1);
								if(elem.issync == 1){
									if(!$(this).parents(".form-wrap").find(".tongbu").is(":checked")){
										$(this).parents(".form-wrap").find(".tongbu").click();
									}
								}
								var cycle=elem.sync_cycle;
								if(cycle){
									$(this).parents(".form-wrap").find("input[name=cycle-num]").val(cycle.substring(0,cycle.length-1));
									$(this).parents(".form-wrap").find(".cycle").val(cycle.charAt(cycle.length-1));
								}
								
								var synctypeData=elem.sync_type;
								if(synctypeData){
									$(this).parents(".form-wrap").find(".syncType").val(synctypeData);
								}
								
								if(elem.sync_field_list != null && elem.sync_field_list.length > 0){
									var fields = elem.sync_field_list.split(",");
									$(this).parents(".form-wrap").find(".select-area input[type='checkbox']").each(function(){
										if(fields.toString().indexOf($(this).val()) > -1){
											$(this).prop("checked",true).parents(".g-checkbox").addClass("on");
										}
									});
								}
							}
						}
					});
				});
			}
		});		
		
		
		$(".form-dialog-fix").fadeIn(100);
		$(".form-dialog-fix").find(".form-dialog").animate({
			"right":0
		});
	});
	/**
		点击新增配置模板数据
	**/
	$(".increase").on("click",function(){
		$(".form-dialog div.title").html("新增配置"+
		"<input value='取消' class='g-cancel2 g-btn-gray' type='reset'>"+
		"<input placeholder='' name='increase' class='g-submit2 g-btn-green' value='保存' type='submit'>");
		
		$("#deviceDbList").html("");
		$("#rightHTMlDiv").html("");
		$("#deviceDbList").html(liLeft);
		$("#rightHTMlDiv").html(dbTabRightDivRet);
		
		//初始化弹出层
		$("#temp_ID").val("");
		$("#temp_name").val("");
		$("#type").val(-1);
		//$(".right-content .table").val(-1); //表名默认为不选择
		//默认选中表不可改
		$(".right-content .table").attr("disabled", true);
		$(".right-content .table").attr("style", "color: gray;background-color: #E0E0E0");
		//默认数据库不可改
		$(".right-content .db").attr("disabled", true);
		$(".right-content .db").attr("style", "color: gray;background-color: #E0E0E0");
		$(".right-content .cycle").val(-1); //同步周期时间 默认为不选择
		$(".right-content .tongbu").each(function(){
			if($(this).is(':checked'))
				$(this).click();
		});
		$(".right-content .select-area .fieldCheck").each(function(){
			if($(this).is(':checked'))
				$(this).click();
		});
		$(".right-content input[name=cycle-num]").val("");
		
		$(".basicInfo").trigger("click");
		$(".form-dialog-fix").fadeIn(100);
		$(".form-dialog-fix").find(".form-dialog").animate({
			"right":0
		});
	});

	$("div.form-dialog").on("click",":reset",function(){
		var thisRight = $(this).parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
	
	  $(this).parents(".form-dialog-fix").find(".form-dialog").animate({
	    "right": thisRight
	  }, function() {
	    $(this).parents(".form-dialog-fix").fadeOut(100);
	  });
	  $("body").css("overflow", "visible");
	});
	
	//文本框聚焦事件
	$(":text").focus(function(){
		$(this).parents("li").removeClass("error");
	});
	//设备类型下拉列表框聚焦事件
	$("#type").focus(function(){
		$(this).parents("li").removeClass("error");
	});
	/* $(".form-dialog .form-wrap  .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	}); */

	$("div.form-dialog").on("click",".g-submit2",function(){
		var value = $(this).attr("name");
		var checkdata = true;
		//data 获取模板配置信息
		var data = [];
		//下拉框 数据库名
		$("select.db").each(function(){
			if($(this).parents(".form-wrap").find(".tongbu").is(":checked")){
				var dbname = $(this).find("option:selected").html();
				var tablename = $(this).parents(".form-wrap").find(".table option:selected").html();
				var param ={
					"library_idx":0,
					"device_tpl_type":0,
					"db_name":dbname,
					"table_name":tablename,
				};
				//alert(tablename);
				var cycletime = $(this).parents(".form-wrap").find(".cycle").val();
				console.log("cycletime:"+cycletime);
				var cycleNum=$(this).parents(".form-wrap").find("input[name=cycle-num]").val();
				var syncTypeD = $(this).parents(".form-wrap").find(".syncType").val();
				
				if(cycletime=="-1"){
					layer.alert("数据库库："+dbname+" 表名："+tablename+" 未设置同步周期单位");
					checkdata = false;
						return;
				}
				if(!cycleNum){
					layer.alert("数据库库："+dbname+" 表名："+tablename+" 未设置同步周期");
					checkdata = false;
					return;
				}
				if(!$.isNumeric(cycleNum)){
					layer.alert("数据库库："+dbname+" 表名："+tablename+" 同步周期必须是数字");
					checkdata = false;
					return;
				}
				param.sync_cycle = cycleNum+cycletime;
				if(syncTypeD != "-1"){
					param.sync_type=syncTypeD;
				}
				
				if($(this).parents(".form-wrap").find(".tongbu").is(":checked")){
					param.issync = 1;
				}else{
					param.issync = 0;
				}
				var sync_field_list = "";
				var a = 0;
				var b = 0;
				$(this).parents(".form-wrap").find(".select-area input[type='checkbox']").each(function(){
					a ++;
					if($(this).is(":checked")){
						sync_field_list = sync_field_list + $(this).val() + ",";
						b++;
					}
				});
				if(b == 0){
					layer.alert("数据库库："+dbname+" 表名："+tablename+" 同步字段设置不能为空");
					checkdata = false;
					return;
				}else{
					param.sync_field_list = sync_field_list.substring(0,sync_field_list.length -1);
				}
				data.push(param);
			}else{
				var dbname = $(this).find("option:selected").html();
				var tablename = $(this).parents(".form-wrap").find(".table option:selected").html();
				var param ={
					"library_idx":0,
					"device_tpl_type":0,
					"db_name":dbname,
					"table_name":tablename,
				};
				//alert(tablename);
				var cycletime = $(this).parents(".form-wrap").find(".cycle").val();
				console.log("cycletime:"+cycletime);
				var cycleNum=$(this).parents(".form-wrap").find("input[name=cycle-num]").val();
				var syncTypeD = $(this).parents(".form-wrap").find(".syncType").val();
				if(syncTypeD == "-1"){
					syncTypeD == null;
				}
				if(cycletime=="-1"){
					cycletime = null;
				}
				if(!cycleNum){
					cycleNum == null;
				}
				if(cycleNum != null && cycleNum.length > 0 && !$.isNumeric(cycleNum)){
					layer.alert("数据库库："+dbname+" 表名："+tablename+" 同步周期必须是数字");
					checkdata = false;
					return;
				}
				if(syncTypeD != null && cycletime != null && cycleNum != null){
					param.sync_cycle = cycleNum+cycletime;
					param.sync_type=syncTypeD;
				}
				
				if($(this).parents(".form-wrap").find(".tongbu").is(":checked")){
					param.issync = 1;
				}else{
					param.issync = 0;
				}
				var sync_field_list = "";
				var a = 0;
				var b = 0;
				$(this).parents(".form-wrap").find(".select-area input[type='checkbox']").each(function(){
					a ++;
					if($(this).is(":checked")){
						sync_field_list = sync_field_list + $(this).val() + ",";
						b++;
					}
				});
				if(b > 0){
					param.sync_field_list = sync_field_list.substring(0,sync_field_list.length -1);
				}
				data.push(param);
			}
		});
	
		//获取模板基本信息
		var temp_ID = $("#temp_ID").val();
		var temp_name = $("#temp_name").val();
		var type = $("#type").val();
		//数据检查
		if(type == -1){
			$("#type").parents("li").addClass("error");
			layer.alert("请选择设备类型");
			checkdata = false;
		}
		if(!temp_ID){
			$("#temp_ID").parents("li").addClass("error");
			if(checkdata){
				layer.alert("请填写同步模版ID");
			}
			checkdata = false;
		}
		if(!temp_name){
			$("#temp_name").parents("li").addClass("error");
			if(checkdata){
				layer.alert("请填写同步模版名称");
			}
			checkdata = false;
		}
		if(!checkdata){
			return;
		}
		//模板数据至少要填一项
		if(data.length==0){
			layer.alert("至少要设置一项模板数据！");
			return;
		}
		var TempInfo = {
			"device_tpl_ID":temp_ID,
			"device_tpl_desc":temp_name,
			"device_type":type,
		};
		//--------点击新增保存按钮--------//
		if(value == "increase" && checkdata == true){
			var param ={
				"operator_idx":o.operator_idx,
				"TempInfo":TempInfo,
				"TempConfigInfo":data,			
			};
			$.ajax({
				url:"${pageContext.request.contextPath}/dbsync/AddNewDBsynctemp",
				type:"POST",
				data:{
					"json":JSON.stringify(param)
				},
				success:function(data){
					if(data.state){
						showMsg({
							timeout : 1000,
							showText : '添加配置成功',
							status : true,
							callback:function(){
								var thisRight =  $(this).parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
								$(this).parents(".form-dialog-fix").find(".form-dialog").animate({"right":thisRight},function(){
									$(this).parents(".form-dialog-fix").fadeOut(100);
								});
							}
						});
						//获取条件更新页面
						var size = $(".g-select.refresh").find("option:selected").html();
						var currentpage = $(".paging-bar li.active").html();
						var info = {"page":currentpage,"pageSize":size};
						var search_type =$("#search_type").val();
						if(search_type == 1)
							info.device_tpl_ID = $("#search_info").val();
						if(search_type == 2)
							info.device_tpl_desc = $("#search_info").val();
						$.select(info);
						$("input[type=reset]").trigger("click");
					}else{
						layer.alert("添加失败，"+data.message);
// 						showMsg({
// 							timeout : 1000,
// 							showText : '添加配置失败',
// 							status : false,
// 							callback:function(){
							
// 								var thisRight =  $(this).parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
		
// 								$(this).parents(".form-dialog-fix").find(".form-dialog").animate({"right":thisRight},function(){
// 									$(this).parents(".form-dialog-fix").fadeOut(100);
// 								});
// 							}
// 						});
					}
				}
			});
		}
		//编辑后保存
		if(value == "edit"  && checkdata == true){
			TempInfo.device_tpl_idx = temp_idx;
			var param ={
				"operator_idx":o.operator_idx,
				"TempInfo":TempInfo,
				"TempConfigInfo":data,			
			};
			$.ajax({
				url:"${pageContext.request.contextPath}/dbsync/UpdDBsynctemp",
				type:"POST",
				data:{"json":JSON.stringify(param)},
				success:function(data){
					if(data.state){
						showMsg({
							timeout : 1000,
							showText : '修改配置成功',
							status : true,
							callback:function(){
							
								var thisRight =  $(this).parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
		
								$(this).parents(".form-dialog-fix").find(".form-dialog").animate({"right":thisRight},function(){
									$(this).parents(".form-dialog-fix").fadeOut(100);
								});
							}
						});
						//获取条件更新页面
						var size = $(".g-select.refresh").find("option:selected").html();
						var currentpage = $(".paging-bar li.active").html();
						var info = {"page":currentpage,"pageSize":size};
						
						var search_type =$("#search_type").val();
						if(search_type == 1)
							info.device_tpl_ID = $("#search_info").val();
						if(search_type == 2)
							info.device_tpl_desc = $("#search_info").val();
						
						$.select(info);
						$("input[type=reset]").trigger("click");
					
					}else{
						layer.alert("修改失败,"+data.message);
// 						showMsg({
// 							timeout : 1000,
// 							showText : '修改配置失败',
// 							status : false,
// 							callback:function(){
// 								var thisRight =  $(this).parents(".form-dialog-fix").find(".form-dialog").attr("date-right");
// 								$(this).parents(".form-dialog-fix").find(".form-dialog").animate({"right":thisRight},function(){
// 									$(this).parents(".form-dialog-fix").fadeOut(100);
// 								});
// 							}
// 						});
					}
				}
			});
		}	
			/* showMsg({
				timeout : 1000,
				showText : '添加配置成功',
				status : true,
				callback:function(){
					//应该是成功的时候收回吧，酌情处理
					var thisRight =  $(this).parents(".form-dialog-fix").find(".form-dialog").attr("date-right");

					$(this).parents(".form-dialog-fix").find(".form-dialog").animate({
						"right":thisRight
					},function(){
						$(this).parents(".form-dialog-fix").fadeOut(100);
					});
				}
			}); */
	});
	
		//数组去重
	function unique(arr){
    	var result = [], hash = {};
    	for (var i = 0, elem; (elem = arr[i]) != null; i++) {
    	    if (!hash[elem]) {
    	        result.push(elem);
    	        hash[elem] = true;
       	 	}
    	}
    	return result;
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
	
	$(".search").on("click",function(){
		var size = $(".g-select.refresh").find("option:selected").html();
		var param = {"page":"1","pageSize":size};
			
		var search_type =$("#search_type").val();
		if(search_type == 1)
			param.device_tpl_ID = $("#search_info").val();
		if(search_type == 2)
			param.device_tpl_desc = $("#search_info").val();
			
		$.select(param);
		
	});
	
	<%-- 查询按钮 --%>
	jQuery.select=function(param){
		$(".g-table tbody").html("");
		$(".col1 :checkbox").prop("checked",false).trigger("change");
		$.ajax({
			url:"${pageContext.request.contextPath}/dbsync/SelectDBsynctemp",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				$("tbody").html("");
				TempConfig =[];
				TempConfig = data.result.rows;
				$.each(data.result.rows,function(i,item){
					var html ="";
					var col_temp = [];
					var col5 = "";
					var col5Short="";
					html +="<tr>"+
						"<td class='col1'><div class='g-checkbox'><input name='idx' data-name='"+item.device_tpl_desc+"' id="+item.device_tpl_idx+" type='checkbox'></div></td>"+
						"<td class='col2'>"+ item.device_tpl_ID +"</td>"+
						"<td class='col3'>"+ item.device_tpl_desc +"</td>";
				$.each(item.TempConfigInfo,function(j,elem){
						var cycle = elem.sync_cycle;
						var newcycle=null;
						if(cycle != null && cycle.length > 1){
							if(cycle.charAt(cycle.length-1) == "D"){
							newcycle = cycle.replace("D", "天");	
							}else if(cycle.charAt(cycle.length-1) == "H"){
								newcycle = cycle.replace("H", "小时");	
							}else if(cycle.charAt(cycle.length-1) == "M"){	
								newcycle = cycle.replace("M", "分");
							}else if(cycle.charAt(cycle.length-1) == "S"){
								newcycle = cycle.replace("S", "秒");
							}
						}
						
						col_temp.push(elem.db_name);
						col5+=elem.table_name+" : "+(elem.issync==1?newcycle:"未同步")+"<br/>";
						if(j>3){return;}//break 只显示前4条
						col5Short+=elem.table_name+" : "+(elem.issync==1?newcycle:"未同步")+"<br/>";
					});
					col5='<span class="short">'+col5Short+'</span><span class="all">'+col5+'</span><a class="more">(更多)</a>';
					var col4 = unique(col_temp);
					var col4_html = "";
					for(var m =0;m<col4.length;m++){
						col4_html +=col4[m]+"<br/>";
					}
					html +="<td class='col4'>"+ col4_html +"</td>"+
						"<td class='col5'>"+ col5 +"</td>"+
						"<td class='col7'>"+
							"<span class='btn-a edit'>编辑</span>"+
							"<span class='btn-a delete' data-name=\""+item.device_tpl_desc+"\">删除</span>"+
						"</td>";
					$("tbody").append(html);
				});
				var t=0;
				//修改权限 by huanghuang 20170215
   				<shiro:lacksPermission name="0102020802">
   			 		t++;
					$(".delete").attr("style","display:none;");
    			</shiro:lacksPermission>
    			<shiro:lacksPermission name="0102020803">
    				t++;
					$(".edit").attr("style","display:none;");
    			</shiro:lacksPermission>
    			if(t==2){
    				$(".col7").attr("style","display:none;");
    			}
    			
				var pageinfo = {
				"total":data.result.total,
				"page":data.result.page,
				"pageSize":data.result.pageSize,
				};
				$.pagination(pageinfo);
				 //对于每一个评论中查看内容的超链接：
				 $("tbody").find("a.more").each(function(){
				  	//如果点击该链接则：
				  	$(this).on("click",function(){
					    //把不需要显示的内容隐藏，需要显示的内容展开。
					    $(this).parent().children('.all,.short').toggle();
					    //替换超链接的文字
					    if($(this).html()=='(更多)'){
					    	$(this).html('(收起)');
					    }else{
					    	$(this).html('(更多)');
					    } 
				  });
	 			});
			}
		});
	};
	
	/**
		分页查询
	**/
	$(document).ready(function(){
		$(".g-table tbody").html("");
		$(".col1 :checkbox").prop("checked",false).trigger("change");
		var size= $(".g-select.refresh").find("option:selected").html();
		var param={	"page":"1",
				"pageSize":size};
		$.ajax({
			url:"${pageContext.request.contextPath}/dbsync/SelectDBsynctemp",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				$("tbody").html("");
				TempConfig =[];
				TempConfig = data.result.rows;
				
				var cycle_temp = [];
				$.each(data.result.rows,function(i,item){
					var html ="";
					var col_temp = [];
					var col5 = "";
					var col5Short="";
					html +="<tr>"+
						"<td class='col1'><div class='g-checkbox'><input name='idx' data-name=\""+item.device_tpl_desc+"\" id=\""+item.device_tpl_idx+"\" type='checkbox'></div></td>"+
						"<td class='col2'>"+ item.device_tpl_ID +"</td>"+
						"<td class='col3'>"+ item.device_tpl_desc +"</td>";
				 	$.each(item.TempConfigInfo,function(j,elem){
						var cycle = elem.sync_cycle;
						var newcycle=null;
						if(cycle != null && cycle.length > 1){
							if(cycle.charAt(cycle.length-1) == "D"){
								newcycle = cycle.replace("D", "天");	
							}else if(cycle.charAt(cycle.length-1) == "H"){
								newcycle = cycle.replace("H", "小时");	
							}else if(cycle.charAt(cycle.length-1) == "M"){	
								newcycle = cycle.replace("M", "分");
							}else if(cycle.charAt(cycle.length-1) == "S"){
								newcycle = cycle.replace("S", "秒");
							}
						}
						
						//获取sync_cycle
						cycle_temp.push(cycle);
						col_temp.push(elem.db_name);
						col5 +=elem.table_name+" : "+(elem.issync==1?newcycle:"未同步")+"<br/>";
						if(j>3){return;}//break 只显示前4条
						col5Short+=elem.table_name+" : "+(elem.issync==1?newcycle:"未同步")+"<br/>";
					});
					//数据处理，输出到页面
					col5='<span class="short">'+col5Short+'</span><span class="all">'+col5+'</span><a class="more">(更多)</a>';
					var col4 = unique(col_temp);
					var col4_html = "";
					for(var m =0;m<col4.length;m++){
						col4_html +=col4[m]+"<br/>";
					}
					html +="<td class='col4'>"+ col4_html +"</td>"+
						"<td class='col5'>"+ col5 +"</td>"+
						"<td class='col7'>"+
							"<span class='btn-a edit'>编辑</span>"+
							"<span class='btn-a delete' data-name=\""+item.device_tpl_desc+"\">删除</span>"+
						"</td>";
					$("tbody").append(html);
				});
				
			var t=0;
			<shiro:lacksPermission name="0102020801">
				$(".increase").attr("style","display:none;");
   			</shiro:lacksPermission>
   			<shiro:lacksPermission name="0102020802">
   			 	t++;
				$(".delete").attr("style","display:none;");
    		</shiro:lacksPermission>
    		<shiro:lacksPermission name="0102020803">
    			t++;
				$(".edit").attr("style","display:none;");
    		</shiro:lacksPermission>
    		if(t==2){
    			$(".col7").attr("style","display:none;");
    		}	
				
			var pageinfo = {
				"total":data.result.total,
				"page":data.result.page,
				"pageSize":data.result.pageSize,
			};
			$.pagination(pageinfo);
				//对于每一个评论中查看内容的超链接：
				 $("tbody").find("a.more").each(function(){
				  	//如果点击该链接则：
				  	$(this).on("click",function(){
					    //把不需要显示的内容隐藏，需要显示的内容展开。
					    $(this).parent().children('.all,.short').toggle();
					    //替换超链接的文字
					    if($(this).html()=='(更多)'){
					    	$(this).html('(收起)');
					    }else{
					    	$(this).html('(更多)');
					    } 
				  });
	 			});
			}
		});
		
	});
	
	var dbTabRightDivRet = "";
	var liLeft = "";
	var cycle_html = "";
	var dataTypeHtml = "";
	$(document).ready(function() {
   		$.ajax({
   		url:"${pageContext.request.contextPath}/metadevicetype/SelectType",
		type:"POST",
		async: false,//设置成同步
		success:function(data){
		var html="";
		$("#type").html("<option value='-1' selected>选择设备类型</option>");
			$.each(data.result,function(i,item){
				html +="<option value=\""+item.meta_devicetype_idx+"\">"+item.device_type_desc+"</option>";
				deviceTypeList[item.meta_devicetype_idx] = item;
            });
            $("#type").append(html);
   		}
    	});
   		dbTabRightDivRet = $("#rightHTMlDiv").html();
   		liLeft = $("#deviceDbList").html();
   		cycle_html = '<option value="-1">请选择</option>';
		cycle_html +='<option value="D">天</option>';
		cycle_html +='<option value="M">月</option>';
		cycle_html +='<option value="H">小时</option>';
		cycle_html +='<option value="S">秒</option>';
		
		dataTypeHtml = '<option value="-1">请选择</option>';
		dataTypeHtml +='<option value="zip">压缩文件</option>';
		dataTypeHtml +='<option value="txt">文本文件</option>';
 	});
	
	$("#rightHTMlDiv").delegate("#type","change",function(){
	//$("#type").on('change',function(){
		var id = $(this).val();
		if(id!="-1"){
			var item = deviceTypeList[id];
			var device_db_list = item.device_db_list;
			var device_logic_list = item.device_logic_list;
			var dbTabRightDiv = dbTabRightDivRet;
			var li = liLeft;
			$("#deviceDbList").html("");
			$("#rightHTMlDiv").html("");
			if(!_.isEmpty(device_db_list)){
				var list = device_db_list.split(",");
				for(i in list){
					//组装左侧
					li += "<li>"+list[i]+"</li>";
					//组装右侧
					var param={
						"db_name":list[i]
					};
					
					dbTabRightDiv += "<div class=\"right-content\" hidden>";
					
					$.ajax({
						url:"${pageContext.request.contextPath}/metadevicetype/selectMetadataDeviceDbAndTableInfo",
						data:{req:JSON.stringify(param)},
						async: false,//设置成同步
						type:"GET"
					}).done(function(data){
						if(data&&data.state&&data.result){
							var deviceDbTables=data.result;
							$.each(deviceDbTables,function(index,dbtab){
								dbTabRightDiv += "<div class=\"submenu-segmentation-line\">";
								dbTabRightDiv += "<div class=\"t\">"+dbtab.table_name+"</div>";
								dbTabRightDiv += "</div>";
								dbTabRightDiv += "<div class=\"form-wrap\">";
								dbTabRightDiv += "<ul>";
								dbTabRightDiv += "<li>";
								dbTabRightDiv += "<div class=\"left\">数据库名</div>";
								dbTabRightDiv += "<div class=\"right\">";
								dbTabRightDiv += "<div class=\"g-select\">";
								dbTabRightDiv += "<select class=\"db\">";
								dbTabRightDiv += "<option value=\"\" selected>"+dbtab.db_name+"</option>";
								dbTabRightDiv += "</select>";
								dbTabRightDiv += "<span class=\"arr-icon\"></span>";
								dbTabRightDiv += "</div>";
								dbTabRightDiv += "</div>";
								dbTabRightDiv += "</li>";
								dbTabRightDiv += "<li>";
								dbTabRightDiv += "<div class=\"left\">表名</div>";
								dbTabRightDiv += "<div class=\"right\">";
								dbTabRightDiv += "<div class=\"g-select\">";
								dbTabRightDiv += "<select class=\"table\">";
								dbTabRightDiv += "<option value=\"1\" >"+dbtab.table_name+"</option>";
								dbTabRightDiv += "<option value=\"-1\">请选择</option>";
								dbTabRightDiv += "</select>";
								dbTabRightDiv += "<span class=\"arr-icon\"></span>";
								dbTabRightDiv += "</div>";
								dbTabRightDiv += "</div>";
								dbTabRightDiv += "</li>";
								dbTabRightDiv += "<li>";
								dbTabRightDiv += "<div class=\"left\">&nbsp;</div>";
								dbTabRightDiv += "<div class=\"right\">";
								dbTabRightDiv += "<div class=\"g-checkbox\"><input type=\"checkbox\"  name=\"\" value=\"\" class=\"tongbu\" /></div><label for=\"tongbu\">是否同步</label>";
								dbTabRightDiv += "</div>";
								dbTabRightDiv += "</li>";
								dbTabRightDiv += "<li>";
								dbTabRightDiv += "<form action=\"\">";
								/* dbTabRightDiv += "<div class=\"left\">&nbsp;</div>"; */
								dbTabRightDiv += "<div class=\"right short-select\">";
								dbTabRightDiv += "<span class=\"fl\">";
								dbTabRightDiv += "同步周期";
								dbTabRightDiv += "<input type=\"text\" class=\"cycle-input g-input\" name=\"cycle-num\" value=\"\"/>";
								dbTabRightDiv += "</span>";
								dbTabRightDiv += "<div class=\"g-select\">";
								dbTabRightDiv += "<select class=\"cycle\">";
								dbTabRightDiv += "<option value=\"\">一天</option>";
								dbTabRightDiv += "</select>";
								dbTabRightDiv += "<div class=\"arr-icon\"></div>";
								dbTabRightDiv += "</div>";
								dbTabRightDiv += "</div>";
								dbTabRightDiv += "</form>";
								dbTabRightDiv += "</li>";
								
								dbTabRightDiv += "<li>";
								dbTabRightDiv += "<form action=\"\">";
								/* dbTabRightDiv += "<div class=\"left\">&nbsp;</div>"; */
								dbTabRightDiv += "<div class=\"right short-select\">";
								dbTabRightDiv += "<span class=\"fl\">";
								dbTabRightDiv += "同步方式";
								/* dbTabRightDiv += "<input type=\"text\" class=\"cycle-input g-input\" name=\"sync_type\" value=\"\"/>"; */
								dbTabRightDiv += "</span>";
								dbTabRightDiv += "<div class=\"g-select\">";
								dbTabRightDiv += "<select class=\"syncType\">";
								dbTabRightDiv += "<option value=\"\">请选择</option>";
								dbTabRightDiv += "</select>";
								dbTabRightDiv += "<div class=\"arr-icon\"></div>";
								dbTabRightDiv += "</div>";
								dbTabRightDiv += "</div>";
								dbTabRightDiv += "</form>";
								dbTabRightDiv += "</li>";
								
								dbTabRightDiv += "<li>";
								dbTabRightDiv += "<div class=\"left\">同步字段设置</div>";
								dbTabRightDiv += "<div class=\"right\">";
								dbTabRightDiv += "<div class=\"select-area\">";
								dbTabRightDiv += "<dl class=\"choose-area indexPermessions\">";
								
								if(dbtab.table_name.indexOf("soft_state")!= -1 || dbtab.table_name.indexOf("operation_log_")!= -1){
									if(indexPermessions != null){
										for(var i=0;i<indexPermessions.length;i++){
											dbTabRightDiv += "<dd><div class=\"g-checkbox\"><input type=\"checkbox\" value="+indexPermessions[i].opercmd+" class=\"fieldCheck\"></div>"+indexPermessions[i].opercmd_desc+"</dd>";
										}
									}
                                                                }else if(dbtab.table_name.indexOf("ext_state")!= -1){
									if(device_logic_list != null){
										var fields = device_logic_list.split(",");
										for(var i=0;i<fields.length;i++){
											dbTabRightDiv += "<dd><div class=\"g-checkbox\"><input type=\"checkbox\" value="+fields[i]+" class=\"fieldCheck\"></div>"+fields[i]+"</dd>";
										}
									}
								}else{
									if(dbtab.table_field_list != null){
										var fields = dbtab.table_field_list.split(",");
										for(var i=0; i<fields.length; i++){
											dbTabRightDiv += "<dd><div class=\"g-checkbox\"><input type=\"checkbox\" value="+fields[i]+" class=\"fieldCheck\"></div>"+fields[i]+"</dd>";
										}
									}
								}
							    dbTabRightDiv += "</dl>";
							    /* dbTabRightDiv += "<div class=\"handle-area\">";
							    dbTabRightDiv += "<div class=\"g-btn checkAll\">全选</div>";
							    dbTabRightDiv += "<div class=\"g-btn noCheckAll\">反选</div>";
							    dbTabRightDiv += "</div>"; */
							    dbTabRightDiv += "</div>";
							    dbTabRightDiv += "</div>";
							    dbTabRightDiv += "</li>";
							    dbTabRightDiv += "</ul>";
							    dbTabRightDiv += "</div>";
							});
						}
					});
					dbTabRightDiv += "</div>";
				}
			}
			$("#deviceDbList").html(li);
			$("#rightHTMlDiv").html(dbTabRightDiv);
			$("div.right-content .cycle").html(cycle_html); 
			$("div.right-content .syncType").html(dataTypeHtml); 
			$("#type").val(id);
		}
	});
	
	$("div.left-tab").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var thisIndex = $(this).index();
		$(this).addClass("active").siblings("li").removeClass("active");
		$("div.right-form .right-content").hide().eq(thisIndex).show();
		
	});
	
	//上一页操作
	$("#page").on("click",".prev-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size= $(".g-select.refresh").find("option:selected").html();
		var info = {	"page":page,
				"pageSize":size}; 
		//获取条件更新页面
		var search_type = $("#search_type").val();
		if(search_type == 1)
			info.device_tpl_ID = $("#search_info").val();
		if(search_type == 2)
			info.device_tpl_desc = $("#search_info").val();
			
		$.select(info);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $(".g-select.refresh").find("option:selected").html();
		var info = {	"page":page,
				"pageSize":size,}; 
		//获取条件更新页面
		var search_type =$("#search_type").val();
		if(search_type == 1)
			info.device_tpl_ID = $("#search_info").val();
		if(search_type == 2)
			info.device_tpl_desc = $("#search_info").val();
			
		$.select(info);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var size= $(".g-select.refresh").find("option:selected").html();
		var page = $(this).html();
		if(page=="...") return;
		var info={	"page":page,
				"pageSize":size,}; 
	 	//获取条件更新页面
		var search_type =$("#search_type").val();
		if(search_type == 1)
			info.device_tpl_ID = $("#search_info").val();
		if(search_type == 2)
			info.device_tpl_desc = $("#search_info").val();
			
		$.select(info);
	});
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var size= $('#showSize option:selected').html();
		var info={	"page":1,"pageSize":size}; 
	 	//获取条件更新页面
		var search_type =$("#search_type").val();
		if(search_type == 1)
			info.device_tpl_ID = $("#search_info").val();
		if(search_type == 2)
			info.device_tpl_desc = $("#search_info").val();
		$.select(info);
	}); 
	
	$(".checkAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,true);
	});
	$(".noCheckAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,false);
	});

	function allInputEach(container,toChecked){
		var $container = container || $(document);

		if(toChecked){
			$container.find("input[type='checkbox']").each(function(){
				if(!$(this).is(":checked")){
					$(this).prop("checked",true).parents(".g-checkbox").addClass("on");
				}
			});
		}else{
			$container.find("input[type='checkbox']").each(function(){
				$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on");
			});
		}

	}
});
</script>
</body>
</html>


