package com.ssitcloud.view.devmgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.devmgmt.service.ThirdPartyServiceService;

@Service
public class ThirdPartyServiceServiceImpl extends BasicServiceImpl implements ThirdPartyServiceService {

	private String URL_queryThirdPartyService = "queryThirdPartyService";
	private String URL_deleteThirdPartyService = "deleteThirdPartyService";
	private String URL_queryThirdPartyServiceByPage = "queryThirdPartyServiceByPage";
	private String URL_editThirdPartyService = "editThirdPartyService";
	private String URL_borrowOrderInfo = "borrowOrderInfo";
	private String URL_borrowBackCountInfo = "borrowBackCountInfo";
	private String URL_cameCountInfo = "cameCountInfo";
	private String URL_readerRank = "readerRank";
	private String URL_countCertificate = "countCertificate";
	private String URL_countLoanForBar = "countLoanForBar";
	private String URL_countRealTimeVisitor = "countRealTimeVisitor";
	private String URL_loadLibInfo = "loadLibInfo";
	
	private String URL_queryDisplayInfo = "queryDisplayInfo";
	private String URL_deleteDisplayInfo = "deleteDisplayInfo";
	private String URL_queryDisplayInfoList = "queryDisplayInfoList";
	private String URL_editDisplayInfo = "editDisplayInfo";
	
	private String URL_getRecommendList = "getRecommendList";
	private String URL_getBookListByTopN = "getBookListByTopN";

	public ResultEntity queryThirdPartyServiceByParams(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryThirdPartyService), map, charset);
		if (JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		} else {
			return new ResultEntity(false, "操作失败");
		}
	}

	@Override
	public ResultEntity deleteThirdPartyService(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_deleteThirdPartyService), map, charset);
		if (JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		} else {
			return new ResultEntity(false, "操作失败");
		}
	}

	@Override
	public ResultEntity queryThirdPartyServiceByPage(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryThirdPartyServiceByPage), map, charset);
		if (JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		} else {
			return new ResultEntity(false, "操作失败");
		}
	}

	@Override
	public ResultEntity editThirdPartyService(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_editThirdPartyService), map, charset);
		if (JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		} else {
			return new ResultEntity(false, "操作失败");
		}
	}

	@Override
	public JSONObject borrowOrderInfo(Map<String, String> param) {
		JSONObject obj = null;
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_borrowOrderInfo), param, charset);
		if (JSONUtils.mayBeJSON(result)) {
			obj = JSONObject.fromObject(result);
		} else {
			obj = new JSONObject();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");
		}
		return obj;
	}

	@Override
	public JSONObject borrowBackCountInfo(Map<String, String> param) {
		JSONObject obj = null;
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_borrowBackCountInfo), param, charset);
		if (JSONUtils.mayBeJSON(result)) {
			obj = JSONObject.fromObject(result);
		} else {
			obj = new JSONObject();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");
		}
		return obj;
	}

	@Override
	public JSONObject cameCountInfo(Map<String, String> param) {
		JSONObject obj = null;
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_cameCountInfo), param, charset);
		if (JSONUtils.mayBeJSON(result)) {
			obj = JSONObject.fromObject(result);
		} else {
			obj = new JSONObject();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");
		}
		return obj;
	}

	@Override
	public JSONObject readerRank(Map<String, String> param) {
		JSONObject obj = null;
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_readerRank), param, charset);
		if (JSONUtils.mayBeJSON(result)) {
			obj = JSONObject.fromObject(result);
		} else {
			obj = new JSONObject();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");
		}
		return obj;
	}

	@Override
	public JSONObject countCertificate(Map<String, String> param) {
		JSONObject obj = null;
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_countCertificate), param, charset);
		if (JSONUtils.mayBeJSON(result)) {
			obj = JSONObject.fromObject(result);
		} else {
			obj = new JSONObject();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");
		}
		return obj;
	}

	@Override
	public JSONObject countRealTimeVisitor(Map<String, String> param) {
		JSONObject obj = null;
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_countRealTimeVisitor), param, charset);
		if (JSONUtils.mayBeJSON(result)) {
			obj = JSONObject.fromObject(result);
		} else {
			obj = new JSONObject();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");
		}
		return obj;
	}

	@Override
	public JSONObject countLoanForBar(Map<String, String> param) {
		JSONObject obj = null;
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_countLoanForBar), param, charset);
		if (JSONUtils.mayBeJSON(result)) {
			obj = JSONObject.fromObject(result);
		} else {
			obj = new JSONObject();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");
		}
		return obj;
	}

	@Override
	public JSONObject loadLibInfo(Map<String, String> param) {
		JSONObject obj = null;
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_loadLibInfo), param, charset);
		if (JSONUtils.mayBeJSON(result)) {
			obj = JSONObject.fromObject(result);
		} else {
			obj = new JSONObject();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");
		}
		return obj;
	}

	@Override
	public ResultEntity deleteDisplayInfo(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_deleteDisplayInfo), map, charset);
		if (JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		} else {
			return new ResultEntity(false, "操作失败");
		}
	}

	@Override
	public ResultEntity editDisplayInfo(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_editDisplayInfo), map, charset);
		if (JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		} else {
			return new ResultEntity(false, "操作失败");
		}
	}

	@Override
	public ResultEntity queryDisplayInfo(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDisplayInfo), map, charset);
		if (JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		} else {
			return new ResultEntity(false, "查询失败");
		}
	}

	@Override
	public ResultEntity queryDisplayInfoList(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDisplayInfoList), map, charset);
		if (JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		} else {
			return new ResultEntity(false, "查询失败");
		}
	}

	/**
	 * 根据规则推荐图书信息到读者手机上
	 * @param req
	 * @return
	 */
	@Override
	public ResultEntity getRecommendListForSMS(String req) {
		ResultEntity resultEntity = this.getRecommendList(req);
		if(resultEntity.getState()){
			JSONArray array = JSONArray.fromObject(resultEntity.getResult());
			if (!array.isEmpty()) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.optJSONObject(i);
					String mobile = obj.optString("mobile");
					List<String> bookList = (List<String>) obj.opt("bookList");
					this.sendSMS(mobile, bookList);// 发送短信
				}
			}
			return resultEntity;
		}
		return resultEntity;
	}
	
	/**
	 * 根据规则推荐图书信息到读者手机上
	 * @param req
	 * @return
	 */
	@Override
	public ResultEntity getRecommendList(String req) {
		Map<String, String> param = new HashMap<>();
		param.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_getRecommendList), param, charset);
		if (JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		} else {
			return new ResultEntity(false, "查询失败，返回无效结果");
		}
		
	}
	
	/**
	 * 发送推荐短信
	 * @param mobile 手机号
	 * @param recommendBookList 图书列表
	 * @return
	 */
	private boolean sendSMS(String mobile, List<String> recommendBookList) {
		if (StringUtils.hasText(mobile)) {
			String content = "热门图书推荐：";// 短信格式--热门图书推荐：#booklist#。示例：热门图书推荐：《时间之书》《不畏将来》
			for (String book : recommendBookList) {
				content += "《" + book + "》";
			}
			JSONObject json = new JSONObject();
			json.put("content", content);
			json.put("phone", mobile);

			Map<String, String> map = new HashMap<String, String>();
			map.put("json", json.toString());
			// 直接发送短信
			String resp = HttpClientUtil.doPost(requestURL.getRequestURL("sendSMS"), map, Consts.UTF_8.toString());
			if (JSONUtils.mayBeJSON(resp)) {
				ResultEntity sendResult = JsonUtils.fromJson(resp, ResultEntity.class);
				if (sendResult.getState()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取TOP N的热门图书
	 */
	@Override
	public ResultEntity getBookListByTopN(String req) {
		if (!StringUtils.hasText(req)) {
			return new ResultEntity(false, "图书馆ID不能为空");
		}
		String library = null;
		int topHits = 500;
		if (JSONUtils.mayBeJSON(req)) {
			JSONObject json = JSONObject.fromObject(req);
			library = json.optString("library");
			topHits = json.optInt("topHits", 500);
		}

		if (!StringUtils.hasText(library)) {
			return new ResultEntity(false, "图书馆ID不能为空");
		}

		JSONObject obj = new JSONObject();
		obj.put("indexName", "*_loan_log");// 借书日志
		obj.put("groupCondition", "Title,Barcode");// 书名，条码
		JSONObject searchCondition = new JSONObject();
		searchCondition.put("opercmd", "00010201");// 借书
		searchCondition.put("lib_id", library);
		obj.put("searchCondition", searchCondition);

		JSONObject exclusiveConditon = new JSONObject();
		exclusiveConditon.put("Title", "查询不到图书");// 过滤掉查询不到图书的记录
		obj.put("exclusiveConditon", exclusiveConditon);

		obj.put("topHits", topHits);// Top N

		Map<String, String> param = new HashMap<>();
		param.put("req", obj.toString());
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_getBookListByTopN), param, charset);
		if (JSONUtils.mayBeJSON(result)) {
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if (resultEntity.getState()) {
				JSONObject json = JSONObject.fromObject(resultEntity.getResult());
				JSONArray children = json.optJSONArray("children");
				if (children == null || children.isEmpty()) {
					return new ResultEntity(false, "没有数据");
				} else {
					JSONArray resultArray = new JSONArray();
					String _name = null;
					for (int i = 0; i < children.size(); i++) {
						JSONObject resultObj = new JSONObject();
						JSONObject val = children.optJSONObject(i);
						if (val != null) {
							String name = val.optString("name");
							String text = val.optString("text");
							if (text != null)resultObj.put(name, text);
							
							JSONArray children2 = val.optJSONArray("children");
							if (children2 != null && !children2.isEmpty()) {
								JSONArray _children = new JSONArray();
								for (int j = 0; j < children2.size(); j++) {
									JSONObject _val = children2.optJSONObject(j);
									if (_val != null) {
										_name = _val.optString("name");
										String _text = _val.optString("text");
										if (_text != null) {
											_children.add(_text);
										}
									}
								}
								if (_name != null)
									resultObj.put(_name, _children);
							}
						}
						if(!resultObj.isEmpty())resultArray.add(resultObj);
					}
					
					resultEntity.setState(true);
					resultEntity.setResult(resultArray);
					return resultEntity;
				}
			} else {
				return new ResultEntity(false, "查询失败");
			}
		} else {
			return new ResultEntity(false, "查询失败");
		}
	}

}
