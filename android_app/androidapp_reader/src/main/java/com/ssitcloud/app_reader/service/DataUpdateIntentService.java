package com.ssitcloud.app_reader.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.DeviceBizI;
import com.ssitcloud.app_reader.biz.impl.ConfigBizImpl;
import com.ssitcloud.app_reader.biz.impl.DeviceBizImpl;
import com.ssitcloud.app_reader.entity.AppOPACEntity;

import java.net.SocketException;

/**
 * Created by LXP on 2017/8/8.
 * *
 */

public class DataUpdateIntentService extends IntentService {

    public DataUpdateIntentService() {
        super("DataUpdateIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        DeviceBizI deviceBiz = new DeviceBizImpl(this);
        ConfigBizI configBiz = new ConfigBizImpl(this);
        AppOPACEntity preferDevice = configBiz.getPreferDevice();

        if(preferDevice != null){
            try {
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                }
                AppOPACEntity appOPACEntity = deviceBiz.updateAppOPACEntity(preferDevice);
                configBiz.savePreferDevice(appOPACEntity);
                Log.d("DataUpdateIntentService","获取到的AppOPACEntity->"+appOPACEntity);
            } catch (SocketException e) {
                Log.d("DataUpdateIntentService","发生异常,message->"+e.getMessage());
            }
        }
    }
}
