package com.ssitcloud.business.mobile.service.impl;

import com.ssitcloud.business.mobile.service.MetadataOpercmdCacheServiceI;
import com.ssitcloud.devmgmt.entity.MetadataOpercmdEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by LXP on 2017/9/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/springMVC.xml")
public class MetadataOpercmdCacheServiceImplTest {
    @Autowired
    private MetadataOpercmdCacheServiceI mocs;

    @Test
    public void getSourceData() throws Exception {
        MetadataOpercmdEntity t1 = mocs.get("0101010101");
        assert t1 != null && t1.getOpercmd_desc().equals("新增用户") : "t1 error";

        MetadataOpercmdEntity t2 = mocs.get("0101010404");
        assert t2 != null && t2.getOpercmd_desc().equals("查询权限组") : "t2 error";

        MetadataOpercmdEntity t3 = mocs.get("0102010501");
        assert t3 != null && t3.getOpercmd_desc().equals("新增维护卡") : "t3 error";

        MetadataOpercmdEntity t4 = mocs.get("0102020301");
        assert t4 != null && t4.getOpercmd_desc().equals("新增设备组") : "t4 error";

    }

}