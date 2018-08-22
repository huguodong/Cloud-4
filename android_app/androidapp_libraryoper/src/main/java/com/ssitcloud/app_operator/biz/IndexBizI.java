package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.db.entity.MessageRemindDbEntity;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/4/6 15:11
 * @author shuangjunjie
 */

public interface IndexBizI {

//    ResultEntity getDeviceMonitor(Map<String,Object> map,Map<String,Object> map2) throws SocketException;
    @Deprecated
    ResultEntity querySlaveLibraryByMasterIdx(Map<String,Object> map) throws SocketException;
}
