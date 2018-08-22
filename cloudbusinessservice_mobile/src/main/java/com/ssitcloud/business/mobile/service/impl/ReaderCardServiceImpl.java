package com.ssitcloud.business.mobile.service.impl;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.StringUtils;
import com.ssitcloud.business.mobile.fatory.WebserviceConverter;
import com.ssitcloud.business.mobile.service.*;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.libraryinfo.entity.BibliosPageEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;
import com.ssitcloud.mobileserver.core.Session;
import com.ssitcloud.mobileserver.entity.LoanBookInfo;
import com.ssitcloud.mobileserver.entity.ReaderCardState;
import com.ssitcloud.mobileserver.entity.ReaderInfo;
import com.ssitcloud.mobileserver.exception.InitSessionException;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LXP
 * @version 创建时间：2017年3月1日 下午3:40:48
 */
@Service("readerCardService")
public class ReaderCardServiceImpl implements ReaderCardServiceI {
    private final String URL_SELECT_READER_CARDS = "selectReaderCards";
    private final String URL_DELETE_CARD = "deleteReaderCard";
    private final static String URL_INSERT_CARD = "insertReaderCard";
    // private final String URL_UPDATE_CARD = "updateReaderCard";

    @Resource(name = "requestURL")
    private RequestURLListEntity requestURLEntity;
    private static final String charset = "utf-8";

    @Autowired
    private ReaderCardServiceAsyncI readerCardServiceAsync;

    @Autowired
    private PasswordServiceI passwordService;// 解密读者卡密码

    @Autowired
    private WebserviceI webservice;

    @Override
    public List<Map<String, Object>> selectReaderCards(ReaderCardEntity readerCardEntity) {
        if (readerCardEntity == null) {
            return new ArrayList<>(0);
        }
        String url = requestURLEntity.getRequestURL(URL_SELECT_READER_CARDS);
        Map<String, String> paramMap = new HashMap<>(3);
        paramMap.put("json", JsonUtils.toJson(readerCardEntity));
        String remoteResultJson = HttpClientUtil.doPost(url, paramMap, charset);
        try {
            ResultEntity remoteResultEntity = JsonUtils.fromJson(remoteResultJson, ResultEntity.class);
            if (remoteResultJson == null || !remoteResultEntity.getState()) {
                LogUtils.error("从" + url + " 获取数据失败");
                return null;
            }
            List<Map<String, Object>> data = (List<Map<String, Object>>) remoteResultEntity.getResult();
            boolean b = false;
            if (data.size() < 2) {
                b = true;
            }
            for (Map<String, Object> mapTemp : data) {
                if (b) {
                    Integer reader_idx = readerCardEntity.getReader_idx();
                    Integer lib_idx = Integer.valueOf(String.valueOf(mapTemp.get("lib_idx")));
                    String card_no = String.valueOf(mapTemp.get("card_no"));
                    String card_pwd = String.valueOf(mapTemp.get("card_password"));
                    String pwd = passwordService.decrypt(card_pwd);
                    if(pwd == null){
                        LogUtils.error("解密读者卡失败");
                        continue;
                    }
                    try(Session session = webservice.getWebserviceSession(lib_idx)){
                        ReaderInfo readerInfo = session.readerInfo(card_no, pwd);
                        if(readerInfo != null){
                            ReaderCardEntity updateReaderCard = WebserviceConverter.converter(readerInfo);
                            updateReaderCard.setReader_idx(reader_idx);
                            updateReaderCard.setLib_idx(lib_idx);
                            updateReaderCard.setCard_no(card_no);
                            Map<String, Object> map = JsonUtils.fromJson(JsonUtils.toJson(updateReaderCard),new TypeReference<HashMap<String, Object>>() {});
                            mapTemp.putAll(map);
                            readerCardServiceAsync.updateReaderCardAsync(updateReaderCard);//异步更新
                        }
                    } catch (InitSessionException e) {
                        LogUtils.info("初始化webservice异常,idx->"+lib_idx+",message->"+e.getMessage());
                    } catch (SocketException e) {
                        LogUtils.error("使用webservice发生异常,lib_idx->" + lib_idx + ",message->" + e.getMessage());
                    } catch (IOException e) {
                        LogUtils.info("webservice发生IOException,lib_idx->"+lib_idx+",message->"+e.getMessage());
                    }
                } else {//数量多发送异步后台更新请求
                    ReaderCardEntity readerCard = new ReaderCardEntity();
                    readerCard.setLib_idx(Integer.valueOf(String.valueOf(mapTemp.get("lib_idx"))));
                    readerCard.setReader_idx(readerCardEntity.getReader_idx());
                    readerCard.setCard_no(String.valueOf(mapTemp.get("card_no")));
                    readerCard.setCard_password(String.valueOf(mapTemp.get("card_password")));
                    readerCardServiceAsync.updateReaderCardOnAcsAsync(readerCard);
                }
            }
            return data;
        } catch (Exception e) {
            LogUtils.error("selectReaderCards exception",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultEntity unbindCard(Map<String, Object> data) {
        ResultEntity resultEntity = new ResultEntity();
        // 校验数据
        Object reader_idx = data.get("reader_idx");
        Object card_no = data.get("card_no");
        Object lib_idx = data.get("lib_idx");
        if (reader_idx == null || card_no == null || lib_idx == null) {
            resultEntity.setState(false);
            resultEntity.setMessage("reader_idx is null or card_no is null or lib_idx is null");
            return resultEntity;
        }
        String url = requestURLEntity.getRequestURL(URL_DELETE_CARD);
        Map<String, String> param = new HashMap<>(1);
        param.put("json", JsonUtils.toJson(data));
        String remoteResult = HttpClientUtil.doPost(url, param, charset);
        if (remoteResult == null) {
            resultEntity.setState(false);
            resultEntity.setMessage("连接远程服务器失败url=>" + url);
            LogUtils.error("请求 " + url + "失败，服务器没有返回数据");
            return resultEntity;
        }
        try {
            ResultEntity remoteResultEntity = JsonUtils.fromJson(remoteResult, ResultEntity.class);
            resultEntity.setState(remoteResultEntity.getState());
            return resultEntity;
        } catch (Exception e) {
            LogUtils.error(url + "没有返回期望数据，期望数据格式为ResultEntity");
            resultEntity.setState(false);
            return resultEntity;
        }

    }

    @Override
    public ResultEntity unbindCard(List<Map<String, Object>> data) {
        ResultEntity resultEntity = new ResultEntity();
        if (data == null || data.isEmpty()) {
            resultEntity.setState(true);
            return resultEntity;
        }

        List<Map<String, Object>> failList = new ArrayList<>(data.size());
        for (int i = 0, z = data.size(); i < z; i++) {
            Map<String, Object> map = data.get(i);
            ResultEntity tempResult = this.unbindCard(map);
            if (!tempResult.getState()) {
                failList.add(map);
            }
        }
        resultEntity.setState(failList.isEmpty());
        resultEntity.setResult(!failList.isEmpty() ? failList : null);
        resultEntity.setMessage(failList.isEmpty() ? "解绑成功" : "解绑失败了，请刷新后重试");
        return resultEntity;
    }

    @Override
    public ResultEntity bindCard(ReaderCardEntity readercard) {
        ResultEntity resultEntity = new ResultEntity();

        Integer reader_idx = readercard.getReader_idx();
        Integer lib_idx = readercard.getLib_idx();
        String card_no = readercard.getCard_no();
        String card_pwd = readercard.getCard_password();
        if (reader_idx == null || StringUtils.isEmpty(card_pwd) || StringUtils.isEmpty(card_no)) {
            resultEntity.setMessage("提交参数不正确，应该提供reader_idx,lib_idx,card_no,card_pwd");
            resultEntity.setState(false);
            return resultEntity;
        }

        try (Session session = webservice.getWebserviceSession(lib_idx)) {
            ReaderCardState cardState = session.readerCardState(card_no, card_pwd);
            if (cardState.getState() == ReaderCardState.CardState.NORMAL) {
                ReaderInfo readerInfo = session.readerInfo(card_no, card_pwd);
                if (readerInfo != null) {
                    ReaderCardEntity rc = WebserviceConverter.converter(readerInfo);
                    rc.setReader_idx(reader_idx);
                    rc.setCard_no(card_no);
                    rc.setCard_password(card_pwd);
                    rc.setLib_idx(lib_idx);
                    ResultEntity r = insertReaderCard(rc, passwordService, requestURLEntity);
                    resultEntity.setState(r.getState());
                    resultEntity.setRetMessage(r.getState() ? "0" : "-3");
                    return resultEntity;
                } else {
                    LogUtils.error("webservice验证卡有效，获取读者信息失败,lib_idx->" + lib_idx + " reader_idx->" + reader_idx +
                            " card_no->" + card_no);
                    resultEntity.setValue(false, "卡无效");
                    resultEntity.setRetMessage("-1");
                }
            } else if (cardState.getState() == ReaderCardState.CardState.INVAILD) {
                resultEntity.setValue(false, "卡无效");
                resultEntity.setRetMessage("-1");
                return resultEntity;
            } else if (cardState.getState() == ReaderCardState.CardState.PASSWORD_ERROR) {
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

        resultEntity.setState(false);
        return resultEntity;
    }

    @Override
    public ResultEntity selectCardBooks(ReaderCardEntity readerCard) {
        ResultEntity resultEntity = new ResultEntity();

        Integer reader_idx = readerCard.getReader_idx();
        Integer lib_idx = readerCard.getLib_idx();
        String card_no = readerCard.getCard_no();
        if (reader_idx == null || lib_idx == null || StringUtils.isEmpty(card_no)) {
            resultEntity.setMessage("提交参数不正确，应该提供reader_idx,lib_idx,card_no");
            resultEntity.setState(false);
            return resultEntity;
        }
        List<Map<String, Object>> selectReaderCards = selectReaderCards(readerCard);
        if (selectReaderCards == null || selectReaderCards.isEmpty()) {
            resultEntity.setState(false);
            resultEntity.setRetMessage("-3");
            resultEntity.setMessage("没有查询到读者卡");
            return resultEntity;
        }
        Map<String, Object> readerCardMap = selectReaderCards.get(0);
        String card_pwd = (String) readerCardMap.get("card_password");
        String pwd = passwordService.decrypt(card_pwd);
        if (pwd == null) {
            LogUtils.error("解密读者密码失败");
            return resultEntity;
        }

        try (Session session = webservice.getWebserviceSession(lib_idx)) {
            ReaderCardState rcs = session.readerCardState(card_no, pwd);
            if (rcs.getState() == ReaderCardState.CardState.NORMAL) {
                List<LoanBookInfo> loanInfos = session.currentLoan(card_no, pwd);
                List<BibliosPageEntity> borrowBookList = new ArrayList<>(25);
                for (LoanBookInfo loanInfo : loanInfos) {
                    BibliosPageEntity bpe = WebserviceConverter.converter(loanInfo);
                    borrowBookList.add(bpe);
                }

                resultEntity.setState(true);
                resultEntity.setRetMessage("0");
                resultEntity.setResult(borrowBookList);
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

    /**
     * 插入读者证信息
     *
     * @param readerCard
     * @return
     */
    public static ResultEntity insertReaderCard(ReaderCardEntity readerCard, PasswordServiceI passwordService, RequestURLListEntity requestURLEntity) {
        ResultEntity resultEntity = new ResultEntity();
        if (readerCard == null
                || readerCard.getReader_idx() == null
                || StringUtils.isEmpty(readerCard.getCard_no())
                || StringUtils.isEmpty(readerCard.getCard_password())) {
            LogUtils.info("没有设置读者idx或读者卡卡号或密码");
            resultEntity.setState(false);
            return resultEntity;
        }
        //设置更新日期和创建日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        readerCard.setCreate_time(new Date());
        readerCard.setUpdate_time(readerCard.getCreate_time());

        //加密读者卡密码
        String mi = passwordService.encryption(readerCard.getCard_password());
        if (mi == null) {
            LogUtils.info("加密读者密码失败");
            resultEntity.setState(false);
            return resultEntity;
        }
        readerCard.setCard_password(mi);

        //准备数据，开始发送
        String url = requestURLEntity.getRequestURL(URL_INSERT_CARD);
        Map<String, String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(readerCard));
        String remoteResult = HttpClientUtil.doPost(url, map, charset);
        try {
            ResultEntity remoteResultEntity = JsonUtils.fromJson(remoteResult, ResultEntity.class);
            if (remoteResultEntity != null && remoteResultEntity.getState()) {
                return remoteResultEntity;
            } else {
                resultEntity.setState(false);
                LogUtils.info("插入读者证信息失败,return Json=>" + remoteResult);
                return resultEntity;
            }
        } catch (Exception e) {
            LogUtils.info("插入读者证信息失败", e);
            resultEntity.setState(false);
            return resultEntity;
        }
    }
}
