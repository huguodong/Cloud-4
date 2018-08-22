package com.ssitcloud.mobileserver.exception;

/**
 * Created by LXP on 2017/7/21.
 * 解析数据失败时抛出此异常
 */

public class AnalysisDataException extends Exception {
    public AnalysisDataException() {
    }

    public AnalysisDataException(String message) {
        super(message);
    }

    public AnalysisDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnalysisDataException(Throwable cause) {
        super(cause);
    }

    public AnalysisDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
