package com.ssitcloud.datasync.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ever {
	private List<AcsCfgProtocols> protocols;

	@XmlElement
	public List<AcsCfgProtocols> getProtocols() {
		return protocols;
	}

	public void setProtocols(List<AcsCfgProtocols> protocols) {
		this.protocols = protocols;
	}
}
