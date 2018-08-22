package com.ssitcloud.app_reader.common.exception;

/**
 * Created by LXP on 2017/5/8.
 * 解析二维码失败
 */

public class UnDecodeBarcode extends Exception{
    public UnDecodeBarcode(){}
    public UnDecodeBarcode(String message){
        super(message);
    }
}
