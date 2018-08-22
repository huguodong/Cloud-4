package com.ssitcloud.app_operator.view.viewInterface;

/**
 * 创建日期：2017/3/31 10:07
 * @author shuangjunjie
 */

public interface FeedBackViewI {

    /**
     * 发送意见反馈成功
     * @param message
     */
    void sendFeedBackSuccess(String message);

    /**
     * 发送意见反馈失败
     * @param message
     */
    void sendFeedBackFail(String message);
}
