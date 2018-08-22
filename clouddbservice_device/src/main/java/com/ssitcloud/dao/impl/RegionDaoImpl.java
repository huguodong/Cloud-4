package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.RegionDao;
import com.ssitcloud.entity.RegionEntity;

@Repository
public class RegionDaoImpl extends CommonDaoImpl implements RegionDao {

	@Override
	public RegionEntity selRegionByRegionIdx(RegionEntity regionEntity) {
		return this.sqlSessionTemplate.selectOne("region.selRegionByRegionIdx", regionEntity);
	}

	@Override
	public List<RegionEntity> selRegionsByRegionCode(RegionEntity regionEntity) {
		return this.sqlSessionTemplate.selectList("region.selRegionsByRegionCode", regionEntity);
	}

	@Override
	public List<RegionEntity> selProCityAreaByRegionCode(RegionEntity regionEntity) {
		return this.sqlSessionTemplate.selectList("region.selProCityAreaByRegionCode", regionEntity);
	}

	@Override
	public List<RegionEntity> selRegions(List<String> list) {
		return this.sqlSessionTemplate.selectList("region.selRegions", list);
	}
	
	@Override
	public List<RegionEntity> selRegionsByRegionIdxs(Map map) {
		return this.sqlSessionTemplate.selectList("region.selRegionsByRegionIdxs", map);
	}

	@Override
	public List<RegionEntity> selRegionsForNormal(Map map) {
		return this.sqlSessionTemplate.selectList("region.selRegionsForNormal", map);
	}

    @Override
    public List<RegionEntity> selRegionsByLibidx(List<String> list) {
        return this.sqlSessionTemplate.selectList("region.selRegionsByLibidx", list);
    }

	@Override
	public RegionEntity selRegionCodeByDeviceidx(Map<String,String> params) {
		return this.sqlSessionTemplate.selectOne("region.selRegionCodeByDeviceidx", params);
	}

	@Override
	public List<RegionEntity> queryAllRegion() {
		return this.sqlSessionTemplate.selectList("region.queryAllRegion");
	}

    
}
