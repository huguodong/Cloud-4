package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.ElecBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.impl.ConfigBizImpl;
import com.ssitcloud.app_reader.biz.impl.ElecBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.entity.AppElectronicEntity;
import com.ssitcloud.app_reader.view.viewInterface.ElectronicCertificateViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

/**
 * Created by LXP on 2017/3/31.
 *
 */

public class ElectronicCertificatePresenter {
    private SoftReference<ElectronicCertificateViewI> elecViewRef;
    private Context mcontext;
    private ElecBizI elecBiz;
    private LoginBizI loginBiz;
    private ConfigBizI configBizI;

    public ElectronicCertificatePresenter(ElectronicCertificateViewI view, Context context){
        mcontext = context.getApplicationContext();
        elecViewRef = new SoftReference<>(view);
        elecBiz = new ElecBizImpl(mcontext);
        loginBiz = new LoginBizImpl(mcontext.getResources(),mcontext);
        configBizI = new ConfigBizImpl(mcontext);
    }

    public void loadData(final Date d,final int pageSize,final int pageCurrent){
        final ElectronicCertificateViewI view = elecViewRef.get();
        if(view == null){
            return;
        }
        final Integer idx = loginBiz.isLogin();
        if(idx == null){
            view.setFail(-1);
            return ;
        }
        AsyncTask<Void,Void,List<AppElectronicEntity>> task = new AsyncTask<Void, Void, List<AppElectronicEntity>>() {
            private int state = 0;
            @Override
            protected List<AppElectronicEntity> doInBackground(Void... params) {
                try {
                    return elecBiz.obtainElecByService(idx,d,pageSize,pageCurrent);
                } catch (SocketException e) {
                    state = -2;
                } catch (AuthException e) {
                    state = -1;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<AppElectronicEntity> appElectronicEntities) {
                if(state == -1){
                    view.setFail(-1);
                }else if(state == -2){
                    view.setFail(-2);
                }else{
                    view.setData(appElectronicEntities);
                }
            }
        };

        task.execute();
    }

    public void setReader(){
        configBizI.setElecNoticeCount(0);
    }
}
