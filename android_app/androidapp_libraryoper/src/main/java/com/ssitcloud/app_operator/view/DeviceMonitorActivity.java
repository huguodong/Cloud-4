package com.ssitcloud.app_operator.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.component.ManageActivity;
import com.ssitcloud.app_operator.component.MyApplication;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.DisplayUtil;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.entity.LibraryEntity;
import com.ssitcloud.app_operator.entity.RegionEntity;
import com.ssitcloud.app_operator.presenter.DeviceMonitorPresenter;
import com.ssitcloud.app_operator.view.spinner.SpinnerPopWindow;
import com.ssitcloud.app_operator.view.viewInterface.DeviceMonitorViewI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/3/31 15:53
 *
 * @author shuangjunjie
 */
public class DeviceMonitorActivity extends ManageActivity implements DeviceMonitorViewI {

    private final String TAG = "DeviceMonitorActivity";
    private LayoutInflater inflater;
    private TextView selectLibView;
    private TextView keyWordView;
    private ImageView notFoundImg;
    private TextView notFoundTextView;
    private DeviceMonitorPresenter deviceMonitorPresenter;
    private SpinnerPopWindow<String> mSpinerPopWindow;
    private MyApplication app;
    private String operatorType;           //操作员类型
    private Integer clickLibraryIdx;        //下拉框选择的图书馆idx
    private ListViewAdapter adapter;
    private int currentPage = 1;
    private boolean isSelectByLibrary = false;              //是否通过图书馆检索数据
    private boolean isFirstPull = true;            //第一次进入设备监控页面
    private RegionEntity regionEntity;

    private TextView selectRegion;          //选择区域view

    private PullToRefreshListView normalPullToRefreshView;

    private View networkWait;

    private ArrayList<LibraryEntity> libraryEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        libraryEntities = new ArrayList<>((List<LibraryEntity>) bundle.getSerializable("libs"));

        adapter = new ListViewAdapter();

        setContentView(R.layout.activity_device_monitor);

        app = (MyApplication) getApplication();

        inflater = LayoutInflater.from(DeviceMonitorActivity.this);
        ImageView returnV = (ImageView) findViewById(R.id.device_monitor_return);
//        listView = (ListView) findViewById(R.id.device_monitor_list_view);
        selectLibView = (TextView) findViewById(R.id.device_monitor_select_lib);
        keyWordView = (TextView) findViewById(R.id.device_monitor_key_word);
        notFoundImg = (ImageView) findViewById(R.id.device_monitor_not_found_img);
        notFoundTextView = (TextView) findViewById(R.id.device_monitor_not_found_text);
        ImageView searchImg = (ImageView) findViewById(R.id.device_monitor_search_img);
        networkWait = findViewById(R.id.networkWait);
        selectRegion = (TextView) findViewById(R.id.device_monitor_select_region);

        selectRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeviceMonitorActivity.this, RegionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("libs", libraryEntities);
                i.putExtras(bundle);
                startActivityForResult(i, 1);
            }
        });

        operatorType = app.getOperator_type();

        selectLibView.setText("查询全部");
//        if (OperatorEntity.SSITCLOUD_ADMIN.equals(operatorType) || OperatorEntity.SSITCLOUD_MANAGER.equals(operatorType)) {
//            selectLibView.setText("查询全部");
//        } else {
//            if (!StringUtils.isEmpty(app.getLib_name())) {
//                selectLibView.setText(app.getLib_name());
//            }
//        }

        deviceMonitorPresenter = new DeviceMonitorPresenter(this, this);

        normalPullToRefreshView = (PullToRefreshListView) findViewById(R.id.device_monitor_normal_pull_to_refresh_listview);

        adapter.getmList().addAll(new ArrayList<Item>());
        normalPullToRefreshView.setAdapter(adapter);

        normalPullToRefreshView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//上拉加载
        normalPullToRefreshView.getLoadingLayoutProxy(false, true).setPullLabel("继续滑动加载更多");
        normalPullToRefreshView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放立即加载");
        normalPullToRefreshView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        normalPullToRefreshView.getLoadingLayoutProxy(false, true).setLoadingDrawable(this.getResources().getDrawable(R.drawable.load));
        //设置刷新监听
        normalPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (isSelectByLibrary) {
                    currentPage = 2;
                }
                if (isFirstPull) {
                    currentPage = 2;
                    isFirstPull = false;
                }
                nextPage(currentPage);
            }
        });

        normalPullToRefreshView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String deviceIdx = adapter.getmList().get(position - 1).getDeviceIdx();
                String libraryIdx = adapter.getmList().get(position - 1).getLibraryIdx();
                String deviceId = adapter.getmList().get(position - 1).getDeviceId();
                String deviceName = adapter.getmList().get(position - 1).getDeviceName();
                String libDevice = null;
                for (LibraryEntity libraryEntity : libraryEntities) {
                    if(libraryEntity.getLibrary_idx().toString().equals(libraryIdx)){
                        libDevice = libraryEntity.getLib_id() + "_" + deviceId;
                        break;
                    }
                }

                String deviceType = adapter.getmList().get(position - 1).getDeviceType();
                Intent intent = new Intent();
                intent.putExtra("deviceName", deviceName);
                intent.putExtra("library_idx", libraryIdx);
                intent.putExtra("device_idx", deviceIdx);
                intent.putExtra("device_id", deviceId);
                intent.putExtra("device_type", deviceType);
                intent.setClass(DeviceMonitorActivity.this, SSLActivity.class);
                startActivity(intent);
            }
        });

        networkWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        returnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        selectLibView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLibrarys();
            }
        });

        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<>();
                List<Integer> ids;
                if (clickLibraryIdx != null) {
                    ids = new ArrayList<>(1);
                    ids.add(clickLibraryIdx);
                } else {
                    ids = getAllIds();
                }
                params.put("page", 1);
                params.put("pageSize", 5);
                params.put("flag", 0);
                String keyWord = keyWordView.getText().toString();
                if (!StringUtils.isEmpty(keyWord)) {
                    params.put("keyWord", keyWord);
                }
                if (regionEntity != null && regionEntity.getRegi_idx() != null) {
                    params.put("region_idx", regionEntity.getRegi_idx());
                }

                isSelectByLibrary = true;

                deviceMonitorPresenter.SelectDeviceMgmtByLibraryIdxs(params, ids);
            }
        });

        normalPullToRefreshView.setRefreshing();
        nextPage(currentPage);
    }

    private static class Item {

        private String deviceName;
        private String deviceIdx;
        private String libraryIdx;
        private String deviceType;
        private Drawable image;
        private String deviceId;

        String getDeviceName() {
            return deviceName;
        }

        void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        String getDeviceIdx() {
            return deviceIdx;
        }

        void setDeviceIdx(String deviceIdx) {
            this.deviceIdx = deviceIdx;
        }

        String getLibraryIdx() {
            return libraryIdx;
        }

        void setLibraryIdx(String libraryIdx) {
            this.libraryIdx = libraryIdx;
        }

        String getDeviceType() {
            return deviceType;
        }

        void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        Drawable getImage() {
            return image;
        }

        void setImage(Drawable image) {
            this.image = image;
        }

        String getDeviceId() {
            return deviceId;
        }

        void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }
    }

    private class ListViewAdapter extends BaseAdapter {

        private List<Item> mList;

        ListViewAdapter() {
            this.mList = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_items, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.items_image);
                holder.tvName = (TextView) convertView.findViewById(R.id.items_textview);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Drawable img = mList.get(position).getImage();
            String tvName = mList.get(position).getDeviceName();
            holder.imageView.setImageDrawable(img);
            holder.tvName.setText(tvName);
            return convertView;
        }

        List<Item> getmList() {
            return mList;
        }
    }

    private static class ViewHolder {
        private TextView tvName;
        private ImageView imageView;
    }


    public void showLibrarys() {
        if (mSpinerPopWindow == null || !mSpinerPopWindow.isShowing()) {
            List<Map<String, Object>> datas = new ArrayList<>(libraryEntities.size() + 1);
            Map<String, Object> m = new ArrayMap<>();
            m.put("lib_name", "查询全部");
            m.put("library_idx", "");
            datas.add(m);

            for (LibraryEntity lib : libraryEntities) {
                m = new ArrayMap<>();
                m.put("lib_name", lib.getLib_name());
                m.put("library_idx", String.valueOf(lib.getLibrary_idx()));
                datas.add(m);
            }

            mSpinerPopWindow = new SpinnerPopWindow<>(DeviceMonitorActivity.this, datas, itemClickListener);
            mSpinerPopWindow.setWidth(selectLibView.getWidth());
            mSpinerPopWindow.setHeight(DisplayUtil.getScreenHeight(this) * 3 / 5);
            mSpinerPopWindow.showAsDropDown(selectLibView);
        }
        selectLibView.setClickable(true);
    }

    @Override
    public void selectFail(String message) {
        selectLibView.setClickable(true);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            List<Map<String, Object>> list = mSpinerPopWindow.getList();
            Map<String, Object> libraryEntity = list.get(position);
            selectLibView.setText((String)libraryEntity.get("lib_name"));
            Map<String, Object> params = new ArrayMap<>();
            List<Integer> ids;
            if ("查询全部".equals(libraryEntity.get("lib_name"))) {
                ids = getAllIds();
                clickLibraryIdx = null;
            } else {
                ids = new ArrayList<>(1);
                clickLibraryIdx = Integer.valueOf(String.valueOf(libraryEntity.get("library_idx")));
                ids.add(clickLibraryIdx);
            }

            String keyWord = keyWordView.getText().toString();
            if (!StringUtils.isEmpty(keyWord)) {
                params.put("keyWord", keyWord);
            }
            if (null != regionEntity && null != regionEntity.getRegi_idx()) {
                params.put("region_idx", regionEntity.getRegi_idx());
            }

            params.put("page", 1);
            params.put("pageSize", 5);
            params.put("flag", 0);

            isSelectByLibrary = true;
            deviceMonitorPresenter.SelectDeviceMgmtByLibraryIdxs(params, ids);
        }
    };

    @Override
    public void getDeviceStateFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        networkWait.setVisibility(View.INVISIBLE);
    }

    @Override
    public void SelectDeviceMgmtByLibraryIdxsSuccess(ResultEntity resultEntity) {
        Map<String, Object> resultMap = JsonUtils.fromJson(JsonUtils.toJson(resultEntity.getResult()), Map.class);
        List<Map> rows = (List<Map>) resultMap.get("rows");
        List<Item> listItem = getListItem(rows);

        adapter.getmList().clear();
        if (!isSelectByLibrary || listItem.size() == 0){
            Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
        }

        adapter.getmList().addAll(listItem);
        normalPullToRefreshView.setAdapter(adapter);
        if (adapter.getmList().size() == 0) {
            notFoundImg.setVisibility(View.VISIBLE);
            notFoundTextView.setVisibility(View.VISIBLE);
        } else {
            notFoundImg.setVisibility(View.INVISIBLE);
            notFoundTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void SelectDeviceMgmtByLibraryIdxsFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private List<DeviceMonitorActivity.Item> getListItem(List<Map> rows) {
        List<DeviceMonitorActivity.Item> mlist = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> rowsMap = rows.get(i);
            Item item = new Item();
            if (null != rowsMap && rowsMap.containsKey("device_name")) {
                item.setDeviceName(rowsMap.get("device_name").toString());
            }
            if (null != rowsMap && rowsMap.containsKey("device_idx")) {
                item.setDeviceIdx(rowsMap.get("device_idx").toString());
            }
            if (null != rowsMap && rowsMap.containsKey("device_type")) {
                item.setDeviceType(rowsMap.get("device_type").toString());
            }
            if (null != rowsMap && rowsMap.containsKey("library_idx")) {
                item.setLibraryIdx(rowsMap.get("library_idx").toString());
            }
            if (null != rowsMap && rowsMap.containsKey("device_id")) {
                item.setDeviceId(rowsMap.get("device_id").toString());
            }
            item.setImage(ContextCompat.getDrawable(this,R.drawable.right));
            mlist.add(item);
        }
        return mlist;
    }

    private void nextPage(int currentPage) {
        String keyWord = keyWordView.getText().toString();
        Map<String, Object> params = new ArrayMap<>();
        params.put("page", currentPage);
        params.put("pageSize", 5);
        params.put("flag", 1);

        //设置图书馆查询参数
        List<Integer> ids;
        if (clickLibraryIdx != null) {
            ids = new ArrayList<>(1);
            ids.add(clickLibraryIdx);
        } else {
            ids = getAllIds();
        }

        if (!StringUtils.isEmpty(keyWord)) {
            params.put("keyWord", keyWord);
        }
        if (null != regionEntity && null != regionEntity.getRegi_idx()) {
            params.put("region_idx", regionEntity.getRegi_idx());
        }
        deviceMonitorPresenter.SelectDeviceMgmtByLibraryIdxs(params, ids);
    }

    @Override
    public void SelectDeviceMgmtByPageSuccess(ResultEntity resultEntity) {
        Map<String, Object> resultMap = JsonUtils.fromJson(JsonUtils.toJson(resultEntity.getResult()), Map.class);
        List<Map> rows = (List<Map>) resultMap.get("rows");
        List<Item> listItem = getListItem(rows);
        if (listItem.isEmpty()) {
            Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
            if (adapter.getmList().size() == 0) {
                notFoundImg.setVisibility(View.VISIBLE);
                notFoundTextView.setVisibility(View.VISIBLE);
            } else {
                notFoundImg.setVisibility(View.INVISIBLE);
                notFoundTextView.setVisibility(View.INVISIBLE);
            }
        } else {
            notFoundImg.setVisibility(View.INVISIBLE);
            notFoundTextView.setVisibility(View.INVISIBLE);

            ++currentPage;
            isSelectByLibrary = false;
            adapter.getmList().addAll(listItem);
//            normalPullToRefreshView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        normalPullToRefreshView.onRefreshComplete();

    }

    @Override
    public void SelectDeviceMgmtByPageFail(String message) {
        normalPullToRefreshView.onRefreshComplete();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            RegionEntity r = (RegionEntity) data.getExtras().getSerializable("region");
            if (null != r && null != r.getRegi_idx()) {
                regionEntity = r;
                selectRegion.setText(regionEntity.getRegi_area());
            }
        }
    }

    private List<Integer> getAllIds() {
        List<Integer> ids = new ArrayList<>(libraryEntities.size() + 1);
        for (LibraryEntity libraryEntity : libraryEntities) {
            ids.add(libraryEntity.getLibrary_idx());
        }
        ids.add(app.getLibrary_idx());

        return ids;
    }
}
