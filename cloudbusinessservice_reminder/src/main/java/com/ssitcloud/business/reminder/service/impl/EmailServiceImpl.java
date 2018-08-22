package com.ssitcloud.business.reminder.service.impl;


import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.reminder.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.reminder.common.util.HttpClientUtil;
import com.ssitcloud.business.reminder.common.util.JsonUtils;
import com.ssitcloud.business.reminder.common.util.ResourcesUtil;
import com.ssitcloud.business.reminder.common.util.SimpleMailSender;
import com.ssitcloud.business.reminder.common.util.XMLUtils;
import com.ssitcloud.business.reminder.service.EmailService;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;


@Service
public class EmailServiceImpl extends BasicServiceImpl implements EmailService {
	//取得發送方的郵箱地址和密碼
	private static final String URL_QUERYDEVICETROUBLE = "selectDeviceTroubles";
	//取得發送方的郵箱地址和密碼
	private static final String URL_QUERYEMAILPARAMS = "selectEmailParams";
	//通过lib_idx查找图书馆
	private static final String URL_QUERYLIBRARY = "selLibraryByIdxOrId";
	//通过device_idx查找设备
	private static final String URL_QUERYDEVICE = "selectDevice";
	//通过lib_idx查找接收邮件的人
	private static final String URL_QUERYOPINFO = "selectOperatorInfos";
	//通过trouble_idx更新故障表
	private static final String URL_UPDATEDEVTROUBLE = "updateDeviceTrouble";

	@Override
	public ResultEntity selectDeviceTroubles(String req) {
		return postURL(URL_QUERYDEVICETROUBLE, req);
	}

	@Override
	public ResultEntity selectEmailParams(String req) {
		return postURL(URL_QUERYEMAILPARAMS, req);
	}

	@Override
	public ResultEntity selLibraryByIdxOrId(Map<String, String> param) {
		ResultEntity result = new ResultEntity();
		if(requestURL==null)
			requestURL = new RequestURLListEntity(XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml")));
		String reqURL=requestURL.getRequestURL(URL_QUERYLIBRARY);
		String res=HttpClientUtil.doPost(reqURL, param, Consts.UTF_8.toString());
		result = JsonUtils.fromJson(res, ResultEntity.class);	
		return result ;
	}

	@Override
	public ResultEntity selectDevice(Map<String, String> param) {
		ResultEntity result = new ResultEntity();
		if(requestURL==null)
			requestURL = new RequestURLListEntity(XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml")));
		String reqURL=requestURL.getRequestURL(URL_QUERYDEVICE);
		String res=HttpClientUtil.doPost(reqURL, param, Consts.UTF_8.toString());
		result = JsonUtils.fromJson(res, ResultEntity.class);	
		return result ;
	}

	@Override
	public ResultEntity selectOperatorInfos(String req) {
		return postURL(URL_QUERYOPINFO, req);
	}

	@Override
	public ResultEntity updateDeviceTrouble(String req) {
		return postURL(URL_UPDATEDEVTROUBLE, req);
	}

	@Override
	public ResultEntity sendEmail(JSONObject json) {
		ResultEntity resultEntity = new ResultEntity();
		// 发送邮件的账户
		String EmailNo = json.getString("email_account");
		// 发送邮件的授权码
		String EmailPwd = json.getString("email_password");
		//SMTP邮件服务器地址
		String emailSMTP = json.getString("email_smtp");
		//邮件服务器端口号
		String emailPort = json.getString("email_port");
		SimpleMailSender Sms = new SimpleMailSender(emailSMTP,EmailNo,
				EmailPwd,emailPort);// 得到邮件发送实体SimpleMailSender
		String receEmailAddr = json.getString("receEmailAddr");
		String subject = json.getString("subject");//邮件主题
		String content = json.getString("content");//邮件内容
		try {
			Sms.send(receEmailAddr,subject,content);// 发送邮件
		} catch (AddressException e) {
			System.err.println(receEmailAddr+"=======>用户邮箱地址有误");
			resultEntity.setValue(false, "failed","","用户邮箱地址有误");
			return resultEntity;
		} catch (MessagingException e) {
			System.err.println(json.toString()+"=======>邮件mime类型有误，可能是邮件服务的session初始化错误，请检查发送邮件的账户、授权码和SMTP邮件服务器地址及端口号是否正确");
			resultEntity.setValue(false, "failed","","邮件mime类型有误，可能是邮件服务的session初始化错误，请检查发送邮件的账户、授权码和SMTP邮件服务器地址及端口号是否正确");
			return resultEntity;
		}
		resultEntity.setValue(true, "success", "", "您发送的邮件已成功");
		return resultEntity;
	}


}
