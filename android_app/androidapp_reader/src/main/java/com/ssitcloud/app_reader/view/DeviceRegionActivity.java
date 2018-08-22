package com.ssitcloud.app_reader.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.DeviceRegionEntity;
import com.ssitcloud.app_reader.entity.RegionEntity;
import com.ssitcloud.app_reader.entity.Tag;
import com.ssitcloud.app_reader.myview.TagListView;
import com.ssitcloud.app_reader.myview.TagView;
import com.ssitcloud.app_reader.presenter.DeviceRegionPresenter;
import com.ssitcloud.app_reader.view.viewInterface.StandardViewI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2017/5/5.
 * 设备地址视图
 */

public class DeviceRegionActivity extends Activity implements StandardViewI<List<DeviceRegionEntity>> {

    private List<DeviceRegionEntity> regions;

    private DeviceRegionPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_region);

        Intent i = getIntent();
        int lib_idx = i.getIntExtra("lib_idx",-1);
        if(lib_idx == -1){
            finish();
            return;
        }
        presenter = new DeviceRegionPresenter(this,this);

        TagListView tagListView = (TagListView)findViewById(R.id.tagListView);
        tagListView.setOnTagClickListener(new TagListView.OnTagClickListener() {
            @Override
            public void onTagClick(TagView tagView, Tag tag) {
                int index = tag.getId();
                RegionEntity region;
                if(index == -1){
                    region = new RegionEntity();
                }else{
                    region = regions.get(index);
                }
                Intent intent = new Intent();
                intent.putExtra("region",region);
                setResult(RESULT_OK,intent);
                DeviceRegionActivity.this.finish();
            }
        });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceRegionActivity.this.finish();
            }
        });

        presenter.loadData(lib_idx);
    }



    private String getRegionStr(DeviceRegionEntity dr){
        String s = "";
        int num = 0;
        if(!StringUtils.isEmpty(dr.getRegi_area())){
            s = dr.getRegi_area();
            num++;
        }
        if(!StringUtils.isEmpty(dr.getRegi_city())){
            if(num == 1){
                s = '-'+s;
            }
            s = dr.getRegi_city()+s;
            num++;
        }
        if(num <2 && !StringUtils.isEmpty(dr.getRegi_province())){
            if(!s.isEmpty()){
                s = '-'+s;
            }
            s = dr.getRegi_province()+s;
        }
        return s;
    }

    public void setTags(List<DeviceRegionEntity> regions) {
        this.regions = regions;
        List<Tag> tags = new ArrayList<>(regions.size());

        Tag all = new Tag();
        all.setId(-1);
        all.setTitle("全部");
        tags.add(all);

        for (int i = 0,z = regions.size(); i < z; i++) {
            DeviceRegionEntity dr = regions.get(i);
            Tag tag = new Tag();
            tag.setId(i);
            tag.setTitle(getRegionStr(dr));
            tags.add(tag);
        }

        TagListView tagListView = (TagListView) findViewById(R.id.tagListView);
        tagListView.setTags(tags);
    }

    @Override
    public void setView(Standard_State state, List<DeviceRegionEntity> deviceRegionEntities) {
        findViewById(R.id.waitView).setVisibility(View.INVISIBLE);
        if(state == Standard_State.SUCCESS){
            setTags(deviceRegionEntities);
        }else /*if(state == Standard_State.NETOWRK_ERROR)*/{
            findViewById(R.id.networkError).setVisibility(View.VISIBLE);
        }
    }
}
