package com.ssitcloud.app_reader.view.viewInterface;


import com.ssitcloud.app_reader.entity.ReservationBookEntity;
import com.ssitcloud.app_reader.entity.ReservationMessage;

import java.util.List;

public interface ReservationViewI {
    void setBookList(List<ReservationBookEntity> books);

    void setState(StandardViewI.Standard_State state);

    void setInreservationState(StandardViewI.Standard_State state,ReservationMessage message);
}
