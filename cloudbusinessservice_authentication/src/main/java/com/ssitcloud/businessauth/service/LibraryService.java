package com.ssitcloud.businessauth.service;

import java.util.Map;










import com.ssitcloud.common.entity.ResultEntity;

/**
 * <p>2016年4月5日 上午11:31:19
 * @author hjc
 *
 */
public interface LibraryService {
	
	/**
	 * 新增图书馆表的信息
	 *
	 * <p>2016年4月21日 下午5:28:11
	 * <p>create by hjc
	 * @param param 
	 * @return
	 */
	public abstract String addLibrary(Map<String, String> param);
	
	/**
	 * 根据图书馆library_idx删除图书馆信息
	 *
	 * <p>2016年4月21日 下午5:27:35
	 * <p>create by hjc
	 * @param param 
	 * @return
	 */
	public abstract String delLibraryByIdx(Map<String, String> param);
	
	/**
	 * 根据图书馆library_idx或library_id查询图书馆信息
	 *
	 * <p>2016年4月21日 下午5:27:57
	 * <p>create by hjc
	 * @param param 
	 * @return
	 */
	public abstract String selLibraryByIdxOrId(Map<String, String> param);
	
	/**
	 * 根据图书馆表library_idx的list,或者图书馆lib_id的list查询图书馆信息
	 *
	 * <p>2016年4月21日 下午5:27:57
	 * <p>create by hjc
	 * @param param 
	 * @return
	 */
	public abstract String selLibraryByIdxsOrIds(Map<String, String> param);

	
	public abstract String selLibraryByFuzzy(Map<String, String> param);
	
	public abstract String selectlibinfo(Map<String,String> param);
	
	public abstract String selectlibinfoByParam(Map<String,String> param);
	
	public abstract String selectMasterinfo(Map<String,String> param);
	
	public abstract String addlibinfo(Map<String,String> param);
	
	public abstract ResultEntity deletelibinfo(String req);
	
	public abstract String updatelibinfo(Map<String,String> param);

	public abstract ResultEntity querylibInfoByCurUser(String req);

	public abstract ResultEntity querySlaveLibraryByMasterIdx(String req);

	public abstract ResultEntity queryAllMasterLibAndSlaveLib(String req);

	public abstract ResultEntity getLibIdAndLibIdx(String req);
	/**
	 * 查询lib_id list 根据 lib_id 模糊查询
	 * @param req
	 * @return
	 */
	public abstract ResultEntity selLibraryIDByFuzzy(String req);

	public abstract ResultEntity querylibInfoByCurUserEXT1(String req);
	
	public abstract ResultEntity queryMasterSubRelations(String req);
	
	public abstract ResultEntity selActualLibraryMaster(String req);
}
