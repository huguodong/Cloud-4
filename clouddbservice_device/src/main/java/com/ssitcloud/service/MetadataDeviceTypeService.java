package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.entity.MetadataDeviceTypeAndLogicObjEntity;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月8日
 */
public interface MetadataDeviceTypeService {

	/**
	 * 添加设备类型信息数据
	 * @param metadataDeviceTypeEntity
	 * @return
	 */
	public abstract int addMetadataDeviceType(
			MetadataDeviceTypeEntity metadataDeviceTypeEntity);
	
	/**
	 * 更新设备类型信息数据
	 * @param metadataDeviceTypeEntity
	 * @return
	 */
	public abstract int updMetadataDeviceType(
			MetadataDeviceTypeEntity metadataDeviceTypeEntity);
	
	/**
	 * 删除设备类型信息数据
	 * @param metadataDeviceTypeEntity
	 * @return
	 */
	public abstract int delMetadataDeviceType(
			MetadataDeviceTypeEntity metadataDeviceTypeEntity);
	
	/**
	 * 根据条件查询设备类型信息数据
	 * @param metadataDeviceTypeEntity
	 * @return
	 */
	public abstract List<MetadataDeviceTypeEntity> selbyidMetadataDeviceType(
			MetadataDeviceTypeEntity metadataDeviceTypeEntity);
	
	/*public abstract List<MetadataDeviceTypeEntity> selectType();*/
	/**
	 * 获取所有设备类型
	 *
	 * <p>2016年4月25日 下午3:44:54
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<MetadataDeviceTypeEntity> selAllMetadataDeviceType();
	
	/**
	 * 分页查询设备类型信息数据
	 * @return
	 */
	public abstract PageEntity selPageMetadataDeviceType(Map<String, String> map);
	/**
	 * 获取所有设备类型 按device_type 分组
	 * @author lbh
	 * @return
	 */
	List<MetadataDeviceTypeEntity> selAllDeviceTypeGroupByType();

	public abstract List<MetadataDeviceTypeAndLogicObjEntity> queryDeviceTypeLogicObj(
			String req);

	public abstract MetadataDeviceTypeEntity queryDeviceTypeByName(MetadataDeviceTypeEntity entity);
	
	/**
	 * 通过device_type查出meta_devicetype_idx
	 * <p>2017年3月6日 下午5:57
	 * <p>create by shuangjunjie
	 * @param entity
	 * @return
	 */
	public abstract List<Integer> selectMetaDevTypeIdxByType(MetadataDeviceTypeEntity entity);
	
	/**加载设备类型到redis**/
	void loadDeviceTypeToRedis();

}
