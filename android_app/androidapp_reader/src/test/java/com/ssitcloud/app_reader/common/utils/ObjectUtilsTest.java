package com.ssitcloud.app_reader.common.utils;

import com.ssitcloud.app_reader.entity.AppSettingEntity;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by LXP on 2017/3/20.
 */
public class ObjectUtilsTest {
    @Test
    public void convertMap() throws Exception {
        Map<String,Object> m = new HashMap<>();
        m.put("user_type","2");
        AppSettingEntity appSettingEntity = ObjectUtils.convertMap(m,AppSettingEntity.class);
        System.out.println(appSettingEntity.getUser_type());
        System.out.println(ObjectUtils.objectToMap(appSettingEntity));
    }

}