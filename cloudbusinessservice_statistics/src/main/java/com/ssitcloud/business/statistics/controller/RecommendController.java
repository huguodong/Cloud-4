package com.ssitcloud.business.statistics.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.service.RecommendService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "recommend" })
public class RecommendController {
	@Resource
	RecommendService recommendService;

	@RequestMapping("/editRankRole")
	@ResponseBody
	public ResultEntity editRankRole(HttpServletRequest request, String req) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);
		return recommendService.editRankRole(map);
	}

	@RequestMapping("/deleteRankRole")
	@ResponseBody
	public ResultEntity deleteRankRole(HttpServletRequest request, String req) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);
		return recommendService.deleteRankRole(map);
	}

	@RequestMapping("/findRankRoleByIdx")
	@ResponseBody
	public ResultEntity findRankRoleByIdx(HttpServletRequest request, String req) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);
		return recommendService.findRankRoleByIdx(map);
	}

	@RequestMapping("/findRankRoleList")
	@ResponseBody
	public ResultEntity findRankRoleList(HttpServletRequest request, String req) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);
		return recommendService.findRankRoleList(map);
	}
	
	@RequestMapping("/getRecommendList")
	@ResponseBody
	public ResultEntity recommend(HttpServletRequest request, String req) {
		Map<String, String> map = new HashMap<String, String>();
		if (JSONUtils.mayBeJSON(req)) {
			map = JsonUtils.fromJson(req, Map.class);
			if (map.containsKey("cardNo")) {
				boolean isSuccess = false;
				String cardNo = map.get("cardNo");
				JSONArray array = new JSONArray();
				if (cardNo.indexOf(",") != -1) {//多个读者卡号
					String[] cardNos = cardNo.split("\\,");
					String failList = "";
					String msg = "读者【${cardNo}】的推荐图书信息查询失败!";
					for (String no : cardNos) {
						map.put("cardNo", no);
						ResultEntity result = recommendService.recommend(map);
						if (result.getState()) {
							array.add(result.getResult());
							isSuccess = true;
						} else {
							failList += no + ",";
						}
					}
					ResultEntity result = new ResultEntity();
					if (StringUtils.hasText(failList)) {
						msg = msg.replaceAll("(\\$\\{cardNo\\})", failList.substring(0, failList.length() - 1));
						result.setMessage(msg);
					}
					result.setState(isSuccess);
					result.setResult(array);
					return result;
				}else {//一个读者卡号
					ResultEntity result = recommendService.recommend(map);
					if (result.getState()) {
						array.add(result.getResult());
						result.setState(true);
						result.setResult(array);
					}
					return result;
				}
			} 
		}
		return new ResultEntity(false, "参数不正确");
	}
}
