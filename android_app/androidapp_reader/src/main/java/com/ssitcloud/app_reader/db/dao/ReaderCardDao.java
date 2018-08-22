package com.ssitcloud.app_reader.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler;

import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2017/3/9.
 */

public class ReaderCardDao {
    private static final String TABLE_NAME = "reader_card";

    /**
     * 插入登陆对象
     *
     * @param writeDb
     * @param model
     */
    public static void insert(SQLiteDatabase writeDb, ReaderCardDbEntity model) {
        ContentValues param = new ContentValues();
        param.put("card_no", model.getCard_no());
        param.put("lib_idx", model.getLib_idx());
        param.put("lib_id",model.getLib_id());
        param.put("lib_name", model.getLib_name());

        param.put("allown_loancount",model.getAllown_loancount());
        param.put("surplus_count", model.getSurplus_count());
        param.put("advance_charge", model.getAdvance_charge());
        param.put("deposit", model.getDeposit());
        param.put("arrearage", model.getArrearage());
        param.put("create_time", model.getCreate_time());
        param.put("update_time", model.getUpdate_time());
        param.put("reader_name", model.getReader_name());

        if(model.getCardPwd() != null){
            param.put("card_pwd",model.getCardPwd());
        }
        writeDb.replace(TABLE_NAME, null, param);
    }

    public static void update(SQLiteDatabase writeDb, ReaderCardDbEntity entity){
        ContentValues value = new ContentValues();
        value.put("allown_loancount",entity.getAllown_loancount());
        value.put("surplus_count", entity.getSurplus_count());
        value.put("advance_charge", entity.getAdvance_charge());
        value.put("deposit", entity.getDeposit());
        value.put("arrearage", entity.getArrearage());
        value.put("reader_name", entity.getReader_name());
        writeDb.update(TABLE_NAME, value,"card_no = ? and lib_idx = ?",new String[]{entity.getCard_no(), entity.getLib_idx().toString()});
    }

    public static void delete(SQLiteDatabase writeDb, ReaderCardDbEntity whereModel) {
        if (StringUtils.isEmpty(whereModel.getCard_no()) || whereModel.getLib_idx() == null) {
            return;
        }
        writeDb.delete(TABLE_NAME, "card_no = ? and lib_idx = ?", new String[]{whereModel.getCard_no(), whereModel.getLib_idx().toString()});
    }

    public static void deleteAll(SQLiteDatabase writeDb) {
        writeDb.delete(TABLE_NAME, "", new String[]{});
    }

    public static List<ReaderCardDbEntity> select(SQLiteDatabase readDb) {
        String[] rows = new String[]{"card_no", "lib_idx","lib_id", "lib_name","allown_loancount","surplus_count","advance_charge",
                                     "deposit","arrearage","create_time","update_time","card_pwd","reader_name"};
        Cursor query = readDb.query(TABLE_NAME, rows, "", new String[]{}, "", "", "");
        try {
            List<ReaderCardDbEntity> dbData = new ArrayList<>();
            while (query.moveToNext()) {
                ReaderCardDbEntity dbModel = new ReaderCardDbEntity();
                dbModel.setCard_no(query.getString(0));
                dbModel.setLib_idx(query.getInt(1));
                dbModel.setLib_id(query.getString(2));
                dbModel.setLib_name(query.getString(3));
                dbModel.setAllown_loancount(StringUtils.parseInteger(query.getString(4)));
                dbModel.setSurplus_count(StringUtils.parseInteger(query.getString(5)));
                dbModel.setAdvance_charge(StringUtils.parseDouble(query.getString(6)));
                dbModel.setDeposit(StringUtils.parseDouble(query.getString(7)));
                dbModel.setArrearage(StringUtils.parseDouble(query.getString(8)));
                dbModel.setCreate_time(query.getString(9));
                dbModel.setUpdate_time(query.getString(10));
                dbModel.setCardPwd(query.getString(11));
                dbModel.setReader_name(query.getString(12));
                //add list
                dbData.add(dbModel);
            }
            return dbData;
        }finally {
            query.close();
        }
    }
}
