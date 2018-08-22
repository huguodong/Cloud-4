<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<%-- <%@include file="common/global.jsf" %>  --%>

<script src="${pageContext.request.contextPath}/static/js/wz_jsgraphics.js" language="javascript" type="text/javascript"></script>
<script type="text/javascript">
	var   OriginImage=new   Image();
	var Factor = 1;
	var img_x = 0;
	var img_y = 0;
	var org_Factor = 1;
	function DrawLine(line_factor)
	{
		jg.clear();
		var nos = parseInt(document.getElementById("route_noo").innerHTML);
		if( nos <= 0)
			return;
		var org_x = parseInt(parseInt(document.getElementById("route_xx1").innerHTML) * line_factor);
		var org_y = parseInt(parseInt(document.getElementById("route_yy1").innerHTML) * line_factor);
		jg.setFont("verdana,geneva,sans-serif", "18px", Font.BOLD);
		//jg.drawString("入口处", 230+54 + org_x, 54 + org_y);
		jg.drawImage("${pageContext.request.contextPath}/static/images/man.png", 230+40 + org_x,54 + org_y, 30, 30, 0);
		var dest_x, dest_y;
		var i = 2;
		while( i <= nos)
		{
			dest_x = parseInt(parseInt(document.getElementById("route_xx" + String(i)).innerHTML) * line_factor);
			dest_y = parseInt(parseInt(document.getElementById("route_yy" + String(i)).innerHTML) * line_factor);
			jg.drawLine(230+54 + org_x, 54+ + org_y, 230+54 + dest_x, 54 + dest_y);
			org_x = dest_x;
			org_y = dest_y;
			 i++;
		}
	jg.paint();
	}
	
	
	function   zoom(imgid,flag)
	{
		displayw=document.all(imgid).width;
		displayh=document.all(imgid).height;
		realw=GetImageWidth(imgid);
		realh=GetImageHeight(imgid);
		if(flag<0){
			document.all(imgid).width=displayw*1.2;
			document.all(imgid).height=displayh*1.2;
			Factor = Factor * 1.2;
			DrawLine(Factor);
		}
		if(flag==0){
			if(img_x == 0)
			{
				if((1600 / document.all(imgid).width) > (1200 / document.all(imgid).height))
				Factor = 1200 / document.all(imgid).height;
				else
				Factor = 1600 / document.all(imgid).width;
				displayw=document.all(imgid).width;
				displayh=document.all(imgid).height;
				document.all(imgid).width=displayw*Factor;
				document.all(imgid).height=displayh*Factor;
				img_x = displayw*Factor;
				img_y = displayh*Factor;
				org_Factor = Factor;
		  	}
			else
			{
                document.all(imgid).width = img_x;
                document.all(imgid).height = img_y;
                Factor = org_Factor;
	      	}
		  DrawLine(Factor);
	   }
	}
		function   GetImageWidth(imgid)
        {
		   var   obImage;
		   obImage=document.all(imgid);
		   if(OriginImage.src!=obImage.src)
		      OriginImage.src=obImage.src;
		   return   OriginImage.width;
		}
		function   GetImageHeight(imgid)
		{
		   var   obImage;
		   obImage=document.all(imgid);
		   if(OriginImage.src!=obImage.src)
		      OriginImage.src=obImage.src;
		   return   OriginImage.height;
		}
	</script>
<TITLE>架位导航</TITLE>
</head>
<body onload = "DRAW()"   style="padding-left:60px">
	<table>
		<tr>
			<td></td>
			<td align="left">
				<input type=image src=${pageContext.request.contextPath}/static/images/enlarge.jpg onclick="javascript:zoom('mapimg','-1')" width="80" height="35" style="cursor:hand;">
				<input type=image src=${pageContext.request.contextPath}/static/images/revert.jpg onclick="javascript:zoom('mapimg','0')" width="80" height="35" style="cursor:hand;">
				<img src=${pageContext.request.contextPath}/static/images/shelf.jpg width="80" height="35">
				<font style="margin-left: 10px;font-size: 30px">${locations[0]}馆${locations[1]}区${locations[2]}列${locations[3]}列名${locations[4]}左/右${locations[5]}架</font>
			</td>
		</tr>
	</table>
	<div id="templayer" style="position:absolute; z-index:1; visibility:hidden">
		<table>
			<c:forEach items="${points }" var="point" varStatus="s">
				<tr id="route_x${s.index+1}"><td id="route_xx${s.index+1}">${point[0]}<td></tr>
				<tr id="route_y${s.index+1}"><td id="route_yy${s.index+1}">${point[1]}<td></tr>
			</c:forEach>
			<tr id="route_no"><td id="route_noo">${fn:length(points)}<td></tr>
		</table>
	</div>
	<div id="test">
		<div style="position:absolute; left:54px; top:54px; padding-left:230px;">
			<img id="mapimg" src="/file/${navigationInfoEty.floorimage_url}" alt="" border="0" usemap="#Map"/>
		</div>
	</div>
	<div style="position:absolute; left:0px; top:70px; padding-left:75px">
		<img src="/file/${navigationInfoEty.shelfimage_url}" alt="" width = 200 height = 400 border="0" style="position:absolute"/> 
		<canvas width="200" height="400"  style="border: solid 0px white;position:absolute;" id="shelf">您的浏览器不支持 canvas 标签</canvas>
	</div>
<script type="text/javascript">
jg = new jsGraphics("test");
function DRAW()
{
	jg.setColor("#ff0000");
	jg.setStroke(2);
	zoom('mapimg','0');
};

var i=0,add=1;
FadeText();
function FadeText()
{
if( add==1 )
{
	i++;
	document.fgColor="#FF0000";
	if( i==15 ) add=0;
}
else
{
	i--;
	document.fgColor="#000000";
	if( i==0 ) add=1;
}
setTimeout("FadeText()",15);
};

//给图书书架描色
shelf();
function shelf(){
	var c=document.getElementById("shelf");
    var ctx=c.getContext("2d");
    //定义书架图片上下边框宽度，层次间宽度
    var downH = 28;
    var topH = 10;
    var midH = 3;
    var shadowH = (400-topH-downH)/${navigationInfoEty.layercount}; //总高度减去上下宽度再除以总层数
    var shadowS = (${navigationInfoEty.shelf_layer} -1) * shadowH + topH + midH; //起始位置
	ctx.globalAlpha=0.5;
	if( add== 1) {ctx.fillStyle="#99CCFF";}
	else{ctx.fillStyle="#FFFFFF";}
	ctx.clearRect(0,shadowS,200,shadowH);
	ctx.fillRect(0,shadowS,200,shadowH);
	setTimeout("shelf()",15);
};
</script>
</body>
</html>