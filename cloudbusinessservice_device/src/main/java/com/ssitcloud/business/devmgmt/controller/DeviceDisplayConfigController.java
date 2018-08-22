package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.devmgmt.service.DeviceDisplayConfigService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @package com.ssitcloud.devmgmt.controller
 * @comment
 * @data 2016年4月18日
 * @author hwl
 */
@Controller
@RequestMapping("/devicedisplay")
public class DeviceDisplayConfigController{

	@Resource
	DeviceDisplayConfigService deviceDisplayConfigService;

	/**
	 * 查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public ResultEntity queryAll(HttpServletRequest request,String req) {
		return deviceDisplayConfigService.queryAll(req);
	}

	/**
	 * 新增、修改
	 * @param request
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public ResultEntity insert(HttpServletRequest request,String req) {
		return deviceDisplayConfigService.insert(req);
	}
	
	/**
	 * 新增、修改
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ResultEntity update(HttpServletRequest request,String req) {
		return deviceDisplayConfigService.update(req);
	}

	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResultEntity delete(HttpServletRequest request,String req) {
		return deviceDisplayConfigService.delete(req);
	}

	/**
	 * 根据id来查询设备显示类型
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public ResultEntity findById(HttpServletRequest request,String req) {
		return deviceDisplayConfigService.findById(req);
	}
	
	/**
	 * 根据id来查询设备显示类型
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/findByTypeId")
	@ResponseBody
	public ResultEntity findByTypeId(HttpServletRequest request,String req) {
		return deviceDisplayConfigService.findByTypeId(req);
	}
	
	/**
	 * 获取设备类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDeviceTypes")
	@ResponseBody
	public ResultEntity getDeviceTypes(HttpServletRequest request,String req){
		return deviceDisplayConfigService.getDeviceTypes(req);
	}
}
