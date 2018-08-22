package com.ssitcloud.dbauth.service.impl;

import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.service.DynamicSqlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lqw on 2017/8/29.
 */
@Service
public class DynamicSqlServiceImpl implements DynamicSqlService {
    @Resource
    private CommonDao commonDao;

    @Override
    public List<Map<String, Object>> selectBySql(String sql) {
        List<Map<String,Object>> recordlist = commonDao.selectBySql(sql);
        return recordlist;
    }
}
