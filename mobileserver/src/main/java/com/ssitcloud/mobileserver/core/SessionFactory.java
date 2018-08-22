package com.ssitcloud.mobileserver.core;

import com.ssitcloud.mobileserver.exception.InitSessionException;

import java.util.Map;

/**
 * Created by LXP on 2017/8/2.
 */

public final class SessionFactory {
    public enum SysType {ILASIII_WEBSERVER}

    private SessionFactory() {
    }

    public static Session getSession(String type,Map<String,Object> param) throws InitSessionException{
        if("ilasIII_webservice".equals(type)){
            return getSession(SysType.ILASIII_WEBSERVER,param);
        }
        throw new InitSessionException("not support system type->"+type);
    }
    /**
     * 获取一个session
     * @param type 图书馆系统类型
     * @param param 参数
     * @return session
     * @throws InitSessionException 不能初始化session抛出
     */
    public static Session getSession(SysType type, Map<String,Object> param) throws InitSessionException {
        if(type == SysType.ILASIII_WEBSERVER){
            try {
                String url = (String) param.get("url");
                String apiName = (String) param.get("apiName");
                String charset = (String) param.get("charset");
                if(url == null || apiName == null || charset == null){
                    throw new IllegalArgumentException("url or apiName or charset is null");
                }
                String connTimeout = ((String) param.get("connTimeout"));
                String readTimeout = ((String) param.get("readTimeout"));
                if(connTimeout != null && readTimeout != null){
                    return new IlasIIIWebServiceSession(url, apiName, charset,Integer.valueOf(connTimeout),Integer.valueOf(readTimeout));
                }else {
                    return new IlasIIIWebServiceSession(url, apiName, charset);
                }
            } catch (Exception e) {
                throw new InitSessionException(e.getMessage());
            }
        }

        throw new InitSessionException("not support system type");
    }
}
