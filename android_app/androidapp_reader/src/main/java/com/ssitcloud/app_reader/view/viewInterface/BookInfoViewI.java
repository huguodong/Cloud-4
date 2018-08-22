package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.ReservationMessage;

/**
 *
 * Created by LXP on 2017/4/27.
 */

public interface BookInfoViewI {
    enum BookInfo_State{NETOWRK_ERROR,AUTH_ERROR,SUCCESS,FAIL};

    /**
     * 设置预借状态
     * @param message
     */
    void setReservationResult(ReservationMessage message);

    /**
     * 设置取消预借状态
     * @param message
     */
    void setInReservationResult(ReservationMessage message);

    void setFail(BookInfo_State s);

    /**
     * 设置图书状态
     * @param type 1为可借，0为不可借
     */
    void setBookState(int type);
}
