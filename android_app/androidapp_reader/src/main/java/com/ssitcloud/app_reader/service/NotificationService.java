package com.ssitcloud.app_reader.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.task.ElecNoticeTask;
import com.ssitcloud.app_reader.task.RenewNoticeTask;

import java.util.Timer;

/**
 * Created by LXP on 2017/4/17.
 * 通知服务
 */

public class NotificationService extends Service implements NoticeComponentI {
    private Timer timer;

    //times
    private long elecNoticeTime = 5_000;
    private long renewNoticeTime = 6 * 60 * 60_000;//6 hours

    private ElecNoticeTask elecNoticeTask;
    private RenewNoticeTask renewNoticeTask;

    private boolean isNew = false;

    private NotificationManager mNotificationManager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        elecNoticeTime = getResources().getInteger(R.integer.elec_notice_time);
        renewNoticeTime = getResources().getInteger(R.integer.renew_notice_time);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        timer = new Timer();


        elecNoticeTask = new ElecNoticeTask(this, this);
        timer.schedule(elecNoticeTask, 0, elecNoticeTime);
        renewNoticeTask = new RenewNoticeTask(this, this);
        timer.schedule(renewNoticeTask, 0, renewNoticeTime);

        isNew = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if(!isNew){//客户端启动，获取一次数据
            AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    elecNoticeTask.run();
                    renewNoticeTask.run();
                    return null;
                }
            };

            task.execute();
        }

        isNew = false;
        return START_STICKY;
    }

    @Override
    public void sendNotice(int notifyId, Notification notification) {
        sendNotice(null, notifyId, notification);
    }

    @Override
    public void sendNotice(String tag, int notifyId, Notification notification) {
        mNotificationManager.notify(tag, notifyId, notification);
    }
}
