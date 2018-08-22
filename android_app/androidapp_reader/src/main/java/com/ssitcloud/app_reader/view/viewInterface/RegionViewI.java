package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.RegionEntity;

import java.util.List;

/**
 * Created by LXP on 2017/4/28.
 * 地区视图接口
 */

public interface RegionViewI {
    /**
     * 设置省
     */
    void setProvince(List<RegionEntity> provinces);

    /**
     * 设置城市
     */
    void setCity(List<RegionEntity> citys);

    /**
     * 设置区域
     */
    void setArea(List<RegionEntity> areas);

    void setSelectRegion(String regionCode);

    void setNetworkFail();
}
