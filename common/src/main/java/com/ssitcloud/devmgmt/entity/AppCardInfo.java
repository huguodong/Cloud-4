package com.ssitcloud.devmgmt.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;


public class AppCardInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//设备ID
	//对应接口的device_id 
	private String device_id;
	//图书馆ID
	private String library_id;
	
	/*
	 * 手机的默认卡的基础信息
	系统组成(1 IOS  2 Android)|密钥版本号|图书馆ID|读者证号|图书列表(多个条码号之间用逗号分隔)|密文
	密钥版本号：用来标识RSA密钥对的变化。如果设备端与手机端不符，则提示升级手机端。
	密文构成    RSA(MD5(时间戳|读者证密码)|时间戳|读者证密码)
	MD5采用的是32位大写的字符
	RSA（RSA_PKCS1_PADDING ）填充模式是PKCS#1

	 */
	private String app_data;
	
	private Date receiveTime;
	
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AppCardInfo other = (AppCardInfo) obj;
		
		if (device_id == null) {
			if (other.device_id != null) {
				return false;
			}
		}else if (!device_id.equals(other.device_id)) {
			return false;
		}
		
		if (library_id == null) {
			if (other.library_id != null) {
				return false;
			}
		}else if (!library_id.equals(other.library_id)) {
			return false;
		}

		//
		//数据最终格式
		//系统组成(1 IOS  2 Android)|密钥版本号|图书馆ID|读者证号|图书列表(多个条码号之间用逗号分隔)|密文
		/*
		 * 1|V1.0|SZTSG001|014229|0251365,0125487|BTVOWv23piwm2DDAyWw1T9FfPzRV+XwW7pw7cidNfhFMYCWD+RlnL1g+oIVOt4mqFKLA+SXkH9DX
LMv/HS/GH0wIA6JMNlznlgTqFEYxftRNyWj4kR/cQ1eYcHhxpKSRvwhMUr9OpWyuVEuCEDzMSQDx
YKBg/f0j7pWL2/ldHO4=	
		 */
		try {
			String [] data1 = app_data.split("\\|");
			String [] data2 = other.app_data.split("\\|");
			if (data1.length != data2.length) {
				return false;
			}
			if (!data1[0].equals(data2[0])) {//系统
				return false;
			}
			if (!data1[1].equals(data2[1])) {//密钥版本号
				return false;
			}
			if (!data1[2].equals(data2[2])) {//图书馆id
				return false;
			}
			if (!data1[3].equals(data2[3])) {//读者证号
				return false;
			}
//			if (!data1[4].equals(data2[4])) {//图书列表
//				return false;
//			}
			//图书列表
			String [] books1 = (data1[4]).split(",");
			String [] books2 = (data2[4]).split(",");
			Arrays.sort(books1);
			Arrays.sort(books2);
			if (!Arrays.equals(books1, books2)) {
				return false;
			}
			
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	

	public Date getReceiveTime() {
		return receiveTime;
	}



	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}



	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	

	public String getLibrary_id() {
		return library_id;
	}


	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}


	public String getApp_data() {
		return app_data;
	}

	public void setApp_data(String app_data) {
		this.app_data = app_data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
