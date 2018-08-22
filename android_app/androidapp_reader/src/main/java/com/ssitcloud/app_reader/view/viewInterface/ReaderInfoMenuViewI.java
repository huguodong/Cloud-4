package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.ReaderInfoMenuViewEntity;

import java.util.Map;

/**
 * Created by LXP on 2017/3/17.
 * 读者信息菜单界面
 */

public interface ReaderInfoMenuViewI {
    /**
     * 是否已经设置了视图
     * @return
     */
    boolean alreadySetMenuView();
    /**
     * 设置视图
     * @param data
     */
    void setView(ReaderInfoMenuViewEntity data);

    /**
     * 其他视图
     * @param data 数据
     * @param code 视图代码 -1未登陆
     */
    void otherView(Map<String,Object> data,int code);
}
