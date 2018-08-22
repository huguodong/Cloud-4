package com.ssitcloud.app_operator.view.spinner;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ssitcloud.app_operator.R;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/3/30 18:52
 * @author shuangjunjie
 */

public class SpinnerPopWindow<T> extends PopupWindow{
    private LayoutInflater inflater;
    private ListView mListView;
    private List<Map<String,Object>> list;

    public SpinnerPopWindow(Context context, List<Map<String,Object>> list, AdapterView.OnItemClickListener clickListener){
        super(context);
        assert list != null;

        inflater = LayoutInflater.from(context);
        this.list = list;
        init(clickListener);
    }

    private void init(AdapterView.OnItemClickListener clickListener){
        View view = inflater.inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setAdapter(new MyAdapter());
        mListView.setOnItemClickListener(clickListener);
    }

    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;

            if(convertView==null){
                holder=new ViewHolder();
                convertView=inflater.inflate(R.layout.spiner_item_layout, null);
                holder.tvName=(TextView) convertView.findViewById(R.id.tv_name);
                holder.libIdx=(TextView) convertView.findViewById(R.id.library_idx);

                convertView.setTag(holder);
            }else{
                holder=(ViewHolder) convertView.getTag();
            }

            if (((Map) getItem(position)).containsKey("feedBack")){
                holder.tvName.setText(((Map) getItem(position)).get("feedBack").toString());
            }else if (((Map) getItem(position)).containsKey("lib_name")){
                holder.tvName.setText(((Map) getItem(position)).get("lib_name").toString());
                holder.libIdx.setText(((Map) getItem(position)).get("library_idx").toString());
            }

            return convertView;
        }
    }

    private class ViewHolder{
        private TextView tvName;
        private TextView libIdx;
    }

    public List<Map<String, Object>> getList() {
        return Collections.unmodifiableList(list);
    }
}
