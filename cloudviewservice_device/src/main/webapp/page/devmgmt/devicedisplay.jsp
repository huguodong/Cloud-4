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
		<span class="title">设备显示类型<a href="${pageContext.request.contextPath}/static/help-page.html" target="_blank" class="g-help"></a></span>
		<div class="form-wrap fr">
			<input type="text" name="keyword" id="keyword" class="input g-input" placeholder="填写风格类型名称" />
			<div class="btn search">查询</div>
			<!-- 修改权限 by huanghuang 20170215 -->
			<shiro:hasPermission name="0102020401">
				<div class="btn increase g-btn-green">新增</div>
			</shiro:hasPermission>
			<shiro:hasPermission name="0102020402">
				<div class="btn delete">删除</div>
			</shiro:hasPermission>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox" ><input type="checkbox" name="" id="" /></div></th>
					<th class="col2">风格类型编号</th>
					<th class="col3">风格类型名称</th>
					<th class="col4">关联设备类型</th>
					<th class="col6">跳转的URL</th>
					<th class="col5">备注</th>
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
	<div class="title">新增设备显示类型</div>
	<input type="hidden" name="display_type_idx" id="display_type_idx"/>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">风格类型编号</span></div>
				<div class="right">
					<input type="text" name="display_type_id" id="display_type_id" class="g-input" placeholder="请输入"  />
					<input type="hidden" name="version_stamp" id="version_stamp" class="g-input" />
					<span class="error-msg">风格类型编号不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">风格类型名称</span></div>
				<div class="right">
					<input type="text" name="display_type_name" id="display_type_name" class="g-input" placeholder="请输入"  />
					<span class="error-msg">风格类型名称不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">关联设备类型</span></div>
				<div class="right">
					<div class="g-select">
						<select id="device_type_idx">
							<option value="">请选择</option>
						</select>
						<span class="arr-icon"></span>
					</div>
					<span class="error-msg">请选择关联设备类型</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">跳转的URL</span></div>
				<div class="right">
					<input type="text" name="display_type_url" id="display_type_url" class="g-input" placeholder="请输入"  />
					<span class="error-msg">请填写正确的跳转地址 (以http://或者https://开头)</span>
				</div>
			</li>
			<li class="logic-obj-li"></li>
			<li>
				<div class="left">备注</div>
				<div class="right">
					<textarea class="g-textarea" name="display_type_desc" id="display_type_desc"></textarea>
				</div>
			</li>
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
	var devicetype_idx = new Array() ;
	$(".delete").on("click",function(){
		devicetype_idx.length = 0;
		var num = $("tbody input[name='idx']:checked").length;
		$("tbody input[name='idx']:checked").each(function() {  
			devicetype_idx.push($(this).attr("id"));
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
		devicetype_idx.length = 0;
		devicetype_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
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
		if(!devicetype_idx) return;
		$.ajax({
			url:basePath+"/devicedisplay/delete",
			type:"POST",
			data : {
				"req" : devicetype_idx.join(",")
			},
			success:function(data){
			if(data.state){
				if(delLayer){
					layer.close(delLayer);
				}
				if(data.result){
					GlobalShowMsg({showText:"删除成功",status:true});
				}
		     	var currentpage = $(".paging-bar li.active").text();
		     	var size= $('#showSize option:selected').text();
				var Page ={"page":currentpage,"pageSize":size};
				//调用页面的查询ajax
				$.select(Page);  
		     }else if(data.retMessage){
		     	showRetMessage(data.retMessage);
		     }
			}
		});
		
	});
	
    
	/**
		编辑按钮操作显示对话框
	**/
	$("tbody").on("click",".edit",function(){
		var display_type_idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
		var version_stamp =$(this).parent().parent().find(".version_stamp").html();
    	layerOpen({
    		"title":"编辑",
            "btnText":"保存",
            "btnColorClass":"g-btn-blue",
            "status" : true,
            "index":display_type_idx,
            "version_stamp":version_stamp
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
		var display_type_idx = $("#display_type_idx").val();
		var display_type_id = $("#display_type_id").val();
		var display_type_name=$("#display_type_name").val();
		var device_type_idx=$("#device_type_idx").val();
		var display_type_url=$("#display_type_url").val();
		var display_type_desc=$("#display_type_desc").val();
		
		if($("#device_type_idx").parent().parent().parent().hasClass("error")){
			$("#device_type_idx").parent().parent().parent().removeClass("error");
		}
		
		if(!display_type_id){
			$("#display_type_id").parent().parent().addClass("error");
			//$(".error-msg").text("风格类型编号不能为空!");
			return;
		}else if(!display_type_name){
			$("#display_type_name").parent().parent().addClass("error");
			//$(".error-msg").text("风格类型名称不能为空!");
			return;
		}else if(!device_type_idx||device_type_idx==-1){
			$("#device_type_idx").parent().parent().parent().addClass("error");
			//$(".error-msg").text("请选择设备类型!");
			return;
		}else if(!display_type_url){
			$("#display_type_url").parent().parent().addClass("error");
			//$(".error-msg").text("跳转的URL不能为空!");
			return;
		}else if(isNotURL(display_type_url)){
			$("#display_type_url").parent().parent().addClass("error");
			return;
		}
		
		var baseURL=basePath+"/devicedisplay/insert";
		if (isEdit) {//编辑
			baseURL=basePath+"/devicedisplay/update";
			var version_stamp=$("#version_stamp").val();
			if(!display_type_idx){
				return;
			}
			var param={
				"display_type_idx":display_type_idx,
				"display_type_id":display_type_id,
				"display_type_name":display_type_name,
				"device_type_idx":device_type_idx,
				"display_type_url":display_type_url,
				"display_type_desc":display_type_desc,
				"version_stamp":version_stamp
			};
		}else{//新增
			var param={
				"display_type_id":display_type_id,
				"display_type_name":display_type_name,
				"device_type_idx":device_type_idx,
				"display_type_url":display_type_url,
				"display_type_desc":display_type_desc
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
			    	 var msg = data.message;
			    	 if(msg.indexOf("optimistic")>=0){
			    		 showRetMessage("当前选择数据不是最新数据,请刷新之后再做编辑操作");
			    	 }else{
			    		 showRetMessage(data.retMessage);
			    	 }
			     }
		 	}
		});
	});
	
	
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var display_type_name= $("#keyword").val();
		var size= $('#showSize option:selected').text();
		var param={"display_type_name":display_type_name,"page":"1","pageSize":size}; 
		$.select(param);
	});
	
	//封装查询ajax
	jQuery.select=function(param){
		$.ajax({
			url:basePath+"/devicedisplay/queryAll",
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
	            		"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.display_type_idx+"' /></div></td>" +
	            	    "<td class='col2'>" + item.display_type_id    + "</td>" +
	            	    "<td class='col3'>" + item.display_type_name    + "</td>" +
	            	    "<td class='col4'>" + item.device_type_name + "</td>" +
	                    "<td class='col6'>" + item.display_type_url + "</td>" +
	                    "<td class='col5'>" + item.display_type_desc + "</td>"+
	                    "<td class='col7'>"+
	                    "<span class='btn-a edit'>编辑</span><span class='btn-a delete'>删除</span>" +
	                	"</td>"+
	                	"<td class='version_stamp hide'>" + item.version_stamp + "</td>" +
	                	"</tr>");	
					});
					//一条都没有的情况下，尝试跳转到上一页
					if(data.result.rows.length==0&&param.page!=1){
						param.page-=1;
						$.select(param);
					}
					var t=0;
					<shiro:lacksPermission name="0102020402">
	   			 		t++;
						$(".delete").attr("style","display:none;");
	    			</shiro:lacksPermission>
	    			<shiro:lacksPermission name="0102020403">
	    				t++;
						$(".edit").attr("style","display:none;");
	    			</shiro:lacksPermission>
	    			if(t==2){
	    				$(".col7").attr("style","display:none;");
	    			}
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
		var display_type_name= $("#keyword").val();
		var param={"display_type_name":display_type_name,"page":page,"pageSize":size}; 
		$.select(param);
	});
	 
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").text();
		page = Number(currentpage) + 1;
		var size= $('#showSize option:selected').text();
		var display_type_name= $("#keyword").val();
		var param={"display_type_name":display_type_name,"page":page,"pageSize":size}; 
		$.select(param);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var size= $('#showSize option:selected').text();
		var page = $(this).html();
		if(page=="...") return;
		var display_type_name= $("#keyword").val();
		var param={"display_type_name":display_type_name,"page":page,"pageSize":size}; 
		$.select(param);
		
	}); 
	/**
		每页显示的条目数切换
	**/
	$("select#showSize").on("change",function(){
		GlobalGLoading();
		var size= $('#showSize option:selected').text();
		var display_type_name= $("#keyword").val();
		var param={"display_type_name":display_type_name,"page":"1","pageSize":size}; 
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
    	$("#version_stamp").val(options.version_stamp);
    	var param = {
				"display_type_idx" : options.index,
			};
    	$.ajax({
    		url:basePath+"/devicedisplay/findById",
			type : "POST",
			data : {
				"req" : JSON.stringify(param)
			},
			success : function(data) {
				if (data.state) {
					$("#display_type_idx").val(data.result.display_type_idx);
					$("#display_type_id").val(data.result.display_type_id);
					$("#display_type_id").attr("disabled",true);
					$("#display_type_name").val(data.result.display_type_name);
					$("#display_type_url").val(data.result.display_type_url);
					$("#display_type_desc").val(data.result.display_type_desc);
					var device_type_idx=data.result.device_type_idx;
					$.ajax({
						url : basePath + "/devicedisplay/getAllDeviceTypes",
						type : "POST",
						success : function(data) {
							var _html="";
							if (data.state) {
								$("#device_type_idx").empty();//清空下拉框
								$("#device_type_idx").append("<option value='' selected>请选择</option>");
								$.each(data.result,function(i, item) {
									if(device_type_idx==item.meta_devicetype_idx){
										_html+="<option value='"+item.meta_devicetype_idx+"' data-type='"+item.device_type+"' selected>"+item.device_type_desc+"</option>"
									}else{
									
										_html+="<option value='"+item.meta_devicetype_idx+"' data-type='"+item.device_type+"'>"+item.device_type_desc+"</option>"
									}
								});
								$("#device_type_idx").append(_html);
							}else{
								$("#device_type_idx").val(device_type_idx);
							}
						}
					});
				}
			}
    	});
    }
    else{
    	$.ajax({
			url : basePath + "/devicedisplay/getDeviceTypes",
			type : "POST",
			success : function(data) {
				var _html="";
				if (data.state) {
					$("#device_type_idx").empty();//清空下拉框
					$("#device_type_idx").append("<option value='' selected>请选择</option>");
					$.each(data.result,function(i, item) {
						_html+="<option value='"+item.meta_devicetype_idx+"' data-type='"+item.device_type+"'>"+item.device_type_desc+"</option>"
					});
					$("#device_type_idx").append(_html);
				}
			}
		});
    	$("#display_type_idx").val("");
    	$("#display_type_id").val("");
		$("#display_type_id").removeAttr("disabled");
		$("#display_type_name").val("");
		$("#device_type_idx").val("");
		$("#display_type_url").val("");
		$("#display_type_desc").val("");
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
		area:["600px"],
		offset :["180px"],
		content: $(".form-dialog"), //捕获的元素
		cancel: function(index){
			layer.close(increaseLayer);
			this.content.hide();
		}
	});
};

$(function() {
	var Page = {
		"page" : 1,
		"pageSize" : $('#showSize option:selected').text(),
	};
	$.select(Page);
});

function isNotURL(str_url) {// 验证url  
    var strRegex=/(http|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/; 
    var re=new RegExp(strRegex); 
    return !re.test(str_url); 
}
</script>
</body>
</html>


