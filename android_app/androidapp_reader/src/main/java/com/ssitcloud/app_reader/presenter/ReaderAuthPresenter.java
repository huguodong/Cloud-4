package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.biz.BarcodeBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.BarcodeBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.biz.SecurityBizI;
import com.ssitcloud.app_reader.biz.impl.SecurityBizImpl;
import com.ssitcloud.app_reader.common.exception.NonExistentPublicKey;
import com.ssitcloud.app_reader.common.exception.UnLoginException;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.view.viewInterface.ReaderAuthViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/3/24.
 *
 */

public class ReaderAuthPresenter {
    private SoftReference<ReaderAuthViewI> authView;
    private LoginBizI loginBiz;
    private ReaderCardBizI readerCardBiz;
    private BarcodeBizI barcodeBiz;
    private SecurityBizI securityBiz;

    public  ReaderAuthPresenter(Context context, ReaderAuthViewI view){
        authView = new SoftReference<>(view);
        loginBiz = new LoginBizImpl(context.getResources(),context.getApplicationContext());
        readerCardBiz = new ReaderCardBizImpl(context.getResources(),context.getApplicationContext());
        barcodeBiz = new BarcodeBizImpl(context);
        securityBiz = new SecurityBizImpl(context);
    }

    public void loadData(){
        loadBorrorData(null);
    }

    public void loadBorrorData(List<String> barcodes){
        ReaderAuthViewI view = authView.get();
        if(view == null){
            return;
        }
        Map<String, Object> data = new HashMap<>();

        LoginInfoDbEntity reader = loginBiz.getLoginReturnData();
        if(reader == null){
            view.setFailView(-1);
            return;
        }

        data.put("readerName",reader.getReader_name());

        ReaderCardDbEntity card = readerCardBiz.obtainPreferCard();
        if(card == null){
            view.setOtherData(data);
            view.setFailView(-3);
            return ;
        }
        if(card.getLib_id() == null || card.getCardPwd() == null){
            view.setOtherData(data);
            view.setFailView(-5);
            return ;
        }

        try {
            Bitmap authBarcode;
            if(barcodes == null) {
                authBarcode = barcodeBiz.createAuthBarcode(card);
            }else{
                authBarcode = barcodeBiz.createBorrowBarcode(card,barcodes);
            }
            if(authBarcode != null) {
                view.setBarcode(authBarcode);
            }
        } catch (UnLoginException e) {
            view.setFailView(-1);
        } catch (NonExistentPublicKey nonExistentPublicKey) {
            view.setFailView(-4);
        }

        data.put("cardNo",card.getCard_no());
        data.put("libraryName",card.getLib_name());
        view.setOtherData(data);
    }

    public void updatePuk(){
        updatePuk(null);
    }

    public void updatePuk(final List<String> bookbars){
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            private volatile int state = 0;
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    state = securityBiz.updatePuk()?1:0;
                } catch (SocketException e) {
                    state = -1;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(bookbars == null){
                    ReaderAuthPresenter.this.loadData();
                }else{
                    ReaderAuthPresenter.this.loadBorrorData(bookbars);
                }

            }
        };

        task.execute();
    }
}
