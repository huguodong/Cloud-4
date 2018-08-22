package com.ssitcloud.app_reader.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by LXP on 2017/3/7.
 *
 */

public class LoginInfoDao {
    private static final String TABLE_NAME = "login_info";
    private static final String[] row = new String[]{"reader_idx", "login_time","login_name","reader_sex","pwd","reader_name","id_card","mobile"};

    /**
     * 插入登陆对象
     *
     * @param db
     * @param model
     */
    public static void insert(SQLiteDatabase db, LoginInfoDbEntity model) {
        ContentValues param = new ContentValues();
        param.put("reader_idx", model.getReader_idx());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        param.put("login_time", sdf.format(model.getLogin_time()));
        param.put("login_name",model.getLogin_name());
        param.put("reader_sex",model.getReader_sex());
        param.put("pwd",model.getPwd());
        param.put("reader_name",model.getReader_name());
        param.put("id_card",model.getId_card());
        param.put("mobile",model.getMobile());

        db.replace(TABLE_NAME, null, param);
    }

    public static void delete(SQLiteDatabase db){
        db.delete(TABLE_NAME,"",new String[]{});
    }

    public static LoginInfoDbEntity select(SQLiteDatabase db) {
        Cursor query = db.query(TABLE_NAME,row , "", new String[]{}, "", "", "");
        try {
            if (query.moveToNext()) {
                LoginInfoDbEntity model = new LoginInfoDbEntity();
                model.setReader_idx(query.getInt(0));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = query.getString(1);
                if (date != null) {
                    try {
                        model.setLogin_time(sdf.parse(date));
                    } catch (ParseException e) {
                    }
                }
                model.setLogin_name(query.getString(2));
                model.setReader_sex(query.getString(3));
                model.setPwd(query.getString(4));
                model.setReader_name(query.getString(5));
                model.setId_card(query.getString(6));
                model.setMobile(query.getString(7));
                return model;
            }
        }finally {
            query.close();
        }
        return null;
    }
}
