package com.ssitcloud.mobileserver.entity;

/**
 * Created by LXP on 2017/7/21.
 */

public class BookInfo {
    public enum BookState{LOAN,AVALIABLE,RESERVE};
    private String recno;
    private String barcode;
    private String title;
    private String author;
    private String page;
    private String callno;
    private String publish;
    private BookState state = BookState.LOAN;

    public String getRecno() {
        return recno;
    }

    public void setRecno(String recno) {
        this.recno = recno;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getCallno() {
        return callno;
    }

    public void setCallno(String callno) {
        this.callno = callno;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "recno='" + recno + '\'' +
                ", barcode='" + barcode + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", page='" + page + '\'' +
                ", callno='" + callno + '\'' +
                ", publish='" + publish + '\'' +
                ", state=" + state +
                '}';
    }
}
