package com.ssitcloud.datasync.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.Log;
import com.ssitcloud.datasync.entity.AskCfgSyncResultEntity;
import com.ssitcloud.datasync.entity.HeartBeatLibraryInfoData;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;

@Component(value="askLibraryInfoDataSync")
public class AskLibraryInfoDataSync extends BasicServiceImpl implements DataSyncCommand{
	
	
	@Resource(name="heartBeatLibraryInfoData")
	private HeartBeatLibraryInfoData heartBeatLibraryInfoData;
	
	@SuppressWarnings("unchecked")
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		Map<String, Object> map = conditionInfo.getData();
		String lib_id = map.get(LIB_ID) + "";
		String dev_id = map.get(DEV_ID) + "";
		//String tables="";
		List<String> tables=new ArrayList<>();
		List<Integer> triIdxList=new ArrayList<>();
		List<String> triTabNameList=new ArrayList<>();
		getChangeTables(tables,triIdxList,triTabNameList,dev_id);
		
		AskCfgSyncResultEntity askCfgSyncResult=new AskCfgSyncResultEntity(dev_id,lib_id,AskCfgSyncResultEntity.RequestResult_allow,"",	StringUtils.collectionToCommaDelimitedString(tables));
		resp.getData().setResult(askCfgSyncResult);
		resp.getData().setState(true);
		
		Log.DebugOnScr(JsonUtils.toJson(resp));
		return resp;
	}
	/**
	 * 获取有变化的配置表
	 * @param tables
	 * @param triIdxList
	 * @param triTabNameList
	 * @param dev_id
	 */
	@SuppressWarnings("unchecked")
	private void  getChangeTables(List<String> tables,
			List<Integer> triIdxList, List<String> triTabNameList,String dev_id) {
		if(!"null".equals(dev_id)&&heartBeatLibraryInfoData.containsKey(dev_id)){//deivce_id,如果存在
			Queue<SyncConfigEntity> changes=heartBeatLibraryInfoData.get(dev_id);
			if(changes!=null&&changes.size()>0){
				for(SyncConfigEntity change:changes){
					String tableName=change.getTable_name();
					 if(tableName!=null&&!tables.contains(tableName)){
							tables.add(tableName);
					}}
			}
		}
	}
}
