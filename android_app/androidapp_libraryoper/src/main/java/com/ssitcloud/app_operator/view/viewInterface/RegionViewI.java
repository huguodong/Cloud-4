package com.ssitcloud.app_operator.view.viewInterface;

import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.entity.RegionEntity;

import java.util.List;

/**
 * 创建日期：2017/4/28 14:06
 * @author shuangjunjie
 */

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
