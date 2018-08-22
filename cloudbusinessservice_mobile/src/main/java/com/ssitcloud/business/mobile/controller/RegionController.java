package com.ssitcloud.business.mobile.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.service.RegionService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.RegionEntity;

/**
 * 地区服务对外接口
 * 
 * @author LXP
 *
 */
@Controller
@RequestMapping("/region")
public class RegionController {
	@Autowired
	private RegionService regionService;

	/**
	 * 根据regi_idx查出对应实体
	 * 
	 * json = { "regi_idx":"4" }
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selRegionByRegionIdx")
	@ResponseBody
	public ResultEntity selRegionByRegionIdx(HttpServletRequest request, String req) {
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if (StringUtils.isNotBlank(json)) {
			RegionEntity regionEntity = JsonUtils.fromJson(json, RegionEntity.class);
			return regionService.selRegionByRegionIdx(regionEntity);
		} else {
			resultEntity.setValue(false, "参数不能为空");
		}
		return resultEntity;
	}

	/**
	 * 根据regi_code查出对应实体 json = { "regi_code": "01010101" }
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selRegionsByRegionCode")
	@ResponseBody
	public ResultEntity selRegionsByRegionCode(HttpServletRequest request, String req) {
		ResultEntity resultEntity = new ResultEntity();
		String regionCodeStr = "";
		String json = request.getParameter("json");
		if (StringUtils.isNotBlank(json)) {
			RegionEntity regionEntity = JsonUtils.fromJson(json, RegionEntity.class);
			regionCodeStr = regionEntity.getRegi_code();
			if (regionCodeStr.length() == 8) {
				regionEntity.setRegi_code(regionCodeStr);
				return regionService.selRegionsByRegionCode(regionEntity);
			} else {
				resultEntity.setValue(false, "regionCode长度不等于8");
			}

		} else {
			resultEntity.setValue(false, "参数不能为空");
		}
		return resultEntity;
	}

	/**
	 * 根据regi_code查出下级实体 json = { "regi_code": "01010101" }
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "selRegionsOnRegionCode" })
	@ResponseBody
	public ResultEntity selRegionsOnRegionCode(HttpServletRequest request, String req) {
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if (StringUtils.isNotBlank(json)) {
			RegionEntity regionEntity = JsonUtils.fromJson(json, RegionEntity.class);
			String regionCode = regionEntity.getRegi_code();
			if (regionCode != null) {
				RegionEntity r = new RegionEntity();
				r.setRegi_code(regionCode);
				return regionService.selRegionsOnRegionCode(r);
			} else {
				resultEntity.setValue(false, "regionCode　is empty");
			}

		} else {
			resultEntity.setValue(false, "参数不能为空");
		}
		return resultEntity;
	}
	
	/**
	 * 区域RegionEntity多个查询 可查所有省、省对应的市、对应的区
	 * 根据传入参数的长度来查不同的实体，长度为0时查出所有省，长度为4时查出对应市，长度为6时查出对应区
	 * json = {
	 * 		 "regi_code": "01010101"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selProCityAreaByRegionCode"})
	@ResponseBody
	public ResultEntity selProCityAreaByRegionCode(HttpServletRequest request,String req){
		ResultEntity resultEntity=new ResultEntity();
		String json = request.getParameter("json");
		if (StringUtils.isNotBlank(json)) {
			RegionEntity regionEntity = JsonUtils.fromJson(json, RegionEntity.class);
			String regionCode = regionEntity.getRegi_code();
			
			RegionEntity r = new RegionEntity();
			r.setRegi_code(regionCode);
			return regionService.selProCityAreaByRegionCode(r);
			
		}else{
			resultEntity.setValue(false, "参数不能为空");
		}
		return resultEntity;
	}
	
	/**
	 * 根据regi_idxs查出对应实体集合
	 * 
	 * json = []
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selRegionsByRegionIdxs"})
	@ResponseBody
	public ResultEntity selRegionByRegionIdxs(HttpServletRequest request,String req){
		ResultEntity resultEntity=new ResultEntity();
		String json = request.getParameter("json");
		if (StringUtils.isNotBlank(json)) {
			if(JSONUtils.mayBeJSON(json)){
				List l = JsonUtils.fromJson(json, List.class);
				return regionService.selRegionsByRegionIdxs(l);
			}
		} else {
			resultEntity.setValue(false, "参数不能为空");
		}
		return resultEntity;
	}
	
	/**
	 * 根据regi_code查出下级实体
	 * json = {
	 * 		 "pCode":"0102",
	 * 		 "cCode":"010201"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selRegionsForNormal"})
	@ResponseBody
	public ResultEntity selRegionsForNormal(HttpServletRequest request,String req){
		ResultEntity resultEntity=new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
//				Map map = JsonUtils.fromJson(json, Map.class);
				return regionService.selRegionsForNormal(json);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		return resultEntity;
	}

}
