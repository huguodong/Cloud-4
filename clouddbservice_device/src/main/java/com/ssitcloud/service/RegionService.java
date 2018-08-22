package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.RegionEntity;

public interface RegionService {
	
	
	/**
	 * 区域Region实体单个查询
	 * author shuangjunjie
	 * 2017年4月26日 下午3:14
	 * @param regionEntity
	 * @return
	 */
	public abstract RegionEntity selRegionByRegionIdx(RegionEntity regionEntity);
	
	/**
	 * 区域Region实体多个查询
	 * author shuangjunjie
	 * 2017年4月26日 下午3:50
	 * @param regionEntity
	 * @return
	 */
	public abstract List<RegionEntity> selRegionsByRegionCode(RegionEntity regionEntity);
	
	/**
	 * 区域RegionEntity多个查询 可查所有省、省对应的市、对应的区
	 * author shuangjunjie
	 * 2017年4月28日 上午10:07
	 * @param regionEntity
	 * @return
	 */
	public abstract List<RegionEntity> selProCityAreaByRegionCode(RegionEntity regionEntity);
	
	/**
	 * 根据地区码列表查询地区
	 * @param list
	 * @return 符合条件的地区实体
	 */
	List<RegionEntity> selRegions(List<String> list);
	
	/**
	 * 区域Region实体多个查询
	 * author shuangjunjie
	 * 2017年4月26日 下午3:14
	 * @param map
	 * @return
	 */
	public abstract List<RegionEntity> selRegionsByRegionIdxs(Map map);
	
	/**
	 * 区域Region实体多个查询
	 * author shuangjunjie
	 * 2017年4月26日 下午3:14
	 * @param map
	 * @return
	 */
	public abstract List<RegionEntity> selRegionsForNormal(Map map);

    /**
     * 根据馆IDX查询所有设备的地区分布
     * 2017-08-24 lqw
     * @param
     */
    List<RegionEntity> selRegionsByLibidx(List<String> list);
    
    /**
     * 根据设备IDX查询设备所在地区
     * @param deviceIdx
     * @return
     */
    RegionEntity selRegionCodeByDeviceidx(Map<String,String> params);
    
    /**
     * 查询所有的地区信息
     *
     * <p>2017年9月7日 上午10:50:25 
     * <p>create by hjc
     * @return
     */
    public abstract List<RegionEntity> queryAllRegion();
}
