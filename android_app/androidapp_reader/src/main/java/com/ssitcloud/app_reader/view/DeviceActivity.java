package com.ssitcloud.app_reader.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.impl.ConfigBizImpl;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.AppOPACEntity;
import com.ssitcloud.app_reader.entity.RegionEntity;
import com.ssitcloud.app_reader.myview.SearchEditText;
import com.ssitcloud.app_reader.presenter.DevicePresenter;
import com.ssitcloud.app_reader.view.viewInterface.DeviceViewI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2017/4/6.
 * 设备检索视图,选择设备后会关闭此视图并设置result intent{opac = AppOPACEntity.class}
 */

public class DeviceActivity extends BaseActivity implements DeviceViewI {
    private View waitView;

    private PullToRefreshListView normalPullToRefreshView;
    private DeviceAdapter normalAdapter;

    private View selectRegionView;
    private View searchView;//搜索视图
    private Animation searchViewOpenAnim;
    private Animation searchViewCloseAnim;
    private ListView searchListview;//搜索视图的数据视图
    private DeviceAdapter searchAdapter;
    private SearchEditText searchEdit;
    private Animation searchEditOpenAnim;
    private Animation searchEditCloseAnim;

    private DevicePresenter devicePresenter;

    private boolean isOnce = true;//是否为首次进入视图
    private boolean isRefresh = false;//是否为刷新模式
    private boolean isFinish = false;//此视图是否已经关闭
    private boolean isSearchModel = false;//是否为搜索模式

    private String regionCode = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seach_device);
        devicePresenter = new DevicePresenter(this,this);

        normalPullToRefreshView = (PullToRefreshListView) findViewById(R.id.normal_pull_to_refresh_listview);
        normalPullToRefreshView.setMode(PullToRefreshBase.Mode.BOTH);//两端刷新
        normalPullToRefreshView.getLoadingLayoutProxy(true,false).setPullLabel("继续滑动刷新");
        normalPullToRefreshView.getLoadingLayoutProxy(true,false).setReleaseLabel("放开以刷新");
        normalPullToRefreshView.getLoadingLayoutProxy(false,true).setPullLabel("继续滑动加载更多");
        normalPullToRefreshView.getLoadingLayoutProxy(false,true).setReleaseLabel("放开加载更多");
        //设置刷新监听
        normalPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nextPage();
            }
        });

        normalAdapter = new DeviceAdapter(this);
        normalAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppOPACEntity opac = (AppOPACEntity) v.getTag();
                //存入偏好数据库
                ConfigBizI configBiz = new ConfigBizImpl(DeviceActivity.this);
                configBiz.savePreferDevice(opac);

                //返回结果
                Intent resultIntent = new Intent();
                resultIntent.putExtra("opac",opac);
                setResult(RESULT_OK,resultIntent);
                DeviceActivity.this.finish();
            }
        });

        normalPullToRefreshView.setAdapter(normalAdapter);

        //设置搜索组件
        searchListview = (ListView) findViewById(R.id.search_listview);
        searchView = findViewById(R.id.search_view);
        searchViewOpenAnim = AnimationUtils.loadAnimation(this,R.anim.search_content_open_translate);
        searchViewCloseAnim = AnimationUtils.loadAnimation(this,R.anim.search_content_close_translate);
        searchEdit = (SearchEditText) findViewById(R.id.searchStr);
        searchEditOpenAnim = AnimationUtils.loadAnimation(this,R.anim.search_edittext_open_translate);
        searchEditCloseAnim = AnimationUtils.loadAnimation(this,R.anim.search_edittext_close_translate);

        searchAdapter = new DeviceAdapter(this);
        searchAdapter.setOnClickListener(normalAdapter.getOnClickListener());
        searchListview.setAdapter(searchAdapter);
        searchEdit.setIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ((EditText) v).getText().toString();
                if(!StringUtils.isEmpty(s)){
                    waitView.setVisibility(View.VISIBLE);
                    devicePresenter.loadAllData(s,regionCode);
                }else{
                    Toast.makeText(DeviceActivity.this,"请输入关键字",Toast.LENGTH_SHORT).show();
                }

            }
        });

        waitView = findViewById(R.id.waitView);
        waitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });

        //导航栏搜索图标
        View search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOnce && !isSearchModel){
                    searchModel();
                }
            }
        });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceActivity.this.finish();
            }
        });

        selectRegionView = findViewById(R.id.deviceView);
        selectRegionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeviceActivity.this,DeviceRegionActivity.class);
                Integer libIdx = devicePresenter.getLibIdx();
                i.putExtra("lib_idx",libIdx);
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isOnce){
            isOnce = false;
            waitView.setVisibility(View.VISIBLE);
            devicePresenter.loadData(regionCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFinish = true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(isSearchModel){
                normalModel();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void searchModel(){
        isSearchModel = true;
        searchEdit.setVisibility(View.VISIBLE);
        searchEdit.startAnimation(searchEditOpenAnim);
        searchView.setVisibility(View.VISIBLE);
        searchView.startAnimation(searchViewOpenAnim);
    }

    private void normalModel(){
        isSearchModel = false;
        searchEdit.setVisibility(View.INVISIBLE);
        searchEdit.startAnimation(searchEditCloseAnim);
        searchView.setVisibility(View.INVISIBLE);
        searchView.startAnimation(searchViewCloseAnim);
        waitView.setVisibility(View.INVISIBLE);
        searchEdit.setText("");

        searchAdapter.getDataSource().clear();
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void setFailView(FAIL_CODE code) {
        if(isFinish){
            return ;
        }
        waitView.setVisibility(View.INVISIBLE);
        if(code == FAIL_CODE.network_error){
            Toast.makeText(this,"网络连接失败",Toast.LENGTH_SHORT).show();
        }
        normalPullToRefreshView.onRefreshComplete();
    }

    @Override
    public void setSuccessView(List<AppOPACEntity> data,DATA_STATE state) {
        if(isFinish){
            return ;
        }
        waitView.setVisibility(View.INVISIBLE);
        if(isRefresh){
            normalAdapter.getDataSource().clear();
            isRefresh = false;
        }
        if(!data.isEmpty()) {
            normalAdapter.getDataSource().addAll(data);
            normalAdapter.notifyDataSetChanged();
        }else if(data.isEmpty() && state == DATA_STATE.full){
            Toast.makeText(this,"已经全部加载完成了",Toast.LENGTH_SHORT).show();
        }
        if(state == DATA_STATE.full) {
            normalPullToRefreshView.onRefreshComplete();
        }
    }

    @Override
    public void setSearchView(List<AppOPACEntity> data, DATA_STATE state) {
        if(isFinish || !isSearchModel){
            return ;
        }
        waitView.setVisibility(View.INVISIBLE);

        searchAdapter.getDataSource().clear();
        if(!data.isEmpty()) {
            searchAdapter.getDataSource().addAll(data);
        }else if(data.isEmpty() && state == DATA_STATE.full){
            Toast.makeText(this,"没有搜索到设备",Toast.LENGTH_SHORT).show();
        }
        searchAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新视图，即重新载入数据
     */
    private void refresh(){
        devicePresenter.resetState();//重置适配器状态
        devicePresenter.loadData(regionCode);
        isRefresh = true;
    }

    private void nextPage(){
        devicePresenter.loadData(regionCode);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                RegionEntity region = (RegionEntity) data.getSerializableExtra("region");
                if(region != null){
                    //不一样的时候才进行重新设置
                    if(!StringUtils.isEqual(regionCode,region.getRegi_code())) {
                        regionCode = region.getRegi_code();
                        TextView tv = (TextView) selectRegionView.findViewById(R.id.regionName);
                        String str = "";
                        if (!StringUtils.isEmpty(region.getRegi_province())) {
                            str += region.getRegi_province();
                        }
                        if (!StringUtils.isEmpty(region.getRegi_city())) {
                            str += "-" + region.getRegi_city();
                        }
                        if (!StringUtils.isEmpty(region.getRegi_area())) {
                            str += "-" + region.getRegi_area();
                        }
                        if (str.isEmpty()) {
                            str = "全部";
                        }
                        tv.setText(str);
                        refresh();
                    }
                }
            }
        }
    }

    /**
     * 数据适配器
     */
    private static class DeviceAdapter extends BaseAdapter{
        private List<AppOPACEntity> dataSource;
        private LayoutInflater inflater;
        private View.OnClickListener onClickListener;

        private DeviceAdapter(Context context){
            this(new ArrayList<AppOPACEntity>(),context);
        }

        private DeviceAdapter(List<AppOPACEntity> data,Context context){
            dataSource = data;
            inflater = LayoutInflater.from(context.getApplicationContext());
        }
        @Override
        public int getCount() {
            return dataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return dataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if(convertView == null){
                v = inflater.inflate(R.layout.sub_view_device,null);

            }else{
                v = convertView;
            }
            AppOPACEntity opac = dataSource.get(position);

            TextView deviceName = (TextView) v.findViewById(R.id.deviceName);
            TextView deviceLoan = (TextView) v.findViewById(R.id.deviceLoan);

            deviceName.setText(opac.getDevName());
            deviceLoan.setText(opac.getLocation());

            v.setTag(opac);

            v.setOnClickListener(onClickListener);

            return v;
        }

        private List<AppOPACEntity> getDataSource() {
            return dataSource;
        }

        private void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        private View.OnClickListener getOnClickListener() {
            return onClickListener;
        }
    }
}
