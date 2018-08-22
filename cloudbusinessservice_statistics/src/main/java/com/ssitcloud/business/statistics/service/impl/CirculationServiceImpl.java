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
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.statistics.entity.CirculationDayDataEntity;
import com.ssitcloud.statistics.entity.CirculationMonthDataEntity;
import com.ssitcloud.statistics.entity.CirculationWeekDataEntity;
import com.ssitcloud.statistics.entity.CirculationYearDataEntity;

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
import com.ssitcloud.business.statistics.service.CirculationService;

@Service
public class CirculationServiceImpl extends BasicServiceImpl implements CirculationService {
	
	private static final String URL_GETALLCIRCULATIONDAY = "getAllCirculationDay";
	
	private static final String URL_GETALLCIRCULATIONWEEK = "getAllCirculationWeek";
	
	private static final String URL_GETALLCIRCULATIONMONTH = "getAllCirculationMonth";
	
	private static final String URL_GETALLCIRCULATIONYEAR = "getAllCirculationYear";
	
	private static final String URL_SELREGIONBYDEVICEIDX = "selRegionByDeviceidx";
	
	
	@Override
	public ResultEntity getAllCirculation() {
		ResultEntity resultEntity = new ResultEntity();
		try {
			getAllCirculationDayData();
			getAllCirculationWeekData();
			getAllCirculationMonthData();
			getAllCirculationYearData();
			
		} catch (Exception e) {
			LogUtils.error("",e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 获取日流通数据
	 *
	 * <p>2017年4月21日 上午10:37:18 
	 * <p>create by hjc
	 */
	@SuppressWarnings({ "unchecked" })
	private void getAllCirculationDayData(){

		String cirDayResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLCIRCULATIONDAY), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(cirDayResult) ) {
			ResultEntity cirResultEntity = JsonUtils.fromJson(cirDayResult, ResultEntity.class);
//			
			if (cirResultEntity.getResult() instanceof List) {
				List<CirculationDayDataEntity> list1 = JsonUtils.fromJson(JsonUtils.toJson(cirResultEntity.getResult()), new TypeReference<List<CirculationDayDataEntity>>() {});
				
				for (CirculationDayDataEntity circulationDayDataEntity : list1) {
					try {
						String device_idx = circulationDayDataEntity.getDevice_idx();
						String lib_idx = circulationDayDataEntity.getLib_idx();
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(lib_id == null) continue;
						LibraryEntity libraryEntity = JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
						String deviceId = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+device_idx);
						DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+deviceId,DeviceEntity.class);
						if (libraryEntity == null || deviceEntity == null ) {
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
						
						//idx加上前缀，防止几个type的id有冲突
//						circulationDayDataEntity.setCir_idx("d" + circulationDayDataEntity.getCir_idx());
						
						circulationDayDataEntity.setLib_id(libraryEntity.getLib_id());
						circulationDayDataEntity.setLib_name(libraryEntity.getLib_name());
						
						circulationDayDataEntity.setDevice_id(device_id);
						circulationDayDataEntity.setDevice_idx(device_idx);
						circulationDayDataEntity.setDevice_name(device_name);
						circulationDayDataEntity.setDevice_type(device_type);
						circulationDayDataEntity.setDevice_type_desc(device_type_desc);
						circulationDayDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						circulationDayDataEntity.setAreaCode(areaCode);
						
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.LOAN_LOG + "_" + Const.STATISTICS;
						
						//判断索引是不是存在
						boolean isExisted = checkIndexExisted(esIndexName, Const.DAY);
						JSONObject map = StatisticsUtils.clazzToMap(circulationDayDataEntity,circulationDayDataEntity.getCir_idx());
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
	
	
	/**
	 * 获取周流通数据
	 *
	 * <p>2017年4月21日 上午10:37:52 
	 * <p>create by hjc
	 */
	@SuppressWarnings("unchecked")
	private void getAllCirculationWeekData(){
		String cirWeekResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLCIRCULATIONWEEK), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(cirWeekResult) ) {
			ResultEntity cirResultEntity = JsonUtils.fromJson(cirWeekResult, ResultEntity.class);
			
			if (cirResultEntity.getResult() instanceof List) {
				List<CirculationWeekDataEntity> list = JsonUtils.fromJson(JsonUtils.toJson(cirResultEntity.getResult()), new TypeReference<List<CirculationWeekDataEntity>>() {});
				
				for (CirculationWeekDataEntity circulationWeekDataEntity : list) {
					try {
						String device_idx = circulationWeekDataEntity.getDevice_idx();
						String lib_idx = circulationWeekDataEntity.getLib_idx();
						
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(lib_id == null) continue;
						LibraryEntity libraryEntity = JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
						String deviceId = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+device_idx);
						DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+deviceId,DeviceEntity.class);
						if (libraryEntity == null || deviceEntity == null ) {
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
						
						//idx加上前缀，防止几个type的id有冲突
//						circulationWeekDataEntity.setCir_idx("w" + circulationWeekDataEntity.getCir_idx());
						
						circulationWeekDataEntity.setLib_id(libraryEntity.getLib_id());
						circulationWeekDataEntity.setLib_name(libraryEntity.getLib_name());
						
						circulationWeekDataEntity.setDevice_id(device_id);
						circulationWeekDataEntity.setDevice_idx(device_idx);
						circulationWeekDataEntity.setDevice_name(device_name);
						circulationWeekDataEntity.setDevice_type(device_type);
						circulationWeekDataEntity.setDevice_type_desc(device_type_desc);
						circulationWeekDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						circulationWeekDataEntity.setAreaCode(areaCode);
						
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.LOAN_LOG + "_" + Const.STATISTICS;
						
						boolean isExisted = checkIndexExisted(esIndexName, Const.WEEK);
						JSONObject map = StatisticsUtils.clazzToMap(circulationWeekDataEntity,circulationWeekDataEntity.getCir_idx());
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
	@SuppressWarnings("unchecked")
	private void getAllCirculationMonthData(){
		String cirDayResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLCIRCULATIONMONTH), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(cirDayResult) ) {
			ResultEntity cirResultEntity = JsonUtils.fromJson(cirDayResult, ResultEntity.class);
			
			if (cirResultEntity.getResult() instanceof List) {
				List<CirculationMonthDataEntity> list = JsonUtils.fromJson(JsonUtils.toJson(cirResultEntity.getResult()), new TypeReference<List<CirculationMonthDataEntity>>() {});
				
				for (CirculationMonthDataEntity circulationMonthDataEntity : list) {
					try {
						String device_idx = circulationMonthDataEntity.getDevice_idx();
						String lib_idx = circulationMonthDataEntity.getLib_idx();
						
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(lib_id == null) continue;
						LibraryEntity libraryEntity = JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
						String deviceId = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+device_idx);
						DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+deviceId,DeviceEntity.class);
						if (libraryEntity == null || deviceEntity == null ) {
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
						
						
						//idx加上前缀，防止几个type的id有冲突
//						circulationMonthDataEntity.setCir_idx("m" + circulationMonthDataEntity.getCir_idx());
						
						circulationMonthDataEntity.setLib_id(libraryEntity.getLib_id());
						circulationMonthDataEntity.setLib_name(libraryEntity.getLib_name());
						
						circulationMonthDataEntity.setDevice_id(device_id);
						circulationMonthDataEntity.setDevice_idx(device_idx);
						circulationMonthDataEntity.setDevice_name(device_name);
						circulationMonthDataEntity.setDevice_type(device_type);
						circulationMonthDataEntity.setDevice_type_desc(device_type_desc);
						circulationMonthDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						circulationMonthDataEntity.setAreaCode(areaCode);
						
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.LOAN_LOG + "_" + Const.STATISTICS;
						
						boolean isExisted = checkIndexExisted(esIndexName, Const.MONTH);
						JSONObject map = StatisticsUtils.clazzToMap(circulationMonthDataEntity,circulationMonthDataEntity.getCir_idx());
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
	@SuppressWarnings("unchecked")
	private void getAllCirculationYearData(){
		String cirDayResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLCIRCULATIONYEAR), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(cirDayResult) ) {
			ResultEntity cirResultEntity = JsonUtils.fromJson(cirDayResult, ResultEntity.class);
			
			if (cirResultEntity.getResult() instanceof List) {
				List<CirculationYearDataEntity> list = JsonUtils.fromJson(JsonUtils.toJson(cirResultEntity.getResult()), new TypeReference<List<CirculationYearDataEntity>>() {});
				
				for (CirculationYearDataEntity circulationYearDataEntity : list) {
					try {
						String device_idx = circulationYearDataEntity.getDevice_idx();
						String lib_idx = circulationYearDataEntity.getLib_idx();
						
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(lib_id == null) continue;
						LibraryEntity libraryEntity = JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
						String deviceId = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+device_idx);
						DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+deviceId,DeviceEntity.class);
						if (libraryEntity == null || deviceEntity == null ) {
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
						
						
						//idx加上前缀，防止几个type的id有冲突
//						circulationYearDataEntity.setCir_idx("y" + circulationYearDataEntity.getCir_idx());
						
						
						circulationYearDataEntity.setLib_id(libraryEntity.getLib_id());
						circulationYearDataEntity.setLib_name(libraryEntity.getLib_name());
						
						circulationYearDataEntity.setDevice_id(device_id);
						circulationYearDataEntity.setDevice_idx(device_idx);
						circulationYearDataEntity.setDevice_name(device_name);
						circulationYearDataEntity.setDevice_type(device_type);
						circulationYearDataEntity.setDevice_type_desc(device_type_desc);
						circulationYearDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						circulationYearDataEntity.setAreaCode(areaCode);
						
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.LOAN_LOG + "_" + Const.STATISTICS;
						
						boolean isExisted = checkIndexExisted(esIndexName, Const.YEAR);
						JSONObject map = StatisticsUtils.clazzToMap(circulationYearDataEntity,circulationYearDataEntity.getCir_idx());
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
