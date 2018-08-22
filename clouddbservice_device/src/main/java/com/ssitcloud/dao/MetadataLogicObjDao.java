package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.MetadataLogicObjEntity;
import com.ssitcloud.system.entity.MetadataLogicObjRemoteEntity;


/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月11日
 */
public interface MetadataLogicObjDao extends CommonDao {
	
	public abstract int insert(MetadataLogicObjEntity metadataLogicObjEntity);
	
	public abstract int insertMapWithIdx(Map<String,Object> metadataLogicObjEntity);
	
	public abstract int delete(MetadataLogicObjEntity metadataLogicObjEntity);

	public abstract int update(MetadataLogicObjEntity metadataLogicObjEntity);

	public abstract List<MetadataLogicObjEntity> select(
			MetadataLogicObjEntity metadataLogicObjEntity);
	/**
	 * 数据同步用
	 * 
	 * <p>2016年6月24日 下午2:12:20
	 * <p>create by lbh
	 * @param deviceExtConfig
	 * @return
	 */
	List<MetadataLogicObjRemoteEntity> selectListByDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig);
    /**
     * 根据logic_obj字段取得对应的对象
     * @param asList
     * @author lbh
     * @return
     */
	public abstract List<MetadataLogicObjEntity> selectInlogicObjArr(List<String> asList);
	/**
	 * 根据IDX查询
	 * @param metadataLogicObjEntity
	 * @return
	 */
	public abstract com.ssitcloud.entity.MetadataLogicObjEntity selOneByIdx(
			MetadataLogicObjEntity metadataLogicObjEntity);

	public abstract int updateByMap(Map<String, Object> m);

}
