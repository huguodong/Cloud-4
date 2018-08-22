package com.ssitcloud.dbstatistics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbstatistics.common.utils.ExceptionHelper;
import com.ssitcloud.dbstatistics.common.utils.JsonUtils;
import com.ssitcloud.dbstatistics.entity.BookRankRolePageEntity;
import com.ssitcloud.dbstatistics.service.RecommendService;
import com.ssitcloud.statistics.entity.BookRankRoleEntity;

@Controller
@RequestMapping(value={"/recommend"})
public class RecommendController {
	@Resource
	private RecommendService recommendService;
	
	@RequestMapping(value={"/editRankRole"})
	@ResponseBody
	public ResultEntity editRankRole(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isNotBlank(req)) {
				BookRankRoleEntity bookRankRoleEntity = JsonUtils.fromJson(req, BookRankRoleEntity.class);
				Integer role_idx=bookRankRoleEntity.getRole_idx();
				if(null==role_idx){
					resultEntity = recommendService.addRankRole(bookRankRoleEntity);
				}else{
					resultEntity = recommendService.updateRankRole(bookRankRoleEntity);
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	@RequestMapping(value={"/deleteRankRole"})
	@ResponseBody
	public ResultEntity deleteRankRole(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isNotBlank(req)) {
				resultEntity = recommendService.deleteRankRole(req);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	@RequestMapping(value={"/findRankRoleByIdx"})
	@ResponseBody
	public ResultEntity findRankRoleByIdx(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isNotBlank(req)) {
				BookRankRoleEntity bookRankRoleEntity = JsonUtils.fromJson(req, BookRankRoleEntity.class);
				resultEntity = recommendService.findRankRoleByIdx(bookRankRoleEntity);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"/findRankRoleByParam"})
	@ResponseBody
	public ResultEntity findRankRoleByParam(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isNotBlank(req)) {
				BookRankRoleEntity bookRankRoleEntity = JsonUtils.fromJson(req, BookRankRoleEntity.class);
				resultEntity = recommendService.findRankRoleByParam(bookRankRoleEntity);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	@RequestMapping(value={"/findRankRoleList"})
	@ResponseBody
	public ResultEntity findRankRoleList(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isNotBlank(req)) {
				BookRankRolePageEntity bookRankRolePageEntity = JsonUtils.fromJson(req, BookRankRolePageEntity.class);
				resultEntity = recommendService.findRankRoleList(bookRankRolePageEntity);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
