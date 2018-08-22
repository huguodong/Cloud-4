package com.ssitcloud.app_operator.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssitcloud.app_operator.R;

import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/4/10 15:53
 * @author shuangjunjie
 */

public class ListAdapter extends BaseAdapter{

    private List<Map> list;
    private LayoutInflater inflater;

    public ListAdapter(List<Map> list, LayoutInflater inflater) {
        this.list = list;
        this.inflater = inflater;
    }

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

        ViewHolder holder;

        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.activity_items, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.items_image);
            holder.stateDesc = (TextView) convertView.findViewById(R.id.items_state_desc);
            holder.tvName=(TextView) convertView.findViewById(R.id.items_textview);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        String stateDesc = "";
        if (((Map) getItem(position)).containsKey("stateDesc") && null != ((Map) getItem(position)).get("stateDesc")){

            stateDesc = ((Map) getItem(position)).get("stateDesc").toString();
        }
        String extName = ((Map) getItem(position)).get("ext_name").toString();
        Integer imgResource = 0;
        if (((Map) getItem(position)).containsKey("imgResource") && null != ((Map) getItem(position)).get("imgResource")){

            imgResource = Integer.parseInt(((Map) getItem(position)).get("imgResource").toString());
        }

        holder.tvName.setText(extName);
        holder.stateDesc.setText(stateDesc);
        holder.imageView.setImageResource(imgResource);

        if ("正常".equals(stateDesc)){
            holder.stateDesc.setTextColor(Color.parseColor("#FF55C76A"));
        }else if ("告警".equals(stateDesc)){
            holder.stateDesc.setTextColor(Color.parseColor("#FFF85B5B"));
        }else if ("禁用".equals(stateDesc)){
            holder.stateDesc.setTextColor(Color.parseColor("#FFC7C7C7"));
        }else if ("异常".equals(stateDesc)){
            holder.stateDesc.setTextColor(Color.parseColor("#FFF85B5B"));
        }

        return convertView;
    }

    private class ViewHolder{

        private TextView tvName;
        private TextView stateDesc;
        private ImageView imageView;
    }
}
