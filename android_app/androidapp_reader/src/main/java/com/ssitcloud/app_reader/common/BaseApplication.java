package com.ssitcloud.app_reader.common;

import android.app.Application;

import com.ssitcloud.app_reader.common.exception.ExceptionHandler;

/**
 * Created by LXP on 2017/4/24.
 * 基础Application
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ExceptionHandler exceptionHandler = new ExceptionHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
    }
}
