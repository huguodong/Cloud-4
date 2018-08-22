package com.ssitcloud.business.mobile.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.libraryinfo.entity.BookItemPageEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;

/**
 * 借阅书服务
 * @author LXP
 * @version 创建时间：2017年2月28日 下午4:10:55
 */
public interface BorrowServiceI {
	
	/**
	 * 根据图书馆id和设备id获取设备在架书，第一种情况
	 * @param lib_id
	 * @param device_id
	 * @param state 图书状态 0 – 已分派1 – 已上架2 – 已借出3 – 已下架
	 * @return null则查询失败
	 */
	List<Map<String, Object>> findBookByLibAndDevice(BookItemPageEntity selectEntity);
	
	/**
	 * 续借图书
	 * @return
	 */
	ResultEntity renewBook(ReaderCardEntity card,String booksn);
	
	/**
	 * 根据bookitem_idx查询BookUnionEntity
	 * @param bookitem_id
	 * @return
	 */
	ResultEntity queryBookUnionEntity(Integer bookitem_idx);
	
	/**
	 * 查询bookitem以及对应的简易数目信息
	 * @param map {device_idx:设备id（可选）
	 * 			   nowlib_idx:当前馆藏地（可选）
	 * 			   title:书名（模糊查询可选）
	 * 			   isbn:isbn（可选）
	 * 			   state:图书状态（可选）
	 * 			   pageCurrent:第几页，1开始（可选）
	 * 			   pageSize:每页多少条（可选）
	 * }
	 * @return
	 */
	ResultEntity queryBookitemAndBookInfo(Map<String, Object> param);

	/**
	 * 预借图书
	 * @return
	 */
	ResultEntity reservationBook(Map<String, Object> param,Map<String, Object> iddata);
	
	/**
	 * 取消预借图书
	 * @return
	 */
	ResultEntity inReservationBook(Map<String, Object>  param);
	
	/**
	 * 查询预借列表
	 * @param param
	 * @return
	 */
	ResultEntity reservationList(Map<String, Object>  param);
	
	/**
	 * 查询图书预借状态
	 * @param booksn
	 * @return
	 */
	ResultEntity selectBookState(Integer bookitem_idx);
	
	/**
	 * 获取图书分类信息
	 * @param data_type 1 中图法
	 * @return
	 */
	ResultEntity bookClassIfy(Integer data_type);
}
