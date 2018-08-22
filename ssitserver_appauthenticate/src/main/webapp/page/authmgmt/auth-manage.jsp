<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <meta HTTP-EQUIV="expires" CONTENT="0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>认证管理</title>
</head>
<body>
<div class="equipment-guanli">
        <div class="page-title-bar">
            <span class="title">认证管理<span class="g-help"></span></span>
            <div class="form-wrap fr">
                <input type="text" name="keyword" id="keyword" class="input g-input" placeholder="输入会话ID" />
                <div class="btn search">查询</div>
                <div class="btn increase g-btn-green">新增</div>
            </div>
        </div>
        <div class="main">
            <table class="g-table">
                <thead>
	                <tr>
	                    <th class="col1"><div class="g-checkbox"><input type="checkbox" name=""/></div></th>
	                    <th class="col2">会话ID</th>
	                    <th class="col3">设备号</th>
	                    <th class="col4">读者证号</th>
	                    <th class="col5">读者证密码</th>
	                    <th class="col6">扫码时间</th>
	                    <th class="col7">认证结果</th>
	                    <th class="col8">操作</th>
	                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
	<%@ include file="../include/page_bar.jsf" %>
</div>

<div class="form-dialog">
    <div class="title">新增认证</div>
    <input type="hidden" name="idx"  id="idx" value="" />
    <div class="form-wrap" style="float:left">
        <ul>
            <li>
                <div class="left"><span class="g-mustIn">会话ID</span></div>
                <div class="right">
                    <input type="text" name="random_code"  id="random_code" class="g-input"/>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">设备ID</span></div>
                <div class="right">
                    <input type="text" name="device_id"  id="device_id" class="g-input"/>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">读者证号</span></div>
                <div class="right">
                    <input type="text" name="reader_code"  id="reader_code" class="g-input"/>
                </div>
            </li>
           <li>
                <div class="left">读者证密码</div>
                <div class="right">
                    <input type="text" name="reader_passwd"  id="reader_passwd" class="g-input"/>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">扫码时间</span></div>
                <div class="right">
					<div class="g-inputdate">
						<input type="text" name="scan_date"  id="scan_date" class="g-input"/>
					</div>
				</div>
            </li>
             <li>
                <div class="left"><span class="g-mustIn">认证状态</span></div>
                <div class="right">
                    <div class="g-select">
                        <select name="state" id="state">
                            <option value="000" selected>成功</option>
                            <option value="001" selected>失败</option>
                        </select>
                        <span class="arr-icon"></span>
                    </div>
                </div>
            </li>
        </ul>
    </div>
   <div class="item" style="text-align: center;padding-bottom:30px;padding-top: 20px">
		<input type="submit" name="save" value="保存" class="g-submit g-btn-blue">
		<input type="button" name="return" value="返回" class="g-submit g-btn-gray">
	</div>
</div>
<script type="text/javascript">
	var editlLayer = null;
	var basePath = null;
	$(function() {
		(function mainHeightController() {
			var winH = $(window).height();
			var headerH = $(".g-header").outerHeight();
			var pageTitleBar = $(".page-title-bar").outerHeight();
			var pagingBarH = $(".paging-bar").outerHeight();
			var location = (window.location+'').split('/');  
			basePath = location[0]+'//'+location[2]+'/'+location[3];
			$(".main").css({
				"min-height" : winH - headerH - pagingBarH - pageTitleBar
			});
		})();
		
		$(".datepicker" ).datepicker({
			numberOfMonths:1,//显示几个月  
			showButtonPanel:false,//是否显示按钮面板  
			dateFormat: 'yy-mm-dd',//日期格式  yy-mm-dd 00:00:00
			clearText:"清除",//清除日期的按钮名称  
			closeText:"关闭",//关闭选择框的按钮名称  
			yearSuffix: '', //年的后缀  
			showMonthAfterYear:true,//是否把月放在年的后面  
			//defaultDate:'2016-04-19',//默认日期  
			//minDate:'2011-03-05',//最小日期  
			//maxDate:'2011-03-20',//最大日期  
			monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
			dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
			dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
			dayNamesMin: ['日','一','二','三','四','五','六'],  
			
		});
		
		$("tbody").on("click",".edit",function(obj){
			$(".error-msg").text("");
			var type_idx=$(this).parent().parent().find("div input[name='idx']").attr("id");
        	layerOpen({
                "title":"编辑信息",
                "btnText":"保存",
                "btnColorClass":"g-btn-blue",
                "status" : true,
                "idx":type_idx
            });
        });
		
		/**查询按钮操作**/
		$(".search").on("click",function(){
			GlobalGLoading();
			
			var size= $('#showSize option:selected').text();
			var random_code = $("#keyword").val();
			var param={
				"random_code":random_code,"page":1,"pageSize":size
			};
			$.select(param);
		});
		
		//上一页操作
		$("#page").on("click", ".prev-page", function() {
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) - 1;
			var size = $('#showSize option:selected').html();
			var random_code = $("#keyword").val();
			var param={
				"random_code":random_code,"page":page,"pageSize":size
			};
			$.select(param);
		});
		
		//下一页操作
		$("#page").on("click", ".next-page", function() {
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) + 1;
			var size = $('#showSize option:selected').html();
			var random_code = $("#keyword").val();
			var param={
				"random_code":random_code,"page":page,"pageSize":size
			};
			$.select(param);
		});

		$("#page").on("click", "li", function() {
			if ($(this).hasClass("active"))
				return;
			var page = $(this).html();
			if (page == "...")
				return;

			var size = $('#showSize option:selected').text();
			var random_code = $("#keyword").val();
			var param={
				"random_code":random_code,"page":page,"pageSize":size
			};
			$.select(param);

		});

		/**
			每页显示的条目数切换
		 **/
		$("select#showSize").on("change", function() {
			GlobalGLoading();
			var page = $("#page").find("li.active").html();//当前页
			var size = $('#showSize option:selected').html();
			var random_code = $("#keyword").val();
			var param={
				"random_code":random_code,"page":page,"pageSize":size
			};
			$.select(param);
		});

		$(".increase").on("click", function() {
			$(".error-msg").text("");
			layerOpen({
				"title" : "新增预借",
				"btnText" : "新增",
				"btnColorClass" : "g-btn-blue",
				"status" : false
			});
		});

		$(".form-btn.cancel").on("click", function() {
			if (delLayer) {
				layer.close(delLayer);
			}
		});
		
		/**新增、修改弹出框保存操作**/
		$("input[name='save']").on("click", function() {
			if(!isCanSave){
				return;
			}
			var random_code = $("input[name='random_code']").val();
			var device_id = $("input[name='device_id']").val();
			var reader_code = $("input[name='reader_code']").val();
			var state = $("select[name='state']").val();
			var reader_passwd = $("input[name='reader_passwd']").val();
			var scan_date = $("input[name='scan_date']").val();
			var idx = $("input[name='idx']").val();
			if(!random_code){
				layer.tips('ID不能为空', '#random_code', {tips: [2, '#ff6666']});
				return;
			}else if(!reader_code){
				layer.tips('读者证号不能为空', '#reader_code', {tips: [2, '#ff6666']});
				return;
			}else if(!state){
				layer.tips('请选择认证状态', '#state', {tips: [2, '#ff6666']});
				return;
			}else if(!device_id){
				layer.tips('设备ID不能为空', '#device_id', {tips: [2, '#ff6666']});
				return;
			}else if(!scan_date){
				layer.tips('扫码时间不能为空', '#scan_date', {tips: [2, '#ff6666']});
				return;
			}
			
			/* if(!isDate(scan_date)){
				layer.tips('请填写正确的端口', '#container_port', {tips: [2, '#ff6666']});
				return;
			} */
			
			if (editlLayer) {
				layer.close(editlLayer);
			}
			var baseURL = basePath + "/auth/add";
			if (isEdit) {//编辑
				baseURL = basePath + "/auth/update";
				if(!idx){
					return;
				}
				var param = {
					"random_code" : random_code,
					"device_id" : device_id,
					"reader_code" : reader_code,
					"reader_passwd" : reader_passwd,
					"scan_date" : scan_date,
					"state" : state
				};
			} else {//新增
				var param = {
					"random_code" : random_code,
					"device_id" : device_id,
					"reader_code" : reader_code,
					"reader_passwd" : reader_passwd,
					"scan_date" : scan_date,
					"state" : state
				};
			}
			var size = $('#showSize option:selected').text();
			$.ajax({
				url : baseURL,
				type : "POST",
				data : {
					"req" : JSON.stringify(param)
				},
				success : function(data) {
					if (data.state) {
						GlobalShowMsg({showText:"操作成功",status:true});
						var Page = {
							"page" : "1",
							"pageSize" : size,
						};
						$.select(Page);
						GlobalGLoading();
					}
				}
			});
		});

		/**取消新增、修改弹出框**/
		$("input[name='return']").on("click", function() {
			if (editlLayer) {
				layer.close(editlLayer);
			}

		});
		
	});

	var isEdit = false;
	var isCanSave = true;
	/**封装的弹出操作**/
	function layerOpen(options) {
		var defaults = {
			title : "",
			btnText : "新增"
		}
		options = $.extend(defaults, options);
		var $submit = $(".form-dialog .submit");
		$(".form-dialog .title").text(options.title);
		if (options.status) {
			isEdit = true;
			var param = {
				"random_code" : options.idx,
			};
			$.ajax({
				url : basePath + "/auth/queryAuthByRandomCode",
				type : "POST",
				data : {
					"req" : JSON.stringify(param)
				},
				success : function(data) {
					if (data.state) {
						$("input[name='idx']").val(data.result.random_code);
						$("input[name='random_code']").val(data.result.random_code);
						$("input[name='device_id']").val(data.result.device_id);
						$("input[name='reader_code']").val(data.result.reader_code);
						$("input[name='reader_passwd']").val(data.result.reader_passwd);
						$("input[name='scan_date']").val(data.result.scan_date);
						$("select[name='state']").val(data.result.state);
						
						$("input[name='random_code']").attr("readonly",true);
					}
				}
			});
		} else {
			isEdit = false;
			$("input[name='random_code']").val("");
			$("input[name='device_id']").val("");
			$("input[name='reader_code']").val("");
			$("input[name='reader_passwd']").val("");
			$("input[name='scan_date']").val("");
			$("select[name='state']").val("0");
			$("input[name='random_code']").attr("readonly",false);
		}
		$submit.val(options.btnText);
		$submit.removeClass("g-btn-green").removeClass(".g-btn-blue");
		$submit.addClass(options.btnColorClass);
		editlLayer = layer.open({
			type : 1,
			shade : false,
			title : false, //不显示标题
			scrollbar : false,
			closeBtn : 1,
			shade : 0.5,
			shadeClose : true,
			area : [ "750px" ],
			offset : [ "150px" ],
			content : $('.form-dialog')
		});
	};
	
	$("#random_code").blur(function(){ 
		if(isEdit){
			return;
		}
		var random_code = $("input[name='random_code']").val();
		if(!random_code){
			return;
		}
		var param = {
				"random_code" : random_code
			};
		$.ajax({
			url : basePath+"/auth/queryAuthByRandomCode",
			type : "POST",
			data : {
				"req" : JSON.stringify(param)
			},
			success : function(data) {
				if (data.state) {
					layer.tips('ID已存在', '#random_code', {tips: [2, '#ff6666']});
					$("#random_code")[0].focus();
				}
			}
		});
	}); 
	

	/**封装的查询方法**/
	jQuery.select = function(param) {
		$.ajax({
			url : basePath + "/auth/queryByParam",
			type : "POST",
			data : {
				"req" : JSON.stringify(param)
			},
			success : function(data) {
				$("tbody").html("");
				if (data.state) {
					if (data.result.rows&& data.result.rows.length == 0) {
						var index = param.page;
						if (index > 1) {
							param.page = index - 1;
							$.select(param);
						}
					} else {
						$.each(data.result.rows,function(i, item) {
							var state='';
							var random_code=item.random_code==null?"":item.random_code;
							var device_id=item.device_id==null?"":item.device_id;
							var reader_code=item.reader_code==null?"":item.reader_code;
							var reader_passwd=item.reader_passwd==null?"":item.reader_passwd;
							var scan_date=item.scan_date==null?"":item.scan_date;
							if(item.state=='000'){
								state='成功';
							}else{
								state='失败';
							}
							$("tbody").append(
							"<tr>"
							+ "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+random_code+"' /></div></td>"
							+ "<td class='col2'>"
							+ random_code
							+ "</td>"
							+ "<td class='col3'>"
							+ device_id
							+ "</td>"
							+ "<td class='col4'>"
							+ reader_code
							+ "</td>"
							+ "<td class='col5'>"
							+ reader_passwd
							+ "</td>"
							+ "<td class='col6'>"
							+ scan_date
							+ "</td>"
							+ "<td class='col7'>"
							+ state
							+ "</td>"
							+ "<td class='col8'>"
							+ "<span class='btn-a edit'>编辑</span>"
							+ "</td>"
							+ "</tr>");
						});
					}
				}
	
				$.pagination(data.result);
			}
		});
	};
	
	$(function() {
		var Page = {
			"page" : "1",
			"pageSize" : $('#showSize option:selected').text(),
		};
		$.select(Page);
	});
	
</script>
</body>
</html>
