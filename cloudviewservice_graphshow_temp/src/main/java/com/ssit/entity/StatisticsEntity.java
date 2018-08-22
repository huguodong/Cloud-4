package com.ssit.entity;

public class StatisticsEntity extends BaseEntity {
    private long cardTodayTotal;    // 当日办证数量
    private long cardMonthTotal;    // 当月办证数量
    private long cardYearTotal;     // 全年办证数量
    private long bookBorrowTodayTotal;  // 当日借书数量
    private long bookBorrowMonthTotal;  // 当月借书数量
    private long bookBorrowYearTotal;   // 全年借书数量
    private long bookReturnTodayTotal;  // 当日还书数量
    private long bookReturnMonthTotal;  // 当月还书数量
    private long bookReturnYearTotal;   // 全年还书数量

    public StatisticsEntity() {
    }

    public long getCardTodayTotal() {
        return cardTodayTotal;
    }

    public void setCardTodayTotal(long cardTodayTotal) {
        this.cardTodayTotal = cardTodayTotal;
    }

    public long getCardMonthTotal() {
        return cardMonthTotal;
    }

    public void setCardMonthTotal(long cardMonthTotal) {
        this.cardMonthTotal = cardMonthTotal;
    }

    public long getCardYearTotal() {
        return cardYearTotal;
    }

    public void setCardYearTotal(long cardYearTotal) {
        this.cardYearTotal = cardYearTotal;
    }

    public long getBookBorrowTodayTotal() {
        return bookBorrowTodayTotal;
    }

    public void setBookBorrowTodayTotal(long bookBorrowTodayTotal) {
        this.bookBorrowTodayTotal = bookBorrowTodayTotal;
    }

    public long getBookBorrowMonthTotal() {
        return bookBorrowMonthTotal;
    }

    public void setBookBorrowMonthTotal(long bookBorrowMonthTotal) {
        this.bookBorrowMonthTotal = bookBorrowMonthTotal;
    }

    public long getBookBorrowYearTotal() {
        return bookBorrowYearTotal;
    }

    public void setBookBorrowYearTotal(long bookBorrowYearTotal) {
        this.bookBorrowYearTotal = bookBorrowYearTotal;
    }

    public long getBookReturnTodayTotal() {
        return bookReturnTodayTotal;
    }

    public void setBookReturnTodayTotal(long bookReturnTodayTotal) {
        this.bookReturnTodayTotal = bookReturnTodayTotal;
    }

    public long getBookReturnMonthTotal() {
        return bookReturnMonthTotal;
    }

    public void setBookReturnMonthTotal(long bookReturnMonthTotal) {
        this.bookReturnMonthTotal = bookReturnMonthTotal;
    }

    public long getBookReturnYearTotal() {
        return bookReturnYearTotal;
    }

    public void setBookReturnYearTotal(long bookReturnYearTotal) {
        this.bookReturnYearTotal = bookReturnYearTotal;
    }
}
