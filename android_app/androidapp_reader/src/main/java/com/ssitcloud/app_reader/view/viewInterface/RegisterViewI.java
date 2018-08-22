package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

/**
 * Created by LXP on 2017/3/6.
 */

public interface RegisterViewI {
    /**
     * 获取对象
     */
    ReaderInfoEntity getReaderInfo();


    /**
     * 获取输入的手机号
     */
    String getMobile();

    /**
     * 获取验证码
     */
    String getVcode();
    /**
     * 注册成功
     */
    void registerSuccess();

    /**
     * 注册失败
     * @param failMessage 失败消息
     */
    void registerFail(String failMessage);

    /**
     * 验证码状态
     * @param state 1发送成功，2发送失败，-1网络连接失败
     */
    void sendVcodeInfo(int state);

    void showWait();

    void hideWait();
}
