package com.ssitcloud.dbauth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.OperatorType;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.LibraryEntity;
import com.ssitcloud.dbauth.entity.OperationLogEntity;
import com.ssitcloud.dbauth.entity.OperatorAppEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.PasswordHistoryEntity;
import com.ssitcloud.dbauth.entity.RSAEntiy;
import com.ssitcloud.dbauth.entity.SoxTemplateEntity;
import com.ssitcloud.dbauth.entity.page.OperatorPageEntity;
import com.ssitcloud.dbauth.param.GetVaildTimeParam;
import com.ssitcloud.dbauth.service.LibraryService;
import com.ssitcloud.dbauth.service.OperationLogService;
import com.ssitcloud.dbauth.service.OperatorService;
import com.ssitcloud.dbauth.service.PasswordHistoryService;
import com.ssitcloud.dbauth.service.RSAService;
import com.ssitcloud.dbauth.service.SoxTemplateService;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;
import com.ssitcloud.dbauth.utils.PasswordMatch;
import com.ssitcloud.dbauth.utils.RsaHelper;

/**
 * <p>操作员操作数据库操作接口
 *
 * <p>2016年3月25日 上午10:48:40  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/operator")
public class OperatorController {
	
	@Value("${isCheckIP}")
	private Integer isCheckIP;

	@Resource
	private OperatorService operatorService;
	
	@Resource
	private SoxTemplateService soxTemplateService;
	
	@Resource
	private OperationLogService operationLogService;
	
	@Resource
	private PasswordHistoryService passwordHistoryService;
	
	@Resource
	private RSAService rsaService;
	
	@Resource
	private LibraryService libraryService;
	
	
	/**
	 * 通过操作员信息取得使用期限信息
	 *
	 * <p>2016年3月25日 上午10:57:10 
	 * <p>create by hjc
	 * @param operInfo  operInfo输入的参数条件。例：操作员ID {operator_id:"1"}
	 * @param request 
	 * @return ResultEntity 结果集 ,result内容参数具体如下
	 *  <p>operator_id	String	操作员ID
		<p>operator_name	String	操作员名称
		<p>Sox_tpl_id	String	密码模板ID
		<p>Sox_tpl_name	String	密码模板名
		<p>Password_length	String 	密码长度
		<p>Password_charset	String	密码字符集
		<p>login_fail_times	String	登录失败次数
		<p>isLock	String	锁定时长
		<p>Lock_time	String	锁定时长
		<p>First_login_chgpwd	String	首次登录修改密码
		<p>Count_history_password	String	验证密码密码个
		<p>Password_validdays	String	密码有效天数
		<p>Vaild_time	String	用户可用时间段，例：9：00－18：00
		<p>Lib_name	String 	馆名称
		<p>Lib_service_tpl_desc	String	用户服务描述
		<p>Service_expirt_date	String	用户可使用的终止日期
		<p>Service_Start_date	String	用户可使用的开始日期
		<p>Max_device_count	String 	最大设备数
		<p>Max_operator_count	String 	最多操作员数
		<p>Max_sublib_count	String	最多分馆数
	 * @see com.ssitcloud.common.entity.ResultEntity
	 */
	@RequestMapping(value = "/getVaildTime")
	@ResponseBody
	public ResultEntity getVaildTime(String operInfo,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		OperatorEntity operator = new OperatorEntity();
		GetVaildTimeParam getVaildTimeParam = new GetVaildTimeParam();
		try {
			operator = mapper.readValue(operInfo,OperatorEntity.class);
			//如果传入参数的operator_id不为空，查询相关数据
			if (!StringUtils.isBlank(operator.getOperator_id()) ) {
				getVaildTimeParam = operatorService.getVaildTime(operator);
				resultEntity.setValue(true, "success","",getVaildTimeParam);
			}else {
				resultEntity.setValue(false, "operator_id 不能为空");
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
	 * 根据操作员表idx删除一个操作员（物理删除）
	 *
	 * <p>2016年3月30日 上午9:21:07 
	 * <p>create by hjc
	 * @param operInfo  如{operator_idx："1"}
	 * @param request
	 * @return ResultEntity结果集
	 */
	@RequestMapping("/delOperatorByIdx")
	@ResponseBody
	public ResultEntity delOperatorByIdx(String operInfo,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		OperatorEntity operatorEntity = new OperatorEntity();
		try {
			operatorEntity = mapper.readValue(operInfo,OperatorEntity.class);
			int ret = operatorService.delOperatorByIdx(operatorEntity);
			if (ret >= 1) {
				resultEntity.setValue(true, "success");
			}else {
				resultEntity.setValue(false, "failed");
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
	 * 根据操作员表的operator_idx更新操作员的所有信息，（包括操作员ID）
	 *
	 * <p>2016年3月30日 下午5:46:19 
	 * <p>create by hjc
	 * @param operInfo operInfo输入的参数条件。例：操作员ID,
	 *  <p>operator_idx:操作员表的idx  必传参数，其余的不能为空
	 *  <p>operator_id:操作员id   n(not null)
		<p>library_lID:图书馆id   n
		<p>sox_tpl_id:sox模板id  n
		<p>operator_name:用户名        n
		<p>operator_pwd:用户密码      n
		<p>operator_type:操作员类型     n
		<p>isActive:是否激活      n
		<p>isLock:是都锁定    n
		<p>isLogged:是否已经登录     n
	 * @param request
	 * @return ResultEntity结果集
	 */
	@RequestMapping("/updOperatorByIdx")
	@ResponseBody
	public ResultEntity updOperatorByIdx(String operInfo,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		OperatorEntity operatorEntity = new OperatorEntity();
		try {
			operatorEntity = mapper.readValue(operInfo,OperatorEntity.class);
			int ret = operatorService.updOperatorByIdx(operatorEntity);
			if (ret >= 1) {
				resultEntity.setValue(true, "success");
			}else {
				resultEntity.setValue(false, "failed");
			}
			resultEntity.setResult(operatorEntity);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据传入条件参数对一个操作员的信息进行更新,此操作可能批量
	 * 
	 * <p>2016年4月7日 上午10:03:22
	 * <p>create by hjc
	 * @param updParam 
	 * 要修改操作员的相关信息，JSON格式,不能为空
	   	例： {operator_id："1","operator_name":"ss"}
	    <p>operator_id:操作员id   
		<p>library_lID:图书馆id   
		<p>sox_tpl_id:sox模板id  
		<p>operator_name:用户名        
		<p>operator_pwd:用户密码      
		<p>operator_type:操作员类型     
		<p>isActive:是否激活      
		<p>isLock:是都锁定    
		<p>isLogged:是否已经登录     
	 * @param whereParam 条件参数，不能为空，根据条件参数对符合条件的操作员进行修改，JSON格式，
	   	例： {operator_idx:"1",}
	    <p>operator_idx:操作员表的idx  
	    <p>operator_id:操作员id  
		<p>library_lID:图书馆id   
		<p>sox_tpl_id:sox模板id  
		<p>operator_name:用户名        
		<p>operator_pwd:用户密码      
		<p>operator_type:操作员类型     
		<p>isActive:是否激活      
		<p>isLock:是都锁定    
		<p>isLogged:是否已经登录     
	 * @param request
	 * @return
	 */
	@RequestMapping("/updOperatorByParam")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ResultEntity updOperatorByParam(String updParam ,String whereParam,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper objectMapper = new ObjectMapper();
		if (StringUtils.isBlank(updParam) && StringUtils.isBlank(whereParam)) {
			resultEntity.setValue(false, "updParam 和 whereParam 不能为空");
		}
		Map<String, Object> uMap = new HashMap<String,Object>();
		Map<String, Object> wMap = new HashMap<String,Object>();
		try {
			uMap = objectMapper.readValue(updParam, Map.class);
			wMap = objectMapper.readValue(whereParam, Map.class);
			int ret = operatorService.updOperatorByParam(uMap, wMap);
			if (ret >= 1) {
				resultEntity.setValue(true, "success");
			}else{
				resultEntity.setValue(false, "没有数据更新");
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
	 * 根据操作员的operator_id 或者操作员表的operator_idx 查询
	 * 
	 * <p>2016年4月7日 下午4:38:25
	 * <p>create by hjc
	 * @param operInfo {operator_idx:"1"} 或 {operator_id:"1"}
	 * @param request 
	 * @return 返回结果集ResultEntity， result中内容为
	 *  <p>operator_idx	String	操作员表idx
	 *  <p>operator_id	String	操作员ID
	 *  <p>library_idx	String	图书馆idx
		<p>operator_name	String	操作员名称
		<p>operator_type	String	操作员类型
		<p>Sox_tpl_id	String	密码模板ID
		<p>isActive	String	是否激活
		<p>isLock	String	是否锁定
		<p>isLogged	String	是否已经登录
		<p>last_login_ip	String	最后登录ip
		<p>last_login_time	String	最后登录时间
		<p>login_fail_times	String	登录失败次数
		<p>last_lock_time	String	最后锁定时间
		<p>last_chgpwd_time	String	上次更改密码时间
	 */
	@RequestMapping("/selOperatorByOperIdOrIdx")
	@ResponseBody
	public ResultEntity selOperatorByOperIdOrIdx(String operInfo,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		OperatorEntity operatorEntity = new OperatorEntity();
		ObjectMapper mapper = new ObjectMapper();
		try {
			operatorEntity = mapper.readValue(operInfo, OperatorEntity.class);
			if (operatorEntity.getOperator_idx() == null  && StringUtils.isBlank(operatorEntity.getOperator_id())) {
				resultEntity.setValue(false, "operator_idx 和 operator_id不能都为空");
			}else {
				operatorEntity = operatorService.selOperatorByOperIdOrIdx(operatorEntity);
				resultEntity.setValue(true, "success","",operatorEntity);
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
	 * 修改密码接口
	 * 
	 * <p>2016年4月7日 下午5:42:16
	 * <p>create by hjc
	 * @param operInfo {"operator_id":"1","old_password":"1","new_password":"1"}
	 * @param request
	 * @return
	 */
	@RequestMapping("/changePassword")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ResultEntity changePassword(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		Map<String, String> proMap = new HashMap<String,String>();
		OperatorEntity operatorEntity = new OperatorEntity();
		SoxTemplateEntity soxTemplateEntity = new SoxTemplateEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			proMap = JsonUtils.fromJson(req, Map.class);
			if (proMap==null || proMap.isEmpty()) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			String old = proMap.get("old");
			String pwd1 = proMap.get("pwd1");
			String operid = proMap.get("operid");
			if (StringUtils.isBlank(operid) 
					&& StringUtils.isBlank(old)
					&& StringUtils.isBlank(pwd1)	) {
				resultEntity.setValue(false, "参数不能为空");
			}else {
				//根据operator获取操作员信息
				operatorEntity.setOperator_id(operid);
				operatorEntity = operatorService.selOperatorByOperIdOrIdx(operatorEntity);
				if(operatorEntity==null){
					resultEntity.setValue(false, operid+"用户不存在");
					return resultEntity;
				}else {
					soxTemplateEntity.setSox_tpl_id(operatorEntity.getSox_tpl_id());
					soxTemplateEntity = soxTemplateService.getSoxTemplateEntity(soxTemplateEntity);
					Integer countHistory = soxTemplateEntity.getCount_history_password();//判断多少次以内的密码不能重复
					List<PasswordHistoryEntity> pwdList = passwordHistoryService.selPwdHistoryByOperIdx(operatorEntity.getOperator_idx(), countHistory);
					if (pwdList!=null && pwdList.size()>0) {
						for (PasswordHistoryEntity p : pwdList) {
							if (p.getPassword().equals(pwd1)) {
								//密码与最近几次密码重复
								resultEntity.setValue(false, "新密码与最近几次密码相同");
								return resultEntity;
							}
						}
					}
					if(pwd1.length()<soxTemplateEntity.getPassword_length()){
						resultEntity.setValue(false, "密码长度需"+soxTemplateEntity.getPassword_length()+"位" ,"length","");
						return resultEntity;
					}
					//密码与最近几次密码不重复,验证密码是否符合规范
					if (!PasswordMatch.isMatch( pwd1, 
							soxTemplateEntity.getPassword_charset(), 
							soxTemplateEntity.getPassword_length()) ) {
						resultEntity.setValue(false, "新密码不符合规范:"+soxTemplateEntity.getPassword_charset() ,"charset","");
						return resultEntity;
					}
					//判断原始密码是否相同
					RSAEntiy rsaEntiy = rsaService.selRsaEntityTop();
					String oPwd = old;
					String dPwd = RsaHelper.decryRSA(operatorEntity.getOperator_pwd(), rsaEntiy.getPrivateKey());
					if (!oPwd.equals(dPwd)) {
						resultEntity.setValue(false, "原始密码错误");
						return resultEntity;
					}
					//修改密码，同时新增到历史密码表中
					int ret = operatorService.changePassword(operatorEntity.getOperator_idx(),pwd1,rsaEntiy.getPublicKey());
					
					if (ret >= 1) {
						resultEntity.setValue(true, "success","","修改密码成功");
					}else{
						resultEntity.setValue(false, "修改密码失败");
					}
					String retMessage="图书馆IDX："+operatorEntity.getLibrary_idx()+"|用户IDX："+operatorEntity.getOperator_idx()+"|用户名："+operatorEntity.getOperator_id();
					resultEntity.setRetMessage(retMessage);
				}
				//
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
	 * 登录验证
	 * 
	 * <p>2016年4月8日 下午5:17:38
	 * <p>create by hjc
	 * @param operInfo 接收用户的参数，设备用户没有密码{operator_id:"1",operator_pwd:"KSKWDi",ip:"192.168.1.11",port:"",faild_times:"0"}
	 * @param request
	 * @return 
	 */
	@RequestMapping("/loginCheck")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ResultEntity loginCheck(String operInfo,HttpServletRequest request) {
		
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> operMap = new HashMap<String,String>();
		
		
		try {
			//判断参数是否存在
			operMap = mapper.readValue(operInfo, Map.class);
			if ( StringUtils.isBlank(operMap.get("operator_id"))  ) {
				resultEntity.setValue(false, "operator_id不能为空");
				return resultEntity;
			}else {
				//进行登录验证
				resultEntity = operatorService.loginChcek(operMap);
				Boolean addOperLog = true;//设备同ip登陆不记录操作日志
				//保存操作日志
				OperationLogEntity operationLogEntity = new OperationLogEntity();
				operationLogEntity.setClient_ip(operMap.get("ip")==null?"0.0.0.0":operMap.get("ip"));
				operationLogEntity.setClient_port(operMap.get("port")==null?"0":operMap.get("port"));
				if (resultEntity.getState()) {
					operationLogEntity.setOperation_result(String.valueOf(resultEntity.getState()));
					operationLogEntity.setOperation(resultEntity.getRetMessage());
					if("success".equals(resultEntity.getMessage())){
						addOperLog = false;
					}
				}else {
					//设备是否验证IP
					if (resultEntity.getResult()!=null && "9".equals(resultEntity.getRetMessage().toString()) && isCheckIP==1) {
						resultEntity.setState(true);
						resultEntity.setMessage("success");
						operationLogEntity.setOperation_result("true");
						operationLogEntity.setOperation(resultEntity.getMessage());
					}else{
						operationLogEntity.setOperation_result(String.valueOf(resultEntity.getState()));
						operationLogEntity.setOperation(resultEntity.getMessage());
					}
				}
				operationLogEntity.setOperation_cmd("0101010205");// 登录验证操作类型 2017年3月4号修改新命令码
				operationLogEntity.setError_code("0");//错误码
				if(addOperLog){
					//保存操作日志
					//20180223修改 by xbf---去除登录信息的保存
					//operationLogService.addOperationLogWithOperatorId(operationLogEntity,operMap.get("operator_id"));
				}
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
	
	
	/**
	 * 新增一个设备用户，
	 * 包括operator表，以及ipWhite表
	 *
	 * <p>2016年5月5日 下午3:09:05 
	 * <p>create by hjc
	 * @param json
	 * @param request
	 * @return
	 */
	@RequestMapping("/addDevice")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ResultEntity addDevice(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> param = new HashMap<>();
		try {
			
			if (StringUtils.isBlank(json)) {
				resultEntity.setValue(false, "json is null");
				return resultEntity;
			}
			param = JsonUtils.fromJson(json, Map.class);
			
			//先查询这个param中相同id 的设备用户是否存在，如果存在则 返回这个对象
			String operator_id = param.get("operator_id")==null?"":param.get("operator_id")+"";
			
			if(operator_id.equals("")){
				resultEntity.setValue(false, "设备id不能为空","" , "");
				return resultEntity;
			}else{
				//根据id查询此设备
				OperatorEntity operatorEntity  = new OperatorEntity();
				operatorEntity.setOperator_id(operator_id);
				operatorEntity = operatorService.selOperatorByOperIdOrIdx(operatorEntity);
				if (operatorEntity!=null && operatorEntity.getOperator_idx()!=null) {
					resultEntity.setValue(true, "success","",operatorEntity);
					return resultEntity;
				}
				
				resultEntity = operatorService.addDevice(param);
				
				//保存操作日志
				OperationLogEntity operationLogEntity = new OperationLogEntity();
				operationLogEntity.setClient_ip(param.get("ip")==null?"is null":param.get("ip").toString());
				operationLogEntity.setClient_port(param.get("port")==null?"is null":param.get("port").toString());
				if (resultEntity.getState()) {
//					operationLogEntity.setOperation_result(String.valueOf(resultEntity.getState()));
//					operationLogEntity.setOperation(resultEntity.getRetMessage());
				}else {
					//新增成功的时候保存日志
					operationLogEntity.setOperation_result(String.valueOf(resultEntity.getState()));
					operationLogEntity.setOperation(resultEntity.getRetMessage());
					operationLogEntity.setOperation_cmd("0101010101");// 新增用户操作类型 2017年3月4号修改新命令码
					//保存操作日志
					operationLogService.addOperationLogWithOperatorIdx(operationLogEntity,param.get("admin_idx").toString());
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
	 * req=
	 * {
	 * 	[1,2,3,4,5,6,7]
	 * }
	 * 
	 * @author lbh
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryOperatorNameByoperIdxArr"})
	@ResponseBody
	public ResultEntity queryOperatorNameByoperIdxArr(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=operatorService.queryOperatorNameByoperIdxArr(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/*@RequestMapping("/AddOperationLog")
	@ResponseBody
	public ResultEntity AddOperationLog(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json)) {
				resultEntity.setValue(false, "json is null");
				return resultEntity;
			}
			OperationLogEntity operlog = JsonUtils.fromJson(json, OperationLogEntity.class);
			int ret = operationLogService.addOperationLog(operlog);
			if(ret<=0){
				throw new RuntimeException("无效的图书馆模板id");
			}
			resultEntity.setValue(true, "success");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return resultEntity;
	}*/
	
	/**
	 * 分页查询操作员信息
	 *
	 * <p>2016年6月8日 下午1:45:03 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/queryOperatorByParam"})
	@ResponseBody
	public ResultEntity queryOperatorByParam(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			System.out.println("queryOperatorByParam(param)--->["+req+"]");
			Map<String, Object> param = new HashMap<>();	
			if (!StringUtils.isBlank(req)) {
				param = JsonUtils.fromJson(req, Map.class);
				String operatorType = null;
				String operatorIdx = null;
				String libraryIdx = null;
				OperatorPageEntity operatorPageEntity = new OperatorPageEntity();
				
				if (param.get("operatorType")!=null && !param.get("operatorType").toString().equals("")) {
					operatorType = param.get("operatorType").toString();
				}else{
					result.setValue(false, "操作员类型不能为空");
					return result;
				}
				
				if (param.get("operator_idx")!=null && !param.get("operator_idx").toString().equals("")) {
					operatorIdx = param.get("operator_idx").toString();
				}else{
					result.setValue(false, "操作员idx不能为空");
					return result;
				}
				
				if (param.get("library_idx")!=null && !param.get("library_idx").toString().equals("")) {
					libraryIdx = param.get("library_idx").toString();
				}else{
					result.setValue(false, "操作员所属馆不能为空");
					return result;
				}
				
				if (param.get("page")!=null && !param.get("page").toString().equals("")) {
					operatorPageEntity.setPage(Integer.valueOf(param.get("page").toString()));
				}
				
				if (param.get("pageSize")!=null && !param.get("pageSize").toString().equals("")) {
					operatorPageEntity.setPageSize(Integer.valueOf(param.get("pageSize").toString()));
				}
				
				if (param.get("type")!=null && !param.get("type").toString().equals("")) {
					operatorPageEntity.setType(param.get("type").toString());
				}else {
					operatorPageEntity.setType("");
				}
				
				if (param.get("keyword")!=null && !param.get("keyword").toString().equals("")) {
					operatorPageEntity.setKeyword(param.get("keyword").toString());
				}else {
					operatorPageEntity.setKeyword("");
				}
				
				//系统管理员,查询所有
				if (operatorType.equals(OperatorType.CLOUD_ADMIN)) {
					operatorPageEntity.setQueryType("1");
				}
				//云平台维护人员，如果有权限，查询所有,但是不包括云平台管理员
				if (operatorType.equals(OperatorType.MAINTRINER)) {
					operatorPageEntity.setQueryType("2");
				}
				//图书馆管理员，查询本馆，
				if (operatorType.equals(OperatorType.LIBRARY_ADMIN)) {
					operatorPageEntity.setQueryType("3");
					operatorPageEntity.setLibrary_idx(Integer.valueOf(libraryIdx));
				}
				//图书馆普通用户，如果有权限，查询本馆
				if (operatorType.equals(OperatorType.LIBRARY_USER)) {
					operatorPageEntity.setQueryType("3");
					operatorPageEntity.setLibrary_idx(Integer.valueOf(libraryIdx));
				}
				//查询分页信息
				operatorPageEntity = operatorService.queryOperatorByParam(operatorPageEntity);
				result.setValue(true, "success","",operatorPageEntity);
			}
			
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 根据idx查询用户的详细信息
	 *
	 * <p>2016年6月13日 上午11:03:14 
	 * <p>create by hjc
	 * @param request
	 * @param req {"library_idx":"1","operatorType":"1","operator_idx":"1","idx":"1"}
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryOperatorDetailByIdx")
	@ResponseBody
	public ResultEntity queryOperatorDetailByIdx(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req)) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			Map<String, String> param = new HashMap<>();
			param = JsonUtils.fromJson(req, Map.class);
			String library_idx = param.get("library_idx"); //进行此操作的操作员所属馆
			String operator_idx = param.get("operator_idx");//进行此操作的操作员idx
			String operator_type = param.get("operatorType");//进行此操作的操作员类型
			String idx = param.get("idx");
			if (StringUtils.isBlank(idx) || StringUtils.isBlank(library_idx)
					|| StringUtils.isBlank(operator_idx) || StringUtils.isBlank(operator_type)) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			OperatorEntity operatorEntity = new OperatorEntity();
			operatorEntity.setOperator_idx(Integer.valueOf(idx));
			operatorEntity = operatorService.selOperatorByOperIdOrIdx(operatorEntity);
			if (operatorEntity==null) {
				resultEntity.setValue(false, "没有该用户信息");
				return resultEntity;
			}
			operatorEntity.setOperator_pwd("");//不传递密码信息
			Integer otype = operatorEntity.getOperator_type();
			//不能查看比自己高級的用戶类型
			if (otype < Integer.valueOf(operator_type)){
				resultEntity.setValue(false, "没有该用户信息");
				return resultEntity;
			}
			
			//不是云平台管理员或者维护人员，不能查看其它馆的信息
			if (!operator_type.equals("1") && !library_idx.equals(String.valueOf(operatorEntity.getLibrary_idx()))) {
				resultEntity.setValue(false, "没有该用户信息");
				return resultEntity;
			}
			
			//查询所属图书馆信息
			LibraryEntity libraryEntity = new LibraryEntity();
			libraryEntity.setLibrary_idx(operatorEntity.getLibrary_idx());
			libraryEntity = libraryService.selLibraryByIdxOrId(libraryEntity);
			
			//获取信息 infolist
			List<Map<String, Object>> infoList =  operatorService.queryOperatorInfoList(operatorEntity);
			//获取可以添加的信息list 
			String[] arr = {"6","7","8","9","10"};
			List<Map<String, Object>> addList = operatorService.queryOperatorAddInfoList(arr);
			
			//封装操作员类型，
			List<Map<String, String>> typeList = new ArrayList<>();
			if(Integer.valueOf(operator_type) <= Integer.valueOf(OperatorType.CLOUD_ADMIN)){
				Map<String, String> map = new HashMap<>();
				map.put("type", "1");
				map.put("name", "云平台管理员");
				typeList.add(map);
			}
			if(Integer.valueOf(operator_type) <= Integer.valueOf(OperatorType.MAINTRINER)){
				Map<String, String> map = new HashMap<>();
				map.put("type", "2");
				map.put("name", "云平台维护人员");
				typeList.add(map);
			}
			if(Integer.valueOf(operator_type) <= Integer.valueOf(OperatorType.LIBRARY_ADMIN)){
				Map<String, String> map = new HashMap<>();
				map.put("type", "3");
				map.put("name", "图书馆管理员");
				typeList.add(map);
			}
			if(Integer.valueOf(operator_type) <= Integer.valueOf(OperatorType.LIBRARY_USER)){
				Map<String, String> map = new HashMap<>();
				map.put("type", "4");
				map.put("name", "图书馆馆员");
				typeList.add(map);
			}
			
			resultMap.put("typeList", typeList);
			resultMap.put("addList", addList);
			resultMap.put("infoList", infoList);
			resultMap.put("library", libraryEntity);
			resultMap.put("operator", operatorEntity);
			
			resultEntity.setValue(true, "","",resultMap);
			
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	/**
	 * 根据oper_id删除设备用户信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteDevOperatorInfoByOperIds")
	@ResponseBody
	public ResultEntity deleteDevOperatorInfoByOperIds(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=operatorService.deleteDevOperatorInfoByOperIds(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		
		return result;
	}
	/**
	 * 更新操作员信息 
	 *
	 * <p>2016年7月5日 下午6:23:18 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateOperator")
	@ResponseBody
	public ResultEntity updateOperator(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空！");
			}
			resultEntity = operatorService.updateOperator(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	

	/**
	 * 新增用户
	 *
	 * <p>2016年7月7日 下午4:59:44 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOperator")
	@ResponseBody
	public ResultEntity addOperator(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			resultEntity = operatorService.addOperator(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 根据登录用户的信息查询可以显示的操作员类型信息
	 *
	 * <p>2016年7月5日 下午6:23:18 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryOperatorTypes")
	@ResponseBody
	public ResultEntity queryOperatorTypes(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			
			Map<String, Object> param = JsonUtils.fromJson(req, Map.class);
			/*
			 * param.put("operIdx", "1");
				param.put("operLibIdx", "0");
				param.put("operType", "1");
			 */
			if(param.get("operType")==null || param.get("operType").toString().equals("")
					|| param.get("operLibIdx")==null || param.get("operLibIdx").toString().equals("")
					|| param.get("operIdx")==null || param.get("operIdx").toString().equals("")){
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			String operator_type = param.get("operType").toString();
			//封装操作员类型，
			List<Map<String, String>> typeList = new ArrayList<>();
			if(Integer.valueOf(operator_type) <= Integer.valueOf(OperatorType.CLOUD_ADMIN)){
				Map<String, String> map = new HashMap<>();
				map.put("type", "1");
				map.put("name", "云平台管理员");
				typeList.add(map);
			}
			if(Integer.valueOf(operator_type) <= Integer.valueOf(OperatorType.MAINTRINER)){
				Map<String, String> map = new HashMap<>();
				map.put("type", "2");
				map.put("name", "云平台维护人员");
				typeList.add(map);
			}
			if(Integer.valueOf(operator_type) <= Integer.valueOf(OperatorType.LIBRARY_ADMIN)){
				Map<String, String> map = new HashMap<>();
				map.put("type", "3");
				map.put("name", "图书馆管理员");
				typeList.add(map);
			}
			if(Integer.valueOf(operator_type) <= Integer.valueOf(OperatorType.LIBRARY_USER)){
				Map<String, String> map = new HashMap<>();
				map.put("type", "4");
				map.put("name", "图书馆馆员");
				typeList.add(map);
			}
			
			resultEntity.setValue(true, "success", "", typeList);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 删除操作员信息（设置成失效）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/delOperator"})
	@ResponseBody
	public ResultEntity delOperator(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("")) {
				result.setValue(false, "参数不能为空！");
				return result;
			}
			result=operatorService.delOperator(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常 "+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 删除多个操作员信息（设置成失效）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/delMultiOperator"})
	@ResponseBody
	public ResultEntity delMultiOperator(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			result=operatorService.delMultiOperator(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
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
			String req = request.getParameter("req");
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
	 * 重置用户密码为888888
	 *
	 * <p>2016年7月14日 下午7:32:12 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/resetPassword")
	@ResponseBody
	public ResultEntity resetPassword(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = operatorService.resetPassword(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	/**
	 * 根据设备用户id查询设备的ip
	 *
	 * <p>2016年9月21日 下午6:57:15 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryDeviceIps")
	@ResponseBody
	public ResultEntity queryDeviceIps(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = operatorService.queryDeviceIps(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	/**
	 * 发送请求到鉴权库，将旧馆设备转移到新馆，并且修改相关ip
	 *
	 * <p>2016年9月21日 下午6:31:15 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/authTransferToLibrary")
	@ResponseBody
	public ResultEntity authTransferToLibrary(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = operatorService.authTransferToLibrary(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 发送请求到鉴权库，将旧馆设备转移到新馆，并且修改相关ip
	 *
	 * <p>2016年9月21日 下午6:31:15 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/devTransferToLibraryLog")
	@ResponseBody
	public ResultEntity devTransferToLibraryLog(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = operatorService.devTransferToLibraryLog(req);
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
	 * <p>2016年12月20日 下午1:36:25 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkPwdFormat")
	@ResponseBody
	public ResultEntity checkPwdFormat(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = operatorService.checkPwdFormat(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 馆员App
	 * 通过 operator_idx或者operator_id 查询馆员信息
	 * 2017年2月25日
	 * create by shuangjunjie
	 * @param req {operator_idx:"1"} 或 {operator_id:"1"}
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectOperAppByIdOrIdx")
	@ResponseBody
	public ResultEntity selectOperAppByIdOrIdx(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		OperatorAppEntity operatorAppEntity = new OperatorAppEntity();
		ObjectMapper mapper = new ObjectMapper();
		try {
			operatorAppEntity = mapper.readValue(req, OperatorAppEntity.class);
			if (operatorAppEntity.getOperator_idx() == null  && StringUtils.isBlank(operatorAppEntity.getOperator_id())) {
				resultEntity.setValue(false, "operator_idx 和 operator_id不能都为空");
			}else {
				operatorAppEntity = operatorService.selectOperAppByIdOrIdx(operatorAppEntity);
				resultEntity.setValue(true, "success","",operatorAppEntity);
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
	 * 馆员App
	 * 根据操作员idx更新操作员信息
	 * 2017年2月25日
	 * create by shuangjunjie
	  * <p>operator_idx:操作员表的idx  必传参数，其余的不能为空
	 	<p>email:邮箱   n(not null)
		<p>mobile:手机号   n
		<p>id_card身份证号  n
	 */
	@RequestMapping("/updateOperAppByIdx")
	@ResponseBody
	public ResultEntity updateOperAppByIdx(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		OperatorAppEntity operatorAppEntity = new OperatorAppEntity();
		try {
			operatorAppEntity = mapper.readValue(req,OperatorAppEntity.class);
			int ret = operatorService.updateOperAppByIdx(operatorAppEntity);
			if (ret >= 1) {
				resultEntity.setValue(true, "success");
			}else {
				resultEntity.setValue(false, "修改失败");
			}
//			Map<String,Object> map = JsonUtils.fromJson(JsonUtils.toJson(operatorAppEntity), Map.class);
//			map.remove("isLogged");
//			map.remove("last_login_ip");
//			map.remove("last_login_time");
//			map.remove("last_lock_time");
//			map.remove("last_chgpwd_time");
//			map.remove("login_fail_times");
//			map.remove("createtime");
//			
//			map.put("operator_idx", operatorAppEntity.getOperator_idx());
//			map.put("operator_id", operatorAppEntity.getOperator_id());
//			map.put("library_idx", operatorAppEntity.getLibrary_idx());
//			map.put("sox_tpl_id", operatorAppEntity.getSox_tpl_id());
//			map.put("operator_name", operatorAppEntity.getOperator_name());
//			map.put("operator_type", operatorAppEntity.getOperator_type());
//			map.put("isActive", operatorAppEntity.getIsActive());
//			map.put("isLock", operatorAppEntity.getIsLock());
//			map.put("mobile", operatorAppEntity.getMobile());
//			map.put("email", operatorAppEntity.getEmail());
//			map.put("id_card", operatorAppEntity.getId_card());
			
			resultEntity.setResult(operatorAppEntity);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
		
	}
	
	/**
	 * 馆员App
	 * 根据操作员idx更新操作员密码
	 * json = {
	 * 		"operator_idx":""
	 * }
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateOperAppPwdByIdx")
	@ResponseBody
	public ResultEntity updateOperAppPwdByIdx(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		OperatorAppEntity operatorAppEntity = new OperatorAppEntity();
		try {
			operatorAppEntity = mapper.readValue(req,OperatorAppEntity.class);
			int ret = operatorService.updateOperAppPwdByIdx(operatorAppEntity);
			if (ret >= 1) {
				resultEntity.setValue(true, "success");
			}else {
				resultEntity.setValue(false, "failed");
			}
			
			Map<String,Object> map = JsonUtils.fromJson(JsonUtils.toJson(operatorAppEntity), Map.class);
			map.remove("operator_pwd");
			map.remove("operator_type");
			map.remove("isActive");
			map.remove("isLock");
			map.remove("isLogged");
			map.remove("last_login_ip");
			map.remove("last_login_time");
			map.remove("last_lock_time");
			map.remove("last_chgpwd_time");
			map.remove("login_fail_times");
			map.remove("createtime");
			resultEntity.setResult(map);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
		
	}
	
	/**
	 * 馆员App
	 * 密码找回 验证身份
	 * json = {
	 * 		"mobile":"",
	 * 		"email":"",
	 * 		"id_card":""
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
			req = request.getParameter("req");
			OperatorAppEntity operatorAppEntity = JsonUtils.fromJson(req, OperatorAppEntity.class);
			result = operatorService.checkOperIdentity(operatorAppEntity);
			if(null != result.getResult() && result.getState()){
				result.setValue(true, "success", "", result.getResult());
			}else{
				result.setValue(false, "failed");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 通过手机或者邮箱或者身份证号查出operator_id
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectOperatorIdByParam")
	@ResponseBody
	public ResultEntity selectOperatorIdByParam(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("req");
			OperatorAppEntity operatorAppEntity = JsonUtils.fromJson(req, OperatorAppEntity.class);
			result = operatorService.selectOperatorIdByParam(operatorAppEntity);
			if(null != result.getResult() && result.getState()){
				result.setValue(true, "success", "", result.getResult());
			}else{
				result.setValue(false, "failed", "", null);
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 通过查询参数查询Operator
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectByParam")
	@ResponseBody
	public ResultEntity selectByParam(String req){
		ResultEntity result = new ResultEntity();
		try {
			OperatorAppEntity operatorEntity = JsonUtils.fromJson(req, OperatorAppEntity.class);
			List<OperatorAppEntity> d = operatorService.selectByParam(operatorEntity);
			if(null != d){
				result.setValue(true, "success", "", d);
			}else{
				result.setValue(false, "failed", "", null);
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
}
