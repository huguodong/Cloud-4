package com.ssitcloud.business.statistics.common.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.ssitcloud.statistics.entity.CardissueLogEntity;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.codehaus.jackson.type.TypeReference;






import org.springframework.stereotype.Component;

import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.common.utils.StatisticsUtils;
import com.ssitcloud.business.statistics.service.CardissueLogService;
import com.ssitcloud.business.statistics.service.StatisticsService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

@JobHandler(value="elasticsearchCardissueLogScheduled")
@Component
public class ElasticsearchCardissueLogScheduled  extends IJobHandler{
	
	
	@Resource
	private CardissueLogService cardissueLogService;
	
	@Resource
	private StatisticsService statisticsservice;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	
		
	/**
	 * 从mongodb中获取cardissue_log数据，然后完善读者信息，设备信息，图书馆信息，将这些信息保存到es中
	 * 
	 * <br>=>获取所有图书馆
	 * <br>=>获取图书馆所有设备
	 * <br>=>查询es中是否有设备的日志，有则获取最后一条数据的时间
	 * 
	 * <p>2017年5月12日 
	 * <p>create by lqw
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception{
		System.out.println("mixCardissueLog定时任务开始");
		Long start = System.currentTimeMillis();
		Map<String, String> libMap= JedisUtils.getInstance().hgetAll(RedisConstant.LIBRARY_KEY);
		if(!MapUtils.isEmpty(libMap)){
			for(String key : libMap.keySet()){
				String libStr = libMap.get(key);
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
						String mongodbName = lib_id + "_" + device_id;
						String esIndexName = mongodbName + "_" + Const.CARDISSUE_LOG;;
						//先查询es中是不是有这一台设备的cardissuelog数据，在es中的index名称规则为   lib_id+"_"+device_id+"_cardissue_log_v1" (有v1的为版本号，一般使用索引的别名 lib_id_device_id_cardissue_log)
						
						CardissueLogEntity cardissueLogEntity = cardissueLogService.queryEsLastCardissuelog(esIndexName, Const.CARDISSUE_LOG);
						
						if (cardissueLogEntity == null || cardissueLogEntity.getId() == null) {
							//如果没有数据，则从mongodb中获取所有数据
							
							ResultEntity mongodbCardissueLogResult = cardissueLogService.queryAllCardissuelogFromMongodb(mongodbName);
							
							if (mongodbCardissueLogResult.getResult() instanceof List  && !mongodbCardissueLogResult.getResult().equals("[]")) {
								List<CardissueLogEntity> list = JsonUtils.fromJson(JsonUtils.toJson(mongodbCardissueLogResult.getResult()), new TypeReference<List<CardissueLogEntity>>() {});
								for (CardissueLogEntity cardissueLog : list) {
									cardissueLog.setLib_id(lib_id);
									cardissueLog.setLib_name(lib_name);
									cardissueLog.setLibrary_idx(String.valueOf(libraryEntity.getLibrary_idx()));
									cardissueLog.setDevice_idx(device_idx);
									cardissueLog.setDevice_id(device_id);
									cardissueLog.setDevice_name(device_name);
									cardissueLog.setDevice_type(device_type);
									cardissueLog.setDevice_type_desc(device_type_desc);
									cardissueLog.setDevice_type_mark(device_type_mark);
									cardissueLog.setAreaCode(areaCode);
									JSONObject r = StatisticsUtils.clazzToMap(cardissueLog,cardissueLog.getId());
									if(cardissueLog.getBirth()!=null&&cardissueLog.getBirth().length()>4){
										Date date = new Date();
										String curDate = sdf.format(date);
										String birYear = cardissueLog.getBirth().substring(0,4);
										if(StatisticsUtils.strIsNum(birYear)){
											r.put("peopleAge", String.valueOf(Integer.parseInt(curDate)-Integer.parseInt(birYear)));
										}
									}else{
										r.put("peopleAge", String.valueOf(0));
									}
									cardissueLogService.saveCardissueLog(esIndexName, Const.CARDISSUE_LOG.toUpperCase(), r);
									
								}
							}
							
						}
						
						
						
					}
				}
				
				
			}
			
		}
		System.out.println("mixCardissueLog定时任务耗时：" + (System.currentTimeMillis()-start) + "ms");
		return SUCCESS;
	}
}
