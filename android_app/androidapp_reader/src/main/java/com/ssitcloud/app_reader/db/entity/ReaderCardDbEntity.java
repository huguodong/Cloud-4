package com.ssitcloud.app_reader.db.entity;

import java.io.Serializable;

/**
 * Created by LXP on 2017/3/9.
 * 读者卡的数据库对象
 */
public class ReaderCardDbEntity implements Serializable {
    private String card_no;
    private Integer lib_idx;
    private String lib_id;
    private String lib_name;
    private Integer allown_loancount;//最大借阅数
    private Integer surplus_count;//剩余可借阅数
    private Double advance_charge;//预付款
    private Double deposit;//押金
    private Double arrearage;//欠款
    private String create_time;//创建时间=>绑定时间
    private String update_time;//更新时间
    private String cardPwd;
    private String reader_name;

    public String getReader_name() {
        return reader_name;
    }

    public void setReader_name(String reader_name) {
        this.reader_name = reader_name;
    }

    public String getLib_name() {
        return lib_name;
    }

    public void setLib_name(String lib_name) {
        this.lib_name = lib_name;
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

    public String getLib_id() {
        return lib_id;
    }

    public void setLib_id(String lib_id) {
        this.lib_id = lib_id;
    }

    public Integer getAllown_loancount() {
        return allown_loancount;
    }

    public void setAllown_loancount(Integer allown_loancount) {
        this.allown_loancount = allown_loancount;
    }

    public Integer getSurplus_count() {
        return surplus_count;
    }

    public void setSurplus_count(Integer surplus_count) {
        this.surplus_count = surplus_count;
    }

    public Double getAdvance_charge() {
        return advance_charge;
    }

    public void setAdvance_charge(Double advance_charge) {
        this.advance_charge = advance_charge;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getArrearage() {
        return arrearage;
    }

    public void setArrearage(Double arrearage) {
        this.arrearage = arrearage;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCardPwd() {
        return cardPwd;
    }

    public void setCardPwd(String cardPwd) {
        this.cardPwd = cardPwd;
    }
}
