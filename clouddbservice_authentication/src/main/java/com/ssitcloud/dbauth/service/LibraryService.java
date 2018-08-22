package com.ssitcloud.dbauth.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.LibraryEntity;
import com.ssitcloud.dbauth.entity.page.LibraryPageEntity;
import com.ssitcloud.dbauth.entity.page.LibraryPageTempInfoEntity;
import com.ssitcloud.dbauth.entity.page.LibraryUnionEntity;
import com.ssitcloud.dbauth.param.LibraryParam;

/**
 * <p>2016年4月5日 上午11:31:19
 * @author hjc
 *
 */
public interface LibraryService {
	
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
	
	public abstract ResultEntity delLibraryInfoByIdx(String req);
	
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
	 * @param libraryEntity
	 * @return
	 */
	public abstract List<LibraryEntity> selLibraryByIdxsOrIds(Map<String, Object> infos);
	
	/**
	 * 根据id模糊查询
	 * @comment
	 * @param infos
	 * @return
	 * @data 2016年5月18日`
	 * @author hwl
	 */
	public abstract List<LibraryEntity> selLibraryByFuzzy(LibraryEntity libraryEntity);
	/**
	 * 新增图书馆，以及相关操作
	 *
	 * <p>2016年4月22日 下午7:27:58
	 * <p>create by hjc
	 * @param libraryParam
	 * @return
	 */
	public abstract ResultEntity addNewLibrary(LibraryParam libraryParam);
	
	/**
	 * 修改图书馆信息，以及相关表操作
	 *
	 * <p>2016年4月23日 下午4:18:18
	 * <p>create by hjc
	 * @param libraryParam
	 * @return
	 */
	public abstract ResultEntity modifyLibrary(LibraryParam libraryParam);
	
	/**
	 * 分页查询图书馆信息
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	public abstract LibraryPageEntity sellibraryInfo(String json);
	
	/**
	 * 查询主馆信息
	 * @comment
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	public abstract List<LibraryEntity> selMasterLib();
	
	/**
	 * 条件查询图书馆信息
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	public abstract LibraryPageTempInfoEntity selLibInfoByParam(String json);

	public abstract ResultEntity querylibInfoByCurUser(String req);
	/**
	 * 根据父馆IDX查询下属馆的信息
	 * @param req
	 * @param request
	 * @return
	 */
	public abstract ResultEntity querySlaveLibraryByMasterIdx(String req);

	public abstract ResultEntity queryAllMasterLibAndSlaveLib(String req);

	public abstract ResultEntity getLibIdAndLibIdx(String req);
	/**
	 * 批量删除
	 * @param operMap
	 * @return
	 */
	public abstract ResultEntity delLibraryInfoByIdxArray(Map<String, Object> operMap);
	/**
	 * 查询lib_id list 根据 lib_id 模糊查询 
	 * @param req
	 * @return
	 */
	public abstract ResultEntity selLibraryIDByFuzzy(String req);
	/**
	 * 
	 * @param req
	 * @return
	 */
	public abstract ResultEntity querylibInfoByCurUserEXT1(String req);
	
	/**
	 * 通过图书馆ID或名称模糊查询
	 *lqw 2017/3/22
	 */
	public abstract ResultEntity selLibraryByNameORLibId(String req);


	ResultEntity selectChildLibrary(Map<String, Object> param,Integer pageCurrent,Integer pageSize);

	/**
	 * 获取所有的图书馆数据
	 *
	 * <p>2017年3月21日 下午2:11:27 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity getAllLibraryList(String req);
	/**
	 * 根据馆id查询父馆 xbf-20170327
	 * @param library_idx
	 * @return
	 */
	public ResultEntity queryMasterLibraryBySlaveIdx(String req);
	
	/**
	 * 查询子馆的idx和地区码
	 * @param mastLibIdx 主馆idx
	 */
	List<Map<String, Object>> selectChildLibraryRegionCode(Integer mastLibIdx);
	
	List<LibraryUnionEntity> selectLibraryAndInfo(Map<String, Object> param);
	
	public ResultEntity queryMasterSubRelations(String req);
	
	public ResultEntity selActualLibraryMaster(String req);
	
	public void loadLibraryToRedis();
}
