package com.ssitcloud.dbstatistics.rllcount.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.BeanUtilEx;
import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.dbstatistics.rllcount.service.RllCountService;
import com.ssitcloud.devmgmt.entity.RllCountEntity;
import com.ssitcloud.devmgmt.entity.RllCountVO;

@Controller
@RequestMapping(value = { "rllCount" })
public class RllCountController {
	@Resource
	private RllCountService rllCountService;

	/**
	 * @param request
	 * @param req
	 * @return 格式如下： req={ "data": [ { "device_idx": 11, "startTime":
	 *         1507618286412, "everyday_idx": 11, "out_count": 20, "updateTime":
	 *         1507618286412, "everymonth_idx": 0, "endTime": 1507618286412,
	 *         "in_count": 200, "everyweek_idx": 0, "lib_idx": 11,
	 *         "everyyear_idx": 0 }, { "device_idx": 22, "startTime":
	 *         1507618286412, "everyday_idx": 22, "out_count": 20, "updateTime":
	 *         1507618286412, "everymonth_idx": 0, "endTime": 1507618286412,
	 *         "in_count": 200, "everyweek_idx": 0, "lib_idx": 22,
	 *         "everyyear_idx": 0 } ] }
	 */
	@RequestMapping(value = { "saveRllCountData" })
	@ResponseBody
	public ResultEntity saveRllCountData(HttpServletRequest request, String req) {

		// 解析统计数据
		List<RllCountVO> rllCountVOs = parseRllCountJson(req);
		// 将统计数据转换成实体类
		List<RllCountEntity> rllCountEntitys = copyField(rllCountVOs);
		boolean flag = true;
		String messsage = "成功";
		int count = rllCountService
				.insertVisitorsEveryDayBatch(rllCountEntitys);
		if (count != rllCountEntitys.size()) {
			flag = false;
			messsage = "失败";
		}
		return new ResultEntity(flag, messsage);
	}
	/**
	 * 将统计数据转换成实体类
	 * 
	 * @param rllCountEntitys
	 * @return
	 */
	private List<RllCountEntity> copyField(List<RllCountVO> rllCountVOs) {
		List<RllCountEntity> rllCountEntitys = new ArrayList<RllCountEntity>();
		for (RllCountVO vo : rllCountVOs) {
			RllCountEntity entity = new RllCountEntity();
			try {
				BeanUtilEx.copyProperties(entity, vo);
				rllCountEntitys.add(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rllCountEntitys;
	}

	/**
	 * 解析数据
	 * 
	 * @param data
	 * @return
	 */
	public List<RllCountVO> parseRllCountJson(String data) {

		List<RllCountVO> rllCountVOs = new ArrayList<RllCountVO>();
		if (!"".equals(data) && data != null) {
			JSONObject jsonObj = JSONObject.fromObject(data);
			if (jsonObj.get("data") != null) {

				JSONArray resultArr = JSONArray.fromObject(jsonObj.get("data"));

				for (int i = 0; i < resultArr.size(); i++) {
					RllCountVO vo = (RllCountVO) JSONObject.toBean(
							(JSONObject) resultArr.get(i), RllCountVO.class);
					// vo.setUpdateTime(1000l);
					rllCountVOs.add(vo);
				}
			} else {
				LogUtils.info("解析人流量数据失败，原因：传过来的人流量统计数据为空");
			}
		}
		return rllCountVOs;

	}

}
