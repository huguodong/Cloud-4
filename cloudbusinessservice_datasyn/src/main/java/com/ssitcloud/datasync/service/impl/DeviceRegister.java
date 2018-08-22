package com.ssitcloud.datasync.service.impl;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.CloudSyncResponse;
import com.ssitcloud.common.netty.server.NettyServer;
import com.ssitcloud.common.netty.server.RpcService;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.datasync.service.DataSynHandler;
/***
 * 设备注册
 * @author yanyong
 *
 */
//@Component(value="deviceRegister")
@RpcService(name="deviceRegister")
public class DeviceRegister extends BasicServiceImpl implements DataSynHandler{

	public CloudSyncResponse handle(ChannelHandlerContext handlerContext,CloudSyncRequest request) {
		CloudSyncResponse cloudSyncResponse = new CloudSyncResponse();
		cloudSyncResponse.setMsg_name("deviceRegister");
		cloudSyncResponse.setMsg_type("respond");
		cloudSyncResponse.setStatus("0");
		cloudSyncResponse.setResult("register is success");
		
		List<Map<String,String>> responseData = new ArrayList<>();
		
		cloudSyncResponse.setData(responseData);	
		
		List<Map<String,String>> datas = request.getData();
		if (CollectionUtils.isEmpty(datas)){
			cloudSyncResponse.setResult("data is null");
			cloudSyncResponse.setStatus(1+"");
			return cloudSyncResponse;
		}
		
		Map<String,String> data = datas.get(0);
		String libId = data.get("library_id");
		String deviceId = data.get("device_id");
		
		if (StringUtils.isEmpty(libId) || StringUtils.isEmpty(deviceId) ){
			cloudSyncResponse.setResult("libId or deviceId is null");
			cloudSyncResponse.setStatus(1+"");
			return cloudSyncResponse;
		}
		String cliendIdKey = "cliendId:"+libId+":"+deviceId ;
		//从redis获取终端设备id(设备特征码)
		 String cliendId = request.getClientId();
		if (!StringUtils.isEmpty(cliendId) ){
			NettyServer.redis.set(cliendIdKey, cliendId) ;
		}
		handlerMap.put(cliendId,handlerContext.channel());
		return cloudSyncResponse;
	}
	

}
