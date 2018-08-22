package com.ssitcloud.app_operator.biz.impl;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.DeviceMonitorBizI;
import com.ssitcloud.app_operator.common.entity.HttpResponce;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.HttpClientUtil;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.RequestUrlUtil;
import com.ssitcloud.app_operator.common.utils.StringUtils;

import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * 创建日期：2017/4/5 16:33
 * @author shuangjunjie
 */

public class DeviceMonitorBizImpl implements DeviceMonitorBizI{

    private final String TAG = "DeviceMonitorBizImpl";
    private final String charset = "utf-8";
    private Resources resources;
    private Context context;

    public DeviceMonitorBizImpl(Resources resources, Context context) {
        this.resources = resources;
        this.context = context.getApplicationContext();
    }

    @Override
    public Map selectLib(Map<String, Object> reqMap) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.select_lib_url);
        Map<String, String> map = new HashMap<>();
        map.put("json", JsonUtils.toJson(reqMap));
        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if (hr.getState() == 200){
            try {
                Map resultMap = JsonUtils.fromJson(hr.getBody(), Map.class);
                return resultMap;
            }catch (Exception e){
                Log.e(TAG,"to json exception",e);
                return null;
            }
        }else {
            throw new SocketException("网络异常");
        }
    }

    @Override
    public ResultEntity selectDeviceState(Map<String, Object> reqMap) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.get_device_state_url);
        Map<String, String> map = new HashMap<>();
        map.put("json", JsonUtils.toJson(reqMap));
        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if (hr.getState() == 200){
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                return result;
            }catch (Exception e){
                Log.e(TAG,"to json exception",e);
                return null;
            }
        }else {
            throw new SocketException("网络异常");
        }
    }

    @Override
    public Observable<Map<String, Object>> selectDeviceState(final String library_idx, final String device_idx) {
        return Observable.create(new ObservableOnSubscribe<Map<String, Object>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Map<String, Object>> subscribe) throws Exception {
                String url = RequestUrlUtil.getUrl(resources, R.string.get_device_state_url);
                Map<String, String> map = new HashMap<>();
                map.put("json", "{\"device_idx\":"+device_idx+",\"library_idx\":"+library_idx+"}");
                HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
                if(hr.getState() == 200){
                    try{
                        ResultEntity resultEntity = JsonUtils.fromJson(hr.getBody(),ResultEntity.class);
                        if(resultEntity.getState()){
                            subscribe.onNext((Map<String, Object>) resultEntity.getResult());
                            subscribe.onComplete();
                        }else{
                            subscribe.onError(new Exception("data_error"));
                        }
                        return ;
                    }catch (Exception e){
                        subscribe.onError(e);
                        return ;
                    }

                }
                subscribe.onError(new SocketException("网络异常"));
            }
        });
    }

    @Override
    public ResultEntity selectLibraryIdByIdx(Map<String, Object> reqMap) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.get_library_id_url);
        Map<String, String> map = new HashMap<>();
        map.put("json", JsonUtils.toJson(reqMap));
        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if (hr.getState() == 200){
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                return result;
            }catch (Exception e){
                Log.e(TAG,"to json exception",e);
                return null;
            }
        }else {
            throw new SocketException("网络异常");
        }
    }

    @Override
    public ResultEntity querySlaveLibraryByMasterIdx(Map<String, Object> reqMap) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.get_slave_master_url);
        Map<String, String> map = new HashMap<>();
        map.put("json", JsonUtils.toJson(reqMap));
        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if (hr.getState() == 200){
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                return result;
            }catch (Exception e){
                Log.e(TAG,"to json exception",e);
                return null;
            }
        }else {
            throw new SocketException("网络异常");
        }
    }

    @Override
    public ResultEntity selectDeviceMgmtByLibraryIdxs(Map<String, Object> reqMap, List<Integer> ids) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.select_device_mgmt_url);
        Map<String, String> map = new HashMap<>();
        Object keyWord = reqMap.remove("keyWord");
        if(reqMap.containsKey("page")) {
            map.put("page", JsonUtils.toJson(reqMap));
        }
        if (keyWord != null ){
            map.put("device_id", keyWord.toString());
        }
        if (reqMap.containsKey("region_idx") && !StringUtils.isEmpty(reqMap.get("region_idx").toString())){
            map.put("region_idx", reqMap.get("region_idx").toString());
        }
        if (ids != null){
            map.put("libIdxs", JsonUtils.toJson(ids));
        }
        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if (hr.getState() == 200){
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                return result;
            }catch (Exception e){
                Log.e(TAG,"to json exception",e);
                return null;
            }
        }else {
            throw new SocketException("网络异常");
        }
    }
}
