package com.ssitcloud.mobileserver.entity;

import java.util.Date;

/**
 * Created by LXP on 2017/8/7.
 */

public class Reserve {
    public enum StateEnum{SUCCESS,FAIL}

    private StateEnum state;
    private String message;
    private Date dateLine;

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateLine() {
        return dateLine;
    }

    public void setDateLine(Date dateLine) {
        this.dateLine = dateLine;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", dateLine=" + dateLine +
                '}';
    }
}
