package com.ssitcloud.businessauth.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @comment 
 * @date 2016年5月20日
 * @author hwl
 */
public interface LibraryTemplateService {

	String selectlibtemp(Map<String, String> map);
	
	ResultEntity deletelibtemp(String info);
	
	ResultEntity updatelibtemp(String req);
	
	String addlibtemp(String info);
	
	String selectAllLibtemp(Map<String, String> map);
	
	
	/**
	 * 查询图书馆的模板信息，以及所有 有效的设备用户的id
	 *
	 * <p>2016年6月2日 下午5:48:49 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selDeviceUserByLibraryIdx(String json);
	/**
	 * 根据主键取得 模板信息
	 * @param req
	 * @return
	 */
	ResultEntity selLibraryServiceTemplateByIdx(String req);
}
