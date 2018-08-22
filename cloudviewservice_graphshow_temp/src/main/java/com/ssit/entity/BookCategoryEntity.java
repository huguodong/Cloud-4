package com.ssit.entity;

public class BookCategoryEntity extends BaseEntity {
    private long id;
    private String name;            // 图书类别名称
    private long circulateTotal;    // 图书类别数量
    private double proportion;      // 图书类别比重

    public BookCategoryEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCirculateTotal() {
        return circulateTotal;
    }

    public void setCirculateTotal(long circulateTotal) {
        this.circulateTotal = circulateTotal;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }
}
