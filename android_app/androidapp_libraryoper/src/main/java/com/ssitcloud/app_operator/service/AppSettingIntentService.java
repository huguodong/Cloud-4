package com.ssitcloud.app_operator.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.ssitcloud.app_operator.biz.LoginBizI;
import com.ssitcloud.app_operator.biz.impl.LoginBizImpl;
import com.ssitcloud.app_operator.entity.AppSettingEntity;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by LXP on 2017/8/16.
 *
 */

public class AppSettingIntentService extends IntentService {

    public AppSettingIntentService() {
        super("AppSettingIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LoginBizI loginBizI = new LoginBizImpl(this.getResources(),this);
        loginBizI.getAppSetting(null)
                .subscribe(new Consumer<List<AppSettingEntity>>() {
                    @Override
                    public void accept(@NonNull List<AppSettingEntity> appSettingEntities) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
    }

    public static void startInterService(Context context){
        Intent intent = new Intent(context,AppSettingIntentService.class);
        context.startService(intent);
    }
}
