package com.ssit.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import com.ssit.entity.*;

import com.ssit.util.*;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ssit.common.entity.ResultEntity;
import com.ssit.dao.DisplayDao;
import com.ssit.service.DisplayService;
import org.springframework.transaction.annotation.Transactional;

@Service("displayService")
public class DisplayServiceImpl implements DisplayService {
    private static Logger logger = LoggerFactory.getLogger(DisplayServiceImpl.class);

    @Resource
    private DisplayDao displayDao;

    @Override
    public ResultEntity bookRank() {
        ResultEntity resultEntity = new ResultEntity();
        try {
            List<BookEntity> bookList = displayDao.bookRank();
            resultEntity.setState(true);
            resultEntity.setResult(bookList);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setState(false);
        }
        return resultEntity;
    }

    @Override
    public ResultEntity bookCategory() {
        ResultEntity resultEntity = new ResultEntity();
        try {
            List<BookCategoryEntity> bookCategoryList = displayDao.bookCategory();
            resultEntity.setState(true);
            resultEntity.setResult(bookCategoryList);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setState(false);
        }
        return resultEntity;
    }

    @Override
    public ResultEntity bookCirculate() {
        ResultEntity resultEntity = new ResultEntity();
        try {
            int num = DateUtil.getCurHour();
            List<BookCirculateEntity> bookCategoryList = displayDao.bookCirculate(num < 1 ? 1 : (num + 1));
            if (!bookCategoryList.isEmpty()) {
                int min = DateUtil.getCurMin();
                BigDecimal b1 = new BigDecimal(min);
                BigDecimal b2 = new BigDecimal(60);
                double pro = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                BookCirculateEntity bookCirculate = bookCategoryList.get(bookCategoryList.size() - 1);
                bookCirculate.setContinueTotal((long) (bookCirculate.getContinueTotal() * pro));
                bookCirculate.setReturnTotal((long) (bookCirculate.getReturnTotal() * pro));
                bookCirculate.setBorrowTotal((long) (bookCirculate.getBorrowTotal() * pro));
            }
            resultEntity.setState(true);
            resultEntity.setResult(bookCategoryList);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setState(false);
        }
        return resultEntity;
    }

    @Override
    public ResultEntity equipmentCount() {
        ResultEntity resultEntity = new ResultEntity();
        try {
            EquipmentEntity equipment = displayDao.equipmentCount();
            resultEntity.setState(true);
            resultEntity.setResult(equipment.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setState(false);
        }
        return resultEntity;
    }

    @Override
    public ResultEntity libraryList() {
        ResultEntity resultEntity = new ResultEntity();
        try {
            List<LibraryEntity> libraryList = displayDao.libraryList();
            resultEntity.setState(true);
            resultEntity.setResult(libraryList);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setState(false);
        }
        return resultEntity;
    }

    @Override
    public ResultEntity visitRecord() {
        ResultEntity resultEntity = new ResultEntity();
        try {
            VisitRecordEntity visitRecord = displayDao.visitRecord();
            resultEntity.setState(true);
            resultEntity.setResult(visitRecord);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setState(false);
        }
        return resultEntity;
    }

    @Override
    public ResultEntity visitRecordToday() {
        ResultEntity resultEntity = new ResultEntity();
        try {
            int num = DateUtil.getCurHour();
            List<VisitRecordTodayEntity> visitRecordTodayList = displayDao.VisitRecordTodayList(num < 1 ? 1 : (num + 1));
            if (!visitRecordTodayList.isEmpty()) {
                int min = DateUtil.getCurMin();
                BigDecimal b1 = new BigDecimal(min);
                BigDecimal b2 = new BigDecimal(60);
                double pro = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                VisitRecordTodayEntity visitRecordToday = visitRecordTodayList.get(visitRecordTodayList.size() - 1);
                visitRecordToday.setOutTotal((long) (visitRecordToday.getInTotal() * pro));
                visitRecordToday.setId((long) (visitRecordToday.getOutTotal() * pro));
            }
            resultEntity.setState(true);
            resultEntity.setResult(visitRecordTodayList);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setState(false);
        }
        return resultEntity;
    }

    @Override
    public ResultEntity statistics() {
        ResultEntity resultEntity = new ResultEntity();
        try {
            StatisticsEntity statistics = displayDao.statistics();
            resultEntity.setState(true);
            resultEntity.setResult(statistics);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setState(false);
        }
        return resultEntity;
    }

    @Override
    @Transactional
    public void updateData() {
        try {
            Random random = new Random();
            int hour = DateUtil.getCurHour() + 1;
            if (hour > 23) {
                hour = 0;
            }
            int id = hour + 1;
            if (id > 24) {
                id = 1;
            }
            // 借书
            int borrowNum = random.nextInt(ShowDataUtils.bookBorrow.get(hour)) + ShowDataUtils.bookBorrowBase.get(hour);
            // 还书
            int returnNum = random.nextInt(ShowDataUtils.bookReturn.get(hour)) + ShowDataUtils.bookReturnBase.get(hour);
            // 续借
            int continueNum = random.nextInt(ShowDataUtils.bookContinue.get(hour)) + ShowDataUtils.bookContinueBase.get(hour);
            // 新增的图书数据都加入statistics
            StatisticsEntity statistics = new StatisticsEntity();
            statistics.setBookBorrowTodayTotal(borrowNum);
            statistics.setBookReturnTodayTotal(returnNum);
            displayDao.updateStatistics(statistics);

            // 新增的图书数据加入book_circulate
            BookCirculateEntity bookCirculate = new BookCirculateEntity();
            bookCirculate.setBorrowTotal(borrowNum);
            bookCirculate.setReturnTotal(returnNum);
            bookCirculate.setContinueTotal(continueNum);
            bookCirculate.setId(id);
            displayDao.updateBookCirculate(bookCirculate);

            // 新增的图书记录，随机加入book_category，并重新计算百分比，保留一位小数
            int bookTatal = borrowNum + returnNum + continueNum;
            if (bookTatal > 0) {
                List<BookCategoryEntity> bookCategoryList = displayDao.bookCategory();
                List<BookCategoryEntity> bookCategoryListNew = new ArrayList<>();
                int categoryBook = 0;
                for (int i = 0; i < bookCategoryList.size(); i++) {
                    categoryBook += bookCategoryList.get(i).getCirculateTotal();
                    long categoryNum = bookCategoryList.get(i).getCirculateTotal();
                    boolean isAll = i == bookCategoryList.size() - 1 ? true : false;
                    if (bookTatal <= 1) {
                        isAll = true;
                    }
                    BookCategoryEntity temp = ShowDataUtils.randomBookCategory(bookCategoryList.get(i), bookTatal, isAll);
                    bookTatal -= temp.getCirculateTotal();
//                    temp.setCirculateTotal(bookCategoryList.get(i).getCirculateTotal());
                    BookCategoryEntity bookCategoryNew = new BookCategoryEntity();
                    bookCategoryNew.setCirculateTotal(temp.getCirculateTotal());
                    bookCategoryNew.setProportion(temp.getProportion());
                    bookCategoryNew.setId(temp.getId());
                    bookCategoryListNew.add(bookCategoryNew);
                    bookCategoryList.get(i).setCirculateTotal(categoryNum);
                }

                for (int i = 0; i < bookCategoryListNew.size(); i++) {
                    categoryBook += bookCategoryListNew.get(i).getCirculateTotal();
                }

                for (int i = 0; i < bookCategoryListNew.size(); i++) {
                    BigDecimal b1 = new BigDecimal(bookCategoryListNew.get(i).getCirculateTotal() + bookCategoryList.get(i).getCirculateTotal());
                    BigDecimal b2 = new BigDecimal(categoryBook);
                    bookCategoryListNew.get(i).setProportion(b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).doubleValue() * 100);
                    bookCategoryListNew.get(i).setCirculateTotal(bookCategoryListNew.get(i).getCirculateTotal() + bookCategoryList.get(i).getCirculateTotal());
                    displayDao.updateBookCategory(bookCategoryListNew.get(i));
                }
            }
            // 进馆
            int inNum = random.nextInt(ShowDataUtils.visitIn.get(hour)) + ShowDataUtils.visitInBase.get(hour);
            // 出馆
            int outNum = random.nextInt(ShowDataUtils.visitOut.get(hour)) + ShowDataUtils.visitOutBase.get(hour);
            // 进出馆数据写入visit_record
            VisitRecordEntity visitRecord = new VisitRecordEntity();
            visitRecord.setTodayTotal(inNum);
            displayDao.updateVisitRecord(visitRecord);
            // 进出馆数据写入visit_record_today
            VisitRecordTodayEntity visitRecordToday = new VisitRecordTodayEntity();
            visitRecordToday.setInTotal(inNum);
            visitRecordToday.setOutTotal(outNum);
            visitRecordToday.setId(id);
            displayDao.updateVisitRecordToday(visitRecordToday);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void updateCard() {
        // 办证，一天50个左右 单独写，早上八点到下午五点每半小时增加3个左右
        int cardNum = ShowDataUtils.card() + 1;
        StatisticsEntity statistics = new StatisticsEntity();
        statistics.setCardTodayTotal(cardNum);
        displayDao.updateStatisticsCard(statistics);
    }

    @Override
    @Transactional
    public void initData() {
        try {
            displayDao.initBookCirculate();
            displayDao.initStatistics();
            displayDao.initVisitRecord();
            displayDao.initVisitRecordToday();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateData(int hour) {
        try {
            hour += 1;
            if (hour > 23) {
                hour = 0;
            }
            Random random = new Random();
            int id = hour + 1;
            if (id > 24) {
                id = 1;
            }
            // 借书
            int borrowNum = random.nextInt(ShowDataUtils.bookBorrow.get(hour)) + ShowDataUtils.bookBorrowBase.get(hour);
            // 还书
            int returnNum = random.nextInt(ShowDataUtils.bookReturn.get(hour)) + ShowDataUtils.bookReturnBase.get(hour);
            // 续借
            int continueNum = random.nextInt(ShowDataUtils.bookContinue.get(hour)) + ShowDataUtils.bookContinueBase.get(hour);
            // 新增的图书数据都加入statistics
            StatisticsEntity statistics = new StatisticsEntity();
            statistics.setBookBorrowTodayTotal(borrowNum);
            statistics.setBookReturnTodayTotal(returnNum);
            displayDao.updateStatistics(statistics);

            // 新增的图书数据加入book_circulate
            BookCirculateEntity bookCirculate = new BookCirculateEntity();
            bookCirculate.setBorrowTotal(borrowNum);
            bookCirculate.setReturnTotal(returnNum);
            bookCirculate.setContinueTotal(continueNum);
            bookCirculate.setId(id);
            displayDao.updateBookCirculate(bookCirculate);

            // 新增的图书记录，随机加入book_category，并重新计算百分比，保留一位小数
            int bookTatal = borrowNum + returnNum + continueNum;
            if (bookTatal > 0) {
                List<BookCategoryEntity> bookCategoryList = displayDao.bookCategory();
                List<BookCategoryEntity> bookCategoryListNew = new ArrayList<>();
                int categoryBook = 0;
                for (int i = 0; i < bookCategoryList.size(); i++) {
                    categoryBook += bookCategoryList.get(i).getCirculateTotal();
                    long categoryNum = bookCategoryList.get(i).getCirculateTotal();
                    boolean isAll = i == bookCategoryList.size() - 1 ? true : false;
                    if (bookTatal <= 1) {
                        isAll = true;
                    }
                    BookCategoryEntity temp = ShowDataUtils.randomBookCategory(bookCategoryList.get(i), bookTatal, isAll);
                    bookTatal -= temp.getCirculateTotal();
//                    temp.setCirculateTotal(bookCategoryList.get(i).getCirculateTotal());
                    BookCategoryEntity bookCategoryNew = new BookCategoryEntity();
                    bookCategoryNew.setCirculateTotal(temp.getCirculateTotal());
                    bookCategoryNew.setProportion(temp.getProportion());
                    bookCategoryNew.setId(temp.getId());
                    bookCategoryListNew.add(bookCategoryNew);
                    bookCategoryList.get(i).setCirculateTotal(categoryNum);
                }

                for (int i = 0; i < bookCategoryListNew.size(); i++) {
                    categoryBook += bookCategoryListNew.get(i).getCirculateTotal();
                }

                for (int i = 0; i < bookCategoryListNew.size(); i++) {
                    BigDecimal b1 = new BigDecimal(bookCategoryListNew.get(i).getCirculateTotal() + bookCategoryList.get(i).getCirculateTotal());
                    BigDecimal b2 = new BigDecimal(categoryBook);
                    bookCategoryListNew.get(i).setProportion(b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).doubleValue() * 100);
                    bookCategoryListNew.get(i).setCirculateTotal(bookCategoryListNew.get(i).getCirculateTotal() + bookCategoryList.get(i).getCirculateTotal());
                    displayDao.updateBookCategory(bookCategoryListNew.get(i));
                }
            }
            // 进馆
            int inNum = random.nextInt(ShowDataUtils.visitIn.get(hour)) + ShowDataUtils.visitInBase.get(hour);
            // 出馆
            int outNum = random.nextInt(ShowDataUtils.visitOut.get(hour)) + ShowDataUtils.visitOutBase.get(hour);
            // 进出馆数据写入visit_record
            VisitRecordEntity visitRecord = new VisitRecordEntity();
            visitRecord.setTodayTotal(inNum);
            displayDao.updateVisitRecord(visitRecord);
            // 进出馆数据写入visit_record_today
            VisitRecordTodayEntity visitRecordToday = new VisitRecordTodayEntity();
            visitRecordToday.setInTotal(inNum);
            visitRecordToday.setOutTotal(outNum);
            visitRecordToday.setId(id);
            displayDao.updateVisitRecordToday(visitRecordToday);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}