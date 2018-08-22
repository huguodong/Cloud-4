package com.ssitcloud.app_reader.entity;

/**
 * Created by LXP on 2017/3/10.
 * 绑定卡实体
 */

public class BindCardEntity {
    private Integer lib_idx;
    private String card_no;
    private String card_pwd;

    public Integer getLib_idx() {
        return lib_idx;
    }

    public void setLib_idx(Integer lib_idx) {
        this.lib_idx = lib_idx;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCard_pwd() {
        return card_pwd;
    }

    public void setCard_pwd(String card_pwd) {
        this.card_pwd = card_pwd;
    }
}
