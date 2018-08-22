package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.db.entity.LoginInfoDbEntity;

import java.net.SocketException;
import java.util.Map;

/**
 * 创建日期：2017/3/16 11:46
 * @author shuangjunjie
 */

public interface MainActivityBizI {

    ResultEntity login(Map<String, Object> map) throws SocketException;

    ResultEntity getMenu(Map<String, Object> map) throws SocketException;

    void logout();

    LoginInfoDbEntity getLoginInfo();

    /**
     * 消息提醒（查询故障消息）
     * @param map
     * @return
     * @throws SocketException
     */
    ResultEntity selectDeviceTroublesByLibIdxs(Map<String, Object> map) throws SocketException;



}
