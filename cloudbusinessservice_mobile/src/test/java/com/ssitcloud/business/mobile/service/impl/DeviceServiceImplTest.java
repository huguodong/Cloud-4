package com.ssitcloud.business.mobile.service.impl;

import com.ssitcloud.business.mobile.service.DeviceServiceI;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by LXP on 2017/8/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/springMVC.xml")
public class DeviceServiceImplTest {
    @Autowired
    private DeviceServiceI deviceService;

    @Test
    public void testFindDevice(){
        DeviceEntity param = new DeviceEntity();
        param.setDevice_idx(394);
        List<Map<String, Object>> device = deviceService.findDevice(param);
        System.out.println(device);
    }
}