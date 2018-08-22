<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
	.upload-dialog .form-wrap {
    	padding: 30px 0 60px;
	}
	.system-databackup .col4 {
   	 	width: 150px;
	}
	.system-databackup .col3 {
   	 	width: 35px;
	}
	.form-dialog .g-input {
	    width: 200px;
	    height: 26px;
	    line-height: 26px;
	}
</style>
<title>数据库备份（馆）</title>
<div class="system-databackup">
<div class="g-loading"></div>
	<div  class=""><%--main-content --%>
		<div class="page-title-bar">
			<span class="title">数据库备份<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/sysmgmt/system-databackup.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-inputdate"><input name="startDate" readonly="readonly" type="text" placeholder="起始时间" class="g-input datepicker"><span class="icon"></span></div> 
				<span class="fl">-</span>
				<div class="g-inputdate"><input name="endDate" readonly="readonly" type="text" placeholder="结束时间" class="g-input datepicker"><span class="icon"></span></div> 
				<div class="btn search">查询</div>
					<!-- 修改权限 by huanghuang 20170215 -->
					<shiro:hasPermission name="0109010201">
						<div class="btn backup g-btn-green">备份</div>
					</shiro:hasPermission>
					<!-- <div class="btn upload g-btn-blue">上传</div> -->
					<div class="btn delete">删除</div>
			  </div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="col2">名称</th>
						<th class="">馆名称</th>
						<!-- <th class="col4">路径</th> -->
						<th class="col3">文件大小</th>
						<th class="col4">备份时间</th>
						<th class="">最近还原时间</th>
						<th class="">是否成功</th>
						<th class="">是否存在</th>
						<th class="col5">操作</th>
					</tr>
				</thead>
				<tbody>
				<%-- <tr>
					<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
					<td class="col2">海恒智能数据库备份</td>
					<td class="col3">60mb</td>
					<td class="col4">2013-2-20 13:20:50</td>
					<td class="col5">
						<span class="btn-a download g-btn-green">下载</span>
						<span class="btn-a restore g-btn-yellow">还原</span>
						<span class="btn-a delete">删除</span>
					</td>
				</tr> --%>
				</tbody>
			</table>
		</div>
	
		<%@include file="../include/page_bar.jsf" %>
	</div>
</div>
<div class="delete-dialog dialog1">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red">7</span> 个项目<br>
		是否要删除选择的备份文件？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<div class="backup-dialog dialog1 form-dialog">
	<span class="line"></span>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left">馆ID</div>
				<div class="right">
					<input type="hidden" id="library_IDX">
					<input type="text" name="" id="library_id" class="input g-input" placeholder="请输入图书馆ID" />
				</div>
			</li>
			<li>
				<div class="left">馆名称</div>
				<div class="right">
					<input type="text" readonly="readonly" name="" id="library_name" class="input g-input" placeholder="图书馆名称" />
				</div>
			</li>
			<li>
				<div class="left">数据类型（仅展示）</div>
				<div class="right">
					<div class="g-select">
						<select id="data-type">
							<option  value="1">基础部分数据</option>
							<option  value="2">增量备份数据</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>				
		</ul>
	</div>
	<div class="word">
		<!-- 是否要对当前的数据库进行备份？<br> -->
		<span class="w1">上一次备份：2013-2-50 15:50:40</span>
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn backup-btn g-btn-green">备份</div>
</div>
<div class="upload-dialog dialog1 form-dialog">
	<span class="line"></span>
	<div class="form-wrap">
<%-- <form id="uploadForm" action="${pageContext.request.contextPath}/database/bakUpload" enctype="multipart/form-data" method="post" target="nm_iframe"> 			
 --%>
 	请输入路径：	 		
 	<input id="file" name="file" type="file"  class="input" /><!-- accept="application/x-zip-compressed" 使用accept谷歌浏览器很卡 -->
		<!--  </form> -->
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn upload g-btn-green">上传</div>
</div>
<iframe id="id_iframe" name="nm_iframe" style="display:none;"></iframe>
<%--还原对话框 --%>
<div class="restore-dialog dialog1">
	<span class="line"></span>
	<div class="word">
		是否还原选中的数据库文件？<br>
		<span class="w1">海恒智能数据库备份 2013-2-3 13:20:50</span>
	</div>

	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn restore-btn g-btn-yellow">还原</div>
</div>
<div class="load-dialog">
	<span class="line"></span>
	<div class="load-gif"></div>
	<div class="word">请稍等，正在还原文件···</div>
</div>
<div class="result-state dialog1">
	<span class="line"></span>
	<div class="status-icon"></div>
	<div class="status-word">数据库备份成功</div>
	<div class="w1">你可以请重新尝试备份，或者联系<br>系统管理员解决问题。</div>
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
	/* 因为页面不能解析oper，所以将JSON.parse去掉,for huanghuang 20170526 */
	//var oper='<shiro:principal/>';
	//var operator=JSON.parse(oper);
	var oper=<shiro:principal/>;
	var operator=oper;
	var libIdAndNameObj={};
	var libIdxAndNameObj={};
	var masterlibIdxArr=[];
	
	function GetlibIdAndNameOBJ(Page){
		if(!typeof libIdAndNameObj=="undefined"&&!typeof libIdxAndNameObj =="undefined"&&param&&Page){
			selectBakPage(Page);
			return;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/ascconfig/querylibInfoByCurUserEXT1",
			type:"GET",
			data:{}
		}).then(function(data){
			if(data){
				console.log("获取到图书馆信息",data);
				if(data.state && data.result){
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
							Page.library_idx_arr=masterlibIdxArr;
						}
						if(Page){selectBakPage(Page);}
					}else if(data.message=="_SLAVE_"){//子馆
							libIdAndNameObj[data.result.lib_id]={"lib_name":data.result.lib_name,"library_idx":data.result.library_idx};
							libIdxAndNameObj[data.result.library_idx]={"lib_id":data.result.lib_id,"lib_name":data.result.lib_name};
							masterlibIdxArr.push(data.result.library_idx);
							if(masterlibIdxArr.length>0){
								Page.library_idx_arr=masterlibIdxArr;
							}
							console.log("Page",Page);
							if(Page){selectBakPage(Page);}
					}else{
						for(var i=0;i<data.result.length;i++){//云平台用户
							libIdAndNameObj[data.result[i].lib_id]={"lib_name":data.result[i].lib_name,"library_idx":data.result[i].library_idx};
							libIdxAndNameObj[data.result[i].library_idx]={"lib_id":data.result[i].lib_id,"lib_name":data.result[i].lib_name};
							masterlibIdxArr.push(data.result[i].library_idx);
						}
						console.log("masterlibIdxArr",masterlibIdxArr);
						if(Page){selectBakPage(Page);}
					}
					console.log("libIdxAndNameObj",libIdxAndNameObj);
				}
			}
		});
	}
	
	
	
	var pageSize=10;
	/**
			<tr>
				<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
				<td class="col2">海恒智能数据库备份</td>
				<td class="col3">60mb</td>
				<td class="col4">2013-2-20 13:20:50</td>
				<td class="col5">
					<span class="btn-a download g-btn-green">下载</span>
					<span class="btn-a restore g-btn-yellow">还原</span>
					<span class="btn-a delete">删除</span>
				</td>
			</tr>
						<th class="col2">名称</th>
						<th class="col3">路径</th>
						<th class="col3">文件大小</th>
						<th class="col4">备份时间</th>
						<th class="col4">是否成功</th>
						<th class="col4">是否存在</th>
						<th class="col5">操作</th>
						管理员才能下载
	**/
	$tbody=$("div.main").find(".g-table").find("tbody");

	var drawRow=function(rows){
		var arr=[];
		if(!rows){return;}
		var tbody='';
		var permesionNum=0;
		for(var i=0;i<rows.length;i++){
			var tr='<tr>';
			var row=rows[i];
			var bfile={"file_path":row.file_path,"is_exist":row.is_exist,"idx":row.idx};
			var lib_name="";
			var library_idx=row.library_idx;
			var lib_id="";
			if(libIdxAndNameObj[library_idx]){
				lib_name=libIdxAndNameObj[library_idx].lib_name;
				lib_id=libIdxAndNameObj[library_idx].lib_id;
			}
			arr.push(bfile);
			tr+='<td class="fileName hide">'+row.file_name+'</td>';
			tr+='<td class="deletePath hide">'+row.file_path+'</td>';
			tr+='<td class="is_exist hide">'+row.is_exist+'</td>';
			tr+='<td class="col1"><div class="g-checkbox"><input type="checkbox" idx="'+row.idx+'" id="" /></div></td>';
			tr+='<td class="col2">'+row.file_name+'</td>';
			tr+='<td class="col2">'+lib_name+'</td>';
			tr+='<td class="col3 hide">'+row.file_path+'</td>';
			tr+='<td class="">'+row.file_size.toFixed(2)+'M</td>';
			tr+='<td class="col4">'+formatDate(row.create_time)+'</td>';
			tr+='<td class="">'+formatDate(row.restore_time)+'</td>';
			tr+='<td class="bakup_flg">'+(row.bakup_flg==1?"是":"否")+'</td>';
			tr+='<td class="is_exist">'+(row.is_exist==1?"是":"否")+'</td>';
			tr+='<td class="col5">';//
			console.log("oper.operator_type",oper);
			<shiro:hasPermission name="0109010204">
				if(operator.operator_type=="2"||operator.operator_type=="1"){
					tr+='<span class="btn-a download g-btn-green">下载</span>';
					permesionNum+=1;
				}
			</shiro:hasPermission>
			//tr+='';//数据库还原权限 不分库
			//修改权限 by huanghuang 20170215
			<shiro:hasPermission name="0109010202">
				tr+='<span class="btn-a restore g-btn-yellow" library_idx="'+library_idx+'" lib_id="'+lib_id+'">还原</span>';
				permesionNum+=1;
			//tr+='';
			</shiro:hasPermission>
			<shiro:hasPermission name="0102040500">
				tr+='<span class="btn-a delete">删除</span>';
				permesionNum+=1;
			</shiro:hasPermission>
			tr+='</td>';
			tr+='</tr>';
			tbody+=tr;
		}
		$tbody.html(tbody);
		if(permesionNum==0){
			$(".col5").attr("style","display:none;");
		}else{
			$(".col5").attr("style","");
		}
		checkBakUpFileIfExsit(arr);
	};
	/** 检查 ，一般用于开发使用 **/
	function checkBakUpFileIfExsit(arr){
		$.ajax({
			url:"${pageContext.request.contextPath}/database/checkBakUpFileIfExsit",
			type:"POST",
			data:{"req":JSON.stringify(arr)},
			success:function(data){
				//查出来更新？
				if(data && data.state){
					if(data.message=="need_fresh"){
						var currentpage = $("#page").find("li.active").html();
						var page=Number(currentpage);
						var Page=makeQueryParam(page, pageSize);
						selectBakPage(Page);
					}
				}
			}
		});
	}
	
	var selectBakPage=function(obj){
		if(masterlibIdxArr.length>0){
			obj.library_idx_arr=masterlibIdxArr;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/database/queryLibraryDbBakByparamExt",
			type:"POST",
			data:{"req":JSON.stringify(obj)}
		}).done(function(data){
			if(data){
				var page=data.result;
				console.log("selectBakPage",page);
				if(page){
					//console.log(page.rows);
					drawRow(page.rows);
					$.pagination(page);
				}
			}
		});
	};
	//组装 翻页和查询 参数
	var makeQueryParam=function(page,pageSize){
		var startDate=$("input[name=startDate]").val();
		var endDate=$("input[name=endDate]").val();
		var Page ={
			"page":page,
			"pageSize":pageSize,
			//开始时间 结束时间
			//"create_time_start":startDate,
			//"create_time_end":endDate
		};
		
		if(startDate){
			Page.create_time_start=startDate+" 00:00:00";
		}
		if(endDate){
			Page.create_time_end=endDate+" 23:59:59";
		}
		console.log(Page);
		//alert(keyWord);
		return Page;
	};
	
	//下一页操作
	$("div.paging-bar").on("click",".next-page",function(){
		var currentpage = $("#page").find("li.active").html();//当前页
		var page = Number(currentpage) + 1;//下一页
		var Page=makeQueryParam(page, pageSize);
		selectBakPage(Page);
	});
	//上一页操作
	$("div.paging-bar").on("click",".prev-page",function(){
		var currentpage = $("#page").find("li.active").html();
		var page=Number(currentpage)-1;
		var Page=makeQueryParam(page, pageSize);
		//带参数
		selectBakPage(Page);
	});
	$("div.paging-bar").on("click","li",function(){
		if($(this).hasClass("active")) return;
		var page = $(this).html();
		if(page=="...") return;	
		var Page=makeQueryParam(page, pageSize);
		selectBakPage(Page);
	});
	var Page={"page":1,"pageSize":pageSize};
	GetlibIdAndNameOBJ(Page);
	/**
		查询操作
	**/
	$("div.btn.search").on("click",function(){
		//layer.alert("查询");
		//var page = $("#page").find("li.active").html();
		var page=1;
		var Page=makeQueryParam(page, pageSize);
		selectBakPage(Page);
	});
	/**
		 批量删除
	**/
	$("div.page-title-bar").on("click",".delete",function(){
	var num=0;
	$("tbody").find(".g-checkbox").find(":checked").each(function(index,ele){
		num++;
	});
	$(".delete-dialog").find(".font-red").html(num);
	 layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.delete-dialog'), //捕获的元素
			success : function(index,layero){
				$(".cancel").on("click",function(){
					layer.close(layero);
				});
				$(".remove").on("click",function(){
					layer.close(layero);
					var arr=new Array();
					//获取所有选中的
					$("tbody").find(".g-checkbox").find(":checked").each(function(index,ele){
						var deletePath=$(ele).parents("tr").find(".deletePath").html();
						// var fileName=$(ele).parents("tr").find(".col2").html();
						// var bakUp={
						//    "fileName":fileName,
						//	  "deletePath":deletePath
						// };
						var delPath=deletePath;
						arr.push(delPath);
					});
					$.ajax({
						url:"${pageContext.request.contextPath}/database/deleteLibBakup",
						data:{"req":JSON.stringify(arr)},
						type:"POST"
					}).done(function(data){
						if(data){
							GlobalShowMsg({
								showText:"删除成功",
								status:true
							});
							//删除成功
							console.log("删除备份："+data);
							//当前页为空，则跳转到前一页
							var currentpage = $("div.paging-bar").find("li.active").html();//当前页
							var Page={"page":currentpage,"pageSize":10};
							selectBakPage(Page);
						}
			   		});		
				});
			},
			end:function(){
				$(".cancel").unbind("click");
				$(".remove").unbind("click");
			}
		});
	});
	
	
	
	/**
		删除单个备份文件
	**/
	$("tbody").on("click",".delete",function(){
		//获取到 删除地址。
		var deletePath=$(this).parents("tr").find(".deletePath").html();
		var fileName=$(this).parents("tr").find(".col2").html();
		//$(".delete-dialog").find(".font-red").html(1);
		$(".delete-dialog").find(".word").html('是否删除  <span class="font-red">'+fileName+'</span>');
		layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.delete-dialog'), //捕获的元素
			success : function(index,layero){
				$(".cancel").on("click",function(){
					layer.close(layero);
				});
				$(".remove").on("click",function(){
					GlobalGLoading();
					layer.close(layero);
					var delPath=deletePath;
					var arr=new Array();
					arr.push(delPath);
					if(arr){
						$.ajax({
							url:"${pageContext.request.contextPath}/database/deleteLibBakup",
							data:{"req":JSON.stringify(arr)},
							type:"POST",
							timeout:30000
						}).done(function(data){
							if(data){
								GlobalShowMsg({
									showText:"删除成功",
									status:true
								});
								//删除成功
								console.log("删除备份："+data);
								//当前页为空，则跳转到前一页
								var currentpage = $("div.paging-bar").find("li.active").html();//当前页
								var Page={"page":currentpage,"pageSize":10};
								selectBakPage(Page);
							}
						});					
					}else{
						//找不到删除地址
					}
				});
			},
			end:function(){
				$(".cancel").unbind("click");
				$(".remove").unbind("click");
			}
		});
	});
	/**
		数据还原
	**/
	$("tbody").on("click",".restore",function(){
		var fileName=$(this).parents("tr").find(".fileName").html();
		var createTime=$(this).parents("tr").find(".col4").html();
		
		var idx=$(this).parents("tr").find("input[type=checkbox]").attr("idx");
		var file_path=$(this).parents("tr").find(".col3").html();
		var file_name=$(this).parents("tr").find(".col2").html();
		var library_idx=$.trim($(this).attr("library_idx"));
		var library_id=$.trim($(this).attr("lib_id"));
		
		if(!library_id || library_id==""){
			layer.alert("获取不到图书馆ID，请刷新后重试");
			return;
		}
		if(!library_idx || library_idx==""){
			layer.alert("获取不到图书馆IDX，请刷新后重试");
			return;
		}
		/**
			var libInfo={"idx":5,
				"library_idx":16,
				"library_id":"LIB001",
				"file_path":"E:\\SsitCloudBakUp\\libidx\\LIB001\\1484296680476.xml",
				"file_name":"1484296680476.xml"
			};
		**/
		var libInfo={
				"idx":idx,
				"library_idx":library_idx,
				"library_id":library_id,
				"file_path":file_path,
				"file_name":file_name
		};
		layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.restore-dialog'), //捕获的元素
			success : function(index,layero){
				$(".restore-dialog").find(".w1").html(fileName+" "+createTime);
			
				$(".cancel").on("click",function(){
					layer.close(layero);
				});
				$(".restore-btn").on("click",function(){
					layer.close(layero);
					var dialogIndex = loadingDialog({
						loadText:"请稍等，正在还原文件···"
					});
					$.ajax({
						url:"${pageContext.request.contextPath}/database/restoreDataByLibraryIdx",
						data:{"req":JSON.stringify(libInfo)},
						type:"POST"
					}).done(function(data){
						if(data){
							layer.close(dialogIndex);
							//console.log("restoreBakup:"+data);
							if(data.state){
								resultDialog({
									loadText:"数据库还原成功",
									success:function(){
										$(".result-state").removeClass("error");
									}
								});
							}else{
								resultDialog({
									loadText:"数据库还原失败",
									success:function(){
										$(".result-state").addClass("error");
									}
								});
							}
						}
					});
				});
			},
			end:function(){
				$(".cancel").unbind("click");
				$(".restore-btn").unbind("click");
			}
		});
	});
	/***
		备份数据 按钮
	**/
	$(".backup").on("click",function(){
		//清除数据
		$("#library_IDX").val("");
		$("#library_name").val("");
		$("#library_id").val("");
		if(oper){
			var o=JSON.parse(oper);
			
			if(o.operator_type==3 || o.operator_type==4){
				//如果不是云平台管理员则需要限制输入
				$("#library_IDX").val(o.library_idx);
				$("#library_id").val(o.lib_id);
				$("#library_id").attr("readonly","readonly");
				$("#library_name").val(o.lib_name);
			}else if(o.operator_type==1 || o.operator_type==2){
				console.log("o.operator_type",o.operator_type);
				$("#library_id").attr("readonly",false);
			}
		}else{
			layer.alert("请登录后操作");
			return;
		}
		layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.backup-dialog'), //捕获的元素
			success : function(index,layero){
				var json={"t":"1"};
				/**
					获取个数据的最后备份时间,如果是 某个馆的获取某个馆的最后备份时间
				**/
				$.ajax({
					url:"${pageContext.request.contextPath}/database/getLastLibBakUpTime",
					data:{"req":JSON.stringify(json)},
					type:"GET"
				}).done(function(data){
					console.log("getLastBakUpTime:"+JSON.stringify(data));
					if(data && data.state){
						var lastModifyTime=data.result;
						$("div.backup-dialog").find(".w1").html("上一次备份："+formatDate(lastModifyTime.create_time));
					}
				});
				$(".cancel").on("click",function(){
					layer.close(layero);
				});
				$(".backup-btn").on("click",function(){
					layer.close(layero);
					var dialogIndex = loadingDialog({
						loadText:"请稍等，正在备份文件···"
					});
					//var libInfo={"library_idx":16,"library_id":"LIB001"};
					var library_idx=$("#library_IDX").val();
					var library_id=$("#library_id").val();
					var lib_idx=libIdAndNameObj[library_id].library_idx;
					console.log("lib_idx",lib_idx);
					console.log("library_idx",library_idx);
					if(lib_idx!=library_idx){
						layer.alert("图书馆ID："+library_id+" 与IDX："+library_idx+" 不符");
						layer.close(dialogIndex);
						return;
					}
					
					var libInfo={
						"library_idx":library_idx,
						"library_id":library_id
					};
					//console.log(reqBody);
					$.ajax({
						url:"${pageContext.request.contextPath}/database/bakupByLibraryIdx",
						data:{"req":JSON.stringify(libInfo)},
						type:"POST"
					}).done(function(data){
						if(data){
							if(data.state==true){
								layer.close(dialogIndex);
								resultDialog({
									loadText:"数据库备份成功",
									success:function(){
										$(".result-state").removeClass("error");
									}
								});
							}else{
								layer.close(dialogIndex);
								resultDialog({
									loadText:"数据库备份失败",
									success:function(){
										$(".result-state").addClass("error");
									}
								});
							}
							var currentpage = $("div.paging-bar").find("li.active").html();//当前页
							var Page={"page":currentpage,"pageSize":10};
							selectBakPage(Page);
						}
					});
				});
			},
			end:function(){
				$(".cancel").unbind("click");
				$(".backup-btn").unbind("click");
			}
		});
	});
	/**
		下载
	**/
	var download = function(url, data, method){    
	// 获得url和data
    if( url && data ){ 
    	 // data 是 string 或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data);        // 把参数组装成 form的  input
    	var inputs = '';
    	jQuery.each(data.split('&'), function(){ 
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />'; 
        });        
        // request发送请求
        jQuery('<form action="'+ url +'" method="'+ (method||'post') +'">'+inputs+'</form>')
        .appendTo('body').submit().remove();
   	 };
	};
	/**
		下载备份文件
	**/
	$("tbody").on("click",".btn-a.download",function(){
		/**
			先判断一下，如果不存在的话则不能下载
		*/
		var is_exist=$.trim($(this).parents("tr").find(".is_exist").html());
		if(is_exist!=1){
			//不存在，不能下载
			layer.alert("文件不存在");
			return;
		}
		var filePath=$(this).parents("tr").find(".deletePath").html();
		var fileName=$(this).parents("tr").find(".fileName").html();
		var url="${pageContext.request.contextPath}/database/downloadFile";
		var data="fileName="+fileName+"&filePath="+filePath;
		/**
		 *	检查下载的文件是否存在VIEW项目所在服务器,如果不在则线传输到VIEW SERVER
		 **/
	 	var dialogIndex = loadingDialog({
			loadText:"请稍等，准备中..."
		});
		$.getJSON("${pageContext.request.contextPath}/database/checkSQLFileExsits",data,function(d){
			if(d){
				if(d.state){
					layer.close(dialogIndex);
					download(url,data,"get");
				}else{
					alert("下载错误！");
				}
			}else{
				alert("下载错误！");
			}
		});
	});

	/**
	   	上传文件按钮 点击
	**/
	var bakUpload={};
	bakUpload.isClicked=false;
	bakUpload.uploadDialog;
	$(".btn.upload").on("click",function(){
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
		   if(!$("#file").val()){
		   		layer.alert("请选择文件后上传");
		   		return;
		   }
		   var dialogIndex = loadingDialog({
				loadText:"请稍等，上传中..."
		   });
		   //上传文件
		   $.ajaxFileUpload({
				 url:'${pageContext.request.contextPath}/database/bakUpload',//处理图片脚本
				 secureuri :false,
				 fileElementId :'file',//file控件id
				 type:'post',
				 dataType:'json',
				 success : function (data,status){
				 if(data){
				    //去掉<pre></pre>
				    //<pre style="word-wrap: break-word; white-space: pre-wrap;">{"state":false,"message":"","retMessage":"","result":null}</pre>
				     var reg=new RegExp("<[^>]*>","gi");
				     var dateTxt=data.replace(reg,"").replace(reg,"");
				     console.log(dateTxt);
				     var obj=JSON.parse(dateTxt);
				     if(obj.state==true){
				    	  layer.close(dialogIndex);
				          GlobalShowMsg({showText:"上传成功",status:true});
				     }else{
				     	if(obj.message.indexOf("|")>=0){
				     		var msgarr=obj.message.split("|");
				     		var msgs="";
				     		for(var i=0;i<msgarr.length;i++){
				     			msgs+=msgarr[i]+"</br>";
				     		}
				     		layer.close(dialogIndex);
				     		layer.alert("<span style='color: red;'>"+msgs+"<span>");
				     	}else if(obj.message){
				     		layer.close(dialogIndex);
				     		layer.alert(obj.message);
				     	}
				      }
				     bakUpload.isClicked=false;
				     layer.close(bakUpload.uploadDialog);	
				     var currentpage = $("div.paging-bar").find("li.active").html();//当前页
					 var Page={"page":currentpage,"pageSize":10};
					 selectBakPage(Page);
				  }
				},
				 error: function(data, status, e){
				       console.log(data,status,e);
				       bakUpload.isClicked=false;
				       layer.close(bakUpload.uploadDialog);	
				 }
			});
	});
	
	
	/**
		获取mongodb数据库,并加载到下拉框
	**/
	/* $.ajax({
		url:"${pageContext.request.contextPath}/database/getMongodbNames",
		type:"GET",
		data:{}
	}).done(function(data){
		if(data){
			//console.log("mongo",data);
			if(data.result){
				var devNames=data.result;//array
				$select=$("div[val=MongoDB]").find("select");
				var options='';
				for(var i=0;i<devNames.length;i++){
					options+='<option value="'+devNames[i]+'">'+devNames[i]+'</option>';
				}
				$select.html(options);
			}
		}
	}); */
	
	/*
		每个需要交换显示的select框，都必须有
		类名need-change，
		唯一的ID名 对应着 将需要显示的那个DIV的date-id属性，
		select框 的value值 对应着 DIV的val属性
		如示例
	 */

	$(document).on("change",".need-change",function(){
		var thisId =  $(this).attr("id");//select id ??
		var thisVal = $(this).val();// option value
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

		/*页面加载后，若select已经有值，先将对应的DIV显示*/
		
		$(document).find(".need-change").each(function(){
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

	function resultDialog(options){
		var defaults = {
			area:'["400px,320px"]',
			loadText:"数据库已备份成功",
			closeTime:2000,
			success:function(){}
		};
		defaults = $.extend(defaults,options);

		$('.status-word').text(defaults.loadText);

		var dialogIndex = layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:defaults.area,
			offset :["195px"],
			content: $('.result-state'), //捕获的元素
			success : function(index,layero){
				defaults.success();
				
			},
		});
		setTimeout(function(){
			layer.close(dialogIndex);
		},defaults.closeTime);

		return dialogIndex;
	}
	
	//datetimepicker 	timeFormat:'HH:mm:ss',
	$( ".datepicker").datepicker({
		controlType:"select",
		numberOfMonths:1,//显示几个月  
		showButtonPanel:true,//是否显示按钮面板  
		dateFormat: 'yy-mm-dd',//日期格式  
		clearText:"清除",//清除日期的按钮名称  
		closeText:"关闭",//关闭选择框的按钮名称  
		currentText:"当前时间",
		yearSuffix: '', //年的后缀  
		showMonthAfterYear:true,//是否把月放在年的后面  
		//defaultDate:'2016-05-19',//默认日期  
		//minDate:'2011-03-05',//最小日期  
		//maxDate:'2011-03-20',//最大日期  
		monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
		dayNamesMin: ['日','一','二','三','四','五','六'],  
		onSelect: function(selectedDate) {//选择日期后执行的操作  
	
		},
		beforeShow: function( input ) { 
			// 
	        setTimeout(function() {  
	          var buttonPane = $( input )  
	            .datepicker( "widget" )  
	            .find( ".ui-datepicker-buttonpane" );  
	          $( "<button>", {  
	            text: "清除",  
	            click: function() {  
	              $.datepicker._clearDate( input );  
	            }  
	          }).appendTo(buttonPane);  
	        }, 1);
	         //删除当前时间
	         setTimeout(function() { 
	         	$(".ui-datepicker-current").remove();  
	         },1) ;
      }    
	});
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		pageSize=$(this).val();
		var page = $("#page").find("li.active").html();
		var Page=makeQueryParam(page, pageSize);
		//带参数
		selectBakPage(Page);
	});
	$("#library_id").on("keyup",function(){
		var library_id=$(this).val();	
		var lib_info=libIdAndNameObj[library_id];
		if(lib_info){
			$("#library_IDX").val(lib_info.library_idx);
			$("#library_name").val(lib_info.lib_name);
		}
		console.log("lib_info",lib_info);
	});
});
function test(){
		var libInfo={"library_idx":16,"library_id":"LIB001"};
		$.ajax({
			url:"${pageContext.request.contextPath}/database/bakupByLibraryIdx",
			type:"post",
			data:{"req":JSON.stringify(libInfo)},
			success:function(data){
				console.log(data);
			}
		});	
}
function TEST_BAKUP(library_idx,library_id){
	var idx=$.trim(library_idx);
	var id=$.trim(library_id);
	if(!id || id==""){
		layer.alert("获取不到图书馆ID，请刷新后重试");
		return;
	}
	if(!idx || idx==""){
		layer.alert("获取不到图书馆IDX，请刷新后重试");
		return;
	}
	var libInfo={"library_idx":idx,"library_id":id};
	$.ajax({
		url:"${pageContext.request.contextPath}/database/restoreDataByLibraryIdx",
		type:"post",
		data:{"req":JSON.stringify(libInfo)},
		success:function(data){
			console.log(data);
			//还原成功之后处理
			if(data && data.state){
				
				
			
			}
		}
	});
}
</script>



