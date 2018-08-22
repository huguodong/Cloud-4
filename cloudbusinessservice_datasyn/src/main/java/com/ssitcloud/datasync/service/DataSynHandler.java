package com.ssitcloud.datasync.service;

import io.netty.channel.ChannelHandlerContext;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.CloudSyncResponse;

public interface DataSynHandler {
	
	public CloudSyncResponse handle(ChannelHandlerContext handlerContext,CloudSyncRequest cloudSyncRequest);

}
