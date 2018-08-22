package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;
import android.util.ArrayMap;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.FeedbackBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.entity.FeedbackEntity;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2017/4/19.
 * 反馈业务实现类
 */

public class FeedbackBizImpl extends AbstractAuthBizImpl implements FeedbackBizI {
    private Context mcontext;
    private ConfigBizI configBiz;

    public FeedbackBizImpl(Context context){
        mcontext = context.getApplicationContext();
        configBiz = new ConfigBizImpl(context);
    }


    @Override
    public boolean feedback(FeedbackEntity feedbackEntity) throws SocketException, AuthException {
        String url = RequestUrlUtil.getUrl(mcontext.getResources(), R.string.feedback_url);
        Map<String, String> param = new android.support.v4.util.ArrayMap<>();
        param.put("json", JsonUtils.toJson(feedbackEntity));

        addAuthMessage(param);

        HttpResponce hr = HttpClientUtil.dopost(url, param, charset);
        if(hr.getState() == 200){
            String json = hr.getBody();
            try {
                ResultEntity resultEntity = JsonUtils.fromJson(json,ResultEntity.class);
                return resultEntity.getState();
            } catch (Exception e) {
                throw new SocketException("服务器没有返回预期数据，data==>"+json);
            }
        }else if(hr.getState() == 403){
            throw  new AuthException();
        }
        throw new SocketException("连接服务器失败");
    }

    @Override
    protected ConfigBizI getConfigBiz() {
        return configBiz;
    }
}
