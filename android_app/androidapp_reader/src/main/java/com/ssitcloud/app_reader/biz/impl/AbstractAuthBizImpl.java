package com.ssitcloud.app_reader.biz.impl;

import android.util.Log;

import com.ssitcloud.app_reader.biz.ConfigBizI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by LXP on 2017/3/29.
 * 认证业务，需要进行认证的业务可继承该类
 */

public abstract class AbstractAuthBizImpl {
    private final String TAG = ""+getClass();

    protected final String charset = "utf-8";

    abstract protected ConfigBizI getConfigBiz();

    protected void addAuthMessage(Map map){
        String authCode = getConfigBiz().getAuthCode();
        try {
            map.put("reader_auth", URLEncoder.encode(authCode,charset));
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG,"URLEncoder error,Encoding is not supported.");
        }
    }
}
