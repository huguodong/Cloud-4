package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.entity.RegionEntity;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/4/28 11:36
 * @author shuangjunjie
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
     * 获取图书馆所在区域
     * @param list 图书馆idxs
     * @return 地区
     * @throws SocketException 网络连接异常
     */
    List<RegionEntity> regionListForNormal(List<Integer> list) throws SocketException;
}
