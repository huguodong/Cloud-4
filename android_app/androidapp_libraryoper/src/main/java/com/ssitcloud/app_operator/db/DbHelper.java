package com.ssitcloud.app_operator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private final String TAG="SQLiteOpenHelper";

    private static final String DbNmae="operator";
    private static final int version = 6;

    //LoginInfoDbEntity相关数据
    private final String dropOperator = "DROP TABLE IF EXISTS login_info";
    private final String createOperator="create table login_info(" +
            "operator_idx int," +
            "mobile char(32),"+
            "login_time date,"+
            "operator_pwd char(32),"+
            "operator_name char(32)"+
            ")";

    //MessageRemindDbEntity相关数据
    private final String dropMessageRemind = "DROP TABLE IF EXISTS message_remind";
    private final String createMessageRemind="create table message_remind(" +
            "trouble_idx int," +
            "lib_idx int," +
            "log_idx int," +
            "device_idx int,"+
            "trouble_info char(32),"+
            "trouble_name char(32),"+
            "trouble_time char(32),"+
            "create_time char(32)," +
            "state int,"+
            "PRIMARY KEY (trouble_idx) )";
    //app setting
    private final String dropAppSetting = "DROP TABLE IF EXISTS app_setting";
    private final String createAppSetting = "create table app_setting(" +
            "setting_idx int," +
            "user_type char(5)," +
            "image_url varchar(256)," +
            "lib_idx int," +
            "service_id varchar(25)," +
            "setting_sort int," +
            "PRIMARY KEY (setting_idx) )";

    public DbHelper(Context context) {
        super(context, DbNmae,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createOperator);
        db.execSQL(createMessageRemind);
        db.execSQL(createAppSetting);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion) {
            Log.i(TAG,"Update database newVersion==>"+newVersion+" oldVersion==>"+oldVersion);
            db.beginTransaction();
            try {
                db.execSQL(dropOperator);
                db.execSQL(dropMessageRemind);
                db.execSQL(dropAppSetting);
                onCreate(db);
                db.setTransactionSuccessful();
            }finally {
                db.endTransaction();
            }
        }
    }

}
