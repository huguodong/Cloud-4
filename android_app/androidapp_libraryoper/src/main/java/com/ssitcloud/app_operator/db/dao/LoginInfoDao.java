package com.ssitcloud.app_operator.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ssitcloud.app_operator.db.entity.LoginInfoDbEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 创建日期：2017/3/16 17:05
 * @author shuangjunjie
 */

public class LoginInfoDao {

    private static final String TABLE_NAME = "login_info";
    private static final String[] row = new String[]{"operator_idx","mobile","login_time","operator_pwd","operator_name"};

    public static void insert(SQLiteDatabase db, LoginInfoDbEntity loginInfoDbEntity){
        ContentValues cv = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        cv.put("operator_idx",loginInfoDbEntity.getOperator_idx());
        cv.put("mobile",loginInfoDbEntity.getMobile());
        cv.put("login_time",sdf.format(loginInfoDbEntity.getLogin_time()));
        cv.put("operator_pwd",loginInfoDbEntity.getOperator_pwd());
        cv.put("operator_name",loginInfoDbEntity.getOperator_name());
        db.insert(TABLE_NAME,null,cv);
    }

    public static void delete(SQLiteDatabase db){
        db.delete(TABLE_NAME,"",new String[]{});
    }

    public static LoginInfoDbEntity select(SQLiteDatabase db){
        Cursor query = db.query(TABLE_NAME,row , "", new String[]{}, "", "", "");
        try {
            if (query.moveToNext()) {
                LoginInfoDbEntity model = new LoginInfoDbEntity();
                model.setOperator_idx(query.getInt(0));
                model.setMobile(query.getString(1));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = query.getString(2);
                if (date != null) {
                    try {
                        model.setLogin_time(sdf.parse(date));
                    }catch (ParseException e){

                    }
                }
                model.setOperator_pwd(query.getString(3));
                model.setOperator_name(query.getString(4));
                return model;
            }

        }finally {
            query.close();
        }
        return null;
    }
}
