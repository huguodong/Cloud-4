package com.ssitcloud.business.mobile.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.StringUtils;
import com.ssitcloud.business.mobile.operationEntity.FeedbackOperationLog;
import com.ssitcloud.business.mobile.service.FeedbackReplyServiceI;
import com.ssitcloud.business.mobile.service.FeedbackServiceI;
import com.ssitcloud.business.mobile.service.OperationLogServiceI;
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
	@Autowired
	private OperationLogServiceI operationLogService;
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
		//构造日志对象
		FeedbackOperationLog fol = new FeedbackOperationLog();
		fol.setClient_ip(StringUtils.getIpAddr(request));
		fol.setClient_port(String.valueOf(request.getRemotePort()));
		
		//检查实体是否为合格的实体
		try{
			FeedbackEntity check = JsonUtils.fromJson(json, FeedbackEntity.class);
			if(check.getUser_type() == null || check.getUser_type().length()>2){
				resultEntity.setValue(false, "user_type is too long,max length is 2");
				return resultEntity;
			}
			if(check.getFeedback_type() == null || check.getFeedback_type().length()>2){
				resultEntity.setValue(false, "feedback_type is too long,max length is 2");
				return resultEntity;
			}
			if(check.getReader_idx() == null){
				resultEntity.setValue(false, "reader_idx is null");
				return resultEntity;
			}
			check.setCreatetime(new Timestamp(System.currentTimeMillis()));
			json = JsonUtils.toJson(check);
			fol.setReader_idx(String.valueOf(check.getReader_idx()));
		}catch(Exception e){
			resultEntity.setValue(false, "解析json出错，请检查json格式,exception message==>"+e.getMessage());
			return resultEntity;
		}
		try{
			ResultEntity resultData = feedbackService.insertFeedback(json);
			resultEntity = resultData;
			//写入日志
			fol.setOperation_result(resultEntity.getState());
			try{
				fol.setFeedback_idx(String.valueOf(((Map)resultEntity.getResult()).get("feedback_idx")));			
				}catch(Exception e){
				LogUtils.info("转换feedback对象出错");
			}
			operationLogService.addOperationLog(fol);
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
	 * 查询FeedBack及其回复信息
	 * 
	 * @param json={feedback_idx:feedback主键
	 * pageCurrent:回复内容的第几页（不设置则不分页）
	 * pageSize:回复内容每页多少条（不设置则不分页）
	 * }
	 * @return ResultEntity，state包含是否成功，result中是一个map《string,object》，
	 *         键feedback是反馈实体，键feedbackReply是回复内容<br>
	 * 
	 */
	@RequestMapping("/selectFeedbackAndReply")
	@ResponseBody
	public ResultEntity selectFeedbackAndReply(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try{
			String json = request.getParameter("json");
			Map<String , Object> map = JsonUtils.fromJson(json, Map.class);
			Integer feedback_idx=(Integer) map.get("feedback_idx");
			Integer pageCurrent=(Integer) map.get("pageCurrent");
			Integer pageSize=(Integer) map.get("pageSize");
			if(feedback_idx == null){
				resultEntity.setValue(false, "没有查询的主键参数");
				return resultEntity;
			}
		
			
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
			LogUtils.info(e);
			resultEntity.setState(false);
			resultEntity.setMessage("请检查提交参数"+e.getMessage());
		}
		
		
		return resultEntity;
	}
}
