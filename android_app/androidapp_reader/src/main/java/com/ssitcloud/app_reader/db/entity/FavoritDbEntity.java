package com.ssitcloud.app_reader.db.entity;

import com.ssitcloud.app_reader.entity.AppOPACEntity;
import com.ssitcloud.app_reader.entity.BookUnionEntity;

/**
 * Created by LXP on 2017/4/12.
 * 收藏夹数据库实体
 */

public class FavoritDbEntity {
    private Integer bookitem_idx;
    private BookUnionEntity bue;
    private AppOPACEntity opac;

    public Integer getBookitem_idx() {
        return bookitem_idx;
    }

    public void setBookitem_idx(Integer bookitem_idx) {
        this.bookitem_idx = bookitem_idx;
    }

    public BookUnionEntity getBue() {
        return bue;
    }

    public void setBue(BookUnionEntity bue) {
        this.bue = bue;
    }

    public AppOPACEntity getOpac() {
        return opac;
    }

    public void setOpac(AppOPACEntity opac) {
        this.opac = opac;
    }
}
