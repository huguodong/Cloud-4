package com.ssitcloud.app_operator.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.test.mock.MockApplication;

import com.ssitcloud.app_operator.biz.LoginBizI;
import com.ssitcloud.app_operator.biz.impl.LoginBizImpl;
import com.ssitcloud.app_operator.service.PermissionUpdateService;
import com.ssitcloud.app_operator.view.LoginActivity;

/**
 * Created by LXP on 2017/9/15.
 * 管理Activity，继承该Activity会被管理调用栈
 */

public abstract class ManageActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().push(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().pop(this);
    }

    /**
     * 登出操作
     * @param isToLogin 是否需要跳转到登录界面，若为true则跳转
     */
    protected void logout(boolean isToLogin){
        Intent stopPermissionService = new Intent(this, PermissionUpdateService.class);
        stopService(stopPermissionService);

        LoginBizI loginBiz = new LoginBizImpl(getResources(),this);
        loginBiz.logout();
        ActivityManager.getInstance().popFinishAll();
        if(isToLogin) {
            Intent toLogin = new Intent(this, LoginActivity.class);
            startActivity(toLogin);
        }
    }
}
