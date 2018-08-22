package com.ssit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ssit.Entity.BookEntity;
import com.ssit.Entity.CertificateEntity;
import com.ssit.Entity.CirculateEntity;
import com.ssit.Entity.FinanceEntity;
import com.ssit.Entity.ReaderEntity;


public interface DisplayDao {
	// 办证
	public List<CertificateEntity> countCertificateByParam(Map<String, String> param) throws DataAccessException;

	// 流通（借、还、续借）
	public List<CirculateEntity> countCirculateByParam(Map<String, String> param) throws DataAccessException;

	// 财经
	public List<FinanceEntity> countFinanceByParam(Map<String, String> param) throws DataAccessException;

	//查询图书借阅排行榜
	public List<BookEntity> bookRankByParam(Map<String, String> param) throws DataAccessException;

	//查询读者借阅排行榜
	public List<ReaderEntity> readerRankByParam(Map<String, String> param) throws DataAccessException;
}
