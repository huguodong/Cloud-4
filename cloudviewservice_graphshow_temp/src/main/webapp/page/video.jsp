<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
    <meta charset="utf-8" />
	<title>大屏展示</title>
</head>
<style type="text/css">
	video::-webkit-media-controls-enclosure {
		/*禁用播放器控制栏的样式*/
		display: none !important;
	}
</style>
<body style="background-color: #000">
<video autoplay="autoplay" width="100%" height="100%" loop="loop" id="video">
	<source src="${pageContext.request.contextPath}/video/${videoName}" type="video/mp4">
</video>
</body>
<script type="text/javascript">
	document.getElementById('video').play();
    setTimeout('location.href="${pageContext.request.contextPath}/index?videoCode=${videoCode}"', 120000);
</script>
</html>