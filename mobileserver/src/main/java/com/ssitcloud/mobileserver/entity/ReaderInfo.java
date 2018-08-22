package com.ssitcloud.mobileserver.entity;

import java.util.Date;

/**
 * Created by LXP on 2017/7/20.
 */

public class ReaderInfo {
    private String name;
    private String cardNo;
    private String phone;
    private Integer sex;//0男 1女
    private Date barth;
    private String idCard;
    private Double deposit;//押金
    private Double prefine;//预付款
    private Double overDue;//欠款
    private Integer maxloannum;
    private Integer surplusnum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBarth() {
        return barth;
    }

    public void setBarth(Date barth) {
        this.barth = barth;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getPrefine() {
        return prefine;
    }

    public void setPrefine(Double prefine) {
        this.prefine = prefine;
    }

    public Double getOverDue() {
        return overDue;
    }

    public void setOverDue(Double overDue) {
        this.overDue = overDue;
    }

    public Integer getMaxloannum() {
        return maxloannum;
    }

    public void setMaxloannum(Integer maxloannum) {
        this.maxloannum = maxloannum;
    }

    public Integer getSurplusnum() {
        return surplusnum;
    }

    public void setSurplusnum(Integer surplusnum) {
        this.surplusnum = surplusnum;
    }

    @Override
    public String toString() {
        return "ReaderInfo{" +
                "name='" + name + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", barth=" + barth +
                ", idCard='" + idCard + '\'' +
                ", deposit=" + deposit +
                ", prefine=" + prefine +
                ", overDue=" + overDue +
                ", maxloannum=" + maxloannum +
                ", surplusnum=" + surplusnum +
                '}';
    }
}
