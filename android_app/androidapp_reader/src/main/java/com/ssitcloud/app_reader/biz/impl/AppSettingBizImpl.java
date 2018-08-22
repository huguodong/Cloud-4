package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.AppSettingBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.ObjectUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.db.DbHelper;
import com.ssitcloud.app_reader.db.dao.AppSettingDao;
import com.ssitcloud.app_reader.entity.AppSettingEntity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/3/20
 * 服务实现类.
 */

public class AppSettingBizImpl implements AppSettingBizI {
    private final String TAG = ""+getClass();
    private Resources resources;
    private Context context;

    public AppSettingBizImpl(Context context){
        this.context = context.getApplicationContext();
        this.resources = context.getResources();
    }

    @Override
    public List<AppSettingEntity> obtainAppSettingByService(Integer lib_idx) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.app_setting_list_url);
        Map<String,String> param = new HashMap<>(3);
        param.put("json","{\"user_type\":\"2\"}");
        HttpResponce hr = HttpClientUtil.dopost(url,param,"utf-8");
        if(hr.getState() == 200){
            String body = hr.getBody();
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(body, ResultEntity.class);
                if (resultEntity.getState()){
                    List<Map<String,Object>> mapData = (List<Map<String, Object>>) resultEntity.getResult();
                    List<AppSettingEntity> data = new ArrayList<>(mapData.size());
                        for (Map<String, Object> stringObjectMap : mapData) {
                            AppSettingEntity appSetting = ObjectUtils.convertMap(stringObjectMap, AppSettingEntity.class);
                            data.add(appSetting);

                        }

                    //init database
                    DbHelper dh = new DbHelper(context);
                    SQLiteDatabase writeDb;
                    try {
                        writeDb = dh.getWritableDatabase();
                    }catch (SQLiteException e){
                        Log.e(TAG,"open writeDb database error");
                        return filterLib(data,lib_idx);
                    }
                    try {
                        writeDb.beginTransaction();
                        List<Integer> settingIds = new ArrayList<>(data.size());
                        for (AppSettingEntity appSettingEntity : data) {
                            AppSettingDao.insert(writeDb,appSettingEntity);
                            settingIds.add(appSettingEntity.getSetting_idx());
                        }
                        AppSettingDao.deleteNotIn(writeDb,settingIds);
                        writeDb.setTransactionSuccessful();
                    }finally {
                        writeDb.endTransaction();
                        dh.close();
                    }
                    return filterLib(data,lib_idx);
                }
            }catch (Exception e){
                throw new SocketException("服务器没有返回预期数据 "+body);
            }
        }

        throw new SocketException(url+" responce state==>"+hr.getState());

    }

    @Override
    public List<AppSettingEntity> obtainAppSetting(Integer lib_idx) {
        DbHelper dh = new DbHelper(context);
        try {
            SQLiteDatabase rDb = dh.getReadableDatabase();
            return AppSettingDao.select(rDb, lib_idx.toString());
        }finally {
            dh.close();
        }
    }

    private static List<AppSettingEntity> filterLib(List<AppSettingEntity> data,Integer lib_idx){
        if(lib_idx == null){
            return data;
        }
        List<AppSettingEntity> returnData = new ArrayList<>(data.size());
        for (AppSettingEntity appSettingEntity : data) {
            if(lib_idx.equals(appSettingEntity.getLib_idx())){
                returnData.add(appSettingEntity);
            }
        }
        return returnData;
    }
}
