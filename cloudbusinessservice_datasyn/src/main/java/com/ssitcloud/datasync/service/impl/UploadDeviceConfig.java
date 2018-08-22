package com.ssitcloud.datasync.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.CloudSyncResponse;
import com.ssitcloud.common.netty.server.RpcService;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.datasync.service.DataSynHandler;
import com.ssitcloud.library.service.LibraryService;

import io.netty.channel.ChannelHandlerContext;


/***
 * 上传设备端配置
 * { "servicetype":"ssitcloud","target":"ssitcloud","operation":"uploadDeviceConfig", 
 * "data":{"library_id":"HHTEST","device_id":"STA#001", "table": "device_run_config",     
 * "fields": {  "run_conf_type": "1","run_conf_value": "2","updatetime": "3"     },     
 * "records": [ ]}}
 *
 */
//@Component(value="uploadDeviceConfig")
@RpcService(name="uploadDeviceConfig")
public class UploadDeviceConfig extends BasicServiceImpl implements DataSynHandler{
	
	public static final String URL_UPLOADCFGSYNC = "uploadcfgSyn";
	
	@Resource
	private LibraryService libService;
	
	
	public CloudSyncResponse handle(ChannelHandlerContext handlerContext, CloudSyncRequest cloudSyncRequest) {
		
		List<Map<String, String>> deviceConfs = cloudSyncRequest.getData();
		CloudSyncResponse response = new CloudSyncResponse();
		response.setMsg_name("uploadDeviceConfig");
		response.setMsg_type("respond");
		response.setStatus("0");
		response.setResult("success");
		
		if(deviceConfs != null){
			
			for(Map<String,String> conf : deviceConfs){
				if(!conf.containsKey("table") || !conf.containsKey("fields") || !conf.containsKey("records")) {
					response.setResult("数据格式不对");
					response.setStatus("1");
				}
				
				String library_id = conf.get("library_id");
				int library_idx = libService.getLibraryIdxById(library_id);
				conf.put("library_id", library_idx+"");
				conf.put("lib_id", library_id);
				conf.put("req", JsonUtils.toJson(conf));
				String result = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_UPLOADCFGSYNC), conf, charset);
				System.out.println(result);
			}
		}
		return response;
	}
}
