package com.ssitcloud.dbstatistics.service.impl;

import com.ssitcloud.dbstatistics.dao.MainfieldExtendDao;
import com.ssitcloud.dbstatistics.entity.MainfieldExtendEntity;
import com.ssitcloud.dbstatistics.service.MainfieldExtendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by LQW on 2017/9/1.
 */
@Service
public class MainfieldExtendServiceImpl implements MainfieldExtendService {
    @Resource
    MainfieldExtendDao mainfieldExtendDao;

    @Override
    public List<MainfieldExtendEntity> selectByMfid(MainfieldExtendEntity mainfieldExtendEntity) {
        return mainfieldExtendDao.selectByMfid(mainfieldExtendEntity);
    }
}
