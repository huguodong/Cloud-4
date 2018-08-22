package com.ssitcloud.app_reader.biz.impl;


import android.content.res.Resources;
import android.util.Log;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.RegisterBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2017/3/6.
 *
 */

public class RegisterBizImpl implements RegisterBizI {
    private final String TAG = "RegisterBizImpl";
    private String charset = "utf-8";
    private Resources resources;

    public RegisterBizImpl(Resources resources) {
        this.resources = resources;
    }

    @Override
    public boolean seneRegisterMailCode(String mail) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.register_mail_code_url);
        Map<String, String> map = new HashMap<>(3, 1.0f);
        map.put("json", "{\"mail\":\"" + mail + "\"}");
        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if (hr.getState() == 200) {
            String json = hr.getBody();
            try{
                ResultEntity resultEntity = JsonUtils.fromJson(json,ResultEntity.class);
                return resultEntity.getState();
            }catch (Exception e){
                throw new SocketException("服务器没有返回预期数据");
            }
        }
        throw new SocketException();
    }

    @Override
    public boolean seneRegisterMobileCode(String mobile) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.register_mobile_code_url);
        Map<String, String> map = new HashMap<>(3, 1.0f);
        map.put("json", "{\"mobile\":\"" + mobile + "\"}");
        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if (hr.getState() == 200) {
            String json = hr.getBody();
            try{
                ResultEntity resultEntity = JsonUtils.fromJson(json,ResultEntity.class);
                return resultEntity.getState();
            }catch (Exception e){
                throw new SocketException("服务器没有返回预期数据");
            }
        }
        throw new SocketException();
    }

    @Override
    public String register(ReaderInfoEntity readerInfo, String vcode) {
        String url = RequestUrlUtil.getUrl(resources, R.string.register_url);
        Map<String, String> map = new HashMap<>(3, 1.0f);
        map.put("json", JsonUtils.toJson(readerInfo));
        map.put("vcode", vcode);
        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if (hr.getState() != 200) {
            return "-500";
        }
        try {
            ResultEntity resultEntity = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
            if (resultEntity.getState()) {
                return "success";
            } else {
                return resultEntity.getRetMessage();
            }
        } catch (Exception e) {
            Log.e(TAG, "to json exception", e);
            return null;
        }
    }
}
