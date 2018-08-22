package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.entity.AppOPACEntity;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/6.
 * 设备业务
 */

public interface DeviceBizI {
    /**
     * 获取opac设备
     * @param selectData
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    List<AppOPACEntity> findOpacDevice(Map<String,Object> selectData,Integer lib_idx,Integer pageCurrent,Integer pageSize) throws SocketException;

    /**
     * 获取自助图书馆设备数据
     * @param selectData
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    List<AppOPACEntity> findOpacSelfHelpLibrary(Map<String,Object> selectData,Integer lib_idx,Integer pageCurrent,Integer pageSize) throws SocketException;

    /**
     * 搜索设备，包括自助设备和自助图书馆
     * @param selectData 搜索参数，可选search_str
     * @return
     */
    List<AppOPACEntity> searchDevice(Map<String,Object> selectData, Integer lib_idx) throws SocketException;

    AppOPACEntity updateAppOPACEntity(AppOPACEntity entity) throws SocketException;
}
