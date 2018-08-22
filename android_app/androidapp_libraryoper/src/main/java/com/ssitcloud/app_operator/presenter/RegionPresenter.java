package com.ssitcloud.app_operator.presenter;

import android.content.Context;
import android.graphics.Region;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ssitcloud.app_operator.biz.RegionBizI;
import com.ssitcloud.app_operator.biz.impl.RegionBizImpl;
import com.ssitcloud.app_operator.entity.RegionEntity;
import com.ssitcloud.app_operator.view.viewInterface.RegionViewI;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2017/4/28 14:01
 * @author shuangjunjie
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
//                    view.setNetworkFail();
                    Toast.makeText(mcontext, "没有找到区域", Toast.LENGTH_SHORT).show();
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

    public void loadDataForNormal(/*final String preferRegionCode, final String nationCode*/final List list){
        AsyncTask<Void,Void,List<RegionEntity>> task = new AsyncTask<Void, Void, List<RegionEntity>>() {
            @Override
            protected List<RegionEntity> doInBackground(Void... params) {
                try {
                    List<RegionEntity> regionEntities = regionBiz.regionListForNormal(list);
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
//                if(regionEntities == null){
//                    view.setNetworkFail();
//                    return ;
//                }
                regions = regionEntities;
                List<RegionEntity> pL = new ArrayList<>(32);
                List<RegionEntity> cL = new ArrayList<>(64);
                List<RegionEntity> aL = new ArrayList<>(64);
                String pr = null;
                if (null != regionEntities && regionEntities.size() > 0){

                    for (RegionEntity regionEntity : regionEntities) {
                        String temp = regionEntity.getRegi_code();
                        if(temp.length() == 8){
                            pr = temp;
                            break;
                        }
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
                }else {
                    Toast.makeText(mcontext, "没有找到区域", Toast.LENGTH_SHORT).show();
                }

                view.setProvince(pL);
                view.setCity(cL);
                view.setArea(aL);
//                view.setSelectRegion(preferRegionCode);
            }
        };

        task.execute();
    }

}
