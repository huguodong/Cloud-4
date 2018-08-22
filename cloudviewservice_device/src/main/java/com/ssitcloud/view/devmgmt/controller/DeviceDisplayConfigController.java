package com.ssitcloud.view.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceDisplayConfig;
import com.ssitcloud.devmgmt.entity.DeviceDisplayConfigPage;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.LogUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.devmgmt.service.DeviceDisplayConfigService;

/**
 * 
 * @package com.ssitcloud.devmgmt.controller
 * @comment
 * @data 2016年4月18日
 * @author hwl
 */
@Controller
@RequestMapping("/devicedisplay")
public class DeviceDisplayConfigController extends BasicController {

	@Resource
	DeviceDisplayConfigService deviceDisplayConfigService;

	/**
	 * 页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "main" })
	public String main(HttpServletRequest request) {
		return "/page/devmgmt/devicedisplay";
	}

	/**
	 * 查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public ResultEntity queryAll(HttpServletRequest request, String req) {
		DeviceDisplayConfigPage entity = deviceDisplayConfigService.queryAll(req);
		ResultEntity result = new ResultEntity();
		if (entity != null) {
			result.setValue(true, "success", "", entity);
		} else {
			result.setState(false);
		}
		return result;
	}

	/**
	 * 新增、修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public ResultEntity insert(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceDisplayConfigService.insert(req);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_ADD_DEVDISPLAY);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 新增、修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ResultEntity update(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceDisplayConfigService.update(req);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_UPD_DEVDISPLAY);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
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
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceDisplayConfigService.delete(req);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_DEL_DEVDISPLAY);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 根据id来查询设备显示类型
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public ResultEntity findById(HttpServletRequest request, String req) {
		DeviceDisplayConfig entity = deviceDisplayConfigService.findById(req);
		ResultEntity result = new ResultEntity();
		if (entity != null) {
			result.setValue(true, "success", "", entity);
		} else {
			result.setState(false);
		}
		return result;
	}

	/**
	 * 获取设备的类型
	 * 
	 * <p>
	 * 2016年4月25日 下午2:42:56
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDeviceTypes")
	@ResponseBody
	public ResultEntity getDeviceTypes(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String response = deviceDisplayConfigService.getDeviceTypes("");
			resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}
	

	/**
	 * 获取设备类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllDeviceTypes")
	@ResponseBody
	public ResultEntity getAllDeviceTypes(HttpServletRequest request, String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String response = deviceDisplayConfigService.getAllDeviceTypes("");
			resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}
}
