package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.common.entity.ResultEntity;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 创建日期：2017/4/5 16:32
 * @author shuangjunjie
 */

public interface DeviceMonitorBizI {

    /**
     * 查询出对应的 图书馆
     * @param map
     * @return
     * @throws SocketException
     */
    Map selectLib(Map<String,Object> map) throws SocketException;

    ResultEntity selectDeviceState(Map<String,Object> map) throws SocketException;

    Observable<Map<String,Object>> selectDeviceState(String library_idx,String device_idx);

    ResultEntity selectLibraryIdByIdx(Map<String,Object> map) throws SocketException;

    ResultEntity querySlaveLibraryByMasterIdx(Map<String,Object> map) throws SocketException;

    ResultEntity selectDeviceMgmtByLibraryIdxs(Map<String,Object> map, List<Integer> ids) throws SocketException;
}
