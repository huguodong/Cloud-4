package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.common.exception.UnDecodeBarcode;
import com.ssitcloud.app_reader.common.utils.BarcodeUtil;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.view.ScannerBarcodeActivity;

import java.net.SocketException;

/**
 * Created by LXP on 2017/5/8.
 *
 */

public class DecodeBarcodePresenter {

    private Context mcontext;
    private LoginBizI loginBiz;
    private ReaderCardBizI readerCardBiz;
    private ReaderBizI readerBiz;

    public DecodeBarcodePresenter(Context context){
        mcontext = context.getApplicationContext();
        loginBiz = new LoginBizImpl(mcontext.getResources(),mcontext);
        readerCardBiz = new ReaderCardBizImpl(mcontext.getResources(),mcontext);
        readerBiz = new ReaderBizImpl(mcontext);
    }

    /**
     * 解析二维码
     */
    public void decode(Bitmap bitmap, @NonNull DecodeCallBack callBack){
        DecodeBarcodeAsyncTask task = new DecodeBarcodeAsyncTask(callBack);
        task.execute(bitmap);
    }

    /**
     * 发送二维码数据到服务器
     */
    public void sendBarcode(final String barcode, @NonNull final SendBarcodeCallBack callBack){
        final LoginInfoDbEntity loginData= loginBiz.getLoginReturnData();
        if(loginData == null){
            callBack.result(SendBarcodeCallBack.SEND_BARCODE_STATE.auth_fail);
            return ;
        }
        final ReaderCardDbEntity card = readerCardBiz.obtainPreferCard();
        if(card == null){
            callBack.result(SendBarcodeCallBack.SEND_BARCODE_STATE.unbind_card);
            return;
        }

        AsyncTask<Void,Void,Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            private int state = 0;
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    boolean b = readerBiz.readerAuth(loginData, card, barcode);
                    state = 1;
                    return b;
                } catch (SocketException e) {
                    state = -1;
                } catch (AuthException e) {
                    state = -2;
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if(state == 1){
                    if(aBoolean){
                        callBack.result(SendBarcodeCallBack.SEND_BARCODE_STATE.success);
                    }else{
                        callBack.result(SendBarcodeCallBack.SEND_BARCODE_STATE.fail);
                    }
                }else if(state == -1){
                    callBack.result(SendBarcodeCallBack.SEND_BARCODE_STATE.network_error);
                }else if(state == -2){
                    callBack.result(SendBarcodeCallBack.SEND_BARCODE_STATE.auth_fail);
                }
            }
        };

        task.execute();
    }

    /**
     * 解码回调
     */
    public interface SendBarcodeCallBack{
        enum SEND_BARCODE_STATE{success,unbind_card,network_error,auth_fail,fail}
        void result(SEND_BARCODE_STATE state);
    }

    /**
     * 解码回调
     */
    public interface DecodeCallBack{
        /**
         * 解码结束后调用
         * @param s 解码成功返回字符串，失败返回null
         */
        void decodeResult(String s);
    }

    private class DecodeBarcodeAsyncTask extends AsyncTask<Bitmap, Void, String> {
        private DecodeCallBack callBack;

        DecodeBarcodeAsyncTask(@NonNull DecodeCallBack callBack){
            this.callBack = callBack;
        }

        @Override
        protected String doInBackground(Bitmap... params) {
            try {
                return BarcodeUtil.decode(params[0]);
            } catch (UnDecodeBarcode unDecodeBarcode) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            callBack.decodeResult(s);
        }
    }
}
