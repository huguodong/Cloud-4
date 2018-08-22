package com.ssitcloud.business.mobile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.ObjectUtils;
import com.ssitcloud.business.mobile.service.AppElectronicConverterServiceI;
import com.ssitcloud.business.mobile.service.ElectronicCertificateServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.AppElectronicEntity;
import com.ssitcloud.mobile.entity.ElectronicCertificateEntity;
import com.ssitcloud.mobile.entity.ElectronicCertificatePageEntity;
import com.ssitcloud.redisutils.JedisUtils;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年2月28日 上午10:57:00
 */
@Service
public class ElectronicCertificateServiceImpl implements ElectronicCertificateServiceI {
	private final String URL_SELECT_S = "selectElectronicCertificates";
	private final String URL_COUNT = "countElectronicCertificate";
	private final String URL_SELECT_BY_READER_IDX = "selectByReaderIdx";
	private final String URL_READER="updateState";
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURLEntity;
	private static final String charset = "utf-8";
	
	private JedisUtils redis;

	@Autowired
	private AppElectronicConverterServiceI appElectronicConverterService;
	
	@Override
	public ResultEntity selectElectronicCertificateS(String certificateEntityJson) {
		// 检查实体完整性
		ResultEntity resultEntity = new ResultEntity();
		try{
			ElectronicCertificatePageEntity electronicCertificate = JsonUtils.fromJson(certificateEntityJson,
					ElectronicCertificatePageEntity.class);
			if(certificateEntityJson == null || electronicCertificate == null){
				resultEntity.setValue(false, "搜索条件格式不正确"+certificateEntityJson);
				return resultEntity;
			}
			String url = requestURLEntity.getRequestURL(URL_SELECT_S);
			Map<String, String> map = new HashMap<>(3);
			//重复序列化，保证实体是符合下层格式的实体
			map.put("json", JsonUtils.toJson(electronicCertificate));
			String remoteResult = HttpClientUtil.doPost(url, map , charset);
			ResultEntity remoteResultEntity = JsonUtils.fromJson(remoteResult, ResultEntity.class);
			if(remoteResult == null){
				LogUtils.error("从远程服务器获取数据失败，url=>"+url+" json=>"+certificateEntityJson);
				resultEntity.setState(false);
				return resultEntity;
			}else{
				return remoteResultEntity;
			}
		}catch(Exception e){
			resultEntity.setValue(false, "搜索条件格式不正确"+certificateEntityJson);
			return resultEntity;
		}
	}

	@Override
	public ResultEntity countElectronicCertificate(String certificateEntityJson) {
		// 检查实体完整性
		ResultEntity resultEntity = new ResultEntity();
		try{
			ElectronicCertificateEntity electronicCertificate = JsonUtils.fromJson(certificateEntityJson,
					ElectronicCertificateEntity.class);
			if(certificateEntityJson == null || electronicCertificate == null){
				resultEntity.setValue(false, "搜索条件格式不正确"+certificateEntityJson);
				return resultEntity;
			}
			String url = requestURLEntity.getRequestURL(URL_COUNT);
			Map<String, String> map = new HashMap<>(3);
			//重复序列化，保证实体是符合下层格式的实体
			map.put("json", JsonUtils.toJson(electronicCertificate));
			String remoteResult = HttpClientUtil.doPost(url, map , charset);
			ResultEntity remoteResultEntity = JsonUtils.fromJson(remoteResult, ResultEntity.class);
			if(remoteResult == null){
				LogUtils.error("从远程服务器获取数据失败，url=>"+url+" json=>"+certificateEntityJson);
				resultEntity.setState(false);
				return resultEntity;
			}else{
				return remoteResultEntity;
			}
		}catch(Exception e){
			resultEntity.setValue(false, "搜索条件格式不正确"+certificateEntityJson);
			return resultEntity;
		}
	}

	@Override
	public ResultEntity selectByReaderIdx(Map<String, Object> data) {
		ResultEntity resultEntity = new ResultEntity();
		if(data.get("reader_idx") == null){
			return resultEntity;
		}
		String url = requestURLEntity.getRequestURL(URL_SELECT_BY_READER_IDX);
		Map<String, String> map = new HashMap<>(3);
		map.put("json", JsonUtils.toJson(data));
		String remoteResult = HttpClientUtil.doPost(url, map , charset);
		try{
			ResultEntity remoteResultEntity = JsonUtils.fromJson(remoteResult, ResultEntity.class);
			if(remoteResult == null){
				LogUtils.error("从远程服务器获取数据失败，url=>"+url+" json=>"+map.get("json"));
				resultEntity.setState(false);
				return resultEntity;
			}else{
				List<Map<String, Object>> elecMap = (List<Map<String, Object>>) remoteResultEntity.getResult();
				List<AppElectronicEntity> appElec = new ArrayList<>(elecMap.size());
				for (Map<String, Object> map2 : elecMap) {
					ElectronicCertificateEntity elec = ObjectUtils.convertMap(map2, ElectronicCertificateEntity.class);
					appElec.add(appElectronicConverterService.converter(elec));
				}
				remoteResultEntity.setResult(appElec);
				return remoteResultEntity;
			}
		}catch(Exception e){
			LogUtils.debug(url+" 没有返回预期格式的数据", e);
			return resultEntity;
		}
	}

	@Override
	public ResultEntity setReaderElectronicCertificate(List<Integer> idxs,int state) {
		ResultEntity resultEntity = new ResultEntity();
		if(idxs == null || idxs.isEmpty()){
			resultEntity.setMessage("must set id_list(List<int>)");
			return resultEntity;
		}
		String url = requestURLEntity.getRequestURL(URL_READER);
		Map<String, Object> param = new HashMap<>(3,1.0f);
		param.put("id_list", idxs);
		param.put("state", state);
		Map<String, String> map = new HashMap<>(2,1.0f);
		
		map.put("json", JsonUtils.toJson(param));
		String resultJson = HttpClientUtil.doPost(url, map, charset);
		try{
			ResultEntity r = JsonUtils.fromJson(resultJson, ResultEntity.class);
			if(r != null){
				return r;
			}
		}catch(Exception e){
			LogUtils.info("可能时服务器没有返回预期数据，返回数据"+resultJson,e);
		}
		
		return resultEntity;
	}

	/**
	 * 根据key（图书馆id+读者卡号）从Redis获取推荐的图书列表
	 * 
	 * @param data
	 */
	@Override
	public ResultEntity getRecommendList(Map<String, Object> data) {
		String library_idx = data.get("library_idx") + "";
		String cardNo = data.get("cardNo") + "";
		if (!StringUtils.hasText(library_idx) || !StringUtils.hasText(cardNo)) {
			return new ResultEntity(false, "参数不完整");
		}

		String redis_key = "recommendLog:" + library_idx + ":" + cardNo;// Redis的key值
		String val = redis.get(redis_key);

		ResultEntity result = new ResultEntity();
		if (val != null) {
			result.setState(true);
			result.setResult(val);
		} else {
			result.setState(false);
			result.setMessage("没有数据返回");
		}

		return result;
	}
}
