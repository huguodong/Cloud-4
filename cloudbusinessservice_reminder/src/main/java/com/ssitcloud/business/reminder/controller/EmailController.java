package com.ssitcloud.business.reminder.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.reminder.common.util.ExceptionHelper;
import com.ssitcloud.business.reminder.service.EmailService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "email" })
public class EmailController {
	@Resource
	private EmailService emailService;

	/**
	 * 
	 * 格式 req = { 
	 * "lib_idx":"",//图书馆idx
	 * "receEmailAddr":"",//收件人邮箱
	 * "subject":"",//主题
	 * "content":""//邮件内容
	 * } 
	 * author huanghuang 2017年2月9日 下午1:42:14
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "sendEmail" })
	@ResponseBody
	public ResultEntity sendEmail(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if(StringUtils.isBlank(json)){
				resultEntity.setValue(false, "参数不能为空");
			}else{
				JSONObject jObject = JSONObject.fromObject(json);
				if(jObject.containsKey("lib_idx")){
					Integer libidx = jObject.getInt("lib_idx");
					String req = "{\"libIdxs\":\"" + libidx + "\"}";
					ResultEntity emailResult = emailService.selectEmailParams(req);
					JSONArray value = null;
					if (emailResult!=null&&emailResult.getState()) {
						value = JSONArray.fromObject(emailResult.getResult());
						if(value.size()>0){
							JSONObject obj = value.getJSONObject(0);//得到的邮件对象
							if(jObject.containsKey("receEmailAddr")){
								obj.put("receEmailAddr", jObject.getString("receEmailAddr"));
							}else{
								resultEntity.setValue(false, "receEmailAddr参数不能为空");
								return resultEntity;
							}
							if(jObject.containsKey("subject")){
								obj.put("subject", jObject.getString("subject"));
							}else{
								resultEntity.setValue(false, "subject参数不能为空");
								return resultEntity;
							}
							if(jObject.containsKey("content")){
								obj.put("content", jObject.getString("content"));
							}else{
								resultEntity.setValue(false, "content参数不能为空");
								return resultEntity;
							}
							resultEntity = emailService.sendEmail(obj);
						}else{
							resultEntity.setValue(false, "找不到对应的邮件发送方");
						}
					}
				}else{
					resultEntity.setValue(false, "lib_idx参数不能为空");
				}
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}


}
