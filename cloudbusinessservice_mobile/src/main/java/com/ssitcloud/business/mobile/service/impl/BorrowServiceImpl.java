package com.ssitcloud.business.mobile.service.impl;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.StringUtils;
import com.ssitcloud.business.mobile.fatory.WebserviceConverter;
import com.ssitcloud.business.mobile.service.*;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.libraryinfo.entity.BookItemPageEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;
import com.ssitcloud.mobile.entity.ReservationBookEntity;
import com.ssitcloud.mobile.entity.ReservationMessage;
import com.ssitcloud.mobile.entity.StaticsTypeEntity;
import com.ssitcloud.mobileserver.core.Session;
import com.ssitcloud.mobileserver.entity.*;
import com.ssitcloud.mobileserver.exception.InitSessionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LXP
 * @version 创建时间：2017年2月28日 下午4:54:27
 */
@Service
public class BorrowServiceImpl implements BorrowServiceI {
    private final String URL_SELECT_BOOK_ITEM = "queryBookItemListByPage";
    private final String URL_SELECT_BOOK_ITEM_AND_INFO = "queryBookitemAndBookInfo";
    private final String URL_SELECT_BOOK_ITEM_AND_INFO_BY_IDX = "queryBookitemAndBookInfobyIdx";
    private final String URL_SELECT_BOOK_CLASSIFY = "queryStaticsTypeList";
    private final String charset = "utf-8";

    @Resource(name = "requestURL")
    private RequestURLListEntity requestURLEntity;

    @Autowired
    private BibliosServiceI bibliosService;

    @Autowired
    private ReaderCardServiceI readerCardService;

    @Autowired
    private PasswordServiceI passwordService;

    @Autowired
    private DeviceServiceI deviceService;

    @Autowired
    private WebserviceI webservice;

    @Override
    public List<Map<String, Object>> findBookByLibAndDevice(BookItemPageEntity selectEntity) {
        if (selectEntity == null
                || selectEntity.getLib_idx() == null
                || selectEntity.getDevice_idx() == null) {
            LogUtils.info(new IllegalArgumentException("lib_id或device_idx为null"));
            return null;
        }
        Map<String, String> paramMap = new HashMap<>(1);
        paramMap.put("json", JsonUtils.toJson(selectEntity));
        String bookItemUrl = requestURLEntity.getRequestURL(URL_SELECT_BOOK_ITEM);
        String bookItemResultJson = HttpClientUtil.doPost(bookItemUrl, paramMap, charset);
        List<Map<String, Object>> bookItemMapList = null;
        try {
            ResultEntity bookItemResultEntity;
            bookItemResultEntity = JsonUtils.fromJson(bookItemResultJson, ResultEntity.class);
            if (!bookItemResultEntity.getState()) {
                return null;
            }
            Map<String, Object> result = (Map<String, Object>) bookItemResultEntity.getResult();
            bookItemMapList = (List<Map<String, Object>>) result.get("rows");
        } catch (Exception e) {
            LogUtils.info(e);
        }

        if (bookItemMapList != null) {
            try {
                List<Map<String, Object>> dataList = new ArrayList<>(32);
                for (Map<String, Object> bookItemMap : bookItemMapList) {
                    Map<String, Object> data = new HashMap<>(10);
                    data.put("book_barcode", bookItemMap.get("book_barcode"));//图书barcode
                    data.put("book_uid", bookItemMap.get("book_uid"));//图书uid
                    data.put("bib_idx", bookItemMap.get("bib_idx"));//图书id
                    data.put("lib_idx", bookItemMap.get("lib_idx"));//图书馆id
                    data.put("device_idx", bookItemMap.get("device_idx"));//设备idx
                    data.put("bookitem_idx", bookItemMap.get("bookitem_idx"));//bookitem idx
                    data.put("noewlocation", bookItemMap.get("noewlocation"));//当前馆藏地
                    data.put("location", bookItemMap.get("location"));
                    data.put("nowlib_idx", bookItemMap.get("nowlib_idx"));
                    data.put("serverlib_idx", bookItemMap.get("serverlib_idx"));
                    data.put("state", bookItemMap.get("state"));

                    //获取图书具体信息
                    Integer bib_idx = (Integer) bookItemMap.get("bib_idx");
                    if (bib_idx == null) {
                        throw new RuntimeException(this.getClass() + "获取到的bookItem数据不是完整的，bib_id为null。data=>" + bookItemMap);
                    }
                    //调用服务获取
                    Map<String, Object> bibliosMap = bibliosService.selectBiblios(bib_idx);
                    if (bibliosMap == null) {
                        LogUtils.debug(this.getClass() + "从bibliosService.selectBiblios没有获取到数数据,biblios idx=>" + bib_idx);
                        continue;
                    }
                    data.putAll(bibliosMap);
                    dataList.add(data);
                }
                return dataList;

            } catch (Exception e) {
                LogUtils.info(e);
            }
        }

        return null;
    }


    @Override
    public ResultEntity renewBook(ReaderCardEntity card, String booksn) {
        ResultEntity resultEntity = new ResultEntity();
        Integer lib_idx = card.getLib_idx();
        List<Map<String, Object>> selectReaderCards = readerCardService.selectReaderCards(card);
        if (selectReaderCards == null || selectReaderCards.isEmpty()) {
            resultEntity.setState(false);
            resultEntity.setRetMessage("-3");
            resultEntity.setMessage("没有查询到读者卡");
            return resultEntity;
        }

        String miPwd = (String) selectReaderCards.get(0).get("card_password");
        String pwd = passwordService.decrypt(miPwd);
        if (pwd == null) {
            LogUtils.error("解密用户密码失败");
            return resultEntity;
        }

        try (Session session = webservice.getWebserviceSession(card.getLib_idx())) {
            ReaderCardState rcs = session.readerCardState(card.getCard_no(), pwd);
            if (rcs.getState() == ReaderCardState.CardState.NORMAL) {
                Renew renew = session.renew(card.getCard_no(), pwd, booksn);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                boolean renewState = renew.getState() == Renew.StateEnum.SUCCESS;
                Map<String, Object> renewMessage = new HashMap<>();
                renewMessage.put("SCREENMESSAGE", renew.getMessage());
                renewMessage.put("state", renewState);
                renewMessage.put("PATRONCARDSN", card.getCard_no());
                renewMessage.put("BOOKSN", booksn);
                if (renew.getReturnDate() != null) {
                    renewMessage.put("SHOULDRETURNDATE", sdf.format(renew.getReturnDate()));
                }

                resultEntity.setState(renewState);
                resultEntity.setResult(renewMessage);
                return resultEntity;
            } else if (rcs.getState() == ReaderCardState.CardState.INVAILD) {
                resultEntity.setValue(false, "卡无效");
                resultEntity.setRetMessage("-1");
                return resultEntity;
            } else if (rcs.getState() == ReaderCardState.CardState.PASSWORD_ERROR) {
                resultEntity.setValue(false, "卡密码错误");
                resultEntity.setRetMessage("-2");
                return resultEntity;
            }
        } catch (InitSessionException e) {
            LogUtils.error("初始化webservice失败,lib_idx->" + lib_idx + ",message->" + e.getMessage());
            resultEntity.setState(false);
            resultEntity.setRetMessage("lib_not_support");//图书暂时无法提供服务
            resultEntity.setMessage("图书馆暂时无法提供此服务");
        } catch (SocketException e) {
            LogUtils.error("使用webservice发生异常,lib_idx->" + lib_idx + ",message->" + e.getMessage());
            resultEntity.setState(false);
            resultEntity.setRetMessage("lib_not_support");//图书暂时无法提供服务
            resultEntity.setMessage("图书馆暂时无法提供此服务");
        } catch (IOException e) {
            LogUtils.info("webservice发生IOException,lib_idx->" + lib_idx + ",message->" + e.getMessage());
        }

        return resultEntity;
    }


    @Override
    public ResultEntity queryBookitemAndBookInfo(Map<String, Object> param) {
        ResultEntity resultEntity = new ResultEntity();
        if (param == null) {
            resultEntity.setMessage("mast input args");
            return resultEntity;
        }
        String url = requestURLEntity.getRequestURL(URL_SELECT_BOOK_ITEM_AND_INFO);

        Map<String, String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(param));
        String resultJson = HttpClientUtil.doPost(url, map, charset);
        if (resultJson != null) {
            try {
                ResultEntity r = JsonUtils.fromJson(resultJson, ResultEntity.class);
                if (r.getState()) {
                    return r;
                }
            } catch (Exception e) {
                LogUtils.info(url + " 服务器没有返回预期数据ResultEntity", e);
            }
        }

        return resultEntity;
    }


    @Override
    public ResultEntity queryBookUnionEntity(Integer bookitem_idx) {
        ResultEntity resultEntity = new ResultEntity();

        String url = requestURLEntity.getRequestURL(URL_SELECT_BOOK_ITEM_AND_INFO_BY_IDX);

        Map<String, String> map = new HashMap<>(3);
        map.put("json", "{\"bookitem_idx\":" + bookitem_idx + "}");
        String resultJson = HttpClientUtil.doPost(url, map, charset);
        if (resultJson != null) {
            try {
                ResultEntity r = JsonUtils.fromJson(resultJson, ResultEntity.class);
                if (r.getState()) {
                    return r;
                }
            } catch (Exception e) {
                LogUtils.info(url + " 服务器没有返回预期数据ResultEntity", e);
            }
        }

        return resultEntity;
    }


    @Override
    public ResultEntity reservationBook(Map<String, Object> param, Map<String, Object> iddata) {
        ResultEntity resultEntity = new ResultEntity();
        String card_no = (String) param.get("card_no");
        Integer reader_idx = (Integer) param.get("reader_idx");
        Integer lib_idx = (Integer) param.get("lib_idx");
        String bookSn = (String) param.get("sn");
        if (iddata == null || reader_idx == null || lib_idx == null || StringUtils.isEmpty(card_no) || bookSn == null) {
            resultEntity.setMessage("提交参数不正确，应该提供iddata,reader_idx,lib_idx,card_no,bookSn");
            resultEntity.setState(false);
            return resultEntity;
        }

        String logistics_number = String.valueOf(iddata.get("logistics_number"));
        if ("null".equals(logistics_number) || logistics_number.isEmpty()) {
            resultEntity.setMessage("iddata logistics_number is empty");
            return resultEntity;
        }
        ReaderCardEntity readerCard = new ReaderCardEntity();
        readerCard.setReader_idx(reader_idx);
        readerCard.setCard_no(card_no);
        readerCard.setLib_idx(lib_idx);
        List<Map<String, Object>> selectReaderCards = readerCardService.selectReaderCards(readerCard);
        if (selectReaderCards == null || selectReaderCards.isEmpty()) {
            resultEntity.setState(false);
            resultEntity.setRetMessage("no_card");
            resultEntity.setMessage("没有查询到读者卡");
            return resultEntity;
        }

        //获取并解密读者证密码
        String card_pwd = (String) selectReaderCards.get(0).get("card_password");
        card_pwd = passwordService.decrypt(card_pwd);
        if (card_pwd == null) {
            LogUtils.error("解密读者密码失败");
            return resultEntity;
        }

        try(Session session = webservice.getWebserviceSession(lib_idx)){
            ReaderCardState rcs = session.readerCardState(card_no, card_pwd);
            if (rcs.getState() == ReaderCardState.CardState.NORMAL) {
                Reserve reserve = session.reserve(card_no, card_pwd, bookSn, logistics_number);
                resultEntity.setState(reserve.getState() == Reserve.StateEnum.SUCCESS);
                ReservationMessage rm = WebserviceConverter.converter(reserve);
                resultEntity.setResult(rm);
                return resultEntity;
            } else if (rcs.getState() == ReaderCardState.CardState.INVAILD) {
                resultEntity.setValue(false, "卡无效");
                resultEntity.setRetMessage("-1");
                return resultEntity;
            } else if (rcs.getState() == ReaderCardState.CardState.PASSWORD_ERROR) {
                resultEntity.setValue(false, "卡密码错误");
                resultEntity.setRetMessage("-2");
                return resultEntity;
            }
        } catch (InitSessionException e) {
            LogUtils.error("初始化webservice失败,lib_idx->" + lib_idx + ",message->" + e.getMessage());
            resultEntity.setState(false);
            resultEntity.setRetMessage("lib_not_support");//图书暂时无法提供服务
            resultEntity.setMessage("图书馆暂时无法提供此服务");
        } catch (SocketException e) {
            LogUtils.error("使用webservice发生异常,lib_idx->" + lib_idx + ",message->" + e.getMessage());
            resultEntity.setState(false);
            resultEntity.setRetMessage("lib_not_support");//图书暂时无法提供服务
            resultEntity.setMessage("图书馆暂时无法提供此服务");
        } catch (IOException e) {
            LogUtils.info("webservice发生IOException,lib_idx->" + lib_idx + ",message->" + e.getMessage());
        }

        return resultEntity;
    }


    @Override
    public ResultEntity inReservationBook(Map<String, Object> param) {
        ResultEntity resultEntity = new ResultEntity();
		String card_no = (String) param.get("card_no");
		Integer reader_idx = (Integer) param.get("reader_idx");
		Integer lib_idx = (Integer) param.get("lib_idx");
		String bookSn = (String) param.get("sn");
//		String book_recon = (String) param.get("book_recon");
		if (reader_idx == null || lib_idx == null || StringUtils.isEmpty(card_no) || (bookSn == null /*&& book_recon == null*/)) {
			resultEntity.setMessage("提交参数不正确，应该提供reader_idx,lib_idx,card_no,sn or book_recon");
			resultEntity.setState(false);
			return resultEntity;
		}

		ReaderCardEntity readerCard = new ReaderCardEntity();
		readerCard.setReader_idx(reader_idx);
		readerCard.setCard_no(card_no);
		readerCard.setLib_idx(lib_idx);
		List<Map<String, Object>> selectReaderCards = readerCardService.selectReaderCards(readerCard );
		if (selectReaderCards == null || selectReaderCards.isEmpty()) {
			resultEntity.setState(false);
			resultEntity.setRetMessage("no_card");
			resultEntity.setMessage("没有查询到读者卡");
			return resultEntity;
		}

		//获取并解密读者证密码
		String card_pwd = (String) selectReaderCards.get(0).get("card_password");
		card_pwd = passwordService.decrypt(card_pwd);
		if (card_pwd == null) {
			LogUtils.error("解密读者密码失败");
			return resultEntity;
		}

        try(Session session = webservice.getWebserviceSession(lib_idx)){
            ReaderCardState rcs = session.readerCardState(card_no, card_pwd);
            if (rcs.getState() == ReaderCardState.CardState.NORMAL) {
                Reserve reserve = session.inReserve(card_no, card_pwd, bookSn);
                resultEntity.setState(reserve.getState() == Reserve.StateEnum.SUCCESS);
                ReservationMessage rm = WebserviceConverter.converter(reserve);
                resultEntity.setResult(rm);
                return resultEntity;
            } else if (rcs.getState() == ReaderCardState.CardState.INVAILD) {
                resultEntity.setValue(false, "卡无效");
                resultEntity.setRetMessage("-1");
                return resultEntity;
            } else if (rcs.getState() == ReaderCardState.CardState.PASSWORD_ERROR) {
                resultEntity.setValue(false, "卡密码错误");
                resultEntity.setRetMessage("-2");
                return resultEntity;
            }
        } catch (InitSessionException e) {
            LogUtils.error("初始化webservice失败,lib_idx->" + lib_idx + ",message->" + e.getMessage());
            resultEntity.setState(false);
            resultEntity.setRetMessage("lib_not_support");//图书暂时无法提供服务
            resultEntity.setMessage("图书馆暂时无法提供此服务");
        } catch (SocketException e) {
            LogUtils.error("使用webservice发生异常,lib_idx->" + lib_idx + ",message->" + e.getMessage());
            resultEntity.setState(false);
            resultEntity.setRetMessage("lib_not_support");//图书暂时无法提供服务
            resultEntity.setMessage("图书馆暂时无法提供此服务");
        } catch (IOException e) {
            LogUtils.info("webservice发生IOException,lib_idx->" + lib_idx + ",message->" + e.getMessage());
        }

        return resultEntity;
    }

    @Override
    public ResultEntity reservationList(Map<String, Object> param) {
        ResultEntity resultEntity = new ResultEntity();
		String card_no = (String) param.get("card_no");
		Integer reader_idx = (Integer) param.get("reader_idx");
		Integer lib_idx = (Integer) param.get("lib_idx");
		if (reader_idx == null || lib_idx == null || StringUtils.isEmpty(card_no) ) {
			resultEntity.setMessage("提交参数不正确，应该提供reader_idx,lib_idx,card_no");
			resultEntity.setState(false);
			return resultEntity;
		}

        ReaderCardEntity readerCard = new ReaderCardEntity();
        readerCard.setReader_idx(reader_idx);
        readerCard.setCard_no(card_no);
        readerCard.setLib_idx(lib_idx);
        List<Map<String, Object>> selectReaderCards = readerCardService.selectReaderCards(readerCard );
        if (selectReaderCards == null || selectReaderCards.isEmpty()) {
            resultEntity.setState(false);
            resultEntity.setRetMessage("no_card");
            resultEntity.setMessage("没有查询到读者卡");
            return resultEntity;
        }

        //获取并解密读者证密码
        String card_pwd = (String) selectReaderCards.get(0).get("card_password");
        card_pwd = passwordService.decrypt(card_pwd);
        if (card_pwd == null) {
            LogUtils.error("解密读者密码失败");
            return resultEntity;
        }
        try(Session session = webservice.getWebserviceSession(lib_idx)){
            ReaderCardState rcs = session.readerCardState(card_no, card_pwd);
            if (rcs.getState() == ReaderCardState.CardState.NORMAL) {
                List<ReserveBookInfo> reserveBookInfos = session.reserveList(card_no,card_pwd);
                List<ReservationBookEntity> books = new ArrayList<>(reserveBookInfos.size());
                DeviceEntity de = new DeviceEntity();//用于查询物流编码
                for (ReserveBookInfo reserveBookInfo : reserveBookInfos) {
                    ReservationBookEntity bookinfo = WebserviceConverter.converter(reserveBookInfo);

                    //根据物流编码查询实际地点
                    de.setLogistics_number(bookinfo.getLogisticsNum());
                    List<Map<String,Object>> devices = deviceService.findDevice(de);
                    if(devices!= null && !devices.isEmpty()){
                        bookinfo.setDeliverAddr((String) devices.get(0).get("circule_location"));
                    }

                    books.add(bookinfo);
                }

                resultEntity.setState(true);
                resultEntity.setResult(books);
                return resultEntity;
            } else if (rcs.getState() == ReaderCardState.CardState.INVAILD) {
                resultEntity.setValue(false, "卡无效");
                resultEntity.setRetMessage("-1");
                return resultEntity;
            } else if (rcs.getState() == ReaderCardState.CardState.PASSWORD_ERROR) {
                resultEntity.setValue(false, "卡密码错误");
                resultEntity.setRetMessage("-2");
                return resultEntity;
            }
        } catch (InitSessionException e) {
            LogUtils.error("初始化webservice失败,lib_idx->" + lib_idx + ",message->" + e.getMessage());
            resultEntity.setState(false);
            resultEntity.setRetMessage("lib_not_support");//图书暂时无法提供服务
            resultEntity.setMessage("图书馆暂时无法提供此服务");
        } catch (SocketException e) {
            LogUtils.error("使用webservice发生异常,lib_idx->" + lib_idx + ",message->" + e.getMessage());
            resultEntity.setState(false);
            resultEntity.setRetMessage("lib_not_support");//图书暂时无法提供服务
            resultEntity.setMessage("图书馆暂时无法提供此服务");
        } catch (IOException e) {
            LogUtils.info("webservice发生IOException,lib_idx->" + lib_idx + ",message->" + e.getMessage());
        }

        return resultEntity;
    }

    @Override
    public ResultEntity selectBookState(Integer bookitem_idx) {
        ResultEntity resultEntity = new ResultEntity();
        ResultEntity selectBookEntity = queryBookUnionEntity(bookitem_idx);
        Map<String,Object> m = (Map<String, Object>) selectBookEntity.getResult();
        if(m == null || m.get("lib_idx") == null || m.get("book_barcode") == null){
            resultEntity.setState(false);
			resultEntity.setMessage("no such book id==>"+bookitem_idx);
			return resultEntity;
		}
		Integer lib_idx = (Integer) m.get("lib_idx");
		String booksn = (String) m.get("book_barcode");

		try(Session session = webservice.getWebserviceSession(lib_idx)){
            BookInfo bookinfo = session.bookinfo(booksn);
            if(bookinfo != null){
                if (bookinfo.getState() == BookInfo.BookState.AVALIABLE) {
                    resultEntity.setResult("AVALIABLE");
                } else if (bookinfo.getState() == BookInfo.BookState.LOAN) {
                    resultEntity.setResult("LOAN");
                } else if (bookinfo.getState() == BookInfo.BookState.RESERVE) {
                    resultEntity.setResult("RESERVE");
                } else {
                    resultEntity.setResult("LOAN");
                }
                resultEntity.setState(true);
            }
            return resultEntity;
        } catch (InitSessionException e) {
            LogUtils.error("初始化webservice失败,lib_idx->" + lib_idx + ",message->" + e.getMessage());
            resultEntity.setState(false);
            resultEntity.setRetMessage("lib_not_support");//图书暂时无法提供服务
            resultEntity.setMessage("图书馆暂时无法提供此服务");
            return resultEntity;
        } catch (Exception e){
		    LogUtils.error("使用webservice发生异常,lib_idx->"+ lib_idx+ ",message->" + e.getMessage());
            resultEntity.setRetMessage("lib_not_support");//图书暂时无法提供服务
            resultEntity.setMessage("图书馆暂时无法提供此服务");
            return resultEntity;
        }
    }

    @Override
    public ResultEntity bookClassIfy(Integer data_type) {
        String url = requestURLEntity.getRequestURL(URL_SELECT_BOOK_CLASSIFY);
        StaticsTypeEntity entity = new StaticsTypeEntity();
        entity.setData_type(data_type);
        Map<String, String> map = new HashMap<>(1, 2.0f);
        map.put("req", JsonUtils.toJson(entity));

        String req = HttpClientUtil.doPost(url, map, charset);
        try {
            ResultEntity resultEntity = JsonUtils.fromJson(req, ResultEntity.class);
            if (resultEntity.getState()) {
                return resultEntity;
            } else {
                LogUtils.info("请求下层服务失败,retMessage:" + resultEntity.getRetMessage());
            }
        } catch (Exception e) {
            LogUtils.info(e);
        }

        return new ResultEntity();
    }
}
