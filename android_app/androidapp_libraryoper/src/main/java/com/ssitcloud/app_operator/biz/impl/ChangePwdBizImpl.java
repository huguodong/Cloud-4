package com.ssitcloud.app_operator.biz.impl;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.ChangePwdBizI;
import com.ssitcloud.app_operator.common.entity.HttpResponce;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.HttpClientUtil;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.RequestUrlUtil;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建日期：2017/3/22 19:12
 * @author shuangjunjie
 */

public class ChangePwdBizImpl implements ChangePwdBizI{
    private final String TAG = "ChangePwdBizImpl";
    private final String charset = "utf-8";
    private Resources resources;
    private Context context;

    public ChangePwdBizImpl(Resources resources, Context context) {
        this.resources = resources;
        this.context = context.getApplicationContext();
    }

    @Override
    public ResultEntity changePwd(Map<String, Object> reqMap) throws SocketException {
        String url = RequestUrlUtil.getUrl(resources, R.string.change_pwd_url);
        Map<String, String> map = new HashMap<>();
        map.put("json", JsonUtils.toJson(reqMap));
        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
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
