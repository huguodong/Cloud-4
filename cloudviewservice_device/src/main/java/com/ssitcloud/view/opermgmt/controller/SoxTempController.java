package com.ssitcloud.view.opermgmt.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.UserRolePermessionEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.opermgmt.service.SoxTempService;

/** 
 *
 * <p>2016年6月21日 下午4:31:22  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/soxtemp")
public class SoxTempController extends BasicController{
	@Resource
	private SoxTempService soxTempService;
	
	/**
	 * 级安全模板主页面
	 *
	 * <p>2016年6月21日 下午4:47:40 
	 * <p>create by hjc
	 * @return
	 */
	@RequestMapping("/main")
	public String gotSoxTempMain(){
		return "/page/opermgmt/soxtemp-manage";
	}
	
	/**
	 * 根据参数获取鉴权模板分类信息
	 *
	 * <p>2016年6月21日 下午4:46:48 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSoxTempListByParam")
	@ResponseBody
	public ResultEntity getSoxTempListByParam(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			Operator operator = getCurrentUser();
			String operType = operator.getOperator_type();
			
			if (StringUtils.isBlank(json)) {
				json = "{\"operType\":\""+operType+"\"}";
			}else{
				Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
				map.put("operType", operType);
				json = JsonUtils.toJson(map);
			}
			resultEntity = soxTempService.getSoxTempListByParam(json);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 新增模板数据
	 *
	 * <p>2016年6月22日 下午3:42:14 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addSoxTemp")
	@ResponseBody
	public ResultEntity addSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			Map<String, Object> jsonMap = JsonUtils.fromJson(json, Map.class);
			String login_fail_times = jsonMap.get("login_fail_times")==null?"":jsonMap.get("login_fail_times").toString();
			String lock_time = jsonMap.get("lock_time")==null?"":jsonMap.get("lock_time").toString();
			String password_validdays = jsonMap.get("password_validdays")==null?"":jsonMap.get("password_validdays").toString();
			
			if(!login_fail_times.equals("") && Integer.valueOf(login_fail_times)>3200){
				resultEntity.setValue(false, "失败次数不能超过32000", "", "");
				return resultEntity;
			}
			if(!lock_time.equals("") && Integer.valueOf(lock_time)>3200){
				resultEntity.setValue(false, "锁定时长不能超过32000", "", "");
				return resultEntity;
			}
			if(!password_validdays.equals("") && Integer.valueOf(password_validdays)>3200){
				resultEntity.setValue(false, "密码有效时间不能超过32000", "", "");
				return resultEntity;
			}
			
			resultEntity = soxTempService.addSoxTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_ADD_SOXTEMP);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新模板数据
	 *
	 * <p>2016年6月22日 下午3:42:10 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateSoxTemp")
	@ResponseBody
	public ResultEntity updateSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			
			Map<String, Object> jsonMap = JsonUtils.fromJson(json, Map.class);
			String login_fail_times = jsonMap.get("login_fail_times")==null?"":jsonMap.get("login_fail_times").toString();
			String lock_time = jsonMap.get("lock_time")==null?"":jsonMap.get("lock_time").toString();
			String password_validdays = jsonMap.get("password_validdays")==null?"":jsonMap.get("password_validdays").toString();
			
			if(!login_fail_times.equals("") && Integer.valueOf(login_fail_times)>3200){
				resultEntity.setValue(false, "失败次数不能超过32000", "", "");
				return resultEntity;
			}
			if(!lock_time.equals("") && Integer.valueOf(lock_time)>3200){
				resultEntity.setValue(false, "锁定时长不能超过32000", "", "");
				return resultEntity;
			}
			if(!password_validdays.equals("") && Integer.valueOf(password_validdays)>3200){
				resultEntity.setValue(false, "密码有效时间不能超过32000", "", "");
				return resultEntity;
			}
			
			resultEntity = soxTempService.updateSoxTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_UPDATE_SOXTEMP);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除模板
	 *
	 * <p>2016年6月22日 下午4:59:52 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delSoxTemp")
	@ResponseBody
	public ResultEntity delSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			resultEntity = soxTempService.delSoxTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_DELETE_SOXTEMP);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除多个模板
	 *
	 * <p>2016年6月22日 下午4:59:55 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delMultiSoxTemp")
	@ResponseBody
	public ResultEntity delMultiSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			resultEntity = soxTempService.delMultiSoxTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_DELETE_SOXTEMP);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
}
