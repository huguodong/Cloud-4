package com.ssitcloud.mobileserver.exception;

/**
 * Created by LXP on 2017/8/2.
 * 当session工厂不能初始化异常时抛出
 */

public class InitSessionException extends Exception {
    public InitSessionException() {
    }

    public InitSessionException(String message) {
        super(message);
    }

    public InitSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
