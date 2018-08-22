<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("utf-8");
String deviceCode=request.getParameter("deviceCode");
String device_idx=request.getParameter("device_idx");
String device_id=request.getParameter("device_id");
String device_ip=request.getParameter("device_ip");

String old_library_idx=request.getParameter("old_library_idx");
String old_library_id=request.getParameter("old_library_id");
String old_library_name=request.getParameter("old_library_name");
/* old_library_name=old_library_name==null?"":new String(request.getParameter("old_library_name").getBytes("iso-8859-1"), "utf-8"); */
%>

<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>设备迁移</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/style.css" />
<script type="text/javascript" src="<%=basePath %>static/js/jquery.min.js" ></script>
<script type="text/javascript" src="<%=basePath %>static/js/common.js" ></script>
<script type="text/javascript" src="<%=basePath %>static/js/underscore/underscore-min.js" ></script>

<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/ie.css" />
<![endif]-->
<!--[if lt IE 9]>
	<script type="text/javascript" src="<%=basePath %>static/js/html5.js" ></script>
	<script type="text/javascript" src="<%=basePath %>static/js/ie.js" ></script>
<![endif]-->
<style type="text/css">
#money label{
	display: inline-block;
	width: 40px;
}

.form-wrap li.error div.g-select {
    background-color: #ffeeee;
    border-color: #ff2323;
}

div.muban span.error-msg{
	display: none;
}

div.muban div.item.error div.g-select{
	background-color: #ffeeee;
    border-color: #ff2323;
}

div.muban div.item.error span.error-msg{
	margin-left: 10px;
	display:inline;
	color: #ff2323;
}

.register-back {
    background-color: #2cbe5f;
    border-radius: 2px;
    color: #fff;
    cursor: pointer;
    height: 28px;
    line-height: 28px;
    margin-top: 16px;
    text-align: center;
    width: 80px;
}

.equipment-zhuce .form-wrap.acs {
	padding-top:10px;
}

.equipment-zhuce .form-wrap.acs .item {
	margin-bottom:10px;
}

.form-wrap .delete-li {
	margin-left: 5px;
}

.equipment-zhuce .field-title{
	margin-top:30px;
}
.equipment-zhuce .form-wrap.acs label {
	display: inline-block;
	margin-right:20px;
	margin-left:3px;
	width: 30px;
}

.g-loading.save {
	background-position: 80% center;
}

.readonly{
	color: gray;
	background-color: #E0E0E0;
}

</style>
</head>
<body>
<div class="g-loading"></div>
<div class="g-header">
	<a href="#" class="logo"></a>
	<a class="exit" href="<%=basePath %>device/connect?deviceCode=<%=deviceCode%>&isSwitch=false"><span class="icon"></span>退出</a>
	<div class="status"><span class="icon"></span>系统管理员</div>
</div>
<div class="page-title-bar">
	<a href="#" class="back-btn"></a>
	<span class="title">设备迁移<a href="#" class="g-help"></a></span>
</div>

<div class="form-dialog">
    <div class="title">设备迁移</div>
    <div class="form-wrap">
   		<input type="hidden" name="device_idx"  id="device_idx" value="<%=device_idx %>" />
        <ul>
            <li>
                <div class="left">迁出馆编号</div>
                <div class="right">
                	<input type="hidden" name="old_library_idx"  id="old_library_idx" value="<%=old_library_idx %>" />
                    <input type="text" name="old_library_id"  id="old_library_id" class="g-input readonly" readonly value="<%=old_library_id%>"/>
                </div>
            </li>
            <li>
                <div class="left">迁出馆名称</div>
                <div class="right">
                    <input type="text" name="old_library_name"  id="old_library_name" class="g-input readonly" readonly value="<%=old_library_name%>"/>
                </div>
            </li>
            <li>
                <div class="left"><span class="g-mustIn">迁入馆编号</span></div>
                <div class="right">
                	<input type="hidden" name="new_library_idx"  id="new_library_idx" value="" />
                    <input type="text" name="new_library_id"  id="new_library_id" class="g-input" value=""/>
                    <span class="error-msg"></span>
                </div>
            </li>
            <li>
                <div class="left">迁入馆名称</div>
                <div class="right">
                    <input type="text" name="new_library_name"  id="new_library_name" class="g-input readonly" readonly value=""/>
                </div>
            </li>
            <li>
                <div class="left">迁出设备编号</div>
                <div class="right">
                    <input type="text" name="device_id"  id="device_id" class="g-input readonly" readonly value="<%=device_id %>"/>
                     <span class="error-msg"></span>
                </div>
            </li>
            <li>
                <div class="left">迁出设备IP</div>
                <div class="right">
                    <input type="text" name="device_ip"  id="device_ip" class="g-input" value="<%=device_ip %>"/>
                     <span class="error-msg"></span>
                </div>
            </li>
        </ul>
    </div>
    <div class="register-result">
		<div class="status-word">恭喜你，设备已经迁移成功</div>
		<div class="tips-word">
			你的设备在完成迁移后，后台系统需下载一<br>些数据更新，请稍等片刻。即将自动跳转...
		</div>
		<div ><input type="button"  class="register-back g-btn-blue" value="返回">
		</div>
	</div>
   <div class="item">
		<input type="submit" id="increaseBtn" value="保存" class="g-submit g-btn-blue">
		<input type="button" id="cancelBtn" value="返回" class="g-submit g-btn-gray">
	</div>
</div>

<link rel="stylesheet" href="<%=basePath %>static/css/jqueryui.css">
<link rel="stylesheet" href="<%=basePath %>static/css/theme.css">
<script src="<%=basePath %>static/js/jqueryui.js"></script>
<script src="<%=basePath %>static/js/jqueryuitimepicker.js"></script>
<script src="<%=basePath %>static/plugins/layer/layer.js"></script>
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

	var editlLayer=null;
	
	layer.confirm('设备当前属于出厂设备，是否需要切换到您所在图书馆？', {
	  	btn: ['是','否'] //按钮
	}, function(){
		layer.closeAll('dialog');//关闭弹框
		editlLayer = layer.open({
            type: 1,
            shade: false,
            title: false, //不显示标题
            scrollbar :false,
            closeBtn :1,
            shade:0.5,
            shadeClose :false,//除了点击关闭、取消按
            area:["600px"],
            offset :["160px"],
            content: $('.form-dialog')
        });
		$("#new_library_idx").val("");
		$("#new_library_id").val("");
		$("#new_library_name").val("");
		$("#device_id").val("<%=device_id %>");
		$("#device_ip").val("<%=device_ip %>");
	}, function(){
		//跳转url
		self.location='${pageContext.request.contextPath}/device/connect?deviceCode=<%=deviceCode%>&isNotSwitch=true'; 
	});
	
	//取消按钮操作
	$("#cancelBtn").on("click",function(){
		window.location.reload();
	});
	
	//保存按钮操作
	$("#increaseBtn").on("click",function(){
		var device_idx = $("#device_idx").val();
		var device_id = $("#device_id").val();
		var device_ip = $("#device_ip").val();
		var old_library_idx = $("#old_library_idx").val();
		var old_library_id=$("#old_library_id").val();
		//var old_library_name=$("#old_library_name").val();
		var new_library_idx=$("#new_library_idx").val();
		var new_library_id=$("#new_library_id").val();
		var new_library_name=$("#new_library_name").val();
		
		if(!old_library_id||!old_library_idx){
			$("#old_library_id").parent().parent().addClass("error");
			return;
		}else if(!new_library_id){
			$("#new_library_id").parent().parent().addClass("error");
			$("#new_library_id").parent().find(".error-msg").text("设备迁入馆编号不能为空");
			return;
		}else if(old_library_id==new_library_id){
			$("#new_library_id").parent().parent().addClass("error");
			$("#new_library_id").parent().find(".error-msg").text("不能迁移到同一个图书馆");
			return;
		}else if(!new_library_idx){
			$("#new_library_id").parent().parent().addClass("error");
			$("#new_library_id").parent().find(".error-msg").text("请填写正确的设备迁入馆编号");
			return;
		}else if(!device_ip){
			$("#device_ip").parent().parent().addClass("error");
			$("#device_ip").parent().find(".error-msg").text("迁出设备IP不能为空");
			return;
		}else if(!isIP(device_ip)){
			$("#device_ip").parent().parent().addClass("error");
			$("#device_ip").parent().find(".error-msg").text("请填写正确的设备IP");
			return;
		}
		
		var transferList = [];
		var obj = {
			"device_idx":device_idx,
			"device_id":device_id,
			"ip":device_ip,
		};
		transferList.push(obj);
		
		var param={
			"oldLibidx":old_library_idx,
			"oldLibid":old_library_id,
			//"old_library_name":old_library_name,
			"newLibidx":new_library_idx,
			"newLibid":new_library_id,
			//"new_library_name":new_library_name,
			"count":1,
			"transferList":transferList,
		};
		$.ajax({
			 url:"${pageContext.request.contextPath}/device/transferToNewLib",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
			     if(data.state){
			    	 url = "${pageContext.request.contextPath}/device/connect?deviceCode=<%=deviceCode%>";
					<%-- 访问中间件 --%>
					systemInit(url,new_library_id,device_id);
	     			//跳转url
					//self.location="${pageContext.request.contextPath}/device/connect?deviceCode=<%=deviceCode%>";
					/* if(editlLayer){
						//关闭弹框
			     		layer.close(editlLayer);
			     	} */
			     }else if(data.message){
			    	 //layer.msg('操作失败');
			    	 layer.alert(data.message);
			     }else{
			    	 layer.alert('操作失败');
			     }
		 	 }
		});
	});
	
	function systemInit(dataUrl,libraryId,deviceId){
		var param = {
				req:{
					servicetype:"sys",
					target:"sys_init",
					operation:"sys_init_reset_id",
					data:"{\"DEVICEID\":\""+deviceId+"\",\"LIBRARYID\":\""+libraryId+"\"}"
				}
		};
		var errMsg = "迁移成功，系统初始化时出现问题！";
		$.ajax({
			url:"http://localhost:38090/webservice/sys.asmx/ServicesHandle",
			type:"POST",
			timeout:120000,//120秒超时时间
			contentType: "application/json; charset=utf-8",
			data:JSON.stringify(param)
		}).done(function(data,status){
			console.log("done:",JSON.stringify(data));
			if(!_.isEmpty(data) && !_.isEmpty(data.d) && data.d.result!=null){
				if(data.d.result=="0" || data.d.result==0){
					registerResult(true,"正在跳转，请稍后...");	
					setTimeout(function(){gotoUrl(dataUrl)},2000);
				}else{
// 					var err = jsonToObj(data.d.data);
// 					var message = "";
// 					var description = "";
// 					if(err!=""){
// 						message = err.Message;
// 						description = err.Description;
// 					}
					registerResult(false,"请联系维护人员！",errMsg);	
				}
			}else{
				registerResult(false,"请联系维护人员！",errMsg);			
			}
		}).fail(function(data,status,code){
			console.log("fail:",JSON.stringify(data));
			if(data!=null && data.statusText!="timeout"){
				registerResult(false,"设备连接初始化服务器失败，错误码："+data.status+"，请联系维护人员！",errMsg);	
			}
		}).always(function(data,status){
			console.log("always:",JSON.stringify(data));
			if(status=="timeout"){
				console.log("超时");
				registerResult(false,"设备初始化超时，请联系维护人员！",errMsg);					
			}
		});
	}
	
	<%-- 注册之后提示的返回按钮 --%>
	$(document).on("click",".register-back",function(){
		$(".register-result").fadeOut();
	});

	<%-- 注册结果页面效果 --%>
	function registerResult(status,msg,errMsg){
		$("html, body").animate({scrollTop:0},"fast");
		if(!status){
			$(".register-result").addClass("fail");
			if(errMsg!=null){
				$(".register-result").find(".status-word").html(errMsg);
			}else{
				$(".register-result").find(".status-word").html("抱歉，你的设备迁移失败");
			}
			if(msg!=null){
				$(".register-result").find(".tips-word").html(msg);
			}else{
				$(".register-result").find(".tips-word").html("你可以请重新尝试迁移，或者联系系统管理员解决问题。");
			}
			$(".register-back").show();
		}else{
			$(".register-back").hide();
			$(".register-result").removeClass("fail");
			if(msg!=null){
				$(".register-result").find(".status-word").html("恭喜你，初始化成功");
				$(".register-result").find(".tips-word").html(msg);				
			}else{
				$(".register-result").find(".status-word").html("恭喜你，设备已经迁移成功");
				$(".register-result").find(".tips-word").html("系统正在进行初始化，请稍后...");
			}
		}
		$(".register-result").fadeIn();
	}
	
	function gotoUrl(url){
		window.location.href = url;
	}
	
	<%-- 输入新馆id，查询新馆相关信息 --%>
	$("#new_library_id").on("keyup",function(){
		var libId = $(this).val();
		if(!libId){
			/* $("#new_library_idx").val("");
			$("#new_library_idx").val("");
			$("#new_library_name").val(""); */
			return;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/device/getLibName",
			type:"POST",
			data:{"json":libId},
			success:function(data){
				var libObj  = {};
				if(data.state){
					$("#new_library_idx").val(data.retMessage);
					/* $("#new_library_id").val(libId); */
					$("#new_library_name").val(data.result);
				}else{
					$("#new_library_idx").val("");
					/* $("#new_library_id").val(libId); */
					$("#new_library_name").val(查找失败);
				}
		 	 }
		});
	});
	
});

function isIP(strIP) {   
    var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g   
    if(re.test(strIP)){   
      if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) return true;   
    }   
    return false;   
} 
</script>

</body>
</html>




