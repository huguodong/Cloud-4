package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.MetadataOrderEntity;
import com.ssitcloud.system.entity.MetadataOrderRemoteEntity;
/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月9日
 */
public interface MetadataOrderDao extends CommonDao {

	public abstract int insert(MetadataOrderEntity metadataOrderEntity);

	public abstract int delete(MetadataOrderEntity metadataOrderEntity);

	public abstract int update(MetadataOrderEntity metadataOrderEntity);

	public abstract List<MetadataOrderEntity> select(MetadataOrderEntity metadataOrderEntity);

	/**
	 * 设备端获取要下载的表信息
	 * 
	 * 
	 * <p>2016年6月25日 上午9:25:58
	 * <p>create by lbh
	 * @param metadataOrderEntity
	 * @return
	 */
	List<MetadataOrderRemoteEntity> selectByDown(MetadataOrderEntity metadataOrderEntity);

}
