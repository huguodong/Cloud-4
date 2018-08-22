package com.ssitcloud.datasync.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.HeartBeatTransOperLogState;
import com.ssitcloud.datasync.entity.MetadataOpercmdTableEntity;
import com.ssitcloud.datasync.entity.TransOperationLogEntity;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.library.service.LibraryService;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
/**
 * 上传操作日志到Mongodb 
 * 
 * 主要是跟图书馆业务相关的日志
 *
 * <p>2017年3月2日 上午10:39:56  
 * @author hjc 
 *
 */
@Component(value="transOperationLog")
public class TransOperationLog extends BasicServiceImpl implements DataSyncCommand{
	
	private static final String URL_TRANS_OPERATION_LOG="transOperationLog";
	private static final String URL_ADDITIONAL_MAINFIELD_LIST="additionalMainfieldList";
	
	private static final String URL_addElectronicCertificateWithoutSame="addElectronicCertificateWithoutSame";
	private static final String URL_SAVE_DEVICEREADER_INFO="saveDeviceReaderInfo";
	
	//@Resource(name="MAINFIELD_CACHE")
	//private Cache fieldCache;
	
	//@Resource(name="CMDTABLE_CACHE")
	//private Cache cmdTableCache;
	
	//@Resource(name="LIBRARY_CACHE")
	//private Cache librarycache;
	
	@Resource(name = "heartBeatTransOperLogState")
	private HeartBeatTransOperLogState heartBeatTransOperLogState;//记录上一次操作数据，比较本次与上一次是否一致，相同则不处理
	
	@Resource
	private LibraryService libServcie;
	
	@SuppressWarnings("unchecked")
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
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
		
		boolean isNewState = false;
		if (map != null && map.containsKey("device_id") && map.containsKey("library_id")) {
			String dev_id = (String) map.get("device_id");
			String library_id =(String)map.get("library_id");
			if(!StringUtils.isBlank(dev_id)&&!StringUtils.isBlank(library_id)){
				String keyName=library_id+"_"+dev_id;
				if(heartBeatTransOperLogState != null && heartBeatTransOperLogState.containsKey(keyName)){
					Queue<Map<String, Object>> transOperLogState = heartBeatTransOperLogState.get(keyName);
					Map<String, Object> operLogMap = null;
					for(Map<String, Object> map_ : transOperLogState){
						operLogMap = map_;
						break;
					}
					for (String key : content.keySet()) {
						String value = String.valueOf(content.get(key));
						String value_ = String.valueOf(operLogMap.get(key));
						if(value == null || !value.equals(value_)){
							transOperLogState.remove(operLogMap);
							transOperLogState.add(content);
							isNewState = true;
							break;
						}
					}
				}else{
					ConcurrentLinkedQueue<Map<String, Object>> transOperLogState = new ConcurrentLinkedQueue<Map<String, Object>>();
					transOperLogState.add(content);
					heartBeatTransOperLogState.put(keyName, transOperLogState);
					isNewState = true;
				}
			}
		}
		
		if(!isNewState){
			resp.getData().setState(true);
			return resp;
		}
		
		String opercmd = (String) content.get("opercmd");//获取指令码 
//		String newOpercmd = TransOperationLogEntity.getTranslation(opercmd);//跟要保存的mongodb表名相同
//		String electronicType = TransOperationLogEntity.getElectronicType(opercmd);//保存到library_info的库中的电子凭证类型
		
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
			LogUtils.error("对比字段出错",e);
		}
		
		
		String library_idx = null;
		if(map.get("library_id") != null){
			library_idx = libServcie.getLibraryIdxById(map.get("library_id").toString())+"";
			/*if(librarycache != null){
				Element e = librarycache.get(map.get("library_id"));
				if(e!=null){
					library_idx = (String) e.getObjectValue();
				}
			}*/
		}
		if(electronicType!=null){
			//如果电子凭证的类型不为空，则发送数据到library_info库中新增电子凭证
			
//			electronic_idxint(11) NOT NULL记录号
//			lib_idxint(11) NOT NULL图书馆idx
//			electronic_typevarchar(10) NOT NULL操作类型 1借书 2还书 3续借 4交押金 5交预付款 6交欠款 7从预付款扣除
//			cardnovarchar(50) NULL证号
//			barnovarchar(50) NULL图书条码号
//			titlevarchar(300) NULL书名
//			finedouble NULL费用
//			control_timetimestamp NOT NULL处理时间
			
			//先根据图书馆id查询图书馆idx
			try {
				Map<String, String> electronicMap = new HashMap<>();
				Map<String, String> libParam = new HashMap<>();
				String libid = map.get("library_id")==null?"":(map.get("library_id")+"");
				if("".equals(libid)){
					LogUtils.error("设备传过来的日志中，library_id为空");
				}else{
					electronicMap.put("lib_idx", library_idx);
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
		Map<String,String> params=new HashMap<>();
		params.put("req", JsonUtils.toJson(map));
		String result=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_TRANS_OPERATION_LOG), params, charset);
		
		if (newOpercmd.equals(TransOperationLogEntity.CARDISSUE_LOG)) {//如果等于办证，把读者信息保存到 libraryinfo库中的devicereader表中
			try {
				Map<String, String> readerParam = new HashMap<>();
				Map<String, Object> readerMap = new HashMap<>();
				readerMap.putAll(map);
				
				if (library_idx==null) {
					readerMap.put("library_idx",library_idx);
					readerParam.put("req", JsonUtils.toJson(readerMap));
					HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SAVE_DEVICEREADER_INFO), readerParam, charset);
				}
			} catch (Exception e) {
				LogUtils.error("",e);
			}
		}
		if(result!=null){
			ResultEntity res=JsonUtils.fromJson(result, ResultEntity.class);
			if(res!=null){
				resp.setData(res);
			}
		}
		
		return resp;
	}
	
	/*@SuppressWarnings("unchecked")
	private void updateMainfieldCache(){
		Assert.notNull(fieldCache, "Need a cache. Please use setCache(Cache) create it.");  
		String result = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_GET_MAINFIELD), new HashMap<String, String>(), charset);
		if (StringUtils.isNotBlank(result)) {
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if (resultEntity.getState() && resultEntity.getResult() instanceof List) {
				List<Map<String, Object>> list = (List<Map<String, Object>>) resultEntity.getResult();
				for (Map<String, Object> map : list) {
					String table = (String) map.get("mainfield_table");//对应mongodb的表名
					String field = (String) map.get("field");
					List<String> fieldlist = Arrays.asList(field.split(","));
					Element element = new Element(table, fieldlist);
					fieldCache.put(element);
				}
			}
		}
	}*/
	
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
