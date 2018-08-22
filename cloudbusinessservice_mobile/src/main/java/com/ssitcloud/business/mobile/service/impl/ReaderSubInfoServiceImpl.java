package com.ssitcloud.business.mobile.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.ReaderSubInfoServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderSubInfoEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年2月27日 下午4:03:46
 */
@Service
public class ReaderSubInfoServiceImpl implements ReaderSubInfoServiceI {
	private final String URL_DELETE_AND_INSERT = "readerSbuInfoDeleteAndInsert";
	private final String URL_SELECT = "selectReaderSubInfos";
	
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURLEntity;
	private static final String charset = "utf-8";

	@Override
	public ResultEntity deleteAndInsert(List<ReaderSubInfoEntity> readerSubInfoList) {
		if (readerSubInfoList == null || readerSubInfoList.size() == 0) {
			throw new IllegalArgumentException("readerSubInfoList is null");
		}
		ResultEntity resultEntity = new ResultEntity();
		// 完整性测试
		Integer idx = readerSubInfoList.get(0).getReader_idx();
		for (int i = 0, z = readerSubInfoList.size(); i < z; ++i) {
			ReaderSubInfoEntity forTemp = readerSubInfoList.get(i);
			if (forTemp.getInfotype_idx() == null || forTemp.getInfotype_value() == null
					|| forTemp.getReader_idx() == null) {
				resultEntity.setValue(false, "参数不完整");
				return resultEntity;
			}
			if (idx == null || !idx.equals(forTemp.getReader_idx())) {
				resultEntity.setValue(false, "idx不一致");
				return resultEntity;
			}
		}
		// 通过完整性测试,发送请求
		String url = requestURLEntity.getRequestURL(URL_DELETE_AND_INSERT);
		Map<String, String> map = new HashMap<>(1);
		ResultEntity argsEntity = new ResultEntity();
		argsEntity.setResult(readerSubInfoList);
		map.put("json", JsonUtils.toJson(argsEntity));
		String resultJson = HttpClientUtil.doPost(url, map, charset);
		if (resultJson == null) {
			resultEntity.setValue(false, "请求服务失败");
			LogUtils.error(this.getClass()+"->deleteAndInsert请求不到底层服务,url=>"+url);
		} else {
			try {
				ResultEntity returnEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
				resultEntity.setValue(returnEntity.getState(),returnEntity.getMessage());
			} catch (Exception e) {
				resultEntity.setValue(false, "请求服务失败");
				LogUtils.error(this.getClass()+"->deleteAndInsert请底层服务没有获取到预期数据,url=>"+url);
			}
		}
		return resultEntity;
	}

	@Override
	public ResultEntity selectReaderSubInfo(Integer reader_idx) {
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setState(false);
		if(reader_idx == null){
			resultEntity.setMessage("提交参数有误");
			return resultEntity;
		}
		Map<String, String> map = new HashMap<>(1);
		map.put("json", "{\"reader_idx\":"+reader_idx+"}");
		String url = requestURLEntity.getRequestURL(URL_SELECT);
		String remoteResultJson = HttpClientUtil.doPost(url , map, charset);
		ResultEntity remoteEntity = JsonUtils.fromJson(remoteResultJson, ResultEntity.class);
		if(remoteEntity == null){
			return resultEntity;
		}
		
		return remoteEntity;
	}

}
