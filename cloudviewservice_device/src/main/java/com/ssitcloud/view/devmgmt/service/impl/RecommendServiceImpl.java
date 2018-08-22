package com.ssitcloud.view.devmgmt.service.impl;

import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.devmgmt.service.RecommendService;

@Service
public class RecommendServiceImpl extends BasicServiceImpl implements RecommendService {

	@Override
	public ResultEntity editRankRole(Map<String, String> map) {
		String resp = HttpClientUtil.doPost(requestURL.getRequestURL("editRankRole"), map, Consts.UTF_8.toString());
		ResultEntity result = null;
		if (JSONUtils.mayBeJSON(resp)) {
			result = JsonUtils.fromJson(resp, ResultEntity.class);
		} else {
			result = new ResultEntity();
			result.setState(false);
			result.setMessage("添加图书推荐规则失败");
		}
		return result;
	}

	@Override
	public ResultEntity deleteRankRole(Map<String, String> map) {
		String resp = HttpClientUtil.doPost(requestURL.getRequestURL("deleteRankRole"), map, Consts.UTF_8.toString());
		ResultEntity result = null;
		if (JSONUtils.mayBeJSON(resp)) {
			result = JsonUtils.fromJson(resp, ResultEntity.class);
		} else {
			result = new ResultEntity();
			result.setState(false);
			result.setMessage("删除图书推荐规则失败");
		}
		return result;
	}

	@Override
	public ResultEntity findRankRoleByIdx(Map<String, String> map) {
		String resp = HttpClientUtil.doPost(requestURL.getRequestURL("findRankRoleByIdx"), map, Consts.UTF_8.toString());
		ResultEntity result = null;
		if (JSONUtils.mayBeJSON(resp)) {
			result = JsonUtils.fromJson(resp, ResultEntity.class);
		} else {
			result = new ResultEntity();
			result.setState(false);
			result.setMessage("查询图书推荐规则失败");
		}
		return result;
	}

	@Override
	public ResultEntity findRankRoleList(Map<String, String> map) {
		String resp = HttpClientUtil.doPost(requestURL.getRequestURL("findRankRoleList"), map, Consts.UTF_8.toString());
		ResultEntity result = null;
		if (JSONUtils.mayBeJSON(resp)) {
			result = JsonUtils.fromJson(resp, ResultEntity.class);
		} else {
			result = new ResultEntity();
			result.setState(false);
			result.setMessage("查询图书推荐规则失败");
		}
		return result;
	}
}
