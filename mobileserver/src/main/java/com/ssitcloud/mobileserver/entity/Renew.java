package com.ssitcloud.mobileserver.entity;

import java.util.Date;

/**
 * Created by LXP on 2017/8/2.
 */

public class Renew {
    public enum StateEnum{SUCCESS,FAIL}

    private StateEnum state;
    private String message;
    private Date returnDate;

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

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Renew{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", returnDate=" + returnDate +
                '}';
    }
}
