package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.DeviceDisplayConfig;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.entity.page.DeviceDisplayConfigPage;
import com.ssitcloud.service.DeviceDisplayConfigService;

/**
 * 
 * @package com.ssitcloud.devmgmt.controller
 * @comment
 * @data 2016年4月18日
 * @author hwl
 */
@Controller
@RequestMapping("/devicedisplay")
public class DeviceDisplayConfigController {

	@Resource
	DeviceDisplayConfigService deviceDisplayConfigService;

	/**
	 * 查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public ResultEntityF<DeviceDisplayConfigPage> queryAll(HttpServletRequest request, String req) {
		ResultEntityF<DeviceDisplayConfigPage> result = new ResultEntityF<DeviceDisplayConfigPage>();
		DeviceDisplayConfigPage page = new DeviceDisplayConfigPage();

		if (StringUtils.hasText(req)&&JSONUtils.mayBeJSON(req)) {
			page = JsonUtils.fromJson(req, DeviceDisplayConfigPage.class);
		}else{
			return null;
		}
		page.setDoAount(true);
		page = deviceDisplayConfigService.queryAll(page);
		result.setResult(page);
		result.setState(true);
		return result;
	}

	/**
	 * 新增
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public ResultEntity insert(HttpServletRequest request, String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			DeviceDisplayConfig entity = deviceDisplayConfigService.insert(req);
			String retMessage="|风格类型idx："+entity.getDisplay_type_idx()+"|风格类型名："+entity.getDisplay_type_name()+"|设备类型idx："+entity.getDevice_type_idx()+"|";
			if (entity != null) {
				resultEntity.setValue(true, "success", "", entity);
			} else {
				resultEntity.setValue(false, "无记录");
			}
			resultEntity.setRetMessage(retMessage);
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ResultEntity update(HttpServletRequest request, String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = deviceDisplayConfigService.update(req);
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResultEntity delete(HttpServletRequest request, String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			int count = deviceDisplayConfigService.delete(req);
			String retMessage="|风格类型idx："+req+"|";
			if (count > 0) {
				resultEntity.setValue(true, "success", "", count);
			} else {
				resultEntity.setValue(false, "删除失败");
			}
			resultEntity.setRetMessage(retMessage);
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}

	/**
	 * 根据id来查询设备显示类型
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public DeviceDisplayConfig findById(HttpServletRequest request, String req) {
		DeviceDisplayConfig page = new DeviceDisplayConfig();
		if (StringUtils.hasText(req)&&JSONUtils.mayBeJSON(req)) {
			page = JsonUtils.fromJson(req, DeviceDisplayConfig.class);
		}else{
			return null;
		}
		return deviceDisplayConfigService.findById(page);
	}

	/**
	 * 根据设备类型id来查询设备显示类型
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/findByTypeId")
	@ResponseBody
	public DeviceDisplayConfig findByTypeId(HttpServletRequest request, String req) {
		DeviceDisplayConfig page = new DeviceDisplayConfig();
		if (StringUtils.hasText(req)&&JSONUtils.mayBeJSON(req)) {
			page = JsonUtils.fromJson(req, DeviceDisplayConfig.class);
		}else{
			return null;
		}
		page = deviceDisplayConfigService.findByTypeId(page);
		return page;
	}

	/**
	 * 获取设备类型
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDeviceTypes")
	@ResponseBody
	public ResultEntity getDeviceTypes(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			List<MetadataDeviceTypeEntity> list = deviceDisplayConfigService.getDeviceTypes(req);
			result.setResult(list);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

}
