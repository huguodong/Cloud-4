package com.ssitcloud.app_operator.entity;

/**
 * Created by LXP on 2017/8/29.
 * 书架实体
 */

public class ShiftEntity {
    private String current;//当前在架书
    private String all;//总共在架数

    public ShiftEntity() {
    }

    public ShiftEntity(String current, String all) {
        this.current = current;
        this.all = all;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }
}
