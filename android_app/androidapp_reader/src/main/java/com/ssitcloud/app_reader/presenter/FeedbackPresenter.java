package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.ssitcloud.app_reader.biz.FeedbackBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.impl.FeedbackBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.entity.FeedbackEntity;
import com.ssitcloud.app_reader.view.viewInterface.StandardViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;

/**
 * Created by LXP on 2017/4/19
 * 意见反馈presenter
 */

public class FeedbackPresenter {
    private SoftReference<StandardViewI<Void>> viewRef;
    private LoginBizI loginBiz;
    private FeedbackBizI feedbackBiz;

    public FeedbackPresenter(StandardViewI<Void> view, Context context){
        viewRef = new SoftReference<>(view);
        loginBiz = new LoginBizImpl(context.getResources(),context);
        feedbackBiz = new FeedbackBizImpl(context);
    }

    public void sendFeedback(FeedbackEntity feedbackEntity){
        final StandardViewI view = viewRef.get();
        if(view == null){
            return ;
        }

        Integer idx = loginBiz.isLogin();
        if(idx == null){
            view.setView(StandardViewI.Standard_State.AUTH_ERROR,1);
            return ;
        }
        AsyncTask<FeedbackEntity,Void,Void> task = new AsyncTask<FeedbackEntity, Void, Void>() {
            private volatile int state = 0;
            @Override
            protected Void doInBackground(FeedbackEntity... params) {
                try {
                    state = feedbackBiz.feedback(params[0])?1:2;
                } catch (SocketException e) {
                    state = -1;
                } catch (AuthException e) {
                    state = -2;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(state == -2){
                    view.setView(StandardViewI.Standard_State.AUTH_ERROR,null);
                }else if(state == -1){
                    view.setView(StandardViewI.Standard_State.NETOWRK_ERROR,null);
                }else if(state == 1){
                    view.setView(StandardViewI.Standard_State.SUCCESS,null);
                }else if(state == 2){
                    view.setView(StandardViewI.Standard_State.FAIL,null);
                }
            }
        };

        feedbackEntity.setReader_idx(idx);
        feedbackEntity.setUser_type("2");

        task.execute(feedbackEntity);
    }
}
