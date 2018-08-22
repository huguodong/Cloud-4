package com.ssitcloud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
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
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.entity.DeviceGroupEntity;
import com.ssitcloud.service.DeviceGroupService;

/**
 * 
 * @comment 设备组表Controller
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Controller
@RequestMapping("/devicegroup")
public class DeviceGroupController {

	@Resource
	DeviceGroupService deviceGroupService;

	/**
	 * 添加设备组数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddDeviceGroup" })
	@ResponseBody
	public ResultEntity AddDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			DeviceGroupEntity d = JsonUtils.fromJson(request.getParameter("json"), DeviceGroupEntity.class);
			List<DeviceGroupEntity> deviceGroups=deviceGroupService.selectgroupBylibIdxAndDeviceGroupId(d);
			if(CollectionUtils.isNotEmpty(deviceGroups)){
				result.setRetMessage("设备组ID已经存在，请修改");
				return result;
			}
			int re = deviceGroupService.addDeviceGroup(d);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			result.setRetMessage(re >= 1 ? "馆ID:"+d.getLibrary_idx()+"IDX:"+d.getDevice_group_idx()+"|组名:"+d.getDevice_group_name()+"||": Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新设备组数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdDeviceGroup" })
	@ResponseBody
	public ResultEntity UpdDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			DeviceGroupEntity d=JsonUtils.fromJson(request.getParameter("json"), DeviceGroupEntity.class);
			deviceGroupService.selectByDeviceGroupIdx(d);
			
			List<DeviceGroupEntity> deviceGroups=deviceGroupService.selectgroupBylibIdxAndDeviceGroupId(d);
		    if (CollectionUtils.isNotEmpty(deviceGroups)) {
		        for (DeviceGroupEntity deviceGroup : deviceGroups){
		          if (deviceGroup.getDevice_group_idx() != d.getDevice_group_idx()) {
		            result.setRetMessage("该图书馆中的设备ID:"+d.getDevice_group_id()+"已经存在，请修改");
		            return result;
		          }
		        }
		     }
			int re = deviceGroupService.updDeviceGroup(d);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			result.setRetMessage(re >= 1 ? "馆ID:"+d.getLibrary_idx()+"IDX:"+d.getDevice_group_idx()+"|组名:"+d.getDevice_group_name()+"||": "optimistic");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除设备组数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteDeviceGroup" })
	@ResponseBody
	public ResultEntity DeleteDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		int re = 0;
		StringBuilder idx=new StringBuilder();
		StringBuilder deleteFailIdxs=new StringBuilder();
		try {
			List<DeviceGroupEntity> list = JsonUtils.fromJson(request.getParameter("json"),new TypeReference<List<DeviceGroupEntity>>() {});
			for (Iterator<DeviceGroupEntity> iterator = list.iterator(); iterator.hasNext();) {
				DeviceGroupEntity dEntity = (DeviceGroupEntity)iterator.next();
				int d=0;
				try {
					d=deviceGroupService.delDeviceGroup(dEntity);
				} catch (org.springframework.dao.DataIntegrityViolationException e) {
					String msg=e.getRootCause().getMessage();
					LogUtils.error(msg, e);
					if(StringUtils.contains(msg, "Cannot delete or update a parent row: a foreign key constraint fails")){
						if(list.size()==1){//单个操作直接抛出异常
							throw new RuntimeException("删除失败,数据正在使用中");
						}else if(list.size()>1){
							deleteFailIdxs.append(dEntity.getDevice_group_idx()).append(",");
						}
					}
				}
				if(d>0){
					idx.append(dEntity.getDevice_group_idx()).append(",");
				}
				re+=d;
			}
			result.setState(re >= 1 ? true : false);//至少有一个删除成功的
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			if(CollectionUtils.isNotEmpty(list)&&list.size()==1){//单个删除 
				result.setMessage("ONE");
				result.setRetMessage(re >= 1 ?"删除成功的IDX:"+idx.toString().substring(0, idx.length()-1): Const.FAILED);
			}else if(CollectionUtils.isNotEmpty(list)&&list.size()>1){//批量删除
				if(deleteFailIdxs.length()>0){
					result.setResult("删除失败的IDX:"+deleteFailIdxs.toString().substring(0, deleteFailIdxs.length()-1));
				}else{
					result.setMessage("ONE");//没有删除失败的 加上标记，用于页面JS判断
				}
				if(idx.length()>0){
					result.setRetMessage(re >= 1 ? "删除成功的IDX:"+idx.toString().substring(0, idx.length()-1): Const.FAILED);
				}
			}
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 根据条件查询设备组数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelectDeviceGroup" })
	@ResponseBody
	public ResultEntity SelectDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			PageEntity p= deviceGroupService.selPageDeviceGroup(map);
			result.setResult(p);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "SelectGroup" })
	@ResponseBody
	public ResultEntity SelectGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		Map<String, String> map = JsonUtils.fromJson(request.getParameter("json"), Map.class);
		List<Integer> libraryIdxs = new ArrayList<>();
		if(map!=null ) {
			if(StringUtils.isNotBlank(map.get("library_idx"))){
				libraryIdxs.add(Integer.valueOf(map.get("library_idx")));
				//子馆
				if(StringUtils.isNotBlank(map.get("sub_idx"))){
					String[] subArr = map.get("sub_idx").split(",");
					for (String string : subArr) {
						libraryIdxs.add(Integer.valueOf(string));
					}
				}
			}
		}
//		DeviceGroupEntity dGroupEntity = JsonUtils.fromJson(request.getParameter("json"), DeviceGroupEntity.class);
//		if(dGroupEntity==null){
//			dGroupEntity=new DeviceGroupEntity();
//		}
		try {	
			List<DeviceGroupEntity> list = new ArrayList<>();
			if(libraryIdxs.size()>0){
//				list = deviceGroupService.selectgroupBylib_idx(dGroupEntity.getLibrary_idx());
				list = deviceGroupService.selectgroupBylib_idxs(libraryIdxs);
			}else {
				list= deviceGroupService.selectgroup();
			}
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
	//
	@RequestMapping(value = { "SelectGroupByParam" })
	@ResponseBody
	public ResultEntity SelectGroupByParam(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			DeviceGroupEntity deviceGroupEntity=new DeviceGroupEntity();
			if(JSONUtils.mayBeJSON(req)){
				deviceGroupEntity=JsonUtils.fromJson(req, DeviceGroupEntity.class);
			}
			List<DeviceGroupEntity> list= deviceGroupService.selbyidDeviceGroup(deviceGroupEntity);
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
	 * 根据设备IDX查询设备分组信息
	 * req={
	 * 	device_idx:"xxx"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "selectGroupByDeviceIdx" })
	@ResponseBody
	public ResultEntity selectGroupByDeviceIdx(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceGroupService.selectGroupByDeviceIdx(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
