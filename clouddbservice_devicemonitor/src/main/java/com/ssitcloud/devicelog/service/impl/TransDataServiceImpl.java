package com.ssitcloud.devicelog.service.impl;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.service.InterfaceRequestComService;
import com.ssitcloud.common.util.BeanUtilEx;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.datasync.entity.HeartBeatTransDataState;
import com.ssitcloud.devicelog.service.DeviceStateService;
import com.ssitcloud.devicelog.service.TransDataService;
import com.ssitcloud.operlog.bookenum.EnumClass;
import com.ssitcloud.request.entity.InterfaceRequestDto;

@Service
public class TransDataServiceImpl implements TransDataService {

	private static final String UPDATEREQUEST = "updateRequest";
	private static final String DELETEREQUEST = "deleteRequest";

	@Resource(name = "heartBeatTransOperLogState")
	private HeartBeatTransDataState heartBeatTransDataState;//记录上一次操作数据，比较本次与上一次是否一致，相同则不处理
	
	@Resource
	private DeviceStateService deviceStateService;
	
	@Resource
	private InterfaceRequestComService interfaceRequestComService ;
	
	@Resource(name = "requestURL")
	protected RequestURLListEntity requestURL;
	
	@Override
	public RespEntity execute(String req) {
		System.out.println("-------TransData,req:" + req);
		RequestEntity requestEntity=JsonUtils.fromJson(req, RequestEntity.class);
		
		RespEntity respEntity =executeTransData(requestEntity) ;
		String requestId = requestEntity.getRequestId();
		boolean state = respEntity.getData().getState();
		String  remark = respEntity.getData().getMessage() ;
		
		//更新接口监控表数据
		if(!StringUtils.isEmpty(requestId)){
			
			String updateInterfaceUrl = requestURL.getRequestURL(UPDATEREQUEST) ;
			String deleteInterfaceUrl = requestURL.getRequestURL(DELETEREQUEST) ;
			
			InterfaceRequestDto requestInterfaceDto = new InterfaceRequestDto();
			requestInterfaceDto.setRequestId(requestId);
			
			if(!state){
				
				requestInterfaceDto.setRemark(remark);
				//0-未处理：默认状态，1-异常状态,2--正常状态
				requestInterfaceDto.setResponseStatus(1);
				interfaceRequestComService.updateRequest(requestInterfaceDto, updateInterfaceUrl);
				
			}else{
				
				interfaceRequestComService.deleteRequest(requestInterfaceDto, deleteInterfaceUrl);
			}
		}
		
		
		return respEntity;
	}

	@SuppressWarnings("unchecked")
	public RespEntity executeTransData(RequestEntity conditionInfo) {
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
						
						for(Map<String, Object> transMap : transDataState){
							if(transMap != null){
								if(transMap.containsKey(EnumClass.COLLECTION.bin_state.name())){
									binStateMap = transMap;
								}else if(transMap.containsKey(EnumClass.COLLECTION.ext_state.name())){
									extStateMap = transMap;
								}else if(transMap.containsKey(EnumClass.COLLECTION.bookrack_state.name())){
									bookrackStateMap = transMap;
								}else if(transMap.containsKey(EnumClass.COLLECTION.soft_state.name())){
									softStateMap = transMap;
								}else if(transMap.containsKey(EnumClass.COLLECTION.bookbox_log.name())){
									bookboxLogMap = transMap;
								}else if(transMap.containsKey(EnumClass.COLLECTION.bookrack_log.name())){
									bookrackLogMap = transMap;
								}else if(transMap.containsKey(EnumClass.COLLECTION.cardbox_log.name())){
									cardboxLogMap = transMap;
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
					ResultEntityF<Object> result = saveTransDataToMongodb(conditionInfo.getData());
					res.setMessage(result.getMessage());
					res.setState(result.getState());
				}
			}
		}
		resp.setData(res);
		return resp;
	}

	/**
	 * 保存数据到mongodb
	 * @param map
	 * @return
	 */
	private ResultEntityF<Object> saveTransDataToMongodb(Map<String, Object> map) {
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		result.setState(false);
		String req ="";
		
		System.out.println("------------transData to MongoDB------------------req:" + map);
		
		try {
			
				req = JsonUtils.object2String(map);
				result = deviceStateService.transData(req);
			
		} catch (Exception e1) {
			System.out.println("------------transData to MongoDB,异常" + e1.getMessage());
			result.setMessage(e1.getMessage());
			e1.printStackTrace();
		}
		
		return result;
	}
}
