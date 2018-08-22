package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.FeedBackEntity;
import com.ssitcloud.entity.page.FeedBackPageEntity;
import com.ssitcloud.service.FeedBackService;

@Controller
@RequestMapping(value={"feedback"})
public class FeedBackController {
	@Resource
	private FeedBackService feedBackService;
	
	/**
	 * 新增APP中意见反馈FeedBackEntity
	 * 格式
	 * json = {
	 * 		"feedback_idx":"",//APP中意见反馈主键，自增
	 * 		"reader_idx":"",
	 * 		"feedback_type":"",
	 * 		"user_type":"",
	 * 		"feedback_value":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addFeedback"})
	@ResponseBody
	public ResultEntity addFeedBack (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				FeedBackEntity feedBackEntity = JsonUtils.fromJson(json, FeedBackEntity.class);
				int ret = feedBackService.insertFeedBack(feedBackEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",feedBackEntity);
				}else{
					resultEntity.setValue(false, "failed");
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
	
	/**
	 * 修改APP中意见反馈FeedBackEntity
	 * 格式
	 * json = {
	 * 		"feedback_idx":"",//APP中意见反馈主键，自增
	 * 		"reader_idx":"",
	 * 		"feedback_type":"",
	 * 		"user_type":"",
	 * 		"feedback_value":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateFeedback"})
	@ResponseBody
	public ResultEntity updateFeedBack (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				FeedBackEntity feedBackEntity = JsonUtils.fromJson(json, FeedBackEntity.class);
				int ret = feedBackService.updateFeedBack(feedBackEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",feedBackEntity);
				}else{
					resultEntity.setValue(false, "failed");
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
	
	/**
	 * 删除APP中意见反馈FeedBackEntity
	 * 格式
	 * json = {
	 * 		"feedback_idx":"",//APP中意见反馈主键，自增
	 * 		"reader_idx":"",
	 * 		"feedback_type":"",
	 * 		"user_type":"",
	 * 		"feedback_value":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteFeedback"})
	@ResponseBody
	public ResultEntity deleteFeedBack (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				FeedBackEntity feedBackEntity = JsonUtils.fromJson(json, FeedBackEntity.class);
				int ret = feedBackService.deleteFeedBack(feedBackEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",feedBackEntity);
				}else{
					resultEntity.setValue(false, "failed");
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
	
	/**
	 * 查询一条记录APP中意见反馈FeedBackEntity
	 * 格式
	 * json = {
	 * 		"feedback_idx":"",//APP中意见反馈主键，自增
	 * 		"reader_idx":"",
	 * 		"feedback_type":"",
	 * 		"user_type":"",
	 * 		"feedback_value":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectFeedback"})
	@ResponseBody
	public ResultEntity selectFeedBack (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				FeedBackEntity feedBackEntity = JsonUtils.fromJson(json, FeedBackEntity.class);
				feedBackEntity = feedBackService.queryOneFeedBack(feedBackEntity);
				resultEntity.setValue(true, "success","",feedBackEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多条APP中意见反馈记录FeedBackPageEntity
	 * 格式
	 * json = {
	 * 		"feedback_idx":"",//APP中意见反馈主键，自增
	 * 		"reader_idx":"",
	 * 		"feedback_type":"",
	 * 		"user_type":"",
	 * 		"feedback_value":"",
	 * 		"createtime":""
	 * 		pageCurrent第几页，从1开始
	 * 		pageSize每页多少条
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectFeedbacks"})
	@ResponseBody
	public ResultEntity selectFeedBacks (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			FeedBackPageEntity feedBackEntity = JsonUtils.fromJson(json, FeedBackPageEntity.class);
			List<FeedBackEntity> list = feedBackService.queryFeedBacks(feedBackEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
