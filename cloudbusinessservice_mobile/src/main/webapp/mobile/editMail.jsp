<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>修改邮箱</title>

    <link rel="stylesheet" href="${ctx}/mobile/css/mui.min.css"/>
    <link rel="stylesheet" href="${ctx}/mobile/css/child.css">
</head>
<body class="child">
<div class="password-body">
    <form class="mui-input-group">
        <div class="mui-input-row">
            <label>邮箱</label>
            <input id="mail" type="text" class="mui-input-clear" placeholder="请输入邮箱" value="${mail}" />
        </div>
        <input type="text" style="display: none" />
    </form>
    <div class="logOut">
        <button id="submit" type="button" class="mui-btn mui-btn-warning">提交</button>
    </div>
</div>
<script src="${ctx}/static/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/mobile/js/mui.min.js"></script>
<script type="text/javascript">
    var auth = '${auth}';
    var json = {reader_idx:${reader_idx}};
    var pattenMail = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
    $(function () {
        mui('body').on('tap', 'a', function () {
            window.top.location.href = this.href;
        });
        $('#submit').click(function () {
            var v = $('#mail').val();
            var isMail = pattenMail.test(v);
            if (!isMail) {
                mui.toast('请输入正确的邮箱', {duration: 'short', type: 'div'});
                return;
            }

            var view = $('#submit');
            view.attr('disabled', "true");

            json['mail'] = v;
            $.ajax({
                    url: '${ctx}/readerSetting/setMail',
                    type: "post",
                    data: {json: JSON.stringify(json), auth: auth},
                    timeout: 8000,
                    cache: false,
                    dataType: "json",
                    success: function (data, textStatus) {
                        view.removeAttr("disabled");
                        if (data.state) {
                            mui.alert('修改邮箱成功', '提示', '确定', function () {
                            }, 'div');
                        } else {
                            var message = '';
                            if (data.retMessage == 'mail_repeat') {
                                message = '邮箱重复了';
                            }
                            mui.toast('修改失败，' + data.message, {duration: 'short', type: 'div'});
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        view.removeAttr("disabled");
                        mui.toast('网络超时，请稍后再试', {duration: 'short', type: 'div'});
                    }
                }
            );
        });
    });
</script>
</body>
</html>
