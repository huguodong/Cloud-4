package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.common.entity.ResultEntity;

import java.net.SocketException;
import java.util.Map;

/**
 * 创建日期：2017/3/22 19:10
 * @author shuangjunjie
 */

public interface ChangePwdBizI {

    /**
     * 修改密码
     * @param map
     * @return
     * @throws SocketException
     */
    ResultEntity changePwd(Map<String,Object> map) throws SocketException;
}
