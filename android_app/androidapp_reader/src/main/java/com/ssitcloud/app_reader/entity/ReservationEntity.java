package com.ssitcloud.app_reader.entity;

/**
 * Created by LXP on 2017/4/27.
 * 预借请求实体
 */

public class ReservationEntity {
    private Integer reader_idx;
    private String card_no;
    private Integer lib_idx;
    private String sn;

    public Integer getReader_idx() {
        return reader_idx;
    }

    public void setReader_idx(Integer reader_idx) {
        this.reader_idx = reader_idx;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public Integer getLib_idx() {
        return lib_idx;
    }

    public void setLib_idx(Integer lib_idx) {
        this.lib_idx = lib_idx;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
