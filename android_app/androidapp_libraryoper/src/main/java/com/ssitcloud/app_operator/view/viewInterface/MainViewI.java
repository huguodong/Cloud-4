package com.ssitcloud.app_operator.view.viewInterface;

import com.ssitcloud.app_operator.common.entity.ResultEntity;

/**
 * 创建日期：2017/4/25 19:29
 * @author shuangjunjie
 */

public interface MainViewI {

    /**
     * 登录成功
     */
    void loginSuccess(ResultEntity result);

    /**
     * 登录失败
     * @param message 提示信息
     */
    void loginFail(String message);

}
