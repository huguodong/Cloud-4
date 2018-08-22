package com.ssitcloud.app_operator.view.listView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * 动态设置listview高度
 * Created by Administrator on 2017/4/7.
 */

public class ListViewRelayout {

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // get the list view adapter, so this function must be invoked after set the adapter.
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        // get the ListView count
        int count = listAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            // measure the child view
            listItem.measure(0, 0);
            // calculate the total height of items
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // get divider height for all items and add the total height
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
