package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.ObjectUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.db.DbHelper;
import com.ssitcloud.app_reader.db.dao.ConfigDao;
import com.ssitcloud.app_reader.db.dao.ElecDao;
import com.ssitcloud.app_reader.db.dao.FavoritDao;
import com.ssitcloud.app_reader.db.dao.LoginInfoDao;
import com.ssitcloud.app_reader.db.dao.ReaderCardDao;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2017/3/7.
 *
 */

public class LoginBizImpl extends AbstractAuthBizImpl implements LoginBizI {
    private final String TAG = "RegisterBizImpl";
    private final String charset = "utf-8";
    private Resources resources;
    private Context context;

    private ConfigBizI configBiz;
    
    public LoginBizImpl(Resources resources, Context context) {
        this.resources = resources;
        this.context = context.getApplicationContext();
        configBiz = new ConfigBizImpl(this.context);
    }

    @Override
    public ResultEntity login(ReaderInfoEntity readerInfo) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.login_url);
        Map<String, String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(readerInfo));
        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if (hr.getState() == 200) {
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                //服务器返回的正常的数据，且登陆成功
                if (result != null && result.getState()) {
                    ReaderInfoEntity r = ObjectUtils.convertMap((Map<String, Object>) result.getResult(), ReaderInfoEntity.class);
                    if (r != null) {
                        LoginInfoDbEntity dbModel = new LoginInfoDbEntity();
                        dbModel.setReader_idx(r.getReader_idx());
                        dbModel.setLogin_time(new Date());
                        dbModel.setReader_sex(r.getReader_sex());
                        dbModel.setLogin_name(r.getLogin_name());
                        dbModel.setReader_name(r.getReader_name());
                        dbModel.setMobile(r.getMobile());
                        dbModel.setId_card(r.getId_card());

                        DbHelper dbHelper = new DbHelper(context);
                        SQLiteDatabase wdb = null;
                        try {
                            wdb = dbHelper.getWritableDatabase();
                            wdb.beginTransaction();
                            LoginInfoDao.delete(wdb);//删除旧的登陆数据
                            FavoritDao.deleteAll(wdb);//删除收藏夹数据

                            LoginInfoDao.insert(wdb, dbModel);
                            wdb.setTransactionSuccessful();
                        } catch (SQLiteException e) {
                            Log.e(TAG, "open writable database error");
                        }finally {
                            if(wdb!=null){
                                wdb.endTransaction();
                            }
                            dbHelper.close();
                        }
                        //插入登陆凭证
                        configBiz.saveAuthCode(result.getRetMessage());
                    }
                    result.setResult(r);
                }

                return result;

            } catch (Exception e) {
                Log.e(TAG, "json转换对象出错", e);
                throw new SocketException("服务器没有传回预期数据");
            }
        } else {
            throw new SocketException("网络异常");
        }
    }

    @Override
    public void logout() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase writeDb = null;

        try{
            writeDb = dbHelper.getWritableDatabase();
            writeDb.beginTransaction();
            logout(writeDb);
            writeDb.setTransactionSuccessful();
        }catch (SQLException e){
            Log.e(TAG,"logout error==>"+e.getMessage());
        }finally {
            if(writeDb != null){
                writeDb.endTransaction();
            }
            dbHelper.close();
        }
    }

    /**
     * 封装登出需要抹除的数据
     * @param writeDb 可写的数据库
     */
    private void logout(SQLiteDatabase writeDb) {
        LoginInfoDao.delete(writeDb);
        ReaderCardDao.deleteAll(writeDb);
        ElecDao.deleteAll(writeDb);
    }

    @Override
    public Integer isLogin() {
        LoginInfoDbEntity ldbe = getLoginReturnData();
        if (ldbe != null) {
            return ldbe.getReader_idx();
        }

        return null;
    }

    @Override
    public LoginInfoDbEntity getLoginReturnData() {
        DbHelper dbHelper = new DbHelper(context);
        try {
            SQLiteDatabase rdb = dbHelper.getReadableDatabase();
            LoginInfoDbEntity ldbe = LoginInfoDao.select(rdb);
            return ldbe;
        }catch (SQLException e){
            Log.e(TAG,"open database error,"+e.getMessage());
        }finally {
            dbHelper.close();
        }
        return null;
    }

    @Override
    public ReaderInfoEntity obtainReader(Integer reader_idx) throws SocketException, AuthException {
        String url = RequestUrlUtil.getUrl(context.getResources(),R.string.obtain_reader_url);
        Map<String,String> map = new HashMap<>(3);
        map.put("json","{\"reader_idx\":"+reader_idx+"}");

        addAuthMessage(map);

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String json = hr.getBody();
            try{
                ResultEntity resultEntity = JsonUtils.fromJson(json,ResultEntity.class);
                if(resultEntity.getState()) {
                    ReaderInfoEntity r = ObjectUtils.convertMap((Map<String, Object>) resultEntity.getResult(), ReaderInfoEntity.class);
                    if (r != null) {
                        LoginInfoDbEntity dbModel = new LoginInfoDbEntity();
                        dbModel.setReader_idx(r.getReader_idx());
                        dbModel.setLogin_time(new Date());
                        dbModel.setReader_sex(r.getReader_sex());
                        dbModel.setLogin_name(r.getLogin_name());
                        dbModel.setReader_name(r.getReader_name());
                        dbModel.setMobile(r.getMobile());
                        dbModel.setId_card(r.getId_card());
                        DbHelper dbHelper = new DbHelper(context);
                        SQLiteDatabase wdb = null;
                        try {
                            wdb = dbHelper.getWritableDatabase();
                            wdb.beginTransaction();

                            LoginInfoDao.insert(wdb, dbModel);
                            wdb.setTransactionSuccessful();
                        } catch (SQLiteException e) {
                            Log.e(TAG, "open writable database error");
                        } finally {
                            if (wdb != null) {
                                wdb.endTransaction();
                            }
                            dbHelper.close();
                        }
                    }
                    return r;
                }
            }catch (Exception e){
                throw new SocketException("obtainReader服务器没有返回预期数据");
            }
        }else if(hr.getState() == 403){
            throw new AuthException();
        }

        throw new SocketException("obtainReader连接服务器失败");
    }

    @Override
    protected ConfigBizI getConfigBiz() {
        return configBiz;
    }
}
