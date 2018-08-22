package com.ssitcloud.business.mobile.common.filter;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by LXP on 2017/8/14.
 */

public class WebReaderAuthFilter extends AuthFilter{
    @Override
    protected void check(HttpServletRequest request) {

    }

    @Override
    protected void forbidden(HttpServletRequest request, HttpServletResponse response) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setState(false);
        resultEntity.setRetMessage("forbidden");
        try(OutputStream out = response.getOutputStream()){
            out.write(JsonUtils.toJson(resultEntity).getBytes("UTF-8"));
            out.flush();
        } catch (IOException e) {
            LogUtils.info(e);
        }
    }

    @Override
    protected String getAuthStr(HttpServletRequest request) {
        String authStr = request.getParameter("auth");
        String authStrJson = request.getParameter("auth_json");
        if(authStr == null && authStrJson != null){
            Map<String, Object> map = JsonUtils.fromJson(authStrJson, Map.class);
            authStr = (String) map.get("auth");
        }
        return authStr;
    }
}
