package com.ssitcloud.app_reader.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.SecurityBizI;
import com.ssitcloud.app_reader.biz.impl.SecurityBizImpl;
import com.ssitcloud.app_reader.service.DataUpdateIntentService;
import com.ssitcloud.app_reader.service.DataUpdateService;
import com.ssitcloud.app_reader.service.NotificationService;

import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by LXP on 2017/3/23.
 * 欢迎界面
 */

public class WelComeActivity extends Activity {
    public static final String ORDER = "ORDER";
    public static final int TO_MAIN_ORDER = 1;

    public volatile AtomicBoolean taskComplete = new AtomicBoolean(false);

    private Timer timer = new Timer();
    private Handler handler = new Handler();

    private long maxWait = 5_000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        if ((i.getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        int order = i.getIntExtra(ORDER,-1);
        if(order == TO_MAIN_ORDER){
            toMain();
            finish();
            return ;
        }
        if(i.getBooleanExtra("crash",false)){
            Toast.makeText(this,"应用崩溃了，已为您重启",Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_welcome);

        Intent deviceUpdateService = new Intent(this, DataUpdateIntentService.class);
        startService(deviceUpdateService);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        final SecurityBizI securityBiz = new SecurityBizImpl(this);
        final AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            private long s;
            private long e;
            @Override
            protected Void doInBackground(Void... params) {
                s = System.currentTimeMillis();
                try {
                    securityBiz.updatePuk();
                } catch (SocketException e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(taskComplete.compareAndSet(false,true)) {
                    e = System.currentTimeMillis();
                    long w = maxWait/2-e+s;
                    w = w>0?w:0;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    verificationLogin();
                                }
                            });
                        }
                    }, w);
                }
            }
        };

        task.execute();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(taskComplete.compareAndSet(false,true)) {
                    task.cancel(true);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            verificationLogin();
                        }
                    });
                }
            }
        },maxWait);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void verificationLogin(){
        LoginBizI loginBizI = new LoginBizImpl(this.getResources(),WelComeActivity.this);
        if(loginBizI.isLogin() != null){
            toMain();
        }else{
            toLogin();
        }
        finish();
    }

    private void toLogin(){
        Intent login = new Intent(this,LoginActivity.class);
        startActivity(login);
    }

    private void toMain(){
        Intent mainIntent = new Intent(this,MainActivity.class);
        startActivity(mainIntent);
        //开始服务
        Intent dataUpdateService = new Intent(this, DataUpdateService.class);
        startService(dataUpdateService);

        Intent noticeService = new Intent(this,NotificationService.class);
        startService(noticeService);
    }
}
