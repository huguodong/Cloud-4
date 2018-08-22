package com.ssitcloud.app_operator.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ssitcloud.app_operator.biz.PermissionBiz;
import com.ssitcloud.app_operator.biz.impl.PermissionBizImpl;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LXP on 2017/8/17.
 *
 */

public class PermissionUpdateService extends Service {
    private Timer timer;
    private PermissionBiz permissionBiz;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        permissionBiz = PermissionBizImpl.getInstance(this);
        timer = new Timer();
        TimerTask updateTask = new TimerTask() {
            @Override
            public void run() {
                permissionBiz.getPermission(null).subscribe();
            }
        };
        timer.schedule(updateTask,0,10_000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
