package com.ssitcloud.app_operator.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.component.ManageActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class PlcStateActivity extends ManageActivity{
    private final String TAG = "PlcStateActivity";

    private List<Item> mList;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plc_state);

        Intent intent = getIntent();
        String pclStr = intent.getStringExtra("plcStateData");
        if(pclStr == null){
            Toast.makeText(this,"请刷新后重试",Toast.LENGTH_SHORT).show();
            finish();
        }
        ImageView returnV = (ImageView) findViewById(R.id.plc_back);
        returnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mList = new ArrayList<>();
        Map<String,Object> plcStateData = JsonUtils.fromJson(pclStr,Map.class);
        Set<Map.Entry<String, Object>> entries = sortSet(plcStateData.entrySet());
        for (Map.Entry<String, Object> entry : entries) {
            Map<String,Object> value = (Map<String, Object>) entry.getValue();
            Item item = new Item();
            Object state1 = value.get("state");
            if (state1 != null) {
                boolean state = "0".equals(state1);
                if (state){
                    item.setPlcStateDesc("正常");
                    item.setImage(this.getResources().getDrawable(R.drawable.state_normal));
                    item.setColor(0xFF55C76A);
                }else {
                    item.setPlcStateDesc("告警");
                    item.setImage(this.getResources().getDrawable(R.drawable.alarm));
                    item.setColor(0xFFF85B5B);
                }
            }else{
                item.setPlcStateDesc("异常");
                item.setImage(this.getResources().getDrawable(R.drawable.disable));
                item.setColor(0xFFC7C7C7);
            }

            item.setDeviceName(StringUtils.getDesc(value.get("desc")));

            mList.add(item);
        }

        ListViewAdapter adapter = new ListViewAdapter();
        inflater = LayoutInflater.from(this);
        ListView listView = (ListView) findViewById(R.id.plc_state_list_view);

        listView.setAdapter(adapter);
    }

    private Set<Map.Entry<String, Object>> sortSet(Set<Map.Entry<String, Object>> set){
        Set<Map.Entry<String, Object>> treeSet = new TreeSet<>(new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> t1, Map.Entry<String, Object> t2) {
                if(t1.getKey() == null && t2.getKey() != null){
                    return -1;
                }
                if(t1.getKey() != null && t2.getKey() == null){
                    return 1;
                }
                return t1.getKey().compareTo(t2.getKey());
            }
        });

        for (Map.Entry<String, Object> stringObjectEntry : set) {
            treeSet.add(stringObjectEntry);
        }

        return treeSet;
    }

    private class Item{
        private String deviceName;
        private Drawable image;
        private String plcStateDesc;
        private int color=0xFFC7C7C7;
        String getDeviceName() {
            return deviceName;
        }

        void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        Drawable getImage() {
            return image;
        }

        void setImage(Drawable image) {
            this.image = image;
        }

        String getPlcStateDesc() {
            return plcStateDesc;
        }

        void setPlcStateDesc(String plcStateDesc) {
            this.plcStateDesc = plcStateDesc;
        }

        int getColor() {
            return color;
        }

        void setColor(int color) {
            this.color = color;
        }
    }

    private class ViewHolder{

        private TextView tvName;
        private TextView stateDesc;
        private ImageView imageView;
    }

    private class ListViewAdapter extends BaseAdapter {

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
            if(convertView==null){
                convertView=inflater.inflate(R.layout.activity_items, null);
                holder=new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.items_image);
                holder.stateDesc = (TextView) convertView.findViewById(R.id.items_state_desc);
                holder.tvName=(TextView) convertView.findViewById(R.id.items_textview);
                convertView.setTag(holder);
            }else{
                holder=(ViewHolder) convertView.getTag();
            }

            Item item = mList.get(position);
            holder.imageView.setImageDrawable(item.getImage());
            holder.tvName.setText(item.getDeviceName());
            holder.stateDesc.setText(item.getPlcStateDesc());
            holder.stateDesc.setTextColor(item.getColor());

            return convertView;
        }

    }
}
