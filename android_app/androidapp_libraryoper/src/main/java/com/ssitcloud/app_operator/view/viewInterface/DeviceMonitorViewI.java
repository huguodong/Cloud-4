package com.ssitcloud.app_operator.view.viewInterface;

import com.ssitcloud.app_operator.common.entity.ResultEntity;

import java.util.Map;

/**
 * 创建日期：2017/4/5 16:28
 * @author shuangjunjie
 */

public interface DeviceMonitorViewI {
    /**
     * 查询失败
     * @param message
     */
    void selectFail(String message);

//    /**
//     * 获取设备状态成功
//     * @param resultEntity
//     */
//    void getDeviceStateSuccess(ResultEntity resultEntity);

    /**
     * 获取设备状态失败
      * @param message
     */
    void getDeviceStateFail(String message);

//    void querySlaveLibraryByMasterIdxSuccess(ResultEntity resultEntity);
//
//    void querySlaveLibraryByMasterIdxFail(String message);

    void SelectDeviceMgmtByLibraryIdxsSuccess(ResultEntity resultEntity);

    void SelectDeviceMgmtByLibraryIdxsFail(String  message);

    void SelectDeviceMgmtByPageSuccess(ResultEntity resultEntity);

    void SelectDeviceMgmtByPageFail(String  message);
}
