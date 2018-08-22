package com.ssitcloud.app_operator.view.viewInterface;

/**
 * 创建日期：2017/3/22 17:59
 * @author shuangjunjie
 */

public interface ChangePwdViewI {


    /**
     * 修改密码成功
     * @param message
     */
    void changePwdSuccess(String message);

    /**
     * 修改密码失败
     * @param message
     */
    void changePwdFail(String message);

    /**
     * 显示进度条
     */
    void showWait();

    /**
     * 隐藏进度条
     */
    void hideWait();

}
