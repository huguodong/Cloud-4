package com.ssitcloud.app_reader.view.viewInterface;

import java.util.Map;

/**
 * Created by LXP on 2017/3/27.
 * 修改密码视图接口
 */

public interface ChangePasswordViewI {
    void success();

    /**
     *
     * @param code -1 未登陆 -2 网络连接失败 0 原密码错误
     */
    void fail(int code);

    void showWait();

    void hideWait();

    String getOldPwd();

    String getNewPwd();
}
