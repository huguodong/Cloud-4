package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.ArrayMap;

import com.ssitcloud.app_reader.biz.BookBizI;
import com.ssitcloud.app_reader.biz.impl.BookBizImpl;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.AppOPACEntity;
import com.ssitcloud.app_reader.entity.BookUnionEntity;
import com.ssitcloud.app_reader.view.viewInterface.BookItemViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/8.
 * BookItemPresenter
 */

public class BookItemPresenter {
    private int pageSize;
    private SoftReference<BookItemViewI> bookItemViewRef;
    private BookBizI deviceBiz;

    public BookItemPresenter(BookItemViewI view,Context context,int pageSize){
        bookItemViewRef = new SoftReference<>(view);
        this.pageSize = pageSize;
        deviceBiz = new BookBizImpl(context);
    }

    /**
     * 加载下一页数据
     */
    public void loadData(AppOPACEntity opac, String bookClassify, final String search_str, Integer pageCurrent, final long syncTime){
        final BookItemViewI view = bookItemViewRef.get();
        if(view == null){
            return ;
        }

        if(opac == null){
            view.setFail(BookItemViewI.FAIL_STATE.un_select_device,syncTime);
            return ;
        }

        AsyncTask<Map<String,Object>,Void,List<BookUnionEntity>> task = new AsyncTask<Map<String, Object>, Void, List<BookUnionEntity>>() {
            private volatile int state = 0;
            @Override
            protected List<BookUnionEntity> doInBackground(Map<String, Object>... params) {
                Integer pageCurrent = (Integer) params[0].get("pageCurrent");
                Integer pageSize = (Integer) params[0].get("pageSize");
                Map<String,Object> idData = (Map<String, Object>) params[0].get("idData");
                Map<String,Object> selectMap = (Map<String,Object>) params[0].get("selectMap");
                try {
                    List<BookUnionEntity> bookitems = deviceBiz.findDeviceBook(selectMap, idData, pageCurrent, pageSize);
                    state = 1;
                    return bookitems;
                } catch (SocketException e) {
                    state = -1;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<BookUnionEntity> bookUnionEntities) {
                if(state == -1){
                    if(search_str == null) {
                        view.setFail(BookItemViewI.FAIL_STATE.network_error,syncTime);
                    }else{
                        view.setSearchFail(BookItemViewI.FAIL_STATE.network_error,syncTime);
                    }
                }else if(state == 1){
                    if(search_str == null) {
                        view.setSuccessData(bookUnionEntities,syncTime);
                    }else{
                        view.setSearchData(bookUnionEntities,syncTime);
                    }
                }
            }
        };
        Map<String,Object> selectMap = new ArrayMap<>();
        if(!StringUtils.isEmpty(bookClassify)) {
            selectMap.put("callNo", bookClassify);
        }
        if(!StringUtils.isEmpty(search_str)) {
            selectMap.put("search_str", search_str);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("idData",opac.getIdData());
        map.put("selectMap",selectMap);

        if (pageCurrent != null) {
            map.put("pageCurrent",pageCurrent);
            map.put("pageSize",pageSize);
        }

        task.execute(map);
    }

}
