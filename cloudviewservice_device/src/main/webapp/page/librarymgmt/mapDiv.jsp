<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
  	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=HLY8UYq0rnxe47hg9oNzmV2F7yk1wzai"></script>
<style type="text/css">
.mapDiv{
	position:absolutely;
	overflow: hidden;
	display:none;
	height:600px;
	width:100%;
}
.locmap{
	height:80%;
	width:100%;
}
</style>
</head>
<body>
<div class="mapDiv">
	<div>
		<div>
			<div>
					<b>经度：</b><input style="color:#ff9933;font-weight:bold;width:150px" type="text" id="device_lng" name="device_lng" readonly="readonly">
					<b>纬度：</b><input style="color:#ff9933;font-weight:bold;width:150px" type="text" id="device_lat" name="device_lat" readonly="readonly">		
			</div>
		</div>
	</div>
	<!--make a div for google map-->
	<div id="locmap" class="locmap">地图加载中....</div>
	<div style="text-align:center;margin-top:20px">
		<input id="savePosition" style="height:28;width:50px;background-color:#00a2e9;border:1px solid" type="button" value="保存">
 	</div>
</div>
</body>
</html>
<script type="text/javascript">
	//地图初始化
	var bm = new BMap.Map("locmap");
	bm.centerAndZoom("兰州", 6);
	bm.enableScrollWheelZoom();
	//bm.centerAndZoom(new BMap.Point(116.378688937,39.9076296510), 15);
	bm.addEventListener("click",main);
	function main(e) {
		document.getElementById("device_lng").value = e.point.lng;
		document.getElementById("device_lat").value = e.point.lat;
		if(document.getElementById("device_lng").value != "" && document.getElementById("device_lat").value != ""){
			bm.clearOverlays();
			//bm.removeEventListener("click", main); 
			var new_point = new BMap.Point(document.getElementById("device_lng").value,document.getElementById("device_lat").value);
			var label = new BMap.Label("选定设备位置",{offset:new BMap.Size(20,-10)});
			var marker = new BMap.Marker(new_point);  // 创建标注
			marker.setLabel(label);
			bm.addOverlay(marker);              // 将标注添加到地图中
			bm.panTo(new_point);
		}
	}
	$("#savePosition").on("click",function(){
		$("#jingdu").val($("#device_lng").val());
		$("#weidu").val($("#device_lat").val());
		
		//console.info("4444",libPosLayer);
		layer.alert("位置信息保存成功！",{icon:6});
		layer.close(libPosLayer);
		//$(".mapDiv").css("display","none");
		//$(".layui-layer-content").css("display","none");
	});
</script>
