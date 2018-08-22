package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.biz.RegionBizI;
import com.ssitcloud.app_reader.biz.impl.RegionBizImpl;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.RegionEntity;
import com.ssitcloud.app_reader.view.viewInterface.RegionViewI;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2017/4/28.
 *
 */

public class RegionPresenter {
    private RegionViewI view;
    private Context mcontext;

    private RegionBizI regionBiz;
    private List<RegionEntity> regions;

    public RegionPresenter(RegionViewI view, Context context){
        this.view = view;
        mcontext = context.getApplicationContext();
        regionBiz = new RegionBizImpl(context);
    }

    public void loadData(final String preferRegionCode, final String nationCode){
        AsyncTask<Void,Void,List<RegionEntity>> task = new AsyncTask<Void, Void, List<RegionEntity>>() {
            @Override
            protected List<RegionEntity> doInBackground(Void... params) {
                try {
                    List<RegionEntity> regionEntities = regionBiz.regionList(nationCode);
                    return regionEntities;
                } catch (SocketException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<RegionEntity> regionEntities) {
                if(view == null){
                    return ;
                }
                if(regionEntities == null){
                    view.setNetworkFail();
                    return ;
                }
                regions = regionEntities;
                List<RegionEntity> pL = new ArrayList<>(32);
                List<RegionEntity> cL = new ArrayList<>(64);
                List<RegionEntity> aL = new ArrayList<>(64);
                String pr = preferRegionCode;
                if(pr == null || pr.length() != 8){
                    pr = "01010101";
                }
                String c = pr.substring(0,6);
                String p = pr.substring(0,4);
                for (RegionEntity regionEntity : regionEntities) {
                    if(regionEntity.getRegi_code().length() == 8 && regionEntity.getRegi_code().startsWith(c)){
                        aL.add(regionEntity);
                    }else if(regionEntity.getRegi_code().length() == 6 && regionEntity.getRegi_code().startsWith(p)){
                        cL.add(regionEntity);
                    }else if(regionEntity.getRegi_code().length() == 4){
                        pL.add(regionEntity);
                    }
                }
                view.setProvince(pL);
                view.setCity(cL);
                view.setArea(aL);
                view.setSelectRegion(preferRegionCode);
            }
        };

        task.execute();
    }

    public void selectProvince(String regiCode){
        List<RegionEntity> cL = new ArrayList<>(64);
        List<RegionEntity> aL = new ArrayList<>(64);

        for (RegionEntity regionEntity : regions) {
            if(regionEntity.getRegi_code().length() == 8 && regionEntity.getRegi_code().startsWith(regiCode)){
                aL.add(regionEntity);
            }else if(regionEntity.getRegi_code().length() == 6 && regionEntity.getRegi_code().startsWith(regiCode)){
                cL.add(regionEntity);
            }
        }
        view.setCity(cL);
        view.setArea(aL);
    }

    public void selectCity(String regiCode){
        List<RegionEntity> aL = new ArrayList<>(64);

        for (RegionEntity regionEntity : regions) {
            if(regionEntity.getRegi_code().length() == 8 && regionEntity.getRegi_code().startsWith(regiCode)){
                aL.add(regionEntity);
            }
        }
        view.setArea(aL);
    }

}
