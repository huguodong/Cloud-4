<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
			<span class="title">模板组管理</span>
			<div class="form-wrap fr">
				<%-- <div class="g-select">
					<select>
						<option value="-1" selected="">选择类型</option>
						<option value="">类型1</option>
					</select>
					<span class="arr-icon"></span>
				</div> --%>
				<input type="text" name="statisticsGroupName" id="" class="input g-input" placeholder="输入组名" />
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
						<th class="col3">描述</th>
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
	<%@ include file="template-group-add.jsp" %>
	<%@ include file="template-group-edit.jsp" %>
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
	var libIdxAndNameObj={};
	$tbody=$("div.main").find(".g-table").find("tbody");
	authorityGroup.drawRow=function(rows){
		if(!rows)return;
		var tbody='';
		//var perNum=0;
		for(var i=0;i<rows.length;i++){
			var tr='<tr>';
			var row=rows[i];
			tr+='<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>';
			tr+='<td class="statistics_group_idx hide">'+row.statistics_group_idx+'</td>';
			tr+='<td class="statistics_idx_str hide">'+row.statistics_idx_str+'</td>';
			tr+='<td class="col6">'+row.statistics_group_id+'</td>';
			tr+='<td class="col4">'+row.statistics_group_name+'</td>';
			tr+='<td class="col2">'+row.statistics_group_desc+'</td>';
			tr+='<td class="col5">';
			//<shiro:hasPermission name="0101010403">
				tr+='<span class="btn-a edit">编辑</span>';
				//perNum++;
			//</shiro:hasPermission>
			//<shiro:hasPermission name="0101010402">
				tr+='<span class="btn-a delete">删除</span>';
				//perNum++;
			//</shiro:hasPermission>
			tr+='</td>';
			tr+='</tr>';
			tbody+=tr;
		}
		$tbody.html(tbody);
		//if(perNum==0){
			//$(".col5").attr("style","display:none;");
		//}else{
			$(".col5").attr("style","");
		//} 
	};
	
	/**
		带参数分页查询
	**/
	authorityGroup.selectServGroupPage=function(obj){
		if(authorityGroup.operator.operator_type>=3)
			obj.library_idx = authorityGroup.operator.library_idx;
			$.ajax({
				url:"${pageContext.request.contextPath}/statisticsGroup/querysGroupByPageParam",
				type:"POST",
				data:{"req":JSON.stringify(obj)}
			}).done(function(data){
				//console.log(data);
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
	};
	authorityGroup.pageSize=10;
	authorityGroup.Page={"page":1,"pageSize":authorityGroup.pageSize};
	authorityGroup.selectServGroupPage(authorityGroup.Page);
	//组装 翻页和查询 参数
	authorityGroup.makeQueryParam=function(page,pageSize){
		var statisticsGroupName=$("input[name=statisticsGroupName]").val();
		var Page ={
			"page":page,
			"pageSize":pageSize,
			"statistics_group_name":statisticsGroupName
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
		url:"${pageContext.request.contextPath}/statisticsGroup/selectStatisticsTemplates",
		data:{},
		type:"GET"
	}).then(function(data){
		if(data&&data.state==true){
			indexPermessions=data.result.rows;
			if(indexPermessions!=null){
				for(var j=0;j<indexPermessions.length;j++){
					indexPermessionsNoDuplicate.push(indexPermessions[j]);
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
		authorityGroup.delService_group_idx=$.trim($(this).parents("tr").find(".statistics_group_idx.hide").html());
		//$(".g-delete-dialog").find(".font-red").html(1);
		$(".g-delete-dialog").find(".word").html(
		'是否删除 <span class="font-red">'+$.trim($(this).parents("tr").find("td.col6").html())+'</span>');
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
			layer.alert("请选择要删除的模板组");
			return;
		}
		
		$(".g-delete-dialog").find(".word").html('当前选择了 <span class="font-red">'+authorityGroup.delSelelcted.length+'</span> 个项目<br>是否要删除选择的模板组数据？');
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
	var statistics_group_idx = "";
	$tbody.on("click",".edit",function(){
		var statistics_group_id=$.trim($(this).parents("tr").find("td.col6").html());
		var statistics_group_name=$.trim($(this).parents("tr").find("td.col4").html());
		var statistics_group_desc=$.trim($(this).parents("tr").find("td.col2").html());
		var statistics_idx_str=$.trim($(this).parents("tr").find("td.statistics_idx_str.hide").html());
		//当前编辑的service_group_idx
		statistics_group_idx=$.trim($(this).parents("tr").find("td.statistics_group_idx.hide").html());
		authorityGroup.updateDialog=layer.open({
			type: 1, 
			content: $("#edit-templategroup-div"),
			title :false,
			closeBtn :1,
			area :["840px","787px"],
			shade:0.5,
			shadeClose :false,
			scrollbar :false,
			move:false,
			skin:false,
			success :function(layero, index){
				$("#edit-templategroup-div .title").text("编辑分组");
				$("#edit-templategroup-div .g-submit").val("保存").removeClass("g-btn-green").addClass("g-btn-blue");
				$("#edit-templategroup-div").find("input[name=auth-group-id]").val(statistics_group_id);
				$("#edit-templategroup-div").find("input[name=auth-group-name]").val(statistics_group_name);
				$("#edit-templategroup-div").find("textarea[name=auth-group-desc]").val(statistics_group_desc);
				if(indexPermessions&&permessions){
					var dl='';
					var dl2='';
					for(var i=0;i<indexPermessionsNoDuplicate.length;i++){
						if(indexPermessionsNoDuplicate[i].statistics_tpl_type==1){
							dl+='<dd><div class="g-checkbox"><input type="checkbox" value="'+indexPermessionsNoDuplicate[i].statistics_tpl_idx+'"></div>'+indexPermessionsNoDuplicate[i].statistics_tpl_desc+'</dd>';
						}else{
							dl2+='<dd><div class="g-checkbox"><input type="checkbox" value="'+indexPermessionsNoDuplicate[i].statistics_tpl_idx+'"></div>'+indexPermessionsNoDuplicate[i].statistics_tpl_desc+'</dd>';
						}
					}
					$(".form-dialog-2").find(".choose-area1.indexPermessions").html(dl);
					$(".form-dialog-2").find(".choose-area2.permessions").html(dl2);
				}
				//增加权限组列表
				if(statistics_idx_str!="null"){
					var statistics_idx_arr = statistics_idx_str.split(",");
					$(".indexPermessions1").find("input[type='checkbox']").each(function(){
						var v1 = $(this).val();
						if($.inArray(v1, statistics_idx_arr)>-1){
							$(this).parent().addClass("on");
						}
					});
					$(".permessions").find("input[type='checkbox']").each(function(){
						var v2 = $(this).val();
						if($.inArray(v2, statistics_idx_arr)>-1){
							$(this).parent().addClass("on");
						}
					});
				}
				
			}
		}); 
	});
	authorityGroup.increaseDialog={};
	$(".increase").on("click",function(){
		authorityGroup.increaseDialog=layer.open({
			type: 1, 
			content: $("#add-templategroup-div"),
			title :false,
			closeBtn :1,
			area :["840px","787px"],
			shade:0.5,
			shadeClose :false,
			scrollbar :false,
			move:false,
			skin:false,
			success :function(layero, index){
				$("#add-templategroup-div").find(".title").text("新增分组");
				$("#add-templategroup-div").find(".g-submit").val("保存").removeClass("g-btn-green").addClass("g-btn-blue");
				$("#add-templategroup-div").find(".choose-area1.indexPermessions").html("");
				$("#add-templategroup-div").find(".choose-area2.permessions").html("");	
				if(indexPermessions&&permessions){
					//加载一级权限菜单
					var dl='';
					var dl2='';
					for(var i=0;i<indexPermessionsNoDuplicate.length;i++){
						if(indexPermessionsNoDuplicate[i].statistics_tpl_type==1){
							dl+='<dd><div class="g-checkbox"><input type="checkbox" value="'+indexPermessionsNoDuplicate[i].statistics_tpl_idx+'"></div>'+indexPermessionsNoDuplicate[i].statistics_tpl_desc+'</dd>';
						}else{
							dl2+='<dd><div class="g-checkbox"><input type="checkbox" value="'+indexPermessionsNoDuplicate[i].statistics_tpl_idx+'"></div>'+indexPermessionsNoDuplicate[i].statistics_tpl_desc+'</dd>';
						}
					}
					$(".form-dialog-2").find(".choose-area1.indexPermessions").html(dl);
					$(".form-dialog-2").find(".choose-area2.permessions").html(dl2);
				}
				//清空原来的数据
				$("#add-templategroup-div").find(":text").val("");
				$("#add-templategroup-div").find("textarea").val("");
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
	$("#add-templategroup-div .g-submit").on("click",function(){
		GlobalGLoading();
		//获取表单数据
		var statistics_group_id = $("#add-templategroup-div").find("input[name=auth-group-id]").val();
		var statistics_group_name = $("#add-templategroup-div").find("input[name=auth-group-name]").val();
		var statistics_group_desc = $("#add-templategroup-div").find("textarea[name=auth-group-desc]").val();
		var selectTIDs = "";
		var statisticsTIDs = "";
		$(".addCX").find("input[type='checkbox']").each(function (){
			if($(this).parent().hasClass("on")){
				selectTIDs += $(this).val()+",";
			}
		});
		if(selectTIDs.endWith(",")>-1){
			selectTIDs=selectTIDs.substring(0, selectTIDs.length-1);
		}
		/* if(!selectTIDs||$.trim(selectTIDs)==""){
			layer.alert("请选择查询模板");
			return;
		} */
		$(".addTJ").find("input[type='checkbox']").each(function (){
			if($(this).parent().hasClass("on")){
				statisticsTIDs += $(this).val()+",";
			}
		});
		if(statisticsTIDs.endWith(",")>-1){
			statisticsTIDs = statisticsTIDs.substring(0, statisticsTIDs.length-1);
		}
		/* if(!statisticsTIDs||$.trim(statisticsTIDs)==""){
			layer.alert("请选择统计模板");
			return;
		} */
		var templateGroup={
			"statistics_group_id":statistics_group_id,
			"statistics_group_name":statistics_group_name,
			"statistics_group_desc":statistics_group_desc,
			"selectTIDs":selectTIDs,
			"statisticsTIDs":statisticsTIDs
		};
		if(!checkData(templateGroup,"add-templategroup-div")){
			return;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/statisticsGroup/addTemplateGroup",
			data:{req:JSON.stringify(templateGroup)},
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
						showText:"新增模板组成功",
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
		if(_.isEmpty(servGroup.statistics_group_id)){
			$("#"+id).find("input[name=auth-group-id]").parents("li").addClass("error");
			if(_.isEmpty(servGroup.statistics_group_name)){
				$("#"+id).find("input[name=auth-group-name]").parents("li").addClass("error");
			}
			return false;
		}
		if(_.isEmpty(servGroup.statistics_group_name)){
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
	$("#edit-templategroup-div .g-submit").on("click",function(){
		GlobalGLoading();
		//获取表单数据
		var statistics_group_id = $("#edit-templategroup-div").find("input[name=auth-group-id]").val();
		var statistics_group_name = $("#edit-templategroup-div").find("input[name=auth-group-name]").val();
		var statistics_group_desc = $("#edit-templategroup-div").find("textarea[name=auth-group-desc]").val();
		var selectTIDs = "";
		var statisticsTIDs = "";
		$(".editCX").find("input[type='checkbox']").each(function (){
			if($(this).parent().hasClass("on")){
				selectTIDs += $(this).val()+",";
			}
		});
		if(selectTIDs.endWith(",")>-1){
			selectTIDs=selectTIDs.substring(0, selectTIDs.length-1);
		}
		/* if(!selectTIDs||$.trim(selectTIDs)==""){
			layer.alert("请选择查询模板");
			return;
		} */
		$(".editTJ").find("input[type='checkbox']").each(function (){
			if($(this).parent().hasClass("on")){
				statisticsTIDs += $(this).val()+",";
			}
		});
		if(statisticsTIDs.endWith(",")>-1){
			statisticsTIDs = statisticsTIDs.substring(0, statisticsTIDs.length-1);
		}
		/* if(!statisticsTIDs||$.trim(statisticsTIDs)==""){
			layer.alert("请选择统计模板");
			return;
		} */
		var templateGroup={
			"statistics_group_idx":statistics_group_idx,
			"statistics_group_id":statistics_group_id,
			"statistics_group_name":statistics_group_name,
			"statistics_group_desc":statistics_group_desc,
			"selectTIDs":selectTIDs,
			"statisticsTIDs":statisticsTIDs
		};
		if(!checkData(templateGroup,"edit-templategroup-div")){
			return;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/statisticsGroup/modifyStatisticsGroup",
			data:{req:JSON.stringify(templateGroup)},
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
						showText:"修改模板组成功",
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
		if(authorityGroup.delService_group_idx){
			var servGroup={
				"gidx":authorityGroup.delService_group_idx,
			};
			//删除
			$.ajax({
				url:"${pageContext.request.contextPath}/statisticsGroup/removeStatisticsGroup",
				data:{"req":JSON.stringify(servGroup)},
				type:"POST"
			}).done(function(data){
				if(data){
					if(data.state==true){
						//删除成功 刷新当前页面
						if(delLayer){
							layer.close(delLayer);
						}
						GlobalShowMsg({
							showText:"删除模板组成功",
						    status:true
						});
						//如果是最后一条数据了，则转到上一页。
						var page = $("#page").find("li.active").html();
						var Page=authorityGroup.makeQueryParam(page,authorityGroup.pageSize);
						authorityGroup.selectServGroupPage(Page);
					}else if(data.message=="FAILED"){
						//[\(（][\s\S]*[\)）] 匹配括号
						var reg=new RegExp("^[a-zA-Z0-9_]+[\(（][\s\S]*[\)）]异常","gi");
						layer.alert(data.retMessage.replace(reg,""));
					}else if(data.retMessage){
						layer.alert(data.retMessage);
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
			var gidx = "";
			$tbody.find("input[type=checkbox]:checked").each(function(index,eleDom){
					var statistics_group_idx=$.trim($(eleDom).parents("tr").find(".statistics_group_idx.hide").html());
					gidx += statistics_group_idx+",";
			});
		    gidx = gidx.substring(0, gidx.length-1);
			var servGroup={"gidx":gidx};
			$.ajax({
				url:"${pageContext.request.contextPath}/statisticsGroup/removeStatisticsGroup",
				data:{"req":JSON.stringify(servGroup)},
				type:"POST"
			}).done(function(data){
					if(data){
					if(data.state==true){
						//删除成功 刷新当前页面
						if(delLayer){
							layer.close(delLayer);
						}
						GlobalShowMsg({
							showText:"删除模板组成功",
						    status:true
						});
						//如果是最后一条数据了，则转到上一页。
						var page = $("#page").find("li.active").html();
						var Page=authorityGroup.makeQueryParam(page,authorityGroup.pageSize);
						authorityGroup.selectServGroupPage(Page);
					}else if(data.message=="FAILED"){
						//[\(（][\s\S]*[\)）] 匹配括号
						var reg=new RegExp("^[a-zA-Z0-9_]+[\(（][\s\S]*[\)）]异常","gi");
						layer.alert(data.retMessage.replace(reg,""));
					}else if(data.retMessage){
						layer.alert(data.retMessage);
					}
				}
			});
			
		}
	  }
   });
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
	$(".indexPermessions").parent().next().find(".checkAll").on("click",function(){
		var targetDom = $(this).parents(".select-area1");
		allInputEach(targetDom,true,"indexPermessions");
	});
	$(".indexPermessions").parent().next().find(".noCheckAll").on("click",function(){
		var targetDom = $(this).parents(".select-area1");
		allInputEach(targetDom,false,"indexPermessions");
	});
	$(".permessions").parent().next().find(".checkAll").on("click",function(){
		var targetDom = $(this).parents(".select-area2");
		allInputEach(targetDom,true,"");
	});
	$(".permessions").parent().next().find(".noCheckAll").on("click",function(){
		var targetDom = $(this).parents(".select-area2");
		allInputEach(targetDom,false,"");
	});
	/**
		权限 全选反选操作
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

/**
 * ajax操作屏蔽 避免因网络原因重复提交
 * **/
function GlobalGLoading(){
	$(".g-loading").fadeIn();
	setTimeout(function(){
		$(".g-loading").fadeOut();
	},1000);
}

function GlobalShowMsg(option){
	var defaults = {
		timeout : option.timeout || 1000,
		showText : option.showText || '添加成功',
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

String.prototype.endWith=function(endStr){
    var d=this.length-endStr.length;
    return (d>=0&&this.lastIndexOf(endStr)==d)
  }
</script>
</body>
</html>


