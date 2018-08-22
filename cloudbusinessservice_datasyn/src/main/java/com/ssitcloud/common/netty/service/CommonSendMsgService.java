package com.ssitcloud.common.netty.service;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * Created by yeyalin
 */
public interface CommonSendMsgService {
	
    
    /**
     * 给指定客户端发信息
     * @param cliendId
     * @param msg
     */
   public ResultEntity sendMsg(CloudSyncRequest request);
}
