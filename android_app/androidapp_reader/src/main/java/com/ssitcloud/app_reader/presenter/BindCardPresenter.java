package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.entity.BindCardEntity;
import com.ssitcloud.app_reader.view.viewInterface.BindCardViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;

/**
 * Created by LXP on 2017/3/11.
 *
 */

public class BindCardPresenter {
    private final String TAG="BindCardPresenter";

    private SoftReference<BindCardViewI> bindCardViewSr;
    private Context context;
    private ReaderCardBizI readerCardBiz;
    private LoginBizI loginBiz;
    
    public BindCardPresenter(BindCardViewI bindCardView, Context context){
        this.bindCardViewSr = new SoftReference<>(bindCardView);
        this.context = context.getApplicationContext();
        readerCardBiz = new ReaderCardBizImpl(context.getResources(),this.context);
        loginBiz = new LoginBizImpl(context.getResources(),this.context);
    }

    /**
     * 绑定卡
     * @param bindCardEntity
     */
    public void bindCard(BindCardEntity bindCardEntity){
        final BindCardViewI bindCardView = bindCardViewSr.get();
        final Integer reader_idx = loginBiz.isLogin();
        if(reader_idx == null){
            bindCardView.bindFail(-1);
            return ;
        }
        AsyncTask<BindCardEntity,Void,Integer> task = new AsyncTask<BindCardEntity, Void, Integer>() {
            @Override
            protected Integer doInBackground(BindCardEntity... params) {
                try {
                    int i = readerCardBiz.bindCard(params[0], reader_idx);
                    return i;
                }catch (AuthException e){
                    return -500;//需要重新登陆
                }catch (SocketException e) {
                    return 1;//net work error;
                }
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if(integer == 0){
                    bindCardView.bindSuccess();
                }else if(integer == 1){//网络连接失败
                    bindCardView.bindFail(-2);
                }else if(integer == -1){//卡无效
                    bindCardView.bindFail(-3);
                }else if(integer == -2){//卡密码错误
                    bindCardView.bindFail(-4);
                }else if(integer == -3){
                    bindCardView.bindFail(-5);
                }else if(integer == -4){
                    bindCardView.bindFail(-6);
                }else{
                    bindCardView.bindFail(-1);
                }
            }
        };
        
        task.execute(bindCardEntity);
    }
}
