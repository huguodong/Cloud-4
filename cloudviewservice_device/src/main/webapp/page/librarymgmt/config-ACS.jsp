<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
.config-ACS .col2 {
    width: 250px;
}
.config-ACS .col5 {
 	width: 500px;
}
</style>
<div class="config-ACS">
<div class="g-loading"></div>
	<div class="">
		<div class="page-title-bar">
			<span class="title">ACS服务模板配置<a href="help-page.html" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-select">
					<select class="protocol_type">
						<option value="-1" selected>选择类型</option>
						<option value="1">SIP2</option>
						<option value="2">NCIP</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<input type="text" name="keyWord" id="" class="input g-input" placeholder="输入模板名称" />
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
						<th class="col2">图书馆名</th>
					 	<th class="col3">模板名称</th>
						<th class="col3">指令类型</th>
						<!-- <th class="col4">指令代码</th> -->
						<th class="col5">指令描述</th>
						<!-- <th class="col6">起始位置</th>
						<th class="col6">结束位置</th>
						<th class="col6">指令标识符</th> -->
						<th class="col7">操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<%--<tr>
						<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
						<td>朝阳区图书馆馆</td>
						<td>24小时街区图书馆</td>
						<td>SIP2</td>
						<td>17</td>
						<td>当前借阅数</td>
						<td></td>
						<td></td>
						<td>AH</td>
						<td>
							<span class="btn-a edit">编辑</span>
							<span class="btn-a delete">删除</span>
						</td>
					</tr>--%>
				</tbody>
			</table>
		</div>
		<%@include file="../include/page_bar.jsf"%>
	</div>
</div>
<%@include file="../librarymgmt/add-acsconf-divN.jsf" %>
<%@include file="../librarymgmt/edit-acsconf-div.jsp" %>
<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red">7</span> 个项目<br>
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
	var currentOperator=<shiro:principal/>;
	var ascconfig={};
	/**
		<tr>
			<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
			<td>朝阳区图书馆馆</td>
			<td>24小时街区图书馆</td>
			<td>SIP2</td>
			<td>17</td>
			<td>当前借阅数</td>
			<td></td>
			<td></td>
			<td>AH</td>
			<td>
				<span class="btn-a edit">编辑</span>
				<span class="btn-a delete">删除</span>
			</td>
		</tr>
	**/
	$tbody=$("div.main").find(".g-table").find("tbody");
	var drawRow=function(rows){
		if(!rows) return;
		var tbody='';
		for(var i=0;i<rows.length;i++){
			var tr='<tr>';
			var row=rows[i];
			tr+='<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" value="'+row.protocol_idx+'" /></div></td>';
			tr+='<td>'+row.lib_name+'</td>';
			tr+='<td class="col4 protocol_tpl_desc">'+row.protocol_tpl_desc+'</td>';
			tr+='<td class="protocol_defalut hide">'+row.protocol_defalut+'</td>';
			tr+='<td class="library_idx hide">'+row.library_idx+'</td>';
			tr+='<td class="protocol_type">'+row.protocol_type+'</td>';
			var protocol_field_desc=row.protocol_field_desc;
			if(protocol_field_desc&&protocol_field_desc.length>60){
				tr+='<td class="col6 protocol_field_desc">'+protocol_field_desc.substring(0,60)+'....'+'</td>';
			}else{
				tr+='<td class="col6 protocol_field_desc">'+row.protocol_field_desc+'</td>';
			}
			tr+='<td>';
			tr+='<span class="btn-a edit">编辑</span>';
			tr+='<span class="btn-a delete">删除</span>';
			tr+='</td></tr>';
			tbody+=tr;
		}
		$tbody.html(tbody);
	};
	var oper = <shiro:principal/>;
	//分页查询
	var selectAcsConfigPage=function(obj){
		if(oper.operator_type>=3)
			obj.library_idx = oper.library_idx;
		$.ajax({
			url:"${pageContext.request.contextPath}/ascconfig/queryAcsConfigByparam",
			type:"POST",
			data:{"req":JSON.stringify(obj)}
		}).done(function(data){
			if(data){
				var page=data.result;
				//console.log("selectAcsConfigPage:"+JSON.stringify(data));
				if(page){
					//console.log(page.rows);
					if(page.rows&&page.rows.length==0&&obj.page>1){
						obj.page-=1;
						selectAcsConfigPage(obj);
						return;
					}
					drawRow(page.rows);//page.rows
					$.pagination(page);
				}
			}
		});
	};
	//组装 翻页和查询 参数
	var makeQueryParam=function(page,pageSize){
		var protocol_type=$(".page-title-bar").find("select.protocol_type").val();
		var protocol_tpl_desc=$(".page-title-bar").find("input[name=keyWord]").val();
		var Page ={
			"page":page,
			"pageSize":pageSize,
		};
		if(protocol_type!=-1){
			Page["protocol_type"]=protocol_type;
		}
		if(protocol_tpl_desc){
			Page["protocol_tpl_desc"]=protocol_tpl_desc;
		}
		return Page;
	};
	var pageSize=10;
	var Page={"page":1,"pageSize":pageSize};
	selectAcsConfigPage(Page);
	
	//下一页操作
	$("div.paging-bar").on("click",".next-page",function(){
		var currentpage = $("#page").find("li.active").html();//当前页
		page = Number(currentpage) + 1;//下一页
		var Page=makeQueryParam(page, pageSize);
		selectAcsConfigPage(Page);
	});
	//上一页操作
	$("div.paging-bar").on("click",".prev-page",function(){
		var currentpage = $("#page").find("li.active").html();
		var page=Number(currentpage)-1;
		var Page=makeQueryParam(page, pageSize);
		//带参数
		selectAcsConfigPage(Page);
	});
	$("div.paging-bar").on("click","li",function(){
		if($(this).hasClass("active")) return;
		var page = $(this).html();
		if(page=="...") return;	
		var Page=makeQueryParam(page, pageSize);
		selectAcsConfigPage(Page);
	});
	
	var delLayer = null;
	//删除单个模板
	$tbody.on("click",".delete",function(){
		ascconfig.isDelelteOne=true;
		//去的要删除的模板IDX
		ascconfig.deleteIdx=$(this).parents("tr").find("input[type=checkbox]").val();
		$(".g-delete-dialog").find(".font-red").html(1);
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
	//批量删除
	$("div.page-title-bar").on("click",".delete",function(){
		ascconfig.isDelelteOne=false;
		ascconfig.deleteIdxArr=new Array();
		//选中个数
		$tbody.find("input[type=checkbox]:checked").each(function(index,eleDom){
			var ascconfigEntity={
					"protocol_tpl_idx":$(eleDom).val()
			};
			ascconfig.deleteIdxArr.push(ascconfigEntity);
		});
		$(".g-delete-dialog").find(".font-red").html(ascconfig.deleteIdxArr.length);
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
	
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	var deleteFunction=function(arr){
		if(!arr) return;
		$.ajax({
					url:"${pageContext.request.contextPath}/ascconfig/delProtocolConfigTemplate",
					data:{"req":JSON.stringify(arr)},
					type:"POST"
				}).done(function(data){
					if(data&&data.state==true){
						//全部删除成功
					}else if(data.state==false&&data.retMessage){
						var idx=data.retMessage;
						layer.alert("ID:"+idx+"删除失败");
					}
					if (delLayer) {
						layer.close(delLayer);
					}
					//删除成功刷新页面
					var currentpage = $("#page").find("li.active").html();//当前页
					var Page=makeQueryParam(currentpage, pageSize);
					selectAcsConfigPage(Page);
				});
	};
	
	
	$(".form-btn.remove").on("click",function(){
		/*执行删除操作*/
		if(ascconfig.isDelelteOne==true){
			if(ascconfig.deleteIdx){
				var ascconfigEntity={
					"protocol_tpl_idx":ascconfig.deleteIdx
				};
				var arr=new Array();
				arr.push(ascconfigEntity);
				deleteFunction(arr);
			}
		}else if(ascconfig.isDelelteOne==false){
			if(ascconfig.deleteIdxArr){
				deleteFunction(ascconfig.deleteIdxArr);
			}
		}
		//alert("这里执行删除操作");
	});

	//新增按钮
	$(".increase").on("click",function(){
		$("#add-protocol-left-tab").find("li:eq(0)").trigger("click");
		$("#add-acsconf-div").fadeIn(100);
		$("#add-acsconf-div").find(".form-dialog").animate({
			"right":0
		});
		if(oper.operator_type>=3){//
			$("#add-acsconf-div").find("input[name=lib_id]").val(libIdxAndNameObj[oper.library_idx].lib_id);
			$("#add-acsconf-div").find("input[name=lib_name]").val(libIdxAndNameObj[oper.library_idx].lib_name);
			$("#add-acsconf-div").find("input[name=lib_id]").prop("readonly","readonly").prop("style","color:gray;background-color:#E0E0E0");
			$("#add-acsconf-div").find(".right-content").find("input[name=library_idx]").val(oper.library_idx);
		}
	});
	//存储 {模板IDX1:[IDX1模板数据数组],模板IDX2:[IDX2模板数据数组]}
	ascconfig.tplProtocolCfgMap=new Object();
	/**
		编辑按钮
	**/
	$tbody.on("click",".edit",function(){
		//回填数据
		//当前修改模板的IDX
		ascconfig.current_edit_protocol_idx=$(this).parents("tr").find("input[type=checkbox]").val();
		var library_idx=$(this).parents("tr").find(".library_idx.hide").html();
		var protocol_tpl_desc=$(this).parents("tr").find(".protocol_tpl_desc").html();
		$("#edit-acsconf-div").find("input[name=library_idx]").val(library_idx);
		$("#edit-acsconf-div").find("input[name=protocol_tpl_desc]").val(protocol_tpl_desc);
		if(libIdxAndNameObj){
			$("#edit-acsconf-div").find("input[name=lib_id]").val(libIdxAndNameObj[library_idx].lib_id);
			$("#edit-acsconf-div").find("input[name=lib_name]").val(libIdxAndNameObj[library_idx].lib_name);
		}
		if(oper.operator_type>=3){//
			$("#edit-acsconf-div").find("input[name=lib_id]").prop("readonly","readonly").prop("style","color:gray;background-color:#E0E0E0");
		}
		if(ascconfig.current_edit_protocol_idx&&$.isNumeric(ascconfig.current_edit_protocol_idx)){
			var protocolConfig={
				"protocol_idx":ascconfig.current_edit_protocol_idx
			};
			//如果页面中存在改模板数据 则从页面中获取
			if(ascconfig.tplProtocolCfgMap[ascconfig.current_edit_protocol_idx]){
				var result=ascconfig.tplProtocolCfgMap[ascconfig.current_edit_protocol_idx];
				//首先需要清除数据，然后填上数据
				$("#edit-acsconf-div").find("div.copy").each(function(index,eleDom){
					$(eleDom).find("input[name=protocol_start]").val("");
					$(eleDom).find("input[name=protocol_end]").val("");
					$(eleDom).find("input[name=protocol_code]").val("");
					$(eleDom).find("input[name=protocol_defalut]").val("");
				});
				for(var i=0;i<result.length;i++){
					var protocolCfg=result[i];
					$protocolLibraryDdx=$("#edit-acsconf-div").find("div[protocol_library_idx="+protocolCfg.protocol_library_idx+"]");
					$protocolLibraryDdx.find("input[name=protocol_start]").val(protocolCfg.protocol_start);
					$protocolLibraryDdx.find("input[name=protocol_end]").val(protocolCfg.protocol_end);
					$protocolLibraryDdx.find("input[name=protocol_code]").val(protocolCfg.protocol_code);
					$protocolLibraryDdx.find("input[name=protocol_defalut]").val(protocolCfg.protocol_defalut);
				}
			}else{
				//第一次从数据库获取
				$.ajax({
					url:"${pageContext.request.contextPath}/ascconfig/queryProtocolConfigByTplIdx",
					data:{"req":JSON.stringify(protocolConfig)}
				}).done(function(data){
					//console.log("queryProtocolConfigByTplIdx",data);
					if(data&&data.result){
						//设置图书馆信息
						ascconfig.tplProtocolCfgMap[ascconfig.current_edit_protocol_idx]=data.result;
						//首先需要清除数据，然后填上数据
						$("#edit-acsconf-div").find("div.copy").each(function(index,eleDom){
							$(eleDom).find("input[name=protocol_start]").val("");
							$(eleDom).find("input[name=protocol_end]").val("");
							$(eleDom).find("input[name=protocol_code]").val("");
							$(eleDom).find("input[name=protocol_defalut]").val("");
						});
						for(var i=0;i<data.result.length;i++){
							var protocolCfg=data.result[i];
							$protocolLibraryDdx=$("#edit-acsconf-div").find("div[protocol_library_idx="+protocolCfg.protocol_library_idx+"]");
							$protocolLibraryDdx.find("input[name=protocol_start]").val(protocolCfg.protocol_start);
							$protocolLibraryDdx.find("input[name=protocol_end]").val(protocolCfg.protocol_end);
							$protocolLibraryDdx.find("input[name=protocol_code]").val(protocolCfg.protocol_code);
							$protocolLibraryDdx.find("input[name=protocol_defalut]").val(protocolCfg.protocol_defalut);
						}
					}
				});
			}
		}
		$("#edit-protocol-left-tab").find("li:eq(0)").trigger("click");
		$("#edit-acsconf-div").fadeIn(100);
		$("#edit-acsconf-div").find(".form-dialog").animate({
			"right":0
		});
	});
	
	$(".form-dialog .form-wrap .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	});
	
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
			return $.ajax({
				url:"${pageContext.request.contextPath}/ascconfig/queryAllProtocolConfig",
				type:"GET",
				data:{}
			});		
		}
	}).then(function(data){
		if(data){
			//console.log("queryAllProtocolConfig",data);
			if(data.result){
				//制造数据
				$rightContent=$("#add-acsconf-div div.right-form");
				$copyTemp=$rightContent.find(".copy-temp.hide");
				
				/**************处理左边菜单 BEGIN****************/
				var protocol_show_arr=new Array();
				for(var i=0;i<data.result.length;i++){
					var protocol=data.result[i];
					protocol_show_arr.push(protocol.protocol_show);
				}
				//之取出指令代码  去重复
				protocol_show_arr=_.uniq(protocol_show_arr);
				var li='<li>基本信息</li>';
			 	var html='<div class="right-content hide" protocol_idx=""><div class="form-wrap">'+
			 				'<div class="item">'+
			 				'<ul><li class="">'+
			 				'<div class="left">图书馆ID</div><div class="right">'+
									'<input type="hidden" name="library_idx" value=""/>'+
									'<input type="text" name="lib_id" id="" class="g-input" placeholder="请输入" />'+
									'<span class="error-msg">错误提示</span>'+
								'</div>'+
							'</li>'+
							'<li class="">'+
								'<div class="left">图书馆名称</div>'+
								'<div class="right">'+
									'<input type="text" name="lib_name" id="" readonly="readonly" class="g-input" placeholder="图书馆名称" style="color: gray;background-color: #E0E0E0" />'+
									'<span class="error-msg">错误提示</span>'+
								'</div>'+
							'</li>'+
							'<li>'+
						'<div class="left">指令类型</div>'+
						'<div class="right">'+
							'<div class="g-select">'+
								'<select  class="protocol_type">'+
									'<option value="1" selected>SIP2</option>'+
									//'<option value="2">NCIP</option>'+
								'</select>'+
								'<span class="arr-icon"></span>'+
							'</div>'+
						'</div>'+
					'</li>'+
					 '<li>'+
					 	'<div class="left">模板名称</div>'+
								'<div class="right">'+
									'<input type="text" name="protocol_tpl_desc" id="" class="g-input" placeholder="请输入" />'+
									'<span class="error-msg">错误提示</span>'+
						'</div>'+
					 '</li>'+
				 	'</ul>'+
				 	'</div></div></div>';
				 	
				//var protocolShowObject={};
				//设置新增 、编辑 左边栏指令代码
				for(var i=0;i<protocol_show_arr.length;i++){
					li+='<li show="'+protocol_show_arr[i]+'">'+protocol_show_arr[i]+'指令代码</li>';
					$rightContent.find(".right-content").attr("protocol_show",protocol_show_arr[i]);
					html+=$rightContent.html();//只循环4次 .right-content
				}
				$("#add-protocol-left-tab ul, #edit-protocol-left-tab ul").html(li);
				$("#add-acsconf-div div.right-form, #edit-acsconf-div div.right-form").html(html);
				/***************处理左边菜单 END*****************/
				
				/***************右边逻辑显示 BEGIN***************/
				for(var i=0;i<data.result.length;i++){
					var protocol=data.result[i];
					for(var j=0;j<protocol_show_arr.length;j++){
						//指令代码 相等？ 则加入数组中
						if(protocol.protocol_show==protocol_show_arr[j]){//protocol_field_idx
							$copyTemp.find(".segmentation").find(".t").html(protocol.protocol_field_desc);
							$("#add-acsconf-div div.right-form,#edit-acsconf-div div.right-form").find(".right-content[protocol_show="+protocol_show_arr[j]+"]").find(".form-wrap").append('<div protocol_library_idx="'+protocol.protocol_field_idx+'" class="copy">'+$copyTemp.html()+'</div>');
						}
					}
					//$rightContent.find(".right-content").attr("protocol_idx",protocol.protocol_field_idx);
				}
				/***************右边逻辑显示 END*****************/
				
				//图书馆 id 和 图书馆名字 联动
				if(libIdAndNameObj.length==1){
					for(var libid in libIdAndNameObj){
						$("#add-acsconf-div input[name=lib_id],#edit-acsconf-div input[name=lib_id]").val(libid);
						$("#add-acsconf-div input[name=lib_name],#edit-acsconf-div input[name=lib_name]").val(libIdAndNameObj[libid]);
					}
				}
				$("#add-acsconf-div input[name=lib_id],#edit-acsconf-div input[name=lib_id]").on("keyup",function(){
					var libId=$(this).val();
					$libName=$(this).parents("li").next().find("input[name=lib_name]");
					$libIdx=$(this).prev();
					if(!libIdAndNameObj[libId]){
						//提示libid错误
						$libName.val("");
						$libIdx.val("");
					}else{
						$libIdx.val(libIdAndNameObj[libId].library_idx);
						//可以考虑全部设置
						$libName.val(libIdAndNameObj[libId].lib_name);
					}
				});
			}
		}
	});
	/**
		新增 编辑 按钮 左边栏 点击事件
	**/
	$(".left-tab").on("click","li",function(){
		var protocol_show=$(this).attr("show");
		// 64 63 17 18
		if(protocol_show){
			//回填 
			$("#add-acsconf-div input[name=protocol_type],#edit-acsconf-div input[name=protocol_type]").val("SIP2");//暂时这样写
			$("#add-acsconf-div input[name=protocol_show],#edit-acsconf-div input[name=protocol_show]").val(protocol_show);
		}
	});
	
	<%-- <li>必须和右侧内容的<div class="right-content">顺序一一对应，否则点击菜单对应的内容不一致--%>
	$("#add-protocol-left-tab").on("click","li",function(){
		var thisIndex = $(this).index();
		$(this).addClass("active").siblings("li").removeClass("active");
		$("#add-acsconf-div").find(".right-content").hide().eq(thisIndex).show();
	});
	$("#edit-protocol-left-tab").on("click","li",function(){
		var thisIndex = $(this).index();
		$(this).addClass("active").siblings("li").removeClass("active");
		$("#edit-acsconf-div").find(".right-content").hide().eq(thisIndex).show();
	});

	//新增的提交
	$("#add-acsconf-div .g-submit2").on("click",function(){
		var arr=new Array();
		var canSubmit=true;
		//获取图书馆信息
		var library_idx=$("#add-acsconf-div").find(".right-content").find("input[name=library_idx]").val();
		if(!library_idx){
			//没有填写图书管ID,则提示需要填写
			layer.alert("图书馆ID为必填项,请填写正确图书馆ID");
			return;
		}
		var operator_idx=currentOperator.operator_idx;
		var protocol_type=$("#add-acsconf-div").find("select.protocol_type").val();
		var protocol_tpl_desc=$("#add-acsconf-div").find("input[name=protocol_tpl_desc]").val();
		if(!protocol_tpl_desc){
			layer.alert("模板名称为必填项");
			return;
		}
		//获取表单数据
		$("#add-acsconf-div").find(".right-content").each(function(index,eleDom){
			if($(eleDom).find("input[name=library_idx]").val()){
				//查得出图书馆IDX的表示的是基本信息，去掉
				return;
			}
			//指令类型
			//var protocol_type=$(eleDom).find("select.protocol_type").val();
			//指令代码
			var protocol_show=$(eleDom).find("input[name=protocol_show]").val();
			if(!protocol_show){
				layer.alert("指令代码不能为空！");
				canSubmit=false;
				return;
			}
			$(eleDom).find(".copy").each(function(index,copyDom){
				var protocol_start=$(copyDom).find("input[name=protocol_start]").val();
				var protocol_end=$(copyDom).find("input[name=protocol_end]").val();
				var protocol_defalut=$(copyDom).find("input[name=protocol_defalut]").val();
				var protocol_code=$(copyDom).find("input[name=protocol_code]").val();
				var protocol_library_idx=$(copyDom).attr("protocol_library_idx");
				//console.log(protocol_start);
				if(protocol_start&&!$.isNumeric(protocol_start)){
					$(copyDom).find("input[name=protocol_start]").parent().parent("li").addClass("error");
					canSubmit=false;
				}
				if(protocol_end&&!$.isNumeric(protocol_end)){
					$(copyDom).find("input[name=protocol_end]").parent().parent("li").addClass("error");
					canSubmit=false;
				}
				if(protocol_code&&!upperReg.test(protocol_code)){
					$(copyDom).find("input[name=protocol_code]").parent().parent("li").addClass("error");
					canSubmit=false;
				}
				var protocolConfig={
					//"protocol_idx":protocol_idx,//模板新增的时候返回模板ID
					"device_tpl_type":0,
					"library_idx":library_idx,
					"protocol_type":protocol_type,
					"protocol_show":protocol_show,
					"protocol_start":protocol_start,
					"protocol_end":protocol_end,
					"protocol_code":protocol_code,
					"protocol_defalut":protocol_defalut,
					"operator_idx":operator_idx,
					"protocol_library_idx":protocol_library_idx
				};
				arr.push(protocolConfig);
			});
		});
		if(!canSubmit){
			return;
		}
		var dataObj={
			"library_idx":library_idx,
			"protocol_type":protocol_type,
			"protocol_tpl_desc":protocol_tpl_desc,
			"protocol_config_arr":arr
		};
		//console.log(dataObj);
		//添加操作
		$.ajax({
			url:"${pageContext.request.contextPath}/ascconfig/addProtocolConfig",
			type:"POST",
			data:{"req":JSON.stringify(dataObj)}
		}).done(function(data){
			if(data.state==true){
			//删除成功刷新页面
			var currentpage = $("#page").find("li.active").html();//当前页
			var Page=makeQueryParam(currentpage, pageSize);
			selectAcsConfigPage(Page);
			showMsg({
				timeout : 1000,
				showText : '添加配置成功',
				status : true,
				callback:function(){
					/*应该是成功的时候收回吧，酌情处理*/
					var thisRight =  $("#add-acsconf-div").find(".form-dialog").attr("date-right");
					$("#add-acsconf-div").find(".form-dialog").animate({
						"right":thisRight
					},function(){
						$("#add-acsconf-div").fadeOut(100);
					});
				}
			});
			/*
				提交成功  showMsg("添加配置成功",1000,true);
				提交失败  showMsg("添加配置失败",1000,false);
			 */
			 /****只要点击新增保存按钮 清除表单数据***/
			$("#add-acsconf-div").find("input[type=text]").val("");
			
			}else if(data.retMessage){
				showRetMessage(data.retMessage);
			}
		});
	});
	/**
		取消错误提示
	**/
	$(".form-dialog-fix").on("keyup","input[type=text]",function(){
		$(this).parent().parent("li").removeClass("error");
	});
	
	var upperReg=/[A-Z]+/;/**匹配大写字母**/
	/**验证**/
	$(".form-dialog-fix").on("keyup","input[name=protocol_code]",function(){
		var protocol_code=$(this).val();
		if(protocol_code&&!upperReg.test(protocol_code)){
			$(this).parent().parent("li").addClass("error");
		}else{
			$(this).parent().parent("li").removeClass("error");
		}
	});
	$(".form-dialog-fix").on("keyup","input[name=protocol_start]",function(){
		var protocol_start=$(this).val();
		if(protocol_start&&!$.isNumeric(protocol_start)){
			$(this).parent().parent("li").addClass("error");
		}else{
			$(this).parent().parent("li").removeClass("error");
		}
	});
	$(".form-dialog-fix").on("keyup","input[name=protocol_end]",function(){
		var protocol_end=$(this).val();
		if(protocol_end&&!$.isNumeric(protocol_end)){
			$(this).parent().parent("li").addClass("error");
		}else{
			$(this).parent().parent("li").removeClass("error");
		}
	});
	/**
		编辑保存 修改
		NOTE:编辑保存之后要把 数据更新到容器
	**/
	$("#edit-acsconf-div .g-submit2").on("click",function(){
		//编辑 与 新增的区别就是 要获取到 模板的IDX
		
		var protocol_idx=ascconfig.current_edit_protocol_idx;
		if(!protocol_idx||!$.isNumeric(protocol_idx)){
			layer.alert("没有找到模板IDX");
			return;
		}
		var arr=new Array();
		var canSubmit=true;
		//获取图书馆信息
		var library_idx=$("#edit-acsconf-div").find(".right-content").find("input[name=library_idx]").val();
		if(!library_idx){
			//没有填写图书管ID,则提示需要填写
			layer.alert("图书馆ID为必填项");
			return;
		}
		var operator_idx=currentOperator.operator_idx;
		var protocol_type=$("#edit-acsconf-div").find("select.protocol_type").val();
		
		//获取表单数据
		$("#edit-acsconf-div").find(".right-content").each(function(index,eleDom){
			if($(eleDom).find("input[name=library_idx]").val()){
				//查得出图书馆IDX的表示的是基本信息，去掉
				return;
			}
			//指令类型
			//var protocol_type=$(eleDom).find("select.protocol_type").val();
			//指令代码
			var protocol_show=$(eleDom).find("input[name=protocol_show]").val();
			if(!protocol_show){
				layer.alert("指令代码不能为空！");
				canSubmit=false;
				return;
			}
			$(eleDom).find(".copy").each(function(index,copyDom){
				var protocol_start=$(copyDom).find("input[name=protocol_start]").val();
				var protocol_end=$(copyDom).find("input[name=protocol_end]").val();
				var protocol_defalut=$(copyDom).find("input[name=protocol_defalut]").val();
				var protocol_code=$(copyDom).find("input[name=protocol_code]").val();
				var protocol_library_idx=$(copyDom).attr("protocol_library_idx");
				if(protocol_start&&!$.isNumeric(protocol_start)){
					$(copyDom).find("input[name=protocol_start]").parent().parent("li").addClass("error");
					canSubmit=false;
				}
				if(protocol_end&&!$.isNumeric(protocol_end)){
					$(copyDom).find("input[name=protocol_end]").parent().parent("li").addClass("error");
					canSubmit=false;
				}
				if(protocol_code&&!upperReg.test(protocol_code)){
					$(copyDom).find("input[name=protocol_code]").parent().parent("li").addClass("error");
					canSubmit=false;
				}
				var protocolConfig={
					"protocol_idx":protocol_idx,
					"device_tpl_type":0,
					"library_idx":library_idx,
					"protocol_type":protocol_type,
					"protocol_show":protocol_show,
					"protocol_start":protocol_start,
					"protocol_end":protocol_end,
					"protocol_code":protocol_code,
					"protocol_defalut":protocol_defalut,
					"operator_idx":operator_idx,
					"protocol_library_idx":protocol_library_idx
				};
				arr.push(protocolConfig);
			});
		});
		if(!canSubmit){
			return;
		}
		var dataObj={
			"protocol_tpl_idx":protocol_idx,
			"library_idx":library_idx,
			"protocol_type":protocol_type,
			"protocol_config_arr":arr
		};
		//console.log(dataObj);
		//return;
		//添加操作
		$.ajax({
			url:"${pageContext.request.contextPath}/ascconfig/updProtocolConfig",
			type:"POST",
			data:{"req":JSON.stringify(dataObj)}
		}).done(function(data){
			if(data.state==true){
			//修改数据成功 更新页面存储的缓存数据
			ascconfig.tplProtocolCfgMap[protocol_idx]=arr;
			//成功刷新页面
			var currentpage = $("#page").find("li.active").html();//当前页
			var Page=makeQueryParam(currentpage, pageSize);
			selectAcsConfigPage(Page);
			showMsg({
				timeout : 1000,
				showText : '修改配置成功',
				status : true,
				callback:function(){
					/*应该是成功的时候收回吧，酌情处理*/
					var thisRight =  $("#edit-acsconf-div").find(".form-dialog").attr("date-right");
					$("#edit-acsconf-div").find(".form-dialog").animate({
						"right":thisRight
					},function(){
						$("#edit-acsconf-div").fadeOut(100);
					});
				}
			});
			/*
				提交成功  showMsg("添加配置成功",1000,true);
				提交失败  showMsg("添加配置失败",1000,false);
			 */
			}else if(data.retMessage){
				showRetMessage(data.retMessage);
			}
		});
	});
	
	
	/**
		查询
	**/
	$(".btn.search").on("click",function(){
		//var currentpage = $("#page").find("li.active").html();//当前页
		var Page=makeQueryParam(1, pageSize);
		selectAcsConfigPage(Page);
	});

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
	//测试用按钮
	$("#test").on("click",function(){
		var num=0;
		//获取表单数据
		$("#add-acsconf-div").find(".right-content").each(function(index,eleDom){
			$(eleDom).find(".copy").each(function(index,copyDom){
				num++;
				$(copyDom).find("input[name=protocol_start]").val(num);
				$(copyDom).find("input[name=protocol_end]").val(num);
				$(copyDom).find("input[name=protocol_defalut]").val(num);
				$(copyDom).find("input[name=protocol_code]").val("A");
			});
		});
	});
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var size=$(this).val();
		pageSize=size;
		var currentpage = $("#page").find("li.active").html();//当前页
		var Page=makeQueryParam(currentpage, pageSize);
		selectAcsConfigPage(Page);
	});
	
});
</script>



