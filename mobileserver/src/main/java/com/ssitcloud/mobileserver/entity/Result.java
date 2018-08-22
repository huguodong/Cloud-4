package com.ssitcloud.mobileserver.entity;

/**
 * Created by LXP on 2017/7/20.
 */

public class Result<T> {
    public enum StateEnum{SUCCESS,FAIL}

    private StateEnum state;
    private String message;
    private T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
