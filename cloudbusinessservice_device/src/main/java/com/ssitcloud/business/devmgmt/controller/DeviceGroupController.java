package com.ssitcloud.business.devmgmt.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.devmgmt.entity.DeviceGroupEntity;
import com.ssitcloud.business.devmgmt.service.DeviceGroupService;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;

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
		ResultEntity libresult = new ResultEntity();
		try {
			String req = request.getParameter("json");
			//解析json数据，转换成LibraryEntity和DeviceGroupEntity，进行操作
			DeviceGroupEntity dEntity = JsonUtils.fromJson(req, DeviceGroupEntity.class);
			LibraryEntity lEntity = new LibraryEntity();
			lEntity.setLib_id(dEntity.getLib_id());
			String libinfo = JsonUtils.toJson(lEntity);
			String lib_resp = deviceGroupService.SelLibrary(libinfo);
			libresult = JsonUtils.fromJson(lib_resp, ResultEntity.class);
			if(libresult.getState()){
				if(libresult.getResult()!=null&&((List<?>)libresult.getResult()).size()>0){
					String libidx =JsonUtils.toJson(libresult.getResult());
					ObjectMapper mapper = new ObjectMapper();
					LibraryEntity libraryEntity = mapper.readValue(libidx, LibraryEntity[].class)[0];
					dEntity.setLibrary_idx(libraryEntity.getLibrary_idx());
					String resps = deviceGroupService.AddDeviceGroup(JsonUtils.toJson(dEntity));
					result = JsonUtils.fromJson(resps, ResultEntity.class);
				}else{
					//根据图书馆ID获取不到图书馆IDX
					result.setRetMessage("图书馆ID填写不正确");
				}
				//LibraryEntity libEntity = JsonUtils.fromJson(JsonUtils.toJson(libresult.getResult()),LibraryEntity.class);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 
	 * @methodName 
	 * @returnType ResultEntity
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdDeviceGroup" })
	@ResponseBody
	public ResultEntity UpdDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		ResultEntity libresult = new ResultEntity();
		try {
			String req = request.getParameter("json");
			//解析json数据，转换成LibraryEntity和DeviceGroupEntity，进行操作
			DeviceGroupEntity dEntity = JsonUtils.fromJson(req, DeviceGroupEntity.class);
			LibraryEntity lEntity = new LibraryEntity();
			lEntity.setLib_id(dEntity.getLib_id());
			String libinfo = JsonUtils.toJson(lEntity);
			String lib_resp = deviceGroupService.SelLibrary(libinfo);
			libresult = JsonUtils.fromJson(lib_resp, ResultEntity.class);
			if(libresult.getState()){
				String libidx =JsonUtils.toJson(libresult.getResult());
				ObjectMapper mapper = new ObjectMapper();
				LibraryEntity[] libraryEntitys = mapper.readValue(libidx, LibraryEntity[].class);
				if(libraryEntitys.length==0){
					result.setRetMessage("请输入正确的图书馆ID");
					return result;
				}
				LibraryEntity libraryEntity=libraryEntitys[0];
				//LibraryEntity libEntity = JsonUtils.fromJson(JsonUtils.toJson(libresult.getResult()),LibraryEntity.class);
				dEntity.setLibrary_idx(libraryEntity.getLibrary_idx());
				String resps = deviceGroupService.UpdDeviceGroup(JsonUtils.toJson(dEntity));
				result = JsonUtils.fromJson(resps, ResultEntity.class);
			}
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
	
		try {
					
			String resps = deviceGroupService.DeleteDeviceGroup(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
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
		ResultEntity lib_result = new ResultEntity();
		try {
			String req =request.getParameter("json");
			Map<String, String> map = new HashMap<>();
			map.put("json", req);
			map.put("page", request.getParameter("page"));
			//获取图书馆lib_id
			DeviceGroupEntity dgEntity = JsonUtils.fromJson(req, DeviceGroupEntity.class);
			LibraryEntity libEntity = new LibraryEntity();
			libEntity.setLibrary_idx(dgEntity.getLibrary_idx());
			String libinfo = JsonUtils.toJson(libEntity);
			String lib_resp = deviceGroupService.SelLibrary(libinfo);
			lib_result = JsonUtils.fromJson(lib_resp, ResultEntity.class);
			List<LibraryEntity> liblist = JsonUtils.fromJson(JsonUtils.toJson(lib_result.getResult()), new TypeReference<List<LibraryEntity>>() {});
			Map<Integer, LibraryEntity> libMap = new HashMap<>();
			for (LibraryEntity libraryEntity : liblist) {
				libMap.put(libraryEntity.getLibrary_idx(), libraryEntity);
			}
			//查询设备分组信息
			String resps = deviceGroupService.SelDeviceGroup(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			PageEntity page = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), PageEntity.class);
			List<DeviceGroupEntity> list = JsonUtils.fromJson(JsonUtils.toJson(page.getRows()), new TypeReference<List<DeviceGroupEntity>>() {});
			for (DeviceGroupEntity deviceGroupEntity : list) {
				LibraryEntity l = libMap.get(deviceGroupEntity.getLibrary_idx());
				if (l != null) {
					deviceGroupEntity.setLib_id(l.getLib_id());
				}
			}
			//设备分组信息获取lib_id
//			for (Iterator<DeviceGroupEntity> iterator = list.iterator(); iterator.hasNext();) {
//				DeviceGroupEntity dEntity = (DeviceGroupEntity)iterator.next();
//				for (Iterator<LibraryEntity> libiterator =liblist.iterator(); libiterator.hasNext();){
//					LibraryEntity lEntity = (LibraryEntity)libiterator.next();
//					if(dEntity.getLibrary_idx() == lEntity.getLibrary_idx())
//						dEntity.setLib_id(lEntity.getLib_id());
//				}
//			}
			page.setRows(list);
			result.setResult(page);
			}catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "SelectGroup" })
	@ResponseBody
	public ResultEntity SelectGroup(HttpServletRequest request) {

		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceGroupService.SelectGroup(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		}catch (Exception e) {
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
			 result=deviceGroupService.SelectGroupByParam(req);
			
		}catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	@RequestMapping(value = { "selectGroupByDeviceIdx" })
	@ResponseBody
	public ResultEntity selectGroupByDeviceIdx(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			 result=deviceGroupService.selectGroupByDeviceIdx(req);
		}catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
