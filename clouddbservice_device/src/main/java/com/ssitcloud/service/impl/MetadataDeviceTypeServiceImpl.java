package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.MetadataDeviceTypeDao;
import com.ssitcloud.dao.MetadataLogicObjDao;
import com.ssitcloud.entity.MetadataDeviceTypeAndLogicObjEntity;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.entity.MetadataLogicObjEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.service.MetadataDeviceTypeService;
/**
 * 
 * @comment 设备类型描述元数据表Service
 * 
 * @author hwl
 * @data 2016年4月8日
 */
@Service
public class MetadataDeviceTypeServiceImpl implements MetadataDeviceTypeService {

	@Resource
	MetadataDeviceTypeDao metadataDeviceTypeDao;
	
	@Resource
	private MetadataLogicObjDao metadataLogicObjDao;
	
	@Override
	public int addMetadataDeviceType(
			MetadataDeviceTypeEntity metadataDeviceTypeEntity) {
		int inc = 0;
		try {
			 inc=metadataDeviceTypeDao.insert(metadataDeviceTypeEntity);
			 JedisUtils.getInstance().set(RedisConstant.DEVICE_TYPE+metadataDeviceTypeEntity.getMeta_devicetype_idx()
				, JsonUtils.toJson(metadataDeviceTypeEntity));
		} catch (org.springframework.dao.DuplicateKeyException e) {
			String msg=e.getCause().getMessage();
			System.out.println(msg);
			if(org.apache.commons.lang.StringUtils.contains(msg, "Duplicate entry")
					&& org.apache.commons.lang.StringUtils.contains(msg, "device_type")
					){
				throw new RuntimeException("设备类型名称已经被占用，请修改");
			}else if(org.apache.commons.lang.StringUtils.contains(msg, "Duplicate entry")
					&& org.apache.commons.lang.StringUtils.contains(msg, "device_type_desc")){
				throw new RuntimeException("描述已经被占用，请修改");
			}else{
				throw new RuntimeException(e);
			}
			
		}
		return inc;
	}

	@Override
	public int updMetadataDeviceType(
			MetadataDeviceTypeEntity metadataDeviceTypeEntity) {
		// TODO Auto-generated method stub
		int result = metadataDeviceTypeDao.update(metadataDeviceTypeEntity);
		JedisUtils.getInstance().set(RedisConstant.DEVICE_TYPE+metadataDeviceTypeEntity.getMeta_devicetype_idx()
		, JsonUtils.toJson(metadataDeviceTypeEntity));
		return result;
	}

	@Override
	public int delMetadataDeviceType(
			MetadataDeviceTypeEntity metadataDeviceTypeEntity) {
		// TODO Auto-generated method stub
		int result = metadataDeviceTypeDao.delete(metadataDeviceTypeEntity);
		JedisUtils.getInstance().del(RedisConstant.DEVICE_TYPE+metadataDeviceTypeEntity.getMeta_devicetype_idx());
		return result;
	}

	@Override
	public List<MetadataDeviceTypeEntity> selbyidMetadataDeviceType(
			MetadataDeviceTypeEntity metadataDeviceTypeEntity) {
		List<MetadataDeviceTypeEntity> metadataDeviceTypes=metadataDeviceTypeDao.selectByid(metadataDeviceTypeEntity);
		
		return metadataDeviceTypes;
	}

	@Override
	public List<MetadataDeviceTypeEntity> selAllMetadataDeviceType() {
		return metadataDeviceTypeDao.selAllMetadataDeviceType();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageEntity selPageMetadataDeviceType(
			Map<String, String> map) {
		PageEntity p=metadataDeviceTypeDao.selectPage(map);
		List<MetadataDeviceTypeEntity> metadataDeviceTypes=p.getRows();
		if(CollectionUtils.isNotEmpty(metadataDeviceTypes)){
			for(MetadataDeviceTypeEntity metadataDeviceType:metadataDeviceTypes){
				String deviceLogicList=metadataDeviceType.getDevice_logic_list();
				if(StringUtils.hasLength(deviceLogicList)){
					String[] logicObjArr=StringUtils.commaDelimitedListToStringArray(deviceLogicList);
					List<MetadataLogicObjEntity> metadataLogicObjs=metadataLogicObjDao.selectInlogicObjArr(Arrays.asList(logicObjArr));
					StringBuilder sb=new StringBuilder();
					for(MetadataLogicObjEntity metadataLogicObj:metadataLogicObjs){
						sb.append(metadataLogicObj.getLogic_obj_desc()).append(",");
					}
					metadataDeviceType.setDevice_logic_list_desc(sb.deleteCharAt(sb.length()-1).toString());
				}
			}
		}
		return p;
	}

	/*@Override
	public List<MetadataDeviceTypeEntity> selectType() {
		// TODO Auto-generated method stub
		return metadataDeviceTypeDao.selectType();
	}*/
	@Override
	public List<MetadataDeviceTypeEntity> selAllDeviceTypeGroupByType(){
		return metadataDeviceTypeDao.selAllDeviceTypeGroupByType();
	}

	@Override
	public List<MetadataDeviceTypeAndLogicObjEntity> queryDeviceTypeLogicObj(String req) {
		
		List<MetadataDeviceTypeAndLogicObjEntity> metadataDeviceTypeAndLogicObjs=new ArrayList<>();
		List<MetadataDeviceTypeEntity> metadataDeviceTypes=metadataDeviceTypeDao.selAllDeviceTypeGroupByType();
		if(CollectionUtils.isNotEmpty(metadataDeviceTypes)){
			for(MetadataDeviceTypeEntity metadataDeviceType:metadataDeviceTypes){
				MetadataDeviceTypeAndLogicObjEntity metadataDeviceTypeAndLogicObj=new MetadataDeviceTypeAndLogicObjEntity();
				
				metadataDeviceTypeAndLogicObj.setMetadataDeviceType(metadataDeviceType);
				String deviceLogicList=metadataDeviceType.getDevice_logic_list();
				if(StringUtils.hasText(deviceLogicList)){
					String[] logicObjArr=StringUtils.commaDelimitedListToStringArray(deviceLogicList);
					if(logicObjArr!=null&&logicObjArr.length>0){
						List<MetadataLogicObjEntity> metadataLogicObjs=metadataLogicObjDao.selectInlogicObjArr(Arrays.asList(logicObjArr));
						metadataDeviceTypeAndLogicObj.setMetadataLogicObjs(metadataLogicObjs);
					}
				}
				metadataDeviceTypeAndLogicObjs.add(metadataDeviceTypeAndLogicObj);
			}
		}
		
		return metadataDeviceTypeAndLogicObjs;
	}

	@Override
	public MetadataDeviceTypeEntity queryDeviceTypeByName(MetadataDeviceTypeEntity entity) {
		return metadataDeviceTypeDao.queryDeviceTypeByName(entity);
	}

	@Override
	public List<Integer> selectMetaDevTypeIdxByType(MetadataDeviceTypeEntity entity) {
		return metadataDeviceTypeDao.selectMetaDevTypeIdxByType(entity);
	}
	
	public List<MetadataDeviceTypeEntity> selectAllDeviceType(){
		return metadataDeviceTypeDao.selAllMetadataDeviceType();
		
	}
	
	/**
	 * 将设备类型缓存到redis
	 */
	public void loadDeviceTypeToRedis(){
		
		List<MetadataDeviceTypeEntity> entities = metadataDeviceTypeDao.selectAllDeviceType();
		for(MetadataDeviceTypeEntity deviceTypeEntity : entities){
			JedisUtils.getInstance().set(RedisConstant.DEVICE_TYPE+deviceTypeEntity.getMeta_devicetype_idx()
			, JsonUtils.toJson(deviceTypeEntity));
		}
		
	}
	

}
