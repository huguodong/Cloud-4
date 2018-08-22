package com.ssitcloud.mobileserver.core;

import com.ssitcloud.mobileserver.connector.HttpConnector;
import com.ssitcloud.mobileserver.entity.*;
import com.ssitcloud.mobileserver.exception.AnalysisDataException;
import com.ssitcloud.mobileserver.handles.IlasIIIWebServiceHandlesImpl;
import com.ssitcloud.mobileserver.handles.IlasWebServiceHandles;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/7/21.
 */

public class IlasIIIWebServiceSession implements Session {
    private Logger logger;
    private final String url;
    private final String apiName;
    private final String charset;
    private HttpConnector httpConnector;
    private IlasWebServiceHandles handles;

    private final int connTimeout;
    private final int readerTimeout;

    private volatile boolean isClosed = false;

    public IlasIIIWebServiceSession(String url, String apiName, String charset) {
        this(url, apiName, charset, 8_000, 8_000);
    }

    public IlasIIIWebServiceSession(String url, String apiName, String charset, int connTimeout, int readerTimeout) {
        this.url = url;
        this.apiName = apiName;
        this.charset = charset;
        httpConnector = new HttpConnector(charset);
        handles = new IlasIIIWebServiceHandlesImpl(apiName, charset);
        logger = Logger.getLogger(this.getClass());
        this.connTimeout = connTimeout;
        this.readerTimeout = readerTimeout;
    }

    @Override
    public ReaderCardState readerCardState(final String cardNo, final String pwd) throws SocketException {
        Processor<ReaderCardState> processor = new Processor<ReaderCardState>() {
            @Override
            public HTTP_METHOD getMethod() {
                return HTTP_METHOD.GET;
            }

            @Override
            public String getMessage() {
                return handles.readerCardStateBody(cardNo, pwd);
            }

            @Override
            public ReaderCardState analysis(String message) throws AnalysisDataException {
                return handles.analysisReaderCardState(message);
            }

            @Override
            public String getFunctionName() {
                return "GetRdrRec";
            }
        };
        return requestData(processor);
    }

    @Override
    public ReaderInfo readerInfo(final String cardNo, final String pwd) throws SocketException {
        Processor<ReaderInfo> processor = new Processor<ReaderInfo>() {
            @Override
            public HTTP_METHOD getMethod() {
                return HTTP_METHOD.GET;
            }

            @Override
            public String getMessage() {
                return handles.readerInfoBody(cardNo, pwd);
            }

            @Override
            public ReaderInfo analysis(String message) throws AnalysisDataException {
                return handles.analysisReaderInfo(message);
            }

            @Override
            public String getFunctionName() {
                return "GetRdrRec";
            }
        };
        return requestData(processor);
    }

    public BookInfo bookinfo(final String recno) throws SocketException {
        Processor<BookInfo> processor = new Processor<BookInfo>() {
            @Override
            public HTTP_METHOD getMethod() {
                return HTTP_METHOD.GET;
            }

            @Override
            public String getMessage() {
                return handles.bookInfoBody(recno);
            }

            @Override
            public BookInfo analysis(String message) throws AnalysisDataException {
                return handles.analysisBookInfo(message);
            }

            @Override
            public String getFunctionName() {
                return "BarcodeSrch";
            }
        };
        return requestData(processor);
    }

    @Override
    public List<LoanBookInfo> currentLoan(final String cardNo, final String pwd) throws SocketException {
        Processor<List<LoanBookInfo>> processor = new Processor<List<LoanBookInfo>>() {
            @Override
            public HTTP_METHOD getMethod() {
                return HTTP_METHOD.GET;
            }

            @Override
            public String getMessage() {
                return handles.currentLoanBody(cardNo, pwd);
            }

            @Override
            public List<LoanBookInfo> analysis(String message) throws AnalysisDataException {
                return handles.analysisCurrentLoan(message);
            }

            @Override
            public String getFunctionName() {
                return "GetRdrLoanList";
            }
        };

        return requestData(processor);
    }

    @Override
    public Renew renew(final String cardno, final String pwd, final String recno) throws SocketException {
        Processor<Renew> renewProcessor = new Processor<Renew>() {
            @Override
            public HTTP_METHOD getMethod() {
                return HTTP_METHOD.POST;
            }

            @Override
            public String getMessage() {
                return handles.renewBody(cardno, pwd, recno);
            }

            @Override
            public Renew analysis(String message) throws AnalysisDataException {
                return handles.analysisRenew(message);
            }

            @Override
            public String getFunctionName() {
                return "RenewBook";
            }
        };
        return requestData(renewProcessor);
    }

    @Override
    public Reserve reserve(String cardno, String pwd, String barcode, String localCode) throws SocketException {
        return this.reserve(cardno, pwd, barcode, "2", localCode);
    }

    private Reserve reserve(final String cardno, final String pwd, final String barcode, final String noticeModel, final String localCode) throws SocketException {
        Processor<Reserve> renewProcessor = new Processor<Reserve>() {
            @Override
            public HTTP_METHOD getMethod() {
                return HTTP_METHOD.POST;
            }

            @Override
            public String getMessage() {
                return handles.reserveBody(cardno, pwd, barcode, noticeModel, localCode);
            }

            @Override
            public Reserve analysis(String message) throws AnalysisDataException {
                return handles.analysisReserve(message);
            }

            @Override
            public String getFunctionName() {
                return "ReserveBook";
            }
        };
        return requestData(renewProcessor);
    }

    @Override
    public Reserve inReserve(final String cardno, final String pwd, final String barcode) throws SocketException {
        Processor<Reserve> renewProcessor = new Processor<Reserve>() {
            @Override
            public HTTP_METHOD getMethod() {
                return HTTP_METHOD.POST;
            }

            @Override
            public String getMessage() {
                return handles.inReserveBody(cardno,pwd,barcode);
            }

            @Override
            public Reserve analysis(String message) throws AnalysisDataException {
                return handles.analysisInReserve(message);
            }

            @Override
            public String getFunctionName() {
                return "CancleReservatBook";
            }
        };
        return requestData(renewProcessor);
    }

    @Override
    public List<ReserveBookInfo> reserveList(final String cardno, final String pwd) throws SocketException {
        Processor<List<ReserveBookInfo>> renewProcessor = new Processor<List<ReserveBookInfo>>() {
            @Override
            public HTTP_METHOD getMethod() {
                return HTTP_METHOD.GET;
            }

            @Override
            public String getMessage() {
                return handles.reserveListBody(cardno, pwd);
            }

            @Override
            public List<ReserveBookInfo> analysis(String message) throws AnalysisDataException {
                return handles.analysisReserveList(message);
            }

            @Override
            public String getFunctionName() {
                return "GetRdrRese";
            }
        };
        return requestData(renewProcessor);
    }

    @Override
    public boolean isClosed() throws IOException {
        return isClosed;
    }

    @Override
    public void close() throws IOException {
        isClosed = true;
    }

    private <T> T requestData(Processor<T> processor) throws SocketException {
        String message = null;
        long k = System.currentTimeMillis();
        try {
            URL address = new URL(url);
            String requestBody = processor.getMessage();
            logger.debug(k + ": send body->" + requestBody);

            Map<String, String> headers = getHeaders(processor.getFunctionName(), requestBody.getBytes(charset).length);
            if (processor.getMethod() == Processor.HTTP_METHOD.GET) {
                message = httpConnector.requestGetData(address, headers, requestBody, connTimeout, readerTimeout);
            } else if (processor.getMethod() == Processor.HTTP_METHOD.POST) {
                message = httpConnector.requestPostData(address, headers, requestBody, connTimeout, readerTimeout);
            }
            logger.debug(k + ":responce message->" + message);

            return processor.analysis(message);
        } catch (Exception e) {
            String exceptionMessage = null;
            if (e instanceof SocketException) {
                exceptionMessage = "网络连接失败";
            } else if (e instanceof AnalysisDataException) {
                exceptionMessage = "解析数据失败,返回数据->" + message + " Exception message->" + e.getMessage();
            }
            exceptionMessage = exceptionMessage != null ? exceptionMessage : e.getMessage();
            logger.debug(exceptionMessage);
            throw new SocketException(exceptionMessage);
        }
    }

    private Map<String, String> getHeaders(String funcName, int contentLen) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/xml; charset=" + charset.toLowerCase());
        headers.put("Content-Length", "" + contentLen);
        headers.put("SOAPAction", apiName + "/" + funcName);
        return headers;
    }

    private interface Processor<T> {
        enum HTTP_METHOD {POST, GET}

        HTTP_METHOD getMethod();

        String getMessage();

        T analysis(String message) throws AnalysisDataException;

        String getFunctionName();
    }
}
