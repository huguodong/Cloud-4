package com.ssitcloud.app_reader.view.viewInterface;

/**
 * Created by LXP on 2017/4/19.
 *
 */

public interface StandardViewI<T> {
    enum Standard_State{NETOWRK_ERROR,AUTH_ERROR,SUCCESS,FAIL, UNBIND_CARD,LIB_NOT_SUPPORT};

    void setView(Standard_State state,T t);
}
