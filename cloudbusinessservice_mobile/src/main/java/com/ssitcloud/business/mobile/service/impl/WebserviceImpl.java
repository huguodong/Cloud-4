package com.ssitcloud.business.mobile.service.impl;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.WebserviceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobileserver.core.Session;
import com.ssitcloud.mobileserver.core.SessionFactory;
import com.ssitcloud.mobileserver.exception.InitSessionException;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2017/8/2.
 */
@Service
public class WebserviceImpl implements WebserviceI {
    private final String WEB_SERVICE_SETTING = "web_service_setting";

    @Resource(name = "requestURL")
    private RequestURLListEntity requestURLEntity;
    private static final String charset = "utf-8";

    @Override
    public Map<String, Object> getLibWebserverSetting(Integer id) {
        String address = requestURLEntity.getRequestURL(WEB_SERVICE_SETTING);
        Map<String, String> param = new HashMap<>();
        param.put("json","{\"lib_idx\":"+id+"}");
        String responceBody = HttpClientUtil.doPost(address, param, charset);
        try{
            ResultEntity data = JsonUtils.fromJson(responceBody,ResultEntity.class);
            if(data != null && data.getState()){
                return ((Map<String, Object>) data.getResult());
            }

            throw new Exception("db层无法返回图书馆web_service设置,rsponce->"+responceBody);
        }catch (Exception e){
            LogUtils.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Session getWebserviceSession(Integer id) throws InitSessionException {
        Map<String, Object> libWebserverSetting = getLibWebserverSetting(id);
        if(libWebserverSetting != null) {
            String type = String.valueOf(libWebserverSetting.get("@lib_sys_type"));
            return SessionFactory.getSession(type,libWebserverSetting);
        }

        throw new InitSessionException("没有找到支持的图书馆类型,图书馆设置->"+libWebserverSetting);
    }

}
