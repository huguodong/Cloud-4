<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>

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
		.equipment-guanli .col2 {
    		width: 150px;
		}
		.equipment-guanli .col10 {
    		width: 200px;
		}
		.form-dialog .form-wrap{
			padding:20px 0 20px;
		}
		.form-dialog .form-wrap .left{
			width:180px;
		}
		.form-dialog .form-wrap .right{
			width: 380px;
		}
		/*a  upload */
		.main-content .form-dialog a {
		    color: #888;
		}
		.a-upload {
		    padding: 4px 10px;
		    height: 20px;
		    line-height: 20px;
		    position: relative;
		    cursor: pointer;
		    color: #888;
		    background: #eee;
		    border: 1px solid #ddd;
		    border-radius: 4px;
		    overflow: hidden;
		    display: inline-block;
		    *display: inline;
		    *zoom: 1
		}
		.a-upload  input {
		    position: absolute;
		    font-size: 100px;
		    right: 0;
		    top: 0;
		    opacity: 0;
		    filter: alpha(opacity=0);
		    cursor: pointer
		}
		
		.a-upload:hover {
		    color: #444;
		    background: #eee;
		    border-color: #ccc;
		    text-decoration: none
		}
	</style>
</head>
<body>
<div class="equipment-guanli">
	<div class="page-title-bar">
		<!-- <a href="" class="back-btn"></a> -->
		<span class="title"><sp:message code="大屏展示页面功能"></sp:message><a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/devmgmt/devicemgmt.jsp" target="_blank" class="g-help"></a></span>
		<div class="form-wrap fr">
			
			<div class="g-select" id="library_div">
				<select id="library">
					<option value="-1"><sp:message code="devicemgmt.jsp.searchlibrary"></sp:message></option>
				</select>
				<span class="arr-icon"></span>
			</div>
			<div class="btn search" ><sp:message code="message.gloable.search"></sp:message></div>
			<div class="btn increase g-btn-green" ><sp:message code="message.gloable.add"></sp:message></div>
			<div class="btn delete"><sp:message code="message.gloable.delete"></sp:message></div>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
					<th class="col2"><sp:message code="显示标题"></sp:message></th>
					<th class="col2"><sp:message code="显示页面"></sp:message></th>
					<th class="col2"><sp:message code="图书馆"></sp:message></th>	
					<th class="col2"><sp:message code="图书馆所在城市"></sp:message></th>	
					<th class="col2"><sp:message code="安全门名称"></sp:message></th>	
					<%-- <th class="col2"><sp:message code="图书馆logo"></sp:message></th>	 --%>
					<th class="col2"><sp:message code="操作"></sp:message></th>
				</tr>
			</thead>
			<tbody>
			
			</tbody>
		</table>
	</div>
	<%@ include file="../include/page_bar.jsf" %>
</div>
<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		<sp:message code="devicemgmt.jsp.curselect"></sp:message> <span class="font-red"></span> <sp:message code="devicemgmt.jsp.selectunit"></sp:message><br>
		<sp:message code="devicemgmt.jsp.delmonitortip"></sp:message>
	</div>
	<div class="form-btn cancel g-btn-gray"><sp:message code="message.gloable.cancel"></sp:message></div>
	<div class="form-btn remove g-btn-red"><sp:message code="message.gloable.delete"></sp:message></div>
</div>

<div class="form-dialog" id="add">
	<div class="title"><sp:message code="配置大屏展示页面"></sp:message></div>
	<input type="hidden" name="display_info_idx" id="display_info_idx"/>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">大屏展示标题</span></div>
				<div class="right">
					<input type="text" name="display_title" id="display_title" class="g-input" placeholder='请填写大屏展示标题'/>
					<span class="error-msg">大屏展示标题不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">图书馆ID</span></div>
				<div class="right">
					<input type="hidden" name="library_idx" id="library_idx"/>
					<input type="text" name="library_id" id="library_id" class="g-input" placeholder='请填写图书馆ID'  />
					<span class="error-msg">图书馆ID不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">图书馆名称</span></div>
				<div class="right">
					<input type="text" name="library_name" id="library_name" style="color:gray;background-color:#E0E0E0" readonly="readonly"class="g-input"/>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">图书馆所在城市</span></div>
				<div class="right">
					<input type="text" name="weather_city" id="weather_city" class="g-input" placeholder='请填写图书馆所在城市'  />
				</div>
			</li>
			<li>
				<div class="left"><span>显示页面</span></div>
				<div class="right">
					<div class="g-select">
						<select name="index_page" id="index_page">
							<option value="index1">云平台用户显示页</option>
							<option value="index">其他图书馆用户显示页</option>
						</select>
						<span class="arr-icon"></span>
					</div>
				</div>
			</li>
			<li>
				<div class="left"><span style="display: block;">安全门名称及序号</span><span>(*多个以逗号分隔)</span></div>
				<div class="right">
					<textarea name="patron_door" id="patron_door" class="g-textarea" placeholder='示例：北门|1,南门|2'/>
				</div>
			</li>
			<li style="display:none">
				<div class="left"><span>图书馆logo</span></div>
				<div class="right">
					<a href="javascript:;" class="a-upload">
					    <input type="file" name="logo_img" id="logo_img">上传图书馆logo文件
					</a>
					<span class="showFileName"></span>
					<span class="error-msg">上传文件格式不正确</span>
				</div>
			</li>
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input id="increaseBtn"  type="button" value="保存" class="submit g-btn-blue" />
				</div>
			</li>
		</ul>
	</div>
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
	
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
	
	var delLayer = null;
	var editLayer = null;
	var libraryInfos={};
	/**
	 * 删除操作按钮
	 **/
	var display_info_idx = new Array();
	$(".delete").on("click",function(){
		display_info_idx.length = 0;
		
		var num = $("tbody Input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			display_info_idx.push($(this).attr("id"));
        }); 
		if(num>0){
			$(".g-delete-dialog .word").html("");
        	$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"个项目<br>是否要删除选择的应用");
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
		   });
		}else{
			layer.alert("请选择要删除的应用");
		}		
	});
	
	//删除当前行的应用
	$("tbody").on("click",".delete",function(){
		
		display_info_idx.length = 0;
		display_info_idx.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
		
		$('.g-delete-dialog .word').html("");
		$('.g-delete-dialog .word').append("是否要删除选择的应用");
		delLayer = layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["450px"],
			offset :["195px"],
			content: $('.g-delete-dialog'), //捕获的元素
			cancel: function(index){
				layer.close(delLayer);
				this.content.hide();
			}
		});
	});
	
	//编辑操作
	$("tbody").on("click",".edit",function(){
		var idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
		var param ={"display_info_idx":idx};
		reset();
		$.ajax({
			url:basePath+"/thirdPartyService/queryDisplayInfo",
			type:"POST",
			data:{
				"req":JSON.stringify(param)
			},
			success:function(data){
				if(data&&data.state){
					var result = data.result;
					$("#display_info_idx").val(result.display_info_idx);
					$("#index_page").val(result.index_page);
					$("#display_title").val(result.display_title);
					$("#weather_city").val(result.weather_city);
					$("#patron_door").val(result.patron_door);
					$("#logo_img").val(result.logo_img);
					
					var library_idx=result.library_idx;
					$("#library_idx").val(library_idx);
					if(typeof(library_idx)!='undefined' && libraryInfos){
						$.each(libraryInfos,function(i,item){
							if(item.library_idx==library_idx){
								$("#library_name").val(item.lib_name);
								$("#library_id").val(item.lib_id);
								$("#library_id").attr("readonly",true);
								return true;
							}
						});
					}
					
					editLayer=layer.open({
						type: 1,
						shade: false,
						title: false, //不显示标题
						scrollbar :false,
						closeBtn :1,
						shade:0.5,
						shadeClose :false,
						area:["600px"],
						offset :["195px"],
						content: $('#add'), //捕获的元素
						cancel: function(index){
							layer.close(editLayer);
							this.content.hide();
						}
					});
				}
			}
		});
		
	});

	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	
	//批量删除操作
	$(".form-btn.remove").on("click",function(){
		GlobalGLoading();
		if(!display_info_idx) return;
		var param={
			"display_info_idx":display_info_idx
		};
		$.ajax({
			url:basePath+"/thirdPartyService/deleteDisplayInfo",
			type:"POST",
			data:{
				"req":JSON.stringify(param)
			},
			success:function(data){
				//console.log(".form-btn.remove ",data);
				if(delLayer){
					layer.close(delLayer);
				}
				if(data.state){
					layer.alert(data.message);
					queryDisplayInfo();
				}
			}
		});
	});
	
	//封装的查询操作
	jQuery.select=function(param){
		$("tbody").empty();
		$.ajax({
			url:basePath+"/thirdPartyService/queryDisplayInfoList",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				if(data&&data.state){
					$.each(data.result.rows,function(i,item){
						var page=item.index_page;
						if(page=='index1'){
							page="云平台用户显示页";
						}else{
							page="其他图书馆用户显示页";
						}
						var door=item.patron_door;
						if(typeof(door)=='undefined'){
							door="";
						}
						/* var logo=item.logo_img;
						if(typeof(logo)=='undefined'){
							logo="";
						} */
						
						$("tbody").append( "<tr>"+
				            "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.display_info_idx+"' /></div></td>"+
							"<td>"+item.display_title+"</td>"+
							"<td>"+page+"</td>"+
							"<td id='"+item.display_info_idx+"_lib'>"+item.library_idx+"</td>"+
							"<td>"+item.weather_city+"</td>"+
							"<td>"+door+"</td>"+
							/* "<td>"+logo+"</td>"+ */
							"<td><span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span></td>"+
		                	"</tr>");
						//console.info(JSON.stringify(libraryInfos));
						$.each(libraryInfos,function(i,libInfo){
							if(libInfo.library_idx == item.library_idx){
								$("#"+item.display_info_idx+"_lib").text(libInfo.lib_name);
							}
					    });
					});
					$.pagination(data.result); 
				}
			}
		});	
	};
	
	/**
		查询按钮操作
	**/
	$(".search").on("click",function(){
		queryDisplayInfo();
	});
	
	function queryDisplayInfo(){
		var library_idx=$("#library").val()
		var size= $('#showSize option:selected').text();
		var display_title=$("#display_title_query").val();
		if(library_idx == -1){
			library_idx = "";
		}	
		var param={
			"library_idx":library_idx,
			"display_title":display_title,
			"page":"1",
			"pageSize":size
		};
		$.select(param);
	}
	
	
	function isNull(param){
		if(param == null){
			return "";
		}else{
			return param;
		}
	}
	
	//初始化弹出框
	function reset(){
		$("#display_info_idx,#index_page,#display_title,#weather_city,#patron_door,#logo_img,#library_name,#library_id,#library_idx").val("");
		$(".form-wrap ul").find("li").each(function () {
			$(this).removeClass("error");
		});		
		$("#library_id").removeAttr("readonly");
	}
	
	/**
	 *	监听图书馆ID变化 改变对应的图书馆名
	 **/
	 $(document).on("change","#library_id",function(){
		var lib_id=$(this).val();
		if(lib_id && libraryInfos){
			$.each(libraryInfos,function(i,item){
				if(item.lib_id==lib_id){
					$("#library_name").val(item.lib_name);
					$("#library_idx").val(item.library_idx);
					return true;
				}
			});
		}else{
			$("#library_name").val("");
			$("#library_idx").val("");
		}
	});
	
	$(document).ready(function() {
		$.ajax({
	   		url:basePath+"/library/querylibInfoByCurUserEXT1",
			type:"POST",
			async: false,
			success:function(data){
				libraryInfos = data.result;
				$("#library").html("<option value='-1' selected>选择图书馆</option>");
				$.each(libraryInfos,function(i,item){
					$("#library").append("<option value='"+item.library_idx+"'>"+item.lib_name+"</option>");
		        });
	   		}
    	});
		
    	var param = {
   			"page" : "1",
   			"pageSize" : $('#showSize option:selected').text(),
   		};
    	$.select(param);
 	});
	
	$(".increase").on("click",function(){
		reset();
		editLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["600px"],
			offset :["195px"],
			content: $('#add'), //捕获的元素
			cancel: function(index){
				layer.close(editLayer);
				this.content.hide();
			}
		});
		$("#app_key").closest("li").hide();
	});
 	
	
	$("#increaseBtn").on("click",function(){
		var display_title=$("#display_title").val();
		var library_id=$("#library_id").val();
		var library_name=$("#library_name").val();
		var index_page=$("#index_page").val();
		var weather_city=$("#weather_city").val();
		if(_.isEmpty(display_title)){
			$("#display_title").closest("li").addClass("error");
			return false;
		}else if(_.isEmpty(library_id)){
			$("#library_id").closest("li").addClass("error");
			return false;
		}else if(_.isEmpty(library_name)){
			$("#library_name").closest("li").addClass("error");
			return false;
		}else if(_.isEmpty(index_page)){
			$("#index_page").closest("li").addClass("error");
			return false;
		}else if(_.isEmpty(weather_city)){
			$("#weather_city").closest("li").addClass("error");
			return false;
		}
		var display_info_idx=$("#display_info_idx").val();
		var patron_door=$("#patron_door").val();
		var logo_img=$("#logo_img").val();
		var library_idx=$("#library_idx").val();
		
		var param={
			"display_info_idx":display_info_idx,
			"display_title":display_title,
			"index_page":index_page,
			"weather_city":weather_city,
			"library_idx":library_idx,
			"patron_door":patron_door,
			"logo_img":logo_img
		};	
		//console.info(JSON.stringify(param));
		$.ajax({
	   		url:basePath+"/thirdPartyService/editDisplayInfo",
			type:"POST",
			data:{"req":JSON.stringify(param)},
			success:function(data){
				//console.info(data);
				if(data.state){
					if(data.message)layer.alert(data.message);
					if(editLayer)layer.close(editLayer);
					queryDisplayInfo();
				}else{
					if(data.message)layer.alert(data.message);
					else if(data.retMessage)layer.alert(data.retMessage);
				}
	   		}
    	});
		
	});
	
	$(".a-upload").on("change","input[type='file']",function(){
	    var filePath=$(this).val();
	    if(filePath.indexOf("jpg")!=-1 || filePath.indexOf("png")!=-1){
	        $("#logo_img").closest("li").removeClass("error");
	        var arr=filePath.split('\\');
	        var fileName=arr[arr.length-1];
	        $(".showFileName").html(fileName);
	    }else{
	        $(".showFileName").html("");
	        $("#logo_img").closest("li").addClass("error");
	        return false 
	    }
	});
	
 	 //上一页操作
	$("#page").on("click",".prev-page",function(){
		
		var library_idx=$("#library").val()
		
		var display_title=$("#display_title_query").val();
		if(library_idx == -1){
			library_idx = "";
		}	
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)-1;
		var size= $('#showSize option:selected').html();
		
		var param={
			"library_idx":library_idx,
			"display_title":display_title,
			"page":page,
			"pageSize":size
		};	
		$.select(param);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		
		var library_idx=$("#library").val()
		
		var display_title=$("#display_title_query").val();
		if(library_idx == -1){
			library_idx = "";
		}	
		var currentpage = $(".paging-bar li.active").html();
		var page=Number(currentpage)+1;
		var size= $('#showSize option:selected').html();
		
		var param={
			"library_idx":library_idx,
			"display_title":display_title,
			"page":page,
			"pageSize":size
		};	
		$.select(param);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var page = $(this).html();
		if(page=="...") return;
		var library_idx=$("#library").val()
		var display_title=$("#display_title_query").val();
		if(library_idx == -1){
			library_idx = "";
		}	
		var size= $('#showSize option:selected').html();
		
		var param={
			"library_idx":library_idx,
			"display_title":display_title,
			"page":page,
			"pageSize":size
		};	
		$.select(param);
		
	}); 
	
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var library_idx=$("#library").val()
		var display_title=$("#display_title_query").val();
		if(library_idx == -1){
			library_idx = "";
		}
		var size= $('#showSize option:selected').html();
		
		var param={
			"library_idx":library_idx,
			"display_title":display_title,
			"page":1,
			"pageSize":size
		};	
		$.select(param);
	});
});
</script>
</body>
</html>


