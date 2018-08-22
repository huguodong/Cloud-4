package com.ssitcloud.app_operator.biz.impl;

import android.content.Context;
import android.util.Log;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.LibraryBiz;
import com.ssitcloud.app_operator.common.entity.HttpResponce;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.HttpClientUtil;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.RequestUrlUtil;
import com.ssitcloud.app_operator.entity.LibraryEntity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * *
 * Created by LXP on 2017/8/25.
 */

public class LibraryBizImpl implements LibraryBiz {

    private Context context;
    private final String charset = "UTF-8";

    public LibraryBizImpl(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public Observable<List<LibraryEntity>> getSlaveLibrary(final Integer mastLibIdx) {
        return Observable.create(new ObservableOnSubscribe<List<LibraryEntity>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<LibraryEntity>> subscribe) throws Exception {
                String url = RequestUrlUtil.getUrl(context.getResources(), R.string.get_slave_master_url);
                Map<String, String> map = new HashMap<>();
                map.put("json", "{\"library_idx\":" + mastLibIdx + "}");

                HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
                if (hr.getState() == 200) {
                    try {
                        ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                        Map<String, Object> reMap = (Map<String, Object>) result.getResult();

                        List<LibraryEntity> libs = new ArrayList<>();
                        Map<String, Object> mastLibDatas = (Map<String, Object>) reMap.get("masterLibrary");
                        if (mastLibDatas != null) {
                            LibraryEntity le = new LibraryEntity();
                            le.setLib_name((String) mastLibDatas.get("lib_name"));
                            le.setLib_id((String) mastLibDatas.get("lib_id"));
                            le.setLibrary_idx((Integer) mastLibDatas.get("library_idx"));

                            libs.add(le);
                        }

                        List<Map<String, Object>> slaveLibDatas = (List<Map<String, Object>>) reMap.get("slaveLibrary");
                        if (slaveLibDatas != null) {
                            for (Map<String, Object> libData : slaveLibDatas) {
                                LibraryEntity le = new LibraryEntity();
                                le.setLib_name((String) libData.get("lib_name"));
                                le.setLib_id((String) libData.get("lib_id"));
                                le.setLibrary_idx((Integer) libData.get("library_idx"));

                                libs.add(le);
                            }
                        }
                        subscribe.onNext(libs);
                        subscribe.onComplete();
                        return;
                    } catch (Exception e) {
                        Log.e("LibraryBizImpl", e.getMessage());
                        subscribe.onError(e);
                        return;
                    }
                }

                throw new SocketException("网络异常");
            }
        });
    }
}
