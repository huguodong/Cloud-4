package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.common.exception.NonExistentPublicKey;
import com.ssitcloud.app_reader.entity.PublicKeyEntity;

import java.net.SocketException;

/**
 * Created by LXP on 2017/4/14.
 * 安全服务
 */

public interface SecurityBizI {
    /**
     * 使用内置公钥加密数据
     * @param data
     * @return 加密后的数据
     */
    byte[] encryptData(byte[] data);


    /**
     * 更新公钥
     * @return 更新是否成功
     */
    boolean updatePuk() throws SocketException;

    /**
     * 获取公钥，若不存在返回null
     * @return
     */
    PublicKeyEntity getPuk() throws NonExistentPublicKey;
}
