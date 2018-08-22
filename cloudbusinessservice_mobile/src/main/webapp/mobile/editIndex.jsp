<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>修改手机号 / 身份证号 / 邮箱</title>
    <link href="${ctx}/mobile/css/mui.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx}/mobile/css/child.css">
</head>
<body class="child">
<div class="setting-body">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a href="${ctx}/readerSetting/mobile?auth=${auth}" class="mui-navigate-right">更改手机号</a>
        </li>
        <li class="mui-table-view-cell">
            <a href="${ctx}/readerSetting/idcard?auth=${auth}" class="mui-navigate-right">更改身份证号</a>
        </li>
        <li class="mui-table-view-cell">
            <a href="${ctx}/readerSetting/mail?auth=${auth}" class="mui-navigate-right">更改邮箱</a>
        </li>
    </ul>
</div>
<script src="${ctx}/mobile/js/mui.min.js"></script>
<script type="text/javascript">
    $(function () {
        mui.init();
        mui('body').on('tap', 'a', function () {
            window.top.location.href = this.href;
        });
    });
</script>
</body>
</html>