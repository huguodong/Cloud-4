package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.view.viewInterface.ChangePasswordViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;

/**
 * Created by LXP on 2017/3/27.
 * 修改密码presenter
 */

public class ChangePasswordPresenter {
    private Context mcontext;
    private LoginBizI loginBiz;
    private ReaderBizI readerBiz;
    private SoftReference<ChangePasswordViewI> viewRef;

    public ChangePasswordPresenter(ChangePasswordViewI view,Context context){
        mcontext = context.getApplicationContext();
        loginBiz = new LoginBizImpl(mcontext.getResources(),mcontext);
        readerBiz = new ReaderBizImpl(mcontext);
        viewRef = new SoftReference<>(view);
    }

    public void changePwd(){
        final ChangePasswordViewI v = viewRef.get();
        if(v == null){
            return ;
        }
        final Integer reader_idx = loginBiz.isLogin();
        if(reader_idx == null){
            v.fail(-1);
            return ;
        }
        final String oPwd = v.getOldPwd();
        final String newPwd = v.getNewPwd();

        AsyncTask<Void,Void,Integer> task = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    return readerBiz.changePwd(reader_idx,oPwd,newPwd)?1:0;
                } catch (SocketException e) {
                    return null;
                }catch (AuthException e){
                    return -1;
                }
            }

            @Override
            protected void onPostExecute(Integer integer) {
                v.hideWait();
                if(integer == null){
                    v.fail(-2);
                }else if(integer == 1){
                    v.success();
                    loginBiz.logout();
                }else if(integer == -1){//授权过期了
                    v.fail(-1);
                }else{//原密码不正确
                    v.fail(0);
                }
            }
        };
        v.showWait();
        task.execute();
    }
}
