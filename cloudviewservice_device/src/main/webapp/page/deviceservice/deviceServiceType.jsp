<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		.w-900 label, .form-dialog label {
   		 /* padding-left: 10px; */
   		 	padding-left:2px;
    		vertical-align: middle;
		}
		
		.form-wrap label {
   			 margin-right: 10px;
		}
		.logic-obj-li li{
			float: left;
			/*min-width: 130px;*/
		}
		.logic-obj-li label{
			min-width: 100px;
		}
		.device-db-li li{
			float: left;
			/*min-width: 130px;*/
		}
		.device-db-li label{
			min-width: 100px;
		}
		.form-dialog .form-wrap .device-db-li{
   			 margin-bottom: 2px;
		}
		.form-dialog .form-wrap .logic-obj-li{
   			 margin-bottom: 2px;
		}
		.equipment-leixing .col6 {
    		width: 500px;
		}
		.equipment-leixing .col4 {
    		min-width: 100px;
		}
		.form-dialog .g-select select {
		  width: 255px;
		}
	</style>
</head>
<body>
<div class="equipment-leixing">
	
	<div class="page-title-bar">
		<span class="title">服务类型<a href="${pageContext.request.contextPath}/static/help-page.html" target="_blank" class="g-help"></a></span>
		<div class="form-wrap fr">
			<input type="text" name="keyword" id="keyword" class="input g-input" placeholder="填写风格类型名称" />
			<div class="btn search">查询</div>
			<!-- 修改权限 by huanghuang 20170215 -->
			<div class="btn increase g-btn-green">新增</div>
			<div class="btn delete">删除</div>
			
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox" ><input type="checkbox" name="" id="" /></div></th>
					<th class="col2">编号</th>
					<th class="col3">名称</th>
					<th class="col7">操作</th>
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
		当前选择了<span class="font-red"></span>个项目<br>
		是否要删除选择的设备显示类型？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>

<div class="form-dialog">
	<div class="title">新增服务类型</div>
	<input type="hidden" name="display_type_idx" id="display_type_idx"/>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">类型编号</span></div>
				<div class="right">
					<input type="text" name="service_type_id" id="service_type_id" class="g-input" placeholder="请输入"  />
					<span class="error-msg">类型编号不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">类型名称</span></div>
				<div class="right">
					<input type="text" name="service_type_name" id="service_type_name" class="g-input" placeholder="请输入"  />
					<span class="error-msg">类型名称不能为空</span>
				</div>
			</li>
			<li class="device-db-li"></li>
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input id="increaseBtn" type="submit" value="保存" class="submit g-btn-blue" />
				</div>
			</li>
		</ul>
	</div>
</div>

<script type="text/javascript">
var basePath = null;
var increaseLayer=null;
$(function(){
	var lang='zh_CN';
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
	basePath = location[0]+'//'+location[2]+'/'+location[3];  
	var delLayer = null;
	/**
	删除按钮操作显示对话框
	**/
	var servicetype_idx = new Array() ;
	var service_type_idx;
	$(".delete").on("click",function(){
		servicetype_idx.length = 0;
		var num = $("tbody input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			servicetype_idx.push($(this).attr("id"));
        });  
		
		if(num>0){
			$(".word").html("");
        	$(".word").append("当前选择了<span class='font-red'>"+num+"</span>个项目<br>是否要删除选择的设备显示类型？");
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
					layer.close(delLayer);
					this.content.hide();
				}
			});
		}else{
			alert("请选择要删除的项目！");
		}			
	});
	
	//删除按钮操作
	$("tbody").on("click",".delete",function(){
		servicetype_idx.length = 0;
		servicetype_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
		$('.word').html("");
		$('.word').append("是否要删除当前行的设备显示类型？");
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
				layer.close(delLayer);
				this.content.hide();
			}
		});
	});
	/**
		删除按钮操作
	**/
	$(".form-btn.remove").on("click",function(){
		
		GlobalGLoading();
		if(!servicetype_idx) return;
		$.ajax({
			url:basePath+"/deviceServiceType/deleteDeviceServiceType",
			type:"POST",
			data : {
				"req" : servicetype_idx.join(",")
			},
			success:function(data){
				if(delLayer){
					layer.close(delLayer);
				}
				if(data.state){
					GlobalShowMsg({showText:"删除成功",status:true});
			     	var currentpage = $(".paging-bar li.active").text();
			     	var size= $('#showSize option:selected').text();
					var Page ={"page":currentpage,"pageSize":size};
					//调用页面的查询ajax
					$.select(Page);  
			     }else if(!data.state){
			    	 
			    	 if(data.message != ''){ 
				     		showRetMessage(data.message);
				    	 }else{
				    		 showRetMessage("删除失败");
				    }
			     }
				
			}
		});
		
	});
	
    
	/**
		编辑按钮操作显示对话框
	**/
	$("tbody").on("click",".edit",function(){
		service_type_idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
    	layerOpen({
    		"title":"编辑",
            "btnText":"保存",
            "btnColorClass":"g-btn-blue",
            "status" : true,
            "service_type_idx":service_type_idx,
        });
	});
	
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});

	$(".g-increase-dialog .form-wrap  .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	});
	
	$(":text").focus(function(){
		$(this).parent().parent().removeClass("error");
	});
	
	/**
		新增按钮操作显示对话框
	**/

	$(".increase").on("click",function(){
		 layerOpen({
             "title":"新增",
             "btnText":"保存",
             "btnColorClass":"g-btn-blue",
             "status" : false
         });
	});
	
	//新增、修改保存按钮操作
	$("#increaseBtn").on("click",function(){
		var service_type_id = $("#service_type_id").val();
		var service_type_name=$("#service_type_name").val();
		
		if(!service_type_id){
			$("#service_type_id").parent().parent().addClass("error");
			return;
		}else if(!service_type_name){
			$("#service_type_name").parent().parent().addClass("error");
			return;
		}
		var deviceDbList = "";
		$(".device-db-li").find("input[type=checkbox]:checked").each(function(index,dom){
			deviceDbList+=$(dom).attr("id")+",";
		});
		/**清除尾部的逗号**/
		if(deviceDbList!=""){
			deviceDbList=deviceDbList.substring(0,deviceDbList.length-1);
		}else{
			//layer.alert("请至少选择一个逻辑部件");
			$("li.device-db-li").each(function(index,dom){
				$(dom).addClass("error");
			});
			return;
		}
		
		var baseURL=basePath+"/deviceServiceType/addDeviceServiceType";
		if (isEdit) {//编辑
			baseURL=basePath+"/deviceServiceType/editDeviceServiceType";
			if(!service_type_idx){
				return;
			}
			var param={
				"service_type_id":service_type_id,
				"service_type_name":service_type_name,
				"service_type_idx":service_type_idx,
				"service_type_db":deviceDbList
			};
		}else{//新增
			var param={
				"service_type_id":service_type_id,
				"service_type_name":service_type_name,
				"service_type_db":deviceDbList
			};
		}
		
		
		$.ajax({
			 url:baseURL,
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
			     if(data.state){
			     	if(increaseLayer){
			     		layer.close(increaseLayer);
			     	}
			     	GlobalShowMsg({showText:"操作成功",status:true});
			     	var currentpage = $(".paging-bar li.active").html();
			     	var size= $('#showSize option:selected').html();
					var Page ={"page":currentpage,"pageSize":size};
					//调用页面的查询ajax
					$.select(Page);
			     }else{
			    	 GlobalShowMsg({showText:"操作失败",status:false});
			     }
		 	}
		});
	});
	
	
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var service_type_name= $("#keyword").val();
		var size= $('#showSize option:selected').text();
		var param={"service_type_name":service_type_name,"page":"1","pageSize":size}; 
		$.select(param);
	});
	
	//封装查询ajax
	jQuery.select=function(param){
		$.ajax({
			url:basePath+"/deviceServiceType/queryDeviceServiceTypeByPage",
			type:"POST",
			data:{
				"req":JSON.stringify(param),
			},
			success:function(data){
				console.info(data);
				if (data.state) {
					$("tbody").html("");
					$(".paging-bar li:eq("+data.result.page+")").addClass("active");
					$.each(data.result.rows,function(i,item){
						console.info(item);
						$("tbody").append(
						"<tr>"+
	            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.service_type_idx+"' /></div></td>" +
	            	    "<td class='col2'>" + item.service_type_id    + "</td>" +
	            	    "<td class='col3'>" + item.service_type_name    + "</td>" +
	                    "<td class='col7'>"+
	                    "<span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span>" +
	                	"</td>"+
	                	"</tr>");	
					});
	    			$.pagination(data.result);
				}
			}
		});
	};

	 //上一页操作
	$("#page").on("click",".prev-page",function(){
		var currentpage = $(".paging-bar li.active").text();
		var page=Number(currentpage)-1;
		var size= $('#showSize option:selected').text();
		var service_type_name= $("#keyword").val();
		var param={"service_type_name":service_type_name,"page":page,"pageSize":size}; 
		$.select(param);
	});
	 
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").text();
		page = Number(currentpage) + 1;
		var size= $('#showSize option:selected').text();
		var service_type_name= $("#keyword").val();
		var param={"service_type_name":service_type_name,"page":page,"pageSize":size}; 
		$.select(param);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var size= $('#showSize option:selected').text();
		var page = $(this).html();
		if(page=="...") return;
		var service_type_name= $("#keyword").val();
		var param={"service_type_name":service_type_name,"page":page,"pageSize":size}; 
		$.select(param);
		
	}); 
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var size= $('#showSize option:selected').text();
		var device_type_desc= $("#device_type_desc").val();
		var param={"device_type_desc":device_type_desc,"page":"1","pageSize":size}; 
		$.select(param);
	});
	
});

var isEdit = false;
function layerOpen(options){
    var defaults = {
        title:"",
        btnText : "保存"
    }
    options = $.extend(defaults,options);
    if($("#device_type_idx").parent().parent().parent().hasClass("error")){
		$("#device_type_idx").parent().parent().parent().removeClass("error");
	}
    var $submit = $(".form-dialog .submit");
    $(".form-dialog .title").text(options.title);
    if(options.status){
    	isEdit = true;
    	var param = {
				"service_type_idx" : options.service_type_idx,
			};
    	$.ajax({
    		url:basePath+"/deviceServiceType/queryDeviceServiceTypeByParams",
			type : "POST",
			data : {
				"req" : JSON.stringify(param)
			},
			success : function(data) {
				if (data.state) {
					$("#service_type_id").val(data.result[0].service_type_id);
					$("#service_type_name").val(data.result[0].service_type_name);
					$(".device-db-li").find("input[type=checkbox]").removeAttr("checked").parents(".g-checkbox").removeClass("on");
					var device_db_list = data.result[0].service_type_db;
					
					if(device_db_list){
						if(device_db_list.indexOf(",")){
							var device_db_list_arr=device_db_list.split(",");
							for(var i=0;i<device_db_list_arr.length;i++){
								var id=device_db_list_arr[i];
								$(".device-db-li").find("input[type=checkbox][id="+id+"]").prop("checked",true).parents(".g-checkbox").addClass("on");
							}
						}else{
							
						}
					}
				}
			}
    	});
    }
    else{
    	//$("#display_type_idx").val("");
    	$("#service_type_id").val("")
		$("#service_type_name").val("");
    	$(".device-db-li").find("input[type=checkbox]").removeAttr("checked").parents(".g-checkbox").removeClass("on");
    	//$(".device-db-li").find("input[type=checkbox][id="+id+"]").prop("checked",true).parents(".g-checkbox").addClass("on");
    	isEdit = false;
    }
    $submit.val(options.btnText);
    $submit.removeClass("g-btn-green").removeClass(".g-btn-blue");
    $submit.addClass(options.btnColorClass);
    increaseLayer=layer.open({
		type: 1,
		shade: false,
		title: false, //不显示标题
		scrollbar :false,
		closeBtn :1,
		shade:0.5,
		shadeClose :false,
		area:["800px"],
		offset :["180px"],
		content: $(".form-dialog"), //捕获的元素
		cancel: function(index){
			layer.close(increaseLayer);
			this.content.hide();
		}
	});
};

function getDeviceDb(){
	
$.ajax({
	url:basePath+"/metadevicetype/selectMetadataDeviceDb",
	data:{req:""},
	type:"GET"
}).done(function(data){
	if(data&&data.state&&data.result){
		var metaDeviceDbs=data.result;
		$(".device-db-li").remove();
		var li='<li class="device-db-li">'+
				'<div class="left"><span class="g-mustIn">设备本地数据库</span></div>'+
				'<ul>';
		$.each(metaDeviceDbs,function(index,metaDeviceDb){
			if(index!=0&&(index+1)%4==0){/**每行最后一个**/
				//4的倍数
				li+='<li>';
				li+='<div class="g-checkbox">';
				li+='<input type="checkbox" id="'+metaDeviceDb.db_name+'">';
				li+='</div>';
				li+='<label style="display:inline-block;" for="'+metaDeviceDb.db_name+'">'+metaDeviceDb.db_name+'</label>';
				li+='</li>';
				li+='</ul></li>';
				if((index+1)<metaDeviceDbs.length){
					li+='<li class="device-db-li"><div class="left"><span class="">&nbsp;</span></div><ul>';
				}
			}else if(index+1==metaDeviceDbs.length){/**最后一个**/
				li+='<li>';
				li+='<div class="g-checkbox">';
				li+='<input type="checkbox" id="'+metaDeviceDb.db_name+'">';
				li+='</div>';
				li+='<label style="display:inline-block;" for="'+metaDeviceDb.db_name+'">'+metaDeviceDb.db_name+'</label>';
				li+='</li>';
				li+='</ul></li>';
			}else{/**其他**/
				li+='<li>';
				li+='<div class="g-checkbox">';
				li+='<input type="checkbox" id="'+metaDeviceDb.db_name+'">';
				li+='</div>';
				li+='<label style="display:inline-block;" for="'+metaDeviceDb.db_name+'">'+metaDeviceDb.db_name+'</label>';
				li+='</li>';
			}
		});
		li+='<li class="device-db-li">'+
			 '<div class="left">&nbsp;</div>'+
				 '<div class="right"><span class="error-msg">至少需要选择一个设备本地数据库</span></div>'+	
			'</li>';
		
		$("#service_type_name").parents("li").after(li);
		$("li.device-db-li").on("change","input[type=checkbox]",function(){
			$("li.device-db-li").each(function(index,d){
				$(d).removeClass("error");
			});
		});
	}
});
}




$(function() {
	var Page = {
		"page" : 1,
		"pageSize" : $('#showSize option:selected').text(),
	};
	$.select(Page);
	getDeviceDb();
	
});

function isNotURL(str_url) {// 验证url  
    var strRegex=/(http|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/; 
    var re=new RegExp(strRegex); 
    return !re.test(str_url); 
}
</script>
</body>
</html>


