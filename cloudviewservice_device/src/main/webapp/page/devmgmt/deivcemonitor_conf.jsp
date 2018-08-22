<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style type="text/css">
.page-title-bar .g-select {
    float: left;
    margin-left: 10px;
    width: 130px;
    height: 28px;
    line-height: 28px;
    border: 1px solid #dddddd;
    border-radius: 2px;
    background-color: #f6f6f6;
}
  .pink{
  	background-color:#ffeeee
  }
/*   .form-wrap ul li .error-msg:before {
    content: "";
    position: absolute;
    left: -4px;
    top: 14px;
    width: 0;
    height: 0;
    border-right: 4px solid #ffdddd;
    border-top: 4px solid transparent;
    border-bottom: 4px solid transparent;
}
 .form-wrap ul li .error-msg {
    position: relative;
    left: 230px;
    top: -72px;
    width: 150px;
    height: 35px;
    text-align: center;
    line-height: 35px;
    border-radius: 2px;
    background-color: #ffdddd;
} */
/*  .form-wrap ul li .error-msgt:before {
    content: "";
    position: absolute;
    left: -4px;
    top: 14px;
    width: 0;
    height: 0;
    border-right: 4px solid #ffdddd;
    border-top: 4px solid transparent;
    border-bottom: 4px solid transparent;
} */
/*  .form-wrap ul li .error-msgt {
    position: relative;
    left: 230px;
    top: -35px;
    width: 150px;
    height: 35px;
    text-align: center;
    line-height: 35px;
    border-radius: 2px;
    background-color: #ffdddd;
} */
.w-900 .left-tab ul {
    padding: 20px 20px 0;
}
</style>
<div class="config-yingjian">
	<div class="">
		<div class="page-title-bar">
			<span class="title">设备监控模板配置<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/devmgmt/devicemonitor-config.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-select">
					<select id="device_type"></select>
					<span class="arr-icon"></span>
				</div>
				<input type="text" name="keyWord" id="" class="input g-input" placeholder="输入模板名称" />
				<div class="btn search">查询</div>
				<!-- 修改权限 by huanghuang 20170215 -->
				<shiro:hasPermission name="0102020501">
					<div class="btn increase">新增</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0102020502">
					<div class="btn delete">删除</div>
				</shiro:hasPermission>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="col2">模板ID</th>
						<th class="col3">名称</th>
						<th class="col4">设备类型</th>
						<th class="col5">模板内容</th>
						<th class="col6">操作</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
		<%@include file="../include/page_bar.jsf" %>
	</div>
</div>
<%--
	设备监控配置 编辑 显示页面 lbh 2016年5月18日14:13:56
--%>
<%@include file="../include/dev_mon_conf.jsf"%>


<div class="g-delete-dialog single">
	<span class="line"></span>
	<div class="word">
		是否要删除选择的配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>

<div class="g-delete-dialog batch">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red">7</span> 个项目<br>
		是否要删除选择的设备监控模板配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn batch-remove g-btn-red">删除</div>
</div>
<script src="${pageContext.request.contextPath}/static/plugins/layer/layer.js"></script>
<script type="text/javascript">
$(function(){
	/**假设一开始不是添加新的**/
	var isAddNew=false;
	/**
		格式：
		{
			"idx1":[....],"idx2":[....]
		}
		
	**/
	var deviceTypeIdxAndLogicObj=new Object();
	
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();

		$(".main").css({
			"min-height":winH - headerH - pagingBarH -pageTitleBar
		});
	})();
	/**
				<tr>
					<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
					<td>00001</td>
					<td>24小时自助借还机</td>
					<td>读者证阅读器</td>
					<td>读者证阅读器为荣瑞高频阅读器</td>
					<td>
						<span class="btn-a edit">编辑</span>
						<span class="btn-a delete">删除</span>
					</td>
				</tr>
	**/
	$tbody=$("div.main").find("tbody");
	//行
	var drawRow=function(rows){
		if(!rows)return;
		$tbody.html("");
		var trBody="";
		var perNum=0;
		for(var i=0;i<rows.length;i++){
			var row=rows[i];
			trBody+='<tr>';
			trBody+='<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>';
			trBody+='<td class="device_mon_tpl_idx hide">'+row.device_mon_tpl_idx+'</td>';//模板ID
			trBody+='<td class="device_mon_type_idx hide">'+row.device_mon_type_idx+'</td>';//模板ID
			trBody+='<td class="device_mon_tpl_id">'+row.device_mon_tpl_id+'</td>';//模板ID
			trBody+='<td class="device_mon_tpl_desc">'+row.device_mon_tpl_desc+'</td>';//模板名称
			trBody+='<td>'+row.device_type_desc+'</td>';//设备类型描述
			trBody+='<td>'+row.temp_content+'</td>';//模板内容
			trBody+='<td class="col6">';
			<shiro:hasPermission name="0102020503">
				trBody+='<span class="btn-a edit">编辑</span>';
				perNum++;
			</shiro:hasPermission>
			<shiro:hasPermission name="0102020502">
				trBody+='<span class="btn-a delete">删除</span>';
				perNum++;
			</shiro:hasPermission>
			trBody+='</td>';
			trBody+='</tr>';
		}
		$tbody.html(trBody);
		if(perNum==0){
			$(".col6").attr("style","display:none;");
		}else{
			$(".col6").attr("style","");
		}
	};
	
	//带参数 分页查询数据
	var selectDeviceMonConfPage=function(obj){
		$.ajax({
			url:"${pageContext.request.contextPath}/devicemonconf/queryDeviceMonitorByParam",
			type:"POST",
			data:{"req":JSON.stringify(obj)}
		}).done(function(data){
			if(data){
				var page=data.result;
				//console.log("selectDeviceMonConfPage:"+JSON.stringify(data));
				if(page.rows){
					if(page.rows.length==0&&page.page!=1){
						obj.page-=1;
						selectDeviceMonConfPage(obj);
						return;
					}
					drawRow(page.rows);
					$.pagination(page);
				}
			}
		});
	};
	//组装 翻页和查询 参数
	var makeQueryParam=function(page,pageSize){
	 	$inputText=$("div.page-title-bar").find("input[name=keyWord]");
	    var device_type=$("select#device_type").val();
		var keyWord=$inputText.val();
		var Page ={
			"page":page,
			"pageSize":pageSize,
			"device_type":device_type,
			"device_mon_tpl_desc":keyWord
		};
		//alert(keyWord);
		return Page;
	};
	var pageSize=10;//暂时
	//下一页操作
	$("div.paging-bar").on("click",".next-page",function(){
		var currentpage = $("#page").find("li.active").html();//当前页
		page = Number(currentpage) + 1;//下一页
		var Page=makeQueryParam(page, pageSize);
		selectDeviceMonConfPage(Page);
	});
	//上一页操作
	$("div.paging-bar").on("click",".prev-page",function(){
		var currentpage = $("#page").find("li.active").html();
		var page=Number(currentpage)-1;
		var Page=makeQueryParam(page, pageSize);
		//带参数
		selectDeviceMonConfPage(Page);
	});
	$("div.paging-bar").on("click","li",function(){
		if($(this).hasClass("active")) return;
		var page = $(this).html();
		if(page=="...") return;	
		var Page=makeQueryParam(page, pageSize);
		selectDeviceMonConfPage(Page);
	});
	var pageSize=10;
	var pageObj ={"page":1,"pageSize":pageSize};
	selectDeviceMonConfPage(pageObj);
	
	//SelectType获取设备类型
	$.ajax({
		url:"${pageContext.request.contextPath}/metadevicetype/SelectType",
		data:{},
		type:"GET",
		success:function(data){
			if(data&&data.state==true){
				var deviceTypeArr=data.result;
				$("select#device_type").html('<option value="" selected>选择设备类型</option>');
				for(var i=0;i<deviceTypeArr.length;i++){
					var deviceTypeObj=deviceTypeArr[i];
					//var device_type=deviceTypeObj.device_type;
					var device_type_desc=deviceTypeObj.device_type_desc;
					var opt='<option value="'+deviceTypeObj.meta_devicetype_idx+'">'+device_type_desc+'</option>';
					$("select#device_type").append(opt);
				}
				
				$("select.dialog-device-type").html('<option value="" selected>选择设备类型</option>');
				
				for(var i=0;i<deviceTypeArr.length;i++){
					var deviceTypeObj=deviceTypeArr[i];
					var device_type_desc=deviceTypeObj.device_type_desc;
					var opt='<option value="'+deviceTypeObj.meta_devicetype_idx+'">'+device_type_desc+'</option>';
					$("select.dialog-device-type").append(opt);
				}
			}
		}
	});
	
	/**
		取得是否报警
	**/
	var getAlert=function(divId){
		if($(divId).find(".right.level").find(".active").hasClass("alert")){//启动报警
			return 1;
		}else if($(divId).find(".right.level").find(".active").hasClass("error")){//不启动
			return 0;
		}
		return 0;
	};
	/**
		取得设置的颜色
	**/
	var getColor=function(divId){
		return $(divId).find("select.colorSelect").val();
	};
	/**
		取得报警限额
	**/
	var getThreshold=function(divId){
		return $(divId).find("input[name=threshold]").val();
	};
	/**
		消除错误提示
	**/
	$("input[name=threshold]").on("keydown",function(){
		//$(this).parents(".right").find(".error-msg").css("display","none");
		$(this).parents("li").removeClass("error");
		var removePink=true;
		$(this).parents(".right-content").find(".error-msg").each(function(index,dom){
			if($(dom).css("display")=="block"){
				//不消除li红色提示
				removePink=false;
			}
		});
		if(removePink){
			//消除pink颜色
			var dataId=$(this).parents(".right-content").attr("id");
			$("#monitor-left-tab").find("li[data-id="+dataId+"]").removeClass("pink");
		}
	});
	$("input[name=device_mon_tpl_id],input[name=device_mon_tpl_desc]").on("keydown",function(){
		//$(this).parents(".right").find(".error-msg").css("display","none");
		$(this).parents("li").removeClass("error");
		$("#monitor-left-tab").find("li.basic-info").removeClass("pink");
	});
	/**
		存储模板数据 {模板IDX：[数组]，模板IDX2：[数组2]},编辑更新的时候需要重新更新这个数据
	**/
	var editDateMap=new Object();
	
	/**检查是否输入非数字**/
	$("input[name=threshold]").on("keyup",function(){
		if($(this).val()&&!$.isNumeric($(this).val())){
			//layer.alert("只能输入整数");
			showErrorTips($(this),"只能输入整数");
			$(this).val("");
		}
	});
	//保存、更新 操作
	$(".form-dialog-fix.w-900").on("click","input[name=saveOrUpd]",function(){
		GlobalGLoading();
		//var thresholdIsNull=false;
		var num=-1;
		var thresholdIsNullLiArray=new Array();
		//验证操作
		$(".form-dialog-fix.w-900").find("input[name=threshold]").each(function(index,dom){
			 num++;
			 var color=$(dom).parents("ul").find("select.colorSelect").val();
			 if(color=="-1"){//-1的话没有选择颜色不用验证是否填写报警限额
			 	return;
			 }
			 if(!$(dom).val()){
			 	 //验证是否存在没有填写的报警限额
				 thresholdIsNull=true;
				 //$(dom).parents(".right").find(".error-msg").css("display","block");
				 showErrorTips($(dom),"超时设置不能为空");
				 var id=$(dom).parents(".right-content").attr("id");
				 thresholdIsNullLiArray.push(id);
				 return;
			 }
		});
		var fieldCheck=true;
		var device_mon_type_idx=$("#device_mon_tpl_idx").find("select.dialog-device-type").val();
		var device_mon_tpl_id_=$("#device_mon_tpl_idx").find("input[name=device_mon_tpl_id]").val();
		var device_mon_tpl_desc=$("#device_mon_tpl_idx").find("input[name=device_mon_tpl_desc]").val();
		if(device_mon_tpl_id_ && device_mon_tpl_id_.length>10){
			//layer.alert("模版ID不能超过10个字符");
			showErrorTips($("#device_mon_tpl_idx").find("input[name=device_mon_tpl_id]"),"模版ID不能超过10个字符");
			fieldCheck=false;
		}else if(!device_mon_tpl_id_){
			showErrorTips($("#device_mon_tpl_idx").find("input[name=device_mon_tpl_id]"),"模板ID不能为空");
			fieldCheck=false;
		}
		console.log("device_mon_type_idx",device_mon_type_idx=="");
		if(device_mon_type_idx==-1||device_mon_type_idx==""){
			showErrorSelectTips($("#device_mon_tpl_idx").find("select.dialog-device-type"),"请选择设备类型");
			fieldCheck=false;
		}
		if(!device_mon_tpl_desc){
			showErrorTips($("#device_mon_tpl_idx").find("input[name=device_mon_tpl_desc]"),"模板名称不能为空");
			fieldCheck=false;
		}else if(device_mon_tpl_desc.length>100){
			showErrorTips($("#device_mon_tpl_idx").find("input[name=device_mon_tpl_desc]"),"模板名称超过限定长度，请修改");
			fieldCheck=false;
		}	
		if(!fieldCheck){
			return;			
		}
		//提示信息
		if(thresholdIsNullLiArray.length>0){
			for(var i=0;i<thresholdIsNullLiArray.length;i++){
				var lidataId=thresholdIsNullLiArray[i];
				$("#monitor-left-tab").find("li[data-id="+lidataId+"]").addClass("pink");
				if(lidataId=="time_out"){
					layer.alert("超时设置不能为空");
				}
			}
			return;
		}
		var logicObjIdx={};
		$("#monitor-left-tab").find("li").each(function(index,dom){
			var dataId=$(dom).attr("data-id");
			if(dataId){
				logicObjIdx[dataId]=dataId;
			}
		});
		//新增保存操作 。isAddNew==true 并且 input[name=device_mon_tpl_idx] 不存在 值。
		var device_mon_tpl_idx=$(".form-dialog-fix.w-900").find("input[name=device_mon_tpl_idx]").val();
		//console.log("device_mon_tpl_idx:"+device_mon_tpl_idx);//新增的时候需要清除idx
		if(isAddNew==true&&!$(".form-dialog-fix.w-900").find("input[name=device_mon_tpl_idx]").val()){//新增操作
			console.log("新增");
			var checkData=true;
			var device_mon_tpl={};
			var devMonConfArr=new Array();
			//获取表单数据
			//需要对应 设备类型对应的外设部件  获取表单数据
			$(".form-dialog-fix.w-900").find(".right-form").find(".right-content").each(function(index,domEle){
				
				var idx=$(domEle).attr("id");//logic_idx
				var logicObjName=$(domEle).attr("logicObjName");
				
				//过滤 只取对应的idx
				if(idx!="device_mon_tpl_idx"){
					if(!logicObjIdx[idx]) return;//continue
				}
				if(idx=="device_mon_tpl_idx"){
					//等于则
					var device_mon_type_idx=$("#device_mon_tpl_idx").find("select.dialog-device-type").val();
					var device_mon_tpl_id=$("#device_mon_tpl_idx").find("input[name=device_mon_tpl_id]").val();
					var device_mon_tpl_desc=$("#device_mon_tpl_idx").find("input[name=device_mon_tpl_desc]").val();
					
					
					//这里需要判断 以上三个一定不能为空
					if(!device_mon_type_idx){
						layer.alert("模板类型不能为空");
						$("#monitor-left-tab").find("li.basic-info").addClass("pink");
						checkData=false;
						return;
					}
					if(!device_mon_tpl_id){
						$("#device_mon_tpl_idx").find("input[name=device_mon_tpl_id]").parents(".right").find(".error-msg").css("display","block");
						$("#monitor-left-tab").find("li.basic-info").addClass("pink");
						checkData=false;
						return;
					}
					if(!device_mon_tpl_desc){
						$("#monitor-left-tab").find("li.basic-info").addClass("pink");
						$("#device_mon_tpl_idx").find("input[name=device_mon_tpl_desc]").parents(".right").find(".error-msg").css("display","block");
						checkData=false;
						return;
					}
					//保存模板基本信息之后用于生成 device_mon_tpl_idx
					 device_mon_tpl={
							"device_mon_type_idx":device_mon_type_idx,
							"device_mon_tpl_id":device_mon_tpl_id,
							"device_mon_tpl_desc":device_mon_tpl_desc,
					};
					
				}else if(logicObjName=="PlcSSL"||logicObjName=="PlcSorter"||logicObjName=="PlcReturn"){
						var table_name="plc_logic_obj";
						$(domEle).find("ul").each(function(index,domElement){
							//ul  plc_logic_obj_idx
							var plcLogicObjIdx=$(domElement).attr("plcLogicObjIdx");
							var liAlert=getAlert(domElement);
							var liColor=getColor(domElement);
							var liThreshold=getThreshold(domElement);
							var meta_log_obj_idx=idx;
							if(!liThreshold){
								liThreshold=0;//没有在表单中显示的，但是数据库默认不能为空，所以设置默认值
							}
							var plcLogicObj={
									"logic_obj_idx":plcLogicObjIdx,
									"alert":liAlert,
									"color":liColor,
									"threshold":liThreshold,
									"table_name":table_name,
									"meta_log_obj_idx":meta_log_obj_idx,
									"visiable":1,//默认值
									"library_idx":0//模板用0
							};
							devMonConfArr.push(plcLogicObj);
						});
					
				}else if(logicObjName=="time_out"){//超时设置
					var threshold=getThreshold(domEle);
					if(!threshold){
						threshold=600;//如果跳过检查？默认设置600秒
					}
					var plcLogicObj={
							"logic_obj_idx":0,
							"alert":1,
							"color":0,
							"threshold":threshold,
							"table_name":"time_out",
							"meta_log_obj_idx":0,
							"visiable":1,//默认值
							"library_idx":0//模板用0
					};
					devMonConfArr.push(plcLogicObj);
				
				}else{
					var table_name="metadata_logic_obj";
					//则 idx=meta_log_obj_idx
					var alert=getAlert(domEle);
					var color=getColor(domEle);
					var meta_log_obj_idx=idx;
					var threshold=getThreshold(domEle);
					
					if(!threshold){
						threshold=0;//没有在表单中显示的，但是数据库默认不能为空，所以设置默认值
					}
					var metadataLogicObj={
							"logic_obj_idx":meta_log_obj_idx,
							"alert":alert,
							"color":color,
							"threshold":threshold,
							"table_name":table_name,
							"meta_log_obj_idx":meta_log_obj_idx, //用于区分PLC设备
							"visiable":1,
							"library_idx":0
					};
					
					if(logicObjName=="CardDispenser"){//3 CardDispenser//4 CashAcceptor
						metadataLogicObj.table_name="monitor_logic_obj";
						metadataLogicObj.logic_obj_idx="7";
					}else if(logicObjName=="CashAcceptor"){//3 CardDispenser//4 CashAcceptor
						metadataLogicObj.table_name="monitor_logic_obj";
						metadataLogicObj.logic_obj_idx="11";
					}
					devMonConfArr.push(metadataLogicObj);
				}
				//console.log(domEle);
			});
			if(!checkData) return;//检查不通过
			var insertDate={
					"device_mon_tpl":device_mon_tpl,//模板实体
					"dev_mon_conf_Arr":devMonConfArr   //device_monitor_config list 集合
			};
			$.ajax({
				url:"${pageContext.request.contextPath}/devicemonconf/AddMonitorConfTemp",
				data:{"req":JSON.stringify(insertDate)},
				type:"POST"
			}).done(function(data){
				//console.log(data);
				if(data&&data.state==true){
					//保存成功，执行后续
					//重新加载当前页
					showEditOrAddMsg("新增模板配置成功",true);
					var currentpage = $("div.paging-bar").find("li.active").html();//当前页
					var Page=makeQueryParam(Number(currentpage), pageSize);
					selectDeviceMonConfPage(Page);
				}else if(data.message=="FAILED"){
					showRetMessage(data.retMessage);
				}else if(data.retMessage){
					layer.alert(data.retMessage);
				}
			});
			
		}else if(isAddNew==false&&$(".form-dialog-fix.w-900").find("input[name=device_mon_tpl_idx]").val()){
			var devMonConfArr=new Array();
			//编辑 修改保存操作 
			var device_mon_tpl_idx=$(".form-dialog-fix.w-900").find("input[name=device_mon_tpl_idx]").val();
			var device_mon_type_idx=$("#device_mon_tpl_idx").find("select.dialog-device-type").val();
			var device_mon_tpl_id=$(".form-dialog-fix.w-900").find("input[name=device_mon_tpl_id]").val();
			var device_mon_tpl_desc=$(".form-dialog-fix.w-900").find("input[name=device_mon_tpl_desc]").val();
			$(".form-dialog-fix.w-900").find(".right-form").find(".right-content").each(function(index,domEle){
				var idx=$(domEle).attr("id");
				var logicObjName=$(domEle).attr("logicObjName");
				//过滤 只取对应的idx
				if(idx!="device_mon_tpl_idx"){
					if(!logicObjIdx[idx]) return;//continue
				}
				if(idx=="device_mon_tpl_idx"){
					// donothing
				}else if(logicObjName=="PlcSSL"||logicObjName=="PlcSorter"||logicObjName=="PlcReturn"){
					var table_name="plc_logic_obj";
					$(domEle).find("ul").each(function(index,domElement){
						//ul  plc_logic_obj_idx
						var plcLogicObjIdx=$(domElement).attr("plcLogicObjIdx");
						var liAlert=getAlert(domElement);
						var liColor=getColor(domElement);
						var liThreshold=getThreshold(domElement);
						var meta_log_obj_idx=idx;
						if(!liThreshold){
							liThreshold=0;//没有在表单中显示的，但是数据库默认不能为空，所以设置默认值
						}
						var plcLogicObj={
								"device_mon_tpl_idx":device_mon_tpl_idx,
								"logic_obj_idx":plcLogicObjIdx,
								"alert":liAlert,
								"color":liColor,
								"threshold":liThreshold,
								"table_name":table_name,
								"meta_log_obj_idx":meta_log_obj_idx,
								"visiable":1,
								"library_idx":0,
								"device_ext_type":0
						};
						devMonConfArr.push(plcLogicObj);
					});
				
			}else if(logicObjName=="time_out"){//超时设置
					var threshold=getThreshold(domEle);
					if(!threshold){
						threshold=600;//如果跳过检查？默认设置600秒
					}
					var plcLogicObj={
							"device_mon_tpl_idx":device_mon_tpl_idx,
							"logic_obj_idx":0,
							"alert":1,
							"color":0,
							"threshold":threshold,
							"table_name":"time_out",
							"meta_log_obj_idx":0,
							"visiable":1,//默认值
							"library_idx":0//模板用0
					};
					devMonConfArr.push(plcLogicObj);
			}else{
				var table_name="metadata_logic_obj";
				//则 idx=meta_log_obj_idx
				var alert=getAlert(domEle);
				var color=getColor(domEle);
				var meta_log_obj_idx=idx;
				var threshold=getThreshold(domEle);
				
				if(!threshold){
					threshold=0;//没有在表单中显示的，但是数据库默认不能为空，所以设置默认值
				}
				if(color=="-1"){//没有选择颜色的则跳过
					return;
				}
				var metadataLogicObj={
						"device_mon_tpl_idx":device_mon_tpl_idx,
						"logic_obj_idx":meta_log_obj_idx,
						"alert":alert,
						"color":color,
						"threshold":threshold,
						"table_name":table_name,
						"meta_log_obj_idx":meta_log_obj_idx, //用于区分PLC设备
						"visiable":1,
						"library_idx":0,
						"device_ext_type":0
				};
				if(logicObjName=="CardDispenser"){//3 CardDispenser//4 CashAcceptor
						metadataLogicObj.table_name="monitor_logic_obj";
						metadataLogicObj.logic_obj_idx="7";
					}else if(logicObjName=="CashAcceptor"){//3 CardDispenser//4 CashAcceptor
						metadataLogicObj.table_name="monitor_logic_obj";
						metadataLogicObj.logic_obj_idx="11";
				}
				devMonConfArr.push(metadataLogicObj);
			}
		});
		//上面的为循环体 结束 device_mon_tpl_id device_mon_tpl_desc
		
		var insertDate={
				"device_mon_tpl":{
					"device_mon_tpl_idx":device_mon_tpl_idx,
					"device_mon_type_idx":device_mon_type_idx,
					"device_mon_tpl_id":device_mon_tpl_id,
					"device_mon_tpl_desc":device_mon_tpl_desc
				},//模板实体IDX
				"dev_mon_conf_Arr":devMonConfArr   //device_monitor_config list 集合
		};
		//console.log("updateDate",insertDate);
		// library_idx 和 visiable 不能为空
		$.ajax({
			url:"${pageContext.request.contextPath}/devicemonconf/UpdMonitorConfTemp",
			data:{"req":JSON.stringify(insertDate)},
			type:"POST"
		}).done(function(data){
			//console.log(data);
			if(data&&data.state==true){
				//保存成功，执行后续
				//更新页面保存的数据
				editDateMap[device_mon_tpl_idx]=devMonConfArr;
				showEditOrAddMsg("更新模板配置成功",true);
				//重新加载当前页
				var currentpage = $("div.paging-bar").find("li.active").html();//当前页
				var Page=makeQueryParam(Number(currentpage), pageSize);
				selectDeviceMonConfPage(Page);
			}else if(data.message=="FAILED"){
				showRetMessage(data.retMessage);
			}else if(data.retMessage){
				layer.alert(data.retMessage);
			}
		});
	}
 });
	
	var delLayer = null;
	/**
		要删除的模板ID
	**/
	var delete_device_mon_tpl_idx="";
	//是否是批量删除 flag
	
	$("tbody").on("click",".delete",function(){
		//设置ID 
		//获取device_mon_tpl_idx 模板IDX
		delete_device_mon_tpl_idx=$.trim($(this).parents("tr").find(".device_mon_tpl_idx.hide").html());
		$(".g-delete-dialog").find(".word").html(
		'是否删除 <span class="font-red">'+$(this).parents("tr").find(".device_mon_tpl_id").html()+'</span>');
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
	
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	//单个删除
	$(".form-btn.remove").on("click",function(){
		var rowNum=$("tbody").find("tr").length;
		var device_mon_tpl={
				"device_mon_tpl_idx":delete_device_mon_tpl_idx
		};
		/*执行删除操作*/
		if(delete_device_mon_tpl_idx){
			$.ajax({
				url:"${pageContext.request.contextPath}/devicemonconf/DelMonitorConfTemp",
				data:{"req":JSON.stringify(device_mon_tpl)},
				type:"POST"
			}).done(function(data){
				//console.log(data);
				if(data){
					if(data.message=="FAILED"){
						 showMsg({
								timeout : 1500,
								showText : '删除失败，可能正在使用中',
								status : false
							});
					}else if(data.state==true){
						//删除成功
						//重新加载当前页,如果是当前页最后一条数据则可能会有问题。
						var currentpage = $("div.paging-bar").find("li.active").html();//当前页
						var Page={};
						if(rowNum==1&&currentpage>1){//不是第一页，最后一条数据则
							Page=makeQueryParam(Number(currentpage)-1, pageSize);
						}else{
							Page=makeQueryParam(Number(currentpage), pageSize);
						}
						selectDeviceMonConfPage(Page);
					}
				}
			});
		}
		if (delLayer) {
			layer.close(delLayer);
		}
		
	});
	

 
	var deleteFailId="";
	//执行批量删除之后
	var afterBatchRemove=function(){
				if(deleteFailId){
					var _showText='模板ID '+deleteFailId+' 删除失败，可能正在使用中';
					showMsg({timeout : 2000,showText : _showText,status : false});
				}else{
					showMsg({timeout : 1500,showText : '删除完成',status : true});
				}
				//重新加载当前页,如果是当前页最后一条数据则可能会有问题。
				var currentpage = $("div.paging-bar").find("li.active").html();//当前页
				var Page=makeQueryParam(Number(currentpage), pageSize);
				selectDeviceMonConfPage(Page); 
	};
	
	var batchRemoveClick=function(){
		deleteFailId="";
		var length=$("tbody").find("td.col1").find(".g-checkbox.on").length;
		var num=0;
		$("tbody").find("td.col1").find(".g-checkbox.on").each(function(index,eleDom){
				var device_mon_tpl_idx=$(eleDom).parents("tr").find("td.device_mon_tpl_idx").html();
				var tempId=$(eleDom).parents("tr").find("td:eq(3)").html();
					//另外的删除条件是 library_idx=0 device_type=0
				var device_mon_tpl={
						"device_mon_tpl_idx":device_mon_tpl_idx
				};
				//存在外键的关系，有可能部分删除会失败
				$.ajax({
					url:"${pageContext.request.contextPath}/devicemonconf/DelMonitorConfTemp",
					data:{"req":JSON.stringify(device_mon_tpl)},
					type:"POST"
				}).done(function(data){
					num++;//第一个
					if(data){
						if(data.message=="FAILED"){
							deleteFailId+=tempId+",";
							//afterBatchRemove(tempId);
						}else if(data.state==true){
								
						}
					}
				}).fail(function(data){
					num++;//第一个
				});
			});
			//关闭弹出层
			if (delLayer) {
				layer.close(delLayer);
			}
			//采用周期循环处理
			var Interval=setInterval(function(){
				if(num>=length){
					clearInterval(Interval);//清除
					afterBatchRemove();
					return;
				}
			},500);
	
	};

	/**
		批量删除 确认
	**/
	$(".form-btn.batch-remove").on("click",function(){
		batchRemoveClick();
	});
	  
	/**
	 ** 点击 批量删除 按钮
	 **/
	$("div.page-title-bar").on("click",".delete",function(){
		var delNum=$("tbody").find("td.col1").find(".g-checkbox.on").length;
		if(!delNum||delNum==0){
			layer.alert("请选择要删除的模板");
			return;
		}
		//设置项目数显式
		//$(".g-delete-dialog.batch").find("span.font-red").html(delNum);
		$(".g-delete-dialog.batch").find(".word").html(
		'当前选择了'+ '<span class="font-red">'+delNum+'</span>个项目<br>'+
		'是否要删除选择的设备监控模板配置？');
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
			content: $('.g-delete-dialog.batch')
		});
	});
	
	
	//点击新增按钮
	$(".increase").on("click",function(){
		
		//清除tips
		$(".form-dialog").find("li").removeClass("error");
	
		//点击新增的时候 设置 isAddNew=true
		isAddNew=true;
		//清除左侧li数据
		$("#monitor-left-tab").find("li:not(.basic-info)").remove();
		
		//先全部清空选择过的数据
		//模板IDX清除
		$(".form-dialog-fix.w-900").find("input[name=device_mon_tpl_idx]").val("");
		//清除模板数据
		$("select.dialog-device-type").val(-1);
		$("input[name=device_mon_tpl_id]").val("");
		$("input[name=device_mon_tpl_desc]").val("");
		
		//限额
		$("div.right-form").find("input[name=threshold]").each(function(index,dom){
			$(dom).val("");//清空数据
		});
		//颜色
		$("div.right-form").find("select.colorSelect").each(function(index,dom){
			$(dom).val("-1");
		});
		//是否启动（默认启动）
		$("div.right-form").find(".alert.btn").each(function(index,dom){
			$(dom).trigger("click");
		});
		
		//显示  新增模板基本信息填写
		$basicInfoLi=$(".form-dialog-fix.w-900").find("div.left-tab").find("ul").find(".basic-info");
		$basicInfoLi.removeClass("hide");
		$basicInfoLi.trigger("click");
		$(".right-form #device_mon_tpl_idx").removeClass("hide");
		$(".form-dialog-fix.w-900").find(".title .char").html("新增配置");
		$(".form-dialog-fix.w-900").find("#device_mon_tpl_idx").removeClass("hide");
		$(".form-dialog-fix.w-900").fadeIn(100);
		$(".form-dialog-fix.w-900").find(".form-dialog").animate({
			"right":0
		});

		
	});
	
	
	/**
		点击编辑按钮，查询数据库数据 ，回填
		如果有一些数据是没的情况，设置为默认值
	**/
	$tbody.on("click",".edit",function(){
		//清除tips
		$(".form-dialog").find("li").removeClass("error");
		//点击编辑按钮的时候设置 isAddNew=false
		isAddNew=false;
		//获取device_mon_tpl_idx 模板IDX
		var device_mon_tpl_idx=$.trim($(this).parents("tr").find(".device_mon_tpl_idx.hide").html());
		var device_mon_type_idx=$.trim($(this).parents("tr").find(".device_mon_type_idx.hide").html());
		var device_mon_tpl_id=$.trim($(this).parents("tr").find(".device_mon_tpl_id").html());
		var device_mon_tpl_desc=$.trim($(this).parents("tr").find(".device_mon_tpl_desc").html());
		//设置左边列表显示对应的外设部件列表
		$("select.dialog-device-type").val(device_mon_type_idx).trigger("change");
		
		var reqObj={
			"device_mon_tpl_idx":device_mon_tpl_idx
		};
		//页面数据存在则
		if(editDateMap[device_mon_tpl_idx]){
			$(".form-dialog-fix.w-900").find("input[name=device_mon_tpl_idx]").val(device_mon_tpl_idx);
			editBtnDrawData(editDateMap[device_mon_tpl_idx]);
		}else{
			//从数据库取出数据
			$.ajax({
				url:"${pageContext.request.contextPath}/devicemonconf/SelDevMonConfMetaLogObjByDeviceMonTplIdx",
				data:{"req":JSON.stringify(reqObj)},
				type:"POST"
			}).done(function(data){
				if(data&&data.state==true){
					//设置 device_mon_tpl_idx
					$(".form-dialog-fix.w-900").find("input[name=device_mon_tpl_idx]").val(device_mon_tpl_idx);
					//回填数据
					//console.log(data);
					var resultArr=data.result;
					editDateMap[device_mon_tpl_idx]=resultArr;
					editBtnDrawData(resultArr);
				}
			});
		}
		$(".form-dialog-fix.w-900").find("input[name=device_mon_tpl_id]").val(device_mon_tpl_id);
		$(".form-dialog-fix.w-900").find("input[name=device_mon_tpl_desc]").val(device_mon_tpl_desc);
		//隐藏  新增模板基本信息填写
		$basicInfoLi=$(".form-dialog-fix.w-900").find("div.left-tab").find("ul").find(".basic-info");
		//$basicInfoLi.addClass("hide");
		$basicInfoLi.removeClass("hide");
		//$basicInfoLi.next().trigger("click");//点击下一个li
		$basicInfoLi.trigger("click");
		//$(".form-dialog-fix.w-900").find("#device_mon_tpl_idx").addClass("hide");
		//填充模板ID模板名称
		
		$(".form-dialog-fix.w-900").find(".title .char").html("编辑配置");
		$(".form-dialog-fix.w-900").fadeIn(100);
		$(".form-dialog-fix.w-900").find(".form-dialog").animate({
			"right":0
		});
	});
	
	/**
		点击 编辑按钮 后需要执行的函数，回填数据
	**/
	var editBtnDrawData=function(resultArr){
			//先全部清空选择过的数据
			$("div.right-form").find("input[name=threshold]").each(function(index,dom){
				$(dom).val("");//清空数据
			});
			$("div.right-form").find("select.colorSelect").each(function(index,dom){
				$(dom).val("-1");
			});
			$("div.right-form").find(".alert.btn").each(function(index,dom){
				$(dom).trigger("click");
			});
			
			for(var i=0;i<resultArr.length;i++){
					var result=resultArr[i];
					var alert=result.alert;
					var color=result.color;
					var logic_obj_idx=result.logic_obj_idx;
					var meta_log_obj_idx=result.meta_log_obj_idx;
					var table_name=result.table_name;
					var threshold=result.threshold;
					
					if(table_name=="metadata_logic_obj"||table_name=="monitor_logic_obj"){
						//设置是否启动
						if(alert=="1"){//启动
							$("#"+meta_log_obj_idx).find(".right.level").find(".alert").trigger("click");
						}else{
							$("#"+meta_log_obj_idx).find(".right.level").find(".error").trigger("click");
						}
						//设置颜色
						$("#"+meta_log_obj_idx).find("select.colorSelect").val(color);
						//设置限额
						$("#"+meta_log_obj_idx).find("input[name=threshold]").val(threshold);
					}else if(table_name=="plc_logic_obj"){
						//PLC 还书机 24小时借还设备 捡书机
						//获取对应 部件的UL
						$ul=$("#"+meta_log_obj_idx).find("ul[plclogicobjidx="+logic_obj_idx+"]");
						
						if(alert=="1"){//启动
							$ul.find(".right.level").find(".alert").trigger("click");
						}else{
							$ul.find(".right.level").find(".error").trigger("click");
						}
						//设置颜色
						$ul.find("select.colorSelect").val(color);
						//设置限额
						$ul.find("input[name=threshold]").val(threshold);
						
					}else if(table_name=="time_out"){
						//超时设置
						$("#time_out").find("input[name=threshold]").val(threshold);
					}
				}
	};
	
	
	
	
	$(".form-dialog .form-wrap  .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	});
	
	<%-- 
		<li>必须和右侧内容的<div class="right-content">顺序一一对应，否则点击菜单对应的内容不一致
	--%>
	$("#monitor-left-tab").on("click","li",function(){
		//data-id="time_out
		if($(this).attr("data-id")=="time_out"){
			$(".form-dialog-fix.w-900").find(".right-content").hide();
			$(".form-dialog-fix.w-900").find("div[logicObjName=time_out]").show();
			$(this).addClass("active").siblings("li").removeClass("active");
			
		}else{
			var thisIndex=$(this).attr("data-id");
			if(!thisIndex) 
				thisIndex= $(this).index();
			$(this).addClass("active").siblings("li").removeClass("active");
// 			$(".form-dialog-fix.w-900").find(".right-content").hide().eq(thisIndex).show();
			if(thisIndex == 0) {
				$(".form-dialog-fix.w-900").find(".right-content").hide().eq(thisIndex).show();
			}else{
				$(".form-dialog-fix.w-900").find(".right-content").hide();
				$("#"+thisIndex).show();
			}

		}
	});

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
	/**
		 获得配置的颜色信息
	**/
	var colors;
	$.ajax({
		url:"${pageContext.request.contextPath}/devicemonconf/queryMonitorColorConf",
		data:{},
		type:"POST"
	}).done(function(data){
		if(data&&data.state==true){
			var result=data.result;
			colors=result;
			//console.log("colors:"+JSON.stringify(colors));
			if(colors){
				var selectHtml='<option value="-1" selected>请选择</option>';
				for(var i=0;i<colors.length;i++){
					var color=colors[i];
					selectHtml+='<option value="'+color.colorId+'">'+color.colorCN+'</option>';
				}
				$("select.colorSelect").html(selectHtml);
				$("select.colorSelect").val("-1");//默认设置,没有选择颜色，则表示没有选择这个逻辑设备
			}
		}
	});
	
	/**
		查询   设备类型 、模板名称
	**/
	$("div.btn.search").on("click",function(){
		
		//重新加载当前页,如果是当前页最后一条数据则可能会有问题。
		var currentpage = $("div.paging-bar").find("li.active").html();//当前页
		if(!currentpage){//这里需要判断，否则可能报错
			currentpage=1;
		}
		var Page=makeQueryParam(Number(currentpage), pageSize);
		selectDeviceMonConfPage(Page);
		
		console.log("查询按钮");
	});
	
	function selectChange(){
		/*页面加载后，若select已经有值，先将对应的DIV显示*/
		$(".need-change").each(function(){
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
	}
	selectChange();
	
	
	var showEditOrAddMsg=function(showText,state,timeout){
		showMsg({
				timeout : timeout,
				showText : showText,
				status : state,
				callback:function(){ 
					if(state){
						/*应该是成功的时候收回吧，酌情处理*/
						/*失败的情况下是不会收回去的*/
						var thisRight =  $(".form-dialog-fix.w-900").find(".form-dialog").attr("date-right");
						  $(".form-dialog-fix.w-900").find(".form-dialog").animate({"right":thisRight},
						  function(){
						  $(".form-dialog-fix.w-900").fadeOut(100);
						});
					}
				}
			});
	};

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
	
	
	
	<%-- 
		动态 显示 设备类型对应的外设逻辑   开始
	--%>
	/**
	根据设备类型 获取对应的  metadata_logic_obj
	数据格式 为：
		{
			[
				{
					metadataDeviceType,
					[metadata_logic_obj1,metadata_logic_obj2]
				},
				{
					.....
				}
			]
		}
	**/
	var queryDeviceTypeLogicObj=function(){
		$.ajax({
			url:"${pageContext.request.contextPath}/metadevicetype/queryDeviceTypeLogicObj",
			type:"GET",
			data:{"req":JSON.stringify()}
		}).done(function(data){
			//console.log("queryDeviceTypeLogicObj",data);
			if(data&&data.state){
				var metadataDeviceTypeAndLogicObjs=data.result;
				for(var i=0;i<metadataDeviceTypeAndLogicObjs.length;i++){
					var metadataDeviceTypeAndLogicObj=metadataDeviceTypeAndLogicObjs[i];
					var metadataDeviceType=metadataDeviceTypeAndLogicObj.metadataDeviceType;
					var meta_devicetype_idx=metadataDeviceType.meta_devicetype_idx;
					var metadataLogicObjsArr=metadataDeviceTypeAndLogicObj.metadataLogicObjs;
					deviceTypeIdxAndLogicObj[meta_devicetype_idx]=metadataLogicObjsArr;
				}
				//console.log("deviceTypeIdxAndLogicObj",deviceTypeIdxAndLogicObj);
			}
		});
	};
	queryDeviceTypeLogicObj();
	
	<%--字符串转JSON格式--%>
	function jsonToObj(str){ 
		try{
			return JSON.parse(str); 
		}catch(e){
			return "";
		}
	} 	
	/**
		新增设置的时候 设置
		选择设备类型的切换外设逻辑或者是PLC
	**/
	$("select.dialog-device-type").on("change",function(){
		//clear error message
		$(".g-select").parents("li").removeClass("error");
	
		//return;
		$("#monitor-left-tab").find("li:not(.basic-info)").remove();
		var meta_devicetype_idx=$(this).val();
		if(deviceTypeIdxAndLogicObj[meta_devicetype_idx]){
			var metadataLogicObjsArr=deviceTypeIdxAndLogicObj[meta_devicetype_idx];
			var li='';
			//<li data-id="${metadataLogicObject.meta_log_obj_idx}">${metadataLogicObject.logic_obj_desc}</li>
			for(var i=0;i<metadataLogicObjsArr.length;i++){
				var metadataLogicObj=metadataLogicObjsArr[i];
				var lang_logic_obj_desc=jsonToObj(metadataLogicObj.logic_obj_desc);
				li+='<li data-id="'+metadataLogicObj.meta_log_obj_idx+'">'+lang_logic_obj_desc.zh_CN+'</li>';
			}
			//li 需要增加超时设置
			li+='<li data-id="time_out">超时设置</li>';
			$("#monitor-left-tab").find("ul").append(li);
		}
	});
	<%-- 
		动态 显示 设备类型对应的外设逻辑  结束
	--%>
	$("select#showSize").on("click",function(){
		var size=$(this).val();
		pageSize=size;
		var Page=makeQueryParam(1, pageSize);
		selectDeviceMonConfPage(Page);
	});
	
	
});
</script>
