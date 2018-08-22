package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.RegionBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.entity.DeviceRegionEntity;
import com.ssitcloud.app_reader.entity.RegionEntity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/28.
 *
 */

public class RegionBizImpl implements RegionBizI {
    private Context mcontext;
    private final String charset="utf-8";

    public RegionBizImpl(Context context){
        mcontext = context.getApplicationContext();
    }

    @Override
    public List<RegionEntity> regionList(String nationCode) throws SocketException {
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.region_url);
        Map<String,String> map = new HashMap<>(1,1.0f);
        map.put("json","{\"regi_code\":\""+nationCode+"\"}");

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String j = hr.getBody();
            try {
                ResultEntity r = JsonUtils.fromJson(j,ResultEntity.class);
                if(r.getState()){
                    List d = (List) r.getResult();
                    List<RegionEntity> rData = new ArrayList<>(d.size());
                    for (Object m : d) {
                        rData.add(JsonUtils.fromJson(JsonUtils.toJson(m),RegionEntity.class));
                    }
                    return rData;
                }
            } catch (Exception e) {
                throw new SocketException("服务器没有返回预期数据");
            }
        }

        throw new SocketException();
    }

    @Override
    public List<DeviceRegionEntity> deviceRegionList(Integer lib_idx) throws SocketException {
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.device_region_url);
        Map<String,String> map = new HashMap<>(1,1.0f);
        map.put("json","{\"library_idx\":"+lib_idx+"}");

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String j = hr.getBody();
            try {
                ResultEntity r = JsonUtils.fromJson(j,ResultEntity.class);
                if(r.getState()){
                    List d = (List) r.getResult();
                    List<DeviceRegionEntity> rData = new ArrayList<>(d.size());
                    for (Object m : d) {
                        rData.add(JsonUtils.fromJson(JsonUtils.toJson(m),DeviceRegionEntity.class));
                    }
                    return rData;
                }
            } catch (Exception e) {
                throw new SocketException("服务器没有返回预期数据");
            }
        }

        throw new SocketException();
    }
}
