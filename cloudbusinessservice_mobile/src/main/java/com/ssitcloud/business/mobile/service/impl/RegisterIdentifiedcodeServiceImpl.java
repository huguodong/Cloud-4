package com.ssitcloud.business.mobile.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.RegisterIdentifiedcodeService;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.RegisterIdentifiedcodeEntity;

@Service
public class RegisterIdentifiedcodeServiceImpl implements RegisterIdentifiedcodeService {
    private final String charset = "utf-8";
    private final String URL_ADD_CODE = "registerAddCode";
    private final String URL_SELECT_CODE = "registerSelectCode";
    private final String URL_DELETE_CODE = "registerDeleteCode";
    @Resource(name = "requestURL")
    protected RequestURLListEntity requestURL;

    @Override
    public boolean insert(RegisterIdentifiedcodeEntity entity) {
        String url = requestURL.getRequestURL(URL_ADD_CODE);
        Map<String, String> map = new HashMap<>(2, 1.0f);
        map.put("json", JsonUtils.toJson(entity));
        String doPost = HttpClientUtil.doPost(url, map, charset);
        try {
            ResultEntity resultEntity = JsonUtils.fromJson(doPost, ResultEntity.class);
            return resultEntity.getState();
        } catch (Exception e) {
            LogUtils.error(getClass() + " 插入验证码异常", e);
        }
        return false;
    }

    @Override
    public RegisterIdentifiedcodeEntity selectCode(String email, long timeout) {
        String url = requestURL.getRequestURL(URL_SELECT_CODE);
        Map<String, String> map = new HashMap<>(2, 1.0f);
        map.put("json", "{\"email\":\"" + email + "\"}");
        String doPost = HttpClientUtil.doPost(url, map, charset);
        try {
            ResultEntity resultEntity = JsonUtils.fromJson(doPost, ResultEntity.class);
            if (resultEntity.getState()) {
                Map<String, Object> m = (Map<String, Object>) resultEntity.getResult();
                RegisterIdentifiedcodeEntity selectCode = JsonUtils.fromJson(JsonUtils.toJson(m), RegisterIdentifiedcodeEntity.class);
                if (selectCode != null
                        && System.currentTimeMillis() < selectCode.getCreatetime().getTime() + timeout) {
                    return selectCode;
                }
            }
        } catch (Exception e) {
            LogUtils.error(getClass() + " 查询验证码异常", e);
        }

        return null;
    }

    @Async
    @Override
    public void deleteByPkAsync(String email) {
        String url = requestURL.getRequestURL(URL_DELETE_CODE);
        Map<String, String> map = new HashMap<>(2, 1.0f);
        map.put("json", "{\"email\":\"" + email + "\"}");
        String doPost = HttpClientUtil.doPost(url, map, charset);
        LogUtils.info(getClass() + " 删除注册验证码，服务器返回" + doPost);
    }

}
