package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;
import com.ssitcloud.app_reader.entity.ReaderInfoMenuViewEntity;
import com.ssitcloud.app_reader.view.viewInterface.ReaderInfoMenuViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;

/**
 * Created by LXP on 2017/3/17.
 * 读者界面Presenter
 */

public class ReaderInfoMenuPresenter {
    private final String TAG = "" + getClass();

    private SoftReference<ReaderInfoMenuViewI> readerInfoMenuView;
    private Context context;

    private LoginBizI loginBiz;
    private ReaderCardBizI readerCardBiz;

    public ReaderInfoMenuPresenter(ReaderInfoMenuViewI readerInfoMenuView, Context context) {
        this.context = context.getApplicationContext();
        this.readerInfoMenuView = new SoftReference<>(readerInfoMenuView);
        loginBiz = new LoginBizImpl(this.context.getResources(),this.context);
        readerCardBiz = new ReaderCardBizImpl(context.getResources(), this.context);
    }

    public void loadData() {
        final ReaderInfoMenuViewI view = readerInfoMenuView.get();
        if (view == null) {
            return;
        }

        final LoginInfoDbEntity readerDbData = loginBiz.getLoginReturnData();//获取数据库数据
        if (readerDbData == null) {
            view.otherView(null, -1);//设置登陆
            return;
        }
        //是否已经加载过视图，若没有则先从数据库加载一次
        if (!view.alreadySetMenuView()) {
            ReaderInfoMenuViewEntity viewData = new ReaderInfoMenuViewEntity();
            viewData.setReaderInfo(readerDbData);
            viewData.setReaderCard(readerCardBiz.obtainPreferCard());
            view.setView(viewData);
        }

        AsyncTask<LoginInfoDbEntity, Void, LoginInfoDbEntity> task = new AsyncTask<LoginInfoDbEntity, Void, LoginInfoDbEntity>() {
            @Override
            protected LoginInfoDbEntity doInBackground(LoginInfoDbEntity... params) {
                ReaderInfoEntity loginData = new ReaderInfoEntity();
                loginData.setLogin_name(params[0].getLogin_name());
                loginData.setReader_pwd(params[0].getPwd());

                try {

                    loginBiz.obtainReader(params[0].getReader_idx());
                    LoginInfoDbEntity loginInfoDbData = loginBiz.getLoginReturnData();
                    //读取一次读者卡数据
                    try {
                        readerCardBiz.obtainReaderCardByService(loginInfoDbData.getReader_idx());
                    } catch (Exception e) {
                        Log.d(TAG, "readerCardBiz.obtainReaderCardByService error,message " + e.getMessage());
                    }
                    return loginInfoDbData;//获取数据成功

                } catch (SocketException e) {
                    Log.d(TAG, "loginBizI.login() error,message " + e.getMessage());
                } catch (AuthException e) {
                    return null;//登陆失败，需要重新登陆
                }

                return loginBiz.getLoginReturnData();//使用旧的数据库数据
            }

            @Override
            protected void onPostExecute(LoginInfoDbEntity returnData) {
                if (returnData != null) {
                    ReaderInfoMenuViewEntity viewData = new ReaderInfoMenuViewEntity();
                    viewData.setReaderInfo(returnData);
                    ReaderCardDbEntity preferCard = readerCardBiz.obtainPreferCard();
                    viewData.setReaderCard(preferCard);

                    view.setView(viewData);
                } else {
                    view.otherView(null, -1);
                }
            }
        };

        task.execute(readerDbData);
    }
}
