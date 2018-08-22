package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.common.entity.ResultEntity;

import java.net.SocketException;
import java.util.Map;

/**
 * 创建日期：2017/3/31 10:12
 * @author shuangjunjie
 */

public interface FeedBackBizI {

    public ResultEntity sendFeedBack(Map<String,Object> map)  throws SocketException;
}
