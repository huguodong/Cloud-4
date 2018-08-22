package com.ssitcloud.app_reader.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.db.entity.FavoritDbEntity;
import com.ssitcloud.app_reader.entity.AppOPACEntity;
import com.ssitcloud.app_reader.entity.BookUnionEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2017/4/12.
 *
 */

public class FavoritDao {
    private static final String[] rows = new String[]{"bookitem_idx","bue_json","opac_json"};
    private static final String TABLE_NAME="favorit";

    /**
     * 插入数据到数据库，采用replace方式
     */
    public static void insert(SQLiteDatabase wdb, FavoritDbEntity entity){
        ContentValues values = new ContentValues();
        values.put("bookitem_idx", entity.getBookitem_idx());
        values.put("bue_json",JsonUtils.toJson(entity.getBue()));
        values.put("opac_json",JsonUtils.toJson(entity.getOpac()));
        wdb.replace(TABLE_NAME,null,values);
    }

    /**
     * 根据主键查询
     * @param rdb
     * @param idx
     */
    public static FavoritDbEntity select(SQLiteDatabase rdb,Integer idx){
        List<FavoritDbEntity> d = selectAllData(rdb,idx);
        if(d.isEmpty()){
            return null;
        }else{
            return d.get(0);
        }
    }

    public static List<FavoritDbEntity> selectAll(SQLiteDatabase rdb,Integer idx){
        return selectAllData(rdb,idx);
    }

    private static List<FavoritDbEntity> selectAllData(SQLiteDatabase rdb,Integer idx){
        String selection = "";
        if(idx != null){
            selection = "bookitem_idx = "+idx;
        }
        Cursor query = rdb.query(TABLE_NAME, rows, selection, new String[]{}, "", "", "");
        List<FavoritDbEntity> data = new ArrayList<>(query.getCount());
        try{
            while (query.moveToNext()){
                FavoritDbEntity e = new FavoritDbEntity();
                e.setBookitem_idx(StringUtils.parseInteger(query.getString(0)));
                e.setBue(JsonUtils.fromJson(query.getString(1), BookUnionEntity.class));
                e.setOpac(JsonUtils.fromJson(query.getString(2), AppOPACEntity.class));
                data.add(e);
            }
            return data;
        }finally {
            query.close();
        }
    }

    /**
     * 删除一条收藏夹数据
     * @param wdb
     * @param idx
     */
    public static void delete(SQLiteDatabase wdb,Integer idx){
        wdb.delete(TABLE_NAME,"bookitem_idx = ?",new String[]{idx.toString()});
    }

    /**
     * 删除所有收藏夹数据
     * @param wdb
     */
    public static void deleteAll(SQLiteDatabase wdb){
        wdb.delete(TABLE_NAME,"",new String[]{});
    }
}
