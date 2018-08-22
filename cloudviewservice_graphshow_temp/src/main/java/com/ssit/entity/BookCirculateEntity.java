package com.ssit.entity;

public class BookCirculateEntity extends BaseEntity {
    private long id;
    private long borrowTotal;       // 借书数量
    private long returnTotal;       // 还书数量
    private long continueTotal;     // 续借数量
    private String time;              // 时间点

    public BookCirculateEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBorrowTotal() {
        return borrowTotal;
    }

    public void setBorrowTotal(long borrowTotal) {
        this.borrowTotal = borrowTotal;
    }

    public long getReturnTotal() {
        return returnTotal;
    }

    public void setReturnTotal(long returnTotal) {
        this.returnTotal = returnTotal;
    }

    public long getContinueTotal() {
        return continueTotal;
    }

    public void setContinueTotal(long continueTotal) {
        this.continueTotal = continueTotal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
