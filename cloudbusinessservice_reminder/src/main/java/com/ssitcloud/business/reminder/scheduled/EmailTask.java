package com.ssitcloud.business.reminder.scheduled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.ssitcloud.business.reminder.common.util.LogUtils;
import com.ssitcloud.business.reminder.common.util.SimpleMailSender;
import com.ssitcloud.business.reminder.service.EmailService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * author huanghuang 2017年2月27日 下午5:42:37
 */
@Component("emailTask")
public class EmailTask {
	@Resource
	private EmailService emailService;

	/**
	 * 每天零点查询一次要发送的邮件
	 * author huanghuang
	 * 2017年3月14日 上午10:48:10
	 */
	public void sendEmail() {
		ResultEntity sendReconds = emailService.selectDeviceTroubles("{}");
		JSONArray sendArray = null;// 得到当前要发送的邮件
		if (sendReconds.getState()) {
			sendArray = JSONArray.fromObject(sendReconds.getResult());
		}
		JSONObject library = null;// 得到图书馆
		JSONObject device = null;// 得到设备
		JSONArray send = null;// 邮件发送方
		if (sendArray!=null&&!sendArray.isEmpty()) {
			JSONObject deviceTrouble = null;
			for (int d = 0; d < sendArray.size(); d++) {
				deviceTrouble = sendArray.getJSONObject(d);
				Integer libidx = deviceTrouble.getInt("lib_idx");
				Integer deviceidx = deviceTrouble.getInt("device_idx");
				library = takeLib(libidx);
				device = takeDev(deviceidx);
				send = takeEmailParam(libidx);
				if (!send.isEmpty()) {
					for (int i = 0; i < send.size(); i++) {
						JSONObject jObject = send.getJSONObject(i);
						// 发送邮件的账户
						String EmailNo = jObject.getString("email_account");
						// 发送邮件的授权码
						String EmailPwd = jObject.getString("email_password");
						//SMTP邮件服务器地址
						String emailSMTP = jObject.getString("email_smtp");
						//邮件服务器端口号
						String emailPort = jObject.getString("email_port");
						SimpleMailSender Sms = new SimpleMailSender(emailSMTP,EmailNo,
								EmailPwd,emailPort);// 得到邮件发送实体SimpleMailSender
						JSONArray receArray = takeReceiver(libidx,8);
						List<String> receList = new ArrayList<String>();// 邮件接收方
						if (!receArray.isEmpty()) {
							for (int r = 0; r < receArray.size(); r++) {
								receList.add(receArray.getJSONObject(r)
										.getString("info_value"));
							}
							try {
								Sms.send(
										receList,
										library.getString("lib_name")
												+ "的"
												+ device.getString("device_name")
												+ "发生故障", deviceTrouble
												.getString("trouble_info"));// 发送邮件
							} catch (AddressException e) {
								System.err.println(receList.toString()+"=======>用户邮箱地址有误");
								continue;//当前邮件发送失败，则跳到下一个邮件发送
							} catch (MessagingException e) {
								System.err.println(jObject.toString()+"=======>邮件mime类型有误，可能是邮件服务的session初始化错误，请检查发送邮件的账户、授权码和SMTP邮件服务器地址及端口号是否正确");
								continue;//当前邮件发送失败，则跳到下一个邮件发送
							}
						}
						emailService.updateDeviceTrouble("{\"trouble_idx\":\""
								+ deviceTrouble.getInt("trouble_idx") + "\"}");// 更新设备故障信息
					}
				}
			}
		}

	}

	private Map<Integer, JSONObject> libMap = new HashMap<Integer, JSONObject>();// 当map里存在图书馆时，则不用请求数据库

	// 图书馆的缓存
	public JSONObject takeLib(Integer key) {
		JSONObject value = null;
		value = libMap.get(key);
		if (value == null) {
			// 通过libidx得到图书馆
			String libidx = "{\"library_idx\":\"" + key + "\"}";
			Map<String, String> param = new HashMap<>();
			param.put("json", libidx);
			ResultEntity libResult = emailService.selLibraryByIdxOrId(param);
			if (libResult.getState()) {
				JSONArray libArray = JSONArray
						.fromObject(libResult.getResult());
				if(libArray.size()>0){
					value = libArray.getJSONObject(0);
				}else{
					LogUtils.debug("library_idx为"+key+"的idx找不到对应的图书馆");
				}
			}
			if (value == null) {
				throw new RuntimeException("=====>通过任务的lib_idx查找不到对应的图书馆");
			}
			libMap.put(key, value);
		}

		return value;
	}

	private Map<Integer, JSONObject> devMap = new HashMap<Integer, JSONObject>();// 当map里存在图书馆设备时，则不用请求数据库

	// 设备的缓存
	public JSONObject takeDev(Integer key) {
		JSONObject value = null;
		value = devMap.get(key);
		if (value == null) {
			// 通过device_idx得到所有设备
			String json = "{\"device_idx\":\"" + key + "\"}";
			Map<String, String> map = new HashMap<>();
			map.put("json", json);
			ResultEntity devResult = emailService.selectDevice(map);
			if (devResult!=null&&devResult.getState()) {
				JSONArray v = JSONArray.fromObject(devResult.getResult());
				if(v.size()>0){
					value = v.getJSONObject(0);
				}else{
					LogUtils.debug("device_idx为"+key+"的设备idx找不到对应的设备");
				}
			}
			if (value == null) {
				throw new RuntimeException("=====>通过任务的device_idx查找不到对应的设备");
			}
			devMap.put(key, value);

		}

		return value;
	}

	private Map<Integer, JSONArray> emailParamMap = new HashMap<Integer, JSONArray>();// 当map里存在邮件发送方时，则不用请求数据库

	// 邮件发送方的缓存
	public JSONArray takeEmailParam(Integer key) {
		JSONArray value = null;
		value = emailParamMap.get(key);
		if (value == null) {
			// 通过lib_idx得到所有邮件发送方
			String req = "{\"libIdxs\":\"" + key + "\"}";
			ResultEntity devResult = emailService.selectEmailParams(req);
			if (devResult.getState()) {
				value = JSONArray.fromObject(devResult.getResult());
			}
			if (value == null) {
				throw new RuntimeException("=====>通过任务的lib_idx查找不到对应的设备");
			}
			emailParamMap.put(key, value);

		}

		return value;
	}

	private Map<Integer, JSONArray> receiverMap = new HashMap<Integer, JSONArray>();// 当map里存在邮件接收方时，则不用请求数据库

	// 邮件接收方的缓存
	public JSONArray takeReceiver(Integer key,Integer code) {
		JSONArray value = null;
		value = receiverMap.get(key);
		if (value == null) {
			// 通过lib_idx得到所有邮件接收方
			String req = "{\"library_idx\":\"" + key
					+ "\",\"infotype_idx\":\""+code+"\"}";
			ResultEntity receResult = emailService.selectOperatorInfos(req);
			if (receResult.getState()) {
				value = JSONArray.fromObject(receResult.getResult());
			}
			if (value == null) {
				throw new RuntimeException("=====>通过任务的lib_idx查找不到对应的邮件接收方");
			}
			receiverMap.put(key, value);

		}

		return value;
	}

}
