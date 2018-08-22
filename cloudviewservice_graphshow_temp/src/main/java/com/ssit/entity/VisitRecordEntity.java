package com.ssit.entity;

public class VisitRecordEntity extends BaseEntity {
    private long todayTotal;    // 当日到馆数量
    private long monthTotal;    // 当月到馆数量
    private long yearTotal;     // 全年到馆数量

    public VisitRecordEntity() {
    }

    public long getTodayTotal() {
        return todayTotal;
    }

    public void setTodayTotal(long todayTotal) {
        this.todayTotal = todayTotal;
    }

    public long getMonthTotal() {
        return monthTotal;
    }

    public void setMonthTotal(long monthTotal) {
        this.monthTotal = monthTotal;
    }

    public long getYearTotal() {
        return yearTotal;
    }

    public void setYearTotal(long yearTotal) {
        this.yearTotal = yearTotal;
    }
}
