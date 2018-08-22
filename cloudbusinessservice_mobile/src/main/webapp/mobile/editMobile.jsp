<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>更换手机号</title>

    <link rel="stylesheet" href="${ctx}/mobile/css/mui.min.css" />
    <link rel="stylesheet" href="${ctx}/mobile/css/child.css">
</head>
<body class="phone-body">
<form class="mui-input-group phone-check">
    <div class="tip-text">
        原手机验证码：
    </div>
    <div class="mui-input-row phone-input">
        <input id="vcode" type="text" placeholder="请输入验证码" />
        <button type="button" class="phone-btn" id="vcode1">获取验证码</button>
    </div>
    <div class="tip-text">
        新手机号码：
    </div>
    <div class="mui-input-row phone-input">
        <input id="newMobile" type="text" class="mui-input-clear " placeholder="请输入新号码" />
    </div>
    <div class="tip-text">
        新手机验证码：
    </div>
    <div class="mui-input-row phone-input">
        <input id="newVcode" type="text" placeholder="请输入新手机验证码" />
        <button id="vcode2" type="button" class="phone-btn">获取验证码</button>
    </div>
</form>
<div class="logOut">
    <button id="submit" type="button" class="mui-btn mui-btn-warning">提交</button>
</div>
</body>
</html>
<script src="${ctx}/static/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/mobile/js/mui.min.js"></script>
<script>
    var pattenMobile = /((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}/;
    var auth = '${auth}';
    var json = {reader_idx:${reader_idx}};
    var vcodeTimeout = 60;
    $(function () {
        $("#vcode1").click(function () {
            var view = $("#vcode1");
            view.attr('disabled', "true");
            $.ajax({
                    url: '${ctx}/readerSetting/vcode',
                    type: "post",
                    data: {json: JSON.stringify(json), auth: auth},
                    timeout: 8000,
                    cache: false,
                    dataType: "json",
                    success: function (data, textStatus) {
                        if (data.state) {
                            setRepeat("#vcode1", vcodeTimeout);
                            showMessage('发送验证码成功');
                        } else {
                            view.removeAttr("disabled");
                            showMessage('发送验证码失败');
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        view.removeAttr("disabled");
                        mui.toast('网络超时，请稍后再试', {duration: 'short', type: 'div'});
                    }
                }
            );
        });

        $("#vcode2").click(function () {
            var view = $("#vcode2");
            var mobile = $('#newMobile').val();
            if (!pattenMobile.test(mobile)) {
                showMessage('请输入合法的手机号码');
                return;
            }
            json['mobile'] = mobile;
            view.attr('disabled', "true");
            $.ajax({
                    url: '${ctx}/readerSetting/newVcode',
                    type: "post",
                    data: {json: JSON.stringify(json), auth: auth},
                    timeout: 8000,
                    cache: false,
                    dataType: "json",
                    success: function (data, textStatus) {
                        if (data.state) {
                            setRepeat("#vcode2", vcodeTimeout);
                            showMessage('发送验证码成功');
                        } else {
                            view.removeAttr("disabled");
                            showMessage('发送验证码失败');
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        view.removeAttr("disabled");
                        mui.toast('网络超时，请稍后再试', {duration: 'short', type: 'div'});
                    }
                }
            );
        });

        $("#submit").click(function () {
            var vcode = $('#vcode').val();
            var newVcode = $('#newVcode').val();
            var mobile = $('#newMobile').val();
            if (!vcode) {
                showMessage('请输入验证码');
                return;
            }
            if (!newVcode) {
                showMessage('请输入新手机验证码');
                return;
            }
            if (!pattenMobile.test(mobile)) {
                showMessage('请输入合法的手机号码');
                return;
            }
            $("#submit").attr('disabled', "true");
            json['vcode'] = vcode;
            json['newVcode'] = newVcode;
            json['newMobile'] = mobile;
            $.ajax({
                    url: '${ctx}/readerSetting/setNewMobile',
                    type: "post",
                    data: {json: JSON.stringify(json), auth: auth},
                    timeout: 8000,
                    cache: false,
                    dataType: "json",
                    success: function (data, textStatus) {
                        $("#submit").removeAttr("disabled");
                        if (data.state) {
                            mui.alert('修改手机号码成功', '提示', '确定', function () {
                            }, 'div');
                        } else if (data.retMessage == 'original_vcode_error') {
                            showMessage('原手机验证码不正确');
                        } else if (data.retMessage == 'new_vcode_error') {
                            showMessage('新手机验证码不正确');
                        } else if (data.retMessage == 'mobile_repeat') {
                            showMessage('新手机已经绑定其他账号');
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        $("#submit").removeAttr("disabled");
                        mui.toast('网络超时，请稍后再试', {duration: 'short', type: 'div'});
                    }
                }
            );
        });
    });

    function showMessage(message) {
        mui.toast(message, {duration: 'short', type: 'div'});
    }

    function setRepeat(id, timeout) {
        $(id).html(timeout + '秒后重新获取');
        setTimeout(function () {
            if (timeout > 1) {
                setRepeat(id, timeout - 1);
            } else {
                $(id).html('获取验证码');
                $(id).removeAttr("disabled");
            }
        }, 1000);
    }
</script>
</body>
</html>
