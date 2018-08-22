package com.ssitcloud.common.netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.CloudSyncResponse;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.netty.server.NettyServer;
import com.ssitcloud.common.system.ScheduledCloudSyncRequest;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.datasync.service.DataSynHandler;
import com.ssitcloud.datasync.service.impl.DeviceRegister;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

/**
 * @author yeyalin
 */
@SuppressWarnings({ "unchecked", "unused" })
public class CloudSyncHandlerAdapter extends ChannelInboundHandlerAdapter {
	
	private ScheduledCloudSyncRequest scheduledCloudSyncRequest;
    private volatile Channel channel;

    public Channel getChannel() {
        return channel;
    }
    
    //接收消息
	public void channelRead(final ChannelHandlerContext ctx, final Object msg)
			throws Exception {
		NettyServer.submit(new Runnable() {
            @Override
            public void run() {
            	handleMessage(ctx,msg);
            }
        });
	}
    
	//处理消息(客户端的请求、响应消息)
    private void handleMessage(ChannelHandlerContext ctx,Object request){
    		
    	if(request != null && !StringUtils.isBlank(request.toString())){
    		JSONObject req = JSONObject.fromObject(request);
    		if(req.containsKey("operation")){//处理客户端的请求消息(cloudRequest)
        		handleRequestMessage(ctx,request);
        	}else{//处理客户端的响应消息(cloudResonse)
        		handleResponseMessage(request);
        	}
    	}
    }
    
    /**
     * 处理客户端的请求消息
     */
    private void handleRequestMessage(ChannelHandlerContext ctx,Object request){
    	CloudSyncResponse response = new CloudSyncResponse();
    	try{
    		//如果进来的是客户端请求消息
			CloudSyncRequest cloudSyncRequest = JsonUtils.fromJson(request.toString(), CloudSyncRequest.class);
			if(StringUtils.isBlank(cloudSyncRequest.getOperation())){
				response.setStatus("0300");
				response.setResult("请求方法为空");
			}
			DataSynHandler synHandler = (DataSynHandler) NettyServer.proxyBeanMap.get(cloudSyncRequest.getOperation());
			response = synHandler.handle(ctx, cloudSyncRequest);
			response.setRequestId(cloudSyncRequest.getRequestId());
			ctx.channel().writeAndFlush(response);
    	}catch(Exception e){
    		e.printStackTrace();
    		response.setStatus("0500");
			response.setResult("服务端发生错误 =====>");
			System.out.println("服务端发生错误 =====>"+e.getCause().toString());
			ctx.channel().writeAndFlush(response);
    	}
    }
    
    
    /**
     * 处理客户端的响应消息
     */
    private void handleResponseMessage(Object request){
    	
    }
    
    
    
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
	

	/**
	 * 给指定客户端发送信息
	 * 每次requestId为：UUID+时间戳
	 * requestId=UUID.randomUUID().toString() + System.currentTimeMillis()
	 * @param request
	 * @param cliendId
	 */
	public ResultEntity  sendRequest(CloudSyncRequest request,String cliendId) {
		ResultEntity result = new ResultEntity();
		try{
			if(StringUtils.isEmpty(cliendId)){
				//如果cliendId 为空.
				cacheCloudSyncRequest(request);
				result.setState(false);
				return result;
			}
			result.setState(true);
			 
			Channel channel = (Channel)DeviceRegister.handlerMap.get(cliendId);
			if(channel == null || !channel.isActive()){
				//如果获取channel失败，将数据缓存起来，定时任务去调.
				cacheCloudSyncRequest(request);
				result.setState(false);
				return result;
			}
			
			String uuid = UUID.randomUUID().toString().replaceAll("-", "") ;
			request.setRequestId(uuid + System.currentTimeMillis());
			final CountDownLatch latch = new CountDownLatch(1);
			channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) {
					latch.countDown();
				}
			});
			
			latch.await();
		
		}catch(Exception e){
			e.printStackTrace();
			result.setState(false);
		}
		return result;

	}
	
	
	
	/**
	 * 将缓存添加到redis缓存中去
	 * @param cloudSyncRequest
	 */
	private void cacheCloudSyncRequest(CloudSyncRequest cloudSyncRequest){
		
		String lib_id = cloudSyncRequest.getLib_id();
		String device_id = cloudSyncRequest.getDevice_id();
		List<Map<String,String>> list = cloudSyncRequest.getData();
		List<Map<String, String>> tempList = new ArrayList<>();
		if(list != null && !list.isEmpty()){
			tempList.addAll(list);
		}
		for(Map<String, String> map : tempList){
			String key = map.get("table");
			if(StringUtils.isEmpty(key)){
				key = cloudSyncRequest.getOperation();
			}
			key = lib_id+":"+device_id+":"+key;
			List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
			dataList.add(map);
			cloudSyncRequest.setData(dataList);
			long num = JedisUtils.getInstance().sadd(RedisConstant.CLOUDSYNCCACHEKEY,RedisConstant.CLOUDSYNCCACHE+key);
			JedisUtils.getInstance().setex(RedisConstant.CLOUDSYNCCACHE+key, RedisConstant.ONE_DAY_EXPIRE_TIME, JsonUtils.toJson(cloudSyncRequest));
		}
	}
	

}
