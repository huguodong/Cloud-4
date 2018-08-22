<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<html lang="en">
  <head>
    <title>图书OPAC检索</title>
	<style>
		html, body {
            margin: 0;
            padding: 0;
            height: 100%;
            min-height: 100%;
        }
        body {
            margin: 0 auto;
            padding: 0;
            font-family: Trebuchet, Arial, Helvetica;
            /* background-color: #c1c1c1; */
            min-width: 1000px;
            width: 60%;
        }
        .top{
        	width: 100%;
        	height: 5%;
        	text-align:center;
        	line-height:40px;   
        	font-size:25px;
        	border: 1px solid #000;
        	background-color: #c1c1c1;
        }
        .mid{
        	width: 100%;
        	height: 3%;
        	border: 1px solid #fff;
        	display:table-cell; 
        	vertical-align:middle;
        }
        /* .barcodeTxt{
        	width:20%;
        	height: 10%
        } */
        .down{
        	width: 100%;
        	height: 91.5%;
        	border: 1px solid #000;
        }
        .down .pagebtn{
        	text-align:right;
        	width: 80%;
        	height: 3%;
        	background-color: gray;
        	padding-left: 20%;
        	line-height:25px;
        	font-size: 14px;
        }
        .down .pagebtn a{
        	line-height:25px;
        	height:90%;
    		margin-right: 5px;
        	border: 1px solid #e5e5e5;
    		text-decoration: none;
    		background: #fff;
    		color: #00c;
    		cursor: pointer;
    		outline: 0;
    		min-width: 34px;
    		vertical-align: middle;
    		border-radius: 5px;
    		text-align: center;
    		display: block;
    		float: left;
        }
        .down .pagebtn span {
        	line-height:25px;
        	height:90%;
       		margin-right: 5px;
		    border: 1px solid #d5d5d5;
		    background: #efefef;
		    font-weight: bold;
		    min-width: 34px;
		    vertical-align: middle;
		    border-radius: 5px;
    		text-align: center;
    		display: block;
    		float: left;
		}
        /* .down .pagebtn a:visited {
		    color: #741274;
		} */
        .down .bookTab{
        	width: 100%;
        	height:97%;
        	overflow: auto;
        }
        .down .bookTab .Tab{
        	border-collapse:collapse; 
        	width: 100%;
        }
        .down .bookTab tr{
        	border-bottom:1px solid black; 
        	width: 100%;
        }
        .bookTab .tdImg{
        	width: 10%;
        	height: 150px;
        }
        .bookTab .tdMsg{
        	width: 90%;
        	height: 100px;
        	padding-left: 20px;
        }
        .bookTab .tdMsg .bookinfoDiv{
        	width: 100%;
        	height: 90px;
        	overflow: hidden;
        }
        .bookTab .tdMsg .ckjwdh{
        	line-height:31px;
        	height:30px;
    		margin-right: 10px;
        	border: 1px solid #e5e5e5;
    		text-decoration: none;
    		background: #E0FFFF;
    		color: #00c;
    		cursor: pointer;
    		outline: 0;
    		font-size: 14px;
    		vertical-align: text-bottom;
    		border-radius: 5px;
    		text-align: center;
    		display: block;
    		float: left;
    		font-weight: bold;
		    min-width: 120px;
        }
        .bookTab .tdImg img{
        	width: 100%;
        	height: 100px;
        }
        #fullbg { 
			background-color:gray; 
			left:0; 
			opacity:0.5; 
			position:absolute; 
			top:0; 
			z-index:3; 
			filter:alpha(opacity=50); 
			-moz-opacity:0.5; 
			-khtml-opacity:0.5; 
			width: 100%;
		} 
		#dialog { 
			background-color:#fff; 
			border:5px solid rgba(0,0,0, 0.4); 
			height:100px; 
			left:50%; 
			margin:-200px 0 0 -200px; 
			padding:1px; 
			position:fixed !important; /* 浮动对话框 */ 
			position:absolute; 
			top:50%; 
			width:400px; 
			z-index:5; 
			border-radius:5px; 
			display:none; 
		} 
		#dialog p { 
			margin:0 0 12px; 
			height:24px; 
			line-height:24px; 
			background:#CCCCCC; 
		} 
		#dialog p.close { 
			text-align:right; 
			padding-right:10px; 
		} 
		#dialog p.close a { 
			color:#fff; 
			text-decoration:none; 
		} 
	</style>
  </head>
  <body>
  	<div class="top">
  		<span>深圳市海恒智能图书馆</span>
  	</div>
   	<div class="mid">
   		<select id="selType">
   			<option value="1">书名</option>
   			<option value="2">作者</option>
   			<option value="3">索书号</option>
   			<option value="4">ISBN</option>
   			<option value="5">出版社</option>
   			<option value="6">条码号</option>
   		</select>
   		<input class="selValue" id="selValue" type="text"/>
   		<button type="submit" id="selBtn">检索</button>
   	</div>
   	<div class="down">
		<div class="bookTab">
			<table class="Tab" id="Tab">
			</table>
		</div>
		<div class="pagebtn">
			<a class="pev" id="1">1</a>
		</div>
	</div>
	<div id="fullbg"></div> 
	<div id="dialog"> 
	<p class="close"><a href="#" onclick="closeBg();">关闭</a></p> 
	<div style="text-align: center">正在加载，请稍后....<br/><br/><img src="${pageContext.request.contextPath}/static/images/jiazai2.gif"></img></div> 
	</div> 
  </body>
  <script type="text/javascript">
  	var currentPage= 1;
  	var totalPage = 1;
  	$(".mid").on("click","#selBtn",function(){
  		//获取检索条件
  		var selType = $("#selType").val();
  		var selValue = $("#selValue").val();
  		if(selValue == null || selValue.length == 0){
  			alert("请输入查询内容");
  			return;
  		}
  		var Json = {};
  		if(selType == 1){
  			Json = {"title":selValue}
  		}else if(selType == 2){
  			Json = {"author":selValue}
  		}else if(selType == 3){
  			Json = {"callNo":selValue}
  		}else if(selType == 4){
  			Json = {"isbn":selValue}
  		}else if(selType == 5){
  			Json = {"publish":selValue}
  		}else if(selType == 6){
  			Json = {"book_barcode":selValue}
  		}
		var Page={"page":1,"pageSize":10};
		currentPage = 1;
  		selectBookData(Json,Page);
  	});
  	function selectBookData(Json,Page){
  		var param={	json: Json , page : Page};
		$.ajax({
		url:"${pageContext.request.contextPath}/bookitem/opacQueryBookitem",
		type:"POST",
		data:{"req":JSON.stringify(param),},
		beforeSend: function () {  
	        // 禁用按钮防止重复提交  
	        $("#selBtn").attr({ disabled: "disabled" });  
	        showBg();
	    }, 
		success:function(data){
			var html = "";
			$.each(data.result.rows,function(i,item){
				var shelfLayerCode = item.shelflayer_barcode;
				var name = item.title;
				if(name == null || name.length == 0){
					return true;
				}
				var isbn = item.isbn == null ? "&nbsp;" : item.isbn;
				var callNo = item.callNo == null ? "&nbsp;" : item.callNo;
				var author = item.author == null ? "&nbsp;" : item.author;
				var publish = item.publish == null ? "&nbsp;" : item.publish;
				html += "<tr>";
				html += "<td class='tdImg'><img src='${pageContext.request.contextPath}/static/images/noImg.jpg'></img></td>";
				html += "<td class='tdMsg'>";
				html += "<div class='bookinfoDiv'>";
				html += "<b>"+name+"</b><br/>";
				html += "<p>ISBN: "+isbn+" 索书号"+callNo+" 作 者："+author+" 出版社："+publish+";</p><br/>";
				html += "</div>";
				if(shelfLayerCode != null && shelfLayerCode.length > 0 && item.book_barcode != null && item.book_barcode.length > 0){
					html += "<a class='ckjwdh' href='${pageContext.request.contextPath}/bookcode/"+item.book_barcode+"' target='_blank'>查看架位导航</a>";
				}
				html += "</td>";
				html += "</tr>";
			}); 
			$(".Tab").empty().html(html);
			var totalPage = Math.floor(data.result.total/10);
			if(totalPage == 0) totalPage = 1;
			drawFenYe(currentPage,totalPage);       
		},
	    complete: function () {  
	        $("#selBtn").removeAttr("disabled");  
	        closeBg();
	    }
		});
  	};
  	$(".pagebtn").on("click",".pev",function(){
  		var id = $(this).attr('id');
  		if(id == 'home'){
  			currentPage = 1;
  		}else if(id == 'previous'){
  			currentPage = parseInt(currentPage,10) -1;
  		}else if(id == 'next'){
  			currentPage = parseInt(currentPage,10) + 1;
  		}else if(id == 'last'){
  			currentPage = totalPage;
  		}else{
  			currentPage = id;
  		}
  		var selType = $("#selType").val();
  		var selValue = $("#selValue").val();
  		if(selValue == null || selValue.length == 0){
  			return;
  		}
  		var Json = {};
  		if(selType == 1){
  			Json = {"title":selValue}
  		}else if(selType == 2){
  			Json = {"author":selValue}
  		}else if(selType == 3){
  			Json = {"callNo":selValue}
  		}else if(selType == 4){
  			Json = {"isbn":selValue}
  		}else if(selType == 5){
  			Json = {"publish":selValue}
  		}else if(selType == 6){
  			Json = {"book_barcode":selValue}
  		}
		var Page={"page":currentPage,"pageSize":10};
  		selectBookData(Json,Page);
  	});
  	
  	function drawFenYe(currentPage,totalPage){
  		var html="页码:"+currentPage+"/"+totalPage+"页 每页 10条 &nbsp;&nbsp;&nbsp;&nbsp;"
  		if(currentPage > 1){
  			html += "<a class='pev' id='home'>首页</a>";
	  		html += "<a class='pev' id='previous'>上一页</a>";
  		}
  		if(currentPage <= 6){
  			if(totalPage <= 10){
  				for(var i=1;i<=totalPage;i++){
  					if(currentPage == i){
  						html += "<span>"+i+"</span>";
  					}else{
  						html += "<a class='pev' id='"+i+"'>"+i+"</a>";
  					}
  				}
  			}else{
  				for(var i=1;i<=10;i++){
  					if(currentPage == i){
  						html += "<span>"+i+"</span>";
  					}else{
  						html += "<a class='pev' id='"+i+"'>"+i+"</a>";
  					}
  				}
  			}
  		}else{
  			var end = parseInt(currentPage,10)+4;
  			var start = parseInt(currentPage,10)-5;
  			if(end >= totalPage){
  				for(var i=start;i<=totalPage;i++){
  					if(currentPage == i){
  						html += "<span>"+i+"</span>";
  					}else{
  						html += "<a class='pev' id='"+i+"'>"+i+"</a>";
  					}
  				}
  			}else{
  				for(var i=start;i<=end;i++){
  					if(currentPage == i){
  						html += "<span>"+i+"</span>";
  					}else{
  						html += "<a class='pev' id='"+i+"'>"+i+"</a>";
  					}
  				}
  			}
  		}
  		if(currentPage<totalPage){
  			html += "<a class='pev' id='next'>下一页</a>";
  	  		html += "<a class='pev' id='last'>尾页</a>";
  		}
  		$(".pagebtn").empty().html(html);
  	};
  	window.onload = function() {
  		$("#1").trigger("click");//触发button的click事件
  	}
  	
  	//显示灰色 jQuery 遮罩层 
  	function showBg() { 
	  	var bh = $("body").height(); 
	  	var bw = $("body").width(); 
	  	$("#fullbg").css({ 
	  	height:bh, 
	  	display:"block"
  		}); 
  	$("#dialog").show(); 
  	} 
  	//关闭灰色 jQuery 遮罩 
  	function closeBg() { 
  	$("#fullbg,#dialog").hide(); 
  	} 
  </script>
</html>
