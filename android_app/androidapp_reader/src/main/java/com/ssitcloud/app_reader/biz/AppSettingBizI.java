package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.entity.AppSettingEntity;

import java.net.SocketException;
import java.util.List;

/**
 * Created by LXP on 2017/3/20.
 * Appsetting业务
 */

public interface AppSettingBizI {
    /**
     * 从服务器获取appSetting并且更新本地数据库
     * @throws SocketException 远程服务异常
     * @return
     */
    List<AppSettingEntity> obtainAppSettingByService(Integer lib_idx) throws SocketException;

    /**
     * 从本地数据库获取appSetting
     * @return
     */
    List<AppSettingEntity> obtainAppSetting(Integer lib_idx);
}
