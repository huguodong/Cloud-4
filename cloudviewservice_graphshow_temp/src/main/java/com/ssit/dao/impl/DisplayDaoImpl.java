package com.ssit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssit.entity.*;
import org.springframework.stereotype.Repository;

import com.ssit.common.dao.BaseDaoImpl;
import com.ssit.dao.DisplayDao;

@Repository
public class DisplayDaoImpl extends BaseDaoImpl implements DisplayDao {

	private static Map<String,Object> param = new HashMap<>();

	@Override
	public List<BookEntity> bookRank() {
		return this.selectAll("display.bookRank", param);
	}

	@Override
	public List<BookCategoryEntity> bookCategory() {
		return this.selectAll("display.bookCategory", param);
	}

	@Override
	public List<BookCirculateEntity> bookCirculate(int num) {
		param = new HashMap<>();
		param.put("num", num);
		return this.selectAll("display.bookCirculate", param);
	}

	@Override
	public EquipmentEntity equipmentCount() {
		return this.select("display.equipmentCount", null);
	}

	@Override
	public List<LibraryEntity> libraryList() {
		return this.selectAll("display.libraryList", param);
	}

	@Override
	public VisitRecordEntity visitRecord() {
		return this.select("display.visitRecord", null);
	}

	@Override
	public List<VisitRecordTodayEntity> VisitRecordTodayList(int num) {
		param = new HashMap<>();
		param.put("num", num);
		return this.selectAll("display.visitRecordTodayList", param);
	}

	@Override
	public StatisticsEntity statistics() {
		return this.select("display.statistics", null);
	}

	@Override
	public void updateStatistics(StatisticsEntity statisticsEntity) {
		this.update("display.updateStatistics", statisticsEntity);
	}

	@Override
	public void updateStatisticsCard(StatisticsEntity statistics) {
		this.update("display.updateStatisticsCard", statistics);
	}

	@Override
	public void updateBookCirculate(BookCirculateEntity bookCirculate) {
		this.update("display.updateBookCirculate", bookCirculate);
	}

	@Override
	public void updateBookCategory(BookCategoryEntity bookCategory) {
		this.update("display.updateBookCategory", bookCategory);
	}

	@Override
	public void updateVisitRecord(VisitRecordEntity visitRecord) {
		this.update("display.updateVisitRecord", visitRecord);
	}

	@Override
	public void updateVisitRecordToday(VisitRecordTodayEntity visitRecordToday) {
		this.update("display.updateVisitRecordToday", visitRecordToday);
	}

	@Override
	public void initBookCirculate() {
		this.update("display.initBookCirculate", param);
	}

	@Override
	public void initStatistics() {
		this.update("display.initStatistics", param);
	}

	@Override
	public void initVisitRecord() {
		this.update("display.initVisitRecord", param);
	}

	@Override
	public void initVisitRecordToday() {
		this.update("display.initVisitRecordToday", param);
	}

}
