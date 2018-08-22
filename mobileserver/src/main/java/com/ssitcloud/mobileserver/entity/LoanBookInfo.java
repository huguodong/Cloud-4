package com.ssitcloud.mobileserver.entity;

import java.util.Date;

/**
 * Created by LXP on 2017/7/24.
 */

public class LoanBookInfo extends BookInfo{
    private Date loandate;
    private Date returndate;

    public Date getLoandate() {
        return loandate;
    }

    public void setLoandate(Date loandate) {
        this.loandate = loandate;
    }

    public Date getReturndate() {
        return returndate;
    }

    public void setReturndate(Date returndate) {
        this.returndate = returndate;
    }

    @Override
    public String toString() {
        return "LoanBookInfo{{" +
                "loandate=" + loandate +
                ", returndate=" + returndate +
                "},"+super.toString()+'}';
    }
}
