package com.ssitcloud.common.netty.server.protocol;

/**
 * Created by yeyalin
 */
public interface AsyncCloudCallback {

    void success(Object result);

    void fail(Exception e);

}
