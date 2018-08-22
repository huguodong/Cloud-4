package com.ssitcloud.business.mobile.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.PropertiesUtil;
import com.ssitcloud.business.mobile.service.EmailServiceI;
import com.ssitcloud.business.mobile.service.NodeServiceI;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.EmailEntity;

@Service
public class EmailServiceImpl implements EmailServiceI {
	private final String charset = "utf-8";

	@Autowired
	private NodeServiceI nodeService;
	
	private final long TIME_INTERVAL;//12H
	private final int MAX_SEND_TIMES;//同一时间间隔内最大发送次数
	private Map<String, SendMail> sendRecord = Collections.synchronizedMap(new WeakHashMap<String, SendMail>()); 
	
	public EmailServiceImpl(){
		String timeStr = PropertiesUtil.getConfigPropValueAsText("max_send_times");
		String intervalStr = PropertiesUtil.getConfigPropValueAsText("send_interval");
		if(timeStr == null || intervalStr == null){
			throw new IllegalArgumentException("must setting max_send_times and send_interval");
		}
		MAX_SEND_TIMES = Integer.valueOf(timeStr);
		TIME_INTERVAL = Integer.valueOf(intervalStr);
	}
	
	@Override
	public boolean sendEnail(EmailEntity email) {
		//检查最后发送事件和次数
		SendMail sendMail = sendRecord.get(email.getReceEmailAddr());
		if(sendMail == null){
			sendMail = new SendMail();
			sendRecord.put(email.getReceEmailAddr(), sendMail);
		}else{
			if(sendMail.sendTimes.get()>MAX_SEND_TIMES){//检查发信次数是否达到限制
				if(System.currentTimeMillis() < sendMail.time.get()+TIME_INTERVAL){//检查发信时间间隔
					LogUtils.debug("用户超过发信次数，times==>"+sendMail.sendTimes.get()+" max==>"+MAX_SEND_TIMES+" "+sendMail.time.get()+"  cycle==>"+TIME_INTERVAL);
					return false;
				}else{
					sendMail.sendTimes.set(0);
				}
			}
		}
		String url = nodeService.getEmailUrl(email.getLib_idx());
		if(url != null){
			Map<String, String> map = new HashMap<>(1,1.0f);
			map.put("req", JsonUtils.toJson(email));
			String doPost = HttpClientUtil.doPost(url, map, charset);
			try{
				ResultEntity resultEntity = JsonUtils.fromJson(doPost, ResultEntity.class);
				boolean state = resultEntity.getState();
				if(state){
					sendMail.time.set(System.currentTimeMillis());//设置最后发信时间
					sendMail.sendTimes.incrementAndGet();//发信次数增加
				}
				return state;
			}catch (Exception e) {
				LogUtils.info(getClass()+" 发送email失败,发信服务器返回数据==>"+doPost,e);
			}
		}
		return false;
	}

	@Override
	public boolean sendRegisterEmail(String email, String content) {
		EmailEntity emailEntity = new EmailEntity();
		emailEntity.setLib_idx(0);
		emailEntity.setReceEmailAddr(email);
		emailEntity.setSubject("邮箱验证码");
		emailEntity.setContent(content);
		
		return sendEnail(emailEntity);
	}

	@Override
	public boolean sendPasswordEmail(String email, String content) {
		EmailEntity emailEntity = new EmailEntity();
		emailEntity.setLib_idx(0);
		emailEntity.setReceEmailAddr(email);
		emailEntity.setSubject("找回密码验证码");
		emailEntity.setContent(content);
		
		return sendEnail(emailEntity);
	}

	private class SendMail{
		AtomicInteger sendTimes = new AtomicInteger(0);
		AtomicLong time = new AtomicLong(0);
	}
}
