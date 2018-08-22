package com.ssitcloud.app_operator.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ssitcloud.app_operator.db.entity.MessageRemindDbEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 创建日期：2017/4/21 17:02
 *
 * @author shuangjunjie
 */

public class MessageRemindDao {

    private static final String TABLE_NAME = "message_remind";
    private static final String[] row = new String[]{"lib_idx", "trouble_idx", "log_idx",
            "device_idx", "trouble_info", "trouble_name", "trouble_time", "create_time", "state"};

    public static void insert(SQLiteDatabase db, MessageRemindDbEntity messageRemindDbEntity) {
        ContentValues cv = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        cv.put("lib_idx", messageRemindDbEntity.getLib_idx());
        cv.put("trouble_idx", messageRemindDbEntity.getTrouble_idx());
        cv.put("log_idx", messageRemindDbEntity.getLog_idx());
        cv.put("device_idx", messageRemindDbEntity.getDevice_idx());
        cv.put("trouble_info", messageRemindDbEntity.getTrouble_info());
        cv.put("trouble_name", messageRemindDbEntity.getTrouble_name());
        cv.put("trouble_time", messageRemindDbEntity.getTrouble_time());
        cv.put("create_time", sdf.format(messageRemindDbEntity.getCreate_time()));
        cv.put("state", messageRemindDbEntity.getState() != null ? messageRemindDbEntity.getState() : 0);
        db.replace(TABLE_NAME, null, cv);
    }

    public static void delete(SQLiteDatabase db) {
        db.delete(TABLE_NAME, "", new String[]{});
    }

    public static List<MessageRemindDbEntity> select(SQLiteDatabase db, Integer page, Integer pageSize) {
        String sql = "select * from message_remind order by create_time desc,trouble_time desc";
        String[] param;
        if (page != null && pageSize != null) {
            sql += " limit ?,?";
            param = new String[]{String.valueOf((page - 1) * pageSize), String.valueOf(pageSize)};
        } else {
            param = new String[]{};
        }
        Cursor c = db.rawQuery(sql, param);
        return getCursorList(c);
    }

    public static void update(SQLiteDatabase db, MessageRemindDbEntity entity) {
        ContentValues values = new ContentValues();
        values.put("state", entity.getState());
        db.update(TABLE_NAME, values, "trouble_idx = ?", new String[]{entity.getDevice_idx().toString()});
    }

    private static List<MessageRemindDbEntity> getCursorList(Cursor c) {
        List<MessageRemindDbEntity> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String[] coloms = c.getColumnNames();//[_id,name] 有多少字段项
        while (c.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < coloms.length; i++) {
                String key = coloms[i];
                Object values = null;
                int type = c.getType(i);
                switch (type) {
                    case Cursor.FIELD_TYPE_BLOB:
                        values = c.getBlob(i);
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        values = c.getFloat(i);
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        values = c.getInt(i);
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        values = c.getString(i);
                        break;

                    default:
                        break;
                }
                map.put(key, values);
            }
            MessageRemindDbEntity m = new MessageRemindDbEntity();
            m.setLib_idx((Integer) map.get("lib_idx"));
            m.setDevice_idx((Integer) map.get("device_idx"));
            m.setTrouble_idx((Integer) map.get("trouble_idx"));
            m.setTrouble_name((String) map.get("trouble_name"));
            m.setTrouble_info((String) map.get("trouble_info"));
            m.setTrouble_time((String) map.get("trouble_time"));
            try {
                m.setCreate_time(sdf.parse((String) map.get("create_time")));
            } catch (ParseException e) {
            }
            m.setState((Integer) map.get("state"));
            list.add(m);
        }

        return list;

    }
}
