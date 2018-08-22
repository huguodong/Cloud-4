package com.ssitcloud.business.mobile.fatory;

import com.ssitcloud.libraryinfo.entity.BibliosPageEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;
import com.ssitcloud.mobile.entity.ReservationBookEntity;
import com.ssitcloud.mobile.entity.ReservationMessage;
import com.ssitcloud.mobileserver.entity.*;

import java.text.SimpleDateFormat;

/**
 * Created by LXP on 2017/8/2.
 * webservice转换器
 */

public class WebserviceConverter {

    public static BibliosPageEntity converter(BookInfo bookInfo) {
        BibliosPageEntity bpe = new BibliosPageEntity();
        bpe.setTitle(bookInfo.getTitle());
        bpe.setPublish(bookInfo.getPublish());
        bpe.setBook_barcode(bookInfo.getBarcode());
        bpe.setAuthor(bookInfo.getAuthor());
        return bpe;
    }

    public static BibliosPageEntity converter(LoanBookInfo loanInfo) {
        BibliosPageEntity bpe = converter(((BookInfo) loanInfo));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        bpe.setReturnDate(sdf.format(loanInfo.getReturndate()));
        bpe.setLoanDate(sdf.format(loanInfo.getLoandate()));
        bpe.setState(1);
        return bpe;
    }

    public static ReaderCardEntity converter(ReaderInfo readerInfo) {
        ReaderCardEntity rc = new ReaderCardEntity();
        rc.setReader_name(readerInfo.getName());
        rc.setDeposit(readerInfo.getDeposit());
        rc.setSurplus_count(readerInfo.getSurplusnum());
        rc.setAllown_loancount(readerInfo.getMaxloannum());
        rc.setAdvance_charge(readerInfo.getPrefine());
        rc.setArrearage(readerInfo.getOverDue());
        return rc;
    }

    public static ReservationMessage converter(Reserve reserve){
        ReservationMessage rm = new ReservationMessage();
        rm.setState(reserve.getState() == Reserve.StateEnum.SUCCESS);
        rm.setMessage(reserve.getMessage());
        return rm;
    }

    public static ReservationBookEntity converter(ReserveBookInfo reserveBookInfo){
        ReservationBookEntity rbe = new ReservationBookEntity();
        rbe.setBookRecode(reserveBookInfo.getBarcode());
        rbe.setTitle(reserveBookInfo.getTitle());
        rbe.setCallNo(reserveBookInfo.getCallno());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        rbe.setDeadline(sdf.format(reserveBookInfo.getEndDate()));
        rbe.setLogisticsNum(reserveBookInfo.getLocalCode());
        return rbe;
    }
}
