package com.ssitcloud.app_reader.common.exception;

/**
 * Created by LXP on 2017/4/14
 * 用户未登陆异常
 */

public class UnLoginException extends Exception {
    public UnLoginException(){
        super("用户未登陆");
    }
}
