package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.ReaderBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2017/3/27.
 * 用户信息服务业务类
 */

public class ReaderBizImpl extends AbstractAuthBizImpl implements ReaderBizI {


    private Context mcontext;
    private final String charset = "utf-8";
    private ConfigBizI configBiz;

    public ReaderBizImpl(Context context){
        mcontext = context.getApplicationContext();
        configBiz = new ConfigBizImpl(mcontext);
    }


    @Override
    public int forgetPwd(ReaderInfoEntity reader,String vcode) throws SocketException{
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.forget_pwd_url);
        HashMap<String,String> map = new HashMap<>(3);
        map.put("json", JsonUtils.toJson(reader));
        if(vcode != null) {
            map.put("vcode", vcode);
        }
        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String json = hr.getBody();
            try{
                ResultEntity resultEntity = JsonUtils.fromJson(json,ResultEntity.class);
                if (resultEntity.getState()) {
                    return success;
                } else if ("2".equals(resultEntity.getRetMessage())) {
                    return noUser;
                } else if ("3".equals(resultEntity.getRetMessage())) {
                    return vcodeTimeOut;
                } else if ("4".equals(resultEntity.getRetMessage())) {
                    return vcodeError;
                } else {
                    return unKnowError;
                }
            }catch (Exception e){
                throw new SocketException("服务器没有返回预期数据");
            }
        }
        throw new SocketException("连接服务器失败");
    }

    @Override
    public boolean changePwd(Integer idx, String oldPwd, String newPwd) throws SocketException, AuthException {
        String url = RequestUrlUtil.getUrl(mcontext.getResources(),R.string.change_pwd_url);
        HashMap<String,Object> data = new HashMap<>(6);
        data.put("reader_idx",idx);
        data.put("old_pwd",oldPwd);
        data.put("new_pwd",newPwd);
        HashMap<String,String> map = new HashMap<>(3);
        map.put("json",JsonUtils.toJson(data));

        addAuthMessage(map);

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String json = hr.getBody();
            try{
                ResultEntity resultEntity = JsonUtils.fromJson(json,ResultEntity.class);
                return resultEntity.getState();
            }catch (Exception e){
                throw new SocketException("服务器没有返回预期数据");
            }
        }else if(hr.getState() == 403){
            throw new AuthException();
        }
        throw new SocketException("连接服务器失败");
    }

    @Override
    public boolean modifyInfo(ReaderInfoEntity readerInfo) throws SocketException, AuthException {
        String url = RequestUrlUtil.getUrl(mcontext.getResources(),R.string.reader_update);
        HashMap<String,String> map = new HashMap<>(3);
        map.put("json",JsonUtils.toJson(readerInfo));

        addAuthMessage(map);

        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if(hr.getState() == 200){
            String rJson = hr.getBody();
            try{
                ResultEntity resultEntity = JsonUtils.fromJson(rJson,ResultEntity.class);
                return resultEntity.getState();
            }catch (Exception e){
                throw new SocketException("没有返回预期格式数据");
            }
        }else if(hr.getState() == 403){
            throw new AuthException();
        }
        throw new SocketException();
    }

    @Override
    public boolean readerAuth(LoginInfoDbEntity loginInfo, ReaderCardDbEntity card, String barcode)
            throws SocketException,AuthException{
        Integer reader_idx = loginInfo.getReader_idx();
        Integer lib_idx = card.getLib_idx();
        String card_no = card.getCard_no();
        if(reader_idx == null){
            throw new IllegalArgumentException("loginInfo==>reader_idx is null");
        }
        if(lib_idx == null){
            throw new IllegalArgumentException("ReaderCardDbEntity==>lib_idx is null");
        }
        if(card_no == null){
            throw new IllegalArgumentException("ReaderCardDbEntity==>card_no is null");
        }

        String url = RequestUrlUtil.getUrl(mcontext.getResources(),R.string.reader_auth_url);

        Map<String,Object> data = new HashMap<>(8,1.0f);
        data.put("reader_idx",reader_idx);
        data.put("lib_idx",lib_idx);
        data.put("card_no",card_no);
        data.put("barcode",barcode);
        data.put("app_type",2);

        Map<String,String> param = new HashMap<>(1,1.0f);
        param.put("json",JsonUtils.toJson(data));

        addAuthMessage(param);

        HttpResponce hr = HttpClientUtil.dopost(url,param,charset);
        if(hr.getState() == 200){
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(hr.getBody(),ResultEntity.class);
                return resultEntity.getState();
            } catch (Exception e) {
                throw new SocketException("服务器没有返回预期数据");
            }
        }else if(hr.getState()==403){
            throw new AuthException();
        }

        throw new SocketException();
    }

    @Override
    protected ConfigBizI getConfigBiz() {
        return configBiz;
    }

}
