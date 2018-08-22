package com.ssitcloud.app_reader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LXP on 2017/3/7.
 *
 */

public class DbHelper extends SQLiteOpenHelper {
    private final String TAG="SQLiteOpenHelper";

    private static final String DbNmae="reader";
    private static final int version = 9;

    //ReaderInfoDbEntity相关数据
    private final String dropReaderInfo = "DROP TABLE IF EXISTS login_info";
    private final String createReaderInfo="create table login_info(" +
            "reader_idx int," +
            "login_time date," +
            "login_name varchar(32)," +
            "reader_sex char(3)," +
            "pwd char(32)," +
            "reader_name varchar(32)," +
            "id_card varchar(24)," +
            "mobile varchar(12),"+
            "PRIMARY KEY (reader_idx) )";

    //readerCardDbEntity
    private final String dropReaderCard = "DROP TABLE IF EXISTS reader_card";
    private final String createReaderCard = "create table reader_card("+
            "card_no varchar(30) not null," +
            "lib_idx int not null," +
            "lib_id varchar(30), " +
            "lib_name varchar(30)," +
            "allown_loancount int," +
            "surplus_count int," +
            "advance_charge double," +
            "deposit double," +
            "arrearage double," +
            "create_time varchar(20)," +
            "update_time varchar(20)," +
            "card_pwd varchar(30)," +
            "reader_name varchar(50),"+
            "PRIMARY KEY (card_no, lib_idx) )";
    //app config
    private final String dropConfig = "DROP TABLE IF EXISTS config";
    private final String createConfig = "create table config(" +
            "config_key char(16) not null," +
            "config_value text not null," +
            "PRIMARY KEY (config_key) )";

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
    //elec
    private final String dropElec = "DROP TABLE IF EXISTS elec";
    private final String createElec = "CREATE TABLE elec (" +
            "electronic_idx int NOT NULL," +
            "libraryName varchar(32)," +
            "control_time timestamp NOT NULL," +
            "title varchar(32)," +
            "content text," +
            "state int,"+
            "PRIMARY KEY (electronic_idx) )";

    private final String dropFavorit = "DROP TABLE IF EXISTS favorit";
    private final String createFavorit = "CREATE TABLE favorit (" +
            "bookitem_idx int not null," +
            "bue_json text not null," +
            "opac_json text not null," +
            "PRIMARY KEY (bookitem_idx) )";

    public DbHelper(Context context) {
        super(context, DbNmae,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createReaderInfo);
        db.execSQL(createReaderCard);
        db.execSQL(createConfig);
        db.execSQL(createAppSetting);
        db.execSQL(createElec);
        db.execSQL(createFavorit);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion) {
            Log.i(TAG,"Update database newVersion==>"+newVersion+" oldVersion==>"+oldVersion);
            db.beginTransaction();
            try {
                db.execSQL(dropReaderInfo);
                db.execSQL(dropReaderCard);
                db.execSQL(dropConfig);
                db.execSQL(dropAppSetting);
                db.execSQL(dropElec);
                db.execSQL(dropFavorit);
                onCreate(db);
                db.setTransactionSuccessful();
            }finally {
                db.endTransaction();
            }
        }
    }
}
