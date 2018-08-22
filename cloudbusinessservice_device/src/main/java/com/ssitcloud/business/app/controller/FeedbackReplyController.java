package com.ssitcloud.business.app.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.app.service.FeedbackReplyServiceI;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.FeedbackReplyEntity;

import net.sf.json.util.JSONUtils;

/**
 * FeedbackReply的对外查询接口<br/>
 * 注意！如果需要同时获取feedback和响应的回复信息，请参见FeedbackController中的selectFeedbackAndReply接口
 * @author LXP
 * @version 创建时间：2017年2月23日 下午3:08:04
 */

@Controller
@RequestMapping("/feedbackReply")
public class FeedbackReplyController {
	
	@Autowired
	private FeedbackReplyServiceI feedbackReplyService;
	
	/**
	 * 插入FeedbackReply
	 * @param request 应该包含json={FeedbackReplyEntity实体}
	 * @return
	 */
	@RequestMapping("/insertFeedbackReply")
	@ResponseBody
	public ResultEntity insertFeedbackReply(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		
		String json = request.getParameter("json");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEntity.setValue(false, "没有json参数或者json格式不正确");
			return resultEntity;
		}
		//检查实体是否为合格的实体
		try{
			FeedbackReplyEntity check = JsonUtils.fromJson(json, FeedbackReplyEntity.class);
			
			if(check.getReply_value() == null || check.getReply_value().length()>500){
				resultEntity.setValue(false, "reply value is (too long) or (is null),max length is 500");
				return resultEntity;
			}
			if(check.getOperator_idx() == null){
				resultEntity.setValue(false, "operator is null");
				return resultEntity;
			}
			check.setCreatetime(new Timestamp(System.currentTimeMillis()));
			json = JsonUtils.toJson(check);
		}catch(Exception e){
			resultEntity.setValue(false, "解析json出错，请检查json格式,exception message==>"+e.getMessage());
			return resultEntity;
		}
		try{
			ResultEntity resultData = feedbackReplyService.insertFeedbackReply(json);
			resultEntity = resultData;
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		return resultEntity;
	}
	
	/**
	 * 通过主键查询FeedBackReply
	 * @param reply_idx 主键id
	 * @return
	 */
	@RequestMapping("/selectFeedbackReplyByPk")
	@ResponseBody
	public ResultEntity selectFeedbackReplyByPk(Integer reply_idx){
		ResultEntity resultEntity = new ResultEntity();
		if(reply_idx == null){
			resultEntity.setValue(false, "没有查询的主键参数");
			return resultEntity;
		}
		try{
			FeedbackReplyEntity feedbackReplyEntity = feedbackReplyService.queryOneFeedbackReply(reply_idx);
	
			resultEntity.setState(true);
			resultEntity.setResult(feedbackReplyEntity);
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		
		return resultEntity;
	}
	
	/**
	 * 模糊查询FeedBackReply
	 * @param 应该包含json={FeedBackReplyPageEntity实体}
	 * @return
	 */
	@RequestMapping("/selectFeedbackReplys")
	@ResponseBody
	public ResultEntity selectFeedbackReplys(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEntity.setValue(false, "请检查提交参数");
			return resultEntity;
		}
		
		try{
			List<FeedbackReplyEntity> data = feedbackReplyService.queryFeedbackReplyReplys(json);
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
	 * 更新FeedBackReply
	 * @param request 应该包含json={FeedBackReplyEntity实体，必须设置主键id}
	 * @return
	 */
	@RequestMapping("/updateFeedbackReply")
	@ResponseBody
	public ResultEntity updateFeedbackReply(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		
		String json = request.getParameter("json");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEntity.setValue(false, "没有json参数或者json格式不正确");
			return resultEntity;
		}
		//检查实体是否为合格的实体
		try{
			FeedbackReplyEntity check = JsonUtils.fromJson(json, FeedbackReplyEntity.class);
			
			if(check.getReply_value() == null && check.getReply_value().length()>2){
				resultEntity.setValue(false, "reply value is (too long) or (is null),max length is 500");
				return resultEntity;
			}
			if(check.getReply_idx() == null){
				resultEntity.setValue(false, "primary key is not set");
				return resultEntity;
			}
			check.setCreatetime(new Timestamp(System.currentTimeMillis()));
			json = JsonUtils.toJson(check);
		}catch(Exception e){
			resultEntity.setValue(false, "解析json出错，请检查json格式,exception message==>"+e.getMessage());
			return resultEntity;
		}
		
		try{
			ResultEntity resultData = feedbackReplyService.updateFeedbackReply(json);
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
	@RequestMapping("/deleteFeedbackReplyByPk")
	@ResponseBody
	public ResultEntity deleteFeedbackReplyByPk(Integer reply_idx){
		ResultEntity resultEntity = new ResultEntity();
		if(reply_idx == null){
			resultEntity.setValue(false, "没有删除的主键参数");
			return resultEntity;
		}
		
		try{
			boolean result = feedbackReplyService.deleteFeedbackReply(reply_idx);
			resultEntity.setState(result);
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		
		return resultEntity;
	}
}
