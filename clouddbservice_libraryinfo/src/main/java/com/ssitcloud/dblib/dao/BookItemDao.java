package com.ssitcloud.dblib.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.dblib.entity.BibliosAndBook;
import com.ssitcloud.dblib.entity.BookItemEntity;
import com.ssitcloud.dblib.entity.page.BookItemPageEntity;
import com.ssitcloud.dblib.entity.page.BookUnionEntity;


public interface BookItemDao {
	
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月7日 下午5:03:46 
	 * <p>create by hjc
	 * @param bookItemEntity
	 * @return
	 */
	public abstract int insertBookItem(BookItemEntity bookItemEntity);
	
	/**
	 * 批量新增bookitem
	 *
	 * <p>2017年11月11日 下午4:45:10 
	 * <p>create by hjc
	 * @param list
	 * @return
	 */
	public abstract int insertBookItemBatch(List<BookItemEntity> list);
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
	 * 批量更新bookitem
	 *
	 * <p>2017年11月11日 下午4:48:01 
	 * <p>create by hjc
	 * @param list
	 * @return
	 */
	public abstract int updateBookItemBatch(List<BookItemEntity> list);
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
	
	public abstract int updateBookitemByCodeAndLibId(BookItemEntity bookitem);
	
	public abstract List<BookItemEntity> queryAllBookitemByLibId(BookItemEntity bookitem);
	/**
	 * 查询bookitem以及对应的简易数目信息
	 * @param map {device_idx:设备id（可选）
	 * 			   nowlib_idx:当前馆藏地（可选）
	 * 			   title:书名（模糊查询可选）
	 * 			   isbn:isbn（可选）
	 * 			   state:图书状态（可选）
	 * 			   limitS:分页参数（可选）
	 * 			   limitE:分页参数（可选）
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
	
	/**
	 * 根据libidx  以及  book_barcode 查询馆藏数据
	 *
	 * <p>2017年4月12日 上午11:41:38 
	 * <p>create by hjc
	 * @param bookItemEntity
	 * @return
	 */
	BookItemEntity queryBookItemByLibIdxAndBookBarcode(BookItemEntity bookItemEntity);
	
	List<BookUnionEntity> queryAllBookitemAndBookInfo(BookItemEntity bookItemEntity);
	
	/**
	 * 查询所有的图书馆藏信息以及图书详情
	 *
	 * <p>2017年8月14日 下午5:10:13 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract List<Map<String, Object>> queryBookItemAndBibliosInfo(Map<String, Object> param);
	
	public abstract int insertBookItemNoUpdatetime(BookItemEntity bookItemEntity);
	public abstract int updateBookItemNoUpdatetime(BookItemEntity bookItemEntity);
	public List<BookUnionEntity> queryBookItemByUnion(BookUnionEntity bookUnionEntity);
	
	public abstract BibliosAndBook insertBookItem2(BibliosAndBook bibliosAndBook);
	
	/**
	 * 查询图书信息作为图书推荐规则
	 * @param bookItemEntity
	 * @return
	 */
	public abstract List<BookItemEntity> queryBookInfoForRecommend(BookItemEntity bookItemEntity);
	
	/**
	 * 更新馆藏图书状态
	 * @param bookItemEntity
	 * @return
	 */
	public abstract int updateState(BookItemEntity bookItemEntity);
}
