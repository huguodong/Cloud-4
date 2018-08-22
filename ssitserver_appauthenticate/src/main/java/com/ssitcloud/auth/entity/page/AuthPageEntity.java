package com.ssitcloud.auth.entity.page;

import java.util.Date;

import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * 
 * @comment 表名AuthPageEntity
 * 
 * @author min
 * @data 2017年4月25日
 */
@SuppressWarnings("rawtypes")
public class AuthPageEntity extends DatagridPageEntity{
	private static final long serialVersionUID = -2620275071464449606L;
	private String random_code;
	private String device_id;
	private String reader_code;
	private String reader_passwd;
	private String scan_date;
	private String state;

	public AuthPageEntity() {
	}

	public String getRandom_code() {
		return random_code;
	}

	public void setRandom_code(String random_code) {
		this.random_code = random_code;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getReader_code() {
		return reader_code;
	}

	public void setReader_code(String reader_code) {
		this.reader_code = reader_code;
	}

	public String getReader_passwd() {
		return reader_passwd;
	}

	public void setReader_passwd(String reader_passwd) {
		this.reader_passwd = reader_passwd;
	}

	public String getScan_date() {
		return scan_date;
	}

	public void setScan_date(String scan_date) {
		this.scan_date = scan_date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "AuthEntity [random_code=" + random_code + ", device_id="
				+ device_id + ", reader_code=" + reader_code
				+ ", reader_passwd=" + reader_passwd + ", scan_date="
				+ scan_date + ", state=" + state + "]";
	}

}
