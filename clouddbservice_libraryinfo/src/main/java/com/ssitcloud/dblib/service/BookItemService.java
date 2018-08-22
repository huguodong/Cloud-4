package com.ssitcloud.dblib.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.entity.BookItemEntity;
import com.ssitcloud.dblib.entity.page.BookItemPageEntity;
import com.ssitcloud.dblib.entity.page.BookUnionEntity;

public interface BookItemService {
	
	/**
	 * 插入一条bookItemEntity。注意，此操作当实体中nowlib_idx==null时，会将nowlib_idx设为lib_idx
	 * @param bookItemEntity
	 * @return
	 */
	public abstract int insertBookItem(BookItemEntity bookItemEntity);
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月7日 下午5:03:46 
	 * <p>create by hjc
	 * @param bookItemEntity
	 * @return
	 */
	public abstract int deleteBookItem(BookItemEntity bookItemEntity);
	/**
	 * 更新一条记录
	 *
	 * <p>2017年2月7日 下午5:03:46 
	 * <p>create by hjc
	 * @param bookItemEntity
	 * @return
	 */
	public abstract int updateBookItem(BookItemEntity bookItemEntity);
	/**
	 * 查询一条记录 
	 *
	 * <p>2017年2月7日 下午5:03:46 
	 * <p>create by hjc
	 * @param bookItemEntity
	 * @return
	 */
	public abstract BookItemEntity queryBookItem(BookItemEntity bookItemEntity);
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月7日 下午5:03:46 
	 * <p>create by hjc
	 * @param bookItemEntity
	 * @return
	 */
	public abstract List<BookItemEntity> queryBookItemList(BookItemEntity bookItemEntity);
	
	/**
	 * 分页查询
	 *
	 * <p>2017年2月9日 下午2:16:38 
	 * <p>create by hjc
	 * @param bookItemPageEntity
	 * @return
	 */
	public abstract BookItemPageEntity queryBookItemListByPage(BookItemPageEntity bookItemPageEntity);
	
	public abstract List<BookItemEntity> queryAllBookitem(BookItemEntity bookitem);
	
	public abstract BookItemEntity queryBookitemByCode(BookItemEntity bookitem);
	public abstract BookUnionEntity queryAllBookitemAndBookInfoByCode(BookItemEntity bookitem);
	
	@Deprecated
	public abstract int updateBookitem(BookItemEntity bookitem);
	
	public abstract int deleteBookitemById(List<BookItemEntity> list);
	
	public abstract int addBookitem(BookItemEntity bookitem);
	
	public abstract int importBookitem(Integer lib_idx,String fileName);
	
	public abstract int updateBookitemByCodeAndLibId(BookItemEntity bookitem);
	
	public abstract List<BookItemEntity> queryAllBookitemByLibId(BookItemEntity bookitem);
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
	List<BookUnionEntity> queryBookitemAndBookInfo(Map<String, Object> map);
	
	/**
	 * 根据bookitem_idx查询BookUnionEntity
	 * @param bookitemIdx
	 * @return
	 */
	BookUnionEntity queryBookitemAndBookInfobyIdx(Integer bookitemIdx);
	
	public abstract List<BookUnionEntity> queryAllBookitemAndBookInfo(BookItemEntity bookitem);
	
	public abstract BookItemEntity queryBookItemByLibIdxAndBookBarcode(BookItemEntity bookitem);
	
	/**
	 * 查询所有的bookitem以及biblios的数据
	 *
	 * <p>2017年8月14日 下午3:43:28 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryBookItemAndBibliosInfo(String req);
	
	public ResultEntity queryBookItemByUnion(String req);
	
	/**
	 * 查询图书信息作为图书推荐规则
	 * @param bookItemEntity
	 * @return
	 */
	public abstract ResultEntity queryBookInfoForRecommend(String req);
	/**
	 *  更新馆藏图书状态
	 * @param bookItemEntity
	 * @return
	 */
	public abstract int updateState(BookItemEntity bookItemEntity);
}
