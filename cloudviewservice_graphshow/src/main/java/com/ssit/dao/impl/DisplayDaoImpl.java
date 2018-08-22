package com.ssit.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.ssit.Entity.BookEntity;
import com.ssit.Entity.CertificateEntity;
import com.ssit.Entity.CirculateEntity;
import com.ssit.Entity.FinanceEntity;
import com.ssit.Entity.ReaderEntity;
import com.ssit.common.dao.BaseDaoImpl;
import com.ssit.dao.DisplayDao;

@Repository
public class DisplayDaoImpl extends BaseDaoImpl implements DisplayDao {

	@Override
	public List<CertificateEntity> countCertificateByParam(Map<String, String> param) throws DataAccessException {
		return this.selectAll("display.countCertificate", param);
	}

	@Override
	public List<CirculateEntity> countCirculateByParam(Map<String, String> param) throws DataAccessException {
		return this.selectAll("display.countCirculate", param);
	}

	@Override
	public List<FinanceEntity> countFinanceByParam(Map<String, String> param) throws DataAccessException {
		return this.selectAll("display.countFinance", param);
	}

	@Override
	public List<BookEntity> bookRankByParam(Map<String, String> param) throws DataAccessException {
		return this.selectAll("display.bookRank", param);
	}

	@Override
	public List<ReaderEntity> readerRankByParam(Map<String, String> param) throws DataAccessException {
		return this.selectAll("display.readerRank", param);
	}

}
