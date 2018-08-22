package com.ssitcloud.business.devmgmt.service;

import java.util.Map;



/**
 * 数据同步service
 * @author hwl
 *
 */
public interface DeviceDBsyncConfigService {

	String SelDeviceDbsync(String json);
	
	String UpdDeviceDbsync(String json);
	
	String AddDeviceDbsync(String json);
	
	String SelLibrary(String json);

	String delDevDBsync(String json);

	String updDevConfig(String json);

	/**
	 * 
	 * @comment
	 * @param map
	 * @return
	 * @data 
	 * @author hwl
	 */
	String selDbsynctemp(Map<String, String> map);
	
	/**
	 * 
	 * @comment
	 * @param map
	 * @return
	 * @data 
	 * @author hwl
	 */
	String updDbsynctemp(Map<String, String> map);
	
	/**
	 * 
	 * @comment
	 * @param map
	 * @return
	 * @data 
	 * @author hwl
	 */
	String addDbsynctemp(Map<String, String> map);
	
	/**
	 * 
	 * @comment
	 * @param map
	 * @return
	 * @data 
	 * @author hwl
	 */
	String delDbsynctemp(Map<String, String> map);
	
}
