package com.ssitcloud.app_reader.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ssitcloud.app_reader.common.utils.ObjectUtils;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.AppElectronicEntity;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by LXP on 2017/3/30.
 * 电子凭证操作dao
 */

public class ElecDao {
    private static final String TABLE_NAME="elec";
    private static final String[] rows=new String[]{
            "electronic_idx","libraryName","control_time","title","content","state"};

    /**
     * 插入一条电子凭证
     * @param wdb
     * @param elec
     */
    public static void insert(SQLiteDatabase wdb, AppElectronicEntity elec){
        Map<String,Object> mElec = ObjectUtils.objectToMap(elec);
        ContentValues param = new ContentValues();
        Set<String> keySet = mElec.keySet();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (String s : keySet) {
            if(s.equals("control_time")){
                param.put(s, sdf.format(elec.getControl_time()));
            }else {
                param.put(s, StringUtils.getStr(mElec.get(s), null));
            }
        }
        wdb.replace(TABLE_NAME,null,param);
    }

    /**
     * 取出数据库中所有的电子凭证
     * @param rdb
     * @return
     */
    public static List<AppElectronicEntity> selectAll(SQLiteDatabase rdb){
        Cursor query = rdb.query(TABLE_NAME, rows, "", new String[]{}, "", "", "");
        try{
            List<AppElectronicEntity> d = new ArrayList<>(query.getCount());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            while (query.moveToNext()){
                AppElectronicEntity e = new AppElectronicEntity();
                e.setElectronic_idx(query.getInt(0));
                e.setLibraryName(query.getString(1));


                try {
                    e.setControl_time(new Timestamp(sdf.parse(query.getString(2)).getTime()));
                } catch (ParseException e1) {
                }
                e.setTitle(query.getString(3));
                e.setConetent(query.getString(4));
                e.setState(StringUtils.parseInteger(query.getString(5)));

                d.add(e);
            }

            return d;
        }finally {
            query.close();
        }
    }

    public static void deleteAll(SQLiteDatabase wdb){
        wdb.delete(TABLE_NAME,"",new String[]{});
    }
}
