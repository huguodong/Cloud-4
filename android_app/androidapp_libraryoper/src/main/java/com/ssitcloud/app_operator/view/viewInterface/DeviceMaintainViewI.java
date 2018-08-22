package com.ssitcloud.app_operator.view.viewInterface;

/**
 * 创建日期：2017/4/15 11:29
 * @author shuangjunjie
 */

public interface DeviceMaintainViewI {

    /**
     * 发送指令成功
     */
    void sendOrderSuccess(String message);

    /**
     * 发送指令失败
     */
    void sendOrderFail(String message);
}
