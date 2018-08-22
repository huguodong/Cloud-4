package com.ssitcloud.datasync.service.impl;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.ConstsEntity;
import com.ssitcloud.datasync.entity.ControlResultEntity;
import com.ssitcloud.datasync.entity.UploadRunLogEntity;
import com.ssitcloud.datasync.service.DataSyncCommand;

@Component(value="uploadRunLog")
public class UploadRunLog extends BasicServiceImpl implements DataSyncCommand{
	
	public static final String URL_UPLOAD_RUNLOG = "uploadRunLog";
	
	@Resource(name="hazelcastInstance")
	private HazelcastInstance hazelcastInstance;

	
	public static final String PAGES="pages";
	public static final String NOW_PAGE="nowPage";
	public static final String CONTENTS="contents";
	public static final String OPERATOR_ID="operator_id";
	//public static final String START_TIME="startTime";
	//public static final String END_TIME="endTime";
	
	/****
	 * 可以考虑 存储在磁盘上
	 * 目录结构 /usr/deviceLog/device_id/operator_id/查询的日志
	 * 现在是存储在内存中
	 * 
	 * **/
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		Map<String, Object> map = conditionInfo.getData();
		try {
			String library_id=(String) map.get(LIB_ID);
			String device_id=(String) map.get(DEV_ID);
			String pages = null;
			String nowPage = null;
			String contents = null;
			String operator_id=null;
			if(map.containsKey(PAGES)){
				 pages=(String) map.get(PAGES);
				 LogUtils.info("设备："+device_id+"。上传的日志总页数："+pages);
			}
			if(map.containsKey(NOW_PAGE)){
				 nowPage=(String) map.get(NOW_PAGE);
				 LogUtils.info("设备："+device_id+"。上传的日志当前页数："+nowPage);
			}
			if(map.containsKey(CONTENTS)){
				 contents=(String) map.get(CONTENTS);
				 LogUtils.info("设备："+device_id+"。上传的日志内容为："+contents);
			}
			if(map.containsKey(OPERATOR_ID)){
				operator_id=(String) map.get(OPERATOR_ID);
			}
			UploadRunLogEntity UploadRunLog=new UploadRunLogEntity(device_id, library_id,operator_id, pages, nowPage, contents);
			//Map<String, String> params = new HashMap<>();
			//params.put("req", JsonUtils.toJson(map));
			//String result = HttpClientUtil.doPost(requestURLListEntity.getRquestURL(URL_UPLOAD_RUNLOG), params, charset);
			IMap<String,TreeMap<String,UploadRunLogEntity>> uploadRunLogContainer=hazelcastInstance.getMap(ConstsEntity.UPLOAD_RUNLOG_CONTAINER);
			if(uploadRunLogContainer!=null){
				String key=device_id;//设备ID+用户ID
				if(uploadRunLogContainer.containsKey(key)){
					if(UploadRunLog.getNowPage()!=null){
						TreeMap<String,UploadRunLogEntity> pageMap=uploadRunLogContainer.get(key);
						pageMap.put(UploadRunLog.getNowPage(), UploadRunLog);
						uploadRunLogContainer.set(key, pageMap);
					}
				}else{
					//第一次
					if(UploadRunLog.getNowPage()!=null){
						TreeMap<String,UploadRunLogEntity> set=new TreeMap<String,UploadRunLogEntity>();
						set.put(UploadRunLog.getNowPage(), UploadRunLog);
						uploadRunLogContainer.set(key, set);
					}
				}
				resp.getData().setState(true);
				resp.getData().setResult(new ControlResultEntity(device_id, library_id, "1", ""));
			}
			
			
		} catch (Exception e) {
			// log error and post to the View Service
			// setting respEntity and return
			ExceptionHelper.afterException(resp, Thread.currentThread(), e);
		}

		return resp;
	}

}
