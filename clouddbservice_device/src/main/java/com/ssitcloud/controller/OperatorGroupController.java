package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.OperatorGroupEntity;
import com.ssitcloud.entity.page.OperGroupMgmtPageEntity;
import com.ssitcloud.service.OperatorGroupService;

/**
 * 
 * @comment 操作员组表Controller
 * 
 * @author hwl
 * @data 2016年4月5日
 */
@Controller
@RequestMapping("/operatorgroup")
public class OperatorGroupController {

	@Resource
	OperatorGroupService operatorGroupService;

	/**
	 * 添加操作员组数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddOperGroup" })
	@ResponseBody
	public ResultEntity AddOperGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = operatorGroupService.addOperatorGroup(JsonUtils.fromJson(
					request.getParameter("json"), OperatorGroupEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新操作员组数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdOperGroup" })
	@ResponseBody
	public ResultEntity UpdOperGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = operatorGroupService.updOperatorGroup(JsonUtils.fromJson(
					request.getParameter("json"), OperatorGroupEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除操作员组数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteOperGroup" })
	@ResponseBody
	public ResultEntity DeleteOperGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = operatorGroupService.delOperatorGroup(JsonUtils.fromJson(
					request.getParameter("json"), OperatorGroupEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 根据条件查询数据
	 * 
	 * @param operatorgroupEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "SelectOperGroup" })
	@ResponseBody
	public ResultEntity SelectOperGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<OperatorGroupEntity> oEntities = operatorGroupService
					.selbyidOperatorGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							OperatorGroupEntity.class));
			result.setResult(oEntities);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 查询操作员组 （带参数）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"queryOperGroupByparam"})
	@ResponseBody
	public ResultEntity queryOperGroupByparam(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			OperGroupMgmtPageEntity OperGroupMgmtPage=operatorGroupService.queryOperGroupByparamDb(req);
			result.setResult(OperGroupMgmtPage);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 新增操作
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"addOperGroup"})
	@ResponseBody
	public ResultEntity addOperGroup(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=operatorGroupService.addOperGroup(req);
		} catch (Exception e) {
			String msg=e.getCause().getMessage();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
			if(org.apache.commons.lang.StringUtils.contains(msg, "Duplicate entry")
					&& org.apache.commons.lang.StringUtils.contains(msg, "groupid")){
				result.setRetMessage("组ID已经存在，请修改");
			}else 
			if(org.apache.commons.lang.StringUtils.contains(msg, "Duplicate entry")
					&& org.apache.commons.lang.StringUtils.contains(msg, "groupname")){
				result.setRetMessage("组名已经存在，请修改");
			}
		}
		return result;
	}
	/**
	 * 删除分组
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"delOperGroup"})
	@ResponseBody
	public ResultEntity delOperGroup(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=operatorGroupService.delOperGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 修改组
	 * @return
	 */
	@RequestMapping(value={"updOperGroup"})
	@ResponseBody
	public ResultEntity updOperGroup(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=operatorGroupService.updOperGroup(req);
		} catch (Exception e) {
			String msg=e.getCause().getMessage();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
			if(org.apache.commons.lang.StringUtils.contains(msg, "Duplicate entry")
					&& org.apache.commons.lang.StringUtils.contains(msg, "groupid")){
				result.setRetMessage("组ID已经存在，请修改");
			}else if(org.apache.commons.lang.StringUtils.contains(msg, "Duplicate entry")
					|| org.apache.commons.lang.StringUtils.contains(msg, "groupname")){
					result.setRetMessage("组名已存在，请修改");
			}
		}
		return result;
	}
	
	/**
	 * 查询所有的用户组信息，以及用户组对应的权限信息
	 *
	 * <p>2016年6月23日 下午7:19:45 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllServiceGroup")
	@ResponseBody
	public ResultEntity queryAllServiceGroup(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req)) {
				result.setValue(false, "参数为空");
				return result;
			}
			result = operatorGroupService.queryAllServiceGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询所有的用户组信息，以及用户组对应的权限信息
	 *
	 * <p>2016年6月23日 下午7:19:45 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryLibraryServiceGroup")
	@ResponseBody
	public ResultEntity queryLibraryServiceGroup(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				result.setValue(false, "参数为空");
				return result;
			}
			result = operatorGroupService.queryLibraryServiceGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	

}
