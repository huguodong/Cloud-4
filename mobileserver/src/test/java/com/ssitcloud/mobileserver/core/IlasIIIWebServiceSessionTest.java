package com.ssitcloud.mobileserver.core;

import com.ssitcloud.mobileserver.entity.*;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created by LXP on 2017/7/21.
 */
//@RunWith(Arquillian.class)
public class IlasIIIWebServiceSessionTest {
    private static Session session;

    @BeforeClass
    public static void init() {
        session = new IlasIIIWebServiceSession("http://89.133.12.110:8081/Service.asmx", "ilasIIIOpenAPI", "UTF-8");
    }

    @Test
    public void readerCardState() throws Exception {
        ReaderCardState readerCardState = session.readerCardState("G0625", "123");
        System.out.println(readerCardState);
    }

    @Test
    public void readerInfo() throws Exception {
        System.out.println(session.readerInfo("01580022003", "123"));
    }

    @Test
    public void bookinfo() throws Exception {
        System.out.println(session.bookinfo("0000022"));
    }

    @Test
    public void currentLoan() throws Exception {
        List<LoanBookInfo> loanInfos = session.currentLoan("G0627", "123");
        System.out.println(loanInfos);
    }

    @Test
    public void renew() throws Exception {
        Renew result = session.renew("G0625", "123", "0000023");
        System.out.println(result);
    }

    @Test
    public void reserve() throws Exception {
        Reserve reserve = session.reserve("G0625", "123", "0000024", "QWERDFB");
        System.out.println(reserve);
    }

    @Test
    public void inReserve() throws Exception {
        Reserve reserve = session.inReserve("G0625", "123", "0000024");
        System.out.println(reserve);
    }

    @Test
    public void reserveList() throws Exception {
        List<ReserveBookInfo> reserveList = session.reserveList("G0625", "123");
        System.out.println(reserveList);
    }
}
