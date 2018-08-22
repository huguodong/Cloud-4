package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.LibraryEntity;

import java.util.List;

/**
 * Created by LXP on 2017/3/10.
 *
 */

public interface LibraryListViewI {
    /**
     * 设置一个视图
     * @param viewData
     */
    void setView(List<LibraryEntity> viewData);


    /**
     * 设置信息视图
     * @param code 错误码 -1:未登录，-2网络连接失败，-3：刷新后重试
     */
    void setMessageView(int code);
}
