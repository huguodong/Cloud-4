package com.ssitcloud.datasync.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AcsCfgProtocols implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String acsType;

	private String acsName;

	private String modifiedTime;

	private String applyDescription;

	private List<Protocol> protocol;

	@XmlElement
	public List<Protocol> getProtocol() {
		return protocol;
	}

	public void setProtocol(List<Protocol> protocol) {
		this.protocol = protocol;
	}

	@XmlAttribute
	public String getAcsType() {
		return acsType;
	}

	public void setAcsType(String acsType) {
		this.acsType = acsType;
	}

	@XmlAttribute
	public String getAcsName() {
		return acsName;
	}

	public void setAcsName(String acsName) {
		this.acsName = acsName;
	}

	@XmlAttribute
	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	@XmlAttribute
	public String getApplyDescription() {
		return applyDescription;
	}

	public void setApplyDescription(String applyDescription) {
		this.applyDescription = applyDescription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
