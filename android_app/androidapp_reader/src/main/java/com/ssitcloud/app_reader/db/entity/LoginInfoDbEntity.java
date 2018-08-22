package com.ssitcloud.app_reader.db.entity;

import java.util.Date;

/**
 * Created by LXP on 2017/3/7.
 */

public class LoginInfoDbEntity {
    private Integer reader_idx;//int(11) NOT NULL读者记录号
    private Date login_time;//登陆时间;
    private String login_name;
    private String reader_sex;
    private String pwd;
    private String reader_name;
    private String id_card;
    private String mobile;

    public Date getLogin_time() {
        return login_time;
    }

    public void setLogin_time(Date login_time) {
        this.login_time = login_time;
    }

    public Integer getReader_idx() {
        return reader_idx;
    }

    public void setReader_idx(Integer reader_idx) {
        this.reader_idx = reader_idx;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getReader_sex() {
        return reader_sex;
    }

    public void setReader_sex(String reader_sex) {
        this.reader_sex = reader_sex;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getReader_name() {
        return reader_name;
    }

    public void setReader_name(String reader_name) {
        this.reader_name = reader_name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
