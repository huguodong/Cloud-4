package com.ssit.service;

import com.ssit.common.entity.ResultEntity;

import java.util.Map;

public interface DisplayService {
    ResultEntity bookRank();

    ResultEntity bookCategory() throws Exception;

    ResultEntity bookCirculate();

    ResultEntity equipmentCount();

    ResultEntity libraryList();

    ResultEntity visitRecord();

    ResultEntity visitRecordToday();

    ResultEntity statistics();

    //修改数据
    void updateData();

    void updateCard();

    //凌晨时清除前一天数据
    void initData();

    // 单元测试用
    void updateData(int hour);
}
