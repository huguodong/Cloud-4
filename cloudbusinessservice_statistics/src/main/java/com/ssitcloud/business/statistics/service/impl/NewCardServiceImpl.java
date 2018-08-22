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
import com.ssitcloud.statistics.entity.NewCardDayDataEntity;
import com.ssitcloud.statistics.entity.NewCardMonthDataEntity;
import com.ssitcloud.statistics.entity.NewCardWeekDataEntity;
import com.ssitcloud.statistics.entity.NewCardYearDataEntity;

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
import com.ssitcloud.business.statistics.service.NewCardService;

@Service
public class NewCardServiceImpl extends BasicServiceImpl implements NewCardService{
	
	private static final String URL_GETALLNEWCARDDAY = "getAllNewCardDay";
	
	private static final String URL_GETALLNEWCARDWEEK = "getAllNewCardWeek";
	
	private static final String URL_GETALLNEWCARDMONTH = "getAllNewCardMonth";
	
	private static final String URL_GETALLNEWCARDYEAR = "getAllNewCardYear";
	
	private static final String URL_SELREGIONBYDEVICEIDX = "selRegionByDeviceidx";
	
	

	@Override
	public ResultEntity getAllNewCard() {
		ResultEntity resultEntity = new ResultEntity();
		try {
			getAllNewCardDayData();
			getAllNewCardWeekData();
			getAllNewCardMonthData();
			getAllNewCardYearData();
		} catch (Exception e) {
			LogUtils.error("",e);
		}
		return resultEntity;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	private void getAllNewCardDayData(){
		String newCardDayResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLNEWCARDDAY), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(newCardDayResult) ) {
			ResultEntity newCardResultEntity = JsonUtils.fromJson(newCardDayResult, ResultEntity.class);
//			
			if (newCardResultEntity.getResult() instanceof List) {
				List<NewCardDayDataEntity> list = JsonUtils.fromJson(JsonUtils.toJson(newCardResultEntity.getResult()), new TypeReference<List<NewCardDayDataEntity>>() {});
				
				for (NewCardDayDataEntity newCardDayDataEntity : list) {
					try {
						String device_idx = newCardDayDataEntity.getDevice_idx();
						String lib_idx = newCardDayDataEntity.getLib_idx();
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(StringUtils.isEmpty(lib_id)){
							continue;
						}
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
						
						
						newCardDayDataEntity.setLib_id(libraryEntity.getLib_id());
						newCardDayDataEntity.setLib_name(libraryEntity.getLib_name());
						
						newCardDayDataEntity.setDevice_id(device_id);
						newCardDayDataEntity.setDevice_idx(device_idx);
						newCardDayDataEntity.setDevice_name(device_name);
						newCardDayDataEntity.setDevice_type(device_type);
						newCardDayDataEntity.setDevice_type_desc(device_type_desc);
						newCardDayDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						newCardDayDataEntity.setAreaCode(areaCode);
						
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.CARDISSUE_LOG + "_" + Const.STATISTICS;
						
						boolean isExisted = checkIndexExisted(esIndexName, Const.DAY);
						JSONObject map = StatisticsUtils.clazzToMap(newCardDayDataEntity,newCardDayDataEntity.getNewCard_idx());
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
	private void getAllNewCardWeekData(){
		String newCardWeekResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLNEWCARDWEEK), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(newCardWeekResult) ) {
			ResultEntity newCardResultEntity = JsonUtils.fromJson(newCardWeekResult, ResultEntity.class);
//			
			if (newCardResultEntity.getResult() instanceof List) {
				List<NewCardWeekDataEntity> list = JsonUtils.fromJson(JsonUtils.toJson(newCardResultEntity.getResult()), new TypeReference<List<NewCardWeekDataEntity>>() {});
				
				for (NewCardWeekDataEntity newCardWeekDataEntity : list) {
					try {
						String device_idx = newCardWeekDataEntity.getDevice_idx();
						String lib_idx = newCardWeekDataEntity.getLib_idx();
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(StringUtils.isEmpty(lib_id)) continue;
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
						
						
						newCardWeekDataEntity.setLib_id(libraryEntity.getLib_id());
						newCardWeekDataEntity.setLib_name(libraryEntity.getLib_name());
						
						newCardWeekDataEntity.setDevice_id(device_id);
						newCardWeekDataEntity.setDevice_idx(device_idx);
						newCardWeekDataEntity.setDevice_name(device_name);
						newCardWeekDataEntity.setDevice_type(device_type);
						newCardWeekDataEntity.setDevice_type_desc(device_type_desc);
						newCardWeekDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						newCardWeekDataEntity.setAreaCode(areaCode);
						
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.CARDISSUE_LOG + "_" + Const.STATISTICS;
						
						boolean isExisted = checkIndexExisted(esIndexName, Const.WEEK);
						JSONObject map = StatisticsUtils.clazzToMap(newCardWeekDataEntity,newCardWeekDataEntity.getNewCard_idx());
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
	private void getAllNewCardMonthData(){
		String newCardMonthResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLNEWCARDMONTH), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(newCardMonthResult) ) {
			ResultEntity newCardResultEntity = JsonUtils.fromJson(newCardMonthResult, ResultEntity.class);
//			
			if (newCardResultEntity.getResult() instanceof List) {
				List<NewCardMonthDataEntity> list = JsonUtils.fromJson(JsonUtils.toJson(newCardResultEntity.getResult()), new TypeReference<List<NewCardMonthDataEntity>>() {});
				
				for (NewCardMonthDataEntity newCardMonthDataEntity : list) {
					try {
						String device_idx = newCardMonthDataEntity.getDevice_idx();
						String lib_idx = newCardMonthDataEntity.getLib_idx();
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(StringUtils.isEmpty(lib_id)){
							continue;
						}
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
						
						
						newCardMonthDataEntity.setLib_id(libraryEntity.getLib_id());
						newCardMonthDataEntity.setLib_name(libraryEntity.getLib_name());
						
						newCardMonthDataEntity.setDevice_id(device_id);
						newCardMonthDataEntity.setDevice_idx(device_idx);
						newCardMonthDataEntity.setDevice_name(device_name);
						newCardMonthDataEntity.setDevice_type(device_type);
						newCardMonthDataEntity.setDevice_type_desc(device_type_desc);
						newCardMonthDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						newCardMonthDataEntity.setAreaCode(areaCode);
						
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.CARDISSUE_LOG + "_" + Const.STATISTICS;
						
						boolean isExisted = checkIndexExisted(esIndexName, Const.MONTH);
						JSONObject map = StatisticsUtils.clazzToMap(newCardMonthDataEntity,newCardMonthDataEntity.getNewCard_idx());
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
	private void getAllNewCardYearData(){
		String newCardYearResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLNEWCARDYEAR), new HashMap<String,String>(), Consts.UTF_8.toString());
		if (StringUtils.isNotBlank(newCardYearResult) ) {
			ResultEntity newCardResultEntity = JsonUtils.fromJson(newCardYearResult, ResultEntity.class);
//			
			if (newCardResultEntity.getResult() instanceof List) {
				List<NewCardYearDataEntity> list = JsonUtils.fromJson(JsonUtils.toJson(newCardResultEntity.getResult()), new TypeReference<List<NewCardYearDataEntity>>() {});
				
				for (NewCardYearDataEntity newCardYearDataEntity : list) {
					try {
						String device_idx = newCardYearDataEntity.getDevice_idx();
						String lib_idx = newCardYearDataEntity.getLib_idx();
						String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+lib_idx);
						if(StringUtils.isEmpty(lib_id)){
							continue;
						}
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
						
						
						newCardYearDataEntity.setLib_id(libraryEntity.getLib_id());
						newCardYearDataEntity.setLib_name(libraryEntity.getLib_name());
						
						newCardYearDataEntity.setDevice_id(device_id);
						newCardYearDataEntity.setDevice_idx(device_idx);
						newCardYearDataEntity.setDevice_name(device_name);
						newCardYearDataEntity.setDevice_type(device_type);
						newCardYearDataEntity.setDevice_type_desc(device_type_desc);
						newCardYearDataEntity.setDevice_type_mark(device_type_mark);
						//add by huanghuang 20170825
						String areaCode = "";
						if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
							areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
						}
						newCardYearDataEntity.setAreaCode(areaCode);
						
						String esIndexName = libraryEntity.getLib_id() + "_" + device_id + "_" + Const.CARDISSUE_LOG + "_" + Const.STATISTICS;
						
						boolean isExisted = checkIndexExisted(esIndexName, Const.YEAR);
						JSONObject map = StatisticsUtils.clazzToMap(newCardYearDataEntity,newCardYearDataEntity.getNewCard_idx());
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
