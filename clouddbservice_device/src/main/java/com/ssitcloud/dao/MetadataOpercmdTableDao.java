package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.MetadataOpercmdTableEntity;

public interface MetadataOpercmdTableDao extends CommonDao{
	
	/**
	 * 查询所有的数据
	 *
	 * <p>2017年8月29日 上午9:21:41 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<MetadataOpercmdTableEntity> queryAllOpercmdTable();

}
