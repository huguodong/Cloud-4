package com.ssitcloud.app_operator.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.UpdateOperatorInfoBizI;
import com.ssitcloud.app_operator.biz.impl.LoginBizImpl;
import com.ssitcloud.app_operator.biz.impl.UpdateOperatorInfoBizImpl;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.view.viewInterface.UpdateOperatorInfoViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.Map;

/**
 * 创建日期：2017/3/23 18:08
 * @author shuangjunjie
 */

public class UpdateOperatorInfoPresenter {

    private final String TAG = "UpdateOperatorInfoPresenter";

    private SoftReference<UpdateOperatorInfoViewI> updateOperatorInfoView;
    private Context context;
    private Resources resources;
    private UpdateOperatorInfoBizI updateOperatorInfoBiz;
    private LoginBizImpl loginBiz;

    public UpdateOperatorInfoPresenter(UpdateOperatorInfoViewI updateOperatorInfoViewI, Context context) {
        this.updateOperatorInfoView = new SoftReference<UpdateOperatorInfoViewI>(updateOperatorInfoViewI);
        this.context = context.getApplicationContext();
        this.updateOperatorInfoBiz = new UpdateOperatorInfoBizImpl(this.context,context.getResources());
        this.loginBiz= new LoginBizImpl(this.context.getResources(),this.context);
    }

    public void updateOperatorInfo(Map<String,Object> map){
        final UpdateOperatorInfoViewI updateOperatorInfoV = updateOperatorInfoView.get();
        if (null == updateOperatorInfoV) return ;
        resources = context.getResources();
        AsyncTask<Map<String,Object>,Void,ResultEntity> task = new AsyncTask<Map<String,Object>, Void, ResultEntity>() {
            @Override
            protected ResultEntity doInBackground(Map<String,Object>... params) {
                try {
                    return updateOperatorInfoBiz.updateOperatorInfo(params[0]);
                }catch (SocketException e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(ResultEntity data){
//                forgetPwdV.hideWait();
                if (null == data){
                    updateOperatorInfoV.updateFail(resources.getString(R.string.connect_network_fail));
                }else if (data.getState()){
                    updateOperatorInfoV.updateSuccess(data);
                }else {
                    updateOperatorInfoV.updateFail(data.getMessage());
                }
            }
        };

        task.execute(map);

    }
}
