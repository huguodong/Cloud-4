package com.ssitcloud.business.librarymgmt.service;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
/**
 * 
 * @comment 
 * @date 2016年5月17日
 * @author hwl
 */
public interface ReaderTypeService {

	String Selectreadertype(Map<String, String> map);
	
	String SelectreaderTypeByFuzzy(Map<String, String> map);
	
	String Updatereadertype(String info);
	
	String Insertreadertype(String info);
	
	String Deletereadertype(String info);
	
	String SelLibrary(String reqInfo);
	
	String SelLibraryByFuzzy(String reqInfo);
	
	String addOperationlog(String operlog);
	
	/**
	 * 根据图书馆ID查询图书馆维护卡信息
	 *
	 * <p>2016年7月14日 下午2:01:43 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	public abstract ResultEntity queryMaintenanceCard(String req);
	/**
	 * 根据图书馆ID查询图书馆维护卡信息
	 *
	 * <p>2016年7月14日 下午2:01:43 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	public abstract ResultEntity queryOperatorMaintenanceCard(String req);
	/**
	 * 修改操作员的维护卡信息
	 *
	 * <p>2016年7月14日 下午4:55:33 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateOperatorMaintenanceCard(String req);
	/**
	 * 根据 lib_id模糊查询 lib_id List
	 * 模糊查询
	 * @param libinfo
	 * @return
	 */
	ResultEntity selLibraryIDByFuzzy(String libinfo);
}
