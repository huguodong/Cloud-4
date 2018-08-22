package com.ssitcloud.app_reader.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ssitcloud.app_reader.db.entity.ConfigDbEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2017/3/16.
 * app config dao
 */

public class ConfigDao {
    private final static String TABLE_NAME="config";
    private final static String[] rows = new String[]{"config_key","config_value"};
    /**
     * 插入一条数据到app config，若存在数据，则更新
     * @param writeDb 可写的数据库
     * @param entity 插入实体
     */
    public static void insert(SQLiteDatabase writeDb, ConfigDbEntity entity){
        if(entity.getConfigValue() == null || entity.getConfigKey() == null){
            throw new IllegalArgumentException("key and value must be not null");
        }
        ContentValues cv = new ContentValues();
        cv.put("config_key",entity.getConfigKey());
        cv.put("config_value",entity.getConfigValue());

        writeDb.replace(TABLE_NAME,null,cv);
    }

    /**
     * 查询所有配置
     * @param readDb 可读的数据库
     * @return 数据
     */
    public static List<ConfigDbEntity> selectAll(SQLiteDatabase readDb){
        Cursor query = readDb.query(TABLE_NAME,rows,"", new String[]{}, "", "", "");
        List<ConfigDbEntity> data = new ArrayList<>(query.getCount());
        try{
            while(query.moveToNext()){
                ConfigDbEntity config = new ConfigDbEntity();
                config.setConfigKey(query.getString(0));
                config.setConfigValue(query.getString(1));
                data.add(config);
            }
        }finally {
            query.close();
        }
        return data;
    }

    /**
     * 删除一条数据，根据key
     * @param writeDb 可写的数据库
     * @param key key
     */
    public static void deleteByEntity(SQLiteDatabase writeDb, String key){
        writeDb.delete(TABLE_NAME," config_key = ?",new String[]{key});
    }

    /**
     * 删除一条数据，根据实体
     * @param writeDb 可写的数据库
     * @param config 实体
     */
    public static void deleteByEntity(SQLiteDatabase writeDb, ConfigDbEntity config){
        writeDb.delete(TABLE_NAME," config_key = ? and config_value = ?",new String[]{config.getConfigKey(),config.getConfigValue()});
    }

    /**
     * 根据key查询配置
     * @param readDb  可读的数据库
     * @param key key
     * @return 配置，若查询不到返回null
     */
    public static ConfigDbEntity selectBykey(SQLiteDatabase readDb,String key){
        ConfigDbEntity data = null;
        Cursor query = readDb.query(TABLE_NAME,rows,"config_key = ?",new String[]{key},"","","");
        try {
            if (query.moveToNext()) {
                data = new ConfigDbEntity();
                data.setConfigKey(query.getString(0));
                data.setConfigValue(query.getString(1));
            }
        }finally {
            query.close();
        }

        return data;
    }
}
