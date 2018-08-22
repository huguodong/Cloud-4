package com.ssitcloud.business.app.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.app.service.FeedbackReplyServiceI;
import com.ssitcloud.business.app.service.FeedbackServiceI;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.FeedbackEntity;
import com.ssitcloud.mobile.entity.FeedbackReplyEntity;
import com.ssitcloud.mobile.entity.FeedbackReplyPageEntity;

import net.sf.json.util.JSONUtils;

/**
 * FeedBack服务对外接口
 * @author LXP
 * @version 创建时间：2017年2月23日 上午10:51:57
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {
	@Autowired
	private FeedbackServiceI feedbackService;
	
	@Autowired
	private FeedbackReplyServiceI feedbackReplyService;
	
	/**
	 * 插入FeedBack
	 * @param request 应该包含json={FeedBackEntity实体}
	 * @return
	 */
	@RequestMapping("/insertFeedback")
	@ResponseBody
	public ResultEntity insertFeedback(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		
		String json = request.getParameter("json");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEntity.setValue(false, "没有json参数或者json格式不正确");
			return resultEntity;
		}
		//检查实体是否为合格的实体
		try{
			FeedbackEntity check = JsonUtils.fromJson(json, FeedbackEntity.class);
			if(check.getUser_type() != null && check.getUser_type().length()>2){
				resultEntity.setValue(false, "user_type is too long,max length is 2");
				return resultEntity;
			}
			if(check.getFeedback_type()!= null && check.getFeedback_type().length()>2){
				resultEntity.setValue(false, "feedback_type is too long,max length is 2");
				return resultEntity;
			}
			check.setCreatetime(new Timestamp(System.currentTimeMillis()));
			json = JsonUtils.toJson(check);
		}catch(Exception e){
			resultEntity.setValue(false, "解析json出错，请检查json格式,exception message==>"+e.getMessage());
			return resultEntity;
		}
		try{
			ResultEntity resultData = feedbackService.insertFeedback(json);
			resultEntity = resultData;
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		return resultEntity;
	}
	
	/**
	 * 通过主键查询FeedBack
	 * @param json ={"feedback_idx":主键id}
	 * @return
	 */
	@RequestMapping("/selectFeedbackByPk")
	@ResponseBody
	public ResultEntity selectFeedbackByPk(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try{
			String json = request.getParameter("json");
			Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
			Integer feedback_idx = (Integer) map.get("feedback_idx");
			if (feedback_idx == null) {
				resultEntity.setValue(false, "没有查询的主键参数");
				return resultEntity;
			}
		
			FeedbackEntity feedBackEntity = feedbackService.queryOneFeedback(feedback_idx);
	
			resultEntity.setState(true);
			resultEntity.setResult(feedBackEntity);
		}catch(Exception e){
			LogUtils.info(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		
		return resultEntity;
	}
	
	/**
	 * 模糊查询FeedBack
	 * @param 应该包含json={FeedBackQueryEntity实体}
	 * @return
	 */
	@RequestMapping("/selectFeedbacks")
	@ResponseBody
	public ResultEntity selectFeedbacks(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEntity.setValue(false, "请检查提交参数");
			return resultEntity;
		}
		
		try{
			List<FeedbackEntity> data = feedbackService.queryFeedbacks(json);
			resultEntity.setState(true);
			resultEntity.setResult(data);
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		
		return resultEntity;
	}
	

	/**
	 * 更新FeedBack
	 * @param request 应该包含json={FeedBackEntity实体，必须设置主键id}
	 * @return
	 */
	@RequestMapping("/updateFeedback")
	@ResponseBody
	public ResultEntity updateFeedback(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		
		String json = request.getParameter("json");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEntity.setValue(false, "没有json参数或者json格式不正确");
			return resultEntity;
		}
		//检查实体是否为合格的实体
		try{
			FeedbackEntity check = JsonUtils.fromJson(json, FeedbackEntity.class);
			if(check.getFeedback_idx() == null){
				resultEntity.setValue(false, "没有设置主键");
				return resultEntity;
			}
			if(check.getUser_type() != null && check.getUser_type().length()>2){
				resultEntity.setValue(false, "user_type is too long,max length is 2");
				return resultEntity;
			}
			if(check.getFeedback_type()!= null && check.getFeedback_type().length()>2){
				resultEntity.setValue(false, "feedback_type is too long,max length is 2");
				return resultEntity;
			}
			//强制设置更新时间
			check.setCreatetime(new Timestamp(System.currentTimeMillis()));
			json = JsonUtils.toJson(check);
		}catch(Exception e){
			resultEntity.setValue(false, "解析json出错，请检查json格式,exception message==>"+e.getMessage());
			return resultEntity;
		}
		
		try{
			ResultEntity resultData = feedbackService.updateFeedback(json);
			resultEntity = resultData;
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		
		return resultEntity;
	}
	
	/**
	 * 通过主键删除FeedBack
	 * @param feedback_idx 主键id
	 * @return
	 */
	@RequestMapping("/deleteFeedbackByPk")
	@ResponseBody
	public ResultEntity deleteFeedbackByPk(Integer feedback_idx){
		ResultEntity resultEntity = new ResultEntity();
		if(feedback_idx == null){
			resultEntity.setValue(false, "没有查询的主键参数");
			return resultEntity;
		}
		
		try{
			boolean result = feedbackService.deleteFeedback(feedback_idx);
			resultEntity.setState(result);
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		
		return resultEntity;
	}
	
	/**
	 * 查询FeedBack及其回复信息
	 * 
	 * @param feedback_idx
	 *            feedback主键
	 * @param pageCurrent 回复内容的第几页（不设置则不分页）
	 * @param pageSize 回复内容每页多少条（不设置则不分页）
	 * @return ResultEntity，state包含是否成功，result中是一个map《string,object》，
	 *         键feedback是反馈实体，键feedbackReply是回复内容<br>
	 * 
	 */
	@RequestMapping("/selectFeedbackAndReply")
	@ResponseBody
	public ResultEntity selectFeedbackAndReply(Integer feedback_idx,Integer pageCurrent,Integer pageSize){
		ResultEntity resultEntity = new ResultEntity();
		if(feedback_idx == null){
			resultEntity.setValue(false, "没有查询的主键参数");
			return resultEntity;
		}
		try{
			FeedbackEntity feedBackEntity = feedbackService.queryOneFeedback(feedback_idx);
			//查询回复内容
			FeedbackReplyPageEntity fre = new FeedbackReplyPageEntity();
			fre.setFeedback_idx(feedback_idx);
			fre.setPageCurrent(pageCurrent);
			fre.setPageSize(pageSize);
			String feedBackReplyQueryEntityJson = JsonUtils.toJson(fre);
			List<FeedbackReplyEntity> feedBackReplyList = feedbackReplyService
					.queryFeedbackReplyReplys(feedBackReplyQueryEntityJson);
			
			//合成返回实体
			resultEntity.setState(true);
			Map<String, Object> resultMap = new HashMap<>(2);
			resultMap.put("feedback", feedBackEntity);
			resultMap.put("feedbackReply", feedBackReplyList);
			resultEntity.setResult(resultMap);
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		
		
		return resultEntity;
	}
}
