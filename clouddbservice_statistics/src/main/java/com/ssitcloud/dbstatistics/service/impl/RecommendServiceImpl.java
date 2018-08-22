package com.ssitcloud.dbstatistics.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbstatistics.dao.RecommendDao;
import com.ssitcloud.dbstatistics.entity.BookRankRolePageEntity;
import com.ssitcloud.dbstatistics.service.RecommendService;
import com.ssitcloud.statistics.entity.BookRankRoleEntity;

@Service
public class RecommendServiceImpl implements RecommendService {
	@Resource
	RecommendDao recommendDao;

	@Override
	public ResultEntity addRankRole(BookRankRoleEntity entity) {
		int count = recommendDao.addRankRole(entity);
		ResultEntity result = new ResultEntity();
		if (count > 0) {
			result.setState(true);
			result.setMessage("成功添加图书推荐规则");
		} else {
			result.setState(false);
			result.setMessage("添加图书推荐规则失败");
		}
		return result;
	}

	@Override
	public ResultEntity updateRankRole(BookRankRoleEntity entity) {
		int count = recommendDao.updateRankRole(entity);
		ResultEntity result = new ResultEntity();
		if (count > 0) {
			result.setState(true);
			result.setMessage("成功更新图书推荐规则");
		} else {
			result.setState(false);
			result.setMessage("更新图书推荐规则失败");
		}
		return result;
	}

	@Override
	public ResultEntity deleteRankRole(String req) {
		ResultEntity result = new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			JSONObject obj=JSONObject.fromObject(req);
			JSONArray arr=obj.optJSONArray("role_idx");
			List<Integer> array = (List<Integer>) JSONArray.toCollection(arr, Integer.class);
			int count = recommendDao.deleteRankRole(array);
			if (count > 0) {
				result.setState(true);
				result.setMessage("成功删除" + count + "条图书推荐规则");
				return result;
			}
		}
		result.setState(false);
		result.setMessage("删除图书推荐规则失败");
		return result;
	}

	@Override
	public ResultEntity findRankRoleByIdx(BookRankRoleEntity entity) {
		entity = recommendDao.findRankRoleByIdx(entity);
		ResultEntity result = new ResultEntity();
		result.setState(true);
		result.setResult(entity);
		return result;
	}
	
	@Override
	public ResultEntity findRankRoleByParam(BookRankRoleEntity entity) {
		entity = recommendDao.findRankRoleByParam(entity);
		ResultEntity result = new ResultEntity();
		result.setState(true);
		result.setResult(entity);
		return result;
	}

	@Override
	public ResultEntity findRankRoleList(BookRankRolePageEntity entity) {
		entity = recommendDao.findRankRoleList(entity);
		ResultEntity result = new ResultEntity();
		result.setState(true);
		result.setResult(entity);
		return result;
	}
}
