package com.ssitcloud.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.MetadataDeviceTypeAndLogicObjEntity;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.service.MetadataDeviceTypeService;

/**
 * 
 * @comment 设备类型描述元数据表Controller
 * 
 * @author hwl
 * @data 2016年4月8日
 */
@Controller
@RequestMapping("/metadevicetype")
public class MetadataDeviceTypeController {

	@Resource
	MetadataDeviceTypeService metadataDeviceTypeService;

	/**
	 * 添加设备元数据
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddMetaDeviceType" })
	@ResponseBody
	public ResultEntity AddMetaDeviceType(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			MetadataDeviceTypeEntity m = JsonUtils.fromJson(request.getParameter("json"),MetadataDeviceTypeEntity.class);
			int re = metadataDeviceTypeService.addMetadataDeviceType(m);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			result.setRetMessage(re >= 1 ? "IDX:"+m.getMeta_devicetype_idx()+"|设备类型:"+m.getDevice_type()+"|||" : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新设备元数据
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdMetaDeviceType" })
	@ResponseBody
	public ResultEntity UpdMetaDeviceType(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			MetadataDeviceTypeEntity md=JsonUtils.fromJson(request.getParameter("json"),MetadataDeviceTypeEntity.class);
			int re = metadataDeviceTypeService.updMetadataDeviceType(md);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			result.setRetMessage(re >= 1 ? "IDX:"+md.getMeta_devicetype_idx()+"|设备类型:"+md.getDevice_type()+"|||" : "optimistic");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除设备元数据
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteMetaDeviceType" })
	@ResponseBody
	public ResultEntity DeleteMetaDeviceType(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		int re=0;
		StringBuilder idx=new StringBuilder();
		StringBuilder deleteFailIdxs=new StringBuilder();
		try {
			List<MetadataDeviceTypeEntity> list = JsonUtils.fromJson(request.getParameter("json"),new TypeReference<List<MetadataDeviceTypeEntity>>() {});
			for (Iterator<MetadataDeviceTypeEntity> iterator = list.iterator(); iterator.hasNext();) {
				MetadataDeviceTypeEntity mTypeEntity = (MetadataDeviceTypeEntity)iterator.next();
				try {
					re = metadataDeviceTypeService.delMetadataDeviceType(mTypeEntity);
				} catch (org.springframework.dao.DataIntegrityViolationException e) {
					String msg=e.getRootCause().getMessage();
					if(StringUtils.contains(msg, "Cannot delete or update a parent row: a foreign key constraint fails")){
						if(list.size()==1){//单个操作直接抛出异常
							throw new RuntimeException("删除失败,数据正在使用中");
						}else if(list.size()>1){
							deleteFailIdxs.append(mTypeEntity.getMeta_devicetype_idx()).append(",");
						}
					}
				}
				if(re>0){
					idx.append(mTypeEntity.getMeta_devicetype_idx()).append(",");
				}
			}
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			if(idx.length()>0&&deleteFailIdxs.length()>0){
				result.setRetMessage("删除成功的IDX:"+idx.toString().substring(0,idx.length()-1)+"|删除失败的IDX:"+deleteFailIdxs.toString().substring(0,deleteFailIdxs.length()-1));
			}else if(idx.length()>0){
				result.setRetMessage("删除成功的IDX:"+idx.toString().substring(0,idx.length()-1));
				if(deleteFailIdxs.length()==0){
					result.setMessage("ONE");
				}
			}else if(deleteFailIdxs.length()>0){
				result.setRetMessage("删除失败的IDX:"+deleteFailIdxs.toString().substring(0,deleteFailIdxs.length()-1));
			}
			if(list!=null&&list.size()==1){
				result.setMessage("ONE");
			}
		} catch (Exception e) {
			result.setResult(re);
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 条件查询设备元数据
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelectMetaDeviceType" })
	@ResponseBody
	public ResultEntity SelectMetaDeviceType(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			PageEntity p= metadataDeviceTypeService.selPageMetadataDeviceType(map);
			result.setResult(p);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/*@RequestMapping(value = { "SelectType" })
	@ResponseBody
	public ResultEntity SelectType(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<MetadataDeviceTypeEntity> list= metadataDeviceTypeService.selectType();
			result.setResult(list);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}*/
	/**
	 * 查询所有的设备类型
	 *
	 * <p>2016年4月25日 下午3:48:36
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/SelAllMetadataDeviceType")
	@ResponseBody
	public ResultEntity SelAllMetadataDeviceType(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<MetadataDeviceTypeEntity> list = metadataDeviceTypeService.selAllMetadataDeviceType();
			result.setResult(list);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 查询所有的元数据类型 按 device_type 分组
	 * @author lbh
	 * @return
	 */
	@RequestMapping(value={"selAllDeviceTypeGroupByType"})
	@ResponseBody
	public ResultEntity selAllDeviceTypeGroupByType(){
		ResultEntity result = new ResultEntity();
		try {
			List<MetadataDeviceTypeEntity> list = metadataDeviceTypeService.selAllDeviceTypeGroupByType();
			result.setResult(list);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
		
	}
	/**
	 * 根据设备类型 获取对应的  metadata_logic_obj
		数据格式 为：
		{
			[
				{
					idx:"",
					deviceType:"",
					[metadata_logic_obj1,metadata_logic_obj2]
				},
				{
					.....
				}
			]
		}
		@param req={}  空
	 * @return
	 */
	@RequestMapping(value={"queryDeviceTypeLogicObj"})
	@ResponseBody
	public ResultEntity queryDeviceTypeLogicObj(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			List<MetadataDeviceTypeAndLogicObjEntity> list = metadataDeviceTypeService.queryDeviceTypeLogicObj(req);
			result.setResult(list);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	@RequestMapping(value={"queryDeviceTypeByName"})
	@ResponseBody
	public ResultEntity queryDeviceTypeByName(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			if(JSONUtils.mayBeJSON(req)){
				JSONObject obj=JSONObject.fromObject(req);
				MetadataDeviceTypeEntity entity=new MetadataDeviceTypeEntity();
				entity.setDevice_type(obj.optString("device_type"));
				entity = metadataDeviceTypeService.queryDeviceTypeByName(entity);
				if(entity!=null){
					result.setResult(entity);
					result.setMessage(Const.SUCCESS);
					result.setState(true);
				}else{
					result.setMessage(Const.FAILED);
					result.setState(false);
				}
			}else {
				result.setMessage(Const.FAILED);
				result.setState(false);
			}
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 通过device_type查出meta_devicetype_idx
	 * <p>2017年3月6日 下午6:25
	 * <p>create by shuangjunjie
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("selectMetaDevTypeIdxByType")
	@ResponseBody
	public ResultEntity selectMetaDevTypeIdxByType(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			if(JSONUtils.mayBeJSON(req)){
				MetadataDeviceTypeEntity entity = JsonUtils.fromJson(req, MetadataDeviceTypeEntity.class);
				List<Integer> metaDeviceTypeIdxs = metadataDeviceTypeService.selectMetaDevTypeIdxByType(entity);
				result.setState(true);
				result.setMessage(Const.SUCCESS);
				result.setResult(metaDeviceTypeIdxs);
			}else {
				result.setMessage(Const.FAILED);
				result.setState(false);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	
}
