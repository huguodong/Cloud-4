package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.common.entity.ResultEntity;

import java.net.SocketException;
import java.util.Map;

/**
 * 创建日期：2017/3/23 18:17
 * @author shuangjunjie
 */

public interface UpdateOperatorInfoBizI {

    /**
     * 更新个人信息
     * @param map
     * @return
     * @throws SocketException
     */
    ResultEntity updateOperatorInfo(Map<String,Object> map) throws SocketException;

}
