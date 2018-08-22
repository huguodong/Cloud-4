package com.ssitcloud.business.mobile.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.BibliosServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年2月28日 下午5:50:36
 */
@Service
public class BibliosServiceImpl implements BibliosServiceI {
	private final String URL_SELECT_BIBLIOS="queryBiblios";
	private final String URL_SELECT_FOR_LIBID_BARCODE = "queryBibliosForBCAndLib";
	
	@Resource(name="requestURL")
	private RequestURLListEntity requestURLEntity;
	private static final String charset = "utf-8";
	
	@Override
	public Map<String, Object> selectBiblios(Integer bib_idx) {
		if(bib_idx == null){
			return null;
		}
		String bibliosUrl = requestURLEntity.getRequestURL(URL_SELECT_BIBLIOS);
		Map<String, String> paramMap = new HashMap<>(3);
		paramMap.put("json", "{\"bib_idx\":"+bib_idx+"}");
		try{
			String bibliosResultJson = HttpClientUtil.doPost(bibliosUrl, paramMap, charset);
			ResultEntity bibliosResultEntity = JsonUtils.fromJson(bibliosResultJson, ResultEntity.class);
			if(bibliosResultEntity.getState()){
				return (Map<String, Object>) bibliosResultEntity.getResult();
			}
		}catch(Exception e){
			LogUtils.error(BibliosServiceImpl.class+"向底层数据库获取数据失败,url=>"+bibliosUrl+" data=>"+paramMap);
			return null;
		}
		return null;
	}

	@Override
	public Map<String, Object> queryBibliosForBCAndLib(Integer lib_id, String book_barcode) {
		Map<String, String> map = new HashMap<>(3);
		Map<String, Object> param = new HashMap<>(5);
		param.put("book_barcode", book_barcode);
		param.put("lib_id", lib_id);
		map.put("json", JsonUtils.toJson(param));
		String url = requestURLEntity.getRequestURL(URL_SELECT_FOR_LIBID_BARCODE);
		try{
			String remoteJson = HttpClientUtil.doPost(url, map, charset);
			ResultEntity resultEntity = JsonUtils.fromJson(remoteJson, ResultEntity.class);
			if(resultEntity.getState()){
				return (Map<String, Object>) resultEntity.getResult();
			}
		}catch(Exception e){
			LogUtils.error(BibliosServiceImpl.class+"向底层数据库获取数据失败,url=>"+url+" data=>"+map);
			return null;
		}
		return null;
	}

}
