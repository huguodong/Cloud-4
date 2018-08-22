<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/e-smart-zoom-jquery.js"></script>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>图书导航</title>
    <style>
        html, body {
            margin: 0;
            padding: 0;
            height: 100%;
            min-height: 100%;
        }
        body {
            margin: 0;
            padding: 0;
            font-family: Trebuchet, Arial, Helvetica;
            background-color: #c1c1c1;
            min-width: 1000px;
        }
        .page-top{
            height:10%;
            /* background-color:#100000; */
            width:100%;
            margin:0;
            text-align: center;
            border-bottom:1px solid #000
        }
        #lmsg{
            margin: 0 auto;
            font-family: 微软雅黑;
            font-size: 50px;
        }
        .page-down{
            width:100%;
            /*background:#0099CC;*/
            height:89%;
            margin:0;
        }
        .page-down-left{
            width:15%;
            height:100%;
            /*background:#CCCC66;*/
            float:left;
            margin:0;
        }
        .page-down-right{
            width:85%;
            height:100%;
            /*background:#FFFFFF;*/
            margin:0;
            float:left;
        }
        .page-down-left-top{
            height:30%;
            /*background-color:red;*/
            width:80%;
            margin:auto;
            font-family: 微软雅黑;
            font-size: 10px;
            color: black;
            background: url("${pageContext.request.contextPath}/static/images/book.jpg");
            /* background-size:cover; */
            background-size:100% 100%;-moz-background-size:100% 100%;
            border:0px solid #000000;
            border-left:1px solid white;
            border-right:1px solid white;
        }
        table{
            border:0px solid white;
            width:100%;
            /* background-color: red; */
            padding-left: 15%;
            padding-top: 10%;
            /*height: 100%;*/
            /*margin: 0;*/
            /*设置tr间距*/
            /*border-collapse:separate;
            border-spacing:5px;*/
        }
        table tr{
            height: 5%;
        }
        table .td1{
            width:30%;
            text-align:right;
            padding-right: 5%;
        }
        table .td2{
            width:70%;
        }
        .page-down-left-down{
            /* background:green; */
            height:70%;
            width:80%;
            margin:0px auto;
            position: relative;
        }
        #shujiaCanvas{
            height:100%;
            width:100%;
            background-color:#FFFFFF;
            /* position:absolute; */
            background: url("${pageContext.request.contextPath}/static/images/noShelfImage.png") repeat;
            /* background-size:cover; */
            background-size:100% 100%;-moz-background-size:100% 100%;
        }
        #shujiaImg{
            width:100%;
            height:100%;
        }
        #shelf{
            width:100%;
            height:100%;
            /*background-color:#ff0000;*/
        }
        #pageContent{
            width:100%;
            height:100%;
            /*background-color: darkgreen;*/
            /*width: 980px;
            height: 500px;*/
            overflow: hidden;
            position:relative;
            margin:0px auto;
        }
        #imgContainer {
            width:100%;
            height:100%;
            /*width: 980px;
            height: 500px;*/
        }
        #positionButtonDiv {
            background: rgb(58, 56, 63);
            background: rgba(58, 56, 63, 0.8);
            border: solid 1px #100000;
            color: #FFFFFF;
            padding: 8px;
            text-align: left;
            position: absolute;
            right:35px;
            top:35px;
        }
        #positionButtonDiv .positionButtonSpan img {
            float: right;
            border: 0;
        }
        .positionMapClass area {
            cursor: pointer;
        }
        .zoomButton {
            border: 0;
            cursor: pointer;
        }
        .zoomableContainer {
            /* background-image: url("${pageContext.request.contextPath}/static/images/transparent.png"); */
            /* background: url("${pageContext.request.contextPath}/static/images/transparent.png");
            background-size:cover; */
        }
        #imageFullScreen{
            width: 1600px;
            height: 800px;
            /* background: url("${pageContext.request.contextPath}/static/images/louceng.jpg") no-repeat;
            background-size:cover; */
        }
    </style>
<body>
    <div class="page-top"><div id="lmsg">${locations[0]}馆${locations[1]}楼${locations[2]}区${locations[3]}排${locations[4]}面${locations[5]}列<c:if test="${navigationInfoEty.shelf_layer eq '1'}">1~3</c:if><c:if test="${navigationInfoEty.shelf_layer eq '2'}">4~6</c:if>层</div></div>
    	<input type="hidden" value="<%-- ${locations[1]}楼${locations[2]}区 --%>${locations[3]}排${locations[4]}面${locations[5]}列<c:if test="${navigationInfoEty.shelf_layer eq '1'}">1~3</c:if><c:if test="${navigationInfoEty.shelf_layer eq '2'}">4~6</c:if>层" id="locationMsgInput"/>
    <div class="page-down">
        <div class="page-down-left">
            <div class="page-down-left-top">
                <table>
                    <tr>
                        <td class="td1">条码号:</td>
                        <td class="td2">${bookitemEntity.book_barcode}</td>
                    </tr>
                    <tr>
                        <td class="td1">书名:</td>
                        <td class="td2">${bookitemEntity.title}</td>
                    </tr>
                    <tr>
                        <td class="td1">架位号:</td>
                        <td class="td2">${navigationInfoEty.shelflayer_barcode}</td>
                    </tr>
                </table>
            </div>
            <div class="page-down-left-down">
                <%-- <div id="shujiaDiv">
                    <img src="${pageContext.request.contextPath}/static/images/shujia.jpg" alt="" id="shujiaImg"/>
                </div> --%>
                <div id="shujiaCanvas">
                    <canvas id="shelf">您的浏览器不支持 canvas 标签</canvas>
                </div>
            </div>
        </div>
        <div class="page-down-right">
            <div id="pageContent">
                <div id="imgContainer">
                    <!--<img id="imageFullScreen" src="assets/zoomFullScreen.jpg"/>-->
                    <canvas id="imageFullScreen"></canvas>
                </div>
                <div id="positionButtonDiv">
                    <p><span> <img id="zoomInButton" class="zoomButton" src="${pageContext.request.contextPath}/static/images/zoomIn.png" title="zoom in" alt="zoom in" /> 
                    <img id="zoomOutButton" class="zoomButton" src="${pageContext.request.contextPath}/static/images/zoomOut.png" title="zoom out" alt="zoom out" /> </span> </p>
                    <p><span class="positionButtonSpan">
                    <map name="positionMap" class="positionMapClass">
                        <area id="topPositionMap" shape="rect" coords="20,0,40,20" title="move up" alt="move up"/>
                        <area id="leftPositionMap" shape="rect" coords="0,20,20,40" title="move left" alt="move left"/>
                        <area id="rightPositionMap" shape="rect" coords="40,20,60,40" title="move right" alt="move right"/>
                        <area id="bottomPositionMap" shape="rect" coords="20,40,40,60" title="move bottom" alt="move bottom"/>
                    </map>
                    <img src="${pageContext.request.contextPath}/static/images/position.png" usemap="#positionMap" /> </span> </p>
                </div>
            </div>
            <div id="dataArray" style="display: none">
			<table>
				<c:forEach items="${points }" var="point" varStatus="s">
					<tr id="route_x${s.index+1}"><td id="route_xx${s.index+1}">${point[0]}<td></tr>
					<tr id="route_y${s.index+1}"><td id="route_yy${s.index+1}">${point[1]}<td></tr>
				</c:forEach>
				<tr id="route_no"><td id="route_noo">${fn:length(points)}<td></tr>
			</table>
			</div>
        </div>
    </div>
<script>
    window.onload = function(){
    	var shelfImageUrl = '${navigationInfoEty.shelfimage_url}';
    	if(shelfImageUrl.length > 0){
    		var url = "/temp/"+shelfImageUrl;
    		$("#shujiaCanvas").css("background-image", 'url(' + url + ')')
    	}
        //标题闪动
        var i=0,add=1;
        function FadeText()
        {
            if( add==1 )
            {
                i++;
                document.getElementById("lmsg").style.color='#FF0000';
                if( i==15 ) add=0;
            }
            else
            {
                i--;
                document.getElementById("lmsg").style.color='#FF0000';
                if( i==0 ) add=1;
            }
        };

        //给图书书架描色
        function shelf(){
            var c=document.getElementById("shelf");
            var ctx=c.getContext("2d");

            var img = document.getElementById("shelf");
            var ImgWidth = img.width;
            var ImgHeight = img.height;

            //定义书架图片上下边框宽度，层次间宽度
            var downH = ImgHeight/10;
            var topH = ImgHeight/40;
            var midH = ImgHeight/100;
            //var shadowH = (ImgHeight-topH-downH)/${navigationInfoEty.layercount}; //总高度减去上下宽度再除以总层数
            //var shadowS = (${navigationInfoEty.shelf_layer} -1) * shadowH + topH + midH; //起始位置
            
            //正常情况是 oneShelfOwnLayerNum = ${navigationInfoEty.layercount};
            var oneShelfOwnLayerNum = 2; //特殊情况一个书架上只贴有两个标签
            
            var actMidH = (ImgHeight-topH-downH)/6;
            var shadowH = (ImgHeight-topH-downH)/6; //总高度减去上下宽度再除以总层数
            var lastH = (ImgHeight-topH-downH)/6;
            
            if(${navigationInfoEty.layercount} % oneShelfOwnLayerNum == 0){
            	var num = (${navigationInfoEty.layercount}) / oneShelfOwnLayerNum;
            	lastH = shadowH * num;
            	shadowH = shadowH * num;
            }else{
            	var num = Math.floor((${navigationInfoEty.layercount}) / oneShelfOwnLayerNum);
            	if(${navigationInfoEty.shelf_layer} == oneShelfOwnLayerNum){
            		lastH = shadowH * num;
            		//最后一个层算法应该是全部减去之前
            		shadowH = shadowH * (${navigationInfoEty.layercount} - num * (oneShelfOwnLayerNum - 1));
            		
            	}else{
            		lastH = shadowH * num;
            		shadowH = shadowH * num;
            	}
            }
            
            var shadownSWhite = (6-${navigationInfoEty.layercount}) * actMidH + topH + midH;
            
            var shadowS = shadownSWhite + (${navigationInfoEty.shelf_layer} -1) * lastH; //起始位置
            
            
			/* if(${navigationInfoEty.shelf_layer} == '1'){
				shadowH = shadowH*3;
            }else if(${navigationInfoEty.shelf_layer} == '2'){
            	shadowH = shadowH*3;
            	shadowS = shadowH + topH + midH;
            } */
            //console.log(shadowS,shadowH);
            ctx.globalAlpha=0.5;
            if( add== 1) {ctx.fillStyle="#99CCFF";}
            else{ctx.fillStyle="#FFFFFF";}
            ctx.clearRect(0,shadowS,ImgWidth,shadowH);
            ctx.fillRect(0,shadowS,ImgWidth,shadowH);
        };

        setInterval(function(){
            FadeText();
            shelf();
        },10);


        $('#imageFullScreen').smartZoom({'containerClass':'zoomableContainer'});
        $('#topPositionMap,#leftPositionMap,#rightPositionMap,#bottomPositionMap').bind("click", moveButtonClickHandler);
        $('#zoomInButton,#zoomOutButton').bind("click", zoomButtonClickHandler);
        
        function zoomButtonClickHandler(e){
            var scaleToAdd = 0.8;
            if(e.target.id == 'zoomOutButton')
                scaleToAdd = -scaleToAdd;
            $('#imageFullScreen').smartZoom('zoom', scaleToAdd);
        }
        function moveButtonClickHandler(e){
            var pixelsToMoveOnX = 0;
            var pixelsToMoveOnY = 0;

            switch(e.target.id){
                case "leftPositionMap":
                    pixelsToMoveOnX = 50;
                    break;
                case "rightPositionMap":
                    pixelsToMoveOnX = -50;
                    break;
                case "topPositionMap":
                    pixelsToMoveOnY = 50;
                    break;
                case "bottomPositionMap":
                    pixelsToMoveOnY = -50;
                    break;
            }
            $('#imageFullScreen').smartZoom('pan', pixelsToMoveOnX, pixelsToMoveOnY);
        }
        
        var canvas = document.getElementById('imageFullScreen');
        console.log(canvas.width,canvas.height)
        canvas.width = 1500;
        canvas.height = 1300;

        var interval = 15;//小方格的大小

        var ctx = canvas.getContext('2d');
	
        //(791,1018)-(1160,872)-(1113,803)-(1634,602)
        //	(341,332)-(479,281)-(468,257)-(669,198)
        
		var line_factor_x = 0.94; //308
		var line_factor_y = 1.63; //529.36
		
		var img = new Image();
		var floorImageUrl = '${navigationInfoEty.floorimage_url}';
		var url = "/temp/"+floorImageUrl;
    	if(floorImageUrl.length > 0){
    		//img.src = "${pageContext.request.contextPath}/static/images/"+'${navigationInfoEty.floorimage_url}';
    		img.src = url;
    	}else{
    		img.src = "${pageContext.request.contextPath}/static/images/noFloorImage.png";
    	}

        var nos = parseInt(document.getElementById("route_noo").innerHTML);
		if( nos <= 0 || floorImageUrl.length == 0){
			img.onload = function() {
	            ctx.drawImage(img, 0, 0,1500,1300);
	        }
			return;
		}
		
		var org_x = parseFloat(parseFloat(document.getElementById("route_xx1").innerHTML)*line_factor_x);
		var org_y = parseFloat(parseFloat(document.getElementById("route_yy1").innerHTML)*line_factor_y);
		var dest_x = parseFloat(parseFloat(document.getElementById("route_xx" + String(nos)).innerHTML)*line_factor_x);
        var dest_y = parseFloat(parseFloat(document.getElementById("route_yy" + String(nos)).innerHTML)*line_factor_y);
		var locationMsgInput = $("#locationMsgInput").val();
		var fzLen = locationMsgInput.length;
		
        img.onload = function() {
            ctx.drawImage(img, 0, 0,1500,1300);
            ctx.beginPath();
        	ctx.fillStyle="#FF0000";
        	ctx.arc(org_x,org_y,5,0,Math.PI*2,true);
        	ctx.arc(dest_x,dest_y,5,0,Math.PI*2,true);
        	ctx.font="10px sans-serif";
        	ctx.strokeStyle = "#FF0000";
        	ctx.strokeText('入口',org_x-12, org_y+20);
        	ctx.fillStyle="#FFE4E1";
        	ctx.fillRect(dest_x-5,dest_y+5,fzLen*9,20);
        	ctx.strokeStyle = "#FF0000";
            ctx.strokeText(locationMsgInput,dest_x-5, dest_y+20);
            ctx.closePath();
        	ctx.fill();
        }

        DrawX();

        // 画横线的方法
        function DrawX(){
            var timer = 500;
            var i = 1;

            function TM(){
                setInterval(function(){
                    if ( i < nos ) {
                        ctx.beginPath();
                        ctx.strokeStyle = 'red';
                        ctx.lineWidth = 2;
                        ctx.lineCap = 'round';
                		org_x = parseFloat(parseFloat(document.getElementById("route_xx" + String(i)).innerHTML)*line_factor_x);
                        org_y = parseFloat(parseFloat(document.getElementById("route_yy" + String(i)).innerHTML)*line_factor_y);
                        dest_x = parseFloat(parseFloat(document.getElementById("route_xx" + String(i+1)).innerHTML)*line_factor_x);
                        dest_y = parseFloat(parseFloat(document.getElementById("route_yy" + String(i+1)).innerHTML)*line_factor_y);
                        
                        /* ctx.moveTo(org_x, org_y);
                        ctx.lineTo(dest_x, dest_y);
                        ctx.stroke(); */
                        drawDirectionLine(org_x, org_y,dest_x, dest_y);
                        i++;
                    }
                },timer);
            }
            TM();
        }
        
        function drawDirectionLine(x1, y1, x2, y2) {
			var angle = Math.abs(Math.atan((x2 - x1) / (y2 - y1))); //倾斜角余角
			var length = 10; //箭头斜线长度
			var degree = Math.PI / 6; //箭头倾斜角
			var theta = 0;
			var altha = 0;
			var a1 = 0;
			var b1 = 0;
			var a2 = 0;
			var b2 = 0;

			//console.log("angle: " + angle);
			//console.log("degree: " + degree);

			if (angle >= degree && angle <= Math.PI / 2 - degree) {
				//console.log("30-60");
				theta = angle - degree;
				altha = Math.PI / 2 - 2 * degree - theta;
				if (x2 >= x1) {
					//console.log("x2 >= x1");
					a1 = x2 - length * Math.sin(theta);
					a2 = x2 - length * Math.cos(altha);
				} else {
					//console.log("x2 < x1");
					a1 = x2 + length * Math.sin(theta);
					a2 = x2 + length * Math.cos(altha);
				}
				if (y2 >= y1) {
					//console.log("y2 >= y1");
					b1 = y2 - length * Math.cos(theta);
					b2 = y2 - length * Math.sin(altha);
				} else {
					//console.log("y2 < y1");
					b1 = y2 + length * Math.cos(theta);
					b2 = y2 + length * Math.sin(altha);
				}
			} else {
				//console.log("0-30 && 60-90");
				theta = angle - degree;
				altha = theta + 2 * degree - Math.PI / 2;
				if (x2 >= x1 && y2 >= y1) {
					//console.log("x2 >= x1 && y2 >= y1");
					a1 = x2 - length * Math.sin(theta);
					b1 = y2 - length * Math.cos(theta);
					a2 = x2 - length * Math.cos(altha);
					b2 = y2 + length * Math.sin(altha);
				} else if (x2 >= x1 && y2 < y1) {
					//console.log("x2 >= x1 && y2 < y1");
					a1 = x2 - length * Math.sin(theta);
					b1 = y2 + length * Math.cos(theta);
					a2 = x2 - length * Math.cos(altha);
					b2 = y2 - length * Math.sin(altha);
				} else if (x2 < x1 && y2 < y1) {
					//console.log("x2 < x1 && y2 < y1");
					a1 = x2 + length * Math.sin(theta);
					b1 = y2 + length * Math.cos(theta);
					a2 = x2 + length * Math.cos(altha);
					b2 = y2 - length * Math.sin(altha);
				} else {
					//console.log("x2 < x1 && y2 >= y1");
					a1 = x2 + length * Math.sin(theta);
					b1 = y2 - length * Math.cos(theta);
					a2 = x2 + length * Math.cos(altha);
					b2 = y2 + length * Math.sin(altha);
				}
			}

			ctx.beginPath();
			ctx.moveTo(x1, y1);
			ctx.lineTo(x2, y2);
			ctx.lineTo(a1, b1);
			ctx.stroke();
			ctx.moveTo(x2, y2);
			ctx.lineTo(a2, b2);
			ctx.stroke();

			//console.log("x2: " + x2 + "   " + "y2: " + y2);
			//console.log("a1: " + a1 + "   " + "b1: " + b1);
			//console.log("a2: " + a2 + "   " + "b2: " + b2);
		}
    }



</script>
</body>
</html>