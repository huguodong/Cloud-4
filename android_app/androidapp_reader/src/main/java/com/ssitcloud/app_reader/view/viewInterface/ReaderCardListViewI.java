package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;

import java.util.List;

/**
 * Created by LXP on 2017/3/9.
 */

public interface ReaderCardListViewI {
    /**
     * 设置一个视图
     * @param viewData
     */
    void setView(List<ReaderCardDbEntity> viewData);

    /**
     * 设置网络错误视图
     */
    void setNetworkErrorView();

    /**
     * 设置未登陆
     */
    void setUnlogin();

    /**
     * 设置信息视图
     * @param code 错误码 -1:未登录，-2网络连接失败，-3：刷新后重试
     */
    void setMessageView(int code);

    void deleteView(ReaderCardDbEntity viewTag);

    void setPreferCard(ReaderCardDbEntity viewTag);
}
