package com.ssitcloud.app_reader.entity;

import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;

/**
 * Created by LXP on 2017/3/17.
 * 读者菜单界面实体
 */

public class ReaderInfoMenuViewEntity {
    private LoginInfoDbEntity readerInfo;
    private ReaderCardDbEntity readerCard;

    public LoginInfoDbEntity getReaderInfo() {
        return readerInfo;
    }

    public void setReaderInfo(LoginInfoDbEntity readerInfo) {
        this.readerInfo = readerInfo;
    }

    public ReaderCardDbEntity getReaderCard() {
        return readerCard;
    }

    public void setReaderCard(ReaderCardDbEntity readerCard) {
        this.readerCard = readerCard;
    }
}
