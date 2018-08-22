package com.ssitcloud.common.system;

import java.util.Set;

import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.netty.server.handler.CloudSyncHandlerAdapter;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

import io.netty.channel.Channel;

public class ScheduledCloudSyncRequest {
	
	public void consumeCloudSyncRequest(){
		 //获取缓存数据的键
		Set<String> cacheKey = JedisUtils.getInstance().smembers(RedisConstant.CLOUDSYNCCACHEKEY);
		if(cacheKey  == null || cacheKey.isEmpty()) return;
		CloudSyncHandlerAdapter adapter = new CloudSyncHandlerAdapter();
		for(String key : cacheKey){
			String cacheValue = JedisUtils.getInstance().get(key);
			if(StringUtils.isEmpty(cacheValue)){//如果获取的数据为空，说明数据已经过期，删除掉集合中的键
				JedisUtils.getInstance().srem(RedisConstant.CLOUDSYNCCACHEKEY, key);
				continue;
			}
			CloudSyncRequest cloudSyncRequest = JsonUtils.fromJson(cacheValue, CloudSyncRequest.class);
			String clientId = cloudSyncRequest.getClientId();
			if(StringUtils.isEmpty(clientId)){
				clientId = cloudSyncRequest.getCacheClient(cloudSyncRequest.getLib_id(),cloudSyncRequest.getDevice_id());
			}
			if(StringUtils.isEmpty(clientId)) continue;//clientId 为空，说明设备端没有与云端连接
			cloudSyncRequest.setClientId(clientId);
			Channel channel = BasicServiceImpl.handlerMap.get(clientId);
			if(channel == null || !channel.isActive()) continue;
			ResultEntity resultEntity = adapter.sendRequest(cloudSyncRequest,cloudSyncRequest.getClientId());
			if(resultEntity.getState()){//发送成功，删除数据
				JedisUtils.getInstance().srem(RedisConstant.CLOUDSYNCCACHEKEY, key);
				JedisUtils.getInstance().del(key);
			}
		}
		
	}
	

}
