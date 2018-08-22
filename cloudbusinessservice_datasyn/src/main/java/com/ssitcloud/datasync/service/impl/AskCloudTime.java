package com.ssitcloud.datasync.service.impl;

import io.netty.channel.ChannelHandlerContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.CloudSyncResponse;
import com.ssitcloud.common.netty.server.RpcService;
import com.ssitcloud.common.netty.server.protocol.JsonUtil;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.datasync.service.DataSynHandler;

/**
 * 返回UTC时间
 * @package: com.ssitcloud.datasync.service.impl
 * @classFile: AskCloudTime
 * @author: liuBh
 * @description: TODO
 */
//@Component(value="askCloudTime")
@RpcService(name="askCloudTime")
public class AskCloudTime extends BasicServiceImpl implements DataSynHandler {

	@Override
	public CloudSyncResponse handle(ChannelHandlerContext handlerContext, CloudSyncRequest cloudSyncRequest)  {

		CloudSyncResponse cloudSyncResponse = new CloudSyncResponse();

		cloudSyncResponse.setMsg_name("askCloudTime");
		cloudSyncResponse.setMsg_type("response");
		cloudSyncResponse.setRequestId(cloudSyncRequest.getRequestId());
		cloudSyncResponse.setStatus("0");
		cloudSyncResponse.setResult("askCloudTime is success");

		List<Map<String,String>> datas = cloudSyncRequest.getData();
		Map<String,String> data = datas.get(0);

		String library_id = data.get("library_id");
		String device_id = data.get("device_id");

		Map<String,String> responseDataMap = new HashMap<>();

		//设置基础时间为格林威治时间
		TimeZone gmtTz = TimeZone.getTimeZone("GMT");
		//设置目标时间为中国标准时
		TimeZone desTz = TimeZone.getTimeZone("Asia/Shanghai");
		GregorianCalendar rightNow = new GregorianCalendar(gmtTz);
		Date mydate=rightNow.getTime();
		//设置时间字符串格式
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX",Locale.CHINESE);
		//设置目的时间时区

		df.setTimeZone(gmtTz);
		responseDataMap.put("time",df.format(mydate));
		responseDataMap.put("library_id",library_id);
		responseDataMap.put("device_id",device_id);

        String json = JsonUtil.objectToJson(responseDataMap);
		List<Map<String,String>> responseData = new ArrayList<>();
		responseData.add(responseDataMap);
		cloudSyncResponse.setData(responseData);
		return cloudSyncResponse;
	}
}
