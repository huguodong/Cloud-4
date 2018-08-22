package com.ssitcloud.business.mobile.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.authentication.entity.LibraryInfoEntity;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 图书馆服务
 * @author LXP
 * @version 创建时间：2017年3月1日 下午4:56:09
 */
public interface LibraryServiceI {
	
	/**
	 * 根据图书馆主键id获取图书馆信息
	 * @param lib
	 * @return
	 */
	Map<String, Object> selectLibraryByPk(Integer lib);
	
	/**
	 * 根据图书馆id列表查询图书馆信息
	 * @param libIdList
	 * @return
	 */
	List<Map<String, Object>> selectLibrarys(List<Integer> libIdList);
	
	/**
	 * 根据参数查询图书馆信息
	 * @param param pageSize每页多少条数据，pageCurrent
	 * @return
	 */
	List<Map<String, Object>> selectLibrarys(Map<String, Object> param);
	
	
	/**
	 * 查出子馆如果有的话
	 * add by shuangjunjie
	 * 2017年3月6日 下午4:18
	 * @param req
	 * @return
	 */
	ResultEntity querySlaveLibraryByMasterIdx(String req);
	
	/**
	 * 根据library_idx查出library_id
	 * add by shuangjunjie 
	 * 2017年3月6日 下午4:31
	 * @param req
	 * @return
	 */
	ResultEntity selectLibraryIdByIdx(String req);
	
	/**
	 * 获取所有的不是slave的馆和其一级子馆
	 * add by shuangjunjie
	 * 2017年4月5日 上午10:33
	 * @param req
	 * @return
	 */
	ResultEntity queryAllMasterLibAndSlaveLib(String req);
	
	/**
	 * 查询子馆idx和子馆地区码
	 * @return
	 */
	List<Map<String, Object>> selectChildIdxAndRegionCode(int master_lib_idx);
	
	/**
	 * 发送二维码数据到datasync
	 * @param json 客户端提交数据
	 */
	ResultEntity sendBarcode(String json,ReaderCardServiceI readerCardService);

    /**
     * 查询LibraryInfoEntity
     * @param param 查询参数，可以为null
     * @return 若成功返回数组，失败返回null
     */
	List<LibraryInfoEntity> selectLibraryInfo(LibraryInfoEntity param);
}
