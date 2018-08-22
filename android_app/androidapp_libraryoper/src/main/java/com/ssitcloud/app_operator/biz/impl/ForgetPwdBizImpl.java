package com.ssitcloud.app_operator.biz.impl;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.ForgetPwdBizI;
import com.ssitcloud.app_operator.common.entity.HttpResponce;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.HttpClientUtil;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.RequestUrlUtil;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建日期：2017/3/21 17:42
 * @author shuangjunjie
 */

public class ForgetPwdBizImpl implements ForgetPwdBizI{

    private final String TAG = "ForgetPwdBizImpl";
    private final String charset = "utf-8";
    private Resources resources;
    private Context context;

    public ForgetPwdBizImpl(Resources resources, Context context) {
        this.resources = resources;
        this.context = context.getApplicationContext();
    }

    @Override
    public ResultEntity sendFoundVcodeVcode(String mobile) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.send_found_pwd_vcode_url);
        Map<String, String> map = new HashMap<>();
        map.put("json", "{\"mobile\":\""+mobile+"\"}");
        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if (hr.getState() == 200){
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                return result;
            }catch (Exception e){
                Log.e(TAG,"to json exception",e);
                return null;
            }
        }else {
            throw new SocketException("网络异常");
        }
    }

    @Override
    public ResultEntity changePwdByVocde(String mobile, String vcode, String pwd) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.change_pwd_by_vcode_url);
        Map<String, String> m = new HashMap<>();
        m.put("mobile",mobile);
        m.put("vcode",vcode);
        m.put("pwd",pwd);
        Map<String, String> map = new HashMap<>(8);

        map.put("json", JsonUtils.toJson(m));
        HttpResponce hr = HttpClientUtil.dopost(url, map, charset);
        if (hr.getState() == 200){
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                return result;
            }catch (Exception e){
                Log.e(TAG,"to json exception",e);
                return null;
            }
        }else {
            throw new SocketException("网络异常");
        }
    }
}
