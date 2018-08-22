package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.entity.FeedbackEntity;

import java.net.SocketException;

/**
 * Created by LXP on 2017/4/19.
 * 反馈业务
 */

public interface FeedbackBizI {
    /**
     * 反馈意见
     * @param feedbackEntity 反馈实体
     */
    boolean feedback(FeedbackEntity feedbackEntity)throws SocketException,AuthException;
}
