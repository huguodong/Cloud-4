package com.ssit.dao;

import java.util.List;

import com.ssit.entity.*;

public interface DisplayDao {
	//查询图书借阅排行榜
	List<BookEntity> bookRank();

	//查询图书类别数量和比重（流通分类）
	List<BookCategoryEntity> bookCategory();

	//查询当天文献借还量
	List<BookCirculateEntity> bookCirculate(int num);

	//查询设备接入总数
	EquipmentEntity equipmentCount();

	//查询图书馆列表
	List<LibraryEntity> libraryList();

	//查询到馆人数统计
	VisitRecordEntity visitRecord();

	//查询当天人流量
	List<VisitRecordTodayEntity> VisitRecordTodayList(int num);

	//查询当天/当月/全年统计
	StatisticsEntity statistics();

	// 每一段时间写入数据
	void updateStatistics(StatisticsEntity statistics);
	void updateStatisticsCard(StatisticsEntity statistics);
	void updateBookCirculate(BookCirculateEntity bookCirculate);
	void updateBookCategory(BookCategoryEntity bookCategory);
	void updateVisitRecord(VisitRecordEntity visitRecord);
	void updateVisitRecordToday(VisitRecordTodayEntity visitRecordToday);

	// 每天00:00初始化数据
	void initBookCirculate();
	void initStatistics();
	void initVisitRecord();
	void initVisitRecordToday();
}
