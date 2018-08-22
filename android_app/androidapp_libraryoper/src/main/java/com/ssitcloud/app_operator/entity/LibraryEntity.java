package com.ssitcloud.app_operator.entity;

import java.io.Serializable;

/**
 * Created by LXP on 2017/3/10.
 * 图书馆实体类
 */

public class LibraryEntity implements Serializable{

    private Integer library_idx;
    private String lib_name;
    private String lib_id;

    public Integer getLibrary_idx() {
        return library_idx;
    }

    public void setLibrary_idx(Integer library_idx) {
        this.library_idx = library_idx;
    }

    public String getLib_name() {
        return lib_name;
    }

    public void setLib_name(String lib_name) {
        this.lib_name = lib_name;
    }

    public String getLib_id() {
        return lib_id;
    }

    public void setLib_id(String lib_id) {
        this.lib_id = lib_id;
    }
}
