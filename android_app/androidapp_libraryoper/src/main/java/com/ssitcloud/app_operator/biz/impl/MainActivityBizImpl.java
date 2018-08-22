package com.ssitcloud.app_operator.biz.impl;

import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.common.entity.HttpResponce;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.HttpClientUtil;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.RequestUrlUtil;
import com.ssitcloud.app_operator.db.DbHelper;
import com.ssitcloud.app_operator.db.dao.LoginInfoDao;
import com.ssitcloud.app_operator.db.entity.LoginInfoDbEntity;

import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建日期：2017/4/25 19:31
 * @author shuangjunjie
 */

public class MainActivityBizImpl{
    private final String TAG = "MainActivityBizImpl";
    private final String charset = "utf-8";
    private Resources resources;
    private Context context;

    public MainActivityBizImpl(Resources resources, Context context) {
        this.resources = resources;
        this.context = context.getApplicationContext();
    }

    /**
     * 登录
     * @param operatorMap
     * @return
     */
    public ResultEntity login(Map<String,Object> operatorMap) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.login_url);
        Map<String, String> map = new HashMap<>();
        map.put("json", JsonUtils.toJson(operatorMap));
        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if (hr.getState() == 200) {
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                if (null != result.getResult() && result.getState()) {
                    Map<String,Object> operator = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), Map.class);
                    if (null != operator) {
                        LoginInfoDbEntity lfd = new LoginInfoDbEntity();
                        lfd.setOperator_idx(Integer.parseInt(operator.get("operator_idx").toString()));

                        lfd.setLogin_time(new Date());
                        lfd.setOperator_pwd(operatorMap.get("operator_pwd").toString());
                        if (operator.containsKey("mobile") && null != operator.get("mobile")){
                            lfd.setMobile(operator.get("mobile").toString());
                        }
                        if (operator.containsKey("operator_id") && null != operator.get("operator_id")){
                            lfd.setOperator_name(operator.get("operator_id").toString());
                        }
//                        System.out.println("mobile>>>>>"+operator.get("mobile").toString());

                        DbHelper db = new DbHelper(context);
                        SQLiteDatabase wdb = null;
                        try {
                            wdb = db.getWritableDatabase();
                            wdb.beginTransaction();
                            logout(wdb);
                            LoginInfoDao.insert(wdb,lfd);
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

    /**
     * 登出
     */

    public void logout() {
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


    private void logout(SQLiteDatabase writeDb){
        LoginInfoDao.delete(writeDb);
    }


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


    public ResultEntity selectDeviceTroublesByLibIdxs(Map<String,Object> reqMap) throws SocketException{
        String url = RequestUrlUtil.getUrl(resources, R.string.selectDeviceTroublesByLibIdxs);
        Map<String, String> map = new HashMap<>();
        map.put("json", JsonUtils.toJson(reqMap));
        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if (hr.getState() == 200) {
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            throw new SocketException("网络异常");
        }
    }

}
