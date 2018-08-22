package com.ssitcloud.datasync.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Protocol {
	private String commanndkey;

	private String commanndname;

	private String description;

	private String request;

	private String response;

	@XmlAttribute
	public String getCommanndkey() {
		return commanndkey;
	}

	public void setCommanndkey(String commanndkey) {
		this.commanndkey = commanndkey;
	}

	@XmlAttribute
	public String getCommanndname() {
		return commanndname;
	}

	public void setCommanndname(String commanndname) {
		this.commanndname = commanndname;
	}

	@XmlAttribute
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@XmlElement
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
