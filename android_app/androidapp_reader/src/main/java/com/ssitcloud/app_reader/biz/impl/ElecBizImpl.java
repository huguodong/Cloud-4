package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.ElecBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.ObjectUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.db.DbHelper;
import com.ssitcloud.app_reader.db.dao.ElecDao;
import com.ssitcloud.app_reader.entity.AppElectronicEntity;

import org.json.JSONObject;

import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/3/30.
 *
 */
public class ElecBizImpl extends AbstractAuthBizImpl implements ElecBizI {
    private ConfigBizI configBiz;
    private Context mContext;

    public ElecBizImpl(Context context){
        mContext = context.getApplicationContext();
        configBiz = new ConfigBizImpl(context);
    }


    @Override
    public List<AppElectronicEntity> obtainElecByService(Integer reader_idx, Date date,Integer pageSize,Integer pageCurrent) throws SocketException, AuthException {
        return obtainElecByService(reader_idx,date,null,pageSize,pageCurrent);
    }

    @Override
    public List<AppElectronicEntity> obtainUnReaderElecByService(Integer reader_idx,Integer pageSize,Integer pageCurrent) throws SocketException, AuthException {
        return obtainElecByService(reader_idx,null,0,pageSize,pageCurrent);
    }

    @Override
    public List<AppElectronicEntity> obtainElecByService(Integer reader_idx, Date date, Integer state, Integer pageSize, Integer pageCurrent) throws SocketException, AuthException {
        String url = RequestUrlUtil.getUrl(mContext.getResources(), R.string.elec_url);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> dataMap = new HashMap<>(10);
        dataMap.put("reader_idx",reader_idx);
        if(date != null) {
            dataMap.put("control_time", sdf.format(date));
        }
        dataMap.put("order",0);
        if(pageSize != null && pageCurrent != null){
            dataMap.put("pageSize",pageSize);
            dataMap.put("pageCurrent",pageCurrent);
        }
        if(state != null){
            dataMap.put("electronic_state",state);
        }

        Map<String,String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(dataMap));
        addAuthMessage(map);

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String resultJson = hr.getBody();
            try {
                ResultEntity resEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
                if(resEntity.getState()){
                    List<Map<String,Object>> list = (List<Map<String, Object>>) resEntity.getResult();
                    List<AppElectronicEntity> data = new ArrayList<>(list.size());
                    for (Map<String, Object> stringObjectMap : list) {
                        AppElectronicEntity elec = ObjectUtils.convertMap(stringObjectMap,AppElectronicEntity.class);
                        data.add(elec);
                    }

                    return data;
                }
            }catch (Exception e){
                throw new SocketException("服务器没有返回预期数据");
            }
        }else if(hr.getState() == 403){
            throw new AuthException();
        }

        throw new SocketException();
    }

    @Override
    public List<AppElectronicEntity> obtainElec() {
        DbHelper dh = new DbHelper(mContext);
        try {
            SQLiteDatabase rdb = dh.getReadableDatabase();
            List<AppElectronicEntity> d = ElecDao.selectAll(rdb);
            return d;
        }finally {
            dh.close();
        }
    }

    @Override
    public void setReadElec(Integer reader_idx, List<Integer> ids) throws SocketException, AuthException {
        String url = RequestUrlUtil.getUrl(mContext.getResources(), R.string.update_elec_url);
        Map<String,Object> dataMap = new HashMap<>(10);
        dataMap.put("reader_idx",reader_idx);
        dataMap.put("ids",ids);

        Map<String,String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(dataMap));

        addAuthMessage(map);

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String json = hr.getBody();
            try{
                ResultEntity r = JsonUtils.fromJson(json,ResultEntity.class);
//                return r.getState();
            }catch (Exception e){
                throw new SocketException("服务器没有返回预期数据");
            }
        }else if(hr.getState() == 403){
            throw new AuthException();
        }

        throw new SocketException();
    }

    /**
     * 获取图书推荐列表
     * @param lib_idx
     * @param cardNo
     * @return
     * @throws SocketException
     * @throws AuthException
     */
    @Override
    public List<String> getRecommendList(Integer lib_idx,String cardNo) throws SocketException,AuthException{
        String url = RequestUrlUtil.getUrl(mContext.getResources(), R.string.getRecommendList_url);
        Map<String,Object> dataMap = new HashMap<>(10);
        dataMap.put("library_idx",lib_idx);
        dataMap.put("cardNo",cardNo);

        Map<String,String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(dataMap));

        addAuthMessage(map);

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String json = hr.getBody();
            try{
                ResultEntity r = JsonUtils.fromJson(json,ResultEntity.class);
                if(r.getState()){
                    List<String> list=JsonUtils.fromJson(JsonUtils.toJson(r.getResult()),List.class);
                    return list;
                }
            }catch (Exception e){
                throw new SocketException("服务器没有返回预期数据");
            }
        }else if(hr.getState() == 403){
            throw new AuthException();
        }

        throw new SocketException();
    }

    @Override
    protected ConfigBizI getConfigBiz() {
        return configBiz;
    }
}
