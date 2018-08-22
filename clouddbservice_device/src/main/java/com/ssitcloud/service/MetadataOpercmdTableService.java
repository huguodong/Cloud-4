package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.MetadataOpercmdTableEntity;

public interface MetadataOpercmdTableService {
	
	/**
	 * 查询所有的数据
	 *
	 * <p>2017年8月29日 上午9:21:41 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<MetadataOpercmdTableEntity> queryAllOpercmdTable();
	
	/** 加载opercmd_table中的数据到redis*/
	void loadCMDTableToRedis();

}
