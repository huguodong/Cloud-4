package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.BookUnionEntity;

import java.util.List;

/**
 * Created by LXP on 2017/4/8.
 * 图书检索视图接口
 */

public interface BookItemViewI {
    enum FAIL_STATE{network_error,un_select_device}

    void setFail(FAIL_STATE state,long sync);

    /**
     * 设置获取数据成功数据
     * @param data
     */
    void setSuccessData(List<BookUnionEntity> data,long sync);

    /**
     * 设置搜索数据
     * @param data
     */
    void setSearchData(List<BookUnionEntity> data,long sync);

    void setSearchFail(FAIL_STATE state,long sync);
}
