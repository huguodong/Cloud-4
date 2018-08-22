package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.SecurityBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.exception.NonExistentPublicKey;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.ObjectUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.common.utils.RsaHelper;
import com.ssitcloud.app_reader.entity.PublicKeyEntity;

import java.net.SocketException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2017/4/14.
 *
 */

public class SecurityBizImpl implements SecurityBizI {
    private Context mcontext;
    private ConfigBizI configBiz;

    public SecurityBizImpl(Context context){
        mcontext = context.getApplicationContext();
        configBiz = new ConfigBizImpl(mcontext);
    }
    @Override
    public byte[] encryptData(byte[] data) {
        PublicKeyEntity pukEntity = configBiz.getPuk();
        PublicKey publicKey = RsaHelper.decodePublicKeyFromXml(pukEntity.getPublickey());
        return RsaHelper.encryptData(data,publicKey);
    }

    @Override
    public boolean updatePuk() throws SocketException {
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.update_puk_url);
        Map<String,String> param = new HashMap<>(3);
        param.put("json","{\"app_type\":2}");
        HttpResponce hr = HttpClientUtil.dopost(url,param,"utf-8");
        if(hr.getState() == 200){
            String returnJson = hr.getBody();
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(returnJson,ResultEntity.class);
                if(resultEntity.getState()){
                    Map<String,Object> r = (Map<String, Object>) resultEntity.getResult();
                    PublicKeyEntity pukEntity = ObjectUtils.convertMap(r,PublicKeyEntity.class);
                    configBiz.updatePuk(pukEntity);
                }
            }catch (Exception e){
                throw new SocketException("服务器没有返回预期数据，data==>"+returnJson);
            }
        }

        throw new SocketException("服务器响应失败，响应码==>"+hr.getState());
    }

    @Override
    public PublicKeyEntity getPuk() throws NonExistentPublicKey {
        PublicKeyEntity puk = configBiz.getPuk();
        if(puk != null){
            return puk;
        }

        throw new NonExistentPublicKey();
    }
}
