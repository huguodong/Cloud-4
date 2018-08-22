package com.ssitcloud.business.statistics.common.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.common.utils.StatisticsUtils;
import com.ssitcloud.business.statistics.service.BookItemService;
import com.ssitcloud.business.statistics.service.LoanLogService;
import com.ssitcloud.business.statistics.service.StatisticsService;
import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.devmgmt.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.libraryinfo.entity.BookUnionEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.statistics.entity.LoanLogEntity;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

@JobHandler(value="scheduledLoanLog")
@Component
public class ElasticsearchScheduled  extends IJobHandler{
	
	
	@Resource
	private LoanLogService loanLogService;
	
	@Resource
	private StatisticsService statisticsservice;
	@Resource
	private BookItemService bookItemService;
	
	
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	
	
	
	/**
	 * 从mongodb中获取loan_log数据，然后完善读者信息，设备信息，图书馆信息，将这些信息保存到es中
	 * 
	 * <br>=>获取所有图书馆
	 * <br>=>获取图书馆所有设备
	 * <br>=>查询es中是否有设备的日志，有则获取最后一条数据的时间
	 * 
	 * <p>2017年3月17日 下午5:29:17 
	 * <p>create by hjc
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception{
		System.out.println("执行mixloanlog");
		
		/**
		 * 测试
		 */
		System.out.println("mixLoanLog,同步日志开始");
		Long start = System.currentTimeMillis();
		//查询出所有的图书馆
		Map<String, String> cacheLib= JedisUtils.getInstance().hgetAll(RedisConstant.LIBRARY_KEY);
		System.out.println("总馆数："+cacheLib.size());
		if(!MapUtils.isEmpty(cacheLib)){
			for(String libId : cacheLib.keySet()){
				String libStr = cacheLib.get(libId);
				LibraryEntity libraryEntity = null;
				if (!StringUtils.isEmpty(libStr)) {
					libraryEntity = JsonUtils.fromJson(libStr, LibraryEntity.class);
				}
				if(libraryEntity == null) continue;	
				String lib_id = libraryEntity.getLib_id();
				String lib_name = libraryEntity.getLib_name();
				Set<String> deviceIds = JedisUtils.getInstance().smembers(RedisConstant.LIBRARY_DEVICEID + libraryEntity.getLibrary_idx());
				System.out.println("总设备数："+deviceIds.size());
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
						//add by huanghuang 20170825
						Integer devIdx = 0;
						if(StatisticsUtils.strIsNum(device_idx)){
							devIdx = Integer.parseInt(device_idx);
						}
						String areaCode = statisticsservice.retAreaCode(devIdx);
						
						//map 为device的数据
						//mongodb的数据库名格式  lib_id + "_" + device_id 
						String mongodbName = lib_id + "_" +device_id;
						String esIndexName = mongodbName + "_" + Const.LOAN_LOG;
						//先查询es中是不是有这一台设备的loanlog数据，在es中的index名称规则为   lib_id+"_"+device_id+"_loan_log_v1" (有v1的为版本号，一般使用索引的别名 lib_id_device_id_loan_log)
						
						LoanLogEntity loanLogEntity = loanLogService.queryEsLastLoanlog(esIndexName, Const.LOAN_LOG);
						
						String req = "";
//						if (loanLogEntity == null || loanLogEntity.getId() == null) {
						if (loanLogEntity != null && StringUtils.isNotBlank(loanLogEntity.getId())) {
							//获取id之后的数据
							req = "{\"mongodbName\":\""+mongodbName+"\",\"id\":\""+loanLogEntity.getId()+"\"}";
							
							//如果没有数据，则从mongodb中获取所有数据
							//ResultEntity mongodbLoanLogResult = loanLogService.queryAllLoanlogFromMongodb(mongodbName);
							/*
							if (mongodbLoanLogResult.getResult() instanceof List  && !mongodbLoanLogResult.getResult().equals("[]")) {
								List<LoanLogEntity> list = JsonUtils.fromJson(JsonUtils.toJson(mongodbLoanLogResult.getResult()), new TypeReference<List<LoanLogEntity>>() {});
								for (LoanLogEntity loanLog : list) {
									loanLog.setLib_id(lib_id);
									loanLog.setLib_name(lib_name);
									loanLog.setLibrary_idx(String.valueOf(l.getLibrary_idx()));
									loanLog.setDevice_idx(device_idx);
									loanLog.setDevice_id(device_id);
									loanLog.setDevice_name(device_name);
									loanLog.setDevice_type(device_type);
									loanLog.setDevice_type_desc(device_type_desc);
									loanLog.setDevice_type_mark(device_type_mark);
									loanLog.setAreaCode(areaCode);
									JSONObject r = StatisticsUtils.clazzToMap(loanLog,loanLog.getId());
									loanLogService.saveLoanLog(esIndexName, Const.LOAN_LOG.toUpperCase(), r);
								}
							}
							*/
						}else{
							req = "{\"mongodbName\":\""+mongodbName+"\"}";
						}
						ResultEntity mongodbLoanLogResult = loanLogService.queryLoanLogFromMongodb(req);
						if (mongodbLoanLogResult.getResult() instanceof List  && !mongodbLoanLogResult.getResult().equals("[]")) {
							List<LoanLogEntity> list = JsonUtils.fromJson(JsonUtils.toJson(mongodbLoanLogResult.getResult()), new TypeReference<List<LoanLogEntity>>() {});
							for (LoanLogEntity loanLog : list) {
								loanLog.setLib_id(lib_id);
								loanLog.setLib_name(lib_name);
								loanLog.setLibrary_idx(String.valueOf(libraryEntity.getLibrary_idx()));
								loanLog.setDevice_idx(device_idx);
								loanLog.setDevice_id(device_id);
								loanLog.setDevice_name(device_name);
								loanLog.setDevice_type(device_type);
								loanLog.setDevice_type_desc(device_type_desc);
								loanLog.setDevice_type_mark(device_type_mark);
								loanLog.setAreaCode(areaCode);
								//补全索书号
								if(loanLog.getCallno() == null || loanLog.getCallno().length() <= 0){
									BookUnionEntity bookUnionEntity = queryBookItemByUnion(libraryEntity,loanLog);
									if(bookUnionEntity != null){
										loanLog.setCallno(bookUnionEntity.getCallNo());
									} 
								}
								String title =  loanLog.getTitle();
								
								if(!StringUtils.isEmpty(title)){
									title = title.replaceAll(",", "&");
									loanLog.setTitle(title);
								}
								
								JSONObject r = StatisticsUtils.clazzToMap(loanLog,loanLog.getId());
								if(loanLog.getCallno()!=null&&loanLog.getCallno().length()>1){
									r.put("callNumber", loanLog.getCallno().substring(0,1).toUpperCase());
									r.put("callNumber_group", loanLog.getCallno().substring(0,1).toUpperCase());
									String[] callNos = loanLog.getCallno().split("/");//细化到小类（索书号第一个‘/’之前的是分类号）
									if(callNos !=null && callNos.length >0){
										r.put("detailCallNumber", callNos[0]);
										r.put("detailCallNumber_group", callNos[0]);
									}
								}
								
								if(loanLog.getBirth()!=null&&loanLog.getBirth().length() > 4){
									Date date = new Date();
									String curDate = sdf.format(date);
									String birYear = loanLog.getBirth().substring(0,4);
									if(StatisticsUtils.strIsNum(birYear)){
										r.put("peopleAge", String.valueOf(Integer.parseInt(curDate)-Integer.parseInt(birYear)));
									}
								}else{
									r.put("peopleAge", String.valueOf(0));
								}
								loanLogService.saveLoanLog(esIndexName, Const.LOAN_LOG.toUpperCase(), r);
							}
						}
					}
				}
			}
			
		}
		System.out.println("mixloanlog,耗时：" + (System.currentTimeMillis()-start) + "ms");
		return SUCCESS;
	}
	
	
	/**
	 * 补全loan_log中的索书号
	 * @param loanLogEntity
	 * @return
	 */
	private BookUnionEntity queryBookItemByUnion(LibraryEntity libraryEntity,LoanLogEntity loanLogEntity){
		try{
			JsonObject param = new JsonObject();
			String lib_idx = libraryEntity.getLibrary_idx()+"";
			param.addProperty("lib_idx", lib_idx);
			
			String isbn = loanLogEntity.getISBN();
			if(!StringUtils.isBlank(isbn)){
				param.addProperty("ISBN", isbn);
			}else{
				//没有标题，直接返回null
				if(StringUtils.isBlank(loanLogEntity.getTitle())){
					return null;
				}
				param.addProperty( "title",loanLogEntity.getTitle());
				if(!StringUtils.isBlank(loanLogEntity.getAuthor())){
					param.addProperty("author", loanLogEntity.getAuthor());
				}
			}
			ResultEntity entity = bookItemService.queryBookItemByUnion(param.toString());
			if(entity.getState()){
				if(entity.getResult() instanceof List){
					List<BookUnionEntity> bookUnionEntities = JsonUtils.fromJson(JsonUtils.toJson(entity.getResult()),new TypeReference<List<BookUnionEntity>>(){});
					if(!bookUnionEntities.isEmpty()){
						return bookUnionEntities.get(0);
					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
