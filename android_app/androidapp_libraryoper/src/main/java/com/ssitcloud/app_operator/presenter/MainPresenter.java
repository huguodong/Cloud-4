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
import com.ssitcloud.app_operator.view.viewInterface.MainViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.Map;

/**
 * 创建日期：2017/4/25 19:30
 * @author shuangjunjie
 */

public class MainPresenter {

    private final String TAG = "MainPresenter";

    private SoftReference<MainViewI> mainView;
    private Context context;
    private Resources resources;
    private LoginBizI loginBiz;

    public MainPresenter(MainViewI mainViewI, Context context) {
        this.mainView = new SoftReference<>(mainViewI);
        this.context = context.getApplicationContext();
        this.loginBiz = new LoginBizImpl(context.getResources(),this.context);
    }

    public void login(Map<String,Object> map, final boolean isAutoLogin){
        final MainViewI mainV = mainView.get();
        if (null == mainV){
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
//                if (!isAutoLogin){
//                    mainV.hideWait();
//                }
                if (null == data){
                    mainV.loginFail(resources.getString(R.string.connect_network_fail));
                }else if (null == data.getResult()){
                    mainV.loginFail(data.getMessage());
                }else if (data.getState() && null != data.getResult()){
                    mainV.loginSuccess(data);
                }else if (!data.getState()){
                    mainV.loginFail(data.getMessage());
                }
            }
        };
//        if (!isAutoLogin){
//            mainV.showWait();
//        }

        task.execute(map);
    }

    public LoginInfoDbEntity getLoginInfo(){
        return loginBiz.getLoginInfo();
    }

    public void logout(){
        loginBiz.logout();
    }
}
