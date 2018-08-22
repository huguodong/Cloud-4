package com.ssitcloud.datasync.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.StringUtil;
import com.ssitcloud.datasync.entity.HeartBeatChangeTableData;
import com.ssitcloud.datasync.entity.HeartBeatFileUploadState;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.devmgmt.entity.FileUploadState;
import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;

@Component(value="downloadOsbSync")
public class DownloadOsbSync extends BasicServiceImpl implements DataSyncCommand{

	@Resource(name = "heartBeatFileUploadState")
	private HeartBeatFileUploadState heartBeatFileUploadState;
	
	@Resource(name="heartBeatChangeTableData")
	private HeartBeatChangeTableData heartBeatChangeTableData;
	
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		Map<String, Object> map = conditionInfo.getData();
		String device_id = (String) map.get(DEV_ID);
		String tableName = (String) map.get("table");
		//String fileName = (String) map.get("fileName");
		
		if(StringUtils.isEmpty(device_id)){
			resp.getData().setState(false);
			resp.getData().setMessage("请求参数格式不正确,必须参数包括library_id、device_id");
			return resp;
		}
		
		String key = device_id;
		if (key != null && heartBeatFileUploadState.containsKey(key)) {
			ConcurrentLinkedQueue<FileUploadState> fileUploadState = heartBeatFileUploadState.get(key);
			for(FileUploadState fus : fileUploadState){
				if(fus.getState() != null && "2".equals(fus.getState()) && (StringUtil.isEmpty(tableName) || tableName.equals(fus.getTableName()))){
						fileUploadState.remove(fus);
				}
			}
			heartBeatFileUploadState.remove(key);
			if(fileUploadState != null && fileUploadState.size() > 0){
				heartBeatFileUploadState.put(key, fileUploadState);
			}
			resp.getData().setState(true);
		}
		return resp;
	}
}
