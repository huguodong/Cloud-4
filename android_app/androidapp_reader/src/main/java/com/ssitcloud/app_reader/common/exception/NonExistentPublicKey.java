package com.ssitcloud.app_reader.common.exception;

/**
 * Created by LXP on 2017/4/14.
 * 系统没有存在公钥异常
 */

public class NonExistentPublicKey extends Exception {
    public NonExistentPublicKey(){
        super("系统没有公钥配置");
    }
}
