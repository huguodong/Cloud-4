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
	</style>
</head>
<body>
<div class="equipment-leixing">
	
	<div class="page-title-bar">
		<span class="title">设备类型<span class="g-help"></span></span>
		<div class="form-wrap fr">
			<div class="g-select">
				<select id="select">
					<option value="-1" selected>选择类型</option>
					
				</select>
				<span class="arr-icon"></span>
			</div>
			<div class="btn search">查询</div>
			<div class="btn increase g-btn-green">新增</div>
			<div class="btn delete">删除</div>
		</div>
	</div>
	<div class="main">
		<table class="g-table">
			<thead>
				<tr>
					<th class="col1"><div class="g-checkbox" ><input type="checkbox" name="" id="" /></div></th>
					<th class="col2">DB_NAME</th>
					<th class="col3">HOST</th>
					<th class="col6">PORT</th>
					<th class="col7">操作</th>
				</tr>
			</thead>
			<tbody>
			
						
			</tbody>
		</table>
	</div>
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
		当前选择了<span class="font-red"></span>个项目<br>
		是否要删除选择的设备类型？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<div class="form-dialog" id="add">
	<div class="title">新增设备类型</div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">名称</span></div>
				<div class="right">
					<input type="text" name="" id="device_type" class="g-input" placeholder="请输入"  />
					<span class="error-msg">设备类型名称不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">描述</span></div>
				<div class="right">
					<input type="text" name="" id="device_type_desc" class="g-input" placeholder="请输入"  />
					<span class="error-msg">设备类型描述不能为空</span>
				</div>
			</li>
			<li class="logic-obj-li"></li>
			<li>
				<div class="left">备注</div>
				<div class="right">
					<textarea class="g-textarea" name="" id="device_type_mark"></textarea>
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

<div class="form-dialog" id="modify">
	<div class="title">编辑设备类型</div>
	<div class="form-wrap">
		<ul>
			<li>
				<div class="left"><span class="g-mustIn">DB_NAME</span></div>
				<div class="right">
					<input type="text" name="" id="db_name" class="g-input" placeholder="请输入"  />
					<span class="error-msg">设备类型名称不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">HOST</span></div>
				<div class="right">
					<input type="text" name="" id="host" class="g-input" placeholder="请输入"  />
					<span class="error-msg">ip不能为空</span>
				</div>
			</li>
			<li>
				<div class="left"><span class="g-mustIn">PORT</span></div>
				<div class="right">
					<input type="text" name="" id="port" class="g-input" placeholder="请输入"  />
					<span class="error-msg">端口不能为空</span>
				</div>
			</li>
			<li>
				<div class="left">&nbsp;</div>
				<div class="right">
					<input id="ineditBtn" type="submit" value="保存" class="submit g-btn-blue" />
				</div>
			</li>
		</ul>
	</div>
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
	/**
		host" 127.0.0.1" 
		operDatabase "M42_TESTSYNC_02"
	    port 27017
	    pwd  "pwd"
		user "superuser"
	**/
	function drawRow(result){
		if(!result) return;
		var html="";
		for(var i=0;i<result.length;i++){
			html+="<tr>";
			var res=result[i];
			var dbName=res.dbName;
			var dbInfo=res[dbName];
			var host=dbInfo.host;
			var port=dbInfo.port;
			html+="<td class='col1'><div class='g-checkbox'><input type='checkbox'/></div></td>";
			html+="<td class='col2 dbName'>"+dbName+"</td>";
			html+="<td class='col3 host'>"+host+"</td>";
			html+="<td class='col4 port'>"+port+"</td>";
			html+="<td class='col7'><span class='btn-a edit'>编辑</span></td>";
			html+="</tr>";
		}
		$("tbody").html(html);
	}
	var updLayer=null;
	$("tbody").on("click",".edit",function(){
		$("#modify").find("input").val("");
		$("#modify").find("textarea").val("");
		var dbName=$(this).parent().sublings().find(".dbName");
		var host=$(this).parent().sublings().find(".host");
		var port=$(this).parent().sublings().find(".port");
		
		updLayer=layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :1,
			shade:0.5,
			shadeClose :false,
			area:["800px"],
			offset :["130px"],
			content: $("#modify"), //捕获的元素
			cancel: function(index){
				layer.close(updLayer);
				this.content.hide();
			}
		});
	});
	
	(function queryMongoInstance(){
		$.ajax({
			url:"${pageContext.request.contextPath}/devconf/queryMongoInstances",
			data:{},
			success:function(data){
				console.log(data);
				if(data && data.state){
					var result=data.result;
					drawRow(result);
				}
			}
		});
	})();
});	
</script>
</body>
</html>


