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
import com.ssitcloud.entity.FeedbackReplyEntity;
import com.ssitcloud.entity.page.FeedbackReplyPageEntity;
import com.ssitcloud.service.FeedbackReplyService;

@Controller
@RequestMapping(value={"feedbackReply"})
public class FeedbackReplyController {
	@Resource
	private FeedbackReplyService feedbackReplyService;
	
	/**
	 * 新增APP意见反馈回复FeedbackReplyEntity
	 * 格式
	 * json = {
	 * 		"reply_idx":"",//APP意见反馈回复主键，自增
	 * 		"feedback_idx":"",
	 * 		"operator_idx":"",
	 * 		"reply_value":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addFeedbackReply"})
	@ResponseBody
	public ResultEntity addFeedbackReply (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				FeedbackReplyEntity feedbackReplyEntity = JsonUtils.fromJson(json, FeedbackReplyEntity.class);
				int ret = feedbackReplyService.insertFeedbackReply(feedbackReplyEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",feedbackReplyEntity);
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
	 * 修改APP意见反馈回复FeedbackReplyEntity
	 * 格式
	 * json = {
	 * 		"reply_idx":"",//APP意见反馈回复主键，自增
	 * 		"feedback_idx":"",
	 * 		"operator_idx":"",
	 * 		"reply_value":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateFeedbackReply"})
	@ResponseBody
	public ResultEntity updateFeedbackReply (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				FeedbackReplyEntity feedbackReplyEntity = JsonUtils.fromJson(json, FeedbackReplyEntity.class);
				int ret = feedbackReplyService.updateFeedbackReply(feedbackReplyEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",feedbackReplyEntity);
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
	 * 删除APP意见反馈回复FeedbackReplyEntity
	 * 格式
	 * json = {
	 * 		"reply_idx":"",//APP意见反馈回复主键，自增
	 * 		"feedback_idx":"",
	 * 		"operator_idx":"",
	 * 		"reply_value":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteFeedbackReply"})
	@ResponseBody
	public ResultEntity deleteFeedbackReply (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				FeedbackReplyEntity feedbackReplyEntity = JsonUtils.fromJson(json, FeedbackReplyEntity.class);
				int ret = feedbackReplyService.deleteFeedbackReply(feedbackReplyEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",feedbackReplyEntity);
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
	 * 查询一条APP意见反馈回复记录FeedbackReplyEntity
	 * 格式
	 * json = {
	 * 		"reply_idx":"",//APP意见反馈回复主键，自增
	 * 		"feedback_idx":"",
	 * 		"operator_idx":"",
	 * 		"reply_value":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectFeedbackReply"})
	@ResponseBody
	public ResultEntity selectFeedbackReply (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				FeedbackReplyEntity feedbackReplyEntity = JsonUtils.fromJson(json, FeedbackReplyEntity.class);
				feedbackReplyEntity = feedbackReplyService.queryOneFeedbackReply(feedbackReplyEntity);
				resultEntity.setValue(true, "success","",feedbackReplyEntity);
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
	 * 查询多条APP意见反馈回复记录FeedbackReplyEntity
	 * 格式
	 * json = {
	 * 		"reply_idx":"",//APP意见反馈回复主键，自增
	 * 		"feedback_idx":"",
	 * 		"operator_idx":"",
	 * 		"reply_value":"",
	 * 		"createtime":""
	 * 		pageCurrent第几页，从1开始
	 * 		pageSize每页多少条
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectFeedbackReplys"})
	@ResponseBody
	public ResultEntity selectFeedbackReplys (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			FeedbackReplyPageEntity feedbackReplyEntity = JsonUtils.fromJson(json, FeedbackReplyPageEntity.class);
			List<FeedbackReplyEntity> list = feedbackReplyService.queryFeedbackReplys(feedbackReplyEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
