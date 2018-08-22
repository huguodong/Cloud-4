package com.ssitcloud.app_operator.biz.impl;

import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.component.MyApplication;
import com.ssitcloud.app_operator.biz.LoginBizI;
import com.ssitcloud.app_operator.common.entity.HttpResponce;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.HttpClientUtil;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.ObjectUtils;
import com.ssitcloud.app_operator.common.utils.RequestUrlUtil;
import com.ssitcloud.app_operator.db.DbHelper;
import com.ssitcloud.app_operator.db.dao.AppSettingDao;
import com.ssitcloud.app_operator.db.dao.LoginInfoDao;
import com.ssitcloud.app_operator.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_operator.entity.AppSettingEntity;
import com.ssitcloud.app_operator.entity.OperatorEntity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


/**
 * 创建日期：2017/3/16 11:49
 *
 * @author shuangjunjie
 */

public class LoginBizImpl implements LoginBizI {
    private final String TAG = "LoginBizImpl";
    private final String charset = "utf-8";
    private Resources resources;
    private Context context;

    public LoginBizImpl(Resources resources, Context context) {
        this.resources = resources;
        this.context = context.getApplicationContext();
    }

    /**
     * 登录
     *
     * @param operatorMap
     * @return
     */
    @Override
    public ResultEntity login(Map<String, Object> operatorMap) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.login_url);
        Map<String, String> map = new HashMap<>();
        map.put("json", JsonUtils.toJson(operatorMap));
        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if (hr.getState() == 200) {
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                if (null != result.getResult() && result.getState()) {
                    Map<String, Object> operator = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), Map.class);
                    if (null != operator) {
                        LoginInfoDbEntity lfd = new LoginInfoDbEntity();
                        lfd.setOperator_idx(Integer.parseInt(operator.get("operator_idx").toString()));

                        lfd.setLogin_time(new Date());
                        lfd.setOperator_pwd(operatorMap.get("operator_pwd").toString());
                        if (operator.containsKey("mobile") && null != operator.get("mobile")) {
                            lfd.setMobile(operator.get("mobile").toString());
                        }
                        if (operator.containsKey("operator_id") && null != operator.get("operator_id")) {
                            lfd.setOperator_name(operator.get("operator_id").toString());
                        }
//                        System.out.println("mobile>>>>>"+operator.get("mobile").toString());

                        DbHelper db = new DbHelper(context);
                        SQLiteDatabase wdb = null;
                        try {
                            wdb = db.getWritableDatabase();
                            wdb.beginTransaction();
                            logout(wdb);
                            LoginInfoDao.insert(wdb, lfd);
                            wdb.setTransactionSuccessful();
                        } catch (SQLException e) {
                            Log.e(TAG, "open writable database error");
                        } finally {
                            if (null != wdb) {
                                wdb.endTransaction();
                            }
                            db.close();
                        }
                    }
//                    result.setResult(operator);
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                throw new SocketException("服务器没有传回预期数据");
            }
        } else {
            throw new SocketException("网络异常");
        }
    }

    @Override
    public Observable<List<AppSettingEntity>> getAppSetting(final Integer lib_idx) {
        return Observable.create(new ObservableOnSubscribe<List<AppSettingEntity>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AppSettingEntity>> subscribe) throws Exception {
                String url = RequestUrlUtil.getUrl(resources, R.string.get_appSetting_url);
                Map<String, Object> param = new ArrayMap<>();
                param.put("user_type", "1");
                if (lib_idx != null) {
                    param.put("lib_idx", lib_idx);
                }

                Map<String, String> map = new ArrayMap<>();
                map.put("json", JsonUtils.toJson(param));
                HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
                if (hr.getState() == 200) {
                    try {
                        ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                        if (result.getState()) {
                            List<Map<String, Object>> mapData = (List<Map<String, Object>>) result.getResult();
                            List<AppSettingEntity> data = new ArrayList<>(mapData.size());
                            for (Map<String, Object> stringObjectMap : mapData) {
                                AppSettingEntity appSetting = ObjectUtils.convertMap(stringObjectMap, AppSettingEntity.class);
                                data.add(appSetting);
                            }

                            //init database
                            DbHelper dh = new DbHelper(context);
                            SQLiteDatabase writeDb;
                            try {
                                writeDb = dh.getWritableDatabase();
                            } catch (SQLiteException e) {
                                Log.e(TAG, "open writeDb database error");
                                subscribe.onNext(filterLib(data, lib_idx));
                                subscribe.onComplete();
                                return ;
                            }
                            try {
                                writeDb.beginTransaction();
                                List<Integer> settingIds = new ArrayList<>(data.size());
                                for (AppSettingEntity appSettingEntity : data) {
                                    AppSettingDao.insert(writeDb, appSettingEntity);
                                    settingIds.add(appSettingEntity.getSetting_idx());
                                }
                                AppSettingDao.deleteNotIn(writeDb, settingIds);
                                writeDb.setTransactionSuccessful();
                            } finally {
                                writeDb.endTransaction();
                                dh.close();
                            }
                            subscribe.onNext(filterLib(data, lib_idx));
                            subscribe.onComplete();
                            return ;
                        }
                    } catch (Exception e) {
                        throw new SocketException();
                    }
                }

                throw new SocketException();
            }
        });
    }

    @Override
    public List<AppSettingEntity> getAppSettingByDb(Integer lib_idx) {
        DbHelper dh = new DbHelper(context);
        try{
            SQLiteDatabase rdb = dh.getReadableDatabase();
            return AppSettingDao.select(rdb,lib_idx);
        }catch (Exception e){
        }finally {
            dh.close();
        }
        return new ArrayList<>(0);
    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        ((MyApplication) context.getApplicationContext()).deleteAll();
        DbHelper db = new DbHelper(context);
        SQLiteDatabase wdb = null;
        try {
            wdb = db.getWritableDatabase();
            wdb.beginTransaction();
            logout(wdb);
            wdb.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e(TAG, "open writable database error");
        } finally {
            if (null != wdb) {
                wdb.endTransaction();
            }
            db.close();
        }
    }


    private void logout(SQLiteDatabase writeDb) {
        LoginInfoDao.delete(writeDb);
    }

    @Override
    public LoginInfoDbEntity getLoginInfo() {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase wdb = null;
        try {
            wdb = db.getWritableDatabase();
            wdb.beginTransaction();
            LoginInfoDbEntity model = LoginInfoDao.select(wdb);
            wdb.setTransactionSuccessful();
            return model;
        } catch (SQLException e) {
            Log.e(TAG, "open writable database error");
        } finally {
            if (null != wdb) {
                wdb.endTransaction();
            }
            db.close();
        }
        return null;
    }

    @Override
    public Observable<OperatorEntity> loginCheck() {
        return Observable.create(new ObservableOnSubscribe<OperatorEntity>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<OperatorEntity> subscriber) throws Exception {
                LoginInfoDbEntity loginInfo = getLoginInfo();
                if (loginInfo == null) {
                    throw new Exception("logout");
                }

                String url = RequestUrlUtil.getUrl(resources, R.string.login_url);
                Map<String, Object> param = new HashMap<>(6);
                param.put("operator_name", loginInfo.getOperator_name());
                param.put("operator_pwd", loginInfo.getOperator_pwd());
                Map<String, String> map = new HashMap<>();
                map.put("json", JsonUtils.toJson(param));
                HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
                if (hr.getState() == 200) {
                    try {
                        ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                        if (null != result.getResult() && result.getState()) {
                            Map<String, Object> operator = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), Map.class);
                            OperatorEntity entity = new OperatorEntity();
                            entity.setEmail((String) operator.get("email"));
                            entity.setMobile((String) operator.get("mobile"));
                            entity.setId_card((String) operator.get("id_card"));
                            entity.setOperator_id((String) operator.get("operator_id"));
                            entity.setOperator_idx(Integer.valueOf((String) operator.get("operator_idx")));
                            entity.setLib_id((String) operator.get("lib_id"));
                            entity.setOperator_name((String) operator.get("operator_name"));
                            entity.setPasswordSet((String) operator.get("password_set"));
                            subscriber.onNext(entity);
                            subscriber.onComplete();
                            return ;
                        }
                    } catch (Exception e) {
                        throw new SocketException("服务器没有传回预期数据");
                    }
                }

                throw new SocketException("网络异常");
            }
        });
    }

    private static List<AppSettingEntity> filterLib(List<AppSettingEntity> data, Integer lib_idx) {
        if (lib_idx == null) {
            return data;
        }
        List<AppSettingEntity> returnData = new ArrayList<>(data.size());
        for (AppSettingEntity appSettingEntity : data) {
            if (lib_idx.equals(appSettingEntity.getLib_idx())) {
                returnData.add(appSettingEntity);
            }
        }
        return returnData;
    }
}
