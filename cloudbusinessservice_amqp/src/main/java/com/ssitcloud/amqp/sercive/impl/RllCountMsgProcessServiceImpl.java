package com.ssitcloud.amqp.sercive.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.amqp.sercive.RllCountMsgProcessService;
import com.ssitcloud.businessauth.utils.HttpClientUtil;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.devmgmt.entity.RllCountMsgVO;
import com.ssitcloud.devmgmt.entity.RllCountVO;

/**
 * 
 * 人流量统计消息处理类
 * 
 * @author yeyalin
 * @data 2017年10月18日
 */
@Service
public class RllCountMsgProcessServiceImpl implements RllCountMsgProcessService {
	private static final String CHARSET = "UTF-8";
	private static final String SAVERLLCOUNTDATA = "saveRllCountData";
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURL;

	/**
	 * 
	 * 人流量统计消息处理
	 * 
	 * @author yeyalin
	 * @data 2017年10月18日
	 */
	@Override
	public boolean dealRllCountMsg(RllCountMsgVO msgVo) {
		boolean flag = true;
		try {
			// 解析并处理人流量统计数据
			String rllCounts = dealRllCouuntMsg(msgVo);
			Map<String, String> param = new ConcurrentHashMap<String, String>();
			param.put("req", rllCounts);
			String url = requestURL.getRequestURL(SAVERLLCOUNTDATA);
			System.out.println("--人流量统计----请求url=" +url + "-----,请求参数:"+param);
			String response = HttpClientUtil.doPost(url, param, CHARSET);
			if (!"".equals(response) && response != null) {
				JSONObject jsonObj = JSONObject.fromObject(response);
				// 状态：1-成功，0-失败
				boolean status = jsonObj.getBoolean("state");
				// 失败原因
				String errorMsg = jsonObj.getString("message");
				if (!status) {
					LogUtils.info("人流量统计消息处理返回失败，原因如下： " + errorMsg);
					flag=false;
				}
			}else{
				System.out.println("-----------reciceDhstateMessage RllStateMsgVO--人流量统计消息处理返回失败-----------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 解析并处理人流量统计数据
	 * 人流量和采集设备一样，当作特殊设备来处理
	 * @param datas
	 * @return
	 */
	private String dealRllCouuntMsg(RllCountMsgVO vo) {
		Map<String, String> param = new ConcurrentHashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
		List<RllCountVO> RllCountVOs = new ArrayList<>();
		RllCountVO rllCountVO = new RllCountVO();
		Integer lib_idx = 0;
		String  lib_id ="";
		Long updateTime = null;
		try {
			BeanUtils.copyProperties(rllCountVO, vo);
			rllCountVO.setUpdate_time(null);
			lib_idx = rllCountVO.getLib_idx();
			lib_id = rllCountVO.getLib_id();
			updateTime = vo.getUpdateTime();
			rllCountVO.setDevice_type("Service");
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		String datas = null;
		try {
			datas = objectMapper.writeValueAsString(vo);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		RllCountVOs.add(rllCountVO);
		RllCountVOs.addAll(parseRllCountDevJson(datas, lib_idx, lib_id,updateTime));
		String data = "";
		// 将RllCountVOs转化为map
		try {
			String rllCountStr = objectMapper.writeValueAsString(RllCountVOs);
			param.put("data", rllCountStr);
			data = objectMapper.writeValueAsString(param);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 解析采集设备统计信息
	 * 
	 * @param requestURL
	 * @author yeyalin 2017-08-21
	 * @param json
	 * @return List
	 */
	public List<RllCountVO> parseRllCountDevJson(String data, Integer lib_idx,String lib_id,
			Long updateTime) {

		List<RllCountVO> rllCountVOList = new ArrayList<RllCountVO>();
		if (!"".equals(data) && data != null) {
			JSONObject jsonObj = JSONObject.fromObject(data);
			if (jsonObj.get("series") != null) {

				JSONObject seriesObj = JSONObject.fromObject(jsonObj
						.get("series"));
				Iterator<String> it = seriesObj.keys();
				while (it.hasNext()) {
					RllCountVO vo = new RllCountVO();
					String json_key = it.next();
					vo.setDevice_id(json_key);
					JSONObject keyJsonObj = JSONObject.fromObject(seriesObj
							.get(json_key));
					String in_Count = keyJsonObj.getString("in_Count");
					String out_Count = keyJsonObj.getString("out_Count");
					if (in_Count != null && !"".equals(in_Count)) {
						vo.setIn_count(Integer.parseInt(in_Count));
					}
					if (out_Count != null && !"".equals(out_Count)) {
						vo.setOut_count(Integer.parseInt(out_Count));
					}

					vo.setLib_idx(lib_idx);
					vo.setLib_id(lib_id);
					vo.setUpdateTime(updateTime);
					vo.setUpdate_time(null);
					vo.setDevice_type("Device");
					rllCountVOList.add(vo);
				}
			} else {
				LogUtils.info("解析节点配置，返回结果为空");
			}
		}
		return rllCountVOList;

	}

}
