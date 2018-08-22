package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;
import android.content.Entity;

import com.ssitcloud.app_reader.biz.FavoritBizI;
import com.ssitcloud.app_reader.db.DbHelper;
import com.ssitcloud.app_reader.db.dao.FavoritDao;
import com.ssitcloud.app_reader.db.entity.FavoritDbEntity;

import java.util.List;

/**
 * Created by LXP on 2017/4/12.
 *
 */

public class FavoritBizImpl implements FavoritBizI {
    private Context mcontext;

    public FavoritBizImpl(Context context){
        mcontext = context.getApplicationContext();
    }

    @Override
    public List<FavoritDbEntity> selectAll() {
        DbHelper dh = new DbHelper(mcontext);
        try{
            return FavoritDao.selectAll(dh.getReadableDatabase(),null);
        }finally {
            dh.close();
        }
    }

    @Override
    public FavoritDbEntity select(Integer idx) {
        DbHelper dh = new DbHelper(mcontext);
        try{
            return FavoritDao.select(dh.getReadableDatabase(),idx);
        }finally {
            dh.close();
        }
    }

    @Override
    public void insert(FavoritDbEntity entity) {
        DbHelper dh = new DbHelper(mcontext);
        try{
            FavoritDao.insert(dh.getWritableDatabase(),entity);
        }finally {
            dh.close();
        }
    }

    @Override
    public void delete(Integer idx) {
        DbHelper dh = new DbHelper(mcontext);
        try{
            FavoritDao.delete(dh.getWritableDatabase(),idx);
        }finally {
            dh.close();
        }
    }

    @Override
    public void deleteAll() {
        DbHelper dh = new DbHelper(mcontext);
        try{
            FavoritDao.deleteAll(dh.getWritableDatabase());
        }finally {
            dh.close();
        }
    }
}
