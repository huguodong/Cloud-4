package com.ssitcloud.app_reader.common.exception;

/**
 * Created by LXP on 2017/3/21.
 * 读者卡失效异常
 */

public class ReaderCardInvalidException extends Exception {
    public ReaderCardInvalidException(){

    }
    public ReaderCardInvalidException(String s){
        super(s);
    }
}
