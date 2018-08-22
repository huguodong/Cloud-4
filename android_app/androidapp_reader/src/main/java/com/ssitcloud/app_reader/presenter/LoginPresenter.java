package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.AppSettingBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.AppSettingBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;
import com.ssitcloud.app_reader.view.viewInterface.LoginViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;

/**
 * Created by LXP on 2017/3/7.
 *
 */

public class LoginPresenter {
    private final String TAG="LoginPresenter";

    private SoftReference<LoginViewI> loginview;
    private Context mContext;
    private Resources resources;
    private LoginBizI loginBiz;
    private AppSettingBizI appSettingBiz;
    private ReaderCardBizI readerCardBiz;

    public LoginPresenter(LoginViewI loginview, Context context){
        this.loginview = new SoftReference<>(loginview);
        this.mContext = context.getApplicationContext();
        this.loginBiz = new LoginBizImpl(mContext.getResources(),this.mContext);
        this.appSettingBiz = new AppSettingBizImpl(this.mContext);
        this.readerCardBiz = new ReaderCardBizImpl(mContext.getResources(),this.mContext);
    }

    public void login(final ReaderInfoEntity readerInfo){
        final LoginViewI loginV = loginview.get();
        if(loginV == null){
            return ;
        }
        resources = mContext.getResources();
        AsyncTask<ReaderInfoEntity,Void,ResultEntity> task = new AsyncTask<ReaderInfoEntity, Void, ResultEntity>() {
            @Override
            protected ResultEntity doInBackground(ReaderInfoEntity... params) {
                ResultEntity r;
                try {
                    r = loginBiz.login(params[0]);
                }catch (SocketException e){
                    return null;
                }
                if(r != null && r.getState()) {//检查登陆是否成功
                    ReaderInfoEntity reader = (ReaderInfoEntity) r.getResult();
                    //加载app setting
                    try {
                        appSettingBiz.obtainAppSettingByService(null);
                    } catch (SocketException e) {//加载app setting失败，退出登陆
                        loginBiz.logout();
                        return null;
                    }

                    //加载读者卡信息
                    try {
                        readerCardBiz.obtainReaderCardByService(reader.getReader_idx());
                    } catch (AuthException e){
                    } catch (SocketException e) {
                    }
                }

                return r;
            }

            @Override
            protected void onPostExecute(ResultEntity data) {
                loginV.hideWait();
                if(data == null){
                    loginV.loginFail(resources.getString(R.string.login_network_fail));
                }else if(data.getState()){
                    loginV.loginSuccess();
                }else if("4".equals(data.getRetMessage())){
                    loginV.loginFail(resources.getString(R.string.login_user_pwd_error));
                }else if("1".equals(data.getRetMessage())){
                    loginV.loginFail(resources.getString(R.string.login_user_lock));
                }else if("3".equals(data.getRetMessage())){
                    loginV.loginFail(resources.getString(R.string.login_user_check_pwd));
                }else{
                    loginV.loginFail(resources.getString(R.string.login_user_check_user));
                }
            }
        };
        loginV.showWait();
        task.execute(readerInfo);
    }
}
