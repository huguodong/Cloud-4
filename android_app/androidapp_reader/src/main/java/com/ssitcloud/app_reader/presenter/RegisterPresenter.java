package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;

import com.ssitcloud.app_reader.biz.RegisterBizI;
import com.ssitcloud.app_reader.biz.impl.RegisterBizImpl;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;
import com.ssitcloud.app_reader.view.viewInterface.RegisterViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;

/**
 * Created by LXP on 2017/3/6.
 *
 */

public class RegisterPresenter {
    private SoftReference<RegisterViewI> registerViewSoftReference;

    private RegisterBizI registerBiz;

    public RegisterPresenter(RegisterViewI registerView, Context context){
        this.registerViewSoftReference = new SoftReference<>(registerView);
        registerBiz = new RegisterBizImpl(context.getApplicationContext().getResources());
    }

    public void sendVcode(){
        final RegisterViewI registerViewI = registerViewSoftReference.get();
        if(registerViewI == null){
            return ;
        }
        final String mobile = registerViewI.getMobile();
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            private volatile int state = 0;
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    boolean b = registerBiz.seneRegisterMobileCode(mobile);
                    state =  b?1:2;
                } catch (SocketException e) {
                    state = -1;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                registerViewI.sendVcodeInfo(state);
            }
        };

        task.execute();
    }

    public void register(){
        final RegisterViewI registerViewI = registerViewSoftReference.get();
        if(registerViewI == null){
            return ;
        }
        final ReaderInfoEntity readerInfo = registerViewI.getReaderInfo();
        final String vocde = registerViewI.getVcode();
        AsyncTask<ReaderInfoEntity,Void,String> registerTask = new AsyncTask<ReaderInfoEntity, Void, String>() {
            @Override
            protected String doInBackground(ReaderInfoEntity... params) {
                return registerBiz.register(readerInfo,vocde);
            }

            @Override
            protected void onPostExecute(String s) {
                registerViewI.hideWait();
                if ("success".equals(s)) {
                    registerViewI.registerSuccess();
                } else{
                    registerViewI.registerFail(s);
                }
            }
        };

        registerViewI.showWait();
        registerTask.execute(readerInfo);
    }
}
