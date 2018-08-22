package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月8日
 */
public interface MetadataDeviceTypeDao extends CommonDao {

	public abstract int insert(MetadataDeviceTypeEntity metadataDeviceTypeEntity);

	public abstract int update(MetadataDeviceTypeEntity metadataDeviceTypeEntity);

	public abstract int delete(MetadataDeviceTypeEntity metadataDeviceTypeEntity);

	public abstract List<MetadataDeviceTypeEntity> selectByid(
			MetadataDeviceTypeEntity metadataDeviceTypeEntity);
	
	public abstract MetadataDeviceTypeEntity selOneByIdx(MetadataDeviceTypeEntity metadataDeviceTypeEntity);
	
	/*public abstract List<MetadataDeviceTypeEntity> selectType();*/
	/**
	 * 获取所有设备类型
	 *
	 * <p>2016年4月25日 下午3:44:54
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<MetadataDeviceTypeEntity> selAllMetadataDeviceType();
	
	public abstract PageEntity selectPage(Map<String, String> map);
	/**
	 * 获取所有设备类型 按 device_type分组
	 * @author lbh
	 * @return
	 */
	List<MetadataDeviceTypeEntity> selAllDeviceTypeGroupByType();

	int updateByMap(Map<String, Object> param);
	/**
	 * 
	 * @param m
	 * @return
	 */
	public abstract int insertWithIdx(Map<String, Object> m);

	public abstract MetadataDeviceTypeEntity queryDeviceTypeByName(MetadataDeviceTypeEntity entity);
	
	/**
	 * 通过device_type查出meta_devicetype_idx
	 * 
	 * <p>2017年3月6日 下午5:53
	 * <p>create by shuangjunjie
	 * @param entity
	 * @return
	 */
	public abstract List<Integer> selectMetaDevTypeIdxByType(MetadataDeviceTypeEntity entity);
	
	public List<MetadataDeviceTypeEntity> selectAllDeviceType();
}
