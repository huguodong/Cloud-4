package com.ssitcloud.datasync.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.datasync.entity.ConstsEntity;
import com.ssitcloud.datasync.entity.ControlResultEntity;
import com.ssitcloud.datasync.service.DataSyncCommand;

@Component(value="controlResult")
public class ControlResult extends BasicServiceImpl implements DataSyncCommand{


	@Resource(name="hazelcastInstance")
	private HazelcastInstance hazelcastInstance;
	
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		Map<String, Object> map = conditionInfo.getData();

		String lib_id = map.get(LIB_ID) + "";
		String dev_id = map.get(DEV_ID) + "";
		//不加的话这边控制一个人操作。
		//String operator_id = map.get("operator_id") + "";
		String controlresult=map.get("controlresult")+"";
		String errInfo=map.get("errInfo")+"";
		IMap<String, ConcurrentLinkedQueue<ControlResultEntity>> controlResultContainer=hazelcastInstance.getMap(ConstsEntity.CONTROL_RESULT_CONTAINER);
		String key=dev_id;
		
		ControlResultEntity controlResult=new ControlResultEntity(dev_id,lib_id,controlresult,errInfo);

		if(controlResultContainer!=null){
			if(controlResultContainer.containsKey(key)){
				//存在命令执行结果队列
				ConcurrentLinkedQueue<ControlResultEntity> queue=controlResultContainer.get(dev_id);
				queue.add(controlResult);
				controlResultContainer.put(key, queue);
			}else{//不存在队列，第一次
				ConcurrentLinkedQueue<ControlResultEntity> queue= new ConcurrentLinkedQueue<>();
				queue.add(controlResult);
				controlResultContainer.put(key, queue);
			}
		}else{
			//controlResultContainer is null?
		}
		Map<String, Object> res = new HashMap<>();
		res.put(LIB_ID, lib_id);
		res.put(DEV_ID, dev_id);
		resp.getData().setState(true);
		resp.getData().setResult(res);
		// String
		// ctrlResult=map.containsKey(CONTROL_RESULT)==true?map.get(CONTROL_RESULT)+"":null;
		// String
		// errInfo=map.containsKey(ERR_INFO)==true?map.get(ERR_INFO)+"":null;
		// post 到 view 层?
		// ControlResultEntity controlResult=new ControlResultEntity(dev_id,
		// lib_id, ctrlResult, errInfo);
		//try {
			//Map<String, String> params = new HashMap<>();
			//params.put("req", JsonUtils.toJson(map));
			//HttpClientUtil.doPost(requestURLListEntity.getRquestURL(URL_CONTROL_RESULT), params,charset);
		//} catch (Exception e) {
			//ExceptionHelper.afterException(new RespEntity(), Thread.currentThread(), e);
			//HttpClientUtil.doPost(requestURLListEntity.getRquestURL(URL_CONTROL_RESULT),new HashMap<String, String>(), charset);
			//view 显示执行成功与否
		//}

		return resp;
	}

}
