package com.ssitcloud.app_reader.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.entity.RegionEntity;
import com.ssitcloud.app_reader.presenter.RegionPresenter;
import com.ssitcloud.app_reader.view.viewInterface.RegionViewI;

import java.util.List;

public class RegionActivity extends AppCompatActivity implements RegionViewI {
    private ViewGroup provinceVg;
    private ViewGroup cityVg;
    private ViewGroup areaVg;
    private View waitView;
    private View networkLost;

    private LayoutInflater inflater;

    private RegionPresenter presenter;

    private String selectCityColor = "#EAEAEA";
    private String selectProinceColor = "#F5F5F5";

    private View.OnClickListener prvoinceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RegionEntity tag = (RegionEntity) v.getTag();
            v.setBackgroundColor(Color.parseColor(selectProinceColor));
            for(int i = 0,z = provinceVg.getChildCount();i<z;++i){
                View v2 = provinceVg.getChildAt(i);
                if(!v2.equals(v)){
                    v2.setBackgroundColor(Color.TRANSPARENT);
                }
            }
            presenter.selectProvince(tag.getRegi_code());
        }
    };

    private View.OnClickListener cityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RegionEntity tag = (RegionEntity) v.getTag();
            v.setBackgroundColor(Color.parseColor(selectCityColor));
            for(int i = 0,z = cityVg.getChildCount();i<z;++i){
                View v2 = cityVg.getChildAt(i);
                if(!v2.equals(v)){
                    v2.setBackgroundColor(Color.TRANSPARENT);
                }
            }
            presenter.selectCity(tag.getRegi_code());
        }
    };

    private View.OnClickListener areaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RegionEntity tag = (RegionEntity) v.getTag();
            Intent i = new Intent();
            i.putExtra("region",tag);
            setResult(RESULT_OK,i);
            RegionActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        inflater = LayoutInflater.from(this);

        provinceVg = (ViewGroup) findViewById(R.id.provinceVg);
        cityVg = (ViewGroup) findViewById(R.id.cityVg);
        areaVg = (ViewGroup) findViewById(R.id.areaVg);
        presenter = new RegionPresenter(this,this);

        waitView = findViewById(R.id.waitView);
        waitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });

        networkLost = findViewById(R.id.networkError);
        networkLost.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegionActivity.this.finish();
            }
        });

        presenter.loadData(null,"");
    }

    @Override
    public void setProvince(List<RegionEntity> provinces) {
        waitView.setVisibility(View.GONE);
        provinceVg.removeAllViews();

        ViewGroup.LayoutParams pm = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (RegionEntity province : provinces) {
            View tv = getTv(province,province.getRegi_province(),1);
            provinceVg.addView(tv,pm);
        }

        if(provinceVg.getChildCount()>0){
            provinceVg.getChildAt(0).setBackgroundColor(Color.parseColor(selectProinceColor));
        }
    }

    @Override
    public void setCity(List<RegionEntity> citys) {
        waitView.setVisibility(View.GONE);

        ViewGroup.LayoutParams pm = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int childCount = cityVg.getChildCount();
        for (int i = 0,z = citys.size(); i < z; i++) {
            RegionEntity city = citys.get(i);
            if(i < childCount){
                View tv = cityVg.getChildAt(i);
                getTv(tv,city,city.getRegi_city(),2);
            }else{
                View tv = getTv(city,city.getRegi_city(),2);
                cityVg.addView(tv,pm);
            }
        }
        //调整视图组容量
        childCount = cityVg.getChildCount();
        for (int i = citys.size(); i < childCount; i++) {
            cityVg.getChildAt(i).setVisibility(View.GONE);
        }


        if(cityVg.getChildCount()>0){
            cityVg.getChildAt(0).setBackgroundColor(Color.parseColor(selectCityColor));
        }
    }

    @Override
    public void setArea(List<RegionEntity> areas) {
        waitView.setVisibility(View.GONE);

        ViewGroup.LayoutParams pm = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int childCount = areaVg.getChildCount();
        for (int i = 0,z = areas.size(); i < z; i++) {
            RegionEntity area = areas.get(i);
            if(i < childCount){
                View tv = areaVg.getChildAt(i);
                getTv(tv,area,area.getRegi_area(),3);
            }else{
                View tv = getTv(area,area.getRegi_area(),3);
                areaVg.addView(tv,pm);
            }
        }

        //调整视图组容量
        childCount = areaVg.getChildCount();
        for (int i = areas.size(); i < childCount; i++) {
            areaVg.getChildAt(i).setVisibility(View.GONE);
        }
    }

    @Override
    public void setNetworkFail() {
        waitView.setVisibility(View.GONE);
        networkLost.setVisibility(View.VISIBLE);
    }

    private View getTv(RegionEntity region, String showText, int state/*1:province 2:city 3:area*/) {
        return getTv(null, region, showText, state);
    }

    private View getTv(View v, RegionEntity region, String showText, int state/*1:province 2:city 3:area*/) {
        if (v == null) {
            v = inflater.inflate(R.layout.sub_region_tv, null);
        }else{
            if(v.getVisibility() != View.VISIBLE){
                v.setVisibility(View.VISIBLE);
            }
            v.setBackgroundColor(Color.TRANSPARENT);
        }
        TextView tv = (TextView) v.findViewById(R.id.tv);
        tv.setText(showText);
        v.setTag(region);

        if (state == 1) {
            v.setOnClickListener(prvoinceListener);
        } else if (state == 2) {
            v.setOnClickListener(cityListener);
        } else if (state == 3) {
            v.setOnClickListener(areaListener);
        }
        return v;
    }

    @Override
    public void setSelectRegion(String regionCode) {

    }
}
