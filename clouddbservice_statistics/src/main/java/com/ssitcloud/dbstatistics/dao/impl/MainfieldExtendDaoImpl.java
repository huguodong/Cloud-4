package com.ssitcloud.dbstatistics.dao.impl;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.MainfieldExtendDao;
import com.ssitcloud.dbstatistics.entity.MainfieldExtendEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LQW on 2017/9/1.
 */
@Repository
public class MainfieldExtendDaoImpl extends CommonDaoImpl implements MainfieldExtendDao {
    @Override
    public List<MainfieldExtendEntity> selectByMfid(MainfieldExtendEntity mainfieldExtendEntity) {
        return this.sqlSessionTemplate.selectList("mfextend.selectByMfid",mainfieldExtendEntity);
    }
}
