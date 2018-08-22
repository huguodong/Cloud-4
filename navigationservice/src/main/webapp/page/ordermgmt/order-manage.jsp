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
    <title>预约管理</title>
</head>
<body>
<div class="equipment-guanli">
        <div class="page-title-bar">
            <span class="title">预约管理<span class="g-help"></span></span>
            <div class="form-wrap fr">
                <input type="text" name="keyword" id="keyword" class="input g-input" placeholder="输入图书条码号" />
                <div class="btn search">查询</div>
                <div class="btn increase g-btn-green">新增</div>
            </div>
        </div>
        <div class="main">
            <table class="g-table">
                <thead>
	                <tr>
	                    <th class="col1"><div class="g-checkbox"><input type="checkbox" name=""/></div></th>
	                    <th class="col2">图书条码</th>
	                    <th class="col3">读者证号</th>
	                    <th class="col4">预借状态</th>
	                    <th class="col5">图书包裹号</th>
	                    <th class="col6">预借逾期时间</th>
	                    <th class="col7">更新时间</th>
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
    <div class="title">新增预约</div>
    <div class="form-wrap" style="float:left">
   	 <input type="hidden" name="idx"  id="idx" value="" />
        <ul>
            <li>
                <div class="left"><span class="g-mustIn">图书条码</span></div>
                <div class="right">
                    <input type="text" name="book_barcode"  id="book_barcode" class="g-input"/>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">读者证号</span></div>
                <div class="right">
                    <input type="text" name="readerid"  id="readerid" class="g-input"/>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">预借状态</span></div>
                <div class="right">
                    <div class="g-select">
                        <select name="state" id="state">
                            <option value="0" selected>已分派</option>
                            <option value="1" selected>已上架</option>
                            <option value="2" selected>已下架</option>
                            <option value="3" selected>已借出</option>
                        </select>
                        <span class="arr-icon"></span>
                    </div>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">图书包裹号</span></div>
                <div class="right">
                    <input type="text" name="packetid" id="packetid" class="g-input" placeholder="请输入"  />
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">预借逾期时间</span></div>
                <div class="right">
					<div class="g-inputdate">
						<input type="text" id="overduedate" name="overduedate" placeholder="输入日期"  readonly="readonly" class="g-input datepicker"/>
						<span class="icon"></span>
					</div>
				</div>
            </li>
            <li>
            	<div id ="dispalyLocation" style="display: none">
                    <div class="left"><span>图书导航</span></div>
                    <div class="right" id="booklocation"></div>
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
			var book_barcode = $("#keyword").val();
			var param={
				"book_barcode":book_barcode,"page":1,"pageSize":size
			};
			$.select(param);
		});
		
		//上一页操作
		$("#page").on("click", ".prev-page", function() {
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) - 1;
			var size = $('#showSize option:selected').html();
			var book_barcode = $("#keyword").val();
			var param={
				"book_barcode":book_barcode,"page":page,"pageSize":size
			};
			$.select(param);
		});
		
		//下一页操作
		$("#page").on("click", ".next-page", function() {
			var currentpage = $(".paging-bar li.active").html();
			var page = Number(currentpage) + 1;
			var size = $('#showSize option:selected').html();
			var book_barcode = $("#keyword").val();
			var param={
				"book_barcode":book_barcode,"page":page,"pageSize":size
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
			var book_barcode = $("#keyword").val();
			var param={
				"book_barcode":book_barcode,"page":page,"pageSize":size
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
			var book_barcode = $("#keyword").val();
			var param={
				"book_barcode":book_barcode,"page":page,"pageSize":size
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
			var book_barcode = $("input[name='book_barcode']").val();
			var readerid = $("input[name='readerid']").val();
			var state = $("select[name='state']").val();
			var packetid = $("input[name='packetid']").val();
			var overduedate = $("input[name='overduedate']").val();
			var idx = $("input[name='idx']").val();
			if(!book_barcode){
				layer.tips('图书条码号不能为空', '#book_barcode', {tips: [2, '#ff6666']});
				return;
			}else if(!readerid){
				layer.tips('读者证号不能为空', '#readerid', {tips: [2, '#ff6666']});
				return;
			}else if(!state){
				layer.tips('请选择预借状态', '#state', {tips: [2, '#ff6666']});
				return;
			}else if(!packetid){
				layer.tips('图书包裹号不能为空', '#packetid', {tips: [2, '#ff6666']});
				return;
			}else if(!overduedate){
				layer.tips('预借逾期时间不能为空', '#overduedate', {tips: [2, '#ff6666']});
				return;
			}
			
			/* if(!isDate(overduedate)){
				layer.tips('请填写正确的端口', '#container_port', {tips: [2, '#ff6666']});
				return;
			} */
			
			if (editlLayer) {
				layer.close(editlLayer);
			}
			var baseURL = basePath + "/order/add";
			if (isEdit) {//编辑
				baseURL = basePath + "/order/update";
				if(!idx){
					return;
				}
				var param = {
					"idx":idx,
					"book_barcode" : book_barcode,
					"readerid" : readerid,
					"state" : state,
					"packetid" : packetid,
					"overduedate" : overduedate,
					"updatetime" : formatDate(new Date(),"yyyy-MM-dd")
				};
			} else {//新增
				var param = {
					"book_barcode" : book_barcode,
					"readerid" : readerid,
					"state" : state,
					"packetid" : packetid,
					"overduedate" : overduedate,
					"updatetime" : formatDate(new Date(),"yyyy-MM-dd")
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
				"idx" : options.idx,
			};
			$.ajax({
				url : basePath + "/order/queryById",
				type : "POST",
				data : {
					"req" : JSON.stringify(param)
				},
				success : function(data) {
					if (data.state) {
						$("input[name='idx']").val(data.result.idx);
						$("input[name='book_barcode']").val(data.result.book_barcode);
						$("input[name='readerid']").val(data.result.readerid);
						$("select[name='state']").val(data.result.state);
						$("input[name='packetid']").val(data.result.packetid);
						$("input[name='overduedate']").val(data.result.overduedate);
						$("input[name='updatetime']").val(data.result.updatetime);
						dispaly3dInfo(data.result.book_barcode);
					}
				}
			});
		} else {
			isEdit = false;
			$("input[name='idx']").val("");
			$("input[name='book_barcode']").val("");
			$("input[name='readerid']").val("");
			$("select[name='state']").val("0");
			$("input[name='packetid']").val("");
			$("input[name='overduedate']").val("");
			$("input[name='updatetime']").val("");
			$("#dispalyLocation").hide();
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
	
	$("#book_barcode").blur(function(){ 
		var book_barcode = $("input[name='book_barcode']").val();
		if(!book_barcode){
			return;
		}
		var param = {
				"book_barcode" : book_barcode
			};
		$.ajax({
			url : basePath+"/bookitem/queryBookitemByCode",
			type : "POST",
			data : {
				"req" : JSON.stringify(param)
			},
			success : function(data) {
				if (data.state) {
					dispaly3dInfo(book_barcode);
				}else{
					$("#dispalyLocation").hide();
					layer.tips('图书条码号不存在', '#book_barcode', {tips: [2, '#ff6666']});
					  $("#book_barcode")[0].focus();
					  isCanSave = false;
				}
			}
		});
	}); 
	
	
	function dispaly3dInfo(book_barcode){
		$("#dispalyLocation").show();
		var param = {
				"book_barcode" : book_barcode
			};
		$.ajax({
			url : basePath+"/navigation/getLocationData",
			type : "POST",
			data : {
				"req" : JSON.stringify(param)
			},
			success : function(data) {
				var str ="暂无导航信息";
				if (data.state) {
					var ety = data.result;
					var arr = ety.locations;
					if(arr.length=5){
						str = "<a href='"+basePath+"/navigation/"+book_barcode+"' style='font-weight:bold;color:#000000' target='_blank'>"
						+"<span style='color:red'>"+arr[0]+"</span>&nbsp;楼&nbsp;"+"<span style='color:red'>"+arr[1]+"</span>&nbsp;区&nbsp;"+"<span style='color:red'>"+arr[2]+"</span>&nbsp;列&nbsp;"
						+"<span style='color:red'>"+arr[3]+"</span>&nbsp;列名&nbsp;"+"<span style='color:red'>"+arr[4]+"</span>&nbsp;节&nbsp;"+"<span style='color:red'>"+ety.shelf_layer+"</span>&nbsp;层&nbsp;"
						+"</a>";
					}
				}
				$("#booklocation").empty(); 
				$("#booklocation").append(str); 
			}
		});
	}

	/**封装的查询方法**/
	jQuery.select = function(param) {
		$.ajax({
			url : basePath + "/order/queryByParam",
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
							var bookState='';
							var idx=item.idx==null?"":item.idx;
							var book_barcode=item.book_barcode==null?"":item.book_barcode;
							var readerid=item.readerid==null?"":item.readerid;
							var packetid=item.packetid==null?"":item.packetid;
							var overduedate=item.overduedate==null?"":item.overduedate;
							var updatetime=item.updatetime==null?"":item.updatetime;
							if(item.state=='0'){
								bookState='已分派';
							}else if(item.state=='1'){
								bookState='已上架';
							}else if(item.state=='2'){
								bookState='已下架';
							}else if(item.state=='3'){
								bookState='已借出';
							}
							$("tbody").append(
							"<tr>"
							+ "<td class='col1'><div class='g-checkbox'><input type='checkbox' name='idx' id='"+idx+"' /></div></td>"
							+ "<td class='col2'>"
							+ book_barcode
							+ "</td>"
							+ "<td class='col3'>"
							+ readerid
							+ "</td>"
							+ "<td class='col4'>"
							+ bookState
							+ "</td>"
							+ "<td class='col5'>"
							+ packetid
							+ "</td>"
							+ "<td class='col6'>"
							+ overduedate
							+ "</td>"
							+ "<td class='col7'>"
							+ updatetime
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
	
	function isPort(str){  
	    var parten=/^(\d)+$/g;  
	    if(parten.test(str)&&parseInt(str)<=65535&&parseInt(str)>=0){  
	        return true;  
	     }else{  
	        return false;  
	     }   
	}
	
	function formatDate(time,format){
		  if(!time) return "";
		  var paddNum = function(num){
		    num += "";
		    return num.replace(/^(\d)$/,"0$1");
		  }
		  var date=new Date(time);
		  /* 指定格式字符 */
		  var cfg = {
		     yyyy : date.getFullYear() //年 : 4位
		    ,yy : date.getFullYear().toString().substring(2)//年 : 2位
		    ,M  : date.getMonth() + 1  //月 : 如果1位的时候不补0
		    ,MM : paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
		    ,d  : date.getDate()   //日 : 如果1位的时候不补0
		    ,dd : paddNum(date.getDate())//日 : 如果1位的时候补0
		    ,hh : date.getHours()  //时
		    ,mm : date.getMinutes() //分
		    ,ss : date.getSeconds() //秒
		  }
		  format || (format = "yyyy-MM-dd hh:mm:ss");
		  return format.replace(/([a-z])(\1)*/ig,function(m){return cfg[m];});
		} 
</script>
</body>
</html>
