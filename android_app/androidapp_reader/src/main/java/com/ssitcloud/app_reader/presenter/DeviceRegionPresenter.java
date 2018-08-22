package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.biz.RegionBizI;
import com.ssitcloud.app_reader.biz.impl.RegionBizImpl;
import com.ssitcloud.app_reader.entity.DeviceRegionEntity;
import com.ssitcloud.app_reader.view.viewInterface.StandardViewI;

import java.net.SocketException;
import java.util.List;

/**
 * Created by LXP on 2017/5/5.
 *
 */

public class DeviceRegionPresenter {
    private Context mcontext;
    private StandardViewI<List<DeviceRegionEntity>> view;
    private RegionBizI regionBiz;

    public DeviceRegionPresenter(Context context, StandardViewI<List<DeviceRegionEntity>> view){
        mcontext = context.getApplicationContext();
        this.view = view;
        regionBiz = new RegionBizImpl(mcontext);
    }

    public void loadData(Integer lib_idx){
        AsyncTask<Integer,Void,List<DeviceRegionEntity>> task = new AsyncTask<Integer, Void, List<DeviceRegionEntity>>() {
            private volatile int state = 0;
            @Override
            protected List<DeviceRegionEntity> doInBackground(Integer... params) {
                try {
                    List<DeviceRegionEntity> data = regionBiz.deviceRegionList(params[0]);
                    state = 1;
                    return data;
                } catch (SocketException e) {
                    state = -1;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<DeviceRegionEntity> deviceRegionEntities) {
                if(state == 1){
                    view.setView(StandardViewI.Standard_State.SUCCESS,deviceRegionEntities);
                }else {
                    view.setView(StandardViewI.Standard_State.NETOWRK_ERROR,null);
                }
            }
        };
        task.execute(lib_idx);
    }
}
