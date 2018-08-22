package com.ssitcloud.app_operator.view.viewInterface;

public interface ForgetPwdViewI {
    int sendVcodeSuccess = 1;
    int sendVcodeUnknowUser=-3;
    int sendVcodeFail = -2;

    int changePwdSuccess = 1;
    int changePwdUnknowUser = -2;
    int vcodeError = -3;
    int vcodeOld = -4;

    int networkError = -1;

    /**
     * 发送验证码返回状态
     * @param state 状态码
     */
    void sendVcode(int state);

    void changePwd(int state);

    void setPasswordType(String type);

    /**
     * 显示进度
     */
    void showWait();

    /**
     * 隐藏进度
     */
    void hideWait();
}
