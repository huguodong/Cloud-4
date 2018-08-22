package com.ssitcloud.app_reader.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.task.AppSettingUpdateTask;
import com.ssitcloud.app_reader.task.ReaderCardUpdateTask;
import com.ssitcloud.app_reader.task.ReaderUpdateTask;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LXP on 2017/3/20.
 * 数据更新服务，用于更新本地数据库数据
 */

public class DataUpdateService extends Service {
    private final String TAG = "DataUpdateService";
//    private long UPDATE_CARD_TIME;//读者卡更新
    private long UPDATE_APP_SETTING_TIME;//app设置更新
    private long UPDATE_READER_TIME = 30_000;
    private Timer updateTimer;
    private volatile int timerState = 0;

    @Override
    public void onCreate() {
        super.onCreate();
//        UPDATE_CARD_TIME = getResources().getInteger(R.integer.update_app_setting_time);
        UPDATE_APP_SETTING_TIME = getResources().getInteger(R.integer.update_app_setting_time);
        UPDATE_READER_TIME = getResources().getInteger(R.integer.update_reader_time);
        updateTimer = new Timer("DataUpdateService Timer");

//        TimerTask readerCardTask = new ReaderCardUpdateTask(this);
//        updateTimer.schedule(readerCardTask, 30_000, UPDATE_CARD_TIME);
//        Log.d(TAG, "DataUpdateService add task update reader card");

        TimerTask appsettingTask = new AppSettingUpdateTask(this);
        updateTimer.schedule(appsettingTask, 0, UPDATE_APP_SETTING_TIME);
        Log.d(TAG, "DataUpdateService add task update reader card");

        TimerTask readerTask = new ReaderUpdateTask(this);
        updateTimer.schedule(readerTask, 0, UPDATE_READER_TIME);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "DataUpdateService start");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        updateTimer.cancel();
        updateTimer = null;

        Log.d(TAG, "DataUpdateService stop");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
