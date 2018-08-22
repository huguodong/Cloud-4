package com.ssitcloud.business.mobile.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.RegionEntity;

/**
 * 地区服务
 * @author LXP
 *
 */
public interface RegionService {
	
	
	/**
	 * 区域Region实体单个查询
	 * @param regionEntity
	 * @return
	 */
	public abstract ResultEntity selRegionByRegionIdx(RegionEntity regionEntity);
	
	/**
	 * 区域Region实体多个查询
	 * @param regionEntity
	 * @return
	 */
	ResultEntity selRegionsByRegionCode(RegionEntity regionEntity);

	/**
	 * 区域Region实体多个查询,根据地区码查询地区码符合及其地区码下级地区
	 * @param regionEntity
	 * @return
	 */
	ResultEntity selRegionsOnRegionCode(RegionEntity regionEntity);
	
	/**
	 * 区域RegionEntity多个查询 可查所有省、省对应的市、对应的区
	 * 根据传入参数的长度来查不同的实体，长度为0时查出所有省，长度为4时查出对应市，长度为6时查出对应区
	 * @param regionEntity
	 * @return
	 */
	ResultEntity selProCityAreaByRegionCode(RegionEntity regionEntity);
	
	/**
	 * 根据地区码列表查询地区
	 * @param regi_codes 地区码列表
	 * @return 查询到的地区
	 */
	List<RegionEntity> selRegions(List<String> regi_codes);
	
	/**
	 * 区域Region实体多个查询（通过regionIdxs）
	 * @param regionEntity
	 * @return
	 */
	ResultEntity selRegionsByRegionIdxs(List l);
	
	/**
	 * 区域Region实体多个查询,根据地区码查询地区码符合及其地区码下级地区
	 * @param req
	 * @return
	 */
	ResultEntity selRegionsForNormal(String req);
}
