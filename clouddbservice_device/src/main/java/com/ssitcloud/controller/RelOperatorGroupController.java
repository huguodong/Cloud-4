package com.ssitcloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ssitcloud.entity.RelOperatorGroupEntity;
import com.ssitcloud.service.RelOperatorGroupService;

/**
 * 
 * @comment 操作员分组关联表Controller
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Controller
@RequestMapping("/reloperatorgroup")
public class RelOperatorGroupController {

	@Resource
	RelOperatorGroupService relOperatorGroupService;

	/**
	 * 添加操作员分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddRelOperGroup" })
	@ResponseBody
	public ResultEntity AddRelOperGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = relOperatorGroupService.addRelOperatorGroup(JsonUtils.fromJson(
					request.getParameter("json"), RelOperatorGroupEntity.class));
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
	 * 更新操作员分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdRelOperGroup" })
	@ResponseBody
	public ResultEntity UpdRelOperGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = relOperatorGroupService.updRelOperatorGroup(JsonUtils.fromJson(
					request.getParameter("json"), RelOperatorGroupEntity.class));
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
	 * 删除操作员分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteRelOperGroup" })
	@ResponseBody
	public ResultEntity DeleteRelOperGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = relOperatorGroupService.delRelOperatorGroup(JsonUtils.fromJson(
					request.getParameter("json"), RelOperatorGroupEntity.class));
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
	 * 条件查询操作员分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelectRelOperGroup" })
	@ResponseBody
	public ResultEntity SelectRelOperGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<RelOperatorGroupEntity> rEntities = relOperatorGroupService
					.selbyidRelOperatorGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							RelOperatorGroupEntity.class));
			result.setResult(rEntities);
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
	 * 根据用户idx查询其所在用户组信息
	 *
	 * <p>2016年6月28日 下午5:24:02 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryOperatorGroupByOperIdx")
	@ResponseBody
	public ResultEntity queryOperatorGroupByOperIdx(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}") ) {
				result.setValue(false, "参数不能为空！");
				return result;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param = JsonUtils.fromJson(req, Map.class);
			if(param.get("idx")==null || param.get("idx").toString().equals("")){
				result.setValue(false, "参数不能为空！");
				return result;
			}
			RelOperatorGroupEntity groupEntity = new RelOperatorGroupEntity();
			groupEntity.setOperator_idx(Integer.valueOf(param.get("idx").toString()));
			groupEntity = relOperatorGroupService.queryOperatorGroupByOperIdx(groupEntity);
			result.setValue(true, "", "", groupEntity);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 更新操作员的用户组
	 *
	 * <p>2016年7月14日 下午3:36:02 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateOperatorGroup")
	@ResponseBody
	public ResultEntity updateOperatorGroup(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				result.setValue(false, "参数为空");
				return result;
			}
			result = relOperatorGroupService.updateOperatorGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 通过 OperatorIdxs删除 RelOperatorGroup表信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteRelOperatorGroupByOperatorIdxs")
	@ResponseBody
	public ResultEntity deleteRelOperatorGroupByOperatorIdxs(HttpServletRequest request,String req){
		ResultEntity resultEntity=new ResultEntity();
		try {
			resultEntity=relOperatorGroupService.deleteRelOperatorGroupByOperatorIdxs(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
}
