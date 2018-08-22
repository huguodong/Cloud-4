package com.ssitcloud.app_operator.view.viewInterface;

/**
 * 创建日期：2017/3/21 20:11
 * @author shuangjunjie
 */

public interface SetNewPwdViewI {

    /**
     * 设置新密码成功
     * @param message
     */
    void setNewPwdSuccess(String message);

    /**
     * 设置新密码失败
     * @param message
     */
    void setNewPwdFail(String message);

    /**
     * 显示加载进度
     */
    void showWait();

    /**
     * 隐藏加载进度
     */
    void hideWait();
}
