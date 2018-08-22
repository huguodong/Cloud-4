package com.ssitcloud.app_reader.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.common.utils.ActivityManager;
import com.ssitcloud.app_reader.service.DataUpdateService;
import com.ssitcloud.app_reader.view.LoginActivity;

/**
 * Created by LXP on 2017/3/27.
 * 基础activity，此activity会被ActivityManager管理
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager am = ActivityManager.getInstance();
        am.push(this);
        Log.d(getClass()+" activity","onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager am = ActivityManager.getInstance();
        am.pop(this);
        Log.d(getClass()+" activity","onDestroy");
    }

    /**
     * 登出操作
     */
    public void logout(){
        LoginBizI loginBiz = new LoginBizImpl(getResources(),this);
        loginBiz.logout();

        ActivityManager am = ActivityManager.getInstance();
        am.popFinishAll();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        //stop service
        Intent stopSevice = new Intent(this, DataUpdateService.class);
        stopService(stopSevice);
    }
}
