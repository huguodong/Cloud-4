package com.ssitcloud.common.netty.service.impl;

import io.netty.channel.Channel;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.netty.server.handler.CloudSyncHandlerAdapter;
import com.ssitcloud.common.netty.service.CommonSendMsgService;
import com.ssitcloud.common.service.impl.BasicServiceImpl;

/**
 * Created by yeyalin
 */
@Service
public class CommonSendMsgServiceImpl extends BasicServiceImpl implements CommonSendMsgService {
	
    
    /**
     * 给指定客户端发信息
     * @param cliendId
     * @param msg
     */
    public ResultEntity sendMsg(CloudSyncRequest request){
    	ResultEntity entity = new ResultEntity();
//    	Channel channel = getChannel(request.getClientId());
//		entity = pushMessage(channel, request);
		
		CloudSyncHandlerAdapter handler = new CloudSyncHandlerAdapter();
		entity =handler.sendRequest(request, request.getClientId());
		return entity;
    }
}
