package com.ssitcloud.app_reader.task;

import android.content.Context;
import android.util.Log;

import com.ssitcloud.app_reader.biz.AppSettingBizI;
import com.ssitcloud.app_reader.biz.impl.AppSettingBizImpl;

import java.net.SocketException;

/**
 * Created by LXP on 2017/3/20.
 * app setting 更新任务
 */

public class AppSettingUpdateTask extends AbstractSafeTask {
    private final String TAG = "AppSettingUpdateTask";
    private Context context;
    private AppSettingBizI appSettingBizI;

    public AppSettingUpdateTask(Context context){
        this.context = context.getApplicationContext();
        appSettingBizI = new AppSettingBizImpl(context);
    }

    @Override
    public void task() {
        try {
            appSettingBizI.obtainAppSettingByService(null);
            Log.d(TAG,"appSettingBizI.obtainAppSettingByService success");
        } catch (SocketException e) {
            Log.d(TAG,"appSettingBizI.obtainAppSettingByService network error");
        }
    }
}
