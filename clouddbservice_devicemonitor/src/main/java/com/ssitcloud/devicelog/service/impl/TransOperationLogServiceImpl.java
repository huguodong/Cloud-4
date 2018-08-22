package com.ssitcloud.devicelog.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.LibraryEntity;
import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.service.InterfaceRequestComService;
import com.ssitcloud.common.util.BeanUtilEx;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.HeartBeatTransOperLogState;
import com.ssitcloud.datasync.entity.TransOperationLogEntity;
import com.ssitcloud.devicelog.service.DeviceStateService;
import com.ssitcloud.devicelog.service.TransOperationLogService;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.request.entity.InterfaceRequestDto;

@Service
public class TransOperationLogServiceImpl implements TransOperationLogService {

	private static final String URL_ADDITIONAL_MAINFIELD_LIST="additionalMainfieldList";
	
	private static final String URL_addElectronicCertificateWithoutSame="addElectronicCertificateWithoutSame";
	private static final String URL_SAVE_DEVICEREADER_INFO="saveDeviceReaderInfo";
	
	private static final String UPDATEREQUEST = "updateRequest";
	private static final String DELETEREQUEST = "deleteRequest";
	
	protected final static String charset=Consts.UTF_8.toString();
	
	@Resource(name = "requestURL")
	protected RequestURLListEntity requestURLListEntity;
	
	@Resource(name = "heartBeatTransOperLogState")
	private HeartBeatTransOperLogState heartBeatTransOperLogState;//记录上一次操作数据，比较本次与上一次是否一致，相同则不处理
	
	@Resource
	private DeviceStateService deviceStateService;
	
	@Resource
	private InterfaceRequestComService interfaceRequestComService ;
	
	@Resource(name = "requestURL")
	protected RequestURLListEntity requestURL;
	
	@Override
	public RespEntity execute(String req) {
		System.out.println("-------TransOperationLog,req:" + req);
		RequestEntity requestEntity=JsonUtils.fromJson(req, RequestEntity.class);
		
		RespEntity respEntity =executeOptLog(requestEntity);
		
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
	public RespEntity executeOptLog(RequestEntity conditionInfo) {
		
		RespEntity resp = new RespEntity(conditionInfo);
		Map<String, Object> map = conditionInfo.getData();
		
		if (map == null || !map.containsKey("content")) {
			resp.getData().setState(false);
			resp.getData().setMessage("content 不能为空");
			return resp;
		}
		
		Map<String, Object> content = (Map<String, Object>) map.get("content");
		
		if(content == null){
			resp.getData().setState(false);
			resp.getData().setMessage("content 不能为空");
			return resp;
		}
		
		//对比数据
		boolean isNewState =compareOperateLogs(map);
		
		if(!isNewState){
			resp.getData().setState(true);
			return resp;
		}
		
		String opercmd = (String) content.get("opercmd");//获取指令码 
		
		String newOpercmd = getMongodbTable(opercmd);//跟要保存的mongodb表名相同
		String electronicType = getElectronicType(opercmd);//保存到library_info的库中的电子凭证类型
		
		
		if (StringUtils.isBlank(newOpercmd)) {
			resp.getData().setState(false);
			resp.getData().setMessage("请检查【"+opercmd+"】指令码是否存在");
			return resp;
		}
		List<String> fieldList = new ArrayList<>();//保存传过来日志字段
		for (Entry<String, Object> ent : content.entrySet()) {
			fieldList.add(ent.getKey());
		}
		content.put("collection", newOpercmd);//加上将要存入mongodb表名的字段，在db层要删掉此字段，这一行代码必须放到fieldList之后。
		
		//处理device库中的mainfield表数据
		dealMainField(newOpercmd,fieldList);
		
		String libraryId = map.get("library_id").toString();
		String libraryIdx = getLibraryIdxByLibrayId(libraryId);
		
	
		if(electronicType!=null){
			
			//处理电子凭证
			dealElectronicConfirm(map, libraryIdx, electronicType, content);
			
		}
		
		//保存数据到mongodb
		ResultEntityF<Object> result = saveOptLogToMongodb(map);
		resp.getData().setState(result.getState());
		resp.getData().setMessage(result.getMessage());
		if (newOpercmd.equals(TransOperationLogEntity.CARDISSUE_LOG)) {//如果等于办证，把读者信息保存到 libraryinfo库中的devicereader表中
			
			dealCardIssueLog(map,libraryIdx);
		}
		return resp;
	}

	/**
	 * 保存数据到mongodb
	 * @param map
	 * @return
	 */
	private ResultEntityF<Object> saveOptLogToMongodb(Map<String, Object> map) {
		String req ="";
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		
		try {
			req = JsonUtils.object2String(map);
			
			System.out.println("----------操作日志接口,transOperationLog,req=" + map);
			
			result = deviceStateService.transOperationLog(req);
			
		} catch (Exception e1) {
			System.out.println("------------transData to MongoDB,异常" + e1.getMessage());
			result.setState(false);
			e1.printStackTrace();
		}
		
		return result;
	}
	/**
	 * 日志数据对比，如有不同则更新到mongodb
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean compareOperateLogs(Map<String, Object> map) {
		
		boolean isNewState = false;
		Map<String, Object> content = (Map<String, Object>) map.get("content");
		
		if (map != null && map.containsKey("device_id") && map.containsKey("library_id")) {
			
			String dev_id = (String) map.get("device_id");
			String library_id =(String)map.get("library_id");
			
			if(!StringUtils.isBlank(dev_id)&&!StringUtils.isBlank(library_id)){
				
				String redisKey="heartsOptLog:" + library_id + ":"+dev_id;
				
//				Set<String> transOperLogs = JedisUtils.getInstance().smembers(redisKey);
//				
//				if(CollectionUtils.isEmpty(transOperLogs)){
//					return false ;
//				}
				
				if(heartBeatTransOperLogState != null && heartBeatTransOperLogState.containsKey(redisKey)){
					
					Queue<Map<String, Object>> transOperLogState = heartBeatTransOperLogState.get(redisKey);
					Map<String, Object> operLogMap = null;
					
					for(Map<String, Object> operateLogMap : transOperLogState){
						
						operLogMap = operateLogMap;
						break;
						
					}
					
					for (String key : content.keySet()) {
						
						String value = String.valueOf(content.get(key));
						String orgOptValue = String.valueOf(operLogMap.get(key));
						
						if(value == null || !value.equals(orgOptValue)){
							
							transOperLogState.remove(operLogMap);
							transOperLogState.add(content);
							isNewState = true;
							break;
						}
					}
					
				}else{
					
					ConcurrentLinkedQueue<Map<String, Object>> transOperLogState = new ConcurrentLinkedQueue<Map<String, Object>>();
					transOperLogState.add(content);
					heartBeatTransOperLogState.put(redisKey, transOperLogState);
					isNewState = true;
				}
			}
		}
		return isNewState;
	}
	/**
	 * 处理device库中的mainfield表数据
	 * @param newOpercmd
	 * @param fieldList
	 */
	private void dealMainField(String newOpercmd,List<String> fieldList) {
		try{
			Set<String> cacheFields = JedisUtils.getInstance().smembers(RedisConstant.MAINFIELD+newOpercmd);
			if(cacheFields != null && !cacheFields.isEmpty()){
				fieldList.removeAll(cacheFields);
				if(fieldList.size() > 0){//如果差集大于0，说明传过来的字段是mainfield表没有记录的,发送给服务器保存
					Map<String,String> param = new HashMap<>();
					param.put("mainfield_field", JsonUtils.toJson(fieldList));
					param.put("mainfield_table", newOpercmd);
					HttpClientUtil.doPost(
					requestURLListEntity.getRequestURL(URL_ADDITIONAL_MAINFIELD_LIST), param, charset);
					//刷新缓存
					cacheFields.addAll(fieldList);
					JedisUtils.getInstance().sadd(RedisConstant.MAINFIELD+newOpercmd,(String[])cacheFields.toArray());
				}
			}else{
				LogUtils.error("没有找到【"+newOpercmd+"】的字段缓存");
			}
		}catch(Exception e){
			e.printStackTrace();
			LogUtils.error("处理device库中的mainfield表数据出错",e);
		}
		
	}
	/**
	 * 处理办证信息
	 * @param map
	 */
	private void dealCardIssueLog(Map<String, Object> map,String libraryIdx) {
		try {
			Map<String, String> readerParam = new HashMap<>();
			Map<String, Object> readerMap = new HashMap<>();
			readerMap.putAll(map);
			
			if (libraryIdx==null) {
				readerMap.put("library_idx",libraryIdx);
				readerParam.put("req", JsonUtils.toJson(readerMap));
				String result = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SAVE_DEVICEREADER_INFO), readerParam, charset);
			}
		} catch (Exception e) {
			LogUtils.error("",e);
		}
		
	}
	/**
	 * 处理电子凭证：发送数据到library_info库中新增电子凭证
	 * @param map
	 * @param libraryIdx
	 * @param electronicType
	 * @param content
	 */
	private void dealElectronicConfirm(Map<String, Object> map,String libraryIdx,String electronicType,Map<String, Object> content) {

		try {
			Map<String, String> electronicMap = new HashMap<>();
			String libid = map.get("library_id")==null?"":(map.get("library_id")+"");
			if("".equals(libid)){
				LogUtils.error("设备传过来的日志中，library_id为空");
			}else{
				electronicMap.put("lib_idx", libraryIdx);
				electronicMap.put("electronic_type", electronicType);
				electronicMap.put("cardno", content.get("operator")+"");
				electronicMap.put("barno", content.get("Barcode")+"");
				electronicMap.put("title", content.get("Title")+"");
				if(content.containsKey("Money")){
					String m = content.get("Monkey")==null?"0":(content.get("Monkey")+"");
					electronicMap.put("fine", m);
				}else if(content.containsKey("PrivilegeFee")){
					String m = content.get("PrivilegeFee")==null?"0":(content.get("PrivilegeFee")+"");
					electronicMap.put("fine", m);
					electronicMap.put("purpose", "1");//办证目的为 1
				}else{
					electronicMap.put("fine", "0");
				}
				//如果不是办证又有存款用途的时候,保存进去，存款用途 1押金 2欠款 3预付款
				if (content.containsKey("Purpose") && !content.containsKey("PrivilegeFee")) {
					electronicMap.put("purpose", content.get("Purpose")+"");
				}
				Timestamp timestamp = Timestamp.valueOf(content.get("opertime")+"");
				electronicMap.put("control_time_str", timestamp.toString());
				Map<String, String> eMap = new HashMap<>();
				eMap.put("json", JsonUtils.toJson(electronicMap));
				String addEleResult = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_addElectronicCertificateWithoutSame), eMap, charset);
				if(addEleResult!=null){
					ResultEntity addEleEntity = JsonUtils.fromJson(addEleResult, ResultEntity.class);
					if(!addEleEntity.getState()){
						LogUtils.error("添加电子凭证失败，"+addEleEntity.getMessage());
					}
				}
			}
		} catch (Exception e) {
			LogUtils.error("保存电子凭证出错",e);
		}
		
	}
	/**
	 * 获取图书馆idx
	 * @param map
	 * @return
	 */
	private String getLibraryIdxByLibrayId(String library_id) {
		String library_idx = "";
		if(StringUtils.isBlank(library_id)){
			return "";
		}
		String libraryStr = JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY, library_id);
		if(!StringUtils.isEmpty(libraryStr)){
			LibraryEntity libraryEntity = JsonUtils.fromJson(libraryStr, LibraryEntity.class);
			library_idx = libraryEntity.getLibrary_idx().toString();
		}
		return library_idx;
	}
	/**
	 * 从缓存中获取mongodb的保存表名
	 *
	 * <p>2017年8月29日 下午2:30:01 
	 * <p>create by hjc
	 * @param code
	 * @return
	 */
	private String getMongodbTable(String code){
		if (StringUtils.isNotBlank(code)) {
			
			String mongodbTable = JedisUtils.getInstance().get(RedisConstant.OPERCMD_TABLE+code);
			if(!StringUtils.isBlank(mongodbTable)){
				return mongodbTable;
			}
			else {
				LogUtils.error("没有获取到【"+code+"】的metadata_opercmd_table缓存，请确认metadata_opercmd_table表总是否有对应的数据");
			}
			
		}
		return null;
	}
	
	/**
	 * 从缓存中获取ElectronicType
	 *
	 * <p>2017年8月29日 下午2:34:01 
	 * <p>create by hjc
	 * @param code
	 * @return
	 */
	private String getElectronicType(String code){
		if (StringUtils.isNotBlank(code)) {
			String table = JedisUtils.getInstance().get("opercmd_type:"+code);
			if(!StringUtils.isBlank(table)){
				return table;
			}else{
				LogUtils.error("没有获取到【"+code+"】的metadata_opercmd_table缓存，请确认metadata_opercmd_table表总是否有对应的数据");
			}
		}
		return null;
	}
}
