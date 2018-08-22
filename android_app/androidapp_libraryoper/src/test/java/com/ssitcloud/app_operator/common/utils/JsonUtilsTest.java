package com.ssitcloud.app_operator.common.utils;

import com.ssitcloud.app_operator.common.entity.ResultEntity;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by LXP on 2017/8/17.
 */
public class JsonUtilsTest {
    @Test
    public void fromJson() throws Exception {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setState(true);
        String source = JsonUtils.toJson(resultEntity);

        Map<String,Object> editMap = JsonUtils.fromJson(source,Map.class);
        editMap.put("qwer","dasd");
        String editJson = JsonUtils.toJson(editMap);

        System.out.println(source);
        System.out.println(editJson);

        String last = JsonUtils.toJson(JsonUtils.fromJson(editJson,resultEntity.getClass()));
        System.out.println(last);

        Assert.assertEquals(source,last);
    }

    @Test
    public void fromJsonForNull() throws Exception {
        System.out.println(JsonUtils.toJson(null));
        System.out.println(JsonUtils.fromJson(null,ResultEntity.class) instanceof ResultEntity);
    }
}