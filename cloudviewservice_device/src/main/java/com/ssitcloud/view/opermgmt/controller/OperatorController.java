package com.ssitcloud.view.opermgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.LogUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.opermgmt.service.OperatorService;

/** 
 * 操作员管理
 * <p>2016年6月8日 上午9:06:13  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/operator")
public class OperatorController extends BasicController{
	/* 重置密码的值**/
	@Value("${resetPassword}")
	private String resetPassword;
	
	@Resource
	OperatorService operatorService;
	
	/**
	 * 操作员管理主页
	 *
	 * <p>2016年6月8日 上午9:06:57 
	 * <p>create by hjc
	 * @return
	 */
	@RequestMapping("/main")
	public String goToMain(){
		return "/page/opermgmt/operator-manage";
	}
	
	@RequestMapping("/getDefaultPwd")
	@ResponseBody
	public ResultEntity getDefaultPwd(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(resetPassword)) {
				resultEntity.setValue(true, "888888", "", "");
			}else{
				resultEntity.setValue(true, resetPassword, "", "");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 分页查询操作员信息
	 *
	 * <p>2016年6月12日 上午9:14:57 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryOperatorByParam")
	@ResponseBody
	public ResultEntity queryOperByParam(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {	
			String req = request.getParameter("req");
			Map<String, String> param = new HashMap<String, String>();
			if (!StringUtils.isBlank(req)) {
				param = JsonUtils.fromJson(req, Map.class);
			}
			String operIdx = getCurrentUser().getOperator_idx();
			String operLibIdx = getCurrentUser().getLibrary_idx();
			String operType = getCurrentUser().getOperator_type();
			Operator operator = getCurrentUser();
			param.put("operatorType", operType);//操作员类型
			param.put("operator_idx", operIdx);//操作员idx
			param.put("library_idx", operLibIdx);//所属馆
			resultEntity = operatorService.queryOperatorByParam(JsonUtils.toJson(param));
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据操作员operator_idx查询操作员详细信息
	 *
	 * <p>2016年6月12日 下午7:53:37 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryOperatorDetailByIdx")
	@ResponseBody
	public ResultEntity queryOperatorDetailByIdx(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {	
			String idx = request.getParameter("idx");
			if (StringUtils.isBlank(idx)) {
				resultEntity.setValue(false, "参数不能为空","","");
				return resultEntity;
			}
			Map<String, String> param = new HashMap<String, String>();
			
			String operIdx = getCurrentUser().getOperator_idx();
			String operLibIdx = getCurrentUser().getLibrary_idx();
			String operType = getCurrentUser().getOperator_type();
			
			//获取当前操作员的信息，用于判断这个操作员是否有查询这个用户信息的权限
			param.put("operatorType", operType);//进行此操作的操作员类型
			param.put("operator_idx", operIdx);//进行此操作的操作员idx
			param.put("library_idx", operLibIdx);//进行此操作的操作员所属馆
			param.put("idx", idx);//要查询的操作员的idx
			Map<String,Object> resMap = new HashMap<>();
			ResultEntity oEntity = operatorService.queryOperatorDetailByIdx(JsonUtils.toJson(param));//获取用户详细信息
			if (oEntity.getState()) {
				resMap = (Map<String, Object>) oEntity.getResult();
			}else {
				resultEntity = oEntity;
			}
			//根据idx查询用户所在的用户组
			ResultEntity gEntity = operatorService.queryOperatorGroupByOperIdx(JsonUtils.toJson(param));
			resMap.put("group", gEntity);
			
			//根据idx 查询用户的维护卡信息
			ResultEntity mEntity = operatorService.queryOperatorMaintenanceCard(JsonUtils.toJson(param));
			resMap.put("mCard", mEntity);
			
			resultEntity.setValue(true, "success", "", resMap);
			
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 查询所有的用户模板
	 *
	 * <p>2016年6月13日 下午7:11:33 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllSoxTemp")
	@ResponseBody
	public ResultEntity queryAllSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {	
			Map<String, String> param = new HashMap<String, String>();
			resultEntity = operatorService.queryAllSoxTemp(JsonUtils.toJson(param));
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}


	/**
	 * 根据当前用户的用户类型可以显示的操作员类型信息，。
	 * 1：云平台管理员、2：云平台维护人员、3：图书馆管理员、4：图书馆普通用户
	 * 查询的用户类型不能比
	 * 
	 * <p>2016年7月7日 上午11:31:37 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryOperatorTypes")
	@ResponseBody
	public ResultEntity queryOperatorTypes(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {	
			Map<String, String> param = new HashMap<String, String>();
			//获取当前操作用户的相关信息
//			Operator operator =  getCurrentUser();
			String operIdx = getCurrentUser().getOperator_idx();
			String operLibIdx = getCurrentUser().getLibrary_idx();
			String operType = getCurrentUser().getOperator_type();
			param.put("operIdx", operIdx);
			param.put("operLibIdx",operLibIdx);
			param.put("operType", operType);
			
			resultEntity = operatorService.queryOperatorTypes(JsonUtils.toJson(param));
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	

	/**
	 * 查询图书馆的libname
	 *
	 * <p>2016年6月2日 下午3:57:30 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getLibNameByIdx")
	@ResponseBody
	public ResultEntity getLibName(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (!StringUtils.isBlank(json)) {
				Operator operator = getCurrentUser();
				if(operator==null || "".equals(operator.getOperator_type()) ){
					resultEntity.setValue(true, "", "", "无此图书馆");
					return resultEntity;
				}else{
					String curLibid = operator.getLib_id();
					if(!curLibid.equals(json)  && !Operator.SSITCLOUD_ADMIN.equals(operator.getOperator_type()) 
							&& !Operator.SSITCLOUD_MANAGER.equals(operator.getOperator_type())) {
						resultEntity.setValue(true, "", "", "无此图书馆");
						return resultEntity;
					}
					
				}
				String libinfo = "{\"lib_id\":\""+json+"\"}";
				String libresp = operatorService.selLibraryByIdxOrId(libinfo);
				if (libresp!=null) {
					ResultEntity libEntity = JsonUtils.fromJson(libresp, ResultEntity.class);
					if(libEntity.getState()){
						if(libEntity.getResult() instanceof Map){
							Map<String, Object> libMap = (Map<String, Object>) libEntity.getResult();
							resultEntity.setValue(true,"","",libMap);
						}else if (resultEntity.getResult()==null) {
							resultEntity.setValue(true,"","","无此图书馆");
						}
					}else{
						resultEntity.setValue(false, libEntity.getMessage(),"","");
						return resultEntity;
					}
				}else{
					resultEntity.setValue(false, "","","获取名称超时");
					return resultEntity;
				}
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新操作员信息
	 *
	 * <p>2016年7月6日 上午11:12:50 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateOperator")
	@ResponseBody
	public ResultEntity updateOperator(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (!StringUtils.isBlank(json) && !json.equals("{}")) {
				Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
				Map<String, Object> op = new HashMap<>();//封装当前操作员的信息
				String operIdx = getCurrentUser().getOperator_idx();
				String operLibIdx = getCurrentUser().getLibrary_idx();
				String operType = getCurrentUser().getOperator_type();
				op.put("operIdx", operIdx);
				op.put("operLibIdx",operLibIdx);
				op.put("operType", operType);
				map.put("op", op);
				ResultEntity gEntity = operatorService.updateOperatorGroup(JsonUtils.toJson(map));
				if(gEntity==null || !gEntity.getState()){
					LogUtils.error("修改操作员所属用户组失败，"+gEntity.getMessage());
				}
				ResultEntity mEntity = operatorService.updateOperatorMaintenanceCard(JsonUtils.toJson(map));
				if (mEntity==null || !mEntity.getState()) {
					LogUtils.error("修改操作员维护卡信息失败，"+mEntity.getMessage());
				}
				resultEntity = operatorService.updateOperator(JsonUtils.toJson(map));
			}else{
				resultEntity.setValue(false, "参数不能为空", "", "");
			}
			
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_UPDATE_OPERATOR);
			
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 新增用户信息
	 *
	 * <p>2016年7月6日 上午11:12:50 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addOperator")
	@ResponseBody
	public ResultEntity addOperator(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (!StringUtils.isBlank(json) && !json.equals("{}")) {
				Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
				Map<String, Object> op = new HashMap<>();//封装当前操作员的信息
				String operIdx = getCurrentUser().getOperator_idx();
				String operLibIdx = getCurrentUser().getLibrary_idx();
				String operType = getCurrentUser().getOperator_type();
				op.put("operIdx", operIdx);
				op.put("operLibIdx",operLibIdx);
				op.put("operType", operType);
				map.put("op", op);
				resultEntity = operatorService.addOperator(JsonUtils.toJson(map));
				if(resultEntity.getState() && resultEntity.getResult()!=null && !(resultEntity.getResult() instanceof String) ){
					Map<String, Object> oper = (Map<String, Object>) resultEntity.getResult();
					Map<String,Object> param = (Map<String, Object>) map.get("operator");
					param.put("operator_idx", oper.get("operator_idx"));
					map.put("operator", param);
					ResultEntity gEntity = operatorService.updateOperatorGroup(JsonUtils.toJson(map));
					if(gEntity==null || !gEntity.getState()){
						LogUtils.error("新增操作员所属用户组失败，"+gEntity.getMessage());
					}
					ResultEntity mEntity = operatorService.updateOperatorMaintenanceCard(JsonUtils.toJson(map));
					if (mEntity==null || !mEntity.getState()) {
						LogUtils.error("新增操作员维护卡信息失败，"+mEntity.getMessage());
					}
				}
			}else{
				resultEntity.setValue(false, "参数不能为空", "", "");
			}
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_ADD_OPERATOR);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 批量删除用户
	 *
	 * <p>2016年7月7日 下午6:50:21 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/delOperator")
	@ResponseBody
	public ResultEntity delOperator(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (!StringUtils.isBlank(json) && !json.equals("{}")) {
				Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
				Map<String, Object> op = new HashMap<>();//封装当前操作员的信息
				String operIdx = getCurrentUser().getOperator_idx();
				String operLibIdx = getCurrentUser().getLibrary_idx();
				String operType = getCurrentUser().getOperator_type();
				op.put("operIdx", operIdx);
				op.put("operLibIdx",operLibIdx);
				op.put("operType", operType);
				map.put("op", op);
				resultEntity = operatorService.delOperator(JsonUtils.toJson(map));
				if(resultEntity!=null && resultEntity.getState() && "success".equals(resultEntity.getMessage())){
					//删除成功之后需要 清空从系统中登出被删除的用户 
					if(resultEntity.getResult()!=null){
						String operator_id=resultEntity.getResult().toString();
						kickOutUser(operator_id);
					}
				}
			}else{
				resultEntity.setValue(false, "参数不能为空", "", "");
			}
			if(!resultEntity.getState()){
				resultEntity.setRetMessage(resultEntity.getMessage());
			}
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_DELETE_OPERATOR);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 批量删除用户
	 *
	 * <p>2016年7月7日 下午6:50:21 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/delMultiOperator")
	@ResponseBody
	public ResultEntity delMultiOperator(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (!StringUtils.isBlank(json) && !json.equals("{}") && !json.equals("[]")) {
				Map<String, Object> map = new HashMap<>();
				List<Map<String, Object>> list = JsonUtils.fromJson(json, List.class);
				if (list.size()<=0) {
					resultEntity.setValue(false, "参数不能为空", "", "");
					return resultEntity;
				}
				Map<String, Object> op = new HashMap<>();//封装当前操作员的信息
				String operIdx = getCurrentUser().getOperator_idx();
				String operLibIdx = getCurrentUser().getLibrary_idx();
				String operType = getCurrentUser().getOperator_type();
				op.put("operIdx", operIdx);
				op.put("operLibIdx",operLibIdx);
				op.put("operType", operType);
				map.put("op", op);
				map.put("operList", list);
				resultEntity = operatorService.delMultiOperator(JsonUtils.toJson(map));
			}else{
				resultEntity.setValue(false, "参数不能为空", "", "");
			}
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_DELETE_OPERATOR);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	
	/**
	 * 获取所有的用户信息参数，以及可以新增的信息
	 *
	 * <p>2016年7月9日 上午11:24:20 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllOperatorInfo")
	@ResponseBody
	public ResultEntity queryAllOperatorInfo(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("json");
			resultEntity = operatorService.queryAllOperatorInfo(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据图书馆ID查询图书馆维护卡信息
	 *
	 * <p>2016年7月14日 下午2:01:43 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMaintenanceCard")
	@ResponseBody
	public ResultEntity queryMaintenanceCard(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = operatorService.queryMaintenanceCard(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 获取重置的密码
	 *
	 * <p>2016年7月14日 下午7:49:44 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getResetPassword")
	@ResponseBody
	public ResultEntity getResetPassword(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if(resetPassword==null || resetPassword.equals("")){
				resultEntity.setMessage("888888");
			}else{
				resultEntity.setMessage(resetPassword);
			}
			resultEntity.setState(true);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 重置用户密码为888888
	 *
	 * <p>2016年7月14日 下午7:32:12 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/resetPassword")
	@ResponseBody
	public ResultEntity resetPassword(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			Map<String, Object> param = JsonUtils.fromJson(req, Map.class);
			if(resetPassword==null || resetPassword.equals("")){
				param.put("password", "888888");
			}else{
				param.put("password", resetPassword);
			}
			resultEntity = operatorService.resetPassword(JsonUtils.toJson(param));
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_RESET_PWD);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 用户修改密码
	 *
	 * <p>2016年7月28日 下午3:14:19 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/changePassword")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ResultEntity changePassword(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("json");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空！");
				return resultEntity;
			}
			Map<String, String> param = JsonUtils.fromJson(req, Map.class);
			String old = param.get("old");
			String pwd1 = param.get("pwd1");
			String pwd2 = param.get("pwd2");
			if(StringUtils.isBlank(old) || StringUtils.isBlank(pwd1) || StringUtils.isBlank(pwd2)){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空！");
				return resultEntity;
			}
			
			if (StringUtils.isBlank(getCurrentUser().getOperator_id())) {
				resultEntity.setState(false);
				resultEntity.setMessage("无法获取登录信息，尝试重新登录！");
				return resultEntity;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("operid", getCurrentUser().getOperator_id());
			map.put("operidx", getCurrentUser().getOperator_idx());
			
			map.put("old", old);
			map.put("pwd1", pwd1);
			map.put("pwd2", pwd2);
			
			resultEntity = operatorService.changePassword(JsonUtils.toJson(map));
			
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_UPD_PWD);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}

	/**
	 * 查询当前操作员的所在馆ID
	 *
	 * <p>2016年9月12日 下午5:12:22 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryCurrentOperInfo")
	@ResponseBody
	public ResultEntity queryCurrentOperInfo(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Operator operator  = getCurrentUser();
			String libid = operator.getLib_id();
			String operType = operator.getOperator_type();
			Map<String, String> map = new HashMap<>();
			map.put("libId", libid);
			map.put("operType", operType);
			resultEntity.setValue(true, "", "", map);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 检查密码格式
	 *
	 * <p>2016年12月20日 上午10:45:53 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkPwdFormat")
	@ResponseBody
	public ResultEntity checkPwdFormat(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String pwd = request.getParameter("pwd");
			String soxid = request.getParameter("soxid");
			if (StringUtils.isBlank(pwd) || StringUtils.isBlank(soxid)) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("password", pwd);
			map.put("soxid", soxid);
			resultEntity = operatorService.checkPwdFormat(JsonUtils.toJson(map));
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 登陆的时候检测用户状态
	 *
	 * <p>2016年12月20日 上午10:45:53 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkOperatorStatus")
	@ResponseBody
	public ResultEntity checkOperatorStatus(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Operator operator = getCurrentUser();
			String firstChange = operator.getFirstChange();
			String pwdInvalid = operator.getPwdInvalid();
			String sessionPwdInvalid = (String) request.getSession().getAttribute("sessionPwdInvalid");//用session来判断每次登陆只提示一次
			
			if( !StringUtils.isBlank(firstChange) 
					&& "1".equals(firstChange)){
				resultEntity.setValue(true, "changePwd", "", "");	
				return resultEntity;
			}
			
			if ( (StringUtils.isBlank(sessionPwdInvalid) || "null".equals(sessionPwdInvalid))
					&& !StringUtils.isBlank(pwdInvalid) 
					&& !"-1".equals(pwdInvalid)) {
				resultEntity.setValue(true, "alert", "", pwdInvalid);
				request.getSession().setAttribute("sessionPwdInvalid", "1");
				return resultEntity;
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
}
