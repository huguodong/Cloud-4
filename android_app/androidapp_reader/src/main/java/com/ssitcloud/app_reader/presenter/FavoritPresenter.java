package com.ssitcloud.app_reader.presenter;

import android.content.Context;

import com.ssitcloud.app_reader.biz.FavoritBizI;
import com.ssitcloud.app_reader.biz.impl.FavoritBizImpl;
import com.ssitcloud.app_reader.db.entity.FavoritDbEntity;
import com.ssitcloud.app_reader.entity.BookUnionEntity;
import com.ssitcloud.app_reader.view.viewInterface.FavoritViewI;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by LXP on 2017/4/12.
 *
 */

public class FavoritPresenter {
    private SoftReference<FavoritViewI> viewRef;
    private Context mcontext;
    private FavoritBizI favoritBiz;

    public FavoritPresenter(FavoritViewI view, Context context){
        viewRef = new SoftReference<>(view);
        mcontext = context.getApplicationContext();
        favoritBiz = new FavoritBizImpl(mcontext);
    }

    public void loadData(){
        FavoritViewI view = viewRef.get();
        if(view == null ){
            return;
        }

        List<FavoritDbEntity> favoritDbEntities = favoritBiz.selectAll();
        List<FavoritViewI.FavoritViewEntity> viewData = new ArrayList<>(favoritDbEntities.size());

        for (FavoritDbEntity favoritDbEntity : favoritDbEntities) {
            boolean isExists = false;
            //查询是否已经存在分类
            for (FavoritViewI.FavoritViewEntity favoritViewEntity : viewData) {
                if(favoritViewEntity.opac.equals(favoritDbEntity.getOpac())){
                    favoritViewEntity.bookS.add(favoritDbEntity.getBue());
                    isExists = true;
                    break;
                }
            }
            //不存在分类，加入新分类
            if(!isExists){
                FavoritViewI.FavoritViewEntity favoritViewEntity = new FavoritViewI.FavoritViewEntity();
                favoritViewEntity.opac = favoritDbEntity.getOpac();
                favoritViewEntity.bookS = new ArrayList<>(5);
                favoritViewEntity.bookS.add(favoritDbEntity.getBue());
                viewData.add(favoritViewEntity);
            }
        }

        Collections.sort(viewData,new FavoritViewEntityComparator());
        //对书进行排序
        FavoritBookComparator bookComparator = new FavoritBookComparator();
        for (FavoritViewI.FavoritViewEntity favoritViewEntity : viewData) {
            Collections.sort(favoritViewEntity.bookS,bookComparator);
        }

        view.setView(viewData);
    }

    private class FavoritViewEntityComparator implements Comparator<FavoritViewI.FavoritViewEntity> {
        @Override
        public int compare(FavoritViewI.FavoritViewEntity o1, FavoritViewI.FavoritViewEntity o2) {
            return o1.opac.getDevName().compareTo(o2.opac.getDevName());
        }
    }

    private class FavoritBookComparator implements Comparator<BookUnionEntity> {
        @Override
        public int compare(BookUnionEntity o1, BookUnionEntity o2) {
            String title1 = String.valueOf(o1.getTitle());
            String title2 = String.valueOf(o2.getTitle());
            return title1.compareTo(title2);
        }
    }
}
