package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.db.DbHelper;
import com.ssitcloud.app_reader.db.dao.ConfigDao;
import com.ssitcloud.app_reader.db.dao.ReaderCardDao;
import com.ssitcloud.app_reader.db.entity.ConfigDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.BindCardEntity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by LXP on 2017/3/9.
 *
 */

public class ReaderCardBizImpl extends AbstractAuthBizImpl implements ReaderCardBizI {
    private Resources resources;
    private Context context;
    private ConfigBizI configBiz;

    private final String charset = "utf-8";
    private final String TAG = "ReaderCardBizImpl";
    private final String preferCardKey = "perferCard";
    private final String preferCardSqlit = "LXP_READER_CARD_PREFER_Sqlit";

    public ReaderCardBizImpl(Resources resources, Context context){
        this.resources = resources;
        this.context = context.getApplicationContext();
        configBiz = new ConfigBizImpl(this.context);
    }

    @Override
    public List<ReaderCardDbEntity> obtainReaderCardByService(Integer reader_idx) throws SocketException,AuthException {
        String url = RequestUrlUtil.getUrl(resources, R.string.reader_cards_url);
        Map<String,String> map = new HashMap<>(3);
        map.put("json","{\"reader_idx\":"+reader_idx+"}");
        addAuthMessage(map);

        HttpResponce hr = HttpClientUtil.dopost(url,map, charset);
        if(hr.getState() == 200){
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                if(result != null && result.getState()){
                    Map<String,Object> remoteMap = (Map<String, Object>) result.getResult();
                    List<Map<String,Object>> libraryData = (List<Map<String, Object>>) remoteMap.get("lib");
                    List<Map<String ,Object>> readerCardData = (List<Map<String, Object>>) remoteMap.get("card");

                    //read data
                    List<ReaderCardDbEntity> returnData = new ArrayList<>(readerCardData.size());
                    DbHelper dbHelp = new DbHelper(context);
                    SQLiteDatabase writeDb = null;
                    try {
                        writeDb = dbHelp.getWritableDatabase();
                    }catch (SQLiteException e){
                        Log.e(TAG,"open write db error");
                    }
                    try {
                        if(writeDb != null) {
                            writeDb.beginTransaction();//begin transaction
                            ReaderCardDao.deleteAll(writeDb);
                        }
                        for (int i = 0, z = readerCardData.size(); i < z; ++i) {
                            ReaderCardDbEntity rcde = new ReaderCardDbEntity();
                            Map<String, Object> m = readerCardData.get(i);
                            rcde.setCard_no(String.valueOf(m.get("card_no")));
                            rcde.setLib_idx((Integer) m.get("lib_idx"));

                            rcde.setAllown_loancount((Integer) m.get("allown_loancount"));
                            rcde.setSurplus_count((Integer) m.get("surplus_count"));
                            rcde.setAdvance_charge((Double) m.get("advance_charge"));
                            rcde.setDeposit((Double) m.get("deposit"));
                            rcde.setArrearage((Double) m.get("arrearage"));
                            rcde.setCreate_time(String.valueOf(m.get("create_time")));
                            rcde.setUpdate_time(String.valueOf(m.get("update_time")));
                            rcde.setCardPwd((String) m.get("card_password"));
                            rcde.setReader_name((String) m.get("reader_name"));
                            //found library information
                            for (int j = 0, j1 = libraryData.size(); j < j1; ++j) {
                                Map<String, Object> m2 = libraryData.get(j);
                                Object o = m2.get("library_idx");
                                if (StringUtils.isEqual(rcde.getLib_idx(), o)) {
                                    rcde.setLib_name(String.valueOf(m2.get("lib_name")));
                                    rcde.setLib_id((String) m2.get("lib_id"));
                                    break;
                                }
                            }
                            //add list and insert database
                            returnData.add(rcde);
                            if(writeDb != null) {
                                ReaderCardDao.insert(writeDb, rcde);
                            }
                        }

                        if(writeDb != null) {
                            writeDb.setTransactionSuccessful();//end transaction
                        }
                    }finally {
                        if(writeDb != null) {
                            writeDb.endTransaction();
                        }
                        dbHelp.close();
                    }
                    //sort
                    Collections.sort(returnData,getNomalComparator());
                    return returnData;
                }else{
                    throw new SocketException("获取数据失败");
                }
            }catch (Exception e){
                Log.i(TAG,"发生了异常",e);
                throw new SocketException("服务器没有返回期望数据");
            }
        }else if(hr.getState() == 403) {
            throw new AuthException();
        }else{
            throw new SocketException("获取数据失败");
        }
    }

    @Override
    public Observable<ReaderCardDbEntity> obtainReaderCardByService(final Integer reader_idx,final Integer lib_idx,final String cardno){
        return Observable.create(new ObservableOnSubscribe<ReaderCardDbEntity>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ReaderCardDbEntity> ne) throws Exception {
                String url = RequestUrlUtil.getUrl(resources, R.string.reader_card_url);
                Map<String,String> map = new HashMap<>(3);
                map.put("json",String.format("{\"reader_idx\":%s,\"lib_idx\":%s,\"card_no\":\"%s\"}"
                        ,reader_idx.toString(),lib_idx.toString(),cardno));
                addAuthMessage(map);

                HttpResponce hr = HttpClientUtil.dopost(url,map, charset);
                if(hr.getState() == 200){
                    try {
                        ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                        if(result != null && result.getState()){
                            List<Map<String,Object>> remoteMap = (List<Map<String,Object>>) result.getResult();
                            if(remoteMap != null && !remoteMap.isEmpty()) {
                                Map<String, Object> data = remoteMap.get(0);
                                ReaderCardDbEntity rcde = mapToEntity(data);
                                DbHelper dbHelp = new DbHelper(context);
                                SQLiteDatabase wdb = null;
                                try {
                                    wdb = dbHelp.getWritableDatabase();
                                    wdb.beginTransaction();
                                    ReaderCardDao.update(wdb,rcde);
                                }finally {
                                    if(wdb != null){
                                        wdb.setTransactionSuccessful();
                                        wdb.endTransaction();
                                    }
                                    dbHelp.close();
                                }
                                ne.onNext(rcde);
                            }else{
                                ne.onNext(null);
                            }
                            ne.onComplete();
                            return ;
                        }

                        throw new SocketException("获取数据失败");
                    }catch (Exception e){
                        Log.i(TAG,"发生了异常",e);
                        throw new SocketException("服务器没有返回期望数据");
                    }
                }else if(hr.getState() == 403) {
                    throw new AuthException();
                }else{
                    throw new SocketException("获取数据失败");
                }
            }
        });
    }

    @Override
    public List<ReaderCardDbEntity> obtainReaderCardByBb() {
        DbHelper dhHelp = new DbHelper(context);

        try{
            SQLiteDatabase readerDb = dhHelp.getReadableDatabase();
            List<ReaderCardDbEntity> select = ReaderCardDao.select(readerDb);
            Collections.sort(select,getNomalComparator());
            return select;
        }catch (SQLException e){
            Log.e(TAG,"open reader db error,"+e.getMessage());
            return new ArrayList<>(0);
        }finally {
            dhHelp.close();
        }
    }

    @Override
    public boolean unbindCard(ReaderCardDbEntity cardInfo, Integer reader_idx) throws SocketException, AuthException {
        String url = RequestUrlUtil.getUrl(resources,R.string.reader_card_unbain_url);

        Map<String,Object> data = new HashMap<>(3);
        data.put("reader_idx",reader_idx);//add reader idx

        List<Map<String,Object>> cardList = new ArrayList<>(1);
        Map<String,Object> cardMap = new HashMap<>(3);
        cardMap.put("card_no",cardInfo.getCard_no());
        cardMap.put("lib_idx",cardInfo.getLib_idx());
        cardList.add(cardMap);
        data.put("cards",cardList);//add card List

        Map<String, String> param =new HashMap<>(3);
        param.put("json",JsonUtils.toJson(data));

        //加入鉴权数据
        addAuthMessage(param);

        HttpResponce hr = HttpClientUtil.dopost(url, param,charset);
        if(hr.getState() == 200){
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                //write data to database
                if(result.getState()){
                    try {
                        DbHelper dhHelp = new DbHelper(context);
                        SQLiteDatabase writeDb = dhHelp.getWritableDatabase();
                        try {
                            writeDb.beginTransaction();
                            ReaderCardDao.delete(writeDb, cardInfo);
                            //删除偏好卡设置，若存在
                            ConfigDao.deleteByEntity(writeDb,getPreferConfig(cardInfo));
                            writeDb.setTransactionSuccessful();
                        } finally {
                            if(writeDb != null) {
                                writeDb.endTransaction();
                            }
                            dhHelp.close();
                        }
                    }catch (SQLException sqlException) {
                        Log.e(TAG,"write database error,e==>"+ sqlException.getMessage());
                    }
                }
                return result.getState();
            }catch (Exception e){
                throw new SocketException("获取不到预期数据格式，data==>"+hr.getBody());
            }
        }else if(hr.getState() == 403) {
            throw new AuthException();
        }else{
            throw new SocketException("获取服务器失败");
        }
    }

    @Override
    public int bindCard(BindCardEntity cardInfo, Integer reader_idx) throws SocketException, AuthException {
        String url = RequestUrlUtil.getUrl(resources,R.string.reader_card_bind_url);
        Map<String,Object> dataMap = new HashMap<>(8);
        dataMap.put("reader_idx",reader_idx);
        dataMap.put("lib_idx",cardInfo.getLib_idx());
        dataMap.put("card_no",cardInfo.getCard_no());
        dataMap.put("card_password",cardInfo.getCard_pwd());

        Map<String,String> param = new HashMap<>(3);
        param.put("json",JsonUtils.toJson(dataMap));

        //加入鉴权数据
        addAuthMessage(param);

        HttpResponce hr = HttpClientUtil.dopost(url,param,charset);
        if(hr.getState() == 200){
            try{
                ResultEntity resultEntity = JsonUtils.fromJson(hr.getBody(),ResultEntity.class);
                if(resultEntity.getState()){
                    //插入读者证
                    ReaderCardDbEntity dbModel = new ReaderCardDbEntity();
                    dbModel.setLib_idx(cardInfo.getLib_idx());
                    dbModel.setCard_no(cardInfo.getCard_no());
                    dbModel.setCardPwd(cardInfo.getCard_pwd());
                    DbHelper dbHelper = new DbHelper(context);
                    try{
                        SQLiteDatabase wdb = dbHelper.getReadableDatabase();
                        ReaderCardDao.insert(wdb,dbModel);
                    }finally {
                        dbHelper.close();
                    }
                    return 0;
                }else{
                    if("-1".equals(resultEntity.getRetMessage())){
                        return -1;
                    }else if("-2".equals(resultEntity.getRetMessage())){//-2
                        return -2;
                    }else if("-3".equals(resultEntity.getRetMessage())){
                        return -3;
                    }else if("lib_not_support".equals(resultEntity.getRetMessage())){
                        return -4;
                    }else{
                        throw new SocketException("请求服务器失败");
                    }
                }
            }catch (Exception e){
                throw new SocketException("请求服务器数据不为预期格式");
            }
        }else if(hr.getState() == 403) {
            throw new AuthException();
        }else{
            throw new SocketException("请求服务器失败");
        }
    }

    @Override
    public ReaderCardDbEntity obtainPreferCard() {
        DbHelper dh = new DbHelper(context);
        try {
            SQLiteDatabase readDb = dh.getReadableDatabase();
            //查询本地读者卡
            List<ReaderCardDbEntity> readerCardList = ReaderCardDao.select(readDb);

            if(readerCardList.isEmpty()){//本地数据库无读者卡数据
                return null;
            }
            //查询配置
            ConfigDbEntity configDbEntity = ConfigDao.selectBykey(readDb, preferCardKey);
            if (configDbEntity == null){//没有查到配置
                Collections.sort(readerCardList,getNomalComparator());
                return readerCardList.get(0);
            }else{
                ReaderCardDbEntity target = null;
                String[] info = configDbEntity.getConfigValue().split(preferCardSqlit);
                String infoCardNo = info[0];
                Integer infoLibIdx = Integer.valueOf(info[1]);
                for (ReaderCardDbEntity readerCard : readerCardList) {
                    Integer lib_idx = readerCard.getLib_idx();
                    String card_no = readerCard.getCard_no();
                    if(card_no.equals(infoCardNo) && lib_idx.equals(infoLibIdx)){
                        target = readerCard;
                        break;
                    }
                }
                if(target == null){//根据配置查询卡失败，返回首张卡
                    Collections.sort(readerCardList,getNomalComparator());
                    return readerCardList.get(0);
                }
                return target;
            }
        }catch (SQLException e){
            Log.e(TAG,"open read databse error");
        }finally {
            dh.close();
        }
        return null;
    }

    @Override
    public void setPreferCard(ReaderCardDbEntity readerCard) {
        DbHelper dh = new DbHelper(context);
        try{
            SQLiteDatabase writeDb = dh.getWritableDatabase();
            ConfigDao.insert(writeDb,getPreferConfig(readerCard));
        }catch (SQLException e){
            Log.e(TAG,"open write db error");
        }finally {
            dh.close();
        }
    }

    /**
     * 获取卡偏好设置的对应的config值
     * @param readerCard 读者卡信息
     * @return config值
     */
    private ConfigDbEntity getPreferConfig(ReaderCardDbEntity readerCard){
        ConfigDbEntity entity = new ConfigDbEntity();
        entity.setConfigKey(preferCardKey);
        entity.setConfigValue(readerCard.getCard_no()+preferCardSqlit+readerCard.getLib_idx());
        return entity;
    }

    private ReaderCardDbEntity mapToEntity(Map<String,Object> map){
        ReaderCardDbEntity rcde = new ReaderCardDbEntity();
        rcde.setCard_no(String.valueOf(map.get("card_no")));
        rcde.setLib_idx((Integer) map.get("lib_idx"));
        rcde.setAllown_loancount((Integer) map.get("allown_loancount"));
        rcde.setSurplus_count((Integer) map.get("surplus_count"));
        rcde.setAdvance_charge((Double) map.get("advance_charge"));
        rcde.setDeposit((Double) map.get("deposit"));
        rcde.setArrearage((Double) map.get("arrearage"));
        rcde.setCreate_time(String.valueOf(map.get("create_time")));
        rcde.setUpdate_time(String.valueOf(map.get("update_time")));
        rcde.setCardPwd((String) map.get("card_password"));
        rcde.setReader_name((String) map.get("reader_name"));

        return rcde;
    }

    private Comparator<ReaderCardDbEntity> getNomalComparator(){
        return new Comparator<ReaderCardDbEntity>() {
            @Override
            public int compare(ReaderCardDbEntity o1, ReaderCardDbEntity o2) {
                String s1  = o1.getLib_name();
                String s2 = o2.getLib_name();
                if(s1 == null && s2 == null){
                    return 0;
                }
                if(s1 == null){
                    return -1;
                }
                if(s2 == null){
                    return 1;
                }

                int c = s1.compareTo(s2);
                return c!=0?c:o1.getCard_no().compareTo(o2.getCard_no());
            }
        };
    }

    @Override
    protected ConfigBizI getConfigBiz() {
        return configBiz;
    }
}
