package com.ssitcloud.business.mobile.controller;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.authentication.entity.LibraryInfoEntity;
import com.ssitcloud.authentication.entity.MasterLibAndSlaveLibsEntity;
import com.ssitcloud.authentication.entity.OperatorAppEntity;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.service.*;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.MetadataOpercmdEntity;
import com.ssitcloud.mobile.entity.RegionEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 馆员APP business层 OperatorController
 * 2017年2月24日 下午4:11
 * @author shuangjunjie
 *
 */
@Controller
@RequestMapping("/operator")
public class OperatorController {
	
	@Resource
	private OperatorService operatorService;
	@Resource
	private PasswordServiceI passwordService;
	@Resource
	private LibraryServiceI libraryService;
	@Autowired
	private RegionService regionService;

	@Autowired
	private MetadataOpercmdCacheServiceI metadataOpercmdCacheService;
	/**
	 * 登录验证
	 * json = {
	 * 		"operator_name":"j",
	 * 		"operator_pwd":"ZZZ"
	 * 或者
	 * 		"mobile":"13647198123",
	 * 		"operator_pwd":"ZZZ"
	 * 或者
	 * 		"email":"123@qq.com",
	 * 		"operator_pwd":"ZZZ",
	 * 或者
	 * 		"id_card":"42900519951234",
	 * 		"operator_pwd":"ZZZ"
	 * }
	 * create by shuangjunjie
	 * 2017年2月25日
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/loginCheck")
	@ResponseBody
	public ResultEntity loginCheck(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		ResultEntity operResult = new ResultEntity();
		OperatorAppEntity operatorAppEntity = new OperatorAppEntity();
		try {
			req = request.getParameter("json");
			Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
			if(null != map.get("operator_name")){
				map.put("operator_id", map.get("operator_name"));
				map.remove("operator_name");
			}
			
			if(null != map.get("mobile")){
				String mobile = map.get("mobile").toString();
				if(StringUtils.isNotBlank(mobile)){
					if(!com.ssitcloud.business.mobile.common.util.StringUtils.isMobile(mobile)){
						result.setValue(false, "手机号格式不正确");
						return result;
					}
				}else{
					result.setValue(false, "手机号不能为空");
					return result;
				}
			}
			if(null != map.get("email")){
				String email = map.get("email").toString();
				if(StringUtils.isNotBlank(email)){
					if(!com.ssitcloud.business.mobile.common.util.StringUtils.isEmail(email)){
						result.setValue(false, "邮箱格式不正确");
						return result;
					}
				}else{
					result.setValue(false, "邮箱不能为空");
					return result;
				}
			}
			if(null != map.get("id_card")){
				String id_card = map.get("id_card").toString();
				if(StringUtils.isNotBlank(id_card)){
					if(!com.ssitcloud.business.mobile.common.util.StringUtils.isIdNumber(id_card)){
						result.setValue(false, "身份证号格式不正确");
						return result;
					}
				}else{
					result.setValue(false, "身份证号不能为空");
					return result;
				}
			}
			
			req = JsonUtils.toJson(map);
			result = operatorService.selectOperatorIdByParam(req);		//通过operator_id或者手机号或者邮箱或者身份证号 查出operator
			if(!result.getState() || null == result.getResult()){
				result.setValue(false, "登录名或者密码错误");		//实际上 是 该用户不存在，但是为了安全考虑，直接提示“登录名或者密码错误”
				return result;
			}
			operatorAppEntity = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), OperatorAppEntity.class);
			map.put("operator_id", operatorAppEntity.getOperator_id());
			if(null != map.get("mobile")){
				map.remove("mobile");
			}
			if(null != map.get("email")){
				map.remove("email");
			}
			if(null != map.get("id_card")){
				map.remove("id_card");
			}
			result = operatorService.loginCheck(JsonUtils.toJson(map));		//通过operator_id或者手机号或者邮箱或者身份证号 验证身份
			
			if(result.getState() && null != result.getResult()){
				result.setMessage("登录成功！");
				Map<String,Object> operatorMap = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), Map.class);
				operatorMap.remove("operator_pwd");//去除密码
				if(null != operatorMap.get("operator_idx")){
					if(StringUtils.isNotBlank(operatorMap.get("operator_idx").toString())){
						operResult = operatorService.selectOperaotrByIdxOrId("{\"operator_idx\":"+operatorMap.get("operator_idx").toString()+"}");
						if(operResult.getState() && null != operResult.getResult()){
							Map<String,Object> operRetmap = JsonUtils.fromJson(JsonUtils.toJson(operResult.getResult()), Map.class);
							operatorMap.put("mobile", operRetmap.get("mobile"));
							operatorMap.put("email", operRetmap.get("email"));
							operatorMap.put("id_card", operRetmap.get("id_card"));
						}else{
							return operResult;
						}
					}
				}
				result.setResult(operatorMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return result;
	}
	
	
	/**
	 * 检查馆员具有的app操作权限
	 * @param request
	 * @return
	 */
//	@RequestMapping("/checkPermission")
//	@ResponseBody
//	public ResultEntity checkPermission(HttpServletRequest request){
//		ResultEntity resultEntity = new ResultEntity();
//		String json = request.getParameter("json");
//		try {
//			if(StringUtils.isBlank(json) || json.equals("{}")){
//				resultEntity.setState(false);
//				resultEntity.setMessage("参数不能为空");
//				return resultEntity;
//			}
//			resultEntity = operatorService.checkPermission(json);
//		} catch (Exception e) {
//			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
//		}
//		
//		return resultEntity;
//	}
	
	
	
	
	
	/**
	 * 查询操作员信息
	 * 2017年2月24日
	 * json = {operator_idx:"1"} 或 {operator_id:"1"}
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectOperatorByIdxOrId")
	@ResponseBody
	public ResultEntity selectOperatorByIdxOrId(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("json");
			result = operatorService.selectOperaotrByIdxOrId(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return result;
	}
	
	/**
	 * 修改操作员信息
		json = {
			"operator_idx":"",
			"email":"",
			"mobile":"",
			"id_card":""
		}
	 * 2017年2月24日
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateOperator")
	@ResponseBody
	public ResultEntity updateOperator(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("json");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				result.setState(false);
				result.setMessage("参数不能为空");
				return result;
			}
			Map<String,Object> reqMap = JsonUtils.fromJson(req,Map.class);
			if(reqMap.get("operator_idx") == null){
				result.setState(false);
				result.setMessage("operator_idx不能为空");
				return result;
			}
			
			if(null != reqMap.get("mobile")){
				result = operatorService.selectOperatorIdByParam("{\"mobile\":"+"\""+reqMap.get("mobile").toString()+"\"}");
				if(result.getState() || null != result.getResult()){
					Map<String, Object> data = (Map<String, Object>) result.getResult();
					if (!reqMap.get("operator_idx").equals(data.get("operator_idx"))) {
						result.setValue(false, "该手机号已被使用", "", "");
						return result;
					}
				}
			}
			
			if(null != reqMap.get("email")){
				result = operatorService.selectOperatorIdByParam("{\"email\":"+"\""+reqMap.get("email").toString()+"\"}");
				if(result.getState() || null != result.getResult()){
					Map<String, Object> data = (Map<String, Object>) result.getResult();
					if (!reqMap.get("operator_idx").equals(data.get("operator_idx"))) {
						result.setValue(false, "该邮箱已被使用", "", "");
						return result;
					}
				}
			}
			
			if(null != reqMap.get("id_card")){
				result = operatorService.selectOperatorIdByParam("{\"id_card\":"+"\""+reqMap.get("id_card").toString()+"\"}");
				if(result.getState() || null != result.getResult()){
					Map<String, Object> data = (Map<String, Object>) result.getResult();
					if (!reqMap.get("operator_idx").equals(data.get("operator_idx"))) {
						result.setValue(false, "该身份证号已被使用", "", "");
						return result;
					}
				}
			}
			
			result = operatorService.updateOperator(req);
			if(!result.getState() || null == result.getResult())
				return result;
			
			result = operatorService.selectOperaotrByIdxOrId(req);
			if(result.getState() && null != result.getResult()){
				Map<String,Object> map = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), Map.class);
				map.remove("operator_pwd");
				map.remove("isLogged");
				map.remove("last_login_ip");
				map.remove("last_login_time");
				map.remove("last_lock_time");
				map.remove("last_chgpwd_time");
				map.remove("login_fail_times");
				map.remove("createtime");
				result.setValue(true, "修改成功", "", map);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return result;
	}
	
	/**
	 * 修改操作员密码
	 * json = {
	 * 		"old_pwd":"DDD",
	 * 		"new_pwd":"WWW",
	 * 		"operator_idx":123
	 * }
	 * 
	 * 2017年2月24日
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/changePassword")
	@ResponseBody
	public ResultEntity changePassword(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		OperatorAppEntity operatorAppEntity = new OperatorAppEntity(); 
		try {
			req = request.getParameter("json");
			Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
			String old_pwd = map.get("old_pwd").toString();
			String new_pwd = map.get("new_pwd").toString();
			
			if(null != map.get("old_pwd")){
				map.remove("old_pwd");
			}
			if(null != map.get("new_pwd")){
				map.remove("new_pwd");
			}
			if(null != map.get("operator_idx")){
				result = operatorService.selectOperaotrByIdxOrId(JsonUtils.toJson(map));
				if(result.getState() && null != result.getResult()){
					operatorAppEntity = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), OperatorAppEntity.class);
					map.put("operid",operatorAppEntity.getOperator_id());
					map.remove("operator_idx");
				}else{
					return result;
				}
			}
			map.put("old", old_pwd);
			map.put("pwd1", new_pwd);
			result = operatorService.changePassword(JsonUtils.toJson(map));
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return result;
	}
	
	/**
	 * 密码找回 验证馆员身份
	 * 2017年2月25日
	 * json = {
	 * 	"mobile":"",
	 * 	"email":"",
	 * 	"id_card":""
	 * 
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/retrievePassword")
	@ResponseBody
	public ResultEntity retrievePassword(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("json");
			if(StringUtils.isBlank(req)){
				result.setValue(false, "参数不能为空");
				return result;
			}
			Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
			String mobile = map.get("mobile").toString();
			String email = map.get("email").toString();
			String id_card = map.get("id_card").toString();
			
			if(!com.ssitcloud.business.mobile.common.util.StringUtils.isMobile(mobile)){
				result.setValue(false, "手机号格式不正确", "", "2");
				return result;
			}
			if(!com.ssitcloud.business.mobile.common.util.StringUtils.isEmail(email)){
				result.setValue(false, "邮箱格式不正确", "", "3");
				return result;
			}
			
			if(!com.ssitcloud.business.mobile.common.util.StringUtils.isIdNumber(id_card)){
				result.setValue(false, "身份证号格式不正确", "", "4");
				return result;
			}
			
			//手机号、邮箱和身份证号格式都正确
			result = operatorService.retrievePassword(req);		//验证 手机号、邮箱和身份证号的正确性
			if(result.getState() && null != result.getResult()){
//				Map<String,Object> resultMap = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()),Map.class);
				
				//移除不需要的信息
//				Iterator<Map.Entry<String, Object>> it = resultMap.entrySet().iterator();
//				while(it.hasNext()){  
//		            Map.Entry<String, Object> entry=it.next();  
//		            String key=entry.getKey();  
//		            if(!key.equals("mobile") && !key.equals("email") && !key.equals("id_card")){
//						it.remove();
//					}
//		        } 
//				
//				result.setResult(resultMap);
				result.setValue(true, "验证通过", "", "");
			}else{
				result.setValue(false, "验证不通过", "", "0");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return result;
		
	}
	
	/**
	 * 密码找回验证通过后，保存新密码
	 * json = {
	 * 		"mobile": "13647198123",
     *		"email": "123@qq.com",
     *		"id_card": "429005199510251234",
     *		"password": "DDD"
	 * }
	 * create by shuangjunjie
	 * 2017年2月25日
     *
     * 2017年9月1日接口已经废弃
	 * @param request
	 * @param req
	 * @return
	 */
	@Deprecated
//	@RequestMapping("/saveNewPassword")
//	@ResponseBody
	public ResultEntity saveNewPassword(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		OperatorAppEntity operatorAppEntity = new OperatorAppEntity();
		try {
			req = request.getParameter("json");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				result.setState(false);
				result.setMessage("参数不能为空");
				return result;
			}
			Map<String,Object> reqMap = JsonUtils.fromJson(req, Map.class);
			String password ="";
			if(null != reqMap.get("password")){
				password = reqMap.get("password").toString();
				
				if(StringUtils.isBlank(password)){
					result.setValue(false, "密码不能为空");
					return result; 
				}
			}
			reqMap.remove("password");
			result = operatorService.retrievePassword(JsonUtils.toJson(reqMap));
			
			operatorAppEntity = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), OperatorAppEntity.class);
			
			reqMap.put("password", password);
			reqMap.put("soxid", operatorAppEntity.getSox_tpl_id());
			
			result = operatorService.checkPwdFormat(JsonUtils.toJson(reqMap));		//检查密码格式
			if(!result.getState()){
				return result;
			}
			
//			if(null == result.getResult() || !result.getState()){
//				result.setValue(false, "密码格式不对，请重新输入");
//				return result;
//			}
			
			operatorAppEntity.setOperator_pwd(passwordService.encryption(password));		//加密新改的密码
			result = operatorService.updateOperAppPwdByIdx(JsonUtils.toJson(operatorAppEntity));		//更新用户密码
			if(result.getState() && null != result.getResult()){
				result.setValue(true, "密码修改成功，请牢记密码");
			}else{
				result.setValue(false, "密码修改失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return result;
	}
	
	/**
	 * 查询设备 列表
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/deviceMain")
	@ResponseBody
	public ResultEntity deviceMain(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		OperatorAppEntity operatorAppEntity = new OperatorAppEntity();
		try {
			req = request.getParameter("json");
			if(StringUtils.isEmpty(req)){
				req="{\"pageSize\":12}";
			}
			result = operatorService.selectOperaotrByIdxOrId(req);
			if(result.getState() && null != result.getResult()){
				operatorAppEntity = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), OperatorAppEntity.class);
				String operType=operatorAppEntity.getOperator_type().toString();
				if(!OperatorAppEntity.SSITCLOUD_ADMIN.equals(operType)&&!OperatorAppEntity.SSITCLOUD_MANAGER.equals(operType)){
					//图书馆人员 只显示本馆的设备
					Map<String, Object> params=JsonUtils.fromJson(req, Map.class);
					String library_idx=operatorAppEntity.getLibrary_idx().toString();
					params.put("library_idx", library_idx);
					if(OperatorAppEntity.LIBRARY_USER.equals(operType)){//设备组设备限制
						params.put("operator_idx_Limit", operatorAppEntity.getOperator_idx());
					}
					req=JsonUtils.toJson(params);
				}
			}else{
				return result;
			}
			Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
			if(null != map.get("operator_idx")){
				map.remove("operator_idx");
			}
			result = operatorService.selectDeviceByPage(JsonUtils.toJson(map));
			if(!result.getState()){
				result.setValue(false, "查询失败");
			}else if(null == result.getResult()){
				result.setValue(false, "查询没有结果");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return result;
	}
	
	/**
	 * json = {
	 * 		"keyWord":"",
	 * 		"operator_idx":"",
	 * 		"library_idx":""
	 * }
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/queryDeviceByParam")
	@ResponseBody
	public ResultEntity queryDeviceByParam(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		OperatorAppEntity operatorAppEntity = new OperatorAppEntity();
		try {
			if(StringUtils.isEmpty(req)){
				req="{\"pageSize\":12}";
			}
			req = request.getParameter("json");
			Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
			String keyWord = null;
			
			if(null != map.get("keyWord")){
				keyWord = map.get("keyWord").toString();
				map.remove("keyWord");
			}
			
			result = operatorService.selectOperaotrByIdxOrId(JsonUtils.toJson(map));
			if(null != map.get("operator_idx")){
				map.remove("operator_idx");
			}
			if(StringUtils.isNotBlank(keyWord)){
				map.put("keyWord", keyWord);
			}
			req = JsonUtils.toJson(map);
			if(result.getState() && null != result.getResult()){
				operatorAppEntity = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), OperatorAppEntity.class);
				String operType=operatorAppEntity.getOperator_type().toString();
				if(!OperatorAppEntity.SSITCLOUD_ADMIN.equals(operType)&&!OperatorAppEntity.SSITCLOUD_MANAGER.equals(operType)){
					//图书馆人员 只显示本馆的设备
					Map<String, Object> params=JsonUtils.fromJson(req, Map.class);
					if(OperatorAppEntity.LIBRARY_USER.equals(operType)){//设备组设备限制
						params.put("operator_idx_Limit", operatorAppEntity.getOperator_idx());
					}
					Integer library_idx=operatorAppEntity.getLibrary_idx();
					//params.put("library_idx", library_idx); 这个不能放进来 会影响后面的判断
					//req=JsonUtils.toJson(params);
					//先查询 一下 有没有子馆,有子馆的情况下则获取所有子馆
					Map<String, Object> m=new HashMap<>();
					m.put("library_idx", library_idx);
					//查询出子馆，如果有子馆 则显示子馆的设备
					ResultEntity res=libraryService.querySlaveLibraryByMasterIdx(JsonUtils.toJson(m));
					List<Integer> list=new ArrayList<>();
					if(res!=null && res.getState()){
						MasterLibAndSlaveLibsEntity libs=JsonUtils.convertMap(res.getResult(), new org.codehaus.jackson.type.TypeReference<MasterLibAndSlaveLibsEntity>() {});
						if(libs!=null){
							List<LibraryEntity> LibraryEntitys=libs.getSlaveLibrary();
							if(CollectionUtils.isNotEmpty(LibraryEntitys)){
								try {
									list.add(library_idx);
								} catch (Exception e) {
								}
								for (LibraryEntity libraryEntity : LibraryEntitys) {
									list.add(libraryEntity.getLibrary_idx());
								}
								params.put("lib_idx_list", list);
								req=JsonUtils.toJson(params);
							}else{
								//有可能是子馆
								list.add(library_idx);
								params.put("lib_idx_list", list);
								req=JsonUtils.toJson(params);
							}
						}else{//没有子馆的情况下
							list.add(library_idx);
							params.put("lib_idx_list", list);
							req=JsonUtils.toJson(params);
						}
					}else{
						list.add(library_idx);
						params.put("lib_idx_list", list);
						req=JsonUtils.toJson(params);
					}
					
				}
//				else if(OperatorAppEntity.SSITCLOUD_MANAGER.equals(operType)){
//					if(!checkPermession("0103000000")){
//						result.setMessage("没有权限！");
//						return result;
//					}
//				}
				result = operatorService.queryDeviceByParam(req);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		
		return result;
	}
	
	/**
	 * 查出设备 对应的所有信息和状态
	 * json = {
	 * 		"device_idx":"",
	 * 		"library_idx":""
	 * 	
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/getDevMonitor")
	@ResponseBody
    public ResultEntity getDevMonitor(HttpServletRequest request) {
        ResultEntity result = new ResultEntity();
        Map<String, Object> reqListMap = new HashMap<>();

        try {
            String req = request.getParameter("json");

            if (StringUtils.isEmpty(req)) {
                result.setValue(false, "参数不能为空", "", null);
                return result;
            }
            Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
            String device_id = null;
            String device_idx = null;
            String library_id = null;
//			List<String> ssLIdxs = null;		//SSL类型设备idx
//			List<String> reGIdxs = null;		//REG类型设备idx

//            String sslDevType = "{\"device_type\":\"SSL\"}";
//            String regDevType = "{\"device_type\":\"REG\"}";

            if (null != map.get("device_idx")) {
                device_idx = map.get("device_idx").toString();
            }

            result = operatorService.selectDevIdByIdx(req);            //根据device_idx查出device_id
            if (result.getState() && null != result.getResult()) {
                device_id = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), String.class);
            } else {
                result.setState(false);
                result.setMessage("获取device_id失败");
                return result;
            }
            map.remove("device_idx");
            result = libraryService.selectLibraryIdByIdx(JsonUtils.toJson(map));            //根据library_idx查出library_id
            if (result.getState() && null != result.getResult()) {
                LibraryEntity libraryEntity = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), LibraryEntity.class);
                library_id = libraryEntity.getLib_id();
            } else {
                result.setState(false);
                result.setMessage("获取library_id失败");
                return result;
            }

//			ResultEntity resultMetaDevTypeIdx = operatorService.selectMetaDevTypeIdxByType(sslDevType);					//根据设备类型（SSL）查出meta_devicetype_idx
//			if(!resultMetaDevTypeIdx.getState() || null == resultMetaDevTypeIdx.getResult()){
//				return resultMetaDevTypeIdx;
//			}
//			List<Integer> temp = (List<Integer>) resultMetaDevTypeIdx.getResult();
//			ssLIdxs = new ArrayList<>(temp.size());
//			for(Integer i:temp){
//				if(i != null){
//					ssLIdxs.add(i.toString());
//				}
//			}

//			resultMetaDevTypeIdx = operatorService.selectMetaDevTypeIdxByType(regDevType);					//根据设备类型（REG）查出meta_devicetype_idx
//			if(!resultMetaDevTypeIdx.getState() || null == resultMetaDevTypeIdx.getResult()){
//				return resultMetaDevTypeIdx;
//			}
//			temp = (List<Integer>) resultMetaDevTypeIdx.getResult();
//			reGIdxs = new ArrayList<>(temp.size());
//			for(Integer i:temp){
//				if(i != null){
//					reGIdxs.add(i.toString());
//				}
//			}

//			String json = "{\"device_id\":"+"\""+device_id+"\""+"}";			//拼接device_id  json
//			result = operatorService.selectDevicTypeByDeviceId(json);		//根据device_id查出设备类型

            final String libDevJson = "[\"" + library_id + "_" + device_id + "\"]";        //拼接library_id_device_id  json

//			if(result.getState() && null != result.getResult()){
//				if(ssLIdxs.contains(deviceType.toString()) || reGIdxs.contains(deviceType.toString())){//设备类型为SSL或者REG 都需要 查询卡、钱和书箱的信息
            ResultEntity resultBinState = operatorService.selectBinState(libDevJson);            //查询卡、钱和书箱信息
            Map<String, Object> newBMap = new HashMap<>();
            if (resultBinState.getState() && resultBinState.getResult() != null) {
//						resultBinState.setValue(false, "获取卡、钱和书箱数据失败");
//						return resultBinState;

                JSONObject binJsonObject = JSONObject.fromObject(resultBinState.getResult());
                JSONObject jsonObject = binJsonObject.getJSONObject(library_id + "_" + device_id);
                if (null != jsonObject && !jsonObject.isNullObject()) {
                    Map<String, Object> binMap = JsonUtils.fromJson(JsonUtils.toJson(jsonObject), Map.class);

                    for (String key : binMap.keySet()) {
                        if (key.equals("cardbin")) {
                            Map<String, Object> cardMap = new HashMap<>();
                            JSONObject cardJsonObject = jsonObject.getJSONObject(key);
                            cardMap.put("amount", cardJsonObject.get("amount"));//卡数量
                            cardMap.put("state", cardJsonObject.get("state"));//状态

                            newBMap.put("cards", cardMap);
                        }
                        if (key.equals("cashbin")) {
                            JSONArray cashJsonArray = jsonObject.getJSONArray(key);
                            List<Map<String, Object>> cashList = new ArrayList<>();
                            for (int i = 0; i < cashJsonArray.size(); i++) {
                                Map<String, Object> cashMap = new HashMap<>();
                                cashMap.put("subtype", cashJsonArray.getJSONObject(i).getString("subtype"));//纸币面值
                                cashMap.put("amount", cashJsonArray.getJSONObject(i).getString("amount"));//纸币数量
                                cashMap.put("state", cashJsonArray.getJSONObject(i).getString("state"));//状态
                                cashList.add(cashMap);
                            }
                            newBMap.put("cashs", cashList);
                        }
                        if (key.equals("bookbin")) {
                            JSONArray bookJsonArray = jsonObject.getJSONArray(key);
                            List<Map<String, Object>> bookList = new ArrayList<>();
                            for (int i = 0; i < bookJsonArray.size(); i++) {
                                Map<String, Object> bookMap = new HashMap<>();
                                bookMap.put("binno", bookJsonArray.getJSONObject(i).getString("binno"));//书箱号
                                bookMap.put("desc", getDesc(bookJsonArray.getJSONObject(i).getString("desc")));//书箱类型描述
                                bookMap.put("subtype", bookJsonArray.getJSONObject(i).getString("subtype"));//
                                bookMap.put("amount", bookJsonArray.getJSONObject(i).getString("amount"));//
                                bookMap.put("state", bookJsonArray.getJSONObject(i).getString("state"));//
                                bookList.add(bookMap);
                            }
                            newBMap.put("bookBoxs", bookList);
                        }
                    }
                }
            }
//				}
//            if (ssLIdxs.contains(deviceType.toString())) {
            ResultEntity resultBookrackState = operatorService.selectBookrackState(libDevJson);            //查询书架信息
            if (resultBookrackState.getState() && resultBookrackState.getResult() != null) {
                JSONObject bookraceObject = JSONObject.fromObject(resultBookrackState.getResult());
                JSONObject bookraceJson = bookraceObject.getJSONObject(library_id + "_" + device_id);
                if (null != bookraceJson && !bookraceJson.isNullObject()) {
                    Map<String, Object> bookraceMap = JsonUtils.fromJson(JsonUtils.toJson(bookraceJson), Map.class);
                    if (null != bookraceMap) {
                        Map<String, Object> newBookraceMap = new HashMap<>();
                        for (String key : bookraceMap.keySet()) {
                            if (key.equals("Exbox")) {
                                String exboxString = bookraceMap.get(key).toString();
                                newBookraceMap.put("exbox", exboxString);//特型书盒
                            } else if (key.equals("Precheckout")) {
                                newBookraceMap.put("precheckout", bookraceMap.get(key));//Precheckout
                            } else if (key.equals("Level1")) {
                                String levelOneString = bookraceMap.get(key).toString();
                                newBookraceMap.put("level1", levelOneString);//一层书盒
                            } else if (key.equals("Level2")) {
                                String levelTwoString = bookraceMap.get(key).toString();
                                newBookraceMap.put("level2", levelTwoString);//二层书盒
                            } else if (key.equals("Level3")) {
                                String levelThreeString = bookraceMap.get(key).toString();
                                newBookraceMap.put("level3", levelThreeString);//三层书盒
                            } else if (key.equals("Standardized")) {
                                newBookraceMap.put("standardized", String.valueOf(bookraceMap.get(key)));//标准格口
                            } else if (key.equals("Oversized")) {
                                newBookraceMap.put("oversized", String.valueOf(bookraceMap.get(key)));//标准格口
                            } else if (key.equals("Abnormal")) {
                                newBookraceMap.put("abnormal", String.valueOf(bookraceMap.get(key)));//标准格口
                            }
                        }
                        newBMap.put("bookracks", newBookraceMap);
                    }
                }
            }
//            }
            if (!newBMap.isEmpty()) {
                reqListMap.put("bin_state", newBMap);
            }
//            }else{
//				result.setValue(false, "获取设备类型失败");
//			}

            String getDevExtModelReq = "[\"" + device_idx + "\"]";
            ResultEntity resultGetDevExtModel = operatorService.getDevExtModel(getDevExtModelReq);            //获取  外设信息
            if (resultGetDevExtModel.getState() && null != resultGetDevExtModel.getResult()) {
                JSONObject jsonObject = JSONObject.fromObject(resultGetDevExtModel.getResult());
                JSONArray deviceMonConfigsArr = jsonObject.getJSONArray("deviceMonConfigs");
				JSONArray deviceExtConfigsArr = jsonObject.getJSONArray("deviceExtConfigs");
                JSONArray plc_logic_objArr = jsonObject.getJSONArray("plcLogicOBJList");

                Map<String, Map<String, String>> alerts = new HashMap<>(32);//需要监控的部件查询map
                if (deviceMonConfigsArr != null && deviceMonConfigsArr.size() > 0) {
                    for (int i = 0, z = deviceMonConfigsArr.size(); i < z; i++) {
                        JSONObject tempJsonObj = deviceMonConfigsArr.getJSONObject(i);
                        if("time_out".equalsIgnoreCase(String.valueOf(tempJsonObj.getString("table_name")))){
                            continue;
                        }
                        Map<String, String> mtemp = new HashMap<>(3);
                        mtemp.put("desc", tempJsonObj.getString("logic_obj_desc"));
                        mtemp.put("alert",tempJsonObj.getString("alert"));
                        alerts.put(tempJsonObj.getString("logic_obj"), mtemp);
                    }
                }
                Map<String, Map<String, String>> plcAlerts = new HashMap<>(32);
                if (plc_logic_objArr != null && plc_logic_objArr.size() > 0) {
                    for (int i = 0,z = plc_logic_objArr.size(); i < z; i++) {
                        JSONObject tempJsonObj = plc_logic_objArr.getJSONObject(i);
                        Map<String, String> mtemp = new HashMap<>();
                        mtemp.put("desc", tempJsonObj.getString("plc_logic_obj_desc"));
                        plcAlerts.put(tempJsonObj.getString("plc_logic_obj"), mtemp);
                    }
                }
                Map<String,Map<String,String>> extConfig = new HashMap<>(32);//设备配置的部件
                if (deviceExtConfigsArr != null) {
                    for (int i = 0,z = deviceExtConfigsArr.size(); i < z; i++) {
                        JSONObject tempJsonObj = deviceExtConfigsArr.getJSONObject(i);
                        Map<String, String> mtemp = new HashMap<>();
                        mtemp.put("desc", tempJsonObj.getString("logic_obj_desc"));
                        extConfig.put(tempJsonObj.getString("logic_obj"), mtemp);
                    }
                }

                ResultEntity resultDeviceExtState = operatorService.selectDeviceExtState(libDevJson);            //查询 外设和plc对应状态
                if (resultDeviceExtState.getState() && resultDeviceExtState.getResult() != null) {
                    Map<String, Object> extState = new HashMap<>(32);
                    Map<String, Object> r = (Map<String, Object>) resultDeviceExtState.getResult();
                    Map<String, Object> resultMap = (Map<String, Object>) r.get(library_id + "_" + device_id);
                    //检查plc数据
                    Object plc = resultMap.remove("PlcSSL");
                    if (plc != null) {
                        extConfig.remove("PlcSSL");
                        Map<String, Object> pclState = new HashMap<>(16, 0.9f);
                        if (plc instanceof Map) {
                            boolean state = true;
                            Set<Map.Entry<String, Object>> plcEntries = ((Map<String, Object>) plc).entrySet();
                            for (Map.Entry<String, Object> plcEntry : plcEntries) {
                                Map<String, Object> mtemp = new HashMap<>();
                                mtemp.put("state", plcEntry.getValue());
                                pclState.put(plcEntry.getKey(), mtemp);
                                if (state && !"0".equals(plcEntry.getValue())) {
                                    state = false;
                                }
                                Map<String, String> v = plcAlerts.get(plcEntry.getKey());
                                if (v != null) {
                                    mtemp.put("desc", getDesc(v.get("desc")));
                                }else{
                                    mtemp.put("desc", getDesc(plcEntry.getKey()));
                                }
                            }
                            if (!pclState.isEmpty()) {
                                pclState.put("state", state ? "0" : "1");
                            }
                        } else if (plc instanceof String) {
                            pclState.put("state", plc);
                        }
                        if (!pclState.isEmpty()) {
                            extState.put("PlcSSL", pclState);
                        }
                    }
                    //检查普通数据
                    Set<Map.Entry<String, Object>> entries = resultMap.entrySet();
                    for (Map.Entry<String, Object> entry : entries) {
                        String key = entry.getKey();
                        if("createtime".equalsIgnoreCase(key)){
                            continue;
                        }
                        Map<String, Object> mtemp = new HashMap<>();
                        //填充描述
                        Map<String, String> v = alerts.get(key);
                        boolean ignore = false;
                        if (v != null) {
                            mtemp.put("desc", getDesc(v.get("desc")));
                            ignore = "0".equals(v.get("alert"));
                        }else{
                            mtemp.put("desc",getDesc(key));
                        }
                        mtemp.put("state", ignore?0:entry.getValue());
                        extState.put(key, mtemp);//添加到状态表
                        extConfig.remove(key);//移除配置
                    }
                    //检查有配置但是没有上传状态的部件
                    if(!extConfig.isEmpty()){
                        Set<Map.Entry<String, Map<String, String>>> extEntrys = extConfig.entrySet();
                        for (Map.Entry<String, Map<String, String>> extEntry : extEntrys) {
                            Map<String, Object> mtemp = new HashMap<>();
                            String desc = extEntry.getValue().get("desc");
                            //填充描述
                            if (!StringUtils.isEmpty(desc)) {
                                mtemp.put("desc", getDesc(desc));
                            }else{
                                mtemp.put("desc",getDesc(extEntry.getKey()));
                            }
                            mtemp.put("state",null);
                            extState.put(extEntry.getKey(), mtemp);
                        }
                    }
                    if (!extState.isEmpty()) {
                        reqListMap.put("ext_state", extState);
                    }
                }
            }

            //查询软状态
            ResultEntity resultSoftState = operatorService.selectSoftState(libDevJson);            //获取 功能状态信息
            if (resultSoftState.getState() && resultSoftState.getResult() != null) {
                Map<String, Object> softStateMap = JsonUtils.fromJson(JsonUtils.toJson(resultSoftState.getResult()), Map.class);
                Map<String, Object> sMap = new HashMap();
                if (null != softStateMap && null != softStateMap.get(library_id + "_" + device_id)) {
                    Map softState = (Map) softStateMap.get(library_id + "_" + device_id);
                    if (softState != null) {
                        sMap = (Map<String, Object>) softState.get("Function");
                    }
                }

                Map<String, Object> reqSoftStateMap = new HashMap<>();
                for (String key : sMap.keySet()) {
                    if ("createtime".equalsIgnoreCase(key)) {
                        continue;
                    }
                    Map<String, Object> m = new HashMap<>(7);
                    m.put("state", sMap.get(key));
                    reqSoftStateMap.put(key, m);
                    MetadataOpercmdEntity moe = metadataOpercmdCacheService.get(key);
                    if (moe != null && !StringUtils.isEmpty(moe.getOpercmd_desc())) {
                        m.put("desc", getDesc(moe.getOpercmd_desc()));
                    } else {
                        m.put("desc", getDesc(key));
                    }
                }

                Map<String, Object> softMap = new HashMap<>();
                softMap.put("function_state", reqSoftStateMap);
                reqListMap.put("soft_state", softMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            result.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
            return result;
        }

        result.setValue(true, "", "", reqListMap);
        return result;
    }

    /**
     * 转换消息描述格式
     * @return 若消息
     */
    private Object getDesc(Object o){
        if(JSONUtils.mayBeJSON(String.valueOf(o))){
            return JsonUtils.fromJson(String.valueOf(o),Map.class);
        }else{
            Map<String,Object> m = new HashMap<>(2);
            m.put("zh_CN",String.valueOf(o));
            return m;
        }
    }

	/**
	 * 获取设备对应的外设信息
	 * [
	 * 		"2"
	 * ]
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/getDevExtModel")
	@ResponseBody
	public ResultEntity getDevExtModel(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("json");
			result = operatorService.getDevExtModel(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		return result;
		
	}
	
	/**
	 * 获取 设备外设状态
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectDeviceExtState")
	@ResponseBody
	public ResultEntity selectDeviceExtState(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = operatorService.selectDeviceExtState(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		return result;
	}
	
	/**
	 * 获取 功能状态信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectSoftState")
	@ResponseBody
	public ResultEntity selectSoftState(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = operatorService.selectSoftState(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		return result;
	}
	
	/**
	 * 获取 设备上的书架状态信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectBookrackState")
	@ResponseBody
	public ResultEntity selectBookrackState(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = operatorService.selectBookrackState(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		return result;
	}
	
	/**
	 * 获取箱子信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectBinState")
	@ResponseBody
	public ResultEntity selectBinState(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = operatorService.selectBinState(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		return result;
		
	}
	
	/**
	 * 获取所有的不是slave的馆和其一级子馆 
	 * 只有管理员可以使用
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/queryAllMasterLibAndSlaveLib")
	@ResponseBody
	public ResultEntity queryAllMasterLibAndSlaveLib(HttpServletRequest request, String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			req = request.getParameter("json");
			if(!StringUtils.isNotEmpty(req)){
				resultEntity.setMessage("参数不能为空");
				resultEntity.setState(false);
				return resultEntity;
			}
			resultEntity = libraryService.queryAllMasterLibAndSlaveLib(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return resultEntity;
	}
	
	/**
	 * 查询主馆及其子馆信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/querySlaveLibraryByMasterIdx")
	@ResponseBody
	public ResultEntity querySlaveLibraryByMasterIdx(HttpServletRequest request, String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			req = request.getParameter("json");
			if(!StringUtils.isNotEmpty(req)){
				resultEntity.setMessage("参数不能为空");
				resultEntity.setState(false);
				return resultEntity;
			}
			resultEntity = libraryService.querySlaveLibraryByMasterIdx(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return resultEntity;
	}
	
	/**
	 * 查询主馆及其子馆 对应设备信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/SelectDeviceMgmtByLibraryIdxs")
	@ResponseBody
	public ResultEntity SelectDeviceMgmtByLibraryIdxs(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
//			req = request.getParameter("json");
			Map<String, String> map = new HashMap<>();
			String libIdxs = request.getParameter("libIdxs");
			String device_id = request.getParameter("device_id");
			String region_idx = request.getParameter("region_idx");
			
			if(!StringUtils.isNotEmpty(libIdxs)){
				libIdxs = "[]";
			}
			if(!StringUtils.isNotEmpty(device_id)){
				device_id = "";
			}
			if(!StringUtils.isNotEmpty(region_idx)){
				region_idx = "";
			}
			map.put("libIdxs", libIdxs);
			map.put("device_id", device_id);
			map.put("region_idx", region_idx);
			map.put("page", request.getParameter("page"));
			resultEntity = operatorService.SelectDeviceMgmtByLibraryIdxs(map);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return resultEntity;
	}

	/**
	 * 设备维护 发送指令
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/sendOrder")
	@ResponseBody
	public ResultEntity sendOrder(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("json");
			if(!StringUtils.isNotEmpty(req)){
				resultEntity.setMessage("参数不能为空");
				resultEntity.setState(false);
				return resultEntity;
			}
			resultEntity = operatorService.sendOrder(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return resultEntity;
	}
	
	/**
	 * json = {
	 * 		"page":1,
	 * 		"pageSize":4,
	 * 		"operator_type":"1",
	 * 		"library_idx":163	
	 * }
	 * 消息提醒（故障提醒）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectDeviceTroublesByLibIdxs")
	@ResponseBody
	public ResultEntity selectDeviceTroublesByLibIdxs(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String operatorType = "";
		List listData = new ArrayList<>();
		Map<String,Object> mapData = new HashMap<>();
		List listLib = new ArrayList<>();
		Integer page = null;
		Integer pageSize = null;
		try {
			String req = request.getParameter("json");
			if(StringUtils.isBlank(req)){
				resultEntity.setMessage("参数不能为空");
				resultEntity.setState(false);
				return resultEntity;
			}
			Map reqMap = JsonUtils.fromJson(req, Map.class);
			if(null != reqMap.get("page") && StringUtils.isNotBlank(reqMap.get("page").toString())){
				page = (Integer) reqMap.get("page");
			}
			if(null != reqMap.get("pageSize") && StringUtils.isNotBlank(reqMap.get("pageSize").toString())){
				pageSize = (Integer) reqMap.get("pageSize");
			}
			reqMap.remove("page");
			reqMap.remove("pageSize");
			
			if(null != reqMap.get("operator_type") && StringUtils.isNotBlank(reqMap.get("operator_type").toString())){
				operatorType = reqMap.get("operator_type").toString();
				reqMap.remove("operator_type");
				req = JsonUtils.toJson(reqMap);
				if(OperatorAppEntity.SSITCLOUD_ADMIN.equals(operatorType) 
						|| OperatorAppEntity.SSITCLOUD_MANAGER.equals(operatorType)){
					resultEntity = libraryService.queryAllMasterLibAndSlaveLib(req);
					if(!resultEntity.getState() || null == resultEntity.getResult()){
						resultEntity.setState(false);
						resultEntity.setMessage("获取图书馆idx失败");
						return resultEntity;
					}
				}else{
					resultEntity = libraryService.querySlaveLibraryByMasterIdx(req);
					if(!resultEntity.getState() || null == resultEntity.getResult()){
						resultEntity.setState(false);
						resultEntity.setMessage("获取图书馆idx失败");
						return resultEntity;
					}
				}
			}
			if(null != resultEntity.getResult()){
				String result = JsonUtils.toJson(resultEntity.getResult());
				if (result.startsWith("[")){
					listData = JsonUtils.fromJson(result, List.class);
					for(int i = 0;i < listData.size(); i++){
			            Map map = JsonUtils.fromJson(JsonUtils.toJson(listData.get(i)),Map.class);
			            if (null !=map && null != map.get("masterLibrary")){
			                Map mapMaster = (Map) map.get("masterLibrary");
		
			                //屏蔽云平台
			                if (null != mapMaster 
			                		&& null != mapMaster.get("library_idx") 
	                        		&& StringUtils.isNotBlank(mapMaster.get("library_idx").toString()) 
	                        		&& !mapMaster.get("library_idx").toString().equals("0")){
			                    listLib.add((int) mapMaster.get("library_idx"));
			                }
			            }
			            if (null != map && null != map.get("slaveLibrary")){
			                List listSlaveLibrary = (List) map.get("slaveLibrary");
			                if (null != listSlaveLibrary && listSlaveLibrary.size() > 0){
			                    for (int j = 0;j < listSlaveLibrary.size();j++){
			                        Map mapSlaveLibrary = (Map) listSlaveLibrary.get(j);
			                        if (null != mapSlaveLibrary 
			                        		&& null != mapSlaveLibrary.get("library_idx") 
			                        		&& StringUtils.isNotBlank(mapSlaveLibrary.get("library_idx").toString())){
			                            listLib.add((int) mapSlaveLibrary.get("library_idx"));
			                        }
			                    }
			                }
			            }
			        }
				}else{
					mapData = JsonUtils.fromJson(result,Map.class);
	                if (null != mapData && null != mapData.get("masterLibrary") && StringUtils.isNotBlank(mapData.get("masterLibrary").toString())){
	                    Map mapMaster = (Map) mapData.get("masterLibrary");
	                    //屏蔽云平台
	                    if (null != mapMaster && null != mapMaster.get("library_idx") && !mapMaster.get("library_idx").toString().equals("0")
	                    		&& StringUtils.isNotBlank(mapMaster.get("library_idx").toString())){
	                    	listLib.add((int) mapMaster.get("library_idx"));
	                    }
	                }
	                if (null != mapData && null != mapData.get("slaveLibrary") && StringUtils.isNotBlank(mapData.get("slaveLibrary").toString())){
	                    List listSlaveLibrary = (List) mapData.get("slaveLibrary");
	                    if (null != listSlaveLibrary && listSlaveLibrary.size() > 0){
	                        for (int j = 0;j < listSlaveLibrary.size();j++){
	                            Map mapSlaveLibrary = (Map) listSlaveLibrary.get(j);
	                            if (null != mapSlaveLibrary 
	                            		&& null != mapSlaveLibrary.get("library_idx") 
	                            		&& StringUtils.isNotBlank(mapSlaveLibrary.get("library_idx").toString())){
	                                listLib.add((int) mapSlaveLibrary.get("library_idx"));
	                            }
	                        }
	                    }
	                }
				}
			}
			
			Map param = new HashMap<>();
			param.put("libIdxs", listLib);
			if(page != null && pageSize != null) {
                param.put("page", page);
                param.put("pageSize", pageSize);
            }
			
			req = JsonUtils.toJson(param);
			resultEntity = operatorService.selectDeviceTroublesByLibIdxs(req);
//			if(resultEntity.getState()){
//				if(null != resultEntity.getResult()){
//					Map resultMap = JsonUtils.fromJson(JsonUtils.toJson(resultEntity.getResult()), Map.class);
//					if(resultMap.containsKey("rows")){
//						List rowList = (List) resultMap.get("rows");
//						if(null == rowList || rowList.size() != 4){
//							//去operator_infolog表里查 读者操作引起的 冲突信息
//							
//						}
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return resultEntity;
	}
	
	/**
	 * json = {
	 * 		"trouble_idxs":""
	 * }
	 * 更新消息提醒（故障提醒）
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateDeviceTroubles")
	@ResponseBody
	public ResultEntity updateDeviceTroubles(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("json");
			if(StringUtils.isBlank(req)){
				resultEntity.setMessage("参数不能为空");
				resultEntity.setState(false);
				return resultEntity;
			}
			
			Map reqMap = JsonUtils.fromJson(req, Map.class);
			
			String trouble_idxs = reqMap.get("trouble_idxs").toString();
			
			if(!StringUtils.isNotEmpty(trouble_idxs)){
				trouble_idxs = "[]";
			}
			
			Map param = new HashMap();
			param.put("trouble_idxs", trouble_idxs);
			
			resultEntity = operatorService.updateDeviceTroubles(JsonUtils.toJson(param));
			
		} catch (Exception e) {
			e.printStackTrace();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		
		return resultEntity;
	}
	
	/**
	 * json = []
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selRegionIdxsByLibIdxs")
	@ResponseBody
	public ResultEntity selRegionIdxsByLibIdxs(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();

		try {
			String req = request.getParameter("json");
			if(StringUtils.isBlank(req)){
				resultEntity.setMessage("参数不能为空");
				resultEntity.setState(false);
				return resultEntity;
			}
			List<Map<String,Object>> reqData = JsonUtils.fromJson(req,List.class);
			if(reqData.isEmpty()){
                resultEntity.setMessage("参数不能为空");
                resultEntity.setState(false);
                return resultEntity;
            }
            LibraryInfoEntity infoSelectParam = new LibraryInfoEntity();
            infoSelectParam.setLibrary_idx((Integer) reqData.get(0).get("library_idx"));
            infoSelectParam.setInfotype_idx(24);
            List<LibraryInfoEntity> libraryInfos = libraryService.selectLibraryInfo(infoSelectParam);
            String code;
            if(libraryInfos != null || !libraryInfos.isEmpty()){
                code = libraryInfos.get(0).getInfo_value();
                if(code.length() > 6){
                    code = code.substring(0,6);
                }
            }else{
                code = "01";
            }
            RegionEntity regiSelectParam = new RegionEntity();
            regiSelectParam.setRegi_code(code);

            return regionService.selRegionsOnRegionCode(regiSelectParam);
        } catch (Exception e) {
			e.printStackTrace();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		return resultEntity;
	}
	
	@RequestMapping("/sendFoundPwdVcode")
	@ResponseBody
	public ResultEntity sendVcode(HttpServletRequest request){
		String req = request.getParameter("json");
		if(com.ssitcloud.business.mobile.common.util.StringUtils.isEmail(req)){
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setMessage("没有手机号参数");
			resultEntity.setState(false);
			return resultEntity;
		}
		Map<String, Object> m = JsonUtils.fromJson(req, Map.class);
		String mobile = (String) m.get("mobile");
		if(com.ssitcloud.business.mobile.common.util.StringUtils.isEmpty(mobile)){
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setMessage("没有手机号参数");
			resultEntity.setState(false);
			return resultEntity;
		}
		
		return operatorService.sendFoundPwdVcode(mobile);
	}
	
	@RequestMapping("/changePwdByVcode")
	@ResponseBody
	public ResultEntity changePwdByVcode(String json){
		if(com.ssitcloud.business.mobile.common.util.StringUtils.isEmpty(json)){
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setMessage("提交参数为空");
			resultEntity.setState(false);
			return resultEntity;
		}
		Map<String, Object> data = JsonUtils.fromJson(json, Map.class);
		return operatorService.changePwdByVcode(data);
	}
}
