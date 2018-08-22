package com.ssit.entity;

public class VisitRecordTodayEntity extends BaseEntity {
    private long id;
    private long inTotal;       // 进馆人数
    private long outTotal;      // 出馆人数
    private String time;        // 时间点

    public VisitRecordTodayEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInTotal() {
        return inTotal;
    }

    public void setInTotal(long inTotal) {
        this.inTotal = inTotal;
    }

    public long getOutTotal() {
        return outTotal;
    }

    public void setOutTotal(long outTotal) {
        this.outTotal = outTotal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
