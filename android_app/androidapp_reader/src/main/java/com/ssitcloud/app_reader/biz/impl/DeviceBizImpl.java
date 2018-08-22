package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.DeviceBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.ObjectUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.entity.AppOPACEntity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/6.
 * 设备服务实现类
 */

public class DeviceBizImpl implements DeviceBizI {
    private final String charset = "utf-8";

    private Context mcontext;

    public DeviceBizImpl(Context context){
        mcontext = context.getApplicationContext();
    }

    @Override
    public List<AppOPACEntity> findOpacDevice(Map<String, Object> selectData,Integer lib_idx, Integer pageCurrent, Integer pageSize) throws SocketException {
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.device_url);
        Map<String,Object> data = new HashMap<>(8);
        if(selectData != null) {
            data.putAll(selectData);
        }
        data.put("lib_idx",lib_idx);
        data.put("pageCurrent",pageCurrent);
        data.put("pageSize",pageSize);

        Map<String,String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(data));

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String resultJson = hr.getBody();
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(resultJson,ResultEntity.class);
                if(resultEntity.getState()) {
                    List<Map<String, Object>> temps = (List<Map<String, Object>>) resultEntity.getResult();
                    List<AppOPACEntity> resultData = new ArrayList<>(temps.size());
                    for (Map<String, Object> temp : temps) {
                        AppOPACEntity opac = ObjectUtils.convertMap(temp, AppOPACEntity.class);
                        resultData.add(opac);
                    }
                    return resultData;
                }
            }catch (Exception e){
                throw new SocketException("服务器返回数据不是预期格式");
            }
        }

        throw new SocketException("网络连接失败，响应码"+hr.getState());
    }

    @Override
    public List<AppOPACEntity> findOpacSelfHelpLibrary(Map<String, Object> selectData,Integer lib_idx, Integer pageCurrent, Integer pageSize) throws SocketException {
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.self_lib_url);
        Map<String,Object> data = new HashMap<>(8);
        if(selectData != null) {
            data.putAll(selectData);
        }
        data.put("lib_idx",lib_idx);
        data.put("pageCurrent",pageCurrent);
        data.put("pageSize",pageSize);

        Map<String,String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(data));

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String resultJson = hr.getBody();
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(resultJson,ResultEntity.class);
                if(resultEntity.getState()) {
                    List<Map<String, Object>> temps = (List<Map<String, Object>>) resultEntity.getResult();
                    List<AppOPACEntity> resultData = new ArrayList<>(temps.size());
                    for (Map<String, Object> temp : temps) {
                        AppOPACEntity opac = ObjectUtils.convertMap(temp, AppOPACEntity.class);
                        resultData.add(opac);
                    }
                    return resultData;
                }
            }catch (Exception e){
                throw new SocketException("服务器返回数据不是预期格式");
            }
        }

        throw new SocketException("网络连接失败，响应码"+hr.getState());
    }

    @Override
    public List<AppOPACEntity> searchDevice(Map<String, Object> selectData, Integer lib_idx) throws SocketException {
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.search_device_url);
        Map<String,Object> data = new HashMap<>(8);
        if(selectData != null) {
            data.putAll(selectData);
        }
        data.put("lib_idx",lib_idx);

        Map<String,String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(data));
        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String resultJson = hr.getBody();
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(resultJson,ResultEntity.class);
                if(resultEntity.getState()) {
                    List<Map<String, Object>> temps = (List<Map<String, Object>>) resultEntity.getResult();
                    List<AppOPACEntity> resultData = new ArrayList<>(temps.size());
                    for (Map<String, Object> temp : temps) {
                        AppOPACEntity opac = ObjectUtils.convertMap(temp, AppOPACEntity.class);
                        resultData.add(opac);
                    }
                    return resultData;
                }
            }catch (Exception e){
                throw new SocketException("服务器返回数据不是预期格式");
            }
        }

        throw new SocketException("网络连接失败，响应码"+hr.getState());
    }

    @Override
    public AppOPACEntity updateAppOPACEntity(AppOPACEntity entity) throws SocketException {
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.get_new_version_device_url);
        Map<String,String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(entity));
        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String resultJson = hr.getBody();
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(resultJson,ResultEntity.class);
                if(resultEntity.getState()) {
                    Object temps = resultEntity.getResult();
                    String j = JsonUtils.toJson(temps);
                    return JsonUtils.fromJson(j,AppOPACEntity.class);
                }else if("not_exists".equals(resultEntity.getRetMessage())){
                    return null;
                }
            }catch (Exception e){
                throw new SocketException("服务器返回数据不是预期格式");
            }
        }

        throw new SocketException("网络连接失败，响应码"+hr.getState());
    }
}
