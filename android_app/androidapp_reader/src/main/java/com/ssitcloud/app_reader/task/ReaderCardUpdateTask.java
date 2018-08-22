package com.ssitcloud.app_reader.task;

import android.content.Context;
import android.util.Log;

import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;

import java.net.SocketException;

/**
 * Created by LXP on 2017/3/20.
 * 读者卡更新服务，会间接更新读者数据
 */

public class ReaderCardUpdateTask extends AbstractSafeTask {
    private final String TAG = "ReaderCardUpdateTask";
    private Context context;
    private ReaderCardBizI readerCardBiz;
    private LoginBizI loginBiz;
    public ReaderCardUpdateTask(Context context){
        this.context = context.getApplicationContext();
        readerCardBiz = new ReaderCardBizImpl(context.getResources(),this.context);
        loginBiz = new LoginBizImpl(context.getResources(),this.context);
    }

    @Override
    public void task() {
        Integer reader_idx = loginBiz.isLogin();
        if (reader_idx == null) {
            Log.d(TAG, "user is unlogin");
            return;
        }

        try {
            readerCardBiz.obtainReaderCardByService(reader_idx);
            Log.d(TAG, "ReaderCardBizI.obtainReaderCardByService success");
        } catch (SocketException e) {
            Log.d(TAG, "ReaderCardBizI.obtainReaderCardByService network error");
        } catch (AuthException e) {

        }
    }
}
