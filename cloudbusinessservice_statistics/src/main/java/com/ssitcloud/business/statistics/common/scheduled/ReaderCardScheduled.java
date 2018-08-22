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
import com.ssitcloud.business.statistics.service.CommonEsStatisticService;
import com.ssitcloud.business.statistics.service.LoanLogService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;
import com.ssitcloud.readermgmt.entity.CollegeInfoEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.statistics.entity.LoanLogEntity;
import com.ssitcloud.statistics.entity.ReaderCirculationEntity;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;


@JobHandler(value="changeReaderCardScheduled")
@Component
public class ReaderCardScheduled  extends IJobHandler{
	
	@Resource
	private LoanLogService loanLogService;
	
	@Resource
	private CommonEsStatisticService commonEsService;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 补全图书馆的读者证卡号
	 * 图书馆读者证丢失更换卡后，卡号发生改变，需要补全
	 * 补全读者学籍信息
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception{
		System.out.println("执行ReaderCardScheduled定时任务开始");
		Long start = System.currentTimeMillis();
		//查询出所有的图书馆
		Map<String, String> map = JedisUtils.getInstance().hgetAll(RedisConstant.LIBRARY_KEY);
		if(!MapUtils.isEmpty(map)){
			for(String key : map.keySet()){
				String libStr = map.get(key);
				
				if (libStr == null || libStr.length() <= 0) continue;
				LibraryEntity libEntity = JsonUtils.fromJson(libStr, LibraryEntity.class);
				//获取图书馆的所有设备
				//List<Map<String,Object>> devices = libEntity.getDeviceList();
				Set<String> deviceIdList = JedisUtils.getInstance().smembers(RedisConstant.LIBRARY_DEVICEID+libEntity.getLibrary_idx());
				//循环处理
				for(String deviceId : deviceIdList){
					//String deviceId = device.get("device_id").toString();//设备id
					if(StringUtils.isBlank(deviceId) || StringUtils.isBlank(libEntity.getLib_id())) continue;
					//mongodb数据库名称格式：lib_id +"_"+device_id
					String mongodbName = getMongoName(libEntity.getLib_id(),deviceId);
					String id = null;
					//查询es最后一条数据获取id
					ReaderCirculationEntity circulationEntity = loanLogService.queryReaderEsLastLoanlog(getIndex(libEntity.getLib_id(),deviceId), getType(), new ReaderCirculationEntity() );
					if(circulationEntity != null && !StringUtils.isBlank(circulationEntity.getId())){
						id = circulationEntity.getId();
					}
					//获取mongodb数据
					ResultEntity mongodbEntity = queryLoadLogFromMongodb(mongodbName,id,2000);
					//处理数据mongodb数据
					dealMongodbData(mongodbEntity,libEntity,deviceId);
					
				}
			}
		}
		System.out.println("执行ReaderCardScheduled定时任务,耗时：" + (System.currentTimeMillis() - start ) + "ms");
		return SUCCESS;
	}
	
	
	/***
	 * 处理数据
	 * @param resultEntity
	 * @param cardTemplate
	 * @return
	 */
	private void dealMongodbData(ResultEntity mongodbResultEntity,LibraryEntity libEntity,String device_id){
		
		if(mongodbResultEntity == null || !mongodbResultEntity.getState()) return;
		try{
			if(mongodbResultEntity.getResult() instanceof List){
				List<LoanLogEntity> loanLogEntities = JsonUtils.fromJson(JsonUtils.toJson(mongodbResultEntity.getResult())
				,new TypeReference<List<LoanLogEntity>>(){});
				//查询mysql数据库，获取学籍相关数据，补全学籍相关数据
				//如果查询没有数据，且图书馆是兰州大学，则先插入一条数据再补全es数据
				for(LoanLogEntity loanLogEntity : loanLogEntities){
					//处理读者卡信息
					ReaderCardEntity cardEntity = dealLibReaderCardinfo(loanLogEntity,libEntity);
					//查询读者学籍信息
					CollegeInfoEntity collegeInfoEntity = queryCollegeInfo(cardEntity,libEntity);
					ReaderCirculationEntity circulationEntity = fillLoanLogToReaderCirculation(loanLogEntity,collegeInfoEntity,libEntity,cardEntity);
					//往es插入数据
					insertEsLoadLog(circulationEntity, libEntity,device_id);
				}
			}
		}catch(Exception e){
			System.out.println("报错了");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 1、获取读者证信息
	 *
	 * 2、处理兰州大学的读者证.
	 * 读者证号最后一位是学生更换证的次数，同一个读者可能更换多次，第一次的证号结尾都是1，后续累计为2、3……..。
	 * 根据mongdb数据读者证号再结合对应图书馆读者证提取规则，提取出实际读者证，与MySQL中实际读者证匹配，
	 * 如实际读者证相同且读者证不同则在MySQL新增一条数据，记录最新读者证号码
	 * 
	 * 3、暂时不支持配置读者证规则
	 * @param readerCards
	 */
	public ReaderCardEntity dealLibReaderCardinfo(LoanLogEntity loanLogEntity,LibraryEntity libEntity){
		
		ResultEntity readerResultEntity = null;
		if(!StringUtils.isBlank(loanLogEntity.getAuthCardno())){
			//查询readercard表，如果cardno不存在则插入
			String actual_card_no = loanLogEntity.getAuthCardno();
			if("LZULIB".equals(libEntity.getLib_id())){//兰州大学的实际卡号 = 卡号除去最后一位
				actual_card_no = actual_card_no.substring(0, actual_card_no.length()-1);
			}
			JSONObject params = new JSONObject();
			params.put("actual_card_no",actual_card_no);
			params.put("lib_idx",libEntity.getLibrary_idx());
			//向mysql数据库查询读者卡信息
			readerResultEntity = loanLogService.selectReaderCardByParams(params.toString());
		}
		if(readerResultEntity != null && readerResultEntity.getState()){
			Object object = readerResultEntity.getResult();
			if(object != null && object instanceof List){
				String jsonObject = JsonUtils.toJson(object);
				List<ReaderCardEntity> readerCards = JsonUtils.fromJson(jsonObject, new TypeReference<List<ReaderCardEntity>>(){});
				if(readerCards == null || readerCards.isEmpty()) return new ReaderCardEntity();
				if("LZULIB".equals(libEntity.getLib_id())){//暂时写死 TODO
					int tag = 0;
					for(ReaderCardEntity readerCard : readerCards){
						if(!readerCard.getCard_no().equals(loanLogEntity.getAuthCardno())){
							tag +=1;
						}
					}
					if(tag != 0 && tag == readerCards.size()){//存在换卡的情况,插入该条数据
						
						ReaderCardEntity readerCard = readerCards.get(0);
						readerCard.setCard_no(loanLogEntity.getAuthCardno());
						//插入一条数据
						loanLogService.insertReaderCard(JsonUtils.toJson(readerCard));
						return readerCard;
					}
				}
				return readerCards.size() > 0?readerCards.get(0):new ReaderCardEntity();
			}
		}
		return new ReaderCardEntity();
	}
	
	
	/**
	 * 查询学籍
	 * @param actual_card_no
	 * @return
	 */
	public CollegeInfoEntity queryCollegeInfo(ReaderCardEntity cardEntity,LibraryEntity libraryEntity){
		
		if(cardEntity == null || StringUtils.isBlank(cardEntity.getActual_card_no())){
			return new CollegeInfoEntity();
		}
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("actual_card_no", cardEntity.getActual_card_no());
		jsonObject.addProperty("lib_idx", libraryEntity.getLibrary_idx());
		ResultEntity resultEntity = loanLogService.queryCollegeInfo(jsonObject.toString());
		if(resultEntity.getState()){
			if(resultEntity.getResult() instanceof List){
				List<CollegeInfoEntity> collegeInfoEntities = JsonUtils.fromJson(JsonUtils.toJson(resultEntity.getResult())
						, new TypeReference<List<CollegeInfoEntity>>() {});
				if(!collegeInfoEntities.isEmpty()){
					return collegeInfoEntities.get(0);
				}
			}
		}
		return new CollegeInfoEntity();
	}
	
	
	/***
	* 向mongodb查询数据
	* @param mongodbName 数据库名称
	* @param id 每次查询返回数据的最后一条的id
	* @param limit  每次返回的数据大小
	* @return
	*/
	public ResultEntity queryLoadLogFromMongodb(String mongodbName,String id,int limit){
		//组装参数
		JsonObject params = new JsonObject();
		params.addProperty("limit", limit);
		params.addProperty("id", id);//如果id为空，表示第一次查询mongodb
		params.addProperty("mongodbName", mongodbName);
		return loanLogService.queryLoadLogFromMondbByPage(params.toString());
	}
	
	
	/***
	 * 向es插入数据
	 * @param loanLogEntity
	 * @param libraryEntity
	 * @param cardEnitty
	 * @param collegeInfoEntity
	 */
	public void insertEsLoadLog(ReaderCirculationEntity circulationEntitie,LibraryEntity libraryEntity,String device_id){
		
		
	
		circulationEntitie.setCount(1);
		JSONObject jsonObject = StatisticsUtils.clazzToMap(circulationEntitie, circulationEntitie.getId());
		String lib_id = libraryEntity.getLib_id().toLowerCase();
		String index = getIndex(lib_id,device_id);
		commonEsService.saveOrUpdateDocument(index,getType(),jsonObject);
		
	}
	
	
	/**将loanlog中的数据填充到ReaderCirculation中*/
	private ReaderCirculationEntity fillLoanLogToReaderCirculation(LoanLogEntity loanLogEntity,
			CollegeInfoEntity collegeInfoEntity,LibraryEntity libraryEntity,ReaderCardEntity readerCardEnitty){
		ReaderCirculationEntity circulationEntity = new ReaderCirculationEntity();
		String day = dateFormat.format(new Date());
		
		if(readerCardEnitty != null && StringUtils.isBlank(readerCardEnitty.getActual_card_no())){
			circulationEntity.setId(readerCardEnitty.getActual_card_no());
		}else{
			circulationEntity.setId(loanLogEntity.getId());
		}
		circulationEntity.setId(loanLogEntity.getId());
		circulationEntity.setValidAuthcardNo(loanLogEntity.getAuthCardno());
		circulationEntity.setReader_name(loanLogEntity.getName());
		circulationEntity.setReader_sex(loanLogEntity.getGender());
		circulationEntity.setAddress(loanLogEntity.getAddress());
		if(readerCardEnitty.getBirth() != null){
			try{
				circulationEntity.setBirth_date(dateFormat.format(readerCardEnitty.getBirth()));
			}catch(Exception e){
				circulationEntity.setBirth_date(day);
			}
		}
		circulationEntity.setArea(loanLogEntity.getCurrentLocation());
		circulationEntity.setGrade(collegeInfoEntity == null ? "" : collegeInfoEntity.getGrade());
		circulationEntity.setEducation(collegeInfoEntity == null ? "" : collegeInfoEntity.getEducation());
		circulationEntity.setProfession(collegeInfoEntity == null ? "" : collegeInfoEntity.getProfession());
		circulationEntity.setAcademy(collegeInfoEntity == null ? "" : collegeInfoEntity.getAcademy());
		circulationEntity.setDepartment(collegeInfoEntity == null ? "" : collegeInfoEntity.getDepartment());
		circulationEntity.setSchoolingLength(collegeInfoEntity == null ? "": collegeInfoEntity.getSchoolingLength());
		
		if(collegeInfoEntity.getAdmission_date()!= null){
			try{
				circulationEntity.setAdmission_date(dateFormat.format(collegeInfoEntity.getAdmission_date()));
			}catch(Exception e){
				circulationEntity.setBirth_date(day);
			}
		}else{
			circulationEntity.setAdmission_date(day);
		}
		
		circulationEntity.setLib_idx(libraryEntity.getLibrary_idx()+"");
		circulationEntity.setLib_id(libraryEntity.getLib_id());
		circulationEntity.setLib_name(libraryEntity.getLib_name());
		circulationEntity.setOpercmd(loanLogEntity.getOpercmd());
		circulationEntity.setOpertime(loanLogEntity.getOpertime());
		circulationEntity.setReaderType(collegeInfoEntity == null ?"" : collegeInfoEntity.getReader_type());
		circulationEntity.setLibrary_idx(libraryEntity.getLibrary_idx()+"");
		return circulationEntity;
	}
	
	/**
	 * 获取mongodb名称
	 * @param lib_id 图书馆id
	 * @param device_id 设备id
	 * @return
	 */
	private String getMongoName(String lib_id,String device_id){
		return lib_id+"_"+device_id;
	}
	
	/**
	 * 获取es索引
	 * @param lib_id 图书馆id
	 * @return
	 */
	private String getIndex(String lib_id,String device_id){
		return lib_id.toLowerCase()+"_"+device_id.toLowerCase()+"_reader_circulation_log";
	}
	
	private String getType(){
		return "CIRCULATION_LOG";
	}
	
}
