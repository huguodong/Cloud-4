package com.ssitcloud.view.upgrade.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.upgrade.service.UpgradeService;

/**
 * 升级模板管理
 * <p>
 * 2016年6月8日 上午9:06:13
 * 
 * @author gcy
 * 
 */
@Controller
@RequestMapping("/upgrade")
public class UpgradeController extends BasicController{

	@Resource
	UpgradeService upgradeService;

	/**
	 * 升级管理主页
	 * 
	 * <p>
	 * 2016年6月8日 上午9:06:57
	 * <p>
	 * create by gcy
	 * 
	 * @return
	 */
	@RequestMapping("/main")
	public String goToMain() {
		return "/page/upgrade/upgrade-manage";
	}

	/**
	 * 分页查询升级模板信息
	 * 
	 * <p>
	 * 2016年6月12日 上午9:14:57
	 * <p>
	 * create by gcy
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryUpgradeByParam")
	@ResponseBody
	public ResultEntity queryUpgradeByParam(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			Map<String, String> param = new HashMap<String, String>();
			if (!StringUtils.isBlank(req)) {
				param = JsonUtils.fromJson(req, Map.class);
				if (!StringUtils.isBlank(param.get("type"))) {
					if (param.get("type").equals("patch_version")) {
						param.remove("type");
						param.put("patch_version", param.get("keyword"));
						param.remove("keyword");
					} else if (param.get("type").equals("patch_desc")) {
						param.remove("type");
						param.put("patch_desc", param.get("keyword"));
						param.remove("keyword");
					}
				}else{
					if(param.containsKey("type"))
						param.remove("type");
					if(param.containsKey("keyword"))
						param.remove("keyword");
				}
			} else {
				if(param.containsKey("type"))
					param.remove("type");
				if(param.containsKey("keyword"))
					param.remove("keyword");
			}

			resultEntity = upgradeService.queryUpgradeByParam(JsonUtils
					.toJson(param));
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
		
	}
	
	/**
	 * 删除单条信息
	 *
	 * <p>2016年7月30日 下午5:56:06 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delUpgrade")
	@ResponseBody
	public ResultEntity delUpgrade(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {

			result = upgradeService.delPatchInfo(request.getParameter("json"));
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DEL_PATCHINFO);

		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[0]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 删除单条信息
	 *
	 * <p>2016年7月30日 下午5:56:06 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delMultiUpgrade")
	@ResponseBody
	public ResultEntity delMultiUpgrade(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			
			result = upgradeService.delMultiPatchInfo(request.getParameter("json"));
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DEL_PATCHINFO);

		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[0]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	

	@RequestMapping("/AddUpgrade")
	@ResponseBody
	public ResultEntity AddPatchInfo(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			result = upgradeService.addPatchInfo(request.getParameter("json"));
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_ADD_PATCHINFO);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[0]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@RequestMapping("/UpdUpgrade")
	@ResponseBody
	public ResultEntity UpdPatchInfo(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {

			result = upgradeService.updPatchInfo(request.getParameter("json"));
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_UPD_PATCHINFO);

		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[0]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
