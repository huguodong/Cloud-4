package com.ssitcloud.app_reader.view.viewInterface;

/**
 * Created by LXP on 2017/3/10.
 * 绑卡视图
 */

public interface BindCardViewI {

    void bindSuccess();

    /**
     *
     * @param code 错误码 -1:未登录，-2网络连接失败,-3卡无效,
     *             -4卡密码错误，-5插入卡失败（卡已被绑定） -6图书馆暂时无法提供服务
     */
    void bindFail(int code);
}
