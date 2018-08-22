package com.ssitcloud.app_operator.view.viewInterface;

import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.entity.OperatorEntity;

/**
 * 创建日期：2017/3/17 14:05
 *
 * @author shuangjunjie
 */

public interface LoginViewI {

    /**
     * 登录成功
     */
    void loginSuccess(ResultEntity result);

    /**
     * 登录失败
     *
     * @param message 提示信息
     */
    void loginFail(String message);

    void showWait();

    void hideWait();
}
