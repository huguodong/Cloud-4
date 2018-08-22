package com.ssitcloud.dbauth.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.LibraryEntity;
import com.ssitcloud.dbauth.entity.LibraryServiceTemplateEntity;
import com.ssitcloud.dbauth.entity.RelLibsEntity;
import com.ssitcloud.dbauth.entity.page.LibraryPageEntity;
import com.ssitcloud.dbauth.entity.page.LibraryPageTempInfoEntity;
import com.ssitcloud.dbauth.entity.page.LibraryUnionEntity;

/**
 * <p>2016年4月5日 上午11:21:35
 * @author hjc
 *
 */
public interface LibraryDao extends CommonDao {

	/**
	 * 新增图书馆
	 * 
	 * <p>2016年4月5日 下午3:44:04
	 * <p>create by hjc
	 * @param libraryEntity 图书馆实体类，新增成功之后，library_idx为自增ID
	 * @return 数据库操作结果
	 */
	public abstract int addLibrary(LibraryEntity libraryEntity);
	
	/**
	 * 根据图书馆library_idx删除图书馆信息
	 * 
	 * <p>2016年4月6日 上午9:10:54
	 * <p>create by hjc
	 * @param libraryEntity 图书馆实体类
	 * @return 数据库操作结果
	 */
	public abstract int delLibraryByIdx(LibraryEntity libraryEntity);
	
	/**
	 * 根据图书馆library_idx,或图书馆lib_id查询图书馆信息
	 *
	 * <p>2016年4月21日 下午5:19:42
	 * <p>create by hjc
	 * @param libraryEntity
	 * @return
	 */
	public abstract LibraryEntity selLibraryByIdxOrId(LibraryEntity libraryEntity);
	
	/**
	 *  根据图书馆library_idx的list 或者lib_id的list查询图书馆信息
	 *
	 * <p>2016年4月21日 下午5:19:42
	 * <p>create by hjc
	 * @param param
	 * @return 
	 */
	public abstract List<LibraryEntity> selLibraryByIdxsOrIds(Map<String, Object> param);
	
	/**
	 * 通过图书馆lib_id模糊查询图书馆信息
	 * @comment
	 * @param libraryEntity
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	public abstract List<LibraryEntity> selLibraryFuzzyByID(LibraryEntity libraryEntity);
	
	/**
	 * 分页查询
	 * @comment
	 * @param libraryPageEntity
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	public abstract LibraryPageEntity selLibraryinfo(LibraryPageEntity libraryPageEntity);
	
	/**
	 * 查询所有馆信息(包括主馆 和 分馆)
	 * @comment
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	public abstract List<LibraryEntity> selMasterlib();
	
	/**
	 * 根据条件进行分页查询
	 * @comment
	 * @param lInfoEntity
	 * @return
	 * @data 2016年5月30日`
	 * @author hwl
	 */
	public abstract LibraryPageTempInfoEntity selLibInfoByParam(LibraryPageTempInfoEntity lInfoEntity);
	
	public abstract int updateLibrary(LibraryEntity libraryEntity);
	/**
	 * 查询出下属馆的信息
	 * @param library_idx
	 * @return
	 */
	public abstract List<LibraryEntity> querySlaveLibraryByMasterIdx(
			Integer library_idx);
	
	/**
	 * 查询出父馆的信息
	 * @param library_idx
	 * @return
	 */
	public LibraryEntity queryMasterLibraryBySlaveIdx(Integer library_idx);
	/**
	 * 查询没有分馆的 图书馆
	 * @param libraryEntity
	 * @return
	 */
	public abstract List<LibraryEntity> queryLibWhereisNotSlaveLib(LibraryEntity libraryEntity);

	public abstract Map<Integer, String> getLibIdAndLibIdx();
	
	/**
	 * 根据模板ID查询有多少图书馆在使用这个模板
	 *
	 * <p>2016年7月16日 下午5:57:45 
	 * <p>create by hjc
	 * @param templateEntity
	 * @return
	 */
	public abstract int countLibraryByTempId(LibraryServiceTemplateEntity templateEntity);

	public abstract List<String> selLibraryIDByFuzzy(LibraryEntity libraryEntity);
	/**
	 * 通过图书馆模板ID查询图书馆信息
	 * @param lib_service_tpl_id
	 * @return
	 */
	public abstract List<LibraryEntity> selectLibraryByTempId(Integer lib_service_tpl_id);
	/**
	 * 新增 ，包括主鍵
	 * @param row
	 * @return
	 */
	public abstract int addLibraryWithIdx(LibraryEntity row);

	/**
	 * 查询出子馆的详细详细
	 * @param param 查询参数
	 * @param pageCurrent
	 * @param pageSize
	 * @return
	 */
	List<LibraryUnionEntity> selectChildLibrary(Map<String, Object> param,Integer pageCurrent,Integer pageSize);
	
	/**
	 * 根据参数查询图书馆详细信息
	 * @param param
	 * @param pageCurrent
	 * @param pageSize
	 * @return
	 */
	List<LibraryUnionEntity> selectLibraryAndInfo(Map<String, Object> param);

	
	/**
	 * 获取所有的图书馆数据
	 *
	 * <p>2017年3月21日 下午2:12:41 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<LibraryEntity> getAllLibraryList();
	/**
	 * 通过图书馆ID或名称模糊查询
	 *lqw 2017/3/22
	 */
	public abstract List<LibraryEntity> selLibraryByNameORLibId(LibraryEntity libraryEntity);
	
	/**
	 * 查询子馆的
	 * @param mastLibIdx 主馆idx
	 */
	List<Map<String, Object>> selectChildLibraryRegionCode(Integer mastLibIdx);
	
	/**
	 * 查询所有主从关系
	 *
	 * <p>2017年11月21日 下午2:37:16 
	 * <p>create by hjc
	 * @param mastLibIdx
	 * @return
	 */
	List<Map<String, Object>> queryMasterSubRelations( RelLibsEntity relLibsEntity);
	
	public abstract List<LibraryEntity> selActualLibraryMaster(LibraryEntity libraryEntity);
}
