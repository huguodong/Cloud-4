package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.view.viewInterface.ReaderCardListViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LXP on 2017/3/9.
 *
 */

public class ReaderCardListPresenter {
    private final String TAG = "ReaderCardListPresenter";

    private Context context;
    private SoftReference<ReaderCardListViewI> readerCardView;
    private LoginBizI loginBiz;
    private ReaderCardBizI readerCardBiz;

    public ReaderCardListPresenter(ReaderCardListViewI readerCardView, Context context) {
        this.readerCardView = new SoftReference<>(readerCardView);
        this.context = context.getApplicationContext();
        this.loginBiz = new LoginBizImpl(context.getResources(), this.context);
        this.readerCardBiz = new ReaderCardBizImpl(context.getResources(), this.context);
    }

    public void loadData() {
        final ReaderCardListViewI v = readerCardView.get();
        if(v == null){//无引用
            return ;
        }
        Integer reader_idx = loginBiz.isLogin();
        //检查是否登陆
        if (reader_idx == null) {
            v.setMessageView(-1);
            return;
        }

        AsyncTask<Integer, Void, List<ReaderCardDbEntity>> task = new AsyncTask<Integer, Void, List<ReaderCardDbEntity>>() {
            private volatile int state = 0;

            @Override
            protected List<ReaderCardDbEntity> doInBackground(Integer... params) {
                try {
                    return readerCardBiz.obtainReaderCardByService(params[0]);
                } catch (SocketException e) {
                    Log.i(TAG, "networkException");
                } catch (AuthException e) {
                    state = -1;
                }
                return null;
            }

            @Override
            protected void onPostExecute(final List<ReaderCardDbEntity> data) {
                if(state == -1){
                    v.setUnlogin();
                    return ;
                }
                if (data == null) {//请求网络数据失败
//                        //读取数据库缓存数据
//                        List<ReaderCardDbEntity> dbData = readerCardBiz.obtainReaderCardByBb();
//                        if(v != null){
//                            v.setMessageView(-2);
//                            if(dbData.isEmpty()){
//                                v.setNetworkErrorView();
//                            }else {
//                                v.setBookView(dbData);
//                            }
//                        }
//                        v.setMessageView(-2);
                    v.setNetworkErrorView();
                } else {
                    v.setView(data);
                    ReaderCardDbEntity preferCard = readerCardBiz.obtainPreferCard();
                    v.setPreferCard(preferCard);
                }

            }
        };

        task.execute(reader_idx);

    }


    public void unbain(ReaderCardDbEntity cardInfo) {
        final Integer reader_idx = loginBiz.isLogin();
        ReaderCardListViewI v = readerCardView.get();//获取引用
        if (reader_idx == null) {
            if(v != null) {
                v.setMessageView(-1);
            }
            return;
        }
        if (cardInfo == null || cardInfo.getLib_idx() == null || cardInfo.getCard_no() == null) {
            if(v != null) {
                v.setMessageView(-3);
            }
            return;
        }
        AsyncTask<ReaderCardDbEntity, Void, ReaderCardDbEntity> task = new AsyncTask<ReaderCardDbEntity, Void, ReaderCardDbEntity>() {
            private volatile int state = 0;

            @Override
            protected ReaderCardDbEntity doInBackground(ReaderCardDbEntity... params) {
                try {
                    boolean state = readerCardBiz.unbindCard(params[0], reader_idx);
                    if (state) {
                        return params[0];
                    } else {
                        return null;
                    }
                } catch (SocketException e) {
                    //network error
                    Log.i(TAG, "network error");
                }catch (AuthException e){
                    state = -1;
                }
                return null;
            }

            @Override
            protected void onPostExecute(ReaderCardDbEntity readerInfo) {
                ReaderCardListViewI v = readerCardView.get();
                if(state == -1){
                    v.setUnlogin();
                    return ;
                }
                if (readerInfo == null) {
                    if(v != null) {
                        v.setMessageView(-2);
                    }
                }else {
                    if(v != null) {
                        v.deleteView(readerInfo);
                    }
                }
            }
        };
        task.execute(cardInfo);
    }
}
