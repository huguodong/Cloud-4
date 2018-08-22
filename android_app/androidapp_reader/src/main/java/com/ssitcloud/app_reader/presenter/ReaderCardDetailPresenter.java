package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.view.viewInterface.ReaderCardDetailViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;

/**
 * Created by LXP on 2017/3/23.
 *
 */

public class ReaderCardDetailPresenter {
    private Context mcontext;
    private SoftReference<ReaderCardDetailViewI> readerCardDetailView;
    private LoginBizI loginBiz;
    private ReaderCardBizI readerCardBiz;

    public ReaderCardDetailPresenter(ReaderCardDetailViewI view, Context context){
        readerCardDetailView = new SoftReference<>(view);
        mcontext = context.getApplicationContext();
        loginBiz = new LoginBizImpl(mcontext.getResources(),mcontext);
        readerCardBiz = new ReaderCardBizImpl(mcontext.getResources(),mcontext);
    }

    /**
     * 解绑卡
     * @param card
     */
    public void unBindCard(final ReaderCardDbEntity card){
        final Integer idx = loginBiz.isLogin();
        if(idx == null || card == null){
            return ;
        }
        final ReaderCardDetailViewI view = readerCardDetailView.get();
        if(view == null){
            return ;
        }
        AsyncTask<Void,Void,Integer> task = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    return readerCardBiz.unbindCard(card,idx)?0:1;
                } catch (SocketException e) {
                    return null;
                } catch (AuthException e) {
                    return -1;
                }
            }

            @Override
            protected void onPostExecute(Integer aInteger) {
                if(aInteger == null){
                    view.setMessageView(-2,1);
                }else if(aInteger == -1){
                    view.setMessageView(-1,1);
                }else{
                    view.setMessageView(aInteger,1);//0 or -1
                }
                view.hideWait();
            }
        };
        view.showWait();
        task.execute();
    }

    public void setPreferCard(ReaderCardDbEntity card){
        if(card == null){
            return;
        }
        readerCardBiz.setPreferCard(card);
        ReaderCardDetailViewI view = readerCardDetailView.get();
        if(view == null){
            return ;
        }
        view.setMessageView(0,2);
    }
}
