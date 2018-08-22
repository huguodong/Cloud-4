package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;
import android.content.res.Resources;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.BookBizI;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.common.exception.ReaderCardInvalidException;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.ObjectUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.BibliosEntity;
import com.ssitcloud.app_reader.entity.BibliosPageEntity;
import com.ssitcloud.app_reader.entity.BookUnionEntity;
import com.ssitcloud.app_reader.entity.InReservationEntity;
import com.ssitcloud.app_reader.entity.RenewEntity;
import com.ssitcloud.app_reader.entity.ReservationBookEntity;
import com.ssitcloud.app_reader.entity.ReservationEntity;
import com.ssitcloud.app_reader.entity.ReservationMessage;
import com.ssitcloud.app_reader.entity.StaticsTypeEntity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/3/21.
 * 服务实现类
 */

public class BookBizImpl extends AbstractAuthBizImpl implements BookBizI {
//    private final String TAG = "BookBizImpl";
    private Context mcontext;
    private Resources resources;

    private ConfigBizI configBiz;

    public BookBizImpl(Context context){
        this.mcontext = context.getApplicationContext();
        resources = this.mcontext.getResources();
        configBiz = new ConfigBizImpl(this.mcontext);
    }

    @Override
    public List<BibliosPageEntity> obtainRenewBook(Integer reader_idx, Integer lib_idx, String card_no) throws SocketException, ReaderCardInvalidException, AuthException {
        String url = RequestUrlUtil.getUrl(resources, R.string.renew_book_list_url);
        Map<String,Object> data = new HashMap<>(8);
        data.put("reader_idx",reader_idx);
        data.put("lib_idx",lib_idx);
        data.put("card_no",card_no);

        Map<String, String> param = new HashMap<>(3);
        param.put("json", JsonUtils.toJson(data));

        addAuthMessage(param);

        HttpResponce hr = HttpClientUtil.dopost(url,param,"utf-8");
        if(hr.getState() == 200){
            try{
                ResultEntity result = JsonUtils.fromJson(hr.getBody(),ResultEntity.class);
                if(result.getState()){
                    List<Map<String,Object>> resultData = (List<Map<String, Object>>) result.getResult();
                    List<BibliosPageEntity> returnData = new ArrayList<>(resultData.size());
                    for (Map<String, Object> map : resultData) {
                        BibliosPageEntity b = ObjectUtils.convertMap(map,BibliosPageEntity.class);
                        returnData.add(b);
                    }
                    return returnData;
                }else if("-1".equals(result.getRetMessage())
                        || "-2".equals(result.getRetMessage())
                        /*|| "-3".equals(result.getRetMessage())*/){
                    throw new ReaderCardInvalidException(result.getRetMessage());
                }else if("lib_not_support".equals(result.getRetMessage())){
                    throw new SocketException("lib_not_support");
                }
            }catch (RuntimeException|NoSuchMethodException|ObjectUtils.CanNotInstanceClass e) {
                throw new SocketException("服务器没有返回预期数据");
            }
        }else if(hr.getState() == 403){
            throw new AuthException();
        }

        throw new SocketException("can not link service");
    }

    @Override
    public RenewEntity renew(Integer reader_idx, Integer lib_idx, String card_no, String booksn) throws SocketException, ReaderCardInvalidException, AuthException {
        String url = RequestUrlUtil.getUrl(resources, R.string.renew_book_url);
        Map<String,Object> data = new HashMap<>(8);
        data.put("reader_idx",reader_idx);
        data.put("lib_idx",lib_idx);
        data.put("card_no",card_no);

        Map<String, String> param = new HashMap<>(3);
        param.put("json", JsonUtils.toJson(data));
        param.put("sn",booksn);

        addAuthMessage(param);

        HttpResponce hr = HttpClientUtil.dopost(url,param,"utf-8");
        if(hr.getState() == 200){
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                RenewEntity renewEntity = new RenewEntity();
                renewEntity.setState(result.getState());

                //检查是否为图书馆不支持
                if(!result.getState() && "lib_not_support".equals(result.getRetMessage())){
                    renewEntity.setMessage(resources.getString(R.string.lib_not_support));
                    return renewEntity;
                }else {
                    Map<String, Object> map = (Map<String, Object>) result.getResult();
                    if (map != null) {
                        renewEntity.setMessage(StringUtils.getStr(map.get("SCREENMESSAGE"), null));
                        String returnDate = StringUtils.getStr(map.get("SHOULDRETURNDATE"), null);
                        renewEntity.setReturnDate(StringUtils.isEmpty(returnDate) ? null : returnDate);
                    }
                    return renewEntity;
                }
            }catch (RuntimeException e){
                throw new SocketException("服务器没有返回预期数据");
            }
        }else if(hr.getState() == 403){
            throw new AuthException();
        }

        throw new SocketException("can not link service");

    }
    @Override
    public List<BookUnionEntity> findDeviceBook(Map<String, Object> selectData, Map<String, Object> idData, Integer pageCurrent, Integer pageSize) throws SocketException {
        if(idData == null){
            throw new IllegalArgumentException("idData is null");
        }

        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.book_item_url);
        Map<String,Object> data = new HashMap<>(10);
        if(selectData != null) {
            data.putAll(selectData);
        }
        data.put("idData",idData);
        data.put("pageCurrent",pageCurrent);
        data.put("pageSize",pageSize);

        Map<String,String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(data));

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String resultJson = hr.getBody();
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(resultJson,ResultEntity.class);
                if(resultEntity.getState()) {
                    List<Map<String, Object>> temps = (List<Map<String, Object>>) resultEntity.getResult();
                    List<BookUnionEntity> resultData = new ArrayList<>(temps.size());
                    for (Map<String, Object> temp : temps) {
                        BookUnionEntity opac = ObjectUtils.convertMap(temp, BookUnionEntity.class);
                        resultData.add(opac);
                    }
                    return resultData;
                }
            }catch (Exception e){
                throw new SocketException("服务器返回数据不是预期格式");
            }
        }


        throw new SocketException("网络连接失败，响应码"+hr.getState());
    }

    @Override
    public BibliosEntity findBibliosEntity(Integer bib_idx) throws SocketException {
        if(bib_idx == null){
            throw new IllegalArgumentException("bib_idx is null");
        }
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.book_info_url);

        Map<String,String> map = new HashMap<>(3);
        map.put("json", "{\"bib_idx\":"+bib_idx+"}");

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String resultJson = hr.getBody();
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(resultJson,ResultEntity.class);
                if(resultEntity.getState()) {
                    Map<String, Object> temps = (Map<String, Object>) resultEntity.getResult();
                    BibliosEntity bibliosEntity = ObjectUtils.convertMap(temps, BibliosEntity.class);
                    return bibliosEntity;
                }
            }catch (Exception e){
                throw new SocketException("服务器返回数据不是预期格式");
            }
        }

        throw new SocketException("网络连接失败，响应码"+hr.getState());
    }

    @Override
    public ReservationMessage reservationBook(ReservationEntity entity, Map<String, Object> idData) throws SocketException, AuthException {
        if(entity == null || idData == null || idData.isEmpty()){
            throw new IllegalArgumentException("args is error");
        }
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.reservation_url);


        Map<String,String> map = new HashMap<>(3,1.0f);
        map.put("json", JsonUtils.toJson(entity));
        map.put("idData",JsonUtils.toJson(idData));

        addAuthMessage(map);

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String resultJson = hr.getBody();
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(resultJson,ResultEntity.class);
                if(resultEntity.getState()) {
                    Map<String, Object> temps = (Map<String, Object>) resultEntity.getResult();
                    ReservationMessage reservationMessage = ObjectUtils.convertMap(temps, ReservationMessage.class);
                    return reservationMessage;
                } else if ("-1".equals(resultEntity.getRetMessage())
                        || "-2".equals(resultEntity.getRetMessage())
                        || "lib_not_support".equals(resultEntity.getRetMessage())) {
                    ReservationMessage rm = new ReservationMessage();
                    rm.setState(false);
                    rm.setCode(resultEntity.getRetMessage());
                    return rm;
                }else{
                    Map<String, Object> temps = (Map<String, Object>) resultEntity.getResult();
                    ReservationMessage reservationMessage = ObjectUtils.convertMap(temps, ReservationMessage.class);
                    if(reservationMessage != null){
                        return reservationMessage;
                    }
                }
            }catch (Exception e){
                throw new SocketException("服务器返回数据不是预期格式");
            }
        }

        throw new SocketException("网络连接失败，响应码"+hr.getState());
    }

    @Override
    public ReservationMessage inReservationBook(InReservationEntity entity) throws SocketException, AuthException {
        if(entity == null){
            throw new IllegalArgumentException("args is error");
        }
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.inReservation_url);


        Map<String,String> map = new HashMap<>(2,1.0f);
        map.put("json", JsonUtils.toJson(entity));

        addAuthMessage(map);

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String resultJson = hr.getBody();
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(resultJson,ResultEntity.class);
                if(resultEntity.getState()) {
                    Map<String, Object> temps = (Map<String, Object>) resultEntity.getResult();
                    ReservationMessage reservationMessage = ObjectUtils.convertMap(temps, ReservationMessage.class);
                    return reservationMessage;
                } else if ("-1".equals(resultEntity.getRetMessage())
                        || "-2".equals(resultEntity.getRetMessage())
                        || "lib_not_support".equals(resultEntity.getRetMessage())) {
                    ReservationMessage rm = new ReservationMessage();
                    rm.setState(false);
                    rm.setCode(resultEntity.getRetMessage());
                    return rm;
                }else{
                    Map<String, Object> temps = (Map<String, Object>) resultEntity.getResult();
                    ReservationMessage reservationMessage = ObjectUtils.convertMap(temps, ReservationMessage.class);
                    if(reservationMessage != null){
                        return reservationMessage;
                    }
                }
            }catch (Exception e){
                throw new SocketException("服务器返回数据不是预期格式");
            }
        }

        throw new SocketException("网络连接失败，响应码"+hr.getState());
    }

    @Override
    public String queryReservationAble(Integer bookItem) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources,R.string.selectBookState_url);
        Map<String,String> map = new HashMap<>(1,1.0f);
        map.put("json","{\"bookitem_idx\":"+bookItem+"}");
        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if(hr.getState() == 200){
            String j = hr.getBody();
            try{
                ResultEntity r = JsonUtils.fromJson(j,ResultEntity.class);
                if(r.getState()){
                    return String.valueOf(r.getResult());
                }
            }catch (Exception e){
                throw new SocketException("服务器没有返回预期数据");
            }
        }

        throw new SocketException();
    }

    @Override
    public List<StaticsTypeEntity> bookClassify() throws SocketException {
        String url = RequestUrlUtil.getUrl(resources,R.string.bookclassify_url);
        Map<String,String> map = new HashMap<>(1,1.0f);

        addAuthMessage(map);

        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if(hr.getState() == 200){
            String j = hr.getBody();
            try{
                ResultEntity r = JsonUtils.fromJson(j,ResultEntity.class);
                if(r.getState()){
                    List<Map<String, Object>> maps = (List<Map<String, Object>>) r.getResult();
                    List<StaticsTypeEntity> entities = new ArrayList<>(maps.size());

                    for (Map<String, Object> stringObjectMap : maps) {
                        entities.add(ObjectUtils.convertMap(stringObjectMap,StaticsTypeEntity.class));
                    }

                    return entities;
                }
            }catch (Exception e){
                throw new SocketException("服务器没有返回预期数据");
            }
        }

        throw new SocketException();
    }

    @Override
    public List<ReservationBookEntity> reservationBookList(ReservationEntity entity) throws SocketException, AuthException {
        String url = RequestUrlUtil.getUrl(resources,R.string.reservationState_list_url);
        Map<String,String> map = new HashMap<>(1,1.0f);
        map.put("json",JsonUtils.toJson(entity));

        addAuthMessage(map);

        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if(hr.getState() == 200){
            String j = hr.getBody();
            try{
                ResultEntity r = JsonUtils.fromJson(j,ResultEntity.class);
                if(r.getState()){
                    List<Map<String, Object>> maps = (List<Map<String, Object>>) r.getResult();
                    List<ReservationBookEntity> entities = new ArrayList<>(maps.size());

                    for (Map<String, Object> stringObjectMap : maps) {
                        entities.add(ObjectUtils.convertMap(stringObjectMap,ReservationBookEntity.class));
                    }

                    return entities;
                }else if("-1".equals(r.getRetMessage())
                        || "-2".equals(r.getRetMessage())
                        || "lib_not_support".equals(r.getRetMessage())){
                    throw new SocketException(r.getRetMessage());
                }
            }catch (Exception e){
                if(e instanceof SocketException){
                    throw ((SocketException) e);
                }
                throw new SocketException("服务器没有返回预期数据");
            }
        }

        throw new SocketException();
    }

    @Override
    protected ConfigBizI getConfigBiz() {
        return configBiz;
    }
}
