package com.ssitcloud.app_operator.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.DeviceMonitorBizI;
import com.ssitcloud.app_operator.biz.impl.DeviceMonitorBizImpl;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.view.viewInterface.DeviceMonitorViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/4/5 16:23
 * @author shuangjunjie
 */

public class DeviceMonitorPresenter {

    private final String TAG = "DeviceMonitorPresenter";
    private SoftReference<DeviceMonitorViewI> deviceMonitorView;
    private Context context;
    private Resources resources;
    private DeviceMonitorBizI deviceMonitorBiz;

    public DeviceMonitorPresenter(DeviceMonitorViewI deviceMonitorViewI, Context context) {
        this.deviceMonitorView = new SoftReference<DeviceMonitorViewI>(deviceMonitorViewI);
        this.context = context.getApplicationContext();
        this.deviceMonitorBiz = new DeviceMonitorBizImpl(context.getResources(),this.context);
    }

//    public void selectLib(Map<String,Object> map){
//        final DeviceMonitorViewI deviceMonitorV = deviceMonitorView.get();
//        if (null == deviceMonitorV){
//            return ;
//        }
//
//        resources = context.getResources();
//        AsyncTask<Map<String,Object>,Void,Map> task = new AsyncTask<Map<String,Object>, Void, Map>() {
//            @Override
//            protected Map doInBackground(Map<String,Object>... params) {
//                try {
//                    return deviceMonitorBiz.selectLib(params[0]);
//                }catch (SocketException e){
//                    return null;
//                }
//
//            }
//
//            @Override
//            protected void onPostExecute(Map data){
////                forgetPwdV.hideWait();
//                if (null == data){
//                    deviceMonitorV.selectFail(resources.getString(R.string.connect_network_fail));
//                }else if ((Boolean) data.get("state")){
//                    deviceMonitorV.selectSuccess(data);
//                }else {
//                    deviceMonitorV.selectFail("查询失败");
//                }
//            }
//        };
////        forgetPwdV.showWait();
//
//        task.execute(map);
//    }

//    public void selectDeviceState(Map<String,Object> map){
//        final DeviceMonitorViewI deviceMonitorV = deviceMonitorView.get();
//        if (null == deviceMonitorV){
//            return ;
//        }
//
//        resources = context.getResources();
//        AsyncTask<Map<String,Object>,Void,ResultEntity> task = new AsyncTask<Map<String,Object>, Void, ResultEntity>() {
//            @Override
//            protected ResultEntity doInBackground(Map<String,Object>... params) {
//                try {
//                    return deviceMonitorBiz.selectDeviceState(params[0]);
//                }catch (SocketException e){
//                    return null;
//                }
//
//            }
//
//            @Override
//            protected void onPostExecute(ResultEntity data){
////                forgetPwdV.hideWait();
//                if (null == data){
//                    deviceMonitorV.getDeviceStateFail(resources.getString(R.string.connect_network_fail));
//                }else if (data.getState()){
//                    deviceMonitorV.getDeviceStateSuccess(data);
//                }else if ("1".equals(JsonUtils.toJson(data.getResult()))){
//                    deviceMonitorV.getDeviceStateFail("设备超时");
//                }else {
//                    deviceMonitorV.getDeviceStateFail(data.getMessage());
//                }
//            }
//        };
////        forgetPwdV.showWait();
//
//        task.execute(map);
//    }

//    public void querySlaveLibraryByMasterIdx(Map<String,Object> map){
//        final DeviceMonitorViewI deviceMonitorV = deviceMonitorView.get();
//        if (null == deviceMonitorV){
//            return ;
//        }
//
//        resources = context.getResources();
//        AsyncTask<Map<String,Object>,Void,ResultEntity> task = new AsyncTask<Map<String,Object>, Void, ResultEntity>() {
//            @Override
//            protected ResultEntity doInBackground(Map<String,Object>... params) {
//                try {
//                    return deviceMonitorBiz.querySlaveLibraryByMasterIdx(params[0]);
//                }catch (SocketException e){
//                    return null;
//                }
//
//            }
//
//            @Override
//            protected void onPostExecute(ResultEntity data){
////                forgetPwdV.hideWait();
//                if (null == data){
//                    deviceMonitorV.selectFail(resources.getString(R.string.connect_network_fail));
//                }else if (data.getState()){
//                    deviceMonitorV.selectSuccess(JsonUtils.fromJson(JsonUtils.toJson(data.getResult()),Map.class));
//                }else {
//                    deviceMonitorV.selectFail("查询失败");
//                }
//            }
//        };
////        forgetPwdV.showWait();
//
//        task.execute(map);
//    }

    public void SelectDeviceMgmtByLibraryIdxs(final Map<String,Object> map, final List<Integer> ids){
        final DeviceMonitorViewI deviceMonitorV = deviceMonitorView.get();
        final String flag = map.get("flag").toString();
        map.remove("flag");
        if (null == deviceMonitorV){
            return ;
        }
        resources = context.getResources();
        AsyncTask<Map<String,Object>,Void,ResultEntity> task = new AsyncTask<Map<String,Object>, Void, ResultEntity>() {
            @Override
            protected ResultEntity doInBackground(Map<String,Object>... params) {
                try {
                    return deviceMonitorBiz.selectDeviceMgmtByLibraryIdxs(params[0],ids);
                }catch (SocketException e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(ResultEntity data){
//                forgetPwdV.hideWait();
                if (null == data){
                    if (flag.equals("1")){
                        deviceMonitorV.SelectDeviceMgmtByPageFail(resources.getString(R.string.connect_network_fail));
                    }else{
                        deviceMonitorV.SelectDeviceMgmtByLibraryIdxsFail(resources.getString(R.string.connect_network_fail));
                    }
                }else if (data.getState()){
                    if (flag.equals("1")){
                        deviceMonitorV.SelectDeviceMgmtByPageSuccess(data);
                    }else{
                        deviceMonitorV.SelectDeviceMgmtByLibraryIdxsSuccess(data);
                    }
                }else {
                    if (flag.equals("1")){
                        deviceMonitorV.SelectDeviceMgmtByPageFail("获取失败");
                    }else{
                        deviceMonitorV.SelectDeviceMgmtByLibraryIdxsFail("获取失败");
                    }
                }
            }
        };
//        forgetPwdV.showWait();

        task.execute(map);
    }
}
