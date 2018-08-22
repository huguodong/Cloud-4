package com.ssitcloud.app_reader.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.LibraryEntity;
import com.ssitcloud.app_reader.presenter.LibraryPresenter;
import com.ssitcloud.app_reader.view.viewInterface.LibraryListViewI;

import java.util.List;

/**
 * Created by LXP on 2017/3/10.
 * 用于选择图书馆的activity
 * intent:return resultCode == RESULT_OK ,library_info==>LibraryEntity
 */

public class LibraryListActivity extends BaseActivity implements LibraryListViewI{
    private LibraryPresenter libraryPersenter;

    private ViewGroup viewGroup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_list);
        libraryPersenter = new LibraryPresenter(this,this);

        viewGroup = (ViewGroup) findViewById(R.id.library_list);

        //load data
        libraryPersenter.loadData();
    }

    @Override
    public void setView(List<LibraryEntity> viewData) {
        LayoutInflater infalater = LayoutInflater.from(this);
        View.OnClickListener onClickListener = getOnClickListener();
        for (int i = 0,z = viewData.size(); i < z; i++) {
            LibraryEntity d = viewData.get(i);
            View v = infalater.inflate(R.layout.sub_library_info,null);
            TextView tv = (TextView) v.findViewById(R.id.library_name);
            tv.setText(StringUtils.getStr(d.getLib_name(),"暂无"));
            v.setTag(d);
            v.setOnClickListener(onClickListener);
            viewGroup.addView(v);
        }
    }

    @Override
    public void setMessageView(int code) {
        if (code == -1) {
            Toast.makeText(this, "未登陆", Toast.LENGTH_SHORT).show();
        } else if (code == -2) {
            Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
        }else if(code == -3){
            Toast.makeText(this, "请刷新后重试", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener getOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryEntity d = (LibraryEntity) v.getTag();
                Intent intent = new Intent();
                intent.putExtra("library_info",d);
                LibraryListActivity.this.setResult(RESULT_OK,intent);
                LibraryListActivity.this.finish();
            }
        };
    }
}
