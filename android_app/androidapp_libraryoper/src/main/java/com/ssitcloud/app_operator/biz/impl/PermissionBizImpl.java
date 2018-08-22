package com.ssitcloud.app_operator.biz.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.ArrayMap;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.component.MyApplication;
import com.ssitcloud.app_operator.biz.PermissionBiz;
import com.ssitcloud.app_operator.common.entity.HttpResponce;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.HttpClientUtil;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.RequestUrlUtil;

import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by LXP on 2017/8/17.
 * *
 */

public class PermissionBizImpl implements PermissionBiz {
    private String PERMISSION_SP_NAME="PERMISSION_SP_NAME";
    private MyApplication context;
    private static volatile PermissionBizImpl permissionBiz;

    private PermissionBizImpl(Context context) {
        this.context = ((MyApplication) context.getApplicationContext());
    }

    public static PermissionBiz getInstance(Context context){
        if(permissionBiz == null){
            synchronized (PermissionBizImpl.class){
                if(permissionBiz == null){
                    permissionBiz = new PermissionBizImpl(context);
                }
            }
        }

        return permissionBiz;
    }

    @Override
    public boolean checkPermission(String perm) {
        SharedPreferences sp = context.getSharedPreferences(PERMISSION_SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(perm,false);
    }

    @Override
    public Observable<Boolean> getPermission(final String perm) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> subscribe) throws Exception {
                Integer operator_idx = context.getOperator_idx();
                if(operator_idx != null) {
                    String url = RequestUrlUtil.getUrl(context.getResources(), R.string.get_permission_url);
                    Map<String, String> param = new ArrayMap<>();
                    param.put("json", "{\"operator_idx\":" + operator_idx + "}");
                    HttpResponce hr = HttpClientUtil.dopost(url, param, "utf-8");
                    if (hr.getState() == 200) {
                        try {
                            ResultEntity resultEntity = JsonUtils.fromJson(hr.getBody(),ResultEntity.class);
                            if(resultEntity != null && resultEntity.getState()){
                                Map<String,Object> map = (Map<String, Object>) resultEntity.getResult();
                                if (map != null) {
                                    Set<Map.Entry<String, Object>> entries = map.entrySet();
                                    SharedPreferences sp = context.getSharedPreferences(PERMISSION_SP_NAME, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor edit = sp.edit();
                                    edit.clear();//删除所有权限
                                    for (Map.Entry<String, Object> entry : entries) {
                                        if(entry.getValue() instanceof Boolean){
                                            edit.putBoolean(entry.getKey(), (Boolean) entry.getValue());
                                        }
                                    }
                                    edit.apply();
                                    Object o = map.get(perm);
                                    if(o instanceof Boolean){
                                        subscribe.onNext(((Boolean) o));
                                        subscribe.onComplete();
                                        return ;
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                subscribe.onNext(false);
                subscribe.onComplete();
            }
        });
    }
}
