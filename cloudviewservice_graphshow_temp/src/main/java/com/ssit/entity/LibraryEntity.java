package com.ssit.entity;

public class LibraryEntity extends BaseEntity {
    private long id;
    private String name;    // 图书馆名称

    public LibraryEntity() {
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
}
