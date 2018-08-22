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
    		width: 600px;
		}
		.equipment-leixing .col9 {
    		width: 200px;
		}
		.equipment-leixing .col2 {
    		min-width: 100px;
		}
		.equipment-leixing .col3 {
    		width: 200px;
		}
		.equipment-leixing .col4 {
    		width: 200px;
		}
	</style>
</head>
<body>

<div class="equipment-leixing">
	<div class="page-title-bar">
		<span class="title">模板管理</span>
		<div class="form-wrap fr">
			<div class="g-select">
					<select id="statType">
						<option value="-1" selected>选择类型</option>
						<option value="1" >查询模板</option>
						<option value="2" >统计模板</option>
					</select>
					<span class="arr-icon"></span>
				</div>
			<input type="text" name="keyword" id="Keyword_lib_idx" class="input g-input" placeholder="模板id或者模板名" />
			<div class="btn search">查询</div>
			<div class="btn increase increase-layer">
				<span class="add">新增</span>
				<div class="increase-list">
					<div class="icon"></div>
					<ul>
						<li><a href="javascript:stemplate();"><strong>+</strong> 查询模板</a></li>
						<li><a href="javascript:template();"><strong>+</strong> 统计模板</a></li>
					</ul>
				</div>
			</div>
			<div class="btn" id="copyRow">复制</div>
			<!-- <div class="btn delete">删除</div> -->
		</div>
	</div>
	<div class="main">
		<table class="g-table g-table-v2">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox" ><input type="checkbox" name="" id="" /></div></th>
					<th class="col2" >ID</th>
					<th class="col3">模板类型</th>
					<th class="col4">模板名称</th>
					<th class="col6">简介</th>
					<th class="col9">操作</th>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
	</div>
	<a href="javascript:void(0)" id="chaxun"></a>
	<div class="paging-bar">
			<div class="left">
				<span class="t fl">选择</span>
				<div class="btn g-chooseAll">全选</div>
				<div class="btn g-noChooseAll">反选</div>
				<span class="t2 fl">显示</span>
				<div class="g-select refresh">
					<select id="pagesize">
						<option value="10" selected>10</option>
						<option value="30" >30</option>
						<option value="60">60</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<span class="t2 fl " id="ChooseNum">已选中<span class="total-choosen-num">0</span>个</span>
			</div>
			<div id="page" class="right">
			</div>
			<span class="total-page fr"></span>
			<span class="total-num fr"></span>
	</div>
</div>
<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red">7</span> 个项目<br>
		是否要删除选择的模板？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<script type="text/javascript">
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
	var basePath = location[0]+'//'+location[2]+'/'+location[3];  
	var delLayer = null;
	
	
	/**
	删除按钮操作显示对话框
**/
var statistics_tpl_idx = new Array() ;
$(".delete").on("click",function(){
	statistics_tpl_idx.length = 0;
	var num = $("tbody Input[name='idx']:checked").length;
	$("tbody input[name='idx']:checked").each(function() {  
		statistics_tpl_idx.push($(this).attr("id"));
    });  
	
	if(num>0){
		$(".g-delete-dialog .word").html("");
    	$(".g-delete-dialog .word").append("当前选择了<span class='font-red'>"+num+"</span>个项目<br>是否要删除选择的模板？");
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
			layer.close(curLayer);
			this.content.hide();
		}
	});
	}else{
		layer.alert("请选择要删除的模板配置");
	}			
});

//删除按钮操作
$("tbody").on("click",".delete",function(){
	statistics_tpl_idx.length = 0;
	statistics_tpl_idx.push( $(this).parent().parent().find("div input[name='idx']").attr("id"));
	$(".g-delete-dialog").find(".word").html("是否删除?");
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
			layer.close(curLayer);
			this.content.hide();
		}
	});
});
/**
	删除按钮操作
**/
$(".form-btn.remove").on("click",function(){
	//GlobalGLoading();
	var idx = statistics_tpl_idx;
	if(!idx) return;
	var param=new Array();
	for(var i=0;i<idx.length;i++){
		param[i]={
		"statistics_tpl_idx":idx[i],
		};
	}
	
	$.ajax({
		url:basePath+"/statisticsTemplate/deleteStatisticsTemplate",
		type:"POST",
		data:{"req":JSON.stringify(param)},
		success:function(data){
		if(data.state){
			if(delLayer){
				layer.close(delLayer);
			}
			/**
			 * 新增 修改 删除 成功操作之后 弹窗 使用
			 * 
			 * **/
			if(data.message=="ONE"){
				GlobalShowMsg({showText:"删除成功",status:true});
			}else if(data.retMessage){
				showRetMessage(data.retMessage);
			}
			var statType = $("#statType").val();
			if(statType ==-1){
				statType="";
			}
	     	var currentpage = $(".paging-bar li.active").text();
	     	var size= $('#pagesize option:selected').text();
	     	var str=$("#Keyword_lib_idx").val();
			var Page={"page":currentpage,"pageSize":size,"statistics_tpl_type":statType,"statistics_tpl_desc":str};
			//调用页面的查询ajax
			$.select(Page);  
	     }else if(data.retMessage){
	     	showRetMessage(data.retMessage);
	     }
		}
	});
	
});
//编辑模板
var editData = {};
$("tbody").on("click",".edit",function(){
	statistics_tpl_idx.length = 0;
	statistics_tpl_idx.push($(this).parent().parent().find("div input[name='idx']").attr("id"));
	editData["statistics_tpl_idx"] = statistics_tpl_idx[0];
	editData["statistics_type"] = $(this).parent().parent().find("div input[name='idx']").val();//获取模板类型
    var currentpage = $(".paging-bar li.active").text();
    editData["currentpage"] = currentpage;
	$("#mainframe").load("${pageContext.request.contextPath }/statisticsTemplate/editstatistics_tpl_idx",{"req":JSON.stringify(editData)},function(){
		$(".form-dialog-fix .form-dialog").each(function(){
			$(this).attr("date-right",$(this).css("right"));
		});
		
	});
});

//复制模板
$("#copyRow").on("click",function(){
	var num = $("tbody input[name='idx']:checked").length; 
	if(num==1){
		var statisticsidx = $("tbody input[name='idx']:checked").attr("id");
		editData["copyRecord"] = 1;
		editData["statistics_tpl_idx"] = statisticsidx;
		editData["statistics_type"] = $("tbody input[name='idx']:checked").val();//获取模板类型
		var currentpage = $(".paging-bar li.active").text();
		editData["currentpage"] = currentpage;
		$("#mainframe").load("${pageContext.request.contextPath }/statisticsTemplate/editstatistics_tpl_idx",{"req":JSON.stringify(editData)},function(){
			$(".form-dialog-fix .form-dialog").each(function(){
				$(this).attr("date-right",$(this).css("right"));
			});
			
		});
	}else if(num<1){
		layer.alert("请选择要复制的模板配置");
	}else if(num>1){
		layer.alert("一次只能复制一条模板配置");
	}
});
    var startpage = '${page }';
	$(document).ready(function(){
		var size= $('#pagesize option:selected').text();
		if(startpage ==null || startpage.length <=0){
            startpage="1";
        }
		var Page={"page":startpage,"pageSize":size,"statistics_tpl_type":"","statistics_tpl_desc":""};
		$.ajax({
		url:basePath+"/statisticsTemplate/selectStatisticsTemplatePage",
		type:"POST",
		data:{"req":JSON.stringify(Page)},
		async:false,
		success:function(data){
			$("tbody").html("");
			$.each(data.result.rows,function(i,item){
				var type="";
				if(item.statistics_tpl_type=="1"){
					type="查询模板";
				}else if(item.statistics_tpl_type=="2"){
					type="统计模板";
				}
				var value=item.statistics_tpl_value;
				if(value !=null){
					if(value.length >100){
					 value = value.substring(0,100)+"...}";
					}
				}
				$("tbody").append(
				"<tr>"+
            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.statistics_tpl_idx+"' value='"+item.statistics_tpl_type+"'/></div></td>" +
            	"<td class='col2'>" + item.statistics_tpl_id + "</td>" +
            	"<td class='col3'>" + type + "</td>" +
           		"<td class='col4'>" + item.statistics_tpl_desc + "</td>" +
           		"<td class='col6'>" + value + "</td>" +
                "<td><span class='btn-a edit'>编辑</span>"+
                "<span class='btn-a delete'>删除</span></td>" +
                "</tr>");
                });
			
    		$.pagination(data.result);
		
	}
	});
});
	
	
	/**
	查询按钮操作
	**/
	$(".search").on("click",function(){
		var statType = $("#statType").val();
		if(statType ==-1){
			statType="";
		}
		var str=$("#Keyword_lib_idx").val();
		var size= $('#pagesize option:selected').text();
		var Page={"page":"1","pageSize":size,"statistics_tpl_type":statType,"statistics_tpl_desc":str};
		$.select(Page);
	});
	
	//封装查询ajax
	jQuery.select=function(Page){
		$.ajax({
			url:basePath+"/statisticsTemplate/selectStatisticsTemplatePage",
			type:"POST",
			data:{"req":JSON.stringify(Page)},
			success:function(data){
				$("tbody").html("");
				$.each(data.result.rows,function(i,item){
					var type="";
					if(item.statistics_tpl_type=="1"){
						type="查询模板";
					}else if(item.statistics_tpl_type=="2"){
						type="统计模板";
					}
					var value=item.statistics_tpl_value;
					if(value !=null){
						if(value.length >100){
						 value = value.substring(0,100)+"...}";
						}
					}
					$("tbody").append(
					"<tr>"+
	            	"<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+item.statistics_tpl_idx+"' value='"+item.statistics_tpl_type+"'/></div></td>" +
	            	"<td class='col2'>" + item.statistics_tpl_id + "</td>" +
	            	"<td class='col3'>" + type + "</td>" +
	           		"<td class='col4'>" + item.statistics_tpl_desc + "</td>" +
	           		"<td class='col6'>" + value + "</td>" +
	                "<td><span class='btn-a edit'>编辑</span>"+
	                "<span class='btn-a delete'>删除</span></td>" +
	                "</tr>");
	                });
				//一条都没有的情况下，尝试跳转带上一页
				if(data.result.rows.length==0&&Page.page!=1){
					Page.page-=1;
					$.select(Page);
				}
    			$.pagination(data.result);
			}
		});
	};
	//模板筛选
	$(".template-filter .btn-wrap").on("click",function(){
		$(".drop-box").toggle();
	});
	$(".drop-box .left li").on("click",function(){
		var thisIndex = $(this).index();
		$(".drop-box .right ul").eq(thisIndex).addClass("active").siblings("ul").removeClass("active");
	});

	//新增列表
	$(".increase-layer .add").on("click",function(){
		$(this).next().toggle();
	});


	var delLayer = null;
	$(".delete").on("click",function(){
		delLayer = layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :true,
			area:["400px"],
			offset :["195px"],
			content: $('.g-delete-dialog')
		});
	});
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	})

	 //上一页操作
	$("#page").on("click",".prev-page",function(){
		var statType = $("#statType").val();
		if(statType ==-1){
			statType="";
		}
		var currentpage = $(".paging-bar li.active").text();
		var page=Number(currentpage)-1;
		var size= $('#pagesize option:selected').text();
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":page,"pageSize":size,"statistics_tpl_type":statType,"statistics_tpl_desc":str};
		$.select(Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var statType = $("#statType").val();
		if(statType ==-1){
			statType="";
		}
		var currentpage = $(".paging-bar li.active").text();
		page = Number(currentpage) + 1;
		var size= $('#pagesize option:selected').text();
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":page,"pageSize":size,"statistics_tpl_type":statType,"statistics_tpl_desc":str};
		$.select(Page);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var statType = $("#statType").val();
		if(statType ==-1){
			statType="";
		}
		var size= $('#pagesize option:selected').text();
		var page = $(this).html();
		if(page=="...") return;
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":page,"pageSize":size,"statistics_tpl_type":statType,"statistics_tpl_desc":str};
		$.select(Page);
		
	}); 
	/**
		每页显示的条目数切换
	**/
	$("select#pagesize").on("change",function(){
		var statType = $("#statType").val();
		if(statType ==-1){
			statType="";
		}
		var size= $('#pagesize option:selected').text();
		var str=$("#Keyword_lib_idx").val();
		var Page={"page":1,"pageSize":size,"statistics_tpl_type":statType,"statistics_tpl_desc":str};
		$.select(Page);
	});
	/**返回顶部**/
	$(document).on("click","#topcontrol",function(){
		 $('html,body').animate({scrollTop:0},"fast");  
	});
	
	<%-- 回车事件 --%>
	$('#Keyword_lib_idx').keydown(function(e){
		if(e.keyCode==13){
			var statType = $("#statType").val();
			if(statType ==-1){
				statType="";
			}
			var str=$("#Keyword_lib_idx").val();
		    var size= $('#pagesize option:selected').text();
		    var Page={"page":1,"pageSize":size,"statistics_tpl_type":statType,"statistics_tpl_desc":str};
			$.select(Page);
		}
	});
$(document).on("change"," .g-checkbox input",function(){
//		$(this).parents(".g-checkbox").toggleClass("on");
		if($(this).prop("checked")){
			$(this).parents(".g-checkbox").addClass("on");
		}else{
			$(this).parents(".g-checkbox").removeClass("on");
		}
	});

	$(document).on("click",".g-chooseAll",function(){
		/*全选*/
		allInputEach($(document),true);
	});
	$(document).on("click",".g-noChooseAll",function(){
		/*反选*/
		allInputEach($(document),false);
	});

	function allInputEach(container,toChecked){
		var $container = container || $(document);

		if(toChecked){
			$container.find("tbody input[type='checkbox']").each(function(){
				if(!$(this).is(":checked")){
					$("tbody input[type='checkbox']").each(function(){		
						$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
					});
					
				}
			});
		}else{
			$container.find("tbody input[type='checkbox']").each(function(){	
				if(!$(this).is(":checked")){
					$("tbody input[type='checkbox']").each(function(){		
						$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
					});
					
				}else{
					$("tbody input[type='checkbox']").each(function(){
						$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
					});
				}
			});
		}
		/*操作结束后进行计算当前选中的个数*/
		calculateInputNum();
	}

	/*底部计数功能*/
	function calculateInputNum(){
		var checkBoxLen = $(".g-table tbody [type='checkbox']:checked").length;
		$(".total-choosen-num").text(checkBoxLen);
		return  checkBoxLen;
	}

	$(document).on("click",".g-table [type='checkbox']",function(){
		calculateInputNum();
	});


	/*检查页面全部的checkbox元素，是选中状态的，就给父元素添加选中状态更换背景*/
	$("input[type='checkbox']").each(function(){
		if($(this).is(":checked")){
			$(this).prop("checked",true).parents(".g-checkbox").addClass("on");
		}
	});

	/* 勾选全选 */
	$(document).on("change",".col1:eq(0) .g-checkbox input",function(){
		if(!$(this).is(":checked")){
			$("tbody input[type='checkbox']").each(function(){		
				$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
			});
			
		}else{
			$("tbody input[type='checkbox']").each(function(){
				$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
			});
		}
		calculateInputNum();
	});
});
function stemplate(){
    var currentpage = $(".paging-bar li.active").text();
    var endpage = $(".total-page.fr").html().substring(1,2);
    $("#mainframe").load("${pageContext.request.contextPath }/templatemanagement/stemplate?currentpage="+currentpage+"&endpage="+endpage,"",function(){
        $(".form-dialog-fix .form-dialog").each(function(){
            $(this).attr("date-right",$(this).css("right"));
        });

    });
}
function template(){
    var currentpage = $(".paging-bar li.active").text();
    var endpage = $(".total-page.fr").html().substring(1,2);
    $("#mainframe").load("${pageContext.request.contextPath }/templatemanagement/template?currentpage="+currentpage+"&endpage="+endpage,"",function(){
        $(".form-dialog-fix .form-dialog").each(function(){
            $(this).attr("date-right",$(this).css("right"));
        });

    });
}
</script>
</body>
</html>
