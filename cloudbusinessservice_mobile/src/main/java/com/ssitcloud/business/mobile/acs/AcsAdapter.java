package com.ssitcloud.business.mobile.acs;

import java.util.HashMap;
import java.util.Map;

import com.ssitcloud.authentication.entity.LibraryAcsLogininfoEntity;
import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.mobile.entity.AcsBookInfoByReconEntity;
import com.ssitcloud.mobile.entity.AcsBookInfoEntity;
import com.ssitcloud.mobile.entity.AcsInReservationBookEntity;
import com.ssitcloud.mobile.entity.AcsLoginEntiy;
import com.ssitcloud.mobile.entity.AcsQueryReservationEntity;
import com.ssitcloud.mobile.entity.AcsReaderCardEntity;
import com.ssitcloud.mobile.entity.AcsRenewEntity;
import com.ssitcloud.mobile.entity.AcsReservationBookEntity;
import com.ssitcloud.mobile.entity.AcsReservationBookEntity.NOTIFY_MODE;

/**
 * 转换实体为acs服务需要的json
 * @author LXP
 * @version 创建时间：2017年3月3日 上午8:46:08
 */
public class AcsAdapter {
	
	private final String target;
	private final String servicetype;
	private final String lib_id;
	private String username;
	private String pwd;
	
	public AcsAdapter(LibraryEntity libraryEntity,LibraryAcsLogininfoEntity libraryConfig) {
		this.target = libraryConfig.getAcs_target();
		this.lib_id = libraryEntity.getLib_id();
		this.servicetype = "lib";
		this.username = libraryConfig.getAcs_loginname();
		this.pwd = libraryConfig.getAcs_loginpwd().replace("\n", "");
	}
	
	public String getLogoutJson(){
		Map<String, Object> paraMap = new HashMap<>(6);
		paraMap.put("operation", "lib_sc_logout");
		paraMap.put("servicetype", servicetype);
		paraMap.put("target", target);
		Map<String, Object> data = new HashMap<>(5);
		data.put("LIBCODE", lib_id);//图书馆id
		paraMap.put("data", data);
		return JsonUtils.toJson(paraMap).replace("\n", "");
	}
	
	public String getLoginJson(){
		AcsLoginEntiy loginEntity = new AcsLoginEntiy();
		loginEntity.setUsername(username);
		loginEntity.setPwd(pwd);
		return getLoginJson(loginEntity ).replace("\n", "");
	}
	
	/**
	 * 获取登陆json
	 * @param username
	 * @param pwd
	 * @param lib_idx
	 * @return
	 */
	public String getLoginJson(AcsLoginEntiy loginEntity){
		Map<String, Object> paramMap = getParamMap(loginEntity.getOperation());
		
		Map<String, Object> data = new HashMap<>(8);
		data.put("ACSNAME", loginEntity.getUsername());
		data.put("ACSPWD", loginEntity.getPwd());
		data.put("LIBCODE", lib_id);//图书馆id
		paramMap.put("data", data);
		return JsonUtils.toJson(paramMap).replace("\n", "");
	}
	/**
	 * 获取读者卡json
	 * @param readerCardEntity
	 * @return
	 */
	public String getReaderInfoJson(AcsReaderCardEntity readerCardEntity){
		Map<String, Object> paramMap = getParamMap(readerCardEntity.getOperation());
		
		Map<String, Object> data = new HashMap<>(10);
		data.put("PATRONCARDSN", readerCardEntity.getCard_no());
		data.put("PATRONPWD", readerCardEntity.getCard_pwd());
		data.put("LIBCODE", lib_id);//图书馆id
		paramMap.put("data", data);
		return JsonUtils.toJson(paramMap).replace("\n", "");
	}
	/**
	 * 书信息json
	 * @param bookinfo
	 * @return
	 */
	public String getBookInfoJson(AcsBookInfoEntity bookinfo){
		Map<String, Object> paramMap = getParamMap(bookinfo.getOperation());
		
		Map<String, Object> data = new HashMap<>(3);
		data.put("BOOKSN",bookinfo.getBook_sn());
		data.put("LIBCODE", lib_id);//图书馆id
		paramMap.put("data", data);
		return JsonUtils.toJson(paramMap).replace("\n", "");
	}
	/**
	 * 续借json
	 * @param renew
	 * @return
	 */
	public String getRenewJson(AcsRenewEntity renew){
		Map<String, Object> paramMap = getParamMap(renew.getOperation());
		
		Map<String, Object> data = new HashMap<>(10);
		data.put("BOOKSN",renew.getBook_sn());
		data.put("PATRONCARDSN", renew.getCard_no());
		data.put("PATRONPWD", renew.getCard_pwd());
		data.put("LIBCODE", lib_id);//图书馆id
		paramMap.put("data", data);
		return JsonUtils.toJson(paramMap).replace("\n", "");
	}
	
	/**
	 * 预借指令
	 * @return
	 */
	public String getReservationBookJson(AcsReservationBookEntity reservation){
		Map<String, Object> paramMap = getParamMap(reservation.getOperation());
		
		Map<String, Object> data = new HashMap<>(8,1.0f);
		data.put("PATRONCARDSN", reservation.getCard_no());
		data.put("PATRONPWD", reservation.getCard_pwd());
		data.put("BOOKSN", reservation.getBook_sn());
		NOTIFY_MODE notifyModel = reservation.getNotifyModel();
		if(notifyModel == NOTIFY_MODE.EMAIL){
			data.put("NOTIFYMODE", "1");
		}else if(notifyModel == NOTIFY_MODE.NETWORK_ENQUITY){
			data.put("NOTIFYMODE", "2");
		}else if(notifyModel == NOTIFY_MODE.PHONE){
			data.put("NOTIFYMODE", "3");
		}else if(notifyModel == NOTIFY_MODE.LETTER){
			data.put("NOTIFYMODE", "4");
		}
		data.put("BOOKHANDLEADDR", reservation.getBookHandleAddr());
		data.put("LIBCODE", lib_id);//图书馆id
		
		paramMap.put("data", data);
		return JsonUtils.toJson(paramMap).replace("\n", "");
	}
	
	public String getInReservationBookJson(AcsInReservationBookEntity entity){
		Map<String, Object> paramMap = getParamMap(entity.getOperation());
		
		Map<String, Object> data = new HashMap<>(8,1.0f);
		data.put("PATRONCARDSN", entity.getCard_no());
		data.put("PATRONPWD", entity.getCard_pwd());
		if(entity.getBook_sn() != null){
			data.put("BOOKSN", entity.getBook_sn());
		}else if(entity.getBookRecon() != null){
			data.put("BOOKRECNO", entity.getBookRecon());
		}
		
		data.put("LIBCODE", lib_id);//图书馆id
		
		paramMap.put("data", data);
		return JsonUtils.toJson(paramMap).replace("\n", "");
	}
	
	/**
	 * 通过书名记录号获取预借图书信息
	 * @param entity
	 * @return
	 */
	public String getBookInfoByRecon(AcsBookInfoByReconEntity entity){
		Map<String, Object> paramMap = getParamMap(entity.getOperation());
		
		Map<String, Object> data = new HashMap<>(3,1.0f);
		data.put("BOOKRECNO", entity.getBookRecon());
		data.put("LIBCODE", lib_id);//图书馆id
		
		paramMap.put("data", data);
		return JsonUtils.toJson(paramMap).replace("\n", "");
	}
	
	/**
	 * 查询我的预借列表指令
	 * @param entity
	 * @return
	 */
	public String getQueryReservation(AcsQueryReservationEntity entity){
		Map<String, Object> paramMap = getParamMap(entity.getOperation());
		
		Map<String, Object> data = new HashMap<>(3,1.0f);
		data.put("PATRONCARDSN", entity.getCard_no());
		data.put("LIBCODE", lib_id);//图书馆id
		
		paramMap.put("data", data);
		return JsonUtils.toJson(paramMap).replace("\n", "");
	}
	
	private Map<String, Object> getParamMap(String operation){
		Map<String, Object> paramMap = new HashMap<>(5,1.0f);
		paramMap.put("operation", operation);
		paramMap.put("servicetype", servicetype);
		paramMap.put("target", target);
		return paramMap;
	}
}
