package com.ssitcloud.business.devmgmt.service;


import com.ssitcloud.common.entity.ResultEntity;

public interface RegionService {

    /**
     * 根据馆IDX查询所有设备的地区分布
     * 2017-08-24 lqw
     * @param
     */
    ResultEntity selRegionsByLibidx(String req);

    /**
     * 根据馆idx查询子馆
     * @param req
     * @return
     */
    ResultEntity selectRelLibsByid(String req);
}
