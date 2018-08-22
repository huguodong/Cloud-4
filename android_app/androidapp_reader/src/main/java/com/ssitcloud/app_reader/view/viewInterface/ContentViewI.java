package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.AppSettingEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/3/18.
 * 主页视图接口
 */

public interface ContentViewI {
    /**
     * 是否已经设置了视图
     * @return
     */
    boolean alreadySetContentView();

    /**
     * 设置视图
     * @param data
     */
    void setView(Map<String,Object> data);

    /**
     * 设置功能视图
     * @param contentFcution
     */
    void setFouctionView(List<AppSettingEntity> contentFcution);

    /**
     * 设置其他视图
     * @param data
     * @param code
     */
    void otherView(Map<String, Object> data, int code);

    /**
     * 显示等待界面
     */
    void showWait();

    /**
     * 关闭等待界面
     */
    void hideWait();
}
