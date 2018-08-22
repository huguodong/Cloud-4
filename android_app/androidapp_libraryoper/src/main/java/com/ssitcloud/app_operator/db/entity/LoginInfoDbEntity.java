package com.ssitcloud.app_operator.db.entity;

import java.util.Date;

/**
 * 创建日期：2017/3/16 14:10
 * @author shuangjunjie
 */

public class LoginInfoDbEntity {
    private Integer operator_idx;   //int(11) NOT NULL馆员idx
    private Date login_time;        //登录时间
    private String operator_pwd;             //登录密码
    private String mobile;          //手机号
    private String operator_name;   //操作员 用户名

    public Integer getOperator_idx() {
        return operator_idx;
    }

    public void setOperator_idx(Integer operator_idx) {
        this.operator_idx = operator_idx;
    }

    public Date getLogin_time() {
        return login_time;
    }

    public void setLogin_time(Date login_time) {
        this.login_time = login_time;
    }

    public String getOperator_pwd() {
        return operator_pwd;
    }

    public void setOperator_pwd(String operator_pwd) {
        this.operator_pwd = operator_pwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }
}
