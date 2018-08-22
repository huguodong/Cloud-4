package com.ssitcloud.business.statistics.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ssitcloud.business.statistics.common.utils.StatisticsUtils;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.common.utils.HttpClientUtil;
import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.entity.BookItemEntity;
import com.ssitcloud.business.statistics.service.BookItemService;
import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.devmgmt.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

@Service
public class BookItemServiceImpl extends BasicServiceImpl implements BookItemService{
	
	private static final String URL_GETALLBOOKITEM = "queryBookItemAndBibliosInfo";
	
	private static final String URL_SELREGIONBYDEVICEIDX = "selRegionByDeviceidx";
	
	private static final String URL_QUERYBOOKITEMBYUNION = "queryBookItemByUnion";
	

	@Override
	public ResultEntity getAllBookItem() {
		ResultEntity resultEntity = new ResultEntity();
		
		//先从对应的es表中获取最大的记录号
		
		getBookItems();
		
		return resultEntity;
	}
	
	
	public void getBookItems(){
		String bookitem_idx = JedisUtils.getInstance().get(RedisConstant.ES_BOOKITEM_IDX);
		Map<String, String> param = new HashMap<>();
		String idx = "{\"bookitem_idx\":\"0\"}";
		if (!StringUtils.isBlank(bookitem_idx)) {
			idx = "{\"bookitem_idx\":\""+bookitem_idx+"\"}";
		}
		param.put("req", idx);
		
		String bookitemResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLBOOKITEM), param, Consts.UTF_8.toString());
		if(StringUtils.isNotBlank(bookitemResult)){
			ResultEntity bookitemResultEntity = JsonUtils.fromJson(bookitemResult, ResultEntity.class);
			try {
				//保存现有的最大的idx
				if(!StringUtils.isEmpty(bookitemResultEntity.getMessage())){
					if(Integer.parseInt(bookitemResultEntity.getMessage())> 0){
						
						JedisUtils.getInstance().set(RedisConstant.ES_BOOKITEM_IDX, bookitemResultEntity.getMessage());
					}
					
				}
				
				if (bookitemResultEntity.getResult() instanceof List) {
					List<BookItemEntity> list = JsonUtils.fromJson(JsonUtils.toJson(bookitemResultEntity.getResult()), new TypeReference<List<BookItemEntity>>(){});
					for (BookItemEntity bookItemEntity : list) {
						try {
							StringBuffer device_idx = new StringBuffer();
							String libId = "";
							String devId = "";
							
							//设备ID可能会为空 或者为0 的话则没有数据
							if (StringUtils.isNotBlank(bookItemEntity.getDevice_idx()) && !"0".equals(bookItemEntity.getDevice_idx())) {
								device_idx.append(bookItemEntity.getDevice_idx());
								String device_id = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+bookItemEntity.getDevice_idx());
								DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+device_id,DeviceEntity.class);
								if (deviceEntity == null ) {
									continue;
								}
								
								MetadataDeviceTypeEntity typeEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE_TYPE+deviceEntity.getDevice_model_idx()
								,MetadataDeviceTypeEntity.class);
								device_id = deviceEntity.getDevice_id()==null ? "" : deviceEntity.getDevice_id();
								String device_name = deviceEntity.getDevice_name()==null ? "" : deviceEntity.getDevice_name();
								String device_type = typeEntity.getDevice_type()==null ? "" : typeEntity.getDevice_type();
								String device_type_desc = typeEntity.getDevice_type_desc()==null ? "" : typeEntity.getDevice_type_desc();
								String device_type_mark = typeEntity.getDevice_type_mark()==null ? "" : typeEntity.getDevice_type_mark();
								
								devId = device_id;
								bookItemEntity.setDevice_id(device_id);
								bookItemEntity.setDevice_name(device_name);
								bookItemEntity.setDevice_type(device_type);
								bookItemEntity.setDevice_type_desc(device_type_desc);
								bookItemEntity.setDevice_type_mark(device_type_mark);
								//add by huanghuang 20170825
								String areaCode = "";
								if(StatisticsUtils.strIsNum(device_idx.toString().trim())){
									areaCode = retAreaCode(Integer.parseInt(device_idx.toString().trim()));
								}
								bookItemEntity.setAreaCode(areaCode);
							}else {
								devId = "Other";//如果没有设备ID 保存到Other分类中
							}
							
							//所属馆数据
							if (StringUtils.isNotBlank(bookItemEntity.getLib_idx())) {
								String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+bookItemEntity.getLib_idx());
								LibraryEntity entity = JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
								if (entity == null) {
									continue;
								}
								
								libId = entity.getLib_id();
								bookItemEntity.setLib_id(entity.getLib_id());
								bookItemEntity.setLib_name(entity.getLib_name());
							}else{
								//如果所属馆没有数据，则跳过，es明明需要libid，一般不会有这种情况
								continue;
							}
							bookItemEntity.setLibrary_idx(bookItemEntity.getLib_idx());
							//当前馆数据
							if (StringUtils.isNotBlank(bookItemEntity.getNowlib_idx())) {
								String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+bookItemEntity.getNowlib_idx());
								LibraryEntity entity = JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
								if (entity != null) {
									LibraryEntity nowLibraryEntity = entity;
									
									bookItemEntity.setNowlib_id(nowLibraryEntity.getLib_id());
									bookItemEntity.setNowlib_name(nowLibraryEntity.getLib_name());
								}
							}
							
							//服务馆数据
							if (StringUtils.isNotBlank(bookItemEntity.getServerlib_idx())) {
								String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+bookItemEntity.getNowlib_idx());
								LibraryEntity entity = JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
								if (entity != null) {
									LibraryEntity serverLibraryEntity = entity;
									
									bookItemEntity.setServerlib_id(serverLibraryEntity.getLib_id());
									bookItemEntity.setServerlib_name(serverLibraryEntity.getLib_name());
								}
							}
							
							String esIndexName = libId + "_" + devId + "_" + Const.BOOKITEM ;
							
							//判断索引是不是存在
							boolean isExisted = checkIndexExisted(esIndexName, Const.BOOKITEM);
							if(isExisted){
								//保存数据到es
                                JSONObject r = StatisticsUtils.clazzToMap(bookItemEntity,bookItemEntity.getBookitem_idx());
                                if(bookItemEntity.getCallNo()!=null&&bookItemEntity.getCallNo().length()>1){
									r.put("callNumber", bookItemEntity.getCallNo().substring(0,1).toUpperCase());
								}
								saveOrUpdateDocument(esIndexName, Const.BOOKITEM.toUpperCase(),r);
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
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


	@Override
	public ResultEntity queryBookItemByUnion(String req) {
		return postURL(URL_QUERYBOOKITEMBYUNION,req);
	}
	
	
	
	

}
