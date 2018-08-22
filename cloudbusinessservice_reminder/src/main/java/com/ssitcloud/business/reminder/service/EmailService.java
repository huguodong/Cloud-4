package com.ssitcloud.business.reminder.service;

import java.util.Map;

import net.sf.json.JSONObject;

import com.ssitcloud.common.entity.ResultEntity;


public interface EmailService {
	//取得要发送的邮件记录
	ResultEntity selectDeviceTroubles(String req);
	//取得發送方的郵箱地址和密碼
	ResultEntity selectEmailParams(String req);
	//通过lib_idx查找图书馆
	ResultEntity selLibraryByIdxOrId(Map<String, String> param);
	//通过device_idx查找设备
	ResultEntity selectDevice(Map<String, String> param);
	//通过lib_idx查找接收邮件的人
	ResultEntity selectOperatorInfos(String req);
	//通过trouble_idx更新故障表
	ResultEntity updateDeviceTrouble(String req);
	//发送邮件
	ResultEntity sendEmail(JSONObject json);
}
