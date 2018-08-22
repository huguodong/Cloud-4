package com.ssitcloud.app_operator.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.ssitcloud.app_operator.common.utils.ObjectUtils;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.entity.AppSettingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by LXP on 2017/3/20.
 *
 */

public class AppSettingDao {
    private final static String TABLE_NAME = "app_setting";
    private final static String[] rows = new String[]{"setting_idx", "user_type", "image_url", "lib_idx", "service_id", "setting_sort"};

    public static List<AppSettingEntity> select(SQLiteDatabase readDb, Integer lib_idx) {
        String selection = "";
        if (lib_idx != null) {
            selection = "lib_idx = " + lib_idx;
        }
        Cursor query = readDb.query(TABLE_NAME, rows, selection, new String[]{}, "", "", "setting_sort asc");
        try {
            List<AppSettingEntity> data = new ArrayList<>(query.getCount());
            while (query.moveToNext()) {
                AppSettingEntity a = new AppSettingEntity();
                a.setSetting_idx(StringUtils.parseInteger(query.getString(0)));
                a.setUser_type(query.getString(1));
                a.setImage_url(query.getString(2));
                a.setLib_idx(StringUtils.parseInteger(query.getString(3)));
                a.setService_id(query.getString(4));
                a.setSetting_sort(StringUtils.parseInteger(query.getString(5)));

                data.add(a);
            }
            return data;
        } finally {
            query.close();
        }
    }

    public static void insert(SQLiteDatabase writeDb, AppSettingEntity appSettingEntity) {
        ContentValues param = new ContentValues();
        Map<String, Object> map = ObjectUtils.objectToMap(appSettingEntity);
        map.remove("setting_desc");
        Set<String> keys = map.keySet();
        for (String s : keys) {
            param.put(s, StringUtils.getStr(map.get(s), null));
        }
        writeDb.replace(TABLE_NAME, null, param);
    }

    /**
     * 删除不在id数组内的appsetting设置
     *
     * @param writeDb
     * @param settingIds
     */
    public static void deleteNotIn(SQLiteDatabase writeDb, List<Integer> settingIds) {
        if (!settingIds.isEmpty()) {
            StringBuilder inSb = new StringBuilder("(");
            for (Integer settingId : settingIds) {
                inSb.append(settingId).append(',');
            }
            inSb.setCharAt(inSb.length()-1,')');
            writeDb.delete(TABLE_NAME, "setting_idx not in"+inSb.toString(), new String[]{});
        }else{
            deleteAll(writeDb);
        }
    }

    /**
     * 删除所有的appsetting设置
     * @param writeDb
     */
    public static void deleteAll(SQLiteDatabase writeDb){
        writeDb.delete(TABLE_NAME, "", new String[]{});
    }
}
