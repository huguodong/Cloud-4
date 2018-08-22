package com.ssitcloud.view.devmgmt.controller;
 
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.devmgmt.service.DeviceViewConfigService;

@Controller
@RequestMapping("deviceViewConfig")
public class DeviceViewConfigControll extends BasicController{
	@Resource
	private DeviceViewConfigService deviceViewConfigService;
	
	/**
	 * 跳到设置页面
	 * @author yanyong
	 * @date 20170808
	 */
	@RequestMapping("main")
	public String configDeviceView(){
		return "/page/devmgmt/deviceViewConfig";
	}
	
	/**
	 * 查询设备前端显示配置参数
	 * @author yanyong
	 * @date 20170808
	 */
	@RequestMapping("queryDeviceViewConfig")
	@ResponseBody
	public ResultEntity queryDeviceViewConfig(String req){
		ResultEntity resultEntity = deviceViewConfigService.queryDeviceViewConfigFieldLabel(req);
		return resultEntity;
	}
	
	
	/**
	 * 根据fieldset 查询label
	 * @author yanyong
	 * @date 20170808
	 */
	@RequestMapping("queryLabelByFieldset")
	@ResponseBody
	public ResultEntity queryLabelByFieldset(String req){
		return deviceViewConfigService.queryLabelByFieldset(req);
	}
	
	
	/**
	 * 更新设备配置
	 * @author yanyong
	 * @date 20170808
	 * @param {id:1,view_config_id:2,library_idx：1,content:{label:xx,fieldset_label...}}
	 */
	@RequestMapping("updateDeviceViewConfig")
	@ResponseBody
	public ResultEntity updateDeviceViewConfig(String req){
		System.out.println(req);
		return deviceViewConfigService.updateDeviceViewConfig(req);
	}
	
	
	/**
	 * 查询已经设置的参数
	 * @author yanyong
	 * @date 20170808
	 * @param {"library_idx":xxx}
	 */
	@RequestMapping("queryDeviceViewConfigSet")
	@ResponseBody
	public ResultEntity queryDeviceViewConfigSet(String req){
		ResultEntity resultEntity = deviceViewConfigService.queryDeviceViewConfigSet(req);
		return resultEntity;
	}
	
}
