package com.ssitcloud.datasync.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.datasync.entity.EnumClass;
import com.ssitcloud.datasync.entity.HeartBeatTransDataState;
import com.ssitcloud.datasync.service.DataSyncCommand;
/**
 * 上传操作日志到Mongodb 
 * @package: com.ssitcloud.datasync.service.impl
 * @classFile: TransData
 * @author: liuBh
 * @description: TODO
 */
@Component(value="transData")
public class TransData extends BasicServiceImpl implements DataSyncCommand{
	
	public static final String URL_TRANSDATA="transData";
	
	@Resource(name = "heartBeatTransDataState")
	private HeartBeatTransDataState heartBeatTransDataState;
	
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		ResultEntity res = new ResultEntity(true,"");
		RespEntity resp = new RespEntity(conditionInfo);
		Map<String, Object> map = conditionInfo.getData();
		
		if (map != null && map.containsKey("device_id") && map.containsKey("library_id")) {
			String dev_id = (String) map.get("device_id");
			String library_id =(String)map.get("library_id");
			if(StringUtils.hasText(dev_id)&&StringUtils.hasText(library_id)){
				String keyName=library_id+"_"+dev_id;
				boolean isNewState = false;
				map = (Map<String, Object>) map.get("content");
				if (map != null) {
					if(heartBeatTransDataState != null && heartBeatTransDataState.containsKey(keyName)){
						Queue<Map<String, Object>> transDataState = heartBeatTransDataState.get(keyName);
						Map<String, Object> binStateMap = null;
						Map<String, Object> extStateMap = null;
						Map<String, Object> bookrackStateMap = null;
						Map<String, Object> softStateMap = null;
						Map<String, Object> bookboxLogMap = null;
						Map<String, Object> bookrackLogMap = null;
						Map<String, Object> cardboxLogMap = null;
						for(Map<String, Object> map_ : transDataState){
							if(map_ != null){
								if(map_.containsKey(EnumClass.COLLECTION.bin_state.name())){
									binStateMap = map_;
								}else if(map_.containsKey(EnumClass.COLLECTION.ext_state.name())){
									extStateMap = map_;
								}else if(map_.containsKey(EnumClass.COLLECTION.bookrack_state.name())){
									bookrackStateMap = map_;
								}else if(map_.containsKey(EnumClass.COLLECTION.soft_state.name())){
									softStateMap = map_;
								}else if(map_.containsKey(EnumClass.COLLECTION.bookbox_log.name())){
									bookboxLogMap = map_;
								}else if(map_.containsKey(EnumClass.COLLECTION.bookrack_log.name())){
									bookrackLogMap = map_;
								}else if(map_.containsKey(EnumClass.COLLECTION.cardbox_log.name())){
									cardboxLogMap = map_;
								}
							}
						}
						/**
						 * 状态日志
						 */  
						if (map.containsKey(EnumClass.COLLECTION.bin_state.name())) {
							isNewState = true;
							Map<String, Object> binMap = (Map<String, Object>) map.get(EnumClass.COLLECTION.bin_state.name());
							if(transDataState.contains(binMap)){
								transDataState.remove(binMap);
							}
							transDataState.add(map);
						}else if (map.containsKey(EnumClass.COLLECTION.ext_state.name())) {
							if(extStateMap ==null){
								transDataState.add(map);
								isNewState = true;
							}else{
								Map<String, Object> stateMap = (Map<String, Object>) map.get(EnumClass.COLLECTION.ext_state.name());
								Map<String, Object> stateMap_ = (Map<String, Object>) extStateMap.get(EnumClass.COLLECTION.ext_state.name());
								if(stateMap!=null){
									for (String key : stateMap.keySet()) {
										if("PlcSSL".equals(key)){
											Map<String, Object> PlcSSLMap_ = (Map<String, Object>) stateMap_.get(key);
											Map<String, Object> PlcSSLMap = (Map<String, Object>) stateMap.get(key);
											for (String key_ : PlcSSLMap.keySet()) {
												String value = String.valueOf(PlcSSLMap.get(key_));
												String value_ = String.valueOf(PlcSSLMap_.get(key_));
												if(value == null || !value.equals(value_)){
													transDataState.remove(extStateMap);
													transDataState.add(map);
													isNewState = true;
													break;
												}
											}
										}else{
											String value = String.valueOf(stateMap.get(key));
											String value_ = String.valueOf(stateMap_.get(key));
											if(value == null || !value.equals(value_)){
												transDataState.remove(extStateMap);
												transDataState.add(map);
												isNewState = true;
												break;
											}
										}
									}
								}
							}
						}else if (map.containsKey(EnumClass.COLLECTION.bookrack_state.name())) {
							isNewState = true;
							Map<String, Object> bookrackMap = (Map<String, Object>) map.get(EnumClass.COLLECTION.bookrack_state.name());
							if(transDataState.contains(bookrackMap)){
								transDataState.remove(bookrackMap);
							}
							transDataState.add(map);
						}else if (map.containsKey(EnumClass.COLLECTION.soft_state.name())) {
							if(softStateMap == null){
								transDataState.add(map);
								isNewState = true;
							}else{
								Map<String, Object> stateMap = (Map<String, Object>) map.get(EnumClass.COLLECTION.soft_state.name());
								Map<String, Object> stateMap_ = (Map<String, Object>) softStateMap.get(EnumClass.COLLECTION.soft_state.name());
								if(stateMap!=null){
									if(stateMap.containsKey("Function")){
										Map<String, Object> funcMap = (Map<String, Object>) stateMap.get("Function");
										Map<String, Object> funcMap_ = (Map<String, Object>) stateMap_.get("Function");
										for (String key : funcMap.keySet()) {
											String value = String.valueOf(funcMap.get(key));
											String value_ = String.valueOf(funcMap_.get(key));
											if(value == null || !value.equals(value_)){
												transDataState.remove(softStateMap);
												transDataState.add(map);
												isNewState = true;
												break;
											}
										}
									}
									
								}
							}
						}else if (map.containsKey(EnumClass.COLLECTION.bookbox_log.name())) {//日志操作记录
							isNewState = true;
							Map<String, Object> bookboxMap = (Map<String, Object>) map.get(EnumClass.COLLECTION.bookbox_log.name());
							if(transDataState.contains(bookboxMap)){
								transDataState.remove(bookboxMap);
							}
							transDataState.add(map);
						}else if (map.containsKey(EnumClass.COLLECTION.bookrack_log.name())) {
							isNewState = true;
							Map<String, Object> bookrackMap = (Map<String, Object>) map.get(EnumClass.COLLECTION.bookrack_log.name());
							if(transDataState.contains(bookrackMap)){
								transDataState.remove(bookrackMap);
							}
							transDataState.add(map);
						}else if (map.containsKey(EnumClass.COLLECTION.cardbox_log.name())) {
							isNewState = true;
							Map<String, Object> cardboxMap = (Map<String, Object>) map.get(EnumClass.COLLECTION.cardbox_log.name());
							if(transDataState.contains(cardboxMap)){
								transDataState.remove(cardboxMap);
							}
							transDataState.add(map);
						}
					}else{
						ConcurrentLinkedQueue<Map<String, Object>> transDataState = new ConcurrentLinkedQueue<Map<String, Object>>();
						transDataState.add(map);
						heartBeatTransDataState.put(keyName, transDataState);
						isNewState = true;
					}
				}
				
				if(isNewState){
					Map<String,String> params=new HashMap<>();
					params.put("req", JsonUtils.toJson(conditionInfo.getData()));
					String result=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_TRANSDATA), params, charset);
					if(result!=null){
						res=JsonUtils.fromJson(result, ResultEntity.class);
					}
				}
			}
		}
		resp.setData(res);
		return resp;
	}

}
