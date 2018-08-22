package com.ssitcloud.app_reader.presenter;

import android.content.Context;

import com.ssitcloud.app_reader.biz.AppSettingBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.AppSettingBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.AppSettingEntity;
import com.ssitcloud.app_reader.view.viewInterface.ContentViewI;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2017/3/18.
 * 主页视图Presenter
 */

public class ContentViewPresenter {
    private SoftReference<ContentViewI>  contentView;
    private Context mcontext;
    private ReaderCardBizI readerCardBiz;
    private LoginBizI loginBiz;
    private AppSettingBizI appSettingBiz;

    public ContentViewPresenter(ContentViewI contentViewI, Context context){
        contentView = new SoftReference<ContentViewI>(contentViewI);
        mcontext = context.getApplicationContext();
        readerCardBiz = new ReaderCardBizImpl(mcontext.getResources(),mcontext);
        loginBiz = new LoginBizImpl(mcontext.getResources(),mcontext);
        appSettingBiz=new AppSettingBizImpl(mcontext);
    }

    public void loadData(){
        ContentViewI view = contentView.get();
        if(view == null){
            return;
        }
        LoginInfoDbEntity readerInfoData = loginBiz.getLoginReturnData();
        if(readerInfoData == null){
            view.otherView(null,-1);
            return;
        }
        Map<String, Object> data = new HashMap<>();
        if(!view.alreadySetContentView()){
            data.put("LoginInfoDbEntity",readerInfoData);
            view.setView(data);
        }
        view.showWait();

        ReaderCardDbEntity readerCardDbEntity = readerCardBiz.obtainPreferCard();
        if(readerCardDbEntity != null){
            view.setFouctionView(appSettingBiz.obtainAppSetting(readerCardDbEntity.getLib_idx()));
        }else {
            view.setFouctionView(new ArrayList<AppSettingEntity>(0));
        }

        //设置其他数据
        data.put("ReaderCardDbEntity",readerCardDbEntity);
        data.put("LoginInfoDbEntity",readerInfoData);
        view.setView(data);

        view.hideWait();
    }
}
