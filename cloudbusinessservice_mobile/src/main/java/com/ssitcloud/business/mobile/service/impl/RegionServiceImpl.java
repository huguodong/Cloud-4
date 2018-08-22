package com.ssitcloud.business.mobile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.RegionService;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.RegionEntity;

/**
 * 地区服务实现类
 * @author LXP
 *
 */
@Service
public class RegionServiceImpl implements RegionService {

	private final String URL_SLELCT_BY_IDX="selRegionByRegionIdx";
	private final String URL_SLELCT_BY_CODE="selRegionsByRegionCode";
	private final String URL_SLELCT_ON_CODE="selRegionsOnRegionCode";
	private final String URL_selProCityArea="selProCityAreaByRegionCode";
	private final String URL_selRegions="selRegions";
	private final String URL_selRegionsByRegionIdxs="selRegionsByRegionIdxs";
	private final String URL_selRegionsForNormal="selRegionsForNormal";
	private final String charset="UTF-8";
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	
	@Override
	public ResultEntity selRegionByRegionIdx(RegionEntity regionEntity) {
		String url = requestURL.getRequestURL(URL_SLELCT_BY_IDX);
		Map<String, String> map = new HashMap<>(2,1.0f);
		map.put("json", JsonUtils.toJson(regionEntity));
		String resultJson = HttpClientUtil.doPost(url, map , charset);
		try{
			ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
			if(!resultEntity.getState()){
				LogUtils.info(getClass()+" selRegionByRegionIdx fail ,reMessage"+resultEntity.getRetMessage());
				resultEntity.setRetMessage(null);
			}
			return resultEntity;
		}catch (Exception e) {
			LogUtils.info(e);
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(false);
			return resultEntity;
		}
	}

	@Override
	public ResultEntity selRegionsByRegionCode(RegionEntity regionEntity) {
		String url = requestURL.getRequestURL(URL_SLELCT_BY_CODE);
		Map<String, String> map = new HashMap<>(2,1.0f);
		map.put("json", JsonUtils.toJson(regionEntity));
		String resultJson = HttpClientUtil.doPost(url, map , charset);
		try{
			ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
			if(!resultEntity.getState()){
				LogUtils.info(getClass()+" selRegionsByRegionCode fail ,reMessage"+resultEntity.getRetMessage());
				resultEntity.setRetMessage(null);
			}
			return resultEntity;
		}catch (Exception e) {
			LogUtils.info(e);
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(false);
			return resultEntity;
		}
	}

	@Override
	public ResultEntity selRegionsOnRegionCode(RegionEntity regionEntity) {
		String url = requestURL.getRequestURL(URL_SLELCT_ON_CODE);
		Map<String, String> map = new HashMap<>(2,1.0f);
		map.put("json", JsonUtils.toJson(regionEntity));
		String resultJson = HttpClientUtil.doPost(url, map , charset);
		try{
			ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
			if(!resultEntity.getState()){
				LogUtils.info(getClass()+" selRegionsOnRegionCode fail ,reMessage"+resultEntity.getRetMessage());
				resultEntity.setRetMessage(null);
			}
			return resultEntity;
		}catch (Exception e) {
			LogUtils.info(e);
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(false);
			return resultEntity;
		}
	}

	@Override
	public ResultEntity selProCityAreaByRegionCode(RegionEntity regionEntity) {
		String url = requestURL.getRequestURL(URL_selProCityArea);
		Map<String, String> map = new HashMap<>(2,1.0f);
		map.put("json", JsonUtils.toJson(regionEntity));
		String resultJson = HttpClientUtil.doPost(url, map , charset);
		try{
			ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
			if(!resultEntity.getState()){
				LogUtils.info(getClass()+" selProCityAreaByRegionCode fail ,reMessage"+resultEntity.getRetMessage());
				resultEntity.setRetMessage(null);
			}
			return resultEntity;
		}catch (Exception e) {
			LogUtils.info(e);
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(false);
			return resultEntity;
		}
	}

	@Override
	public List<RegionEntity> selRegions(List<String> regi_codes) {
		String url = requestURL.getRequestURL(URL_selRegions);
		Map<String, Object> data = new HashMap<>(1,2.0f);
		data.put("regi_codes", regi_codes);
		Map<String, String> map = new HashMap<>(2,1.0f);
		map.put("json", JsonUtils.toJson(data));
		String resultJson = HttpClientUtil.doPost(url, map , charset);
		try{
			ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
			if(!resultEntity.getState()){
				LogUtils.info(getClass()+" selRegions fail ,reMessage"+resultEntity.getRetMessage());
			}else{
				List<Map> temps = (List<Map>) resultEntity.getResult();
				List<RegionEntity> regions = new ArrayList<>(temps.size());
				for (Map map2 : temps) {
					RegionEntity region = JsonUtils.fromJson(JsonUtils.toJson(map2), RegionEntity.class);
					regions.add(region);
				}
				return regions;
			}
		}catch (Exception e) {
			LogUtils.info(getClass()+"查询地区发生异常",e);
		}
		return new ArrayList<>(0);
	}
	
	@Override
	public ResultEntity selRegionsByRegionIdxs(List l) {
		String url = requestURL.getRequestURL(URL_selRegionsByRegionIdxs);
		Map<String, String> map = new HashMap<>(2,1.0f);
		map.put("json", JsonUtils.toJson(l));
		String resultJson = HttpClientUtil.doPost(url, map , charset);
		try{
			ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
			if(!resultEntity.getState()){
				LogUtils.info(getClass()+" selRegionsByRegionIdxs fail ,reMessage"+resultEntity.getRetMessage());
				resultEntity.setRetMessage(null);
			}
			return resultEntity;
		}catch (Exception e) {
			LogUtils.info(e);
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(false);
			return resultEntity;
		}
	}

	@Override
	public ResultEntity selRegionsForNormal(String req) {
		String url = requestURL.getRequestURL(URL_selRegionsForNormal);
		Map<String, String> map = new HashMap<>(2,1.0f);
		map.put("json", req);
		String resultJson = HttpClientUtil.doPost(url, map , charset);
		try{
			ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
			if(!resultEntity.getState()){
				LogUtils.info(getClass()+" selRegionsForNormal fail ,reMessage"+resultEntity.getRetMessage());
				resultEntity.setRetMessage(null);
			}
			return resultEntity;
		}catch (Exception e) {
			LogUtils.info(e);
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(false);
			return resultEntity;
		}
	}
	
}
