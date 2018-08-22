package com.ssitcloud.app_operator.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.DeviceMaintainBizI;
import com.ssitcloud.app_operator.biz.impl.DeviceMaintainBizImpl;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.view.viewInterface.DeviceMaintainViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/4/15 11:31
 * @author shuangjunjie
 */

public class DeviceMaintainPresenter {

    private final String TAG = "DeviceMaintainPresenter";

    private final String charset = "utf-8";
    private Context context;
    private Resources resources;
    private SoftReference<DeviceMaintainViewI> deviceMaintainView;
    private DeviceMaintainBizI deviceMaintainBiz;

    public DeviceMaintainPresenter(DeviceMaintainViewI deviceMaintainViewI, Context context) {
        this.deviceMaintainView = new SoftReference<DeviceMaintainViewI>(deviceMaintainViewI);
        this.context = context.getApplicationContext();
        this.deviceMaintainBiz = new DeviceMaintainBizImpl(context.getResources(),this.context);
    }

    public void sendOrder(final List<Map> map){
        final DeviceMaintainViewI deviceMaintainV = deviceMaintainView.get();
        if (null == deviceMaintainV){
            return ;
        }
        resources = context.getResources();
        AsyncTask<List<Map>,Void,ResultEntity> task = new AsyncTask<List<Map>, Void, ResultEntity>() {
            @Override
            protected ResultEntity doInBackground(List<Map>... params) {
                try {
                    return deviceMaintainBiz.sendOrder(params[0]);
                }catch (SocketException e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(ResultEntity data){
                if (null == data){
                    deviceMaintainV.sendOrderFail(resources.getString(R.string.connect_network_fail));
                }else if (data.getState()){
                    deviceMaintainV.sendOrderSuccess("发送成功");
                }else {
                    deviceMaintainV.sendOrderFail("发送失败");
                }

            }
        };

        task.execute(map);



    }


}
