package com.ssitcloud.app_operator.biz.impl;

import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.component.MyApplication;
import com.ssitcloud.app_operator.biz.MessageRemindBizI;
import com.ssitcloud.app_operator.common.entity.HttpResponce;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.HttpClientUtil;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.RequestUrlUtil;
import com.ssitcloud.app_operator.db.DbHelper;
import com.ssitcloud.app_operator.db.dao.MessageRemindDao;
import com.ssitcloud.app_operator.db.entity.MessageRemindDbEntity;

import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 创建日期：2017/3/22 19:12
 * @author shuangjunjie
 */

public class MessageRemindBizImpl implements MessageRemindBizI{
    private final String TAG = "MessageRemindBizImpl";
    private final String charset = "utf-8";
    private Resources resources;
    private MyApplication context;

    public MessageRemindBizImpl(Context context) {
        this.resources = context.getResources();
        this.context = (MyApplication) context.getApplicationContext();
    }

    @Override
    public int[] getMessageRemindCount() {
        List<MessageRemindDbEntity> messageRemind = getMessageRemind(null, null);
        int count = 0;
        for (MessageRemindDbEntity messageRemindDbEntity : messageRemind) {
            if(messageRemindDbEntity.getState() == 0){
                count++;
            }
        }
        return new int[]{count,messageRemind.size()};
    }

    @Override
    public List<MessageRemindDbEntity> getMessageRemind(Integer page, Integer pageSize) {
        DbHelper db = new DbHelper(context);
        try {
            SQLiteDatabase wdb = db.getWritableDatabase();
            return MessageRemindDao.select(wdb,page,pageSize);
        } catch (SQLException e) {
            Log.e(TAG, "open writable database error");
        } finally {
            db.close();
        }
        return null;
    }

    private Observable<List<MessageRemindDbEntity>> selectDeviceTroublesByLibIdxs(){
        final Map<String, Object> reqMap = new ArrayMap<>();
        reqMap.put("operator_type", context.getOperator_type());
        reqMap.put("library_idx", context.getLibrary_idx());
        reqMap.put("page",1);
        reqMap.put("pageSize",Integer.MAX_VALUE);
        return Observable.create(new ObservableOnSubscribe<List<MessageRemindDbEntity>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<MessageRemindDbEntity>> subscribe) throws Exception {
                String url = RequestUrlUtil.getUrl(resources, R.string.selectDeviceTroublesByLibIdxs);
                Map<String, String> map = new HashMap<>();
                map.put("json", JsonUtils.toJson(reqMap));
                HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
                if (hr.getState() == 200) {
                    try {
                        ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                        if (result != null && result.getResult() != null && result.getState()) {
                            List<MessageRemindDbEntity> datas = new ArrayList<>();

                            List<Map<String,Object>> listMap = (List<Map<String, Object>>) ((Map<String, Object>) result.getResult()).get("rows");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                            for (Map<String, Object> data : listMap) {
                                MessageRemindDbEntity m = new MessageRemindDbEntity();
                                m.setTrouble_idx((Integer) data.get("trouble_idx"));
                                m.setLib_idx((Integer) data.get("lib_idx"));
                                m.setDevice_idx((Integer) data.get("device_idx"));
                                m.setTrouble_name(data.get("trouble_name").toString());
                                m.setTrouble_info(data.get("trouble_info").toString());
                                m.setTrouble_time(sdf.format(new Date((Long) data.get("create_time"))));
                                m.setCreate_time(new Date());
                                datas.add(m);
                            }
                            subscribe.onNext(datas);
                            subscribe.onComplete();
                            return ;
                        }
                    } catch (Exception e) {
                        subscribe.onNext(new ArrayList<MessageRemindDbEntity>(0));
                        subscribe.onComplete();
                        return ;
                    }
                }

                throw new SocketException("网络异常");
            }
        });
    }

    @Override
    public Observable<List<MessageRemindDbEntity>> selectUnReadDeviceTroublesByLibIdxs() {
        final List<MessageRemindDbEntity> messageRemind = getMessageRemind(null, null);
        return selectDeviceTroublesByLibIdxs().map(new Function<List<MessageRemindDbEntity>, List<MessageRemindDbEntity>>() {
            @Override
            public List<MessageRemindDbEntity> apply(@NonNull List<MessageRemindDbEntity> messageRemindDbEntities) throws Exception {
                final List<MessageRemindDbEntity> result = new ArrayList<>(messageRemindDbEntities.size());
                c: for (MessageRemindDbEntity messageRemindDbEntity : messageRemindDbEntities) {
                    for (MessageRemindDbEntity remindDbEntity : messageRemind) {
                        if(remindDbEntity.getTrouble_idx().equals(messageRemindDbEntity.getTrouble_idx())){
                            continue c;
                        }
                    }
                    result.add(messageRemindDbEntity);
                }
                insertMessageRemind(result);
                return result;
            }
        });
    }

    @Override
    public boolean insertMessageRemind(List<MessageRemindDbEntity> messageRemindList) {
        if(messageRemindList.isEmpty()){
            return true;
        }
        DbHelper db = new DbHelper(context);
        SQLiteDatabase wdb = null;
        try {
            wdb = db.getWritableDatabase();
            wdb.beginTransaction();
            for (int i = 0;i < messageRemindList.size();i++){
                MessageRemindDao.insert(wdb,messageRemindList.get(i));
            }
            wdb.setTransactionSuccessful();
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "open writable database error");
        } finally {
            if (null != wdb) {
                wdb.endTransaction();
            }
            db.close();
        }
        return false;
    }


    @Override
    public Observable<Void> updateDeviceTroubles(final List<Integer> troubleIdxs){
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Void> subscribe) throws Exception {
                if(troubleIdxs.isEmpty()){
                    return ;
                }

                String url = RequestUrlUtil.getUrl(resources, R.string.updateDeviceTroubles);
                Map<String, String> map = new HashMap<>();
                Map<String, Object> param = new ArrayMap<>();
                param.put("trouble_idxs", troubleIdxs);
                map.put("json", JsonUtils.toJson(param));
                HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
                if (hr.getState() == 200){
                    try {
                        ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                        if(result.getState()){
                            //更新本地数据库信息
                            DbHelper dh = new DbHelper(context);
                            SQLiteDatabase wdb = dh.getWritableDatabase();
                            try{
                                if (wdb != null) {
                                    wdb.beginTransaction();
                                    MessageRemindDbEntity upEntity = new MessageRemindDbEntity();
                                    for (Integer troubleIdx : troubleIdxs) {
                                        upEntity.setDevice_idx(troubleIdx);
                                        upEntity.setState(1);
                                        MessageRemindDao.update(wdb,upEntity);
                                    }
                                    wdb.setTransactionSuccessful();
                                }
                            }catch (Exception e){
                            }finally {
                                if (wdb != null) {
                                    wdb.endTransaction();
                                }
                                dh.close();
                            }
                        }
                    }catch (Exception e){
                        Log.e(TAG,"to json exception",e);
                    }
                }
                subscribe.onComplete();
            }
        });

    }
}
