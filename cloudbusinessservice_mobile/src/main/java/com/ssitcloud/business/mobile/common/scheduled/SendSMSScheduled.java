package com.ssitcloud.business.mobile.common.scheduled;

import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ssitcloud.business.mobile.service.MobileMessageServiceI;
import com.ssitcloud.redisutils.JedisUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

@JobHandler(value="sendSMS")
@Component
public class SendSMSScheduled extends IJobHandler{
	@Autowired
	private MobileMessageServiceI mobileMessageServiceI;
	private JedisUtils redis = JedisUtils.getInstance();

	@Override
	public ReturnT<String> execute(String arg0) throws Exception {
		sendSMS();
		return SUCCESS;
	}
	
	public boolean sendSMS(){
		String key = "SMS";

		List<String> smsList = redis.popAll(key);
		if(CollectionUtils.isEmpty(smsList)){
			return true;
		}
		for (String sms : smsList) {
			if (JSONUtils.mayBeJSON(sms)) {
				JSONObject obj = JSONObject.fromObject(sms);
				if (obj.containsKey("phone") && obj.containsKey("content")) {
					boolean isSend = mobileMessageServiceI.sendSMS(obj.optString("phone"), obj.optString("content"));
					if (isSend) {
					}else{
					}
				}
			}

		}
		return true;
	}

}
