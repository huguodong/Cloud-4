package com.ssitcloud.dbstatistics.service;

import com.ssitcloud.dbstatistics.entity.MainfieldExtendEntity;

import java.util.List;

/**
 * Created by LQW on 2017/9/1.
 */

public interface MainfieldExtendService {
    /**
     * 查询mainfield_extend所有数据
     * @param mainfieldExtendEntity
     * @return
     */
    List<MainfieldExtendEntity> selectByMfid(MainfieldExtendEntity mainfieldExtendEntity);
}
