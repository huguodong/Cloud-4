package com.ssitcloud.mobileserver.entity;

import java.util.Date;

/**
 * Created by LXP on 2017/8/7.
 */

public class ReserveBookInfo extends BookInfo{
    private Date regDate;//预借时间
    private Date endDate;//预借截止日期
    private String localCode;//投递地区代码

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    @Override
    public String toString() {
        return "ReserveBookInfo{" +
                "regDate=" + regDate +
                ", endDate=" + endDate +
                ", localCode='" + localCode + '\'' +
                "},"+super.toString()+'}';
    }
}
