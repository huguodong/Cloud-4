package com.ssitcloud.dblib.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.MetaDataInfoTypeDao;
import com.ssitcloud.dblib.dao.NavigationDao;
import com.ssitcloud.dblib.service.NavigationService;
@Repository
public class NavigationServiceImpl implements NavigationService {
	
	@Resource
	private NavigationDao navigationDao;

	@Override
	public ResultEntity queryBookItemTotal(String req) {
		ResultEntity result = new ResultEntity();
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject jsonObject =JSONObject.fromObject(req);
		if(JSONUtils.mayBeJSON(req)){
			map = JsonUtils.fromJson(req,Map.class);
		}
		//map.put("library_idx", jsonObject.getString("json"));
		int total = navigationDao.queryBookItemTotal(map);
		
		result.setResult(total);
		result.setState(true);
		return result;
	}

	@Override
	public ResultEntity queryBookShelfLayerTotal(String req) {
		ResultEntity result = new ResultEntity();
		Map<String,Object> map = new HashMap<String,Object>();
		//JSONObject jsonObject =JSONObject.fromObject(req);
		if(JSONUtils.mayBeJSON(req)){
			map = JsonUtils.fromJson(req,Map.class);
		}
		//map.put("library_idx", jsonObject.getString("json"));
		int total = navigationDao.queryBookShelfLayerTotal(map);
		
		result.setResult(total);
		result.setState(true);
		return result;
	}

	@Override
	public ResultEntity queryBookShelfTotal(String req) {
		ResultEntity result = new ResultEntity();
		Map<String,Object> map = new HashMap<String,Object>();
		//JSONObject jsonObject =JSONObject.fromObject(req);
		if(JSONUtils.mayBeJSON(req)){
			map = JsonUtils.fromJson(req,Map.class);
		}
		//map.put("library_idx", jsonObject.getString("json"));
		int total = navigationDao.queryBookShelfTotal(map);
		
		result.setResult(total);
		result.setState(true);
		return result;
	}

}
