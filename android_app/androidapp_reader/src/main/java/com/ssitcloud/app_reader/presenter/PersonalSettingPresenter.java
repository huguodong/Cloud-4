package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.util.Log;

import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.impl.ConfigBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.view.viewInterface.PersonalSettingViewI;

import java.lang.ref.SoftReference;

/**
 * Created by LXP on 2017/3/23.
 *
 */

public class PersonalSettingPresenter {
    private SoftReference<PersonalSettingViewI> personalSettingView;
    private Context mContext;
    private LoginBizI loginBiz;
    private ConfigBizI configBiz;

    public PersonalSettingPresenter(PersonalSettingViewI view,Context context){
        personalSettingView = new SoftReference<>(view);
        mContext = context.getApplicationContext();
        loginBiz = new LoginBizImpl(mContext.getResources(),mContext);
        configBiz = new ConfigBizImpl(mContext);
    }

    public void logout(Callback callback){
        try {
            loginBiz.logout();
            callback.success(null);
        }catch (Exception e){
            callback.fail(null);
        }
    }

    /**
     * 加载数据
     */
    public void loadData(){
        PersonalSettingViewI view = personalSettingView.get();
        if(view == null){
            return ;
        }
        int remindTime = configBiz.getRemindTime();
        view.setRemindTime(remindTime);
        boolean messagePushState = configBiz.getMessagePushState();
        view.setMessagePushButton(messagePushState);
    }

    /**
     * 设置还书提醒
     * @param day
     */
    public void setRemindTime(int day){
        configBiz.setRemindTime(day);
        loadData();
    }

    public void setMessagePushState(boolean state){
        configBiz.setMessagePushState(state);
    }

    /**
     * 回调接口
     */
    public static interface Callback{
        /**
         * 成功时的回调
         * @param obj
         */
        void success(Object obj);

        /**
         * 失败时的回调
         * @param obj
         */
        void fail(Object obj);
    }
}
