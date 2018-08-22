package com.ssitcloud.devmgmt.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ssitcloud.common.util.CDataAdapter;

@XmlRootElement(name="table")
public class BakupEntity {

	
	private List<String> row;
	
	private String tablename;

	public String getTablename() {
		return tablename;
	}
	@XmlAttribute(name="tablename")
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	@XmlJavaTypeAdapter(CDataAdapter.class)
	public List<String> getRow() {
		return row;
	}
	
	public void setRow(List<String> row) {
		this.row = row;
	}
}
