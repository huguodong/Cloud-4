package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

/**
 * Created by LXP on 2017/3/7.
 */

public interface LoginViewI {
    /**
     * 获取对象
     * @return
     */
    ReaderInfoEntity getReaderInfo();

    /**
     * 登陆成功
     */
    void loginSuccess();

    /**
     * 登陆失败
     * @param message 提示信息
     */
    void loginFail(String message);

    void showWait();

    void hideWait();
}
