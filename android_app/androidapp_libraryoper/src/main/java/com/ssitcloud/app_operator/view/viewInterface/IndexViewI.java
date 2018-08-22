package com.ssitcloud.app_operator.view.viewInterface;

import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.db.entity.MessageRemindDbEntity;
import com.ssitcloud.app_operator.entity.LibraryEntity;

import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/4/6 15:09
 * @author shuangjunjie
 */

public interface IndexViewI {

//    void getDeviceMonitorSuccess(ResultEntity resultEntity);
//
//    void getDeviceMonitorFail(String message);

    void querySlaveLibraryByMasterIdxSuccess(List<LibraryEntity> libs);

    void querySlaveLibraryByMasterIdxFail(String message);

    void selectDeviceMgmtByLibraryIdxsFail(String message);
}
