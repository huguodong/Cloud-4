package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.db.DbHelper;
import com.ssitcloud.app_reader.db.dao.ConfigDao;
import com.ssitcloud.app_reader.db.entity.ConfigDbEntity;
import com.ssitcloud.app_reader.entity.AppOPACEntity;
import com.ssitcloud.app_reader.entity.PublicKeyEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by LXP on 2017/3/29.
 * 配置业务实现类
 */

public class ConfigBizImpl implements ConfigBizI {
    private Context mContext;

    private final String AUTH_CODE_KEY="AUTH_CODE_KEY";

    private final String DEVICE_KEY="DEVICE_KEY";

    private final String PUK_KEY="Puk_KEY";

    private final String REMIND_DAY_KEY="REMIND_DAY_KEY";

    private final String MESSAGE_PUSH_KEY="MESSAGE_PUSH_KEY";

    private final String UN_READER_ELEC_COUNT_KEY="UN_READER_ELEC_COUNT_KEY";

    private final String REMIND_BOOK_BARCODE_KEY="REMIND_BOOK_BARCODE_KEY";

    public ConfigBizImpl(Context context){
        mContext = context.getApplicationContext();
    }

    @Override
    public void saveAuthCode(String value) {
        DbHelper dh = new DbHelper(mContext);
        try {
            SQLiteDatabase wdb = dh.getWritableDatabase();
            ConfigDbEntity authcode = new ConfigDbEntity();
            authcode.setConfigKey(AUTH_CODE_KEY);
            authcode.setConfigValue(value);
            ConfigDao.insert(wdb, authcode);
        }finally {
            dh.close();
        }
    }

    @Override
    public String getAuthCode() {
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase rdb = null;
        try {
            rdb = dbHelper.getReadableDatabase();
            ConfigDbEntity configDbEntity = ConfigDao.selectBykey(rdb, AUTH_CODE_KEY);
            if (configDbEntity != null) {
                return configDbEntity.getConfigValue();
            }
        }finally {
            dbHelper.close();
        }

        return null;
    }

    @Override
    public void deletePreferData() {
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase wdb = null;
        try {
            wdb = dbHelper.getWritableDatabase();
            ConfigDao.deleteByEntity(wdb,AUTH_CODE_KEY);
        }finally {
            dbHelper.close();
        }
    }

    @Override
    public void savePreferDevice(AppOPACEntity opac) {
        if(opac == null){//delete
            DbHelper dbHelper = new DbHelper(mContext);
            try {
                SQLiteDatabase wdb = dbHelper.getWritableDatabase();
                ConfigDao.deleteByEntity(wdb,DEVICE_KEY);
            } finally {
                dbHelper.close();
            }
        }else {//insert
            DbHelper dbHelper = new DbHelper(mContext);
            try {
                SQLiteDatabase wdb = dbHelper.getWritableDatabase();
                String json = JsonUtils.toJson(opac);
                ConfigDbEntity entity = new ConfigDbEntity();
                entity.setConfigKey(DEVICE_KEY);
                entity.setConfigValue(json);
                ConfigDao.insert(wdb, entity);
            } finally {
                dbHelper.close();
            }
        }
    }

    @Override
    public AppOPACEntity getPreferDevice() {
        DbHelper dbHelper = new DbHelper(mContext);
        try{
            SQLiteDatabase rdb = dbHelper.getReadableDatabase();

            ConfigDbEntity configDbEntity = ConfigDao.selectBykey(rdb, DEVICE_KEY);
            if(configDbEntity != null){
                return JsonUtils.fromJson(configDbEntity.getConfigValue(),AppOPACEntity.class);
            }
        }finally {
            dbHelper.close();
        }
        return null;
    }

    @Override
    public void updatePuk(PublicKeyEntity pukData) {
        String pukJson = JsonUtils.toJson(pukData);

        DbHelper dbHelper = new DbHelper(mContext);
        try{
            SQLiteDatabase wdb = dbHelper.getWritableDatabase();
            ConfigDbEntity configDbEntity = new ConfigDbEntity();
            configDbEntity.setConfigKey(PUK_KEY);
            configDbEntity.setConfigValue(pukJson);
            ConfigDao.insert(wdb,configDbEntity);
        }finally {
            dbHelper.close();
        }

    }

    @Override
    public PublicKeyEntity getPuk() {
        DbHelper dbHelper = new DbHelper(mContext);
        try{
            SQLiteDatabase rdb = dbHelper.getReadableDatabase();

            ConfigDbEntity configDbEntity = ConfigDao.selectBykey(rdb, PUK_KEY);
            if(configDbEntity != null){
                return JsonUtils.fromJson(configDbEntity.getConfigValue(),PublicKeyEntity.class);
            }
        }finally {
            dbHelper.close();
        }
        return null;
    }

    @Override
    public void setRemindTime(int day) {
        DbHelper dbHelper = new DbHelper(mContext);
        try{
            SQLiteDatabase wdb = dbHelper.getWritableDatabase();
            ConfigDbEntity configDbEntity = new ConfigDbEntity();
            configDbEntity.setConfigKey(REMIND_DAY_KEY);
            configDbEntity.setConfigValue(Integer.valueOf(day).toString());
            ConfigDao.insert(wdb,configDbEntity);
        }finally {
            dbHelper.close();
        }
    }

    @Override
    public int getRemindTime() {
        DbHelper dbHelper = new DbHelper(mContext);
        try{
            SQLiteDatabase rdb = dbHelper.getReadableDatabase();

            ConfigDbEntity configDbEntity = ConfigDao.selectBykey(rdb, REMIND_DAY_KEY);
            if(configDbEntity != null){
                return Integer.valueOf(configDbEntity.getConfigValue());
            }else{
                return 3;
            }
        }finally {
            dbHelper.close();
        }
    }

    @Override
    public boolean getMessagePushState() {
        DbHelper dbHelper = new DbHelper(mContext);
        try{
            SQLiteDatabase rdb = dbHelper.getReadableDatabase();

            ConfigDbEntity configDbEntity = ConfigDao.selectBykey(rdb, MESSAGE_PUSH_KEY);
            if(configDbEntity != null){
                return Boolean.valueOf(configDbEntity.getConfigValue());
            }else{
                return true;
            }
        }finally {
            dbHelper.close();
        }
    }

    @Override
    public void setMessagePushState(boolean state) {
        DbHelper dbHelper = new DbHelper(mContext);
        try{
            SQLiteDatabase wdb = dbHelper.getWritableDatabase();
            ConfigDbEntity configDbEntity = new ConfigDbEntity();
            configDbEntity.setConfigKey(MESSAGE_PUSH_KEY);
            configDbEntity.setConfigValue(Boolean.valueOf(state).toString());
            ConfigDao.insert(wdb,configDbEntity);
        }finally {
            dbHelper.close();
        }
    }

    @Override
    public int getElecNoticeCount() {
        ConfigDbEntity cde = getConfig(UN_READER_ELEC_COUNT_KEY);
        if(cde == null) {
            return 0;
        }else{
            return Integer.valueOf(cde.getConfigValue());
        }
    }

    @Override
    public void setElecNoticeCount(int count) {
        ConfigDbEntity cde = new ConfigDbEntity();
        cde.setConfigKey(UN_READER_ELEC_COUNT_KEY);
        cde.setConfigValue(String.valueOf(count));
        setConfig(cde);
    }

    @Override
    public void setRemindBookBarcode(Set<String> bookBarcodes) {
        StringBuilder sb = new StringBuilder();
        for (String bookBarcode : bookBarcodes) {
            sb.append(bookBarcode).append(',');
        }
        if(sb.length()>0){
            sb.deleteCharAt(sb.length()-1);
        }
        ConfigDbEntity cde = new ConfigDbEntity();
        cde.setConfigKey(REMIND_BOOK_BARCODE_KEY);
        cde.setConfigValue(sb.toString());
        setConfig(cde);
    }

    @Override
    public Set<String> getRemindBookBarcode() {
        ConfigDbEntity config = getConfig(REMIND_BOOK_BARCODE_KEY);
        if(config == null || StringUtils.isEmpty(config.getConfigValue())){
            return new HashSet<>(0);
        }
        String value = config.getConfigValue();
        String[] temp = value.split(",");
        Set<String> s = new HashSet<>(temp.length+1,1.0f);
        s.addAll(Arrays.asList(temp));
        return s;
    }

    private void setConfig(ConfigDbEntity configDbEntity){
        DbHelper dbHelper = new DbHelper(mContext);
        try{
            SQLiteDatabase wdb = dbHelper.getWritableDatabase();
            ConfigDao.insert(wdb,configDbEntity);
        }finally {
            dbHelper.close();
        }
    }


    private ConfigDbEntity getConfig(String key){
        DbHelper dbHelper = new DbHelper(mContext);
        try{
            SQLiteDatabase rdb = dbHelper.getReadableDatabase();

            ConfigDbEntity configDbEntity = ConfigDao.selectBykey(rdb, key);
            return configDbEntity;
        }finally {
            dbHelper.close();
        }
    }

}
