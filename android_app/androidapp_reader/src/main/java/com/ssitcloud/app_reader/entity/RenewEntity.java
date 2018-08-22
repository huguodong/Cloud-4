package com.ssitcloud.app_reader.entity;

/**
 * Created by LXP on 2017/3/22.
 * 续借实体
 */

public class RenewEntity {
    private boolean state;//续借是否成功
    private String message;//续借返回的信息
    private String returnDate;//应还日期

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
