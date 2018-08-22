package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.entity.LibraryEntity;

import java.net.SocketException;
import java.util.List;

/**
 * Created by LXP on 2017/3/10.
 * 图书馆业务类
 */

public interface LibraryBizI {

    /**
     * 获取所有图书馆列表
     * @return
     */
    List<LibraryEntity> obtainLibrary() throws SocketException;

}
