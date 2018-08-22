package com.ssitcloud.app_operator.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.ssitcloud.app_operator.biz.ForgetPwdBizI;
import com.ssitcloud.app_operator.biz.impl.ForgetPwdBizImpl;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.view.viewInterface.ForgetPwdViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;

/**
 * 创建日期：2017/3/21 15:29
 *
 * @author shuangjunjie
 */

public class ForgetPwdPresenter {

    private final String TAG = "ForgetPwdPresenter";

    private SoftReference<ForgetPwdViewI> forgetPwdView;
    private Context context;
    private Resources resources;
    private ForgetPwdBizI forgetPwdBiz;

    public ForgetPwdPresenter(ForgetPwdViewI forgetPwdViewI, Context context) {
        this.forgetPwdView = new SoftReference<ForgetPwdViewI>(forgetPwdViewI);
        this.context = context.getApplicationContext();
        this.forgetPwdBiz = new ForgetPwdBizImpl(context.getResources(), this.context);
    }

    /**
     * 发送验证码
     *
     * @param mobile 手机号码
     */
    public void sendVcode(String mobile) {
        final ForgetPwdViewI forgetPwdV = forgetPwdView.get();
        if (null == forgetPwdV) {
            return;
        }
        AsyncTask<String, Void, ResultEntity> task = new AsyncTask<String, Void, ResultEntity>() {
            @Override
            protected ResultEntity doInBackground(String... params) {
                try {
                    return forgetPwdBiz.sendFoundVcodeVcode(params[0]);
                } catch (SocketException e) {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(ResultEntity data) {
                if (null == data) {//网络连接异常
                    forgetPwdV.sendVcode(ForgetPwdViewI.networkError);
                } else if ("1".equals(data.getMessage())) {//成功
                    forgetPwdV.sendVcode(ForgetPwdViewI.sendVcodeSuccess);
                    forgetPwdV.setPasswordType((String) data.getResult());
                } else if ("-1".equals(data.getMessage())) {//没有手机号对应用户
                    forgetPwdV.sendVcode(ForgetPwdViewI.sendVcodeUnknowUser);
                } else {//其他未知错误
                    forgetPwdV.sendVcode(ForgetPwdViewI.sendVcodeFail);
                }
            }
        };

        task.execute(mobile);
    }


    public void changePwd(String mobile, String vcode, String pwd) {
        final ForgetPwdViewI forgetPwdV = forgetPwdView.get();
        if (null == forgetPwdV) {
            return;
        }
        AsyncTask<Object, Void, ResultEntity> task = new AsyncTask<Object, Void, ResultEntity>() {
            @Override
            protected ResultEntity doInBackground(Object... params) {
                try {
                    return forgetPwdBiz.changePwdByVocde(((String) params[0]), (String) params[1], (String) params[2]);
                } catch (SocketException e) {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(ResultEntity data) {
                forgetPwdV.hideWait();
                if (data == null) {
                    forgetPwdV.changePwd(ForgetPwdViewI.networkError);
                }else if("-1".equals(data.getMessage())){
                    forgetPwdV.changePwd(ForgetPwdViewI.changePwdUnknowUser);
                }else if("-2".equals(data.getMessage())){
                    forgetPwdV.changePwd(ForgetPwdViewI.vcodeError);
                }else if("-3".equals(data.getMessage())){
                    forgetPwdV.changePwd(ForgetPwdViewI.vcodeOld);
                }else if("1".equals(data.getMessage())){
                    forgetPwdV.changePwd(ForgetPwdViewI.changePwdSuccess);
                }else{
                    forgetPwdV.changePwd(ForgetPwdViewI.networkError);
                }
            }
        };
        forgetPwdV.showWait();
        task.execute(mobile, vcode, pwd);
    }
}
