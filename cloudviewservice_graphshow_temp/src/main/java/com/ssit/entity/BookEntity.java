package com.ssit.entity;

public class BookEntity extends BaseEntity {
    private String name;       // 书名
    private String coverImg;   // 封面

    public BookEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }
}
