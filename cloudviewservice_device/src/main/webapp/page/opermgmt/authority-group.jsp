<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%--修正最后一个checkbox不显示的问题--%>
<style>
.form-dialog-2 .select-area{
	height: 340px;
}
.col6{
	width: 10%;
}
</style>

<div class="authority-fenzu">
<div class="g-loading"></div>

	<div class="">
		<div class="page-title-bar">
			<span class="title">权限分组<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/opermgmt/authority.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<%-- <div class="g-select">
					<select>
						<option value="-1" selected="">选择类型</option>
						<option value="">类型1</option>
					</select>
					<span class="arr-icon"></span>
				</div> --%>
				<input type="text" name="servGroupName" id="" class="input g-input" placeholder="输入组名" />
				<div class="btn search">查询</div>
				<!-- 修改权限 by huanghuang 20170215 -->
				<shiro:hasPermission name="0101010401">
					<div class="btn increase g-btn-green">新增</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0101010402">
					<div class="btn delete-batch delete">删除</div>
				</shiro:hasPermission>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="col2">组ID</th>
						<th class="col3">组名</th>
						<th class="col6">图书馆</th>
						<th class="col4">权限内容</th>
						<th class="col5">操作</th>
					</tr>
				</thead>
				<tbody>
				<%--
					<tr>
						<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
						<td class="col2">0001</td>
						<td class="col3">朝阳区图书馆</td>
						<td class="col4">系统管理、设备管理、用户管理</td>
						<td class="col5">
							<span class="btn-a edit">编辑</span>
							<span class="btn-a delete">删除</span>
						</td>
					</tr>
				 --%>
				</tbody>
			</table>
		</div>
	   <%@include file="../include/page_bar.jsf"%>
	</div>
	<%@ include file="add-authority-div.jsp" %>
	<%@ include file="edit-authority-div.jsp" %>
</div>
<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red">7</span> 个项目<br>
		是否要删除选择的配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>

<script src="${pageContext.request.contextPath}/static/plugins/layer/layer.js"></script>
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
	var authorityGroup={};
	authorityGroup.operator = <shiro:principal/>;
    /**
		对应用户的图书馆数据
	**/
	var libIdAndNameObj={};
	var libIdxAndNameObj={};
	$tbody=$("div.main").find(".g-table").find("tbody");
	/**
		<tr>
			<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
			<td class="col2">0001</td>
			<td class="col3">朝阳区图书馆</td>
			<td class="col4">系统管理、设备管理、用户管理</td>
			<td class="col5">
				<span class="btn-a edit">编辑</span>
				<span class="btn-a delete">删除</span>
			</td>
		</tr>
	**/
	authorityGroup.drawRow=function(rows){
		if(!rows)return;
		var tbody='';
		var perNum=0;
		for(var i=0;i<rows.length;i++){
			var tr='<tr>';
			var row=rows[i];
			tr+='<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>';
			tr+='<td class="service_group_idx hide">'+row.service_group_idx+'</td>';
			tr+='<td class="library_idx hide">'+row.library_idx+'</td>';
			tr+='<td class="opercmd_str hide">'+row.opercmd_str+'</td>';
			tr+='<td class="meta_opercmd_idx_str hide">'+row.meta_opercmd_idx_str+'</td>';
			tr+='<td class="service_group_desc hide">'+row.service_group_desc+'</td>';
			tr+='<td class="col2 service_group_id">'+row.service_group_id+'</td>';
			tr+='<td class="col3">'+row.service_group_name+'</td>';
			if(libIdxAndNameObj&&libIdxAndNameObj[row.library_idx]){
				tr+='<td class="col6 libName">'+libIdxAndNameObj[row.library_idx].lib_name+'</td>';
			}else{
				tr+='<td class="col6 libName">'+row.library_idx+'</td>';
			}
			tr+='<td class="col4">'+row.opercmd_desc_str+'</td>';
			tr+='<td class="col5">';
			<shiro:hasPermission name="0101010403">
				tr+='<span class="btn-a edit">编辑</span>';
				perNum++;
			</shiro:hasPermission>
			<shiro:hasPermission name="0101010402">
				tr+='<span class="btn-a delete">删除</span>';
				perNum++;
			</shiro:hasPermission>
			tr+='</td>';
			tr+='<td class="version_stamp hide">'+row.version_stamp+'</td>';
			tr+='</tr>';
			tbody+=tr;
		}
		$tbody.html(tbody);
		if(perNum==0){
			$(".col5").attr("style","display:none;");
		}else{
			$(".col5").attr("style","");
		} 
	};
	
	/**
		带参数分页查询
	**/
	authorityGroup.selectServGroupPage=function(obj){
		if(authorityGroup.operator.operator_type>=3)
			obj.library_idx = authorityGroup.operator.library_idx;
		GetlibIdAndNameOBJ(function(){
			$.ajax({
				url:"${pageContext.request.contextPath}/servgroup/queryServgroupByparam",
				type:"POST",
				data:{"req":JSON.stringify(obj)}
			}).done(function(data){
				if(data){
					var page=data.result;
					//console.log("selectServGroupPage:"+JSON.stringify(data));
					if(page){
						 if(page.rows.length==0&&page.page!=1){
							obj.page-=1;
							authorityGroup.selectServGroupPage(obj);
							return;
						 }
						authorityGroup.drawRow(page.rows);
						$.pagination(page);
					}
				}
			});
		});
	};
	authorityGroup.pageSize=10;
	authorityGroup.Page={"page":1,"pageSize":authorityGroup.pageSize};
	authorityGroup.selectServGroupPage(authorityGroup.Page);
	//组装 翻页和查询 参数
	authorityGroup.makeQueryParam=function(page,pageSize){
		var servGroupName=$("input[name=servGroupName]").val();
		var Page ={
			"page":page,
			"pageSize":pageSize,
			"service_group_name":servGroupName
		};
		//alert(keyWord);
		return Page;
	};
	//下一页操作
	$("div.paging-bar").on("click",".next-page",function(){
		var currentpage = $("#page").find("li.active").html();//当前页
		page = Number(currentpage) + 1;//下一页
		var Page=authorityGroup.makeQueryParam(page, authorityGroup.pageSize);
		authorityGroup.selectServGroupPage(Page);
	});
	//上一页操作
	$("div.paging-bar").on("click",".prev-page",function(){
		var currentpage = $("#page").find("li.active").html();
		var page=Number(currentpage)-1;
		var Page=authorityGroup.makeQueryParam(page, authorityGroup.pageSize);
		//带参数
		authorityGroup.selectServGroupPage(Page);
	});
	$("div.paging-bar").on("click","li",function(){
		if($(this).hasClass("active")) return;
		var page = $(this).html();
		if(page=="...") return;	
		var Page=authorityGroup.makeQueryParam(page, authorityGroup.pageSize);
		authorityGroup.selectServGroupPage(Page);
	});
	
	/**
	 	获取并整理权限的关系
	**/
	var indexPermessions={};
	var indexPermessionsNoDuplicate=new Array();
	var permessions={};
	//一级权限 一对多
	var relPermession=new Object();
	//查询一级权限
	$.ajax({
		url:"${pageContext.request.contextPath}/servgroup/selectCmdType",
		data:{},
		type:"GET"
	}).then(function(data){
		if(data&&data.state==true){
			indexPermessions=data.result;
			//查询权限,仅限登陆用户所拥有的权限，这个operator判断可以放在后台其实
			permessions=authorityGroup.operator["userRolePermessions"];
			if(indexPermessions&&permessions){
			//indexPermessions 会存在重复的内容
			for(var j=0;j<indexPermessions.length;j++){
				if(relPermession[indexPermessions[j].opercmd]){
					continue;
				}
				//因为新的命令码有的是十位数，所以截取的字段需要改变当为8位时，截取4位，当为10位时，截取6位，modify by huanghuang 20170216
				//var indexCmd=indexPermessions[j].opercmd.substr(0,4);
				var indexCmd= 0;
				if(indexPermessions[j].opercmd.length==8){
					indexCmd=indexPermessions[j].opercmd.substr(0,4);
				}else if(indexPermessions[j].opercmd.length==10){
					indexCmd=indexPermessions[j].opercmd.substr(0,6);
				}
				var arr=new Array();
				for(var i=0;i<permessions.length;i++){
					if(indexCmd=="0001"||indexCmd=="0003"){//设备端操作权限
						var permes=permessions[i].opercmd.substr(0,4);
						if(permes=="0001"||permes=="0003"){
							arr.push(permessions[i]);
						}
					}else{
						//现在数据库中存在的问题是 同一个权限类型下的 权限码不统一前缀 问题如上
						if(indexCmd==permessions[i].opercmd.substr(0,6)){
							arr.push(permessions[i]);
						}
					}
			 	}
			 	//判断一级权限目录 是否包含 所拥有的权限
			 	//只有拥有权限 才会显示该权限的一级权限
			 	if(arr.length>0){
			 		relPermession[indexPermessions[j].opercmd]=arr;
			 		indexPermessionsNoDuplicate.push(indexPermessions[j]);
			 	}
			}
		}
		}
	});
	/**
		查询 按钮
	**/
	$(".btn.search").on("click",function(){
		var page=1;
		var Page=authorityGroup.makeQueryParam(page,authorityGroup.pageSize);
		authorityGroup.selectServGroupPage(Page);
	});
	
	/**
		点击删除按钮
	**/
	var delLayer = null;
	$tbody.on("click",".delete",function(){
		authorityGroup.isDeleteOne=true;
		authorityGroup.delService_group_idx=$.trim($(this).parents("tr").find(".service_group_idx.hide").html());
		authorityGroup.dellibrary_idx=$.trim($(this).parents("tr").find(".library_idx.hide").html());
		authorityGroup.version_stamp=$.trim($(this).parents("tr").find(".version_stamp").html());
		//$(".g-delete-dialog").find(".font-red").html(1);
		$(".g-delete-dialog").find(".word").html(
		'是否删除 <span class="font-red">'+$(this).parents("tr").find(".service_group_id").html()+'</span>');
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
	/**
		点击批量删除按钮
	**/
	$(".btn.delete-batch").on("click",function(data){
		authorityGroup.isDeleteOne=false;
		//array
		authorityGroup.delSelelcted=$tbody.find("input[type=checkbox]:checked");
		if(authorityGroup.delSelelcted.length<=0){
			layer.alert("请选择要删除的权限组");
			return;
		}
		
		$(".g-delete-dialog").find(".word").html('当前选择了 <span class="font-red">'+authorityGroup.delSelelcted.length+'</span> 个项目<br>是否要删除选择的权限组数据？');
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
	/**
		点击 编辑按钮 
	**/
	authorityGroup.updateDialog={};
	$tbody.on("click",".edit",function(){
		var opercmd_str=$.trim($(this).parents("tr").find("td.opercmd_str.hide").html());
		var meta_opercmd_idx_str=$.trim($(this).parents("tr").find("td.meta_opercmd_idx_str.hide").html());
		var service_group_id=$.trim($(this).parents("tr").find("td.col2").html());
		var service_group_name=$.trim($(this).parents("tr").find("td.col3").html());
		var libName=$.trim($(this).parents("tr").find("td.col6.libName").html());
		var service_group_desc=$.trim($(this).parents("tr").find("td.service_group_desc").html());
		var library_idx=$.trim($(this).parents("tr").find("td.library_idx.hide").html());
		var version_stamp=$.trim($(this).parents("tr").find("td.version_stamp").html());
		//当前编辑的library_idx
		authorityGroup.current_edit_library_idx=$.trim($(this).parents("tr").find("td.library_idx.hide").html());
		//当前编辑的service_group_idx
		authorityGroup.current_edit_service_group_idx=$.trim($(this).parents("tr").find("td.service_group_idx.hide").html());
		authorityGroup.updateDialog=layer.open({
			type: 1, 
			content: $("#edit-authority-div"),
			title :false,
			closeBtn :1,
			area :["860px","660px"],
			shade:0.5,
			shadeClose :false,
			scrollbar :false,
			move:false,
			skin:false,
			success :function(layero, index){
				$("#edit-authority-div .title").text("编辑分组");
				$("#edit-authority-div .g-submit").val("保存").removeClass("g-btn-green").addClass("g-btn-blue");
				$("#edit-authority-div").find(".choose-area.indexPermessions").html("");	
				$("#edit-authority-div").find(".choose-area.permessions").html("");	
				$("#edit-authority-div").find("input[name=auth-group-id]").val(service_group_id);
				$("#edit-authority-div").find("input[name=auth-group-name]").val(service_group_name);
				$("#edit-authority-div").find("textarea[name=auth-group-desc]").val(service_group_desc);
				$("#edit-authority-div").find("input[name=version_stamp]").val(version_stamp);
				if(libIdxAndNameObj&&libIdxAndNameObj[library_idx]){
					$("#edit-authority-div").find("input[name=lib_id]").val(libIdxAndNameObj[library_idx].lib_id);
				}
				$("#edit-authority-div").find("input[name=lib_name]").val(libName);
				$("#edit-authority-div").find("input[name=lib_id]").prop("readonly","").prop("style","");
				if(authorityGroup.operator.operator_type>=3){<%--图书馆管理员或以下角色--%>
					$("#edit-authority-div").find("input[name=lib_id]").prop("readonly","readonly").prop("style","color:gray;background-color:#E0E0E0");
				}else if(authorityGroup.operator.operator_type<3){<%--云平台系统管理员--%>
				}
				
				
				if(indexPermessions&&permessions){
					//加载一级权限菜单
					var dl='';
					for(var i=0;i<indexPermessionsNoDuplicate.length;i++){
						dl+='<dd><div class="g-checkbox"><input type="checkbox" value="'+indexPermessionsNoDuplicate[i].opercmd+'"></div>'+indexPermessionsNoDuplicate[i].operbusinesstype+'</dd>';
					}
					$(".form-dialog-2").find(".choose-area.indexPermessions").html(dl);	
				}
				//增加权限列表
				if(opercmd_str){
					var opercmd_arr=opercmd_str.split(",");
					$indexPermessionsArea=$("#edit-authority-div").find(".choose-area.indexPermessions");
					for(var relOpercmd in relPermession){
						for(var i=0;i<opercmd_arr.length;i++){
							//因为新的命令码有的是十位数，所以截取的字段需要改变当为8位时，截取4位，当为10位时，截取6位 modify by huanghuang 20160216
							var opercmd=opercmd_arr[i].substring(0,6);
							if(opercmd==relOpercmd.substring(0,6)){
								//存在一级权限目录，则并勾选上   如果是设备权限的话 isusse
								$indexPermessionsArea.find("input[type=checkbox][value="+relOpercmd+"]").trigger("click");
								break; //跳到上一层循环
							}
						}
					}
					$permessionsArea=$("#edit-authority-div").find(".choose-area.permessions");
					//根据 idx然后选中左边框
					if(meta_opercmd_idx_str){
						var meta_opercmd_idx_arr=meta_opercmd_idx_str.split(",");
						for(var i=0;i<meta_opercmd_idx_arr.length;i++){
							$permessionsArea.find("input[type=checkbox][value="+meta_opercmd_idx_arr[i]+"]").trigger("click");
						}
					}
				}
				
			}
		}); 
	});
	authorityGroup.increaseDialog={};
	$(".increase").on("click",function(){
		authorityGroup.increaseDialog=layer.open({
			type: 1, 
			content: $("#add-authority-div"),
			title :false,
			closeBtn :1,
			area :["860px","660px"],
			shade:0.5,
			shadeClose :false,
			scrollbar :false,
			move:false,
			skin:false,
			success :function(layero, index){
				$("#add-authority-div").find(".title").text("新增分组");
				$("#add-authority-div").find(".g-submit").val("保存").removeClass("g-btn-green").addClass("g-btn-blue");
				$("#add-authority-div").find(".choose-area.indexPermessions").html("");	
				$("#add-authority-div").find(".choose-area.permessions").html("");	
				if(indexPermessions&&permessions){
					//加载一级权限菜单
					var dl='';
					for(var i=0;i<indexPermessionsNoDuplicate.length;i++){
						dl+='<dd><div class="g-checkbox"><input type="checkbox" value="'+indexPermessionsNoDuplicate[i].opercmd+'"></div>'+indexPermessionsNoDuplicate[i].operbusinesstype+'</dd>';
					}
					$(".form-dialog-2").find(".choose-area.indexPermessions").html(dl);	
				}
				//清空原来的数据
				$("#add-authority-div").find(":text").val("");
				$("#add-authority-div").find("textarea").val("");
				if(authorityGroup.operator.operator_type>=3){<%--图书馆管理员或以下角色--%>
					var library_idx=authorityGroup.operator["library_idx"];
					var lib=libIdxAndNameObj[library_idx];
					if(lib){
						var lib_id=lib.lib_id;
						var lib_name=lib.lib_name;
						$("#add-authority-div").find("input[name=lib_id]").val(lib_id);
						$("#add-authority-div").find("input[name=lib_name]").val(lib_name);
					}else{
						console.error("library_idx !lib",library_idx);
					}
					$("#add-authority-div").find("input[name=lib_id]").prop("readonly","readonly").prop("style","color:gray;background-color:#E0E0E0");
				}else if(authorityGroup.operator.operator_type<3){<%--云平台系统管理员--%>
					$("#add-authority-div").find("input[name=lib_id]").attr("placeholder","如果要指定云平台权限组请填 0");
				}
			}
		}); 

	});
	
	$("input[type=text]").on("keydown",function(){
		$(this).parents("li").removeClass("error");
	});
	/**
		新增权限组操作
		数据包括
		service_group_id，service_group_name，service_group_desc，
		权限IDX，library_idx
	**/
	$("#add-authority-div .g-submit").on("click",function(){
		GlobalGLoading();
		//获取表单数据
		var service_group_id=$("#add-authority-div").find("input[name=auth-group-id]").val();
		var lib_id=$("#add-authority-div").find("input[name=lib_id]").val();
		var service_group_name=$("#add-authority-div").find("input[name=auth-group-name]").val();
		var service_group_desc=$("#add-authority-div").find("textarea[name=auth-group-desc]").val();
		var servIdxArr=$("#add-authority-div").find(".choose-area.permessions").find("input[type=checkbox]:checked");
		var library_idx="";
		if(authorityGroup.operator){
			library_idx=authorityGroup.operator["library_idx"];
		}
		if(authorityGroup.operator.operator_type<3){
			if(lib_id=="ZERO"){//创建云平台权限  上面已经分配
				
			}else{
				if(libIdAndNameObj[lib_id]){
					library_idx=libIdAndNameObj[lib_id].library_idx;
				}else{
					$("#add-authority-div").find("input[name=lib_id]").parents("li").addClass("error");
					return;
				}
			}
		}
		//if(!library_idx){library_idx=0;}
		var idxArrStr="";
		if(servIdxArr){
			for(var i=0;i<servIdxArr.length;i++){
				idxArrStr+=$(servIdxArr[i]).val()+",";
			}
			idxArrStr=idxArrStr.substring(0, idxArrStr.length-1);
		}
		if(!idxArrStr||$.trim(idxArrStr)==""){
			layer.alert("请设置权限");
			return;
		}
		var servGroup={
			"service_group_id":service_group_id,
			"service_group_name":service_group_name,
			"service_group_desc":service_group_desc,
			"meta_opercmd_idx_str":idxArrStr,
			"library_idx":library_idx
		};
		if(!checkData(servGroup,"add-authority-div")){
			return;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/servgroup/addservgroup",
			data:{req:JSON.stringify(servGroup)},
			type:"POST"
		}).done(function(data){
			if(data){
				if(data.state==true&&!data.message){
					//增加成功，刷新页面
					var page = $("#page").find("li.active").html();
					var Page=authorityGroup.makeQueryParam(page,authorityGroup.pageSize);
					authorityGroup.selectServGroupPage(Page);
					if(authorityGroup.increaseDialog){
						layer.close(authorityGroup.increaseDialog);
					}
					GlobalShowMsg({
						showText:"新增权限组成功",
					    status:true
					});
				}else if(data.message=="FAILED"){
					//[\(（][\s\S]*[\)）] 匹配括号
					var reg=new RegExp("^[a-zA-Z0-9_]+[\(（][\s\S]*[\)）]异常","gi");
					layer.alert(data.retMessage.replace(reg,""));
				}else if(data.retMessage){
					layer.alert(data.retMessage);
				}
			}
		});
	});
	/**数据检查**/
	function checkData(servGroup,id){
		if(_.isEmpty(servGroup.service_group_id)){
			$("#"+id).find("input[name=auth-group-id]").parents("li").addClass("error");
			if(_.isEmpty(servGroup.service_group_name)){
				$("#"+id).find("input[name=auth-group-name]").parents("li").addClass("error");
			}
			return false;
		}
		if(_.isEmpty(servGroup.service_group_name)){
			$("#"+id).find("input[name=auth-group-name]").parents("li").addClass("error");
			return false;
		}
		return true;
	}
	/**
		更新 保存权限组操作
		数据包括
		service_group_idx,service_group_id,service_group_name,service_group_desc,
		权限IDX,library_idx
	**/
	$("#edit-authority-div .g-submit").on("click",function(){
		GlobalGLoading();
		//获取表单数据
		var service_group_id=$("#edit-authority-div").find("input[name=auth-group-id]").val();
		var service_group_name=$("#edit-authority-div").find("input[name=auth-group-name]").val();
		var service_group_desc=$("#edit-authority-div").find("textarea[name=auth-group-desc]").val();
		var servIdxArr=$("#edit-authority-div").find(".choose-area.permessions").find("input[type=checkbox]:checked");
		var library_idx=authorityGroup.current_edit_library_idx;
		var lib_id=$("#edit-authority-div").find("input[name=lib_id]").val();
		var service_group_idx=authorityGroup.current_edit_service_group_idx;
		var version_stamp=$("#edit-authority-div").find("input[name=version_stamp]").val();
		//debugger;
		if(lib_id&&libIdAndNameObj[lib_id]){
			library_idx=libIdAndNameObj[lib_id].library_idx;
		}else{
			$("#edit-authority-div").find("input[name=lib_id]").parents("li").addClass("error");
			return;
		}
		console.log("library_idx",library_idx);
		var idxArrStr="";
		if(servIdxArr){
			for(var i=0;i<servIdxArr.length;i++){
				idxArrStr+=$(servIdxArr[i]).val()+",";
			}
			idxArrStr=idxArrStr.substring(0, idxArrStr.length-1);
		}
		if(idxArrStr==null || $.trim(idxArrStr)==""){
			layer.alert("请设置权限");
			return;
		}
		var servGroup={
			"service_group_idx":service_group_idx,
			"service_group_id":service_group_id,
			"service_group_name":service_group_name,
			"service_group_desc":service_group_desc,
			"meta_opercmd_idx_str":idxArrStr,
			"library_idx":library_idx,
			"version_stamp":version_stamp
		};
		if(!checkData(servGroup,"edit-authority-div")){
			return;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/servgroup/updservgroup",
			data:{req:JSON.stringify(servGroup)},
			type:"POST"
		}).done(function(data){
			if(data){
				if(data.state==true&&data.message!="FAILED"){
					//增加成功，刷新页面
					var page = $("#page").find("li.active").html();
					var Page=authorityGroup.makeQueryParam(page,authorityGroup.pageSize);
					authorityGroup.selectServGroupPage(Page);
					
					if(authorityGroup.updateDialog){
						layer.close(authorityGroup.updateDialog);
					}
					GlobalShowMsg({
						showText:"修改权限组成功",
					    status:true
					});
				}else{
				 var message = data.retMessage;
				 if(message.indexOf("optimistic")>=0){
				 	layer.alert("当前选择的数据不是最新数据,请刷新之后再做编辑");
				 }else{
					 if(data.message=="FAILED"){
						//[\(（][\s\S]*[\)）] 匹配括号
						var reg=new RegExp("^[a-zA-Z0-9_]+[\(（][\s\S]*[\)）]异常","gi");
						layer.alert(data.retMessage.replace(reg,""));
					}
				}}
			}
		});
		
	});
	/**
		新增权限组框
	 	点击左边选项事件，增加右边的权限显示
	**/
	$("#add-authority-div").find(".choose-area.indexPermessions").on("click",".g-checkbox",function(){
			var opercmd=$(this).find("input[type=checkbox]").val();
			var permeArr=relPermession[opercmd];
			if($(this).find("input[type=checkbox]").is(":checked")){
				var dl='<dl class='+opercmd+'>';//opercmd 作为标记,用作删除
				for(var i=0;i<permeArr.length;i++){
					dl+='<dd><div class="g-checkbox"><input title="'+permeArr[i].opercmd+'"  type="checkbox" value="'+permeArr[i].meta_opercmd_idx+'"></div>'+permeArr[i].opercmd_desc+'</dd>';
				}
				dl+='</dl>';
				//加载权限列表
				$("#add-authority-div").find(".choose-area.permessions").append(dl);
			}
			if(!$(this).find("input[type=checkbox]").is(":checked")){
				$("#add-authority-div").find(".choose-area.permessions").find("dl."+opercmd).remove();
			}
	});
	/**
		编辑权限组框
	**/
	$("#edit-authority-div").find(".choose-area.indexPermessions").on("click",".g-checkbox",function(){
			var opercmd=$(this).find("input[type=checkbox]").val();
			var permeArr=relPermession[opercmd];
			if($(this).find("input[type=checkbox]").is(":checked")){
				var dl='<dl class='+opercmd+'>';//opercmd 作为标记,用作删除
				for(var i=0;i<permeArr.length;i++){
					dl+='<dd><div class="g-checkbox"><input title="'+permeArr[i].opercmd+'" type="checkbox" value="'+permeArr[i].meta_opercmd_idx+'"></div>'+permeArr[i].opercmd_desc+'</dd>';
				}
				dl+='</dl>';
				//加载权限列表
				$("#edit-authority-div").find(".choose-area.permessions").append(dl);
			}
			if(!$(this).find("input[type=checkbox]").is(":checked")){
				$("#edit-authority-div").find(".choose-area.permessions").find("dl."+opercmd).remove();
			}
	});

	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	/**
		确认删除按钮
	**/
	$(".form-btn.remove").on("click",function(){
	GlobalGLoading();
	if(authorityGroup.isDeleteOne){
		/*执行删除操作*/
		if(authorityGroup.delService_group_idx&&authorityGroup.dellibrary_idx){
			var del=new Array();
			var servGroup={
				"service_group_idx":authorityGroup.delService_group_idx,
				"library_idx":authorityGroup.dellibrary_idx,
				"version_stamp":authorityGroup.version_stamp
			};
			del.push(servGroup);
			//删除
			$.ajax({
				url:"${pageContext.request.contextPath}/servgroup/delservgroup",
				data:{"req":JSON.stringify(del)},
				type:"POST"
			}).done(function(data){
				if(data){
					if(data.state==true){
						//删除成功 刷新当前页面
						if(delLayer){
							layer.close(delLayer);
						}
						GlobalShowMsg({
							showText:"删除权限组成功",
						    status:true
						});
						//如果是最后一条数据了，则转到上一页。
						var page = $("#page").find("li.active").html();
						var Page=authorityGroup.makeQueryParam(page,authorityGroup.pageSize);
						authorityGroup.selectServGroupPage(Page);
					}else{
					 var message = data.retMessage;
					 if(message.indexOf("optimistic")>=0){
					 	layer.alert("当前选择的数据不是最新数据,请刷新之后再做删除");
					 }else{
						 if(data.message=="FAILED"){
							//[\(（][\s\S]*[\)）] 匹配括号
							var reg=new RegExp("^[a-zA-Z0-9_]+[\(（][\s\S]*[\)）]异常","gi");
							layer.alert(data.retMessage.replace(reg,""));
							}	
						}
						if (delLayer) {
							layer.close(delLayer);
						}
					}
				}
			});
			
		}
	}else{
		//批量删除
		if(authorityGroup.delSelelcted){
			authorityGroup.delFail="";
			authorityGroup.deleteIdxArr=new Array();
			var length=$tbody.find("input[type=checkbox]:checked").length;
			var num=0;
		$tbody.find("input[type=checkbox]:checked").each(function(index,eleDom){
				var service_group_idx=$.trim($(eleDom).parents("tr").find(".service_group_idx.hide").html());
				var library_idx=$.trim($(eleDom).parents("tr").find(".library_idx.hide").html());
				var service_group_name=$.trim($(eleDom).parents("tr").find(".col3").html());
				var version_stamp=$.trim($(eleDom).parents("tr").find(".version_stamp").html());
				var servGroup={"service_group_idx":service_group_idx,"library_idx":library_idx,"version_stamp":version_stamp}; 
				authorityGroup.deleteIdxArr.push(servGroup);
				});
				$.ajax({
					url:"${pageContext.request.contextPath}/servgroup/delservgroup",
					data:{"req":JSON.stringify(authorityGroup.deleteIdxArr)},
					type:"POST"
				}).done(function(data){
					if(data){
					if(data.state==true){
						//删除成功 刷新当前页面
						if(delLayer){
							layer.close(delLayer);
						}
						GlobalShowMsg({
							showText:"删除权限组成功",
						    status:true
						});
						//如果是最后一条数据了，则转到上一页。
						var page = $("#page").find("li.active").html();
						var Page=authorityGroup.makeQueryParam(page,authorityGroup.pageSize);
						authorityGroup.selectServGroupPage(Page);
					}else{
					 var message = data.retMessage;
					 if(message.indexOf("optimistic")>=0){
					 	layer.alert("当前选择的数据不是最新数据,请刷新之后再做删除");
					 }else{
						 if(data.message=="FAILED"){
							//[\(（][\s\S]*[\)）] 匹配括号
							var reg=new RegExp("^[a-zA-Z0-9_]+[\(（][\s\S]*[\)）]异常","gi");
							layer.alert(data.retMessage.replace(reg,""));
							}	
						}
						if (delLayer) {
							layer.close(delLayer);
						}
					}
				}
				});
			
		}
	  }
   });
   	/** 如果有删除失败的则alert **/
	authorityGroup.afterBatchRemove=function(){
			if(authorityGroup.delFail){
				layer.alert(authorityGroup.delFail.substring(0,authorityGroup.delFail.length-1)+" 删除失败,模板正在使用中");
			}else{
				GlobalShowMsg({
					showText:"删除权限组成功",
				    status:true
				});
			}
			//如果是最后一条数据了，则转到上一页。
			var page = $("#page").find("li.active").html();
			var Page=authorityGroup.makeQueryParam(page,authorityGroup.pageSize);
			authorityGroup.selectServGroupPage(Page);
	};
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var size=$(this).val();
		authorityGroup.pageSize=size;
		var page = $("#page").find("li.active").html();
		var Page=authorityGroup.makeQueryParam(page,authorityGroup.pageSize);
		authorityGroup.selectServGroupPage(Page);
	});
	/**
		权限INDEX  全选反选 效果
	**/
	$(".indexPermessions").next().find(".checkAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,true,"indexPermessions");
	});
	$(".indexPermessions").next().find(".noCheckAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,false,"indexPermessions");
	});
	$(".permessions").next().find(".checkAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,true,"");
	});
	$(".permessions").next().find(".noCheckAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,false,"");
	});
	/**
		权限 全选反选操作
	**/
	function allInputEach(container,toChecked,type){
		var $container = container || $(document);
		if(type=="indexPermessions"){
			if(toChecked){
				$container.find("input[type='checkbox']").each(function(){
					if(!$(this).is(":checked")){
						$(this).trigger("click");
					}
				});
			}else{
				$container.find("input[type='checkbox']").each(function(){		
					$(this).trigger("click");
				});
			}
		}else{
			if(toChecked){
				$container.find("input[type='checkbox']").each(function(){
					if(!$(this).is(":checked")){
						$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
					}
				});
			}else{
				$container.find("input[type='checkbox']").each(function(){		
					if(!$(this).is(":checked")){
						$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
					}else{
						$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
					}
				});
			}
		}
	}
	var masterlibIdxArr=[];
	/**
	 * 获取图书馆信息
	 **/
	function GetlibIdAndNameOBJ(callback){
		if(!typeof libIdAndNameObj=="undefined"&&!typeof libIdxAndNameObj =="undefined"&&param&&Page){
			return;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/ascconfig/querylibInfoByCurUserEXT1",
			type:"GET",
			data:{}
		}).then(function(data){
			if(data && data.state){
				if(data.message=="_MASTER_SLAVE_"){//非云平台管理员 主馆
						var masterAndSlave=data.result;
						var masterLibrary=masterAndSlave.masterLibrary;
						var slaveLibrary=masterAndSlave.slaveLibrary;//arr
						libIdAndNameObj[masterLibrary.lib_id]={"lib_name":masterLibrary.lib_name,"library_idx":masterLibrary.library_idx};
						libIdxAndNameObj[masterLibrary.library_idx]={"lib_id":masterLibrary.lib_id,"lib_name":masterLibrary.lib_name};
						masterlibIdxArr.push(masterLibrary.library_idx);
						for(var i=0;i<slaveLibrary.length;i++){
							libIdAndNameObj[slaveLibrary[i].lib_id]={"lib_name":slaveLibrary[i].lib_name,"library_idx":slaveLibrary[i].library_idx};
							libIdxAndNameObj[slaveLibrary[i].library_idx]={"lib_id":slaveLibrary[i].lib_id,"lib_name":slaveLibrary[i].lib_name};
							masterlibIdxArr.push(slaveLibrary[i].library_idx);
						}
						if(masterlibIdxArr.length>0){
							param.library_idx_arr=masterlibIdxArr;
						}
					}else if(data.message=="_SLAVE_"){//子馆
							libIdAndNameObj[data.result.lib_id]={"lib_name":data.result.lib_name,"library_idx":data.result.library_idx};
							libIdxAndNameObj[data.result.library_idx]={"lib_id":data.result.lib_id,"lib_name":data.result.lib_name};
							masterlibIdxArr.push(data.result.library_idx);
					}else{
						for(var i=0;i<data.result.length;i++){//云平台用户
							libIdAndNameObj[data.result[i].lib_id]={"lib_name":data.result[i].lib_name,"library_idx":data.result[i].library_idx};
							libIdxAndNameObj[data.result[i].library_idx]={"lib_id":data.result[i].lib_id,"lib_name":data.result[i].lib_name};
							masterlibIdxArr.push(data.result[i].library_idx);
						}
						console.log("masterlibIdxArr",masterlibIdxArr);
					}
			}
			console.log("libIdxAndNameObj",libIdxAndNameObj);
			if(typeof callback=="function"){
				callback();
			}
		});
	};
	/**
	 *	监听图书馆ID变化 改变对应的图书馆名
	 **/
	$("#add-authority-div,#edit-authority-div").on("keyup",function(){
		var lib_id=$(this).find("input[name=lib_id]").val();
		if(lib_id&&libIdAndNameObj[lib_id]){
			$(this).find("input[name=lib_name]").val(libIdAndNameObj[lib_id].lib_name);
		}else{
			$(this).find("input[name=lib_name]").val("");
		}
	});
});
</script>
</body>
</html>


