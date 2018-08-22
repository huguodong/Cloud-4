package com.ssitcloud.datasync.service.impl;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.CloudSyncResponse;
import com.ssitcloud.common.netty.server.RpcService;
import com.ssitcloud.common.netty.server.protocol.JsonUtil;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.HeartBeatDownloadAppCardInfo;
import com.ssitcloud.datasync.service.DataSynHandler;
import com.ssitcloud.devmgmt.entity.AppCardInfo;

//@Component(value="downloadAppCardInfo")
@RpcService(name="downloadAppCardInfo")
public class DownloadAppCardInfo extends BasicServiceImpl implements DataSynHandler {
	
	@Resource(name="heartBeatDownloadAppCardInfo")
	private HeartBeatDownloadAppCardInfo heartBeatDownloadAppCardInfo;
	
	@Override
	public CloudSyncResponse handle(ChannelHandlerContext handlerContext, CloudSyncRequest request) {
		CloudSyncResponse cloudSyncResponse = new CloudSyncResponse();
		cloudSyncResponse.setMsg_name("downloadAppCardInfo");
		cloudSyncResponse.setMsg_type("response");
		cloudSyncResponse.setStatus("1");
		cloudSyncResponse.setResult("downloadAppCardInfo is fail");
		cloudSyncResponse.setRequestId(request.getRequestId());

		List<Map<String,String>> datas = request.getData();
		Map<String,String> data = datas.get(0);
		String deviceId = data.get("device_id");

		try {
			if (heartBeatDownloadAppCardInfo.containsKey(deviceId)) {
				ConcurrentLinkedQueue<AppCardInfo> appCardInfos = heartBeatDownloadAppCardInfo.get(deviceId);
				if (null != appCardInfos && !appCardInfos.isEmpty()) {//不为空
					AppCardInfo appCardInfo = appCardInfos.poll();//去除并且删除头元素
                    Map<String,String> map = JsonUtil.jsonToObjectHashMap(JsonUtil.objectToJson(appCardInfo), String.class, String.class);
					List<Map<String,String>> responseData = new ArrayList<>();
					responseData.add(map);
					cloudSyncResponse.setData(responseData);
					cloudSyncResponse.setStatus("0");
					cloudSyncResponse.setResult("downloadAppCardInfo is success");
				} else if (appCardInfos != null && appCardInfos.isEmpty()) {
					// 已经没有读者操作了
					cloudSyncResponse.setResult("已经没有读者操作了");
				} else {
					// appCardInfos is null
					cloudSyncResponse.setResult("appCardInfos is null");
				}
			}
		} catch (Exception e) {
			LogUtils.error("获取手机app私钥版本信息失败，",e);
			cloudSyncResponse.setStatus("0500");
			cloudSyncResponse.setResult("服务端发生错误 =====>");
		}
		return cloudSyncResponse;
	}

}
