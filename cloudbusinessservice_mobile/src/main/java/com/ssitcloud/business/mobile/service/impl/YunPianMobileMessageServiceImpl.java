package com.ssitcloud.business.mobile.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.PropertiesUtil;
import com.ssitcloud.business.mobile.service.MobileMessageServiceI;

/**
 * 云片短信服务
 * 
 * @author xunpengliu
 * @version 创建时间：2017年5月23日 上午11:12:58
 */
@Service
public class YunPianMobileMessageServiceImpl implements MobileMessageServiceI {

	private final String apiKey;
	private final String library_apiKey;
	private final String url;
	private final String yunpian_tpl_url;
	private final String SMS_template_id;
	private final String registerContent;
	private final String passwordContent;
	private final String charset;

	// private final int maxTimes;
	// private final long interval;

	// private Map<String, SoftReference<SendMessage>> mobileMap = new HashMap<>(512);

	public YunPianMobileMessageServiceImpl() {
		apiKey = PropertiesUtil.getValue("yunpian_api");
		library_apiKey = PropertiesUtil.getValue("library_yunpian_apikey");
		url = PropertiesUtil.getValue("yunpian_url");
		yunpian_tpl_url = PropertiesUtil.getValue("yunpian_tpl_url");
		SMS_template_id = PropertiesUtil.getValue("SMS_template_id");
		charset = PropertiesUtil.getValue("yunpian_charset");
		registerContent = PropertiesUtil.getValue("register_message");
		passwordContent = PropertiesUtil.getValue("password_message");
		// maxTimes = Integer.valueOf(PropertiesUtil.getValue("message_max_send_times"));
		// interval = Long.valueOf(PropertiesUtil.getValue("message_interval"));
	}

	@Override
	public boolean sendRegisterMessage(String mobile, String vcode) {
		return sendMessage(apiKey, mobile, vcode, registerContent);
	}

	@Override
	public boolean sendPasswordMessage(String mobile, String vcode) {
		return sendMessage(apiKey, mobile, vcode, passwordContent);
	}

	private boolean sendMessage(String apiKey, String mobile, String vcode, String content) {
		// SendMessage sendMessage;
		// synchronized (this) {
		// SoftReference<SendMessage> softReference = mobileMap.get(mobile);
		// if (softReference != null) {
		// sendMessage = softReference.get();
		// if (sendMessage == null) {
		// sendMessage = new SendMessage();
		// mobileMap.put(mobile, new SoftReference<>(sendMessage));
		// } else {
		// if (sendMessage.sendTimes.get() > maxTimes) {// 检查发信次数是否达到限制
		// if (System.currentTimeMillis() < sendMessage.time.get() + interval) {// 检查发信时间间隔
		// LogUtils.info("用户超过发信次数，times==>" + sendMessage.sendTimes.get() + " max==>" + maxTimes + " "
		// + sendMessage.time.get() + "  cycle==>" + interval);
		// return false;
		// } else {
		// sendMessage.sendTimes.set(0);
		// }
		// }
		// }
		// } else {
		// sendMessage = new SendMessage();
		// mobileMap.put(mobile, new SoftReference<>(sendMessage));
		// }
		// }

		Map<String, String> map = new HashMap<>();
		map.put("apikey", apiKey);
		map.put("mobile", mobile);
		if (StringUtils.hasText(vcode)) {// 短信验证码
			map.put("text", content.replace("[vcode]", vcode));
		} else {
			map.put("text", content);
		}
		String returnJson = HttpClientUtil.doPost(url, map, charset);
		if (returnJson != null) {
			try {
				Map<String, Object> m = JsonUtils.fromJson(returnJson, Map.class);
				if (String.valueOf(m.get("code")).equals("0")) {
					// sendMessage.sendTimes.incrementAndGet();
					// sendMessage.time.set(System.currentTimeMillis());
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				LogUtils.info("识别云片返回信息出错,返回信息==>" + returnJson);
			}
		}

		LogUtils.info("发送云片短信失败,发送内容==>" + JsonUtils.toJson(map) + "  返回信息==>" + returnJson);

		return false;
	}

	@Override
	public boolean sendSMS(String mobile, String content) {
		return sendMessage(apiKey, mobile, null, content);
	}

	@Override
	public boolean sendSMSByLibrary(String mobile, String content) {
		return sendMessage(library_apiKey, mobile, null, content);
	}

	/**
	 * 使用指定模板接口发短信
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 */
	@Override
	public boolean sendSMSOnTemplate(String mobile, String content) {
		try {
			Map<String, String> map = new HashMap<>();
			map.put("apikey", apiKey);
			map.put("mobile", mobile);
			map.put("tpl_id", SMS_template_id);
			String tpl_value = URLEncoder.encode("#booklist#", charset) + "=" + URLEncoder.encode(content, charset);
			map.put("tpl_value", tpl_value);
			String returnJson = HttpClientUtil.doPost(yunpian_tpl_url, map, Consts.UTF_8.toString());
			if (JSONUtils.mayBeJSON(returnJson)) {
				JSONObject json = JSONObject.fromObject(returnJson);
				String code = json.optString("code");
				if ("0".equals(code)) {
					LogUtils.info("发送云片短信成功,返回信息==>" + returnJson);
					return true;
				}else{
					LogUtils.info("发送云片短信失败,发送内容==>" + JsonUtils.toJson(map) + "  返回信息==>" + returnJson);
				}
			}
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	// private class SendMessage {
	// AtomicInteger sendTimes = new AtomicInteger(0);
	// AtomicLong time = new AtomicLong(0);
	// }

	public static void main(String[] args) throws UnsupportedEncodingException {
		String url = PropertiesUtil.getValue("yunpian_tpl_url");
		String tpl_id = PropertiesUtil.getValue("SMS_template_id");

		String tpl_value = URLEncoder.encode("#booklist#", "utf-8") + "=" + URLEncoder.encode("《时间之书》《不畏将来》《丑陋的中国人》《人间草木》《默读》", "utf-8");

		Map<String, String> map = new HashMap<>();
		map.put("apikey", "223d58ef58048d09292f3e802543c19a");// "db2918a2afc6d9fcd10c99104581e5f3");
		map.put("mobile", "xxx");
		map.put("tpl_id", tpl_id);
		map.put("tpl_value", tpl_value);
		String returnJson = HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		System.out.println(returnJson);
	}
}
