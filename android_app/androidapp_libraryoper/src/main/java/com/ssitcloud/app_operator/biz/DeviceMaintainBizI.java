package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.common.entity.ResultEntity;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/4/15 11:34
 * @author shuangjunjie
 */

public interface DeviceMaintainBizI {

    /**
     * 设备维护 发送指令
     * @param listMap
     * @return
     * @throws SocketException
     */
    ResultEntity sendOrder(List<Map> listMap) throws SocketException;
}
