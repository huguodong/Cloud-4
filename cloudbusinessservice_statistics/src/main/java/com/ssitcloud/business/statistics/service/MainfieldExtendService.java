package com.ssitcloud.business.statistics.service;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * Created by LQW on 2017/9/1.
 */

public interface MainfieldExtendService {
    /**
     * 查询mainfield_extend所有数据
     */
    ResultEntity selectByMfid(String req);
}
