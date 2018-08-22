package com.ssitcloud.app_operator.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.LoginBizI;
import com.ssitcloud.app_operator.biz.impl.LoginBizImpl;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_operator.view.viewInterface.LoginViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.Map;

/**
 * 创建日期：2017/3/16 18:52
 * @author shuangjunjie
 */

public class LoginPresenter {

    private final String TAG = "LoginPresenter";

    private SoftReference<LoginViewI> loginView;
    private Context context;
    private Resources resources;
    private LoginBizI loginBiz;

    public LoginPresenter(LoginViewI loginViewI,Context context) {
        this.loginView = new SoftReference<LoginViewI>(loginViewI);
        this.context = context.getApplicationContext();
        this.loginBiz = new LoginBizImpl(context.getResources(),this.context);
    }

    public void login(Map<String,Object> map){
        final LoginViewI loginV = loginView.get();
        if (null == loginV){
            return ;
        }
        resources = context.getResources();
        AsyncTask<Map<String,Object>,Void,ResultEntity> task = new AsyncTask<Map<String,Object>, Void, ResultEntity>() {
            @Override
            protected ResultEntity doInBackground(Map<String,Object>... params) {
                try {
                    return loginBiz.login(params[0]);
                }catch (SocketException e){
                   return null;
                }

            }

            @Override
            protected void onPostExecute(ResultEntity data){
                loginV.hideWait();
                if (null == data){
                    loginV.loginFail(resources.getString(R.string.connect_network_fail));
                }else if (null == data.getResult()){
                    loginV.loginFail(data.getMessage());
                }else if (data.getState() && null != data.getResult()){
                    loginV.loginSuccess(data);
                }else if (!data.getState()){
                    loginV.loginFail(data.getMessage());
                }
            }
        };

        loginV.showWait();
        task.execute(map);
    }

    public LoginInfoDbEntity getLoginInfo(){
        return loginBiz.getLoginInfo();
    }

    public void logout(){
        loginBiz.logout();
    }
}
