package com.ssitcloud.dblib.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.BibliosDao;
import com.ssitcloud.dblib.entity.BibliosAndBook;
import com.ssitcloud.dblib.entity.BibliosEntity;
import com.ssitcloud.dblib.entity.page.BibliosPageEntity;

@Repository
public class BibliosDaoImpl extends CommonDaoImpl implements BibliosDao{

	@Override
	public int insertBiblios(BibliosEntity bibliosEntity) {
		return this.sqlSessionTemplate.insert("biblios.insertBiblios",bibliosEntity);
	}
	
	@Override
	public int insertBibliosBatch(List<BibliosEntity> list) {
		Date date1 = new Date();
		try(Connection con = this.sqlSessionTemplate.getConfiguration().getEnvironment().getDataSource().getConnection()) {
			boolean defaultAutoCommit = con.getAutoCommit(); //保存当前默认的提交方式
			if(!con.isClosed()) {
				con.setAutoCommit(false);//关闭自动提交
				if (!con.getMetaData().supportsBatchUpdates()) {
					LogUtils.error("不支持批量数据提交");
					return 0;
				}
				PreparedStatement pStatement = con.prepareStatement(batchAddBibliosSql());
				for (BibliosEntity bibliosEntity : list) {
					//设置参数
					setBatchAddBibliosParameter(pStatement, bibliosEntity);
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
		System.out.println("counts:" + list.size() + ", time:" + (new Date().getTime() - date1.getTime()));
		return 0;
		
		
//		return this.sqlSessionTemplate.insert("biblios.insertBibliosBatch", list);
	}

	@Override
	public int deleteBiblios(BibliosEntity bibliosEntity) {
		return this.sqlSessionTemplate.delete("biblios.deleteBiblios",bibliosEntity);
	}

	@Override
	public int updateBiblios(BibliosEntity bibliosEntity) {
		return this.sqlSessionTemplate.update("biblios.updateBiblios",bibliosEntity);
	}
	
	@Override
	public int updateBibliosBatch(List<BibliosEntity> list) {
		Date date1 = new Date();
		PreparedStatement pStatement = null;
		try(Connection con = this.sqlSessionTemplate.getConfiguration().getEnvironment().getDataSource().getConnection()) {
			boolean defaultAutoCommit = con.getAutoCommit(); //保存当前默认的提交方式
			if(!con.isClosed()) {
				con.setAutoCommit(false);//关闭自动提交
				if (!con.getMetaData().supportsBatchUpdates()) {
					LogUtils.error("不支持批量数据提交");
					return 0;
				}
			
				String sql = batchUpdateBibliosSql();
				pStatement = con.prepareStatement(sql);
				for (BibliosEntity bibliosEntity : list) {
					setBatchUpdateBibliosParameter(pStatement, bibliosEntity);
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
		System.out.println("counts:" + list.size() + ", time:" + (new Date().getTime() - date1.getTime()));
		return 0;
//		return this.sqlSessionTemplate.update("biblios.updateBibliosBatch", list);
	}
	
	/**
	 * 构建新增书目的sql
	 * <p>2017年11月10日 下午4:33:34 
	 * <p>create by hjc
	 * @param bibliosEntity
	 * @return
	 */
	private String batchAddBibliosSql( ){
		String sql = "INSERT INTO biblios ("
					+"  `ISBN`,"
					+"  `title`,"
					+"  `author`,"
					+"  `classNo`,"
					+"  `publish`,"
					+"  `pubAddress`,"
					+"  `pubDate`,"
					+"  `price`,"
					+"  `subject`,"
					+"  `book_page`,"
					+"  `book_size`,"
					+"  `contents`,"
					+"  `seriesBook`,"
					+"  `lang`,"
					+"  `version`,"
					+"  `bookimage_url`"
					+") "
					+"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return sql;
	}
	
	/**
	 * 设置批量新增的参数
	 *
	 * <p>2017年11月10日 下午4:35:58 
	 * <p>create by hjc
	 * @param pStatement
	 * @param bibliosEntity
	 * @throws SQLException
	 * 				+"  `ISBN`,"
					+"  `title`,"
					+"  `author`,"
					+"  `classNo`,"
					+"  `publish`,"
					+"  `pubAddress`,"
					+"  `pubDate`,"
					+"  `price`,"
					+"  `subject`,"
					+"  `book_page`,"
					+"  `book_size`,"
					+"  `contents`,"
					+"  `seriesBook`,"
					+"  `lang`,"
					+"  `version`,"
					+"  `bookimage_url`"
	 */
	private void setBatchAddBibliosParameter(PreparedStatement pStatement, BibliosEntity bibliosEntity) throws SQLException  {
		int count = 1;
		pStatement.setString(count++, bibliosEntity.getISBN());
		pStatement.setString(count++, bibliosEntity.getTitle());
		pStatement.setString(count++, bibliosEntity.getAuthor());
		pStatement.setString(count++, bibliosEntity.getClassNo());
		pStatement.setString(count++, bibliosEntity.getPublish());
		pStatement.setString(count++, bibliosEntity.getPubAddress());
		pStatement.setString(count++, bibliosEntity.getPubDate());
		pStatement.setString(count++, bibliosEntity.getPrice());
		pStatement.setString(count++, bibliosEntity.getSubject());
		pStatement.setString(count++, bibliosEntity.getBook_page());
		pStatement.setString(count++, bibliosEntity.getBook_size());
		pStatement.setString(count++, bibliosEntity.getContents());
		pStatement.setString(count++, bibliosEntity.getSeriesBook());
		pStatement.setString(count++, bibliosEntity.getLang());
		pStatement.setString(count++, bibliosEntity.getVersion());
		pStatement.setString(count++, bibliosEntity.getBookimage_url());
	}
	/**
	 * 更新biblios的sql
	 *
	 * <p>2017年11月10日 下午4:33:34 
	 * <p>create by hjc
	 * @param bibliosEntity
	 * @return
	 */
	private String batchUpdateBibliosSql(){
		String sql = "UPDATE biblios set";
		sql += "  `ISBN`=?,";
		sql += "  `title`=?,";
		sql += "  `author`=?,";
		sql += "  `classNo`=?,";
		sql += "  `publish`=?,";
		sql += "  `pubAddress` = ?,";
		sql += "  `pubDate` = ?,";
		sql += "  `price` = ?,";
		sql += "  `subject` = ?,";
		sql += "  `book_page` = ?,";
		sql += "  `book_size` = ?,";
		sql += "  `contents` = ?,";
		sql += "  `seriesBook` = ?,";
		sql += "  `lang` = ?,";
		sql += "  `version` = ?,";
		sql += "  `bookimage_url` = ?";
		sql += " where `bib_idx` = ?";
		return sql;
	}
	
	/**
	 * 设置批量更新的参数
	 *
	 * <p>2017年11月10日 下午4:35:58 
	 * <p>create by hjc
	 * @param pStatement
	 * @param bibliosEntity
	 * @throws SQLException
	 */
	private void setBatchUpdateBibliosParameter(PreparedStatement pStatement, BibliosEntity bibliosEntity) throws SQLException {
		int count = 1;
		pStatement.setString(count++, bibliosEntity.getISBN());
		pStatement.setString(count++, bibliosEntity.getTitle());
		pStatement.setString(count++, bibliosEntity.getAuthor());
		pStatement.setString(count++, bibliosEntity.getClassNo());
		pStatement.setString(count++, bibliosEntity.getPublish());
		pStatement.setString(count++, bibliosEntity.getPubAddress());
		pStatement.setString(count++, bibliosEntity.getPubDate());
		pStatement.setString(count++, bibliosEntity.getPrice());
		pStatement.setString(count++, bibliosEntity.getSubject());
		pStatement.setString(count++, bibliosEntity.getBook_page());
		pStatement.setString(count++, bibliosEntity.getBook_size());
		pStatement.setString(count++, bibliosEntity.getContents());
		pStatement.setString(count++, bibliosEntity.getSeriesBook());
		pStatement.setString(count++, bibliosEntity.getLang());
		pStatement.setString(count++, bibliosEntity.getVersion());
		pStatement.setString(count++, bibliosEntity.getBookimage_url());
		pStatement.setInt(count++, bibliosEntity.getBib_idx());//查询条件
		
	}

	@Override
	public BibliosEntity queryBiblios(BibliosEntity bibliosEntity) {
		return this.sqlSessionTemplate.selectOne("biblios.queryBiblios",bibliosEntity);
	}

	@Override
	public List<BibliosEntity> queryBibliosList(BibliosEntity bibliosEntity) {
		return this.sqlSessionTemplate.selectList("biblios.queryBibliosList",bibliosEntity);
	}

	@Override
	public BibliosPageEntity queryBibliosListByPage(
			BibliosPageEntity bibliosPageEntity) {
		BibliosPageEntity t = this.sqlSessionTemplate.selectOne("biblios.queryBibliosListByPage",bibliosPageEntity);
		bibliosPageEntity.setTotal(t.getTotal());
		bibliosPageEntity.setDoAount(false);
		List<BibliosPageEntity> list = this.sqlSessionTemplate.selectList("biblios.queryBibliosListByPage",bibliosPageEntity);
		bibliosPageEntity.setRows(list);
		return bibliosPageEntity;
	}
	
	
	@Override
	public List<BibliosEntity> queryAllBiblios() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("biblios.queryBibliosList");
	}

	@Override
	public BibliosEntity queryBibliosByISBN(BibliosEntity biblios) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("biblios.selectbyISBN", biblios);
	}
	@Override
	public BibliosEntity queryBibliosByTitleAndAuthor(BibliosEntity biblios) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("biblios.queryBibliosByTitleAndAuthor", biblios);
	}

	@Override
	public int updateBibliosByISBN(BibliosEntity biblios) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("biblios.updateBibliosByISBN",biblios);
	}

	@Override
	public int deleteBibliosById(List<BibliosEntity> list) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("biblios.deleteByISBN",list);
	}
	
	@Override
	public int addBiblios(BibliosEntity biblios) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("biblios.insertBiblios",biblios);
	}

	@Override
	public BibliosEntity queryBibliosForBCAndLib(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectOne("biblios.queryBibliosForBCAndLib", param);
	}

	@Override
	public BibliosEntity queryBibliosByIsbnMultiCondition(BibliosEntity biblios) {
		return this.sqlSessionTemplate.selectOne("biblios.queryBibliosByIsbnMultiCondition", biblios);
	}

	@Override
	public BibliosEntity queryBibliosByTitleAuthorPublish(BibliosEntity biblios) {
		return this.sqlSessionTemplate.selectOne("biblios.queryBibliosByTitleAuthorPublish", biblios);
	}
	
	
	@Override
	public BibliosAndBook insertBiblios2(BibliosAndBook bibliosEntity) {
		this.sqlSessionTemplate.insert("biblios.insertBiblios2",bibliosEntity);
		return bibliosEntity;
	}
}
