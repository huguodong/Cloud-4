package com.ssitcloud.business.statistics.common.scheduled;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.devmgmt.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.statistics.entity.FinanceLogEntity;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.common.utils.StatisticsUtils;
import com.ssitcloud.business.statistics.service.FinanceLogService;
import com.ssitcloud.business.statistics.service.StatisticsService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

@JobHandler(value="elasticsearchFinanceLogScheduled")
@Component
public class ElasticsearchFinanceLogScheduled  extends IJobHandler{
	
	
	@Resource
	private FinanceLogService financeLogService;
	
	@Resource
	private StatisticsService statisticsservice;
	
	
	/**
	 * 从mongodb中获取finance_log数据，然后完善读者信息，设备信息，图书馆信息，将这些信息保存到es中
	 * 
	 * <br>=>获取所有图书馆
	 * <br>=>获取图书馆所有设备
	 * <br>=>查询es中是否有设备的日志，有则获取最后一条数据的时间
	 * 
	 * <p>2017年5月15日 
	 * <p>create by lqw
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception{
		System.out.println("mixFinanceLog定时任务开始");
		Long start = System.currentTimeMillis();
		Map<String, String> libmap= JedisUtils.getInstance().hgetAll(RedisConstant.LIBRARY_KEY);
		if(!MapUtils.isEmpty(libmap)){
			for(String key : libmap.keySet()){
				String libStr = libmap.get(key);
				if (libStr == null || libStr.length() <= 0) continue;
				LibraryEntity libraryEntity = JsonUtils.fromJson(libStr, LibraryEntity.class);
				String lib_id = libraryEntity.getLib_id();
				String lib_name = libraryEntity.getLib_name();
				Set<String> deviceIds = JedisUtils.getInstance().smembers(RedisConstant.LIBRARY_DEVICEID+libraryEntity.getLibrary_idx());
				System.out.println("---当前图书馆:" + lib_id+",设备总数：" + deviceIds.size());
				if (deviceIds != null && deviceIds.size() > 0) {
					for (String deviceId : deviceIds) {
						DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+deviceId,DeviceEntity.class);
						if(deviceEntity == null) deviceEntity = new DeviceEntity();
						MetadataDeviceTypeEntity deviceTypeEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE_TYPE+deviceEntity.getDevice_model_idx(),MetadataDeviceTypeEntity.class);
						if(deviceTypeEntity == null) deviceTypeEntity = new MetadataDeviceTypeEntity();
						String device_idx =deviceEntity.getDevice_idx() == null ? "":deviceEntity.getDevice_idx()+"";
						String device_id = deviceEntity.getDevice_id()==null ? "" : deviceEntity.getDevice_id();
						String device_name = deviceEntity.getDevice_name()==null ? "" : deviceEntity.getDevice_name();
                        String device_type = deviceTypeEntity.getDevice_type() == null ? "" : deviceTypeEntity.getDevice_type();
						String device_type_desc = deviceTypeEntity.getDevice_type_desc()==null ? "" :deviceTypeEntity.getDevice_type_desc();
						String device_type_mark = deviceTypeEntity.getDevice_type_mark()==null ? "" : deviceTypeEntity.getDevice_type_mark();
						Integer devIdx = 0;
						if(StatisticsUtils.strIsNum(device_idx)){
							devIdx = Integer.parseInt(device_idx);
						}
						String areaCode = statisticsservice.retAreaCode(devIdx);
						
						//map 为device的数据
						//mongodb的数据库名格式  lib_id + "_" + device_id 
						String mongodbName = lib_id + "_" +device_id;
						String esIndexName = mongodbName + "_" + Const.FINANCE_LOG;
						
						FinanceLogEntity financeLogEntity = financeLogService.queryEsLastFinancelog(esIndexName, Const.FINANCE_LOG);
						
						if (financeLogEntity == null || financeLogEntity.getId() == null) {
							//如果没有数据，则从mongodb中获取所有数据
							
							ResultEntity mongodbFinanceLogResult = financeLogService.queryAllFinancelogFromMongodb(mongodbName);
							
							if (mongodbFinanceLogResult.getResult() instanceof List  && !mongodbFinanceLogResult.getResult().equals("[]")) {
								List<FinanceLogEntity> list = JsonUtils.fromJson(JsonUtils.toJson(mongodbFinanceLogResult.getResult()), new TypeReference<List<FinanceLogEntity>>() {});
								for (FinanceLogEntity financeLog : list) {
									financeLog.setLib_id(lib_id);
									financeLog.setLib_name(lib_name);
									financeLog.setLibrary_idx(String.valueOf(libraryEntity.getLibrary_idx()));
									financeLog.setDevice_idx(device_idx);
									financeLog.setDevice_id(device_id);
									financeLog.setDevice_name(device_name);
									financeLog.setDevice_type(device_type);
									financeLog.setDevice_type_desc(device_type_desc);
									financeLog.setDevice_type_mark(device_type_mark);
									financeLog.setAreaCode(areaCode);
									JSONObject r = StatisticsUtils.clazzToMap(financeLog,financeLog.getId());
									financeLogService.saveFinanceLog(esIndexName, Const.FINANCE_LOG.toUpperCase(), r);
								}
							}
							
						}
						
						
						
					}
				}
				
				
			}
			
		}
		System.out.println("mixFinanceLog定时任务,耗时：" + (System.currentTimeMillis()-start)+ "ms");
		return SUCCESS;
	}
}
