package com.ssitcloud.dblib.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.BookItemDao;
import com.ssitcloud.dblib.entity.BibliosAndBook;
import com.ssitcloud.dblib.entity.BookItemEntity;
import com.ssitcloud.dblib.entity.page.BookItemPageEntity;
import com.ssitcloud.dblib.entity.page.BookUnionEntity;

@Repository
public class BookItemDaoImpl extends CommonDaoImpl implements BookItemDao{
	
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public int insertBookItem(BookItemEntity bookItemEntity) {
		return this.sqlSessionTemplate.insert("bookitem.insertBookItem",bookItemEntity);
	}
	

	@Override
	public int insertBookItemBatch(List<BookItemEntity> list) {
		Date date1 = new Date();
		try(Connection con = this.sqlSessionTemplate.getConfiguration().getEnvironment().getDataSource().getConnection()) {
			boolean defaultAutoCommit = con.getAutoCommit(); //保存当前默认的提交方式
			if(!con.isClosed()) {
				con.setAutoCommit(false);//关闭自动提交
				if (!con.getMetaData().supportsBatchUpdates()) {
					LogUtils.error("不支持批量数据提交");
					return 0;
				}
				PreparedStatement pStatement = con.prepareStatement(batchAddBookitemSql());
				for (BookItemEntity bookItemEntity : list) {
					//设置参数
					setBatchAddBookitemParameter(pStatement, bookItemEntity);
					pStatement.addBatch();
				}
				if(pStatement!=null){
					pStatement.executeBatch();
					con.commit();
					pStatement.clearBatch();
					pStatement.close();
				}
			}
			con.setAutoCommit(defaultAutoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("bookitem counts:" + list.size() + ", time:" + (new Date().getTime() - date1.getTime()));
		return 0;
	}

	@Override
	public int deleteBookItem(BookItemEntity bookItemEntity) {
		return this.sqlSessionTemplate.delete("bookitem.deleteBookItem",bookItemEntity);
	}

	@Override
	public int updateBookItem(BookItemEntity bookItemEntity) {
		return this.sqlSessionTemplate.update("bookitem.updateBookItem",bookItemEntity);
	}

	
	@Override
	public int updateBookItemBatch(List<BookItemEntity> list) {
		Date date1 = new Date();
		try(Connection con = this.sqlSessionTemplate.getConfiguration().getEnvironment().getDataSource().getConnection()) {
			boolean defaultAutoCommit = con.getAutoCommit(); //保存当前默认的提交方式
			if(!con.isClosed()) {
				con.setAutoCommit(false);//关闭自动提交
				if (!con.getMetaData().supportsBatchUpdates()) {
					LogUtils.error("不支持批量数据提交");
					return 0;
				}
				PreparedStatement pStatement = con.prepareStatement(batchUpdBookitemSql());
				for (BookItemEntity bookItemEntity : list) {
					//设置参数
					setBatchUpdBookitemParameter(pStatement, bookItemEntity);
					pStatement.addBatch();
				}
				if(pStatement!=null){
					pStatement.executeBatch();
					con.commit();
					pStatement.clearBatch();
					pStatement.close();
				}
			}
			con.setAutoCommit(defaultAutoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("bookitem counts:" + list.size() + ", time:" + (new Date().getTime() - date1.getTime()));
		return 0;
	}


	@Override
	public BookItemEntity queryBookItem(BookItemEntity bookItemEntity) {
		return this.sqlSessionTemplate.selectOne("bookitem.queryBookItem",bookItemEntity);
	}

	@Override
	public List<BookItemEntity> queryBookItemList(BookItemEntity bookItemEntity) {
		return this.sqlSessionTemplate.selectList("bookitem.queryBookItemList",bookItemEntity);
	}

	@Override
	public BookItemPageEntity queryBookItemListByPage(
			BookItemPageEntity bookItemPageEntity) {
		BookItemPageEntity t = this.sqlSessionTemplate.selectOne("bookitem.queryBookItemListByPage",bookItemPageEntity);
		bookItemPageEntity.setTotal(t.getTotal());
		bookItemPageEntity.setDoAount(false);
		List<BookItemPageEntity> list = this.sqlSessionTemplate.selectList("bookitem.queryBookItemListByPage",bookItemPageEntity);
		bookItemPageEntity.setRows(list);
		return bookItemPageEntity;
	}
	
	
	@Override
	public List<BookItemEntity> queryAllBookitem(BookItemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("bookitem.select",bookitem);
	}

	@Override
	public BookItemEntity queryBookitemByCode(BookItemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("bookitem.selectByCode",bookitem);
	}
	
	@Override
	public BookUnionEntity queryAllBookitemAndBookInfoByCode(BookItemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("bookitem.queryAllBookitemAndBookInfoByCode",bookitem);
	}

	@Override
	public int updateBookitem(BookItemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("bookitem.update",bookitem);
	}

	@Override
	public int deleteBookitemById(List<BookItemEntity> list) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("bookitem.delete",list);
	}

	@Override
	public int addBookitem(BookItemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("bookitem.insertBookItem",bookitem);
	}

	@Override
	public int updateBookitemByCodeAndLibId(BookItemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("bookitem.updateBookitemByCodeAndLibId",bookitem);
	}
	
	@Override
	public List<BookItemEntity> queryAllBookitemByLibId(BookItemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("bookitem.queryAllBookitemByLibId",bookitem);
	}

	@Override
	public List<BookUnionEntity> queryBookitemAndBookInfo(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectList("bookitem.queryBookitemAndBookInfo",map);
	}

	@Override
	public BookUnionEntity queryBookitemAndBookInfobyIdx(Integer bookitemIdx) {
		return this.sqlSessionTemplate.selectOne("bookitem.queryBookitemAndBookInfobyIdx",bookitemIdx);
	}

	@Override
	public BookItemEntity queryBookItemByLibIdxAndBookBarcode(
			BookItemEntity bookItemEntity) {
		return this.sqlSessionTemplate.selectOne("bookitem.queryBookItemByLibIdxAndBookBarcode",bookItemEntity);
	}
	
	@Override
	public List<BookUnionEntity> queryAllBookitemAndBookInfo(BookItemEntity bookItemEntity) {
		return this.sqlSessionTemplate.selectList("bookitem.queryAllBookitemAndBookInfo",bookItemEntity);
	}

	@Override
	public List<Map<String, Object>> queryBookItemAndBibliosInfo(
			Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("bookitem.queryBookItemAndBibliosInfo", param);
	}
	
	/**
	 * 批量新增bookitem数据sql
	 *
	 * <p>2017年11月11日 下午5:43:15 
	 * <p>create by hjc
	 * @return
	 */
	private String batchAddBookitemSql(){
		String sql = "INSERT INTO bookitem ("
				+"  `lib_idx`,"
				+"  `nowlib_idx`,"
				+"  `serverlib_idx`,"
				+"  `book_barcode`,"
				+"  `book_uid`,"
				+"  `bib_idx`,"
				+"  `callNo`,"
				+"  `shelflayer_barcode`,"
				+"  `update_uid_flag`,"
				+"  `state`,"
				+"  `updatetime`,"
				+"  `statemodcount`,"
				+"  `location`,"
				+"  `nowlocation`,"
				+"  `device_idx`,"
				+"  `createtime`,"
				+"  `regdate`"
				+") "
				+"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return sql;
	}
	
	/**
	 * 填充bookitem参数
	 *
	 * <p>2017年11月11日 下午5:43:11 
	 * <p>create by hjc
	 * @param pStatement
	 * @param bookItemEntity
	 * @throws SQLException
	 */
	private void setBatchAddBookitemParameter(PreparedStatement pStatement, BookItemEntity bookItemEntity) throws SQLException{
		int count = 1;
		pStatement.setInt(count++, bookItemEntity.getLib_idx());
		pStatement.setInt(count++, bookItemEntity.getNowlib_idx());
		if (bookItemEntity.getServerlib_idx() == null) {
			pStatement.setNull(count++, Types.INTEGER);
		}else{
			pStatement.setInt(count++, bookItemEntity.getServerlib_idx());
		}
		pStatement.setString(count++, bookItemEntity.getBook_barcode());
		pStatement.setString(count++, bookItemEntity.getBook_uid());
		pStatement.setInt(count++, bookItemEntity.getBib_idx());
		pStatement.setString(count++, bookItemEntity.getCallNo());
		pStatement.setString(count++, bookItemEntity.getShelflayer_barcode());
		pStatement.setInt(count++, bookItemEntity.getUpdate_uid_flag());
		pStatement.setInt(count++, bookItemEntity.getState());
		pStatement.setString(count++, bookItemEntity.getUpdatetime()); //updatetime
		pStatement.setInt(count++, bookItemEntity.getStateModCount());
		pStatement.setString(count++, bookItemEntity.getLocation());
		pStatement.setString(count++, bookItemEntity.getNowlocation());
		if (bookItemEntity.getDevice_idx() == null) {
			pStatement.setNull(count++, Types.INTEGER);
		}else{
			pStatement.setInt(count++, bookItemEntity.getDevice_idx());
		}
		pStatement.setString(count++, sf.format(new Date())); //createtime
		pStatement.setString(count++, sf.format(new Date())); //regdate
	}
	
	/**
	 * 批量修改bookitem数据sql
	 *
	 * <p>2017年11月11日 下午5:43:15 
	 * <p>create by hjc
	 * @return
	 */
	private String batchUpdBookitemSql(){
		String sql = "UPDATE bookitem SET "
				+"  `lib_idx`=?,"
				+"  `nowlib_idx`=?,"
				+"  `serverlib_idx`=?,"
				+"  `book_barcode`=?,"
				+"  `book_uid`=?,"
//				+"  `bib_idx`=?,"
				+"  `callNo`=?,"
				+"  `shelflayer_barcode`=?,"
				+"  `update_uid_flag`=?,"
				+"  `state`=?,"
				+"  `updatetime`=?,"
				+"  `statemodcount`=?,"
				+"  `location`=?,"
				+"  `nowlocation`=?,"
				+"  `device_idx`=?"
				+" WHERE bookitem_idx = ?";
		return sql;
	}
	
	/**
	 * 填充bookitem参数
	 *
	 * <p>2017年11月11日 下午5:43:11 
	 * <p>create by hjc
	 * @param pStatement
	 * @param bookItemEntity
	 * @throws SQLException
	 */
	private void setBatchUpdBookitemParameter(PreparedStatement pStatement, BookItemEntity bookItemEntity) throws SQLException{
		int count = 1;
		pStatement.setInt(count++, bookItemEntity.getLib_idx());
		pStatement.setInt(count++, bookItemEntity.getNowlib_idx());
		if(bookItemEntity.getServerlib_idx() == null) {
			pStatement.setNull(count++, Types.INTEGER);
		}else{
			pStatement.setInt(count++, bookItemEntity.getServerlib_idx());
		}
		pStatement.setString(count++, bookItemEntity.getBook_barcode());
		pStatement.setString(count++, bookItemEntity.getBook_uid());
//		pStatement.setInt(count++, bookItemEntity.getBib_idx());
		pStatement.setString(count++, bookItemEntity.getCallNo());
		pStatement.setString(count++, bookItemEntity.getShelflayer_barcode());
		pStatement.setInt(count++, bookItemEntity.getUpdate_uid_flag());
		pStatement.setInt(count++, bookItemEntity.getState());
		pStatement.setString(count++, bookItemEntity.getUpdatetime()); //updatetime
		pStatement.setInt(count++, bookItemEntity.getStateModCount());
		pStatement.setString(count++, bookItemEntity.getLocation());
		pStatement.setString(count++, bookItemEntity.getNowlocation());
		if (bookItemEntity.getDevice_idx() == null) {
			pStatement.setNull(count++, Types.INTEGER);
		}else{
			pStatement.setInt(count++, bookItemEntity.getDevice_idx());
		}
		pStatement.setInt(count++, bookItemEntity.getBookitem_idx());
	}
	
	@Override
	public int insertBookItemNoUpdatetime(BookItemEntity bookItemEntity) {
		return this.sqlSessionTemplate.insert("bookitem.insertBookItemNoUpdatetime",bookItemEntity);
	}
	
	@Override
	public int updateBookItemNoUpdatetime(BookItemEntity bookItemEntity) {
		return this.sqlSessionTemplate.update("bookitem.updateBookItemNoUpdatetime",bookItemEntity);
	}


	@Override
	public List<BookUnionEntity> queryBookItemByUnion(BookUnionEntity bookUnionEntity) {
		return this.sqlSessionTemplate.selectList("bookitem.queryBookItemByUnion",bookUnionEntity);
	}
	
	@Override
	public BibliosAndBook insertBookItem2(BibliosAndBook bibliosAndBook) {
		this.sqlSessionTemplate.insert("bookitem.insertBookItem2",bibliosAndBook);
		return bibliosAndBook;
	}
	
	@Override
	public List<BookItemEntity> queryBookInfoForRecommend(BookItemEntity bookItemEntity) {
		return this.sqlSessionTemplate.selectList("bookitem.queryBookInfoForRecommend",bookItemEntity);
	}
	/**
	 * 更新馆藏图书状态
	 * @param bookItemEntity
	 * @return
	 */
	@Override
	public int updateState(BookItemEntity bookItemEntity) {
		return this.sqlSessionTemplate.update("bookitem.updateState",bookItemEntity);
	}
}
