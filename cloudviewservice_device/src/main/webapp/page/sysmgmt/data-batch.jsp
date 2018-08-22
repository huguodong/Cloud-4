<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style>
.upload-dialog .form-wrap {
   	padding: 30px 0 20px;
}

#upload_dialog form { 
	display: block; 
	margin: 20px auto; 
/* 	background: #eee;  */
	border-radius: 10px; 
	padding: 15px 
}

#upload_dialog #progress { 
	position:relative; 
	margin: 10px auto;
	width: 90%; 
	border: 1px solid #ddd; 
	padding: 1px; 
	border-radius: 3px; 
}

#upload_dialog #bar { 
	background-color: #B4F5B4; 
	width:0%; 
	height:20px; 
	border-radius: 3px; 
}

#upload_dialog #percent { 
	position:absolute; 
	display:inline-block; 
	top:3px; 
	left:48%; 
}  
#message{
	height: 20px;
}

.g-choose{
	padding-left: 60px;
	
}
.row{
	margin: 0 10px 10px 10px;
	text-align: left;
}

</style>

<title>基础数据批处理</title>
<div class="system-databackup">
<div class="g-loading"></div>
	<div  class=""><%--main-content --%>
		<div class="page-title-bar">
			<span class="title">基础数据批处理<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/sysmgmt/system-databackup.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
<!-- 				<div class="g-inputdate"><input name="startDate" readonly="readonly" type="text" placeholder="起始时间" class="g-input datepicker"><span class="icon"></span></div>  -->
<!-- 				<span class="fl">-</span> -->
<!-- 				<div class="g-inputdate"><input name="endDate" readonly="readonly" type="text" placeholder="结束时间" class="g-input datepicker"><span class="icon"></span></div>  -->
<!-- 				<div class="btn search">查询</div> -->
<!-- 				<div class="btn backup g-btn-green">备份</div> -->
				<div class="btn upload g-btn-blue">上传</div>
<!-- 				<div class="btn delete">删除</div> -->
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col3">状态</th>
						<th class="col2">上传文件</th>
						<th class="col3">文件大小</th>
						<th class="col3">数据类型</th>
						<th class="col4">后台处理进度</th>
						<th class="col5">开始时间</th>
						<th class="col6">操作</th>
					</tr>
				</thead>
				<tbody>
<!-- 				<tr> -->
<!-- 					<td class="col2">bookitem.txt</td> -->
<!-- 					<td class="col3">60mb</td> -->
<!-- 					<td class="col4">50.01%</td> -->
<!-- 					<td class="col5"> -->
<!-- 					<span class="btn-a g-btn-red" onclick='alert("停止")'>停止</span></td> -->
<!-- 				</tr> -->
				</tbody>
			</table>
		</div>
		
<!-- 		<div class="paging-bar"> -->
<!-- 				<div class="left"> -->
<!-- 					<span class="t2 fl">显示</span> -->
<!-- 					<div class="g-select refresh"> -->
<!-- 						<select id="showSize"> -->
<!-- 							<option value="10" selected>10</option> -->
<!-- 							<option value="30" >30</option> -->
<!-- 							<option value="60">60</option> -->
<!-- 						</select> -->
<!-- 						<span class="arr-icon"></span> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div id="page" class="right"> -->
<!-- 				</div> -->
<!-- 				<span class="total-page fr"></span> -->
<!-- 				<span class="total-num fr"></span> -->
<!-- 		</div>	 -->
	
	</div>
</div>



<div class="upload-dialog dialog1 form-dialog" id="upload_dialog">
	<span class="line"></span>
	<div class="form-wrap">
	    <!-- form表单中的提交参数元素的name不能一样，后台通过name获取参数值 -->
		<form id="myForm" action="${pageContext.request.contextPath }/batch/fileUpload" method="post" enctype="multipart/form-data">  
			<div class="g-choose row">
				
				<div class="g-radio on"> 
					<input type="radio" name="datatype" id="d1" value="biblios" checked/>
				</div>
				<label for="d1">书目数据</label>
				<div class="g-radio"> 
					<input type="radio" name="datatype" id="d2" value="bookitem"/>
				</div>
				<label for="d2">书目带馆藏数据</label>
				<div class="g-radio"> 
					<input type="radio" name="datatype" id="d3" value="reader"/>
				</div>
				<label for="d3">读者数据</label>
			</div>
			<div class="g-choose row">
				<div class="g-radio on"> 
					<input type="radio" name="update" id="u1" value="1" checked>
				</div>
				<label for="u1">更新已有数据</label>
				<div class="g-radio"> 
					<input type="radio" name="update" id="u2" value="2">
				</div>
				<label for="u2">不更新已有数据</label>
			</div>
			<div class="row">
				<div class="left" style="width: 100px;"><span class="">编码</span></div>
				<div class="g-select" style="width:130px;">
					<select id="encoding" name="encoding" style="width:140px;">
						<option value="gbk" selected>GBK</option>
						<option value="utf-8" >UTF-8</option>
					</select>
					<span class="arr-icon"></span>
				</div>
			</div>
			<div class="row hide" style="" id="libdiv">
				<div class="left" style="width:100px;display:inline-block;"><span class="">所属馆ID</span></div>
				<div class="" style="">
					<input type="hidden" id="libidx" name="libidx">
					<input type="text" placeholder="请输入"  id="library_id" class="g-input" style="width:150px;display: inline-block;">
					<input type="text" placeholder="所属馆名称" readonly="readonly" id="library_name" class="g-input" style="width:150px;color: gray;background-color: #E0E0E0;display: inline-block;">
				</div>
			</div>
			
<!-- 			<span>编码</span> -->
<!-- 			<select id="encoding" name="encoding"> -->
<!-- 				<option value="GBK">GBK</option> -->
<!-- 				<option value="UTF-8">UTF-8</option> -->
<!-- 			</select> -->
<!-- 			<span>所属馆</span> -->
<!-- 			<input type="text" id="libidx" name="libidx" value="1"/> -->
			<div class="row" style="margin-top:20px;">
				<div class="left" style="width:100px;"><span class="">上传文件</span></div>
		    	<input type="file" size="60" id="file" name="myfile">  
			</div>
			 <div id="progress">  
		        <div id="bar"></div>  
		        <div id="percent">0%</div >  
			 </div> 
			<div id="message"></div>
		</form>  
		<div class="form-btn cancel g-btn-gray">取消</div>
		<div class="form-btn upload g-btn-green">上传</div>
	</div>
</div>
<!-- <div class="upload-dialog dialog1 form-dialog" id="upload_dialog"> -->
<!-- 	<span class="line"></span> -->
<!-- 	<div class="form-wrap"> -->
<!--  	请输入路径：	 		 -->
<!--  	<input id="file" name="file" type="file"  class="input" /> -->
<!-- 	</div> -->
<!-- 	<div class="form-btn cancel g-btn-gray">取消</div> -->
<!-- 	<div class="form-btn upload g-btn-green">上传</div> -->
<!-- </div> -->

<!-- <div class="upload-dialog dialog1 form-dialog" id="choose_dialog"> -->
<!-- 	<span class="line"></span> -->
<!-- 	<div class="form-wrap"> -->
	
		
<!-- 	</div> -->
<!-- </div> -->
<div class="upload-dialog dialog1 form-dialog" id="interruptDiv">
	<span class="line"></span>
	<div class="word">
	是否要停止当前的上传任务
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn interrupt g-btn-red">停止</div>		
</div>


<div class="load-dialog">
	<span class="line"></span>
	<div class="load-gif"></div>
	<div class="word">上传成功，正在处理数据···</div>
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
});

var uploadEvn = {};
uploadEvn.isClicked = false;<%-- 是否正在上传 --%>
uploadEvn.uploadDialog = null;
$(".btn.upload").on("click",function(){
	uploadEvn.uploadDialog = layer.open({
		type: 1,
		shade: false,
		title: false, //不显示标题
		scrollbar :false,
		closeBtn :0,
		shade:0.5,
		shadeClose :false,
		area:["600px"],
		offset :["195px"],
		content: $("#upload_dialog"), //捕获的元素
		success : function(index,layero){
			var file = $("#file") ;//清空file
			file.after(file.clone().val(""));      
			file.remove();
			$("#upload_dialog .cancel").on("click",function(){
				if(uploadEvn.uploadDialog){
					layer.close(uploadEvn.uploadDialog);
					uploadEvn.uploadDialog = null;
				}
			});  
		},
		end:function(){
			$("#upload_dialog .cancel").off("click");
		}
	});
});

$("#upload_dialog .upload").on("click",function(){
	if(uploadEvn.isClicked){return;}
// 	uploadEvn.isClicked = true;
	var libidx = $("#libidx").val();
	var datatype = $("input[name='datatype']:checked").val();
	if(_.isEmpty(libidx) && "biblios"!=datatype){
		layer.alert("请填写所属馆id");
		uploadEvn.isClicked = false;
		return;
	}
	if(!$("#file").val()){
		layer.alert("请选择上传文件");
		uploadEvn.isClicked = false;
		return;
	}
	
	<%-- 判断是不是已经有上传的任务了 --%>
	$("#myForm").submit();
	
// 	var dialogIndex = loadingDialog({
// 		loadText:"请稍等，上传中..."
//  });
	
});

function loadingDialog(options){
	var defaults = {
		loadText:"正在删除···"
	};
	defaults = $.extend(defaults,options);

	$('.load-dialog .word').text(defaults.loadText);

	dialogIndex = layer.open({
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

<%-- 上传处理dialog --%>
var dialogIndex = null;

var options = null;
var resultList;
$(function(){
	options = {   
		    beforeSend: function() {  
		        $("#progress").show();  
		        //clear everything  
		        $("#bar").width('0%');  
		        $("#percent").html("0%"); 
		    },  
		    uploadProgress: function(event, position, total, percentComplete) {  
		        $("#bar").width(percentComplete+'%');  
		        $("#percent").html(percentComplete+'%');  
		  		$("#message").html( position +"/"+ total );
		  		//上传100%之后
		      	if(percentComplete==100 && dialogIndex==null){
		      		dialogIndex = loadingDialog({
						loadText:"上传成功，正在处理数据..."
				   	});
		      	}
		    },  
		    success: function(data) {
		        $("#bar").width('100%');  
		        $("#percent").html('100%');  
		        
		        if(data) {
		        	console.log("ddddddddddddddddd",data)
		        	//获取上传进度
		        	if(data.state){
// 		        		if(data.result!=null) {
// 		        			var list = data.result;
// 		        			if(list!=null && list.length>0){
// 		        				resultList = list;
// 		        				showList(page, pageSize);
// 		        			}
// 		        		}
				        <%-- 上传完成之后 --%>
				        if(uploadEvn.uploadDialog){
				        	layer.close(uploadEvn.uploadDialog);
				        	uploadEvn.uploadDialog = null;
				        }
				        getProcess();
		        	}else{
		        		layer.alert(data.message);
		        	}
		        	
		        	if(dialogIndex){
			        	layer.close(dialogIndex);
			        	dialogIndex = null;
			        }
		        }
		       
		    },  
		    <%-- 完成传输之后 --%>
		    complete: function(response) {
// 		    	console.log(response)
		    	if (response.readyState === 4) {
		            if (response.status === 200) {
// 		            	$("#message").html(JSON.parse(response.responseText));
		            } else {
		            }
		        }
// 		    	console.log(response)
// 		        $("#message").html("<font color='green'>"+response.responseText+"</font>");  
		    },  
		    error: function(response) { 
// 		    	console.log(response)
		        $("#message").html("<font color='red'> 服务器出错，请稍后再试！</font>");  
		    	if(dialogIndex){
		        	layer.close(dialogIndex);
		        	dialogIndex = null;
		        }
		    }
	}
	
	$("#myForm").ajaxForm(options);  
	
	
	
	<%-- 页面加载完成之后查询是不是有上传任务 --%>
	getProcess();
});



var pTimer = null;

function getProcess(){
	$(".g-table tbody").html("");
	$.ajax({
		url:"${pageContext.request.contextPath}/batch/getUploadProcessing",
		type:"POST",
		data:{}
	}).done(function(data){
		console.log(data)
		if(data && data.state){
			var html = "";
			if(data.result){
				html += "<tr>";
				if(data.result.interrupted){
					html += "<td class=\"col3\">"+ "<span style=\"color:red;\">终止</span>" +"</td>";
				}else if(data.result.completed){
					html += "<td class=\"col3\">"+ "<span style=\"color:green;\">完成</span>" +"</td>";
				}else{
					html += "<td class=\"col3\">"+ "<span style=\"color:blue;\">处理中</span>" +"</td>";
				}
				html += "<td class=\"col2\">"+ data.result.fileName +"</td>";
				html += "<td class=\"col3\">"+ data.result.fileSize +"字节</td>";
				html += "<td class=\"col3\">"+ "bookitem" +"</td>";
				html += "<td class=\"col4\">"+ data.result.processing +"%</td>";
				html += "<td class=\"col5\">"+ data.result.begTime +"</td>";
				html += "<td class=\"col6\">" ;
				if(data.result.interrupted){
					html += "<span class=\"btn-a g-btn-red\" onclick='deleteUpload()'>删除</span>";
				} else if(data.result.completed){
					html += "<span class=\"btn-a g-btn-red\" onclick='deleteUpload()'>删除</span>";
// 					html += "<a class=\"btn-a g-btn-blue\" download  href=\"\">查看结果</a>";
					html += "<a class=\"btn-a g-btn-blue\"   onclick='downloadLog()'>查看错误信息</a>";
				} else{
					html += "<span class=\"btn-a g-btn-red\" onclick='interruptUpload()'>停止</span>";
				}
				html += "</td>";
				html += "</tr>";
			}
			$(".g-table tbody").html(html);
			if(!data.result.interrupted && !data.result.completed){
				updateProcess();
			}
		}
	}).always(function(data){
		console.log("always:",data);
	});
}

<%-- 更新上传进度 --%>
function updateProcess(){
	$.ajax({
		url:"${pageContext.request.contextPath}/batch/getUploadProcessing",
		type:"POST",
		data:{}
	}).done(function(data){
		if(data && data.state){
			if(data.result){
				$(".g-table tbody tr .col4").html(data.result.processing+"%");
			}
			if(data.result && data.result.completed){
				getProcess();
			}else{
				setTimeout(updateProcess, 5000);
			}
		}else{
			getProcess();
		}
	}).always(function(data){
		console.log("always:",data.result.processing);
	});
}

var interruptDiv = null;

<%-- 中断上传 --%>
function interruptUpload(){
	
	interruptDiv = layer.open({
		type: 1,
		shade: false,
		title: false, //不显示标题
		scrollbar :false,
		closeBtn :0,
		shade:0.5,
		shadeClose :false,
		area:["400px"],
		offset :["195px"],
		content: $("#interruptDiv"), //捕获的元素
		success : function(index,layero){
			$("#interruptDiv .cancel").on("click",function(){
				if(interruptDiv){
					layer.close(interruptDiv);
					interruptDiv = null;
				}
			});  
		},
		end:function(){
			$("#interruptDiv .cancel").off("click");
		}
	});
}

<%-- 删除上传 --%>
function deleteUpload(){
	
	dialogIndex = loadingDialog({
		loadText: "正在删除..."
   	});
	
	$.ajax({
		url:"${pageContext.request.contextPath}/batch/deleteUploading",
		type:"POST",
		data:{}
	}).done(function(data){
		if(data&&data.state){
			window.location.reload();
		}
	}).always(function(){
		if(dialogIndex){
			layer.close(dialogIndex);
			dialogIndex = null
		}
		if(interruptDiv){
			layer.close(interruptDiv);
			interruptDiv = null;
		}
	});
	
}

function downloadLog(){
// 	window.location.href = "${pageContext.request.contextPath}/batch/downloadLogFile";	
	window.open( "${pageContext.request.contextPath}/batch/downloadLogFile")
}

$("#interruptDiv .interrupt").on("click", function(){
	
	dialogIndex = loadingDialog({
		loadText: "正在中断上传..."
   	});
	
	$.ajax({
		url:"${pageContext.request.contextPath}/batch/interruptUploading",
		type:"POST",
		data:{}
	}).done(function(data){
		if(data&&data.state){
			window.location.reload();
		}
	}).always(function(){
		if(dialogIndex){
			layer.close(dialogIndex);
			dialogIndex = null
		}
		if(interruptDiv){
			layer.close(interruptDiv);
			interruptDiv = null;
		}
	});
	
});

<%-- 图书馆ID修改之后，根据id查询图书馆名称，并且查询图书馆的用户组信息 --%>
<%-- groupId:用户所在的用户组id --%>
<%-- groupId mId 在queryOperatorDetailByIdx的ajax中传递过来 --%>
$("#library_id").on("change",function(e){
	var libId = $(this).val();
	queryLibName(libId);
}).on("keyup",function(e){
	var libId = $(this).val();
	queryLibName(libId);
});

var loaddingLibName = false;
function queryLibName(libId){
	if(!_.isEmpty(libId) && !loaddingLibName){
		loaddingLibName=true;
		$.ajax({
			url:"<%=basePath%>operator/getLibNameByIdx",
			type:"POST",
			data:{json:libId},
			success:function(data){
				loaddingLibName=false;
				if(data!=null && data!=""){
					if(data.state){
						if(data.result.lib_name!=null){
							$("#library_name").val(data.result.lib_name);
							$("#library_id").val(data.result.lib_id);
							$("#libidx").val(data.result.library_idx);
						}else{
							$("#library_name").val(data.result);
							$("#libidx").val("");
						}
					}
				}
			}
		});
	}else{
		
	}
}

<%-- 如果是上传书目数据，则不用填写图书馆信息 --%>
$("input[name='datatype']").on("change",function(){
	var datatype=$("input[name='datatype']:checked").val();
	if("biblios"==datatype){
		$("#libdiv").addClass("hide");
	}else{
		$("#libdiv").removeClass("hide");
	}
});



</script>



