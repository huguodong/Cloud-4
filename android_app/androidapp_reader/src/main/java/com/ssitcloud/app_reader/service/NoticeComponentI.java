package com.ssitcloud.app_reader.service;

import android.app.Notification;
import android.support.v7.app.NotificationCompat;

/**
 * Created by LXP on 2017/4/17.
 * 通知组件接口
 */

public interface NoticeComponentI {

    void sendNotice(int notifyId,Notification notification);

    void sendNotice(String tag, int notifyId, Notification notification);
}
