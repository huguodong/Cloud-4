package com.ssitcloud.app_reader.task;

import android.content.Context;
import android.util.Log;

import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

import java.net.SocketException;

/**
 * Created by LXP on 2017/3/27.
 *
 */

public class ReaderUpdateTask  extends AbstractSafeTask {
    private final String TAG = "ReaderUpdateTask";
    private Context context;
    private LoginBizI loginBiz;

    public ReaderUpdateTask(Context context){
        this.context = context.getApplicationContext();
        loginBiz = new LoginBizImpl(context.getResources(),this.context);
    }

    @Override
    public void task() {
        LoginInfoDbEntity readerDb = loginBiz.getLoginReturnData();
        if(readerDb != null){
            try {
                ReaderInfoEntity reader = loginBiz.obtainReader(readerDb.getReader_idx());
                if(reader == null){
                    loginBiz.logout();
                    Log.d(TAG,"reader unlogin");
                }else {
                    Log.d(TAG, "obtain reader success");
                }
            } catch (SocketException e) {
            } catch (AuthException e){
                loginBiz.logout();
                Log.d(TAG,"reader unlogin");
            }
        }
    }

}
