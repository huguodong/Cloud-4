package com.ssitcloud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.RegionEntity;
import com.ssitcloud.service.RegionService;

@Controller
@RequestMapping(value={"/region"})
public class RegionController {
	@Resource
	private RegionService regionService;
	

	/**
	 * 根据regi_idx查出对应实体
	 * 
	 * json = {
	 * 		"regi_idx":"4"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selRegionByRegionIdx"})
	@ResponseBody
	public ResultEntity selRegionByRegionIdx(HttpServletRequest request,String req){
		ResultEntity resultEntity=new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RegionEntity regionEntity = JsonUtils.fromJson(json, RegionEntity.class);
				regionEntity = regionService.selRegionByRegionIdx(regionEntity);
				resultEntity.setValue(true, "success", "", regionEntity);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据regi_code查出对应实体
	 * json = {
	 * 		 "regi_code": "01010101"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selRegionsByRegionCode"})
	@ResponseBody
	public ResultEntity selRegionsByRegionCode(HttpServletRequest request,String req){
		ResultEntity resultEntity=new ResultEntity();
		String regionCode = "";
		String regionCodeStr = "";
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RegionEntity regionEntity = JsonUtils.fromJson(json, RegionEntity.class);
				regionCodeStr = regionEntity.getRegi_code();
				if(regionCodeStr.length() == 8){
					regionCode = regionCodeStr.substring(0, 6);
					regionEntity.setRegi_code(regionCode);
					List<RegionEntity> regionEntityList = regionService.selRegionsByRegionCode(regionEntity);
					resultEntity.setValue(true, "success", "", regionEntityList);
				}else{
					resultEntity.setValue(false, "regionCode长度不等于8");
				}
				
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据regi_code查出下级实体
	 * json = {
	 * 		 "regi_code": "01010101"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selRegionsOnRegionCode"})
	@ResponseBody
	public ResultEntity selRegionsOnRegionCode(HttpServletRequest request,String req){
		ResultEntity resultEntity=new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RegionEntity regionEntity = JsonUtils.fromJson(json, RegionEntity.class);
				String regionCode = regionEntity.getRegi_code();
				if(regionCode != null){
					RegionEntity r = new RegionEntity();
					r.setRegi_code(regionCode);
					List<RegionEntity> regionEntityList = regionService.selRegionsByRegionCode(r);
					resultEntity.setValue(true, "success", "", regionEntityList);
				}else{
					resultEntity.setValue(false, "regionCode　is empty");
				}
				
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
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
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RegionEntity regionEntity = JsonUtils.fromJson(json, RegionEntity.class);
				String regionCode = regionEntity.getRegi_code();
				
				RegionEntity r = new RegionEntity();
				r.setRegi_code(regionCode);
				List<RegionEntity> regionEntityList = regionService.selProCityAreaByRegionCode(r);
				resultEntity.setValue(true, "success", "", regionEntityList);
				
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据地区码列表查询地区实体
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selRegions"})
	@ResponseBody
	public ResultEntity selRegions(HttpServletRequest request,String req){
		ResultEntity resultEntity=new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				Map<String, Object> m = JsonUtils.fromJson(json, Map.class);
				List<String> list= (List<String>) m.get("regi_codes");
				List<RegionEntity> regionEntityList = regionService.selRegions(list);
				resultEntity.setValue(true, "success", "", regionEntityList);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}

    /**
     * 根据馆IDX查询所有设备的地区分布
     * 2017-08-24 lqw
     * @param request
     * @param req
     * @return
     */
	@RequestMapping(value={"/selRegionsByLibidx"})
    @ResponseBody
    public ResultEntity selRegionsByLibidx(HttpServletRequest request,String req){
	    ResultEntity result = new ResultEntity();
        try {
            if (StringUtils.isNotBlank(req)) {
                List<String> list=new ArrayList<>();
                Map<String, Object> m = JsonUtils.fromJson(req, Map.class);
                String str = (String)m.get("idx");
                String[] sarr = str.split(",");
                for(int i=0,z=sarr.length;i<z;i++){
                    list.add(sarr[i]);
                }
                List<RegionEntity> regionEntityList = regionService.selRegionsByLibidx(list);
                result.setValue(true, "success", "", regionEntityList);
            }else{
                result.setValue(false, "参数不能为空");
            }
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
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
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
//				if(JSONUtils.mayBeJSON(json)){
					Map map = new HashMap();
					List regionIdxs = JsonUtils.fromJson(json, List.class);
					map.put("regionIdxs", regionIdxs);
					List<RegionEntity> l = regionService.selRegionsByRegionIdxs(map);
					resultEntity.setValue(true, "success", "", l);
//				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
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
				Map map = JsonUtils.fromJson(json, Map.class);
				List<RegionEntity> regionEntityList = regionService.selRegionsForNormal(map);
				resultEntity.setValue(true, "success", "", regionEntityList);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据deviceIdx查出下级实体
	 * json = {
	 * 		 "deviceIdx":123456
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selRegionByDeviceidx"})
	@ResponseBody
	public ResultEntity selRegionCodeByDeviceidx(HttpServletRequest request,String req){
		ResultEntity resultEntity=new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				JSONObject jsonObj = JSONObject.fromObject(json);
				Map<String,String> params = new HashMap<String,String>();
				if(jsonObj.get("deviceIdx")!=null){
					params.put("deviceIdx", jsonObj.getString("deviceIdx"));
				}else{
					resultEntity.setValue(false, "参数不能为空");
					return resultEntity;
				}
				RegionEntity regionEntityList = regionService.selRegionCodeByDeviceidx(params);
				resultEntity.setValue(true, "success", "", regionEntityList);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 查询所有的地区信息
	 *
	 * <p>2017年9月7日 上午10:48:35 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/queryAllRegion")
	@ResponseBody
	public ResultEntity queryAllRegion (HttpServletRequest request, String json){
		ResultEntity resultEntity=new ResultEntity();
		try {
			List<RegionEntity> regionEntityList = regionService.queryAllRegion();
			ObjectMapper mapper = new ObjectMapper();
			//保存代码对应的城市节点
			ObjectNode codeNode = mapper.createObjectNode();
			
			for (RegionEntity regionEntity : regionEntityList) {
				String code = "";
				String place = "";
				//国家
				if (regionEntity.getRegi_code().length() == 2) {
					code = "root";
					place = regionEntity.getRegi_nation();
				}else if(regionEntity.getRegi_code().length() == 4){
					code = regionEntity.getRegi_code().substring(0,2);
					place = regionEntity.getRegi_province();
				}else if(regionEntity.getRegi_code().length() == 6){
					code = regionEntity.getRegi_code().substring(0, 4);
					place = regionEntity.getRegi_city();
				}else if(regionEntity.getRegi_code().length() == 8){
					code = regionEntity.getRegi_code().substring(0, 6);
					place = regionEntity.getRegi_area();
				}
				ArrayNode arrayNode = (ArrayNode) codeNode.get(code);
				if(arrayNode == null){
					arrayNode = mapper.createArrayNode();
				}
				
				ObjectNode cityNode = mapper.createObjectNode();
				cityNode.put("code", regionEntity.getRegi_code());
				cityNode.put("name", place);
				arrayNode.add(cityNode);
				codeNode.set(code, arrayNode);
			}
			resultEntity.setValue(true, "", "", codeNode);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
}
