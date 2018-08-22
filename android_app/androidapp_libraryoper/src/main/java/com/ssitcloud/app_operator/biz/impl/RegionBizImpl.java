package com.ssitcloud.app_operator.biz.impl;

import android.content.Context;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.RegionBizI;
import com.ssitcloud.app_operator.common.entity.HttpResponce;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.HttpClientUtil;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.RequestUrlUtil;
import com.ssitcloud.app_operator.entity.RegionEntity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/4/28 11:47
 * @author shuangjunjie
 */

public class RegionBizImpl implements RegionBizI{

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
    public List<RegionEntity> regionListForNormal(List<Integer> list) throws SocketException {
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.region_for_normal_url);
        Map<String, String> map = new HashMap<>();
        map.put("json", JsonUtils.toJson(list));

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String j = hr.getBody();
            try {
                ResultEntity r = JsonUtils.fromJson(j,ResultEntity.class);
                if(r.getState()){
                    List d = JsonUtils.fromJson(JsonUtils.toJson(r.getResult()),List.class);
                    List<RegionEntity> regionEntities = new ArrayList<>();
                    for (int m = 0; m < d.size(); m++){
                        RegionEntity rEntity = JsonUtils.fromJson(JsonUtils.toJson(d.get(m)),RegionEntity.class);
                        regionEntities.add(rEntity);
                    }
                    return regionEntities;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SocketException("服务器没有返回预期数据");
            }
        }

        throw new SocketException();
    }
}
