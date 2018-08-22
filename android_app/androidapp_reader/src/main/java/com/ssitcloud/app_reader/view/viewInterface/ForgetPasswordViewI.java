package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

/**
 * Created by LXP on 2017/3/27.
 * 找回密码视图接口
 */

public interface ForgetPasswordViewI {
    void showWait();
    void hideWait();

    /**
     * 获取reader info
     * @return
     */
    ReaderInfoEntity getInfo();

    /**
     * 获取验证码
     * @return 验证码
     */
    String getVocde();

    void setSuccess();

    /**
     *
     * @param code -2网络连接失败
     */
    void setMessage(int code);
}
