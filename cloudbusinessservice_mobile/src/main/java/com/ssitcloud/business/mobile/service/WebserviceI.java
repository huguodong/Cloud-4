package com.ssitcloud.business.mobile.service;

import com.ssitcloud.mobileserver.core.Session;
import com.ssitcloud.mobileserver.exception.InitSessionException;

import java.util.Map;

/**
 * Created by LXP on 2017/8/2.
 */

public interface WebserviceI {
    /**
     * 查询图书馆webservice设置
     * @param id 图书馆id
     * @return webservice设置
     */
    Map<String,Object> getLibWebserverSetting(Integer id);

    Session getWebserviceSession(Integer id) throws InitSessionException;

}
