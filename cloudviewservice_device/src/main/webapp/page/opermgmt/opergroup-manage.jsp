<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
	.form-wrap .reset input {
		display: inline-block;
	    padding: 0 10px;
	    margin: 0px 20px;
	}
	.form-dialog-2 dd {
	    height: 30px;
	    line-height: 30px;
	    width: 600px;
	}
	.usergroup-manage .col5 {
	    width: 20px;
	}
	.usergroup-manage .col6 {
	    width: 110px;
	}
</style>
<div class="usergroup-manage">
	<div class="g-loading"></div>
		<div class="">
			<div class="page-title-bar">
				<span class="title">操作员分组管理<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/opermgmt/opergroup.jsp" target="_blank" class="g-help"></a></span>
				<div class="form-wrap fr">
					<input type="text" name="operGroupName" id="" class="input g-input" placeholder="输入组名" />
					<div class="btn search">查询</div>
					<!-- 修改权限 by huanghuang 20170215 -->
					<shiro:hasPermission name="0101010201">
						<div class="btn increase g-btn-green">新增</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="0101010202">
						<div class="btn delete">删除</div>
					</shiro:hasPermission>
				</div>
			</div>
			
			<div  class="main">
				<table class="g-table">
					<thead>
						<tr>
							<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
							<th class="col2">组ID</th>
							<th class="col3">组名</th>
							<th class="col3">图书馆</th>
							<th class="col4">设备组</th>
							<th class="col4">权限组</th>
							<th class="col5">创建用户</th>
							<th class="col6">操作</th>
						</tr>
					</thead>
					<tbody>
		
					</tbody>
				</table>
			</div>
			
		<%@include file="../include/page_bar.jsf"%>
		</div>
	<%@ include file="add-opergroup-div.jsp" %>
	<%@ include file="edit-opergroup-div.jsp" %>
	<div class="g-delete-dialog">
		<span class="line"></span>
		<div class="word">
			当前选择了 <span class="font-red">7</span> 个项目<br>
			是否要删除选择的配置？
		</div>
		<div class="form-btn cancel g-btn-gray">取消</div>
		<div class="form-btn remove g-btn-red">删除</div>
	</div>
	
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
	var opergroup={};
	opergroup.operator=<shiro:principal/>;
	var pageSize=10;//进入默认10条 全局变量
	/**
		<tr>
				<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
				<td class="col2">0001</td>
				<td class="col3">朝阳区图书馆</td>
				<td class="col4">自助借还机、借书机组、还书机组</td>
				<td class="col4">维护人员组</td>
				<td class="col5">管理员</td>
				<td class="col6">
					<span class="btn-a edit">编辑</span>
					<span class="btn-a delete">删除</span>
			    </td>
		</tr>
	**/
	$tbody=$("div.main").find(".g-table").find("tbody");
	var drawRow=function(rows){
		if(!rows){return;}
		var tbody='';
		var perNum=0;
		for(var i=0;i<rows.length;i++){
			var tr='<tr>';
			var row=rows[i];
			tr+='<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" value="'+row.operator_group_idx+'" /></div></td>';
			tr+='<td class="device_group_idx_str hide">'+row.device_group_idx_str+'</td>';
			tr+='<td class="service_group_idx_str hide">'+row.service_group_idx_str+'</td>';
			tr+='<td class="statistics_group_idx_str hide">'+row.statistics_group_idx_str+'</td>';
			
			tr+='<td class="operator_group_desc hide">'+row.operator_group_desc+'</td>';
			tr+='<td class="library_idx hide">'+row.library_idx+'</td>';
			tr+='<td class="col2 operator_group_id">'+row.operator_group_id+'</td>';
			tr+='<td class="col3">'+row.operator_group_name+'</td>';
			tr+='<td class="">'+(libIdxAndNameObj[row.library_idx]?libIdxAndNameObj[row.library_idx].lib_name:"")+'</td>';
			var device_group_name_str=row.device_group_name_str?row.device_group_name_str:"";
			tr+='<td class="col4 device_group_name_str">'+device_group_name_str+'</td>';
			tr+='<td class="col4">'+row.service_group_name_str+'</td>';
			tr+='<td class="col5">'+row.operator_idx+'</td>';
			tr+='<td class="col6">';
			<shiro:hasPermission name="0101010203">
				tr+='<span class="btn-a edit">编辑</span>';
				perNum++;
			</shiro:hasPermission>
			<shiro:hasPermission name="0101010202">
				if(row.operator_group_idx!=1){
					tr+='<span class="btn-a delete">删除</span>';
					perNum++;
				}
			</shiro:hasPermission>
			tr+='</td>';
			tr+='<td class="version_stamp hide">'+row.version_stamp+'</td>';
			tr+='</tr>';
			tbody+=tr;
		}
		$tbody.html(tbody);
		if(perNum==0){
			$(".col6").attr("style","display:none");
		}else{
			$(".col6").attr("style","");
		}
	};
	//分页查询
	var selectOperGroupPage=function(obj){
			obj.library_idx=opergroup.operator["library_idx"];
			obj.operator_idx=opergroup.operator["operator_idx"];
			obj.operator_type=opergroup.operator["operator_type"];//
		$.ajax({
			url:"${pageContext.request.contextPath}/opergroup/queryOperGroupByparam",
			type:"POST",
			data:{"req":JSON.stringify(obj)}
		}).done(function(data){
			if(data){
				var page=data.result;
				//console.log("selectOperGroupPage:"+JSON.stringify(data));
				if(page&&page.rows){
// 					console.log(page.rows);
					if(page.rows.length==0&&page.page>1){
						obj.page-=1;
						selectOperGroupPage(obj);
						return;
					}
					drawRow(page.rows);
					
					var operIdxArr=new Array();
					//获取每一列的operator_idx 替换成 operator_name
					$tbody.find("td.col5").each(function(index,eleDom){
						operIdxArr.push($.trim($(eleDom).html()));
					});
					operIdxArr=_.uniq(operIdxArr);
					if(operIdxArr.length<=0) return;
					$.ajax({
						url:"${pageContext.request.contextPath}/opergroup/queryOperatorNameByoperIdxArr",
						type:"GET",
						data:{"req":JSON.stringify(operIdxArr)}
					}).done(function(data){
						//console.log("queryOperatorNameByoperIdxArr",data);
						if(data&&data.result){
							var operators=data.result;
							//IDX<--->Operator_name
							$tbody.find("td.col5").each(function(index,eleDom){
								var operIdx=$.trim($(eleDom).html());
								for(var i=0;i<operators.length;i++){
									if(operators[i].operator_idx==operIdx){
										$(eleDom).html(operators[i].operator_name);
									}
								}
							});
						}
					});
					$.pagination(page);
				}
			}
		});
	};
	//组装 翻页和查询 参数
	var makeQueryParam=function(page,pageSize){
		var operGroupName=$("input[name=operGroupName]").val().replace(/(^\s*)|(\s*$)/g, "");
		var Page ={
			"page":page,
			"pageSize":pageSize,
			"operator_group_name":operGroupName
		};
		return Page;
	};
	//下一页操作
	$("div.paging-bar").on("click",".next-page",function(){
		var currentpage = $("#page").find("li.active").html();//当前页
		page = Number(currentpage) + 1;//下一页
		var Page=makeQueryParam(page, pageSize);
		selectOperGroupPage(Page);
	});
	//上一页操作
	$("div.paging-bar").on("click",".prev-page",function(){
		var currentpage = $("#page").find("li.active").html();
		var page=Number(currentpage)-1;
		var Page=makeQueryParam(page, pageSize);
		//带参数
		selectOperGroupPage(Page);
	});
	//点击某一页
	$("div.paging-bar").on("click","li",function(){
		if($(this).hasClass("active")) return;
		var page = $(this).html();
		if(page=="...") return;	
		var Page=makeQueryParam(page, pageSize);
		selectOperGroupPage(Page);
	});
	var Page={"page":1,"pageSize":pageSize};
	//查询按钮
	$(".btn.search").on("click",function(){
		var Page=makeQueryParam(1, pageSize);
		selectOperGroupPage(Page);
	});
	//删除单个
	var delLayer = null;
	$tbody.on("click",".delete",function(){
		//删除单个flag=true
		opergroup.isDeleteOne=true;
		//拿到operator_group_idx
		opergroup.del_operator_group_idx=$(this).parents("tr").find("input[type=checkbox]").val();
		//console.log(opergroup.del_operator_group_idx);
		$(".g-delete-dialog").find(".word").html(
		'是否删除 <span class="font-red">'+$(this).parents("tr").find(".operator_group_id").html()+'</span>');
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
	//点击批量删除按钮
	$(".page-title-bar").on("click",".delete",function(){
		//判断是否有选中
		var length=$tbody.find("input[type=checkbox]:checked").length;
		if(length<=0){
			layer.alert("请选择要删除的操作员分组");
			return;
		}
		//删除单个flag=true 批量删除为false
		opergroup.isDeleteOne=false;
		$(".g-delete-dialog").find(".word").html('当前选择了 <span class="font-red">'+length+'</span> 个项目<br>是否要删除选择的操作员组数据？');
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
		点击编辑按钮
	**/
	var updateLayer=null;
	$tbody.on("click",".edit",function(){
		opergroup.current_edit_operator_group_idx=$.trim($(this).parents("tr").find("input[type=checkbox]").val());
		//
		var device_group_idx_str=$.trim($(this).parents("tr").find(".device_group_idx_str.hide").html());
		//理论上一个操作员组只能选一个权限组
		var service_group_idx_str=$.trim($(this).parents("tr").find(".service_group_idx_str.hide").html());
		var statistics_group_idx_str=$.trim($(this).parents("tr").find(".statistics_group_idx_str.hide").html());
		
		var operator_group_id=$.trim($(this).parents("tr").find(".col2").html());
		var operator_group_name=$.trim($(this).parents("tr").find(".col3").html());
		var operator_group_desc=$.trim($(this).parents("tr").find(".operator_group_desc.hide").html());
		var library_idx=$.trim($(this).parents("tr").find(".library_idx.hide").html());
		var version_stamp=$.trim($(this).parents("tr").find(".version_stamp").html());
		
		updateLayer=layer.open({
			type: 1, 
			content: $("#edit-opergroup-div"),
			title :false,
			closeBtn :1,
			area :["860px","660px"],
			shade:0.5,
			shadeClose :false,
			scrollbar :false,
			move:false,
			skin:false,
			success :function(layero, index){
				$("#edit-opergroup-div").find("input[name=oper-group-id]").val(operator_group_id);
				$("#edit-opergroup-div").find("input[name=oper-group-name]").val(operator_group_name);
				$("#edit-opergroup-div").find("textarea[name=operator-group-desc]").val(operator_group_desc);
				$("#edit-opergroup-div").find("input[name=library_idx]").val(library_idx);
				$("#edit-opergroup-div").find("input[name=version_stamp]").val(version_stamp);
				var lib=libIdxAndNameObj[library_idx];
				if(lib){
					$("#edit-opergroup-div").find("input[name=library_name]").val(lib.lib_name);
					$("#edit-opergroup-div").find("input[name=library_id]").val(lib.lib_id);
				}
                var service_group_idx;
                //选择权限组
                if(service_group_idx_str){
                    if(service_group_idx_str.indexOf(",")!=-1){
                        //如果有两个权限的.....测试里面有,只取第一个
                        service_group_idx=service_group_idx_str.split(",")[0];
                    }else{
                        service_group_idx=service_group_idx_str;
                    }

                }
                var id=$("#edit-opergroup-div").find("select.serv-group");
                setServGroup(1,library_idx,id,service_group_idx);
				//获取设备组
				if(deviceGroup){//array 设备分组
					var dds='';
					var len=deviceGroup.length;
					for(var i=0;i<len;i++){
						var deviceG = deviceGroup[i];
						 <%-- 编辑设备的时候 只有图书馆idx相等才显示，及其子馆设备组也显示---%>
						 if(librarySubLibs[library_idx]){
							if(deviceG.library_idx == library_idx || librarySubLibs[library_idx].indexOf(deviceG.library_idx+"")>-1) {
								if(len-i==1){
									dds+='<dd style="margin-bottom: 15px"><div class="g-checkbox"><input type="checkbox" value="'+deviceG.device_group_idx+'"></div>'+deviceG.device_group_name+'</dd>';
								}else{
									dds+='<dd><div class="g-checkbox"><input type="checkbox" value="'+deviceG.device_group_idx+'"></div>'+deviceG.device_group_name+'</dd>';
								}
							}
						 }else{
							if(deviceG.library_idx == library_idx) {
								if(len-i==1){
									dds+='<dd style="margin-bottom: 15px"><div class="g-checkbox"><input type="checkbox" value="'+deviceG.device_group_idx+'"></div>'+deviceG.device_group_name+'</dd>';
								}else{
									dds+='<dd><div class="g-checkbox"><input type="checkbox" value="'+deviceG.device_group_idx+'"></div>'+deviceG.device_group_name+'</dd>';
								}
							}
						 }
					}
					$("#edit-opergroup-div").find("dl.choose-area").html(dds);
				}
				$checkedbox=$("#edit-opergroup-div").find("dl.choose-area").find("input[type=checkbox]");
				//选择设备组
				if(device_group_idx_str){
					var device_group_idx_arr=device_group_idx_str.split(",");
					for(var j=0;j<device_group_idx_arr.length;j++){
						$checkedbox.each(function(index,eleDom){
							if($(eleDom).val()==device_group_idx_arr[j]){
								$(eleDom).trigger("click");
							}
						});
					}
				}

				//选择模板组
				if(statistics_group_idx_str){
					var statistics_group_idx;
					if(statistics_group_idx_str.indexOf(",")!=-1){
						//如果有两个权限的.....测试里面有,只取第一个
						statistics_group_idx=statistics_group_idx_str.split(",")[0];
					}else{
						statistics_group_idx=statistics_group_idx_str;
					}
					$("#edit-opergroup-div").find("select.template_group").val(statistics_group_idx);
					$("#edit-opergroup-div").find("select.template_group").trigger("change");
				}
			}
		}); 
	});
	//设备组
	var deviceGroup={};
	//权限组
	var serviceGroup={};
	var librarySubLibs = {};
	$chooseArea=$("#add-opergroup-div").find("dl.choose-area");
	//获取设备组
	//根据当前用户的所属图书馆IDX 获取 本图书管IDX，如果library_idx="" 则当做管理员
	deviceGroup.selectGroupByParam=function(){
		var deviceG={
// 			"library_idx":opergroup.operator["library_idx"]
			//"operator_idx":opergroup.operator["operator_idx"]
		};
		
		if(opergroup.operator['operator_type']>=3) {//
			deviceG["library_idx"] = opergroup.operator["library_idx"];
			if(librarySubLibs[deviceG["library_idx"]]){
				deviceG["sub_idx"] = librarySubLibs[deviceG["library_idx"]].join(",");
			}
		}else{
			deviceG["library_idx"] = "";
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/devicegroup/SelectGroupByParam",
			data:{"req":JSON.stringify(deviceG)},
			type:"GET"
		}).done(function(data){
			if(data){
				if(data.result){
					deviceGroup=data.result;
				}
				
			}
		});
	};
	
	
	
	<%-- 查询所有图书馆的子馆信息 --%>
	function queryLibrarySubLibs(callback){
		$.ajax({
			url:"${pageContext.request.contextPath}/library/queryMasterSubRelations",
			type:"GET"
		}).done(function(data){
			if(data){
				if(data.result){
					//list:[{"master_lib_id":1, "slave_lib_id":"1,2,3"},{"master_lib_id":1, "slave_lib_id":"1,2,3"}]
					var list = data.result;
					for(var i=0;i<list.length;i++){
						var item = list[i];
						librarySubLibs[item.master_lib_id+""] = item.slave_lib_id.split(",");
					}
				}
				
			}
		}).always(function(){
			callback();
		});
	}
	
	queryLibrarySubLibs(function(){
		deviceGroup.selectGroupByParam();
	})
	
	//最大值
	var queryAllPage={page:1,pageSize:2147483647};
	
	/**
	 *	 获取并整理权限的关系
	 **/
	var indexPermessions={};
	//拥有的一级权限目录
	var indexPermessionsNoDuplicate=new Array();
	//拥有的权限集合
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
			//获取用户的权限
			permessions=opergroup.operator["userRolePermessions"];
			if(indexPermessions&&permessions){
			//indexPermessions 会存在重复的内容
			for(var j=0;j<indexPermessions.length;j++){
				if(relPermession[indexPermessions[j].opercmd]){
					continue;
				}
				//indexPermessionsNoDuplicate.push(indexPermessions[j]);
				//当命令码是8位时，截取4位，当命令码是10位时，截取6位 modify by huanghuang 20170217
				var indexCmd = 0;
				if(indexPermessions[j].opercmd.length==8)
					indexCmd = indexPermessions[j].opercmd.substr(0,4);
				else if(indexPermessions[j].opercmd.length==10)
					indexCmd = indexPermessions[j].opercmd.substr(0,6);
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
			//00010001:arr
			//console.log("indexPermessionsNoDuplicate");
			//console.log(indexPermessionsNoDuplicate);
			//console.log(relPermession);
		 }
		  /**
		   ** 取得全部权限分组,并且设置到下拉框 注意，
		   ** 只有拥有权限组里面的所有权限才可显示该权限组。
		   ** 可以推导出给权限组最多拥有的目录为以上的indexPermessionsNoDuplicate目录。
		   **/
		 return $.ajax({
			url:"${pageContext.request.contextPath}/servgroup/queryServgroupByparam",
			type:"POST",
			data:{"req":JSON.stringify(queryAllPage)}
		});
	  }
  }).then(function(data){
  		if(data&&data.state==true){
				serviceGroup.allgroups=data.result;
				//console.log("serviceGroup.allgroups:"+JSON.stringify(serviceGroup.allgroups));
				//当前用户拥有的权限idx
				var meta_opercmd_idx_arr=new Array();
				//用户所拥有的权限集合				
				if(relPermession){
					for(var opercmd in relPermession){
						var servArr=relPermession[opercmd];
						for(var i=0;i<servArr.length;i++){
							var meta_opercmd_idx=servArr[i].meta_opercmd_idx;//meta_opercmd_idx
							meta_opercmd_idx_arr.push(meta_opercmd_idx);
						}
					}
				}
				meta_opercmd_idx_arr.sort(function(a, b){
					return a-b;
				});//排序 ，关键：使下面的_.indexOf 函数生效
				
				serviceGroup.service_group_arr=new Array();
				if(serviceGroup.allgroups){
					//填充
					var rows=serviceGroup.allgroups.rows;
					var select='<option value="-1">请选择</option>';
					//console.log("排序过后 meta_opercmd_idx_arr");
					//console.log("serviceGroup.allgroups",rows);
					lable:for(var i=0;i<rows.length;i++){
						//查询出的分组权限的 opercmd_idx并设置成整形数组 
						var meta_opercmd_idx_str=rows[i].meta_opercmd_idx_str;
						var opercmd_idx_arr=[];
						if(meta_opercmd_idx_str&&meta_opercmd_idx_str.indexOf(",")>=0){
							opercmd_idx_arr=meta_opercmd_idx_str.split(",");
						}
						var opercmd_idx_arr_int=[];
						for(var z=0;z<opercmd_idx_arr.length;z++){
							opercmd_idx_arr_int.push(parseInt(opercmd_idx_arr[z]));
						}
						//console.log(opercmd_idx_arr_int);
						if(opercmd_idx_arr_int.length>meta_opercmd_idx_arr.length){
								continue;//权限不足，不能选择该权限组
						}
						//var hasAllIdx=true;
						for(var j=0;j<opercmd_idx_arr_int.length;j++){
							if(_.indexOf(meta_opercmd_idx_arr, opercmd_idx_arr_int[j], true)==-1){
								// 如果有一个分组权限的idx没有找到，则权限不足，不能选择该权限组
								continue lable;
							}
						}
						//还需要 符合所属图书馆的IDX 0、other 
						if(opergroup.operator.library_idx!=0){//权限对象集合
							if(rows[i].library_idx==opergroup.operator.library_idx){
								//console.log("opercmd_idx_arr_int",opercmd_idx_arr_int);
								select+='<option value="'+rows[i].service_group_idx+'">'+rows[i].service_group_name+'</option>';
							}
						}else if(opergroup.operator.library_idx==0){
							select+='<option value="'+rows[i].service_group_idx+'">'+rows[i].service_group_name+'</option>';
						}
						serviceGroup.service_group_arr.push(rows[i]);
					}
					//新增框
					$("#add-opergroup-div").find("select.serv-group").html(select);
					//编辑框
					$("#edit-opergroup-div").find("select.serv-group").html(select);
				}
			}
  });
	
	 $.ajax({
		url:"${pageContext.request.contextPath}/statisticsGroup/queryStatisticsGroup",
		type:"POST",
		data:{}
	}).then(function(data){
		var group =data.result;
		
		var str='<option value="-1">请选择</option>';
		for(var i=0;i<group.length;i++){
			str+='<option value='+group[i].statistics_group_idx+'>'+group[i].statistics_group_name+'</option>';
		}
		//新增框
		$("#add-opergroup-div").find("select.template_group").html(str);
		//编辑框
		$("#edit-opergroup-div").find("select.template_group").html(str);
	});
	 /**
	  *	选择权限组下拉框则切换里面的权限内容
	  **/
	  $("#add-opergroup-div").find("select.template_group").on("change",function(){
		  var statistics_group_idx=$(this).val();
		  var dd='';
		  var queryID={"statistics_group_idx":statistics_group_idx};
		  $.ajax({
				url:"${pageContext.request.contextPath}/statisticsGroupTemplate/findByIdStatisticsGroupTemplate",
				type:"POST",
				data:{"req":JSON.stringify(queryID)}
			}).then(function(data){
				var groups =data.result;
				if(statistics_group_idx != -1){
					for(var i=0;i<groups.length;i++){
					  dd+='<dd>'+groups[i].statistics_tpl_desc+'</dd>';
					}
				}
				$("#add-opergroup-div").find(".choose-jurisdiction.template").html(dd);
			});

	  });
	  /**
	   *编辑按钮-----》
	   *	选择权限组下拉框则切换里面的权限内容
	   **/
	  $("#edit-opergroup-div").find("select.template_group").on("change",function(){
		  var statistics_group_idx=$(this).val();
		  var dd='';
		  var queryID={"statistics_group_idx":statistics_group_idx};
		  $.ajax({
				url:"${pageContext.request.contextPath}/statisticsGroupTemplate/findByIdStatisticsGroupTemplate",
				type:"POST",
				data:{"req":JSON.stringify(queryID)}
			}).then(function(data){
				var groups =data.result;
				if(statistics_group_idx != -1){
					for(var i=0;i<groups.length;i++){
					  dd+='<dd>'+groups[i].statistics_tpl_desc+'</dd>';
					}
				}
				$("#edit-opergroup-div").find(".choose-jurisdiction.template").html(dd);
			});

	  });
    var ylibidx;
    var ylibidx2;
    $("#add-opergroup-div").find('input[name="library_id"]').focus(function(){
        var idx = $("#add-opergroup-div").find('input[name="library_idx"]').val();
        ylibidx = idx;
    });
    $("#edit-opergroup-div").find('input[name="library_id"]').focus(function(){
        var idx = $("#edit-opergroup-div").find('input[name="library_idx"]').val();
        ylibidx2 = idx;
    });

    $("#add-opergroup-div").find('input[name="library_id"]').blur(function(){
        var tem = -1;
        var idx = $("#add-opergroup-div").find('input[name="library_idx"]').val();
        if(ylibidx != idx){
            tem = 1;
        }
        var id = $("#add-opergroup-div").find("select.serv-group");
        setServGroup(tem,idx,id,-1);
    });
    $("#edit-opergroup-div").find('input[name="library_id"]').blur(function(){
        var idx = $("#edit-opergroup-div").find('input[name="library_idx"]').val();
        var tem = -1;
        if(ylibidx2 != idx){
            tem = 1;
        }
        var id=$("#edit-opergroup-div").find("select.serv-group");
        setServGroup(tem,idx,id,-1);
    });
	/**
	 *
	 *	选择权限组下拉框则切换里面的权限内容
	 **/
	 var globalCmdTypeList = {};
	 var globalCmdList = [];
	$("#add-opergroup-div").find("select.serv-group").on("change",function(){
		$("#add-opergroup-div").find(".choose-jurisdiction.permessions").html("");
        //console.log($("input[name=library_idx]").val());
		var service_group_idx=$(this).val();
		var dd='';
		var leftdd='';
		//权限idx数组		
		<%--清空数据--%>
		globalCmdTypeList={};
		globalCmdList=[];
		if(serviceGroup.service_group_arr){
			for(var i=0;i<serviceGroup.service_group_arr.length;i++){
				if(serviceGroup.service_group_arr[i].service_group_idx==service_group_idx){
					globalCmdTypeList = serviceGroup.service_group_arr[i];
					var opercmd_desc_str=serviceGroup.service_group_arr[i].opercmd_desc_str;
					//左边栏权限类型(目录)显示
					var opercmd_arr_str=serviceGroup.service_group_arr[i].opercmd_str;
					var opercmd_arr=[];
					if(opercmd_arr_str && opercmd_arr_str.indexOf(",")>-1){
						opercmd_arr=opercmd_arr_str.split(",");
					}
					nlable:for(var n=0;n<indexPermessionsNoDuplicate.length;n++){
						for(var k=0;k<opercmd_arr.length;k++){
							//确定是否拥有该目录
							//把命令码截取改为6位，modify by huanghuang 20170217
							if(opercmd_arr[k].substring(0,6)==indexPermessionsNoDuplicate[n].opercmd.substring(0,6)){
								var tempCmd = opercmd_arr[k].substring(0,6);
								if(globalCmdList.indexOf(tempCmd+"") == -1){
									globalCmdList.push(tempCmd+"");
								}
								leftdd+='<dd data-code="'+tempCmd+'">'+indexPermessionsNoDuplicate[n].operbusinesstype+'</dd>';
								continue nlable;
							} 
						}
					}
					//右边栏权限显示
// 					if(i==0){
// 						if(opercmd_desc_str && opercmd_desc_str.indexOf(",")){
// 							console.log(opercmd_desc_str)
// 							var opercmd_desc_arr=opercmd_desc_str.split(",");
// 							for(var j=0;j<opercmd_desc_arr.length;j++){
// 								dd+='<dd>'+opercmd_desc_arr[j]+'</dd>';
// 							}
// 						}
// 					}
					<%-- 显示第一条数据 --%>
					if(globalCmdList[0]) {
						setCmdList(globalCmdList[0], "add");
					}
					break;
				}
			}
		}
		//console.log(dd);
// 		$("#add-opergroup-div").find(".choose-jurisdiction.permessions").html(dd);
		$("#add-opergroup-div").find(".choose-jurisdiction.indexPermessions").html(leftdd);
	});
	
	<%-- 权限列表第二个列表 --%>
	function setCmdList(code, type){
		if(globalCmdTypeList){
			var opercmd_desc_str = globalCmdTypeList.opercmd_desc_str;
			var opercmd_str = globalCmdTypeList.opercmd_str;
			var desc_arr = opercmd_desc_str.split(",");
			var id_arr = opercmd_str.split(",");
			var html = "";
			for(var i=0; i<desc_arr.length; i++) {
				var item = desc_arr[i];
				var c = id_arr[i];
				if(code==c.substring(0,6)){
					html+='<dd>'+ item +'</dd>';
				}
			}
			if("add"==type) {
				$("#add-opergroup-div").find(".choose-jurisdiction.permessions").html(html);
			}
			if("edit"==type) {
				$("#edit-opergroup-div").find(".choose-jurisdiction.permessions").html(html);
			}
		}
	}
	<%-- 点击权限左侧列表的响应事件 --%>
	$("#add-opergroup-div").on("click",".choose-jurisdiction.indexPermessions dd",function(){
		$("#add-opergroup-div").find(".choose-jurisdiction.permessions").html("");
		var code = $(this).attr("data-code");
		setCmdList(code, "add")
	});
	
	$("#edit-opergroup-div").on("click",".choose-jurisdiction.indexPermessions dd",function(){
		$("#edit-opergroup-div").find(".choose-jurisdiction.permessions").html("");
		var code = $(this).attr("data-code");
		setCmdList(code, "edit")
	});
	
	
	/**
	 *	编辑按钮-----》
	 *	选择权限组下拉框则切换里面的权限内容
	 **/
	$("#edit-opergroup-div").find("select.serv-group").on("change",function(){
		var service_group_idx=$(this).val();
		var dd='';
		var leftdd='';
		//权限idx数组				
		<%--清空数据--%>
		globalCmdTypeList={};
		globalCmdList=[];
		if(serviceGroup.service_group_arr){
			for(var i=0;i<serviceGroup.service_group_arr.length;i++){
				if(serviceGroup.service_group_arr[i].service_group_idx==service_group_idx){
					globalCmdTypeList = serviceGroup.service_group_arr[i];
					var opercmd_desc_str=serviceGroup.service_group_arr[i].opercmd_desc_str;
					//左边栏权限类型(目录)显示
					var opercmd_arr_str=serviceGroup.service_group_arr[i].opercmd_str;
					var opercmd_arr=[];
					if(opercmd_arr_str && opercmd_arr_str.indexOf(",")>-1){
						opercmd_arr=opercmd_arr_str.split(",");
					}
					nlable:for(var n=0;n<indexPermessionsNoDuplicate.length;n++){
						for(var k=0;k<opercmd_arr.length;k++){
							var tempCmd = opercmd_arr[k].substring(0,6);
							//确定是否拥有该目录
							//取二级菜单时，通过前六位命令码判断(之前是取四位)modify by huanghuang 20170217
							if(opercmd_arr[k].substring(0,6)==indexPermessionsNoDuplicate[n].opercmd.substring(0,6)){
								if(globalCmdList.indexOf(tempCmd+"") == -1){
									globalCmdList.push(tempCmd+"");
								}
								leftdd+='<dd data-code="'+tempCmd+'">'+indexPermessionsNoDuplicate[n].operbusinesstype+'</dd>';
								continue nlable;
							} 
						}
					}
					//右边栏权限显示
// 					if(opercmd_desc_str && opercmd_desc_str.indexOf(",")){
// 						var opercmd_desc_arr=opercmd_desc_str.split(",");
// 						for(var j=0;j<opercmd_desc_arr.length;j++){
// 							dd+='<dd>'+opercmd_desc_arr[j]+'</dd>';
// 						}
// 					}
					<%-- 显示第一条数据 --%>
					if(globalCmdList[0]) {
						setCmdList(globalCmdList[0], "edit");
					}
					break;
				}
			}
		}
		//console.log(dd);
// 		$("#edit-opergroup-div").find(".choose-jurisdiction.permessions").html(dd);
		$("#edit-opergroup-div").find(".choose-jurisdiction.indexPermessions").html(leftdd);
	});
	
	/**
		点击新增按钮
	**/
	var increaseLayer=null;
	$(".increase").on("click",function(){
		//清除错误
		$("#add-opergroup-div").find("li.error").removeClass("error");
		$("#add-opergroup-div").find(":text").val("");
		$("#add-opergroup-div").find("select.serv-group").val("-1").change();
		$("#add-opergroup-div").find("select.template_group").val("-1").change();
		$("#add-opergroup-div").find(".g-textarea").val("");
				
		increaseLayer=layer.open({
			type: 1, 
			content: $("#add-opergroup-div"),
			//content:'${pageContext.request.contextPath}/page/opermgmt/add_opergroup_div.jsp',
			title :false,
			closeBtn :1,
			area :["860px","660px"],
			shade:0.5,
			shadeClose :false,
			scrollbar :false,
			move:false,
			skin:false,
			success :function(layero, index){
				if(deviceGroup){//array 设备分组
					var dds='';
					var len=deviceGroup.length;
					for(var i=0;i<len;i++){
						var deviceG=deviceGroup[i];
						if(len-i==1){
							dds+='<dd style="margin-bottom: 15px"><div class="g-checkbox"><input type="checkbox" value="'+deviceG.device_group_idx+'"></div>'+deviceG.device_group_name+'</dd>';
						}else{
							dds+='<dd><div class="g-checkbox"><input type="checkbox" value="'+deviceG.device_group_idx+'"></div>'+deviceG.device_group_name+'</dd>';
						}
					}
					$chooseArea.html(dds);
				}
			}
		}); 
	});
	/**
		消除错误提示
	**/
	$("input[type=text]").on("keyup",function(){
		$(this).parents("li").removeClass("error");
	});
	$("select").on("change",function(){
		$(this).parents("li").removeClass("error");
	});
	/**
		新增按钮，保存数据
	**/
	$("#add-opergroup-div input[type=submit]").on("click",function(){
		GlobalGLoading();
		var operGroupId=$("#add-opergroup-div").find("input[name=oper-group-id]").val();
		var operGroupName=$("#add-opergroup-div").find("input[name=oper-group-name]").val();
		var servGroupIdx=$("#add-opergroup-div").find("select.serv-group").val();
		var operatorGroupDesc=$("#add-opergroup-div").find("textarea[name=operator-group-desc]").val();
		var templateGroupIdx=$("#add-opergroup-div").find("select.template_group").val();
		var isPass=true;//是否通过验证
		//验证
		if(!operGroupId){
			$("#add-opergroup-div").find("input[name=oper-group-id]").parents("li").addClass("error");
			isPass=false;
		}
		if(!operGroupName){
			$("#add-opergroup-div").find("input[name=oper-group-name]").parents("li").addClass("error");
			isPass=false;
		}
		if(servGroupIdx==-1){
			$("#add-opergroup-div").find("select.serv-group").parents("li").addClass("error");
			isPass=false;
		}
		//数组类型
		//var deviceGroup=   
		var library_idx="";
		var operator_type="";
		if(opergroup.operator){
			library_idx=opergroup.operator["library_idx"];//操作员所属馆
			operator_type=opergroup.operator["operator_type"];
		}
		
		if(!library_idx){library_idx=0;}
		
		var arrDevGroupIdx=new Array();
		$("#add-opergroup-div").find("dl.dev-group").find("input[type=checkbox]:checked").each(function(index,eleDom){
			arrDevGroupIdx.push($(eleDom).val());
		});
	
		var operGroup={
			"operator_group_id":operGroupId,
			"library_idx":library_idx,
			"operator_group_name":operGroupName,
			"operator_group_desc":operatorGroupDesc,
			"service_group_idx":servGroupIdx,
			"device_group_idx_arr":arrDevGroupIdx,
			"statistics_group_idx":templateGroupIdx
		};
		if(operator_type==1||operator_type==2){
			var library_idx=$("#add-opergroup-div").find("input[name=library_idx]").val();
			//manager
			if(library_idx){
				operGroup.library_idx=library_idx;
			}else{
				$("#add-opergroup-div").find("input[name=library_idx]").parents("li").addClass("error");
				isPass=false;
			}
		}

		if(!isPass){
			return;
		}
		//console.log(operGroup);
		$.ajax({
			url:"${pageContext.request.contextPath}/opergroup/addOperGroup",
			data:{"req":JSON.stringify(operGroup)},
			type:"POST"
		}).done(function(data){
// 			console.log(data);
			if(data){
				if(data.state==true&&!data.message){
					if(increaseLayer){
						layer.close(increaseLayer);
					}
					/**
					 * 新增 修改 删除 成功操作之后 弹窗 使用
					 * FUNCTION GlobalShowMsg
					 * 参数：
					 * {
					 * 	 showText:"",
					 *   status:true/false
					 * }
					 * **/
					GlobalShowMsg({
						showText:"新增操作员组成功",
						status:true
					});
					$("#add-opergroup-div").find(":text").val("");
					$("#add-opergroup-div").find("select.serv-group").val("-1").change();
					var currentpage = $("#page").find("li.active").html();//当前页
					if(!currentpage) currentpage=1;
					page = Number(currentpage);//下一页
					var Page=makeQueryParam(page, pageSize);
					selectOperGroupPage(Page);
				}else if(data.retMessage){
					layer.alert(data.retMessage);
				}
			}
		});
	});
	
	/**
		保存编辑以后的数据
	**/
	$("#edit-opergroup-div input[type=submit]").on("click",function(){
		GlobalGLoading();
		//layer.alert("add !!");
		var operGroupId=$("#edit-opergroup-div").find("input[name=oper-group-id]").val();
		var operGroupName=$("#edit-opergroup-div").find("input[name=oper-group-name]").val();
		var servGroupIdx=$("#edit-opergroup-div").find("select.serv-group").val();
		var operatorGroupDesc=$("#edit-opergroup-div").find("textarea[name=operator-group-desc]").val();
		var operator_group_idx=opergroup.current_edit_operator_group_idx;
		var version_stamp=$("#edit-opergroup-div").find("input[name=version_stamp]").val();
		var templateGroupIdx=$("#edit-opergroup-div").find("select.template_group").val();
		
		var isPass=true;
		//验证
		if(!operGroupId){
			$("#edit-opergroup-div").find("input[name=oper-group-id]").parents("li").addClass("error");
			isPass=false;
		}
		if(!operGroupName){
			$("#edit-opergroup-div").find("input[name=oper-group-name]").parents("li").addClass("error");
			isPass=false;
		}
		if(servGroupIdx==-1){
			$("#edit-opergroup-div").find("select.serv-group").parents("li").addClass("error");
			isPass=false;
		}
		//数组类型
		//var deviceGroup=   
		var library_idx="";
		var operator_type="";
		if(opergroup.operator){
			library_idx=opergroup.operator["library_idx"];
			operator_type=opergroup.operator["operator_type"];
		}
		//if(!library_idx){library_idx=0;}
		var arrDevGroupIdx=new Array();
		$("#edit-opergroup-div").find("dl.dev-group").find("input[type=checkbox]:checked").each(function(index,eleDom){
			arrDevGroupIdx.push($(eleDom).val());
		});
		var operGroup={
			"operator_group_idx":operator_group_idx,//需要idx
			"operator_group_id":operGroupId,
			"library_idx":library_idx,//图书馆library_idx 不会变
			"operator_group_name":operGroupName,
			"operator_group_desc":operatorGroupDesc,
			"service_group_idx":servGroupIdx,
			"device_group_idx_arr":arrDevGroupIdx,
			"version_stamp":version_stamp,
			"statistics_group_idx":templateGroupIdx
		};
		if(operator_type==1||operator_type==2){
		  	var library_idx=$("#edit-opergroup-div").find("input[name='library_idx']").val();
			//manager
			operGroup.library_idx=library_idx;
			if(library_idx){
				operGroup.library_idx=library_idx;
			}else{
				$("#edit-opergroup-div").find("input[name=library_idx]").parents("li").addClass("error");
				isPass=false;
			}
		}
		if(!isPass){
			return;
		}
		//console.log(operGroup);
		$.ajax({
			url:"${pageContext.request.contextPath}/opergroup/updOperGroup",
			data:{"req":JSON.stringify(operGroup)},
			type:"POST"
		}).done(function(data){
// 			console.log(data);
			if(data){
				if(data.state==true&&!data.message){
					if(updateLayer){
						layer.close(updateLayer);
					}
					GlobalShowMsg({
						showText:"修改操作员组成功",
						status:true
					});
					var currentpage = $("#page").find("li.active").html();//当前页
					page = Number(currentpage);//下一页
					var Page=makeQueryParam(page, pageSize);
					selectOperGroupPage(Page);
				}else {
				 var message = data.retMessage;
				 if(message.indexOf("optimistic")>=0){
				 	layer.alert("当前选择的数据不是最新数据,请刷新之后再做编辑操作");
				 }else{
					if(data.messsage=="FAILED"){
						layer.alert(data.retMessage);
					}else if(data.retMessage){// 多余...
						layer.alert(data.retMessage);
					}
			}}}
		});
	});
	
	//取消
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	/**
		确定删除  删除操作
	**/
	$(".form-btn.remove").on("click",function(){
		GlobalGLoading();
		/*执行删除操作*/
		//删除单个
		if(opergroup.isDeleteOne){
			if(opergroup.del_operator_group_idx){
				var operg={
					"operator_group_idx":opergroup.del_operator_group_idx,
					"version_stamp":opergroup.version_stamp
				};
				$.ajax({
					url:"${pageContext.request.contextPath}/opergroup/delOperGroup",
					data:{"req":JSON.stringify(operg)}
				}).done(function(data){
					if(data){
						//删除成功，刷新当前页面或者上一页
						if(data.state==true){
							GlobalShowMsg({
								showText:"删除操作员组成功",
								status:true
							});
							var currentpage = $("#page").find("li.active").html();//当前页
							page = Number(currentpage);//下一页
							var Page=makeQueryParam(page, pageSize);
							selectOperGroupPage(Page);
							layer.close(delLayer);//删除记录时，弹出框消失add by huanghuang 20170220
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
						if(delLayer){
								layer.close(delLayer);
							}
					}}
				});
			}
		}else{
			var num=0;
			var length=$tbody.find("input[type=checkbox]:checked").length;
			opergroup.delFail="";
			opergroup.delOpt="";
			//批量删除
			$tbody.find("input[type=checkbox]:checked").each(function(index,eleDom){
				var operator_group_name=$.trim($(eleDom).parents("tr").find(".col3").html());
				var operg={
					"operator_group_idx":$(eleDom).val(),
					"version_stamp":opergroup.version_stamp
				};
				$.ajax({
					url:"${pageContext.request.contextPath}/opergroup/delOperGroup",
					data:{"req":JSON.stringify(operg)}
				}).done(function(data){
					num++;
					if(data){
						//删除成功，刷新当前页面或者上一页
						if(data.state==true&&!data.message){
							
						}else{
							var message = data.retMessage;
							if(message!=null && message.indexOf("optimistic")>=0){
								opergroup.delOpt+=operator_group_name+",";
							}else{
								opergroup.delFail+=operator_group_name+",";
							}
						}
					}
				}).fail(function(data){
					num++;
				});
			});
			//关闭弹出层
			if (delLayer) {
				layer.close(delLayer);
			}
			//清除checkbox
			$("thead").find("input[type=checkbox]").removeAttr("checked").parents(".g-checkbox").removeClass("on");
			//采用周期循环处理
			var Interval=setInterval(function(){
				//console.log("setInterval:"+num);
				if(num>=length){
					clearInterval(Interval);//清除
					opergroup.afterBatchRemove();
					return;
				}
			},500);
		}
	});
	//批量删除之后执行
	opergroup.afterBatchRemove=function(){
			if(opergroup.delFail){
				layer.alert("组名："+opergroup.delFail.substring(0, opergroup.delFail.length-1)+" 删除失败,分组可能正在使用中");
			}else{
				if(opergroup.delOpt){
					layer.alert("当前选择的数据不是最新数据,请刷新之后再做删除");
				}else{
				GlobalShowMsg({
					showText:"删除操作员组成功",
					status:true
				});
				}
			}
			//如果是最后一条数据了，则转到上一页。
			var page = $("#page").find("li.active").html();
			var Page=makeQueryParam(page,pageSize);
			selectOperGroupPage(Page);
	};

	/**
		add-acsconfig-div
		去的对应用户的图书馆数据
	**/
	var libIdAndNameObj={};
	var libIdxAndNameObj={};
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
			selectOperGroupPage(Page);
		}
	});
	//图书馆 id 和 图书馆名字 联动
	$("#add-opergroup-div input[name=library_id],#edit-opergroup-div input[name=library_id]").on("keyup",function(){
			var libId=$(this).val();
			$libName=$(this).parents("li").find("input[name=library_name]");
			$libIdx=$(this).next();
			//console.log(libIdAndNameObj[libId]);
			if(!libIdAndNameObj[libId]){
				//提示libid错误
				$libName.val("");
				$libIdx.val("");
				changeDevGroup("");
			}else{
				$libIdx.val(libIdAndNameObj[libId].library_idx);
				//可以考虑全部设置
				$libName.val(libIdAndNameObj[libId].lib_name);
				
				//改变设备组的值
				changeDevGroup(libIdAndNameObj[libId].library_idx);
			}
	});
	
	<%-- 填写了图书馆信息之后显示该馆的数据 --%>
	function changeDevGroup(val){
		$("#edit-opergroup-div").find("dl.choose-area").html("");
		$("#add-opergroup-div").find("dl.choose-area").html("");
		if(deviceGroup){//array 设备分组
			var dds='';
			var len=deviceGroup.length;
			for(var i=0;i<len;i++){
				var deviceG = deviceGroup[i];
				if(librarySubLibs[val]){
					if(deviceG.library_idx == val || val === "" || librarySubLibs[val].indexOf(deviceG.library_idx+"")>-1){
						if(len-i==1){
							dds += '<dd style="margin-bottom: 15px"><div class="g-checkbox"><input type="checkbox" value="'+deviceG.device_group_idx+'"></div>'+deviceG.device_group_name+'</dd>';
						}else{
							dds += '<dd><div class="g-checkbox"><input type="checkbox" value="'+deviceG.device_group_idx+'"></div>'+deviceG.device_group_name+'</dd>';
						}
					}
				}else{
					if(deviceG.library_idx == val || val === "" ){
						if(len-i==1){
							dds += '<dd style="margin-bottom: 15px"><div class="g-checkbox"><input type="checkbox" value="'+deviceG.device_group_idx+'"></div>'+deviceG.device_group_name+'</dd>';
						}else{
							dds += '<dd><div class="g-checkbox"><input type="checkbox" value="'+deviceG.device_group_idx+'"></div>'+deviceG.device_group_name+'</dd>';
						}
					}
				}
			}
			$("#edit-opergroup-div").find("dl.choose-area").html(dds);
			$("#add-opergroup-div").find("dl.choose-area").html(dds);
		}
	}
	
	/**
	 * 全选反选
	 **/
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
					$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
				}
			});
		}else{
			$container.find("input[type='checkbox']").each(function(){		
				$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
			});
		}
	}
	/**
	 *	每页显示的条目数切换
	 **/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var size=$(this).val();
		pageSize=size;
		var page = $("#page").find("li.active").html();
		var Page=makeQueryParam(page,pageSize);
		selectOperGroupPage(Page);
	});
	
	/**
	 *	设备分组  全选反选 效果
	 **/
	$(".choose-area .dev-group").next().find(".checkAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,true,"");
	});
	$(".choose-area .dev-group").next().find(".noCheckAll").on("click",function(){
		var targetDom = $(this).parents(".select-area");
		allInputEach(targetDom,false,"");
	});
	/**
	 *	权限 全选反选操作
	 **/
	function allInputEach(container,toChecked,type){
		var $container = container || $(document);
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
	
});
function setServGroup(tem,idx,id,service_group_idx) {
    if(tem !=-1){
        var queryAllPage={};
        if(idx != 0) {
            queryAllPage={page:1,pageSize:2147483647,library_idx:idx};
        }else {
            queryAllPage={page:1,pageSize:2147483647};
        }
        $.ajax({
            url:"${pageContext.request.contextPath}/servgroup/queryServgroupByparam",
            type:"POST",
            data:{"req":JSON.stringify(queryAllPage)}
        }).then(function(data){
            var rows = data.result.rows;
            var select='<option value="-1">请选择</option>';
            for(var i=0;i<rows.length;i++){
                select+='<option value="'+rows[i].service_group_idx+'">'+rows[i].service_group_name+'</option>';
            }
            id.html(select);
            id.val(service_group_idx);
            id.trigger("change");

        });

    }

}
</script>



