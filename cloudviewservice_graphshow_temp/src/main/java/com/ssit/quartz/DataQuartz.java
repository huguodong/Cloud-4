package com.ssit.quartz;

import com.ssit.service.DisplayService;
import com.ssit.util.SpringContextUtil;

public class DataQuartz {
    DisplayService displayService = (DisplayService) SpringContextUtil.getBean("displayService");

    public void updateDataBy1(){
        displayService.updateData();
    }

    public void updateDataBy30(){
        displayService.updateData();
    }

    public void updateCard(){
        displayService.updateCard();
    }

    public void initData(){
        displayService.initData();
    }
}
