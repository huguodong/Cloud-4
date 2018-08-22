package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.RegionDao;
import com.ssitcloud.entity.RegionEntity;
import com.ssitcloud.service.RegionService;


@Service
public class RegionServiceImpl implements RegionService {
	@Resource
	private RegionDao regionDao;

	@Override
	public RegionEntity selRegionByRegionIdx(RegionEntity regionEntity) {
		return regionDao.selRegionByRegionIdx(regionEntity);
	}

	@Override
	public List<RegionEntity> selRegionsByRegionCode(RegionEntity regionEntity) {
		return regionDao.selRegionsByRegionCode(regionEntity);
	}

	@Override
	public List<RegionEntity> selProCityAreaByRegionCode(
			RegionEntity regionEntity) {
		return regionDao.selProCityAreaByRegionCode(regionEntity);
	}

	@Override
	public List<RegionEntity> selRegions(List<String> list) {
		if(list == null || list.isEmpty()){
			return new ArrayList<>(0);
		}
		return regionDao.selRegions(list);
	}
	
	@Override
	public List<RegionEntity> selRegionsByRegionIdxs(Map map) {
		return regionDao.selRegionsByRegionIdxs(map);
	}

	@Override
	public List<RegionEntity> selRegionsForNormal(Map map) {
		return regionDao.selRegionsForNormal(map);
	}

    @Override
    public List<RegionEntity> selRegionsByLibidx(List<String> list) {
        if(list == null || list.isEmpty()){
            return new ArrayList<>(0);
        }
        return regionDao.selRegionsByLibidx(list);
    }
    
    
    @Override
    public List<RegionEntity> queryAllRegion() {
    	return regionDao.queryAllRegion();
    }
    
    

	@Override
	public RegionEntity selRegionCodeByDeviceidx(Map<String,String> params) {
		return regionDao.selRegionCodeByDeviceidx(params);
	}

}
