package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.AppOPACEntity;

import java.util.List;

/**
 * Created by LXP on 2017/4/6.
 * 设备界面接口
 */

public interface DeviceViewI {
    enum FAIL_CODE {unlogin,network_error,ubbain_card}
    enum DATA_STATE {full,partial}
    /**
     * 设置失败界面
     * @param code unlogin:未登录,network_error:网络连接失败,ubbain_card:请绑定读者卡
     */
    void setFailView(FAIL_CODE code);

    /**
     * 设置成功界面
     * @param data 获取到的数据
     * @param state 数据状态
     */
    void setSuccessView(List<AppOPACEntity> data,DATA_STATE state);

    /**
     * 设置搜索视图界面
     * @param data
     * @param state
     */
    void setSearchView(List<AppOPACEntity> data,DATA_STATE state);
}
