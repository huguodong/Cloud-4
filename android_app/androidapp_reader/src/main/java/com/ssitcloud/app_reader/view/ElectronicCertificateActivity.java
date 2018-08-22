package com.ssitcloud.app_reader.view;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.DisplayUtil;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.AppElectronicEntity;
import com.ssitcloud.app_reader.myview.RefreshScrollView;
import com.ssitcloud.app_reader.presenter.ElectronicCertificatePresenter;
import com.ssitcloud.app_reader.task.ElecNoticeTask;
import com.ssitcloud.app_reader.view.viewInterface.ElectronicCertificateViewI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by LXP on 2017/3/30.
 * 电子凭证视图
 */

public class ElectronicCertificateActivity extends BaseActivity implements ElectronicCertificateViewI {
    private LayoutInflater inflater;
    private RefreshScrollView refreshScrollView;

    private int pageCurrent = 1;
    private int pageSize = 5;
    private Date todayDate;
    private Date yesterdayDate;
    private ElectronicCertificatePresenter elecPresenter;
    private ArrayList<Integer> alreadyData = new ArrayList<>(16);//已经渲染过的电子凭证

    private View waitView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_certificate);
        elecPresenter = new ElectronicCertificatePresenter(this,this);

        refreshScrollView = (RefreshScrollView) findViewById(R.id.v);
        waitView = findViewById(R.id.waitView);
        waitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });
        //*******************************************************************************
        //init RefreshScrollView
        inflater = LayoutInflater.from(this);
        View onR = inflater.inflate(R.layout.sub_refreshing_tip_view,null);
        refreshScrollView.setOnRefreshView(onR);
        View tipR = inflater.inflate(R.layout.sub_refresh_tip_view,null);
        refreshScrollView.setRefreshViewTipView(tipR);
        View successR = inflater.inflate(R.layout.sub_refresh_success_view,null);
        refreshScrollView.setRefreshSuccessView(successR);
        View successNDR = inflater.inflate(R.layout.sub_refresh_success_no_data_view,null);
        refreshScrollView.setRefreshSuccessNoDataView(successNDR);

        refreshScrollView.setRefreshListener(new RefreshScrollView.RefreshListener() {
            @Override
            public void refreshListener() {
                elecPresenter.loadData(null,pageSize,pageCurrent);
            }
        });
        //*******************************************************************************
        //计算今天日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        todayDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        yesterdayDate = calendar.getTime();
        //*******************************************************************************
        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElectronicCertificateActivity.this.finish();
            }
        });

        waitView.setVisibility(View.VISIBLE);
        elecPresenter.loadData(null,pageSize,pageCurrent);

        elecPresenter.setReader();
    }

    @Override
    public void setData(List<AppElectronicEntity> elecListData) {
        waitView.setVisibility(View.INVISIBLE);

        ViewGroup contentView = refreshScrollView.getContentView();
        boolean addView = false;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margins = DisplayUtil.dp2px(this,10);
        lp.setMargins(margins,margins,margins,0);
        long lastDate = -1;
        List<View> vList = new ArrayList<>(elecListData.size()*2);

        long elecTimeInterval = 1 * 60 * 1000;
        for (int i = elecListData.size() - 1; i >= 0; --i) {
            AppElectronicEntity elec = elecListData.get(i);
            if(!alreadyData.contains(elec.getElectronic_idx())) {//判断是否已经渲染过
                //填充日期
                View dv = null;
                if (lastDate == -1) {
                    dv = elecDateViewFactory(elec, inflater, todayDate, yesterdayDate);
                } else {
                    long t = elec.getControl_time().getTime();
                    if (Math.abs(t - lastDate) > elecTimeInterval) {
                        dv = elecDateViewFactory(elec, inflater, todayDate, yesterdayDate);
                    }
                }
                lastDate = elec.getControl_time().getTime();
                //填充正文
                View v = elecViewFactory(elec, inflater);
                //加入填充数组
                if(dv != null){
                    vList.add(dv);
                }
                vList.add(v);
                //添加标记
                alreadyData.add(elec.getElectronic_idx());
                addView = true;
            }
        }
        //填充视图到正文
        if(addView){
            boolean flat = false;
            for (int i = 0,z = vList.size(); i < z; i++) {
                View view = vList.get(i);
                if(contentView.getChildCount() == 0) {
                    flat = true;
                }
                if(flat && i == z-1){
                    LinearLayout.LayoutParams lpF = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lpF.setMargins(margins,margins,margins,margins);
                    contentView.addView(view, i, lpF);
                }else{
                    contentView.addView(view, i, lp);
                }
            }
        }
        if(!elecListData.isEmpty()) {//收到新数据，页码加1
            ++pageCurrent;
            Log.d("elec view"," "+pageCurrent);
        }
        if(!addView){
            refreshScrollView.setsetRefreshNoDataSuccess();
        }else {
            refreshScrollView.setRefreshSuccess();
        }
    }

    @Override
    public void setFail(int code) {
        waitView.setVisibility(View.INVISIBLE);
        if(code == -1){
            logout();
            Toast.makeText(this,"请重新登陆",Toast.LENGTH_SHORT).show();
        }else if(code == -2){
            refreshScrollView.hideRefresh();
            Toast.makeText(this,"网络连接失败",Toast.LENGTH_SHORT).show();
        }
    }

    private View elecViewFactory(AppElectronicEntity elec, LayoutInflater inflater){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        View elecView = inflater.inflate(R.layout.sub_elec_normal,null);
        TextView elecTitle = (TextView) elecView.findViewById(R.id.elecTitle);
        TextView elecDate = (TextView) elecView.findViewById(R.id.elecDate);
        TextView elecContent = (TextView) elecView.findViewById(R.id.elecContent);
        TextView ibraryName = (TextView) elecView.findViewById(R.id.libraryName);

        elecTitle.setText(elec.getTitle());
        elecDate.setText(sdf.format(elec.getControl_time()));
        elecContent.setText(Html.fromHtml(elec.getConetent()));
        ibraryName.setText(StringUtils.getStr(elec.getLibraryName(),"暂无"));

        return elecView;
    }

    private View elecDateViewFactory(AppElectronicEntity elec, LayoutInflater inflater,Date todayDate/*今天的日期，0时0分0秒*/,Date yesterdayDate/*昨天的日期，0时0分0秒*/){
        View v = inflater.inflate(R.layout.sub_elec_time_view,null);
        TextView dv = (TextView) v.findViewById(R.id.elecTime);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        if(todayDate.before(elec.getControl_time())){
            dv.setText(sdf.format(elec.getControl_time()));
        }else if(yesterdayDate.before(elec.getControl_time())){
            dv.setText("昨天 "+sdf.format(elec.getControl_time()));

        }else{
            sdf.applyPattern("yyyy-MM-dd HH:mm");
            dv.setText(sdf.format(elec.getControl_time()));
        }
        return v;
    }
}
