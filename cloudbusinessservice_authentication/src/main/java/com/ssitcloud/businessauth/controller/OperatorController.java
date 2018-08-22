package com.ssitcloud.businessauth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.businessauth.service.OperatorService;
import com.ssitcloud.businessauth.utils.JsonUtils;
import com.ssitcloud.businessauth.utils.LogUtils;
import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;

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
	@Resource
	private OperatorService operatorService;
	
	/**
	 * 设备登录
	 *
	 * <p>2016年4月15日 下午1:49:28
	 * <p>create by hjc
	 * @param req 
	 * {reqHead}+data:"{operator_id:"",ip:"192.168.1.11",port"",faild_times:""}"
	 * @param request
	 * @return
	 */
	@RequestMapping("/deviceLoginCheck")
	@ResponseBody
	public RespEntity deviceLoginCheck(String req, HttpServletRequest request){
		RespEntity respEntity = new RespEntity();
		RequestEntity reqEntity = new RequestEntity();
		try {
			reqEntity = JsonUtils.fromJson(req, RequestEntity.class);
			respEntity.setHeader(reqEntity);
			Map<String , String > param = new HashMap<>();
			param.put("operInfo", JsonUtils.toJson(reqEntity.getData()));
			String response = operatorService.loginCheck(param);
			if (response!=null) {
				ResultEntity resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
				respEntity.setData(resultEntity);
//				respEntity.setValue(resultEntity.getState(), "");
			}else {
				respEntity.setValue(false, "请求无响应");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			respEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return respEntity;
	}
	
	
	
	/**
	 * 通过操作员信息取得使用期限信息
	 *
	 * <p>2016年3月25日 上午10:57:10 
	 * <p>create by hjc
	 * @param req @see com.ssitcloud.busniess.common.entity.ReqEntity
	 * 			  data输入的参数条件。例：操作员ID {operator_id:"1"}
	 * @param request 
	 * @return RespEntity  @see com.ssitcloud.busniess.common.entity.RespEntity
	 * 结果集 ,data内容参数具体如下
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
	public RespEntity getVaildTime(String req,HttpServletRequest request){
		RespEntity respEntity = new RespEntity();
		try {
			RequestEntity reqEntity = JsonUtils.fromJson(req, RequestEntity.class);
			respEntity.setHeader(reqEntity);
			if(reqEntity.getData().get("operator_id")==null){
				respEntity.setValue(false, "operator_id 不能为空");;
				return respEntity;
			}
			Map<String , String > param = new HashMap<>();
			param.put("operInfo", JsonUtils.toJson(reqEntity.getData()));
			String response = operatorService.getVaildTime(param);
			if (response!=null) {
				respEntity.setData(JsonUtils.fromJson(response, ResultEntity.class));
			}else {
				respEntity.setValue(false, "请求无响应");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			respEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return respEntity;
	}
	
	
	
	/**
	 * 根据操作员表idx删除一个操作员（物理删除）
	 *
	 * <p>2016年3月30日 上午9:21:07 
	 * <p>create by hjc
	 * @param req  data如{operator_idx："1"}
	 * @param request
	 * @return RespEntity 结果集
	 */
	@RequestMapping("/delOperatorByIdx")
	@ResponseBody
	public RespEntity delOperatorByIdx(String req,HttpServletRequest request){
		RespEntity respEntity = new RespEntity();
		RequestEntity reqEntity = new RequestEntity();
		try {
			reqEntity = JsonUtils.fromJson(req, RequestEntity.class);
			respEntity.setHeader(reqEntity);
			Map<String, String> param = new HashMap<String,String>();
			param.put("operInfo", JsonUtils.toJson(reqEntity.getData()));
			String response = operatorService.delOperatorByIdx(param);
			if (response!=null) {
				respEntity.setData(JsonUtils.fromJson(response, ResultEntity.class));
			}else {
				respEntity.setValue(false, "请求无响应");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			respEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return respEntity;
	}
	
	
	/**
	 * 根据操作员表的operator_idx更新操作员的所有信息，（包括操作员ID）
	 *
	 * <p>2016年3月30日 下午5:46:19 
	 * <p>create by hjc
	 * @param req 
	 *  data如下
	 *  operInfo输入的参数条件。例：操作员ID,
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
	 * @return RespEntity结果集
	 */
	@RequestMapping("/updOperatorByIdx")
	@ResponseBody
	public RespEntity updOperatorByIdx(String req,HttpServletRequest request) {
		RespEntity respEntity = new RespEntity();
		RequestEntity reqEntity = new RequestEntity();
		try {
			reqEntity = JsonUtils.fromJson(req, RequestEntity.class);
			Map<String, String> param = new HashMap<String, String>();
			param.put("operInfo", JsonUtils.toJson(reqEntity.getData()));
			String response = operatorService.updOperatorByIdx(param);
			if (response!=null) {
				respEntity.setData(JsonUtils.fromJson(response, ResultEntity.class));
			}else {
				respEntity.setValue(false, "请求无响应");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			respEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return respEntity;
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
	 * @return RespEntity结果集
	 */
	@RequestMapping("/updOperatorByParam")
	@ResponseBody
	public RespEntity updOperatorByParam(String req,HttpServletRequest request){
		RespEntity respEntity = new RespEntity();
		RequestEntity reqEntity = new RequestEntity();
		try {
			reqEntity = JsonUtils.fromJson(req, RequestEntity.class);
			Map<String, String> param = new HashMap<String, String>();
			param.put("updParam", JsonUtils.toJson(reqEntity.getData().get("updParam")));
			param.put("whereParam", JsonUtils.toJson(reqEntity.getData().get("whereParam")));
//			String response = operatorService.updOperatorByIdx(param);
//			if (response!=null) {
//				respEntity.setData(JsonUtils.fromJson(response, ResultEntity.class));
//			}else {
//				respEntity.setValue(false, "请求无响应");
//			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			respEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return respEntity;
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
	public ResultEntity selOperatorByOperIdOrIdx(String req,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity=operatorService.selOperatorByOperIdOrIdx(req);
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
	 * 	operator_idx:1,2,3,4 或者 operator_id:1,2,3,4
	 * }
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"queryOperatorNameByoperIdxArr"})
	@ResponseBody
	public ResultEntity queryOperatorNameByoperIdxArr(String req,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity=operatorService.queryOperatorNameByoperIdxArr(req);
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
	 * @param req
	 * @see 
	 *  接收用户的参数，设备用户没有密码{operator_id:"1",operator_pwd:"KSKWDi",ip:"192.168.1.11",port:"",faild_times:"0"}
	 * @param request
	 * @return 
	 */
	@RequestMapping("/loginCheck")
	@ResponseBody
	public RespEntity loginCheck(String req,HttpServletRequest request) {
		RespEntity respEntity = new RespEntity();
		RequestEntity reqEntity = new RequestEntity();
		try {
			reqEntity = JsonUtils.fromJson(req, RequestEntity.class);
			respEntity.setHeader(reqEntity);
			Map<String , String > param = new HashMap<>();
			param.put("operInfo", JsonUtils.toJson(reqEntity.getData()));
			String response = operatorService.loginCheck(param);
			if (response!=null) {
				respEntity.setData(JsonUtils.fromJson(response, ResultEntity.class));
			}else {
				respEntity.setValue(false, "请求无响应");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			respEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return respEntity;
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
	public ResultEntity addDevice(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String response = operatorService.addDevice(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "连接超时");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/*@RequestMapping("/AddOperationLog")
	@ResponseBody
	public ResultEntity AddOperationLog(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String response = operatorService.addOperationLog(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "连接超时");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}*/
	
	/**
	 * 分页查询操作员信息
	 *
	 * <p>2016年6月8日 上午11:21:49 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryOperatorByParam")
	@ResponseBody
	public ResultEntity queryOperatorByParam(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = operatorService.queryOperatorByParam(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
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
	@RequestMapping("/queryOperatorDetailByIdx")
	@ResponseBody
	public ResultEntity queryOperatorDetailByIdx(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = operatorService.queryOperatorDetailByIdx(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteDevOperatorInfoByOperIds")
	@ResponseBody
	public ResultEntity deleteDevOperatorInfoByOperIds(HttpServletRequest request,String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = operatorService.deleteDevOperatorInfoByOperIds(req);
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
	 * <p>2016年7月5日 下午7:22:30 
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
	 * 根据登录用户的信息获取操作员类型信息
	 *
	 * <p>2016年7月7日 下午1:39:06 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryOperatorTypes")
	@ResponseBody
	public ResultEntity queryOperatorTypes(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = operatorService.queryOperatorTypes(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 新增操作员信息
	 *
	 * <p>2016年7月7日 下午4:50:10 
	 * <p>create by hjc
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOperator")
	@ResponseBody
	public ResultEntity addOperator(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
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
			result=operatorService.delOperator(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
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
	 * 用户修改密码接口
	 *
	 * <p>2016年7月28日 下午4:06:51 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/changePassword")
	@ResponseBody
	public ResultEntity changePassword(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = operatorService.changePassword(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
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
	 * <p>2016年9月21日 下午6:28:31 
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
	 * 设备库中将旧馆设备转移到新馆成功之后，保存日志
	 *
	 * <p>2016年9月23日 上午10:49:27 
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
	
	
}
