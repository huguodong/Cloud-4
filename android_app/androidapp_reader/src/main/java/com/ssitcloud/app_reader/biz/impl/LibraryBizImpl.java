package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;
import android.content.res.Resources;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.LibraryBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.entity.LibraryEntity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/3/10.
 * 业务实现类
 */

public class LibraryBizImpl implements LibraryBizI {
    private final String charset="utf-8";
    private final String TAG="LibraryBizImpl";

    private Resources resources;
    private Context context;

    public LibraryBizImpl(Context context){
        this.resources = context.getResources();
        this.context = context.getApplicationContext();
    }

    @Override
    public List<LibraryEntity> obtainLibrary()  throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.library_list_url);
        Map<String, String> param = new HashMap<>(2);
        HttpResponce hr = HttpClientUtil.dopost(url, param, charset);
        if(hr.getState() == 200) {
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                if (result != null && result.getState()) {
                    List<Map<String, Object>> map = (List<Map<String, Object>>) result.getResult();
                    List<LibraryEntity> data = new ArrayList<>(map.size());
                    for(int i = 0,z = map.size();i<z;++i){
                        Map<String,Object> m = map.get(i);
                        LibraryEntity entity = new LibraryEntity();
                        entity.setLib_name(String.valueOf(m.get("lib_name")));
                        entity.setLibrary_idx((Integer) m.get("library_idx"));
                        entity.setLib_id((String) m.get("lib_id"));
                        data.add(entity);
                    }
                    //sort
                    Collections.sort(data,getComparatro());
                    return data;
                }
            } catch (Exception e) {
                throw new SocketException("服务器响应失败");
            }
        }

        throw new SocketException("服务器响应失败");
    }

    private Comparator<LibraryEntity> getComparatro(){
        return new Comparator<LibraryEntity>() {
            @Override
            public int compare(LibraryEntity o1, LibraryEntity o2) {
            try{
                return o1.getLib_name().compareTo(o2.getLib_name());
            }catch (Exception e){
                return 0;
            }
            }
        };
    }
}
