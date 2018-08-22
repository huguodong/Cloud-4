package com.ssitcloud.app_reader.entity;

import com.ssitcloud.app_reader.db.entity.FavoritDbEntity;

/**
 * Created by LXP on 2017/4/12.
 *
 */

public class FavoritEntity extends FavoritDbEntity {
    private int nowState;

    public int getNowState() {
        return nowState;
    }

    public void setNowState(int nowState) {
        this.nowState = nowState;
    }
}
