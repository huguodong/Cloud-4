package com.ssitcloud.business.nodemgmt.service;


public interface NodeMonitorBusinessService {
	public String queryNodeMonitorByPage(String req);

	public String queryNodeMonitorByParam(String req);

	public String queryNodeMonitorById(String req);

	public String getTypeList(String req);
}
