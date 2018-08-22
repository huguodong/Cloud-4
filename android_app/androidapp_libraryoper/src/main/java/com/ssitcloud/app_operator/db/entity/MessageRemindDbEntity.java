package com.ssitcloud.app_operator.db.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 创建日期：2017/4/21 17:00
 * 消息提醒
 * @author shuangjunjie
 */

public class MessageRemindDbEntity {
    private Integer trouble_idx;
    private Integer lib_idx;
    private Integer log_idx;
    private Integer device_idx;
    private String trouble_info;
    private String trouble_name;
    private String trouble_time;
    private Date create_time;
    private Timestamp control_time;
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTrouble_idx() {
        return trouble_idx;
    }
    public void setTrouble_idx(Integer trouble_idx) {
        this.trouble_idx = trouble_idx;
    }
    public Integer getLib_idx() {
        return lib_idx;
    }
    public void setLib_idx(Integer lib_idx) {
        this.lib_idx = lib_idx;
    }

    public Integer getLog_idx() {
        return log_idx;
    }

    public void setLog_idx(Integer log_idx) {
        this.log_idx = log_idx;
    }

    public Integer getDevice_idx() {
        return device_idx;
    }
    public void setDevice_idx(Integer device_idx) {
        this.device_idx = device_idx;
    }
    public String getTrouble_info() {
        return trouble_info;
    }
    public void setTrouble_info(String trouble_info) {
        this.trouble_info = trouble_info;
    }
    public String getTrouble_name() {
        return trouble_name;
    }
    public void setTrouble_name(String trouble_name) {
        this.trouble_name = trouble_name;
    }

    public String getTrouble_time() {
        return trouble_time;
    }

    public void setTrouble_time(String trouble_time) {
        this.trouble_time = trouble_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Timestamp getControl_time() {
        return control_time;
    }
    public void setControl_time(Timestamp control_time) {
        this.control_time = control_time;
    }


}
