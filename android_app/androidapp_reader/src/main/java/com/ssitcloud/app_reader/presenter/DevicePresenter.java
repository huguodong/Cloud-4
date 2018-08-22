package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.biz.DeviceBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.DeviceBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.AppOPACEntity;
import com.ssitcloud.app_reader.view.viewInterface.DeviceViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/6.
 * 设备页面presenter
 */

public class DevicePresenter {
    private Context mcontext;
    private ReaderCardBizI readerCardBiz;
    private DeviceBizI deviceBiz;
    private LoginBizI loginBiz;
    private SoftReference<DeviceViewI> deviceBizISoftRef;

    private enum data_source_state {device, self_lib}

    //当前数据源
    private data_source_state nowDataSource = data_source_state.device;
    private int devicePageCurrent = 1;//设备页数
    private int selfLibPageCurrent = 1;//自助图书馆页数
    private final int pageSize = 10;

    public DevicePresenter(DeviceViewI view, Context context) {
        mcontext = context.getApplicationContext();
        readerCardBiz = new ReaderCardBizImpl(mcontext.getResources(), mcontext);
        deviceBiz = new DeviceBizImpl(mcontext);
        loginBiz = new LoginBizImpl(mcontext.getResources(), mcontext);
        deviceBizISoftRef = new SoftReference<>(view);
    }

    public void loadData(String regionCode) {
        final DeviceViewI view = deviceBizISoftRef.get();
        if (view == null) {
            return;
        }
        //判断登陆
        Integer idx = loginBiz.isLogin();
        if (idx == null) {
            view.setFailView(DeviceViewI.FAIL_CODE.unlogin);
            return;
        }
        //判断是否绑定卡
        ReaderCardDbEntity rcDb = readerCardBiz.obtainPreferCard();
        if (rcDb == null) {
            view.setFailView(DeviceViewI.FAIL_CODE.ubbain_card);
            return;
        }

        Async async = new Async(view, mcontext, nowDataSource) {

            @Override
            protected void onPostExecute(List<AppOPACEntity> appOPACEntities) {
                super.onPostExecute(appOPACEntities);
                if (state == 1) {//判断是否获取了数据
                    if (model == data_source_state.device && appOPACEntities.isEmpty()) {
                        //使用自助图书馆加载一次
                        nowDataSource = data_source_state.self_lib;//转换为自助图书馆模式
                        Async selfLibAsync = new Async(view, mcontext, nowDataSource) {
                            protected void onPostExecute(List<AppOPACEntity> appOPACEntities) {
                                super.onPostExecute(appOPACEntities);
                                if (state == -2) {//网络连接失败
                                    view.setFailView(DeviceViewI.FAIL_CODE.network_error);
                                } else if (state == 1) {//判断是否获取了数据
                                    if (!appOPACEntities.isEmpty()) {
                                        nextPage();
                                    }
                                    view.setSuccessView(appOPACEntities, DeviceViewI.DATA_STATE.full);
                                }
                            }
                        };
                        Map<String, Object> data = lastData != null ? lastData : new HashMap<String, Object>(8);
                        data.put("pageCurrent", selfLibPageCurrent);
                        data.put("pageSize", pageSize);
                        selfLibAsync.execute(data);
                    } else {
                        //翻页
                        if (!appOPACEntities.isEmpty()) {
                            nextPage();
                        }
                        DeviceViewI.DATA_STATE state;
                        if (nowDataSource == data_source_state.device
                                && appOPACEntities.size() != pageSize) {
                            state = DeviceViewI.DATA_STATE.partial;
                            //使用自助图书馆加载一次
                            nowDataSource = data_source_state.self_lib;//转换为自助图书馆模式
                            Async selfLibAsync = new Async(view, mcontext, nowDataSource) {
                                protected void onPostExecute(List<AppOPACEntity> appOPACEntities) {
                                    super.onPostExecute(appOPACEntities);
                                    if (state == -2) {//网络连接失败
                                        view.setFailView(DeviceViewI.FAIL_CODE.network_error);
                                    } else if (state == 1) {//判断是否获取了数据
                                        if (!appOPACEntities.isEmpty()) {
                                            nextPage();
                                        }
                                        view.setSuccessView(appOPACEntities, DeviceViewI.DATA_STATE.full);
                                    }
                                }
                            };
                            Map<String, Object> data = lastData != null ? lastData : new HashMap<String, Object>(8);
                            data.put("pageCurrent", selfLibPageCurrent);
                            data.put("pageSize", pageSize);
                            selfLibAsync.execute(data);
                        } else {
                            state = DeviceViewI.DATA_STATE.full;
                        }
                        view.setSuccessView(appOPACEntities, state);
                    }
                } else if (state == -2) {//网络连接失败
                    view.setFailView(DeviceViewI.FAIL_CODE.network_error);
                }
            }
        };

        //init params
        Map<String, Object> param = new HashMap<>(8);
        Map<String, Object> selectData = new HashMap<>(8);
        if(regionCode != null) {
            selectData.put("regionCode", regionCode);
        }
        param.put("selectMap", selectData);
        param.put("lib_idx", rcDb.getLib_idx());
        if (nowDataSource == data_source_state.device) {
            param.put("pageCurrent", devicePageCurrent);
            param.put("pageSize", pageSize);
        } else if (nowDataSource == data_source_state.self_lib) {
            param.put("pageCurrent", selfLibPageCurrent);
            param.put("pageSize", pageSize);
        }
        //execute
        async.execute(param);
    }


    public void loadAllData(String seachStr,String regionCode) {
        final DeviceViewI view = deviceBizISoftRef.get();
        if (view == null) {
            return;
        }
        //判断登陆
        Integer idx = loginBiz.isLogin();
        if (idx == null) {
            view.setFailView(DeviceViewI.FAIL_CODE.unlogin);
            return;
        }
        //判断是否绑定卡
        ReaderCardDbEntity rcDb = readerCardBiz.obtainPreferCard();
        if (rcDb == null) {
            view.setFailView(DeviceViewI.FAIL_CODE.ubbain_card);
            return;
        }
        AsyncTask<Map<String, Object>, Void, List<AppOPACEntity>> task = new AsyncTask<Map<String, Object>, Void, List<AppOPACEntity>>() {
            private volatile int state = 0;

            @Override
            protected List<AppOPACEntity> doInBackground(Map<String, Object>... params) {
                Map<String, Object> selectMap = (Map<String, Object>) params[0].get("selectMap");
                Integer lib_idx = (Integer) params[0].get("lib_idx");
                try {
                    List<AppOPACEntity> opacs = deviceBiz.searchDevice(selectMap, lib_idx);
                    state = 1;
                    return opacs;
                } catch (SocketException e) {
                    state = -2;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<AppOPACEntity> appOPACEntities) {
                if(state == -2){
                    view.setFailView(DeviceViewI.FAIL_CODE.network_error);
                }else if(state == 1){
                    view.setSearchView(appOPACEntities, DeviceViewI.DATA_STATE.full);
                }
            }
        };

        //init params
        Map<String, Object> param = new HashMap<>(8);
        Map<String, Object> selectData = new HashMap<>(8);
        if(regionCode != null) {
            selectData.put("regionCode", regionCode);
        }
        selectData.put("search_str",seachStr);
        param.put("selectMap", selectData);
        param.put("lib_idx", rcDb.getLib_idx());
        if (nowDataSource == data_source_state.device) {
            param.put("pageCurrent", devicePageCurrent);
            param.put("pageSize", pageSize);
        } else if (nowDataSource == data_source_state.self_lib) {
            param.put("pageCurrent", selfLibPageCurrent);
            param.put("pageSize", pageSize);
        }
        //execute
        task.execute(param);
    }

    public Integer getLibIdx(){
        ReaderCardDbEntity rcDb = readerCardBiz.obtainPreferCard();
        if (rcDb == null) {
            return -1;
        }else{
            return rcDb.getLib_idx();
        }
    }

    public void resetState() {
        //重置状态变量
        nowDataSource = data_source_state.device;
        devicePageCurrent = 1;
        selfLibPageCurrent = 1;
    }

    private void nextPage() {
        if (data_source_state.device == nowDataSource) {
            ++devicePageCurrent;
        } else if (data_source_state.self_lib == nowDataSource) {
            ++selfLibPageCurrent;
        }
    }

    private static class Async extends AsyncTask<Map<String, Object>, Void, List<AppOPACEntity>> {
        private DeviceBizI deviceBiz;

        protected SoftReference<DeviceViewI> deviceBizISoftRef;
        protected volatile int state = 0;
        protected data_source_state model;
        protected Map<String, Object> lastData;//上一次执行的数据

        public Async(DeviceViewI view, Context context, data_source_state model) {
            deviceBizISoftRef = new SoftReference<>(view);
            deviceBiz = new DeviceBizImpl(context);
            this.model = model;
        }

        @Override
        protected List<AppOPACEntity> doInBackground(Map<String, Object>... params) {
            if (deviceBizISoftRef.get() == null) {
                return null;
            }
            lastData = params[0];
            Map<String, Object> selectMap = (Map<String, Object>) params[0].get("selectMap");
            Integer pageCurrent = (Integer) params[0].get("pageCurrent");
            Integer pageSize = (Integer) params[0].get("pageSize");
            Integer lib_idx = (Integer) params[0].get("lib_idx");
            try {
                if (model == data_source_state.device) {//获取设备
                    List<AppOPACEntity> opacDevices = deviceBiz.findOpacDevice(selectMap, lib_idx, pageCurrent, pageSize);
                    state = 1;
                    return opacDevices;
                } else if (model == data_source_state.self_lib) {//获取24小时自助图书馆
                    List<AppOPACEntity> opacDevices = deviceBiz.findOpacSelfHelpLibrary(selectMap, lib_idx, pageCurrent, pageSize);
                    state = 1;
                    return opacDevices;
                }
            } catch (SocketException e) {
                state = -2;
            }
            return null;
        }
    }
}
