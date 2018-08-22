package com.ssitcloud.business.statistics.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.devmgmt.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.mobile.entity.RegionEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.statistics.entity.FineDayDataEntity;
import com.ssitcloud.statistics.entity.FineMonthDataEntity;
import com.ssitcloud.statistics.entity.FineWeekDataEntity;
import com.ssitcloud.statistics.entity.FineYearDataEntity;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.common.utils.HttpClientUtil;
import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.common.utils.LogUtils;
import com.ssitcloud.business.statistics.common.utils.StatisticsUtils;
import com.ssitcloud.business.statistics.service.FinanceService;


@Service
public class FinanceServiceImpl extends BasicServiceImpl implements FinanceService {
	
	private static final String URL_GETALLFINANCEDAY = "getAllFinanceDay";
	
	private static final String URL_GETALLFINANCEWEEK = "getAllFinanceWeek";
	
	private static final String URL_GETALLFINANCEMONTH = "getAllFinanceMonth";
	
	private static final String URL_GETALLFINANCEYEAR = "getAllFinanceYear";
	
	private static final String URL_SELREGIONBYDEVICEIDX = "selRegionByDeviceidx";
	
	@Override
	public ResultEntity getAllFinance() {
		ResultEntity resultEntity = new ResultEntity();
		try {
			getAllFinanceDayData();
			getAllFinanceWeekData();
			getAllFinanceMonthData();
			getAllFinanceYearData();
		} catch (Exception e) {
			LogUtils.error("",e);
		}
		return resultEntity;
	}
	
	@SuppressWarnings({ "unchecked" })
	private void getAllFinanceDayData(){
		String fineDayResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLFINANCEDAY), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(fineDayResult) ) {
			ResultEntity fineResultEntity = JsonUtils.fromJson(fineDayResult, ResultEntity.class);
//			
			if (fineResultEntity.getResult() instanceof List) {
				List<FineDayDataEntity> list = JsonUtils.fromJson(JsonUtils.toJson(fineResultEntity.getResult()), new TypeReference<List<FineDayDataEntity>>() {});
				
				for (FineDayDataEntity fineDayDataEntity : list) {
					try {
						String device_idx = fineDayDataEntity.getDevice_idx();
						String lib_idx = fineDayDataEntity.getLib_idx();
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(lib_id == null) continue;
						LibraryEntity libraryEntity= JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
						String deviceId = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+device_idx);
						if(deviceId == null) continue;
						DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+deviceId,DeviceEntity.class);
						if (libraryEntity == null  || deviceEntity == null ) {
							continue;
						}
						
						MetadataDeviceTypeEntity deviceTypeEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE_TYPE+deviceEntity.getDevice_model_idx()
						,MetadataDeviceTypeEntity.class);
						if(deviceTypeEntity == null) deviceTypeEntity = new MetadataDeviceTypeEntity();
						String device_id = deviceEntity.getDevice_id()==null ? "" : deviceEntity.getDevice_id();
						String device_name = deviceEntity.getDevice_name()==null ? "" : deviceEntity.getDevice_name();
						
						String device_type = deviceTypeEntity.getDevice_type()==null ? "" : deviceTypeEntity.getDevice_type();
						String device_type_desc = deviceTypeEntity.getDevice_type_desc()==null ? "" : deviceTypeEntity.getDevice_type_desc();
						String device_type_mark = deviceTypeEntity.getDevice_type_mark()==null ? "" : deviceTypeEntity.getDevice_type_mark();
						
						
						fineDayDataEntity.setLib_id(libraryEntity.getLib_id());
						fineDayDataEntity.setLib_name(libraryEntity.getLib_name());
						
						fineDayDataEntity.setDevice_id(device_id);
						fineDayDataEntity.setDevice_idx(device_idx);
						fineDayDataEntity.setDevice_name(device_name);
						fineDayDataEntity.setDevice_type(device_type);
						fineDayDataEntity.setDevice_type_desc(device_type_desc);
						fineDayDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						fineDayDataEntity.setAreaCode(areaCode);
						
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.FINANCE_LOG + "_" + Const.STATISTICS;
						
						boolean isExisted = checkIndexExisted(esIndexName, Const.DAY);
						JSONObject map = StatisticsUtils.clazzToMap(fineDayDataEntity,fineDayDataEntity.getFine_idx());
						if(isExisted){
							//保存数据到es
							saveOrUpdateDocument(esIndexName, Const.DAY.toUpperCase(), map);
						}
						
					} catch (Exception e) {
						LogUtils.error("",e);
						continue;
					}
					
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private void getAllFinanceWeekData(){
		String fineWeekResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLFINANCEWEEK), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(fineWeekResult) ) {
			ResultEntity fineResultEntity = JsonUtils.fromJson(fineWeekResult, ResultEntity.class);
//			
			if (fineResultEntity.getResult() instanceof List) {
				List<FineWeekDataEntity> list = JsonUtils.fromJson(JsonUtils.toJson(fineResultEntity.getResult()), new TypeReference<List<FineWeekDataEntity>>() {});
				
				for (FineWeekDataEntity fineWeekDataEntity : list) {
					try {
						String device_idx = fineWeekDataEntity.getDevice_idx();
						String lib_idx = fineWeekDataEntity.getLib_idx();
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(lib_id == null) continue;
						LibraryEntity libraryEntity= JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
						String deviceId = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+device_idx);
						DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+deviceId,DeviceEntity.class);
						if (libraryEntity == null  || deviceEntity == null ) {
							continue;
						}
						
						MetadataDeviceTypeEntity deviceTypeEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE_TYPE+deviceEntity.getDevice_model_idx()
						,MetadataDeviceTypeEntity.class);
						if(deviceTypeEntity == null) deviceTypeEntity = new MetadataDeviceTypeEntity();
						String device_id = deviceEntity.getDevice_id()==null ? "" : deviceEntity.getDevice_id();
						String device_name = deviceEntity.getDevice_name()==null ? "" : deviceEntity.getDevice_name();
						
						String device_type = deviceTypeEntity.getDevice_type()==null ? "" : deviceTypeEntity.getDevice_type();
						String device_type_desc = deviceTypeEntity.getDevice_type_desc()==null ? "" : deviceTypeEntity.getDevice_type_desc();
						String device_type_mark = deviceTypeEntity.getDevice_type_mark()==null ? "" : deviceTypeEntity.getDevice_type_mark();
						
						
						fineWeekDataEntity.setLib_id(libraryEntity.getLib_id());
						fineWeekDataEntity.setLib_name(libraryEntity.getLib_name());
						
						fineWeekDataEntity.setDevice_id(device_id);
						fineWeekDataEntity.setDevice_idx(device_idx);
						fineWeekDataEntity.setDevice_name(device_name);
						fineWeekDataEntity.setDevice_type(device_type);
						fineWeekDataEntity.setDevice_type_desc(device_type_desc);
						fineWeekDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						fineWeekDataEntity.setAreaCode(areaCode);
						
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.FINANCE_LOG + "_" + Const.STATISTICS;
						
						boolean isExisted = checkIndexExisted(esIndexName, Const.WEEK);
						JSONObject map = StatisticsUtils.clazzToMap(fineWeekDataEntity,fineWeekDataEntity.getFine_idx());
						if(isExisted){
							//保存数据到es
							saveOrUpdateDocument(esIndexName, Const.WEEK.toUpperCase(), map);
						}
						
					} catch (Exception e) {
						LogUtils.error("",e);
						continue;
					}
					
				}
			}
		}
	}
	@SuppressWarnings({ "unchecked" })
	private void getAllFinanceMonthData(){
		String fineMonthResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLFINANCEMONTH), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(fineMonthResult) ) {
			ResultEntity fineResultEntity = JsonUtils.fromJson(fineMonthResult, ResultEntity.class);
//			
			if (fineResultEntity.getResult() instanceof List) {
				List<FineMonthDataEntity> list = JsonUtils.fromJson(JsonUtils.toJson(fineResultEntity.getResult()), new TypeReference<List<FineMonthDataEntity>>() {});
				
				for (FineMonthDataEntity fineMonthDataEntity : list) {
					try {
						String device_idx = fineMonthDataEntity.getDevice_idx();
						String lib_idx = fineMonthDataEntity.getLib_idx();
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(lib_id == null) continue;
						LibraryEntity libraryEntity= JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
						String deviceId = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+device_idx);
						DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+deviceId,DeviceEntity.class);
						if (libraryEntity == null  || deviceEntity == null ) {
							continue;
						}
						MetadataDeviceTypeEntity deviceTypeEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE_TYPE+deviceEntity.getDevice_model_idx()
						,MetadataDeviceTypeEntity.class);
						if(deviceTypeEntity == null) deviceTypeEntity = new MetadataDeviceTypeEntity();
						String device_id = deviceEntity.getDevice_id()==null ? "" : deviceEntity.getDevice_id();
						String device_name = deviceEntity.getDevice_name()==null ? "" : deviceEntity.getDevice_name();
						
						String device_type = deviceTypeEntity.getDevice_type()==null ? "" : deviceTypeEntity.getDevice_type();
						String device_type_desc = deviceTypeEntity.getDevice_type_desc()==null ? "" : deviceTypeEntity.getDevice_type_desc();
						String device_type_mark = deviceTypeEntity.getDevice_type_mark()==null ? "" : deviceTypeEntity.getDevice_type_mark();
						
						
						fineMonthDataEntity.setLib_id(libraryEntity.getLib_id());
						fineMonthDataEntity.setLib_name(libraryEntity.getLib_name());
						
						fineMonthDataEntity.setDevice_id(device_id);
						fineMonthDataEntity.setDevice_idx(device_idx);
						fineMonthDataEntity.setDevice_name(device_name);
						fineMonthDataEntity.setDevice_type(device_type);
						fineMonthDataEntity.setDevice_type_desc(device_type_desc);
						fineMonthDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						fineMonthDataEntity.setAreaCode(areaCode);
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.FINANCE_LOG + "_" + Const.STATISTICS;
						
						boolean isExisted = checkIndexExisted(esIndexName, Const.MONTH);
						JSONObject map = StatisticsUtils.clazzToMap(fineMonthDataEntity,fineMonthDataEntity.getFine_idx());
						if(isExisted){
							//保存数据到es
							saveOrUpdateDocument(esIndexName, Const.MONTH.toUpperCase(), map);
						}
						
					} catch (Exception e) {
						LogUtils.error("",e);
						continue;
					}
					
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private void getAllFinanceYearData(){
		String fineYearResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLFINANCEYEAR), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(fineYearResult) ) {
			ResultEntity fineResultEntity = JsonUtils.fromJson(fineYearResult, ResultEntity.class);
//			
			if (fineResultEntity.getResult() instanceof List) {
				List<FineYearDataEntity> list = JsonUtils.fromJson(JsonUtils.toJson(fineResultEntity.getResult()), new TypeReference<List<FineYearDataEntity>>() {});
				
				for (FineYearDataEntity fineYearDataEntity : list) {
					try {
						String device_idx = fineYearDataEntity.getDevice_idx();
						String lib_idx = fineYearDataEntity.getLib_idx();
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(lib_id == null) continue;
						LibraryEntity libraryEntity= JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
						String deviceId = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+device_idx);
						DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+deviceId,DeviceEntity.class);
						if (libraryEntity == null  || deviceEntity == null ) {
							continue;
						}
						
						MetadataDeviceTypeEntity deviceTypeEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE_TYPE+deviceEntity.getDevice_model_idx()
						,MetadataDeviceTypeEntity.class);
						if(deviceTypeEntity == null) deviceTypeEntity = new MetadataDeviceTypeEntity();
						String device_id = deviceEntity.getDevice_id()==null ? "" : deviceEntity.getDevice_id();
						String device_name = deviceEntity.getDevice_name()==null ? "" : deviceEntity.getDevice_name();
						
						String device_type = deviceTypeEntity.getDevice_type()==null ? "" : deviceTypeEntity.getDevice_type();
						String device_type_desc = deviceTypeEntity.getDevice_type_desc()==null ? "" : deviceTypeEntity.getDevice_type_desc();
						String device_type_mark = deviceTypeEntity.getDevice_type_mark()==null ? "" : deviceTypeEntity.getDevice_type_mark();
						
						
						fineYearDataEntity.setLib_id(libraryEntity.getLib_id());
						fineYearDataEntity.setLib_name(libraryEntity.getLib_name());
						
						fineYearDataEntity.setDevice_id(device_id);
						fineYearDataEntity.setDevice_idx(device_idx);
						fineYearDataEntity.setDevice_name(device_name);
						fineYearDataEntity.setDevice_type(device_type);
						fineYearDataEntity.setDevice_type_desc(device_type_desc);
						fineYearDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						fineYearDataEntity.setAreaCode(areaCode);
						
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.FINANCE_LOG + "_" + Const.STATISTICS;
						JSONObject map = StatisticsUtils.clazzToMap(fineYearDataEntity,fineYearDataEntity.getFine_idx());
						boolean isExisted = checkIndexExisted(esIndexName, Const.YEAR);
						if(isExisted){
							//保存数据到es
							saveOrUpdateDocument(esIndexName, Const.YEAR.toUpperCase(), map);
						}
						
					} catch (Exception e) {
						LogUtils.error("",e);
						continue;
					}
					
				}
			}
		}
	}
	
	/**
	 * 取地区码
	 * @param device_idx
	 * @return
	 */
	private String retAreaCode(Integer device_idx){
		String result = "";
		JSONObject reqJson = new JSONObject();
		reqJson.put("deviceIdx", device_idx);
		ResultEntity region = postURL(URL_SELREGIONBYDEVICEIDX, reqJson.toString());
		if(region!=null&&region.getState()){
			Map<String,String> r = (LinkedHashMap) region.getResult();
			if(r!=null){
				result = r.get("regi_code");
			}
		}
		return result;
	}
	

}
