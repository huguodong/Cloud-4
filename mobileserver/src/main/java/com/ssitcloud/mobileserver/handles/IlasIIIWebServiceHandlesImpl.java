package com.ssitcloud.mobileserver.handles;

import com.ssitcloud.mobileserver.entity.*;
import com.ssitcloud.mobileserver.exception.AnalysisDataException;
import com.ssitcloud.mobileserver.util.XmlUtils;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/7/21.
 */

public class IlasIIIWebServiceHandlesImpl implements IlasWebServiceHandles {
    private String apiName;
    private String charset;

    public IlasIIIWebServiceHandlesImpl(String apiName, String charset) {
        this.apiName = apiName;
        this.charset = charset;
    }

    @Override
    public String readerInfoBody(String cardNo, String pwd) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap:Body>" +
                "<GetRdrRec xmlns=\"" + apiName + "\">" +
                "<strCardno>" + cardNo + "</strCardno>" +
                "<strPassword>" + pwd + "</strPassword>" +
                "</GetRdrRec>" +
                "</soap:Body>" +
                "</soap:Envelope>";
    }

    @Override
    public ReaderInfo analysisReaderInfo(String message) throws AnalysisDataException {
        try {
            Map<String, Object> data = convertToMap(message);
            Map<String, Object> recResponse = (Map<String, Object>) data.get("GetRdrRecResponse");
            Map<String, Object> rdrRecResult = (Map<String, Object>) recResponse.get("GetRdrRecResult");
            Map<String, Object> ilasRdrInfo = (Map<String, Object>) rdrRecResult.get("IlasRdrInfo");
            if ("1".equals(ilasRdrInfo.get("@ilasIIIReturn")) && !"N".equals(ilasRdrInfo.get("PIN"))) {
                ReaderInfo readerInfo = new ReaderInfo();
                readerInfo.setName((String) ilasRdrInfo.get("NAME"));
                readerInfo.setCardNo((String) ilasRdrInfo.get("CARDNO"));
                if ("M".equals(ilasRdrInfo.get("GENDER"))) {
                    readerInfo.setSex(0);
                } else if ("F".equals(ilasRdrInfo.get("GENDER"))) {
                    readerInfo.setSex(1);
                }
                String birth = (String) ilasRdrInfo.get("BIRTH");
                if (!StringUtils.isEmpty(birth)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    readerInfo.setBarth(sdf.parse(birth));
                }
                String idcard = (String) ilasRdrInfo.get("IDCARD");
                if (!StringUtils.isEmpty(idcard)) {
                    readerInfo.setIdCard(idcard);
                }
                String phone = (String) ilasRdrInfo.get("PHONE");
                if (!StringUtils.isEmpty(phone)) {
                    readerInfo.setPhone(phone);
                }
                //押金
                String deposit = (String) ilasRdrInfo.get("Deposit");
                if (!StringUtils.isEmpty(deposit)) {
                    readerInfo.setDeposit(Double.valueOf(deposit));
                }
                //预付款
                String prefine = (String) ilasRdrInfo.get("Prefine");
                if (!StringUtils.isEmpty(prefine)) {
                    readerInfo.setPrefine(Double.valueOf(prefine));
                }
                //欠款
                String overDue = (String) ilasRdrInfo.get("OverDue");
                if (!StringUtils.isEmpty(overDue)) {
                    readerInfo.setOverDue(Double.valueOf(overDue));
                }
                //最大可借书
                String maxloannum = (String) ilasRdrInfo.get("Maxloannum");
                if (!StringUtils.isEmpty(maxloannum)) {
                    readerInfo.setMaxloannum(Integer.valueOf(maxloannum));
                }
                //剩余可借书
                String surplusnum = (String) ilasRdrInfo.get("Surplusnum");
                if (!StringUtils.isEmpty(overDue)) {
                    readerInfo.setSurplusnum(Integer.valueOf(surplusnum));
                }
                return readerInfo;
            }

            return null;
        } catch (Exception e) {
            throw new AnalysisDataException(e);
        }
    }

    @Override
    public String readerCardStateBody(String cardNo, String pwd) {
        return readerInfoBody(cardNo, pwd);
    }

    @Override
    public ReaderCardState analysisReaderCardState(String message) throws AnalysisDataException {
        try {
            Map<String, Object> data = convertToMap(message);
            Map<String, Object> recResponse = (Map<String, Object>) data.get("GetRdrRecResponse");
            Map<String, Object> rdrRecResult = (Map<String, Object>) recResponse.get("GetRdrRecResult");
            Map<String, Object> ilasRdrInfo = (Map<String, Object>) rdrRecResult.get("IlasRdrInfo");
            ReaderCardState readerCardState = new ReaderCardState();
            if ("0".equals(ilasRdrInfo.get("@ilasIIIReturn"))
                    || !"n".equals(ilasRdrInfo.get("STATUS"))) {
                readerCardState.setState(ReaderCardState.CardState.INVAILD);
            } else if ("Y".equals(ilasRdrInfo.get("PIN"))) {
                readerCardState.setState(ReaderCardState.CardState.NORMAL);
            } else {
                readerCardState.setState(ReaderCardState.CardState.PASSWORD_ERROR);
            }
            return readerCardState;
        } catch (Exception e) {
            throw new AnalysisDataException(e);
        }
    }

    @Override
    public String bookInfoBody(String recno) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap:Body>" +
                "<BarcodeSrch xmlns=\"" + apiName + "\">" +
                "<strBarcode>" + recno + "</strBarcode>" +
                "</BarcodeSrch>" +
                "</soap:Body>" +
                "</soap:Envelope>";
    }

    @Override
    public BookInfo analysisBookInfo(String message) throws AnalysisDataException {
        try {
            Map<String, Object> data = convertToMap(message);
            Map<String, Object> responce = (Map<String, Object>) data.get("BarcodeSrchResponse");
            Map<String, Object> result = (Map<String, Object>) responce.get("BarcodeSrchResult");
            Map<String, Object> barcodeInfo = (Map<String, Object>) result.get("BarcodeInfo");

            //检查是否返回有书目数据，若没有返回书目数据则返回null
            if (barcodeInfo == null || "0".equals(barcodeInfo.get("@ilasIIIReturn"))) {
                return null;
            }

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            BookInfo bookInfo = new BookInfo();
            bookInfo.setRecno((String) barcodeInfo.get("bib_recno"));
            bookInfo.setBarcode((String) barcodeInfo.get("Barcode"));
            bookInfo.setTitle((String) barcodeInfo.get("Title"));
            bookInfo.setAuthor((String) barcodeInfo.get("Author"));
            bookInfo.setPage((String) barcodeInfo.get("Page"));
            if("b".equals(barcodeInfo.get("StateCode"))){
                bookInfo.setState(BookInfo.BookState.AVALIABLE);
            }else if("m".equals(barcodeInfo.get("StateCode"))){
                bookInfo.setState(BookInfo.BookState.RESERVE);
            }
//            String temp = (String) barcodeInfo.get("Loandate");
//            if(!StringUtils.isEmpty(temp)){
//                bookInfo.setLoandate(sdf.parse(temp));
//            }
//            temp = (String) barcodeInfo.get("Returndate");
//            if (!StringUtils.isEmpty(temp)) {
//                bookInfo.setReturndate(sdf.parse(temp));
//            }
            return bookInfo;
        } catch (Exception e) {
            throw new AnalysisDataException(e);
        }
    }

    @Override
    public String currentLoanBody(String cardNo, String pwd) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap:Body>" +
                "<GetRdrLoanList xmlns=\"" + apiName + "\">" +
                "<strCardno>" + cardNo + "</strCardno>" +
                "</GetRdrLoanList>" +
                "</soap:Body>" +
                "</soap:Envelope>";
    }

    @Override
    public List<LoanBookInfo> analysisCurrentLoan(String message) throws AnalysisDataException {
        try {
            Map<String, Object> data = convertToMap(message);
            Map<String, Object> responce = (Map<String, Object>) data.get("GetRdrLoanListResponse");
            Map<String, Object> result = (Map<String, Object>) responce.get("GetRdrLoanListResult");
            Map<String, Object> loanInfo = (Map<String, Object>) result.get("RdrLoanInfo");
            List<Map<String, Object>> books = null;
            Object infos = loanInfo.get("BookInfo");
            if (infos instanceof Map) {
                books = new ArrayList<>(1);
                books.add((Map<String, Object>) infos);
            } else {
                books = (List<Map<String, Object>>) infos;
            }
            if (books != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                List<LoanBookInfo> loanInfos = new ArrayList<>(books.size());
                for (Map<String, Object> book : books) {
                    LoanBookInfo loanInfo1 = new LoanBookInfo();
                    loanInfo1.setTitle((String) book.get("Title"));
                    loanInfo1.setCallno((String) book.get("Callno"));
                    loanInfo1.setBarcode((String) book.get("Barcode"));
                    loanInfo1.setAuthor((String) book.get("Author"));
                    String temp = (String) book.get("Loandate");
                    if (!StringUtils.isEmpty(temp)) {
                        loanInfo1.setLoandate(sdf.parse(temp));
                    }
                    temp = (String) book.get("Returndate");
                    if (!StringUtils.isEmpty(temp)) {
                        loanInfo1.setReturndate(sdf.parse(temp));
                    }

                    loanInfos.add(loanInfo1);
                }
                return loanInfos;
            } else {
                return new ArrayList<>(0);
            }
        } catch (Exception e) {
            throw new AnalysisDataException(e.getMessage(), e);
        }
    }

    @Override
    public String renewBody(String cardno,String pwd, String recno) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap:Body>" +
                "<RenewBook xmlns=\"" + apiName + "\">" +
                "<strCardno>" + cardno + "</strCardno>" +
                "<strBarno>" + recno + "</strBarno>" +
                "</RenewBook>" +
                "</soap:Body>" +
                "</soap:Envelope>";
    }

    @Override
    public Renew analysisRenew(String message) throws AnalysisDataException {
        try {
            Map<String, Object> data = convertToMap(message);
            Map<String, Object> response = (Map<String, Object>) data.get("RenewBookResponse");
            Map<String, Object> resultMap = (Map<String, Object>) response.get("RenewBookResult");
            Map<String, Object> info = (Map<String, Object>) resultMap.get("RenewBookInfo");

            Renew renew = new Renew();
            if (!"0".equals(info.get("@ilasIIIReturn"))) {
                renew.setState(Renew.StateEnum.SUCCESS);
                renew.setMessage((String) info.get("Message"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date returnDate = sdf.parse((String) info.get("EndDate"));
                renew.setReturnDate(returnDate);
            } else {
                renew.setState(Renew.StateEnum.FAIL);
                renew.setMessage((String) info.get("@FailMessage"));
            }
            return renew;
        } catch (Exception e) {
            throw new AnalysisDataException(e.getMessage());
        }
    }

    @Override
    public String reserveBody(String cardno, String pwd, String barcode, String noticeModel, String localCode) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap:Body>" +
                "<ReserveBook xmlns=\"" + apiName + "\">" +
                "<strCardno>" + cardno + "</strCardno>" +
                "<strBarno>" + barcode + "</strBarno>" +
                "<strType>J</strType>" +
                "<nWay>" + noticeModel + "</nWay>" +
                "<strLocation>" + localCode + "</strLocation>" +
                "</ReserveBook>" +
                "</soap:Body>" +
                "</soap:Envelope>";
    }

    @Override
    public Reserve analysisReserve(String message) throws AnalysisDataException {
        try {
            Map<String, Object> data = convertToMap(message);
            Map<String, Object> response = (Map<String, Object>) data.get("ReserveBookResponse");
            Map<String, Object> resultMap = (Map<String, Object>) response.get("ReserveBookResult");
            Map<String, Object> info = (Map<String, Object>) resultMap.get("ReserveBook");

            Reserve reserve = new Reserve();
            if ("1".equals(info.get("@ilasIIIReturn"))) {
                reserve.setState(Reserve.StateEnum.SUCCESS);
                Object temp = info.get("EndDate");
                if (temp != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    reserve.setDateLine(sdf.parse(((String) temp)));
                }
            } else {
                reserve.setState(Reserve.StateEnum.FAIL);
                reserve.setMessage((String) info.get("@FailMessage"));
            }
            return reserve;
        } catch (Exception e) {
            throw new AnalysisDataException(e.getMessage());
        }
    }

    @Override
    public String reserveListBody(String cardno, String pwd) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap:Body>" +
                "<GetRdrRese xmlns=\"" + apiName + "\">" +
                "<strCardno>" + cardno + "</strCardno>" +
                "</GetRdrRese>" +
                "</soap:Body>" +
                "</soap:Envelope>";
    }

    @Override
    public List<ReserveBookInfo> analysisReserveList(String message) throws AnalysisDataException {
        try {
            Map<String, Object> data = convertToMap(message);
            Map<String, Object> response = (Map<String, Object>) data.get("GetRdrReseResponse");
            Map<String, Object> resultMap = (Map<String, Object>) response.get("GetRdrReseResult");
            Object info = resultMap.get("RdrReseInfo");

            if(info instanceof String){
                return new ArrayList<>(0);
            }else{
                Object temp = ((Map<String, Object>) info).get("BookInfo");

                List<Map<String,Object>> reserveList;
                if(temp instanceof Map){
                    reserveList = new ArrayList<>(1);
                    reserveList.add((Map<String, Object>) temp);
                }else{
                    reserveList = (List<Map<String, Object>>) temp;
                }
                List<ReserveBookInfo> bookInfos = new ArrayList<>();
                if(reserveList != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    for (Map<String, Object> bookinfo : reserveList) {
                        ReserveBookInfo rbi = new ReserveBookInfo();
                        rbi.setBarcode((String) bookinfo.get("Barcode"));
                        rbi.setTitle((String) bookinfo.get("Title"));
                        rbi.setCallno((String) bookinfo.get("CallNo"));
                        rbi.setLocalCode((String) bookinfo.get("cirLocation"));
                        temp = bookinfo.get("RegDate");
                        if (temp != null) {
                            rbi.setRegDate(sdf.parse((String) temp));
                        }
                        temp = bookinfo.get("EndDate");
                        if (temp != null) {
                            rbi.setEndDate(sdf.parse((String) temp));
                        }

                        bookInfos.add(rbi);
                    }
                }
                return bookInfos;
            }
        } catch (Exception e) {
            throw new AnalysisDataException(e.getMessage());
        }
    }

    @Override
    public String inReserveBody(String cardno, String pwd, String barcode) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap:Body>" +
                "<CancleReservatBook xmlns=\""+apiName+"\">" +
                "<strCardno>"+cardno+"</strCardno>" +
                "<strBarno>"+barcode+"</strBarno>" +
                "<strBookNo></strBookNo>" +
                "</CancleReservatBook>" +
                "</soap:Body>" +
                "</soap:Envelope>";
    }

    @Override
    public Reserve analysisInReserve(String message) throws AnalysisDataException {
        try {
            Map<String, Object> data = convertToMap(message);
            Map<String, Object> response = (Map<String, Object>) data.get("CancleReservatBookResponse");
            Map<String, Object> resultMap = (Map<String, Object>) response.get("CancleReservatBookResult");
            Map<String, Object> info = (Map<String, Object>) resultMap.get("CancleReserveBook");

            Reserve reserve = new Reserve();
            if("1".equals(info.get("@ilasIIIReturn"))){
                reserve.setState(Reserve.StateEnum.SUCCESS);
                reserve.setMessage((String) info.get("@Remarks"));
            }else{
                reserve.setState(Reserve.StateEnum.FAIL);
                reserve.setMessage((String) info.get("@FailMessage"));
            }
            return reserve;
        } catch (Exception e) {
            throw new AnalysisDataException(e.getMessage());
        }
    }

    private String pretreatmentMessage(String message) {
        message = message.replaceAll("xmlns\\S*=\"\\S*\"", "");
        message = message.replace("&lt;", "<");
        message = message.replace("&gt;", ">");
        message = message.replaceAll("<\\s*soap:Envelope\\s*>", "<root>");
        message = message.replaceAll("</soap:Envelope\\s*>", "</root>");
        message = message.replaceAll("<\\s*soap:Body\\s*>|</soap:Body\\s*>", "");
        return message;
    }

    private Map<String, Object> convertToMap(String message) {
        message = pretreatmentMessage(message);
//        XMLSerializer xmlSerializer = new XMLSerializer();
//        JSON json = xmlSerializer.read(message);
//        String jsonStr = json.toString().replace("[]", "\"\"");
//        Map<String, Object> data = JsonUtils.fromJson(jsonStr, Map.class);
//        return data;
        return XmlUtils.toMap(message, charset);
    }

}
