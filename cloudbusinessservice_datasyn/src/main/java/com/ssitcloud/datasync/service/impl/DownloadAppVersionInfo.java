package com.ssitcloud.datasync.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.CloudSyncResponse;
import com.ssitcloud.common.netty.server.RpcService;
import com.ssitcloud.common.netty.server.protocol.JsonUtil;
import com.ssitcloud.datasync.service.DataSynHandler;

import io.netty.channel.ChannelHandlerContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;

/**
 * <p>2017年4月6日 下午3:30:45  
 * @author hjc 
 * return 
*/
//@Component(value="downloadAppVersionInfo")
@RpcService(name="downloadAppVersionInfo")
public class DownloadAppVersionInfo extends BasicServiceImpl implements DataSynHandler {
	
	private static final String URL_APP_USEFUL_VERSION_INFO = "getAllUsefulAppVersionInfo";

	@Override
	public CloudSyncResponse handle(ChannelHandlerContext handlerContext, CloudSyncRequest cloudSyncRequest) {
		CloudSyncResponse cloudSyncResponse = new CloudSyncResponse();
		cloudSyncResponse.setMsg_name("downloadAppVersionInfo");
		cloudSyncResponse.setMsg_type("response");
		cloudSyncResponse.setStatus("1");
		cloudSyncResponse.setResult("downloadAppVersionInfo is fail");
		cloudSyncResponse.setRequestId(cloudSyncRequest.getRequestId());

		try {
			String infoResult = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_APP_USEFUL_VERSION_INFO), new HashMap<String, String>(), charset);
			if(StringUtils.isNotBlank(infoResult)){
				ResultEntity infoResultEntity = JsonUtils.fromJson(infoResult, ResultEntity.class);

				if (infoResultEntity.getState()) {
					List<Map<String,Object>> responseData = (List<Map<String,Object>>)infoResultEntity.getResult();
					cloudSyncResponse.setData(responseData);
					cloudSyncResponse.setStatus("0");
					cloudSyncResponse.setResult("downloadAppVersionInfo is success");
				}else{
					LogUtils.error("获取手机app私钥版本信息失败，" + cloudSyncResponse.getMsg_name() + "," + cloudSyncResponse.getResult());
				}
			}
		} catch (Exception e) {
			cloudSyncResponse.setStatus("0500");
			cloudSyncResponse.setResult("服务端发生错误 =====>");
			LogUtils.error("获取手机app私钥版本信息失败，",e);
		}
		return cloudSyncResponse;
	}
}
