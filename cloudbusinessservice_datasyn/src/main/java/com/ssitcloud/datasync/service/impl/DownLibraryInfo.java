package com.ssitcloud.datasync.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonNode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.LibraryEntity;
import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.HeartBeatLibraryInfoData;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;
import com.ssitcloud.library.service.LibraryService;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

@Component(value="downLibraryInfo")
public class DownLibraryInfo extends BasicServiceImpl implements DataSyncCommand{

	public static final String URL_DOWNLOAD_LIBRARYINFO = "downloadLibraryInfo";
	
	@Resource(name="heartBeatLibraryInfoData")
	private HeartBeatLibraryInfoData heartBeatLibraryInfoData;
	
	@Resource
	private LibraryService libServcie;
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		ResultEntity resEntity=new ResultEntity();
		Map<String, Object> map = conditionInfo.getData();
		Map<String, String> params = new HashMap<>();
		String lib_idx = "";
		
		String dev_id = map.get(DEV_ID) + "";
		String lib_id = map.get(LIB_ID) + "";
		String paramTableName = map.get("table") + "";
		String page = map.get("page") + "";
		if(StringUtils.isEmpty(page)){
			map.put("page", 0);
		}
		if (StringUtils.hasLength(lib_id)) {
			
			lib_idx = libServcie.getLibraryIdxById(lib_id)+"";
			
			/*if(librarycache != null){
				Element e = librarycache.get(lib_id);
				if(e!=null){
					lib_idx = (String) e.getObjectValue();
				}
			}*/
		}
		try {
			map.put("library_id", lib_idx);
			map.put("lib_id", lib_id);
			params.put("req", JsonUtils.toJson(map));
			String result = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_DOWNLOAD_LIBRARYINFO), params, charset);
			if (JSONUtils.mayBeJSON(result)) {
				resEntity = JsonUtils.fromJson(result, ResultEntity.class);
				if(resEntity.getState()){
					if(StringUtils.isEmpty(resEntity.getMessage())){
						//有延迟 
						//在这里清空 队列 更新表名
						//清除数据 该设备下载数据之后，清除queue数据，更新change_table_tri
						doClear(dev_id,paramTableName);
					}
				}
				resp.setData(resEntity);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread(), e);
		}
		return resp;
	}
	@Async
	public void doClear(String dev_id,String paramTableName){
		List<Integer> triIdxList=new ArrayList<>();
		if(!"null".equals(dev_id)&&heartBeatLibraryInfoData.containsKey(dev_id)){
			Queue<SyncConfigEntity> changes=heartBeatLibraryInfoData.get(dev_id);
			
			if(CollectionUtils.isNotEmpty(changes)){
				for(SyncConfigEntity change:changes){
					String tableName=change.getTable_name();
					
					
					if(paramTableName.equals(tableName)){
						changes.remove(change);
					}
				}
			}
		}
	}
}
