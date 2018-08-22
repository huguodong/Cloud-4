package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderBizImpl;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;
import com.ssitcloud.app_reader.view.viewInterface.ForgetPasswordViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;

/**
 * Created by LXP on 2017/3/27.
 * 找回密码presenter
 */

public class ForgetPasswordPresenter {
    private Context mcontext;
    private SoftReference<ForgetPasswordViewI> forgetPasswordViewIRef;
    private ReaderBizI readerBiz;
    private LoginBizI loginBiz;
    public ForgetPasswordPresenter(ForgetPasswordViewI view,Context context){
        mcontext = context.getApplicationContext();
        forgetPasswordViewIRef = new SoftReference<>(view);
        readerBiz = new ReaderBizImpl(mcontext);
        loginBiz = new LoginBizImpl(mcontext.getResources(),mcontext);
    }

    public void sendVcode(){
        final ForgetPasswordViewI view = forgetPasswordViewIRef.get();
        if(view == null){
            return;
        }
        ReaderInfoEntity reader = view.getInfo();
        reader.setReader_pwd(null);
        AsyncTask<ReaderInfoEntity,Void,Integer> task = new AsyncTask<ReaderInfoEntity, Void, Integer>() {
            @Override
            protected Integer doInBackground(ReaderInfoEntity... params) {
                try {
                    return readerBiz.forgetPwd(params[0],null);
                } catch (SocketException e) {
                    return -1;
                }
            }

            @Override
            protected void onPostExecute(Integer integer) {
                view.hideWait();
                if(integer == ReaderBizI.success){//成功
                    view.setMessage(0);
                }else if(integer == ReaderBizI.noUser){//找不到用户
                    view.setMessage(1);
                }else if(integer == -1){//网络连接失败
                    view.setMessage(-2);
                }else{//未知错误
                    view.setMessage(-3);
                }
            }
        };

        view.showWait();
        task.execute(reader);
    }

    public void forgetPwd(){
        final ForgetPasswordViewI view = forgetPasswordViewIRef.get();
        if(view == null){
            return;
        }
        ReaderInfoEntity reader = view.getInfo();
        final String vocode = view.getVocde();
        AsyncTask<ReaderInfoEntity,Void,Integer> task = new AsyncTask<ReaderInfoEntity, Void, Integer>() {
            @Override
            protected Integer doInBackground(ReaderInfoEntity... params) {
                try {
                    return readerBiz.forgetPwd(params[0],vocode);
                } catch (SocketException e) {
                    return -1;
                }
            }

            @Override
            protected void onPostExecute(Integer integer) {
                view.hideWait();
                if(integer == ReaderBizI.success){//失败
                    loginBiz.logout();
                    view.setSuccess();
                }else if(integer == ReaderBizI.vcodeError){
                    view.setMessage(2);
                }else if(integer == ReaderBizI.noUser){//找不到用户
                    view.setMessage(1);
                }else if(integer == ReaderBizI.vcodeTimeOut){
                    view.setMessage(3);
                }else if(integer == -1){//网络连接失败
                    view.setMessage(-2);
                }else{//未知错误
                    view.setMessage(-2);
                }
            }
        };

        view.showWait();
        task.execute(reader);
    }
}
