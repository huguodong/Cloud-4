package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.entity.DeviceRegionEntity;
import com.ssitcloud.app_reader.entity.RegionEntity;

import java.net.SocketException;
import java.util.List;

/**
 * Created by LXP on 2017/4/28.
 *
 */

public interface RegionBizI {
    /**
     * 获取国家地区代码下的地区
     * @param nationCode 国家地区代码
     * @return 地区
     * @throws SocketException 网络连接异常
     */
    List<RegionEntity> regionList(String nationCode) throws SocketException;

    /**
     * 获取图书下属设备的地区信息
     * @param lib_idx 图书馆idx
     * @return 地区
     * @throws SocketException 网络连接异常
     */
    List<DeviceRegionEntity> deviceRegionList(Integer lib_idx) throws SocketException;
}
